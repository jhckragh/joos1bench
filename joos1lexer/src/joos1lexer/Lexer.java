package joos1lexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

public final class Lexer {
    protected int columnNumber;
    protected TokenConstants constants;
    protected int currentChar;
    protected PushbackReader input;
    protected int lineNumber;

    public Lexer(Reader in) {
        if (!(in instanceof BufferedReader))
            in = new BufferedReader(in);
        input = new PushbackReader(in);
        constants = new TokenConstants();
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

        // Identifiers
        if (Character.isJavaIdentifierStart((char) currentChar)) {
            StringBuffer sb = new StringBuffer();
            sb.append((char) currentChar);
            takeIt();
            while (Character.isJavaIdentifierPart((char) currentChar)) {
                sb.append((char) currentChar);
                takeIt();
            }
            return new Token(constants.IDENTIFIER, sb.toString(), line, column);
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
            columnNumber = 0;
        }
    }
}
