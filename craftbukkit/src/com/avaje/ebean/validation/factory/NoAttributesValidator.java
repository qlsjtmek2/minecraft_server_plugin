// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.validation.factory;

public abstract class NoAttributesValidator implements Validator
{
    private static final Object[] EMPTY;
    
    public Object[] getAttributes() {
        return NoAttributesValidator.EMPTY;
    }
    
    static {
        EMPTY = new Object[0];
    }
}
