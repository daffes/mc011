package optimization;

import java.util.Set;
import java.util.HashSet;
import temp.Temp;
import util.List;
import assem.Instr;
import assem.OPER;
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
	public List<Instr> optimize(List<Instr> l) {
		cfg = new AssemFlowGraph(l);
		/* executa a analise de longevidade */
		dfa = new LivenessAnalysis(cfg);

		// insira seu codigo aqui
		List<Instr> k = new List<Instr>();

		for (Node n : dfa.flowGraph.nodes()) {
		    if (dfa.flowGraph.instr(n) instanceof OPER) {
			OPER op = (OPER) dfa.flowGraph.instr(n);
			if (op.assem.startsWith("mov `d0, [") ||
			    op.assem.startsWith("add") ||
			    op.assem.startsWith("sub") ||
			    op.assem.startsWith("imul") ||
			    op.assem.startsWith("idiv") ||
			    op.assem.startsWith("and") ||
			    op.assem.startsWith("or") ||
			    op.assem.startsWith("xor") ||
			    op.assem.startsWith("shl") ||
			    op.assem.startsWith("shr") ||
			    op.assem.startsWith("sar")) {
			    Set<Temp> opDef = new HashSet<Temp>();
			    if (op.def() != null)
				for (Temp t : op.def())
				    opDef.add(t);
			    opDef.retainAll(dfa.out.get(n));
			    if (opDef.size() == 0) {
				System.out.println("- " + dfa.flowGraph.instr(n).debug());
				continue;
			    }
			}
		    }

		    k.append(dfa.flowGraph.instr(n));
		    // System.out.println("+ " + dfa.flowGraph.instr(n).debug());
		}

		return k;
	}
}
 
