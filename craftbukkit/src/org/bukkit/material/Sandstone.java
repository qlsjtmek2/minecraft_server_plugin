// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.SandstoneType;
import org.bukkit.Material;

public class Sandstone extends MaterialData
{
    public Sandstone() {
        super(Material.SANDSTONE);
    }
    
    public Sandstone(final SandstoneType type) {
        this();
        this.setType(type);
    }
    
    public Sandstone(final int type) {
        super(type);
    }
    
    public Sandstone(final Material type) {
        super(type);
    }
    
    public Sandstone(final int type, final byte data) {
        super(type, data);
    }
    
    public Sandstone(final Material type, final byte data) {
        super(type, data);
    }
    
    public SandstoneType getType() {
        return SandstoneType.getByData(this.getData());
    }
    
    public void setType(final SandstoneType type) {
        this.setData(type.getData());
    }
    
    public String toString() {
        return this.getType() + " " + super.toString();
    }
    
    public Sandstone clone() {
        return (Sandstone)super.clone();
    }
}
