// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.TreeSpecies;
import org.bukkit.Material;

public class WoodenStep extends MaterialData
{
    public WoodenStep() {
        super(Material.WOOD_STEP);
    }
    
    public WoodenStep(final int type) {
        super(type);
    }
    
    public WoodenStep(final TreeSpecies species) {
        this();
        this.setSpecies(species);
    }
    
    public WoodenStep(final TreeSpecies species, final boolean inv) {
        this();
        this.setSpecies(species);
        this.setInverted(inv);
    }
    
    public WoodenStep(final int type, final byte data) {
        super(type, data);
    }
    
    public WoodenStep(final Material type, final byte data) {
        super(type, data);
    }
    
    public TreeSpecies getSpecies() {
        return TreeSpecies.getByData((byte)(this.getData() & 0x3));
    }
    
    public void setSpecies(final TreeSpecies species) {
        this.setData((byte)((this.getData() & 0xC) | species.getData()));
    }
    
    public boolean isInverted() {
        return (this.getData() & 0x8) != 0x0;
    }
    
    public void setInverted(final boolean inv) {
        int dat = this.getData() & 0x7;
        if (inv) {
            dat |= 0x8;
        }
        this.setData((byte)dat);
    }
    
    public WoodenStep clone() {
        return (WoodenStep)super.clone();
    }
    
    public String toString() {
        return super.toString() + " " + this.getSpecies() + (this.isInverted() ? " inverted" : "");
    }
}
