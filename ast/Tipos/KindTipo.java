package ast.Tipos;

public enum KindTipo{
	ENT("ENT"), BOOL("BOOL"), REAL("REAL"), VOID("VOID"), IDEN("IDEN");

	private final String nombre;

	private KindTipo(String nombre){
		this.nombre = nombre;
	}

	@Override
	public String toString(){
		return nombre;
	}
}