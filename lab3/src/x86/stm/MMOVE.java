package x86.stm;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MMOVE {

    private static Boolean matchMem(Exp exp) {
	return (exp instanceof BINOP &&
		(((BINOP) exp).getOperation() == BINOP.PLUS ||
		 ((BINOP) exp).getOperation() == BINOP.MINUS) && 
		((BINOP) exp).getRight() instanceof CONST);
    }

    public static void doit(MOVE stm) {
	Instr i;
	String si = "mov";
	List<Temp> dst = new List<Temp>();
	List<Temp> src = new List<Temp>();
	int dst_sz = 0, src_sz = 0;
	Temp rj, ri;
	
	/*  [rj+c] <- ri        [rj] <- ri
	 *
	 *      MOVE      MOVE      MOVE
	 *      /  \      /  \      /  \
	 *    MEM       MEM       MEM
	 *     |         |         |
	 *     +         -
	 *    / \       / \
	 *      CONST     CONST
	 */
	if (stm.getDestination() instanceof MEM) {
	    Exp srcExp = ((MEM) stm.getDestination()).getExpression();

	    if (matchMem(srcExp)) {
		rj = Codegen.doit(((BINOP) srcExp).getLeft());
		CONST c = (CONST) ((BINOP) srcExp).getRight();

		if (((BINOP) srcExp).getOperation() == BINOP.PLUS)
		    si = si + " [`s" + src_sz + " + " + c.getValue() + "]";
		else
		    si = si + " [`s" + src_sz + " - " + c.getValue() + "]";
		src.append(rj);
		src_sz++;
	    } else {
		rj = Codegen.doit(srcExp);

		si = si + " [`s" + src_sz + "]";
		src.append(rj);
		src_sz++;
	    }
	} else {
	    rj = Codegen.doit(stm.getDestination());

	    si = si + " `d" + dst_sz;
	    dst.append(rj);
	    dst_sz++;
	}

	/*  rj <- [ri+c]         rj <- [ri]  rj <- c
	 *
	 *      MOVE      MOVE      MOVE      MOVE
	 *      /  \      /  \      /  \      /  \
	 *         MEM       MEM       MEM       CONST
	 *          |         |         |
	 *          +         -
	 *         / \       / \
	 *           CONST     CONST
	 */
	if (stm.getSource() instanceof MEM && 
	    !(stm.getDestination() instanceof MEM)) {
	    Exp srcExp = ((MEM) stm.getSource()).getExpression();

	    if (matchMem(srcExp)) {
		ri = Codegen.doit(((BINOP) srcExp).getLeft());
		CONST c = (CONST) ((BINOP) srcExp).getRight();

		if (((BINOP) srcExp).getOperation() == BINOP.PLUS)
		    si = si + ", [`s" + src_sz + " + " + c.getValue() + "]";
		else
		    si = si + ", [`s" + src_sz + " - " + c.getValue() + "]";
		src.append(ri);
		src_sz++;
	    } else {
		ri = Codegen.doit(srcExp);

		si = si + ", [`s" + src_sz + "]";
		src.append(ri);
		src_sz++;
	    }
	} else if (stm.getSource() instanceof CONST && 
		   !(stm.getDestination() instanceof MEM)) {
	    CONST c = (CONST) stm.getSource();

	    si = si + ", " + c.getValue();
	} else {
	    ri = Codegen.doit(stm.getSource());

	    si = si + ", `s" + src_sz;
	    src.append(ri);
	    src_sz++;
	}

	if (dst_sz == 0) dst = null;
	if (src_sz == 0) src = null;

	i = new assem.OPER(si, dst, src);
	Codegen.emit(i);
    }

}
