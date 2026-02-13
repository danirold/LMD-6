package ast.Linea.Bucles;

import java.util.List;
import ast.Expresion.Expresion;
import ast.Linea.Instruccion;
import ast.Linea.Declaracion;
import ast.Linea.Asignacion;
import ast.Programa;
import ast.ASTNode;
import ast.Linea.Nuevo;

public class For extends Bucles{
	private Declaracion dec;
	private Expresion exp;
	private List<Instruccion> body;
	private Asignacion asig;
	
	public For(Declaracion dec, Expresion exp, List<Instruccion> body, Asignacion asig) {
		this.dec = dec;
		this.exp = exp;
		this.body = body;
		this.asig = asig;
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

		return "(FOR ((" + dec.toString() + ", TO " + exp.toString() + ", " + asig.toString() + "), (" + s + ")))";
	}
	
	public void binding(){
		Programa.abrirBloque();
		dec.binding();
		exp.binding();
		asig.binding();
		for(Instruccion ins : body){
			ins.binding();
		}
		Programa.cerrarBloque();
		
	}
	
	public void checkType(){
		dec.checkType();
		exp.checkType();
		asig.checkType();
		if(!(dec.getTipo().equals("ENT")) || !dec.getTipo().equals(asig.getTipo()) || !(exp.getTipo().equals("ENT"))){
			System.out.println("ERROR: mal tipado en FOR " + this);
			Programa.setFin();
		}
		else{
			for(Instruccion ins: body){
				ins.checkType();
			}
		}
	}


	@Override
	public void setPos(){

	}

	public void setPos(int delta) {
		int forDelta = delta;
		dec.setPos(forDelta);
		if (dec instanceof Nuevo){
			forDelta += dec.getTamanyo();
		}
		for(Instruccion ins : body){			
			ins.setPos(forDelta);
			forDelta += ins.getTamanyo();
		}

	}
	
	public void generaCodigo(){

    	dec.generaCodigo();

    	Programa.codigo.println("\tblock");
    	Programa.codigo.println("\t loop");

    	
    	dec.calcularDirRelativa();
    	Programa.codigo.println("\ti32.load");	
        exp.generaCodigo();                        
        Programa.codigo.println("\ti32.le_s");  
    
    	Programa.codigo.println(" i32.eqz");
    	Programa.codigo.println(" br_if 1");
    
    	for (Instruccion ins : body){
     	   ins.generaCodigo();
    	}

    	asig.generaCodigo();

    	Programa.codigo.println("\t br 0");
    	Programa.codigo.println("\t end");
    	Programa.codigo.println("\tend");

}

public int maxMemory(){
	int max = 0;
	int c = 0;
	max += dec.getTamanyo();
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