package ast;

import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileReader;

import ast.Linea.*;
import ast.Linea.Funcion.*;
import ast.Linea.Bucles.*;
import ast.Linea.Condicional.*;
import ast.Tipos.*;
import ast.Expresion.*;

public class Programa extends ASTNode {
	private List<Instruccion> prog;
	public static PilaTablaSimbolos pila;
	


	private static int fin = 0;	

	private static int numMain = 0;

	public static PrintWriter codigo;


	public Programa(List<Instruccion> prog){
		this.prog = prog;
		this.pila = new PilaTablaSimbolos();
	}
	
	public String toString(){
		String s = "";
		for(int i = 0; i < prog.size(); i++){
			if (i == prog.size() - 1)  s += prog.get(i).toString();
			else s += prog.get(i).toString() + "\n";
		}
		return "PROGRAMA\n" + s;
	}


	public NodeKind nodeKind(){
		return NodeKind.PROGRAMA;
	}
	private void inicializarFunciones(){
		List<Argumento> args = new ArrayList<Argumento>();
		args.add(new Argumento(new TiposBasicos(KindTipo.ENT), "x", 0));
		pila.insertar("outEnt", new Func(new TiposBasicos(KindTipo.VOID), "outEnt",args,new ArrayList<Instruccion>()));
		pila.insertar("toReal", new Func(new TiposBasicos(KindTipo.REAL), "toReal",args,new ArrayList<Instruccion>()));
		List<Argumento> args1 = new ArrayList<Argumento>();
		args1.add(new Argumento(new TiposBasicos(KindTipo.BOOL), "x", 0));
		pila.insertar("outBool", new Func(new TiposBasicos(KindTipo.VOID), "outBool", args1, new ArrayList<Instruccion>()));
		List<Argumento> args2 = new ArrayList<Argumento>();
		args2.add(new Argumento(new TiposBasicos(KindTipo.REAL), "x", 0));
		pila.insertar("outReal", new Func(new TiposBasicos(KindTipo.VOID), "outReal",args2, new ArrayList<Instruccion>()));
		pila.insertar("toEnt", new Func(new TiposBasicos(KindTipo.ENT), "toEnt",args2, new ArrayList<Instruccion>()));
		pila.insertar("inEnt", new Func(new TiposBasicos(KindTipo.ENT), "inEnt", new ArrayList<Argumento>(), new ArrayList<Instruccion>()));
		pila.insertar("inBool", new Func(new TiposBasicos(KindTipo.BOOL), "inBool", new ArrayList<Argumento>(),new ArrayList<Instruccion>()));
		pila.insertar("inReal", new Func(new TiposBasicos(KindTipo.REAL), "inReal", new ArrayList<Argumento>(),new ArrayList<Instruccion>()));

	}
	
	public void binding(){
		pila.abrirBloque();
		inicializarFunciones();
		for (int i = 0; i < prog.size(); i++){
			prog.get(i).binding();
		}
		if (numMain == 0){
			System.out.println("ERROR no hay ningun main en PROGRAMA "); 
		}
		pila.cerrarBloque();
	}
	
	public void checkType(){
		for(int i = 0; i < prog.size(); i++){
			prog.get(i).checkType();
		}
	}
	
	public static void insertar(String name, ASTNode node){
		pila.insertar(name, node);
	}

	public static void abrirBloque(){
		pila.abrirBloque();
	}

	public static void cerrarBloque(){
		pila.cerrarBloque();
	}

	public static ASTNode searchId(String name){
		return pila.searchId(name);
	}
	
	public static ASTNode searchIdLastFun(String name){
		return pila.searchIdLastFun(name);
	}

	public static void print(){
		pila.print();
	}

	public static void printAll(){
		pila.printAll();
	}
	
	public int getFin(){
		return this.fin;
	}	

	public static void setFin(){
		fin = 1;
	}

	public static void addMain(){
		numMain += 1;
	}

	public String getName(){
		return "";
	}
	
	private void calcularDeltas(){
		for(Instruccion ins: prog){
			ins.setPos();
		}
	} 
	
	public static int getSize(){
		return pila.getSize();
	}

	public void updateDelta(int tam){
		pila.updateDelta(tam);
	}
	
	public int maxMemory(){
		int max = 0;
		for(Instruccion ins : prog){
			if(ins instanceof Nuevo || ins instanceof Struct){
				max += ins.getTamanyo();
			}
		}
		return max + 4;
		
	}
	
	public void generaCodigo(){
		try{
		calcularDeltas();
		codigo = new PrintWriter(new FileWriter("codigo.wat"));
		FileReader inicio = new FileReader("generacion_codigo/inicio.wat");
		inicio.transferTo(codigo);
		codigo.println("\ti32.const " + maxMemory());
		codigo.println("\tcall $reserveStack");
		codigo.println("\tlocal.set $temp");
		codigo.println("\tglobal.get $MP");
		codigo.println("\tlocal.get $temp");
		codigo.println("\ti32.store");
		codigo.println("\tglobal.get $MP");
		codigo.println("\ti32.const 4");
		codigo.println("\ti32.add");
		codigo.println("\tlocal.set $localsStart\n");
		Instruccion insMain = null;
		
		for(Instruccion ins : prog){
			if (ins instanceof Nuevo || ins instanceof Asignacion){
				ins.generaCodigo();
				
			}
			else if (ins instanceof Func){
				Func fun = (Func) ins;
				if (fun.getName().equals("main")){
					insMain = ins;
				}
			}
			Programa.codigo.println("");
		}
		codigo.println("\tcall $main");
		codigo.println("\ti32.load");
		codigo.println("\tcall $outEnt");
		codigo.println("\tcall $freeStack");
		codigo.println(")");
		insMain.generaCodigo();
		Programa.codigo.println("");
		for(Instruccion ins : prog){
			if (!(ins instanceof Nuevo) && !(ins instanceof Asignacion) && !(ins.equals(insMain))){
				ins.generaCodigo();
				Programa.codigo.println("");
			}
		}
	
		inicio = new FileReader("generacion_codigo/final.wat");
		codigo.println("\n\n\n ;;FUNCIONES DEFINIDAS");
		inicio.transferTo(codigo);
		inicio.close();
          	codigo.close();
		} catch(Exception e){
			Programa.setFin();	
		}
	}
}
