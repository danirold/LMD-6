package ast.Expresion;

import ast.Linea.Llamada;
import ast.Linea.Funcion.Argumento;

import ast.Linea.KindAsig;

import ast.Programa;
import ast.ASTNode;



import ast.Tipos.Tipo;

public class ELlamada extends Expresion{
	private String iden;
	private Llamada call;
	private KindAsig kind;
	

	public ELlamada(Llamada call){
		this.iden = call.getName();
		this.call = call;
		this.isModifiable = true;
		this.kind = KindAsig.LLAMADA;
	}


	public String toString(){
		return call.toString();
		
	}


	public void binding(){
			call.binding();
			this.link = call.getLink();
		
	}
	
	public KindAsig getKind() {
		return kind;
	}


	public String getName(){
		return iden;
	}
	
	@Override
	public void checkType(){
		if (this.link != null){
	
			setTipo(this.link.getTipo());
		}
		call.checkType();
	}
	
	public void generaCodigo() {

		call.setAsigned();
		call.generaCodigo();
		
	}

	public void calcularDirRelativa(){
		int dir = this.link.getDelta();
		dir += 4;
		Programa.codigo.println("\ti32.const " + dir);
			
	}
	
	

}
