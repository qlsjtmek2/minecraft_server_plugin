// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockDeadBush extends BlockFlower
{
    protected BlockDeadBush(final int n) {
        super(n, Material.REPLACEABLE_PLANT);
        final float n2 = 0.4f;
        this.a(0.5f - n2, 0.0f, 0.5f - n2, 0.5f + n2, 0.8f, 0.5f + n2);
    }
    
    protected boolean f_(final int n) {
        return n == Block.SAND.id;
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return -1;
    }
    
    public void a(final World world, final EntityHuman entityhuman, final int n, final int n2, final int n3, final int n4) {
        if (!world.isStatic && entityhuman.cd() != null && entityhuman.cd().id == Item.SHEARS.id) {
            entityhuman.a(StatisticList.C[this.id], 1);
            this.b(world, n, n2, n3, new ItemStack(Block.DEAD_BUSH, 1, n4));
        }
        else {
            super.a(world, entityhuman, n, n2, n3, n4);
        }
    }
}
