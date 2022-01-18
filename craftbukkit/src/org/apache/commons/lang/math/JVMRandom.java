// 
// Decompiled by Procyon v0.5.30
// 

package org.apache.commons.lang.math;

import java.util.Random;

public final class JVMRandom extends Random
{
    private static final long serialVersionUID = 1L;
    private boolean constructed;
    
    public JVMRandom() {
        this.constructed = false;
        this.constructed = true;
    }
    
    public synchronized void setSeed(final long seed) {
        if (this.constructed) {
            throw new UnsupportedOperationException();
        }
    }
    
    public synchronized double nextGaussian() {
        throw new UnsupportedOperationException();
    }
    
    public void nextBytes(final byte[] byteArray) {
        throw new UnsupportedOperationException();
    }
    
    public int nextInt() {
        return this.nextInt(Integer.MAX_VALUE);
    }
    
    public int nextInt(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Upper bound for nextInt must be positive");
        }
        return (int)(Math.random() * n);
    }
    
    public long nextLong() {
        return nextLong(Long.MAX_VALUE);
    }
    
    public static long nextLong(final long n) {
        if (n <= 0L) {
            throw new IllegalArgumentException("Upper bound for nextInt must be positive");
        }
        return (long)(Math.random() * n);
    }
    
    public boolean nextBoolean() {
        return Math.random() > 0.5;
    }
    
    public float nextFloat() {
        return (float)Math.random();
    }
    
    public double nextDouble() {
        return Math.random();
    }
}
