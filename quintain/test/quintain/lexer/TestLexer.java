package quintain.lexer;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.Reader;
import java.io.StringReader;

public class TestLexer extends TestCase {
    protected TokenConstants kinds = new TokenConstants();

    public static void main(String[] args) {
        new TestLexer().run();
    }

    public TestLexer() {}

    public void runTest() throws Exception {
        testMinimal();
    }

    public void testMinimal() throws Exception {
        String example = "public\tclass Minimal {}\n";
        Lexer lexer = new Lexer((Reader) new StringReader(example));
        Assert.assertTrue(kinds.PUBLIC == lexer.nextToken().kind());
        Assert.assertTrue(kinds.CLASS == lexer.nextToken().kind());
        Assert.assertTrue(kinds.IDENTIFIER == lexer.nextToken().kind());
        Assert.assertTrue(kinds.L_BRACE == lexer.nextToken().kind());
        Assert.assertTrue(kinds.R_BRACE == lexer.nextToken().kind());
        Assert.assertTrue(kinds.EOT == lexer.nextToken().kind());
    }
}
