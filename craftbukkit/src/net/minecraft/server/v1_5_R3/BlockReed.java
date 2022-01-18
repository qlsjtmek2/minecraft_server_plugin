// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.Random;

public class BlockReed extends Block
{
    protected BlockReed(final int i) {
        super(i, Material.PLANT);
        final float f = 0.375f;
        this.a(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 1.0f, 0.5f + f);
        this.b(true);
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (world.isEmpty(i, j + 1, k)) {
            int l;
            for (l = 1; world.getTypeId(i, j - l, k) == this.id; ++l) {}
            if (l < 3) {
                final int i2 = world.getData(i, j, k);
                if (i2 >= (byte)Block.range(3.0f, world.growthOdds / world.getWorld().sugarGrowthModifier * 15.0f + 0.5f, 15.0f)) {
                    CraftEventFactory.handleBlockGrowEvent(world, i, j + 1, k, this.id, 0);
                    world.setData(i, j, k, 0, 4);
                }
                else {
                    world.setData(i, j, k, i2 + 1, 4);
                }
            }
        }
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k) {
        final int l = world.getTypeId(i, j - 1, k);
        return l == this.id || ((l == Block.GRASS.id || l == Block.DIRT.id || l == Block.SAND.id) && (world.getMaterial(i - 1, j - 1, k) == Material.WATER || world.getMaterial(i + 1, j - 1, k) == Material.WATER || world.getMaterial(i, j - 1, k - 1) == Material.WATER || world.getMaterial(i, j - 1, k + 1) == Material.WATER));
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        this.p_(world, i, j, k);
    }
    
    protected final void p_(final World world, final int i, final int j, final int k) {
        if (!this.f(world, i, j, k)) {
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setAir(i, j, k);
        }
    }
    
    public boolean f(final World world, final int i, final int j, final int k) {
        return this.canPlace(world, i, j, k);
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        return null;
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return Item.SUGAR_CANE.id;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public int d() {
        return 1;
    }
}
