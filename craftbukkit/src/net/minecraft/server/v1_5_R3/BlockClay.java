// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockClay extends Block
{
    public BlockClay(final int i) {
        super(i, Material.CLAY);
        this.a(CreativeModeTab.b);
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Item.CLAY_BALL.id;
    }
    
    public int a(final Random random) {
        return 4;
    }
}
