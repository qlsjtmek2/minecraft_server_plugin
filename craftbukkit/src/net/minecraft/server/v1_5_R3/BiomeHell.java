// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BiomeHell extends BiomeBase
{
    public BiomeHell(final int n) {
        super(n);
        this.J.clear();
        this.K.clear();
        this.L.clear();
        this.M.clear();
        this.J.add(new BiomeMeta(EntityGhast.class, 50, 4, 4));
        this.J.add(new BiomeMeta(EntityPigZombie.class, 100, 4, 4));
        this.J.add(new BiomeMeta(EntityMagmaCube.class, 1, 4, 4));
    }
}
