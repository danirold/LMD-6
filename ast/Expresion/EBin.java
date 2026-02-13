package ast.Expresion;

import ast.Linea.KindAsig;
import ast.Programa;
import ast.Tipos.*;

public class EBin extends Expresion {
	private Expresion exp1;
	private Expresion exp2;
	private KindAsig kind;


	public EBin(Expresion exp1, Expresion exp2, KindAsig kind){
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.kind = kind;
	}

	@Override
	public String toString(){
		return "(" + kind.toString() + " (" + exp1.toString() + ", " + exp2.toString() + "))"; 
	}
	
	public void binding(){
		exp1.binding();
		if (!kind.equals(KindAsig.ACCESO)){
			exp2.binding();
		}
	}
	
	public KindAsig getKind() {
		return kind;
	}
	
	public void checkType(){
		exp1.checkType();
		exp2.checkType();
		setTipo(exp2.getTipo());
		switch(kind){				
			case POR:
			case MAS: 
			case MENOS:
				if (!(exp1.getTipo().equals("ENT") || exp1.getTipo().equals("REAL")) ||
				        !(exp2.getTipo().equals("ENT") || exp2.getTipo().equals("REAL"))) {
					System.out.println("ERROR: fallo en tipo EBIN" + this);
					Programa.setFin();
				}
				if (exp1.getTipo().equals("REAL") || exp2.getTipo().equals("REAL")) {
					setTipo(new TiposBasicos(KindTipo.REAL));
				}
				break;
			case DIVREAL:
				if (!(exp1.getTipo().equals("ENT") || exp1.getTipo().equals("REAL")) ||
				        !(exp2.getTipo().equals("ENT") || exp2.getTipo().equals("REAL"))){
					System.out.println("ERROR: fallo en tipo EBIN" + this);
					Programa.setFin();
				}
				setTipo(new TiposBasicos(KindTipo.REAL));
				break;
			case DIVENT:
				if (!(exp1.getTipo().equals("ENT") || exp1.getTipo().equals("REAL")) ||
				        !(exp2.getTipo().equals("ENT") || exp2.getTipo().equals("REAL"))){
					System.out.println("ERROR: fallo en tipo EBIN" + this);
					Programa.setFin();
				}
				setTipo(new TiposBasicos(KindTipo.ENT));
				break;
			case MOD:
				if (!(exp1.getTipo().equals(exp2.getTipo()) && exp1.getTipo().equals("ENT"))){
					System.out.println("ERROR: fallo en tipo EBIN" + this);
					Programa.setFin();
				}
				break;
			case MAYORIG:
			case MENORIG:
			case MAYOR:
			case MENOR:
				if (!(exp1.getTipo().equals(exp2.getTipo()) && !exp1.getTipo().equals("BOOL"))){
					System.out.println("ERROR: fallo en tipo EBIN" + this);
					Programa.setFin();
				}
				setTipo(new TiposBasicos(KindTipo.BOOL));
				break;
			case IGUALIG:
			case DISTINTO:
				if (!(exp1.getTipo().equals(exp2.getTipo()))){
					System.out.println("ERROR: fallo en tipo EBIN" + this);
					Programa.setFin();
				}
				setTipo(new TiposBasicos(KindTipo.BOOL));

				break;
			case AND:
			case OR:
				if (!(exp1.getTipo().equals(exp2.getTipo()) && exp1.getTipo().equals("BOOL"))){
					System.out.println("ERROR: fallo en tipo EBIN" + this);
					Programa.setFin();
				}
				break;
			default:
				break;
			
		}
	}
	
	public void generaCodigo() {
	    if (exp1.getTipo().equals(new TiposBasicos(KindTipo.REAL)) && exp2.getTipo().equals(new TiposBasicos(KindTipo.ENT))) {
	    	exp1.generaCodigo();
		    exp2.generaCodigo();
		    Programa.codigo.println("\t" + "f32.convert_i32_s");
	    }
	    else if (exp1.getTipo().equals(new TiposBasicos(KindTipo.ENT)) && exp2.getTipo().equals(new TiposBasicos(KindTipo.REAL))) {
	    	exp1.generaCodigo();
	    	Programa.codigo.println("\t" + "f32.convert_i32_s");
	    	exp2.generaCodigo();
	    }
	    else if (exp1.getTipo().equals(exp2.getTipo())) {
	    	exp1.generaCodigo();
	    	exp2.generaCodigo();
	    }
	    switch (kind) {
	      case MAS:
	    	  if (exp1.getTipo().equals(new TiposBasicos(KindTipo.REAL)) || exp1.getTipo().equals(exp2.getTipo())){
	    		  Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".add"); 
	    	  }
	    	  else if (exp2.getTipo().equals(new TiposBasicos(KindTipo.REAL))){
	    		  Programa.codigo.println("\t" + exp2.getTipo().convertWasm() + ".add");  
	    	  }
	        break;
	      case MENOS:
	    	  if(exp1.getTipo().equals(new TiposBasicos(KindTipo.REAL)) || exp1.getTipo().equals(exp2.getTipo())){
	    		  Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".sub"); 
	    	  }
	    	  else if (exp2.getTipo().equals(new TiposBasicos(KindTipo.REAL))){
	    		  Programa.codigo.println("\t" + exp2.getTipo().convertWasm() + ".sub");  
	    	  }
	        break;
	      case POR:
	    	  if(exp1.getTipo().equals(new TiposBasicos(KindTipo.REAL)) || exp1.getTipo().equals(exp2.getTipo())){
	    		  Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".mul"); 
	    	  }
	    	  else if (exp2.getTipo().equals(new TiposBasicos(KindTipo.REAL))){
	    		  Programa.codigo.println("\t" + exp2.getTipo().convertWasm() + ".mul");  
	    	  }
	    	  break;
	      case DIVREAL:
		if(exp1.getTipo().equals(new TiposBasicos(KindTipo.REAL))){
			Programa.codigo.println("\tf32.div");

		}
		else{
			Programa.codigo.println("\ti32.div_s");
		}
	        break;
	      case DIVENT:
	        Programa.codigo.println("\ti32.div_s");
	        break;
	      case MOD:
	        Programa.codigo.println("\ti32.rem_s");
	        break;
	      case MENOR:
	        if (exp1.getTipo().equals(new TiposBasicos(KindTipo.REAL))){
			Programa.codigo.println("\tf32.lt");
		}
		else{
	        Programa.codigo.println("\ti32.lt_s");
		}
	        break;
	      case MAYOR:
	        if (exp1.getTipo().equals(new TiposBasicos(KindTipo.REAL))){
			Programa.codigo.println("\tf32.gt");
		}
		else{
	        Programa.codigo.println("\ti32.gt_s");
		}
	        break;
	      case AND:
	        Programa.codigo.println("\ti32.and");
	        break;
	      case OR:
	        Programa.codigo.println("\ti32.or");
	        break;
	      case IGUALIG:
		Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".eq");
	        break;
	      case DISTINTO:
		Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".ne");
	        break;
	      case MAYORIG:
	        if (exp1.getTipo().equals(new TiposBasicos(KindTipo.REAL))){
			Programa.codigo.println("\tf32.ge");
		}
		else{
	        Programa.codigo.println("\ti32.ge_s");
		}
	        break;
	      case MENORIG:
	        if (exp1.getTipo().equals(new TiposBasicos(KindTipo.REAL))){
			Programa.codigo.println("\tf32.le");
		}
		else{
	        Programa.codigo.println("\ti32.le_s");
		}
	        break;
	      default:
	    }
	  }


}