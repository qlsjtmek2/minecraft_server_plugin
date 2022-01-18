// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.util.Map;
import scala.collection.JavaConversions;

public class ScalaMapConverter implements CollectionTypeConverter
{
    public Object toUnderlying(final Object wrapped) {
        if (wrapped instanceof JavaConversions.JMapWrapper) {
            return ((JavaConversions.JMapWrapper)wrapped).underlying();
        }
        return null;
    }
    
    public Object toWrapped(final Object wrapped) {
        if (wrapped instanceof Map) {
            return JavaConversions.asMap((Map)wrapped);
        }
        return wrapped;
    }
}
