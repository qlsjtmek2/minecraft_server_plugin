// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.Material;

public class RedstoneTorch extends Torch implements Redstone
{
    public RedstoneTorch() {
        super(Material.REDSTONE_TORCH_ON);
    }
    
    public RedstoneTorch(final int type) {
        super(type);
    }
    
    public RedstoneTorch(final Material type) {
        super(type);
    }
    
    public RedstoneTorch(final int type, final byte data) {
        super(type, data);
    }
    
    public RedstoneTorch(final Material type, final byte data) {
        super(type, data);
    }
    
    public boolean isPowered() {
        return this.getItemType() == Material.REDSTONE_TORCH_ON;
    }
    
    public String toString() {
        return super.toString() + " " + (this.isPowered() ? "" : "NOT ") + "POWERED";
    }
    
    public RedstoneTorch clone() {
        return (RedstoneTorch)super.clone();
    }
}
