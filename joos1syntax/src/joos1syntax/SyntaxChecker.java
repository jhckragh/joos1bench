package joos1syntax;

import joos1lexer.Lexer;
import joos1lexer.Token;
import joos1lexer.TokenConstants;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public final class SyntaxChecker {
  protected TokenConstants constants;
  protected Token currentToken;
  protected TokenStream tokenStream;

  public SyntaxChecker() {
    constants = new TokenConstants();
  }

  public String init(Reader in) throws IOException {
    Lexer lexer = new Lexer(in);
    tokenStream = new TokenStream();
    Token err = tokenStream.init(lexer);
    if (err != null)
      return "Lexical error on line " + err.line() + ", col " + err.column() +
        ":\n" + err.text();
    else
      return null;
  }

  public void check() {
    currentToken = tokenStream.get();
    checkProgram();
  }

  protected void checkProgram() {
    if (currentToken.kind() == constants.PACKAGE)
      checkPackageDeclaration();
    while (currentToken.kind() == constants.IMPORT)
      checkImportDeclaration();
    checkClassDeclaration();
    accept(constants.EOT);
  }

  protected void checkPackageDeclaration() {
    accept(constants.PACKAGE);
    checkName();
    accept(constants.SEMICOLON);
  }

  protected void checkImportDeclaration() {
    accept(constants.IMPORT);
    accept(constants.IDENTIFIER);
    accept(constants.DOT);
    if (currentToken.kind() == constants.STAR) {
      acceptIt();
      accept(constants.SEMICOLON);
      return;
    } else {
      accept(constants.IDENTIFIER);
    }
    while (currentToken.kind() == constants.DOT) {
      acceptIt();
      if (currentToken.kind() == constants.STAR) {
        acceptIt();
        accept(constants.SEMICOLON);
        return;
      } else {
        accept(constants.IDENTIFIER);
      }
    }
    accept(constants.SEMICOLON);
  }

  protected void checkClassDeclaration() {
    accept(constants.PUBLIC);
    if (currentToken.kind() == constants.FINAL)
      acceptIt();
    if (currentToken.kind() == constants.ABSTRACT)
      acceptIt();
    accept(constants.CLASS);
    accept(constants.IDENTIFIER);
    if (currentToken.kind() == constants.EXTENDS) {
      acceptIt();
      checkName();
    }
    if (currentToken.kind() == constants.IMPLEMENTS) {
      acceptIt();
      checkName();
      while (currentToken.kind() == constants.COMMA) {
        acceptIt();
        checkName();
      }
    }
    checkClassBody();
  }

  protected void checkClassBody() {
    accept(constants.L_BRACE);

    while (isAccess(currentToken)) {
      Token next = tokenStream.lookAhead(0);
      if (next.kind() == constants.ABSTRACT ||
          next.kind() == constants.FINAL ||
          next.kind() == constants.STATIC) {
        checkMethodDeclaration();
      } else {
        if (currentToken.kind() == constants.VOID) {
          // Void method
          acceptIt();
          accept(constants.IDENTIFIER);
          checkFormalParameterList();
          if (currentToken.kind() == constants.THROWS)
            checkThrowsClause();
          checkBlock();
        } else {
          boolean possiblyConstructor = true;
          if (currentToken.kind() == constants.BOOLEAN ||
              currentToken.kind() == constants.BYTE ||
              currentToken.kind() == constants.CHAR ||
              currentToken.kind() == constants.INT ||
              currentToken.kind() == constants.SHORT) {
            acceptIt();
            possiblyConstructor = false;
          } else {
            accept(constants.IDENTIFIER);
            while (currentToken.kind() == constants.DOT) {
              acceptIt();
              accept(constants.IDENTIFIER);
              possiblyConstructor = false;
            }
          }
          if (possiblyConstructor && currentToken.kind() == constants.L_PAREN) {
            // Constructor
            checkFormalParameterList();
            if (currentToken.kind() == constants.THROWS)
              checkThrowsClause();
            checkBlock();
          } else {
            if (currentToken.kind() == constants.L_BRACKET) {
              acceptIt();
              accept(constants.R_BRACKET);
            }
            accept(constants.IDENTIFIER);
            if (currentToken.kind() == constants.L_PAREN) {
              // Method
              checkFormalParameterList();
              if (currentToken.kind() == constants.THROWS)
                checkThrowsClause();
              checkBlock();
            } else {
              // Field
              if (currentToken.kind() == constants.ASSIGN) {
                acceptIt();
                checkExpression();
              }
              accept(constants.SEMICOLON);
            }
          }
        }
      }
    }

    accept(constants.R_BRACE);
  }

  protected void checkExpression() {
    // TODO
  }

  protected void checkStatementExpression() {
    // TODO
  }

  protected void checkMethodDeclaration() {
    if (isAccess(currentToken))
      acceptIt();
    else
      syntaxError("Member declaration must begin with 'public' or 'protected'" +
                  "; found '" + constants.spell(currentToken.kind()) + "'",
                  currentToken.line(), currentToken.column());
    Token mod = null;
    if (isModifier(currentToken)) {
      mod = currentToken;
      acceptIt();
    }
    checkTypeExp();
    accept(constants.IDENTIFIER);
    if (mod != null) {
      if (currentToken.kind() == constants.SEMICOLON ||
          currentToken.kind() == constants.ASSIGN)
        syntaxError("Only methods can have modifiers",
                    mod.line(), mod.column());
    }
    checkFormalParameterList();
    if (currentToken.kind() == constants.THROWS)
      checkThrowsClause();
    if (mod != null && mod.kind() == constants.ABSTRACT)
      accept(constants.SEMICOLON);
    else
      checkBlock();
  }

  protected void checkBlock() {
    accept(constants.L_BRACE);

    boolean done = false;
    while (!done) {
      if (currentToken.kind() == constants.IF ||
          currentToken.kind() == constants.WHILE ||
          currentToken.kind() == constants.FOR ||
          currentToken.kind() == constants.L_BRACE ||
          currentToken.kind() == constants.L_PAREN ||
          currentToken.kind() == constants.SEMICOLON ||
          currentToken.kind() == constants.RETURN ||
          currentToken.kind() == constants.NEW ||
          currentToken.kind() == constants.INTEGER_LITERAL ||
          currentToken.kind() == constants.CHAR_LITERAL ||
          currentToken.kind() == constants.STRING_LITERAL ||
          currentToken.kind() == constants.THIS) {
        checkStatement();
      } else if (currentToken.kind() == constants.VOID ||
                 currentToken.kind() == constants.BOOLEAN ||
                 currentToken.kind() == constants.BYTE ||
                 currentToken.kind() == constants.CHAR ||
                 currentToken.kind() == constants.INT ||
                 currentToken.kind() == constants.SHORT) {
        checkLocalDeclaration();
      } else if (currentToken.kind() == constants.IDENTIFIER) {
        int off = 0;
        while (tokenStream.lookAhead(off).kind() == constants.DOT) {
          off = off + 1;
          if (tokenStream.lookAhead(off).kind() == constants.IDENTIFIER)
            off = off + 1;
        }
        if (tokenStream.lookAhead(off).kind() == constants.L_BRACKET) {
          if (tokenStream.lookAhead(off+1).kind() == constants.R_BRACKET)
            checkLocalDeclaration();
          else
            checkStatement();
        } else if (tokenStream.lookAhead(off).kind() == constants.IDENTIFIER) {
          checkLocalDeclaration();
        } else if (tokenStream.lookAhead(off).kind() == constants.ASSIGN) {
          checkStatement();
        } else {
          syntaxError("Expected statement or local declaration",
                      currentToken.line(), currentToken.column());
        }
      } else {
        done = true;
      }
    }

    accept(constants.R_BRACE);
  }

  protected void checkStatement() {
    if (currentToken.kind() == constants.IF)
      checkIfStatement();
    else if (currentToken.kind() == constants.WHILE)
      checkWhileStatement();
    else if (currentToken.kind() == constants.FOR)
      checkForStatement();
    else if (currentToken.kind() == constants.L_BRACE)
      checkBlock();
    else if (currentToken.kind() == constants.SEMICOLON)
      acceptIt();
    else if (currentToken.kind() == constants.RETURN)
      checkReturnStatement();
    else
      checkExpressionStatement();
  }

  protected void checkIfStatement() {
    accept(constants.IF);
    accept(constants.L_PAREN);
    checkExpression();
    accept(constants.R_PAREN);
    checkStatement();
    if (currentToken.kind() == constants.ELSE) {
      acceptIt();
      checkStatement();
    }
  }

  protected void checkWhileStatement() {
    accept(constants.WHILE);
    accept(constants.L_PAREN);
    checkExpression();
    accept(constants.R_PAREN);
    checkStatement();
  }

  protected void checkForStatement() {
    accept(constants.FOR);
    accept(constants.L_PAREN);
    if (currentToken.kind() != constants.COMMA)
      checkForInitializer();
    accept(constants.SEMICOLON);
    if (currentToken.kind() != constants.COMMA)
      checkExpression();
    accept(constants.SEMICOLON);
    if (currentToken.kind() != constants.R_PAREN)
      checkStatementExpression();
    accept(constants.R_PAREN);
  }

  protected void checkForInitializer() {
    // TODO
  }

  protected void checkReturnStatement() {
    accept(constants.RETURN);
    if (currentToken.kind() != constants.SEMICOLON)
      checkExpression();
    accept(constants.SEMICOLON);
  }

  protected void checkExpressionStatement() {
    checkStatementExpression();
    accept(constants.SEMICOLON);
  }

  protected void checkLocalDeclaration() {
    checkTypeExp();
    accept(constants.IDENTIFIER);
    if (currentToken.kind() == constants.ASSIGN) {
      acceptIt();
      checkExpression();
    }
    accept(constants.SEMICOLON);
  }

  protected void checkThrowsClause() {
    accept(constants.THROWS);
    checkName();
    while (currentToken.kind() == constants.COMMA) {
      acceptIt();
      checkName();
    }
  }

  protected void checkFormalParameterList() {
    accept(constants.L_PAREN);
    if (currentToken.kind() != constants.R_PAREN) {
      checkFormalParameter();
      while (currentToken.kind() == constants.COMMA) {
        acceptIt();
        checkFormalParameter();
      }
    }
    accept(constants.R_PAREN);
  }

  protected void checkFormalParameter() {
    checkTypeExp();
    accept(constants.IDENTIFIER);
  }

  protected void checkTypeExp() {
    if (currentToken.kind() == constants.VOID) {
      acceptIt();
    } else {
      checkType();
      if (currentToken.kind() == constants.L_BRACKET) {
        acceptIt();
        accept(constants.R_BRACKET);
      }
    }
  }

  protected void checkType() {
    if (currentToken.kind() == constants.BOOLEAN ||
        currentToken.kind() == constants.BYTE ||
        currentToken.kind() == constants.CHAR ||
        currentToken.kind() == constants.INT ||
        currentToken.kind() == constants.SHORT) {
      acceptIt();
    } else {
      checkName();
    }
  }

  protected void checkName() {
    accept(constants.IDENTIFIER);
    while (currentToken.kind() == constants.DOT) {
      acceptIt();
      accept(constants.IDENTIFIER);
    }
  }

  protected boolean isAccess(Token t) {
    return t.kind() == constants.PUBLIC || t.kind() == constants.PROTECTED;
  }

  protected boolean isModifier(Token t) {
    return t.kind() == constants.ABSTRACT ||
      t.kind() == constants.FINAL ||
      t.kind() == constants.STATIC;
  }

  protected void accept(byte expected) {
    if (currentToken.kind() == expected)
      acceptIt();
    else
      syntaxError("expected '" + constants.spell(expected) + "' " +
                  "but saw '" + constants.spell(currentToken.kind()) + "'",
                  currentToken.line(), currentToken.column());
  }

  protected void acceptIt() {
    currentToken = tokenStream.get();
  }

  protected void syntaxError(String msg, int line, int col) {
    System.err.println("Syntax error on line " + line + ", col " + col + ":\n" +
                       msg);
    System.exit(1);
  }
}
