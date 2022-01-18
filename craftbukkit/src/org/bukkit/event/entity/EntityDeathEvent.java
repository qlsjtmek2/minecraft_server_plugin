// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import org.bukkit.event.HandlerList;

public class EntityDeathEvent extends EntityEvent
{
    private static final HandlerList handlers;
    private final List<ItemStack> drops;
    private int dropExp;
    
    public EntityDeathEvent(final LivingEntity entity, final List<ItemStack> drops) {
        this(entity, drops, 0);
    }
    
    public EntityDeathEvent(final LivingEntity what, final List<ItemStack> drops, final int droppedExp) {
        super(what);
        this.dropExp = 0;
        this.drops = drops;
        this.dropExp = droppedExp;
    }
    
    public LivingEntity getEntity() {
        return (LivingEntity)this.entity;
    }
    
    public int getDroppedExp() {
        return this.dropExp;
    }
    
    public void setDroppedExp(final int exp) {
        this.dropExp = exp;
    }
    
    public List<ItemStack> getDrops() {
        return this.drops;
    }
    
    public HandlerList getHandlers() {
        return EntityDeathEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityDeathEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
