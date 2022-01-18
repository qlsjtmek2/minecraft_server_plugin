// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

public class AssertionFailedException extends RuntimeException
{
    public static void shouldNotHappen(final Exception ex) throws AssertionFailedException {
        throw new AssertionFailedException(ex);
    }
    
    public AssertionFailedException(final Exception ex) {
        super(Messages.getString("AssertionFailedException.0") + ex.toString() + Messages.getString("AssertionFailedException.1"));
    }
}
