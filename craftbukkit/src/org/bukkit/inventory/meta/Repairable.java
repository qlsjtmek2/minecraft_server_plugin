// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory.meta;

public interface Repairable
{
    boolean hasRepairCost();
    
    int getRepairCost();
    
    void setRepairCost(final int p0);
    
    Repairable clone();
}
