package quintain.lexer;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.IOException;

// Token = java-letter { java-letter | Digit }
//       |  ( "0" | NonzeroDigit { Digit } )
//       | "'" ( single-character | EscapeSequence ) "'"
//       | """" { string-character | EscapeSequence } """"
//       | "!" | "&&" | "||"
//       | "<" | ">" | "==" | "<=" | ">=" | "!="
//       | "+" | "-" | "*" | "/" | "&" | "|" | "^" | "%" | "++" | "--"
//       |  "(" | ")" | "{" | "}" | "[" | "]"
//       |  ";" | "," | "." | "=" .

//       | "abstract"
//       | "boolean"
//       | "break"
//       | "byte"
//       | "case"
//       | "catch"
//       | "char"
//       | "class"
//       | "const"
//       | "continue"
//       | "default"
//       | "do"
//       | "double"
//       | "else"
//       | "extends"
//       | "final"
//       | "finally"
//       | "float"
//       | "for"
//       | "goto"
//       | "if"
//       | "implements"
//       | "import"
//       | "instanceof"
//       | "int"
//       | "interface"
//       | "long"
//       | "native"
//       | "new"
//       | "package"
//       | "private"
//       | "protected"
//       | "public"
//       | "return"
//       | "short"
//       | "static"
//       | "strictfp"
//       | "super"
//       | "switch"
//       | "synchronized"
//       | "this"
//       | "throw"
//       | "throws"
//       | "transient"
//       | "try"
//       | "void"
//       | "volatile"
//       | "while"
//       | "true"
//       | "false"
//       | "null"

// Digit = "0" | NonzeroDigit .

// NonzeroDigit = "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" .

// EscapeSequence = "\b" | "\t" | "\n" | "\f" | "\r" | "\""" | "\'" | "\\" |
//                  OctalEscape .

// OctalEscape = "\" ( OctalDigit [ OctalDigit ] |
//                     ( "0" | "1" | "2" ) OctalDigit OctalDigit ) . 

// Separator = Comment | Blank .

// Comment = TraditionalComment | EndOfLineComment .

// TraditionalComment = "/*" CommentTail .

// CommentTail = "*" CommentTailStar | not-star CommentTail .

// CommentTailStar = "/" | "*" CommentTailStar | not-star-not-slash CommentTail .
// EndOfLineComment = "//" { input-character } end-of-line .

// Blank = space | tab | form-feed | end-of-line .

public final class Lexer {
    protected int column;
    protected int currentChar;
    protected byte currentKind;
    protected StringBuffer currentText;
    protected Reader input;
    protected TokenConstants kinds;
    protected int line;

    public Lexer(Reader input) throws IOException {
        if (input instanceof BufferedReader)
            this.input = input;
        else
            this.input = new BufferedReader(input);

        kinds = new TokenConstants();
        currentChar = this.input.read();
        line = 1;
    }

    public Token nextToken() {
        return null; // TODO
    }

    protected void take(int expectedChar) throws IOException {
        if (currentChar == expectedChar) {
            currentText.append((char) currentChar);
            currentChar = input.read();
        } else {
            // TODO: Proper error handling
            System.err.println("*** saw `" + (char) currentChar +
                               "' but expected `" + (char) expectedChar +
                               "' ***");
        }
    }

    protected void takeIt() throws IOException {
        currentText.append((char) currentChar);
        currentChar = input.read();
    }
}
