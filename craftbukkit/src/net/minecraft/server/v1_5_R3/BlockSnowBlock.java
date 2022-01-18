// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockSnowBlock extends Block
{
    protected BlockSnowBlock(final int i) {
        super(i, Material.SNOW_BLOCK);
        this.b(true);
        this.a(CreativeModeTab.b);
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return Item.SNOW_BALL.id;
    }
    
    public int a(final Random random) {
        return 4;
    }
    
    public void a(final World world, final int n, final int n2, final int n3, final Random random) {
        if (world.b(EnumSkyBlock.BLOCK, n, n2, n3) > 11) {
            this.c(world, n, n2, n3, world.getData(n, n2, n3), 0);
            world.setAir(n, n2, n3);
        }
    }
}
