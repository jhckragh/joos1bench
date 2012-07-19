package joos1syntax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public final class Main {
  public static void main(String[] args) throws IOException {
    SyntaxChecker sc = new SyntaxChecker();
    String err = sc.init((Reader) new InputStreamReader(System.in));
    if (err != null) {
      System.err.println(err);
      System.exit(1);
    } else {
      sc.check();
    }
  }

  public Main() {}
}
