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
  protected PushbackTokenStream tokenStream;

  public SyntaxChecker() {
    constants = new TokenConstants();
  }

  public String init(Reader in) throws IOException {
    Lexer lexer = new Lexer(in);
    tokenStream = new PushbackTokenStream();
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
      Token access = currentToken;
      acceptIt();
      if (currentToken.kind() == constants.ABSTRACT ||
          currentToken.kind() == constants.FINAL ||
          currentToken.kind() == constants.STATIC) {
        tokenStream.unget(currentToken);
        currentToken = access;
        checkMethodDeclaration();
      } else {
        boolean possiblyConstructor = true;
        if (currentToken.kind() == constants.VOID) {
          acceptIt();
          possiblyConstructor = false;
        } else {
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
    // TODO
    accept(constants.R_BRACE);
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
