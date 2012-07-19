package joos1syntax;

import joos1lexer.Lexer;
import joos1lexer.Token;
import joos1lexer.TokenConstants;

import java.io.IOException;
import java.util.LinkedList;

public final class PushbackTokenStream {
  protected TokenConstants constants;
  protected LinkedList deque;
  protected Token eot;

  public PushbackTokenStream() {
    constants = new TokenConstants();
  }

  public Token get() {
    if (deque.isEmpty())
      return eot;
    else
      return (Token) deque.removeFirst();
  }

  public Token init(Lexer lexer) throws IOException {
    deque = new LinkedList();
    Token t = null;
    while ((t = lexer.nextToken()).kind() != constants.EOT) {
      if (t.kind() == constants.ERROR)
        return t;
      else
        deque.addLast((Object) t);
    }
    eot = t;
    return null;
  }

  public void unget(Token t) {
    deque.addFirst((Object) t);
  }
}
