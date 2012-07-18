package joos1parser;

import joos1lexer.Lexer;
import joos1lexer.Token;
import joos1lexer.TokenConstants;

import java.io.IOException;

public final class Parser {
  protected TokenConstants constants;
  protected Token currentToken;
  protected String error;
  protected Lexer lexer;

  public Parser(Lexer lexer) {
    this.lexer = lexer;
    this.constants = new TokenConstants();
  }

  public String error() {
    return error;
  }

  public void parse() throws IOException {
    currentToken = lexer.nextToken();
    parseProgram();
  }

  protected void parseProgram() throws IOException {
    if (currentToken.kind() == constants.PACKAGE)
      parsePackageDeclaration();
    while (currentToken.kind() == constants.IMPORT)
      parseImportDeclaration();
    parseClassDeclaration();
    accept(constants.EOT);
  }

  protected void parseClassDeclaration() throws IOException {
    accept(constants.PUBLIC);
    if (currentToken.kind() == constants.FINAL)
      acceptIt();
    if (currentToken.kind() == constants.ABSTRACT)
      acceptIt();
    accept(constants.CLASS);
    accept(constants.IDENTIFIER);
    if (currentToken.kind() == constants.EXTENDS) {
      acceptIt();
      parseName();
    }
    if (currentToken.kind() == constants.IMPLEMENTS) {
      acceptIt();
      parseName();
      while (currentToken.kind() == constants.COMMA) {
        acceptIt();
        parseName();
      }
    }
    parseClassBody();
  }

  protected void parseClassBody() throws IOException {
    accept(constants.L_BRACE);
    // TODO
    accept(constants.R_BRACE);
  }

  protected void parsePackageDeclaration() throws IOException {
    accept(constants.PACKAGE);
    parseName();
    accept(constants.SEMICOLON);
  }

  protected void parseImportDeclaration() throws IOException {
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

  protected void parseName() throws IOException {
    accept(constants.IDENTIFIER);
    while (currentToken.kind() == constants.DOT) {
      acceptIt();
      accept(constants.IDENTIFIER);
    }
  }

  protected boolean isPrimitiveType(byte kind) {
    return kind == constants.BOOLEAN ||
      kind == constants.BYTE ||
      kind == constants.CHAR ||
      kind == constants.INT ||
      kind == constants.SHORT;
  }

  protected void accept(byte expected) throws IOException {
    if (currentToken.kind() == expected)
      acceptIt();
    else
      error = "syntax error at " +
        "line " + currentToken.line() + ", col " + currentToken.column() +
        ": expected '" + constants.spell(expected) + "' " +
        "but saw '" + constants.spell(currentToken.kind()) + "'";
  }

  protected void acceptIt() throws IOException {
    currentToken = lexer.nextToken();
  }
}
