package x86.stm;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MMOVE {

    public static void doit(MOVE stm) {
	System.out.print("MOVE");
	System.out.print(" (source=");
	System.out.print(stm.getSource());
	System.out.print(") (destination=");
	System.out.print(stm.getDestination());
	System.out.println(")");

	Instr ins;

	/*  [rj+c] <- ri        [rj] <- ri
	 *
	 *      MOVE      MOVE      MOVE
	 *      /  \      /  \      /  \
	 *    MEM       MEM       MEM
	 *     |         |         |
	 *     +         -
	 *    / \       / \
	 *      CONST     CONST
	 */
	if (stm.getSource() instanceof MEM) {
	    Exp srcExp = ((MEM) stm.getSource()).getExpression();

	    if (srcExp instanceof BINOP &&
		((BINOP) srcExp).getOperation() == BINOP.PLUS && 
		((BINOP) srcExp).getRight() instanceof CONST) {
		Temp rj = Codegen.doit(((BINOP) srcExp).getLeft());
		CONST c = (CONST) ((BINOP) srcExp).getRight();

		// falta tratar o ri
		Temp ri = Codegen.doit(stm.getDestination());

		ins = new assem.OPER("mov [`s0 + " + c.getValue() + "], `s1", null, new List<Temp>(rj, new List<Temp>(ri, null)));
	    } else {
		Temp rj = Codegen.doit(srcExp);

		// falta tratar o ri
		Temp ri = Codegen.doit(stm.getDestination());

		ins = new assem.OPER("mov [`s0], `s1", null, new List<Temp>(rj, new List<Temp>(ri, null)));
	    }
	} else {
	    Temp rj = Codegen.doit(stm.getSource());

	    // falta tratar o ri
	    Temp ri = Codegen.doit(stm.getDestination());

	    ins = new assem.MOVE(rj, ri);
	}

	// System.out.println(ins.debug());
	System.out.println(ins);
    }

}
