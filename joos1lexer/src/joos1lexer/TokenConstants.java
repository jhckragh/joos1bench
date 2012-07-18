package joos1lexer;

public final class TokenConstants {
  public byte IDENTIFIER      = (byte) 0;
  public byte INTEGER_LITERAL = (byte) 1;
  public byte CHAR_LITERAL    = (byte) 2;
  public byte STRING_LITERAL  = (byte) 3;

  public byte COMPLEMENT      = (byte) 4;
  public byte AND_AND         = (byte) 5;
  public byte OR_OR           = (byte) 6;

  public byte LT              = (byte) 7;
  public byte GT              = (byte) 8;
  public byte EQ              = (byte) 9;
  public byte LTEQ            = (byte) 10;
  public byte GTEQ            = (byte) 11;
  public byte NEQ             = (byte) 12;

  public byte PLUS            = (byte) 13;
  public byte MINUS           = (byte) 14;
  public byte STAR            = (byte) 15;
  public byte DIV             = (byte) 16;
  public byte AND             = (byte) 17;
  public byte OR              = (byte) 18;
  public byte XOR             = (byte) 19;
  public byte MOD             = (byte) 20;
  public byte PLUS_PLUS       = (byte) 21;
  public byte MINUS_MINUS     = (byte) 22;

  public byte L_PAREN         = (byte) 23;
  public byte R_PAREN         = (byte) 24;
  public byte L_BRACE         = (byte) 25;
  public byte R_BRACE         = (byte) 26;
  public byte L_BRACKET       = (byte) 27;
  public byte R_BRACKET       = (byte) 28;

  public byte SEMICOLON       = (byte) 29;
  public byte COMMA           = (byte) 30;
  public byte DOT             = (byte) 31;
  public byte ASSIGN          = (byte) 32;

  public byte ABSTRACT        = (byte) 33;
  public byte BOOLEAN         = (byte) 34;
  public byte BREAK           = (byte) 35;
  public byte BYTE            = (byte) 36;
  public byte CASE            = (byte) 37;
  public byte CATCH           = (byte) 38;
  public byte CHAR            = (byte) 39;
  public byte CLASS           = (byte) 40;
  public byte CONST           = (byte) 41;
  public byte CONTINUE        = (byte) 42;
  public byte DEFAULT         = (byte) 43;
  public byte DO              = (byte) 44;
  public byte DOUBLE          = (byte) 45;
  public byte ELSE            = (byte) 46;
  public byte EXTENDS         = (byte) 47;
  public byte FINAL           = (byte) 48;
  public byte FINALLY         = (byte) 49;
  public byte FLOAT           = (byte) 50;
  public byte FOR             = (byte) 51;
  public byte GOTO            = (byte) 52;
  public byte IF              = (byte) 53;
  public byte IMPLEMENTS      = (byte) 54;
  public byte IMPORT          = (byte) 55;
  public byte INSTANCEOF      = (byte) 56;
  public byte INT             = (byte) 57;
  public byte INTERFACE       = (byte) 58;
  public byte LONG            = (byte) 59;
  public byte NATIVE          = (byte) 60;
  public byte NEW             = (byte) 61;
  public byte PACKAGE         = (byte) 62;
  public byte PRIVATE         = (byte) 63;
  public byte PROTECTED       = (byte) 64;
  public byte PUBLIC          = (byte) 65;
  public byte RETURN          = (byte) 66;
  public byte SHORT           = (byte) 67;
  public byte STATIC          = (byte) 68;
  public byte STRICTFP        = (byte) 69;
  public byte SUPER           = (byte) 70;
  public byte SWITCH          = (byte) 71;
  public byte SYNCHRONIZED    = (byte) 72;
  public byte THIS            = (byte) 73;
  public byte THROW           = (byte) 74;
  public byte THROWS          = (byte) 75;
  public byte TRANSIENT       = (byte) 76;
  public byte TRY             = (byte) 77;
  public byte VOID            = (byte) 78;
  public byte VOLATILE        = (byte) 79;
  public byte WHILE           = (byte) 80;
  public byte TRUE            = (byte) 81;
  public byte FALSE           = (byte) 82;
  public byte NULL            = (byte) 83;

  public byte EOT             = (byte) 84;
  public byte ERROR           = (byte) 85;

  public TokenConstants() {}

  public String spell(byte t) {
    if (t == EOT)
      return "<eot>";
    if (t == ABSTRACT)
      return "abstract";
    if (t == BOOLEAN)
      return "boolean";
    if (t == BREAK)
      return "break";
    if (t == BYTE)
      return "byte";
    if (t == CASE)
      return "case";
    if (t == CATCH)
      return "catch";
    if (t == CHAR)
      return "char";
    if (t == CLASS)
      return "class";
    if (t == CONST)
      return "const";
    if (t == CONTINUE)
      return "continue";
    if (t == DEFAULT)
      return "default";
    if (t == DO)
      return "do";
    if (t == DOUBLE)
      return "double";
    if (t == ELSE)
      return "else";
    if (t == EXTENDS)
      return "extends";
    if (t == FINAL)
      return "final";
    if (t == FINALLY)
      return "finally";
    if (t == FLOAT)
      return "float";
    if (t == FOR)
      return "for";
    if (t == GOTO)
      return "goto";
    if (t == IF)
      return "if";
    if (t == IMPLEMENTS)
      return "implements";
    if (t == IMPORT)
      return "import";
    if (t == INSTANCEOF)
      return "instanceof";
    if (t == INT)
      return "int";
    if (t == INTERFACE)
      return "interface";
    if (t == LONG)
      return "long";
    if (t == NATIVE)
      return "native";
    if (t == NEW)
      return "new";
    if (t == PACKAGE)
      return "package";
    if (t == PRIVATE)
      return "private";
    if (t == PROTECTED)
      return "protected";
    if (t == PUBLIC)
      return "public";
    if (t == RETURN)
      return "return";
    if (t == SHORT)
      return "short";
    if (t == STATIC)
      return "static";
    if (t == STRICTFP)
      return "strictfp";
    if (t == SUPER)
      return "super";
    if (t == SWITCH)
      return "switch";
    if (t == SYNCHRONIZED)
      return "synchronized";
    if (t == THIS)
      return "this";
    if (t == THROW)
      return "throw";
    if (t == THROWS)
      return "throws";
    if (t == TRANSIENT)
      return "transient";
    if (t == TRY)
      return "try";
    if (t == VOID)
      return "void";
    if (t == VOLATILE)
      return "volatile";
    if (t == WHILE)
      return "while";
    if (t == TRUE)
      return "true";
    if (t == FALSE)
      return "false";
    if (t == NULL)
      return "null";
    if (t == L_PAREN)
      return "(";
    if (t == R_PAREN)
      return ")";
    if (t == L_BRACE)
      return "{";
    if (t == R_BRACE)
      return "}";
    if (t == L_BRACKET)
      return "[";
    if (t == R_BRACKET)
      return "]";
    if (t == SEMICOLON)
      return ";";
    if (t == COMMA)
      return ",";
    if (t == DOT)
      return ".";
    if (t == ASSIGN)
      return "=";
    if (t == COMPLEMENT)
      return "!";
    if (t == AND_AND)
      return "&&";
    if (t == OR_OR)
      return "||";
    if (t == LT)
      return "<";
    if (t == GT)
      return ">";
    if (t == EQ)
      return "==";
    if (t == LTEQ)
      return "<=";
    if (t == GTEQ)
      return ">=";
    if (t == NEQ)
      return "!=";
    if (t == PLUS)
      return "+";
    if (t == MINUS)
      return "-";
    if (t == STAR)
      return "*";
    if (t == DIV)
      return "/";
    if (t == AND)
      return "&";
    if (t == OR)
      return "|";
    if (t == XOR)
      return "^";
    if (t == MOD)
      return "%";
    if (t == PLUS_PLUS)
      return "++";
    if (t == MINUS_MINUS)
      return "--";
    if (t == INTEGER_LITERAL)
      return "INTEGER_LITERAL";
    if (t == CHAR_LITERAL)
      return "CHAR_LITERAL";
    if (t == STRING_LITERAL)
      return "STRING_LITERAL";
    if (t == IDENTIFIER)
      return "IDENTIFIER";

    return "INTERNAL ERROR OCCURRED";
  }
}
