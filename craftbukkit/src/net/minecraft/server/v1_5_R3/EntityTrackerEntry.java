// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.util.Vector;
import java.util.Iterator;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.entity.Player;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class EntityTrackerEntry
{
    public Entity tracker;
    public int b;
    public int c;
    public int xLoc;
    public int yLoc;
    public int zLoc;
    public int yRot;
    public int xRot;
    public int i;
    public double j;
    public double k;
    public double l;
    public int m;
    private double p;
    private double q;
    private double r;
    private boolean s;
    private boolean isMoving;
    private int u;
    private Entity v;
    private boolean w;
    public boolean n;
    public Set trackedPlayers;
    
    public EntityTrackerEntry(final Entity entity, final int i, final int j, final boolean flag) {
        this.m = 0;
        this.s = false;
        this.u = 0;
        this.w = false;
        this.n = false;
        this.trackedPlayers = new HashSet();
        this.tracker = entity;
        this.b = i;
        this.c = j;
        this.isMoving = flag;
        this.xLoc = MathHelper.floor(entity.locX * 32.0);
        this.yLoc = MathHelper.floor(entity.locY * 32.0);
        this.zLoc = MathHelper.floor(entity.locZ * 32.0);
        this.yRot = MathHelper.d(entity.yaw * 256.0f / 360.0f);
        this.xRot = MathHelper.d(entity.pitch * 256.0f / 360.0f);
        this.i = MathHelper.d(entity.getHeadRotation() * 256.0f / 360.0f);
    }
    
    public boolean equals(final Object object) {
        return object instanceof EntityTrackerEntry && ((EntityTrackerEntry)object).tracker.id == this.tracker.id;
    }
    
    public int hashCode() {
        return this.tracker.id;
    }
    
    public void track(final List list) {
        this.n = false;
        if (!this.s || this.tracker.e(this.p, this.q, this.r) > 16.0) {
            this.p = this.tracker.locX;
            this.q = this.tracker.locY;
            this.r = this.tracker.locZ;
            this.s = true;
            this.n = true;
            this.scanPlayers(list);
        }
        if (this.v != this.tracker.vehicle) {
            this.v = this.tracker.vehicle;
            this.broadcast(new Packet39AttachEntity(this.tracker, this.tracker.vehicle));
        }
        if (this.tracker instanceof EntityItemFrame) {
            final EntityItemFrame i4 = (EntityItemFrame)this.tracker;
            final ItemStack i5 = i4.i();
            if (this.m % 10 == 0 && i5 != null && i5.getItem() instanceof ItemWorldMap) {
                final WorldMap i6 = Item.MAP.getSavedMap(i5, this.tracker.world);
                for (final EntityHuman j2 : this.trackedPlayers) {
                    final EntityPlayer j3 = (EntityPlayer)j2;
                    i6.a(j3, i5);
                    if (j3.playerConnection.lowPriorityCount() <= 5) {
                        final Packet j4 = Item.MAP.c(i5, this.tracker.world, j3);
                        if (j4 == null) {
                            continue;
                        }
                        j3.playerConnection.sendPacket(j4);
                    }
                }
            }
            final DataWatcher i7 = this.tracker.getDataWatcher();
            if (i7.a()) {
                this.broadcastIncludingSelf(new Packet40EntityMetadata(this.tracker.id, i7, false));
            }
        }
        else if (this.m % this.c == 0 || this.tracker.an || this.tracker.getDataWatcher().a()) {
            if (this.tracker.vehicle == null) {
                ++this.u;
                final int k = this.tracker.at.a(this.tracker.locX);
                final int l = MathHelper.floor(this.tracker.locY * 32.0);
                final int m = this.tracker.at.a(this.tracker.locZ);
                final int l2 = MathHelper.d(this.tracker.yaw * 256.0f / 360.0f);
                final int i8 = MathHelper.d(this.tracker.pitch * 256.0f / 360.0f);
                final int j5 = k - this.xLoc;
                final int k2 = l - this.yLoc;
                final int l3 = m - this.zLoc;
                Object object = null;
                final boolean flag = Math.abs(j5) >= 4 || Math.abs(k2) >= 4 || Math.abs(l3) >= 4 || this.m % 60 == 0;
                final boolean flag2 = Math.abs(l2 - this.yRot) >= 4 || Math.abs(i8 - this.xRot) >= 4;
                if (flag) {
                    this.xLoc = k;
                    this.yLoc = l;
                    this.zLoc = m;
                }
                if (flag2) {
                    this.yRot = l2;
                    this.xRot = i8;
                }
                if (this.m > 0 || this.tracker instanceof EntityArrow) {
                    if (j5 >= -128 && j5 < 128 && k2 >= -128 && k2 < 128 && l3 >= -128 && l3 < 128 && this.u <= 400 && !this.w) {
                        if (flag && flag2) {
                            object = new Packet33RelEntityMoveLook(this.tracker.id, (byte)j5, (byte)k2, (byte)l3, (byte)l2, (byte)i8);
                        }
                        else if (flag) {
                            object = new Packet31RelEntityMove(this.tracker.id, (byte)j5, (byte)k2, (byte)l3);
                        }
                        else if (flag2) {
                            object = new Packet32EntityLook(this.tracker.id, (byte)l2, (byte)i8);
                        }
                    }
                    else {
                        this.u = 0;
                        if (this.tracker instanceof EntityPlayer) {
                            this.scanPlayers(new ArrayList(this.trackedPlayers));
                        }
                        object = new Packet34EntityTeleport(this.tracker.id, k, l, m, (byte)l2, (byte)i8);
                    }
                }
                if (this.isMoving) {
                    final double d0 = this.tracker.motX - this.j;
                    final double d2 = this.tracker.motY - this.k;
                    final double d3 = this.tracker.motZ - this.l;
                    final double d4 = 0.02;
                    final double d5 = d0 * d0 + d2 * d2 + d3 * d3;
                    if (d5 > d4 * d4 || (d5 > 0.0 && this.tracker.motX == 0.0 && this.tracker.motY == 0.0 && this.tracker.motZ == 0.0)) {
                        this.j = this.tracker.motX;
                        this.k = this.tracker.motY;
                        this.l = this.tracker.motZ;
                        this.broadcast(new Packet28EntityVelocity(this.tracker.id, this.j, this.k, this.l));
                    }
                }
                if (object != null) {
                    this.broadcast((Packet)object);
                }
                final DataWatcher datawatcher1 = this.tracker.getDataWatcher();
                if (datawatcher1.a()) {
                    this.broadcastIncludingSelf(new Packet40EntityMetadata(this.tracker.id, datawatcher1, false));
                }
                this.w = false;
            }
            else {
                final int k = MathHelper.d(this.tracker.yaw * 256.0f / 360.0f);
                final int l = MathHelper.d(this.tracker.pitch * 256.0f / 360.0f);
                final boolean flag3 = Math.abs(k - this.yRot) >= 4 || Math.abs(l - this.xRot) >= 4;
                if (flag3) {
                    this.broadcast(new Packet32EntityLook(this.tracker.id, (byte)k, (byte)l));
                    this.yRot = k;
                    this.xRot = l;
                }
                this.xLoc = this.tracker.at.a(this.tracker.locX);
                this.yLoc = MathHelper.floor(this.tracker.locY * 32.0);
                this.zLoc = this.tracker.at.a(this.tracker.locZ);
                final DataWatcher datawatcher2 = this.tracker.getDataWatcher();
                if (datawatcher2.a()) {
                    this.broadcastIncludingSelf(new Packet40EntityMetadata(this.tracker.id, datawatcher2, false));
                }
                this.w = true;
            }
            final int k = MathHelper.d(this.tracker.getHeadRotation() * 256.0f / 360.0f);
            if (Math.abs(k - this.i) >= 4) {
                this.broadcast(new Packet35EntityHeadRotation(this.tracker.id, (byte)k));
                this.i = k;
            }
            this.tracker.an = false;
        }
        ++this.m;
        if (this.tracker.velocityChanged) {
            boolean cancelled = false;
            if (this.tracker instanceof EntityPlayer) {
                final Player player = (Player)this.tracker.getBukkitEntity();
                final Vector velocity = player.getVelocity();
                final PlayerVelocityEvent event = new PlayerVelocityEvent(player, velocity);
                this.tracker.world.getServer().getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    cancelled = true;
                }
                else if (!velocity.equals(event.getVelocity())) {
                    player.setVelocity(velocity);
                }
            }
            if (!cancelled) {
                this.broadcastIncludingSelf(new Packet28EntityVelocity(this.tracker));
            }
            this.tracker.velocityChanged = false;
        }
    }
    
    public void broadcast(final Packet packet) {
        for (final EntityPlayer entityplayer : this.trackedPlayers) {
            entityplayer.playerConnection.sendPacket(packet);
        }
    }
    
    public void broadcastIncludingSelf(final Packet packet) {
        this.broadcast(packet);
        if (this.tracker instanceof EntityPlayer) {
            ((EntityPlayer)this.tracker).playerConnection.sendPacket(packet);
        }
    }
    
    public void a() {
        for (final EntityPlayer entityplayer : this.trackedPlayers) {
            entityplayer.removeQueue.add(this.tracker.id);
        }
    }
    
    public void a(final EntityPlayer entityplayer) {
        if (this.trackedPlayers.contains(entityplayer)) {
            entityplayer.removeQueue.add(this.tracker.id);
            this.trackedPlayers.remove(entityplayer);
        }
    }
    
    public void updatePlayer(final EntityPlayer entityplayer) {
        if (Thread.currentThread() != MinecraftServer.getServer().primaryThread) {
            throw new IllegalStateException("Asynchronous player tracker update!");
        }
        if (entityplayer != this.tracker) {
            final double d0 = entityplayer.locX - this.xLoc / 32;
            final double d2 = entityplayer.locZ - this.zLoc / 32;
            if (d0 >= -this.b && d0 <= this.b && d2 >= -this.b && d2 <= this.b) {
                if (!this.trackedPlayers.contains(entityplayer) && (this.d(entityplayer) || this.tracker.p)) {
                    if (this.tracker instanceof EntityPlayer) {
                        final Player player = ((EntityPlayer)this.tracker).getBukkitEntity();
                        if (!entityplayer.getBukkitEntity().canSee(player)) {
                            return;
                        }
                    }
                    entityplayer.removeQueue.remove((Object)this.tracker.id);
                    this.trackedPlayers.add(entityplayer);
                    final Packet packet = this.b();
                    entityplayer.playerConnection.sendPacket(packet);
                    if (!this.tracker.getDataWatcher().d()) {
                        entityplayer.playerConnection.sendPacket(new Packet40EntityMetadata(this.tracker.id, this.tracker.getDataWatcher(), true));
                    }
                    this.j = this.tracker.motX;
                    this.k = this.tracker.motY;
                    this.l = this.tracker.motZ;
                    if (this.isMoving && !(packet instanceof Packet24MobSpawn)) {
                        entityplayer.playerConnection.sendPacket(new Packet28EntityVelocity(this.tracker.id, this.tracker.motX, this.tracker.motY, this.tracker.motZ));
                    }
                    if (this.tracker.vehicle != null && this.tracker.id > this.tracker.vehicle.id) {
                        entityplayer.playerConnection.sendPacket(new Packet39AttachEntity(this.tracker, this.tracker.vehicle));
                    }
                    else if (this.tracker.passenger != null && this.tracker.id > this.tracker.passenger.id) {
                        entityplayer.playerConnection.sendPacket(new Packet39AttachEntity(this.tracker.passenger, this.tracker));
                    }
                    if (this.tracker instanceof EntityLiving) {
                        for (int i = 0; i < 5; ++i) {
                            final ItemStack itemstack = ((EntityLiving)this.tracker).getEquipment(i);
                            if (itemstack != null) {
                                entityplayer.playerConnection.sendPacket(new Packet5EntityEquipment(this.tracker.id, i, itemstack));
                            }
                        }
                    }
                    if (this.tracker instanceof EntityHuman) {
                        final EntityHuman entityhuman = (EntityHuman)this.tracker;
                        if (entityhuman.isSleeping()) {
                            entityplayer.playerConnection.sendPacket(new Packet17EntityLocationAction(this.tracker, 0, MathHelper.floor(this.tracker.locX), MathHelper.floor(this.tracker.locY), MathHelper.floor(this.tracker.locZ)));
                        }
                    }
                    this.i = MathHelper.d(this.tracker.getHeadRotation() * 256.0f / 360.0f);
                    this.broadcast(new Packet35EntityHeadRotation(this.tracker.id, (byte)this.i));
                    if (this.tracker instanceof EntityLiving) {
                        final EntityLiving entityliving = (EntityLiving)this.tracker;
                        for (final MobEffect mobeffect : entityliving.getEffects()) {
                            entityplayer.playerConnection.sendPacket(new Packet41MobEffect(this.tracker.id, mobeffect));
                        }
                    }
                }
            }
            else if (this.trackedPlayers.contains(entityplayer)) {
                this.trackedPlayers.remove(entityplayer);
                entityplayer.removeQueue.add(this.tracker.id);
            }
        }
    }
    
    private boolean d(final EntityPlayer entityplayer) {
        return entityplayer.o().getPlayerChunkMap().a(entityplayer, this.tracker.aj, this.tracker.al);
    }
    
    public void scanPlayers(final List list) {
        for (int i = 0; i < list.size(); ++i) {
            this.updatePlayer(list.get(i));
        }
    }
    
    private Packet b() {
        if (this.tracker.dead) {
            return null;
        }
        if (this.tracker instanceof EntityItem) {
            return new Packet23VehicleSpawn(this.tracker, 2, 1);
        }
        if (this.tracker instanceof EntityPlayer) {
            return new Packet20NamedEntitySpawn((EntityHuman)this.tracker);
        }
        if (this.tracker instanceof EntityMinecartAbstract) {
            final EntityMinecartAbstract entityminecartabstract = (EntityMinecartAbstract)this.tracker;
            return new Packet23VehicleSpawn(this.tracker, 10, entityminecartabstract.getType());
        }
        if (this.tracker instanceof EntityBoat) {
            return new Packet23VehicleSpawn(this.tracker, 1);
        }
        if (this.tracker instanceof IAnimal || this.tracker instanceof EntityEnderDragon) {
            this.i = MathHelper.d(this.tracker.getHeadRotation() * 256.0f / 360.0f);
            return new Packet24MobSpawn((EntityLiving)this.tracker);
        }
        if (this.tracker instanceof EntityFishingHook) {
            final EntityHuman entityhuman = ((EntityFishingHook)this.tracker).owner;
            return new Packet23VehicleSpawn(this.tracker, 90, (entityhuman != null) ? entityhuman.id : this.tracker.id);
        }
        if (this.tracker instanceof EntityArrow) {
            final Entity entity = ((EntityArrow)this.tracker).shooter;
            return new Packet23VehicleSpawn(this.tracker, 60, (entity != null) ? entity.id : this.tracker.id);
        }
        if (this.tracker instanceof EntitySnowball) {
            return new Packet23VehicleSpawn(this.tracker, 61);
        }
        if (this.tracker instanceof EntityPotion) {
            return new Packet23VehicleSpawn(this.tracker, 73, ((EntityPotion)this.tracker).getPotionValue());
        }
        if (this.tracker instanceof EntityThrownExpBottle) {
            return new Packet23VehicleSpawn(this.tracker, 75);
        }
        if (this.tracker instanceof EntityEnderPearl) {
            return new Packet23VehicleSpawn(this.tracker, 65);
        }
        if (this.tracker instanceof EntityEnderSignal) {
            return new Packet23VehicleSpawn(this.tracker, 72);
        }
        if (this.tracker instanceof EntityFireworks) {
            return new Packet23VehicleSpawn(this.tracker, 76);
        }
        if (this.tracker instanceof EntityFireball) {
            final EntityFireball entityfireball = (EntityFireball)this.tracker;
            Packet23VehicleSpawn packet23vehiclespawn = null;
            byte b0 = 63;
            if (this.tracker instanceof EntitySmallFireball) {
                b0 = 64;
            }
            else if (this.tracker instanceof EntityWitherSkull) {
                b0 = 66;
            }
            if (entityfireball.shooter != null) {
                packet23vehiclespawn = new Packet23VehicleSpawn(this.tracker, b0, ((EntityFireball)this.tracker).shooter.id);
            }
            else {
                packet23vehiclespawn = new Packet23VehicleSpawn(this.tracker, b0, 0);
            }
            packet23vehiclespawn.e = (int)(entityfireball.dirX * 8000.0);
            packet23vehiclespawn.f = (int)(entityfireball.dirY * 8000.0);
            packet23vehiclespawn.g = (int)(entityfireball.dirZ * 8000.0);
            return packet23vehiclespawn;
        }
        if (this.tracker instanceof EntityEgg) {
            return new Packet23VehicleSpawn(this.tracker, 62);
        }
        if (this.tracker instanceof EntityTNTPrimed) {
            return new Packet23VehicleSpawn(this.tracker, 50);
        }
        if (this.tracker instanceof EntityEnderCrystal) {
            return new Packet23VehicleSpawn(this.tracker, 51);
        }
        if (this.tracker instanceof EntityFallingBlock) {
            final EntityFallingBlock entityfallingblock = (EntityFallingBlock)this.tracker;
            return new Packet23VehicleSpawn(this.tracker, 70, entityfallingblock.id | entityfallingblock.data << 16);
        }
        if (this.tracker instanceof EntityPainting) {
            return new Packet25EntityPainting((EntityPainting)this.tracker);
        }
        if (this.tracker instanceof EntityItemFrame) {
            final EntityItemFrame entityitemframe = (EntityItemFrame)this.tracker;
            final Packet23VehicleSpawn packet23vehiclespawn = new Packet23VehicleSpawn(this.tracker, 71, entityitemframe.direction);
            packet23vehiclespawn.b = MathHelper.d(entityitemframe.x * 32);
            packet23vehiclespawn.c = MathHelper.d(entityitemframe.y * 32);
            packet23vehiclespawn.d = MathHelper.d(entityitemframe.z * 32);
            return packet23vehiclespawn;
        }
        if (this.tracker instanceof EntityExperienceOrb) {
            return new Packet26AddExpOrb((EntityExperienceOrb)this.tracker);
        }
        throw new IllegalArgumentException("Don't know how to add " + this.tracker.getClass() + "!");
    }
    
    public void clear(final EntityPlayer entityplayer) {
        if (Thread.currentThread() != MinecraftServer.getServer().primaryThread) {
            throw new IllegalStateException("Asynchronous player tracker clear!");
        }
        if (this.trackedPlayers.contains(entityplayer)) {
            this.trackedPlayers.remove(entityplayer);
            entityplayer.removeQueue.add(this.tracker.id);
        }
    }
}
