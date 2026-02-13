package ast.Linea;

import java.util.List;
import java.util.ArrayList;

import ast.Linea.Funcion.Argumento;

import ast.Expresion.*;
import ast.Tipos.*;
import ast.ASTNode;
import ast.Programa;
import ast.Linea.KindAsig;


public class Out extends Llamada{
	private String iden;
	private Expresion arg;
	private boolean asigned;

	public Out(String iden, Tipo tipo, Expresion arg){
		this.iden = iden;
		this.tipo = tipo;
		this.arg = arg;
		this.asigned = false;
	}

	@Override
	public String toString(){
		return 	"(CALL (" + iden + ", [" + arg.toString() + "]))";	
	}
	
	public KindAsig getKind() {
		return KindAsig.LLAMADA;
	}

	@Override
	public void binding(){
		ASTNode node = Programa.searchId(iden);
		if (node != null){
			this.link = node;
		}
		else{
			System.out.println("ERROR: funcion " + iden + " no declarada WRITES");
			Programa.setFin();
		}
		arg.binding(); 
	}

	public String getName(){
		return iden;
	}

	@Override
	public void checkType(){
		arg.checkType();
		if (!arg.getTipo().equals(tipo)){
			System.out.println("ERROR en tipado Out");
			Programa.setFin();
		}
	}

	public void generaCodigo(){
		arg.generaCodigo();
		if (tipo.equals("REAL")) Programa.codigo.println("\tcall $outReal");
		else Programa.codigo.println("\tcall $outEnt");
		
	}

	public void setAsigned(){
		this.asigned = true;
	}
	
}