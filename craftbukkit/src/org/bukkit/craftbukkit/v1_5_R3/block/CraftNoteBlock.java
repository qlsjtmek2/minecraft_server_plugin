// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.block;

import org.bukkit.Instrument;
import net.minecraft.server.v1_5_R3.World;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.Block;
import net.minecraft.server.v1_5_R3.TileEntityNote;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.block.NoteBlock;

public class CraftNoteBlock extends CraftBlockState implements NoteBlock
{
    private final CraftWorld world;
    private final TileEntityNote note;
    
    public CraftNoteBlock(final Block block) {
        super(block);
        this.world = (CraftWorld)block.getWorld();
        this.note = (TileEntityNote)this.world.getTileEntityAt(this.getX(), this.getY(), this.getZ());
    }
    
    public Note getNote() {
        return new Note(this.note.note);
    }
    
    public byte getRawNote() {
        return this.note.note;
    }
    
    public void setNote(final Note n) {
        this.note.note = n.getId();
    }
    
    public void setRawNote(final byte n) {
        this.note.note = n;
    }
    
    public boolean play() {
        final Block block = this.getBlock();
        if (block.getType() == Material.NOTE_BLOCK) {
            this.note.play(this.world.getHandle(), this.getX(), this.getY(), this.getZ());
            return true;
        }
        return false;
    }
    
    public boolean play(final byte instrument, final byte note) {
        final Block block = this.getBlock();
        if (block.getType() == Material.NOTE_BLOCK) {
            this.world.getHandle().playNote(this.getX(), this.getY(), this.getZ(), block.getTypeId(), instrument, note);
            return true;
        }
        return false;
    }
    
    public boolean play(final Instrument instrument, final Note note) {
        final Block block = this.getBlock();
        if (block.getType() == Material.NOTE_BLOCK) {
            this.world.getHandle().playNote(this.getX(), this.getY(), this.getZ(), block.getTypeId(), instrument.getType(), note.getId());
            return true;
        }
        return false;
    }
}
