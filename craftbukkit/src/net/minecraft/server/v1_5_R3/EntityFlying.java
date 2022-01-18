// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public abstract class EntityFlying extends EntityLiving
{
    public EntityFlying(final World world) {
        super(world);
    }
    
    protected void a(final float n) {
    }
    
    protected void a(final double n, final boolean b) {
    }
    
    public void e(final float f, final float f2) {
        if (this.G()) {
            this.a(f, f2, 0.02f);
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.800000011920929;
            this.motY *= 0.800000011920929;
            this.motZ *= 0.800000011920929;
        }
        else if (this.I()) {
            this.a(f, f2, 0.02f);
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.5;
            this.motY *= 0.5;
            this.motZ *= 0.5;
        }
        else {
            float n = 0.91f;
            if (this.onGround) {
                n = 0.54600006f;
                final int typeId = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));
                if (typeId > 0) {
                    n = Block.byId[typeId].frictionFactor * 0.91f;
                }
            }
            final float n2 = 0.16277136f / (n * n * n);
            this.a(f, f2, this.onGround ? (0.1f * n2) : 0.02f);
            float n3 = 0.91f;
            if (this.onGround) {
                n3 = 0.54600006f;
                final int typeId2 = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));
                if (typeId2 > 0) {
                    n3 = Block.byId[typeId2].frictionFactor * 0.91f;
                }
            }
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= n3;
            this.motY *= n3;
            this.motZ *= n3;
        }
        this.bh = this.bi;
        final double n4 = this.locX - this.lastX;
        final double n5 = this.locZ - this.lastZ;
        float n6 = MathHelper.sqrt(n4 * n4 + n5 * n5) * 4.0f;
        if (n6 > 1.0f) {
            n6 = 1.0f;
        }
        this.bi += (n6 - this.bi) * 0.4f;
        this.bj += this.bi;
    }
    
    public boolean g_() {
        return false;
    }
}
