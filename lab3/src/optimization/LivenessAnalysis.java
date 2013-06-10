package optimization;

import java.util.Hashtable;
import java.util.Set;
import java.util.HashSet;
import java.util.Enumeration;

import flow_graph.FlowGraph;
import graph.Graph;
import graph.Node;
import temp.Temp;

import util.List;

public class LivenessAnalysis {

	private FlowGraph flowGraph;
	// private Graph graph;  

    private Hashtable<Node, Set<Temp>> in, out, in_, out_;
    private int niter;

	public LivenessAnalysis (FlowGraph g) {

	    this.flowGraph = g;

	    System.out.println("LivenessAnalysis");
	    this.flowGraph.show(System.out);
		// insira seu codigo aqui

	    in = new Hashtable<Node, Set<Temp>>();
	    out = new Hashtable<Node, Set<Temp>>();
	    niter = 0;

	    do {
	    	niter++;
	    	in_ = (Hashtable<Node, Set<Temp>>) in.clone();
	    	out_ = (Hashtable<Node, Set<Temp>>) out.clone();

	    	for (Node n : this.flowGraph.nodes()) {
		    Set<Temp> sin;
		    if (out.containsKey(n))
			sin = new HashSet<Temp>(out.get(n));
		    else sin = new HashSet<Temp>();
		    if (this.flowGraph.def(n) != null)
			for (Temp p : this.flowGraph.def(n))
			    sin.remove(p);
		    if (this.flowGraph.use(n) != null)
			for (Temp p : this.flowGraph.use(n))
			    sin.add(p);
	    	    in.put(n, sin);

	    	    Set<Temp> sout = new HashSet<Temp>();
		    if (n.succ() != null)
			for (Node s : n.succ())
			    if (in.containsKey(s))
				sout.addAll(in.get(s));
		    out.put(n, sout);
	    	}
	    } while(!(in_.equals(in) && out_.equals(out)));

	    System.out.println("Somehow it ended (niter=" + niter + ")");

	    // System.out.println("out");
	    // Enumeration e = out.keys();
	    // while (e.hasMoreElements()) {
	    // 	Node n = (Node) e.nextElement();
	    // 	System.out.println(n);
	    // 	System.out.print(">");
	    // 	for (Temp t : out.get(n)) {
	    // 	    System.out.print(" ");
	    // 	    System.out.print(t);
	    // 	}
	    // 	System.out.println();
	    // }

	}
}
