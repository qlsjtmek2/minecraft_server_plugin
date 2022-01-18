// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class IntHashMap
{
    private transient IntHashMapEntry[] a;
    private transient int b;
    private int c;
    private final float d = 0.75f;
    private transient volatile int e;
    
    public IntHashMap() {
        this.a = new IntHashMapEntry[16];
        this.c = 12;
    }
    
    private static int g(int i) {
        i ^= (i >>> 20 ^ i >>> 12);
        return i ^ i >>> 7 ^ i >>> 4;
    }
    
    private static int a(final int i, final int j) {
        return i & j - 1;
    }
    
    public Object get(final int i) {
        final int j = g(i);
        for (IntHashMapEntry inthashmapentry = this.a[a(j, this.a.length)]; inthashmapentry != null; inthashmapentry = inthashmapentry.c) {
            if (inthashmapentry.a == i) {
                return inthashmapentry.b;
            }
        }
        return null;
    }
    
    public boolean b(final int i) {
        return this.c(i) != null;
    }
    
    final IntHashMapEntry c(final int i) {
        final int j = g(i);
        for (IntHashMapEntry inthashmapentry = this.a[a(j, this.a.length)]; inthashmapentry != null; inthashmapentry = inthashmapentry.c) {
            if (inthashmapentry.a == i) {
                return inthashmapentry;
            }
        }
        return null;
    }
    
    public void a(final int i, final Object object) {
        final int j = g(i);
        final int k = a(j, this.a.length);
        for (IntHashMapEntry inthashmapentry = this.a[k]; inthashmapentry != null; inthashmapentry = inthashmapentry.c) {
            if (inthashmapentry.a == i) {
                inthashmapentry.b = object;
                return;
            }
        }
        ++this.e;
        this.a(j, i, object, k);
    }
    
    private void h(final int i) {
        final IntHashMapEntry[] ainthashmapentry = this.a;
        final int j = ainthashmapentry.length;
        if (j == 1073741824) {
            this.c = Integer.MAX_VALUE;
        }
        else {
            final IntHashMapEntry[] ainthashmapentry2 = new IntHashMapEntry[i];
            this.a(ainthashmapentry2);
            this.a = ainthashmapentry2;
            final float n = i;
            this.getClass();
            this.c = (int)(n * 0.75f);
        }
    }
    
    private void a(final IntHashMapEntry[] ainthashmapentry) {
        final IntHashMapEntry[] ainthashmapentry2 = this.a;
        final int i = ainthashmapentry.length;
        for (int j = 0; j < ainthashmapentry2.length; ++j) {
            IntHashMapEntry inthashmapentry = ainthashmapentry2[j];
            if (inthashmapentry != null) {
                ainthashmapentry2[j] = null;
                IntHashMapEntry inthashmapentry2;
                do {
                    inthashmapentry2 = inthashmapentry.c;
                    final int k = a(inthashmapentry.d, i);
                    inthashmapentry.c = ainthashmapentry[k];
                    ainthashmapentry[k] = inthashmapentry;
                } while ((inthashmapentry = inthashmapentry2) != null);
            }
        }
    }
    
    public Object d(final int i) {
        final IntHashMapEntry inthashmapentry = this.e(i);
        return (inthashmapentry == null) ? null : inthashmapentry.b;
    }
    
    final IntHashMapEntry e(final int i) {
        final int j = g(i);
        final int k = a(j, this.a.length);
        IntHashMapEntry inthashmapentry2;
        IntHashMapEntry inthashmapentry3;
        for (IntHashMapEntry inthashmapentry = inthashmapentry2 = this.a[k]; inthashmapentry2 != null; inthashmapentry2 = inthashmapentry3) {
            inthashmapentry3 = inthashmapentry2.c;
            if (inthashmapentry2.a == i) {
                ++this.e;
                --this.b;
                if (inthashmapentry == inthashmapentry2) {
                    this.a[k] = inthashmapentry3;
                }
                else {
                    inthashmapentry.c = inthashmapentry3;
                }
                return inthashmapentry2;
            }
            inthashmapentry = inthashmapentry2;
        }
        return inthashmapentry2;
    }
    
    public void c() {
        ++this.e;
        final IntHashMapEntry[] ainthashmapentry = this.a;
        for (int i = 0; i < ainthashmapentry.length; ++i) {
            ainthashmapentry[i] = null;
        }
        this.b = 0;
    }
    
    private void a(final int i, final int j, final Object object, final int k) {
        final IntHashMapEntry inthashmapentry = this.a[k];
        this.a[k] = new IntHashMapEntry(i, j, object, inthashmapentry);
        if (this.b++ >= this.c) {
            this.h(2 * this.a.length);
        }
    }
    
    static int f(final int i) {
        return g(i);
    }
}
