package ast.Linea;
import ast.Programa;
import ast.ASTNode;
import ast.Tipos.*;

import java.util.List;

public class Struct extends Nuevo{
	
	private String iden;
	private List<Nuevo> campos;
	private boolean global = false;


	public Struct(String iden, List<Nuevo> campos){
		this.iden = iden;
		this.campos = campos;
	}


	@Override
	public String toString(){
		return "(STRUCT (" + iden + ", " + campos.toString() + "))";
	}
	
	public void binding(){

		ASTNode node = Programa.searchId(iden);
		if (node == null){
			if(Programa.getSize() == 1){
				global = true;
			}
			Programa.insertar(iden, this);
			Programa.abrirBloque();
			for(Nuevo campo : campos){	
				campo.binding();
			}
			Programa.cerrarBloque();
			
		}
		else{
			System.out.println("ERROR: identificador en REGISTRO " + iden + " no se puede utilizar en " + this);
			Programa.setFin();
		}
	} 
	
	public void checkType(){
		for(Nuevo camp: campos){
			camp.checkType();
		}
	}

	public Tipo getTipoCampo(String name){
		for(Nuevo camp : campos){
			if (camp.getName().equals(name)){
				return camp.getTipo();
			}
		}
		return null;
	}
	
	public Tipo getTipoCampo(int ind){
		for(int i = 0; i < campos.size(); ++i){
			if (i == ind){
				return campos.get(i).getTipo();
			}
		}
		return null;
	}
	
	

	public int getDeltaCampo(String name){
		int sol = 0;
		for(Nuevo camp : campos){
			if (camp.getName().equals(name)){
				sol = camp.getDelta();
				break;
			}
		}
		return sol;
	}

	@Override
	public void setPos(){
		int structDelta = 0;
		this.delta = Programa.pila.getDelta();
		for(Nuevo camp : campos){
			
			camp.delta = structDelta;
			structDelta += camp.getTamanyo();
		}
		Programa.pila.updateDelta(getTamanyo());

	}
	public void setPos(int funDelta){
		int structDelta = 0;
		this.delta = funDelta;
		for(Nuevo camp : campos){
			
			camp.delta = structDelta;
			structDelta += camp.getTamanyo();
		}
		Programa.pila.updateDelta(getTamanyo());

	}

	public int getTamanyo(){
		int sol = 0;
		for(Nuevo cre : campos){
			sol += cre.getTamanyo();
		}
		return sol;
	}
	
	public String getName() {
		return iden;
	}
	
	public int getTam() {
		return campos.size();
	}
	
	public void calcularDirRelativa(){
		if (global){
			Programa.codigo.println("\ti32.const " + (delta + 4));
		}
		else{
			Programa.codigo.println("\ti32.const " + delta);
        	Programa.codigo.println("\tlocal.get $localsStart");
        	Programa.codigo.println("\ti32.add");
		}

	}

	public void generaCodigo(){
		for(Nuevo campo : campos){
			campo.setDeltaSt(delta);
			campo.generaCodigo();
		}
	}
}