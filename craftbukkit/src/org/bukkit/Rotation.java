// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

public enum Rotation
{
    NONE, 
    CLOCKWISE, 
    FLIPPED, 
    COUNTER_CLOCKWISE;
    
    private static final Rotation[] rotations;
    
    public Rotation rotateClockwise() {
        return Rotation.rotations[this.ordinal() + 1 & 0x3];
    }
    
    public Rotation rotateCounterClockwise() {
        return Rotation.rotations[this.ordinal() - 1 & 0x3];
    }
    
    static {
        rotations = values();
    }
}
