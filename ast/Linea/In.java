package ast.Linea;

import java.util.List;
import java.util.ArrayList;

import ast.Linea.Funcion.Argumento;

import ast.Expresion.*;
import ast.Tipos.*;
import ast.ASTNode;
import ast.Programa;
import ast.Linea.KindAsig;


public class In extends Llamada{
	private String iden;
	private boolean asigned;

	public In(String iden, Tipo tipo){
		this.iden = iden;
		this.tipo = tipo;
		this.asigned = false;
	}

	@Override
	public String toString(){
		return 	"(CALL (" + iden + ", " + "[]))";	
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
			System.out.println("ERROR: funcion " + iden + " no declarada READS");
			Programa.setFin();
		}

	}

	public String getName(){
		return iden;
	}

	@Override
	public void checkType(){
	}

	public void generaCodigo(){
		if (tipo.equals("REAL")) Programa.codigo.println("\tcall $inReal");
		else Programa.codigo.println("\tcall $inEnt");
		
	}

	public void setAsigned(){
		this.asigned = true;
	}
	
}