// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EntityAIBodyControl
{
    private EntityLiving entity;
    private int b;
    private float c;
    
    public EntityAIBodyControl(final EntityLiving entity) {
        this.b = 0;
        this.c = 0.0f;
        this.entity = entity;
    }
    
    public void a() {
        final double n = this.entity.locX - this.entity.lastX;
        final double n2 = this.entity.locZ - this.entity.lastZ;
        if (n * n + n2 * n2 > 2.500000277905201E-7) {
            this.entity.ay = this.entity.yaw;
            this.entity.aA = this.a(this.entity.ay, this.entity.aA, 75.0f);
            this.c = this.entity.aA;
            this.b = 0;
            return;
        }
        float n3 = 75.0f;
        if (Math.abs(this.entity.aA - this.c) > 15.0f) {
            this.b = 0;
            this.c = this.entity.aA;
        }
        else {
            ++this.b;
            if (this.b > 10) {
                n3 = Math.max(1.0f - (this.b - 10) / 10.0f, 0.0f) * 75.0f;
            }
        }
        this.entity.ay = this.a(this.entity.aA, this.entity.ay, n3);
    }
    
    private float a(final float n, final float n2, final float n3) {
        float g = MathHelper.g(n - n2);
        if (g < -n3) {
            g = -n3;
        }
        if (g >= n3) {
            g = n3;
        }
        return n - g;
    }
}
