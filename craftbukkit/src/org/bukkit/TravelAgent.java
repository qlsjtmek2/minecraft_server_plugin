// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

public interface TravelAgent
{
    TravelAgent setSearchRadius(final int p0);
    
    int getSearchRadius();
    
    TravelAgent setCreationRadius(final int p0);
    
    int getCreationRadius();
    
    boolean getCanCreatePortal();
    
    void setCanCreatePortal(final boolean p0);
    
    Location findOrCreate(final Location p0);
    
    Location findPortal(final Location p0);
    
    boolean createPortal(final Location p0);
}
