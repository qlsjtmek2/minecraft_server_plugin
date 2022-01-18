// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class FoodMetaData
{
    public int foodLevel;
    public float saturationLevel;
    public float exhaustionLevel;
    public int foodTickTimer;
    private int e;
    
    public FoodMetaData() {
        this.foodLevel = 20;
        this.saturationLevel = 5.0f;
        this.foodTickTimer = 0;
        this.e = 20;
    }
    
    public void eat(final int i, final float f) {
        this.foodLevel = Math.min(i + this.foodLevel, 20);
        this.saturationLevel = Math.min(this.saturationLevel + i * f * 2.0f, this.foodLevel);
    }
    
    public void a(final ItemFood itemfood) {
        this.eat(itemfood.getNutrition(), itemfood.getSaturationModifier());
    }
    
    public void a(final EntityHuman entityhuman) {
        final int i = entityhuman.world.difficulty;
        this.e = this.foodLevel;
        if (this.exhaustionLevel > 4.0f) {
            this.exhaustionLevel -= 4.0f;
            if (this.saturationLevel > 0.0f) {
                this.saturationLevel = Math.max(this.saturationLevel - 1.0f, 0.0f);
            }
            else if (i > 0) {
                final FoodLevelChangeEvent event = CraftEventFactory.callFoodLevelChangeEvent(entityhuman, Math.max(this.foodLevel - 1, 0));
                if (!event.isCancelled()) {
                    this.foodLevel = event.getFoodLevel();
                }
                ((EntityPlayer)entityhuman).playerConnection.sendPacket(new Packet8UpdateHealth(((EntityPlayer)entityhuman).getScaledHealth(), this.foodLevel, this.saturationLevel));
            }
        }
        if (this.foodLevel >= 18 && entityhuman.co()) {
            ++this.foodTickTimer;
            if (this.foodTickTimer >= 80) {
                entityhuman.heal(1, EntityRegainHealthEvent.RegainReason.SATIATED);
                this.foodTickTimer = 0;
            }
        }
        else if (this.foodLevel <= 0) {
            ++this.foodTickTimer;
            if (this.foodTickTimer >= 80) {
                if (entityhuman.getHealth() > 10 || i >= 3 || (entityhuman.getHealth() > 1 && i >= 2)) {
                    entityhuman.damageEntity(DamageSource.STARVE, 1);
                }
                this.foodTickTimer = 0;
            }
        }
        else {
            this.foodTickTimer = 0;
        }
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        if (nbttagcompound.hasKey("foodLevel")) {
            this.foodLevel = nbttagcompound.getInt("foodLevel");
            this.foodTickTimer = nbttagcompound.getInt("foodTickTimer");
            this.saturationLevel = nbttagcompound.getFloat("foodSaturationLevel");
            this.exhaustionLevel = nbttagcompound.getFloat("foodExhaustionLevel");
        }
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setInt("foodLevel", this.foodLevel);
        nbttagcompound.setInt("foodTickTimer", this.foodTickTimer);
        nbttagcompound.setFloat("foodSaturationLevel", this.saturationLevel);
        nbttagcompound.setFloat("foodExhaustionLevel", this.exhaustionLevel);
    }
    
    public int a() {
        return this.foodLevel;
    }
    
    public boolean c() {
        return this.foodLevel < 20;
    }
    
    public void a(final float f) {
        this.exhaustionLevel = Math.min(this.exhaustionLevel + f, 40.0f);
    }
    
    public float e() {
        return this.saturationLevel;
    }
}
