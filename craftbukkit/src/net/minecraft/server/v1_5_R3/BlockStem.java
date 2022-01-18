// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.Random;

public class BlockStem extends BlockFlower
{
    private final Block blockFruit;
    
    protected BlockStem(final int i, final Block block) {
        super(i);
        this.blockFruit = block;
        this.b(true);
        final float f = 0.125f;
        this.a(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 0.25f, 0.5f + f);
        this.a((CreativeModeTab)null);
    }
    
    protected boolean f_(final int i) {
        return i == Block.SOIL.id;
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        super.a(world, i, j, k, random);
        if (world.getLightLevel(i, j + 1, k) >= 9) {
            final float f = this.m(world, i, j, k);
            if (random.nextInt((int)(world.growthOdds / ((this.id == Block.PUMPKIN_STEM.id) ? world.getWorld().pumpkinGrowthModifier : world.getWorld().melonGrowthModifier) * (25.0f / f)) + 1) == 0) {
                int l = world.getData(i, j, k);
                if (l < 7) {
                    CraftEventFactory.handleBlockGrowEvent(world, i, j, k, this.id, ++l);
                }
                else {
                    if (world.getTypeId(i - 1, j, k) == this.blockFruit.id) {
                        return;
                    }
                    if (world.getTypeId(i + 1, j, k) == this.blockFruit.id) {
                        return;
                    }
                    if (world.getTypeId(i, j, k - 1) == this.blockFruit.id) {
                        return;
                    }
                    if (world.getTypeId(i, j, k + 1) == this.blockFruit.id) {
                        return;
                    }
                    final int i2 = random.nextInt(4);
                    int j2 = i;
                    int k2 = k;
                    if (i2 == 0) {
                        j2 = i - 1;
                    }
                    if (i2 == 1) {
                        ++j2;
                    }
                    if (i2 == 2) {
                        k2 = k - 1;
                    }
                    if (i2 == 3) {
                        ++k2;
                    }
                    final int l2 = world.getTypeId(j2, j - 1, k2);
                    if (world.getTypeId(j2, j, k2) == 0 && (l2 == Block.SOIL.id || l2 == Block.DIRT.id || l2 == Block.GRASS.id)) {
                        CraftEventFactory.handleBlockGrowEvent(world, j2, j, k2, this.blockFruit.id, 0);
                    }
                }
            }
        }
    }
    
    public void k(final World world, final int i, final int j, final int k) {
        int l = world.getData(i, j, k) + MathHelper.nextInt(world.random, 2, 5);
        if (l > 7) {
            l = 7;
        }
        world.setData(i, j, k, l, 2);
    }
    
    private float m(final World world, final int i, final int j, final int k) {
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
    
    public void g() {
        final float f = 0.125f;
        this.a(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 0.25f, 0.5f + f);
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        this.maxY = (iblockaccess.getData(i, j, k) * 2 + 2) / 16.0f;
        final float f = 0.125f;
        this.a(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, (float)this.maxY, 0.5f + f);
    }
    
    public int d() {
        return 19;
    }
    
    public void dropNaturally(final World world, final int i, final int j, final int k, final int l, final float f, final int i1) {
        super.dropNaturally(world, i, j, k, l, f, i1);
        if (!world.isStatic) {
            Item item = null;
            if (this.blockFruit == Block.PUMPKIN) {
                item = Item.PUMPKIN_SEEDS;
            }
            if (this.blockFruit == Block.MELON) {
                item = Item.MELON_SEEDS;
            }
            for (int j2 = 0; j2 < 3; ++j2) {
                if (world.random.nextInt(15) <= l) {
                    this.b(world, i, j, k, new ItemStack(item));
                }
            }
        }
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return -1;
    }
    
    public int a(final Random random) {
        return 1;
    }
}
