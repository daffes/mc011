package x86.exp;

import util.List;

import assem.Instr;

import tree.*;
import temp.*;

import x86.Codegen;

public class MCALL {

    public static Temp doit(CALL stm) {

        int numArgs = 0;

        if(stm.getArguments() != null)
            numArgs = stm.getArguments().size();

	// Ajustando ponteiro
        Codegen.emit(new assem.OPER("sub esp, "+ numArgs * Codegen.frame.wordsize(), null, null));
        List args = stm.getArguments();

	// Copiando argumentos
	for (int i=0 ; i<numArgs ; i++)
            Codegen.emit(new assem.OPER("mov [`s1+" + i * Codegen.frame.wordsize()+ "], `s0", null, new List<Temp>(Codegen.doit((Exp)args.get(i)), new List<Temp>(Codegen.frame.esp))));
        
        // Imprimindo CALL
        if(stm.getCallable() instanceof NAME)
            Codegen.emit(new assem.OPER("call " + ((NAME)stm.getCallable()).getLabel(), Codegen.frame.calleeDefs(), null));

        else 
            Codegen.emit(new assem.OPER("call `s0", Codegen.frame.calleeDefs(), new List<Temp>(Codegen.doit(stm.getCallable()))));
        
        // Recupera argumentos
        Codegen.emit(new assem.OPER("add esp, " + numArgs * Codegen.frame.wordsize(), new List<Temp>(Codegen.frame.esp), new List<Temp>(Codegen.frame.esp)));

	Temp ret = new Temp();
        Codegen.emit(new assem.MOVE(ret, Codegen.frame.RV()));
        
        return ret;

        // return null;
	// return new Temp();
    }

}
