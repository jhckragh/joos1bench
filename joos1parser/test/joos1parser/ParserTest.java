package joos1parser;

import joos1lexer.Lexer;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import java.io.Reader;
import java.io.StringReader;

public class ParserTest extends TestCase {
  public ParserTest() {}

  protected void assertStringEquals(String expected, String actual) {
    Assert.assertEquals((Object) expected, (Object) actual);
  }

  public void testBareMinimum() throws Exception {
    String input = "public class Foo { public Foo() {} }";
    Lexer lexer = new Lexer((Reader) new StringReader(input));
    Parser parser = new Parser(lexer);
    parser.parse();
    Assert.assertNull((Object) parser.error());
  }
}
