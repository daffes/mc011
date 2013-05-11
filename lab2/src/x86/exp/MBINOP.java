package x86.exp;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MBINOP {

    public static Temp doit(BINOP stm) {
	Temp t = new Temp();
	Temp left = Codegen.doit(stm.getLeft());
	Temp right = Codegen.doit(stm.getRight());

	Instr icopy = new assem.OPER("mov `d0, `s0",
				     new List<Temp>(t),
				     new List<Temp>(left));
	Codegen.emit(icopy);

	Instr i;
	// switch (stm.getOperation()) {
	// case BINOP.PLUS:
	    i = new assem.OPER("add `d0, `s1",
			       new List<Temp>(t),
			       new List<Temp>(t, left));
	//     break;
	// }
	Codegen.emit(i);

	return t;
    }

}
