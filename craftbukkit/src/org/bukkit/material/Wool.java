// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public class Wool extends MaterialData implements Colorable
{
    public Wool() {
        super(Material.WOOL);
    }
    
    public Wool(final DyeColor color) {
        this();
        this.setColor(color);
    }
    
    public Wool(final int type) {
        super(type);
    }
    
    public Wool(final Material type) {
        super(type);
    }
    
    public Wool(final int type, final byte data) {
        super(type, data);
    }
    
    public Wool(final Material type, final byte data) {
        super(type, data);
    }
    
    public DyeColor getColor() {
        return DyeColor.getByWoolData(this.getData());
    }
    
    public void setColor(final DyeColor color) {
        this.setData(color.getWoolData());
    }
    
    public String toString() {
        return this.getColor() + " " + super.toString();
    }
    
    public Wool clone() {
        return (Wool)super.clone();
    }
}
