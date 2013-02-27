INPUT?=inputs/tuto.npl
OUTPUT?=inputs/exemplo.html

compile: src/JornalLexer.java

src/JornalLexer.java: src/Jornal.g4
	java -jar /usr/local/lib/antlr-4.0-complete.jar src/Jornal.g4
	javac src/Jornal*.java

run:
	cd src; java org.antlr.v4.runtime.misc.TestRig Jornal r ../$(INPUT)

clean:
	ls src/* | grep -v Jornal.g4 | xargs rm
