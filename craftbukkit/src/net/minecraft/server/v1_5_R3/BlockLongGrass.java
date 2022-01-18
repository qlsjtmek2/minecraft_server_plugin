// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockLongGrass extends BlockFlower
{
    private static final String[] a;
    
    protected BlockLongGrass(final int n) {
        super(n, Material.REPLACEABLE_PLANT);
        final float n2 = 0.4f;
        this.a(0.5f - n2, 0.0f, 0.5f - n2, 0.5f + n2, 0.8f, 0.5f + n2);
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        if (random.nextInt(8) == 0) {
            return Item.SEEDS.id;
        }
        return -1;
    }
    
    public int getDropCount(final int n, final Random random) {
        return 1 + random.nextInt(n * 2 + 1);
    }
    
    public void a(final World world, final EntityHuman entityhuman, final int n, final int n2, final int n3, final int n4) {
        if (!world.isStatic && entityhuman.cd() != null && entityhuman.cd().id == Item.SHEARS.id) {
            entityhuman.a(StatisticList.C[this.id], 1);
            this.b(world, n, n2, n3, new ItemStack(Block.LONG_GRASS, 1, n4));
        }
        else {
            super.a(world, entityhuman, n, n2, n3, n4);
        }
    }
    
    public int getDropData(final World world, final int i, final int j, final int k) {
        return world.getData(i, j, k);
    }
    
    static {
        a = new String[] { "deadbush", "tallgrass", "fern" };
    }
}
