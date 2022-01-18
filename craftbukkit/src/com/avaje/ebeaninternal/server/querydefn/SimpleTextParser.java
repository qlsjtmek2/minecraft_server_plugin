// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.querydefn;

public class SimpleTextParser
{
    private final String oql;
    private final char[] chars;
    private final int eof;
    private int pos;
    private String word;
    private String lowerWord;
    private int openParenthesisCount;
    
    public SimpleTextParser(final String oql) {
        this.oql = oql;
        this.chars = oql.toCharArray();
        this.eof = oql.length();
    }
    
    public String getOql() {
        return this.oql;
    }
    
    public String getWord() {
        return this.word;
    }
    
    public String peekNextWord() {
        final int origPos = this.pos;
        final String nw = this.nextWordInternal();
        this.pos = origPos;
        return nw;
    }
    
    public boolean isMatch(final String lowerMatch, final String nextWordMatch) {
        if (this.isMatch(lowerMatch)) {
            String nw = this.peekNextWord();
            if (nw != null) {
                nw = nw.toLowerCase();
                return nw.equals(nextWordMatch);
            }
        }
        return false;
    }
    
    public boolean isFinished() {
        return this.word == null;
    }
    
    public int findWordLower(final String lowerMatch, final int afterPos) {
        this.pos = afterPos;
        return this.findWordLower(lowerMatch);
    }
    
    public int findWordLower(final String lowerMatch) {
        while (this.nextWord() != null) {
            if (lowerMatch.equals(this.lowerWord)) {
                return this.pos - this.lowerWord.length();
            }
        }
        return -1;
    }
    
    public boolean isMatch(final String lowerMatch) {
        return lowerMatch.equals(this.lowerWord);
    }
    
    public String nextWord() {
        this.word = this.nextWordInternal();
        if (this.word != null) {
            this.lowerWord = this.word.toLowerCase();
        }
        return this.word;
    }
    
    private String nextWordInternal() {
        this.trimLeadingWhitespace();
        if (this.pos >= this.eof) {
            return null;
        }
        final int start = this.pos;
        if (this.chars[this.pos] == '(') {
            this.moveToClose();
        }
        else {
            this.moveToEndOfWord();
        }
        return this.oql.substring(start, this.pos);
    }
    
    private void moveToClose() {
        ++this.pos;
        this.openParenthesisCount = 0;
        while (this.pos < this.eof) {
            final char c = this.chars[this.pos];
            if (c == '(') {
                ++this.openParenthesisCount;
            }
            else if (c == ')') {
                if (this.openParenthesisCount <= 0) {
                    ++this.pos;
                    return;
                }
                --this.openParenthesisCount;
            }
            ++this.pos;
        }
    }
    
    private void moveToEndOfWord() {
        char c = this.chars[this.pos];
        final boolean isOperator = this.isOperator(c);
        while (this.pos < this.eof) {
            c = this.chars[this.pos];
            if (this.isWordTerminator(c, isOperator)) {
                return;
            }
            ++this.pos;
        }
    }
    
    private boolean isWordTerminator(final char c, final boolean isOperator) {
        if (Character.isWhitespace(c)) {
            return true;
        }
        if (this.isOperator(c)) {
            return !isOperator;
        }
        return c == '(' || isOperator;
    }
    
    private boolean isOperator(final char c) {
        switch (c) {
            case '<': {
                return true;
            }
            case '>': {
                return true;
            }
            case '=': {
                return true;
            }
            case '!': {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    private void trimLeadingWhitespace() {
        while (this.pos < this.eof) {
            final char c = this.chars[this.pos];
            if (!Character.isWhitespace(c)) {
                break;
            }
            ++this.pos;
        }
    }
}
