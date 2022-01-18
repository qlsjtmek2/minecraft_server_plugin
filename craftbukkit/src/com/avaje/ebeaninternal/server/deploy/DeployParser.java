// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.util.Set;

public abstract class DeployParser
{
    protected static final char SINGLE_QUOTE = '\'';
    protected static final char COLON = ':';
    protected static final char UNDERSCORE = '_';
    protected static final char PERIOD = '.';
    protected boolean encrypted;
    protected String source;
    protected StringBuilder sb;
    protected int sourceLength;
    protected int pos;
    protected String word;
    protected char wordTerminator;
    
    protected abstract String convertWord();
    
    public abstract String getDeployWord(final String p0);
    
    public abstract Set<String> getIncludes();
    
    public void setEncrypted(final boolean encrytped) {
        this.encrypted = encrytped;
    }
    
    public String parse(final String source) {
        if (source == null) {
            return source;
        }
        this.pos = -1;
        this.source = source;
        this.sourceLength = source.length();
        this.sb = new StringBuilder(source.length() + 20);
        while (this.nextWord()) {
            final String deployWord = this.convertWord();
            this.sb.append(deployWord);
            if (this.pos < this.sourceLength) {
                this.sb.append(this.wordTerminator);
            }
        }
        return this.sb.toString();
    }
    
    private boolean nextWord() {
        if (!this.findWordStart()) {
            return false;
        }
        final StringBuilder wordBuffer = new StringBuilder();
        wordBuffer.append(this.source.charAt(this.pos));
        while (++this.pos < this.sourceLength) {
            final char ch = this.source.charAt(this.pos);
            if (!this.isWordPart(ch)) {
                this.wordTerminator = ch;
                break;
            }
            wordBuffer.append(ch);
        }
        this.word = wordBuffer.toString();
        return true;
    }
    
    private boolean findWordStart() {
        while (++this.pos < this.sourceLength) {
            final char ch = this.source.charAt(this.pos);
            if (ch == '\'') {
                this.sb.append(ch);
                this.readLiteral();
            }
            else if (ch == ':') {
                this.sb.append(ch);
                this.readNamedParameter();
            }
            else {
                if (this.isWordStart(ch)) {
                    return true;
                }
                this.sb.append(ch);
            }
        }
        return false;
    }
    
    private void readLiteral() {
        while (++this.pos < this.sourceLength) {
            final char ch = this.source.charAt(this.pos);
            this.sb.append(ch);
            if (ch == '\'') {
                break;
            }
        }
    }
    
    private void readNamedParameter() {
        while (++this.pos < this.sourceLength) {
            final char ch = this.source.charAt(this.pos);
            this.sb.append(ch);
            if (Character.isWhitespace(ch)) {
                break;
            }
            if (ch == ',') {
                break;
            }
        }
    }
    
    private boolean isWordPart(final char ch) {
        return Character.isLetterOrDigit(ch) || ch == '_' || ch == '.';
    }
    
    private boolean isWordStart(final char ch) {
        return Character.isLetter(ch);
    }
}
