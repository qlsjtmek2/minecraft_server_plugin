// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.io.Serializable;
import java.math.BigInteger;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
@Beta
public final class DiscreteDomains
{
    public static DiscreteDomain<Integer> integers() {
        return IntegerDomain.INSTANCE;
    }
    
    public static DiscreteDomain<Long> longs() {
        return LongDomain.INSTANCE;
    }
    
    static DiscreteDomain<BigInteger> bigIntegers() {
        return BigIntegerDomain.INSTANCE;
    }
    
    private static final class IntegerDomain extends DiscreteDomain<Integer> implements Serializable
    {
        private static final IntegerDomain INSTANCE;
        private static final long serialVersionUID = 0L;
        
        public Integer next(final Integer value) {
            final int i = value;
            return (i == Integer.MAX_VALUE) ? null : (i + 1);
        }
        
        public Integer previous(final Integer value) {
            final int i = value;
            return (i == Integer.MIN_VALUE) ? null : (i - 1);
        }
        
        public long distance(final Integer start, final Integer end) {
            return end - start;
        }
        
        public Integer minValue() {
            return Integer.MIN_VALUE;
        }
        
        public Integer maxValue() {
            return Integer.MAX_VALUE;
        }
        
        private Object readResolve() {
            return IntegerDomain.INSTANCE;
        }
        
        static {
            INSTANCE = new IntegerDomain();
        }
    }
    
    private static final class LongDomain extends DiscreteDomain<Long> implements Serializable
    {
        private static final LongDomain INSTANCE;
        private static final long serialVersionUID = 0L;
        
        public Long next(final Long value) {
            final long l = value;
            return (l == Long.MAX_VALUE) ? null : (l + 1L);
        }
        
        public Long previous(final Long value) {
            final long l = value;
            return (l == Long.MIN_VALUE) ? null : (l - 1L);
        }
        
        public long distance(final Long start, final Long end) {
            final long result = end - start;
            if (end > start && result < 0L) {
                return Long.MAX_VALUE;
            }
            if (end < start && result > 0L) {
                return Long.MIN_VALUE;
            }
            return result;
        }
        
        public Long minValue() {
            return Long.MIN_VALUE;
        }
        
        public Long maxValue() {
            return Long.MAX_VALUE;
        }
        
        private Object readResolve() {
            return LongDomain.INSTANCE;
        }
        
        static {
            INSTANCE = new LongDomain();
        }
    }
    
    private static final class BigIntegerDomain extends DiscreteDomain<BigInteger> implements Serializable
    {
        private static final BigIntegerDomain INSTANCE;
        private static final BigInteger MIN_LONG;
        private static final BigInteger MAX_LONG;
        private static final long serialVersionUID = 0L;
        
        public BigInteger next(final BigInteger value) {
            return value.add(BigInteger.ONE);
        }
        
        public BigInteger previous(final BigInteger value) {
            return value.subtract(BigInteger.ONE);
        }
        
        public long distance(final BigInteger start, final BigInteger end) {
            return start.subtract(end).max(BigIntegerDomain.MIN_LONG).min(BigIntegerDomain.MAX_LONG).longValue();
        }
        
        private Object readResolve() {
            return BigIntegerDomain.INSTANCE;
        }
        
        static {
            INSTANCE = new BigIntegerDomain();
            MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
            MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
        }
    }
}
