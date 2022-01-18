// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.block;

public interface CommandBlock extends BlockState
{
    String getCommand();
    
    void setCommand(final String p0);
    
    String getName();
    
    void setName(final String p0);
}
