package ast.Linea.Funcion;

import ast.Tipos.Tipo;
import ast.ASTNode;
import ast.Programa;
import ast.NodeKind;

public class Argumento extends ASTNode{
	private Tipo type;
	private String name;
	private int ref;//1 por referencia, 0 no por referencia

	public Argumento(Tipo type, String name, int ref){
		this.type = type;
		this.name = name;
		this.ref = ref;
	}

	public String toString(){
		if (ref == 0){
			return "(" + type.toString() + " " + name + ")";
		}
		else{
			return "(" + type.toString() + " REF " + name + ")";
		}
		
	}
	
	public void binding(){
		ASTNode node = Programa.searchIdLastFun(name);
		if (node == null){
			type.binding();
			
			Programa.insertar(name, this);
			
		}
		else{
			System.out.println("ERROR: identificador ARG" + name + " no se puede utilizar en " + this);
			Programa.setFin();
		}
	}
	
	public NodeKind nodeKind(){
		return NodeKind.ARGUMENTO;
	}

	
	public String getName(){
		return "";
	} 
	
	public void checkType(){
		this.type = type.reduceAlias();
	}
	
	public int getRef(){
		return this.ref;
	}

	public Tipo getTipo(){
		return this.type;
	}

	public int getTam(){
		if (ref == 0){
			return type.getTam();
		}
		return 4;
	}
	
	public void setDelta(int new_delta) {
		delta = new_delta;
	}
	
	

}