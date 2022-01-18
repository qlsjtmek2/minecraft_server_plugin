// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.base;

import java.io.Serializable;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.Beta;

@Beta
@GwtCompatible
public final class Equivalences
{
    public static Equivalence<Object> equals() {
        return Equals.INSTANCE;
    }
    
    public static Equivalence<Object> identity() {
        return Identity.INSTANCE;
    }
    
    @Deprecated
    @GwtCompatible(serializable = true)
    public static <T> Equivalence<Iterable<T>> pairwise(final Equivalence<? super T> elementEquivalence) {
        return (Equivalence<Iterable<T>>)new PairwiseEquivalence((Equivalence<? super Object>)elementEquivalence);
    }
    
    private static final class Equals extends Equivalence<Object> implements Serializable
    {
        static final Equals INSTANCE;
        private static final long serialVersionUID = 1L;
        
        protected boolean doEquivalent(final Object a, final Object b) {
            return a.equals(b);
        }
        
        public int doHash(final Object o) {
            return o.hashCode();
        }
        
        private Object readResolve() {
            return Equals.INSTANCE;
        }
        
        static {
            INSTANCE = new Equals();
        }
    }
    
    private static final class Identity extends Equivalence<Object> implements Serializable
    {
        static final Identity INSTANCE;
        private static final long serialVersionUID = 1L;
        
        protected boolean doEquivalent(final Object a, final Object b) {
            return false;
        }
        
        protected int doHash(final Object o) {
            return System.identityHashCode(o);
        }
        
        private Object readResolve() {
            return Identity.INSTANCE;
        }
        
        static {
            INSTANCE = new Identity();
        }
    }
}
