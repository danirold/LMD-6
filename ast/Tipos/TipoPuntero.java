package ast.Tipos;

import ast.Expresion.Expresion;

public class TipoPuntero extends Tipo{
	
	private Tipo tipoBasico;

	public TipoPuntero(Tipo tipo){
		this.tipoBasico = tipo;
	}	

	@Override
	public String toString(){
		return tipoBasico.toString() + " POINTER";
	}
	
	public void binding(){
		tipoBasico.binding();
	}
	
	public Tipo getTipoBasico(){
		return tipoBasico;
	}
	
	public int getTam(){
		return 4;
	}

	public Tipo reduceAlias(){
		this.tipoBasico = tipoBasico.reduceAlias();
		return this;
	}

}