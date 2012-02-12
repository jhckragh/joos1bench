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
        return scanToken();
    }

    protected Token scanToken() throws IOException {
        if (currentChar == -1)
            return new Token(constants.EOT, "<eot>", line, column);

        if (currentChar == '{') {
            Token lbrace = new Token(constants.L_BRACE, "{", line, column);
            takeIt();
            return lbrace;
        }

        if (currentChar == '}') {
            Token rbrace = new Token(constants.R_BRACE, "}", line, column);
            takeIt();
            return rbrace;
        }

        if (currentChar == '[') {
            Token lbracket = new Token(constants.L_BRACKET, "[", line, column);
            takeIt();
            return lbracket;
        }

        if (currentChar == ']') {
            Token rbracket = new Token(constants.R_BRACKET, "]", line, column);
            takeIt();
            return rbracket;
        }

        if (currentChar == '(') {
            Token lparen = new Token(constants.L_PAREN, "(", line, column);
            takeIt();
            return lparen;
        }

        if (currentChar == ')') {
            Token rparen = new Token(constants.R_PAREN, ")", line, column);
            takeIt();
            return rparen;
        }

        if (currentChar == ';') {
            Token semi = new Token(constants.SEMICOLON, ";", line, column);
            takeIt();
            return semi;
        }

        if (currentChar == ',') {
            Token comma = new Token(constants.COMMA, ",", line, column);
            takeIt();
            return comma;
        }        

        if (currentChar == '.') {
            Token dot = new Token(constants.DOT, ".", line, column);
            takeIt();
            return dot;
        }

        if (currentChar == '=') {
            Token assign = new Token(constants.ASSIGN, "=", line, column);
            takeIt();
            return assign;
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
