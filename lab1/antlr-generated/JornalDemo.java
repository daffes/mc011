import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;
import java.util.*; 
import java.lang.*;
import java.util.regex.*;

public class JornalDemo {
    public String title = null;
    public String date = null;
    public Integer border = 0;
    public Integer col = 0;
        
    public class News extends HashMap<String, String> {
        String order[] = {"abstract", "text", "date"};
        public Integer coli = 1;
        public Integer colf = 1;
        public String name;

        public void validator() throws Exception {
            if (!this.containsKey("title")) {
                throw new Exception("A noticia " + name + " nao tem titulo");
            }
            if (!this.containsKey("abstract")) {
                throw new Exception("A noticia " + name + " nao tem abstract");
            }
            
            assert this.containsKey("title");
            assert this.containsKey("abstract");
        }

        public void makeTitle() {
            print("<a href=\"\" onclick=\"window.open(&#39;" + name + ".html&#39;,&#39;output&#39;,&#39;width=1048,height=500,scrollbars=yes,screenX=400,screenY=200&#39;)\">" + this.get("title")  + "</a><br>");
        }

        public void addImage() {
            print("<div style=\"float: right; padding: 10px\"><img src=\"" + this.get("image") + "\"> </div>"); 
        }

        public void addAuthor() {
            print("<b>Autor:</b> " + this.get("author")); 
        }

        public void addSource() {
            print("<b>Fonte:</b> " + this.get("source")); 
        }

        public void publish() {
            print("<td width=\""+ (new Integer(1012/col)).toString() + "\" align=\"justify\" valign=\"top\" colspan=\"" + (new Integer(colf - coli + 1)).toString() + "\">");
            makeTitle();
            if (this.containsKey("image")) {
                addImage();
            }
            for (String k : order) {
                String v = this.get(k);
                if (v != null) {
                    print(v + "<br><br>");
                }
            }
            if (this.containsKey("author")) {
                addAuthor();
            }
            if (this.containsKey("source")) {
                addSource();
            }

            print("</td>");
        }
    };
    // TODO checar items obrigatorios

    public HashMap<String, News> news = new HashMap<String, News>();
    public ArrayList<News> items = new ArrayList<News>();

    public void validator() throws Exception {
        if (title == null) {
            throw new Exception("O jornal nao tem titulo");
        }
        for (News n : news.values()) {
            n.validator();
        }
    }

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
        print("<center><font size=\"6\">" + this.title + "\"</font></center>" + "<br><br>");
    }

    public void body(ArrayList<News> items) {
        ArrayList<News> ti = new ArrayList<News>();
        for (News n : items) {
            ti.add(n);
        }
        print("<body>");
        printTitle();
        print("<table cellspacing=\"0\" cellpadding=\"8\" width=\"1024\" border=\"" +  border.toString() + "\"><tbody>");
        print("<tr>");

        int c = 0;        
        while (ti.size() > 0) {
            boolean ok = false;
            for (int i = 0; i < ti.size(); i++) {
                if (ti.get(i).coli == c + 1) {
                    ti.get(i).publish();
                    c = (ti.get(i).colf);
                    ti.remove(i);
                    ok = true;
                    break;
                }
            }
            if (ok == false) {
                print("<td width=\"337\" align=\"justify\" valign=\"top\" colspan=\"1\"></td>");
                c = c + 1;
            }
            if (c == col) {
                print("</tr><tr>");
                c = 0;
            }
        }
        while (c < col) {
            print("<td width=\"337\" align=\"justify\" valign=\"top\" colspan=\"1\"></td>");
            c += 1;
        }

        print("</tr>");
        print("</table></tbody>");
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

        validator();
        toHtml(items);
        out.close();
        col = 1;
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