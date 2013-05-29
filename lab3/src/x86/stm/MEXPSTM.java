package x86.stm;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MEXPSTM {

    public static void doit(EXPSTM stm) {
	Codegen.doit(stm.getExpression());
    }

}
