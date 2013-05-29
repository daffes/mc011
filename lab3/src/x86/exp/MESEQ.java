package x86.exp;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MESEQ {

    public static Temp doit(ESEQ stm) {
	Codegen.doit(stm.getStatement());
	return Codegen.doit(stm.getExpression());
    }

}
