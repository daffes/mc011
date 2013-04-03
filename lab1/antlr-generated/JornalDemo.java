import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;
import java.util.*; 

public class JornalDemo {
    public String title;
    public String date;
    public Integer border;
    public Integer col;
        
    public class News extends HashMap<String, String> {
        String order[] = {"abstract", "image", "text", "author", "date" ,"source"};
        public Integer coli = 1;
        public Integer colf = 1;
        public String name;

        public void makeTitle() {
            print("<a href=\"\" onclick=\"window.open(&#39;" + name + ".html&#39;,&#39;output&#39;,&#39;width=720,height=500,scrollbars=yes,screenX=400,screenY=200&#39;)\">" + this.get("title")  + "</a><br>");
        }

        public void publish() {
            makeTitle();
            for (String k : order) {
                String v = this.get(k);
                if (v != null) {
                    print(v + "<br>");
                }
            }
        }
    };
    // TODO checar items obrigatorios

    public HashMap<String, News> news = new HashMap<String, News>();
    public ArrayList<News> items = new ArrayList<News>();

    public class JornalWalker extends JornalBaseListener {
        @Override 
        public void enterNEWSPAPER_DATE(JornalParser.NEWSPAPER_DATEContext ctx) { 
            date = ctx.STRING().toString();
        }

        @Override 
        public void enterNEWSPAPER_TITLE(JornalParser.NEWSPAPER_TITLEContext ctx) { 
            title = ctx.STRING().toString();
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
                n.name = newsName;
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
            News i = new News();
            if (ctx.NUMBER() != null) {
                i.coli = Integer.parseInt(ctx.NUMBER().toString());
                i.colf = i.coli;
            } else {
                i.coli = Integer.parseInt(ctx.pair_number().NUMBER(0).toString());
                i.colf = Integer.parseInt(ctx.pair_number().NUMBER(1).toString());
            }
            for (JornalParser.News_pieceContext c : ctx.news_piece()) {
                String newsName = c.NEWSNAME().toString().toLowerCase();
                String newsField = c.news_field_value().getText().toString().toLowerCase();
                if (newsField.equals("title")) {
                    i.name = newsName;
                }
                i.put(newsField, news.get(newsName).get(newsField));
            }
            items.add(i);
        }
    }

    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public void print(String s) {
        try {
            out.write(s + '\n');
        } catch (Exception e) {
            // pass
        }
                
    }

    public void head() {
        print("<head>");
        print("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
              "<title>" + this.title + "</title>" +
              "<script type=\"text/javascript\"></script>");
        print("</head>");
    }

    public void printTitle() {
        print(this.title + "<br><br>");
    }

    public void body(ArrayList<News> items) {
        print("<body>");
        printTitle();
        for (News i : items) {
            i.publish();
            print("<br>");
        }
        print("</body>");
    }

    public void toHtml(ArrayList<News> items) {
        print("<html>");
        head();
        body(items);
        print("</html>");
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
        for (News i : items) {
            //System.out.println(i);
        }
        toHtml(items);
        out.close();
        for (News n : news.values()) {
            out = new BufferedWriter(new FileWriter("../" + n.name + ".html"));
            ArrayList<News> t = new ArrayList<News>();
            t.add(n);
            toHtml(t);
            out.close();
        }
    }

    public static void main(String[] args) throws Exception {
        JornalDemo j = new JornalDemo();
        j.go(args[0]);
    }
}