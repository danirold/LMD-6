package ast.Tipos;

import ast.Expresion.Expresion;
import java.util.List;
import java.util.ArrayList;
import ast.Expresion.*;
import ast.Linea.Asignacion;

public class TipoArray extends Tipo{
	
	private Tipo tipoBasico;
	private List<Ent> tams;

	public TipoArray (Tipo tipoBasico, List<Ent> tams){
		this.tipoBasico = tipoBasico;
		this.tams = tams;
	}

	@Override
	public String toString(){
		return tipoBasico.toString() + " ARRAY " + tams.toString();
	}
	
	public void binding(){
		tipoBasico.binding();
	}
	
	public List<Ent> getTamanyos(){
		return tams;
	}
	
	public Tipo getTipoBasico(){
		return tipoBasico;
	}
	
	public int getNumElems(){
		int elems = 1;
		for(Ent e : tams){
			elems *= e.getInt();
		}
		return elems;
	}
	
	public int getTam(){
		return tipoBasico.getTam() * getNumElems();
	}

	public Tipo reduceAlias(){
		this.tipoBasico = tipoBasico.reduceAlias();
		return this;
	}

}