package ast;

import java.util.Stack;
import java.util.HashMap;
import java.util.Map;

import java.util.List;
import java.util.ArrayList;
import ast.Linea.Funcion.Argumento;


public class PilaTablaSimbolos {
    
    private List<HashMap<String,ASTNode>> pilaTabla;
    private List<Integer> deltaList;
    private int delta;

    public PilaTablaSimbolos() {
        this.pilaTabla = new ArrayList<HashMap<String,ASTNode>>();
        this.deltaList = new ArrayList<Integer>();
        this.delta = 0;
    }


    public void insertar (String id, ASTNode puntero){
        if(pilaTabla.size() != 0){
            HashMap<String,ASTNode> top = pilaTabla.get(pilaTabla.size() - 1);
            top.put(id, puntero);
        }
    }
    
    public void abrirBloque(){
        pilaTabla.add(new HashMap<String,ASTNode>());
        deltaList.add(delta);
    }

    public void cerrarBloque(){
        if (pilaTabla.size() != 0){ 
            pilaTabla.remove(pilaTabla.size()-1); 
        }
        delta = deltaList.get(deltaList.size() - 1);
        deltaList.remove(deltaList.size() - 1);
    }

    public int getDelta(){
    	return delta;
    }

    public void updateDelta(int size){
    	delta += size;
    }

    public ASTNode searchId (String id){//primera aparicion de id en la pila
        ASTNode node = null;
        if(pilaTabla.size() != 0){
            HashMap<String,ASTNode> last = pilaTabla.get(pilaTabla.size()-1);
            node = last.get(id);
        }
        for (int i = pilaTabla.size() - 2; i >= 0 && node == null; i--){
        	HashMap<String,ASTNode> last = pilaTabla.get(i);
            node = last.get(id);
        } 
        return node;
    }

    public ASTNode searchIdLastFun(String id){
    	ASTNode node = null;
    	if(pilaTabla.size() != 0){
            HashMap<String,ASTNode> last = pilaTabla.get(pilaTabla.size()-1);
            node = last.get(id);
        }
    	return node;
   }

   public int getSize(){
	   return pilaTabla.size();
   }


    private void printAmbito(HashMap<String,ASTNode> ambito){
        for (Map.Entry<String, ASTNode> entry : ambito.entrySet()) {
            String key = entry.getKey();
            ASTNode value = entry.getValue();
            System.out.println(key + " -> " + value);
        }
    }

    public void print(){

	if (pilaTabla.size() != 0){
		System.out.println("INICIO AMBITO");
		HashMap<String,ASTNode> last = pilaTabla.get(pilaTabla.size() - 1);
		printAmbito(last);
		System.out.println("FIN AMBITO");

	}
        else {
		System.out.println("Pila Vacia");
	}
   }

   public void printAll(){
	for(int i = 0; i < pilaTabla.size(); i++){
		System.out.println("INICIO AMBITO");
		HashMap<String,ASTNode> last = pilaTabla.get(i);
		printAmbito(last);
		System.out.println("FIN AMBITO");
	}
  }
        
	
}