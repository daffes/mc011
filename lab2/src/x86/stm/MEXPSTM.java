package x86.stm;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MEXPSTM {

    public static void doit(EXPSTM stm) {
	System.out.print("EXPSTM");
	System.out.print(" (expr=");
	System.out.print(stm.getExpression());
	System.out.println(")");

	Codegen.doit(stm.getExpression());
    }

}
