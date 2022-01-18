// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.util.Collections;
import java.util.Collection;
import java.util.LinkedHashSet;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LoadedPropertiesCache
{
    static ConcurrentHashMap<Integer, Set<String>> cache;
    
    public static Set<String> get(final int partialHash, final Set<String> partialProps, final BeanDescriptor<?> desc) {
        final int manyHash = desc.getNamesOfManyPropsHash();
        final int totalHash = 37 * partialHash + manyHash;
        final Integer key = totalHash;
        Set<String> includedProps = LoadedPropertiesCache.cache.get(key);
        if (includedProps == null) {
            final LinkedHashSet<String> mergeNames = new LinkedHashSet<String>();
            mergeNames.addAll((Collection<?>)partialProps);
            if (manyHash != 0) {
                mergeNames.addAll((Collection<?>)desc.getNamesOfManyProps());
            }
            includedProps = Collections.unmodifiableSet((Set<? extends String>)mergeNames);
            LoadedPropertiesCache.cache.put(key, includedProps);
        }
        return includedProps;
    }
    
    static {
        LoadedPropertiesCache.cache = new ConcurrentHashMap<Integer, Set<String>>(250, 0.75f, 16);
    }
}
