// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EntityEnderSignal extends Entity
{
    public int a;
    private double b;
    private double c;
    private double d;
    private int e;
    private boolean f;
    
    public EntityEnderSignal(final World world) {
        super(world);
        this.a = 0;
        this.a(0.25f, 0.25f);
    }
    
    protected void a() {
    }
    
    public EntityEnderSignal(final World world, final double d0, final double d2, final double d3) {
        super(world);
        this.a = 0;
        this.e = 0;
        this.a(0.25f, 0.25f);
        this.setPosition(d0, d2, d3);
        this.height = 0.0f;
    }
    
    public void a(final double b, final int n, final double d) {
        final double n2 = b - this.locX;
        final double n3 = d - this.locZ;
        final float sqrt = MathHelper.sqrt(n2 * n2 + n3 * n3);
        if (sqrt > 12.0f) {
            this.b = this.locX + n2 / sqrt * 12.0;
            this.d = this.locZ + n3 / sqrt * 12.0;
            this.c = this.locY + 8.0;
        }
        else {
            this.b = b;
            this.c = n;
            this.d = d;
        }
        this.e = 0;
        this.f = (this.random.nextInt(5) > 0);
    }
    
    public void l_() {
        this.U = this.locX;
        this.V = this.locY;
        this.W = this.locZ;
        super.l_();
        this.locX += this.motX;
        this.locY += this.motY;
        this.locZ += this.motZ;
        final float sqrt = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0 / 3.1415927410125732);
        this.pitch = (float)(Math.atan2(this.motY, sqrt) * 180.0 / 3.1415927410125732);
        while (this.pitch - this.lastPitch < -180.0f) {
            this.lastPitch -= 360.0f;
        }
        while (this.pitch - this.lastPitch >= 180.0f) {
            this.lastPitch += 360.0f;
        }
        while (this.yaw - this.lastYaw < -180.0f) {
            this.lastYaw -= 360.0f;
        }
        while (this.yaw - this.lastYaw >= 180.0f) {
            this.lastYaw += 360.0f;
        }
        this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2f;
        this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2f;
        if (!this.world.isStatic) {
            final double n = this.b - this.locX;
            final double n2 = this.d - this.locZ;
            final float n3 = (float)Math.sqrt(n * n + n2 * n2);
            final float n4 = (float)Math.atan2(n2, n);
            double n5 = sqrt + (n3 - sqrt) * 0.0025;
            if (n3 < 1.0f) {
                n5 *= 0.8;
                this.motY *= 0.8;
            }
            this.motX = Math.cos(n4) * n5;
            this.motZ = Math.sin(n4) * n5;
            if (this.locY < this.c) {
                this.motY += (1.0 - this.motY) * 0.014999999664723873;
            }
            else {
                this.motY += (-1.0 - this.motY) * 0.014999999664723873;
            }
        }
        final float n6 = 0.25f;
        if (this.G()) {
            for (int i = 0; i < 4; ++i) {
                this.world.addParticle("bubble", this.locX - this.motX * n6, this.locY - this.motY * n6, this.locZ - this.motZ * n6, this.motX, this.motY, this.motZ);
            }
        }
        else {
            this.world.addParticle("portal", this.locX - this.motX * n6 + this.random.nextDouble() * 0.6 - 0.3, this.locY - this.motY * n6 - 0.5, this.locZ - this.motZ * n6 + this.random.nextDouble() * 0.6 - 0.3, this.motX, this.motY, this.motZ);
        }
        if (!this.world.isStatic) {
            this.setPosition(this.locX, this.locY, this.locZ);
            ++this.e;
            if (this.e > 80 && !this.world.isStatic) {
                this.die();
                if (this.f) {
                    this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Item.EYE_OF_ENDER)));
                }
                else {
                    this.world.triggerEffect(2003, (int)Math.round(this.locX), (int)Math.round(this.locY), (int)Math.round(this.locZ), 0);
                }
            }
        }
    }
    
    public void b(final NBTTagCompound nbtTagCompound) {
    }
    
    public void a(final NBTTagCompound nbtTagCompound) {
    }
    
    public float c(final float n) {
        return 1.0f;
    }
    
    public boolean ap() {
        return false;
    }
}
