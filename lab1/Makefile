INPUT?=misc/inputs/exemplo.npl
OUTPUT?=html/output.html
PATHW?=/usr/local/lib
OUTPUT_PATH=$(dir $(OUTPUT))

compile: 
	cd src; java -jar $(PATHW)/antlr-4.0-complete.jar Jornal.g 
	javac -cp $(PATHW)/antlr-4.0-complete.jar src/Jornal*.java

run:	
	+@[ -d $(dir $(OUTPUT)) ] || mkdir -p $(dir $(OUTPUT))
	cp -r misc/basehtml/* $(dir $(OUTPUT))
	java -cp "$(PATHW)/antlr-4.0-complete.jar:./src/" JornalDemo $(INPUT) $(OUTPUT)

clean:
	ls src/* | grep -v Jornal.g | grep -v JornalDemo.java | xargs -I{} rm -v {}


