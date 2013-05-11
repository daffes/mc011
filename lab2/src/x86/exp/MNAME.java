package x86.exp;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MNAME {

    public static Temp doit(NAME stm) {
	Temp t = new Temp();
	Instr i = new assem.OPER("mov `d0, " + stm.getLabel(), new List<Temp>(t), null);
	Codegen.emit(i);
	return t;
    }

}
