package ast.Tipos;
import ast.Programa;
import ast.ASTNode;
import ast.Linea.Alias;
import ast.Tipos.*;
import ast.Linea.Struct;

public class TipoIden extends Tipo{
	private String tipo;

	public TipoIden (String tipo) {
		this.tipo = tipo;
	}

	public String toString(){
		return tipo;
	}
	
	public void binding(){
		ASTNode node = Programa.searchId(tipo);
		if(node == null){
			System.out.println("ERROR en TipoNombre " + this);
			Programa.setFin();
		}
		else{
			this.link = node;
		}
	}
	
	@Override
	public void checkType(){
		if(!(this.link instanceof Struct)){
			System.out.println("ERROR en TipoNombre " + this);
			Programa.setFin();
		}
		
	}

	public int getTam(){
		Struct st = (Struct) this.link;
		int sol = st.getTamanyo();
		return st.getTamanyo();
	}
	
	public Tipo reduceAlias(){
		if(this.link instanceof Alias){
			return link.getTipo();
		}
		return this;
	}
	
	public String getName() {
		return tipo;
	}

}