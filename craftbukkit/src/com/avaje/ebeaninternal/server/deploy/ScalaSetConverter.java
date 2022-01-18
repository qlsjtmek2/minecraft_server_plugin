// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.util.Set;
import scala.collection.JavaConversions;

public class ScalaSetConverter implements CollectionTypeConverter
{
    public Object toUnderlying(final Object wrapped) {
        if (wrapped instanceof JavaConversions.JSetWrapper) {
            return ((JavaConversions.JSetWrapper)wrapped).underlying();
        }
        return null;
    }
    
    public Object toWrapped(final Object wrapped) {
        if (wrapped instanceof Set) {
            return JavaConversions.asSet((Set)wrapped);
        }
        return wrapped;
    }
}
