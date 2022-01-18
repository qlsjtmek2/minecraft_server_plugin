// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockFromToEvent;
import java.util.Random;

public class BlockDragonEgg extends Block
{
    public BlockDragonEgg(final int i) {
        super(i, Material.DRAGON_EGG);
        this.a(0.0625f, 0.0f, 0.0625f, 0.9375f, 1.0f, 0.9375f);
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        world.a(i, j, k, this.id, this.a(world));
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        world.a(i, j, k, this.id, this.a(world));
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        this.k(world, i, j, k);
    }
    
    private void k(final World world, final int i, int j, final int k) {
        if (BlockSand.canFall(world, i, j - 1, k) && j >= 0) {
            final byte b0 = 32;
            if (!BlockSand.instaFall && world.e(i - b0, j - b0, k - b0, i + b0, j + b0, k + b0)) {
                final EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, i + 0.5f, j + 0.5f, k + 0.5f, this.id, world.getData(i, j, k));
                world.addEntity(entityfallingblock);
            }
            else {
                world.setAir(i, j, k);
                while (BlockSand.canFall(world, i, j - 1, k) && j > 0) {
                    --j;
                }
                if (j > 0) {
                    world.setTypeIdAndData(i, j, k, this.id, 0, 2);
                }
            }
        }
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityhuman, final int l, final float f, final float f1, final float f2) {
        this.m(world, i, j, k);
        return true;
    }
    
    public void attack(final World world, final int i, final int j, final int k, final EntityHuman entityhuman) {
        this.m(world, i, j, k);
    }
    
    private void m(final World world, final int i, final int j, final int k) {
        if (world.getTypeId(i, j, k) == this.id) {
            int l = 0;
            while (l < 1000) {
                int i2 = i + world.random.nextInt(16) - world.random.nextInt(16);
                int j2 = j + world.random.nextInt(8) - world.random.nextInt(8);
                int k2 = k + world.random.nextInt(16) - world.random.nextInt(16);
                if (world.getTypeId(i2, j2, k2) == 0) {
                    final org.bukkit.block.Block from = world.getWorld().getBlockAt(i, j, k);
                    final org.bukkit.block.Block to = world.getWorld().getBlockAt(i2, j2, k2);
                    final BlockFromToEvent event = new BlockFromToEvent(from, to);
                    Bukkit.getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        return;
                    }
                    i2 = event.getToBlock().getX();
                    j2 = event.getToBlock().getY();
                    k2 = event.getToBlock().getZ();
                    if (!world.isStatic) {
                        world.setTypeIdAndData(i2, j2, k2, this.id, world.getData(i, j, k), 2);
                        world.setAir(i, j, k);
                    }
                    else {
                        final short short1 = 128;
                        for (int l2 = 0; l2 < short1; ++l2) {
                            final double d0 = world.random.nextDouble();
                            final float f = (world.random.nextFloat() - 0.5f) * 0.2f;
                            final float f2 = (world.random.nextFloat() - 0.5f) * 0.2f;
                            final float f3 = (world.random.nextFloat() - 0.5f) * 0.2f;
                            final double d2 = i2 + (i - i2) * d0 + (world.random.nextDouble() - 0.5) * 1.0 + 0.5;
                            final double d3 = j2 + (j - j2) * d0 + world.random.nextDouble() * 1.0 - 0.5;
                            final double d4 = k2 + (k - k2) * d0 + (world.random.nextDouble() - 0.5) * 1.0 + 0.5;
                            world.addParticle("portal", d2, d3, d4, f, f2, f3);
                        }
                    }
                }
                else {
                    ++l;
                }
            }
        }
    }
    
    public int a(final World world) {
        return 5;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public int d() {
        return 27;
    }
}
