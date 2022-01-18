// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.Random;

public class BlockCactus extends Block
{
    protected BlockCactus(final int i) {
        super(i, Material.CACTUS);
        this.b(true);
        this.a(CreativeModeTab.c);
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (world.isEmpty(i, j + 1, k)) {
            int l;
            for (l = 1; world.getTypeId(i, j - l, k) == this.id; ++l) {}
            if (l < 3) {
                final int i2 = world.getData(i, j, k);
                if (i2 >= (byte)Block.range(3.0f, world.growthOdds / world.getWorld().cactusGrowthModifier * 15.0f + 0.5f, 15.0f)) {
                    CraftEventFactory.handleBlockGrowEvent(world, i, j + 1, k, this.id, 0);
                    world.setData(i, j, k, 0, 4);
                    this.doPhysics(world, i, j + 1, k, this.id);
                }
                else {
                    world.setData(i, j, k, i2 + 1, 4);
                }
            }
        }
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        final float f = 0.0625f;
        return AxisAlignedBB.a().a(i + f, j, k + f, i + 1 - f, j + 1 - f, k + 1 - f);
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean c() {
        return false;
    }
    
    public int d() {
        return 13;
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k) {
        return super.canPlace(world, i, j, k) && this.f(world, i, j, k);
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        if (!this.f(world, i, j, k)) {
            world.setAir(i, j, k, true);
        }
    }
    
    public boolean f(final World world, final int i, final int j, final int k) {
        if (world.getMaterial(i - 1, j, k).isBuildable()) {
            return false;
        }
        if (world.getMaterial(i + 1, j, k).isBuildable()) {
            return false;
        }
        if (world.getMaterial(i, j, k - 1).isBuildable()) {
            return false;
        }
        if (world.getMaterial(i, j, k + 1).isBuildable()) {
            return false;
        }
        final int l = world.getTypeId(i, j - 1, k);
        return l == Block.CACTUS.id || l == Block.SAND.id;
    }
    
    public void a(final World world, final int i, final int j, final int k, final Entity entity) {
        if (entity instanceof EntityLiving) {
            final org.bukkit.block.Block damager = world.getWorld().getBlockAt(i, j, k);
            final org.bukkit.entity.Entity damagee = (entity == null) ? null : entity.getBukkitEntity();
            final EntityDamageByBlockEvent event = new EntityDamageByBlockEvent(damager, damagee, EntityDamageEvent.DamageCause.CONTACT, 1);
            world.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                damagee.setLastDamageCause(event);
                entity.damageEntity(DamageSource.CACTUS, event.getDamage());
            }
            return;
        }
        entity.damageEntity(DamageSource.CACTUS, 1);
    }
}
