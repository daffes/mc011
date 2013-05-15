package x86.exp;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MCALL {

    public static Temp doit(CALL stm) {

        List<Exp> args = stm.getArguments();
        int numArgs = args.size();

	Instr iargcp, icall, isp, irv;

	// push arguments into the stack (in reverse order)
	for (int i=numArgs-1 ; i>=0 ; i--) {
	    Exp e = (Exp) args.get(i);
	    if (e instanceof CONST) {
		iargcp = new assem.OPER("push " + ((CONST) e).getValue(),
					new List<Temp>(Codegen.frame.esp),
					new List<Temp>(Codegen.frame.esp));
	    } else if (e instanceof MEM) {
		Temp t = Codegen.doit(((MEM) e).getExpression());
		iargcp = new assem.OPER("push [`s0]",
					new List<Temp>(Codegen.frame.esp),
					new List<Temp>(t, Codegen.frame.esp));
	    } else {
		Temp t = Codegen.doit(e);
		iargcp = new assem.OPER("push `s0",
					new List<Temp>(Codegen.frame.esp),
					new List<Temp>(t, Codegen.frame.esp));
	    }
	    Codegen.emit(iargcp);
	}

        if(stm.getCallable() instanceof NAME) {
	    icall = new assem.OPER("call " + ((NAME) stm.getCallable()).getLabel(),
				   Codegen.frame.calleeDefs(),
				   null);
        } else if(stm.getCallable() instanceof MEM) {
	    MEM mcall = (MEM) stm.getCallable();
	    Temp tlabel = Codegen.doit(mcall.getExpression());
	    icall = new assem.OPER("call [`s0]",
				   Codegen.frame.calleeDefs(),
				   new List<Temp>(tlabel));
	} else {
	    Temp tlabel = Codegen.doit(stm.getCallable());
	    icall = new assem.OPER("call `s0",
				   Codegen.frame.calleeDefs(),
				   new List<Temp>(tlabel));
	}
	Codegen.emit(icall);

	// update the stack pointer
	isp = new assem.OPER("add `d0, " + numArgs * Codegen.frame.wordsize(),
			     new List<Temp>(Codegen.frame.esp),
			     new List<Temp>(Codegen.frame.esp));
        Codegen.emit(isp);

	// fetch return value
	Temp rv = new Temp();
	irv = new assem.MOVE(rv, Codegen.frame.RV());
        Codegen.emit(irv);

        return rv;
    }

}
