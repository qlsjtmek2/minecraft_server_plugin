// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.validation.factory;

import java.lang.annotation.Annotation;

public interface ValidatorFactory
{
    Validator create(final Annotation p0, final Class<?> p1);
}
