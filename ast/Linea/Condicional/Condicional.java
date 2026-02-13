package ast.Linea.Condicional;

import java.util.List;
import ast.Expresion.Expresion;
import ast.Linea.Instruccion;
import ast.Linea.Nuevo;
import ast.Programa;
import ast.ASTNode;

public class Condicional extends Instruccion{

	private Expresion exp;
	private List<Instruccion> body;
	private List<Condicional2> cond2;
	private Condicional3 cond3;

	public Condicional(Expresion exp, List<Instruccion> body, List<Condicional2> cond2, Condicional3 cond3){
		this.exp = exp;
		this.body = body;
		this.cond2 = cond2;
		this.cond3 = cond3;

	}
	public String toString(){
		String s = "";
		for(int i = 0; i < body.size(); i++){
			if (i == body.size() - 1){
				s += body.get(i).toString();
			}
			else {
				s += body.get(i).toString() + ", ";
			}
		}
		String s2 = "";
		for(int i = 0; i < cond2.size(); i++){
			if (i == (cond2.size() - 1)){
				s2 += cond2.get(i).toString();
			}
			else {
				s2 += cond2.get(i).toString() + " ";
			}
		}
		if(cond3 == null && cond2.size() == 0){
			return "(IF " + exp.toString() + " THEN (" + s + "))"; 
		}
		else if(cond3 == null && cond2.size() > 0){
			return "((IF " + exp.toString() + " THEN (" + s + ")) " + s2 + ")"; 
		}
		else if (cond2.size() == 0){
			return "((IF " + exp.toString() + " THEN (" + s + ")) " + cond3.toString() + ")"; 
		}
		
		else { 
			return "((IF " + exp.toString() + " THEN (" + s + ")) " + s2 + " " + cond3.toString() + ")";
		}
	}
	
	public void binding(){
		exp.binding();
		Programa.abrirBloque();
		for(Instruccion inst: body){
			inst.binding();
		}
		Programa.cerrarBloque();
		for(Condicional2 c: cond2){
			c.binding();
		}
		if(cond3 != null){
			cond3.binding();
		}	
		
	}
	
	@Override
	public void checkType(){
		exp.checkType();
		if (!exp.getTipo().equals("BOOL")){
			System.out.println("ERROR: fallo en tipo If" + this);
			Programa.setFin();
		}
		else {
			for(Instruccion inst: body){
				inst.checkType();
			}
			for(Condicional2 c: cond2){
				c.checkType();
			}
			if(cond3 != null){
				cond3.checkType();
			}
		}
	}

	public void setPos(int delta) {
		int ifDelta = delta;
		for(Instruccion ins: body){
			ins.setPos(ifDelta);
			ifDelta += ins.getTamanyo();
		}

		for(Condicional2 c: cond2){
			c.setPos(delta);
		}
		if(cond3 != null){
			cond3.setPos(delta);
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
				if(c+max1 > max){
					max = c + max1;
				}
			}
		}

		for(Condicional2 cond: cond2){
			int maxAux = cond.maxMemory();
			if(maxAux > max){
				max = maxAux;
			}
		}
		if (cond3 != null){
		int maxAux2 = cond3.maxMemory();
		if(maxAux2 > max){
			max = maxAux2;
		}
		}
		return max;
	} 
	
public void recorrer(List<Condicional2> cond2, int i){
		
		cond2.get(i).generaCodigo();
		Programa.codigo.println("\telse");
		if (i != cond2.size() - 1){
			recorrer(cond2, i+1);
			Programa.codigo.println("\tend");		
		}
		else if (cond3 == null){
			Programa.codigo.println("\tend");
		}
		else{
			if (cond2.size() == 0){
				Programa.codigo.println("\telse");		
			}
			cond3.generaCodigo();
			if (cond2.size() != 0){
				Programa.codigo.println("\tend");		
			}
		}		
	}

	public void generaCodigo(){
		exp.generaCodigo();
		Programa.codigo.println("\tif");
		for(Instruccion ins : body){
			ins.generaCodigo();
		}
		Programa.codigo.println("\telse");
		if (cond2.size() != 0){
			recorrer(cond2, 0);
		}
		else if (cond3 != null){
			cond3.generaCodigo();
		}	
		Programa.codigo.println("\tend");		

	}
	
	public boolean isBlock(){
		return true;
	}

	


}
