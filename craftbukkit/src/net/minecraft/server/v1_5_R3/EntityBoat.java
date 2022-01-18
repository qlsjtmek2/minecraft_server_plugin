// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.Location;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.Event;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.entity.Vehicle;

public class EntityBoat extends Entity
{
    private boolean a;
    private double b;
    private int c;
    private double d;
    private double e;
    private double f;
    private double g;
    private double h;
    public double maxSpeed;
    public double occupiedDeceleration;
    public double unoccupiedDeceleration;
    public boolean landBoats;
    
    public void collide(final Entity entity) {
        final org.bukkit.entity.Entity hitEntity = (entity == null) ? null : entity.getBukkitEntity();
        final VehicleEntityCollisionEvent event = new VehicleEntityCollisionEvent((Vehicle)this.getBukkitEntity(), hitEntity);
        this.world.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        super.collide(entity);
    }
    
    public EntityBoat(final World world) {
        super(world);
        this.maxSpeed = 0.4;
        this.occupiedDeceleration = 0.2;
        this.unoccupiedDeceleration = -1.0;
        this.landBoats = false;
        this.a = true;
        this.b = 0.07;
        this.m = true;
        this.a(1.5f, 0.6f);
        this.height = this.length / 2.0f;
    }
    
    protected boolean f_() {
        return false;
    }
    
    protected void a() {
        this.datawatcher.a(17, new Integer(0));
        this.datawatcher.a(18, new Integer(1));
        this.datawatcher.a(19, new Integer(0));
    }
    
    public AxisAlignedBB g(final Entity entity) {
        return entity.boundingBox;
    }
    
    public AxisAlignedBB D() {
        return this.boundingBox;
    }
    
    public boolean L() {
        return true;
    }
    
    public EntityBoat(final World world, final double d0, final double d1, final double d2) {
        this(world);
        this.setPosition(d0, d1 + this.height, d2);
        this.motX = 0.0;
        this.motY = 0.0;
        this.motZ = 0.0;
        this.lastX = d0;
        this.lastY = d1;
        this.lastZ = d2;
        this.world.getServer().getPluginManager().callEvent(new VehicleCreateEvent((Vehicle)this.getBukkitEntity()));
    }
    
    public double W() {
        return this.length * 0.0 - 0.30000001192092896;
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        if (this.world.isStatic || this.dead) {
            return true;
        }
        final Vehicle vehicle = (Vehicle)this.getBukkitEntity();
        final org.bukkit.entity.Entity attacker = (damagesource.getEntity() == null) ? null : damagesource.getEntity().getBukkitEntity();
        final VehicleDamageEvent event = new VehicleDamageEvent(vehicle, attacker, i);
        this.world.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return true;
        }
        this.h(-this.h());
        this.b(10);
        this.setDamage(this.getDamage() + i * 10);
        this.J();
        final boolean flag = damagesource.getEntity() instanceof EntityHuman && ((EntityHuman)damagesource.getEntity()).abilities.canInstantlyBuild;
        if (flag || this.getDamage() > 40) {
            final VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, attacker);
            this.world.getServer().getPluginManager().callEvent(destroyEvent);
            if (destroyEvent.isCancelled()) {
                this.setDamage(40);
                return true;
            }
            if (this.passenger != null) {
                this.passenger.mount(this);
            }
            if (!flag) {
                this.a(Item.BOAT.id, 1, 0.0f);
            }
            this.die();
        }
        return true;
    }
    
    public boolean K() {
        return !this.dead;
    }
    
    public void l_() {
        final double prevX = this.locX;
        final double prevY = this.locY;
        final double prevZ = this.locZ;
        final float prevYaw = this.yaw;
        final float prevPitch = this.pitch;
        super.l_();
        if (this.g() > 0) {
            this.b(this.g() - 1);
        }
        if (this.getDamage() > 0) {
            this.setDamage(this.getDamage() - 1);
        }
        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        final byte b0 = 5;
        double d0 = 0.0;
        for (int i = 0; i < b0; ++i) {
            final double d2 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (i + 0) / b0 - 0.125;
            final double d3 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (i + 1) / b0 - 0.125;
            final AxisAlignedBB axisalignedbb = AxisAlignedBB.a().a(this.boundingBox.a, d2, this.boundingBox.c, this.boundingBox.d, d3, this.boundingBox.f);
            if (this.world.b(axisalignedbb, Material.WATER)) {
                d0 += 1.0 / b0;
            }
        }
        final double d4 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        if (d4 > 0.26249999999999996) {
            final double d5 = Math.cos(this.yaw * 3.141592653589793 / 180.0);
            final double d6 = Math.sin(this.yaw * 3.141592653589793 / 180.0);
            for (int j = 0; j < 1.0 + d4 * 60.0; ++j) {
                final double d7 = this.random.nextFloat() * 2.0f - 1.0f;
                final double d8 = (this.random.nextInt(2) * 2 - 1) * 0.7;
                if (this.random.nextBoolean()) {
                    final double d9 = this.locX - d5 * d7 * 0.8 + d6 * d8;
                    final double d10 = this.locZ - d6 * d7 * 0.8 - d5 * d8;
                    this.world.addParticle("splash", d9, this.locY - 0.125, d10, this.motX, this.motY, this.motZ);
                }
                else {
                    final double d9 = this.locX + d5 + d6 * d7 * 0.7;
                    final double d10 = this.locZ + d6 - d5 * d7 * 0.7;
                    this.world.addParticle("splash", d9, this.locY - 0.125, d10, this.motX, this.motY, this.motZ);
                }
            }
        }
        if (this.world.isStatic && this.a) {
            if (this.c > 0) {
                final double d5 = this.locX + (this.d - this.locX) / this.c;
                final double d6 = this.locY + (this.e - this.locY) / this.c;
                final double d11 = this.locZ + (this.f - this.locZ) / this.c;
                final double d12 = MathHelper.g(this.g - this.yaw);
                this.yaw += (float)(d12 / this.c);
                this.pitch += (float)((this.h - this.pitch) / this.c);
                --this.c;
                this.setPosition(d5, d6, d11);
                this.b(this.yaw, this.pitch);
            }
            else {
                final double d5 = this.locX + this.motX;
                final double d6 = this.locY + this.motY;
                final double d11 = this.locZ + this.motZ;
                this.setPosition(d5, d6, d11);
                if (this.onGround) {
                    this.motX *= 0.5;
                    this.motY *= 0.5;
                    this.motZ *= 0.5;
                }
                this.motX *= 0.9900000095367432;
                this.motY *= 0.949999988079071;
                this.motZ *= 0.9900000095367432;
            }
        }
        else {
            if (d0 < 1.0) {
                final double d5 = d0 * 2.0 - 1.0;
                this.motY += 0.03999999910593033 * d5;
            }
            else {
                if (this.motY < 0.0) {
                    this.motY /= 2.0;
                }
                this.motY += 0.007000000216066837;
            }
            if (this.passenger != null) {
                this.motX += this.passenger.motX * this.b;
                this.motZ += this.passenger.motZ * this.b;
            }
            else if (this.unoccupiedDeceleration >= 0.0) {
                this.motX *= this.unoccupiedDeceleration;
                this.motZ *= this.unoccupiedDeceleration;
                if (this.motX <= 1.0E-5) {
                    this.motX = 0.0;
                }
                if (this.motZ <= 1.0E-5) {
                    this.motZ = 0.0;
                }
            }
            double d5 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            if (d5 > 0.35) {
                final double d6 = 0.35 / d5;
                this.motX *= d6;
                this.motZ *= d6;
                d5 = 0.35;
            }
            if (d5 > d4 && this.b < 0.35) {
                this.b += (0.35 - this.b) / 35.0;
                if (this.b > 0.35) {
                    this.b = 0.35;
                }
            }
            else {
                this.b -= (this.b - 0.07) / 35.0;
                if (this.b < 0.07) {
                    this.b = 0.07;
                }
            }
            if (this.onGround && !this.landBoats) {
                this.motX *= 0.5;
                this.motY *= 0.5;
                this.motZ *= 0.5;
            }
            this.move(this.motX, this.motY, this.motZ);
            if (this.positionChanged && d4 > 0.2) {
                if (!this.world.isStatic && !this.dead) {
                    final Vehicle vehicle = (Vehicle)this.getBukkitEntity();
                    final VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, null);
                    this.world.getServer().getPluginManager().callEvent(destroyEvent);
                    if (!destroyEvent.isCancelled()) {
                        this.die();
                        for (int k = 0; k < 3; ++k) {
                            this.a(Block.WOOD.id, 1, 0.0f);
                        }
                        for (int k = 0; k < 2; ++k) {
                            this.a(Item.STICK.id, 1, 0.0f);
                        }
                    }
                }
            }
            else {
                this.motX *= 0.9900000095367432;
                this.motY *= 0.949999988079071;
                this.motZ *= 0.9900000095367432;
            }
            this.pitch = 0.0f;
            double d6 = this.yaw;
            final double d11 = this.lastX - this.locX;
            final double d12 = this.lastZ - this.locZ;
            if (d11 * d11 + d12 * d12 > 0.001) {
                d6 = (float)(Math.atan2(d12, d11) * 180.0 / 3.141592653589793);
            }
            double d13 = MathHelper.g(d6 - this.yaw);
            if (d13 > 20.0) {
                d13 = 20.0;
            }
            if (d13 < -20.0) {
                d13 = -20.0;
            }
            this.b(this.yaw += (float)d13, this.pitch);
            final Server server = this.world.getServer();
            final org.bukkit.World bworld = this.world.getWorld();
            final Location from = new Location(bworld, prevX, prevY, prevZ, prevYaw, prevPitch);
            final Location to = new Location(bworld, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            final Vehicle vehicle2 = (Vehicle)this.getBukkitEntity();
            server.getPluginManager().callEvent(new VehicleUpdateEvent(vehicle2));
            if (!from.equals(to)) {
                final VehicleMoveEvent event = new VehicleMoveEvent(vehicle2, from, to);
                server.getPluginManager().callEvent(event);
            }
            if (!this.world.isStatic) {
                final List list = this.world.getEntities(this, this.boundingBox.grow(0.20000000298023224, 0.0, 0.20000000298023224));
                if (list != null && !list.isEmpty()) {
                    for (int l = 0; l < list.size(); ++l) {
                        final Entity entity = list.get(l);
                        if (entity != this.passenger && entity.L() && entity instanceof EntityBoat) {
                            entity.collide(this);
                        }
                    }
                }
                for (int l = 0; l < 4; ++l) {
                    final int i2 = MathHelper.floor(this.locX + (l % 2 - 0.5) * 0.8);
                    final int j2 = MathHelper.floor(this.locZ + (l / 2 - 0.5) * 0.8);
                    for (int k2 = 0; k2 < 2; ++k2) {
                        final int l2 = MathHelper.floor(this.locY) + k2;
                        final int i3 = this.world.getTypeId(i2, l2, j2);
                        if (i3 == Block.SNOW.id) {
                            if (!CraftEventFactory.callEntityChangeBlockEvent(this, i2, l2, j2, 0, 0).isCancelled()) {
                                this.world.setAir(i2, l2, j2);
                            }
                        }
                        else if (i3 == Block.WATER_LILY.id) {
                            if (!CraftEventFactory.callEntityChangeBlockEvent(this, i2, l2, j2, 0, 0).isCancelled()) {
                                this.world.setAir(i2, l2, j2, true);
                            }
                        }
                    }
                }
                if (this.passenger != null && this.passenger.dead) {
                    this.passenger.vehicle = null;
                    this.passenger = null;
                }
            }
        }
    }
    
    public void U() {
        if (this.passenger != null) {
            final double d0 = Math.cos(this.yaw * 3.141592653589793 / 180.0) * 0.4;
            final double d2 = Math.sin(this.yaw * 3.141592653589793 / 180.0) * 0.4;
            this.passenger.setPosition(this.locX + d0, this.locY + this.W() + this.passenger.V(), this.locZ + d2);
        }
    }
    
    protected void b(final NBTTagCompound nbttagcompound) {
    }
    
    protected void a(final NBTTagCompound nbttagcompound) {
    }
    
    public boolean a_(final EntityHuman entityhuman) {
        if (this.passenger != null && this.passenger instanceof EntityHuman && this.passenger != entityhuman) {
            return true;
        }
        if (!this.world.isStatic) {
            entityhuman.mount(this);
        }
        return true;
    }
    
    public void setDamage(final int i) {
        this.datawatcher.watch(19, i);
    }
    
    public int getDamage() {
        return this.datawatcher.getInt(19);
    }
    
    public void b(final int i) {
        this.datawatcher.watch(17, i);
    }
    
    public int g() {
        return this.datawatcher.getInt(17);
    }
    
    public void h(final int i) {
        this.datawatcher.watch(18, i);
    }
    
    public int h() {
        return this.datawatcher.getInt(18);
    }
}
