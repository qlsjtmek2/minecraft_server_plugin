// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class ExtendedRails extends Rails
{
    public ExtendedRails(final int type) {
        super(type);
    }
    
    public ExtendedRails(final Material type) {
        super(type);
    }
    
    public ExtendedRails(final int type, final byte data) {
        super(type, data);
    }
    
    public ExtendedRails(final Material type, final byte data) {
        super(type, data);
    }
    
    public boolean isCurve() {
        return false;
    }
    
    protected byte getConvertedData() {
        return (byte)(this.getData() & 0x7);
    }
    
    public void setDirection(final BlockFace face, final boolean isOnSlope) {
        final boolean extraBitSet = (this.getData() & 0x8) == 0x8;
        if (face != BlockFace.WEST && face != BlockFace.EAST && face != BlockFace.NORTH && face != BlockFace.SOUTH) {
            throw new IllegalArgumentException("Detector rails and powered rails cannot be set on a curve!");
        }
        super.setDirection(face, isOnSlope);
        this.setData((byte)(extraBitSet ? (this.getData() | 0x8) : (this.getData() & 0xFFFFFFF7)));
    }
    
    public ExtendedRails clone() {
        return (ExtendedRails)super.clone();
    }
}
