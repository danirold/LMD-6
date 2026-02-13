package alex;
import constructorast.ClaseLexica;

public class ALexOperations {
  private AnalizadorLexicoTiny alex;
  
  public ALexOperations(AnalizadorLexicoTiny alex) {
    this.alex = alex;   
  }
  
  public UnidadLexica unidadEof() { return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.EOF);}
 
  
  public UnidadLexica unidadSuma() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OP_SUMA); }
  public UnidadLexica unidadResta() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OP_RESTA); }
  public UnidadLexica unidadMul() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OP_MULT); }
  public UnidadLexica unidadDivEnt() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OP_DIV_ENT); }
  public UnidadLexica unidadDivReal() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OP_DIV_REAL); }
  public UnidadLexica unidadMod() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OP_MOD); }
  public UnidadLexica unidadMayor() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OP_MAYOR); }
  public UnidadLexica unidadMenor() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OP_MENOR); }
  public UnidadLexica unidadIgual() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OP_IGUAL); }
  public UnidadLexica unidadAcceso() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OP_ACCESO); }
  public UnidadLexica unidadExclamacion() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.EXCLAMACION); }
  public UnidadLexica unidadAnd() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OP_AND); }
  public UnidadLexica unidadOr() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OP_OR); }
  public UnidadLexica unidadDollar() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DOLLAR); }
  public UnidadLexica unidadPuntoComa() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OP_PUNTO_COMA); }
  public UnidadLexica unidadArroba() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OP_ARROBA); }
  public UnidadLexica unidadInterrogacion() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OP_INTERROGACION); }
  public UnidadLexica unidadDosPuntos() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OP_DOS_PUNTOS); }
  
  public UnidadLexica unidadMayorIgual() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAYOR_IGUAL); }
  public UnidadLexica unidadMenorIgual() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENOR_IGUAL); }
  public UnidadLexica unidadIgualIgual() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IGUAL_IGUAL); }
  public UnidadLexica unidadDistinto() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DISTINTO); }
  
  public UnidadLexica unidadSumaPref() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.SUMA_PREF); }
  public UnidadLexica unidadRestaPref() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.RESTA_PREF); }
  public UnidadLexica unidadMultiPref() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MULT_PREF); }
  public UnidadLexica unidadDivEnteraPref() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DIV_ENT_PREF); }
  public UnidadLexica unidadDivRealPref() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DIV_REAL_PREF); }
  public UnidadLexica unidadModPref() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MOD_PREF); }
  public UnidadLexica unidadNumEntero() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NUM_ENTERO, alex.lexema()); }
  public UnidadLexica unidadNumReal() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NUM_REAL, alex.lexema()); }
  
  public UnidadLexica unidadParentesisAp() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_AP); }
  public UnidadLexica unidadParentesisCierre() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_CIE); }
  public UnidadLexica unidadLlaveAp() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LL_AP); }
  public UnidadLexica unidadLlaveCierre() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LL_CIE); }
  public UnidadLexica unidadCorcheteAp() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.C_AP); }
  public UnidadLexica unidadCorcheteCierre() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.C_CIE); }
  public UnidadLexica unidadComa() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.COMA); }
  
  public UnidadLexica unidadEnt() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ENT, alex.lexema()); }
  public UnidadLexica unidadReal() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.REAL, alex.lexema()); }
  public UnidadLexica unidadBool() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.BOOL, alex.lexema()); }
  public UnidadLexica unidadV() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.V, alex.lexema()); }
  public UnidadLexica unidadF() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.F, alex.lexema()); }
  public UnidadLexica unidadPunt() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PUNT); }
  public UnidadLexica unidadArray() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ARRAY); }
  public UnidadLexica unidadIf() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IF); }
  public UnidadLexica unidadElse() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ELSE); }
  public UnidadLexica unidadElsif() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ELSIF); }
  public UnidadLexica unidadFunc() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FUNC); }
  public UnidadLexica unidadFor() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FOR); }
  public UnidadLexica unidadWhile() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.WHILE); }
  public UnidadLexica unidadRepeat() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.REPEAT); }
  public UnidadLexica unidadUntil() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.UNTIL); }
  public UnidadLexica unidadStruct() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.STRUCT); }
  public UnidadLexica unidadMain() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAIN); }
  public UnidadLexica unidadVoid() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.VOID); }
  public UnidadLexica unidadReturn() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.RETURN); }
  
  public UnidadLexica unidadInEnt() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IN_ENT, alex.lexema()); }
  public UnidadLexica unidadOutEnt() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OUT_ENT, alex.lexema()); }
  public UnidadLexica unidadInReal() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IN_REAL, alex.lexema()); }
  public UnidadLexica unidadOutReal() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OUT_REAL, alex.lexema()); }
  public UnidadLexica unidadInBool() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IN_BOOL, alex.lexema()); }
  public UnidadLexica unidadOutBool() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OUT_BOOL, alex.lexema()); }
  
  public UnidadLexica unidadComo() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.COMO); }
  public UnidadLexica unidadAlias() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ALIAS); }
  public UnidadLexica unidadNew() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NEW); }
  public UnidadLexica unidadTo() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TO); }
  public UnidadLexica unidadConst() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CONST); }
  
  public UnidadLexica unidadId() { return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IDEN, alex.lexema()); }
  
  public void error() {
    System.err.println("***" + alex.fila() + ", " + alex.columna() + " Caracter inesperado: " + alex.lexema());
  }
  
}
