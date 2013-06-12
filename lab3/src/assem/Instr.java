package assem;

import temp.Label;
import temp.Temp;
import temp.TempMap;
import util.List;

/**
 * Superclasse das instru&ccedil;&otilde;es que ser&atilde;o geradas.
 */
public abstract class Instr{
    public String assem;
    public abstract List<Temp> use();
    public abstract List<Temp> def();
    public abstract Targets jumps();

    public String format(TempMap m){
        List<Temp> dst = def();
        List<Temp> src = use();
        Targets j = jumps();
        List<Label>jump = (j==null) ? null : j.labels;
        StringBuffer s = new StringBuffer();
        int len = assem.length();
        
        for(int i=0; i<len; i++)
            if (assem.charAt(i)=='`')
                switch(assem.charAt(++i)){
                	case 'u':
                    case 's':
                    {
                        int n = Character.digit(assem.charAt(++i),10);
                        s.append(m.tempMap(src.get(n)));
                    }
                    break;
                    
                    case 'd':
                    {
                        int n = Character.digit(assem.charAt(++i),10);
                        s.append(m.tempMap(dst.get(n)));
                    }
                    break;
                    
                    case 'j':
                    {
                        int n = Character.digit(assem.charAt(++i),10);
                        s.append(jump.get(n).toString());
                    }
                    break;
                    
                    case '`': s.append('`'); 
                    break;
                    
                    default: throw new Error("bad Assem format");
                }
            else
                s.append(assem.charAt(i));

        return s.toString();
    }
    
    public String debug(){
        List<Temp> dst = def();
        List<Temp> src = use();
        Targets j = jumps();
        List<Label>jump = (j==null) ? null : j.labels;
        StringBuffer s = new StringBuffer();
        int len = assem.length();
        
        for(int i=0; i<len; i++)
            if (assem.charAt(i)=='`')
                switch(assem.charAt(++i)){
                	case 'u':
                    case 's':
                    {
                        int n = Character.digit(assem.charAt(++i),10);
                        s.append(src.get(n));
                    }
                    break;
                    
                    case 'd':
                    {
                        int n = Character.digit(assem.charAt(++i),10);
                        s.append(dst.get(n));
                    }
                    break;
                    
                    case 'j':
                    {
                        int n = Character.digit(assem.charAt(++i),10);
                        s.append(jump.get(n).toString());
                    }
                    break;
                    
                    case '`': s.append('`'); 
                    break;
                    
                    default: throw new Error("bad Assem format");
                }
            else
                s.append(assem.charAt(i));

        return s.toString();
    }  
}
