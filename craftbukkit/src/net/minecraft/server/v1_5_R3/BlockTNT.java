// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class BlockTNT extends Block
{
    public BlockTNT(final int i) {
        super(i, Material.TNT);
        this.a(CreativeModeTab.d);
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        super.onPlace(world, i, j, k);
        if (world.isBlockIndirectlyPowered(i, j, k)) {
            this.postBreak(world, i, j, k, 1);
            world.setAir(i, j, k);
        }
    }
    
    public void doPhysics(final World world, final int n, final int n2, final int n3, final int n4) {
        if (world.isBlockIndirectlyPowered(n, n2, n3)) {
            this.postBreak(world, n, n2, n3, 1);
            world.setAir(n, n2, n3);
        }
    }
    
    public int a(final Random random) {
        return 1;
    }
    
    public void wasExploded(final World world, final int n, final int n2, final int n3, final Explosion explosion) {
        if (world.isStatic) {
            return;
        }
        final EntityTNTPrimed entity = new EntityTNTPrimed(world, n + 0.5f, n2 + 0.5f, n3 + 0.5f, explosion.c());
        entity.fuseTicks = world.random.nextInt(entity.fuseTicks / 4) + entity.fuseTicks / 8;
        world.addEntity(entity);
    }
    
    public void postBreak(final World world, final int n, final int n2, final int n3, final int n4) {
        this.a(world, n, n2, n3, n4, (EntityLiving)null);
    }
    
    public void a(final World world, final int n, final int n2, final int n3, final int n4, final EntityLiving entityliving) {
        if (world.isStatic) {
            return;
        }
        if ((n4 & 0x1) == 0x1) {
            final EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(world, n + 0.5f, n2 + 0.5f, n3 + 0.5f, entityliving);
            world.addEntity(entityTNTPrimed);
            world.makeSound(entityTNTPrimed, "random.fuse", 1.0f, 1.0f);
        }
    }
    
    public boolean interact(final World world, final int n, final int n2, final int n3, final EntityHuman entityhuman, final int l, final float f, final float f2, final float f3) {
        if (entityhuman.cd() != null && entityhuman.cd().id == Item.FLINT_AND_STEEL.id) {
            this.a(world, n, n2, n3, 1, (EntityLiving)entityhuman);
            world.setAir(n, n2, n3);
            return true;
        }
        return super.interact(world, n, n2, n3, entityhuman, l, f, f2, f3);
    }
    
    public void a(final World world, final int i, final int j, final int k, final Entity entity) {
        if (entity instanceof EntityArrow && !world.isStatic) {
            final EntityArrow entityArrow = (EntityArrow)entity;
            if (entityArrow.isBurning()) {
                this.a(world, i, j, k, 1, (entityArrow.shooter instanceof EntityLiving) ? ((EntityLiving)entityArrow.shooter) : null);
                world.setAir(i, j, k);
            }
        }
    }
    
    public boolean a(final Explosion explosion) {
        return false;
    }
}
