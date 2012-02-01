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
}
