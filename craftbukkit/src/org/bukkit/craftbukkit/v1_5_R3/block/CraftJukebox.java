// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.block;

import net.minecraft.server.v1_5_R3.World;
import net.minecraft.server.v1_5_R3.BlockJukeBox;
import org.bukkit.Effect;
import net.minecraft.server.v1_5_R3.Item;
import net.minecraft.server.v1_5_R3.ItemStack;
import org.bukkit.Material;
import org.bukkit.block.Block;
import net.minecraft.server.v1_5_R3.TileEntityRecordPlayer;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.block.Jukebox;

public class CraftJukebox extends CraftBlockState implements Jukebox
{
    private final CraftWorld world;
    private final TileEntityRecordPlayer jukebox;
    
    public CraftJukebox(final Block block) {
        super(block);
        this.world = (CraftWorld)block.getWorld();
        this.jukebox = (TileEntityRecordPlayer)this.world.getTileEntityAt(this.getX(), this.getY(), this.getZ());
    }
    
    public Material getPlaying() {
        final ItemStack record = this.jukebox.getRecord();
        if (record == null) {
            return Material.AIR;
        }
        return Material.getMaterial(record.id);
    }
    
    public void setPlaying(Material record) {
        if (record == null || Item.byId[record.getId()] == null) {
            record = Material.AIR;
            this.jukebox.setRecord(null);
        }
        else {
            this.jukebox.setRecord(new ItemStack(Item.byId[record.getId()], 1));
        }
        this.jukebox.update();
        if (record == Material.AIR) {
            this.world.getHandle().setData(this.getX(), this.getY(), this.getZ(), 0, 3);
        }
        else {
            this.world.getHandle().setData(this.getX(), this.getY(), this.getZ(), 1, 3);
        }
        this.world.playEffect(this.getLocation(), Effect.RECORD_PLAY, record.getId());
    }
    
    public boolean isPlaying() {
        return this.getRawData() == 1;
    }
    
    public boolean eject() {
        final boolean result = this.isPlaying();
        ((BlockJukeBox)net.minecraft.server.v1_5_R3.Block.JUKEBOX).dropRecord(this.world.getHandle(), this.getX(), this.getY(), this.getZ());
        return result;
    }
}
