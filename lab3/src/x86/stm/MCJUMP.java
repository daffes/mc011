package x86.stm;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MCJUMP {

    public static void doit(CJUMP stm) {
	Temp t = new Temp();
	Temp left = Codegen.doit(stm.getLeft());
	Temp right = Codegen.doit(stm.getRight());

	Instr icmp = new assem.OPER("cmp `s0, `s1",
				    null,
				    new List<Temp>(left, right));
	Codegen.emit(icmp);

	Instr i, ifalse, itrue;
	String op = null;
	switch (stm.getOperation()) {
	case CJUMP.EQ:  op = "je";  break;
	case CJUMP.NE:  op = "jne"; break;
	case CJUMP.LT:  op = "jl";  break;
	case CJUMP.LE:  op = "jle"; break;
	case CJUMP.GT:  op = "jg";  break;
	case CJUMP.GE:  op = "jge"; break;
	case CJUMP.ULT: op = "jb";  break;
	case CJUMP.ULE: op = "jbe"; break;
	case CJUMP.UGT: op = "ja";  break;
	case CJUMP.UGE: op = "jae"; break;
	}

	Label ltrue = new Label();
	i = new assem.OPER(op + " `j0",
			   new List<Label>(ltrue,
					   stm.getLabelTrue(),
					   stm.getLabelFalse()));
	Codegen.emit(i);

	// conditional jump won't work for longer jumps
	ifalse = new assem.OPER("jmp `j0", new List<Label>(stm.getLabelFalse()));
	Codegen.emit(ifalse);

	Codegen.doit(new LABEL(ltrue));

	itrue = new assem.OPER("jmp `j0", new List<Label>(stm.getLabelTrue()));
	Codegen.emit(itrue);
    }

}
