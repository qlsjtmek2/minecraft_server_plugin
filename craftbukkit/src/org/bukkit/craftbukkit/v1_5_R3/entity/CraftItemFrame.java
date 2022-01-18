// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import org.bukkit.entity.EntityType;
import org.apache.commons.lang.Validate;
import org.bukkit.Rotation;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_5_R3.EntityHanging;
import net.minecraft.server.v1_5_R3.EntityItemFrame;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.ItemFrame;

public class CraftItemFrame extends CraftHanging implements ItemFrame
{
    public CraftItemFrame(final CraftServer server, final EntityItemFrame entity) {
        super(server, entity);
    }
    
    public void setItem(final ItemStack item) {
        if (item == null || item.getTypeId() == 0) {
            this.getHandle().getDataWatcher().a(2, 5);
            this.getHandle().getDataWatcher().h(2);
        }
        else {
            this.getHandle().a(CraftItemStack.asNMSCopy(item));
        }
    }
    
    public ItemStack getItem() {
        return CraftItemStack.asBukkitCopy(this.getHandle().i());
    }
    
    public Rotation getRotation() {
        return this.toBukkitRotation(this.getHandle().j());
    }
    
    Rotation toBukkitRotation(final int value) {
        switch (value) {
            case 0: {
                return Rotation.NONE;
            }
            case 1: {
                return Rotation.CLOCKWISE;
            }
            case 2: {
                return Rotation.FLIPPED;
            }
            case 3: {
                return Rotation.COUNTER_CLOCKWISE;
            }
            default: {
                throw new AssertionError((Object)("Unknown rotation " + this.getHandle().j() + " for " + this.getHandle()));
            }
        }
    }
    
    public void setRotation(final Rotation rotation) {
        Validate.notNull(rotation, "Rotation cannot be null");
        this.getHandle().setRotation(toInteger(rotation));
    }
    
    static int toInteger(final Rotation rotation) {
        switch (rotation) {
            case NONE: {
                return 0;
            }
            case CLOCKWISE: {
                return 1;
            }
            case FLIPPED: {
                return 2;
            }
            case COUNTER_CLOCKWISE: {
                return 3;
            }
            default: {
                throw new IllegalArgumentException(rotation + " is not applicable to an ItemFrame");
            }
        }
    }
    
    public EntityItemFrame getHandle() {
        return (EntityItemFrame)this.entity;
    }
    
    public String toString() {
        return "CraftItemFrame{item=" + this.getItem() + ", rotation=" + this.getRotation() + "}";
    }
    
    public EntityType getType() {
        return EntityType.ITEM_FRAME;
    }
}
