package ast.Expresion;

import ast.Linea.KindAsig;
import ast.Tipos.TipoArray;
import ast.Tipos.Tipo;
import ast.Expresion.*;
import ast.Linea.Struct;
import ast.Tipos.*;


import java.util.List;
import java.util.ArrayList;
import ast.Programa;

public class IniStruct extends Constante{
	private List<Expresion> exp;
	private KindAsig kind;
	private String name;
	
	public IniStruct(List<Expresion> exp, KindAsig kind){
		this.exp = exp;
		this.kind = kind;
	}

	public String toString(){
		return "(INISTRUCT {" + exp.toString() + "})";
	}
	
	public void binding(){
		for(Expresion e: exp){
			e.binding();
		}
	}
	
	public void checkType(){
		for(Expresion e: exp){
			e.checkType();
		}
	}
	
	public Tipo getTipoCampo(int ind){
		for(int i = 0; i < exp.size(); ++i){
			if (i == ind) {
				return exp.get(i).getTipo();
			}
			
		}
		return null;
	}


	
	public int getSize(){
		return exp.size();
	}
	
	public KindAsig getKind() {
		return kind;
	}
	
	public void generaCodigo(){
		
		for(int i = 0; i < exp.size(); i++){
			Programa.codigo.println("");
			Programa.codigo.println("\tcall $repeat");
			Programa.codigo.println("\ti32.const " + i*4);
			Programa.codigo.println("\ti32.add");
			exp.get(i).generaCodigo();
			Programa.codigo.println("\t" + exp.get(i).getTipo().convertWasm() + ".store");
			Programa.codigo.println("");
			
		}
		Programa.codigo.println("\tdrop");
		
	}

}
