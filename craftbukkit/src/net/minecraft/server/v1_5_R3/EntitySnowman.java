// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import org.bukkit.block.BlockState;
import org.bukkit.event.Event;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class EntitySnowman extends EntityGolem implements IRangedEntity
{
    public EntitySnowman(final World world) {
        super(world);
        this.texture = "/mob/snowman.png";
        this.a(0.4f, 1.8f);
        this.getNavigation().a(true);
        this.goalSelector.a(1, new PathfinderGoalArrowAttack(this, 0.25f, 20, 10.0f));
        this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 0.2f));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0f));
        this.goalSelector.a(4, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityLiving.class, 16.0f, 0, true, false, IMonster.a));
    }
    
    public boolean bh() {
        return true;
    }
    
    public int getMaxHealth() {
        return 4;
    }
    
    public void c() {
        super.c();
        if (this.F()) {
            this.damageEntity(DamageSource.DROWN, 1);
        }
        int i = MathHelper.floor(this.locX);
        int j = MathHelper.floor(this.locZ);
        if (this.world.getBiome(i, j).j() > 1.0f) {
            this.damageEntity(CraftEventFactory.MELTING, 1);
        }
        for (i = 0; i < 4; ++i) {
            j = MathHelper.floor(this.locX + (i % 2 * 2 - 1) * 0.25f);
            final int k = MathHelper.floor(this.locY);
            final int l = MathHelper.floor(this.locZ + (i / 2 % 2 * 2 - 1) * 0.25f);
            if (this.world.getTypeId(j, k, l) == 0 && this.world.getBiome(j, l).j() < 0.8f && Block.SNOW.canPlace(this.world, j, k, l)) {
                final BlockState blockState = this.world.getWorld().getBlockAt(j, k, l).getState();
                blockState.setTypeId(Block.SNOW.id);
                final EntityBlockFormEvent event = new EntityBlockFormEvent(this.getBukkitEntity(), blockState.getBlock(), blockState);
                this.world.getServer().getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    blockState.update(true);
                }
            }
        }
    }
    
    protected int getLootId() {
        return Item.SNOW_BALL.id;
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final List<ItemStack> loot = new ArrayList<ItemStack>();
        final int j = this.random.nextInt(16);
        if (j > 0) {
            loot.add(new ItemStack(Item.SNOW_BALL.id, j));
        }
        CraftEventFactory.callEntityDeathEvent(this, loot);
    }
    
    public void a(final EntityLiving entityliving, final float f) {
        final EntitySnowball entitysnowball = new EntitySnowball(this.world, this);
        final double d0 = entityliving.locX - this.locX;
        final double d2 = entityliving.locY + entityliving.getHeadHeight() - 1.100000023841858 - entitysnowball.locY;
        final double d3 = entityliving.locZ - this.locZ;
        final float f2 = MathHelper.sqrt(d0 * d0 + d3 * d3) * 0.2f;
        entitysnowball.shoot(d0, d2 + f2, d3, 1.6f, 12.0f);
        this.makeSound("random.bow", 1.0f, 1.0f / (this.aE().nextFloat() * 0.4f + 0.8f));
        this.world.addEntity(entitysnowball);
    }
}
