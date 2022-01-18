// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory.meta;

import java.util.List;
import org.bukkit.FireworkEffect;

public interface FireworkMeta extends ItemMeta
{
    void addEffect(final FireworkEffect p0) throws IllegalArgumentException;
    
    void addEffects(final FireworkEffect... p0) throws IllegalArgumentException;
    
    void addEffects(final Iterable<FireworkEffect> p0) throws IllegalArgumentException;
    
    List<FireworkEffect> getEffects();
    
    int getEffectsSize();
    
    void removeEffect(final int p0) throws IndexOutOfBoundsException;
    
    void clearEffects();
    
    boolean hasEffects();
    
    int getPower();
    
    void setPower(final int p0) throws IllegalArgumentException;
    
    FireworkMeta clone();
}
