// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockMobSpawner extends BlockContainer
{
    protected BlockMobSpawner(final int i) {
        super(i, Material.STONE);
    }
    
    public TileEntity b(final World world) {
        return new TileEntityMobSpawner();
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return 0;
    }
    
    public int a(final Random random) {
        return 0;
    }
    
    public void dropNaturally(final World world, final int i, final int j, final int k, final int l, final float f, final int i1) {
        super.dropNaturally(world, i, j, k, l, f, i1);
    }
    
    public int getExpDrop(final World world, final int data, final int enchantmentLevel) {
        final int j1 = 15 + world.random.nextInt(15) + world.random.nextInt(15);
        return j1;
    }
    
    public boolean c() {
        return false;
    }
}
