// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class NextTickListEntry implements Comparable
{
    private static long g;
    public int a;
    public int b;
    public int c;
    public int d;
    public long e;
    public int f;
    private long h;
    
    public NextTickListEntry(final int i, final int j, final int k, final int l) {
        this.h = NextTickListEntry.g++;
        this.a = i;
        this.b = j;
        this.c = k;
        this.d = l;
    }
    
    public boolean equals(final Object object) {
        if (!(object instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry nextticklistentry = (NextTickListEntry)object;
        return this.a == nextticklistentry.a && this.b == nextticklistentry.b && this.c == nextticklistentry.c && Block.b(this.d, nextticklistentry.d);
    }
    
    public int hashCode() {
        return this.a * 257 ^ this.b ^ this.c * 60217;
    }
    
    public NextTickListEntry a(final long i) {
        this.e = i;
        return this;
    }
    
    public void a(final int i) {
        this.f = i;
    }
    
    public int compareTo(final NextTickListEntry nextticklistentry) {
        return (this.e < nextticklistentry.e) ? -1 : ((this.e > nextticklistentry.e) ? 1 : ((this.f != nextticklistentry.f) ? (this.f - nextticklistentry.f) : ((this.h < nextticklistentry.h) ? -1 : ((this.h > nextticklistentry.h) ? 1 : 0))));
    }
    
    public String toString() {
        return this.d + ": (" + this.a + ", " + this.b + ", " + this.c + "), " + this.e + ", " + this.f + ", " + this.h;
    }
    
    public int compareTo(final Object object) {
        return this.compareTo((NextTickListEntry)object);
    }
    
    static {
        NextTickListEntry.g = 0L;
    }
}
