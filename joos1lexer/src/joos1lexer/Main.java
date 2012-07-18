package joos1lexer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public final class Main {
  public static void main(String[] args) throws IOException {
    TokenConstants constants = new TokenConstants();
    Lexer lexer = new Lexer((Reader) new InputStreamReader(System.in));
    Token tok = null;
    while ((tok = lexer.nextToken()).kind() != constants.EOT) {
      if (tok.kind() == constants.ERROR) {
        System.err.println("lexical error on line " + tok.line() +
                           ", col " + tok.column() + ": " + tok.text());
        System.exit(1);
      }
    }
  }

  public Main() {}
}
