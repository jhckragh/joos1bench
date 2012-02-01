package joos1lexer;

public final class Token {
    protected int column;
    protected byte kind;
    protected int line;
    protected String text;

    public Token(byte kind, String text, int line, int column) {
        this.kind = kind;
        this.text = text;
        this.line = line;
        this.column = column;
    }

    public int column() {
        return column;
    }

    public byte kind() {
        return kind;
    }

    public int line() {
        return line;
    }

    public String text() {
        return text;
    }
}
