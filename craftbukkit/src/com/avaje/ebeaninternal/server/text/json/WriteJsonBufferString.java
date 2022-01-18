// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.text.json;

public class WriteJsonBufferString implements WriteJsonBuffer
{
    private final StringBuilder buffer;
    
    public WriteJsonBufferString() {
        this.buffer = new StringBuilder(256);
    }
    
    public WriteJsonBufferString append(final String content) {
        this.buffer.append(content);
        return this;
    }
    
    public String getBufferOutput() {
        return this.buffer.toString();
    }
    
    public String toString() {
        return this.buffer.toString();
    }
}
