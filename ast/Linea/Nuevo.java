package ast.Linea;

import ast.Linea.Declaracion;
import ast.Tipos.Tipo;
import ast.Expresion.*;
import ast.Programa;
import ast.ASTNode;
import ast.Tipos.*;
import ast.Tipos.TipoArray;

public class Nuevo extends Declaracion{
	private String iden;
	private Expresion exp;
	private boolean global = false;	
	private int deltaSt = -1;
	private int constante;

	public Nuevo(){
		
	}

	public Nuevo(Tipo tipo, String iden, int constante){
		this.tipo = tipo;
		this.iden = iden;
		this.constante = constante;
	}

	public Nuevo(Tipo tipo, String iden, int constante, Expresion exp){
		this.tipo = tipo;
		this.iden = iden;
		this.exp = exp;	
		this.constante = constante;
	}


	public String toString(){
		if (exp == null){
			return "(" + tipo.toString() + " " + iden.toString() + ")";
		}
			return  "(ASIG ((" + tipo.toString() + " " + iden.toString() + "), " + exp.toString() + "))";
		
	}
	
	public void binding(){
		tipo.binding();
		ASTNode node = Programa.searchIdLastFun(iden);
		if (node == null){
			if(Programa.getSize() == 1){
				global = true;
			}
			
			Programa.insertar(iden, this);
		}
		else{
			System.out.println("ERROR: identificador en NUEVO" + iden + " no se puede utilizar en " + this);
			Programa.setFin();
		}
		if (exp != null){
			exp.binding();
		}
	}
	
	public void checkType(){
		this.tipo = tipo.reduceAlias();
		if (exp != null){
			exp.checkType();
			Tipo tipoExp = exp.getTipo();
			if(tipo instanceof TipoArray){		
				TipoArray aux = (TipoArray) tipo;
				IniArray ini = (IniArray) exp;
				if (aux.getNumElems() != ini.getSize() && ini.getSize() > 1) {
					System.out.println("ERROR: la inicialización del array no es correcta " + this);
					Programa.setFin();
				}
				exp.setSize(aux.getTamanyos());
				tipoExp = exp.getTipo();

			}
			else if (tipo instanceof TipoIden && (exp.getKind().equals(KindAsig.LLAVE))) {
				TipoIden aux1 = (TipoIden) tipo;
				IniStruct aux2 = (IniStruct) exp;
				exp.setTipo(new TipoIden(aux1.getName()));
				tipoExp = exp.getTipo();
				Struct st = (Struct) aux1.getLink();
				
				if (aux2.getSize() != st.getTam()) {
					System.out.println("ERROR: la inicialización del struct no es correcta " + this);
				}
				else {
					for (int i = 0; i < aux2.getSize(); ++i) {
						if (!st.getTipoCampo(i).equals(aux2.getTipoCampo(i))) {
							if (!(st.getTipoCampo(i).equals("REAL") && aux2.getTipoCampo(i).equals("ENT"))) {
								System.out.println("ERROR: la inicialización del struct no es correcta " + this);
								Programa.setFin();
							}
						}
					}
				}
		
			}
			if (tipo == null || tipoExp == null || (tipo.equals("REAL") && !(tipoExp.equals("ENT") || tipoExp.equals("REAL"))) || (tipo.equals("BOOL") && !tipoExp.equals("BOOL")) || (tipo.equals("ENT") && !tipoExp.equals("ENT"))) {
				System.out.println(tipo + " " + tipoExp);
				System.out.println("ERROR: mal tipado en NUEVO " + this);
				Programa.setFin();
			}
		}
	}
		

	public String getName(){
		return iden;
	}

	public Tipo getTipo(){
		return tipo;
	}
	
	public void setPos(){
		this.setDelta();
	}
	


	public boolean getGlobal(){
		return global;
	}

	public void setDeltaSt(int delta){
		this.deltaSt = delta;
	}
	
	public void generaCodigo(){
		
		if(exp != null){
		
			if(!(exp.getModifiable())){
				
				if (exp.getKind().equals(KindAsig.CORCH)){
					calcularDirRelativa();
					exp.generaCodigo();
				}	
				else if (exp.getKind().equals(KindAsig.LLAMADA)){
					
					if (exp.getTipo() instanceof TiposBasicos){
						calcularDirRelativa();
						exp.generaCodigo();
						Programa.codigo.println("\t" + exp.getTipo().convertWasm() + ".store");
					}
					else{
						exp.generaCodigo();
						calcularDirRelativa();
						Programa.codigo.println("\ti32.const "  + (tipo.getTam()/4));
						if (tipo.equals("REAL")) Programa.codigo.println("\tcall $copynr");
						else Programa.codigo.println("\tcall $copyn");
					}

				}
				else{
					
					calcularDirRelativa();
					exp.generaCodigo();

					if (!exp.getKind().equals(KindAsig.LLAVE)) Programa.codigo.println("\t" + exp.getTipo().convertWasm() + ".store");

				}

			}
			else {
				
				if (exp.getKind().equals(KindAsig.LLAMADA)){
	
					
					if (exp.getTipo() instanceof TiposBasicos){
		
						calcularDirRelativa();
						exp.generaCodigo();
						Programa.codigo.println("\t" + exp.getTipo().convertWasm() + ".store");
					}
					else{
						exp.generaCodigo();
						calcularDirRelativa();
						Programa.codigo.println("\ti32.const "  + (tipo.getTam()/4));
						if (tipo.equals("REAL")) Programa.codigo.println("\tcall $copynr");
						else Programa.codigo.println("\tcall $copyn");
					}

				}
				else if (exp.getKind().equals(KindAsig.ARROBA) || exp.getKind().equals(KindAsig.INTERROGACION)) {
					calcularDirRelativa();	
					exp.generaCodigo();
					Programa.codigo.println("\t" + exp.getTipo().convertWasm() + ".store");
				}
				else{
			
					exp.calcularDirRelativa();
					calcularDirRelativa();	
					Programa.codigo.println("\ti32.const "  + (tipo.getTam()/4));
					if (tipo.equals("REAL")) Programa.codigo.println("\tcall $copynr");
					else Programa.codigo.println("\tcall $copyn");
				}	
			}
			
		}
		else if (tipo instanceof TipoIden){ 
			TipoIden tipoN = (TipoIden) this.getTipo();
			Struct st = (Struct) tipoN.getLink();
			st.calcularDirRelativa();
			calcularDirRelativa();
			Programa.codigo.println("\ti32.const " + getTamanyo()/4);
			Programa.codigo.println("\tcall $copyn");
		}
		else if (tipo instanceof TipoArray && tipo.getTipoBasico() instanceof TipoIden){ 
			TipoIden tipoN = (TipoIden) this.getTipo().getTipoBasico();
			Struct st = (Struct) tipoN.getLink();
			for(int i = 0; i < this.getTipo().getNumElems(); i++){
				st.calcularDirRelativa();
				calcularDirRelativa();
				Programa.codigo.println("\ti32.const " + getTamanyo()*i);
				Programa.codigo.println("\ti32.add");
				Programa.codigo.println("\ti32.const " + getTamanyo()/4);
				Programa.codigo.println("\tcall $copyn");
			}
		}

	}

	public void calcularDirRelativa(){
		if (deltaSt != -1){
			if (global){
				Programa.codigo.println("\ti32.const " + (deltaSt + delta + 4));
			}
			else{
				Programa.codigo.println("\ti32.const " + (deltaSt + delta));
				Programa.codigo.println("\tlocal.get $localsStart");
        		Programa.codigo.println("\ti32.add");
			}
		}
		else if (global){
			Programa.codigo.println("\ti32.const " + (delta + 4));
		}
		else{
			Programa.codigo.println("\ti32.const " + delta);
        	Programa.codigo.println("\tlocal.get $localsStart");
        	Programa.codigo.println("\ti32.add");
		}
	}
	
	public int isConstant() {
		return constante;
	}
	
	
	

}