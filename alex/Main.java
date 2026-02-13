package alex;
import constructorast.ClaseLexica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Main {
   public static void main(String[] args) throws FileNotFoundException, IOException {
     Reader input = new InputStreamReader(new FileInputStream(args[0]));
     AnalizadorLexicoTiny al = new AnalizadorLexicoTiny(input);
     java_cup.runtime.Symbol unidad;
     do {
       unidad = al.next_token();
       System.out.println(unidad);
     }
     while (unidad.sym != ClaseLexica.EOF);
     
    }        
} 