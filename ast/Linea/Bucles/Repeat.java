package ast.Linea.Bucles;

import java.util.List;
import ast.Programa;
import ast.ASTNode;

import ast.Expresion.Expresion;
import ast.Linea.Instruccion;
import ast.Linea.Declaracion;
import ast.Linea.Nuevo;

public class Repeat extends Bucles{
	private Expresion exp;
	private List<Instruccion> body;
	
	public Repeat(Expresion exp, List<Instruccion> body) {
		this.exp = exp;
		this.body = body;
	}

	public String toString(){
		String s = "";
		for(int i = 0; i < body.size(); i++){
			if (i == (body.size() - 1)){
				s += body.get(i).toString();
			}
			else {
				s += body.get(i).toString() + ", ";
			}
		}

		return "(REPEAT_UNTIL (" + exp.toString() + ", (" + s + ")))";
	}
	
	public void binding(){
		Programa.abrirBloque();
		for(Instruccion ins: body){
			ins.binding();
		}	
		Programa.cerrarBloque();
		exp.binding();
	}
	
	public void checkType(){
		exp.checkType();
		if(!exp.getTipo().equals("BOOL")){
			System.out.println("ERROR: mal tipado en REPEAT " + this);
			Programa.setFin();
		}
		else{
			for(Instruccion ins: body){
				ins.checkType();
			}
		}
	}
	
	public void generaCodigo(){
		
		for (Instruccion ins : body){
    		ins.generaCodigo();
		}
	       
    	Programa.codigo.println("\tblock");
    	Programa.codigo.println("\t  loop");

    	exp.generaCodigo();
    	
    	Programa.codigo.println("\t i32.const 1");
    	Programa.codigo.println("\t i32.eq");

    	Programa.codigo.println("\t br_if 1");
    	
    	for (Instruccion ins : body){
    		ins.generaCodigo();
    	}

   		Programa.codigo.println("\t br 0");
    	Programa.codigo.println("\t end");
    	Programa.codigo.println("\tend");
	}

	public int maxMemory(){
		int max = 0;
		int c = 0;
		for(Instruccion ins : body){
			if(ins instanceof Nuevo){
				c += ins.getTamanyo();
				max += ins.getTamanyo();
			}
		else if(ins.isBlock()){
			int max1 = ins.maxMemory();
			if(c+max1 > max){
				max = c + max1;
			}
		}
	}
	return max;
} 
	

}