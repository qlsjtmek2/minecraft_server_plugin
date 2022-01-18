// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class EntityThrownExpBottle extends EntityProjectile
{
    public EntityThrownExpBottle(final World world) {
        super(world);
    }
    
    public EntityThrownExpBottle(final World world, final EntityLiving entityliving) {
        super(world, entityliving);
    }
    
    public EntityThrownExpBottle(final World world, final double d0, final double d1, final double d2) {
        super(world, d0, d1, d2);
    }
    
    protected float g() {
        return 0.07f;
    }
    
    protected float c() {
        return 0.7f;
    }
    
    protected float d() {
        return -20.0f;
    }
    
    protected void a(final MovingObjectPosition movingobjectposition) {
        if (!this.world.isStatic) {
            int i = 3 + this.world.random.nextInt(5) + this.world.random.nextInt(5);
            final ExpBottleEvent event = CraftEventFactory.callExpBottleEvent(this, i);
            i = event.getExperience();
            if (event.getShowEffect()) {
                this.world.triggerEffect(2002, (int)Math.round(this.locX), (int)Math.round(this.locY), (int)Math.round(this.locZ), 0);
            }
            while (i > 0) {
                final int j = EntityExperienceOrb.getOrbValue(i);
                i -= j;
                this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
            }
            this.die();
        }
    }
}
