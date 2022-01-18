// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BiomeTheEnd extends BiomeBase
{
    public BiomeTheEnd(final int n) {
        super(n);
        this.J.clear();
        this.K.clear();
        this.L.clear();
        this.M.clear();
        this.J.add(new BiomeMeta(EntityEnderman.class, 10, 4, 4));
        this.A = (byte)Block.DIRT.id;
        this.B = (byte)Block.DIRT.id;
        this.I = new BiomeTheEndDecorator(this);
    }
}
