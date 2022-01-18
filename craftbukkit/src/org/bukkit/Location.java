// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;
import org.bukkit.block.Block;

public class Location implements Cloneable
{
    private World world;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;
    
    public Location(final World world, final double x, final double y, final double z) {
        this(world, x, y, z, 0.0f, 0.0f);
    }
    
    public Location(final World world, final double x, final double y, final double z, final float yaw, final float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }
    
    public void setWorld(final World world) {
        this.world = world;
    }
    
    public World getWorld() {
        return this.world;
    }
    
    public Chunk getChunk() {
        return this.world.getChunkAt(this);
    }
    
    public Block getBlock() {
        return this.world.getBlockAt(this);
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getX() {
        return this.x;
    }
    
    public int getBlockX() {
        return locToBlock(this.x);
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getY() {
        return this.y;
    }
    
    public int getBlockY() {
        return locToBlock(this.y);
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public int getBlockZ() {
        return locToBlock(this.z);
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public Vector getDirection() {
        final Vector vector = new Vector();
        final double rotX = this.getYaw();
        final double rotY = this.getPitch();
        vector.setY(-Math.sin(Math.toRadians(rotY)));
        final double h = Math.cos(Math.toRadians(rotY));
        vector.setX(-h * Math.sin(Math.toRadians(rotX)));
        vector.setZ(h * Math.cos(Math.toRadians(rotX)));
        return vector;
    }
    
    public Location add(final Location vec) {
        if (vec == null || vec.getWorld() != this.getWorld()) {
            throw new IllegalArgumentException("Cannot add Locations of differing worlds");
        }
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
        return this;
    }
    
    public Location add(final Vector vec) {
        this.x += vec.getX();
        this.y += vec.getY();
        this.z += vec.getZ();
        return this;
    }
    
    public Location add(final double x, final double y, final double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    
    public Location subtract(final Location vec) {
        if (vec == null || vec.getWorld() != this.getWorld()) {
            throw new IllegalArgumentException("Cannot add Locations of differing worlds");
        }
        this.x -= vec.x;
        this.y -= vec.y;
        this.z -= vec.z;
        return this;
    }
    
    public Location subtract(final Vector vec) {
        this.x -= vec.getX();
        this.y -= vec.getY();
        this.z -= vec.getZ();
        return this;
    }
    
    public Location subtract(final double x, final double y, final double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }
    
    public double length() {
        return Math.sqrt(Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0) + Math.pow(this.z, 2.0));
    }
    
    public double lengthSquared() {
        return Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0) + Math.pow(this.z, 2.0);
    }
    
    public double distance(final Location o) {
        return Math.sqrt(this.distanceSquared(o));
    }
    
    public double distanceSquared(final Location o) {
        if (o == null) {
            throw new IllegalArgumentException("Cannot measure distance to a null location");
        }
        if (o.getWorld() == null || this.getWorld() == null) {
            throw new IllegalArgumentException("Cannot measure distance to a null world");
        }
        if (o.getWorld() != this.getWorld()) {
            throw new IllegalArgumentException("Cannot measure distance between " + this.getWorld().getName() + " and " + o.getWorld().getName());
        }
        return Math.pow(this.x - o.x, 2.0) + Math.pow(this.y - o.y, 2.0) + Math.pow(this.z - o.z, 2.0);
    }
    
    public Location multiply(final double m) {
        this.x *= m;
        this.y *= m;
        this.z *= m;
        return this;
    }
    
    public Location zero() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        return this;
    }
    
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location)obj;
        return (this.world == other.world || (this.world != null && this.world.equals(other.world))) && Double.doubleToLongBits(this.x) == Double.doubleToLongBits(other.x) && Double.doubleToLongBits(this.y) == Double.doubleToLongBits(other.y) && Double.doubleToLongBits(this.z) == Double.doubleToLongBits(other.z) && Float.floatToIntBits(this.pitch) == Float.floatToIntBits(other.pitch) && Float.floatToIntBits(this.yaw) == Float.floatToIntBits(other.yaw);
    }
    
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + ((this.world != null) ? this.world.hashCode() : 0);
        hash = 19 * hash + (int)(Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32);
        hash = 19 * hash + (int)(Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32);
        hash = 19 * hash + (int)(Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32);
        hash = 19 * hash + Float.floatToIntBits(this.pitch);
        hash = 19 * hash + Float.floatToIntBits(this.yaw);
        return hash;
    }
    
    public String toString() {
        return "Location{world=" + this.world + ",x=" + this.x + ",y=" + this.y + ",z=" + this.z + ",pitch=" + this.pitch + ",yaw=" + this.yaw + '}';
    }
    
    public Vector toVector() {
        return new Vector(this.x, this.y, this.z);
    }
    
    public Location clone() {
        try {
            return (Location)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
    
    public static int locToBlock(final double loc) {
        return NumberConversions.floor(loc);
    }
}
