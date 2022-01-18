// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class WorldGenFlatLayerInfo
{
    private int a;
    private int b;
    private int c;
    private int d;
    
    public WorldGenFlatLayerInfo(final int a, final int b) {
        this.a = 1;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.a = a;
        this.b = b;
    }
    
    public WorldGenFlatLayerInfo(final int n, final int n2, final int c) {
        this(n, n2);
        this.c = c;
    }
    
    public int a() {
        return this.a;
    }
    
    public int b() {
        return this.b;
    }
    
    public int c() {
        return this.c;
    }
    
    public int d() {
        return this.d;
    }
    
    public void d(final int d) {
        this.d = d;
    }
    
    public String toString() {
        String s = Integer.toString(this.b);
        if (this.a > 1) {
            s = this.a + "x" + s;
        }
        if (this.c > 0) {
            s = s + ":" + this.c;
        }
        return s;
    }
}
