package ast.Tipos;

import ast.ASTNode;
import ast.NodeKind;
import ast.Expresion.*;
import java.util.List;


public abstract class Tipo extends ASTNode{

	public abstract String toString();
	

	public NodeKind nodeKind(){
		return NodeKind.TIPO;
	}
	
	public void binding(){

	}
	
	public String getName(){
		return "";
	} 
	
	public void checkType(){
	}
	
	public boolean equals(Object o){
		if (this == null || o == null){
			return false;
		}
		if (o.toString().equals(this.toString())){
			return true;
		}
		return false;
	}
	
	public ASTNode getLink(){
		return this.link;
	}
	
	public abstract int getTam();
	
	public int getNumElems(){
		return 0;
	}
	public Tipo getTipoBasico(){
		return this;
	}

	public List<Ent> getTamanyos(){
		return null;
	}

	public Tipo reduceAlias(){
		return this;
	}
	
	public String convertWasm(){
		if (this.equals(new TiposBasicos(KindTipo.REAL))){
			return "f32";
		}
		return "i32";
	}


}