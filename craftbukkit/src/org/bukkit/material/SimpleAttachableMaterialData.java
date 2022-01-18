// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public abstract class SimpleAttachableMaterialData extends MaterialData implements Attachable
{
    public SimpleAttachableMaterialData(final int type) {
        super(type);
    }
    
    public SimpleAttachableMaterialData(final int type, final BlockFace direction) {
        this(type);
        this.setFacingDirection(direction);
    }
    
    public SimpleAttachableMaterialData(final Material type, final BlockFace direction) {
        this(type);
        this.setFacingDirection(direction);
    }
    
    public SimpleAttachableMaterialData(final Material type) {
        super(type);
    }
    
    public SimpleAttachableMaterialData(final int type, final byte data) {
        super(type, data);
    }
    
    public SimpleAttachableMaterialData(final Material type, final byte data) {
        super(type, data);
    }
    
    public BlockFace getFacing() {
        final BlockFace attachedFace = this.getAttachedFace();
        return (attachedFace == null) ? null : attachedFace.getOppositeFace();
    }
    
    public String toString() {
        return super.toString() + " facing " + this.getFacing();
    }
    
    public SimpleAttachableMaterialData clone() {
        return (SimpleAttachableMaterialData)super.clone();
    }
}
