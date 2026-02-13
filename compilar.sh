java -cp cup.jar java_cup.Main -parser ConstructorASTExp -symbols ClaseLexica -nopositions ConstructorAST.cup       

mv ClaseLexica.java constructorast/ClaseLexica.java

mv ConstructorASTExp.java constructorast/ConstructorASTExp.java

java -cp jflex.jar jflex.Main AnalizadorLexicoLMD6.l

mv AnalizadorLexicoTiny.java alex/AnalizadorLexicoTiny.java

javac -cp "cup.jar:." */*.java

