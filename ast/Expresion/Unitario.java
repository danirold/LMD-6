package ast.Expresion;

import ast.Linea.Llamada;

import ast.Programa;
import ast.ASTNode;
import ast.Linea.KindAsig;
import ast.Tipos.*;
import ast.Linea.Funcion.Argumento;
import ast.Linea.Nuevo;

public class Unitario extends Constante{
	private String iden;
	
	public Unitario(){
		this.isModifiable = true;
		this.kind = KindAsig.UNITARIO;
	}

	public Unitario(String iden){
		this.iden = iden;
		this.isModifiable = true;
		this.kind = KindAsig.UNITARIO;
	}

	public String toString(){
		return iden;
	}
	
	public void binding(){
		ASTNode node = Programa.searchId(iden);
		if (node instanceof Nuevo) {
			Nuevo n = (Nuevo) node;
			if (n.isConstant() == 1) this.isModifiable = false;
		}
		else if (node instanceof Argumento) {
			Argumento n = (Argumento) node;
			if (n.getRef() == 0) this.isModifiable = false;	
		}
		
		if (node != null){
			this.link = node;
		}
		else{	
			
			System.out.println("ERROR: identificador en UNITARIO " + iden + " no se puede utilizar en " + this);
			Programa.setFin();
		}
	}
	
	@Override
	public void checkType(){
		if (this.link != null){
			setTipo(this.link.getTipo());
		}
	}

	@Override
	public String getName(){
		return iden;
	}
	
	public Tipo getTipo(){
		return this.link.getTipo();
	}
	
	public void generaCodigo() {
		calcularDirRelativa();
		Programa.codigo.println("\t" + this.getTipo().convertWasm() + ".load");	
	}

	public void calcularDirRelativa(){

		int dir = this.link.getDelta();
		if (this.link.getGlobal()){	
			dir += 4;
			Programa.codigo.println("\ti32.const " + dir);
		}
		else{
			if (this.link instanceof Argumento){

				Argumento arg = (Argumento) this.link;
				if (arg.getRef() == 1){
					Programa.codigo.println("\ti32.const " + dir);
					Programa.codigo.println("\tlocal.get $localsStart");
					Programa.codigo.println("\ti32.add");
					Programa.codigo.println("\ti32.load");

				}
				else{
					Programa.codigo.println("\ti32.const " + dir);
					Programa.codigo.println("\tlocal.get $localsStart");
        			Programa.codigo.println("\ti32.add");
				}

			}
			else{
				Programa.codigo.println("\ti32.const " + dir);
				Programa.codigo.println("\tlocal.get $localsStart");
        		Programa.codigo.println("\ti32.add");
			}
		}
	}
}


