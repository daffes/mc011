package optimization;

import java.util.Set;
import temp.Temp;
import util.List;
import assem.Instr;
import flow_graph.AssemFlowGraph;
import graph.Node;

public class DeadCodeElimination {

	/* armazena a análise de longevidade */
	private LivenessAnalysis dfa;
	private AssemFlowGraph cfg;

	/*
	 * realiza a otimizacao "dead code elimination" utilizando
	 * a analise de fluxo de dados "liveness analysis"
	 */
	public void optimize(List<Instr> l) {
		cfg = new AssemFlowGraph(l);
		/* executa a analise de longevidade */
		dfa = new LivenessAnalysis(cfg);
		// insira seu codigo aqui
	}
}
 
