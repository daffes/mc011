INPUT?=inputs/exemplo.npl
OUTPUT?=inputs/exemplo.html
PATH?=/usr/local/lib

compile: antlr-generated/JornalLexer.java2

antlr-generated/JornalLexer.java2:
	cd src; java -jar $(PATH)/antlr-4.0-complete.jar Jornal*.g -o ../antlr-generated
	javac -cp $(PATH)/antlr-4.0-complete.jar antlr-generated/Jornal*.java

#run: compile
#cd antlr-generated; java org.antlr.v4.runtime.misc.TestRig Jornal r ../$(INPUT)

run:
	cd antlr-generated; java JornalDemo ../$(INPUT) > ../output.html

clean:
	cp antlr-generated/JornalDemo.java .;
	rm antlr-generated/*
	mv JornalDemo.java antlr-generated/

