package ast.Expresion;

import java.util.Arrays;
import java.util.List;
import ast.Tipos.*;
import ast.Linea.KindAsig;
import ast.Programa;

public class Real extends Constante {
	private float n;
	
	public Real(String num, boolean signo){
		List<String> partes = Arrays.asList(num.split(","));
		String temp = partes.get(0) + "." + partes.get(1);
		if (signo) this.n = Float.parseFloat(temp);
		else this.n = -Float.parseFloat(temp);
		this.kind = KindAsig.REAL;
	}
	
	public String toString(){
		return "" + n;
	}
	
	public void binding(){
	}
	
	public Tipo getTipo(){
		return new TiposBasicos(KindTipo.REAL);
	}

	@Override
	public void checkType(){
		setTipo(getTipo());
	}
	
	public void generaCodigo(){
		Programa.codigo.println("\tf32.const " + n);
	}
	
}	

