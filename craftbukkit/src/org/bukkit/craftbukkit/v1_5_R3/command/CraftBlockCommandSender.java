// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.command;

import org.bukkit.block.Block;
import net.minecraft.server.v1_5_R3.TileEntityCommand;
import org.bukkit.command.BlockCommandSender;

public class CraftBlockCommandSender extends ServerCommandSender implements BlockCommandSender
{
    private final TileEntityCommand commandBlock;
    
    public CraftBlockCommandSender(final TileEntityCommand commandBlock) {
        this.commandBlock = commandBlock;
    }
    
    public Block getBlock() {
        return this.commandBlock.getWorld().getWorld().getBlockAt(this.commandBlock.x, this.commandBlock.y, this.commandBlock.z);
    }
    
    public void sendMessage(final String message) {
    }
    
    public void sendMessage(final String[] messages) {
    }
    
    public String getName() {
        return this.commandBlock.getName();
    }
    
    public boolean isOp() {
        return true;
    }
    
    public void setOp(final boolean value) {
        throw new UnsupportedOperationException("Cannot change operator status of a block");
    }
}
