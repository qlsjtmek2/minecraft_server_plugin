// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public abstract class StructurePieceBlockSelector
{
    protected int a;
    protected int b;
    
    public abstract void a(final Random p0, final int p1, final int p2, final int p3, final boolean p4);
    
    public int a() {
        return this.a;
    }
    
    public int b() {
        return this.b;
    }
}
