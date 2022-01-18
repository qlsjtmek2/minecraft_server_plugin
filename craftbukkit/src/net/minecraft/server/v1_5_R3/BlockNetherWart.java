// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.Random;

public class BlockNetherWart extends BlockFlower
{
    private static final String[] a;
    
    protected BlockNetherWart(final int i) {
        super(i);
        this.b(true);
        final float f = 0.5f;
        this.a(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 0.25f, 0.5f + f);
        this.a((CreativeModeTab)null);
    }
    
    protected boolean f_(final int i) {
        return i == Block.SOUL_SAND.id;
    }
    
    public boolean f(final World world, final int i, final int j, final int k) {
        return this.f_(world.getTypeId(i, j - 1, k));
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        int l = world.getData(i, j, k);
        if (l < 3 && random.nextInt(10) == 0) {
            CraftEventFactory.handleBlockGrowEvent(world, i, j, k, this.id, ++l);
        }
        super.a(world, i, j, k, random);
    }
    
    public int d() {
        return 6;
    }
    
    public void dropNaturally(final World world, final int i, final int j, final int k, final int l, final float f, final int i1) {
        if (!world.isStatic) {
            int j2 = 1;
            if (l >= 3) {
                j2 = 2 + world.random.nextInt(3);
                if (i1 > 0) {
                    j2 += world.random.nextInt(i1 + 1);
                }
            }
            for (int k2 = 0; k2 < j2; ++k2) {
                this.b(world, i, j, k, new ItemStack(Item.NETHER_STALK));
            }
        }
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return 0;
    }
    
    public int a(final Random random) {
        return 0;
    }
    
    static {
        a = new String[] { "netherStalk_0", "netherStalk_1", "netherStalk_2" };
    }
}
