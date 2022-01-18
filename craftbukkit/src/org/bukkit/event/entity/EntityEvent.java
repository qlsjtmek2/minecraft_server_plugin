// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

public abstract class EntityEvent extends Event
{
    protected Entity entity;
    
    public EntityEvent(final Entity what) {
        this.entity = what;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public EntityType getEntityType() {
        return this.entity.getType();
    }
}
