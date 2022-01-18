// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.Material;

public class PressurePlate extends MaterialData implements PressureSensor
{
    public PressurePlate() {
        super(Material.WOOD_PLATE);
    }
    
    public PressurePlate(final int type) {
        super(type);
    }
    
    public PressurePlate(final Material type) {
        super(type);
    }
    
    public PressurePlate(final int type, final byte data) {
        super(type, data);
    }
    
    public PressurePlate(final Material type, final byte data) {
        super(type, data);
    }
    
    public boolean isPressed() {
        return this.getData() == 1;
    }
    
    public String toString() {
        return super.toString() + (this.isPressed() ? " PRESSED" : "");
    }
    
    public PressurePlate clone() {
        return (PressurePlate)super.clone();
    }
}
