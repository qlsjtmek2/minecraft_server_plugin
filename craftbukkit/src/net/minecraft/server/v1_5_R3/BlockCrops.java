// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.Random;

public class BlockCrops extends BlockFlower
{
    protected BlockCrops(final int i) {
        super(i);
        this.b(true);
        final float f = 0.5f;
        this.a(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 0.25f, 0.5f + f);
        this.a((CreativeModeTab)null);
        this.c(0.0f);
        this.a(Block.i);
        this.D();
    }
    
    protected boolean f_(final int i) {
        return i == Block.SOIL.id;
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        super.a(world, i, j, k, random);
        if (world.getLightLevel(i, j + 1, k) >= 9) {
            int l = world.getData(i, j, k);
            if (l < 7) {
                final float f = this.k(world, i, j, k);
                if (random.nextInt((int)(world.growthOdds / world.getWorld().wheatGrowthModifier * (25.0f / f)) + 1) == 0) {
                    CraftEventFactory.handleBlockGrowEvent(world, i, j, k, this.id, ++l);
                }
            }
        }
    }
    
    public void e_(final World world, final int i, final int j, final int k) {
        int l = world.getData(i, j, k) + MathHelper.nextInt(world.random, 2, 5);
        if (l > 7) {
            l = 7;
        }
        world.setData(i, j, k, l, 2);
    }
    
    private float k(final World world, final int i, final int j, final int k) {
        float f = 1.0f;
        final int l = world.getTypeId(i, j, k - 1);
        final int i2 = world.getTypeId(i, j, k + 1);
        final int j2 = world.getTypeId(i - 1, j, k);
        final int k2 = world.getTypeId(i + 1, j, k);
        final int l2 = world.getTypeId(i - 1, j, k - 1);
        final int i3 = world.getTypeId(i + 1, j, k - 1);
        final int j3 = world.getTypeId(i + 1, j, k + 1);
        final int k3 = world.getTypeId(i - 1, j, k + 1);
        final boolean flag = j2 == this.id || k2 == this.id;
        final boolean flag2 = l == this.id || i2 == this.id;
        final boolean flag3 = l2 == this.id || i3 == this.id || j3 == this.id || k3 == this.id;
        for (int l3 = i - 1; l3 <= i + 1; ++l3) {
            for (int i4 = k - 1; i4 <= k + 1; ++i4) {
                final int j4 = world.getTypeId(l3, j - 1, i4);
                float f2 = 0.0f;
                if (j4 == Block.SOIL.id) {
                    f2 = 1.0f;
                    if (world.getData(l3, j - 1, i4) > 0) {
                        f2 = 3.0f;
                    }
                }
                if (l3 != i || i4 != k) {
                    f2 /= 4.0f;
                }
                f += f2;
            }
        }
        if (flag3 || (flag && flag2)) {
            f /= 2.0f;
        }
        return f;
    }
    
    public int d() {
        return 6;
    }
    
    protected int j() {
        return Item.SEEDS.id;
    }
    
    protected int k() {
        return Item.WHEAT.id;
    }
    
    public void dropNaturally(final World world, final int i, final int j, final int k, final int l, final float f, final int i1) {
        super.dropNaturally(world, i, j, k, l, f, 0);
        if (!world.isStatic && l >= 7) {
            for (int j2 = 3 + i1, k2 = 0; k2 < j2; ++k2) {
                if (world.random.nextInt(15) <= l) {
                    this.b(world, i, j, k, new ItemStack(this.j(), 1, 0));
                }
            }
        }
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return (i == 7) ? this.k() : this.j();
    }
    
    public int a(final Random random) {
        return 1;
    }
}
