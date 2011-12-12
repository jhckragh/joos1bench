import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

public final class Translate {

    public static void main(String[] args) throws IOException {
        if (!Translate.areArgumentsOkay(args))
            System.exit(1);

        String set1 = Translate.expand(args[0]);
        String set2 = Translate.expand(args[1]);

        int[] table = Translate.buildTable(set1, set2);

        BufferedReader in =
            new BufferedReader((Reader) new InputStreamReader(System.in));

        int c = -1;
        while ((c = in.read()) != -1)
            if (c >= 0 && c <= 127 && table[c] != -1)
                System.out.print((char) table[c]);
            else
                System.out.print((char) c);

        in.close();
    }

    // Precondition: !set.startsWith("-")
    protected static String expand(String set) {
        StringBuffer expanded = new StringBuffer();

        for (int i = 0; i < set.length(); i = i + 1) {
            char c = set.charAt(i);
            if (c == '-' && i < set.length() - 1) {
                char from = set.charAt(i - 1);
                char to = set.charAt(i + 1);
                for (char d = (char) (from + 1); d <= to; d = (char) (d + 1))
                    expanded.append(d);
            } else {
                expanded.append(c);
            }
        }

        return expanded.toString();
    }

    protected static int[] buildTable(String set1, String set2) {
        int[] table = new int[128];
        Arrays.fill(table, -1);

        char[] from = set1.toCharArray();
        char[] to = set2.toCharArray();

        for (int i = 0; i < from.length; i = i + 1) {
            char c = from[i];
            if (i < to.length)
                table[c] = to[i];
            else
                table[c] = to[to.length - 1];
        }

        return table;
    }

    // For now we assume that args[0] is SET1 and args[1] is SET2
    protected static boolean areArgumentsOkay(String[] args) {
        if (args.length != 2) {
            if (args.length < 2)
                Translate.err("missing operand");
            else
                Translate.err("extra operand `" + args[2] + "'");
            return false;
        }

        if (!Translate.isSetOkay(args[0]) || !Translate.isSetOkay(args[1]))
            return false;

        return true;
    }

    protected static boolean isSetOkay(String set) {
        for (int i = 0; i < set.length(); i = i + 1) {
            char c = set.charAt(i);

            if (c < 0 || c > 127) {
                Translate.err("non-ascii character `" + c + "'");
                return false;
            }

            if (c == '-' && i < set.length() - 1) {
                char from = set.charAt(i - 1);
                char to = set.charAt(i + 1);
                if (to < from) {
                    Translate.err("range-endpoints of `" +
                                  from + "-" + to +"' are in " +
                                  "reverse collating sequence order");
                    return false;
                }
            }
        }

        return true;
    }

    protected static void err(String msg) {
        System.err.println("Translate: " + msg);
    }

    public Translate() {}
}
