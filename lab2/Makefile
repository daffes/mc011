INPUT?=test/Teste.txt
OUTPUT?=out.txt

all: clean compile
	@echo ""

clean:
	rm -f src/x86/*.class src/x86/*/*.class src/x86/*~ src/x86/*/*~

compile:
	javac -classpath lib/etapa2.jar src/x86/Codegen.java src/x86/*/*.java

run:
	java -classpath src:lib/etapa2.jar main/Main $(INPUT) $(OUTPUT)

compile_assembly:
	nasm -felf $(INPUT) -o saida.o
	gcc -m32 saida.o lib/runtime.o -o $(OUTPUT)
