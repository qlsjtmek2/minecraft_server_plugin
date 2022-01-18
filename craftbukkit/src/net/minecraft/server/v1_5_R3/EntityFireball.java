// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public abstract class EntityFireball extends Entity
{
    private int e;
    private int f;
    private int g;
    private int h;
    private boolean i;
    public EntityLiving shooter;
    private int j;
    private int au;
    public double dirX;
    public double dirY;
    public double dirZ;
    public float yield;
    public boolean isIncendiary;
    
    public EntityFireball(final World world) {
        super(world);
        this.e = -1;
        this.f = -1;
        this.g = -1;
        this.h = 0;
        this.i = false;
        this.au = 0;
        this.yield = 1.0f;
        this.isIncendiary = true;
        this.a(1.0f, 1.0f);
    }
    
    protected void a() {
    }
    
    public EntityFireball(final World world, final double d0, final double d1, final double d2, final double d3, final double d4, final double d5) {
        super(world);
        this.e = -1;
        this.f = -1;
        this.g = -1;
        this.h = 0;
        this.i = false;
        this.au = 0;
        this.yield = 1.0f;
        this.isIncendiary = true;
        this.a(1.0f, 1.0f);
        this.setPositionRotation(d0, d1, d2, this.yaw, this.pitch);
        this.setPosition(d0, d1, d2);
        final double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
        this.dirX = d3 / d6 * 0.1;
        this.dirY = d4 / d6 * 0.1;
        this.dirZ = d5 / d6 * 0.1;
    }
    
    public EntityFireball(final World world, final EntityLiving entityliving, final double d0, final double d1, final double d2) {
        super(world);
        this.e = -1;
        this.f = -1;
        this.g = -1;
        this.h = 0;
        this.i = false;
        this.au = 0;
        this.yield = 1.0f;
        this.isIncendiary = true;
        this.shooter = entityliving;
        this.a(1.0f, 1.0f);
        this.setPositionRotation(entityliving.locX, entityliving.locY, entityliving.locZ, entityliving.yaw, entityliving.pitch);
        this.setPosition(this.locX, this.locY, this.locZ);
        this.height = 0.0f;
        final double motX = 0.0;
        this.motZ = motX;
        this.motY = motX;
        this.motX = motX;
        this.setDirection(d0, d1, d2);
    }
    
    public void setDirection(double d0, double d1, double d2) {
        d0 += this.random.nextGaussian() * 0.4;
        d1 += this.random.nextGaussian() * 0.4;
        d2 += this.random.nextGaussian() * 0.4;
        final double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
        this.dirX = d0 / d3 * 0.1;
        this.dirY = d1 / d3 * 0.1;
        this.dirZ = d2 / d3 * 0.1;
    }
    
    public void l_() {
        if (!this.world.isStatic && ((this.shooter != null && this.shooter.dead) || !this.world.isLoaded((int)this.locX, (int)this.locY, (int)this.locZ))) {
            this.die();
        }
        else {
            super.l_();
            this.setOnFire(1);
            if (this.i) {
                final int i = this.world.getTypeId(this.e, this.f, this.g);
                if (i == this.h) {
                    ++this.j;
                    if (this.j == 600) {
                        this.die();
                    }
                    return;
                }
                this.i = false;
                this.motX *= this.random.nextFloat() * 0.2f;
                this.motY *= this.random.nextFloat() * 0.2f;
                this.motZ *= this.random.nextFloat() * 0.2f;
                this.j = 0;
                this.au = 0;
            }
            else {
                ++this.au;
            }
            Vec3D vec3d = this.world.getVec3DPool().create(this.locX, this.locY, this.locZ);
            Vec3D vec3d2 = this.world.getVec3DPool().create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
            MovingObjectPosition movingobjectposition = this.world.a(vec3d, vec3d2);
            vec3d = this.world.getVec3DPool().create(this.locX, this.locY, this.locZ);
            vec3d2 = this.world.getVec3DPool().create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
            if (movingobjectposition != null) {
                vec3d2 = this.world.getVec3DPool().create(movingobjectposition.pos.c, movingobjectposition.pos.d, movingobjectposition.pos.e);
            }
            Entity entity = null;
            final List list = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0, 1.0, 1.0));
            double d0 = 0.0;
            for (int j = 0; j < list.size(); ++j) {
                final Entity entity2 = list.get(j);
                if (entity2.K() && (!entity2.i(this.shooter) || this.au >= 25)) {
                    final float f = 0.3f;
                    final AxisAlignedBB axisalignedbb = entity2.boundingBox.grow(f, f, f);
                    final MovingObjectPosition movingobjectposition2 = axisalignedbb.a(vec3d, vec3d2);
                    if (movingobjectposition2 != null) {
                        final double d2 = vec3d.distanceSquared(movingobjectposition2.pos);
                        if (d2 < d0 || d0 == 0.0) {
                            entity = entity2;
                            d0 = d2;
                        }
                    }
                }
            }
            if (entity != null) {
                movingobjectposition = new MovingObjectPosition(entity);
            }
            if (movingobjectposition != null) {
                this.a(movingobjectposition);
                if (this.dead) {
                    CraftEventFactory.callProjectileHitEvent(this);
                }
            }
            this.locX += this.motX;
            this.locY += this.motY;
            this.locZ += this.motZ;
            final float f2 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            this.yaw = (float)(Math.atan2(this.motZ, this.motX) * 180.0 / 3.1415927410125732) + 90.0f;
            this.pitch = (float)(Math.atan2(f2, this.motY) * 180.0 / 3.1415927410125732) - 90.0f;
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
            float f3 = this.c();
            if (this.G()) {
                for (int k = 0; k < 4; ++k) {
                    final float f4 = 0.25f;
                    this.world.addParticle("bubble", this.locX - this.motX * f4, this.locY - this.motY * f4, this.locZ - this.motZ * f4, this.motX, this.motY, this.motZ);
                }
                f3 = 0.8f;
            }
            this.motX += this.dirX;
            this.motY += this.dirY;
            this.motZ += this.dirZ;
            this.motX *= f3;
            this.motY *= f3;
            this.motZ *= f3;
            this.world.addParticle("smoke", this.locX, this.locY + 0.5, this.locZ, 0.0, 0.0, 0.0);
            this.setPosition(this.locX, this.locY, this.locZ);
        }
    }
    
    protected float c() {
        return 0.95f;
    }
    
    protected abstract void a(final MovingObjectPosition p0);
    
    public void b(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setShort("xTile", (short)this.e);
        nbttagcompound.setShort("yTile", (short)this.f);
        nbttagcompound.setShort("zTile", (short)this.g);
        nbttagcompound.setByte("inTile", (byte)this.h);
        nbttagcompound.setByte("inGround", (byte)(this.i ? 1 : 0));
        nbttagcompound.set("power", this.a(this.dirX, this.dirY, this.dirZ));
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        this.e = nbttagcompound.getShort("xTile");
        this.f = nbttagcompound.getShort("yTile");
        this.g = nbttagcompound.getShort("zTile");
        this.h = (nbttagcompound.getByte("inTile") & 0xFF);
        this.i = (nbttagcompound.getByte("inGround") == 1);
        if (nbttagcompound.hasKey("power")) {
            final NBTTagList nbttaglist = nbttagcompound.getList("power");
            this.dirX = ((NBTTagDouble)nbttaglist.get(0)).data;
            this.dirY = ((NBTTagDouble)nbttaglist.get(1)).data;
            this.dirZ = ((NBTTagDouble)nbttaglist.get(2)).data;
        }
        else {
            this.die();
        }
    }
    
    public boolean K() {
        return true;
    }
    
    public float X() {
        return 1.0f;
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        this.J();
        if (damagesource.getEntity() != null) {
            final Vec3D vec3d = damagesource.getEntity().Y();
            if (vec3d != null) {
                this.motX = vec3d.c;
                this.motY = vec3d.d;
                this.motZ = vec3d.e;
                this.dirX = this.motX * 0.1;
                this.dirY = this.motY * 0.1;
                this.dirZ = this.motZ * 0.1;
            }
            if (damagesource.getEntity() instanceof EntityLiving) {
                this.shooter = (EntityLiving)damagesource.getEntity();
            }
            return true;
        }
        return false;
    }
    
    public float c(final float f) {
        return 1.0f;
    }
}
