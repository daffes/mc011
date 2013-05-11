package x86.stm;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MSEQ {

    public static void doit(SEQ stm) {
	Codegen.doit(stm.getLeft());
	Codegen.doit(stm.getRight());
    }

}
