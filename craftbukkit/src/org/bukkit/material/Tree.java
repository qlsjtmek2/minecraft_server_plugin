// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.TreeSpecies;
import org.bukkit.Material;

public class Tree extends MaterialData
{
    public Tree() {
        super(Material.LOG);
    }
    
    public Tree(final TreeSpecies species) {
        this();
        this.setSpecies(species);
    }
    
    public Tree(final TreeSpecies species, final BlockFace dir) {
        this();
        this.setSpecies(species);
        this.setDirection(dir);
    }
    
    public Tree(final int type) {
        super(type);
    }
    
    public Tree(final Material type) {
        super(type);
    }
    
    public Tree(final int type, final byte data) {
        super(type, data);
    }
    
    public Tree(final Material type, final byte data) {
        super(type, data);
    }
    
    public TreeSpecies getSpecies() {
        return TreeSpecies.getByData((byte)(this.getData() & 0x3));
    }
    
    public void setSpecies(final TreeSpecies species) {
        this.setData((byte)((this.getData() & 0xC) | species.getData()));
    }
    
    public BlockFace getDirection() {
        switch (this.getData() >> 2 & 0x3) {
            default: {
                return BlockFace.UP;
            }
            case 1: {
                return BlockFace.WEST;
            }
            case 2: {
                return BlockFace.NORTH;
            }
            case 3: {
                return BlockFace.SELF;
            }
        }
    }
    
    public void setDirection(final BlockFace dir) {
        int dat = 0;
        switch (dir) {
            default: {
                dat = 0;
                break;
            }
            case WEST:
            case EAST: {
                dat = 1;
                break;
            }
            case NORTH:
            case SOUTH: {
                dat = 2;
                break;
            }
            case SELF: {
                dat = 3;
                break;
            }
        }
        this.setData((byte)((this.getData() & 0x3) | dat << 2));
    }
    
    public String toString() {
        return this.getSpecies() + " " + this.getDirection() + " " + super.toString();
    }
    
    public Tree clone() {
        return (Tree)super.clone();
    }
}
