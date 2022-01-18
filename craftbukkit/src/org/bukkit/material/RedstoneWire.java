// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.Material;

public class RedstoneWire extends MaterialData implements Redstone
{
    public RedstoneWire() {
        super(Material.REDSTONE_WIRE);
    }
    
    public RedstoneWire(final int type) {
        super(type);
    }
    
    public RedstoneWire(final Material type) {
        super(type);
    }
    
    public RedstoneWire(final int type, final byte data) {
        super(type, data);
    }
    
    public RedstoneWire(final Material type, final byte data) {
        super(type, data);
    }
    
    public boolean isPowered() {
        return this.getData() > 0;
    }
    
    public String toString() {
        return super.toString() + " " + (this.isPowered() ? "" : "NOT ") + "POWERED";
    }
    
    public RedstoneWire clone() {
        return (RedstoneWire)super.clone();
    }
}
