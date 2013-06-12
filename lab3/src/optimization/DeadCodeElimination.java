package optimization;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import temp.Temp;
import util.List;
import assem.Instr;
import assem.OPER;
import assem.MOVE;
import flow_graph.AssemFlowGraph;
import graph.Node;

public class DeadCodeElimination {

	/* armazena a análise de longevidade */
	private LivenessAnalysis dfa;
	private AssemFlowGraph cfg;

	private Boolean compareListInstr(List<Instr> k, List<Instr> l) {
	    Iterator kit = k.iterator();
	    for (Instr lop : l) {
		if (!kit.hasNext()) return false;
		Instr kop = (Instr) kit.next();
		if (!lop.debug().equals(kop.debug())) // kinda ugly -- replace?
		    return false;
	    }
	    if (kit.hasNext()) return false;
	    return true;
	}

	/*
	 * realiza a otimizacao "dead code elimination" utilizando
	 * a analise de fluxo de dados "liveness analysis"
	 */
	public List<Instr> optimize(List<Instr> l) {
		int nopt = 0;
		List<Instr> k = null;

		do {

		    if (k != null) l = k;
		    k = new List<Instr>();

		    cfg = new AssemFlowGraph(l);
		    /* executa a analise de longevidade */
		    dfa = new LivenessAnalysis(cfg);

		    for (Node n : dfa.flowGraph.nodes()) {
			if (dfa.flowGraph.instr(n) instanceof OPER ||
			    dfa.flowGraph.instr(n) instanceof MOVE) {
			    Instr op = dfa.flowGraph.instr(n);
			    if (op.assem.startsWith("mov `d0,") ||
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
				    OPER x = new OPER("; " + op.assem,
						      op.def(),
						      op.use());
				    k.append(x);
				    // System.out.println("- " + op.debug());
				    nopt++;
				    continue;
				}
			    }
			}

			k.append(dfa.flowGraph.instr(n));
			// System.out.println("+ " + dfa.flowGraph.instr(n).debug());
		    }

		} while (!compareListInstr(k, l));

		System.out.println(nopt + " instructions less!");
		return k;
	}
}
 
