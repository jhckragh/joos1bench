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

    public void testBareMinimum() throws Exception {
        String input = "{";
        Lexer lexer = new Lexer((Reader) new StringReader(input));

        Token lbrace = lexer.nextToken();
        Assert.assertTrue(" { [kind]", lbrace.kind() == constants.L_BRACE);
        Assert.assertTrue(" { [text]", lbrace.text().equals((Object) "{"));
        Assert.assertTrue(" { [line]", lbrace.line() == 1);
        Assert.assertTrue(" { [column: " + lbrace.column() + "]", lbrace.column() == 0);

        Token eot = lexer.nextToken();
        Assert.assertTrue("<eot>", eot.kind() == constants.EOT);
    }

    public void testDelimiters() throws Exception {
        String input = "{}[]()";
        Lexer lexer = new Lexer((Reader) new StringReader(input));

        Token lbrace = lexer.nextToken();
        Assert.assertTrue(" { [kind]", lbrace.kind() == constants.L_BRACE);
        Assert.assertTrue(" { [text]", lbrace.text().equals((Object) "{"));
        Assert.assertTrue(" { [line]", lbrace.line() == 1);
        Assert.assertTrue(" { [column]", lbrace.column() == 0);

        Token rbrace = lexer.nextToken();
        Assert.assertTrue(" } [kind]", rbrace.kind() == constants.R_BRACE);
        Assert.assertTrue(" } [text]", rbrace.text().equals((Object) "}"));
        Assert.assertTrue(" } [line]", rbrace.line() == 1);
        Assert.assertTrue(" } [column]", rbrace.column() == 1);

        Token lbracket = lexer.nextToken();
        Assert.assertTrue(" [ [kind]", lbracket.kind() == constants.L_BRACKET);
        Assert.assertTrue(" [ [text]", lbracket.text().equals((Object) "["));
        Assert.assertTrue(" [ [line]", lbracket.line() == 1);
        Assert.assertTrue(" [ [column]", lbracket.column() == 2);

        Token rbracket = lexer.nextToken();
        Assert.assertTrue(" ] [kind]", rbracket.kind() == constants.R_BRACKET);
        Assert.assertTrue(" ] [text]", rbracket.text().equals((Object) "]"));
        Assert.assertTrue(" ] [line]", rbracket.line() == 1);
        Assert.assertTrue(" ] [column]", rbracket.column() == 3);

        Token lparen = lexer.nextToken();
        Assert.assertTrue(" ( [kind]", lparen.kind() == constants.L_PAREN);
        Assert.assertTrue(" ( [text]", lparen.text().equals((Object) "("));
        Assert.assertTrue(" ( [line]", lparen.line() == 1);
        Assert.assertTrue(" ( [column]", lparen.column() == 4);

        Token rparen = lexer.nextToken();
        Assert.assertTrue(" ) [kind]", rparen.kind() == constants.R_PAREN);
        Assert.assertTrue(" ) [text]", rparen.text().equals((Object) ")"));
        Assert.assertTrue(" ) [line]", rparen.line() == 1);
        Assert.assertTrue(" ) [column]", rparen.column() == 5);

        Token eot = lexer.nextToken();
        Assert.assertTrue("<eot>", eot.kind() == constants.EOT);
    }

    public void testPunctuation() throws Exception {
        String input = ";,.=";
        Lexer lexer = new Lexer((Reader) new StringReader(input));

        Token semi = lexer.nextToken();
        Assert.assertTrue(" ; [kind]", semi.kind() == constants.SEMICOLON);
        Assert.assertTrue(" ; [text]", semi.text().equals((Object) ";"));
        Assert.assertTrue(" ; [line]", semi.line() == 1);
        Assert.assertTrue(" ; [column]", semi.column() == 0);

        Token comma = lexer.nextToken();
        Assert.assertTrue(" , [kind]", comma.kind() == constants.COMMA);
        Assert.assertTrue(" , [text]", comma.text().equals((Object) ","));
        Assert.assertTrue(" , [line]", comma.line() == 1);
        Assert.assertTrue(" , [column]", comma.column() == 1);

        Token dot = lexer.nextToken();
        Assert.assertTrue(" . [kind]", dot.kind() == constants.DOT);
        Assert.assertTrue(" . [text]", dot.text().equals((Object) "."));
        Assert.assertTrue(" . [line]", dot.line() == 1);
        Assert.assertTrue(" . [column]", dot.column() == 2);

        Token assign = lexer.nextToken();
        Assert.assertTrue(" = [kind]", assign.kind() == constants.ASSIGN);
        Assert.assertTrue(" = [text]", assign.text().equals((Object) "="));
        Assert.assertTrue(" = [line]", assign.line() == 1);
        Assert.assertTrue(" = [column]", assign.column() == 3);

        Token eot = lexer.nextToken();
        Assert.assertTrue("<eot>", eot.kind() == constants.EOT);
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
