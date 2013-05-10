package x86.stm;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

public class MLABEL {

    public static void doit(LABEL stm) {
	System.out.print("LABEL");
	System.out.print(" (label=");
	System.out.print(stm.getLabel());
	System.out.println(")");
    }

}
