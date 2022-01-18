// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.Material;

public class PoweredRail extends ExtendedRails implements Redstone
{
    public PoweredRail() {
        super(Material.POWERED_RAIL);
    }
    
    public PoweredRail(final int type) {
        super(type);
    }
    
    public PoweredRail(final Material type) {
        super(type);
    }
    
    public PoweredRail(final int type, final byte data) {
        super(type, data);
    }
    
    public PoweredRail(final Material type, final byte data) {
        super(type, data);
    }
    
    public boolean isPowered() {
        return (this.getData() & 0x8) == 0x8;
    }
    
    public void setPowered(final boolean isPowered) {
        this.setData((byte)(isPowered ? (this.getData() | 0x8) : (this.getData() & 0xFFFFFFF7)));
    }
    
    public PoweredRail clone() {
        return (PoweredRail)super.clone();
    }
}
