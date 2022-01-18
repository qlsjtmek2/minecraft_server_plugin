// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public abstract class EntityProjectile extends Entity implements IProjectile
{
    private int blockX;
    private int blockY;
    private int blockZ;
    private int inBlockId;
    protected boolean inGround;
    public int shake;
    public EntityLiving shooter;
    public String shooterName;
    private int i;
    private int j;
    
    public EntityProjectile(final World world) {
        super(world);
        this.blockX = -1;
        this.blockY = -1;
        this.blockZ = -1;
        this.inBlockId = 0;
        this.inGround = false;
        this.shake = 0;
        this.shooterName = null;
        this.j = 0;
        this.a(0.25f, 0.25f);
    }
    
    protected void a() {
    }
    
    public EntityProjectile(final World world, final EntityLiving entityliving) {
        super(world);
        this.blockX = -1;
        this.blockY = -1;
        this.blockZ = -1;
        this.inBlockId = 0;
        this.inGround = false;
        this.shake = 0;
        this.shooterName = null;
        this.j = 0;
        this.shooter = entityliving;
        this.a(0.25f, 0.25f);
        this.setPositionRotation(entityliving.locX, entityliving.locY + entityliving.getHeadHeight(), entityliving.locZ, entityliving.yaw, entityliving.pitch);
        this.locX -= MathHelper.cos(this.yaw / 180.0f * 3.1415927f) * 0.16f;
        this.locY -= 0.10000000149011612;
        this.locZ -= MathHelper.sin(this.yaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.locX, this.locY, this.locZ);
        this.height = 0.0f;
        final float f = 0.4f;
        this.motX = -MathHelper.sin(this.yaw / 180.0f * 3.1415927f) * MathHelper.cos(this.pitch / 180.0f * 3.1415927f) * f;
        this.motZ = MathHelper.cos(this.yaw / 180.0f * 3.1415927f) * MathHelper.cos(this.pitch / 180.0f * 3.1415927f) * f;
        this.motY = -MathHelper.sin((this.pitch + this.d()) / 180.0f * 3.1415927f) * f;
        this.shoot(this.motX, this.motY, this.motZ, this.c(), 1.0f);
    }
    
    public EntityProjectile(final World world, final double d0, final double d1, final double d2) {
        super(world);
        this.blockX = -1;
        this.blockY = -1;
        this.blockZ = -1;
        this.inBlockId = 0;
        this.inGround = false;
        this.shake = 0;
        this.shooterName = null;
        this.j = 0;
        this.i = 0;
        this.a(0.25f, 0.25f);
        this.setPosition(d0, d1, d2);
        this.height = 0.0f;
    }
    
    protected float c() {
        return 1.5f;
    }
    
    protected float d() {
        return 0.0f;
    }
    
    public void shoot(double d0, double d1, double d2, final float f, final float f1) {
        final float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
        d0 /= f2;
        d1 /= f2;
        d2 /= f2;
        d0 += this.random.nextGaussian() * 0.007499999832361937 * f1;
        d1 += this.random.nextGaussian() * 0.007499999832361937 * f1;
        d2 += this.random.nextGaussian() * 0.007499999832361937 * f1;
        d0 *= f;
        d1 *= f;
        d2 *= f;
        this.motX = d0;
        this.motY = d1;
        this.motZ = d2;
        final float f3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        final float n = (float)(Math.atan2(d0, d2) * 180.0 / 3.1415927410125732);
        this.yaw = n;
        this.lastYaw = n;
        final float n2 = (float)(Math.atan2(d1, f3) * 180.0 / 3.1415927410125732);
        this.pitch = n2;
        this.lastPitch = n2;
        this.i = 0;
    }
    
    public void l_() {
        this.U = this.locX;
        this.V = this.locY;
        this.W = this.locZ;
        super.l_();
        if (this.shake > 0) {
            --this.shake;
        }
        if (this.inGround) {
            final int i = this.world.getTypeId(this.blockX, this.blockY, this.blockZ);
            if (i == this.inBlockId) {
                ++this.i;
                if (this.i == 1200) {
                    this.die();
                }
                return;
            }
            this.inGround = false;
            this.motX *= this.random.nextFloat() * 0.2f;
            this.motY *= this.random.nextFloat() * 0.2f;
            this.motZ *= this.random.nextFloat() * 0.2f;
            this.i = 0;
            this.j = 0;
        }
        else {
            ++this.j;
        }
        Vec3D vec3d = this.world.getVec3DPool().create(this.locX, this.locY, this.locZ);
        Vec3D vec3d2 = this.world.getVec3DPool().create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
        MovingObjectPosition movingobjectposition = this.world.a(vec3d, vec3d2);
        vec3d = this.world.getVec3DPool().create(this.locX, this.locY, this.locZ);
        vec3d2 = this.world.getVec3DPool().create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
        if (movingobjectposition != null) {
            vec3d2 = this.world.getVec3DPool().create(movingobjectposition.pos.c, movingobjectposition.pos.d, movingobjectposition.pos.e);
        }
        if (!this.world.isStatic) {
            Entity entity = null;
            final List list = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0, 1.0, 1.0));
            double d0 = 0.0;
            final EntityLiving entityliving = this.getShooter();
            for (int j = 0; j < list.size(); ++j) {
                final Entity entity2 = list.get(j);
                if (entity2.K() && (entity2 != entityliving || this.j >= 5)) {
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
        }
        if (movingobjectposition != null) {
            if (movingobjectposition.type == EnumMovingObjectType.TILE && this.world.getTypeId(movingobjectposition.b, movingobjectposition.c, movingobjectposition.d) == Block.PORTAL.id) {
                this.Z();
            }
            else {
                this.a(movingobjectposition);
                if (this.dead) {
                    CraftEventFactory.callProjectileHitEvent(this);
                }
            }
        }
        this.locX += this.motX;
        this.locY += this.motY;
        this.locZ += this.motZ;
        final float f2 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0 / 3.1415927410125732);
        this.pitch = (float)(Math.atan2(this.motY, f2) * 180.0 / 3.1415927410125732);
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
        float f3 = 0.99f;
        final float f4 = this.g();
        if (this.G()) {
            for (int k = 0; k < 4; ++k) {
                final float f5 = 0.25f;
                this.world.addParticle("bubble", this.locX - this.motX * f5, this.locY - this.motY * f5, this.locZ - this.motZ * f5, this.motX, this.motY, this.motZ);
            }
            f3 = 0.8f;
        }
        this.motX *= f3;
        this.motY *= f3;
        this.motZ *= f3;
        this.motY -= f4;
        this.setPosition(this.locX, this.locY, this.locZ);
    }
    
    protected float g() {
        return 0.03f;
    }
    
    protected abstract void a(final MovingObjectPosition p0);
    
    public void b(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setShort("xTile", (short)this.blockX);
        nbttagcompound.setShort("yTile", (short)this.blockY);
        nbttagcompound.setShort("zTile", (short)this.blockZ);
        nbttagcompound.setByte("inTile", (byte)this.inBlockId);
        nbttagcompound.setByte("shake", (byte)this.shake);
        nbttagcompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        if ((this.shooterName == null || this.shooterName.length() == 0) && this.shooter != null && this.shooter instanceof EntityHuman) {
            this.shooterName = this.shooter.getLocalizedName();
        }
        nbttagcompound.setString("ownerName", (this.shooterName == null) ? "" : this.shooterName);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        this.blockX = nbttagcompound.getShort("xTile");
        this.blockY = nbttagcompound.getShort("yTile");
        this.blockZ = nbttagcompound.getShort("zTile");
        this.inBlockId = (nbttagcompound.getByte("inTile") & 0xFF);
        this.shake = (nbttagcompound.getByte("shake") & 0xFF);
        this.inGround = (nbttagcompound.getByte("inGround") == 1);
        this.shooterName = nbttagcompound.getString("ownerName");
        if (this.shooterName != null && this.shooterName.length() == 0) {
            this.shooterName = null;
        }
    }
    
    public EntityLiving getShooter() {
        if (this.shooter == null && this.shooterName != null && this.shooterName.length() > 0) {
            this.shooter = this.world.a(this.shooterName);
        }
        return this.shooter;
    }
}
