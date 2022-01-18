// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.CoalType;
import org.bukkit.Material;

public class Coal extends MaterialData
{
    public Coal() {
        super(Material.COAL);
    }
    
    public Coal(final CoalType type) {
        this();
        this.setType(type);
    }
    
    public Coal(final int type) {
        super(type);
    }
    
    public Coal(final Material type) {
        super(type);
    }
    
    public Coal(final int type, final byte data) {
        super(type, data);
    }
    
    public Coal(final Material type, final byte data) {
        super(type, data);
    }
    
    public CoalType getType() {
        return CoalType.getByData(this.getData());
    }
    
    public void setType(final CoalType type) {
        this.setData(type.getData());
    }
    
    public String toString() {
        return this.getType() + " " + super.toString();
    }
    
    public Coal clone() {
        return (Coal)super.clone();
    }
}
