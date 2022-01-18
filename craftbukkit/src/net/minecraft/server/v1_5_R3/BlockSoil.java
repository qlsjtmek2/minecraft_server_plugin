// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.Random;

public class BlockSoil extends Block
{
    protected BlockSoil(final int i) {
        super(i, Material.EARTH);
        this.b(true);
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.9375f, 1.0f);
        this.k(255);
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        return AxisAlignedBB.a().a(i + 0, j + 0, k + 0, i + 1, j + 1, k + 1);
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (!this.m(world, i, j, k) && !world.F(i, j + 1, k)) {
            final int l = world.getData(i, j, k);
            if (l > 0) {
                world.setData(i, j, k, l - 1, 2);
            }
            else if (!this.k(world, i, j, k)) {
                final org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
                if (CraftEventFactory.callBlockFadeEvent(block, Block.DIRT.id).isCancelled()) {
                    return;
                }
                world.setTypeIdUpdate(i, j, k, Block.DIRT.id);
            }
        }
        else {
            world.setData(i, j, k, 7, 2);
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, final Entity entity, final float f) {
        if (!world.isStatic && world.random.nextFloat() < f - 0.5f) {
            if (!(entity instanceof EntityHuman) && !world.getGameRules().getBoolean("mobGriefing")) {
                return;
            }
            Cancellable cancellable;
            if (entity instanceof EntityHuman) {
                cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, Action.PHYSICAL, i, j, k, -1, null);
            }
            else {
                cancellable = new EntityInteractEvent(entity.getBukkitEntity(), world.getWorld().getBlockAt(i, j, k));
                world.getServer().getPluginManager().callEvent((Event)cancellable);
            }
            if (cancellable.isCancelled()) {
                return;
            }
            world.setTypeIdUpdate(i, j, k, Block.DIRT.id);
        }
    }
    
    private boolean k(final World world, final int i, final int j, final int k) {
        final byte b0 = 0;
        for (int l = i - b0; l <= i + b0; ++l) {
            for (int i2 = k - b0; i2 <= k + b0; ++i2) {
                final int j2 = world.getTypeId(l, j + 1, i2);
                if (j2 == Block.CROPS.id || j2 == Block.MELON_STEM.id || j2 == Block.PUMPKIN_STEM.id || j2 == Block.POTATOES.id || j2 == Block.CARROTS.id) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean m(final World world, final int i, final int j, final int k) {
        for (int l = i - 4; l <= i + 4; ++l) {
            for (int i2 = j; i2 <= j + 1; ++i2) {
                for (int j2 = k - 4; j2 <= k + 4; ++j2) {
                    if (world.getMaterial(l, i2, j2) == Material.WATER) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        super.doPhysics(world, i, j, k, l);
        final Material material = world.getMaterial(i, j + 1, k);
        if (material.isBuildable()) {
            world.setTypeIdUpdate(i, j, k, Block.DIRT.id);
        }
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return Block.DIRT.getDropType(0, random, j);
    }
}
