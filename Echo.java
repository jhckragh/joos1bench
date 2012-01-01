public class Echo {
    public static void main(String[] args) {
        boolean printNewline = true;
        boolean interpretEscapes = false;

        for (int i = 0; i < args.length; i = i + 1) {
            boolean isOption = false;

            if (args[i].startsWith("-")) {
                boolean onlyLegalFlags = true;
                for (int j = 1; j < args[i].length(); j = j + 1) {
                    char c = args[i].charAt(j);
                    onlyLegalFlags =
                        onlyLegalFlags && (c == 'n' || c == 'e' || c == 'E');
                }
                if (isOption = onlyLegalFlags) {
                    for (int j = 1; j < args[i].length(); j = j + 1) {
                        char c = args[i].charAt(j);
                        if (c == 'n')
                            printNewline = false;
                        else if (c == 'e')
                            interpretEscapes = true;
                        else if (c == 'E')
                            interpretEscapes = false;
                    }
                }
            }

            if (!isOption) {
                if (interpretEscapes)
                    System.out.print(Echo.unescape(args[i]));
                else
                    System.out.print(args[i]);

                if (i < args.length - 1)
                    System.out.print(' ');
                else if (printNewline)
                    System.out.println();
            }
        }
    }

    // TODO Add support for octal and hexadecimal escapes
    public static String unescape(String s) {
        StringBuffer res = new StringBuffer();

        for (int i = 0; i < s.length(); i = i + 1) {
            boolean isEscape = s.charAt(i) == '\\' && i < s.length() - 1;
            if (isEscape) {
                char c = s.charAt(i + 1);
                if (c == '\\') {
                    res.append('\\');
                } else if (c == 'a') {
                    res.append('\007');
                } else if (c == 'b') {
                    res.append('\b');
                } else if (c == 'f') {
                    res.append('\f');
                } else if (c == 'n') {
                    res.append('\n');
                } else if (c == 'r') {
                    res.append('\r');
                } else if (c == 't') {
                    res.append('\t');
                } else if (c == 'v') {
                    res.append('\013');
                } else {
                    isEscape = false;
                }
            }

            if (isEscape)
                i = i + 1; // skip c
            else
                res.append(s.charAt(i));
        }

        return res.toString();
    }

    public Echo() {}
}
