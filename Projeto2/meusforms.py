from flask_wtf import FlaskForm
from wtforms import validators, StringField, TextAreaField, widgets, PasswordField, SelectMultipleField, SubmitField, RadioField, IntegerField, FormField, SelectField, FieldList, DateField, \
    DateTimeField
from wtforms.validators import DataRequired

'''
Veja mais na documentação do WTForms

https://wtforms.readthedocs.io/en/stable/
https://wtforms.readthedocs.io/en/stable/fields.html

Um outro pacote interessante para estudar:

https://wtforms-alchemy.readthedocs.io/en/latest/

'''


class LoginForm(FlaskForm):
    username = StringField('Nome de usuário', validators=[DataRequired("O preenchimento desse campo é obrigatório")])
    password = PasswordField('Senha', validators=[DataRequired("O preenchimento desse campo é obrigatório")])
    submit = SubmitField('Entrar')


class FormDeRegistro(FlaskForm):
    nome = StringField('Nome', validators=[DataRequired("")])
    senha = PasswordField('Senha', validators=[DataRequired("")])
    submit = SubmitField('Cadastrar')


class FormCadastroDisciplinas(FlaskForm):
    nome = StringField('Disciplina', validators=[DataRequired("")])
    submit = SubmitField('Inserir')


class FormCadastroAssuntos(FlaskForm):
    nome = StringField('Assunto', validators=[DataRequired("")])
    submit = SubmitField('Inserir')


class FormCadastroQuestoes(FlaskForm):
    tipo = SelectField('Tipo da Questão', choices=[('Múltipla Escolha', 'Múltipla Escolha'), ('Discursiva', 'Discursiva')], validators=[DataRequired("")])
    texto = TextAreaField('Texto da Questão', validators=[DataRequired("")])
    resp = TextAreaField('Resposta da Questão')
    submit = SubmitField('Inserir')


class MultiCheckboxField(SelectMultipleField):
    widget = widgets.ListWidget(prefix_label=False)
    option_widget = widgets.CheckboxInput()


class FormGeraProva(FlaskForm):
    nQuestoes = IntegerField('Número de questões na prova', validators=[DataRequired("")])
    tipoQuestao = SelectField('Tipo da Questão', choices=[('Múltipla Escolha', 'Múltipla Escolha'), ('Discursiva', 'Discursiva')], validators=[DataRequired("")])
    somenteNovas = RadioField('Somente questões nunca utilizadas anteriormente?',
                              choices=[('Sim', 'Somente questões nunca utilizadas antes'), ('Não', 'Pode utilizar questões já utilizadas anteriormente')])
    dtLimite = DateField('Não utilizar questões que já foram usadas a partir desta data', [validators.optional()], format='%d/%m/%Y', render_kw={"placeholder": "Ex.: 01/01/2018"})
    submit = SubmitField('Gerar')
    idsAssuntos = MultiCheckboxField('Assuntos a incluir')


