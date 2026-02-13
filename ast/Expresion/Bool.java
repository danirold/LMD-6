package ast.Expresion;
import ast.Tipos.*;
import ast.Linea.KindAsig;
import ast.Programa;

public class Bool extends Constante {
	private boolean b;

	public Bool(String b){

			if (b.equals("V")) {
				this.b = true;
			}
			else {
				this.b = false;
			}
			this.kind = KindAsig.BOOL;
		
	}

	@Override
	public String toString(){
		if (b){
			return "V";
		}
		return "F";
	}
	
	public void binding(){
	}
	
	public Tipo getTipo(){
		return new TiposBasicos(KindTipo.BOOL);
	}
	
	@Override
	public void checkType(){
		setTipo(getTipo());
	}
	
	public int getValue(){
		if (b){
			return 1;
		}
		return 0;
	}
	
	public void generaCodigo(){
		Programa.codigo.println("\ti32.const " + getValue());
	}
}