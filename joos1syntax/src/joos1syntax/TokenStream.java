package joos1syntax;

import joos1lexer.Lexer;
import joos1lexer.Token;
import joos1lexer.TokenConstants;

import java.io.IOException;
import java.util.LinkedList;

public final class TokenStream {
  protected TokenConstants constants;
  protected LinkedList deque;
  protected Token eot;

  public TokenStream() {
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

  public Token lookAhead(int offset) {
    if (offset >= deque.size())
      return eot;
    else
      return (Token) deque.get(offset);
  }
}
