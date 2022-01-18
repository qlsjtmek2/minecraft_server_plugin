// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockSand extends Block
{
    public static boolean instaFall;
    
    public BlockSand(final int i) {
        super(i, Material.SAND);
        this.a(CreativeModeTab.b);
    }
    
    public BlockSand(final int i, final Material material) {
        super(i, material);
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        world.a(i, j, k, this.id, this.a(world));
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int n) {
        world.a(i, j, k, this.id, this.a(world));
    }
    
    public void a(final World world, final int n, final int n2, final int n3, final Random random) {
        if (!world.isStatic) {
            this.k(world, n, n2, n3);
        }
    }
    
    private void k(final World world, final int i, int j, final int k) {
        final int n = j;
        if (canFall(world, i, n - 1, k) && n >= 0) {
            final int n2 = 32;
            if (BlockSand.instaFall || !world.e(i - n2, j - n2, k - n2, i + n2, j + n2, k + n2)) {
                world.setAir(i, j, k);
                while (canFall(world, i, j - 1, k) && j > 0) {
                    --j;
                }
                if (j > 0) {
                    world.setTypeIdUpdate(i, j, k, this.id);
                }
            }
            else if (!world.isStatic) {
                final EntityFallingBlock entity = new EntityFallingBlock(world, i + 0.5f, j + 0.5f, k + 0.5f, this.id, world.getData(i, j, k));
                this.a(entity);
                world.addEntity(entity);
            }
        }
    }
    
    protected void a(final EntityFallingBlock entityFallingBlock) {
    }
    
    public int a(final World world) {
        return 2;
    }
    
    public static boolean canFall(final World world, final int i, final int j, final int k) {
        final int typeId = world.getTypeId(i, j, k);
        if (typeId == 0) {
            return true;
        }
        if (typeId == Block.FIRE.id) {
            return true;
        }
        final Material material = Block.byId[typeId].material;
        return material == Material.WATER || material == Material.LAVA;
    }
    
    public void a_(final World world, final int n, final int n2, final int n3, final int n4) {
    }
    
    static {
        BlockSand.instaFall = false;
    }
}
