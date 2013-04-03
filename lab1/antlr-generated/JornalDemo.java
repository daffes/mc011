import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*; 

public class JornalDemo {
    public String title;
    public String date;
    public Integer border;
    public Integer col;
        
    public class News extends HashMap<String, String> {};
    
    public class Item extends News {
        public Integer coli;
        public Integer colf;
    }

    public HashMap<String, News> news = new HashMap<String, News>();
    public ArrayList<Item> items = new ArrayList<Item>();

    public class JornalWalker extends JornalBaseListener {
        @Override 
        public void enterNEWSPAPER_DATE(JornalParser.NEWSPAPER_DATEContext ctx) { 
            title = ctx.STRING().toString();
        }

        @Override 
        public void enterNEWSPAPER_TITLE(JornalParser.NEWSPAPER_TITLEContext ctx) { 
            date = ctx.STRING().toString();
        }

	@Override 
        public void enterNews_field_value(JornalParser.News_field_valueContext ctx) { 
            if (!ctx.getParent().getParent().getClass().isAssignableFrom(JornalParser.NewsContext.class)) {
                return;
            }
            String newsName = ((JornalParser.NewsContext)ctx.getParent().getParent()).NEWSNAME().toString().toLowerCase();
            String fieldName = ctx.getText().toString().toLowerCase();
            String fieldValue = ((JornalParser.News_fieldsContext)ctx.getParent()).STRING().toString();
            News n;
            if ((n = news.get(newsName)) == null) {
                news.put(newsName, new News());
                n = news.get(newsName);
            };
            n.put(fieldName, fieldValue);
        }

        @Override 
        public void enterFormat(JornalParser.FormatContext ctx) { 
            col = Integer.parseInt(ctx.column().NUMBER().toString());
            border = Integer.parseInt(ctx.border().NUMBER().toString());
        }

        @Override 
        public void enterItem(JornalParser.ItemContext ctx) { 
            //i = new Item();
            //items.add(i);
        }
    }

    public String readFile(String fname) throws Exception {
        BufferedReader reader = new BufferedReader( new FileReader (fname));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");
        
        while( ( line = reader.readLine() ) != null ) {
            stringBuilder.append( line );
            stringBuilder.append( ls );
        }
        return stringBuilder.toString();
    }

    public void go(String fname) throws Exception {
        String all = readFile(fname);
        CharStream in = new ANTLRInputStream(all);
        JornalLexer lexer = new JornalLexer(in);
        TokenStream tokens = new CommonTokenStream(lexer);
        JornalParser parser = new JornalParser(tokens);
        
        ParseTree tree = parser.r();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new JornalWalker(), tree);

    }

    public static void main(String[] args) throws Exception {
        JornalDemo j = new JornalDemo();
        j.go(args[0]);
    }
}