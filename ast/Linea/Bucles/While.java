package ast.Linea.Bucles;

import java.util.List;
import ast.Expresion.Expresion;
import ast.Linea.Instruccion;
import ast.Linea.Declaracion;
import ast.Linea.Nuevo;
import ast.Programa;
import ast.ASTNode;

public class While extends Bucles{
	private Expresion exp;
	private List<Instruccion> body;
	
	public While(Expresion exp, List<Instruccion> body) {
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

		return "(WHILE (" + exp.toString() + ", (" + s + ")))";
	}
	
	public void binding(){
		exp.binding();
		Programa.abrirBloque();
		for(Instruccion ins: body){
			ins.binding();
		}	
		Programa.cerrarBloque();
	}
	
	public void checkType(){
		exp.checkType();
		if(!exp.getTipo().equals("BOOL")){
			System.out.println("ERROR: mal tipado en WHILE " + this);
			Programa.setFin();
		}
		else{
			for(Instruccion ins: body){
				ins.checkType();
			}
		}
	}

	public void setPos(int delta) {
		int whileDelta = delta;
		for(Instruccion ins : body){
			ins.delta = whileDelta;
			ins.setPos(whileDelta);
			whileDelta += ins.getTamanyo();
		}

	}
	
	public void generaCodigo(){
	       
    	Programa.codigo.println("\tblock");
    	Programa.codigo.println("\t  loop");

    	exp.generaCodigo();
    
    	Programa.codigo.println("\t i32.eqz");
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