import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;

public final class WordCount {
    protected int numLines = 0;
    protected int numWords = 0;
    protected int numChars = 0;
    protected String filename = "";
    protected File input;

    public static void main(String[] args) throws IOException {
        List stats = new ArrayList(args.length);

        for (int i = 0; i < args.length; i = i + 1) {
            File f = new File(args[i]);
            if (f.isFile() && f.canRead()) {
                WordCount wc = new WordCount(f);
                wc.count();
                stats.add((Object) wc);
            }
        }

        // Dummy object for total
        WordCount total = new WordCount(new File("total"));
        for (int i = 0; i < stats.size(); i = i + 1) {
            WordCount wc = (WordCount) stats.get(i);
            total.numLines = total.numLines + wc.numLines;
            total.numWords = total.numWords + wc.numWords;
            total.numChars = total.numChars + wc.numChars;
        }
        if (args.length > 1)
            stats.add((Object) total);

        WordCount.printStats(stats, WordCount.width(total.numChars));
    }

    public WordCount(File input) {
        this.input = input;
        filename = input.getName();
    }

    public void count() throws IOException {
        BufferedReader in = new BufferedReader((Reader) new FileReader(input));

        boolean inWord = false;
        int c = -1;
        while ((c = in.read()) != -1) {
            numChars = numChars + 1;
            if (!Character.isWhitespace((char) c)) {
                inWord = true;
            } else {
                if (inWord) {
                    inWord = false;
                    numWords = numWords + 1;
                }
            }
            if (c == '\n')
                numLines = numLines + 1;
        }

        in.close();
    }

    protected static void printStats(List stats, int width) {
        for (int i = 0; i < stats.size(); i = i + 1) {
            WordCount wc = (WordCount) stats.get(i);
            System.out.println(WordCount.format(wc.numLines, width) + " " +
                               WordCount.format(wc.numWords, width) + " " +
                               WordCount.format(wc.numChars, width) + " " +
                               wc.filename);
        }
    }

    protected static String format(int n, int width) {
        String repr = Integer.toString(n);
        int diff = width - repr.length();
        StringBuffer sb = new StringBuffer();
        while (diff > 0) {
            sb.append(' ');
            diff = diff - 1;
        }
        return sb.append(repr).toString();
    }

    protected static int width(int n) {
        if (n == 0)
            return 1;

        int width = 0;
        if (n < 0)
            width = 1;
        while (n != 0) {
            width = width + 1;
            n = n / 10;
        }
        return width;
    }
}
