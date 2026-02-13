package ast.Expresion;

import ast.Linea.KindAsig;
import java.util.List;
import ast.Programa;
import ast.Tipos.*;
import ast.Expresion.*;

public class Acceso extends Expresion {
	private Expresion exp1;
	private List<Expresion> exp2;
	private KindAsig kind;


	public Acceso(Expresion exp1, List<Expresion> exp2, KindAsig kind){
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.kind = kind;
		this.isModifiable = true;
	}

	@Override
	public String toString(){
		return "(POSICION (" + exp1.toString() + ", " + exp2.toString() + "))"; 
	}
	
	public void binding(){
		exp1.binding();
		for(Expresion e: exp2){
			e.binding();
		}
	}
	
	public void checkType(){
		exp1.checkType();
		Tipo tipo1 = exp1.getTipo();
		if (tipo1 == null){
			System.out.println("ERROR: fallo en puntero ACCESO" + this);
			Programa.setFin();
		}
		else{
			if (!(tipo1 instanceof TipoArray)){
				System.out.println("ERROR: fallo en tipo ACCESO" + this);
					Programa.setFin();
			}	
			else{
				
				TipoArray tipoA = (TipoArray) tipo1; 
				setTipo(tipoA.getTipoBasico());
				if(tipoA.getTamanyos().size() != exp2.size()){
					System.out.println("ERROR: fallo en tipo ACCESO" + this);
					Programa.setFin();
				}
				for (int i = 0; i < exp2.size(); ++i) {
					exp2.get(i).checkType();
					if (!exp2.get(i).getTipo().equals(new TiposBasicos(KindTipo.ENT))){
						System.out.println("ERROR: fallo en tipo ACCESO" + this);
						Programa.setFin();
					}
					else if (exp2.get(i) instanceof Ent){
						Ent e = (Ent) exp2.get(i);
						if (e.getInt() >= tipoA.getTamanyos().get(i).getInt() || e.getInt() < 0){
							System.out.println("ERROR: fallo en tipo ACCESO" + this);
							Programa.setFin();
						}
					}
				}
			}
		}
	}
	
	public KindAsig getKind() {
		return kind;
	}
	
	public void calcularDirRelativa(){

		comprobarRangos();

		exp1.calcularDirRelativa();
		List<Ent> listTamanyos = exp1.getTipo().getTamanyos();
		int total = exp1.getTipo().getNumElems();
		for(int i = 0 ; i < listTamanyos.size(); i++){

			total = total / listTamanyos.get(i).getInt();
			Programa.codigo.println("\ti32.const " + total);
			exp2.get(i).generaCodigo();
			Programa.codigo.println("\ti32.mul");
			Programa.codigo.println("\ti32.const " + exp1.getTipo().getTipoBasico().getTam());
			Programa.codigo.println("\ti32.mul");
			Programa.codigo.println("\ti32.add");
		}
	}

	public void comprobarRangos(){
		List<Ent> listTamanyos = exp1.getTipo().getTamanyos();
		for(int i = 0 ; i < listTamanyos.size(); i++){
			exp2.get(i).generaCodigo();
			int max_tam = listTamanyos.get(i).getInt() - 1;
			Programa.codigo.println("\ti32.const " + max_tam);
			Programa.codigo.println("\ti32.gt_s");
			Programa.codigo.println("\tif");
			Programa.codigo.println("\ti32.const 1");
			Programa.codigo.println("\tcall $exception");
			Programa.codigo.println("\tend");
			exp2.get(i).generaCodigo();
			Programa.codigo.println("\ti32.const 0");
			Programa.codigo.println("\ti32.lt_s");
			Programa.codigo.println("\tif");
			Programa.codigo.println("\ti32.const 2");
			Programa.codigo.println("\tcall $exception");
			Programa.codigo.println("\tend");

		}

	}

	public void generaCodigo(){
		calcularDirRelativa();
		Programa.codigo.println("\t" + exp1.getTipo().getTipoBasico().convertWasm() + ".load");
	}

}