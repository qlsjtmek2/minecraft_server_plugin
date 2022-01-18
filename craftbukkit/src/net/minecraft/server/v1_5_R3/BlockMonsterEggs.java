// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockMonsterEggs extends Block
{
    public static final String[] a;
    
    public BlockMonsterEggs(final int i) {
        super(i, Material.CLAY);
        this.c(0.0f);
        this.a(CreativeModeTab.c);
    }
    
    public void postBreak(final World world, final int i, final int j, final int k, final int l) {
        if (!world.isStatic) {
            final EntitySilverfish entity = new EntitySilverfish(world);
            entity.setPositionRotation(i + 0.5, j, k + 0.5, 0.0f, 0.0f);
            world.addEntity(entity);
            entity.aU();
        }
        super.postBreak(world, i, j, k, l);
    }
    
    public int a(final Random random) {
        return 0;
    }
    
    public static boolean d(final int n) {
        return n == Block.STONE.id || n == Block.COBBLESTONE.id || n == Block.SMOOTH_BRICK.id;
    }
    
    public static int e(final int n) {
        if (n == Block.COBBLESTONE.id) {
            return 1;
        }
        if (n == Block.SMOOTH_BRICK.id) {
            return 2;
        }
        return 0;
    }
    
    protected ItemStack c_(final int n) {
        Block block = Block.STONE;
        if (n == 1) {
            block = Block.COBBLESTONE;
        }
        if (n == 2) {
            block = Block.SMOOTH_BRICK;
        }
        return new ItemStack(block);
    }
    
    public int getDropData(final World world, final int i, final int j, final int k) {
        return world.getData(i, j, k);
    }
    
    static {
        a = new String[] { "stone", "cobble", "brick" };
    }
}
