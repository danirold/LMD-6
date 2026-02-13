package ast.Tipos;

import ast.Tipos.KindTipo;

public class TiposBasicos extends Tipo {
	private KindTipo tipo;

	public TiposBasicos(KindTipo tipo){
		this.tipo = tipo;
	}	

	public String toString(){
		return tipo.toString();
	}
	
	public int getTam(){
		int tam = 0;
		switch(tipo){
			case ENT:
			case REAL:
			case BOOL:
				tam = 4;
				break;
		}
		return tam;
	}
}