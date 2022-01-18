// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;

public class BeanCollectionHelpFactory
{
    public static <T> BeanCollectionHelp<T> create(final BeanPropertyAssocMany<T> manyProperty) {
        final ManyType manyType = manyProperty.getManyType();
        switch (manyType.getUnderlying()) {
            case LIST: {
                return new BeanListHelp<T>(manyProperty);
            }
            case SET: {
                return new BeanSetHelp<T>(manyProperty);
            }
            case MAP: {
                return new BeanMapHelp<T>(manyProperty);
            }
            default: {
                throw new RuntimeException("Invalid type " + manyType);
            }
        }
    }
    
    public static <T> BeanCollectionHelp<T> create(final OrmQueryRequest<T> request) {
        final Query.Type manyType = request.getQuery().getType();
        if (manyType.equals(Query.Type.LIST)) {
            return new BeanListHelp<T>();
        }
        if (manyType.equals(Query.Type.SET)) {
            return new BeanSetHelp<T>();
        }
        final BeanDescriptor<T> target = request.getBeanDescriptor();
        final String mapKey = request.getQuery().getMapKey();
        return new BeanMapHelp<T>(target, mapKey);
    }
}
