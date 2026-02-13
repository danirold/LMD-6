package ast.Linea;
import ast.Programa;
import ast.ASTNode;
import ast.Tipos.*;

import ast.Expresion.Expresion;

public class Return extends Instruccion {
	private Expresion exp;
	
	public Return(Expresion exp){
		this.exp = exp;
	}

	public String toString(){
		return "(RETURN (" + exp.toString() + "))";
	}
	
	public void binding(){
		exp.binding();
		if (Programa.getSize() > 2){
			System.out.println("ERROR en RETURN " + this);
			Programa.setFin(); 
		}
	}
	
	public void checkType(){
		exp.checkType();
		Func fun = (Func) this.getLink();
		if(!(fun.getTipo().equals(exp.getTipo()))){
			System.out.println("ERROR en tipo RETURN " + this);
			Programa.setFin();
		}
	}
	
	public void generaCodigo(){
		Programa.codigo.println("\tglobal.get $SP");
		Programa.codigo.println("\ti32.const " + 4);
		Programa.codigo.println("\ti32.sub");
		if (exp.getTipo() instanceof TiposBasicos){
			exp.generaCodigo();
		}
		else {
			exp.calcularDirRelativa();
		}
        Programa.codigo.println("\t" + exp.getTipo().convertWasm() + ".store");
		

		Programa.codigo.println("\tglobal.get $SP");
		Programa.codigo.println("\ti32.const " + 4);
		Programa.codigo.println("\ti32.sub");
		Programa.codigo.println("\tcall $freeStack"); 
        Programa.codigo.println("\treturn");
	}
}