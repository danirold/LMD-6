package ast.Expresion;

import ast.Linea.KindAsig;
import ast.Tipos.TipoArray;
import ast.Tipos.Tipo;
import ast.Expresion.*;


import java.util.List;
import java.util.ArrayList;
import ast.Programa;

public class IniArray extends Constante{
	private List<Expresion> exp;
	private KindAsig kind;
	
	public IniArray(List<Expresion> exp, KindAsig kind){
		this.exp = exp;
		this.kind = kind;
	}

	public String toString(){
		return "(INIARRAY [" + exp.toString() + "])";
	}
	
	public void binding(){
		for(Expresion e: exp){
			e.binding();
		}
	}
	
	@Override
	public void checkType(){
		Expresion e0 = exp.get(0);
		e0.checkType();
		Tipo tipo = e0.getTipo();
		for(Expresion e: exp){
			e.checkType();
			if(!tipo.equals(e.getTipo())){
				System.out.println("ERROR: fallo en IniArray" + this);
				Programa.setFin();
				setTipo(null);
				break;
			}
		}
		List<Ent> tams = new ArrayList<Ent>();
		setTipo(new TipoArray(tipo, tams));

	}

	public void setSize(List<Ent> tamanyos){
		TipoArray tipoArray = new TipoArray(exp.get(0).getTipo(), tamanyos);
		setTipo(tipoArray);
	}
	
	public int getSize() {
		return exp.size();
	}
	
	public KindAsig getKind() {
		return kind;
	}
	
	public void generaCodigo(){
		if (exp.size() == 1) {
			for(int i = 0; i < this.getTipo().getNumElems(); i++){
				Programa.codigo.println("");
				Programa.codigo.println("\tcall $repeat");
				Programa.codigo.println("\ti32.const " + (this.getTipo().getNumElems() - 1 -i)*4);
				Programa.codigo.println("\ti32.add");
				exp.get(0).generaCodigo();
				if(exp.get(0).getModifiable()){
					Programa.codigo.println("\t" + exp.get(0).getTipo().convertWasm() + ".load");

				}
				Programa.codigo.println("\t" + exp.get(0).getTipo().convertWasm() + ".store");
				Programa.codigo.println("");
				
			}
			Programa.codigo.println("\tdrop");	
		}
		else {
			for(int i = 0; i < this.getTipo().getNumElems(); i++){
				Programa.codigo.println("");
				Programa.codigo.println("\tcall $repeat");
				Programa.codigo.println("\ti32.const " + (this.getTipo().getNumElems() - 1 -i)*4);
				Programa.codigo.println("\ti32.add");
				exp.get(i).generaCodigo();
				Programa.codigo.println("\t" + exp.get(i).getTipo().convertWasm() + ".store");
				Programa.codigo.println("");
				
			}
			Programa.codigo.println("\tdrop");
		}

		
		
	}


}
