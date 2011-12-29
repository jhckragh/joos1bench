import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

public final class Translate {
    protected boolean delete = false;
    protected boolean squeeze = false;

    public static void main(String[] args) throws IOException {
        String set1 = "";
        String set2 = "";

        boolean del = false;
        boolean sqz = false;

        {
            int i = 0;
            while (i < args.length && args[i].startsWith("-")) {
                String opts = args[i].substring(1);
                for (int j = 0; j < opts.length(); j = j + 1) {
                    if (opts.charAt(j) == 'd') {
                        del = true;
                    } else if (opts.charAt(j) == 's') {
                        sqz = true;
                    } else {
                        char bad = opts.charAt(j);
                        Translate.err("invalid option `" + bad + "'");
                        System.exit(1);
                    }
                }
                i = i + 1;
            }

            if (i < args.length)
                set1 = args[i];
            if (i < args.length - 1)
                set2 = args[i+1];

            boolean exit = false;
            if (del && !sqz && i < args.length - 1) {
                Translate.err("extra operand `" + args[i+1] + "'");
                Translate.err("Only one string may be given when " +
                              "deleting without squeezing repeats.");
                exit = true;
            } else if (i < args.length - 2) {
                Translate.err("extra operand `" + args[i+1] + "'");
                exit = true;
            } else if (set1.length() == 0) {
                Translate.err("missing operand");
                exit = true;
            } else if (del && sqz && set2.length() == 0) {
                Translate.err("missing operand after `" + set1 + "'");
                Translate.err("Two strings must be given when " +
                              "both deleting and squeezing repeats.");
                exit = true;
            } else if (!del && !sqz && set2.length() == 0) {
                Translate.err("Two strings must be given when translating.");
                exit = true;
            }

            if (exit || !Translate.areSetsOkay(set1, set2))
                System.exit(1);
        }

        Reader in =
            new BufferedReader((Reader) new InputStreamReader(System.in));
        Translate tr = new Translate(del, sqz);
        tr.translate(in, set1, set2);
        in.close();
    }

    public Translate(boolean delete, boolean squeeze) {
        this.delete = delete;
        this.squeeze = squeeze;
    }

    public void translate(Reader in, String set1, String set2)
        throws IOException {

        char[] exp1 = expand(set1);
        char[] exp2 = expandDependentRepeat(expand(set2), exp1);
        int[] table = Translate.buildTable(exp1, exp2);

        Arrays.sort(exp1);
        Arrays.sort(exp2);

        char lastPrinted = '\377';
        int c = -1;
        while ((c = in.read()) != -1) {
            char toPrint = (char) c;
            boolean inExp1 = Arrays.binarySearch(exp1, toPrint) >= 0;
            if (c >= 0 && c <= 127 && table[c] != -1)
                toPrint = (char) table[c];
            boolean inExp2 = Arrays.binarySearch(exp2, toPrint) >= 0;

            boolean suppress = (delete && inExp1) ||
                (!delete && squeeze && inExp1 && toPrint == lastPrinted) ||
                (delete && squeeze && inExp2 && toPrint == lastPrinted);

            if (!suppress) {
                System.out.print(toPrint);
                lastPrinted = toPrint;
            }
        }
    }

    // Precondition: !set.startsWith("-") // FIXME
    protected char[] expand(String set) {
        StringBuffer expanded = new StringBuffer();

        for (int i = 0; i < set.length(); i = i + 1) {
            char c = set.charAt(i);
            if (c == '-' && i < set.length() - 1) {
                char from = set.charAt(i - 1);
                char to = set.charAt(i + 1);
                for (char d = (char) (from + 1); d < to; d = (char) (d + 1))
                    expanded.append(d);
            } else if (c=='[' && i<set.length()-2 && set.charAt(i+1)==':') {
                int end = set.indexOf(":]", i);
                if (end != -1) {
                    String charClass = set.substring(i + 2, end);
                    expanded.append(Translate.expandCharClass(charClass));
                    i = end + 1;
                }
            } else if (c == '\\') {
                expanded.append(unescape(set.charAt(i + 1)));
                i = i + 1;
            } else {
                expanded.append(c);
            }
        }

        return expanded.toString().toCharArray();
    }

    protected char[] expandDependentRepeat(char[] set, char[] otherSet) {
        int lengthWithout = Math.max(set.length - "[c*]".length(), 0);
        if (lengthWithout >= otherSet.length)
            return set;

        StringBuffer expanded = new StringBuffer();
        for (int i = 0; i < set.length; i = i + 1) {
            char c = set[i];
            if (c == '[' && i < set.length - 3 &&
                set[i+2] == '*' && set[i+3] == ']') {
                char r = set[i+1];
                int diff = otherSet.length - lengthWithout;
                for (int j = 0; j < diff; j = j + 1)
                    expanded.append(r);
                i = i + "[c*]".length() - 1;
            } else {
                expanded.append(c);
            }
        }
        return expanded.toString().toCharArray();
    }

    protected static String expandCharClass(String className) {
        String upper  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower  = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String punct  = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

        // To avoid using closest match overloading, which is Joos 2
        Object clazz = (Object) className;

        if ("alnum".equals(clazz))
            return digits + upper + lower;
        if ("alpha".equals(clazz))
            return upper + lower;
        if ("blank".equals(clazz))
            return " \t";
        if ("cntrl".equals(clazz)) {
            StringBuffer sb = new StringBuffer();
            char c = '\000';
            while (c <= 31) {
                sb.append(c);
                c = (char) (c + 1);
            }
            sb.append((char) 127);
            return sb.toString();
        }
        if ("digit".equals(clazz))
            return digits;
        if ("graph".equals(clazz))
            return digits + upper + lower + punct;
        if ("lower".equals(clazz))
            return lower;
        if ("print".equals(clazz))
            return digits + upper + lower + punct;
        if ("punct".equals(clazz))
            return punct;
        if ("space".equals(clazz))
            return " \t\n\f\r\013";
        if ("upper".equals(clazz))
            return upper;
        if ("xdigit".equals(clazz))
            return digits + "abcdefABCDEF";

        return "";
    }

    protected char unescape(char c) {
        if (c == '\\')
            return '\\';
        if (c == 'a')
            return '\007';
        if (c == 'b')
            return '\b';
        if (c == 'f')
            return '\f';
        if (c == 'n')
            return '\n';
        if (c == 'r')
            return '\r';
        if (c == 't')
            return '\t';
        if (c == 'v')
            return '\013';
        return c;
    }

    // Precondition: from.length > 0
    protected static int[] buildTable(char[] from, char[] to) {
        int[] table = new int[128];
        Arrays.fill(table, -1);

        if (to.length > 0) {
            for (int i = 0; i < from.length; i = i + 1) {
                char c = from[i];
                if (i < to.length)
                    table[c] = to[i];
                else
                    table[c] = to[to.length - 1];
            }
        }

        return table;
    }

    protected static boolean areSetsOkay(String set1, String set2) {
        return Translate.isSetOkay(set1, 1) && Translate.isSetOkay(set2, 2);
    }

    protected static boolean isSetOkay(String set, int setNum) {
        boolean seenRepeatUntil = false;

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
            } else if (c=='[' && i<set.length()-2 && set.charAt(i+1)==':') {
                int end = set.indexOf(":]", i);
                if (end != -1) {
                    String charClass = set.substring(i + 2, end);
                    if (charClass.length() == 0) {
                        Translate.err("missing character class name `[::]'");
                        return false;
                    }
                    if (Translate.expandCharClass(charClass).length() == 0) {
                        Translate.err("invalid character class `" +
                                      charClass + "'");
                        return false;
                    }
                    if (setNum == 2 &&
                        !charClass.equals((Object) "lower") &&
                        !charClass.equals((Object) "upper")) {
                        Translate.err("when translating, the only character " +
                                      "classes that may appear in\nstring2 " +
                                      "are `upper' and `lower'");
                        return false;
                    }
                }
            } else if (c=='[' && i<set.length()-3 && set.charAt(i+2)=='*') {
                if (set.charAt(i+3) == ']') {
                    if (setNum == 1) {
                        Translate.err("the [c*] repeat construct may not " +
                                      "appear in string1");
                        return false;
                    } else if (setNum == 2) {
                        if (seenRepeatUntil) {
                            Translate.err("only one [c*] repeat construct " +
                                          "may appear in string2");
                            return false;
                        }
                        seenRepeatUntil = true;
                    }
                }
            } else if (c == '\\' && i == set.length() - 1) {
                if (i == 0 || (i > 0 && set.charAt(i - 1) != '\\')) {
                    Translate.err("unescaped backslash at end of string");
                    return false;
                }
            }
        }

        return true;
    }

    protected static void err(String msg) {
        System.err.println("Translate: " + msg);
    }
}
