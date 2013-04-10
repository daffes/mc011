INPUT?=test/Teste.txt
OUTPUT?=out.txt

all: clean compile
	@echo ""

clean:
	rm -f src/x86/*.class

compile:
	javac -classpath lib/etapa2.jar src/x86/Codegen.java 

run:
	java -classpath src:lib/etapa2.jar main/Main $(INPUT) $(OUTPUT)

compile_assembly:
	nasm -felf32 $(INPUT) -o saida.o
	gcc saida.o lib/runtime.o -o $(OUTPUT)
