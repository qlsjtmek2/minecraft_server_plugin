// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import scala.Some;
import scala.None$;
import scala.Option;
import com.avaje.ebean.config.ScalarTypeConverter;

public class ScalaOptionTypeConverter<S> implements ScalarTypeConverter<Option<S>, S>
{
    public Option<S> getNullValue() {
        return (Option<S>)None$.MODULE$;
    }
    
    public S unwrapValue(final Option<S> beanType) {
        if (beanType.isEmpty()) {
            return null;
        }
        return (S)beanType.get();
    }
    
    public Option<S> wrapValue(final S scalarType) {
        if (scalarType == null) {
            return (Option<S>)None$.MODULE$;
        }
        if (scalarType instanceof Some) {
            return (Option<S>)scalarType;
        }
        return (Option<S>)new Some((Object)scalarType);
    }
}
