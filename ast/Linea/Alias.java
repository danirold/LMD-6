package ast.Linea;

import ast.ASTNode;
import ast.Programa;
import ast.Tipos.Tipo;

public class Alias extends Declaracion {

    private Tipo tipo;
    private String nombre;

    public Alias(Tipo t, String nombre){
        this.nombre = nombre;
        this.tipo = t;
    }


    public String toString() {
        return "(ALIAS (" + nombre + ", " + tipo.toString() + ")";
    }

    public void binding() {
        ASTNode nodo = Programa.pila.searchId(nombre);
        if (nodo != null) {
        	System.out.println("ERROR: este identificador ya esta usado " + nombre);	
        	Programa.setFin();	
       } 
        else {
            Programa.pila.insertar(nombre, this);
            tipo.binding();
        }
    }
    
    public void checkType(){
     	this.tipo = this.tipo.reduceAlias();
    }

    public Tipo getTipo() {
        return this.tipo;
    }
    public String getNombre(){
        return this.nombre;
    }
    
}
