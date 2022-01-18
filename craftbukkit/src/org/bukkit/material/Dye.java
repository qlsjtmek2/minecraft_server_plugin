// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public class Dye extends MaterialData implements Colorable
{
    public Dye() {
        super(Material.INK_SACK);
    }
    
    public Dye(final int type) {
        super(type);
    }
    
    public Dye(final Material type) {
        super(type);
    }
    
    public Dye(final int type, final byte data) {
        super(type, data);
    }
    
    public Dye(final Material type, final byte data) {
        super(type, data);
    }
    
    public DyeColor getColor() {
        return DyeColor.getByDyeData(this.getData());
    }
    
    public void setColor(final DyeColor color) {
        this.setData(color.getDyeData());
    }
    
    public String toString() {
        return this.getColor() + " DYE(" + this.getData() + ")";
    }
    
    public Dye clone() {
        return (Dye)super.clone();
    }
}
