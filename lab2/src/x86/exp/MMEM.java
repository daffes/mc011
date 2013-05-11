package x86.exp;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MMEM {

    public static Temp doit(MEM stm) {
	Temp t = new Temp();

	Exp exp = stm.getExpression();
	Instr i;

	/*  t <- [ri+c]
	 *
	 *    MEM       MEM
	 *     |         |
	 *     +         -
	 *    / \       / \
	 *      CONST     CONST
	 */
	if (exp instanceof BINOP) {
	    BINOP bexp = (BINOP) exp;

	    if (bexp.getOperation() == BINOP.PLUS &&
		bexp.getRight() instanceof CONST) {
		CONST c = (CONST) bexp.getRight();
		Temp ri = Codegen.doit(bexp.getLeft());
		i = new assem.OPER("mov `d0, [`s0 + " + c.getValue() + "]",
				   new List<Temp>(t),
				   new List<Temp>(ri));
		Codegen.emit(i);
		return t;
	    } else if (bexp.getOperation() == BINOP.MINUS &&
		       bexp.getRight() instanceof CONST) {
		CONST c = (CONST) bexp.getRight();
		Temp ri = Codegen.doit(bexp.getLeft());
		i = new assem.OPER("mov `d0, [`s0 - " + c.getValue() + "]",
				   new List<Temp>(t),
				   new List<Temp>(ri));
		Codegen.emit(i);
		return t;
	    }
	}

	/*  t <- [ri]
	 *
	 *    MEM
	 *     |
	 */
	Temp ri = Codegen.doit(exp);
	i = new assem.OPER("mov `d0, [`s0]",
			   new List<Temp>(t),
			   new List<Temp>(ri));
	Codegen.emit(i);
	return t;
    }

}
