import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public final class Translate {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            if (args.length < 2)
                Translate.err("missing operand");
            else
                Translate.err("extra operand `" + args[2] + "'");
            System.exit(1);
        }

        int[] table =
            Translate.buildTable(args[0].toCharArray(), args[1].toCharArray());

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

    protected static int[] buildTable(char[] from, char[] to) {
        int[] table = new int[128];

        for (int i = 0; i < table.length; i = i + 1)
            table[i] = -1;

        for (int i = 0; i < from.length; i = i + 1) {
            char c = from[i];
            if (c >= 0 && c <= 127)
                if (i < to.length)
                    table[c] = to[i];
                else
                    table[c] = to[to.length - 1];
            else
                Translate.err("Translate: non-ascii character `" + c + "'");
        }

        return table;
    }

    protected static void err(String msg) {
        System.err.println("Translate: " + msg);
    }

    public Translate() {}
}
