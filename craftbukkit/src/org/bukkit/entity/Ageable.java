// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

public interface Ageable extends Creature
{
    int getAge();
    
    void setAge(final int p0);
    
    void setAgeLock(final boolean p0);
    
    boolean getAgeLock();
    
    void setBaby();
    
    void setAdult();
    
    boolean isAdult();
    
    boolean canBreed();
    
    void setBreed(final boolean p0);
}
