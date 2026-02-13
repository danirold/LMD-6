package ast.Linea;

import java.util.List;
import java.util.ArrayList;
import ast.Expresion.Expresion;
import ast.Programa;
import ast.ASTNode;
import ast.Linea.Funcion.*;
import ast.Tipos.*;

public class Llamada extends Instruccion {
	private String iden;
	private List<Expresion> args;
	private boolean asigned;
	
	public Llamada(){

	}

	public Llamada(String iden, List<Expresion> args){
		this.iden = iden;
		this.args = args;
		this.asigned = false;
	}

	public Llamada(String iden, Expresion exp){
		this.iden = iden;
		this.args = new ArrayList<Expresion>();
		this.args.add(exp);
		this.asigned = false;
	}
	
	public KindAsig getKind() {
		return KindAsig.LLAMADA;
	}

	@Override
	public String toString(){
		return 	"(CALL (" + iden + ", " + args.toString() + "))";	
	}
	
	public void binding(){
		ASTNode node = Programa.searchId(iden);
		if (node != null){
			this.link = node;
		}
		else{
			System.out.println("ERROR: funcion " + iden + " no declarada LLAMADA");
			Programa.setFin();
		}
		if (args != null){
			for(Expresion arg: args){
				arg.binding(); 
			}
		}
	}
	
	public String getName() {
		return iden;
	}

	@Override
	public void checkType(){
		if (!(this.getLink() instanceof Func)){
			System.out.println("ERROR en Llamada 1 " + this);
			Programa.setFin();
		}
		else{	
			Func fun = (Func) this.getLink();
			List<Argumento> listFunArgs = fun.getArgs();
			if (listFunArgs.size() != args.size()){

				System.out.println("ERROR en Llamada 2" + this);
				Programa.setFin();
			}
			else{

				for(int i = 0; i < listFunArgs.size(); i++){
					args.get(i).checkType();
					Argumento arg = listFunArgs.get(i);
		

					
					if (!(args.get(i).getTipo().equals(arg.getTipo()))){

						System.out.println("ERROR en Llamada 3" + this);
						Programa.setFin();
					}

					if (arg.getRef() == 1){//referencia	
						if (!(args.get(i).getModifiable())){ 
							System.out.println("ERROR en Llamada 4" + this);
							Programa.setFin();
						}
					}
					
				}
			}
		} 
	}
	
public void generaCodigo(){
		
		int delta = 0;
		Func fun = (Func) this.getLink();
		List<Argumento> listFunArgs = fun.getArgs();
		int pos = 0;
		for(Expresion exp : args){
			delta += exp.getTipo().getTam();
			if (!(exp.getModifiable())){
				Programa.codigo.println("\tglobal.get $SP");
				Programa.codigo.println("\ti32.const " + delta);
				Programa.codigo.println("\ti32.add");
				exp.generaCodigo();

				Programa.codigo.println("\t" + exp.getTipo().convertWasm() + ".store");

			}
			else{
				if (listFunArgs.get(pos).getRef() == 0){
					exp.calcularDirRelativa();
					Programa.codigo.println("\tglobal.get $SP");
					Programa.codigo.println("\ti32.const " + (listFunArgs.get(pos).getDelta() + 4));
					Programa.codigo.println("\ti32.add");
					Programa.codigo.println("\ti32.const " + (exp.getTipo().getTam()/4));
					if (exp.getTipo().equals("REAL")) Programa.codigo.println("\tcall $copynr");
					else Programa.codigo.println("\tcall $copyn");
					Programa.codigo.println("\tglobal.get $SP");
					Programa.codigo.println("\ti32.const " + (listFunArgs.get(pos).getDelta() + 4));
					Programa.codigo.println("\ti32.add");
					Programa.codigo.println("\t" + exp.getTipo().convertWasm() + ".load");
				}
				else{
					Programa.codigo.println("\tglobal.get $SP");
					delta -= exp.getTipo().getTam();
					delta += 4;
					Programa.codigo.println("\ti32.const " + (listFunArgs.get(pos).getDelta() + 4));
					Programa.codigo.println("\ti32.add");
					exp.calcularDirRelativa();
					Programa.codigo.println("\t" + exp.getTipo().convertWasm() + ".store");
				}
			}			
			pos += 1;
		}
		Programa.codigo.println("\tcall $" + iden);
		if(!this.asigned && (!(this.link.getTipo().equals(new TiposBasicos(KindTipo.VOID))))){
			Programa.codigo.println("\tdrop");
		}
		else if (!(this.link.getTipo().equals(new TiposBasicos(KindTipo.VOID))) && !iden.equals("toReal") && !iden.equals("toEnt")){
			Programa.codigo.println("\t" + getLink().getTipo().convertWasm() + ".load");
		}

	}

	public void setAsigned(){
		this.asigned = true;
	}
	
}