package joos1lexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.Map;
import java.util.HashMap;

public final class Lexer {
    protected int columnNumber;
    protected TokenConstants constants;
    protected int currentChar;
    protected PushbackReader input;
    protected Map keywords;
    protected int lineNumber;

    public Lexer(Reader in) {
        if (!(in instanceof BufferedReader))
            in = new BufferedReader(in);
        input = new PushbackReader(in);
        constants = new TokenConstants();
        keywords = createKeywordMap(constants);
        currentChar = -1;
        lineNumber = 1;
        columnNumber = -1;
    }

    public Token nextToken() throws IOException {
        if (currentChar == -1)
            takeIt();

        while (currentChar == ' ' ||
               currentChar == '\t' ||
               currentChar == '\f' ||
               currentChar == '\r' ||
               currentChar == '\n')
            takeIt();

        return scanToken();
    }

    protected Token scanToken() throws IOException {
        if (currentChar == -1)
            return new Token(constants.EOT, "<eot>", lineNumber, columnNumber);

        int line = lineNumber;
        int column = columnNumber;

        // Identifiers, keywords, boolean literals, and null literal
        if (Character.isJavaIdentifierStart((char) currentChar)) {
            StringBuffer sb = new StringBuffer();
            sb.append((char) currentChar);
            takeIt();
            while (Character.isJavaIdentifierPart((char) currentChar)) {
                sb.append((char) currentChar);
                takeIt();
            }
            Byte kw = (Byte) keywords.get((Object) sb.toString());
            if (kw != null)
                return new Token(kw.byteValue(), sb.toString(), line, column);
            return new Token(constants.IDENTIFIER, sb.toString(), line, column);
        }

        // Integer literals
        if (currentChar == '0') {
            takeIt();
            return new Token(constants.INTEGER_LITERAL, "0", line, column);
        }
        if (Character.isDigit((char) currentChar)) {
            StringBuffer sb = new StringBuffer();
            sb.append((char) currentChar);
            takeIt();
            while (Character.isDigit((char) currentChar)) {
                sb.append((char) currentChar);
                takeIt();
            }
            return new Token(constants.INTEGER_LITERAL, sb.toString(), line, column);
        }

        // Delimiters
        if (currentChar == '{') {
            takeIt();
            return new Token(constants.L_BRACE, "{", line, column);
        }
        if (currentChar == '}') {
            takeIt();
            return new Token(constants.R_BRACE, "}", line, column);
        }
        if (currentChar == '[') {
            takeIt();
            return new Token(constants.L_BRACKET, "[", line, column);
        }
        if (currentChar == ']') {
            takeIt();
            return new Token(constants.R_BRACKET, "]", line, column);
        }
        if (currentChar == '(') {
            takeIt();
            return new Token(constants.L_PAREN, "(", line, column);
        }
        if (currentChar == ')') {
            takeIt();
            return new Token(constants.R_PAREN, ")", line, column);
        }

        // Punctuation
        if (currentChar == ';') {
            takeIt();
            return new Token(constants.SEMICOLON, ";", line, column);
        }
        if (currentChar == ',') {
            takeIt();
            return new Token(constants.COMMA, ",", line, column);
        }        
        if (currentChar == '.') {
            takeIt();
            return new Token(constants.DOT, ".", line, column);
        }
        if (currentChar == '=') {
            takeIt();
            if (currentChar == '=') {
                takeIt();
                return new Token(constants.EQ, "==", line, column);
            }
            return new Token(constants.ASSIGN, "=", line, column);
        }

        // Relational operators and complement
        if (currentChar == '<') {
            takeIt();
            if (currentChar == '=') {
                takeIt();
                return new Token(constants.LTEQ, "<=", line, column);
            }
            return new Token(constants.LT, "<", line, column);
        }
        if (currentChar == '>') {
            takeIt();
            if (currentChar == '=') {
                takeIt();
                return new Token(constants.GTEQ, ">=", line, column);
            }
            return new Token(constants.GT, ">", line, column);
        }
        if (currentChar == '!') {
            takeIt();
            if (currentChar == '=') {
                takeIt();
                return new Token(constants.NEQ, "!=", line, column);
            }
            return new Token(constants.COMPLEMENT, "!", line, column);
        }

        // Arithmetic and logical operators
        if (currentChar == '+') {
            takeIt();
            if (currentChar == '+') {
                takeIt();
                return new Token(constants.PLUS_PLUS, "++", line, column);
            }
            return new Token(constants.PLUS, "+", line, column);
        }
        if (currentChar == '-') {
            takeIt();
            if (currentChar == '-') {
                takeIt();
                return new Token(constants.MINUS_MINUS, "--", line, column);
            }
            return new Token(constants.MINUS, "-", line, column);
        }
        if (currentChar == '*') {
            takeIt();
            return new Token(constants.STAR, "*", line, column);
        }
        if (currentChar == '/') {
            takeIt();
            return new Token(constants.DIV, "/", line, column);
        }
        if (currentChar == '&') {
            takeIt();
            if (currentChar == '&') {
                takeIt();
                return new Token(constants.AND_AND, "&&", line, column);
            }
            return new Token(constants.AND, "&", line, column);
        }
        if (currentChar == '|') {
            takeIt();
            if (currentChar == '|') {
                takeIt();
                return new Token(constants.OR_OR, "||", line, column);
            }
            return new Token(constants.OR, "|", line, column);
        }
        if (currentChar == '^') {
            takeIt();
            return new Token(constants.XOR, "^", line, column);
        }
        if (currentChar == '%') {
            takeIt();
            return new Token(constants.MOD, "%", line, column);
        }

        return new Token(constants.ERROR, "<error>", line, column);
    }

    protected void takeIt() throws IOException {
        currentChar = input.read();
        columnNumber = columnNumber + 1;
        if (currentChar == '\n') {
            lineNumber = lineNumber + 1;
            columnNumber = -1;
        }
    }

    protected Map createKeywordMap(TokenConstants constants) {
        Map map = new HashMap();
        map.put((Object) "abstract", (Object) new Byte(constants.ABSTRACT));
        map.put((Object) "boolean", (Object) new Byte(constants.BOOLEAN));
        map.put((Object) "break", (Object) new Byte(constants.BREAK));
        map.put((Object) "byte", (Object) new Byte(constants.BYTE));
        map.put((Object) "case", (Object) new Byte(constants.CASE));
        map.put((Object) "catch", (Object) new Byte(constants.CATCH));
        map.put((Object) "char", (Object) new Byte(constants.CHAR));
        map.put((Object) "class", (Object) new Byte(constants.CLASS));
        map.put((Object) "const", (Object) new Byte(constants.CONST));
        map.put((Object) "continue", (Object) new Byte(constants.CONTINUE));
        map.put((Object) "default", (Object) new Byte(constants.DEFAULT));
        map.put((Object) "do", (Object) new Byte(constants.DO));
        map.put((Object) "double", (Object) new Byte(constants.DOUBLE));
        map.put((Object) "else", (Object) new Byte(constants.ELSE));
        map.put((Object) "extends", (Object) new Byte(constants.EXTENDS));
        map.put((Object) "final", (Object) new Byte(constants.FINAL));
        map.put((Object) "finally", (Object) new Byte(constants.FINALLY));
        map.put((Object) "float", (Object) new Byte(constants.FLOAT));
        map.put((Object) "for", (Object) new Byte(constants.FOR));
        map.put((Object) "goto", (Object) new Byte(constants.GOTO));
        map.put((Object) "if", (Object) new Byte(constants.IF));
        map.put((Object) "implements", (Object) new Byte(constants.IMPLEMENTS));
        map.put((Object) "import", (Object) new Byte(constants.IMPORT));
        map.put((Object) "instanceof", (Object) new Byte(constants.INSTANCEOF));
        map.put((Object) "int", (Object) new Byte(constants.INT));
        map.put((Object) "interface", (Object) new Byte(constants.INTERFACE));
        map.put((Object) "long", (Object) new Byte(constants.LONG));
        map.put((Object) "native", (Object) new Byte(constants.NATIVE));
        map.put((Object) "new", (Object) new Byte(constants.NEW));
        map.put((Object) "package", (Object) new Byte(constants.PACKAGE));
        map.put((Object) "private", (Object) new Byte(constants.PRIVATE));
        map.put((Object) "protected", (Object) new Byte(constants.PROTECTED));
        map.put((Object) "public", (Object) new Byte(constants.PUBLIC));
        map.put((Object) "return", (Object) new Byte(constants.RETURN));
        map.put((Object) "short", (Object) new Byte(constants.SHORT));
        map.put((Object) "static", (Object) new Byte(constants.STATIC));
        map.put((Object) "strictfp", (Object) new Byte(constants.STRICTFP));
        map.put((Object) "super", (Object) new Byte(constants.SUPER));
        map.put((Object) "switch", (Object) new Byte(constants.SWITCH));
        map.put((Object) "synchronized", (Object) new Byte(constants.SYNCHRONIZED));
        map.put((Object) "this", (Object) new Byte(constants.THIS));
        map.put((Object) "throw", (Object) new Byte(constants.THROW));
        map.put((Object) "throws", (Object) new Byte(constants.THROWS));
        map.put((Object) "transient", (Object) new Byte(constants.TRANSIENT));
        map.put((Object) "try", (Object) new Byte(constants.TRY));
        map.put((Object) "void", (Object) new Byte(constants.VOID));
        map.put((Object) "volatile", (Object) new Byte(constants.VOLATILE));
        map.put((Object) "while", (Object) new Byte(constants.WHILE));
        map.put((Object) "true", (Object) new Byte(constants.TRUE));
        map.put((Object) "false", (Object) new Byte(constants.FALSE));
        map.put((Object) "null", (Object) new Byte(constants.NULL));
        return map;
    }
}
