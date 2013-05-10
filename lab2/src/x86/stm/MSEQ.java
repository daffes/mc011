package x86.stm;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MSEQ {

    public static void doit(SEQ stm) {
	System.out.print("SEQ");
	System.out.print(" (left=");
	System.out.print(stm.getLeft());
	System.out.print(" / right=");
	System.out.print(stm.getRight());
	System.out.println(")");

	Codegen.doit(stm.getLeft());
	Codegen.doit(stm.getRight());
    }

}
