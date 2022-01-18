// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.util;

import java.util.List;
import java.util.ArrayList;
import com.avaje.ebean.common.BeanList;
import java.util.Set;
import java.util.LinkedHashSet;
import com.avaje.ebean.common.BeanSet;
import java.util.Map;
import java.util.LinkedHashMap;
import com.avaje.ebean.common.BeanMap;
import com.avaje.ebean.Query;
import com.avaje.ebean.bean.BeanCollection;

public class BeanCollectionFactory
{
    private static final int defaultListInitialCapacity = 20;
    private static final int defaultSetInitialCapacity = 32;
    private static final int defaultMapInitialCapacity = 32;
    
    public static BeanCollection<?> create(final BeanCollectionParams params) {
        return BeanCollectionFactoryHolder.me.createMany(params);
    }
    
    private BeanCollection<?> createMany(final BeanCollectionParams params) {
        final Query.Type manyType = params.getManyType();
        switch (manyType) {
            case MAP: {
                return (BeanCollection<?>)this.createMap(params);
            }
            case LIST: {
                return (BeanCollection<?>)this.createList(params);
            }
            case SET: {
                return (BeanCollection<?>)this.createSet(params);
            }
            default: {
                throw new RuntimeException("Invalid Arg " + manyType);
            }
        }
    }
    
    private BeanMap createMap(final BeanCollectionParams params) {
        return new BeanMap((Map<K, E>)new LinkedHashMap<Object, Object>(32));
    }
    
    private BeanSet createSet(final BeanCollectionParams params) {
        return new BeanSet((Set<E>)new LinkedHashSet<Object>(32));
    }
    
    private BeanList createList(final BeanCollectionParams params) {
        return new BeanList((List<E>)new ArrayList<Object>(20));
    }
    
    private static class BeanCollectionFactoryHolder
    {
        private static BeanCollectionFactory me;
        
        static {
            BeanCollectionFactoryHolder.me = new BeanCollectionFactory(null);
        }
    }
}
