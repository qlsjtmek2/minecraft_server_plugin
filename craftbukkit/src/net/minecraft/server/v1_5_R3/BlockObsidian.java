// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockObsidian extends BlockStone
{
    public BlockObsidian(final int n) {
        super(n);
    }
    
    public int a(final Random random) {
        return 1;
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Block.OBSIDIAN.id;
    }
}
