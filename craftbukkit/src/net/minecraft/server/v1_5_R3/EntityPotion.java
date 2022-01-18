// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.PotionSplashEvent;
import java.util.Iterator;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLivingEntity;
import java.util.Map;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;
import java.util.HashMap;

public class EntityPotion extends EntityProjectile
{
    public ItemStack c;
    
    public EntityPotion(final World world) {
        super(world);
    }
    
    public EntityPotion(final World world, final EntityLiving entityliving, final int i) {
        this(world, entityliving, new ItemStack(Item.POTION, 1, i));
    }
    
    public EntityPotion(final World world, final EntityLiving entityliving, final ItemStack itemstack) {
        super(world, entityliving);
        this.c = itemstack;
    }
    
    public EntityPotion(final World world, final double d0, final double d1, final double d2, final ItemStack itemstack) {
        super(world, d0, d1, d2);
        this.c = itemstack;
    }
    
    protected float g() {
        return 0.05f;
    }
    
    protected float c() {
        return 0.5f;
    }
    
    protected float d() {
        return -20.0f;
    }
    
    public void setPotionValue(final int i) {
        if (this.c == null) {
            this.c = new ItemStack(Item.POTION, 1, 0);
        }
        this.c.setData(i);
    }
    
    public int getPotionValue() {
        if (this.c == null) {
            this.c = new ItemStack(Item.POTION, 1, 0);
        }
        return this.c.getData();
    }
    
    protected void a(final MovingObjectPosition movingobjectposition) {
        if (!this.world.isStatic) {
            final List list = Item.POTION.g(this.c);
            final AxisAlignedBB axisalignedbb = this.boundingBox.grow(4.0, 2.0, 4.0);
            final List list2 = this.world.a(EntityLiving.class, axisalignedbb);
            if (list2 != null) {
                final Iterator iterator = list2.iterator();
                final HashMap<LivingEntity, Double> affected = new HashMap<LivingEntity, Double>();
                while (iterator.hasNext()) {
                    final EntityLiving entityliving = iterator.next();
                    final double d0 = this.e(entityliving);
                    if (d0 < 16.0) {
                        double d2 = 1.0 - Math.sqrt(d0) / 4.0;
                        if (entityliving == movingobjectposition.entity) {
                            d2 = 1.0;
                        }
                        affected.put((LivingEntity)entityliving.getBukkitEntity(), d2);
                    }
                }
                final PotionSplashEvent event = CraftEventFactory.callPotionSplashEvent(this, affected);
                if (!event.isCancelled() && list != null && !list.isEmpty()) {
                    for (final LivingEntity victim : event.getAffectedEntities()) {
                        if (!(victim instanceof CraftLivingEntity)) {
                            continue;
                        }
                        final EntityLiving entityliving2 = ((CraftLivingEntity)victim).getHandle();
                        final double d3 = event.getIntensity(victim);
                        for (final MobEffect mobeffect : list) {
                            final int i = mobeffect.getEffectId();
                            if (!this.world.pvpMode && this.getShooter() instanceof EntityPlayer && entityliving2 instanceof EntityPlayer && entityliving2 != this.getShooter()) {
                                if (i == 2 || i == 4 || i == 7 || i == 15 || i == 17 || i == 18) {
                                    continue;
                                }
                                if (i == 19) {
                                    continue;
                                }
                            }
                            if (MobEffectList.byId[i].isInstant()) {
                                MobEffectList.byId[i].applyInstantEffect(this.getShooter(), entityliving2, mobeffect.getAmplifier(), d3, this);
                            }
                            else {
                                final int j = (int)(d3 * mobeffect.getDuration() + 0.5);
                                if (j <= 20) {
                                    continue;
                                }
                                entityliving2.addEffect(new MobEffect(i, j, mobeffect.getAmplifier()));
                            }
                        }
                    }
                }
            }
            this.world.triggerEffect(2002, (int)Math.round(this.locX), (int)Math.round(this.locY), (int)Math.round(this.locZ), this.getPotionValue());
            this.die();
        }
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.hasKey("Potion")) {
            this.c = ItemStack.createStack(nbttagcompound.getCompound("Potion"));
        }
        else {
            this.setPotionValue(nbttagcompound.getInt("potionValue"));
        }
        if (this.c == null) {
            this.die();
        }
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (this.c != null) {
            nbttagcompound.setCompound("Potion", this.c.save(new NBTTagCompound()));
        }
    }
}
