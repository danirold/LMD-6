package ast.Linea.Condicional;

import ast.Linea.Instruccion;
import ast.Expresion.Expresion;
import ast.Linea.Nuevo;

import java.util.List;
import ast.Programa;
import ast.ASTNode;

public class Condicional3 extends Instruccion{

	private List<Instruccion> body;
	
	public Condicional3(List<Instruccion> body){
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
		return "(ELSE (" +  s + "))";
		
	}
	public void binding(){
		Programa.abrirBloque();
		for(Instruccion ins: body){
			ins.binding();
		}
		Programa.cerrarBloque();	
	}
	
	@Override
	public void checkType(){
		for(Instruccion inst: body){
			inst.checkType();
		}
		
	}

	public void setPos(int delta) {
		int elseDelta = delta;
		for(Instruccion ins: body){
			ins.setPos(elseDelta);
			elseDelta += ins.getTamanyo();
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
		return max;
	}


	public boolean isBlock(){
		return true;
	}


	public void generaCodigo(){
		for(Instruccion ins : body){
			ins.generaCodigo();
		}
	}



}