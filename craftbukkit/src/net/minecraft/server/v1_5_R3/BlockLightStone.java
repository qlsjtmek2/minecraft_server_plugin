// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockLightStone extends Block
{
    public BlockLightStone(final int i, final Material material) {
        super(i, material);
        this.a(CreativeModeTab.b);
    }
    
    public int getDropCount(final int n, final Random random) {
        return MathHelper.a(this.a(random) + random.nextInt(n + 1), 1, 4);
    }
    
    public int a(final Random random) {
        return 2 + random.nextInt(3);
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Item.GLOWSTONE_DUST.id;
    }
}
