package x86.stm;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MJUMP {

    public static void doit(JUMP stm) {
	Instr i;

	if (stm.getExpression() instanceof NAME) {
	    Label l = ((NAME) stm.getExpression()).getLabel();
	    i = new assem.OPER("jmp `j0", new List<Label>(l));
	} else {
	    Temp rj = Codegen.doit(stm.getExpression());
	    i = new assem.OPER("jmp `s0",
				 null,
				 new List<Temp>(rj),
				 stm.getTargets());
	}

	Codegen.emit(i);
    }

}
