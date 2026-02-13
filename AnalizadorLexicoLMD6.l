package alex;

import errors.GestionErroresTiny;

%%
%cup
%line
%column
%class AnalizadorLexicoTiny
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErroresTiny errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErroresTiny errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = ({digitoPositivo}{digito}* | 0)
parteDecimal = ({digito}*{digitoPositivo} | 0)
numeroEntero = {parteEntera}
numeroReal = {parteEntera}\,{parteDecimal}



separador = [ \t\r\b\n]
comentario = \#[^\n]*
comentarioMultiLinea = \/[\#][^\#]*\#



operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivisionEntera = \/
operadorDivisionReal = {operadorDivisionEntera}{operadorMultiplicacion}
operadorModulo = \mod
operadorMayor = \>
operadorMenor = \<
operadorIgual = \=
operadorAcceso = \.
operadorExclamacion = \!
operadorAnd = \AND
operadorOr = \OR
operadorDollar = \$
operadorPuntoComa = \;
operadorDosPuntos = \:
operadorArroba = \@
operadorInterrogacion = \?

mayorIgual = {operadorMayor}{operadorIgual}
menorIgual = {operadorMenor}{operadorIgual}
igualIgual = {operadorIgual}{operadorIgual}
distinto = {operadorExclamacion}{operadorIgual}

sumaPrefijo = {operadorSuma}{operadorIgual}
restaPrefijo = {operadorResta}{operadorIgual}
multiPrefijo = {operadorMultiplicacion}{operadorIgual}
divEnteraPrefijo = {operadorDivisionEntera}{operadorIgual}
modPrefijo = {operadorModulo}{operadorIgual}
divRealPrefijo = {operadorDivisionReal}{operadorIgual}


parentesisApertura = \(
parentesisCierre = \)
llaveApertura = \{
llaveCierre = \}
corcheteApertura = \[
corcheteCierre = \]
coma  = \,

ent = ent
real = real
bool = bool
V = V
F = F
punt = punt
array = array
if = if
else = else
elsif = elsif
func = func
for = for
to = to
while = while 
repeat = repeat
until = until
struct = struct
main = main
void = void
return = return
new = new

inEnt = inEnt
outEnt = outEnt
inReal = inReal
outReal = outReal
inBool = inBool
outBool = outBool


alias = alias
como = como
const = const

identificador = {letra}({letra}|{digito})*

%%
{separador}               {}
{comentario}              {}
{comentarioMultiLinea}    {}
{operadorSuma}            {return ops.unidadSuma();}
{operadorResta}           {return ops.unidadResta();}
{operadorMultiplicacion}  {return ops.unidadMul();}
{operadorDivisionEntera}  {return ops.unidadDivEnt();}
{operadorDivisionReal}    {return ops.unidadDivReal();}
{operadorModulo}          {return ops.unidadMod();}
{operadorMayor}           {return ops.unidadMayor();}
{operadorMenor}           {return ops.unidadMenor();}
{operadorIgual}           {return ops.unidadIgual();}
{operadorAcceso}          {return ops.unidadAcceso();}
{operadorExclamacion}     {return ops.unidadExclamacion();}
{operadorAnd}             {return ops.unidadAnd();}
{operadorOr}              {return ops.unidadOr();}
{operadorDollar}          {return ops.unidadDollar();}
{operadorPuntoComa}       {return ops.unidadPuntoComa();}
{operadorArroba}          {return ops.unidadArroba();}
{operadorInterrogacion}          {return ops.unidadInterrogacion();}

{operadorDosPuntos}          {return ops.unidadDosPuntos();}
{numeroEntero}       {return ops.unidadNumEntero();}
{numeroReal}         {return ops.unidadNumReal();}

{mayorIgual}               {return ops.unidadMayorIgual();}
{menorIgual}               {return ops.unidadMenorIgual();}
{igualIgual}               {return ops.unidadIgualIgual();}
{distinto}                 {return ops.unidadDistinto();}

{sumaPrefijo}		  {return ops.unidadSumaPref();}
{restaPrefijo}		  {return ops.unidadRestaPref();}
{multiPrefijo}		  {return ops.unidadMultiPref();}
{divEnteraPrefijo}         {return ops.unidadDivEnteraPref();}
{divRealPrefijo}		  {return ops.unidadDivRealPref();}
{modPrefijo}		  {return ops.unidadModPref();}

{parentesisApertura}        {return ops.unidadParentesisAp();}
{parentesisCierre}          {return ops.unidadParentesisCierre();}
{llaveApertura}             {return ops.unidadLlaveAp();}
{llaveCierre}               {return ops.unidadLlaveCierre();}
{corcheteApertura}          {return ops.unidadCorcheteAp();}
{corcheteCierre}            {return ops.unidadCorcheteCierre();}
{coma}		           {return ops.unidadComa();}

{ent}		           {return ops.unidadEnt();}
{real}		           {return ops.unidadReal();}
{bool}		           {return ops.unidadBool();}
{V}		           {return ops.unidadV();}
{F}		           {return ops.unidadF();}
{punt}		           {return ops.unidadPunt();}
{array}		           {return ops.unidadArray();}
{if}		           {return ops.unidadIf();}
{else}		           {return ops.unidadElse();}
{elsif}		           {return ops.unidadElsif();}
{func}		           {return ops.unidadFunc();}
{for}		           {return ops.unidadFor();}
{to}		           {return ops.unidadTo();}
{while}		           {return ops.unidadWhile();}
{repeat}		           {return ops.unidadRepeat();}
{until}		           {return ops.unidadUntil();}
{struct}		           {return ops.unidadStruct();}
{main}		           {return ops.unidadMain();}
{void}		           {return ops.unidadVoid();}
{return}		           {return ops.unidadReturn();}
{new}		           {return ops.unidadNew();}

{inEnt}		           {return ops.unidadInEnt();}
{outEnt}		           {return ops.unidadOutEnt();}
{inReal}		           {return ops.unidadInReal();}
{outReal}		           {return ops.unidadOutReal();}
{inBool}		           {return ops.unidadInBool();}
{outBool}		   {return ops.unidadOutBool();}

{como}		           {return ops.unidadComo();}
{alias}		           {return ops.unidadAlias();}
{const}		           {return ops.unidadConst();}

{identificador}           {return ops.unidadId();}
[^]                       {ops.error();}  


