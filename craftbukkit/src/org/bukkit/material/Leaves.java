// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.TreeSpecies;
import org.bukkit.Material;

public class Leaves extends MaterialData
{
    public Leaves() {
        super(Material.LEAVES);
    }
    
    public Leaves(final TreeSpecies species) {
        this();
        this.setSpecies(species);
    }
    
    public Leaves(final int type) {
        super(type);
    }
    
    public Leaves(final Material type) {
        super(type);
    }
    
    public Leaves(final int type, final byte data) {
        super(type, data);
    }
    
    public Leaves(final Material type, final byte data) {
        super(type, data);
    }
    
    public TreeSpecies getSpecies() {
        return TreeSpecies.getByData((byte)(this.getData() & 0x3));
    }
    
    public void setSpecies(final TreeSpecies species) {
        this.setData(species.getData());
    }
    
    public String toString() {
        return this.getSpecies() + " " + super.toString();
    }
    
    public Leaves clone() {
        return (Leaves)super.clone();
    }
}
