/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testmaker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author schaiana
 */
public class Query {
    
    public static void showCursos() throws SQLException{
        
        try {
            String sql = "SELECT * FROM Cursos";
            Statement stmt = ConnectionFactory.connection.createStatement(); 

            ResultSet result_set = stmt.executeQuery(sql);

            System.out.println("######### Cursos Cadastrados #########");
            
            while(result_set.next()){
                System.out.println(String.format("|%-5d|%-25s|",
                        result_set.getInt("idCurso"),
                        result_set.getString("nmCurso")));
            }
            System.out.println("#################################\n");
            result_set.close();
            stmt.close();
        }  catch (SQLException exception) {
                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, exception);
            }
    
    }
    
    public static void showDisciplinas() throws SQLException{
        try {
            String sql = "SELECT Disciplinas.idDisciplina, Disciplinas.nmDisciplina, Cursos.nmCurso FROM Disciplinas "+
                    "INNER JOIN Cursos_Disciplinas on Cursos_Disciplinas.idDisciplina = Disciplinas.idDisciplina "+
                    "INNER JOIN Cursos on Cursos.idCurso = Cursos_Disciplinas.idCurso";
            Statement stmt = ConnectionFactory.connection.createStatement(); 

            ResultSet result_set = stmt.executeQuery(sql);

            System.out.println("#### Disciplinas Cadastradas ####");
            while(result_set.next()){
                System.out.println(String.format("|%-5d|%-25s|%-25s|",
                        result_set.getInt("idDisciplina"),
                        result_set.getString("nmDisciplina"),
                        result_set.getString("nmCurso")));

            }
            System.out.println("#################################\n");
            result_set.close();
            stmt.close();
        }  catch (SQLException exception) {
                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, exception);
            }
    
    }
    
    public static void showAssuntos() throws SQLException{    
        try {
            String sql = "SELECT * FROM Assuntos INNER JOIN Disciplinas on Disciplinas.idDisciplina = Assuntos.idDisciplina";
            Statement stmt = ConnectionFactory.connection.createStatement();

            ResultSet result_set = stmt.executeQuery(sql);

            System.out.println("####### Assuntos Cadastrados ########");
            
            while(result_set.next()){
                System.out.println(String.format("|%-5d|%-25s|%-25s|",
                        result_set.getInt("idAssunto"),
                        result_set.getString("nmAssunto"),
                        result_set.getString("nmDisciplina")));

            }
            System.out.println("#################################\n");

            result_set.close();
            stmt.close();
        }  catch (SQLException exception) {
                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, exception);
            }
    
    }
    
    public static void showAssuntosDisciplina(Integer idDisciplina) throws SQLException{    
        try {
            String sql = "SELECT * FROM Assuntos INNER JOIN Disciplinas on Disciplinas.idDisciplina = Assuntos.idDisciplina "+
                    " WHERE Disciplinas.idDisciplina = "+idDisciplina;
            Statement stmt = ConnectionFactory.connection.createStatement();

            ResultSet result_set = stmt.executeQuery(sql);

            System.out.println("######### Assuntos ##########");
            
            while(result_set.next()){
                System.out.println(String.format("|%-5d|%-25s|%-25s|",
                        result_set.getInt("idAssunto"),
                        result_set.getString("nmAssunto"),
                        result_set.getString("nmDisciplina")));

            }
            System.out.println("#################################\n");

            result_set.close();
            stmt.close();
        }  catch (SQLException exception) {
                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, exception);
            }
    
    }
    
    public static void showQuestao() throws SQLException{
        
        try {
            Scanner teclado = new Scanner(System.in);
            System.out.println("Qual o ID da questão que deseja buscar?");
            String idQuestao = teclado.nextLine();

            String sql = "SELECT * FROM Questoes "+
                    " INNER JOIN Assuntos on Assuntos.idAssunto = Questoes.idAssunto "+
                    " INNER JOIN Disciplinas on Disciplinas.idDisciplina = Assuntos.idDisciplina "+
                    " WHERE idQuestao ="+idQuestao;
            Statement stmt = ConnectionFactory.connection.createStatement(); 

            ResultSet result_set = stmt.executeQuery(sql);

            if(result_set.next()){
                System.out.println(String.format("|%-5d|%-10s|%-25s|%-25s|%-25s|%-25s|",
                        result_set.getInt("idQuestao"),
                        result_set.getString("nmTipo"),
                        result_set.getString("textoQuestao"),                        
                        result_set.getString("respostaQuestao"),
                        result_set.getString("nmAssunto"),
                        result_set.getString("nmDisciplina")));

            } else {
                System.out.println("\nQuestão não encontrada\n");
                return;
            }
            
            String sql2 = "SELECT Provas.idProva, Provas.dtRealizacao FROM Provas "+
                    " WHERE idProva in (SELECT idProva FROM Historico WHERE idQuestao="+idQuestao+") ";
            Statement stmt2 = ConnectionFactory.connection.createStatement(); 
            ResultSet result_set2 = stmt2.executeQuery(sql2);
            
            System.out.println("\nProvas em que essa questão apareceu:");
            Integer quantidadeProvas = 0;
            while(result_set2.next()){
                quantidadeProvas = quantidadeProvas +1;
                System.out.println(String.format("|%-5d|%-10s|",
                        result_set.getInt("idProva"),
                        result_set.getString("dtRealizacao")));
            }
            System.out.println("Essa questão foi utilizada "+quantidadeProvas.toString()+" vezes");
            System.out.println("#################################\n");
            result_set.close();
            stmt.close();
        }  catch (SQLException exception) {
                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, exception);

            }
    
    }
    public static void showProvas() throws SQLException{
            
        try {
            Scanner teclado = new Scanner(System.in);
            System.out.println("Qual o ID da prova que deseja buscar?");
            String idProva = teclado.nextLine();

            String sql = "SELECT * FROM Provas WHERE idProva ="+idProva;
            Statement stmt = ConnectionFactory.connection.createStatement(); 

            ResultSet result_set = stmt.executeQuery(sql);

            if(result_set.next()){
                System.out.println(String.format("|%-5d|%-25s|",
                        result_set.getInt("idProva"),
                        result_set.getDate("dtRealizacao")));
            } else {
                System.out.println("\nProva não encontrada.\n");
                return;
            }

            result_set.close();
            stmt.close();
            
            System.out.println("Questões contidas nesta prova: \n");
                  
            String sql2 = "SELECT * FROM Questoes  "+
                    " INNER JOIN Historico on Historico.idQuestao = Questoes.idQuestao "+
                    "WHERE Historico.idProva = "+idProva;
            
            Statement stmt2 = ConnectionFactory.connection.createStatement(); 

            ResultSet result_set2 = stmt2.executeQuery(sql2);

            while(result_set2.next()){
                System.out.println(String.format("ID da Questão: %s\nEnunciado: %s\nResposta: %s\n",
                        result_set2.getString("idQuestao"),
                        result_set2.getString("textoQuestao"),
                        result_set2.getString("respostaQuestao")));
            }
            
            System.out.println("\n");

            result_set2.close();
            stmt2.close();
            
        }  catch (SQLException exception) {
                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
    
   
    
     public static void insertQuestoes() throws SQLException{         
        try {
     
           Scanner teclado = new Scanner(System.in);

            System.out.println("Qual é o enunciado da questão?");
            String textoQuestao = teclado.nextLine();
            System.out.println("Qual é a resposta da questão?");
            String textoResposta = teclado.nextLine();
            String nmTipo;
            do {
                System.out.println("Esta questão é de:\n1 - Múltipla Escolha\n2 - Discursiva");            
                String tipo = teclado.nextLine();
                
                if (tipo.equals("1")) {
                    nmTipo = "Múltipla Escolha";
                    break;
                } else if (tipo.equals("2")){
                    nmTipo = "Discursiva";
                    break;
                 } else {
                    System.out.println("Opção inválida! Digite 1 ou 2.");
                } 
            } while(true);
            
            showDisciplinas();
            System.out.println("Qual é o ID da disciplina da questão?");
            String idDisciplina = teclado.nextLine();
            
            showAssuntosDisciplina(Integer.parseInt(idDisciplina));
            System.out.println("Qual é o ID do assunto da questão?");
            String idAssunto = teclado.nextLine();
            
            String sql;
                   
            if(textoResposta.isEmpty()){
                sql = "INSERT INTO Questoes (nmTipo, textoQuestao, respostaQuestao, idAssunto, idDisciplina)" 
                         + "VALUES ('"+nmTipo+"','"+textoQuestao+"' ,'',"+idAssunto+","+idDisciplina+")";
                //System.out.println("SQL:" + sql);
            } else {
                sql = "INSERT INTO Questoes (nmTipo, textoQuestao, respostaQuestao, idAssunto, idDisciplina)" 
                         + "VALUES ('"+nmTipo+"','" +textoQuestao+"' ,'"+textoResposta+"',"+idAssunto+","+idDisciplina+")";
                //System.out.println("SQL:" + sql);
            }
            Statement stmt = ConnectionFactory.connection.createStatement();
            stmt.executeUpdate(sql);      
            stmt.close(); 
                                  
            
        }  catch (SQLException exception) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, exception);
        }
     }    
    
       
    public static void insertAssuntos() throws SQLException{        
        try {
     
           Scanner teclado = new Scanner(System.in);

            System.out.println("Qual é nome do assunto?");
            String nmAssunto = teclado.nextLine();
            System.out.println("Qual é o ID da disciplina que o assunto pertence?");
            String idDisciplina = teclado.nextLine();
            String sql;
          
            sql = "INSERT INTO Assuntos (nmAssunto, idDisciplina)" 
                         + "VALUES ('"+nmAssunto+"'," +idDisciplina+")";
                //System.out.println("SQL:" + sql);
            
            Statement stmt = ConnectionFactory.connection.createStatement(); 

            stmt.executeUpdate(sql);       
            stmt.close();                                  
            System.out.println("Cadastro realizado.");
        }  catch (SQLException exception) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, exception);
        }     
        
    }
    
 
    public static void insertCursos() throws SQLException{        
        try {
     
            Scanner teclado = new Scanner(System.in);
            System.out.println("Qual o nome do curso?");
            String nmCurso = teclado.nextLine();
            String sql;
          
            sql = "INSERT INTO Cursos (nmCurso)" 
                         + "VALUES ('"+nmCurso+"')";
                //System.out.println("SQL:" + sql);
            Statement stmt = ConnectionFactory.connection.createStatement(); 
            stmt.executeUpdate(sql);        
            stmt.close();     
            
            System.out.println("Cadastro realizado.");
            
        }  catch (SQLException exception) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, exception);
        }     
        
    }
  
    public static void insertDisciplinas() throws SQLException{        
        try {
     
             Scanner teclado = new Scanner(System.in);
            Statement stmt = ConnectionFactory.connection.createStatement(); 

            showCursos();
            System.out.println("Informe o ID do curso");
            System.out.println("8 - Voltar");
            String idCurso = teclado.nextLine();
            
            if (idCurso.equals("8")){
                return;
            }
            
            System.out.println("Qual o nome da disciplina?");
            String nmDisciplina = teclado.nextLine();
            
            String sql;       
          
            sql = "INSERT INTO Disciplinas (nmDisciplina)" 
                         + "VALUES ('"+nmDisciplina+"')";
                //System.out.println("SQL:" + sql);
            
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            
            String idDisciplina="";
            ResultSet rsIdProva = stmt.getGeneratedKeys();
            if (rsIdProva.next()){
                idDisciplina=rsIdProva.getString(1);
            }
            
            String sql2 = "INSERT INTO Cursos_Disciplinas (idCurso, idDisciplina) VALUES ("+idCurso+","+idDisciplina+")";
            stmt.executeUpdate(sql2);
            
            stmt.close();                                  
            System.out.println("Cadastro realizado.");
        }  catch (SQLException exception) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, exception);
        }     
        
    }
    

    
    public static void insertProva(String n_questoes, String id_disciplina, String ids_assuntos, String t_questoes, String nova_questao, String N){
        try {

            String whereAssuntos = "";
            if (!ids_assuntos.isEmpty())
                whereAssuntos = " and Questoes.idAssunto in ("+ids_assuntos+") ";
            
            String whereTipoQuestao = "";
            if (t_questoes.equals("2")) {
                whereTipoQuestao = " and Questoes.nmTipo='Discursiva' ";
            } else if (t_questoes.equals("3")){
                whereTipoQuestao = " and Questoes.nmTipo='Múltipla Escolha' ";
            }

            String whereQuestoesUnicas = "";
            if (nova_questao.equals("S")){
                whereQuestoesUnicas = " and Questoes.idQuestao not in (SELECT Historico.idQuestao FROM Historico) ";
            }
            
            String whereSemestres = "";
            if (!N.isEmpty()){
                Integer semestres = Integer.parseInt(N);
                LocalDate dataInicialExlcluir = LocalDate.now();

                if (dataInicialExlcluir.getMonthValue()<6){
                    dataInicialExlcluir= dataInicialExlcluir.minusMonths(dataInicialExlcluir.getMonthValue()-1);
                } else {
                    dataInicialExlcluir = dataInicialExlcluir.minusMonths(dataInicialExlcluir.getMonthValue()-6);
                }

                dataInicialExlcluir = dataInicialExlcluir.minusDays(dataInicialExlcluir.getDayOfMonth()-1);
                dataInicialExlcluir = dataInicialExlcluir.minusMonths((6*(semestres))-1);
          
                whereSemestres = " and Questoes.idQuestao not in ("+
                        "SELECT Historico.idQuestao from Historico " +
                        " INNER JOIN Provas on Provas.idProva = Historico.idProva" + 
                        " where Provas.dtRealizacao>='"+dataInicialExlcluir.toString()+"'"+
                        " ) ";
            }
            
            String consultaQuestoes = "SELECT Questoes.idQuestao, Questoes.idAssunto, Questoes.idDisciplina "+
                    "FROM Questoes "+
                    "INNER JOIN Assuntos on Assuntos.idAssunto = Questoes.idAssunto "+
                    "INNER JOIN Disciplinas on Disciplinas.idDisciplina = Assuntos.idDisciplina "+
                    "WHERE "+
                    "Questoes.idDisciplina = "+id_disciplina+
                    whereAssuntos+
                    whereTipoQuestao+
                    whereQuestoesUnicas+
                    whereSemestres+
                    " order by RAND() limit "+n_questoes;
            //System.out.println(consultaQuestoes);
            Statement s = ConnectionFactory.connection.createStatement(); 
            //System.out.println(consultaQuestoes);    
            ResultSet rs = s.executeQuery(consultaQuestoes);
            
            //se não encontrou nehnuma prova com os critérios informados
            if(!rs.next()){
                System.out.println("\nNenhuma questão foi encontrada com estes critérios");
                rs.close();
                s.close();
                return;
            }
            
            rs.beforeFirst();
            
            String data = java.time.LocalDate.now().toString();
            String sqlInsereProva = "INSERT INTO Provas (dtRealizacao) VALUES ('"+data+"')";
            
            Statement sInsereProva = ConnectionFactory.connection.createStatement(); 
            sInsereProva.executeUpdate(sqlInsereProva, Statement.RETURN_GENERATED_KEYS);
            
            String idProva="";
            ResultSet rsIdProva = sInsereProva.getGeneratedKeys();
            if (rsIdProva.next()){
                idProva=rsIdProva.getString(1);
            }
            rsIdProva.close();
            sInsereProva.close();
            
            Statement sInsereProvaQuestao = ConnectionFactory.connection.createStatement();
            Integer nQuestoesEncontradas = 0;
            while(rs.next()){
                String sqlInsereProvaProvaQuestao = "INSERT INTO Historico (idProva, idQuestao,"+
                        "idAssunto, idDisciplina) VALUES "+
                        "("+idProva+","+rs.getString("idQuestao")+","+rs.getString("idAssunto")+","+rs.getString("idDisciplina")+")";
                sInsereProvaQuestao.executeUpdate(sqlInsereProvaProvaQuestao);
                nQuestoesEncontradas = nQuestoesEncontradas+1;
            }
            
            Integer nQuestoesPedidas = Integer.parseInt(N);
            
            System.out.println("\nProva gerada! Anote o ID da prova: " +idProva);
            
            if (!Objects.equals(nQuestoesPedidas, nQuestoesEncontradas)){
                System.out.println(String.format("\nSomente %d questões foram inseridas na prova", nQuestoesEncontradas));
            }
            
            sInsereProvaQuestao.close();
            rs.close();
            s.close();
        }  catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
