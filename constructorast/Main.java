/*package constructorast;

import java_cup.runtime.*;
import alex.UnidadLexica;
import alex.TokenValue;
import errors.GestionErroresTiny;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import alex.AnalizadorLexicoTiny;
import java_cup.runtime.*;
import alex.AnalizadorLexicoTiny;
import alex.UnidadLexica;
import alex.TokenValue;
import errors.GestionErroresTiny;


import ast.*;
import ast.Expresion.*;
import ast.Linea.*;
import ast.Linea.Declaracion.*;
import ast.Linea.Funcion.*;
import ast.Linea.Bucles.*;
import ast.Linea.Condicional.*;
import ast.Tipos.*;

import java.util.List;
import java.util.ArrayList;

public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(args[0]));
	 AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
	 ConstructorASTExp constructorast = new ConstructorASTExp(alex);
	 System.out.println(constructorast.parse().value);
 }
}*/

package constructorast;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import alex.AnalizadorLexicoTiny;
import alex.*;
import ast.ASTNode;
import ast.Programa;


public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(args[0]));
	 AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
	 ConstructorASTExp asint = new ConstructorASTExp(alex);
	 try{
			Programa p = (Programa) asint.parse().value;
			System.out.println("\n\n----------AST----------\n");		
			System.out.println(p);
			System.out.println("\n\n----------BINDING----------\n");
			p.binding();
			if (p.getFin() == 1){
				System.out.println("ERROR EN BINDING.");
				System.exit(1);
			}
			System.out.println("\n\n----------TIPADO----------\n");
			p.checkType();
			if (p.getFin() == 1){
				System.out.println("ERROR EN TIPADO.");
				System.exit(1);
			}
			System.out.println("\n\n----------GENERACION DE CODIGO----------\n");
			p.generaCodigo();
			if (p.getFin() == 1){
				System.out.println("ERROR EN GENERACION DE CODIGO");
				System.exit(1);
			}
    } catch(Exception e){
		System.out.println("Error while parsing..." + e.getMessage());
		e.printStackTrace(System.out);
	 }
	 
   }  
   
}
   
