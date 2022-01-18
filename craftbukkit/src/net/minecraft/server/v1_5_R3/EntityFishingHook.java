// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.entity.Player;
import java.util.List;
import org.bukkit.entity.Fish;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class EntityFishingHook extends Entity
{
    private int d;
    private int e;
    private int f;
    private int g;
    private boolean h;
    public int a;
    public EntityHuman owner;
    private int i;
    private int j;
    private int au;
    public Entity hooked;
    private int av;
    private double aw;
    private double ax;
    private double ay;
    private double az;
    private double aA;
    
    public EntityFishingHook(final World world) {
        super(world);
        this.d = -1;
        this.e = -1;
        this.f = -1;
        this.g = 0;
        this.h = false;
        this.a = 0;
        this.j = 0;
        this.au = 0;
        this.hooked = null;
        this.a(0.25f, 0.25f);
        this.am = true;
    }
    
    public EntityFishingHook(final World world, final EntityHuman entityhuman) {
        super(world);
        this.d = -1;
        this.e = -1;
        this.f = -1;
        this.g = 0;
        this.h = false;
        this.a = 0;
        this.j = 0;
        this.au = 0;
        this.hooked = null;
        this.am = true;
        this.owner = entityhuman;
        (this.owner.hookedFish = this).a(0.25f, 0.25f);
        this.setPositionRotation(entityhuman.locX, entityhuman.locY + 1.62 - entityhuman.height, entityhuman.locZ, entityhuman.yaw, entityhuman.pitch);
        this.locX -= MathHelper.cos(this.yaw / 180.0f * 3.1415927f) * 0.16f;
        this.locY -= 0.10000000149011612;
        this.locZ -= MathHelper.sin(this.yaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.locX, this.locY, this.locZ);
        this.height = 0.0f;
        final float f = 0.4f;
        this.motX = -MathHelper.sin(this.yaw / 180.0f * 3.1415927f) * MathHelper.cos(this.pitch / 180.0f * 3.1415927f) * f;
        this.motZ = MathHelper.cos(this.yaw / 180.0f * 3.1415927f) * MathHelper.cos(this.pitch / 180.0f * 3.1415927f) * f;
        this.motY = -MathHelper.sin(this.pitch / 180.0f * 3.1415927f) * f;
        this.c(this.motX, this.motY, this.motZ, 1.5f, 1.0f);
    }
    
    protected void a() {
    }
    
    public void c(double d0, double d1, double d2, final float f, final float f1) {
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
        super.l_();
        if (this.av > 0) {
            final double d0 = this.locX + (this.aw - this.locX) / this.av;
            final double d2 = this.locY + (this.ax - this.locY) / this.av;
            final double d3 = this.locZ + (this.ay - this.locZ) / this.av;
            final double d4 = MathHelper.g(this.az - this.yaw);
            this.yaw += (float)(d4 / this.av);
            this.pitch += (float)((this.aA - this.pitch) / this.av);
            --this.av;
            this.setPosition(d0, d2, d3);
            this.b(this.yaw, this.pitch);
        }
        else {
            if (!this.world.isStatic) {
                final ItemStack itemstack = this.owner.cd();
                if (this.owner.dead || !this.owner.isAlive() || itemstack == null || itemstack.getItem() != Item.FISHING_ROD || this.e(this.owner) > 1024.0) {
                    this.die();
                    this.owner.hookedFish = null;
                    return;
                }
                if (this.hooked != null) {
                    if (!this.hooked.dead) {
                        this.locX = this.hooked.locX;
                        this.locY = this.hooked.boundingBox.b + this.hooked.length * 0.8;
                        this.locZ = this.hooked.locZ;
                        return;
                    }
                    this.hooked = null;
                }
            }
            if (this.a > 0) {
                --this.a;
            }
            if (this.h) {
                final int i = this.world.getTypeId(this.d, this.e, this.f);
                if (i == this.g) {
                    ++this.i;
                    if (this.i == 1200) {
                        this.die();
                    }
                    return;
                }
                this.h = false;
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
            Entity entity = null;
            final List list = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0, 1.0, 1.0));
            double d5 = 0.0;
            for (int j = 0; j < list.size(); ++j) {
                final Entity entity2 = list.get(j);
                if (entity2.K() && (entity2 != this.owner || this.j >= 5)) {
                    final float f = 0.3f;
                    final AxisAlignedBB axisalignedbb = entity2.boundingBox.grow(f, f, f);
                    final MovingObjectPosition movingobjectposition2 = axisalignedbb.a(vec3d, vec3d2);
                    if (movingobjectposition2 != null) {
                        final double d6 = vec3d.distanceSquared(movingobjectposition2.pos);
                        if (d6 < d5 || d5 == 0.0) {
                            entity = entity2;
                            d5 = d6;
                        }
                    }
                }
            }
            if (entity != null) {
                movingobjectposition = new MovingObjectPosition(entity);
            }
            if (movingobjectposition != null) {
                CraftEventFactory.callProjectileHitEvent(this);
                if (movingobjectposition.entity != null) {
                    if (movingobjectposition.entity.damageEntity(DamageSource.projectile(this, this.owner), 0)) {
                        this.hooked = movingobjectposition.entity;
                    }
                }
                else {
                    this.h = true;
                }
            }
            if (!this.h) {
                this.move(this.motX, this.motY, this.motZ);
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
                float f3 = 0.92f;
                if (this.onGround || this.positionChanged) {
                    f3 = 0.5f;
                }
                final byte b0 = 5;
                double d7 = 0.0;
                for (int k = 0; k < b0; ++k) {
                    final double d8 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (k + 0) / b0 - 0.125 + 0.125;
                    final double d9 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (k + 1) / b0 - 0.125 + 0.125;
                    final AxisAlignedBB axisalignedbb2 = AxisAlignedBB.a().a(this.boundingBox.a, d8, this.boundingBox.c, this.boundingBox.d, d9, this.boundingBox.f);
                    if (this.world.b(axisalignedbb2, Material.WATER)) {
                        d7 += 1.0 / b0;
                    }
                }
                if (d7 > 0.0) {
                    if (this.au > 0) {
                        --this.au;
                    }
                    else if (this.random.nextDouble() < ((Fish)this.getBukkitEntity()).getBiteChance()) {
                        this.au = this.random.nextInt(30) + 10;
                        this.motY -= 0.20000000298023224;
                        this.makeSound("random.splash", 0.25f, 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.4f);
                        final float f4 = MathHelper.floor(this.boundingBox.b);
                        for (int l = 0; l < 1.0f + this.width * 20.0f; ++l) {
                            final float f5 = (this.random.nextFloat() * 2.0f - 1.0f) * this.width;
                            final float f6 = (this.random.nextFloat() * 2.0f - 1.0f) * this.width;
                            this.world.addParticle("bubble", this.locX + f5, f4 + 1.0f, this.locZ + f6, this.motX, this.motY - this.random.nextFloat() * 0.2f, this.motZ);
                        }
                        for (int l = 0; l < 1.0f + this.width * 20.0f; ++l) {
                            final float f5 = (this.random.nextFloat() * 2.0f - 1.0f) * this.width;
                            final float f6 = (this.random.nextFloat() * 2.0f - 1.0f) * this.width;
                            this.world.addParticle("splash", this.locX + f5, f4 + 1.0f, this.locZ + f6, this.motX, this.motY, this.motZ);
                        }
                    }
                }
                if (this.au > 0) {
                    this.motY -= this.random.nextFloat() * this.random.nextFloat() * this.random.nextFloat() * 0.2;
                }
                final double d6 = d7 * 2.0 - 1.0;
                this.motY += 0.03999999910593033 * d6;
                if (d7 > 0.0) {
                    f3 *= 0.9;
                    this.motY *= 0.8;
                }
                this.motX *= f3;
                this.motY *= f3;
                this.motZ *= f3;
                this.setPosition(this.locX, this.locY, this.locZ);
            }
        }
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setShort("xTile", (short)this.d);
        nbttagcompound.setShort("yTile", (short)this.e);
        nbttagcompound.setShort("zTile", (short)this.f);
        nbttagcompound.setByte("inTile", (byte)this.g);
        nbttagcompound.setByte("shake", (byte)this.a);
        nbttagcompound.setByte("inGround", (byte)(this.h ? 1 : 0));
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        this.d = nbttagcompound.getShort("xTile");
        this.e = nbttagcompound.getShort("yTile");
        this.f = nbttagcompound.getShort("zTile");
        this.g = (nbttagcompound.getByte("inTile") & 0xFF);
        this.a = (nbttagcompound.getByte("shake") & 0xFF);
        this.h = (nbttagcompound.getByte("inGround") == 1);
    }
    
    public int c() {
        if (this.world.isStatic) {
            return 0;
        }
        byte b0 = 0;
        if (this.hooked != null) {
            final PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), this.hooked.getBukkitEntity(), (Fish)this.getBukkitEntity(), PlayerFishEvent.State.CAUGHT_ENTITY);
            this.world.getServer().getPluginManager().callEvent(playerFishEvent);
            if (playerFishEvent.isCancelled()) {
                this.die();
                this.owner.hookedFish = null;
                return 0;
            }
            final double d0 = this.owner.locX - this.locX;
            final double d2 = this.owner.locY - this.locY;
            final double d3 = this.owner.locZ - this.locZ;
            final double d4 = MathHelper.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
            final double d5 = 0.1;
            final Entity hooked = this.hooked;
            hooked.motX += d0 * d5;
            final Entity hooked2 = this.hooked;
            hooked2.motY += d2 * d5 + MathHelper.sqrt(d4) * 0.08;
            final Entity hooked3 = this.hooked;
            hooked3.motZ += d3 * d5;
            b0 = 3;
        }
        else if (this.au > 0) {
            final EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Item.RAW_FISH));
            final PlayerFishEvent playerFishEvent2 = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), entityitem.getBukkitEntity(), (Fish)this.getBukkitEntity(), PlayerFishEvent.State.CAUGHT_FISH);
            playerFishEvent2.setExpToDrop(this.random.nextInt(6) + 1);
            this.world.getServer().getPluginManager().callEvent(playerFishEvent2);
            if (playerFishEvent2.isCancelled()) {
                this.die();
                this.owner.hookedFish = null;
                return 0;
            }
            final double d6 = this.owner.locX - this.locX;
            final double d7 = this.owner.locY - this.locY;
            final double d8 = this.owner.locZ - this.locZ;
            final double d9 = MathHelper.sqrt(d6 * d6 + d7 * d7 + d8 * d8);
            final double d10 = 0.1;
            entityitem.motX = d6 * d10;
            entityitem.motY = d7 * d10 + MathHelper.sqrt(d9) * 0.08;
            entityitem.motZ = d8 * d10;
            this.world.addEntity(entityitem);
            this.owner.a(StatisticList.B, 1);
            this.owner.world.addEntity(new EntityExperienceOrb(this.owner.world, this.owner.locX, this.owner.locY + 0.5, this.owner.locZ + 0.5, playerFishEvent2.getExpToDrop()));
            b0 = 1;
        }
        if (this.h) {
            final PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), null, (Fish)this.getBukkitEntity(), PlayerFishEvent.State.IN_GROUND);
            this.world.getServer().getPluginManager().callEvent(playerFishEvent);
            if (playerFishEvent.isCancelled()) {
                this.die();
                this.owner.hookedFish = null;
                return 0;
            }
            b0 = 2;
        }
        if (b0 == 0) {
            final PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), null, (Fish)this.getBukkitEntity(), PlayerFishEvent.State.FAILED_ATTEMPT);
            this.world.getServer().getPluginManager().callEvent(playerFishEvent);
        }
        this.die();
        this.owner.hookedFish = null;
        return b0;
    }
    
    public void die() {
        super.die();
        if (this.owner != null) {
            this.owner.hookedFish = null;
        }
    }
}
