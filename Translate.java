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
        set2 = Translate.expandDependentRepeat(set2, set1);

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
                expanded.append(Translate.unescape(set.charAt(i + 1)));
                i = i + 1;
            } else {
                expanded.append(c);
            }
        }

        return expanded.toString();
    }

    protected static String expandDependentRepeat(String set, String otherSet) {
        int lengthWithout = Math.max(set.length() - "[c*]".length(), 0);
        if (lengthWithout >= otherSet.length())
            return set;

        StringBuffer expanded = new StringBuffer();
        for (int i = 0; i < set.length(); i = i + 1) {
            char c = set.charAt(i);

            if (c == '[' && i < set.length() - 3 &&
                set.charAt(i+2) == '*' && set.charAt(i+3) == ']') {
                char r = set.charAt(i+1);
                int diff = otherSet.length() - lengthWithout;
                for (int j = 0; j < diff; j = j + 1)
                    expanded.append(r);
                i = i + "[c*]".length() - 1;
            } else {
                expanded.append(c);
            }
        }
        return expanded.toString();
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

    // TODO Handle octal escapes \NNN
    protected static char unescape(char c) {
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

        if (!Translate.isSetOkay(args[0],1) || !Translate.isSetOkay(args[1],2))
            return false;

        return true;
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

    public Translate() {}
}
