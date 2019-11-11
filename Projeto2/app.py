#!/usr/bin/env python
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.orm import defer
from werkzeug.security import generate_password_hash, check_password_hash
from flask import Flask, render_template, flash, redirect, url_for, request, session
from meusforms import FormGeraProva, LoginForm, FormDeRegistro, FormCadastroDisciplinas, FormCadastroAssuntos, FormCadastroQuestoes
from flask_bootstrap import Bootstrap
from flask_nav import Nav
from flask_nav.elements import Navbar, View, Subgroup, Link
import datetime

SECRET_KEY = 'adhaufYHi124&*&%asoifhwiafnsfp'


app = Flask(__name__)
bootstrap = Bootstrap(app)
app.secret_key = SECRET_KEY

app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///mydatabase.sqlite'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS']=False
db = SQLAlchemy(app)

nav = Nav()
nav.init_app(app) # isso habilita a criação de menus de navegação do pacote Flask-Nav


# Para gerar o banco de dados pela 1a. vez:
#
# Executar a shell interativa do python3 e dentro dela digitar:
#
# from app import db, Usuarios, Disciplinas, Assuntos, Questoes, Provas, Historico
# db.create_all()

#####Inicio definição do banco de dados

class Usuarios(db.Model):
    __tablename__ = "Usuarios"
    idUsuario = db.Column(db.Integer, primary_key=True, autoincrement=True)
    nmUsuario = db.Column(db.String(50), unique=True)
    senha = db.Column(db.String(128))

    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        self.nmUsuario = kwargs.pop('nmUsuario')
        self.senha = generate_password_hash(kwargs.pop('senha'))

    def set_password(self, password):
        self.senha = generate_password_hash(password)

    def check_password(self, password):
        return check_password_hash(self.senha, password)

    def __repr__(self):
        return '<User {}>'.format(self.nmUsuario)


class Disciplinas(db.Model):
    __tablename__ = "Disciplinas"
    idDisciplina = db.Column(db.Integer, primary_key=True, autoincrement=True)
    nmDisciplina = db.Column(db.String(100), unique=True)
    idUsuario = db.Column(db.Integer, db.ForeignKey("Usuarios.idUsuario"))

    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        self.nmDisciplina = kwargs.pop('nmDisciplina')
        self.idUsuario = kwargs.pop('idUsuario')

class Assuntos(db.Model):
	__tablename__ = "Assuntos"
	idAssunto = db.Column(db.Integer, primary_key=True, autoincrement=True)
	nmAssunto = db.Column(db.String(100), unique=True)
	idUsuario = db.Column(db.Integer, db.ForeignKey("Usuarios.idUsuario"))
	idDisciplina = db.Column(db.Integer, db.ForeignKey("Disciplinas.idDisciplina"))


	def __init__(self, **kwargs):
		super().__init__(**kwargs)
		self.nmAssunto = kwargs.pop('nmAssunto')
		self.idUsuario = kwargs.pop('idUsuario')
		self.idDisciplina = kwargs.pop('idDisciplina')

class Questoes(db.Model):
	__tablename__ = "Questoes"

	__table_args__ = (
		db.ForeignKeyConstraint(
			['idAssunto'],
			['Assuntos.idAssunto'],
			#['idAssunto', 'idDisciplina', 'idUsuario'],
			#['Assuntos.idAssunto', 'Disciplinas.idDisciplina', 'Usuarios.idUsuario'],
		),
	)

	idQuestao = db.Column(db.Integer, primary_key=True, autoincrement=True)
	nmTipo = db.Column(db.String(50))
	textoQuestao = db.Column(db.String(8000))
	respostaQuestao = db.Column(db.String(8000))
	idAssunto = db.Column(db.Integer, db.ForeignKey("Assuntos.idAssunto"))
	idDisciplina = db.Column(db.Integer, db.ForeignKey("Disciplinas.idDisciplina"))
	idUsuario = db.Column(db.Integer, db.ForeignKey("Usuarios.idUsuario"))

	def __init__(self, **kwargs):
		super().__init__(**kwargs)
		self.nmTipo = kwargs.pop('nmTipo')
		self.textoQuestao = kwargs.pop('textoQuestao')
		self.respostaQuestao = kwargs.pop('respostaQuestao')
		self.idAssunto = kwargs.pop('idAssunto')
		self.idUsuario = kwargs.pop('idUsuario')
		self.idDisciplina = kwargs.pop('idDisciplina')


class Provas(db.Model):
	__tablename__ = "Provas"
	idProva = db.Column(db.Integer, primary_key=True, autoincrement=True)
	dtRealizacao = db.Column(db.Date)

	def __init__(self, **kwargs):
		super().__init__(**kwargs)
		self.dtRealizacao = kwargs.pop('dtRealizacao')


class Historico(db.Model):
	__tablename__ = "Historico"
	idProva = db.Column(db.Integer, db.ForeignKey("Provas.idProva"), primary_key=True)
	idAssunto = db.Column(db.Integer, db.ForeignKey("Assuntos.idAssunto"), primary_key=True)
	idDisciplina = db.Column(db.Integer, db.ForeignKey("Disciplinas.idDisciplina"), primary_key=True)
	idUsuario = db.Column(db.Integer, db.ForeignKey("Usuarios.idUsuario"), primary_key=True)
	idQuestao = db.Column(db.Integer, db.ForeignKey("Questoes.idQuestao"), primary_key=True)



####Fim definição do banco de dados

####Inicio das definições da API


@nav.navigation()
def meunavbar():
    menu = Navbar('Test Maker 2.0')
    menu.items = [View('Home', 'inicio')]
    #menu.items.append(Subgroup('Menu de opções',View('Aluno','aluno'),View('Professor','professor')))
    if session.get('logged_in')==False or session.get('logged_in')==None:
        menu.items.append(View('Registro', 'cadastro'))
        menu.items.append(View('Login', 'autenticar'))
    if session.get('logged_in'):
        menu.items.append(View('Disciplinas', 'disciplinas'))
        menu.items.append(View('Provas', 'provas'))
        menu.items.append(View('Logout', 'logout'))
    #menu.items.append(Link('Ajuda','https://www.google.com'))
    return menu

@app.route('/inserirDisciplina', methods=['GET', 'POST'])
def inserirDisciplina():
	form = FormCadastroDisciplinas()
	if form.validate_on_submit():
		novaDisciplina = Disciplinas(nmDisciplina=form.nome.data, idUsuario=session['idUsuario'])
		db.session.add(novaDisciplina)
		db.session.commit()
		return redirect(url_for('disciplinas'))
	return render_template('inserirDisciplina.html', title='Cadastro de disciplina', form=form)


@app.route('/disciplinas', methods=['GET', 'POST'])
def disciplinas():
    select_disciplinas = Disciplinas.query.filter_by(idUsuario=session['idUsuario']).all()

    return render_template('disciplinas.html', title='Listar Disciplinas', disciplinas=select_disciplinas)


@app.route('/inserirAssunto', methods=['GET', 'POST'])
def inserirAssunto():
	idDisciplina = request.args['idDisciplina']
	form = FormCadastroAssuntos()
	if form.validate_on_submit():
		novoAssunto = Assuntos(nmAssunto=form.nome.data, idUsuario=session['idUsuario'], idDisciplina=idDisciplina)
		db.session.add(novoAssunto)
		db.session.commit()
		return redirect(url_for('assuntos',idDisciplina=idDisciplina))
	return render_template('inserirAssunto.html', title='Cadastro de assuntos', form=form)


@app.route('/assuntos', methods=['GET', 'POST'])
def assuntos():
	idDisciplina = request.args['idDisciplina']
	select_assuntos = Assuntos.query.filter_by(idDisciplina=idDisciplina, idUsuario=session['idUsuario']).all()

	return render_template('assuntos.html', title='Listar Assuntos', idDisciplina=idDisciplina, assuntos=select_assuntos)


@app.route('/inserirQuestao', methods=['GET', 'POST'])
def inserirQuestao():
	idAssunto = request.args['idAssunto']
	idDisciplina = request.args['idDisciplina']
	form = FormCadastroQuestoes()
	if form.validate_on_submit():
		novaQuestao = Questoes(nmTipo=form.tipo.data, textoQuestao=form.texto.data, respostaQuestao=form.resp.data, idAssunto=idAssunto, idDisciplina=idDisciplina, idUsuario=session['idUsuario'])
		db.session.add(novaQuestao)
		db.session.commit()
		return redirect(url_for('questoes', idAssunto=idAssunto,idDisciplina=idDisciplina))
	return render_template('inserirQuestao.html', title='Cadastro de questões', form=form)


@app.route('/questoes', methods=['GET', 'POST'])
def questoes():
	idAssunto = request.args['idAssunto']
	idDisciplina = request.args['idDisciplina']
	select_questoes = Questoes.query.filter_by(idAssunto=idAssunto, idDisciplina=idDisciplina, idUsuario=session['idUsuario']).all()

	return render_template('questoes.html', title='Listar Questões', idAssunto=idAssunto, idDisciplina=idDisciplina, questoes=select_questoes)


@app.route('/estatisticaQuestao', methods=['GET'])
def estatisticaQuestao():
	idQuestao = request.args['idQuestao']
	nUtilizacao = Historico.query.filter_by(idQuestao=idQuestao).count()
	utilizacoes = Historico.query.join(Provas, Provas.idProva==Historico.idProva).filter(Historico.idQuestao==idQuestao).all()
	questao = Questoes.query.filter_by(idQuestao=idQuestao).first()

	return render_template('estatisticaQuestao.html', nUtilizacao=nUtilizacao, utilizacoes=utilizacoes, questao=questao)



@app.route('/provas', methods=['GET', 'POST'])
def provas():
	select_provas = Provas.query.join(Historico, Historico.idProva==Provas.idProva).filter_by(idUsuario=session['idUsuario']).all()

	return render_template('provas.html', title='Listar Provas', provas=select_provas)


@app.route('/gerarProvaSelDisciplina', methods=['GET'])
def gerarProvaSelDisciplina():
	select_disciplinas = Disciplinas.query.filter_by(idUsuario=session['idUsuario']).all()

	return render_template('gerarProvaSelDisciplina.html', title='Selecionar Disciplinas', disciplinas=select_disciplinas)


@app.route('/gerarProvaOpcoes', methods=['GET','POST'])
def gerarProvaOpcoes():
	msgErro=''
	msgSucesso=''
	form = FormGeraProva()
	idDisciplina = request.args['idDisciplina']
	select_assuntos = Assuntos.query.filter_by(idDisciplina=idDisciplina).all()
	assuntos = [(str(x.idAssunto), x.nmAssunto) for x in select_assuntos]
	form.idsAssuntos.choices = assuntos
	if form.validate_on_submit():
		#query inicial com join e filtro de disciplinas
		qyQuestoes = Questoes.query.join(Assuntos).join(Disciplinas).filter_by(idDisciplina=idDisciplina)

		#filtro tipo de questão
		qyQuestoes = qyQuestoes.filter(Questoes.nmTipo==form.tipoQuestao.data)

		#adiciona filtro de assuntos
		if form.idsAssuntos.data:
			qyQuestoes = qyQuestoes.filter(Questoes.idAssunto.in_(form.idsAssuntos.data))

		#adiciona filtro de somente questões novas
		if form.somenteNovas.data == "Sim":
			qyHistorico = Historico.query.options(defer('*')).add_column('idQuestao')
			qyQuestoes = qyQuestoes.filter(Questoes.idQuestao.notin_(qyHistorico))

		#adiciona filtro para excluir questoes utilizadas a partir de dtLimite
		if form.dtLimite.data:
			qyQuestoesExcluir = Historico.query.join(Provas).options(defer('*')).add_column('idQuestao').filter(Provas.dtRealizacao>=form.dtLimite.data)
			qyQuestoes = qyQuestoes.filter(Questoes.idQuestao.notin_(qyQuestoesExcluir))

		qyQuestoes = qyQuestoes.order_by('RANDOM()').limit(form.nQuestoes.data)

		numeroQuestoesEncontradas = qyQuestoes.count()
		if numeroQuestoesEncontradas == 0:
			msgErro = 'Nenhuma questão foi encontrada!'

		else:
			prova = Provas(dtRealizacao=datetime.datetime.now())
			db.session.add(prova)

			#faz o flush para que a prova seja inserida e o objeto prova tenha o idProva
			db.session.flush()

			selectQuestoes = qyQuestoes.all()

			for questao in selectQuestoes:
				inseriHistorico = Historico(idProva=prova.idProva, idQuestao=questao.idQuestao, idAssunto=questao.idAssunto,
				                            idDisciplina=questao.idDisciplina, idUsuario=session.get('idUsuario'))
				db.session.add(inseriHistorico)

			msgSucesso = 'A prova foi gerada com sucesso'
			if numeroQuestoesEncontradas<form.nQuestoes.data:
				msgSucesso = '{}<br>Porém somente {} questões foram encontradas'.format(msgSucesso,
				                                                                        (form.nQuestoes.data-numeroQuestoesEncontradas))
			db.session.commit()
	return render_template('gerarProvaOpcoes.html', title='Listar Assuntos', form=form, assuntos=select_assuntos, msgErro=msgErro, msgSucesso=msgSucesso)

@app.route('/visualizarProva', methods=['GET'])
def visualizarProva():
	idProva = request.args['idProva']

	select_questoes = Historico.query.join(Questoes, Questoes.idQuestao==Historico.idQuestao)\
		.join(Provas, Provas.idProva==Historico.idProva).join(Assuntos, Assuntos.idAssunto==Questoes.idAssunto)\
		.join(Disciplinas, Disciplinas.idDisciplina==Assuntos.idDisciplina)\
		.filter(Historico.idProva==idProva)
	select_questoes = select_questoes.add_columns(Questoes.textoQuestao, Questoes.respostaQuestao, Disciplinas.nmDisciplina)
	select_questoes = select_questoes.all()
	return render_template('visualizarProva.html', title='Listar Provas', questoes=select_questoes, nQuestoes=len(select_questoes))

"""
@app.route('/gerarProva', methods=['GET', 'POST'])
def gerarProva():

	idDisciplina = request.args['idDisciplina']
	idAssunto = request.args['idAssunto']

	select_provas = Provas.query.all()

	return render_template('provas.html', title='Listar Provas', provas=select_provas)
"""

@app.route('/registro', methods=['GET', 'POST'])
def cadastro():
	form = FormDeRegistro()
	if form.validate_on_submit():
		novoUsuario = Usuarios(nmUsuario = form.nome.data, senha = form.senha.data)
		db.session.add(novoUsuario)
		db.session.commit()
		return render_template('index.html', title="Usuário registrado")
	return render_template('registro.html', title='Cadastro de usuário', form=form)


@app.route('/login', methods=['GET', 'POST'])
def autenticar():

	form = LoginForm()
	if form.validate_on_submit():
		usuario = Usuarios.query.filter_by(nmUsuario=form.username.data).first_or_404()
		if (usuario.check_password(form.password.data)):
			session['logged_in'] = True
			session['nmUsuario'] = usuario.nmUsuario
			session['idUsuario'] = usuario.idUsuario
			return render_template('autenticado.html', title="Usuário autenticado", user=session.get('usuario'))
		else:
			flash('Usuário ou senha inválidos')
			return redirect(url_for('inicio'))
	return render_template('login.html', title='Autenticação de usuários', form=form)

@app.route('/painel')
def painel():
    '''
    Somente usuários autenticados poderão acessar essa URL
    :return: página com dados pessoais do usuário que está autenticado
    '''
    if session.get('logged_in'):
        # Veja mais em: http://flask-sqlalchemy.pocoo.org/2.3/queries/#querying-records
        usuario = Usuarios.query.filter_by(nmUsuario=session.get('usuario')).first_or_404()
        return render_template('dados.html', title="Usuário autenticado", user=usuario)
    return redirect(url_for('inicio'))

@app.route('/')
def inicio():
    '''
    Se não existir a chave 'logged_in' = True na sessão, então redireciona para página de login
    :return: página para fazer login ou página para usuários autenticados
    '''
    if not session.get('logged_in'):
        return redirect(url_for('autenticar'))
    else:
        return render_template('autenticado.html', title='Usuário autenticado', user=session.get('usuario'))


@app.errorhandler(404)
def page_not_found(e):
    '''
    Para tratar erros de páginas não encontradas - HTTP 404
    :param e:
    :return:
    '''
    return render_template('404.html'), 404

@app.route("/logout")
def logout():
    '''
    Para encerrar a sessão autenticada de um usuário
    :return: redireciona para a página inicial
    '''
    session['logged_in'] = False
    return redirect(url_for('inicio'))

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)