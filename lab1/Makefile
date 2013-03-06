INPUT?=inputs/tuto.npl
OUTPUT?=inputs/exemplo.html

compile: antlr-generated/JornalLexer.java

antlr-generated/JornalLexer.java: src/Jornal.g
	cd src; java -jar /usr/local/lib/antlr-4.0-complete.jar Jornal.g -o ../antlr-generated
	javac antlr-generated/Jornal*.java

run: compile
	cd antlr-generated; java org.antlr.v4.runtime.misc.TestRig Jornal r ../$(INPUT)

clean:
	rm antlr-generated/*

