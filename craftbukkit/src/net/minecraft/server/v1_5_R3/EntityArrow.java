// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftItem;
import org.bukkit.entity.Player;
import java.util.List;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class EntityArrow extends Entity implements IProjectile
{
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    public boolean inGround;
    public int fromPlayer;
    public int shake;
    public Entity shooter;
    private int j;
    private int au;
    private double damage;
    private int aw;
    
    public EntityArrow(final World world) {
        super(world);
        this.d = -1;
        this.e = -1;
        this.f = -1;
        this.g = 0;
        this.h = 0;
        this.inGround = false;
        this.fromPlayer = 0;
        this.shake = 0;
        this.au = 0;
        this.damage = 2.0;
        this.l = 10.0;
        this.a(0.5f, 0.5f);
    }
    
    public EntityArrow(final World world, final double d0, final double d1, final double d2) {
        super(world);
        this.d = -1;
        this.e = -1;
        this.f = -1;
        this.g = 0;
        this.h = 0;
        this.inGround = false;
        this.fromPlayer = 0;
        this.shake = 0;
        this.au = 0;
        this.damage = 2.0;
        this.l = 10.0;
        this.a(0.5f, 0.5f);
        this.setPosition(d0, d1, d2);
        this.height = 0.0f;
    }
    
    public EntityArrow(final World world, final EntityLiving entityliving, final EntityLiving entityliving1, final float f, final float f1) {
        super(world);
        this.d = -1;
        this.e = -1;
        this.f = -1;
        this.g = 0;
        this.h = 0;
        this.inGround = false;
        this.fromPlayer = 0;
        this.shake = 0;
        this.au = 0;
        this.damage = 2.0;
        this.l = 10.0;
        this.shooter = entityliving;
        if (entityliving instanceof EntityHuman) {
            this.fromPlayer = 1;
        }
        this.locY = entityliving.locY + entityliving.getHeadHeight() - 0.10000000149011612;
        final double d0 = entityliving1.locX - entityliving.locX;
        final double d2 = entityliving1.boundingBox.b + entityliving1.length / 3.0f - this.locY;
        final double d3 = entityliving1.locZ - entityliving.locZ;
        final double d4 = MathHelper.sqrt(d0 * d0 + d3 * d3);
        if (d4 >= 1.0E-7) {
            final float f2 = (float)(Math.atan2(d3, d0) * 180.0 / 3.1415927410125732) - 90.0f;
            final float f3 = (float)(-(Math.atan2(d2, d4) * 180.0 / 3.1415927410125732));
            final double d5 = d0 / d4;
            final double d6 = d3 / d4;
            this.setPositionRotation(entityliving.locX + d5, this.locY, entityliving.locZ + d6, f2, f3);
            this.height = 0.0f;
            final float f4 = (float)d4 * 0.2f;
            this.shoot(d0, d2 + f4, d3, f, f1);
        }
    }
    
    public EntityArrow(final World world, final EntityLiving entityliving, final float f) {
        super(world);
        this.d = -1;
        this.e = -1;
        this.f = -1;
        this.g = 0;
        this.h = 0;
        this.inGround = false;
        this.fromPlayer = 0;
        this.shake = 0;
        this.au = 0;
        this.damage = 2.0;
        this.l = 10.0;
        this.shooter = entityliving;
        if (entityliving instanceof EntityHuman) {
            this.fromPlayer = 1;
        }
        this.a(0.5f, 0.5f);
        this.setPositionRotation(entityliving.locX, entityliving.locY + entityliving.getHeadHeight(), entityliving.locZ, entityliving.yaw, entityliving.pitch);
        this.locX -= MathHelper.cos(this.yaw / 180.0f * 3.1415927f) * 0.16f;
        this.locY -= 0.10000000149011612;
        this.locZ -= MathHelper.sin(this.yaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.locX, this.locY, this.locZ);
        this.height = 0.0f;
        this.motX = -MathHelper.sin(this.yaw / 180.0f * 3.1415927f) * MathHelper.cos(this.pitch / 180.0f * 3.1415927f);
        this.motZ = MathHelper.cos(this.yaw / 180.0f * 3.1415927f) * MathHelper.cos(this.pitch / 180.0f * 3.1415927f);
        this.motY = -MathHelper.sin(this.pitch / 180.0f * 3.1415927f);
        this.shoot(this.motX, this.motY, this.motZ, f * 1.5f, 1.0f);
    }
    
    protected void a() {
        this.datawatcher.a(16, (Object)(byte)0);
    }
    
    public void shoot(double d0, double d1, double d2, final float f, final float f1) {
        final float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
        d0 /= f2;
        d1 /= f2;
        d2 /= f2;
        d0 += this.random.nextGaussian() * (this.random.nextBoolean() ? -1 : 1) * 0.007499999832361937 * f1;
        d1 += this.random.nextGaussian() * (this.random.nextBoolean() ? -1 : 1) * 0.007499999832361937 * f1;
        d2 += this.random.nextGaussian() * (this.random.nextBoolean() ? -1 : 1) * 0.007499999832361937 * f1;
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
        this.j = 0;
    }
    
    public void l_() {
        super.l_();
        if (this.lastPitch == 0.0f && this.lastYaw == 0.0f) {
            final float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            final float n = (float)(Math.atan2(this.motX, this.motZ) * 180.0 / 3.1415927410125732);
            this.yaw = n;
            this.lastYaw = n;
            final float n2 = (float)(Math.atan2(this.motY, f) * 180.0 / 3.1415927410125732);
            this.pitch = n2;
            this.lastPitch = n2;
        }
        final int i = this.world.getTypeId(this.d, this.e, this.f);
        if (i > 0) {
            Block.byId[i].updateShape(this.world, this.d, this.e, this.f);
            final AxisAlignedBB axisalignedbb = Block.byId[i].b(this.world, this.d, this.e, this.f);
            if (axisalignedbb != null && axisalignedbb.a(this.world.getVec3DPool().create(this.locX, this.locY, this.locZ))) {
                this.inGround = true;
            }
        }
        if (this.shake > 0) {
            --this.shake;
        }
        if (this.inGround) {
            final int j = this.world.getTypeId(this.d, this.e, this.f);
            final int k = this.world.getData(this.d, this.e, this.f);
            if (j == this.g && k == this.h) {
                ++this.j;
                if (this.j == 1200) {
                    this.die();
                }
            }
            else {
                this.inGround = false;
                this.motX *= this.random.nextFloat() * 0.2f;
                this.motY *= this.random.nextFloat() * 0.2f;
                this.motZ *= this.random.nextFloat() * 0.2f;
                this.j = 0;
                this.au = 0;
            }
        }
        else {
            ++this.au;
            Vec3D vec3d = this.world.getVec3DPool().create(this.locX, this.locY, this.locZ);
            Vec3D vec3d2 = this.world.getVec3DPool().create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
            MovingObjectPosition movingobjectposition = this.world.rayTrace(vec3d, vec3d2, false, true);
            vec3d = this.world.getVec3DPool().create(this.locX, this.locY, this.locZ);
            vec3d2 = this.world.getVec3DPool().create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
            if (movingobjectposition != null) {
                vec3d2 = this.world.getVec3DPool().create(movingobjectposition.pos.c, movingobjectposition.pos.d, movingobjectposition.pos.e);
            }
            Entity entity = null;
            final List list = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0, 1.0, 1.0));
            double d0 = 0.0;
            for (int l = 0; l < list.size(); ++l) {
                final Entity entity2 = list.get(l);
                if (entity2.K() && (entity2 != this.shooter || this.au >= 5)) {
                    final float f2 = 0.3f;
                    final AxisAlignedBB axisalignedbb2 = entity2.boundingBox.grow(f2, f2, f2);
                    final MovingObjectPosition movingobjectposition2 = axisalignedbb2.a(vec3d, vec3d2);
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
            if (movingobjectposition != null && movingobjectposition.entity != null && movingobjectposition.entity instanceof EntityHuman) {
                final EntityHuman entityhuman = (EntityHuman)movingobjectposition.entity;
                if (entityhuman.abilities.isInvulnerable || (this.shooter instanceof EntityHuman && !((EntityHuman)this.shooter).a(entityhuman))) {
                    movingobjectposition = null;
                }
            }
            if (movingobjectposition != null) {
                CraftEventFactory.callProjectileHitEvent(this);
                if (movingobjectposition.entity != null) {
                    final float f3 = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
                    int i2 = MathHelper.f(f3 * this.damage);
                    if (this.d()) {
                        i2 += this.random.nextInt(i2 / 2 + 2);
                    }
                    DamageSource damagesource = null;
                    if (this.shooter == null) {
                        damagesource = DamageSource.arrow(this, this);
                    }
                    else {
                        damagesource = DamageSource.arrow(this, this.shooter);
                    }
                    if (movingobjectposition.entity.damageEntity(damagesource, i2)) {
                        if (this.isBurning() && !(movingobjectposition.entity instanceof EntityEnderman) && (!(movingobjectposition.entity instanceof EntityPlayer) || !(this.shooter instanceof EntityPlayer) || this.world.pvpMode)) {
                            final EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(this.getBukkitEntity(), entity.getBukkitEntity(), 5);
                            Bukkit.getPluginManager().callEvent(combustEvent);
                            if (!combustEvent.isCancelled()) {
                                movingobjectposition.entity.setOnFire(combustEvent.getDuration());
                            }
                        }
                        if (movingobjectposition.entity instanceof EntityLiving) {
                            final EntityLiving entityliving = (EntityLiving)movingobjectposition.entity;
                            if (!this.world.isStatic) {
                                entityliving.r(entityliving.bM() + 1);
                            }
                            if (this.aw > 0) {
                                final float f4 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
                                if (f4 > 0.0f) {
                                    movingobjectposition.entity.g(this.motX * this.aw * 0.6000000238418579 / f4, 0.1, this.motZ * this.aw * 0.6000000238418579 / f4);
                                }
                            }
                            if (this.shooter != null) {
                                EnchantmentThorns.a(this.shooter, entityliving, this.random);
                            }
                            if (this.shooter != null && movingobjectposition.entity != this.shooter && movingobjectposition.entity instanceof EntityHuman && this.shooter instanceof EntityPlayer) {
                                ((EntityPlayer)this.shooter).playerConnection.sendPacket(new Packet70Bed(6, 0));
                            }
                        }
                        this.makeSound("random.bowhit", 1.0f, 1.2f / (this.random.nextFloat() * 0.2f + 0.9f));
                        if (!(movingobjectposition.entity instanceof EntityEnderman)) {
                            this.die();
                        }
                    }
                    else {
                        this.motX *= -0.10000000149011612;
                        this.motY *= -0.10000000149011612;
                        this.motZ *= -0.10000000149011612;
                        this.yaw += 180.0f;
                        this.lastYaw += 180.0f;
                        this.au = 0;
                    }
                }
                else {
                    this.d = movingobjectposition.b;
                    this.e = movingobjectposition.c;
                    this.f = movingobjectposition.d;
                    this.g = this.world.getTypeId(this.d, this.e, this.f);
                    this.h = this.world.getData(this.d, this.e, this.f);
                    this.motX = (float)(movingobjectposition.pos.c - this.locX);
                    this.motY = (float)(movingobjectposition.pos.d - this.locY);
                    this.motZ = (float)(movingobjectposition.pos.e - this.locZ);
                    final float f3 = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
                    this.locX -= this.motX / f3 * 0.05000000074505806;
                    this.locY -= this.motY / f3 * 0.05000000074505806;
                    this.locZ -= this.motZ / f3 * 0.05000000074505806;
                    this.makeSound("random.bowhit", 1.0f, 1.2f / (this.random.nextFloat() * 0.2f + 0.9f));
                    this.inGround = true;
                    this.shake = 7;
                    this.a(false);
                    if (this.g != 0) {
                        Block.byId[this.g].a(this.world, this.d, this.e, this.f, this);
                    }
                }
            }
            if (this.d()) {
                for (int l = 0; l < 4; ++l) {
                    this.world.addParticle("crit", this.locX + this.motX * l / 4.0, this.locY + this.motY * l / 4.0, this.locZ + this.motZ * l / 4.0, -this.motX, -this.motY + 0.2, -this.motZ);
                }
            }
            this.locX += this.motX;
            this.locY += this.motY;
            this.locZ += this.motZ;
            final float f3 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0 / 3.1415927410125732);
            this.pitch = (float)(Math.atan2(this.motY, f3) * 180.0 / 3.1415927410125732);
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
            float f5 = 0.99f;
            final float f2 = 0.05f;
            if (this.G()) {
                for (int j2 = 0; j2 < 4; ++j2) {
                    final float f4 = 0.25f;
                    this.world.addParticle("bubble", this.locX - this.motX * f4, this.locY - this.motY * f4, this.locZ - this.motZ * f4, this.motX, this.motY, this.motZ);
                }
                f5 = 0.8f;
            }
            this.motX *= f5;
            this.motY *= f5;
            this.motZ *= f5;
            this.motY -= f2;
            this.setPosition(this.locX, this.locY, this.locZ);
            this.C();
        }
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setShort("xTile", (short)this.d);
        nbttagcompound.setShort("yTile", (short)this.e);
        nbttagcompound.setShort("zTile", (short)this.f);
        nbttagcompound.setByte("inTile", (byte)this.g);
        nbttagcompound.setByte("inData", (byte)this.h);
        nbttagcompound.setByte("shake", (byte)this.shake);
        nbttagcompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        nbttagcompound.setByte("pickup", (byte)this.fromPlayer);
        nbttagcompound.setDouble("damage", this.damage);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        this.d = nbttagcompound.getShort("xTile");
        this.e = nbttagcompound.getShort("yTile");
        this.f = nbttagcompound.getShort("zTile");
        this.g = (nbttagcompound.getByte("inTile") & 0xFF);
        this.h = (nbttagcompound.getByte("inData") & 0xFF);
        this.shake = (nbttagcompound.getByte("shake") & 0xFF);
        this.inGround = (nbttagcompound.getByte("inGround") == 1);
        if (nbttagcompound.hasKey("damage")) {
            this.damage = nbttagcompound.getDouble("damage");
        }
        if (nbttagcompound.hasKey("pickup")) {
            this.fromPlayer = nbttagcompound.getByte("pickup");
        }
        else if (nbttagcompound.hasKey("player")) {
            this.fromPlayer = (nbttagcompound.getBoolean("player") ? 1 : 0);
        }
    }
    
    public void b_(final EntityHuman entityhuman) {
        if (!this.world.isStatic && this.inGround && this.shake <= 0) {
            final ItemStack itemstack = new ItemStack(Item.ARROW);
            if (this.fromPlayer == 1 && entityhuman.inventory.canHold(itemstack) > 0) {
                final EntityItem item = new EntityItem(this.world, this.locX, this.locY, this.locZ, itemstack);
                final PlayerPickupItemEvent event = new PlayerPickupItemEvent((Player)entityhuman.getBukkitEntity(), new CraftItem(this.world.getServer(), this, item), 0);
                event.setCancelled(!entityhuman.canPickUpLoot);
                this.world.getServer().getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    return;
                }
            }
            boolean flag = this.fromPlayer == 1 || (this.fromPlayer == 2 && entityhuman.abilities.canInstantlyBuild);
            if (this.fromPlayer == 1 && !entityhuman.inventory.pickup(new ItemStack(Item.ARROW, 1))) {
                flag = false;
            }
            if (flag) {
                this.makeSound("random.pop", 0.2f, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                entityhuman.receive(this, 1);
                this.die();
            }
        }
    }
    
    protected boolean f_() {
        return false;
    }
    
    public void b(final double d0) {
        this.damage = d0;
    }
    
    public double c() {
        return this.damage;
    }
    
    public void a(final int i) {
        this.aw = i;
    }
    
    public boolean ap() {
        return false;
    }
    
    public void a(final boolean flag) {
        final byte b0 = this.datawatcher.getByte(16);
        if (flag) {
            this.datawatcher.watch(16, (byte)(b0 | 0x1));
        }
        else {
            this.datawatcher.watch(16, (byte)(b0 & 0xFFFFFFFE));
        }
    }
    
    public boolean d() {
        final byte b0 = this.datawatcher.getByte(16);
        return (b0 & 0x1) != 0x0;
    }
}
