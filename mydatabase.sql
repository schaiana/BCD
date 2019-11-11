CREATE DATABASE  IF NOT EXISTS `MyDataBase` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `MyDataBase`;
-- MySQL dump 10.13  Distrib 5.7.24
--
-- Host: 127.0.0.1    Database: MyDataBase
-- ------------------------------------------------------
-- Server version	5.7.24-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Assuntos`
--

DROP TABLE IF EXISTS `Assuntos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Assuntos` (
  `idAssunto` int(11) NOT NULL AUTO_INCREMENT,
  `nmAssunto` varchar(200) NOT NULL,
  `idDisciplina` int(11) NOT NULL,
  PRIMARY KEY (`idAssunto`,`idDisciplina`),
  KEY `fk_Assuntos_Disciplinas1_idx` (`idDisciplina`),
  CONSTRAINT `fk_Assuntos_Disciplinas1` FOREIGN KEY (`idDisciplina`) REFERENCES `Disciplinas` (`idDisciplina`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Assuntos`
--

LOCK TABLES `Assuntos` WRITE;
/*!40000 ALTER TABLE `Assuntos` DISABLE KEYS */;
INSERT INTO `Assuntos` VALUES (1,'Estudo do carbono',3),(2,'Estrutura de Dados',2),(3,'Isomeria',3),(8,'Introdução',1),(9,'Introdução',2);
/*!40000 ALTER TABLE `Assuntos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cursos`
--

DROP TABLE IF EXISTS `Cursos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cursos` (
  `idCurso` int(11) NOT NULL AUTO_INCREMENT,
  `nmCurso` varchar(50) NOT NULL,
  PRIMARY KEY (`idCurso`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cursos`
--

LOCK TABLES `Cursos` WRITE;
/*!40000 ALTER TABLE `Cursos` DISABLE KEYS */;
INSERT INTO `Cursos` VALUES (1,'Engenharia de Telecomunicações'),(2,'Química'),(3,'Engenharia Mecnica');
/*!40000 ALTER TABLE `Cursos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cursos_Disciplinas`
--

DROP TABLE IF EXISTS `Cursos_Disciplinas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cursos_Disciplinas` (
  `idCurso` int(11) NOT NULL,
  `idDisciplina` int(11) NOT NULL,
  PRIMARY KEY (`idCurso`,`idDisciplina`),
  KEY `fk_Cursos_has_Disciplinas_Disciplinas1_idx` (`idDisciplina`),
  KEY `fk_Cursos_has_Disciplinas_Cursos_idx` (`idCurso`),
  CONSTRAINT `fk_Cursos_has_Disciplinas_Cursos` FOREIGN KEY (`idCurso`) REFERENCES `Cursos` (`idCurso`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cursos_has_Disciplinas_Disciplinas1` FOREIGN KEY (`idDisciplina`) REFERENCES `Disciplinas` (`idDisciplina`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cursos_Disciplinas`
--

LOCK TABLES `Cursos_Disciplinas` WRITE;
/*!40000 ALTER TABLE `Cursos_Disciplinas` DISABLE KEYS */;
INSERT INTO `Cursos_Disciplinas` VALUES (1,1),(1,2),(2,3),(1,4);
/*!40000 ALTER TABLE `Cursos_Disciplinas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Disciplinas`
--

DROP TABLE IF EXISTS `Disciplinas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Disciplinas` (
  `idDisciplina` int(11) NOT NULL AUTO_INCREMENT,
  `nmDisciplina` varchar(100) NOT NULL,
  PRIMARY KEY (`idDisciplina`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Disciplinas`
--

LOCK TABLES `Disciplinas` WRITE;
/*!40000 ALTER TABLE `Disciplinas` DISABLE KEYS */;
INSERT INTO `Disciplinas` VALUES (1,'Programação 1'),(2,'Programação 2'),(3,'Química Orgânica'),(4,'Disciplina Teste');
/*!40000 ALTER TABLE `Disciplinas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Historico`
--

DROP TABLE IF EXISTS `Historico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Historico` (
  `idQuestao` int(11) NOT NULL,
  `idAssunto` int(11) NOT NULL,
  `idDisciplina` int(11) NOT NULL,
  `idProva` int(11) NOT NULL,
  PRIMARY KEY (`idQuestao`,`idAssunto`,`idDisciplina`,`idProva`),
  KEY `fk_Questoes_has_Provas_Provas1_idx` (`idProva`),
  KEY `fk_Questoes_has_Provas_Questoes1_idx` (`idQuestao`,`idAssunto`,`idDisciplina`),
  CONSTRAINT `fk_Questoes_has_Provas_Provas1` FOREIGN KEY (`idProva`) REFERENCES `Provas` (`idProva`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Questoes_has_Provas_Questoes1` FOREIGN KEY (`idQuestao`, `idAssunto`, `idDisciplina`) REFERENCES `Questoes` (`idQuestao`, `idAssunto`, `idDisciplina`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Historico`
--

LOCK TABLES `Historico` WRITE;
/*!40000 ALTER TABLE `Historico` DISABLE KEYS */;
INSERT INTO `Historico` VALUES (4,8,1,2),(5,8,1,2),(6,8,1,2),(7,8,1,2),(8,8,1,2);
/*!40000 ALTER TABLE `Historico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Provas`
--

DROP TABLE IF EXISTS `Provas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Provas` (
  `idProva` int(11) NOT NULL AUTO_INCREMENT,
  `dtRealizacao` date DEFAULT NULL,
  PRIMARY KEY (`idProva`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Provas`
--

LOCK TABLES `Provas` WRITE;
/*!40000 ALTER TABLE `Provas` DISABLE KEYS */;
INSERT INTO `Provas` VALUES (2,'2018-11-26');
/*!40000 ALTER TABLE `Provas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Questoes`
--

DROP TABLE IF EXISTS `Questoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Questoes` (
  `idQuestao` int(11) NOT NULL AUTO_INCREMENT,
  `nmTipo` varchar(50) NOT NULL,
  `textoQuestao` varchar(8000) NOT NULL,
  `respostaQuestao` varchar(8000) DEFAULT NULL,
  `idAssunto` int(11) NOT NULL,
  `idDisciplina` int(11) NOT NULL,
  PRIMARY KEY (`idQuestao`,`idAssunto`,`idDisciplina`),
  KEY `fk_Questoes_Assuntos1_idx` (`idAssunto`,`idDisciplina`),
  CONSTRAINT `fk_Questoes_Assuntos1` FOREIGN KEY (`idAssunto`, `idDisciplina`) REFERENCES `Assuntos` (`idAssunto`, `idDisciplina`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Questoes`
--

LOCK TABLES `Questoes` WRITE;
/*!40000 ALTER TABLE `Questoes` DISABLE KEYS */;
INSERT INTO `Questoes` VALUES (1,'Discursiva','A zidovudina ou AZT (azidotimidina) é um fármaco utilizado para inibir a infecção e os efeitos citopáticos do vírus da imunodeficiência humana do tipo HIV-I, o agente causador da AIDS. Quantos carbonos saturados e insaturados, respectivamente, apresenta uma molécula do AZT?','E',1,3),(4,'Discursiva','Escreva uma função que peça dois números inteiros ao usuário e exibe o valor soma seguido pelo maior deles. ',NULL,8,1),(5,'Discursiva','Faça um programa que converta Celsius para Fahrenheit.',NULL,8,1),(6,'Discursiva','Escreva um programa que começa pedindo um número N ao usuário e depois pede N números. O programa deverá mostrar na tela todos esses números ordenados do menor para o maior. Escreva sua própria função de ordenação. ',NULL,8,1),(7,'Múltipla Escolha','Considerando a linguagem de programação \"C\", para uma variável armazenar \"números de ponto flutuante\", deve ser declarada como: a) Real. b) Int. c) Numeric. d) Point. e) Float. ',NULL,8,1),(8,'Múltipla Escolha','São palavras-chave da linguagem C no padrão ANSI e, portanto, não podem ser utilizadas como nomes para variáveis: a) typedef, master, core, newline.  b) union, extern, main, core.   c) int, long, static, void. d) Float, Long, Core, Continue. e) signed, unsigned, master, main. ',NULL,8,1),(10,'Discursiva','Crie um programa capaz de ler os dados de uma matriz quadrada de inteiros. Ao final da leitura o programa deverá imprimir o número da linha que contém o menor dentre todos os números lidos.',NULL,9,2),(11,'Discursiva','Crie um progama capaz de ler dois nomes de pessoas e imprimi-los em ordem alfabética. Faça isto com string de C e de C++',NULL,9,2),(12,'Discursiva','Crie um programa  capaz de multiplicar uma linha de uma matriz de inteiros por um dado número. Faça o mesmo para uma coluna. A matriz deve ser lida de teclado.',NULL,9,2),(13,'Discursiva','Crie um programa capaz de criar a transposta de uma matriz. A matriz deve ser lida de teclado.',NULL,9,2),(14,'Discursiva','Faça um programa que crie um vetor de pessoas. Os dados de uma pessoa devem ser armazenados em um variavel do tipo struct. O programa deve permitir que o usuário digite o nome de 3 pessoas e a seguir imprimi os dados de todas as pessoas. A struct deve armazenar os dados de idade, peso e altura.',NULL,9,2),(15,'Discursiva','Crie uma função capaz de criar a transposta de uma matriz.',NULL,9,2),(16,'Discursiva','Crie uma função capaz de substituir todos os números negativos de uma matriz por seu módulo.',NULL,9,2),(17,'Discursiva','Crie uma função capaz de multiplicar uma linha de uma matriz por um dado número. Faça o mesmo para uma coluna.',NULL,9,2);
/*!40000 ALTER TABLE `Questoes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-26  0:31:23
