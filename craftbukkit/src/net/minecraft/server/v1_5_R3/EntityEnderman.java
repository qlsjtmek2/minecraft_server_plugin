// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.ArrayList;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.Material;

public class EntityEnderman extends EntityMonster
{
    private static boolean[] d;
    private int e;
    private int f;
    private boolean g;
    
    public EntityEnderman(final World world) {
        super(world);
        this.e = 0;
        this.f = 0;
        this.texture = "/mob/enderman.png";
        this.bI = 0.2f;
        this.a(0.6f, 2.9f);
        this.Y = 1.0f;
    }
    
    public int getMaxHealth() {
        return 40;
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(16, new Byte((byte)0));
        this.datawatcher.a(17, new Byte((byte)0));
        this.datawatcher.a(18, new Byte((byte)0));
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setShort("carried", (short)this.getCarriedId());
        nbttagcompound.setShort("carriedData", (short)this.getCarriedData());
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setCarriedId(nbttagcompound.getShort("carried"));
        this.setCarriedData(nbttagcompound.getShort("carriedData"));
    }
    
    protected Entity findTarget() {
        final EntityHuman entityhuman = this.world.findNearbyVulnerablePlayer(this, 64.0);
        if (entityhuman != null) {
            if (this.e(entityhuman)) {
                this.g = true;
                if (this.f == 0) {
                    this.world.makeSound(entityhuman, "mob.endermen.stare", 1.0f, 1.0f);
                }
                if (this.f++ == 5) {
                    this.f = 0;
                    this.a(true);
                    return entityhuman;
                }
            }
            else {
                this.f = 0;
            }
        }
        return null;
    }
    
    private boolean e(final EntityHuman entityhuman) {
        final ItemStack itemstack = entityhuman.inventory.armor[3];
        if (itemstack != null && itemstack.id == Block.PUMPKIN.id) {
            return false;
        }
        final Vec3D vec3d = entityhuman.i(1.0f).a();
        Vec3D vec3d2 = this.world.getVec3DPool().create(this.locX - entityhuman.locX, this.boundingBox.b + this.length / 2.0f - (entityhuman.locY + entityhuman.getHeadHeight()), this.locZ - entityhuman.locZ);
        final double d0 = vec3d2.b();
        vec3d2 = vec3d2.a();
        final double d2 = vec3d.b(vec3d2);
        return d2 > 1.0 - 0.025 / d0 && entityhuman.n(this);
    }
    
    public void c() {
        if (this.F()) {
            this.damageEntity(DamageSource.DROWN, 1);
        }
        this.bI = ((this.target != null) ? 6.5f : 0.3f);
        if (!this.world.isStatic && this.world.getGameRules().getBoolean("mobGriefing")) {
            if (this.getCarriedId() == 0) {
                if (this.random.nextInt(20) == 0) {
                    final int i = MathHelper.floor(this.locX - 2.0 + this.random.nextDouble() * 4.0);
                    final int j = MathHelper.floor(this.locY + this.random.nextDouble() * 3.0);
                    final int k = MathHelper.floor(this.locZ - 2.0 + this.random.nextDouble() * 4.0);
                    final int l = this.world.getTypeId(i, j, k);
                    if (EntityEnderman.d[l] && !CraftEventFactory.callEntityChangeBlockEvent(this, this.world.getWorld().getBlockAt(i, j, k), Material.AIR).isCancelled()) {
                        this.setCarriedId(this.world.getTypeId(i, j, k));
                        this.setCarriedData(this.world.getData(i, j, k));
                        this.world.setTypeIdUpdate(i, j, k, 0);
                    }
                }
            }
            else if (this.random.nextInt(2000) == 0) {
                final int i = MathHelper.floor(this.locX - 1.0 + this.random.nextDouble() * 2.0);
                final int j = MathHelper.floor(this.locY + this.random.nextDouble() * 2.0);
                final int k = MathHelper.floor(this.locZ - 1.0 + this.random.nextDouble() * 2.0);
                final int l = this.world.getTypeId(i, j, k);
                final int i2 = this.world.getTypeId(i, j - 1, k);
                if (l == 0 && i2 > 0 && Block.byId[i2].b() && !CraftEventFactory.callEntityChangeBlockEvent(this, i, j, k, this.getCarriedId(), this.getCarriedData()).isCancelled()) {
                    this.world.setTypeIdAndData(i, j, k, this.getCarriedId(), this.getCarriedData(), 3);
                    this.setCarriedId(0);
                }
            }
        }
        for (int i = 0; i < 2; ++i) {
            this.world.addParticle("portal", this.locX + (this.random.nextDouble() - 0.5) * this.width, this.locY + this.random.nextDouble() * this.length - 0.25, this.locZ + (this.random.nextDouble() - 0.5) * this.width, (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
        }
        if (this.world.v() && !this.world.isStatic) {
            final float f = this.c(1.0f);
            if (f > 0.5f && this.world.l(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) && this.random.nextFloat() * 30.0f < (f - 0.4f) * 2.0f) {
                this.target = null;
                this.a(false);
                this.g = false;
                this.m();
            }
        }
        if (this.F() || this.isBurning()) {
            this.target = null;
            this.a(false);
            this.g = false;
            this.m();
        }
        if (this.q() && !this.g && this.random.nextInt(100) == 0) {
            this.a(false);
        }
        this.bG = false;
        if (this.target != null) {
            this.a(this.target, 100.0f, 100.0f);
        }
        if (!this.world.isStatic && this.isAlive()) {
            if (this.target != null) {
                if (this.target instanceof EntityHuman && this.e((EntityHuman)this.target)) {
                    final float n = 0.0f;
                    this.bE = n;
                    this.bD = n;
                    this.bI = 0.0f;
                    if (this.target.e(this) < 16.0) {
                        this.m();
                    }
                    this.e = 0;
                }
                else if (this.target.e(this) > 256.0 && this.e++ >= 30 && this.p(this.target)) {
                    this.e = 0;
                }
            }
            else {
                this.a(false);
                this.e = 0;
            }
        }
        super.c();
    }
    
    protected boolean m() {
        final double d0 = this.locX + (this.random.nextDouble() - 0.5) * 64.0;
        final double d2 = this.locY + (this.random.nextInt(64) - 32);
        final double d3 = this.locZ + (this.random.nextDouble() - 0.5) * 64.0;
        return this.j(d0, d2, d3);
    }
    
    protected boolean p(final Entity entity) {
        Vec3D vec3d = this.world.getVec3DPool().create(this.locX - entity.locX, this.boundingBox.b + this.length / 2.0f - entity.locY + entity.getHeadHeight(), this.locZ - entity.locZ);
        vec3d = vec3d.a();
        final double d0 = 16.0;
        final double d2 = this.locX + (this.random.nextDouble() - 0.5) * 8.0 - vec3d.c * d0;
        final double d3 = this.locY + (this.random.nextInt(16) - 8) - vec3d.d * d0;
        final double d4 = this.locZ + (this.random.nextDouble() - 0.5) * 8.0 - vec3d.e * d0;
        return this.j(d2, d3, d4);
    }
    
    protected boolean j(final double d0, final double d1, final double d2) {
        final double d3 = this.locX;
        final double d4 = this.locY;
        final double d5 = this.locZ;
        this.locX = d0;
        this.locY = d1;
        this.locZ = d2;
        boolean flag = false;
        final int i = MathHelper.floor(this.locX);
        int j = MathHelper.floor(this.locY);
        final int k = MathHelper.floor(this.locZ);
        if (this.world.isLoaded(i, j, k)) {
            boolean flag2 = false;
            while (!flag2 && j > 0) {
                final int l = this.world.getTypeId(i, j - 1, k);
                if (l != 0 && Block.byId[l].material.isSolid()) {
                    flag2 = true;
                }
                else {
                    --this.locY;
                    --j;
                }
            }
            if (flag2) {
                final EntityTeleportEvent teleport = new EntityTeleportEvent(this.getBukkitEntity(), new Location(this.world.getWorld(), d3, d4, d5), new Location(this.world.getWorld(), this.locX, this.locY, this.locZ));
                this.world.getServer().getPluginManager().callEvent(teleport);
                if (teleport.isCancelled()) {
                    return false;
                }
                final Location to = teleport.getTo();
                this.setPosition(to.getX(), to.getY(), to.getZ());
                if (this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox)) {
                    flag = true;
                }
            }
        }
        if (!flag) {
            this.setPosition(d3, d4, d5);
            return false;
        }
        int l;
        short short1;
        double d6;
        float f;
        float f2;
        float f3;
        double d7;
        double d8;
        double d9;
        for (short1 = 128, l = 0; l < short1; ++l) {
            d6 = l / (short1 - 1.0);
            f = (this.random.nextFloat() - 0.5f) * 0.2f;
            f2 = (this.random.nextFloat() - 0.5f) * 0.2f;
            f3 = (this.random.nextFloat() - 0.5f) * 0.2f;
            d7 = d3 + (this.locX - d3) * d6 + (this.random.nextDouble() - 0.5) * this.width * 2.0;
            d8 = d4 + (this.locY - d4) * d6 + this.random.nextDouble() * this.length;
            d9 = d5 + (this.locZ - d5) * d6 + (this.random.nextDouble() - 0.5) * this.width * 2.0;
            this.world.addParticle("portal", d7, d8, d9, f, f2, f3);
        }
        this.world.makeSound(d3, d4, d5, "mob.endermen.portal", 1.0f, 1.0f);
        this.makeSound("mob.endermen.portal", 1.0f, 1.0f);
        return true;
    }
    
    protected String bb() {
        return this.q() ? "mob.endermen.scream" : "mob.endermen.idle";
    }
    
    protected String bc() {
        return "mob.endermen.hit";
    }
    
    protected String bd() {
        return "mob.endermen.death";
    }
    
    protected int getLootId() {
        return Item.ENDER_PEARL.id;
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final int j = this.getLootId();
        if (j > 0) {
            final List<org.bukkit.inventory.ItemStack> loot = new ArrayList<org.bukkit.inventory.ItemStack>();
            final int count = this.random.nextInt(2 + i);
            if (j > 0 && count > 0) {
                loot.add(new org.bukkit.inventory.ItemStack(j, count));
            }
            CraftEventFactory.callEntityDeathEvent(this, loot);
        }
    }
    
    public void setCarriedId(final int i) {
        this.datawatcher.watch(16, (byte)(i & 0xFF));
    }
    
    public int getCarriedId() {
        return this.datawatcher.getByte(16);
    }
    
    public void setCarriedData(final int i) {
        this.datawatcher.watch(17, (byte)(i & 0xFF));
    }
    
    public int getCarriedData() {
        return this.datawatcher.getByte(17);
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        this.a(true);
        if (damagesource instanceof EntityDamageSource && damagesource.getEntity() instanceof EntityHuman) {
            this.g = true;
        }
        if (damagesource instanceof EntityDamageSourceIndirect) {
            this.g = false;
            for (int j = 0; j < 64; ++j) {
                if (this.m()) {
                    return true;
                }
            }
            return false;
        }
        return super.damageEntity(damagesource, i);
    }
    
    public boolean q() {
        return this.datawatcher.getByte(18) > 0;
    }
    
    public void a(final boolean flag) {
        this.datawatcher.watch(18, (byte)(byte)(flag ? 1 : 0));
    }
    
    public int c(final Entity entity) {
        return 7;
    }
    
    static {
        (EntityEnderman.d = new boolean[256])[Block.GRASS.id] = true;
        EntityEnderman.d[Block.DIRT.id] = true;
        EntityEnderman.d[Block.SAND.id] = true;
        EntityEnderman.d[Block.GRAVEL.id] = true;
        EntityEnderman.d[Block.YELLOW_FLOWER.id] = true;
        EntityEnderman.d[Block.RED_ROSE.id] = true;
        EntityEnderman.d[Block.BROWN_MUSHROOM.id] = true;
        EntityEnderman.d[Block.RED_MUSHROOM.id] = true;
        EntityEnderman.d[Block.TNT.id] = true;
        EntityEnderman.d[Block.CACTUS.id] = true;
        EntityEnderman.d[Block.CLAY.id] = true;
        EntityEnderman.d[Block.PUMPKIN.id] = true;
        EntityEnderman.d[Block.MELON.id] = true;
        EntityEnderman.d[Block.MYCEL.id] = true;
    }
}
