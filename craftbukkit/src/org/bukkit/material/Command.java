// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.Material;

public class Command extends MaterialData implements Redstone
{
    public Command() {
        super(Material.COMMAND);
    }
    
    public Command(final int type) {
        super(type);
    }
    
    public Command(final Material type) {
        super(type);
    }
    
    public Command(final int type, final byte data) {
        super(type, data);
    }
    
    public Command(final Material type, final byte data) {
        super(type, data);
    }
    
    public boolean isPowered() {
        return (this.getData() & 0x1) != 0x0;
    }
    
    public void setPowered(final boolean bool) {
        this.setData((byte)(bool ? (this.getData() | 0x1) : (this.getData() & 0xFFFFFFFE)));
    }
    
    public String toString() {
        return super.toString() + " " + (this.isPowered() ? "" : "NOT ") + "POWERED";
    }
    
    public Command clone() {
        return (Command)super.clone();
    }
}
