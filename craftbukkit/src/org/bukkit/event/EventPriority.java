// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event;

public enum EventPriority
{
    LOWEST(0), 
    LOW(1), 
    NORMAL(2), 
    HIGH(3), 
    HIGHEST(4), 
    MONITOR(5);
    
    private final int slot;
    
    private EventPriority(final int slot) {
        this.slot = slot;
    }
    
    public int getSlot() {
        return this.slot;
    }
}
