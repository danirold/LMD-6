package ast.Expresion;

import ast.Linea.KindAsig;
import ast.Tipos.Tipo;
import ast.Tipos.TipoArray;
import ast.Programa;
import ast.ASTNode;
import ast.Linea.Struct;
import java.util.List;

public class AccesoStruct extends Expresion{
	private Expresion exp1;
	private Expresion exp2;
	private KindAsig kind;


	public AccesoStruct(Expresion exp1, Expresion exp2, KindAsig kind){
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.kind = kind;
		this.isModifiable = true;
	}

	@Override
	public String toString(){
		return "(ACCESO (" + exp1.toString() + ", " + exp2.toString() + "))"; 
	}

	@Override
	public void binding(){
		exp1.binding();
	}
	
	public void checkType(){
		exp1.checkType();

		Tipo t = exp1.getTipo();
				
		if (t == null){
			System.out.println("ERROR: fallo en struct EBIN" + this);
			Programa.setFin();
		}
		else{
			Struct node = (Struct) t.getLink();
			Tipo tipoCampo = node.getTipoCampo(exp2.getName());
			if (tipoCampo == null){
				System.out.println("ERROR: fallo en tipo EBIN" + this);
				Programa.setFin();
			}	
			else{
				setTipo(tipoCampo);
			}
		}

	}
	
	public KindAsig getKind() {
	    return this.kind;
	}
	
	public void generaCodigo(){
		calcularDirRelativa();
		Tipo t = exp1.getTipo();
		Struct node = (Struct) t.getLink();
		Tipo tipoCampo = node.getTipoCampo(exp2.getName());

		Programa.codigo.println("\t" + tipoCampo.convertWasm() + ".load");
	}

	public void calcularDirRelativa(){
		exp1.calcularDirRelativa();
		Struct node = (Struct) exp1.getTipo().getLink();
		int deltaCampo = node.getDeltaCampo(exp2.getName());
		Programa.codigo.println("\ti32.const " + deltaCampo);
		Programa.codigo.println("\ti32.add");

	}

	
}
