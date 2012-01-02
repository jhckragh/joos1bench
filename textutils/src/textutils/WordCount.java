package textutils;

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
        StringBuffer options = new StringBuffer();

        for (int i = 0; i < args.length; i = i + 1) {
            if (args[i].startsWith("-")) {
                options.append(args[i].substring(1));
            } else {
                File f = new File(args[i]);
                if (f.isFile() && f.canRead()) {
                    WordCount wc = new WordCount(f);
                    wc.count();
                    stats.add((Object) wc);
                }
            }
        }

        System.exit(WordCount.printStats(stats, options.toString()));
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

    protected static int printStats(List stats, String opts) {
        boolean printNumLines = false;
        boolean printNumWords = false;
        boolean printNumChars = false;

        if (opts.length() == 0)
            opts = "clw";
        for (int i = 0; i < opts.length(); i = i + 1) {
            char c = opts.charAt(i);
            if (c == 'l')
                printNumLines = true;
            else if (c == 'w')
                printNumWords = true;
            else if (c == 'c')
                printNumChars = true;
            else {
                System.err.println("WordCount: invalid option -- " + c);
                return 1;
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
        if (stats.size() > 1)
            stats.add((Object) total);

        int width = WordCount.width(total.numChars);
        for (int i = 0; i < stats.size(); i = i + 1) {
            WordCount wc = (WordCount) stats.get(i);
            if (printNumLines)
                System.out.print(WordCount.format(wc.numLines, width) + " ");
            if (printNumWords)
                System.out.print(WordCount.format(wc.numWords, width) + " ");
            if (printNumChars)
                System.out.print(WordCount.format(wc.numChars, width) + " ");
            System.out.println(wc.filename);
        }

        return 0;
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
