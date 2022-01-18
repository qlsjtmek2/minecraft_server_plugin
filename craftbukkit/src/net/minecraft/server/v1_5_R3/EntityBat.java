// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Calendar;

public class EntityBat extends EntityAmbient
{
    private ChunkCoordinates a;
    
    public EntityBat(final World world) {
        super(world);
        this.texture = "/mob/bat.png";
        this.a(0.5f, 0.9f);
        this.a(true);
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(16, new Byte((byte)0));
    }
    
    protected float ba() {
        return 0.1f;
    }
    
    protected float aY() {
        return super.aY() * 0.95f;
    }
    
    protected String bb() {
        if (this.h() && this.random.nextInt(4) != 0) {
            return null;
        }
        return "mob.bat.idle";
    }
    
    protected String bc() {
        return "mob.bat.hurt";
    }
    
    protected String bd() {
        return "mob.bat.death";
    }
    
    public boolean L() {
        return false;
    }
    
    protected void o(final Entity entity) {
    }
    
    protected void bg() {
    }
    
    public int getMaxHealth() {
        return 6;
    }
    
    public boolean h() {
        return (this.datawatcher.getByte(16) & 0x1) != 0x0;
    }
    
    public void a(final boolean b) {
        final byte byte1 = this.datawatcher.getByte(16);
        if (b) {
            this.datawatcher.watch(16, (byte)(byte1 | 0x1));
        }
        else {
            this.datawatcher.watch(16, (byte)(byte1 & 0xFFFFFFFE));
        }
    }
    
    protected boolean bh() {
        return true;
    }
    
    public void l_() {
        super.l_();
        if (this.h()) {
            final double motX = 0.0;
            this.motZ = motX;
            this.motY = motX;
            this.motX = motX;
            this.locY = MathHelper.floor(this.locY) + 1.0 - this.length;
        }
        else {
            this.motY *= 0.6000000238418579;
        }
    }
    
    protected void bo() {
        super.bo();
        if (this.h()) {
            if (!this.world.u(MathHelper.floor(this.locX), (int)this.locY + 1, MathHelper.floor(this.locZ))) {
                this.a(false);
                this.world.a(null, 1015, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
            }
            else {
                if (this.random.nextInt(200) == 0) {
                    this.aA = this.random.nextInt(360);
                }
                if (this.world.findNearbyPlayer(this, 4.0) != null) {
                    this.a(false);
                    this.world.a(null, 1015, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
                }
            }
        }
        else {
            if (this.a != null && (!this.world.isEmpty(this.a.x, this.a.y, this.a.z) || this.a.y < 1)) {
                this.a = null;
            }
            if (this.a == null || this.random.nextInt(30) == 0 || this.a.e((int)this.locX, (int)this.locY, (int)this.locZ) < 4.0f) {
                this.a = new ChunkCoordinates((int)this.locX + this.random.nextInt(7) - this.random.nextInt(7), (int)this.locY + this.random.nextInt(6) - 2, (int)this.locZ + this.random.nextInt(7) - this.random.nextInt(7));
            }
            final double n = this.a.x + 0.5 - this.locX;
            final double n2 = this.a.y + 0.1 - this.locY;
            final double n3 = this.a.z + 0.5 - this.locZ;
            this.motX += (Math.signum(n) * 0.5 - this.motX) * 0.10000000149011612;
            this.motY += (Math.signum(n2) * 0.699999988079071 - this.motY) * 0.10000000149011612;
            this.motZ += (Math.signum(n3) * 0.5 - this.motZ) * 0.10000000149011612;
            final float g = MathHelper.g((float)(Math.atan2(this.motZ, this.motX) * 180.0 / 3.1415927410125732) - 90.0f - this.yaw);
            this.bE = 0.5f;
            this.yaw += g;
            if (this.random.nextInt(100) == 0 && this.world.u(MathHelper.floor(this.locX), (int)this.locY + 1, MathHelper.floor(this.locZ))) {
                this.a(true);
            }
        }
    }
    
    protected boolean f_() {
        return false;
    }
    
    protected void a(final float n) {
    }
    
    protected void a(final double n, final boolean b) {
    }
    
    public boolean at() {
        return true;
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        if (!this.world.isStatic && this.h()) {
            this.a(false);
        }
        return super.damageEntity(damagesource, i);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.datawatcher.watch(16, nbttagcompound.getByte("BatFlags"));
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setByte("BatFlags", this.datawatcher.getByte(16));
    }
    
    public boolean canSpawn() {
        final int floor = MathHelper.floor(this.boundingBox.b);
        if (floor >= 63) {
            return false;
        }
        final int lightLevel = this.world.getLightLevel(MathHelper.floor(this.locX), floor, MathHelper.floor(this.locZ));
        int n = 4;
        final Calendar v = this.world.V();
        if ((v.get(2) + 1 == 10 && v.get(5) >= 20) || (v.get(2) + 1 == 11 && v.get(5) <= 3)) {
            n = 7;
        }
        else if (this.random.nextBoolean()) {
            return false;
        }
        return lightLevel <= this.random.nextInt(n) && super.canSpawn();
    }
    
    public void bJ() {
    }
}
