// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockBloodStone extends Block
{
    public BlockBloodStone(final int i) {
        super(i, Material.STONE);
        this.a(CreativeModeTab.b);
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        if (Block.byId[l] != null && Block.byId[l].isPowerSource()) {
            final org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            final int power = block.getBlockPower();
            final BlockRedstoneEvent event = new BlockRedstoneEvent(block, power, power);
            world.getServer().getPluginManager().callEvent(event);
        }
    }
}
