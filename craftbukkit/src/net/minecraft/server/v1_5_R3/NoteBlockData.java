// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class NoteBlockData
{
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    
    public NoteBlockData(final int a, final int b, final int c, final int d, final int e, final int f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.e = e;
        this.f = f;
        this.d = d;
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
        return this.e;
    }
    
    public int e() {
        return this.f;
    }
    
    public int f() {
        return this.d;
    }
    
    public boolean equals(final Object o) {
        if (o instanceof NoteBlockData) {
            final NoteBlockData noteBlockData = (NoteBlockData)o;
            return this.a == noteBlockData.a && this.b == noteBlockData.b && this.c == noteBlockData.c && this.e == noteBlockData.e && this.f == noteBlockData.f && this.d == noteBlockData.d;
        }
        return false;
    }
    
    public String toString() {
        return "TE(" + this.a + "," + this.b + "," + this.c + ")," + this.e + "," + this.f + "," + this.d;
    }
}
