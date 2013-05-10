package x86.stm;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MJUMP {

    public static void doit(JUMP stm) {
	System.out.print("JUMP");
	System.out.print(" (exp=");
	System.out.print(stm.getExpression());
	System.out.print(") (targets=");
	System.out.print(stm.getTargets());
	System.out.println(")");

	Instr ins;

	if (stm.getExpression() instanceof NAME) {
	    Label l = ((NAME) stm.getExpression()).getLabel();
	    ins = new assem.OPER("jmp `j0", new List<Label>(l, null));
	} else {
	    Temp rj = Codegen.doit(stm.getExpression());
	    ins = new assem.OPER("jmp `s0",
				 null,
				 new List<Temp>(rj, null),
				 stm.getTargets());
	}

	System.out.println(ins);
    }

}
