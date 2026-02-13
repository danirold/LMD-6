package ast.Linea.Bucles;

import ast.Linea.Instruccion;
import ast.Programa;
import ast.ASTNode;

public abstract class Bucles extends Instruccion {

	public abstract String toString();
	
	public boolean isBlock(){
		return true;
	}
	
	public void checkType(){
	}


}	