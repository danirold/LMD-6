package ast.Linea;

import ast.Tipos.*;
import ast.Linea.Funcion.Argumento;
import ast.Expresion.Expresion;
import java.util.List;
import ast.Programa;
import ast.ASTNode;


public class Func extends Instruccion {
	private String iden;
	private Tipo type;
	private List<Argumento> args;
	private List<Instruccion> body;
	private int numReturn;

	public Func(Tipo type, String iden, List<Argumento> args, List<Instruccion> body){
		this.iden = iden;
		this.type = type;
		this.args = args;
		this.body = body;
		this.numReturn = 0;
		Programa.addMain();
	}
	
	@Override
	public String toString(){
		String s = "";
		for(int i = 0; i < body.size();i++){
			s += "\n\t" + body.get(i).toString();
		}
		return "(" + type.toString() + " FUNC " + iden.toString() + " (" + args.toString() + ", " + s + ")\n)";
	}
	
	public void binding(){
		ASTNode node = Programa.searchId(iden);
		type.binding();
		if (node == null){
			Programa.insertar(iden, this);
			Programa.abrirBloque();
			for(Argumento arg: args){
				arg.binding();
			}
			for(Instruccion ins: body){
				if (ins instanceof Return){
					numReturn++;
					if (numReturn > 1 || (type.equals(new TiposBasicos(KindTipo.VOID)))){//Ver si permitimos alguno más
						System.out.println("ERROR sobran returns en FUNCION " +this.iden);
						Programa.setFin();
					}
					else{
						ins.setLink(this);
					}	
				}
				ins.binding();

			}
			if (numReturn == 0 && !type.equals(new TiposBasicos(KindTipo.VOID))){
				System.out.println("ERROR falta return en FUNCION " +this.iden);
				Programa.setFin();

			}
			Programa.cerrarBloque();
		}
		else{
			System.out.println("ERROR: identificador en FUNCION " + iden + " no se puede utilizar en " + this);
			Programa.setFin();
		}

	}
	
	@Override
	public void checkType(){
		this.type = type.reduceAlias();
		for(Argumento arg: args){
			arg.checkType();
		}
		for(Instruccion ins: body){
			ins.checkType();
		}
	}

	public List<Argumento> getArgs(){
		return this.args;
	}

	public Tipo getTipo(){
		return this.type;
	}
	
	@Override
	public void setPos(){
		int funDelta = 0;
		this.delta = 0;
		for(Argumento arg : args){
			arg.delta = funDelta;
			funDelta += arg.getTam();
		}
		for(Instruccion ins : body){
			ins.delta = funDelta;
			ins.setPos(funDelta);
			funDelta += ins.getTamanyo();
		}
	}
	
	

	public String getName(){
		return iden;
	}
	
	public boolean isBlock(){
		return true;
	}
	
	public void generaCodigo(){
			int tam = maxMemory() + 4;
        	Programa.codigo.print("(func $" + iden);
        	if (! type.equals(new TiposBasicos(KindTipo.VOID))){
        		tam += 4;
           		if (type.equals(new TiposBasicos(KindTipo.REAL))) {
           			Programa.codigo.print(" (result f32)");
           		}
           		else Programa.codigo.print(" (result i32)");
           				
        	}
       		Programa.codigo.println("");
        	Programa.codigo.println("\t(local $localsStart i32)");
        	Programa.codigo.println("\t(local $temp i32)");
        	Programa.codigo.println("\ti32.const " + tam); //  ;; let this be the stack size needed (params+locals+2)*4");
        	Programa.codigo.println("\tcall $reserveStack"); // ;; returns old MP (dynamic link)");
        
        	Programa.codigo.println("\tlocal.set $temp");
        	Programa.codigo.println("\tglobal.get $MP");
        	Programa.codigo.println("\tlocal.get $temp");
        	Programa.codigo.println("\ti32.store"); // Guardo el MP antiguo en mp
        	Programa.codigo.println("\tglobal.get $MP");
        	Programa.codigo.println("\ti32.const 4"); // salto el mp antiguo y el sp
        	Programa.codigo.println("\ti32.add");
        	Programa.codigo.println("\tlocal.set $localsStart\n"); // La funcion empieza aqui
        	for (Instruccion instruccion: body){

        		instruccion.generaCodigo(); 
        		if (instruccion instanceof Return){
        			break;
        		}
        		Programa.codigo.println("");

        	}
		if (type.equals(new TiposBasicos(KindTipo.VOID))){
			Programa.codigo.println("call $freeStack");
		}
		Programa.codigo.println(")");

	}
	
	public int maxMemory(){
		int max = 0;
		int c = 0;

		for(Argumento arg : args){
			c += arg.getTam();
			max += arg.getTam();
		}
		for(Instruccion ins : body){
			if(ins instanceof Nuevo){
				c += ins.getTamanyo();
				max += ins.getTamanyo();
			}
			else if(ins.isBlock()){
				int max1 = ins.maxMemory();
				if(c+max1 > max){
					max = c + max1;
				}
			}
		}


		return max;
	} 


}