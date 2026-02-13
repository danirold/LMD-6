package ast.Tipos;

public class PunteroArray extends Tipo{
	private Tipo tipoBasico;

	public PunteroArray(Tipo tipo){
		this.tipoBasico = tipo;
	}
	
	@Override
	public String toString(){
		return tipoBasico.toString() + " ARRAY POINTER";
	}
}