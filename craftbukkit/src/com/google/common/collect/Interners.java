// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Equivalences;
import com.google.common.base.Function;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.concurrent.ConcurrentMap;
import com.google.common.annotations.Beta;

@Beta
public final class Interners
{
    public static <E> Interner<E> newStrongInterner() {
        final ConcurrentMap<E, E> map = new MapMaker().makeMap();
        return new Interner<E>() {
            public E intern(final E sample) {
                final E canonical = map.putIfAbsent(Preconditions.checkNotNull(sample), sample);
                return (canonical == null) ? sample : canonical;
            }
        };
    }
    
    @GwtIncompatible("java.lang.ref.WeakReference")
    public static <E> Interner<E> newWeakInterner() {
        return new CustomInterner<E>(new MapMaker().weakKeys());
    }
    
    public static <E> Function<E, E> asFunction(final Interner<E> interner) {
        return new InternerFunction<E>(Preconditions.checkNotNull(interner));
    }
    
    private static class CustomInterner<E> implements Interner<E>
    {
        private final CustomConcurrentHashMap<E, Dummy> map;
        
        CustomInterner(final GenericMapMaker<? super E, Object> mm) {
            this.map = mm.strongValues().keyEquivalence(Equivalences.equals()).makeCustomMap();
        }
        
        public E intern(final E sample) {
            while (true) {
                final CustomConcurrentHashMap.ReferenceEntry<E, Dummy> entry = this.map.getEntry(sample);
                if (entry != null) {
                    final E canonical = entry.getKey();
                    if (canonical != null) {
                        return canonical;
                    }
                }
                final Dummy sneaky = this.map.putIfAbsent(sample, Dummy.VALUE);
                if (sneaky == null) {
                    return sample;
                }
            }
        }
        
        private enum Dummy
        {
            VALUE;
        }
    }
    
    private static class InternerFunction<E> implements Function<E, E>
    {
        private final Interner<E> interner;
        
        public InternerFunction(final Interner<E> interner) {
            this.interner = interner;
        }
        
        public E apply(final E input) {
            return this.interner.intern(input);
        }
        
        public int hashCode() {
            return this.interner.hashCode();
        }
        
        public boolean equals(final Object other) {
            if (other instanceof InternerFunction) {
                final InternerFunction<?> that = (InternerFunction<?>)other;
                return this.interner.equals(that.interner);
            }
            return false;
        }
    }
}
