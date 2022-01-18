// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemAnvil extends ItemMultiTexture
{
    public ItemAnvil(final Block block) {
        super(block.id - 256, block, BlockAnvil.a);
    }
    
    public int filterData(final int n) {
        return n << 2;
    }
}
