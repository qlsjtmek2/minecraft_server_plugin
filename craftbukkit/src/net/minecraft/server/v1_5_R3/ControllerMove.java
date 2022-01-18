// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.TrigMath;

public class ControllerMove
{
    private EntityLiving a;
    private double b;
    private double c;
    private double d;
    private float e;
    private boolean f;
    
    public ControllerMove(final EntityLiving entityliving) {
        this.f = false;
        this.a = entityliving;
        this.b = entityliving.locX;
        this.c = entityliving.locY;
        this.d = entityliving.locZ;
    }
    
    public boolean a() {
        return this.f;
    }
    
    public float b() {
        return this.e;
    }
    
    public void a(final double d0, final double d1, final double d2, final float f) {
        this.b = d0;
        this.c = d1;
        this.d = d2;
        this.e = f;
        this.f = true;
    }
    
    public void c() {
        this.a.f(0.0f);
        if (this.f) {
            this.f = false;
            final int i = MathHelper.floor(this.a.boundingBox.b + 0.5);
            final double d0 = this.b - this.a.locX;
            final double d2 = this.d - this.a.locZ;
            final double d3 = this.c - i;
            final double d4 = d0 * d0 + d3 * d3 + d2 * d2;
            if (d4 >= 2.500000277905201E-7) {
                final float f = (float)(TrigMath.atan2(d2, d0) * 180.0 / 3.1415927410125732) - 90.0f;
                this.a.yaw = this.a(this.a.yaw, f, 30.0f);
                this.a.e(this.e * this.a.bE());
                if (d3 > 0.0 && d0 * d0 + d2 * d2 < 1.0) {
                    this.a.getControllerJump().a();
                }
            }
        }
    }
    
    private float a(final float f, final float f1, final float f2) {
        float f3 = MathHelper.g(f1 - f);
        if (f3 > f2) {
            f3 = f2;
        }
        if (f3 < -f2) {
            f3 = -f2;
        }
        return f + f3;
    }
}
