// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.text.json;

import com.avaje.ebean.text.TextException;

public class ReadJsonSourceString implements ReadJsonSource
{
    private final String source;
    private final int sourceLength;
    private int pos;
    
    public ReadJsonSourceString(final String source) {
        this.source = source;
        this.sourceLength = source.length();
    }
    
    public String getErrorHelp() {
        int prev = this.pos - 50;
        if (prev < 0) {
            prev = 0;
        }
        final String c = this.source.substring(prev, this.pos);
        return "pos:" + this.pos + " precedingcontent:" + c;
    }
    
    public String toString() {
        return this.source;
    }
    
    public int pos() {
        return this.pos;
    }
    
    public void back() {
        --this.pos;
    }
    
    public char nextChar(final String eofMsg) {
        if (this.pos >= this.sourceLength) {
            throw new TextException(eofMsg + " at pos:" + this.pos);
        }
        return this.source.charAt(this.pos++);
    }
    
    public void ignoreWhiteSpace() {
        while (true) {
            final char c = this.source.charAt(this.pos);
            if (!Character.isWhitespace(c)) {
                break;
            }
            ++this.pos;
        }
    }
}
