// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

class WorldGenStrongholdPieceWeight
{
    public Class a;
    public final int b;
    public int c;
    public int d;
    
    public WorldGenStrongholdPieceWeight(final Class a, final int b, final int d) {
        this.a = a;
        this.b = b;
        this.d = d;
    }
    
    public boolean a(final int n) {
        return this.d == 0 || this.c < this.d;
    }
    
    public boolean a() {
        return this.d == 0 || this.c < this.d;
    }
}
