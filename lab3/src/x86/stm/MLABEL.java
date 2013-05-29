package x86.stm;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MLABEL {

    public static void doit(LABEL stm) {
	Instr i = new assem.LABEL(stm.getLabel() + ":", stm.getLabel());
	Codegen.emit(i);
    }

}
