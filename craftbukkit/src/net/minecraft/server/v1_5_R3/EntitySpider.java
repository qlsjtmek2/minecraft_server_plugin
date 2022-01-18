// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntitySpider extends EntityMonster
{
    public EntitySpider(final World world) {
        super(world);
        this.texture = "/mob/spider.png";
        this.a(1.4f, 0.9f);
        this.bI = 0.8f;
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(16, new Byte((byte)0));
    }
    
    public void l_() {
        super.l_();
        if (!this.world.isStatic) {
            this.a(this.positionChanged);
        }
    }
    
    public int getMaxHealth() {
        return 16;
    }
    
    public double W() {
        return this.length * 0.75 - 0.5;
    }
    
    protected Entity findTarget() {
        final float f = this.c(1.0f);
        if (f < 0.5f) {
            final double d0 = 16.0;
            return this.world.findNearbyVulnerablePlayer(this, d0);
        }
        return null;
    }
    
    protected String bb() {
        return "mob.spider.say";
    }
    
    protected String bc() {
        return "mob.spider.say";
    }
    
    protected String bd() {
        return "mob.spider.death";
    }
    
    protected void a(final int i, final int j, final int k, final int l) {
        this.makeSound("mob.spider.step", 0.15f, 1.0f);
    }
    
    protected void a(final Entity entity, final float f) {
        final float f2 = this.c(1.0f);
        if (f2 > 0.5f && this.random.nextInt(100) == 0) {
            final EntityTargetEvent event = new EntityTargetEvent(this.getBukkitEntity(), null, EntityTargetEvent.TargetReason.FORGOT_TARGET);
            this.world.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                if (event.getTarget() == null) {
                    this.target = null;
                }
                else {
                    this.target = ((CraftEntity)event.getTarget()).getHandle();
                }
            }
        }
        else if (f > 2.0f && f < 6.0f && this.random.nextInt(10) == 0) {
            if (this.onGround) {
                final double d0 = entity.locX - this.locX;
                final double d2 = entity.locZ - this.locZ;
                final float f3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
                this.motX = d0 / f3 * 0.5 * 0.800000011920929 + this.motX * 0.20000000298023224;
                this.motZ = d2 / f3 * 0.5 * 0.800000011920929 + this.motZ * 0.20000000298023224;
                this.motY = 0.4000000059604645;
            }
        }
        else {
            super.a(entity, f);
        }
    }
    
    protected int getLootId() {
        return Item.STRING.id;
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final List<ItemStack> loot = new ArrayList<ItemStack>();
        int k = this.random.nextInt(3);
        if (i > 0) {
            k += this.random.nextInt(i + 1);
        }
        if (k > 0) {
            loot.add(new ItemStack(Item.STRING.id, k));
        }
        if (flag && (this.random.nextInt(3) == 0 || this.random.nextInt(1 + i) > 0)) {
            loot.add(new ItemStack(Item.SPIDER_EYE.id, 1));
        }
        CraftEventFactory.callEntityDeathEvent(this, loot);
    }
    
    public boolean g_() {
        return this.o();
    }
    
    public void al() {
    }
    
    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.ARTHROPOD;
    }
    
    public boolean e(final MobEffect mobeffect) {
        return mobeffect.getEffectId() != MobEffectList.POISON.id && super.e(mobeffect);
    }
    
    public boolean o() {
        return (this.datawatcher.getByte(16) & 0x1) != 0x0;
    }
    
    public void a(final boolean flag) {
        byte b0 = this.datawatcher.getByte(16);
        if (flag) {
            b0 |= 0x1;
        }
        else {
            b0 &= 0xFFFFFFFE;
        }
        this.datawatcher.watch(16, b0);
    }
    
    public void bJ() {
        if (this.world.random.nextInt(100) == 0) {
            final EntitySkeleton entityskeleton = new EntitySkeleton(this.world);
            entityskeleton.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0f);
            entityskeleton.bJ();
            this.world.addEntity(entityskeleton);
            entityskeleton.mount(this);
        }
    }
}
