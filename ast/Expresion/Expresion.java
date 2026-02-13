package ast.Expresion;

import ast.ASTNode;
import ast.NodeKind;
import ast.Linea.KindAsig;
import ast.Expresion.*;
import java.util.List;
import ast.Tipos.*;

public abstract class Expresion extends ASTNode {
	public boolean isModifiable = false;
	public KindAsig kind;

	public abstract String toString();

	public NodeKind nodeKind(){
		return NodeKind.EXPRESION;
	}
	
	public abstract void binding();
	
	public abstract void checkType();

	public void setSize(List<Ent> tamanyos){

	}
	
	public String getName(){
		return "";
	}

	public boolean checkModifiable(){
		return true;
	}

	public void generaCodigo(){

	}

	public void calcularDirRelativa(){

	}

	public KindAsig getKind(){
		return kind;
	}

	public boolean getModifiable(){
		return isModifiable;
	}	

}
