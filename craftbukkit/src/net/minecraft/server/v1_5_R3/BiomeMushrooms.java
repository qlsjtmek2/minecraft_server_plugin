// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BiomeMushrooms extends BiomeBase
{
    public BiomeMushrooms(final int n) {
        super(n);
        this.I.z = -100;
        this.I.A = -100;
        this.I.B = -100;
        this.I.D = 1;
        this.I.J = 1;
        this.A = (byte)Block.MYCEL.id;
        this.J.clear();
        this.K.clear();
        this.L.clear();
        this.K.add(new BiomeMeta(EntityMushroomCow.class, 8, 4, 8));
    }
}
