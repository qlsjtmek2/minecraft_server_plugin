// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class WatchableObject
{
    private final int a;
    private final int b;
    private Object c;
    private boolean d;
    
    public WatchableObject(final int a, final int b, final Object c) {
        this.b = b;
        this.c = c;
        this.a = a;
        this.d = true;
    }
    
    public int a() {
        return this.b;
    }
    
    public void a(final Object c) {
        this.c = c;
    }
    
    public Object b() {
        return this.c;
    }
    
    public int c() {
        return this.a;
    }
    
    public boolean d() {
        return this.d;
    }
    
    public void a(final boolean d) {
        this.d = d;
    }
}
