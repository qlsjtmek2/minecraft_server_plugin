// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.util;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.World;
import java.util.Random;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

@SerializableAs("Vector")
public class Vector implements Cloneable, ConfigurationSerializable
{
    private static final long serialVersionUID = -2657651106777219169L;
    private static Random random;
    private static final double epsilon = 1.0E-6;
    protected double x;
    protected double y;
    protected double z;
    
    public Vector() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }
    
    public Vector(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector add(final Vector vec) {
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
        return this;
    }
    
    public Vector subtract(final Vector vec) {
        this.x -= vec.x;
        this.y -= vec.y;
        this.z -= vec.z;
        return this;
    }
    
    public Vector multiply(final Vector vec) {
        this.x *= vec.x;
        this.y *= vec.y;
        this.z *= vec.z;
        return this;
    }
    
    public Vector divide(final Vector vec) {
        this.x /= vec.x;
        this.y /= vec.y;
        this.z /= vec.z;
        return this;
    }
    
    public Vector copy(final Vector vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
        return this;
    }
    
    public double length() {
        return Math.sqrt(Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0) + Math.pow(this.z, 2.0));
    }
    
    public double lengthSquared() {
        return Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0) + Math.pow(this.z, 2.0);
    }
    
    public double distance(final Vector o) {
        return Math.sqrt(Math.pow(this.x - o.x, 2.0) + Math.pow(this.y - o.y, 2.0) + Math.pow(this.z - o.z, 2.0));
    }
    
    public double distanceSquared(final Vector o) {
        return Math.pow(this.x - o.x, 2.0) + Math.pow(this.y - o.y, 2.0) + Math.pow(this.z - o.z, 2.0);
    }
    
    public float angle(final Vector other) {
        final double dot = this.dot(other) / (this.length() * other.length());
        return (float)Math.acos(dot);
    }
    
    public Vector midpoint(final Vector other) {
        this.x = (this.x + other.x) / 2.0;
        this.y = (this.y + other.y) / 2.0;
        this.z = (this.z + other.z) / 2.0;
        return this;
    }
    
    public Vector getMidpoint(final Vector other) {
        final double x = (this.x + other.x) / 2.0;
        final double y = (this.y + other.y) / 2.0;
        final double z = (this.z + other.z) / 2.0;
        return new Vector(x, y, z);
    }
    
    public Vector multiply(final int m) {
        this.x *= m;
        this.y *= m;
        this.z *= m;
        return this;
    }
    
    public Vector multiply(final double m) {
        this.x *= m;
        this.y *= m;
        this.z *= m;
        return this;
    }
    
    public Vector multiply(final float m) {
        this.x *= m;
        this.y *= m;
        this.z *= m;
        return this;
    }
    
    public double dot(final Vector other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }
    
    public Vector crossProduct(final Vector o) {
        final double newX = this.y * o.z - o.y * this.z;
        final double newY = this.z * o.x - o.z * this.x;
        final double newZ = this.x * o.y - o.x * this.y;
        this.x = newX;
        this.y = newY;
        this.z = newZ;
        return this;
    }
    
    public Vector normalize() {
        final double length = this.length();
        this.x /= length;
        this.y /= length;
        this.z /= length;
        return this;
    }
    
    public Vector zero() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        return this;
    }
    
    public boolean isInAABB(final Vector min, final Vector max) {
        return this.x >= min.x && this.x <= max.x && this.y >= min.y && this.y <= max.y && this.z >= min.z && this.z <= max.z;
    }
    
    public boolean isInSphere(final Vector origin, final double radius) {
        return Math.pow(origin.x - this.x, 2.0) + Math.pow(origin.y - this.y, 2.0) + Math.pow(origin.z - this.z, 2.0) <= Math.pow(radius, 2.0);
    }
    
    public double getX() {
        return this.x;
    }
    
    public int getBlockX() {
        return NumberConversions.floor(this.x);
    }
    
    public double getY() {
        return this.y;
    }
    
    public int getBlockY() {
        return NumberConversions.floor(this.y);
    }
    
    public double getZ() {
        return this.z;
    }
    
    public int getBlockZ() {
        return NumberConversions.floor(this.z);
    }
    
    public Vector setX(final int x) {
        this.x = x;
        return this;
    }
    
    public Vector setX(final double x) {
        this.x = x;
        return this;
    }
    
    public Vector setX(final float x) {
        this.x = x;
        return this;
    }
    
    public Vector setY(final int y) {
        this.y = y;
        return this;
    }
    
    public Vector setY(final double y) {
        this.y = y;
        return this;
    }
    
    public Vector setY(final float y) {
        this.y = y;
        return this;
    }
    
    public Vector setZ(final int z) {
        this.z = z;
        return this;
    }
    
    public Vector setZ(final double z) {
        this.z = z;
        return this;
    }
    
    public Vector setZ(final float z) {
        this.z = z;
        return this;
    }
    
    public boolean equals(final Object obj) {
        if (!(obj instanceof Vector)) {
            return false;
        }
        final Vector other = (Vector)obj;
        return Math.abs(this.x - other.x) < 1.0E-6 && Math.abs(this.y - other.y) < 1.0E-6 && Math.abs(this.z - other.z) < 1.0E-6 && this.getClass().equals(obj.getClass());
    }
    
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int)(Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32);
        hash = 79 * hash + (int)(Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32);
        hash = 79 * hash + (int)(Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32);
        return hash;
    }
    
    public Vector clone() {
        try {
            return (Vector)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
    
    public String toString() {
        return this.x + "," + this.y + "," + this.z;
    }
    
    public Location toLocation(final World world) {
        return new Location(world, this.x, this.y, this.z);
    }
    
    public Location toLocation(final World world, final float yaw, final float pitch) {
        return new Location(world, this.x, this.y, this.z, yaw, pitch);
    }
    
    public BlockVector toBlockVector() {
        return new BlockVector(this.x, this.y, this.z);
    }
    
    public static double getEpsilon() {
        return 1.0E-6;
    }
    
    public static Vector getMinimum(final Vector v1, final Vector v2) {
        return new Vector(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y), Math.min(v1.z, v2.z));
    }
    
    public static Vector getMaximum(final Vector v1, final Vector v2) {
        return new Vector(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y), Math.max(v1.z, v2.z));
    }
    
    public static Vector getRandom() {
        return new Vector(Vector.random.nextDouble(), Vector.random.nextDouble(), Vector.random.nextDouble());
    }
    
    public Map<String, Object> serialize() {
        final Map<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("x", this.getX());
        result.put("y", this.getY());
        result.put("z", this.getZ());
        return result;
    }
    
    public static Vector deserialize(final Map<String, Object> args) {
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
        return new Vector(x, y, z);
    }
    
    static {
        Vector.random = new Random();
    }
}
