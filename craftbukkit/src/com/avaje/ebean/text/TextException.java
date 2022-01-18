// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text;

public class TextException extends RuntimeException
{
    private static final long serialVersionUID = 1601310159486033148L;
    
    public TextException(final String msg) {
        super(msg);
    }
    
    public TextException(final String msg, final Exception e) {
        super(msg, e);
    }
    
    public TextException(final Exception e) {
        super(e);
    }
}
