package optimization;

import java.util.Hashtable;
import java.util.Set;
import java.util.HashSet;
import java.util.Enumeration;

import flow_graph.AssemFlowGraph;
import graph.Graph;
import graph.Node;
import temp.Temp;

import util.List;

public class LivenessAnalysis {

    public AssemFlowGraph flowGraph;
    
    public Hashtable<Node, Set<Temp>> in, out;
    private Hashtable<Node, Set<Temp>> in_, out_;
    private int niter;
    
    public LivenessAnalysis (AssemFlowGraph g) {
        
        this.flowGraph = g;
        
        in = new Hashtable<Node, Set<Temp>>();
        out = new Hashtable<Node, Set<Temp>>();
        niter = 0;
        
        do {
            niter++;
            in_ = (Hashtable<Node, Set<Temp>>) in.clone();
            out_ = (Hashtable<Node, Set<Temp>>) out.clone();
            
            for (Node n : this.flowGraph.nodes()) {
                Set<Temp> sin = new HashSet<Temp>();
                if (out.containsKey(n)) {
                    sin.addAll(out.get(n));
                    if (this.flowGraph.def(n) != null)
                        for (Temp p : this.flowGraph.def(n))
                            sin.remove(p);
                }
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
    }
}
