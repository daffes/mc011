package x86.exp;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MESEQ {

    public static Temp doit(ESEQ stm) {
	System.out.print("ESEQ");
	System.out.print(" (stm=");
	System.out.print(stm.getStatement());
	System.out.print(" / expr=");
	System.out.print(stm.getExpression());
	System.out.println(")");

	Codegen.doit(stm.getStatement());
	return Codegen.doit(stm.getExpression());
    }

}
