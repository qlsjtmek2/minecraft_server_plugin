// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.util.List;
import scala.collection.JavaConversions;

public class ScalaBufferConverter implements CollectionTypeConverter
{
    public Object toUnderlying(final Object wrapped) {
        if (wrapped instanceof JavaConversions.JListWrapper) {
            return ((JavaConversions.JListWrapper)wrapped).underlying();
        }
        return null;
    }
    
    public Object toWrapped(final Object wrapped) {
        if (wrapped instanceof List) {
            return JavaConversions.asBuffer((List)wrapped);
        }
        return wrapped;
    }
}
