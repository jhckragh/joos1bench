package joos1lexer;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import java.io.Reader;
import java.io.StringReader;

public class LexerTest extends TestCase {
    protected TokenConstants constants = new TokenConstants();

    public LexerTest() {}

    protected void assertByteEquals(byte expected, byte actual) {
        Assert.assertEquals((Object) new Byte(expected),
                            (Object) new Byte(actual));
    }

    protected void assertIntEquals(int expected, int actual) {
        Assert.assertEquals((Object) new Integer(expected),
                            (Object) new Integer(actual));
    }

    protected void assertStringEquals(String expected, String actual) {
        Assert.assertEquals((Object) expected, (Object) actual);
    }

    public void testBareMinimum() throws Exception {
        String input = "{";
        Lexer lexer = new Lexer((Reader) new StringReader(input));

        Token lbrace = lexer.nextToken();
        assertByteEquals(constants.L_BRACE, lbrace.kind());
        assertStringEquals("{", lbrace.text());
        assertIntEquals(1, lbrace.line());
        assertIntEquals(0, lbrace.column());

        assertByteEquals(constants.EOT, lexer.nextToken().kind());
    }

    public void testDelimiters() throws Exception {
        String input = "{}[]()";
        Lexer lexer = new Lexer((Reader) new StringReader(input));

        Token lbrace = lexer.nextToken();
        assertByteEquals(constants.L_BRACE, lbrace.kind());
        assertStringEquals("{", lbrace.text());
        assertIntEquals(1, lbrace.line());
        assertIntEquals(0, lbrace.column());

        Token rbrace = lexer.nextToken();
        assertByteEquals(constants.R_BRACE, rbrace.kind());
        assertStringEquals("}", rbrace.text());
        assertIntEquals(1, rbrace.line());
        assertIntEquals(1, rbrace.column());

        Token lbracket = lexer.nextToken();
        assertByteEquals(constants.L_BRACKET, lbracket.kind());
        assertStringEquals("[", lbracket.text());
        assertIntEquals(1, lbracket.line());
        assertIntEquals(2, lbracket.column());

        Token rbracket = lexer.nextToken();
        assertByteEquals(constants.R_BRACKET, rbracket.kind());
        assertStringEquals("]", rbracket.text());
        assertIntEquals(1, rbracket.line());
        assertIntEquals(3, rbracket.column());

        Token lparen = lexer.nextToken();
        assertByteEquals(constants.L_PAREN, lparen.kind());
        assertStringEquals("(", lparen.text());
        assertIntEquals(1, lparen.line());
        assertIntEquals(4, lparen.column());

        Token rparen = lexer.nextToken();
        assertByteEquals(constants.R_PAREN, rparen.kind());
        assertStringEquals(")", rparen.text());
        assertIntEquals(1, rparen.line());
        assertIntEquals(5, rparen.column());

        assertByteEquals(constants.EOT, lexer.nextToken().kind());
    }

    public void testPunctuation() throws Exception {
        String input = ";,.=";
        Lexer lexer = new Lexer((Reader) new StringReader(input));

        Token semi = lexer.nextToken();
        assertByteEquals(constants.SEMICOLON, semi.kind());
        assertStringEquals(";", semi.text());
        assertIntEquals(1, semi.line());
        assertIntEquals(0, semi.column());

        Token comma = lexer.nextToken();
        assertByteEquals(constants.COMMA, comma.kind());
        assertStringEquals(",", comma.text());
        assertIntEquals(1, comma.line());
        assertIntEquals(1, comma.column());

        Token dot = lexer.nextToken();
        assertByteEquals(constants.DOT, dot.kind());
        assertStringEquals(".", dot.text());
        assertIntEquals(1, dot.line());
        assertIntEquals(2, dot.column());

        Token assign = lexer.nextToken();
        assertByteEquals(constants.ASSIGN, assign.kind());
        assertStringEquals("=", assign.text());
        assertIntEquals(1, assign.line());
        assertIntEquals(3, assign.column());

        assertByteEquals(constants.EOT, lexer.nextToken().kind());
    }

    public void testRelationalOperator() throws Exception {
        String input = "< > == <= >= !=";
        Lexer lexer = new Lexer((Reader) new StringReader(input));

        Token lt = lexer.nextToken();
        assertByteEquals(constants.LT, lt.kind());
        assertStringEquals("<", lt.text());
        assertIntEquals(1, lt.line());
        assertIntEquals(0, lt.column());

        Token gt = lexer.nextToken();
        assertByteEquals(constants.GT, gt.kind());
        assertStringEquals(">", gt.text());
        assertIntEquals(1, gt.line());
        assertIntEquals(2, gt.column());

        Token eq = lexer.nextToken();
        assertByteEquals(constants.EQ, eq.kind());
        assertStringEquals("==", eq.text());
        assertIntEquals(1, eq.line());
        assertIntEquals(4, eq.column());

        Token lteq = lexer.nextToken();
        assertByteEquals(constants.LTEQ, lteq.kind());
        assertStringEquals("<=", lteq.text());
        assertIntEquals(1, lteq.line());
        assertIntEquals(7, lteq.column());

        Token gteq = lexer.nextToken();
        assertByteEquals(constants.GTEQ, gteq.kind());
        assertStringEquals(">=", gteq.text());
        assertIntEquals(1, gteq.line());
        assertIntEquals(10, gteq.column());

        Token neq = lexer.nextToken();
        assertByteEquals(constants.NEQ, neq.kind());
        assertStringEquals("!=", neq.text());
        assertIntEquals(1, neq.line());
        assertIntEquals(13, neq.column());

        assertByteEquals(constants.EOT, lexer.nextToken().kind());
    }

    public void testArithmeticOperator() throws Exception {
        String input = "+ - * / & | ^ % ++ --";
        Lexer lexer = new Lexer((Reader) new StringReader(input));

        Token plus = lexer.nextToken();
        assertByteEquals(constants.PLUS, plus.kind());
        assertStringEquals("+", plus.text());
        assertIntEquals(1, plus.line());
        assertIntEquals(0, plus.column());

        Token minus = lexer.nextToken();
        assertByteEquals(constants.MINUS, minus.kind());
        assertStringEquals("-", minus.text());
        assertIntEquals(1, minus.line());
        assertIntEquals(2, minus.column());

        Token star = lexer.nextToken();
        assertByteEquals(constants.STAR, star.kind());
        assertStringEquals("*", star.text());
        assertIntEquals(1, star.line());
        assertIntEquals(4, star.column());

        Token div = lexer.nextToken();
        assertByteEquals(constants.DIV, div.kind());
        assertStringEquals("/", div.text());
        assertIntEquals(1, div.line());
        assertIntEquals(6, div.column());

        Token and = lexer.nextToken();
        assertByteEquals(constants.AND, and.kind());
        assertStringEquals("&", and.text());
        assertIntEquals(1, and.line());
        assertIntEquals(8, and.column());

        Token or = lexer.nextToken();
        assertByteEquals(constants.OR, or.kind());
        assertStringEquals("|", or.text());
        assertIntEquals(1, or.line());
        assertIntEquals(10, or.column());

        Token xor = lexer.nextToken();
        assertByteEquals(constants.XOR, xor.kind());
        assertStringEquals("^", xor.text());
        assertIntEquals(1, xor.line());
        assertIntEquals(12, xor.column());

        Token mod = lexer.nextToken();
        assertByteEquals(constants.MOD, mod.kind());
        assertStringEquals("%", mod.text());
        assertIntEquals(1, mod.line());
        assertIntEquals(14, mod.column());

        Token plusplus = lexer.nextToken();
        assertByteEquals(constants.PLUS_PLUS, plusplus.kind());
        assertStringEquals("++", plusplus.text());
        assertIntEquals(1, plusplus.line());
        assertIntEquals(16, plusplus.column());

        Token minusminus = lexer.nextToken();
        assertByteEquals(constants.MINUS_MINUS, minusminus.kind());
        assertStringEquals("--", minusminus.text());
        assertIntEquals(1, minusminus.line());
        assertIntEquals(19, minusminus.column());

        assertByteEquals(constants.EOT, lexer.nextToken().kind());
    }

    public void testLogicalOperators() throws Exception {
        String input = "! && ||";
        Lexer lexer = new Lexer((Reader) new StringReader(input));

        Token complement = lexer.nextToken();
        assertByteEquals(constants.COMPLEMENT, complement.kind());
        assertStringEquals("!", complement.text());
        assertIntEquals(1, complement.line());
        assertIntEquals(0, complement.column());

        Token andand = lexer.nextToken();
        assertByteEquals(constants.AND_AND, andand.kind());
        assertStringEquals("&&", andand.text());
        assertIntEquals(1, andand.line());
        assertIntEquals(2, andand.column());

        Token oror = lexer.nextToken();
        assertByteEquals(constants.OR_OR, oror.kind());
        assertStringEquals("||", oror.text());
        assertIntEquals(1, oror.line());
        assertIntEquals(5, oror.column());

        assertByteEquals(constants.EOT, lexer.nextToken().kind());
    }

    public void testIdentifiers() throws Exception {
        String input = "String i3 $ _ MAX_VALUE isLetterOrDigit";
        Lexer lexer = new Lexer((Reader) new StringReader(input));

        Token string = lexer.nextToken();
        assertByteEquals(constants.IDENTIFIER, string.kind());
        assertStringEquals("String", string.text());
        assertIntEquals(1, string.line());
        assertIntEquals(0, string.column());

        Token i3 = lexer.nextToken();
        assertByteEquals(constants.IDENTIFIER, i3.kind());
        assertStringEquals("i3", i3.text());
        assertIntEquals(1, i3.line());
        assertIntEquals(7, i3.column());

        Token $ = lexer.nextToken();
        assertByteEquals(constants.IDENTIFIER, $.kind());
        assertStringEquals("$", $.text());
        assertIntEquals(1, $.line());
        assertIntEquals(10, $.column());

        Token _ = lexer.nextToken();
        assertByteEquals(constants.IDENTIFIER, _.kind());
        assertStringEquals("_", _.text());
        assertIntEquals(1, _.line());
        assertIntEquals(12, _.column());

        Token MAX_VALUE = lexer.nextToken();
        assertByteEquals(constants.IDENTIFIER, MAX_VALUE.kind());
        assertStringEquals("MAX_VALUE", MAX_VALUE.text());
        assertIntEquals(1, MAX_VALUE.line());
        assertIntEquals(14, MAX_VALUE.column());

        Token isLetterOrDigit = lexer.nextToken();
        assertByteEquals(constants.IDENTIFIER, isLetterOrDigit.kind());
        assertStringEquals("isLetterOrDigit", isLetterOrDigit.text());
        assertIntEquals(1, isLetterOrDigit.line());
        assertIntEquals(24, isLetterOrDigit.column());

        assertByteEquals(constants.EOT, lexer.nextToken().kind());
    }

    // public void testMinimal() throws Exception {
    //     String input = "public class Minimal {}";
    //     Lexer lexer = new Lexer((Reader) new StringReader(input));
    //     TokenConstants kinds = new TokenConstants();

    //     Token token = null;

    //     token = lexer.nextToken();
    //     Assert.assertTrue("public", token.kind() == kinds.PUBLIC);
    //     Assert.assertTrue("public[text]", token.text().equals((Object) "public"));
    //     Assert.assertTrue("public[line]", token.line() == 1);
    //     Assert.assertTrue("public[column]", token.column() == 0);

    //     token = lexer.nextToken();
    //     Assert.assertTrue("class", token.kind() == kinds.CLASS);
    //     Assert.assertTrue("class[text]", token.text().equals((Object) "class"));
    //     Assert.assertTrue("class[line]", token.line() == 1);
    //     Assert.assertTrue("class[column]", token.column() == 7);

    //     token = lexer.nextToken();
    //     Assert.assertTrue("<id>", token.kind() == kinds.IDENTIFIER);
    //     Assert.assertTrue("<id>[text]", token.text().equals((Object) "Minimal"));
    //     Assert.assertTrue("<id>[line]", token.line() == 1);
    //     Assert.assertTrue("<id>[column]", token.column() == 13);

    //     token = lexer.nextToken();
    //     Assert.assertTrue("{", token.kind() == kinds.L_BRACE);
    //     Assert.assertTrue("{[text]", token.text().equals((Object) "{"));
    //     Assert.assertTrue("{[line]", token.line() == 1);
    //     Assert.assertTrue("{[column]", token.column() == 21);

    //     token = lexer.nextToken();
    //     Assert.assertTrue("}", token.kind() == kinds.R_BRACE);
    //     Assert.assertTrue("}[text]", token.text().equals((Object) "}"));
    //     Assert.assertTrue("}[line]", token.line() == 1);
    //     Assert.assertTrue("}[column]", token.column() == 22);

    //     token = lexer.nextToken();
    //     Assert.assertTrue("<eot>", token.kind() == kinds.EOT);
    // }
}
