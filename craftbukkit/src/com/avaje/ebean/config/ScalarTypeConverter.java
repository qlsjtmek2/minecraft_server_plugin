// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

public interface ScalarTypeConverter<B, S>
{
    B getNullValue();
    
    B wrapValue(final S p0);
    
    S unwrapValue(final B p0);
}
