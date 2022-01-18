// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.OtherModule;

import org.bukkit.Location;

public class Vector
{
    private double x;
    private double y;
    private double z;
    
    public Vector(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector(final Location l) {
        this.x = l.getX();
        this.y = l.getY();
        this.z = l.getZ();
    }
    
    public double getX() {
        return this.x;
    }
    
    public int getBlockX() {
        return (int)Math.round(this.x);
    }
    
    public Vector setX(final double x) {
        this.x = x;
        return new Vector(x, this.y, this.z);
    }
    
    public Vector setX(final int x) {
        this.x = x;
        return new Vector(x, this.y, this.z);
    }
    
    public double getY() {
        return this.y;
    }
    
    public int getBlockY() {
        return (int)Math.round(this.y);
    }
    
    public Vector setY(final double y) {
        this.y = y;
        return new Vector(this.x, y, this.z);
    }
    
    public Vector setY(final int y) {
        this.y = y;
        return new Vector(this.x, y, this.z);
    }
    
    public double getZ() {
        return this.z;
    }
    
    public int getBlockZ() {
        return (int)Math.round(this.z);
    }
    
    public Vector setZ(final double z) {
        this.z = z;
        return new Vector(this.x, this.y, z);
    }
    
    public Vector setZ(final int z) {
        this.z = z;
        return new Vector(this.x, this.y, z);
    }
    
    public double distance(final Vector pt) {
        return Math.sqrt(Math.pow(pt.x - this.x, 2.0) + Math.pow(pt.y - this.y, 2.0) + Math.pow(pt.z - this.z, 2.0));
    }
    
    public double distance(final Location l) {
        return Math.sqrt(Math.pow(l.getX() - this.x, 2.0) + Math.pow(l.getY() - this.y, 2.0) + Math.pow(l.getZ() - this.z, 2.0));
    }
    
    public double distance(final double xx, final double yy, final double zz) {
        return Math.sqrt(Math.pow(xx - this.x, 2.0) + Math.pow(yy - this.y, 2.0) + Math.pow(zz - this.z, 2.0));
    }
}
