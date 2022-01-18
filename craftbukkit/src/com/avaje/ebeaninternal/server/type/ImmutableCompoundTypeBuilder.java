// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.util.HashMap;
import java.util.Map;

public final class ImmutableCompoundTypeBuilder
{
    private static ThreadLocal<ImmutableCompoundTypeBuilder> local;
    private Map<Class<?>, Entry> entryMap;
    
    public ImmutableCompoundTypeBuilder() {
        this.entryMap = new HashMap<Class<?>, Entry>();
    }
    
    public static void clear() {
        ImmutableCompoundTypeBuilder.local.get().entryMap.clear();
    }
    
    public static Object set(final CtCompoundType<?> ct, final String propName, final Object value) {
        return ImmutableCompoundTypeBuilder.local.get().setValue(ct, propName, value);
    }
    
    private Object setValue(final CtCompoundType<?> ct, final String propName, final Object value) {
        final Entry e = this.getEntry(ct);
        final Object compoundValue = e.set(propName, value);
        if (compoundValue != null) {
            this.removeEntry(ct);
        }
        return compoundValue;
    }
    
    private void removeEntry(final CtCompoundType<?> ct) {
        this.entryMap.remove(ct.getCompoundTypeClass());
    }
    
    private Entry getEntry(final CtCompoundType<?> ct) {
        Entry e = this.entryMap.get(ct.getCompoundTypeClass());
        if (e == null) {
            e = new Entry((CtCompoundType)ct);
            this.entryMap.put(ct.getCompoundTypeClass(), e);
        }
        return e;
    }
    
    static {
        ImmutableCompoundTypeBuilder.local = new ThreadLocal<ImmutableCompoundTypeBuilder>() {
            protected synchronized ImmutableCompoundTypeBuilder initialValue() {
                return new ImmutableCompoundTypeBuilder();
            }
        };
    }
    
    private static class Entry
    {
        private final CtCompoundType<?> ct;
        private final Map<String, Object> valueMap;
        
        private Entry(final CtCompoundType<?> ct) {
            this.ct = ct;
            this.valueMap = new HashMap<String, Object>();
        }
        
        private Object set(final String propName, final Object value) {
            this.valueMap.put(propName, value);
            return this.ct.create(this.valueMap);
        }
    }
}
