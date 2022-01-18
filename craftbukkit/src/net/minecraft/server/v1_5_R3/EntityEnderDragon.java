// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.PortalType;
import org.bukkit.block.BlockState;
import java.util.Collections;
import org.bukkit.entity.LivingEntity;
import org.bukkit.craftbukkit.v1_5_R3.util.BlockStateListPopulator;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.block.Block;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityEnderDragon extends EntityLiving implements IComplex
{
    public double a;
    public double b;
    public double c;
    public double[][] d;
    public int e;
    public EntityComplexPart[] children;
    public EntityComplexPart g;
    public EntityComplexPart h;
    public EntityComplexPart i;
    public EntityComplexPart j;
    public EntityComplexPart bK;
    public EntityComplexPart bL;
    public EntityComplexPart bM;
    public float bN;
    public float bO;
    public boolean bP;
    public boolean bQ;
    private Entity bT;
    public int bR;
    public EntityEnderCrystal bS;
    private Explosion explosionSource;
    
    public EntityEnderDragon(final World world) {
        super(world);
        this.d = new double[64][3];
        this.e = -1;
        this.bN = 0.0f;
        this.bO = 0.0f;
        this.bP = false;
        this.bQ = false;
        this.bR = 0;
        this.bS = null;
        this.explosionSource = new Explosion(null, this, Double.NaN, Double.NaN, Double.NaN, Float.NaN);
        this.children = new EntityComplexPart[] { this.g = new EntityComplexPart(this, "head", 6.0f, 6.0f), this.h = new EntityComplexPart(this, "body", 8.0f, 8.0f), this.i = new EntityComplexPart(this, "tail", 4.0f, 4.0f), this.j = new EntityComplexPart(this, "tail", 4.0f, 4.0f), this.bK = new EntityComplexPart(this, "tail", 4.0f, 4.0f), this.bL = new EntityComplexPart(this, "wing", 4.0f, 4.0f), this.bM = new EntityComplexPart(this, "wing", 4.0f, 4.0f) };
        this.setHealth(this.getMaxHealth());
        this.texture = "/mob/enderdragon/ender.png";
        this.a(16.0f, 8.0f);
        this.Z = true;
        this.fireProof = true;
        this.b = 100.0;
        this.am = true;
    }
    
    public int getMaxHealth() {
        return 200;
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(16, new Integer(this.getMaxHealth()));
    }
    
    public double[] b(final int i, float f) {
        if (this.health <= 0) {
            f = 0.0f;
        }
        f = 1.0f - f;
        final int j = this.e - i * 1 & 0x3F;
        final int k = this.e - i * 1 - 1 & 0x3F;
        final double[] adouble = new double[3];
        double d0 = this.d[j][0];
        double d2 = MathHelper.g(this.d[k][0] - d0);
        adouble[0] = d0 + d2 * f;
        d0 = this.d[j][1];
        d2 = this.d[k][1] - d0;
        adouble[1] = d0 + d2 * f;
        adouble[2] = this.d[j][2] + (this.d[k][2] - this.d[j][2]) * f;
        return adouble;
    }
    
    public void c() {
        if (!this.world.isStatic) {
            this.datawatcher.watch(16, this.getScaledHealth());
        }
        else {
            final float f = MathHelper.cos(this.bO * 3.1415927f * 2.0f);
            final float f2 = MathHelper.cos(this.bN * 3.1415927f * 2.0f);
            if (f2 <= -0.3f && f >= -0.3f) {
                this.world.a(this.locX, this.locY, this.locZ, "mob.enderdragon.wings", 5.0f, 0.8f + this.random.nextFloat() * 0.3f, false);
            }
        }
        this.bN = this.bO;
        if (this.health <= 0) {
            final float f = (this.random.nextFloat() - 0.5f) * 8.0f;
            final float f2 = (this.random.nextFloat() - 0.5f) * 4.0f;
            final float f3 = (this.random.nextFloat() - 0.5f) * 8.0f;
            this.world.addParticle("largeexplode", this.locX + f, this.locY + 2.0 + f2, this.locZ + f3, 0.0, 0.0, 0.0);
        }
        else {
            this.h();
            float f = 0.2f / (MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 10.0f + 1.0f);
            f *= (float)Math.pow(2.0, this.motY);
            if (this.bQ) {
                this.bO += f * 0.5f;
            }
            else {
                this.bO += f;
            }
            this.yaw = MathHelper.g(this.yaw);
            if (this.e < 0) {
                for (int d05 = 0; d05 < this.d.length; ++d05) {
                    this.d[d05][0] = this.yaw;
                    this.d[d05][1] = this.locY;
                }
            }
            if (++this.e == this.d.length) {
                this.e = 0;
            }
            this.d[this.e][0] = this.yaw;
            this.d[this.e][1] = this.locY;
            if (this.world.isStatic) {
                if (this.bu > 0) {
                    final double d6 = this.locX + (this.bv - this.locX) / this.bu;
                    final double d7 = this.locY + (this.bw - this.locY) / this.bu;
                    final double d8 = this.locZ + (this.bx - this.locZ) / this.bu;
                    final double d9 = MathHelper.g(this.by - this.yaw);
                    this.yaw += (float)(d9 / this.bu);
                    this.pitch += (float)((this.bz - this.pitch) / this.bu);
                    --this.bu;
                    this.setPosition(d6, d7, d8);
                    this.b(this.yaw, this.pitch);
                }
            }
            else {
                final double d6 = this.a - this.locX;
                double d7 = this.b - this.locY;
                final double d8 = this.c - this.locZ;
                final double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                if (this.bT != null) {
                    this.a = this.bT.locX;
                    this.c = this.bT.locZ;
                    final double d10 = this.a - this.locX;
                    final double d11 = this.c - this.locZ;
                    final double d12 = Math.sqrt(d10 * d10 + d11 * d11);
                    double d13 = 0.4000000059604645 + d12 / 80.0 - 1.0;
                    if (d13 > 10.0) {
                        d13 = 10.0;
                    }
                    this.b = this.bT.boundingBox.b + d13;
                }
                else {
                    this.a += this.random.nextGaussian() * 2.0;
                    this.c += this.random.nextGaussian() * 2.0;
                }
                if (this.bP || d9 < 100.0 || d9 > 22500.0 || this.positionChanged || this.H) {
                    this.i();
                }
                d7 /= MathHelper.sqrt(d6 * d6 + d8 * d8);
                final float f4 = 0.6f;
                if (d7 < -f4) {
                    d7 = -f4;
                }
                if (d7 > f4) {
                    d7 = f4;
                }
                this.motY += d7 * 0.10000000149011612;
                this.yaw = MathHelper.g(this.yaw);
                final double d14 = 180.0 - Math.atan2(d6, d8) * 180.0 / 3.1415927410125732;
                double d15 = MathHelper.g(d14 - this.yaw);
                if (d15 > 50.0) {
                    d15 = 50.0;
                }
                if (d15 < -50.0) {
                    d15 = -50.0;
                }
                final Vec3D vec3d = this.world.getVec3DPool().create(this.a - this.locX, this.b - this.locY, this.c - this.locZ).a();
                final Vec3D vec3d2 = this.world.getVec3DPool().create(MathHelper.sin(this.yaw * 3.1415927f / 180.0f), this.motY, -MathHelper.cos(this.yaw * 3.1415927f / 180.0f)).a();
                float f5 = (float)(vec3d2.b(vec3d) + 0.5) / 1.5f;
                if (f5 < 0.0f) {
                    f5 = 0.0f;
                }
                this.bF *= 0.8f;
                final float f6 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 1.0f + 1.0f;
                double d16 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 1.0 + 1.0;
                if (d16 > 40.0) {
                    d16 = 40.0;
                }
                this.bF += (float)(d15 * (0.699999988079071 / d16 / f6));
                this.yaw += this.bF * 0.1f;
                final float f7 = (float)(2.0 / (d16 + 1.0));
                final float f8 = 0.06f;
                this.a(0.0f, -1.0f, f8 * (f5 * f7 + (1.0f - f7)));
                if (this.bQ) {
                    this.move(this.motX * 0.800000011920929, this.motY * 0.800000011920929, this.motZ * 0.800000011920929);
                }
                else {
                    this.move(this.motX, this.motY, this.motZ);
                }
                final Vec3D vec3d3 = this.world.getVec3DPool().create(this.motX, this.motY, this.motZ).a();
                float f9 = (float)(vec3d3.b(vec3d2) + 1.0) / 2.0f;
                f9 = 0.8f + 0.15f * f9;
                this.motX *= f9;
                this.motZ *= f9;
                this.motY *= 0.9100000262260437;
            }
            this.ay = this.yaw;
            final EntityComplexPart g = this.g;
            final EntityComplexPart g2 = this.g;
            final float n = 3.0f;
            g2.length = n;
            g.width = n;
            final EntityComplexPart i = this.i;
            final EntityComplexPart k = this.i;
            final float n2 = 2.0f;
            k.length = n2;
            i.width = n2;
            final EntityComplexPart l = this.j;
            final EntityComplexPart m = this.j;
            final float n3 = 2.0f;
            m.length = n3;
            l.width = n3;
            final EntityComplexPart bk = this.bK;
            final EntityComplexPart bk2 = this.bK;
            final float n4 = 2.0f;
            bk2.length = n4;
            bk.width = n4;
            this.h.length = 3.0f;
            this.h.width = 5.0f;
            this.bL.length = 2.0f;
            this.bL.width = 4.0f;
            this.bM.length = 3.0f;
            this.bM.width = 4.0f;
            final float f2 = (float)(this.b(5, 1.0f)[1] - this.b(10, 1.0f)[1]) * 10.0f / 180.0f * 3.1415927f;
            final float f3 = MathHelper.cos(f2);
            final float f10 = -MathHelper.sin(f2);
            final float f11 = this.yaw * 3.1415927f / 180.0f;
            final float f12 = MathHelper.sin(f11);
            final float f13 = MathHelper.cos(f11);
            this.h.l_();
            this.h.setPositionRotation(this.locX + f12 * 0.5f, this.locY, this.locZ - f13 * 0.5f, 0.0f, 0.0f);
            this.bL.l_();
            this.bL.setPositionRotation(this.locX + f13 * 4.5f, this.locY + 2.0, this.locZ + f12 * 4.5f, 0.0f, 0.0f);
            this.bM.l_();
            this.bM.setPositionRotation(this.locX - f13 * 4.5f, this.locY + 2.0, this.locZ - f12 * 4.5f, 0.0f, 0.0f);
            if (!this.world.isStatic && this.hurtTicks == 0) {
                this.a(this.world.getEntities(this, this.bL.boundingBox.grow(4.0, 2.0, 4.0).d(0.0, -2.0, 0.0)));
                this.a(this.world.getEntities(this, this.bM.boundingBox.grow(4.0, 2.0, 4.0).d(0.0, -2.0, 0.0)));
                this.b(this.world.getEntities(this, this.g.boundingBox.grow(1.0, 1.0, 1.0)));
            }
            final double[] adouble = this.b(5, 1.0f);
            final double[] adouble2 = this.b(0, 1.0f);
            final float f4 = MathHelper.sin(this.yaw * 3.1415927f / 180.0f - this.bF * 0.01f);
            final float f14 = MathHelper.cos(this.yaw * 3.1415927f / 180.0f - this.bF * 0.01f);
            this.g.l_();
            this.g.setPositionRotation(this.locX + f4 * 5.5f * f3, this.locY + (adouble2[1] - adouble[1]) * 1.0 + f10 * 5.5f, this.locZ - f14 * 5.5f * f3, 0.0f, 0.0f);
            for (int j = 0; j < 3; ++j) {
                EntityComplexPart entitycomplexpart = null;
                if (j == 0) {
                    entitycomplexpart = this.i;
                }
                if (j == 1) {
                    entitycomplexpart = this.j;
                }
                if (j == 2) {
                    entitycomplexpart = this.bK;
                }
                final double[] adouble3 = this.b(12 + j * 2, 1.0f);
                final float f15 = this.yaw * 3.1415927f / 180.0f + this.b(adouble3[0] - adouble[0]) * 3.1415927f / 180.0f * 1.0f;
                final float f16 = MathHelper.sin(f15);
                final float f17 = MathHelper.cos(f15);
                final float f18 = 1.5f;
                final float f19 = (j + 1) * 2.0f;
                entitycomplexpart.l_();
                entitycomplexpart.setPositionRotation(this.locX - (f12 * f18 + f16 * f19) * f3, this.locY + (adouble3[1] - adouble[1]) * 1.0 - (f19 + f18) * f10 + 1.5, this.locZ + (f13 * f18 + f17 * f19) * f3, 0.0f, 0.0f);
            }
            if (!this.world.isStatic) {
                this.bQ = (this.a(this.g.boundingBox) | this.a(this.h.boundingBox));
            }
        }
    }
    
    private void h() {
        if (this.bS != null) {
            if (this.bS.dead) {
                if (!this.world.isStatic) {
                    this.a(this.g, DamageSource.explosion(null), 10);
                }
                this.bS = null;
            }
            else if (this.ticksLived % 10 == 0 && this.health < this.maxHealth) {
                final EntityRegainHealthEvent event = new EntityRegainHealthEvent(this.getBukkitEntity(), 1, EntityRegainHealthEvent.RegainReason.ENDER_CRYSTAL);
                this.world.getServer().getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    this.setHealth(this.getHealth() + event.getAmount());
                }
            }
        }
        if (this.random.nextInt(10) == 0) {
            final float f = 32.0f;
            final List list = this.world.a(EntityEnderCrystal.class, this.boundingBox.grow(f, f, f));
            EntityEnderCrystal entityendercrystal = null;
            double d0 = Double.MAX_VALUE;
            for (final EntityEnderCrystal entityendercrystal2 : list) {
                final double d2 = entityendercrystal2.e(this);
                if (d2 < d0) {
                    d0 = d2;
                    entityendercrystal = entityendercrystal2;
                }
            }
            this.bS = entityendercrystal;
        }
    }
    
    private void a(final List list) {
        final double d0 = (this.h.boundingBox.a + this.h.boundingBox.d) / 2.0;
        final double d2 = (this.h.boundingBox.c + this.h.boundingBox.f) / 2.0;
        for (final Entity entity : list) {
            if (entity instanceof EntityLiving) {
                final double d3 = entity.locX - d0;
                final double d4 = entity.locZ - d2;
                final double d5 = d3 * d3 + d4 * d4;
                entity.g(d3 / d5 * 4.0, 0.20000000298023224, d4 / d5 * 4.0);
            }
        }
    }
    
    private void b(final List list) {
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity instanceof EntityLiving) {
                entity.damageEntity(DamageSource.mobAttack(this), 10);
            }
        }
    }
    
    private void i() {
        this.bP = false;
        if (this.random.nextInt(2) == 0 && !this.world.players.isEmpty()) {
            this.bT = this.world.players.get(this.random.nextInt(this.world.players.size()));
        }
        else {
            boolean flag = false;
            do {
                this.a = 0.0;
                this.b = 70.0f + this.random.nextFloat() * 50.0f;
                this.c = 0.0;
                this.a += this.random.nextFloat() * 120.0f - 60.0f;
                this.c += this.random.nextFloat() * 120.0f - 60.0f;
                final double d0 = this.locX - this.a;
                final double d2 = this.locY - this.b;
                final double d3 = this.locZ - this.c;
                flag = (d0 * d0 + d2 * d2 + d3 * d3 > 100.0);
            } while (!flag);
            this.bT = null;
        }
    }
    
    private float b(final double d0) {
        return (float)MathHelper.g(d0);
    }
    
    private boolean a(final AxisAlignedBB axisalignedbb) {
        final int i = MathHelper.floor(axisalignedbb.a);
        final int j = MathHelper.floor(axisalignedbb.b);
        final int k = MathHelper.floor(axisalignedbb.c);
        final int l = MathHelper.floor(axisalignedbb.d);
        final int i2 = MathHelper.floor(axisalignedbb.e);
        final int j2 = MathHelper.floor(axisalignedbb.f);
        boolean flag = false;
        boolean flag2 = false;
        final List<Block> destroyedBlocks = new ArrayList<Block>();
        final CraftWorld craftWorld = this.world.getWorld();
        for (int k2 = i; k2 <= l; ++k2) {
            for (int l2 = j; l2 <= i2; ++l2) {
                for (int i3 = k; i3 <= j2; ++i3) {
                    final int j3 = this.world.getTypeId(k2, l2, i3);
                    if (j3 != 0) {
                        if (j3 != net.minecraft.server.v1_5_R3.Block.OBSIDIAN.id && j3 != net.minecraft.server.v1_5_R3.Block.WHITESTONE.id && j3 != net.minecraft.server.v1_5_R3.Block.BEDROCK.id && this.world.getGameRules().getBoolean("mobGriefing")) {
                            flag2 = true;
                            destroyedBlocks.add(craftWorld.getBlockAt(k2, l2, i3));
                        }
                        else {
                            flag = true;
                        }
                    }
                }
            }
        }
        if (flag2) {
            final org.bukkit.entity.Entity bukkitEntity = this.getBukkitEntity();
            final EntityExplodeEvent event = new EntityExplodeEvent(bukkitEntity, bukkitEntity.getLocation(), destroyedBlocks, 0.0f);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return flag;
            }
            if (event.getYield() == 0.0f) {
                for (final Block block : event.blockList()) {
                    this.world.setAir(block.getX(), block.getY(), block.getZ());
                }
            }
            else {
                for (final Block block : event.blockList()) {
                    final int blockId = block.getTypeId();
                    if (blockId == 0) {
                        continue;
                    }
                    final int blockX = block.getX();
                    final int blockY = block.getY();
                    final int blockZ = block.getZ();
                    if (net.minecraft.server.v1_5_R3.Block.byId[blockId].a(this.explosionSource)) {
                        net.minecraft.server.v1_5_R3.Block.byId[blockId].dropNaturally(this.world, blockX, blockY, blockZ, block.getData(), event.getYield(), 0);
                    }
                    net.minecraft.server.v1_5_R3.Block.byId[blockId].wasExploded(this.world, blockX, blockY, blockZ, this.explosionSource);
                    this.world.setAir(blockX, blockY, blockZ);
                }
            }
            final double d0 = axisalignedbb.a + (axisalignedbb.d - axisalignedbb.a) * this.random.nextFloat();
            final double d2 = axisalignedbb.b + (axisalignedbb.e - axisalignedbb.b) * this.random.nextFloat();
            final double d3 = axisalignedbb.c + (axisalignedbb.f - axisalignedbb.c) * this.random.nextFloat();
            this.world.addParticle("largeexplode", d0, d2, d3, 0.0, 0.0, 0.0);
        }
        return flag;
    }
    
    public boolean a(final EntityComplexPart entitycomplexpart, final DamageSource damagesource, int i) {
        if (entitycomplexpart != this.g) {
            i = i / 4 + 1;
        }
        final float f = this.yaw * 3.1415927f / 180.0f;
        final float f2 = MathHelper.sin(f);
        final float f3 = MathHelper.cos(f);
        this.a = this.locX + f2 * 5.0f + (this.random.nextFloat() - 0.5f) * 2.0f;
        this.b = this.locY + this.random.nextFloat() * 3.0f + 1.0;
        this.c = this.locZ - f3 * 5.0f + (this.random.nextFloat() - 0.5f) * 2.0f;
        this.bT = null;
        if (damagesource.getEntity() instanceof EntityHuman || damagesource.c()) {
            this.dealDamage(damagesource, i);
        }
        return true;
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        return false;
    }
    
    public boolean dealDamage(final DamageSource damagesource, final int i) {
        return super.damageEntity(damagesource, i);
    }
    
    protected void aS() {
        ++this.bR;
        if (this.bR >= 180 && this.bR <= 200) {
            final float f = (this.random.nextFloat() - 0.5f) * 8.0f;
            final float f2 = (this.random.nextFloat() - 0.5f) * 4.0f;
            final float f3 = (this.random.nextFloat() - 0.5f) * 8.0f;
            this.world.addParticle("hugeexplosion", this.locX + f, this.locY + 2.0 + f2, this.locZ + f3, 0.0, 0.0, 0.0);
        }
        if (!this.world.isStatic) {
            if (this.bR > 150 && this.bR % 5 == 0) {
                int i = this.expToDrop / 12;
                while (i > 0) {
                    final int j = EntityExperienceOrb.getOrbValue(i);
                    i -= j;
                    this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
                }
            }
            if (this.bR == 1) {
                this.world.d(1018, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
            }
        }
        this.move(0.0, 0.10000000149011612, 0.0);
        final float n = this.yaw + 20.0f;
        this.yaw = n;
        this.ay = n;
        if (this.bR == 200 && !this.world.isStatic) {
            int i = this.expToDrop - 10 * (this.expToDrop / 12);
            while (i > 0) {
                final int j = EntityExperienceOrb.getOrbValue(i);
                i -= j;
                this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
            }
            this.c(MathHelper.floor(this.locX), MathHelper.floor(this.locZ));
            this.die();
        }
    }
    
    private void c(final int i, final int j) {
        final byte b0 = 64;
        BlockEnderPortal.a = true;
        final byte b2 = 4;
        final BlockStateListPopulator world = new BlockStateListPopulator(this.world.getWorld());
        for (int k = b0 - 1; k <= b0 + 32; ++k) {
            for (int l = i - b2; l <= i + b2; ++l) {
                for (int i2 = j - b2; i2 <= j + b2; ++i2) {
                    final double d0 = l - i;
                    final double d2 = i2 - j;
                    final double d3 = d0 * d0 + d2 * d2;
                    if (d3 <= (b2 - 0.5) * (b2 - 0.5)) {
                        if (k < b0) {
                            if (d3 <= (b2 - 1 - 0.5) * (b2 - 1 - 0.5)) {
                                world.setTypeId(l, k, i2, net.minecraft.server.v1_5_R3.Block.BEDROCK.id);
                            }
                        }
                        else if (k > b0) {
                            world.setTypeId(l, k, i2, 0);
                        }
                        else if (d3 > (b2 - 1 - 0.5) * (b2 - 1 - 0.5)) {
                            world.setTypeId(l, k, i2, net.minecraft.server.v1_5_R3.Block.BEDROCK.id);
                        }
                        else {
                            world.setTypeId(l, k, i2, net.minecraft.server.v1_5_R3.Block.ENDER_PORTAL.id);
                        }
                    }
                }
            }
        }
        world.setTypeId(i, b0 + 0, j, net.minecraft.server.v1_5_R3.Block.BEDROCK.id);
        world.setTypeId(i, b0 + 1, j, net.minecraft.server.v1_5_R3.Block.BEDROCK.id);
        world.setTypeId(i, b0 + 2, j, net.minecraft.server.v1_5_R3.Block.BEDROCK.id);
        world.setTypeId(i - 1, b0 + 2, j, net.minecraft.server.v1_5_R3.Block.TORCH.id);
        world.setTypeId(i + 1, b0 + 2, j, net.minecraft.server.v1_5_R3.Block.TORCH.id);
        world.setTypeId(i, b0 + 2, j - 1, net.minecraft.server.v1_5_R3.Block.TORCH.id);
        world.setTypeId(i, b0 + 2, j + 1, net.minecraft.server.v1_5_R3.Block.TORCH.id);
        world.setTypeId(i, b0 + 3, j, net.minecraft.server.v1_5_R3.Block.BEDROCK.id);
        world.setTypeId(i, b0 + 4, j, net.minecraft.server.v1_5_R3.Block.DRAGON_EGG.id);
        final EntityCreatePortalEvent event = new EntityCreatePortalEvent((LivingEntity)this.getBukkitEntity(), Collections.unmodifiableList((List<? extends BlockState>)world.getList()), PortalType.ENDER);
        this.world.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            for (final BlockState state : event.getBlocks()) {
                state.update(true);
            }
        }
        else {
            for (final BlockState state : event.getBlocks()) {
                final Packet53BlockChange packet = new Packet53BlockChange(state.getX(), state.getY(), state.getZ(), this.world);
                for (final EntityHuman entity : this.world.players) {
                    if (entity instanceof EntityPlayer) {
                        ((EntityPlayer)entity).playerConnection.sendPacket(packet);
                    }
                }
            }
        }
        BlockEnderPortal.a = false;
    }
    
    protected void bn() {
    }
    
    public Entity[] an() {
        return this.children;
    }
    
    public boolean K() {
        return false;
    }
    
    public World d() {
        return this.world;
    }
    
    protected String bb() {
        return "mob.enderdragon.growl";
    }
    
    protected String bc() {
        return "mob.enderdragon.hit";
    }
    
    protected float ba() {
        return 5.0f;
    }
    
    public int getExpReward() {
        return 12000;
    }
}
