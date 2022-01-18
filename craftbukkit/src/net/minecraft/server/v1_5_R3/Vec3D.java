// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class Vec3D
{
    public static final Vec3DPool a;
    public final Vec3DPool b;
    public double c;
    public double d;
    public double e;
    public Vec3D next;
    
    public static Vec3D a(final double d0, final double d1, final double d2) {
        return new Vec3D(Vec3D.a, d0, d1, d2);
    }
    
    protected Vec3D(final Vec3DPool vec3dpool, double d0, double d1, double d2) {
        if (d0 == -0.0) {
            d0 = 0.0;
        }
        if (d1 == -0.0) {
            d1 = 0.0;
        }
        if (d2 == -0.0) {
            d2 = 0.0;
        }
        this.c = d0;
        this.d = d1;
        this.e = d2;
        this.b = vec3dpool;
    }
    
    protected Vec3D b(final double d0, final double d1, final double d2) {
        this.c = d0;
        this.d = d1;
        this.e = d2;
        return this;
    }
    
    public Vec3D a() {
        final double d0 = MathHelper.sqrt(this.c * this.c + this.d * this.d + this.e * this.e);
        return (d0 < 1.0E-4) ? this.b.create(0.0, 0.0, 0.0) : this.b.create(this.c / d0, this.d / d0, this.e / d0);
    }
    
    public double b(final Vec3D vec3d) {
        return this.c * vec3d.c + this.d * vec3d.d + this.e * vec3d.e;
    }
    
    public Vec3D add(final double d0, final double d1, final double d2) {
        return this.b.create(this.c + d0, this.d + d1, this.e + d2);
    }
    
    public double d(final Vec3D vec3d) {
        final double d0 = vec3d.c - this.c;
        final double d2 = vec3d.d - this.d;
        final double d3 = vec3d.e - this.e;
        return MathHelper.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
    }
    
    public double distanceSquared(final Vec3D vec3d) {
        final double d0 = vec3d.c - this.c;
        final double d2 = vec3d.d - this.d;
        final double d3 = vec3d.e - this.e;
        return d0 * d0 + d2 * d2 + d3 * d3;
    }
    
    public double d(final double d0, final double d1, final double d2) {
        final double d3 = d0 - this.c;
        final double d4 = d1 - this.d;
        final double d5 = d2 - this.e;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }
    
    public double b() {
        return MathHelper.sqrt(this.c * this.c + this.d * this.d + this.e * this.e);
    }
    
    public Vec3D b(final Vec3D vec3d, final double d0) {
        final double d = vec3d.c - this.c;
        final double d2 = vec3d.d - this.d;
        final double d3 = vec3d.e - this.e;
        if (d * d < 1.0000000116860974E-7) {
            return null;
        }
        final double d4 = (d0 - this.c) / d;
        return (d4 >= 0.0 && d4 <= 1.0) ? this.b.create(this.c + d * d4, this.d + d2 * d4, this.e + d3 * d4) : null;
    }
    
    public Vec3D c(final Vec3D vec3d, final double d0) {
        final double d = vec3d.c - this.c;
        final double d2 = vec3d.d - this.d;
        final double d3 = vec3d.e - this.e;
        if (d2 * d2 < 1.0000000116860974E-7) {
            return null;
        }
        final double d4 = (d0 - this.d) / d2;
        return (d4 >= 0.0 && d4 <= 1.0) ? this.b.create(this.c + d * d4, this.d + d2 * d4, this.e + d3 * d4) : null;
    }
    
    public Vec3D d(final Vec3D vec3d, final double d0) {
        final double d = vec3d.c - this.c;
        final double d2 = vec3d.d - this.d;
        final double d3 = vec3d.e - this.e;
        if (d3 * d3 < 1.0000000116860974E-7) {
            return null;
        }
        final double d4 = (d0 - this.e) / d3;
        return (d4 >= 0.0 && d4 <= 1.0) ? this.b.create(this.c + d * d4, this.d + d2 * d4, this.e + d3 * d4) : null;
    }
    
    public String toString() {
        return "(" + this.c + ", " + this.d + ", " + this.e + ")";
    }
    
    public void a(final float f) {
        final float f2 = MathHelper.cos(f);
        final float f3 = MathHelper.sin(f);
        final double d0 = this.c;
        final double d2 = this.d * f2 + this.e * f3;
        final double d3 = this.e * f2 - this.d * f3;
        this.c = d0;
        this.d = d2;
        this.e = d3;
    }
    
    public void b(final float f) {
        final float f2 = MathHelper.cos(f);
        final float f3 = MathHelper.sin(f);
        final double d0 = this.c * f2 + this.e * f3;
        final double d2 = this.d;
        final double d3 = this.e * f2 - this.c * f3;
        this.c = d0;
        this.d = d2;
        this.e = d3;
    }
    
    static {
        a = new Vec3DPool(-1, -1);
    }
}
