package x86;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import java.io.*;

import x86.stm.*;
import x86.exp.*;

public class Codegen {
    public static Frame frame;
    private static List<Instr> ilist;
    PrintIR p;

    public static void emit(assem.Instr i) {
	if (ilist == null) ilist = new List<Instr>(i);
	else ilist.append(i);
    }

    public Codegen(Frame f) {
	Codegen.frame = f;
	Codegen.ilist = null;
    }

    public static void doit(Stm stm) {
	if (stm instanceof CJUMP) MCJUMP.doit((CJUMP) stm);
	else if (stm instanceof EXPSTM) MEXPSTM.doit((EXPSTM) stm);
	else if (stm instanceof JUMP) MJUMP.doit((JUMP) stm);
	else if (stm instanceof LABEL) MLABEL.doit((LABEL) stm);
	else if (stm instanceof MOVE) MMOVE.doit((MOVE) stm);
	else if (stm instanceof SEQ) MSEQ.doit((SEQ) stm);
	else System.out.println("hu-ho");
    }

    public static Temp doit(Exp exp) {
	if (exp instanceof BINOP) return MBINOP.doit((BINOP) exp);
	else if (exp instanceof CALL) return MCALL.doit((CALL) exp);
	else if (exp instanceof CONST) return MCONST.doit((CONST) exp);
	else if (exp instanceof ESEQ) return MESEQ.doit((ESEQ) exp);
	else if (exp instanceof MEM) return MMEM.doit((MEM) exp);
	else if (exp instanceof NAME) return MNAME.doit((NAME) exp);
	else if (exp instanceof TEMP) return MTEMP.doit((TEMP) exp);
	else {
	    System.out.println("hu-ho");
	    return null;
	}
    }

    public List<Instr> codegen(List<Stm> body) {
	this.p = new PrintIR(new PrintStream(new FileOutputStream(FileDescriptor.out)));

	for (Stm stm : body) {
	    doit(stm);
	}
        return Codegen.ilist;
    }
}
