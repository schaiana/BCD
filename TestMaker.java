/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testmaker;

import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author schaiana
 */
public class TestMaker {

    /**
     * @param args the command line arguments
     */
    static Scanner teclado;
    static Query MyDataBase;
    
    public static void main(String[] args) throws SQLException {
        ConnectionFactory.connect();
        teclado = new Scanner(System.in);
        MyDataBase = new Query();
        menuPrincipal();
    }

    
    public static void menuPrincipal()throws SQLException {
        
        while(true) {
            System.out.println("### Bem vindo ao TestMaker ###");
            System.out.println("O que deseja fazer?");
            System.out.println("1 - Consultas");
            System.out.println("2 - Cadastros");
            System.out.println("3 - Montar nova prova");            
            System.out.println("9 - Sair");
            System.out.println("#################################");


            
            System.out.println("Número:\n");
            String num = teclado.nextLine();

            switch (num) {
                case "1":                    
                    menu1();
                    break;
                case "2":
                    menu2();
                    break;
                case "3": {
                    menu3();
                    break;
                }    
                case "9":
                    System.exit(0);
            }



        }
        
    }
    public static void menu1() throws SQLException  {
    
        while(true) {    
            System.out.println("### Consultas ###");
            System.out.println("O que deseja consultar?");
            System.out.println("1 - Cursos");
            System.out.println("2 - Disciplinas");
            System.out.println("3 - Assuntos");
            System.out.println("4 - Questões");
            System.out.println("5 - Provas");
            System.out.println("8 - Retornar ao menu anterior");
            System.out.println("9 - Sair");
            System.out.println("#################################");

            System.out.println("Número:\n");
            String num = teclado.nextLine();

            switch(num){
                case "1":
                    MyDataBase.showCursos();
                    break;
                case "2":
                    MyDataBase.showDisciplinas();
                    break;
                case "3":
                    MyDataBase.showAssuntos();
                    break;
                case "4":
                    MyDataBase.showQuestao();
                    break;
                case "5":
                    MyDataBase.showProvas();
                    break;
                case "8":
                    return;                 
                case "9":
                    System.exit(0);
            }

        }

}
    public static void menu2() throws SQLException {    
        while(true) {    
            System.out.println("### Cadastros ###");
            System.out.println("O que deseja cadastrar?");
            System.out.println("1 - Cursos");
            System.out.println("2 - Disciplinas");
            System.out.println("3 - Assuntos");
            System.out.println("4 - Questões");
            System.out.println("8 - Retornar ao menu anterior");
            System.out.println("9 - Sair");
            System.out.println("#################################");

            System.out.println("Número:\n");
            String num = teclado.nextLine();

            switch(num){
                case "1":
                    MyDataBase.insertCursos();
                    //System.out.println("Curso cadastrado! Não esqueça de anotar o ID para uso futuro!");
                    break;
                case "2":
                    MyDataBase.insertDisciplinas();
                    //System.out.println("Disciplina cadastrada! Não esqueça de anotar o ID para uso futuro!");
                    break;
                case "3":
                    MyDataBase.insertAssuntos();
                    //System.out.println("Assunto cadastrado! Não esqueça de anotar o ID para uso futuro!");
                    break;
                case "4":
                    MyDataBase.insertQuestoes();
                    //System.out.println("Questão cadastrada! Não esqueça de anotar o ID para uso futuro!");
                    break;
                case "8":
                    return;
                case "9":
                    System.exit(0);
            }
        }

    
    }
    
    public static void menu3() throws SQLException {       
            System.out.println("### Nova Prova ###");
            System.out.println("Digite os parâmetros da sua nova prova: ");
            System.out.println("Número de questões desejado:");
            String n_questoes = teclado.nextLine();
            
            MyDataBase.showDisciplinas();
            System.out.println("ID da disciplina:");
            String id_disciplina = teclado.nextLine();
            
            MyDataBase.showAssuntosDisciplina(Integer.parseInt(id_disciplina));
            System.out.println("IDs dos Assuntos:");
            String ids_assuntos = teclado.nextLine();
            
            
            System.out.println("Tipo da questões:\n1 - Todas\n2 - Discursiva\n3 - Múltipla escolha");
            String t_questoes = teclado.nextLine();
            
            System.out.println("Somente questões nunca utilizadas? (S/N)");
            String nova_questao = teclado.nextLine();
            System.out.println("Não utilizar de N semestres anteriores:\nN:\n");
            String N = teclado.nextLine();          
            
            System.out.println("8 - Retornar ao menu anterior");
            System.out.println("9 - Sair");
            System.out.println("#################################");
            
            MyDataBase.insertProva(n_questoes, id_disciplina, ids_assuntos, t_questoes, nova_questao, N);


    
    }
}