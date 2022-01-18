// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Egg;
import org.bukkit.event.HandlerList;

public class PlayerEggThrowEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private final Egg egg;
    private boolean hatching;
    private EntityType hatchType;
    private byte numHatches;
    
    public PlayerEggThrowEvent(final Player player, final Egg egg, final boolean hatching, final byte numHatches, final EntityType hatchingType) {
        super(player);
        this.egg = egg;
        this.hatching = hatching;
        this.numHatches = numHatches;
        this.hatchType = hatchingType;
    }
    
    public PlayerEggThrowEvent(final Player player, final Egg egg, final boolean hatching, final byte numHatches, final CreatureType hatchingType) {
        this(player, egg, hatching, numHatches, hatchingType.toEntityType());
    }
    
    public Egg getEgg() {
        return this.egg;
    }
    
    public boolean isHatching() {
        return this.hatching;
    }
    
    public void setHatching(final boolean hatching) {
        this.hatching = hatching;
    }
    
    @Deprecated
    public CreatureType getHatchType() {
        return CreatureType.fromEntityType(this.hatchType);
    }
    
    public EntityType getHatchingType() {
        return this.hatchType;
    }
    
    @Deprecated
    public void setHatchType(final CreatureType hatchType) {
        this.hatchType = hatchType.toEntityType();
    }
    
    public void setHatchingType(final EntityType hatchType) {
        if (!hatchType.isSpawnable()) {
            throw new IllegalArgumentException("Can't spawn that entity type from an egg!");
        }
        this.hatchType = hatchType;
    }
    
    public byte getNumHatches() {
        return this.numHatches;
    }
    
    public void setNumHatches(final byte numHatches) {
        this.numHatches = numHatches;
    }
    
    public HandlerList getHandlers() {
        return PlayerEggThrowEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerEggThrowEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
