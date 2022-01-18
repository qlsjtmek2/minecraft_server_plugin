// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.validation.factory;

public interface Validator
{
    String getKey();
    
    Object[] getAttributes();
    
    boolean isValid(final Object p0);
}
