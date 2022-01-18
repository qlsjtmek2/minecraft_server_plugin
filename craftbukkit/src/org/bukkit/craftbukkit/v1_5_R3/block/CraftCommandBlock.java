// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.block;

import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.block.Block;
import net.minecraft.server.v1_5_R3.TileEntityCommand;
import org.bukkit.block.CommandBlock;

public class CraftCommandBlock extends CraftBlockState implements CommandBlock
{
    private final TileEntityCommand commandBlock;
    private String command;
    private String name;
    
    public CraftCommandBlock(final Block block) {
        super(block);
        final CraftWorld world = (CraftWorld)block.getWorld();
        this.commandBlock = (TileEntityCommand)world.getTileEntityAt(this.getX(), this.getY(), this.getZ());
        this.command = this.commandBlock.b;
        this.name = this.commandBlock.getName();
    }
    
    public String getCommand() {
        return this.command;
    }
    
    public void setCommand(final String command) {
        this.command = ((command != null) ? command : "");
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = ((name != null) ? name : "@");
    }
    
    public boolean update(final boolean force, final boolean applyPhysics) {
        final boolean result = super.update(force, applyPhysics);
        if (result) {
            this.commandBlock.b(this.command);
            this.commandBlock.c(this.name);
        }
        return result;
    }
}
