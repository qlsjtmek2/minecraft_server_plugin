// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.util;

import java.util.Map;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("BlockVector")
public class BlockVector extends Vector
{
    public BlockVector() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }
    
    public BlockVector(final Vector vec) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
    }
    
    public BlockVector(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public BlockVector(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public BlockVector(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public boolean equals(final Object obj) {
        if (!(obj instanceof BlockVector)) {
            return false;
        }
        final BlockVector other = (BlockVector)obj;
        return (int)other.getX() == (int)this.x && (int)other.getY() == (int)this.y && (int)other.getZ() == (int)this.z;
    }
    
    public int hashCode() {
        return Integer.valueOf((int)this.x).hashCode() >> 13 ^ Integer.valueOf((int)this.y).hashCode() >> 7 ^ Integer.valueOf((int)this.z).hashCode();
    }
    
    public BlockVector clone() {
        return (BlockVector)super.clone();
    }
    
    public static BlockVector deserialize(final Map<String, Object> args) {
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        if (args.containsKey("x")) {
            x = args.get("x");
        }
        if (args.containsKey("y")) {
            y = args.get("y");
        }
        if (args.containsKey("z")) {
            z = args.get("z");
        }
        return new BlockVector(x, y, z);
    }
}
