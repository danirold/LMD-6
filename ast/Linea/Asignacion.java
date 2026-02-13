package ast.Linea;

import ast.Expresion.Expresion;
import ast.Programa;
import ast.ASTNode;
import ast.Linea.KindAsig;
import ast.Tipos.*;
import ast.Expresion.*;
import ast.Linea.Struct;

public class Asignacion extends Declaracion{
	private Expresion exp1;
	private Expresion exp2;
	private KindAsig tipoAsig; 

	public Asignacion(Expresion exp1, Expresion exp2, KindAsig tipoAsig){
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.tipoAsig = tipoAsig;
	}

	public String toString(){
		return "(" + tipoAsig.toString() + " (" + exp1.toString() + ", " + exp2.toString() + "))";
	}
	
	public void binding(){
		exp1.binding();
		exp2.binding();
	}
	
	public void checkType(){
		exp1.checkType();
		if(!(exp1.getModifiable()) || (exp1.getKind().equals(KindAsig.LLAMADA))){
			System.out.println("ERROR: expresion no modificable en ASIGNACION " + this);
			Programa.setFin();
			
		}
		else {
			exp2.checkType();
			Tipo tipo1 = exp1.getTipo();
			Tipo tipo2 = exp2.getTipo();
			if(tipo1 instanceof TipoArray && (exp2.getKind().equals(KindAsig.CORCH))){				
				TipoArray aux = (TipoArray) tipo1;
				IniArray ini = (IniArray) exp2;
				if (aux.getNumElems() != ini.getSize() && ini.getSize() > 1) {
					System.out.println("ERROR: la inicialización del array no es correcta " + this);
					Programa.setFin();
				}
				exp2.setSize(aux.getTamanyos());
				tipo2 = exp2.getTipo();
			}
			else if (tipo1 instanceof TipoIden && (exp2.getKind().equals(KindAsig.LLAVE))) {
				TipoIden aux1 = (TipoIden) tipo1;
				IniStruct aux2 = (IniStruct) exp2;
				exp2.setTipo(new TipoIden(aux1.getName()));
				Struct st = (Struct) aux1.getLink();
				
				if (aux2.getSize() != st.getTam()) {
					System.out.println("ERROR: la inicialización del struct no es correcta " + this);
				}
				else {
					for (int i = 0; i < aux2.getSize(); ++i) {
						if (!st.getTipoCampo(i).equals(aux2.getTipoCampo(i))) {
							System.out.println("ERROR: la inicialización del struct no es correcta " + this);
							Programa.setFin();
						}
					}
				}
				tipo2 = exp2.getTipo();
				
			}
			
			if (tipo1 == null || tipo2 == null){
				System.out.println("ERROR: mal tipado en ASIGNACION" + this);
				Programa.setFin();
			}
			else{
				switch(tipoAsig){				
					case SUMAPREF:
					case RESTAPREF:
					case MULTPREF:
						if (!(tipo1.equals("ENT") || tipo1.equals("REAL")) || !(tipo2.equals("ENT") || tipo2.equals("REAL"))){
							System.out.println("ERROR: fallo en tipo ASIGNACION" + this);
							Programa.setFin();
						}
						else{
							setTipo(tipo1);
						}
						break;
					case DIVENTPREF:
						if (!(tipo1.equals("ENT") || tipo1.equals("REAL")) || !(tipo2.equals("ENT") || tipo2.equals("REAL"))){
							System.out.println("ERROR: fallo en tipo ASIGNACION" + this);
							Programa.setFin();
						}
						else{
							setTipo(new TiposBasicos(KindTipo.ENT));
						}
						break;
					case DIVREALPREF:
						if (!(tipo1.equals("ENT") || tipo1.equals("REAL")) || !(tipo2.equals("ENT") || tipo2.equals("REAL"))){
							System.out.println("ERROR: fallo en tipo ASIGNACION" + this);
							Programa.setFin();
						}
						else{
							setTipo(new TiposBasicos(KindTipo.REAL));
						}
						break;
					case MODPREF:
						if (!(tipo1.equals(tipo2)) || !tipo1.equals("ENT")){
							System.out.println("ERROR: fallo en tipo ASIGNACION" + this);
							Programa.setFin();
						}
						else{
							setTipo(tipo1);
						}
						break;
					case ASIG:
						if ((tipo1.equals("REAL") && !(tipo2.equals("ENT") || tipo2.equals("REAL"))) || (tipo1.equals("BOOL") && !tipo2.equals("BOOL")) || (tipo1.equals("ENT") && !tipo2.equals("ENT"))) {
							System.out.println("ERROR: fallo en tipo ASIGNACION" + this);
							Programa.setFin();
						} 
						else{
							setTipo(tipo1);
						}
						break;
						
					default:
						setTipo(tipo2);
						break;

				}
			}
		}

		
	}
	
	public void generaCodigo(){
		if(!(exp2.getModifiable()) ){
			switch(tipoAsig){				
			case SUMAPREF:
				exp1.calcularDirRelativa();
				Programa.codigo.println("\tcall $repeat");
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".load");
				exp2.generaCodigo();
				if (!exp1.getTipo().equals(exp2.getTipo()) && exp2.getTipo().equals("REAL")){
					Programa.codigo.println("\ti32.trunc_f32_s ");
				}
				else if (!exp1.getTipo().equals(exp2.getTipo()) && exp2.getTipo().equals("ENT")) {
					Programa.codigo.println("\t" + "f32.convert_i32_s");
				}
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".add");
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".store");
				break;
			case RESTAPREF:
				exp1.calcularDirRelativa();
				Programa.codigo.println("\tcall $repeat");
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".load");
				exp2.generaCodigo();
				if (!exp1.getTipo().equals(exp2.getTipo()) && exp2.getTipo().equals("REAL")){
					Programa.codigo.println("\ti32.trunc_f32_s ");
				}
				else if (!exp1.getTipo().equals(exp2.getTipo()) && exp2.getTipo().equals("ENT")) {
					Programa.codigo.println("\t" + "f32.convert_i32_s");
				}
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".sub");
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".store");
				break;
				
			case MULTPREF:
				exp1.calcularDirRelativa();
				Programa.codigo.println("\tcall $repeat");
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".load");
				exp2.generaCodigo();
				if (!exp1.getTipo().equals(exp2.getTipo()) && exp2.getTipo().equals("REAL")){
					Programa.codigo.println("\ti32.trunc_f32_s ");
				}
				else if (!exp1.getTipo().equals(exp2.getTipo()) && exp2.getTipo().equals("ENT")) {
					Programa.codigo.println("\t" + "f32.convert_i32_s");
				}
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".mul");
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".store");
				break;
			case DIVENTPREF:
				exp1.calcularDirRelativa();
				Programa.codigo.println("\tcall $repeat");
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".load");
				exp2.generaCodigo();
				if (!exp1.getTipo().equals(exp2.getTipo()) && exp2.getTipo().equals("REAL")){
					Programa.codigo.println("\ti32.trunc_f32_s ");
				}
				else if (!exp1.getTipo().equals(exp2.getTipo()) && exp2.getTipo().equals("ENT")) {
					Programa.codigo.println("\t" + "f32.convert_i32_s");
				}
				Programa.codigo.println("\ti32.div_s");
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".store");
				break;
			case DIVREALPREF:
				exp1.calcularDirRelativa();
				Programa.codigo.println("\tcall $repeat");
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".load");
				exp2.generaCodigo();
				if (!exp1.getTipo().equals(exp2.getTipo()) && exp2.getTipo().equals("REAL")){
					Programa.codigo.println("\ti32.trunc_f32_s ");
				}
				else if (!exp1.getTipo().equals(exp2.getTipo()) && exp2.getTipo().equals("ENT")) {
					Programa.codigo.println("\t" + "f32.convert_i32_s");
				}
				
				if(exp1.getTipo().equals(new TiposBasicos(KindTipo.REAL))){
					Programa.codigo.println("\tf32.div");
				}
				else{
					Programa.codigo.println("\ti32.div_s");
				}
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".store");
				break;
				
			case MODPREF:
				exp1.calcularDirRelativa();
				Programa.codigo.println("\tcall $repeat");
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".load");
				exp2.generaCodigo();
				Programa.codigo.println("\ti32.rem_s");
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".store");
				break;
			case ASIG:
				exp1.calcularDirRelativa();
				exp2.generaCodigo();
				if (exp1.getTipo().equals("REAL") && exp2.getTipo().equals("ENT")){
					Programa.codigo.println("\t" + "f32.convert_i32_s");
				}
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".store");
				break;
			}
		}
		else{
			if (exp2.getKind().equals(KindAsig.LLAMADA)){
				if (exp2.getTipo() instanceof TiposBasicos){
					exp1.calcularDirRelativa();
					exp2.generaCodigo();
					Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".store");
				}
				else{
					exp2.generaCodigo();
					exp1.calcularDirRelativa();
					Programa.codigo.println("\ti32.const "  + (tipo.getTam()/4));
					Programa.codigo.println("\tcall $copyn");
				}
			}
			else{
				exp2.calcularDirRelativa();
				exp1.calcularDirRelativa();
				Programa.codigo.println("\ti32.const "  + (tipo.getTam()/4));
				Programa.codigo.println("\tcall $copyn");
			}

		}
		
	}
	
	

	

}
