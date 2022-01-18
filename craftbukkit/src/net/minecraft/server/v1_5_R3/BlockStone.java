// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockStone extends Block
{
    public BlockStone(final int i) {
        super(i, Material.STONE);
        this.a(CreativeModeTab.b);
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Block.COBBLESTONE.id;
    }
}
