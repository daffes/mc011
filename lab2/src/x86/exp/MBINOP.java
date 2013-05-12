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

	Instr icopy = new assem.OPER("mov `d0, `s0",
				     new List<Temp>(t),
				     new List<Temp>(left));
	Codegen.emit(icopy);

	Instr i;
	String op = null;
	switch (stm.getOperation()) {
	case BINOP.PLUS:    op = "add";  break;
	case BINOP.MINUS:   op = "sub";  break;
	case BINOP.TIMES:   op = "imul"; break;
	case BINOP.DIV:     op = "idiv"; break;
	case BINOP.AND:     op = "and";  break;
	case BINOP.OR:      op = "or";   break;
	case BINOP.XOR:     op = "xor";  break;
	case BINOP.LSHIFT:  op = "shl";  break;
	case BINOP.RSHIFT:  op = "shr";  break;
	case BINOP.ARSHIFT: op = "sar";  break;
	}

	// TIMES and DIV won't work (yet)

	if (stm.getRight() instanceof CONST) {
	    CONST c = (CONST) stm.getRight();
	    i = new assem.OPER(op + " `d0, " + c.getValue(),
			       new List<Temp>(t),
			       new List<Temp>(t));
	} else {
	    Temp right = Codegen.doit(stm.getRight());
	    i = new assem.OPER(op + " `d0, `s1",
			       new List<Temp>(t),
			       new List<Temp>(t, right));
	}
	Codegen.emit(i);

	return t;
    }

}
