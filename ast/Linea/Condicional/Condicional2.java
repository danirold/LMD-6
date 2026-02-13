package ast.Linea.Condicional;

import ast.Linea.Instruccion;
import ast.Linea.Nuevo;
import ast.Programa;
import ast.ASTNode;
import ast.Expresion.Expresion;
import java.util.List;

public class Condicional2 extends Instruccion{

	private Expresion exp;
	private List<Instruccion> body;

	public Condicional2(Expresion exp, List<Instruccion> body){
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
		return "(ELSIF " + exp.toString() + " THEN (" + s + "))";
	}
	
	public void binding(){
		exp.binding();
		Programa.abrirBloque();
		for(Instruccion inst: body){
			inst.binding();
		}
		Programa.cerrarBloque();	
	}
	
	public void checkType(){
		exp.checkType();
		if (!exp.getTipo().equals("BOOL")){
			System.out.println("ERROR: fallo en tipo Else If" + this);
			Programa.setFin();
		}
		else{
			for(Instruccion inst: body){
				inst.checkType();
			}
		}
	}

	public void setPos(int delta) {
		int elseifDelta = delta;
		for(Instruccion ins: body){
			ins.setPos(elseifDelta);
			elseifDelta += ins.getTamanyo();
		}
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
				if(c + max1 > max){
					max = c + max1;
				}
			}
		}
		return max;
	}

	public boolean isBlock(){
		return true;
	}

	public void generaCodigo(){
		exp.generaCodigo();
		Programa.codigo.println("\tif");
		for(Instruccion ins : body){
			ins.generaCodigo();
		}
	}

}