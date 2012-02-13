package joos1lexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

public final class Lexer {
    protected int column;
    protected TokenConstants constants;
    protected int currentChar;
    protected PushbackReader input;
    protected int line;

    public Lexer(Reader in) {
        if (!(in instanceof BufferedReader))
            in = new BufferedReader(in);
        input = new PushbackReader(in);
        constants = new TokenConstants();
        currentChar = -1;
        line = 1;
        column = -1;
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
            return new Token(constants.EOT, "<eot>", line, column);

        int startLine = line;
        int startColumn = column;

        // Delimiters
        if (currentChar == '{') {
            takeIt();
            return new Token(constants.L_BRACE, "{", startLine, startColumn);
        }
        if (currentChar == '}') {
            takeIt();
            return new Token(constants.R_BRACE, "}", startLine, startColumn);
        }
        if (currentChar == '[') {
            takeIt();
            return new Token(constants.L_BRACKET, "[", startLine, startColumn);
        }
        if (currentChar == ']') {
            takeIt();
            return new Token(constants.R_BRACKET, "]", startLine, startColumn);
        }
        if (currentChar == '(') {
            takeIt();
            return new Token(constants.L_PAREN, "(", startLine, startColumn);
        }
        if (currentChar == ')') {
            takeIt();
            return new Token(constants.R_PAREN, ")", startLine, startColumn);
        }

        // Punctuation
        if (currentChar == ';') {
            takeIt();
            return new Token(constants.SEMICOLON, ";", startLine, startColumn);
        }
        if (currentChar == ',') {
            takeIt();
            return new Token(constants.COMMA, ",", startLine, startColumn);
        }        
        if (currentChar == '.') {
            takeIt();
            return new Token(constants.DOT, ".", startLine, startColumn);
        }
        if (currentChar == '=') {
            takeIt();
            if (currentChar == '=') {
                takeIt();
                return new Token(constants.EQ, "==", startLine, startColumn);
            }
            return new Token(constants.ASSIGN, "=", startLine, startColumn);
        }


        // Relational operators and complement
        if (currentChar == '<') {
            takeIt();
            if (currentChar == '=') {
                takeIt();
                return new Token(constants.LTEQ, "<=", startLine, startColumn);
            }
            return new Token(constants.LT, "<", startLine, startColumn);
        }
        if (currentChar == '>') {
            takeIt();
            if (currentChar == '=') {
                takeIt();
                return new Token(constants.GTEQ, ">=", startLine, startColumn);
            }
            return new Token(constants.GT, ">", startLine, startColumn);
        }
        if (currentChar == '!') {
            takeIt();
            if (currentChar == '=') {
                takeIt();
                return new Token(constants.NEQ, "!=", startLine, startColumn);
            }
            return new Token(constants.COMPLEMENT, "!", startLine, startColumn);
        }


        // Arithmetic and logical operators
        if (currentChar == '+') {
            takeIt();
            if (currentChar == '+') {
                takeIt();
                return new Token(constants.PLUS_PLUS, "++", startLine, startColumn);
            }
            return new Token(constants.PLUS, "+", startLine, startColumn);
        }
        if (currentChar == '-') {
            takeIt();
            if (currentChar == '-') {
                takeIt();
                return new Token(constants.MINUS_MINUS, "--", startLine, startColumn);
            }
            return new Token(constants.MINUS, "-", startLine, startColumn);
        }
        if (currentChar == '*') {
            takeIt();
            return new Token(constants.STAR, "*", startLine, startColumn);
        }
        if (currentChar == '/') {
            takeIt();
            return new Token(constants.DIV, "/", startLine, startColumn);
        }
        if (currentChar == '&') {
            takeIt();
            if (currentChar == '&') {
                takeIt();
                return new Token(constants.AND_AND, "&&", startLine, startColumn);
            }
            return new Token(constants.AND, "&", startLine, startColumn);
        }
        if (currentChar == '|') {
            takeIt();
            if (currentChar == '|') {
                takeIt();
                return new Token(constants.OR_OR, "||", startLine, startColumn);
            }
            return new Token(constants.OR, "|", startLine, startColumn);
        }
        if (currentChar == '^') {
            takeIt();
            return new Token(constants.XOR, "^", startLine, startColumn);
        }
        if (currentChar == '%') {
            takeIt();
            return new Token(constants.MOD, "%", startLine, startColumn);
        }

        return new Token(constants.ERROR, "<error>", line, column);
    }

    protected void takeIt() throws IOException {
        currentChar = input.read();
        column = column + 1;
        if (currentChar == '\n') {
            line = line + 1;
            column = 0;
        }
    }
}
