package ast.Linea;

import ast.ASTNode;
import ast.NodeKind;

public abstract class Instruccion extends ASTNode{

	public abstract String toString();
	
	public NodeKind nodeKind(){
		return NodeKind.INSTRUCCION;
	}
	public abstract void binding();
	public abstract void checkType();
	public void setPos() {
		
	}
	
	
	public String getName(){
		return "";
	}


	public int maxMemory(){
		return 0;
	}
	
	public void generaCodigo(){
		
	}
	
	



}