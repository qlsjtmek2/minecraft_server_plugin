// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.TrigMath;

public class ControllerLook
{
    private EntityLiving a;
    private float b;
    private float c;
    private boolean d;
    private double e;
    private double f;
    private double g;
    
    public ControllerLook(final EntityLiving entityliving) {
        this.d = false;
        this.a = entityliving;
    }
    
    public void a(final Entity entity, final float f, final float f1) {
        this.e = entity.locX;
        if (entity instanceof EntityLiving) {
            this.f = entity.locY + entity.getHeadHeight();
        }
        else {
            this.f = (entity.boundingBox.b + entity.boundingBox.e) / 2.0;
        }
        this.g = entity.locZ;
        this.b = f;
        this.c = f1;
        this.d = true;
    }
    
    public void a(final double d0, final double d1, final double d2, final float f, final float f1) {
        this.e = d0;
        this.f = d1;
        this.g = d2;
        this.b = f;
        this.c = f1;
        this.d = true;
    }
    
    public void a() {
        this.a.pitch = 0.0f;
        if (this.d) {
            this.d = false;
            final double d0 = this.e - this.a.locX;
            final double d2 = this.f - (this.a.locY + this.a.getHeadHeight());
            final double d3 = this.g - this.a.locZ;
            final double d4 = MathHelper.sqrt(d0 * d0 + d3 * d3);
            final float f = (float)(TrigMath.atan2(d3, d0) * 180.0 / 3.1415927410125732) - 90.0f;
            final float f2 = (float)(-(TrigMath.atan2(d2, d4) * 180.0 / 3.1415927410125732));
            this.a.pitch = this.a(this.a.pitch, f2, this.c);
            this.a.aA = this.a(this.a.aA, f, this.b);
        }
        else {
            this.a.aA = this.a(this.a.aA, this.a.ax, 10.0f);
        }
        final float f3 = MathHelper.g(this.a.aA - this.a.ay);
        if (!this.a.getNavigation().f()) {
            if (f3 < -75.0f) {
                this.a.aA = this.a.ay - 75.0f;
            }
            if (f3 > 75.0f) {
                this.a.aA = this.a.ay + 75.0f;
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
