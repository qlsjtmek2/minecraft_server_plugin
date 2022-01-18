// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.util.Vector;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import java.util.List;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.Location;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.Event;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.entity.Vehicle;

public abstract class EntityMinecartAbstract extends Entity
{
    private boolean a;
    private final IUpdatePlayerListBox b;
    private String c;
    private static final int[][][] matrix;
    private int e;
    private double f;
    private double g;
    private double h;
    private double i;
    private double j;
    public boolean slowWhenEmpty;
    private double derailedX;
    private double derailedY;
    private double derailedZ;
    private double flyingX;
    private double flyingY;
    private double flyingZ;
    public double maxSpeed;
    
    public EntityMinecartAbstract(final World world) {
        super(world);
        this.slowWhenEmpty = true;
        this.derailedX = 0.5;
        this.derailedY = 0.5;
        this.derailedZ = 0.5;
        this.flyingX = 0.95;
        this.flyingY = 0.95;
        this.flyingZ = 0.95;
        this.maxSpeed = 0.4;
        this.a = false;
        this.m = true;
        this.a(0.98f, 0.7f);
        this.height = this.length / 2.0f;
        this.b = ((world != null) ? world.a(this) : null);
    }
    
    public static EntityMinecartAbstract a(final World world, final double d0, final double d1, final double d2, final int i) {
        switch (i) {
            case 1: {
                return new EntityMinecartChest(world, d0, d1, d2);
            }
            case 2: {
                return new EntityMinecartFurnace(world, d0, d1, d2);
            }
            case 3: {
                return new EntityMinecartTNT(world, d0, d1, d2);
            }
            case 4: {
                return new EntityMinecartMobSpawner(world, d0, d1, d2);
            }
            case 5: {
                return new EntityMinecartHopper(world, d0, d1, d2);
            }
            default: {
                return new EntityMinecartRideable(world, d0, d1, d2);
            }
        }
    }
    
    protected boolean f_() {
        return false;
    }
    
    protected void a() {
        this.datawatcher.a(17, new Integer(0));
        this.datawatcher.a(18, new Integer(1));
        this.datawatcher.a(19, new Integer(0));
        this.datawatcher.a(20, new Integer(0));
        this.datawatcher.a(21, new Integer(6));
        this.datawatcher.a(22, (Object)(byte)0);
    }
    
    public AxisAlignedBB g(final Entity entity) {
        return entity.L() ? entity.boundingBox : null;
    }
    
    public AxisAlignedBB D() {
        return null;
    }
    
    public boolean L() {
        return true;
    }
    
    public EntityMinecartAbstract(final World world, final double d0, final double d1, final double d2) {
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
    
    public boolean damageEntity(final DamageSource damagesource, int i) {
        if (this.world.isStatic || this.dead) {
            return true;
        }
        if (this.isInvulnerable()) {
            return false;
        }
        final Vehicle vehicle = (Vehicle)this.getBukkitEntity();
        final org.bukkit.entity.Entity passenger = (damagesource.getEntity() == null) ? null : damagesource.getEntity().getBukkitEntity();
        final VehicleDamageEvent event = new VehicleDamageEvent(vehicle, passenger, i);
        this.world.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return true;
        }
        i = event.getDamage();
        this.j(-this.k());
        this.i(10);
        this.J();
        this.setDamage(this.getDamage() + i * 10);
        final boolean flag = damagesource.getEntity() instanceof EntityHuman && ((EntityHuman)damagesource.getEntity()).abilities.canInstantlyBuild;
        if (flag || this.getDamage() > 40) {
            if (this.passenger != null) {
                this.passenger.mount(this);
            }
            final VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, passenger);
            this.world.getServer().getPluginManager().callEvent(destroyEvent);
            if (destroyEvent.isCancelled()) {
                this.setDamage(40);
                return true;
            }
            if (flag && !this.c()) {
                this.die();
            }
            else {
                this.a(damagesource);
            }
        }
        return true;
    }
    
    public void a(final DamageSource damagesource) {
        this.die();
        final ItemStack itemstack = new ItemStack(Item.MINECART, 1);
        if (this.c != null) {
            itemstack.c(this.c);
        }
        this.a(itemstack, 0.0f);
    }
    
    public boolean K() {
        return !this.dead;
    }
    
    public void die() {
        super.die();
        if (this.b != null) {
            this.b.a();
        }
    }
    
    public void l_() {
        final double prevX = this.locX;
        final double prevY = this.locY;
        final double prevZ = this.locZ;
        final float prevYaw = this.yaw;
        final float prevPitch = this.pitch;
        if (this.b != null) {
            this.b.a();
        }
        if (this.j() > 0) {
            this.i(this.j() - 1);
        }
        if (this.getDamage() > 0) {
            this.setDamage(this.getDamage() - 1);
        }
        if (this.locY < -64.0) {
            this.B();
        }
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
        if (this.world.isStatic) {
            if (this.e > 0) {
                final double d0 = this.locX + (this.f - this.locX) / this.e;
                final double d2 = this.locY + (this.g - this.locY) / this.e;
                final double d3 = this.locZ + (this.h - this.locZ) / this.e;
                final double d4 = MathHelper.g(this.i - this.yaw);
                this.yaw += (float)(d4 / this.e);
                this.pitch += (float)((this.j - this.pitch) / this.e);
                --this.e;
                this.setPosition(d0, d2, d3);
                this.b(this.yaw, this.pitch);
            }
            else {
                this.setPosition(this.locX, this.locY, this.locZ);
                this.b(this.yaw, this.pitch);
            }
        }
        else {
            this.lastX = this.locX;
            this.lastY = this.locY;
            this.lastZ = this.locZ;
            this.motY -= 0.03999999910593033;
            final int j = MathHelper.floor(this.locX);
            int i = MathHelper.floor(this.locY);
            final int k = MathHelper.floor(this.locZ);
            if (BlockMinecartTrackAbstract.d_(this.world, j, i - 1, k)) {
                --i;
            }
            final double d5 = this.maxSpeed;
            final double d6 = 0.0078125;
            final int l = this.world.getTypeId(j, i, k);
            if (BlockMinecartTrackAbstract.d_(l)) {
                final int i2 = this.world.getData(j, i, k);
                this.a(j, i, k, d5, d6, l, i2);
                if (l == Block.ACTIVATOR_RAIL.id) {
                    this.a(j, i, k, (i2 & 0x8) != 0x0);
                }
            }
            else {
                this.b(d5);
            }
            this.C();
            this.pitch = 0.0f;
            final double d7 = this.lastX - this.locX;
            final double d8 = this.lastZ - this.locZ;
            if (d7 * d7 + d8 * d8 > 0.001) {
                this.yaw = (float)(Math.atan2(d8, d7) * 180.0 / 3.141592653589793);
                if (this.a) {
                    this.yaw += 180.0f;
                }
            }
            final double d9 = MathHelper.g(this.yaw - this.lastYaw);
            if (d9 < -170.0 || d9 >= 170.0) {
                this.yaw += 180.0f;
                this.a = !this.a;
            }
            this.b(this.yaw, this.pitch);
            final org.bukkit.World bworld = this.world.getWorld();
            final Location from = new Location(bworld, prevX, prevY, prevZ, prevYaw, prevPitch);
            final Location to = new Location(bworld, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            final Vehicle vehicle = (Vehicle)this.getBukkitEntity();
            this.world.getServer().getPluginManager().callEvent(new VehicleUpdateEvent(vehicle));
            if (!from.equals(to)) {
                this.world.getServer().getPluginManager().callEvent(new VehicleMoveEvent(vehicle, from, to));
            }
            final List list = this.world.getEntities(this, this.boundingBox.grow(0.20000000298023224, 0.0, 0.20000000298023224));
            if (list != null && !list.isEmpty()) {
                for (int j2 = 0; j2 < list.size(); ++j2) {
                    final Entity entity = list.get(j2);
                    if (entity != this.passenger && entity.L() && entity instanceof EntityMinecartAbstract) {
                        entity.collide(this);
                    }
                }
            }
            if (this.passenger != null && this.passenger.dead) {
                if (this.passenger.vehicle == this) {
                    this.passenger.vehicle = null;
                }
                this.passenger = null;
            }
        }
    }
    
    public void a(final int i, final int j, final int k, final boolean flag) {
    }
    
    protected void b(final double d0) {
        if (this.motX < -d0) {
            this.motX = -d0;
        }
        if (this.motX > d0) {
            this.motX = d0;
        }
        if (this.motZ < -d0) {
            this.motZ = -d0;
        }
        if (this.motZ > d0) {
            this.motZ = d0;
        }
        if (this.onGround) {
            this.motX *= this.derailedX;
            this.motY *= this.derailedY;
            this.motZ *= this.derailedZ;
        }
        this.move(this.motX, this.motY, this.motZ);
        if (!this.onGround) {
            this.motX *= this.flyingX;
            this.motY *= this.flyingY;
            this.motZ *= this.flyingZ;
        }
    }
    
    protected void a(final int i, final int j, final int k, final double d0, final double d1, final int l, int i1) {
        this.fallDistance = 0.0f;
        final Vec3D vec3d = this.a(this.locX, this.locY, this.locZ);
        this.locY = j;
        boolean flag = false;
        boolean flag2 = false;
        if (l == Block.GOLDEN_RAIL.id) {
            flag = ((i1 & 0x8) != 0x0);
            flag2 = !flag;
        }
        if (((BlockMinecartTrackAbstract)Block.byId[l]).e()) {
            i1 &= 0x7;
        }
        if (i1 >= 2 && i1 <= 5) {
            this.locY = j + 1;
        }
        if (i1 == 2) {
            this.motX -= d1;
        }
        if (i1 == 3) {
            this.motX += d1;
        }
        if (i1 == 4) {
            this.motZ += d1;
        }
        if (i1 == 5) {
            this.motZ -= d1;
        }
        final int[][] aint = EntityMinecartAbstract.matrix[i1];
        double d2 = aint[1][0] - aint[0][0];
        double d3 = aint[1][2] - aint[0][2];
        final double d4 = Math.sqrt(d2 * d2 + d3 * d3);
        final double d5 = this.motX * d2 + this.motZ * d3;
        if (d5 < 0.0) {
            d2 = -d2;
            d3 = -d3;
        }
        double d6 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        if (d6 > 2.0) {
            d6 = 2.0;
        }
        this.motX = d6 * d2 / d4;
        this.motZ = d6 * d3 / d4;
        if (this.passenger != null) {
            final double d7 = this.passenger.motX * this.passenger.motX + this.passenger.motZ * this.passenger.motZ;
            final double d8 = this.motX * this.motX + this.motZ * this.motZ;
            if (d7 > 1.0E-4 && d8 < 0.01) {
                this.motX += this.passenger.motX * 0.1;
                this.motZ += this.passenger.motZ * 0.1;
                flag2 = false;
            }
        }
        if (flag2) {
            final double d7 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            if (d7 < 0.03) {
                this.motX *= 0.0;
                this.motY *= 0.0;
                this.motZ *= 0.0;
            }
            else {
                this.motX *= 0.5;
                this.motY *= 0.0;
                this.motZ *= 0.5;
            }
        }
        double d7 = 0.0;
        final double d8 = i + 0.5 + aint[0][0] * 0.5;
        final double d9 = k + 0.5 + aint[0][2] * 0.5;
        final double d10 = i + 0.5 + aint[1][0] * 0.5;
        final double d11 = k + 0.5 + aint[1][2] * 0.5;
        d2 = d10 - d8;
        d3 = d11 - d9;
        if (d2 == 0.0) {
            this.locX = i + 0.5;
            d7 = this.locZ - k;
        }
        else if (d3 == 0.0) {
            this.locZ = k + 0.5;
            d7 = this.locX - i;
        }
        else {
            final double d12 = this.locX - d8;
            final double d13 = this.locZ - d9;
            d7 = (d12 * d2 + d13 * d3) * 2.0;
        }
        this.locX = d8 + d2 * d7;
        this.locZ = d9 + d3 * d7;
        this.setPosition(this.locX, this.locY + this.height, this.locZ);
        double d12 = this.motX;
        double d13 = this.motZ;
        if (this.passenger != null) {
            d12 *= 0.75;
            d13 *= 0.75;
        }
        if (d12 < -d0) {
            d12 = -d0;
        }
        if (d12 > d0) {
            d12 = d0;
        }
        if (d13 < -d0) {
            d13 = -d0;
        }
        if (d13 > d0) {
            d13 = d0;
        }
        this.move(d12, 0.0, d13);
        if (aint[0][1] != 0 && MathHelper.floor(this.locX) - i == aint[0][0] && MathHelper.floor(this.locZ) - k == aint[0][2]) {
            this.setPosition(this.locX, this.locY + aint[0][1], this.locZ);
        }
        else if (aint[1][1] != 0 && MathHelper.floor(this.locX) - i == aint[1][0] && MathHelper.floor(this.locZ) - k == aint[1][2]) {
            this.setPosition(this.locX, this.locY + aint[1][1], this.locZ);
        }
        this.h();
        final Vec3D vec3d2 = this.a(this.locX, this.locY, this.locZ);
        if (vec3d2 != null && vec3d != null) {
            final double d14 = (vec3d.d - vec3d2.d) * 0.05;
            d6 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            if (d6 > 0.0) {
                this.motX = this.motX / d6 * (d6 + d14);
                this.motZ = this.motZ / d6 * (d6 + d14);
            }
            this.setPosition(this.locX, vec3d2.d, this.locZ);
        }
        final int j2 = MathHelper.floor(this.locX);
        final int k2 = MathHelper.floor(this.locZ);
        if (j2 != i || k2 != k) {
            d6 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            this.motX = d6 * (j2 - i);
            this.motZ = d6 * (k2 - k);
        }
        if (flag) {
            final double d15 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            if (d15 > 0.01) {
                final double d16 = 0.06;
                this.motX += this.motX / d15 * d16;
                this.motZ += this.motZ / d15 * d16;
            }
            else if (i1 == 1) {
                if (this.world.u(i - 1, j, k)) {
                    this.motX = 0.02;
                }
                else if (this.world.u(i + 1, j, k)) {
                    this.motX = -0.02;
                }
            }
            else if (i1 == 0) {
                if (this.world.u(i, j, k - 1)) {
                    this.motZ = 0.02;
                }
                else if (this.world.u(i, j, k + 1)) {
                    this.motZ = -0.02;
                }
            }
        }
    }
    
    protected void h() {
        if (this.passenger != null || !this.slowWhenEmpty) {
            this.motX *= 0.996999979019165;
            this.motY *= 0.0;
            this.motZ *= 0.996999979019165;
        }
        else {
            this.motX *= 0.9599999785423279;
            this.motY *= 0.0;
            this.motZ *= 0.9599999785423279;
        }
    }
    
    public Vec3D a(double d0, double d1, double d2) {
        final int i = MathHelper.floor(d0);
        int j = MathHelper.floor(d1);
        final int k = MathHelper.floor(d2);
        if (BlockMinecartTrackAbstract.d_(this.world, i, j - 1, k)) {
            --j;
        }
        final int l = this.world.getTypeId(i, j, k);
        if (BlockMinecartTrackAbstract.d_(l)) {
            int i2 = this.world.getData(i, j, k);
            d1 = j;
            if (((BlockMinecartTrackAbstract)Block.byId[l]).e()) {
                i2 &= 0x7;
            }
            if (i2 >= 2 && i2 <= 5) {
                d1 = j + 1;
            }
            final int[][] aint = EntityMinecartAbstract.matrix[i2];
            double d3 = 0.0;
            final double d4 = i + 0.5 + aint[0][0] * 0.5;
            final double d5 = j + 0.5 + aint[0][1] * 0.5;
            final double d6 = k + 0.5 + aint[0][2] * 0.5;
            final double d7 = i + 0.5 + aint[1][0] * 0.5;
            final double d8 = j + 0.5 + aint[1][1] * 0.5;
            final double d9 = k + 0.5 + aint[1][2] * 0.5;
            final double d10 = d7 - d4;
            final double d11 = (d8 - d5) * 2.0;
            final double d12 = d9 - d6;
            if (d10 == 0.0) {
                d0 = i + 0.5;
                d3 = d2 - k;
            }
            else if (d12 == 0.0) {
                d2 = k + 0.5;
                d3 = d0 - i;
            }
            else {
                final double d13 = d0 - d4;
                final double d14 = d2 - d6;
                d3 = (d13 * d10 + d14 * d12) * 2.0;
            }
            d0 = d4 + d10 * d3;
            d1 = d5 + d11 * d3;
            d2 = d6 + d12 * d3;
            if (d11 < 0.0) {
                ++d1;
            }
            if (d11 > 0.0) {
                d1 += 0.5;
            }
            return this.world.getVec3DPool().create(d0, d1, d2);
        }
        return null;
    }
    
    protected void a(final NBTTagCompound nbttagcompound) {
        if (nbttagcompound.getBoolean("CustomDisplayTile")) {
            this.k(nbttagcompound.getInt("DisplayTile"));
            this.l(nbttagcompound.getInt("DisplayData"));
            this.m(nbttagcompound.getInt("DisplayOffset"));
        }
        if (nbttagcompound.hasKey("CustomName") && nbttagcompound.getString("CustomName").length() > 0) {
            this.c = nbttagcompound.getString("CustomName");
        }
    }
    
    protected void b(final NBTTagCompound nbttagcompound) {
        if (this.s()) {
            nbttagcompound.setBoolean("CustomDisplayTile", true);
            nbttagcompound.setInt("DisplayTile", (this.m() == null) ? 0 : this.m().id);
            nbttagcompound.setInt("DisplayData", this.o());
            nbttagcompound.setInt("DisplayOffset", this.q());
        }
        if (this.c != null && this.c.length() > 0) {
            nbttagcompound.setString("CustomName", this.c);
        }
    }
    
    public void collide(final Entity entity) {
        if (!this.world.isStatic && entity != this.passenger) {
            final Vehicle vehicle = (Vehicle)this.getBukkitEntity();
            final org.bukkit.entity.Entity hitEntity = (entity == null) ? null : entity.getBukkitEntity();
            final VehicleEntityCollisionEvent collisionEvent = new VehicleEntityCollisionEvent(vehicle, hitEntity);
            this.world.getServer().getPluginManager().callEvent(collisionEvent);
            if (collisionEvent.isCancelled()) {
                return;
            }
            if (entity instanceof EntityLiving && !(entity instanceof EntityHuman) && !(entity instanceof EntityIronGolem) && this.getType() == 0 && this.motX * this.motX + this.motZ * this.motZ > 0.01 && this.passenger == null && entity.vehicle == null) {
                entity.mount(this);
            }
            double d0 = entity.locX - this.locX;
            double d2 = entity.locZ - this.locZ;
            double d3 = d0 * d0 + d2 * d2;
            if (d3 >= 9.999999747378752E-5 && !collisionEvent.isCollisionCancelled()) {
                d3 = MathHelper.sqrt(d3);
                d0 /= d3;
                d2 /= d3;
                double d4 = 1.0 / d3;
                if (d4 > 1.0) {
                    d4 = 1.0;
                }
                d0 *= d4;
                d2 *= d4;
                d0 *= 0.10000000149011612;
                d2 *= 0.10000000149011612;
                d0 *= 1.0f - this.aa;
                d2 *= 1.0f - this.aa;
                d0 *= 0.5;
                d2 *= 0.5;
                if (entity instanceof EntityMinecartAbstract) {
                    final double d5 = entity.locX - this.locX;
                    final double d6 = entity.locZ - this.locZ;
                    final Vec3D vec3d = this.world.getVec3DPool().create(d5, 0.0, d6).a();
                    final Vec3D vec3d2 = this.world.getVec3DPool().create(MathHelper.cos(this.yaw * 3.1415927f / 180.0f), 0.0, MathHelper.sin(this.yaw * 3.1415927f / 180.0f)).a();
                    final double d7 = Math.abs(vec3d.b(vec3d2));
                    if (d7 < 0.800000011920929) {
                        return;
                    }
                    double d8 = entity.motX + this.motX;
                    double d9 = entity.motZ + this.motZ;
                    if (((EntityMinecartAbstract)entity).getType() == 2 && this.getType() != 2) {
                        this.motX *= 0.20000000298023224;
                        this.motZ *= 0.20000000298023224;
                        this.g(entity.motX - d0, 0.0, entity.motZ - d2);
                        entity.motX *= 0.949999988079071;
                        entity.motZ *= 0.949999988079071;
                    }
                    else if (((EntityMinecartAbstract)entity).getType() != 2 && this.getType() == 2) {
                        entity.motX *= 0.20000000298023224;
                        entity.motZ *= 0.20000000298023224;
                        entity.g(this.motX + d0, 0.0, this.motZ + d2);
                        this.motX *= 0.949999988079071;
                        this.motZ *= 0.949999988079071;
                    }
                    else {
                        d8 /= 2.0;
                        d9 /= 2.0;
                        this.motX *= 0.20000000298023224;
                        this.motZ *= 0.20000000298023224;
                        this.g(d8 - d0, 0.0, d9 - d2);
                        entity.motX *= 0.20000000298023224;
                        entity.motZ *= 0.20000000298023224;
                        entity.g(d8 + d0, 0.0, d9 + d2);
                    }
                }
                else {
                    this.g(-d0, 0.0, -d2);
                    entity.g(d0 / 4.0, 0.0, d2 / 4.0);
                }
            }
        }
    }
    
    public void setDamage(final int i) {
        this.datawatcher.watch(19, i);
    }
    
    public int getDamage() {
        return this.datawatcher.getInt(19);
    }
    
    public void i(final int i) {
        this.datawatcher.watch(17, i);
    }
    
    public int j() {
        return this.datawatcher.getInt(17);
    }
    
    public void j(final int i) {
        this.datawatcher.watch(18, i);
    }
    
    public int k() {
        return this.datawatcher.getInt(18);
    }
    
    public abstract int getType();
    
    public Block m() {
        if (!this.s()) {
            return this.n();
        }
        final int i = this.getDataWatcher().getInt(20) & 0xFFFF;
        return (i > 0 && i < Block.byId.length) ? Block.byId[i] : null;
    }
    
    public Block n() {
        return null;
    }
    
    public int o() {
        return this.s() ? (this.getDataWatcher().getInt(20) >> 16) : this.p();
    }
    
    public int p() {
        return 0;
    }
    
    public int q() {
        return this.s() ? this.getDataWatcher().getInt(21) : this.r();
    }
    
    public int r() {
        return 6;
    }
    
    public void k(final int i) {
        this.getDataWatcher().watch(20, (i & 0xFFFF) | this.o() << 16);
        this.a(true);
    }
    
    public void l(final int i) {
        final Block block = this.m();
        final int j = (block == null) ? 0 : block.id;
        this.getDataWatcher().watch(20, (j & 0xFFFF) | i << 16);
        this.a(true);
    }
    
    public void m(final int i) {
        this.getDataWatcher().watch(21, i);
        this.a(true);
    }
    
    public boolean s() {
        return this.getDataWatcher().getByte(22) == 1;
    }
    
    public void a(final boolean flag) {
        this.getDataWatcher().watch(22, (byte)(byte)(flag ? 1 : 0));
    }
    
    public void a(final String s) {
        this.c = s;
    }
    
    public String getLocalizedName() {
        return (this.c != null) ? this.c : super.getLocalizedName();
    }
    
    public boolean c() {
        return this.c != null;
    }
    
    public String t() {
        return this.c;
    }
    
    public Vector getFlyingVelocityMod() {
        return new Vector(this.flyingX, this.flyingY, this.flyingZ);
    }
    
    public void setFlyingVelocityMod(final Vector flying) {
        this.flyingX = flying.getX();
        this.flyingY = flying.getY();
        this.flyingZ = flying.getZ();
    }
    
    public Vector getDerailedVelocityMod() {
        return new Vector(this.derailedX, this.derailedY, this.derailedZ);
    }
    
    public void setDerailedVelocityMod(final Vector derailed) {
        this.derailedX = derailed.getX();
        this.derailedY = derailed.getY();
        this.derailedZ = derailed.getZ();
    }
    
    static {
        matrix = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };
    }
}
