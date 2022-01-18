// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.block.Action;

public class BlockRedstoneOre extends Block
{
    private boolean a;
    
    public BlockRedstoneOre(final int i, final boolean flag) {
        super(i, Material.STONE);
        if (flag) {
            this.b(true);
        }
        this.a = flag;
    }
    
    public int a(final World world) {
        return 30;
    }
    
    public void attack(final World world, final int i, final int j, final int k, final EntityHuman entityhuman) {
        this.k(world, i, j, k);
        super.attack(world, i, j, k, entityhuman);
    }
    
    public void b(final World world, final int i, final int j, final int k, final Entity entity) {
        if (entity instanceof EntityHuman) {
            final PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, Action.PHYSICAL, i, j, k, -1, null);
            if (!event.isCancelled()) {
                this.k(world, i, j, k);
                super.b(world, i, j, k, entity);
            }
        }
        else {
            final EntityInteractEvent event2 = new EntityInteractEvent(entity.getBukkitEntity(), world.getWorld().getBlockAt(i, j, k));
            world.getServer().getPluginManager().callEvent(event2);
            if (!event2.isCancelled()) {
                this.k(world, i, j, k);
                super.b(world, i, j, k, entity);
            }
        }
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityhuman, final int l, final float f, final float f1, final float f2) {
        this.k(world, i, j, k);
        return super.interact(world, i, j, k, entityhuman, l, f, f1, f2);
    }
    
    private void k(final World world, final int i, final int j, final int k) {
        this.m(world, i, j, k);
        if (this.id == Block.REDSTONE_ORE.id) {
            world.setTypeIdUpdate(i, j, k, Block.GLOWING_REDSTONE_ORE.id);
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (this.id == Block.GLOWING_REDSTONE_ORE.id) {
            world.setTypeIdUpdate(i, j, k, Block.REDSTONE_ORE.id);
        }
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return Item.REDSTONE.id;
    }
    
    public int getDropCount(final int i, final Random random) {
        return this.a(random) + random.nextInt(i + 1);
    }
    
    public int a(final Random random) {
        return 4 + random.nextInt(2);
    }
    
    public void dropNaturally(final World world, final int i, final int j, final int k, final int l, final float f, final int i1) {
        super.dropNaturally(world, i, j, k, l, f, i1);
    }
    
    public int getExpDrop(final World world, final int l, final int i1) {
        if (this.getDropType(l, world.random, i1) != this.id) {
            final int j1 = 1 + world.random.nextInt(5);
            return j1;
        }
        return 0;
    }
    
    private void m(final World world, final int i, final int j, final int k) {
        final Random random = world.random;
        final double d0 = 0.0625;
        for (int l = 0; l < 6; ++l) {
            double d2 = i + random.nextFloat();
            double d3 = j + random.nextFloat();
            double d4 = k + random.nextFloat();
            if (l == 0 && !world.t(i, j + 1, k)) {
                d3 = j + 1 + d0;
            }
            if (l == 1 && !world.t(i, j - 1, k)) {
                d3 = j + 0 - d0;
            }
            if (l == 2 && !world.t(i, j, k + 1)) {
                d4 = k + 1 + d0;
            }
            if (l == 3 && !world.t(i, j, k - 1)) {
                d4 = k + 0 - d0;
            }
            if (l == 4 && !world.t(i + 1, j, k)) {
                d2 = i + 1 + d0;
            }
            if (l == 5 && !world.t(i - 1, j, k)) {
                d2 = i + 0 - d0;
            }
            if (d2 < i || d2 > i + 1 || d3 < 0.0 || d3 > j + 1 || d4 < k || d4 > k + 1) {
                world.addParticle("reddust", d2, d3, d4, 0.0, 0.0, 0.0);
            }
        }
    }
    
    protected ItemStack c_(final int i) {
        return new ItemStack(Block.REDSTONE_ORE);
    }
}
