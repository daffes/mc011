INPUT_RUN?=test/Teste.txt
OUTPUT_RUN?=out.txt
INPUT?=out.txt
OUTPUT?=a.out

all: clean compile run compile_assembly
	@echo ""

clean:
	rm -f src/x86/*.class src/optimization/*.class src/main/*.class out.txt saida.o a.out

compile:
#  Retire o comentario da linha abaixo se vc for usar o seu próprio Codegen
	javac -classpath src:lib/etapa3.jar src/x86/Codegen.java

	javac -classpath src:lib/etapa3.jar src/assem/Instr.java

#	javac -classpath src:lib/etapa3.jar src/optimization/ReachingDefinitions.java
	javac -classpath src:lib/etapa3.jar src/optimization/LivenessAnalysis.java
	javac -classpath src:lib/etapa3.jar src/optimization/DeadCodeElimination.java
	javac -classpath src:lib/etapa3.jar src/main/Main.java

run:
	java -classpath src:lib/etapa3.jar main/Main $(INPUT_RUN) $(OUTPUT_RUN) -O

compile_assembly:
	nasm -felf $(INPUT) -o saida.o
	gcc -m32 saida.o lib/runtime.o -o $(OUTPUT)
