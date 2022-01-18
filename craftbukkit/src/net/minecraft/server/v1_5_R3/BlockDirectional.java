// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public abstract class BlockDirectional extends Block
{
    protected BlockDirectional(final int i, final Material material) {
        super(i, material);
    }
    
    public static int j(final int n) {
        return n & 0x3;
    }
}
