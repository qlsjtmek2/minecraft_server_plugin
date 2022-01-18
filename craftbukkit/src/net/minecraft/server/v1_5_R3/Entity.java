// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;
import org.bukkit.Location;
import java.util.Iterator;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.craftbukkit.v1_5_R3.CraftTravelAgent;
import org.bukkit.TravelAgent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.painting.PaintingBreakByEntityEvent;
import org.bukkit.entity.Painting;
import org.bukkit.plugin.PluginManager;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Vehicle;
import java.util.List;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.block.Block;
import org.bukkit.Server;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_5_R3.Spigot;
import org.bukkit.craftbukkit.v1_5_R3.SpigotTimings;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.spigotmc.CustomTimingsHandler;
import java.util.UUID;
import java.util.Random;

public abstract class Entity
{
    private static final int CURRENT_LEVEL = 2;
    private static int entityCount;
    public int id;
    public double l;
    public boolean m;
    public Entity passenger;
    public Entity vehicle;
    public boolean p;
    public World world;
    public double lastX;
    public double lastY;
    public double lastZ;
    public double locX;
    public double locY;
    public double locZ;
    public double motX;
    public double motY;
    public double motZ;
    public float yaw;
    public float pitch;
    public float lastYaw;
    public float lastPitch;
    public final AxisAlignedBB boundingBox;
    public boolean onGround;
    public boolean positionChanged;
    public boolean H;
    public boolean I;
    public boolean velocityChanged;
    protected boolean K;
    public boolean L;
    public boolean dead;
    public float height;
    public float width;
    public float length;
    public float Q;
    public float R;
    public float S;
    public float fallDistance;
    private int c;
    public double U;
    public double V;
    public double W;
    public float X;
    public float Y;
    public boolean Z;
    public float aa;
    protected Random random;
    public int ticksLived;
    public int maxFireTicks;
    public int fireTicks;
    public boolean inWater;
    public int noDamageTicks;
    private boolean justCreated;
    protected boolean fireProof;
    protected DataWatcher datawatcher;
    private double f;
    private double g;
    public boolean ai;
    public int aj;
    public int ak;
    public int al;
    public boolean am;
    public boolean an;
    public int portalCooldown;
    protected boolean ap;
    protected int aq;
    public int dimension;
    protected int as;
    private boolean invulnerable;
    public UUID uniqueID;
    public EnumEntitySize at;
    public boolean valid;
    public CustomTimingsHandler tickTimer;
    public final byte activationType;
    public final boolean defaultActivationState;
    public long activatedTick;
    protected CraftEntity bukkitEntity;
    
    static boolean isLevelAtLeast(final NBTTagCompound tag, final int level) {
        return tag.hasKey("Bukkit.updateLevel") && tag.getInt("Bukkit.updateLevel") >= level;
    }
    
    public void inactiveTick() {
    }
    
    public Entity(final World world) {
        this.valid = false;
        this.tickTimer = SpigotTimings.getEntityTimings(this);
        this.activationType = Spigot.initializeEntityActivationType(this);
        this.activatedTick = 0L;
        this.id = Entity.entityCount++;
        this.l = 1.0;
        this.m = false;
        this.boundingBox = AxisAlignedBB.a(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        this.onGround = false;
        this.I = false;
        this.velocityChanged = false;
        this.L = true;
        this.dead = false;
        this.height = 0.0f;
        this.width = 0.6f;
        this.length = 1.8f;
        this.Q = 0.0f;
        this.R = 0.0f;
        this.S = 0.0f;
        this.fallDistance = 0.0f;
        this.c = 1;
        this.X = 0.0f;
        this.Y = 0.0f;
        this.Z = false;
        this.aa = 0.0f;
        this.random = new Random();
        this.ticksLived = 0;
        this.maxFireTicks = 1;
        this.fireTicks = 0;
        this.inWater = false;
        this.noDamageTicks = 0;
        this.justCreated = true;
        this.fireProof = false;
        this.datawatcher = new DataWatcher();
        this.ai = false;
        this.as = 0;
        this.invulnerable = false;
        this.uniqueID = new UUID(this.random.nextLong(), this.random.nextLong());
        this.at = EnumEntitySize.SIZE_2;
        this.world = world;
        this.setPosition(0.0, 0.0, 0.0);
        if (world != null) {
            this.dimension = world.worldProvider.dimension;
            this.defaultActivationState = Spigot.initializeEntityActivationState(this, world.getWorld());
        }
        else {
            this.defaultActivationState = false;
        }
        this.datawatcher.a(0, (Object)(byte)0);
        this.datawatcher.a(1, (Object)(short)300);
        this.a();
    }
    
    protected abstract void a();
    
    public DataWatcher getDataWatcher() {
        return this.datawatcher;
    }
    
    public boolean equals(final Object object) {
        return object instanceof Entity && ((Entity)object).id == this.id;
    }
    
    public int hashCode() {
        return this.id;
    }
    
    public void die() {
        this.dead = true;
    }
    
    protected void a(final float f, final float f1) {
        if (f != this.width || f1 != this.length) {
            this.width = f;
            this.length = f1;
            this.boundingBox.d = this.boundingBox.a + this.width;
            this.boundingBox.f = this.boundingBox.c + this.width;
            this.boundingBox.e = this.boundingBox.b + this.length;
        }
        final float f2 = f % 2.0f;
        if (f2 < 0.375) {
            this.at = EnumEntitySize.SIZE_1;
        }
        else if (f2 < 0.75) {
            this.at = EnumEntitySize.SIZE_2;
        }
        else if (f2 < 1.0) {
            this.at = EnumEntitySize.SIZE_3;
        }
        else if (f2 < 1.375) {
            this.at = EnumEntitySize.SIZE_4;
        }
        else if (f2 < 1.75) {
            this.at = EnumEntitySize.SIZE_5;
        }
        else {
            this.at = EnumEntitySize.SIZE_6;
        }
    }
    
    protected void b(float f, float f1) {
        if (Float.isNaN(f)) {
            f = 0.0f;
        }
        if (f == Float.POSITIVE_INFINITY || f == Float.NEGATIVE_INFINITY) {
            if (this instanceof EntityPlayer) {
                this.world.getServer().getLogger().warning(((CraftPlayer)this.getBukkitEntity()).getName() + " was caught trying to crash the server with an invalid yaw");
                ((CraftPlayer)this.getBukkitEntity()).kickPlayer("Nope");
            }
            f = 0.0f;
        }
        if (Float.isNaN(f1)) {
            f1 = 0.0f;
        }
        if (f1 == Float.POSITIVE_INFINITY || f1 == Float.NEGATIVE_INFINITY) {
            if (this instanceof EntityPlayer) {
                this.world.getServer().getLogger().warning(((CraftPlayer)this.getBukkitEntity()).getName() + " was caught trying to crash the server with an invalid pitch");
                ((CraftPlayer)this.getBukkitEntity()).kickPlayer("Nope");
            }
            f1 = 0.0f;
        }
        this.yaw = f % 360.0f;
        this.pitch = f1 % 360.0f;
    }
    
    public void setPosition(final double d0, final double d1, final double d2) {
        this.locX = d0;
        this.locY = d1;
        this.locZ = d2;
        final float f = this.width / 2.0f;
        final float f2 = this.length;
        this.boundingBox.b(d0 - f, d1 - this.height + this.X, d2 - f, d0 + f, d1 - this.height + this.X + f2, d2 + f);
    }
    
    public void l_() {
        this.x();
    }
    
    public void x() {
        this.world.methodProfiler.a("entityBaseTick");
        if (this.vehicle != null && this.vehicle.dead) {
            this.vehicle = null;
        }
        this.Q = this.R;
        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        this.lastPitch = this.pitch;
        this.lastYaw = this.yaw;
        if (!this.world.isStatic && this.world instanceof WorldServer) {
            this.world.methodProfiler.a("portal");
            final MinecraftServer minecraftserver = ((WorldServer)this.world).getMinecraftServer();
            final int i = this.y();
            if (this.ap) {
                if (this.vehicle == null && this.aq++ >= i) {
                    this.aq = i;
                    this.portalCooldown = this.aa();
                    byte b0;
                    if (this.world.worldProvider.dimension == -1) {
                        b0 = 0;
                    }
                    else {
                        b0 = -1;
                    }
                    this.c(b0);
                }
                this.ap = false;
            }
            else {
                if (this.aq > 0) {
                    this.aq -= 4;
                }
                if (this.aq < 0) {
                    this.aq = 0;
                }
            }
            if (this.portalCooldown > 0) {
                --this.portalCooldown;
            }
            this.world.methodProfiler.b();
        }
        if (this.isSprinting() && !this.G()) {
            final int j = MathHelper.floor(this.locX);
            final int i = MathHelper.floor(this.locY - 0.20000000298023224 - this.height);
            final int k = MathHelper.floor(this.locZ);
            final int l = this.world.getTypeId(j, i, k);
            if (l > 0) {
                this.world.addParticle("tilecrack_" + l + "_" + this.world.getData(j, i, k), this.locX + (this.random.nextFloat() - 0.5) * this.width, this.boundingBox.b + 0.1, this.locZ + (this.random.nextFloat() - 0.5) * this.width, -this.motX * 4.0, 1.5, -this.motZ * 4.0);
            }
        }
        this.H();
        if (this.world.isStatic) {
            this.fireTicks = 0;
        }
        else if (this.fireTicks > 0) {
            if (this.fireProof) {
                this.fireTicks -= 4;
                if (this.fireTicks < 0) {
                    this.fireTicks = 0;
                }
            }
            else {
                if (this.fireTicks % 20 == 0) {
                    this.damageEntity(DamageSource.BURN, 1);
                }
                --this.fireTicks;
            }
        }
        if (this.I()) {
            this.z();
            this.fallDistance *= 0.5f;
        }
        if (this.locY < -64.0) {
            this.B();
        }
        if (!this.world.isStatic) {
            this.a(0, this.fireTicks > 0);
            this.a(2, this.vehicle != null);
        }
        this.justCreated = false;
        this.world.methodProfiler.b();
    }
    
    public int y() {
        return 0;
    }
    
    protected void z() {
        if (!this.fireProof) {
            if (this instanceof EntityLiving) {
                final Server server = this.world.getServer();
                final Block damager = null;
                final org.bukkit.entity.Entity damagee = this.getBukkitEntity();
                final EntityDamageByBlockEvent event = new EntityDamageByBlockEvent(damager, damagee, EntityDamageEvent.DamageCause.LAVA, 4);
                server.getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    damagee.setLastDamageCause(event);
                    this.damageEntity(DamageSource.LAVA, event.getDamage());
                }
                if (this.fireTicks <= 0) {
                    final EntityCombustEvent combustEvent = new EntityCombustByBlockEvent(damager, damagee, 15);
                    server.getPluginManager().callEvent(combustEvent);
                    if (!combustEvent.isCancelled()) {
                        this.setOnFire(combustEvent.getDuration());
                    }
                }
                else {
                    this.setOnFire(15);
                }
                return;
            }
            this.damageEntity(DamageSource.LAVA, 4);
            this.setOnFire(15);
        }
    }
    
    public void setOnFire(final int i) {
        int j = i * 20;
        j = EnchantmentProtection.a(this, j);
        if (this.fireTicks < j) {
            this.fireTicks = j;
        }
    }
    
    public void extinguish() {
        this.fireTicks = 0;
    }
    
    protected void B() {
        this.die();
    }
    
    public boolean c(final double d0, final double d1, final double d2) {
        final AxisAlignedBB axisalignedbb = this.boundingBox.c(d0, d1, d2);
        final List list = this.world.getCubes(this, axisalignedbb);
        return list.isEmpty() && !this.world.containsLiquid(axisalignedbb);
    }
    
    public void move(double d0, double d1, double d2) {
        if (d0 == 0.0 && d1 == 0.0 && d2 == 0.0 && this.vehicle == null && this.passenger == null) {
            return;
        }
        SpigotTimings.entityMoveTimer.startTiming();
        if (this.Z) {
            this.boundingBox.d(d0, d1, d2);
            this.locX = (this.boundingBox.a + this.boundingBox.d) / 2.0;
            this.locY = this.boundingBox.b + this.height - this.X;
            this.locZ = (this.boundingBox.c + this.boundingBox.f) / 2.0;
        }
        else {
            this.world.methodProfiler.a("move");
            this.X *= 0.4f;
            final double d3 = this.locX;
            final double d4 = this.locY;
            final double d5 = this.locZ;
            if (this.K) {
                this.K = false;
                d0 *= 0.25;
                d1 *= 0.05000000074505806;
                d2 *= 0.25;
                this.motX = 0.0;
                this.motY = 0.0;
                this.motZ = 0.0;
            }
            double d6 = d0;
            final double d7 = d1;
            double d8 = d2;
            final AxisAlignedBB axisalignedbb = this.boundingBox.clone();
            final boolean flag = this.onGround && this.isSneaking() && this instanceof EntityHuman;
            if (flag) {
                final double d9 = 0.05;
                while (d0 != 0.0 && this.world.getCubes(this, this.boundingBox.c(d0, -1.0, 0.0)).isEmpty()) {
                    if (d0 < d9 && d0 >= -d9) {
                        d0 = 0.0;
                    }
                    else if (d0 > 0.0) {
                        d0 -= d9;
                    }
                    else {
                        d0 += d9;
                    }
                    d6 = d0;
                }
                while (d2 != 0.0 && this.world.getCubes(this, this.boundingBox.c(0.0, -1.0, d2)).isEmpty()) {
                    if (d2 < d9 && d2 >= -d9) {
                        d2 = 0.0;
                    }
                    else if (d2 > 0.0) {
                        d2 -= d9;
                    }
                    else {
                        d2 += d9;
                    }
                    d8 = d2;
                }
                while (d0 != 0.0 && d2 != 0.0 && this.world.getCubes(this, this.boundingBox.c(d0, -1.0, d2)).isEmpty()) {
                    if (d0 < d9 && d0 >= -d9) {
                        d0 = 0.0;
                    }
                    else if (d0 > 0.0) {
                        d0 -= d9;
                    }
                    else {
                        d0 += d9;
                    }
                    if (d2 < d9 && d2 >= -d9) {
                        d2 = 0.0;
                    }
                    else if (d2 > 0.0) {
                        d2 -= d9;
                    }
                    else {
                        d2 += d9;
                    }
                    d6 = d0;
                    d8 = d2;
                }
            }
            List list = this.world.getCubes(this, this.boundingBox.a(d0, d1, d2));
            for (int i = 0; i < list.size(); ++i) {
                d1 = list.get(i).b(this.boundingBox, d1);
            }
            this.boundingBox.d(0.0, d1, 0.0);
            if (!this.L && d7 != d1) {
                d2 = 0.0;
                d1 = 0.0;
                d0 = 0.0;
            }
            final boolean flag2 = this.onGround || (d7 != d1 && d7 < 0.0);
            for (int j = 0; j < list.size(); ++j) {
                d0 = list.get(j).a(this.boundingBox, d0);
            }
            this.boundingBox.d(d0, 0.0, 0.0);
            if (!this.L && d6 != d0) {
                d2 = 0.0;
                d1 = 0.0;
                d0 = 0.0;
            }
            for (int j = 0; j < list.size(); ++j) {
                d2 = list.get(j).c(this.boundingBox, d2);
            }
            this.boundingBox.d(0.0, 0.0, d2);
            if (!this.L && d8 != d2) {
                d2 = 0.0;
                d1 = 0.0;
                d0 = 0.0;
            }
            if (this.Y > 0.0f && flag2 && (flag || this.X < 0.05f) && (d6 != d0 || d8 != d2)) {
                final double d10 = d0;
                final double d11 = d1;
                final double d12 = d2;
                d0 = d6;
                d1 = this.Y;
                d2 = d8;
                final AxisAlignedBB axisalignedbb2 = this.boundingBox.clone();
                this.boundingBox.c(axisalignedbb);
                list = this.world.getCubes(this, this.boundingBox.a(d6, d1, d8));
                for (int k = 0; k < list.size(); ++k) {
                    d1 = list.get(k).b(this.boundingBox, d1);
                }
                this.boundingBox.d(0.0, d1, 0.0);
                if (!this.L && d7 != d1) {
                    d2 = 0.0;
                    d1 = 0.0;
                    d0 = 0.0;
                }
                for (int k = 0; k < list.size(); ++k) {
                    d0 = list.get(k).a(this.boundingBox, d0);
                }
                this.boundingBox.d(d0, 0.0, 0.0);
                if (!this.L && d6 != d0) {
                    d2 = 0.0;
                    d1 = 0.0;
                    d0 = 0.0;
                }
                for (int k = 0; k < list.size(); ++k) {
                    d2 = list.get(k).c(this.boundingBox, d2);
                }
                this.boundingBox.d(0.0, 0.0, d2);
                if (!this.L && d8 != d2) {
                    d2 = 0.0;
                    d1 = 0.0;
                    d0 = 0.0;
                }
                if (!this.L && d7 != d1) {
                    d2 = 0.0;
                    d1 = 0.0;
                    d0 = 0.0;
                }
                else {
                    d1 = -this.Y;
                    for (int k = 0; k < list.size(); ++k) {
                        d1 = list.get(k).b(this.boundingBox, d1);
                    }
                    this.boundingBox.d(0.0, d1, 0.0);
                }
                if (d10 * d10 + d12 * d12 >= d0 * d0 + d2 * d2) {
                    d0 = d10;
                    d1 = d11;
                    d2 = d12;
                    this.boundingBox.c(axisalignedbb2);
                }
            }
            this.world.methodProfiler.b();
            this.world.methodProfiler.a("rest");
            this.locX = (this.boundingBox.a + this.boundingBox.d) / 2.0;
            this.locY = this.boundingBox.b + this.height - this.X;
            this.locZ = (this.boundingBox.c + this.boundingBox.f) / 2.0;
            this.positionChanged = (d6 != d0 || d8 != d2);
            this.H = (d7 != d1);
            this.onGround = (d7 != d1 && d7 < 0.0);
            this.I = (this.positionChanged || this.H);
            this.a(d1, this.onGround);
            if (d6 != d0) {
                this.motX = 0.0;
            }
            if (d7 != d1) {
                this.motY = 0.0;
            }
            if (d8 != d2) {
                this.motZ = 0.0;
            }
            final double d10 = this.locX - d3;
            double d11 = this.locY - d4;
            final double d12 = this.locZ - d5;
            if (this.positionChanged && this.getBukkitEntity() instanceof Vehicle) {
                final Vehicle vehicle = (Vehicle)this.getBukkitEntity();
                Block block = this.world.getWorld().getBlockAt(MathHelper.floor(this.locX), MathHelper.floor(this.locY - this.height), MathHelper.floor(this.locZ));
                if (d6 > d0) {
                    block = block.getRelative(BlockFace.EAST);
                }
                else if (d6 < d0) {
                    block = block.getRelative(BlockFace.WEST);
                }
                else if (d8 > d2) {
                    block = block.getRelative(BlockFace.SOUTH);
                }
                else if (d8 < d2) {
                    block = block.getRelative(BlockFace.NORTH);
                }
                final VehicleBlockCollisionEvent event = new VehicleBlockCollisionEvent(vehicle, block);
                this.world.getServer().getPluginManager().callEvent(event);
            }
            if (this.f_() && !flag && this.vehicle == null) {
                final int l = MathHelper.floor(this.locX);
                final int k = MathHelper.floor(this.locY - 0.20000000298023224 - this.height);
                final int i2 = MathHelper.floor(this.locZ);
                int j2 = this.world.getTypeId(l, k, i2);
                if (j2 == 0) {
                    final int k2 = this.world.e(l, k - 1, i2);
                    if (k2 == 11 || k2 == 32 || k2 == 21) {
                        j2 = this.world.getTypeId(l, k - 1, i2);
                    }
                }
                if (j2 != net.minecraft.server.v1_5_R3.Block.LADDER.id) {
                    d11 = 0.0;
                }
                this.R += (float)(MathHelper.sqrt(d10 * d10 + d12 * d12) * 0.6);
                this.S += (float)(MathHelper.sqrt(d10 * d10 + d11 * d11 + d12 * d12) * 0.6);
                if (this.S > this.c && j2 > 0) {
                    this.c = (int)this.S + 1;
                    if (this.G()) {
                        float f = MathHelper.sqrt(this.motX * this.motX * 0.20000000298023224 + this.motY * this.motY + this.motZ * this.motZ * 0.20000000298023224) * 0.35f;
                        if (f > 1.0f) {
                            f = 1.0f;
                        }
                        this.makeSound("liquid.swim", f, 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.4f);
                    }
                    this.a(l, k, i2, j2);
                    net.minecraft.server.v1_5_R3.Block.byId[j2].b(this.world, l, k, i2, this);
                }
            }
            this.C();
            final boolean flag3 = this.F();
            if (this.world.e(this.boundingBox.shrink(0.001, 0.001, 0.001))) {
                this.burn(1);
                if (!flag3) {
                    ++this.fireTicks;
                    if (this.fireTicks <= 0) {
                        final EntityCombustEvent event2 = new EntityCombustEvent(this.getBukkitEntity(), 8);
                        this.world.getServer().getPluginManager().callEvent(event2);
                        if (!event2.isCancelled()) {
                            this.setOnFire(event2.getDuration());
                        }
                    }
                    else {
                        this.setOnFire(8);
                    }
                }
            }
            else if (this.fireTicks <= 0) {
                this.fireTicks = -this.maxFireTicks;
            }
            if (flag3 && this.fireTicks > 0) {
                this.makeSound("random.fizz", 0.7f, 1.6f + (this.random.nextFloat() - this.random.nextFloat()) * 0.4f);
                this.fireTicks = -this.maxFireTicks;
            }
            this.world.methodProfiler.b();
        }
        SpigotTimings.entityMoveTimer.stopTiming();
    }
    
    protected void C() {
        final int i = MathHelper.floor(this.boundingBox.a + 0.001);
        final int j = MathHelper.floor(this.boundingBox.b + 0.001);
        final int k = MathHelper.floor(this.boundingBox.c + 0.001);
        final int l = MathHelper.floor(this.boundingBox.d - 0.001);
        final int i2 = MathHelper.floor(this.boundingBox.e - 0.001);
        final int j2 = MathHelper.floor(this.boundingBox.f - 0.001);
        if (this.world.e(i, j, k, l, i2, j2)) {
            for (int k2 = i; k2 <= l; ++k2) {
                for (int l2 = j; l2 <= i2; ++l2) {
                    for (int i3 = k; i3 <= j2; ++i3) {
                        final int j3 = this.world.getTypeId(k2, l2, i3);
                        if (j3 > 0) {
                            net.minecraft.server.v1_5_R3.Block.byId[j3].a(this.world, k2, l2, i3, this);
                        }
                    }
                }
            }
        }
    }
    
    protected void a(final int i, final int j, final int k, final int l) {
        StepSound stepsound = net.minecraft.server.v1_5_R3.Block.byId[l].stepSound;
        if (this.world.getTypeId(i, j + 1, k) == net.minecraft.server.v1_5_R3.Block.SNOW.id) {
            stepsound = net.minecraft.server.v1_5_R3.Block.SNOW.stepSound;
            this.makeSound(stepsound.getStepSound(), stepsound.getVolume1() * 0.15f, stepsound.getVolume2());
        }
        else if (!net.minecraft.server.v1_5_R3.Block.byId[l].material.isLiquid()) {
            this.makeSound(stepsound.getStepSound(), stepsound.getVolume1() * 0.15f, stepsound.getVolume2());
        }
    }
    
    public void makeSound(final String s, final float f, final float f1) {
        this.world.makeSound(this, s, f, f1);
    }
    
    protected boolean f_() {
        return true;
    }
    
    protected void a(final double d0, final boolean flag) {
        if (flag) {
            if (this.fallDistance > 0.0f) {
                this.a(this.fallDistance);
                this.fallDistance = 0.0f;
            }
        }
        else if (d0 < 0.0) {
            this.fallDistance -= (float)d0;
        }
    }
    
    public AxisAlignedBB D() {
        return null;
    }
    
    protected void burn(final int i) {
        if (!this.fireProof) {
            this.damageEntity(DamageSource.FIRE, i);
        }
    }
    
    public final boolean isFireproof() {
        return this.fireProof;
    }
    
    protected void a(final float f) {
        if (this.passenger != null) {
            this.passenger.a(f);
        }
    }
    
    public boolean F() {
        return this.inWater || this.world.F(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) || this.world.F(MathHelper.floor(this.locX), MathHelper.floor(this.locY + this.length), MathHelper.floor(this.locZ));
    }
    
    public boolean G() {
        return this.inWater;
    }
    
    public boolean H() {
        if (this.world.a(this.boundingBox.grow(0.0, -0.4000000059604645, 0.0).shrink(0.001, 0.001, 0.001), Material.WATER, this)) {
            if (!this.inWater && !this.justCreated) {
                float f = MathHelper.sqrt(this.motX * this.motX * 0.20000000298023224 + this.motY * this.motY + this.motZ * this.motZ * 0.20000000298023224) * 0.2f;
                if (f > 1.0f) {
                    f = 1.0f;
                }
                this.makeSound("liquid.splash", f, 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.4f);
                final float f2 = MathHelper.floor(this.boundingBox.b);
                for (int i = 0; i < 1.0f + this.width * 20.0f; ++i) {
                    final float f3 = (this.random.nextFloat() * 2.0f - 1.0f) * this.width;
                    final float f4 = (this.random.nextFloat() * 2.0f - 1.0f) * this.width;
                    this.world.addParticle("bubble", this.locX + f3, f2 + 1.0f, this.locZ + f4, this.motX, this.motY - this.random.nextFloat() * 0.2f, this.motZ);
                }
                for (int i = 0; i < 1.0f + this.width * 20.0f; ++i) {
                    final float f3 = (this.random.nextFloat() * 2.0f - 1.0f) * this.width;
                    final float f4 = (this.random.nextFloat() * 2.0f - 1.0f) * this.width;
                    this.world.addParticle("splash", this.locX + f3, f2 + 1.0f, this.locZ + f4, this.motX, this.motY, this.motZ);
                }
            }
            this.fallDistance = 0.0f;
            this.inWater = true;
            this.fireTicks = 0;
        }
        else {
            this.inWater = false;
        }
        return this.inWater;
    }
    
    public boolean a(final Material material) {
        final double d0 = this.locY + this.getHeadHeight();
        final int i = MathHelper.floor(this.locX);
        final int j = MathHelper.d(MathHelper.floor(d0));
        final int k = MathHelper.floor(this.locZ);
        final int l = this.world.getTypeId(i, j, k);
        if (l != 0 && net.minecraft.server.v1_5_R3.Block.byId[l].material == material) {
            final float f = BlockFluids.d(this.world.getData(i, j, k)) - 0.11111111f;
            final float f2 = j + 1 - f;
            return d0 < f2;
        }
        return false;
    }
    
    public float getHeadHeight() {
        return 0.0f;
    }
    
    public boolean I() {
        return this.world.a(this.boundingBox.grow(-0.10000000149011612, -0.4000000059604645, -0.10000000149011612), Material.LAVA);
    }
    
    public void a(float f, float f1, final float f2) {
        float f3 = f * f + f1 * f1;
        if (f3 >= 1.0E-4f) {
            f3 = MathHelper.c(f3);
            if (f3 < 1.0f) {
                f3 = 1.0f;
            }
            f3 = f2 / f3;
            f *= f3;
            f1 *= f3;
            final float f4 = MathHelper.sin(this.yaw * 3.1415927f / 180.0f);
            final float f5 = MathHelper.cos(this.yaw * 3.1415927f / 180.0f);
            this.motX += f * f5 - f1 * f4;
            this.motZ += f1 * f5 + f * f4;
        }
    }
    
    public float c(final float f) {
        final int i = MathHelper.floor(this.locX);
        final int j = MathHelper.floor(this.locZ);
        if (this.world.isLoaded(i, 0, j)) {
            final double d0 = (this.boundingBox.e - this.boundingBox.b) * 0.66;
            final int k = MathHelper.floor(this.locY - this.height + d0);
            return this.world.q(i, k, j);
        }
        return 0.0f;
    }
    
    public void spawnIn(final World world) {
        if (world == null) {
            this.die();
            this.world = Bukkit.getServer().getWorlds().get(0).getHandle();
            return;
        }
        this.world = world;
    }
    
    public void setLocation(final double d0, final double d1, final double d2, final float f, final float f1) {
        this.locX = d0;
        this.lastX = d0;
        this.locY = d1;
        this.lastY = d1;
        this.locZ = d2;
        this.lastZ = d2;
        this.yaw = f;
        this.lastYaw = f;
        this.pitch = f1;
        this.lastPitch = f1;
        this.X = 0.0f;
        final double d3 = this.lastYaw - f;
        if (d3 < -180.0) {
            this.lastYaw += 360.0f;
        }
        if (d3 >= 180.0) {
            this.lastYaw -= 360.0f;
        }
        this.setPosition(this.locX, this.locY, this.locZ);
        this.b(f, f1);
    }
    
    public void setPositionRotation(final double d0, final double d1, final double d2, final float f, final float f1) {
        this.locX = d0;
        this.lastX = d0;
        this.U = d0;
        final double v = d1 + this.height;
        this.locY = v;
        this.lastY = v;
        this.V = v;
        this.locZ = d2;
        this.lastZ = d2;
        this.W = d2;
        this.yaw = f;
        this.pitch = f1;
        this.setPosition(this.locX, this.locY, this.locZ);
    }
    
    public float d(final Entity entity) {
        final float f = (float)(this.locX - entity.locX);
        final float f2 = (float)(this.locY - entity.locY);
        final float f3 = (float)(this.locZ - entity.locZ);
        return MathHelper.c(f * f + f2 * f2 + f3 * f3);
    }
    
    public double e(final double d0, final double d1, final double d2) {
        final double d3 = this.locX - d0;
        final double d4 = this.locY - d1;
        final double d5 = this.locZ - d2;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }
    
    public double f(final double d0, final double d1, final double d2) {
        final double d3 = this.locX - d0;
        final double d4 = this.locY - d1;
        final double d5 = this.locZ - d2;
        return MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
    }
    
    public double e(final Entity entity) {
        final double d0 = this.locX - entity.locX;
        final double d2 = this.locY - entity.locY;
        final double d3 = this.locZ - entity.locZ;
        return d0 * d0 + d2 * d2 + d3 * d3;
    }
    
    public void b_(final EntityHuman entityhuman) {
    }
    
    public void collide(final Entity entity) {
        if (entity.passenger != this && entity.vehicle != this) {
            double d0 = entity.locX - this.locX;
            double d2 = entity.locZ - this.locZ;
            double d3 = MathHelper.a(d0, d2);
            if (d3 >= 0.009999999776482582) {
                d3 = MathHelper.sqrt(d3);
                d0 /= d3;
                d2 /= d3;
                double d4 = 1.0 / d3;
                if (d4 > 1.0) {
                    d4 = 1.0;
                }
                d0 *= d4;
                d2 *= d4;
                d0 *= 0.05000000074505806;
                d2 *= 0.05000000074505806;
                d0 *= 1.0f - this.aa;
                d2 *= 1.0f - this.aa;
                this.g(-d0, 0.0, -d2);
                entity.g(d0, 0.0, d2);
            }
        }
    }
    
    public void g(final double d0, final double d1, final double d2) {
        this.motX += d0;
        this.motY += d1;
        this.motZ += d2;
        this.an = true;
    }
    
    protected void J() {
        this.velocityChanged = true;
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        this.J();
        return false;
    }
    
    public boolean K() {
        return false;
    }
    
    public boolean L() {
        return false;
    }
    
    public void c(final Entity entity, final int i) {
    }
    
    public boolean c(final NBTTagCompound nbttagcompound) {
        final String s = this.P();
        if (!this.dead && s != null) {
            nbttagcompound.setString("id", s);
            this.e(nbttagcompound);
            return true;
        }
        return false;
    }
    
    public boolean d(final NBTTagCompound nbttagcompound) {
        final String s = this.P();
        if (!this.dead && s != null && this.passenger == null) {
            nbttagcompound.setString("id", s);
            this.e(nbttagcompound);
            return true;
        }
        return false;
    }
    
    public void e(final NBTTagCompound nbttagcompound) {
        try {
            nbttagcompound.set("Pos", this.a(this.locX, this.locY + this.X, this.locZ));
            nbttagcompound.set("Motion", this.a(this.motX, this.motY, this.motZ));
            if (Float.isNaN(this.yaw)) {
                this.yaw = 0.0f;
            }
            if (Float.isNaN(this.pitch)) {
                this.pitch = 0.0f;
            }
            nbttagcompound.set("Rotation", this.a(new float[] { this.yaw, this.pitch }));
            nbttagcompound.setFloat("FallDistance", this.fallDistance);
            nbttagcompound.setShort("Fire", (short)this.fireTicks);
            nbttagcompound.setShort("Air", (short)this.getAirTicks());
            nbttagcompound.setBoolean("OnGround", this.onGround);
            nbttagcompound.setInt("Dimension", this.dimension);
            nbttagcompound.setBoolean("Invulnerable", this.invulnerable);
            nbttagcompound.setInt("PortalCooldown", this.portalCooldown);
            nbttagcompound.setLong("UUIDMost", this.uniqueID.getMostSignificantBits());
            nbttagcompound.setLong("UUIDLeast", this.uniqueID.getLeastSignificantBits());
            nbttagcompound.setLong("WorldUUIDLeast", this.world.getDataManager().getUUID().getLeastSignificantBits());
            nbttagcompound.setLong("WorldUUIDMost", this.world.getDataManager().getUUID().getMostSignificantBits());
            nbttagcompound.setInt("Bukkit.updateLevel", 2);
            this.b(nbttagcompound);
            if (this.vehicle != null) {
                final NBTTagCompound nbttagcompound2 = new NBTTagCompound("Riding");
                if (this.vehicle.c(nbttagcompound2)) {
                    nbttagcompound.set("Riding", nbttagcompound2);
                }
            }
        }
        catch (Throwable throwable) {
            final CrashReport crashreport = CrashReport.a(throwable, "Saving entity NBT");
            final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being saved");
            this.a(crashreportsystemdetails);
            throw new ReportedException(crashreport);
        }
    }
    
    public void f(final NBTTagCompound nbttagcompound) {
        try {
            final NBTTagList nbttaglist = nbttagcompound.getList("Pos");
            final NBTTagList nbttaglist2 = nbttagcompound.getList("Motion");
            final NBTTagList nbttaglist3 = nbttagcompound.getList("Rotation");
            this.motX = ((NBTTagDouble)nbttaglist2.get(0)).data;
            this.motY = ((NBTTagDouble)nbttaglist2.get(1)).data;
            this.motZ = ((NBTTagDouble)nbttaglist2.get(2)).data;
            final double data = ((NBTTagDouble)nbttaglist.get(0)).data;
            this.locX = data;
            this.U = data;
            this.lastX = data;
            final double data2 = ((NBTTagDouble)nbttaglist.get(1)).data;
            this.locY = data2;
            this.V = data2;
            this.lastY = data2;
            final double data3 = ((NBTTagDouble)nbttaglist.get(2)).data;
            this.locZ = data3;
            this.W = data3;
            this.lastZ = data3;
            final float data4 = ((NBTTagFloat)nbttaglist3.get(0)).data;
            this.yaw = data4;
            this.lastYaw = data4;
            final float data5 = ((NBTTagFloat)nbttaglist3.get(1)).data;
            this.pitch = data5;
            this.lastPitch = data5;
            this.fallDistance = nbttagcompound.getFloat("FallDistance");
            this.fireTicks = nbttagcompound.getShort("Fire");
            this.setAirTicks(nbttagcompound.getShort("Air"));
            this.onGround = nbttagcompound.getBoolean("OnGround");
            this.dimension = nbttagcompound.getInt("Dimension");
            this.invulnerable = nbttagcompound.getBoolean("Invulnerable");
            this.portalCooldown = nbttagcompound.getInt("PortalCooldown");
            if (nbttagcompound.hasKey("UUIDMost") && nbttagcompound.hasKey("UUIDLeast")) {
                this.uniqueID = new UUID(nbttagcompound.getLong("UUIDMost"), nbttagcompound.getLong("UUIDLeast"));
            }
            this.setPosition(this.locX, this.locY, this.locZ);
            this.b(this.yaw, this.pitch);
            this.a(nbttagcompound);
            if (this instanceof EntityLiving) {
                final EntityLiving entity = (EntityLiving)this;
                if (!nbttagcompound.hasKey("Bukkit.MaxHealth")) {
                    entity.maxHealth = entity.getMaxHealth();
                }
                if (entity instanceof EntityTameableAnimal && !isLevelAtLeast(nbttagcompound, 2) && !nbttagcompound.getBoolean("PersistenceRequired")) {
                    entity.persistent = !entity.isTypeNotPersistent();
                }
            }
            if (!(this.getBukkitEntity() instanceof Vehicle)) {
                if (Math.abs(this.motX) > 10.0) {
                    this.motX = 0.0;
                }
                if (Math.abs(this.motY) > 10.0) {
                    this.motY = 0.0;
                }
                if (Math.abs(this.motZ) > 10.0) {
                    this.motZ = 0.0;
                }
            }
            if (this instanceof EntityPlayer) {
                final Server server = Bukkit.getServer();
                org.bukkit.World bworld = null;
                final String worldName = nbttagcompound.getString("World");
                if (nbttagcompound.hasKey("WorldUUIDMost") && nbttagcompound.hasKey("WorldUUIDLeast")) {
                    final UUID uid = new UUID(nbttagcompound.getLong("WorldUUIDMost"), nbttagcompound.getLong("WorldUUIDLeast"));
                    bworld = server.getWorld(uid);
                }
                else {
                    bworld = server.getWorld(worldName);
                }
                if (bworld == null) {
                    final EntityPlayer entityPlayer = (EntityPlayer)this;
                    bworld = ((CraftServer)server).getServer().getWorldServer(entityPlayer.dimension).getWorld();
                }
                this.spawnIn((bworld == null) ? null : ((CraftWorld)bworld).getHandle());
            }
        }
        catch (Throwable throwable) {
            final CrashReport crashreport = CrashReport.a(throwable, "Loading entity NBT");
            final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being loaded");
            this.a(crashreportsystemdetails);
            throw new ReportedException(crashreport);
        }
    }
    
    protected final String P() {
        return EntityTypes.b(this);
    }
    
    protected abstract void a(final NBTTagCompound p0);
    
    protected abstract void b(final NBTTagCompound p0);
    
    protected NBTTagList a(final double... adouble) {
        final NBTTagList nbttaglist = new NBTTagList();
        for (final double d0 : adouble) {
            nbttaglist.add(new NBTTagDouble(null, d0));
        }
        return nbttaglist;
    }
    
    protected NBTTagList a(final float... afloat) {
        final NBTTagList nbttaglist = new NBTTagList();
        for (final float f : afloat) {
            nbttaglist.add(new NBTTagFloat(null, f));
        }
        return nbttaglist;
    }
    
    public EntityItem b(final int i, final int j) {
        return this.a(i, j, 0.0f);
    }
    
    public EntityItem a(final int i, final int j, final float f) {
        return this.a(new ItemStack(i, j, 0), f);
    }
    
    public EntityItem a(final ItemStack itemstack, final float f) {
        final EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY + f, this.locZ, itemstack);
        entityitem.pickupDelay = 10;
        this.world.addEntity(entityitem);
        return entityitem;
    }
    
    public boolean isAlive() {
        return !this.dead;
    }
    
    public boolean inBlock() {
        for (int i = 0; i < 8; ++i) {
            final float f = ((i >> 0) % 2 - 0.5f) * this.width * 0.8f;
            final float f2 = ((i >> 1) % 2 - 0.5f) * 0.1f;
            final float f3 = ((i >> 2) % 2 - 0.5f) * this.width * 0.8f;
            final int j = MathHelper.floor(this.locX + f);
            final int k = MathHelper.floor(this.locY + this.getHeadHeight() + f2);
            final int l = MathHelper.floor(this.locZ + f3);
            if (this.world.u(j, k, l)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean a_(final EntityHuman entityhuman) {
        return false;
    }
    
    public AxisAlignedBB g(final Entity entity) {
        return null;
    }
    
    public void T() {
        if (this.vehicle.dead) {
            this.vehicle = null;
        }
        else {
            this.motX = 0.0;
            this.motY = 0.0;
            this.motZ = 0.0;
            this.l_();
            if (this.vehicle != null) {
                this.vehicle.U();
                this.g += this.vehicle.yaw - this.vehicle.lastYaw;
                this.f += this.vehicle.pitch - this.vehicle.lastPitch;
                while (this.g >= 180.0) {
                    this.g -= 360.0;
                }
                while (this.g < -180.0) {
                    this.g += 360.0;
                }
                while (this.f >= 180.0) {
                    this.f -= 360.0;
                }
                while (this.f < -180.0) {
                    this.f += 360.0;
                }
                double d0 = this.g * 0.5;
                double d2 = this.f * 0.5;
                final float f = 10.0f;
                if (d0 > f) {
                    d0 = f;
                }
                if (d0 < -f) {
                    d0 = -f;
                }
                if (d2 > f) {
                    d2 = f;
                }
                if (d2 < -f) {
                    d2 = -f;
                }
                this.g -= d0;
                this.f -= d2;
                this.yaw += (float)d0;
                this.pitch += (float)d2;
            }
        }
    }
    
    public void U() {
        if (this.passenger != null) {
            if (!(this.passenger instanceof EntityHuman) || !((EntityHuman)this.passenger).cg()) {
                this.passenger.U = this.U;
                this.passenger.V = this.V + this.W() + this.passenger.V();
                this.passenger.W = this.W;
            }
            this.passenger.setPosition(this.locX, this.locY + this.W() + this.passenger.V(), this.locZ);
        }
    }
    
    public double V() {
        return this.height;
    }
    
    public double W() {
        return this.length * 0.75;
    }
    
    public void mount(final Entity entity) {
        this.setPassengerOf(entity);
    }
    
    public CraftEntity getBukkitEntity() {
        if (this.bukkitEntity == null) {
            this.bukkitEntity = CraftEntity.getEntity(this.world.getServer(), this);
        }
        return this.bukkitEntity;
    }
    
    public void setPassengerOf(final Entity entity) {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        this.getBukkitEntity();
        this.f = 0.0;
        this.g = 0.0;
        if (entity == null) {
            if (this.vehicle != null) {
                if (this.bukkitEntity instanceof LivingEntity && this.vehicle.getBukkitEntity() instanceof Vehicle) {
                    final VehicleExitEvent event = new VehicleExitEvent((Vehicle)this.vehicle.getBukkitEntity(), (LivingEntity)this.bukkitEntity);
                    pluginManager.callEvent(event);
                }
                this.setPositionRotation(this.vehicle.locX, this.vehicle.boundingBox.b + this.vehicle.length, this.vehicle.locZ, this.yaw, this.pitch);
                this.vehicle.passenger = null;
            }
            this.vehicle = null;
        }
        else {
            if (this.bukkitEntity instanceof LivingEntity && entity.getBukkitEntity() instanceof Vehicle && entity.world.isChunkLoaded((int)entity.locX >> 4, (int)entity.locZ >> 4)) {
                final VehicleEnterEvent event2 = new VehicleEnterEvent((Vehicle)entity.getBukkitEntity(), this.bukkitEntity);
                pluginManager.callEvent(event2);
                if (event2.isCancelled()) {
                    return;
                }
            }
            if (this.vehicle != null) {
                this.vehicle.passenger = null;
            }
            this.vehicle = entity;
            entity.passenger = this;
        }
    }
    
    public void h(final Entity entity) {
        double d0 = this.locX;
        double d2 = this.locY;
        double d3 = this.locZ;
        if (entity != null) {
            d0 = entity.locX;
            d2 = entity.boundingBox.b + entity.length;
            d3 = entity.locZ;
        }
        for (double d4 = -1.5; d4 < 2.0; ++d4) {
            for (double d5 = -1.5; d5 < 2.0; ++d5) {
                if (d4 != 0.0 || d5 != 0.0) {
                    final int i = (int)(this.locX + d4);
                    final int j = (int)(this.locZ + d5);
                    final AxisAlignedBB axisalignedbb = this.boundingBox.c(d4, 1.0, d5);
                    if (this.world.a(axisalignedbb).isEmpty()) {
                        if (this.world.w(i, (int)this.locY, j)) {
                            this.setPositionRotation(this.locX + d4, this.locY + 1.0, this.locZ + d5, this.yaw, this.pitch);
                            return;
                        }
                        if (this.world.w(i, (int)this.locY - 1, j) || this.world.getMaterial(i, (int)this.locY - 1, j) == Material.WATER) {
                            d0 = this.locX + d4;
                            d2 = this.locY + 1.0;
                            d3 = this.locZ + d5;
                        }
                    }
                }
            }
        }
        this.setPositionRotation(d0, d2, d3, this.yaw, this.pitch);
    }
    
    public float X() {
        return 0.1f;
    }
    
    public Vec3D Y() {
        return null;
    }
    
    public void Z() {
        if (this.portalCooldown > 0) {
            this.portalCooldown = this.aa();
        }
        else {
            final double d0 = this.lastX - this.locX;
            final double d2 = this.lastZ - this.locZ;
            if (!this.world.isStatic && !this.ap) {
                this.as = Direction.a(d0, d2);
            }
            this.ap = true;
        }
    }
    
    public int aa() {
        return 900;
    }
    
    public ItemStack[] getEquipment() {
        return null;
    }
    
    public void setEquipment(final int i, final ItemStack itemstack) {
    }
    
    public boolean isBurning() {
        return this.fireTicks > 0 || this.f(0);
    }
    
    public boolean af() {
        return this.vehicle != null || this.f(2);
    }
    
    public boolean isSneaking() {
        return this.f(1);
    }
    
    public void setSneaking(final boolean flag) {
        this.a(1, flag);
    }
    
    public boolean isSprinting() {
        return this.f(3);
    }
    
    public void setSprinting(final boolean flag) {
        this.a(3, flag);
    }
    
    public boolean isInvisible() {
        return this.f(5);
    }
    
    public void setInvisible(final boolean flag) {
        this.a(5, flag);
    }
    
    public void e(final boolean flag) {
        this.a(4, flag);
    }
    
    protected boolean f(final int i) {
        return (this.datawatcher.getByte(0) & 1 << i) != 0x0;
    }
    
    protected void a(final int i, final boolean flag) {
        final byte b0 = this.datawatcher.getByte(0);
        if (flag) {
            this.datawatcher.watch(0, (byte)(b0 | 1 << i));
        }
        else {
            this.datawatcher.watch(0, (byte)(b0 & ~(1 << i)));
        }
    }
    
    public int getAirTicks() {
        return this.datawatcher.getShort(1);
    }
    
    public void setAirTicks(final int i) {
        this.datawatcher.watch(1, (short)i);
    }
    
    public void a(final EntityLightning entitylightning) {
        final org.bukkit.entity.Entity thisBukkitEntity = this.getBukkitEntity();
        final org.bukkit.entity.Entity stormBukkitEntity = entitylightning.getBukkitEntity();
        final PluginManager pluginManager = Bukkit.getPluginManager();
        if (thisBukkitEntity instanceof Painting) {
            final PaintingBreakByEntityEvent event = new PaintingBreakByEntityEvent((Painting)thisBukkitEntity, stormBukkitEntity);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                return;
            }
        }
        final EntityDamageEvent event2 = CraftEventFactory.callEntityDamageEvent(entitylightning, this, EntityDamageEvent.DamageCause.LIGHTNING, 5);
        if (event2.isCancelled()) {
            return;
        }
        this.burn(event2.getDamage());
        ++this.fireTicks;
        if (this.fireTicks == 0) {
            final EntityCombustByEntityEvent entityCombustEvent = new EntityCombustByEntityEvent(stormBukkitEntity, thisBukkitEntity, 8);
            pluginManager.callEvent(entityCombustEvent);
            if (!entityCombustEvent.isCancelled()) {
                this.setOnFire(entityCombustEvent.getDuration());
            }
        }
    }
    
    public void a(final EntityLiving entityliving) {
    }
    
    protected boolean i(final double d0, final double d1, final double d2) {
        final int i = MathHelper.floor(d0);
        final int j = MathHelper.floor(d1);
        final int k = MathHelper.floor(d2);
        final double d3 = d0 - i;
        final double d4 = d1 - j;
        final double d5 = d2 - k;
        final List list = this.world.a(this.boundingBox);
        if (list.isEmpty() && !this.world.v(i, j, k)) {
            return false;
        }
        final boolean flag = !this.world.v(i - 1, j, k);
        final boolean flag2 = !this.world.v(i + 1, j, k);
        final boolean flag3 = !this.world.v(i, j - 1, k);
        final boolean flag4 = !this.world.v(i, j + 1, k);
        final boolean flag5 = !this.world.v(i, j, k - 1);
        final boolean flag6 = !this.world.v(i, j, k + 1);
        byte b0 = 3;
        double d6 = 9999.0;
        if (flag && d3 < d6) {
            d6 = d3;
            b0 = 0;
        }
        if (flag2 && 1.0 - d3 < d6) {
            d6 = 1.0 - d3;
            b0 = 1;
        }
        if (flag4 && 1.0 - d4 < d6) {
            d6 = 1.0 - d4;
            b0 = 3;
        }
        if (flag5 && d5 < d6) {
            d6 = d5;
            b0 = 4;
        }
        if (flag6 && 1.0 - d5 < d6) {
            d6 = 1.0 - d5;
            b0 = 5;
        }
        final float f = this.random.nextFloat() * 0.2f + 0.1f;
        if (b0 == 0) {
            this.motX = -f;
        }
        if (b0 == 1) {
            this.motX = f;
        }
        if (b0 == 2) {
            this.motY = -f;
        }
        if (b0 == 3) {
            this.motY = f;
        }
        if (b0 == 4) {
            this.motZ = -f;
        }
        if (b0 == 5) {
            this.motZ = f;
        }
        return true;
    }
    
    public void al() {
        this.K = true;
        this.fallDistance = 0.0f;
    }
    
    public String getLocalizedName() {
        String s = EntityTypes.b(this);
        if (s == null) {
            s = "generic";
        }
        return LocaleI18n.get("entity." + s + ".name");
    }
    
    public Entity[] an() {
        return null;
    }
    
    public boolean i(final Entity entity) {
        return this == entity;
    }
    
    public float getHeadRotation() {
        return 0.0f;
    }
    
    public boolean ap() {
        return true;
    }
    
    public boolean j(final Entity entity) {
        return false;
    }
    
    public String toString() {
        return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", this.getClass().getSimpleName(), this.getLocalizedName(), this.id, (this.world == null) ? "~NULL~" : this.world.getWorldData().getName(), this.locX, this.locY, this.locZ);
    }
    
    public boolean isInvulnerable() {
        return this.invulnerable;
    }
    
    public void k(final Entity entity) {
        this.setPositionRotation(entity.locX, entity.locY, entity.locZ, entity.yaw, entity.pitch);
    }
    
    public void a(final Entity entity, final boolean flag) {
        final NBTTagCompound nbttagcompound = new NBTTagCompound();
        entity.e(nbttagcompound);
        this.f(nbttagcompound);
        this.portalCooldown = entity.portalCooldown;
        this.as = entity.as;
    }
    
    public void c(final int i) {
        if (!this.world.isStatic && !this.dead) {
            this.world.methodProfiler.a("changeDimension");
            final MinecraftServer minecraftserver = MinecraftServer.getServer();
            WorldServer exitWorld = null;
            if (this.dimension < 10) {
                for (final WorldServer world : minecraftserver.worlds) {
                    if (world.dimension == i) {
                        exitWorld = world;
                    }
                }
            }
            final Location enter = this.getBukkitEntity().getLocation();
            Location exit = (exitWorld != null) ? minecraftserver.getPlayerList().calculateTarget(enter, minecraftserver.getWorldServer(i)) : null;
            final boolean useTravelAgent = exitWorld != null && (this.dimension != 1 || exitWorld.dimension != 1);
            final TravelAgent agent = (TravelAgent)((exit != null) ? ((CraftWorld)exit.getWorld()).getHandle().t() : CraftTravelAgent.DEFAULT);
            final EntityPortalEvent event = new EntityPortalEvent(this.getBukkitEntity(), enter, exit, agent);
            event.useTravelAgent(useTravelAgent);
            event.getEntity().getServer().getPluginManager().callEvent(event);
            if (event.isCancelled() || event.getTo() == null || !this.isAlive()) {
                return;
            }
            exit = (event.useTravelAgent() ? event.getPortalTravelAgent().findOrCreate(event.getTo()) : event.getTo());
            this.teleportTo(exit, true);
        }
    }
    
    public void teleportTo(final Location exit, final boolean portal) {
        final WorldServer worldserver = ((CraftWorld)this.getBukkitEntity().getLocation().getWorld()).getHandle();
        final WorldServer worldserver2 = ((CraftWorld)exit.getWorld()).getHandle();
        final int i = worldserver2.dimension;
        this.dimension = i;
        this.world.kill(this);
        this.dead = false;
        this.world.methodProfiler.a("reposition");
        final boolean before = worldserver2.chunkProviderServer.forceChunkLoad;
        worldserver2.chunkProviderServer.forceChunkLoad = true;
        worldserver2.getMinecraftServer().getPlayerList().repositionEntity(this, exit, portal);
        worldserver2.chunkProviderServer.forceChunkLoad = before;
        this.world.methodProfiler.c("reloading");
        final Entity entity = EntityTypes.createEntityByName(EntityTypes.b(this), worldserver2);
        if (entity != null) {
            entity.a(this, true);
            worldserver2.addEntity(entity);
            this.getBukkitEntity().setHandle(entity);
            entity.bukkitEntity = this.getBukkitEntity();
        }
        this.dead = true;
        this.world.methodProfiler.b();
        worldserver.i();
        worldserver2.i();
        this.world.methodProfiler.b();
    }
    
    public float a(final Explosion explosion, final World world, final int i, final int j, final int k, final net.minecraft.server.v1_5_R3.Block block) {
        return block.a(this);
    }
    
    public boolean a(final Explosion explosion, final World world, final int i, final int j, final int k, final int l, final float f) {
        return true;
    }
    
    public int ar() {
        return 3;
    }
    
    public int as() {
        return this.as;
    }
    
    public boolean at() {
        return false;
    }
    
    public void a(final CrashReportSystemDetails crashreportsystemdetails) {
        crashreportsystemdetails.a("Entity Type", new CrashReportEntityType(this));
        crashreportsystemdetails.a("Entity ID", this.id);
        crashreportsystemdetails.a("Entity Name", new CrashReportEntityName(this));
        crashreportsystemdetails.a("Entity's Exact location", String.format("%.2f, %.2f, %.2f", this.locX, this.locY, this.locZ));
        crashreportsystemdetails.a("Entity's Block location", CrashReportSystemDetails.a(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)));
        crashreportsystemdetails.a("Entity's Momentum", String.format("%.2f, %.2f, %.2f", this.motX, this.motY, this.motZ));
    }
    
    public boolean aw() {
        return true;
    }
    
    public String getScoreboardDisplayName() {
        return this.getLocalizedName();
    }
    
    static {
        Entity.entityCount = 0;
    }
}
