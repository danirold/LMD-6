package ast.Expresion;

import ast.Linea.KindAsig;
import ast.Programa;
import ast.Tipos.*;

public class EUnitaria extends Expresion {
	private Expresion exp;
	private KindAsig kind;


	public EUnitaria(Expresion exp, KindAsig kind){
		this.exp = exp;
		this.kind = kind;
		this.isModifiable = true;
	}

	@Override
	public String toString(){
		return "(" + kind.toString() + " (" + exp.toString() + "))";
	}
	
	public void binding(){
		exp.binding();
	}
	
	public KindAsig getKind() {
		return kind;
	}
	
	public void checkType(){
		exp.checkType();
		Tipo t = exp.getTipo();

		switch(kind){				
			case INTERROGACION: 	

				if (t == null){
					System.out.println("ERROR: fallo en puntero EUN" + this);
					Programa.setFin();
				}
				else{
					if (!(t instanceof TipoPuntero)){
						System.out.println("ERROR: fallo en tipo EUN" + this);
						Programa.setFin();
					}	
					else{
						TipoPuntero tipoP = (TipoPuntero) t;
						setTipo(tipoP.getTipoBasico());
					}
				}

				break;
			case ARROBA: 		
				if (t == null){
					System.out.println("ERROR: fallo en puntero EUN" + this);
					Programa.setFin();
				}
				else{
					setTipo(new TipoPuntero(t));	
				}

				break;


			case EXCLAM:
				if (!(t.equals("BOOL"))){
					System.out.println("ERROR: fallo en tipo EUN" + this);
					Programa.setFin();
				}
				else{
					setTipo(exp.getTipo());
				}
				break;

			case PAR:
				setTipo(t);
				break;
			case TOREAL:
				if (!(t.equals("ENT"))){
					System.out.println("ERROR: fallo en tipo EUN" + this);
					Programa.setFin();
				}
				else{
					setTipo(new TiposBasicos(KindTipo.REAL));
				}
				break;
			case TOENT:
				if (!(t.equals("REAL"))){
					System.out.println("ERROR: fallo en tipo EUN" + this);
					Programa.setFin();
				}
				else{
					setTipo(new TiposBasicos(KindTipo.ENT));
				}
				break;
			case NEG:
				if (!((t.equals("ENT")) || (t.equals("REAL")))){
					System.out.println("ERROR: fallo en tipo EUN" + this);
					Programa.setFin();
				}
				else{
					setTipo(t);
				}
				
				break;
			default:
				break;
			
		}


	}
	
	public boolean getModifiable(){
		if(!kind.equals(KindAsig.EXCLAM) && !kind.equals(KindAsig.TOENT) && !kind.equals(KindAsig.TOENT)){
			return exp.getModifiable();

		}
		return false;
	}
	
	public void generaCodigo(){
		switch (kind) {
	        case EXCLAM:
        	 	exp.generaCodigo();
        	 	Programa.codigo.println("\ti32.load");
          		Programa.codigo.println("\ti32.eqz");
          		Programa.codigo.println("\tif (result i32)");
          		Programa.codigo.println("\ti32.const 1");
          		Programa.codigo.println("\telse");
          		Programa.codigo.println("\ti32.const 0");
          		Programa.codigo.println("\tend");
         		break;
        	case INTERROGACION:
          		exp.generaCodigo();
          		Programa.codigo.println("\t" + this.getTipo().convertWasm() + ".load");
    			break;
		case ARROBA:
			exp.calcularDirRelativa();
			break;
		case TOREAL:
			exp.generaCodigo();
			Programa.codigo.println("\tcall $intToDec");
			break;
		case TOENT:
			exp.generaCodigo();
			Programa.codigo.println("\tcall $decToInt");
			break;
		case PAR:
			exp.generaCodigo();
			break;
		case NEG: 
			Programa.codigo.println("\t"  + this.getTipo().convertWasm() + ".const 0");
			exp.generaCodigo();
			Programa.codigo.println("\t" + this.getTipo().convertWasm() + ".sub");  
			break;
        	default:
      		}
    	}
	
	
	public void calcularDirRelativa(){
		switch (kind) {
			case NEG:
				exp.calcularDirRelativa();
				break;
	        case EXCLAM:
        	 	exp.calcularDirRelativa();
         		break;
        	case INTERROGACION:
          		exp.generaCodigo();
    			break;
        	case ARROBA:
        		break;
        	case TOREAL:
        		break;
        	case TOENT:
        		break;
        	case PAR:
        		exp.calcularDirRelativa();
        		break;
        	default:
      		}
    	}


}
