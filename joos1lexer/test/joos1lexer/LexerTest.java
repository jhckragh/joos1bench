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

    public void testKeywords() throws Exception {
        String input = "abstract boolean break byte case catch char " +
            "class const continue default do double else extends final " +
            "finally float for goto if implements import instanceof int " +
            "interface long native new package private protected public " +
            "return short static strictfp super switch synchronized this " +
            "throw throws transient try void volatile while true false null";
        Lexer lexer = new Lexer((Reader) new StringReader(input));

        Token _abstract = lexer.nextToken();
        assertByteEquals(constants.ABSTRACT, _abstract.kind());
        assertStringEquals("abstract", _abstract.text());
        assertIntEquals(1, _abstract.line());
        assertIntEquals(0, _abstract.column());

        Token _boolean = lexer.nextToken();
        assertByteEquals(constants.BOOLEAN, _boolean.kind());
        assertStringEquals("boolean", _boolean.text());
        assertIntEquals(1, _boolean.line());
        assertIntEquals(9, _boolean.column());

        Token _break = lexer.nextToken();
        assertByteEquals(constants.BREAK, _break.kind());
        assertStringEquals("break", _break.text());
        assertIntEquals(1, _break.line());
        assertIntEquals(17, _break.column());

        Token _byte = lexer.nextToken();
        assertByteEquals(constants.BYTE, _byte.kind());
        assertStringEquals("byte", _byte.text());
        assertIntEquals(1, _byte.line());
        assertIntEquals(23, _byte.column());

        Token _case = lexer.nextToken();
        assertByteEquals(constants.CASE, _case.kind());
        assertStringEquals("case", _case.text());
        assertIntEquals(1, _case.line());
        assertIntEquals(28, _case.column());

        Token _catch = lexer.nextToken();
        assertByteEquals(constants.CATCH, _catch.kind());
        assertStringEquals("catch", _catch.text());
        assertIntEquals(1, _catch.line());
        assertIntEquals(33, _catch.column());

        Token _char = lexer.nextToken();
        assertByteEquals(constants.CHAR, _char.kind());
        assertStringEquals("char", _char.text());
        assertIntEquals(1, _char.line());
        assertIntEquals(39, _char.column());

        Token _class = lexer.nextToken();
        assertByteEquals(constants.CLASS, _class.kind());
        assertStringEquals("class", _class.text());
        assertIntEquals(1, _class.line());
        assertIntEquals(44, _class.column());

        Token _const = lexer.nextToken();
        assertByteEquals(constants.CONST, _const.kind());
        assertStringEquals("const", _const.text());
        assertIntEquals(1, _const.line());
        assertIntEquals(50, _const.column());

        Token _continue = lexer.nextToken();
        assertByteEquals(constants.CONTINUE, _continue.kind());
        assertStringEquals("continue", _continue.text());
        assertIntEquals(1, _continue.line());
        assertIntEquals(56, _continue.column());

        Token _default = lexer.nextToken();
        assertByteEquals(constants.DEFAULT, _default.kind());
        assertStringEquals("default", _default.text());
        assertIntEquals(1, _default.line());
        assertIntEquals(65, _default.column());

        Token _do = lexer.nextToken();
        assertByteEquals(constants.DO, _do.kind());
        assertStringEquals("do", _do.text());
        assertIntEquals(1, _do.line());
        assertIntEquals(73, _do.column());

        Token _double = lexer.nextToken();
        assertByteEquals(constants.DOUBLE, _double.kind());
        assertStringEquals("double", _double.text());
        assertIntEquals(1, _double.line());
        assertIntEquals(76, _double.column());

        Token _else = lexer.nextToken();
        assertByteEquals(constants.ELSE, _else.kind());
        assertStringEquals("else", _else.text());
        assertIntEquals(1, _else.line());
        assertIntEquals(83, _else.column());

        Token _extends = lexer.nextToken();
        assertByteEquals(constants.EXTENDS, _extends.kind());
        assertStringEquals("extends", _extends.text());
        assertIntEquals(1, _extends.line());
        assertIntEquals(88, _extends.column());

        Token _final = lexer.nextToken();
        assertByteEquals(constants.FINAL, _final.kind());
        assertStringEquals("final", _final.text());
        assertIntEquals(1, _final.line());
        assertIntEquals(96, _final.column());

        Token _finally = lexer.nextToken();
        assertByteEquals(constants.FINALLY, _finally.kind());
        assertStringEquals("finally", _finally.text());
        assertIntEquals(1, _finally.line());
        assertIntEquals(102, _finally.column());

        Token _float = lexer.nextToken();
        assertByteEquals(constants.FLOAT, _float.kind());
        assertStringEquals("float", _float.text());
        assertIntEquals(1, _float.line());
        assertIntEquals(110, _float.column());

        Token _for = lexer.nextToken();
        assertByteEquals(constants.FOR, _for.kind());
        assertStringEquals("for", _for.text());
        assertIntEquals(1, _for.line());
        assertIntEquals(116, _for.column());

        Token _goto = lexer.nextToken();
        assertByteEquals(constants.GOTO, _goto.kind());
        assertStringEquals("goto", _goto.text());
        assertIntEquals(1, _goto.line());
        assertIntEquals(120, _goto.column());

        Token _if = lexer.nextToken();
        assertByteEquals(constants.IF, _if.kind());
        assertStringEquals("if", _if.text());
        assertIntEquals(1, _if.line());
        assertIntEquals(125, _if.column());

        Token _implements = lexer.nextToken();
        assertByteEquals(constants.IMPLEMENTS, _implements.kind());
        assertStringEquals("implements", _implements.text());
        assertIntEquals(1, _implements.line());
        assertIntEquals(128, _implements.column());

        Token _import = lexer.nextToken();
        assertByteEquals(constants.IMPORT, _import.kind());
        assertStringEquals("import", _import.text());
        assertIntEquals(1, _import.line());
        assertIntEquals(139, _import.column());

        Token _instanceof = lexer.nextToken();
        assertByteEquals(constants.INSTANCEOF, _instanceof.kind());
        assertStringEquals("instanceof", _instanceof.text());
        assertIntEquals(1, _instanceof.line());
        assertIntEquals(146, _instanceof.column());

        Token _int = lexer.nextToken();
        assertByteEquals(constants.INT, _int.kind());
        assertStringEquals("int", _int.text());
        assertIntEquals(1, _int.line());
        assertIntEquals(157, _int.column());

        Token _interface = lexer.nextToken();
        assertByteEquals(constants.INTERFACE, _interface.kind());
        assertStringEquals("interface", _interface.text());
        assertIntEquals(1, _interface.line());
        assertIntEquals(161, _interface.column());

        Token _long = lexer.nextToken();
        assertByteEquals(constants.LONG, _long.kind());
        assertStringEquals("long", _long.text());
        assertIntEquals(1, _long.line());
        assertIntEquals(171, _long.column());

        Token _native = lexer.nextToken();
        assertByteEquals(constants.NATIVE, _native.kind());
        assertStringEquals("native", _native.text());
        assertIntEquals(1, _native.line());
        assertIntEquals(176, _native.column());

        Token _new = lexer.nextToken();
        assertByteEquals(constants.NEW, _new.kind());
        assertStringEquals("new", _new.text());
        assertIntEquals(1, _new.line());
        assertIntEquals(183, _new.column());

        Token _package = lexer.nextToken();
        assertByteEquals(constants.PACKAGE, _package.kind());
        assertStringEquals("package", _package.text());
        assertIntEquals(1, _package.line());
        assertIntEquals(187, _package.column());

        Token _private = lexer.nextToken();
        assertByteEquals(constants.PRIVATE, _private.kind());
        assertStringEquals("private", _private.text());
        assertIntEquals(1, _private.line());
        assertIntEquals(195, _private.column());

        Token _protected = lexer.nextToken();
        assertByteEquals(constants.PROTECTED, _protected.kind());
        assertStringEquals("protected", _protected.text());
        assertIntEquals(1, _protected.line());
        assertIntEquals(203, _protected.column());

        Token _public = lexer.nextToken();
        assertByteEquals(constants.PUBLIC, _public.kind());
        assertStringEquals("public", _public.text());
        assertIntEquals(1, _public.line());
        assertIntEquals(213, _public.column());

        Token _return = lexer.nextToken();
        assertByteEquals(constants.RETURN, _return.kind());
        assertStringEquals("return", _return.text());
        assertIntEquals(1, _return.line());
        assertIntEquals(220, _return.column());

        Token _short = lexer.nextToken();
        assertByteEquals(constants.SHORT, _short.kind());
        assertStringEquals("short", _short.text());
        assertIntEquals(1, _short.line());
        assertIntEquals(227, _short.column());

        Token _static = lexer.nextToken();
        assertByteEquals(constants.STATIC, _static.kind());
        assertStringEquals("static", _static.text());
        assertIntEquals(1, _static.line());
        assertIntEquals(233, _static.column());

        Token _strictfp = lexer.nextToken();
        assertByteEquals(constants.STRICTFP, _strictfp.kind());
        assertStringEquals("strictfp", _strictfp.text());
        assertIntEquals(1, _strictfp.line());
        assertIntEquals(240, _strictfp.column());

        Token _super = lexer.nextToken();
        assertByteEquals(constants.SUPER, _super.kind());
        assertStringEquals("super", _super.text());
        assertIntEquals(1, _super.line());
        assertIntEquals(249, _super.column());

        Token _switch = lexer.nextToken();
        assertByteEquals(constants.SWITCH, _switch.kind());
        assertStringEquals("switch", _switch.text());
        assertIntEquals(1, _switch.line());
        assertIntEquals(255, _switch.column());

        Token _synchronized = lexer.nextToken();
        assertByteEquals(constants.SYNCHRONIZED, _synchronized.kind());
        assertStringEquals("synchronized", _synchronized.text());
        assertIntEquals(1, _synchronized.line());
        assertIntEquals(262, _synchronized.column());

        Token _this = lexer.nextToken();
        assertByteEquals(constants.THIS, _this.kind());
        assertStringEquals("this", _this.text());
        assertIntEquals(1, _this.line());
        assertIntEquals(275, _this.column());

        Token _throw = lexer.nextToken();
        assertByteEquals(constants.THROW, _throw.kind());
        assertStringEquals("throw", _throw.text());
        assertIntEquals(1, _throw.line());
        assertIntEquals(280, _throw.column());

        Token _throws = lexer.nextToken();
        assertByteEquals(constants.THROWS, _throws.kind());
        assertStringEquals("throws", _throws.text());
        assertIntEquals(1, _throws.line());
        assertIntEquals(286, _throws.column());

        Token _transient = lexer.nextToken();
        assertByteEquals(constants.TRANSIENT, _transient.kind());
        assertStringEquals("transient", _transient.text());
        assertIntEquals(1, _transient.line());
        assertIntEquals(293, _transient.column());

        Token _try = lexer.nextToken();
        assertByteEquals(constants.TRY, _try.kind());
        assertStringEquals("try", _try.text());
        assertIntEquals(1, _try.line());
        assertIntEquals(303, _try.column());

        Token _void = lexer.nextToken();
        assertByteEquals(constants.VOID, _void.kind());
        assertStringEquals("void", _void.text());
        assertIntEquals(1, _void.line());
        assertIntEquals(307, _void.column());

        Token _volatile = lexer.nextToken();
        assertByteEquals(constants.VOLATILE, _volatile.kind());
        assertStringEquals("volatile", _volatile.text());
        assertIntEquals(1, _volatile.line());
        assertIntEquals(312, _volatile.column());

        Token _while = lexer.nextToken();
        assertByteEquals(constants.WHILE, _while.kind());
        assertStringEquals("while", _while.text());
        assertIntEquals(1, _while.line());
        assertIntEquals(321, _while.column());

        Token _true = lexer.nextToken();
        assertByteEquals(constants.TRUE, _true.kind());
        assertStringEquals("true", _true.text());
        assertIntEquals(1, _true.line());
        assertIntEquals(327, _true.column());

        Token _false = lexer.nextToken();
        assertByteEquals(constants.FALSE, _false.kind());
        assertStringEquals("false", _false.text());
        assertIntEquals(1, _false.line());
        assertIntEquals(332, _false.column());

        Token _null = lexer.nextToken();
        assertByteEquals(constants.NULL, _null.kind());
        assertStringEquals("null", _null.text());
        assertIntEquals(1, _null.line());
        assertIntEquals(338, _null.column());

        assertByteEquals(constants.EOT, lexer.nextToken().kind());
    }

    public void testIntegerLiteral() throws Exception {
        String input = "0 2 2147483647 1996\n11";
        Lexer lexer = new Lexer((Reader) new StringReader(input));

        Token zero = lexer.nextToken();
        assertByteEquals(constants.INTEGER_LITERAL, zero.kind());
        assertStringEquals("0", zero.text());
        assertIntEquals(1, zero.line());
        assertIntEquals(0, zero.column());

        Token two = lexer.nextToken();
        assertByteEquals(constants.INTEGER_LITERAL, two.kind());
        assertStringEquals("2", two.text());
        assertIntEquals(1, two.line());
        assertIntEquals(2, two.column());

        Token elGordo = lexer.nextToken();
        assertByteEquals(constants.INTEGER_LITERAL, elGordo.kind());
        assertStringEquals("2147483647", elGordo.text());
        assertIntEquals(1, elGordo.line());
        assertIntEquals(4, elGordo.column());

        Token nineteenNinetySix = lexer.nextToken();
        assertByteEquals(constants.INTEGER_LITERAL, nineteenNinetySix.kind());
        assertStringEquals("1996", nineteenNinetySix.text());
        assertIntEquals(1, nineteenNinetySix.line());
        assertIntEquals(15, nineteenNinetySix.column());

        Token eleven = lexer.nextToken();
        assertByteEquals(constants.INTEGER_LITERAL, eleven.kind());
        assertStringEquals("11", eleven.text());
        assertIntEquals(2, eleven.line());
        assertIntEquals(0, eleven.column());

        assertByteEquals(constants.EOT, lexer.nextToken().kind());
    }

    public void testMinimal() throws Exception {
        String input = "public\nclass\nMinimal {}";
        Lexer lexer = new Lexer((Reader) new StringReader(input));

        Token _public = lexer.nextToken();
        assertByteEquals(constants.PUBLIC, _public.kind());
        assertStringEquals("public", _public.text());
        assertIntEquals(1, _public.line());
        assertIntEquals(0, _public.column());

        Token _class = lexer.nextToken();
        assertByteEquals(constants.CLASS, _class.kind());
        assertStringEquals("class", _class.text());
        assertIntEquals(2, _class.line());
        assertIntEquals(0, _class.column());

        Token minimal = lexer.nextToken();
        assertByteEquals(constants.IDENTIFIER, minimal.kind());
        assertStringEquals("Minimal", minimal.text());
        assertIntEquals(3, minimal.line());
        assertIntEquals(0, minimal.column());

        Token lbrace = lexer.nextToken();
        assertByteEquals(constants.L_BRACE, lbrace.kind());
        assertStringEquals("{", lbrace.text());
        assertIntEquals(3, lbrace.line());
        assertIntEquals(8, lbrace.column());

        Token rbrace = lexer.nextToken();
        assertByteEquals(constants.R_BRACE, rbrace.kind());
        assertStringEquals("}", rbrace.text());
        assertIntEquals(3, rbrace.line());
        assertIntEquals(9, rbrace.column());

        assertByteEquals(constants.EOT, lexer.nextToken().kind());
    }
}
