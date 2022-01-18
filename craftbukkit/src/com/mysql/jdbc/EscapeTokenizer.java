// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

public class EscapeTokenizer
{
    private int bracesLevel;
    private boolean emittingEscapeCode;
    private boolean inComment;
    private boolean inQuotes;
    private char lastChar;
    private char lastLastChar;
    private int pos;
    private char quoteChar;
    private boolean sawVariableUse;
    private String source;
    private int sourceLength;
    
    public EscapeTokenizer(final String s) {
        this.bracesLevel = 0;
        this.emittingEscapeCode = false;
        this.inComment = false;
        this.inQuotes = false;
        this.lastChar = '\0';
        this.lastLastChar = '\0';
        this.pos = 0;
        this.quoteChar = '\0';
        this.sawVariableUse = false;
        this.source = null;
        this.sourceLength = 0;
        this.source = s;
        this.sourceLength = s.length();
        this.pos = 0;
    }
    
    public synchronized boolean hasMoreTokens() {
        return this.pos < this.sourceLength;
    }
    
    public synchronized String nextToken() {
        final StringBuffer tokenBuf = new StringBuffer();
        if (this.emittingEscapeCode) {
            tokenBuf.append("{");
            this.emittingEscapeCode = false;
        }
        while (this.pos < this.sourceLength) {
            final char c = this.source.charAt(this.pos);
            if (!this.inQuotes && c == '@') {
                this.sawVariableUse = true;
            }
            Label_0500: {
                if ((c == '\'' || c == '\"') && !this.inComment) {
                    if (this.inQuotes && c == this.quoteChar && this.pos + 1 < this.sourceLength && this.source.charAt(this.pos + 1) == this.quoteChar && this.lastChar != '\\') {
                        tokenBuf.append(this.quoteChar);
                        tokenBuf.append(this.quoteChar);
                        ++this.pos;
                        break Label_0500;
                    }
                    if (this.lastChar != '\\') {
                        if (this.inQuotes) {
                            if (this.quoteChar == c) {
                                this.inQuotes = false;
                            }
                        }
                        else {
                            this.inQuotes = true;
                            this.quoteChar = c;
                        }
                    }
                    else if (this.lastLastChar == '\\') {
                        if (this.inQuotes) {
                            if (this.quoteChar == c) {
                                this.inQuotes = false;
                            }
                        }
                        else {
                            this.inQuotes = true;
                            this.quoteChar = c;
                        }
                    }
                    tokenBuf.append(c);
                }
                else if (c == '-') {
                    if (this.lastChar == '-' && this.lastLastChar != '\\' && !this.inQuotes) {
                        this.inComment = true;
                    }
                    tokenBuf.append(c);
                }
                else if (c == '\n' || c == '\r') {
                    this.inComment = false;
                    tokenBuf.append(c);
                }
                else if (c == '{') {
                    if (this.inQuotes || this.inComment) {
                        tokenBuf.append(c);
                    }
                    else {
                        ++this.bracesLevel;
                        if (this.bracesLevel == 1) {
                            ++this.pos;
                            this.emittingEscapeCode = true;
                            return tokenBuf.toString();
                        }
                        tokenBuf.append(c);
                    }
                }
                else if (c == '}') {
                    tokenBuf.append(c);
                    if (!this.inQuotes && !this.inComment) {
                        this.lastChar = c;
                        --this.bracesLevel;
                        if (this.bracesLevel == 0) {
                            ++this.pos;
                            return tokenBuf.toString();
                        }
                    }
                }
                else {
                    tokenBuf.append(c);
                }
                this.lastLastChar = this.lastChar;
                this.lastChar = c;
            }
            ++this.pos;
        }
        return tokenBuf.toString();
    }
    
    boolean sawVariableUse() {
        return this.sawVariableUse;
    }
}
