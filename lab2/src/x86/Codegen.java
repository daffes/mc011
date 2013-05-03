package x86;

import util.List;

import tree.Stm;
import assem.Instr;

import tree.*;
import temp.Label;

import errors.ErrorEchoer;

public class Codegen{
    public Codegen(Frame f){
	System.out.print("Codegen(Frame f = ");
	System.out.print(f.name);
	System.out.println(")");
    }

    public List<Instr> codegen(List<Stm> body){
	System.out.println("codegen got called");
	// System.out.println(body.size());
	for (Stm stm : body) {
	    System.out.print("< ");
	    System.out.print(stm);
	    System.out.print(" > ");
	    if (stm instanceof MOVE) {
		System.out.print("This was a MOVE");
		System.out.print(" (source=");
		System.out.print(((MOVE) stm).getSource());
		System.out.print(") (destination=");
		System.out.print(((MOVE) stm).getDestination());
		System.out.println(")");
	    } else if (stm instanceof LABEL) {
		System.out.print("This was a LABEL");
		System.out.print(" (label=");
		System.out.print(((LABEL) stm).getLabel());
		System.out.println(")");
	    } else if (stm instanceof JUMP) {
		System.out.print("This was a JUMP");
		System.out.print(" (exp=");
		System.out.print(((JUMP) stm).getExpression());
		System.out.print(") (targets=");
		for (Label lbl : ((JUMP) stm).getTargets()) {
		    System.out.print(" | ");
		    System.out.print(lbl);
		}
		System.out.println(")");
	    } else {
		System.out.println();
	    }
	}
        return null;
    }
}
