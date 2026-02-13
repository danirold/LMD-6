package ast.Expresion;

import ast.Linea.KindAsig;
import java.util.List;
import ast.Tipos.Tipo;
import ast.Programa;
import ast.Tipos.*;

public class Dinamica extends Expresion {
	private Tipo tipo;
	private Constante exp;

	public Dinamica(Tipo tipo, Constante exp) {
		this.tipo = tipo;
		this.exp = exp;
		this.kind = KindAsig.DINAMICA;
	}

	@Override
	public String toString() {
		return "(NEW " + tipo.toString() + "(" + exp.toString() + "))";	
	}
	
	public void binding(){
		if(exp != null){
			exp.binding();
		}
	}
	
	public void setSize(List<Ent> tamanyos){
		int cont = 1;
		for(Ent ent : tamanyos){
			cont *= ent.getInt();
		}
		if(exp instanceof IniArray){
			exp.checkType();

			IniArray aux = (IniArray) exp;
			if (aux.getSize() != cont){
				System.out.println("ERROR: fallo en tipado ExpreLlave" + this);
				Programa.setFin();
			}
			else{
				TipoArray tipoArray = new TipoArray(aux.getTipo().getTipoBasico(), tamanyos);
				exp.setTipo(new TipoPuntero(tipoArray));
			}
		}
		
	}
	
	public void checkType(){

		TipoPuntero puntero = new TipoPuntero(tipo);
		setTipo(puntero);
		if(exp != null){
			exp.checkType();	
			if(exp.getTipo() instanceof TipoArray){	
				exp.setSize(tipo.getTamanyos());
			}
			else{
				if(!this.tipo.getTipoBasico().equals(exp.getTipo())){
					System.out.println("ERROR: fallo en DINAMICA" + this);
					Programa.setFin();
				}
			}
		}
		
	}
	
	public void generaCodigo(){
		Programa.codigo.println("\ti32.const " + tipo.getTam());
		Programa.codigo.println("\tcall $reserveHeap");
		Programa.codigo.println("\tglobal.get $NP");
		if(exp != null){
			if(exp.getKind().equals(KindAsig.LLAVE)){
				exp.generaCodigo();
				Programa.codigo.println("\tglobal.get $NP");
			}
			else if(exp.getKind().equals(KindAsig.CORCH)){
				exp.generaCodigo();


				Programa.codigo.println("\tglobal.get $NP");

			}
			else{	

				exp.generaCodigo();
				Programa.codigo.println("\t" + exp.getTipo().convertWasm() + ".store");
				Programa.codigo.println("global.get $NP");
			}
		}
	}

}
