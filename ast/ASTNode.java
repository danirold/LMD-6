package ast;

import ast.Tipos.*;
import ast.Programa;

public abstract class ASTNode {
	protected Tipo tipo;
	public ASTNode link;
	public int delta;

	public abstract String toString();
	public abstract NodeKind nodeKind();
	
	public abstract void binding();


	public Tipo getTipo(){
		return tipo;
	}

	public void setTipo(Tipo tipo){
		this.tipo = tipo;
	}	

	public abstract void checkType();
	public abstract String getName();
	public ASTNode getLink(){
		return this.link;
	}

	public void setLink(ASTNode node){
		this.link = node;
	}

	public void setDelta(){
        delta = Programa.pila.getDelta();
	    int tam = tipo.getTam();
     	Programa.pila.updateDelta(tam);
    }

    public int getDelta() {
        return delta;
    }
    

	public void setPos(){
		
	}
	
	public void setPos(int deltaFun) {
		delta = deltaFun;
	}

	public int getTamanyo(){
		if (tipo != null){
			return tipo.getTam();
		}
		return 0;
	}

	public void getMemory(){

	}

	public int maxMemory(){
		return 0;
	} 
	
	public boolean isBlock(){
		return false;
	}

	public boolean getGlobal(){
		return false;
	}

	public void calcularDirRelativa(){
		
	}



}
