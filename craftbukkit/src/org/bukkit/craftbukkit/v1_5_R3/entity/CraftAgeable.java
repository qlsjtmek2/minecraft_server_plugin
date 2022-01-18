// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityAgeable;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Ageable;

public class CraftAgeable extends CraftCreature implements Ageable
{
    public CraftAgeable(final CraftServer server, final EntityAgeable entity) {
        super(server, entity);
    }
    
    public int getAge() {
        return this.getHandle().getAge();
    }
    
    public void setAge(final int age) {
        this.getHandle().setAge(age);
    }
    
    public void setAgeLock(final boolean lock) {
        this.getHandle().ageLocked = lock;
    }
    
    public boolean getAgeLock() {
        return this.getHandle().ageLocked;
    }
    
    public void setBaby() {
        if (this.isAdult()) {
            this.setAge(-24000);
        }
    }
    
    public void setAdult() {
        if (!this.isAdult()) {
            this.setAge(0);
        }
    }
    
    public boolean isAdult() {
        return this.getAge() >= 0;
    }
    
    public boolean canBreed() {
        return this.getAge() == 0;
    }
    
    public void setBreed(final boolean breed) {
        if (breed) {
            this.setAge(0);
        }
        else if (this.isAdult()) {
            this.setAge(6000);
        }
    }
    
    public EntityAgeable getHandle() {
        return (EntityAgeable)this.entity;
    }
    
    public String toString() {
        return "CraftAgeable";
    }
}
