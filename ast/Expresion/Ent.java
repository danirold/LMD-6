package ast.Expresion;
import ast.Tipos.*;
import ast.Linea.KindAsig;
import ast.Programa;

public class Ent extends Constante {
	private int n;

	
	public Ent(String num, boolean signo){
		if (signo) this.n = Integer.parseInt(num);
		else this.n = -Integer.parseInt(num);
		this.kind = KindAsig.ENT;
	}
	
	public String toString(){
		return "" + n;
	}
	
	public void binding(){
	}
	
	public Tipo getTipo(){
		return new TiposBasicos(KindTipo.ENT);
	}

	@Override
	public void checkType(){
		setTipo(getTipo());
	}

	public int getInt(){
		return this.n;
	}
	
	public void generaCodigo(){
		Programa.codigo.println("\ti32.const " + n);
	}

}	
