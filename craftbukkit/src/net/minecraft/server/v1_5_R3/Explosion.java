// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.spigotmc.OrebfuscatorManager;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Random;

public class Explosion
{
    public boolean a;
    public boolean b;
    private int i;
    private Random j;
    private World world;
    public double posX;
    public double posY;
    public double posZ;
    public Entity source;
    public float size;
    public List blocks;
    private Map l;
    public boolean wasCanceled;
    
    public Explosion(final World world, final Entity entity, final double d0, final double d1, final double d2, final float f) {
        this.a = false;
        this.b = true;
        this.i = 16;
        this.j = new Random();
        this.blocks = new ArrayList();
        this.l = new HashMap();
        this.wasCanceled = false;
        this.world = world;
        this.source = entity;
        this.size = (float)Math.max(f, 0.0);
        this.posX = d0;
        this.posY = d1;
        this.posZ = d2;
    }
    
    public void a() {
        if (this.size < 0.1f) {
            return;
        }
        final float f = this.size;
        final HashSet hashset = new HashSet();
        for (int i = 0; i < this.i; ++i) {
            for (int j = 0; j < this.i; ++j) {
                for (int k = 0; k < this.i; ++k) {
                    if (i == 0 || i == this.i - 1 || j == 0 || j == this.i - 1 || k == 0 || k == this.i - 1) {
                        double d3 = i / (this.i - 1.0f) * 2.0f - 1.0f;
                        double d4 = j / (this.i - 1.0f) * 2.0f - 1.0f;
                        double d5 = k / (this.i - 1.0f) * 2.0f - 1.0f;
                        final double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                        d3 /= d6;
                        d4 /= d6;
                        d5 /= d6;
                        float f2 = this.size * (0.7f + this.world.random.nextFloat() * 0.6f);
                        double d7 = this.posX;
                        double d8 = this.posY;
                        double d9 = this.posZ;
                        for (float f3 = 0.3f; f2 > 0.0f; f2 -= f3 * 0.75f) {
                            final int l = MathHelper.floor(d7);
                            final int i2 = MathHelper.floor(d8);
                            final int j2 = MathHelper.floor(d9);
                            final int k2 = this.world.getTypeId(l, i2, j2);
                            if (k2 > 0) {
                                final Block block = Block.byId[k2];
                                final float f4 = (this.source != null) ? this.source.a(this, this.world, l, i2, j2, block) : block.a(this.source);
                                f2 -= (f4 + 0.3f) * f3;
                            }
                            if (f2 > 0.0f && (this.source == null || this.source.a(this, this.world, l, i2, j2, k2, f2)) && i2 < 256 && i2 >= 0) {
                                hashset.add(new ChunkPosition(l, i2, j2));
                            }
                            d7 += d3 * f3;
                            d8 += d4 * f3;
                            d9 += d5 * f3;
                        }
                    }
                }
            }
        }
        this.blocks.addAll(hashset);
        this.size *= 2.0f;
        int i = MathHelper.floor(this.posX - this.size - 1.0);
        int j = MathHelper.floor(this.posX + this.size + 1.0);
        int k = MathHelper.floor(this.posY - this.size - 1.0);
        final int l2 = MathHelper.floor(this.posY + this.size + 1.0);
        final int i3 = MathHelper.floor(this.posZ - this.size - 1.0);
        final int j3 = MathHelper.floor(this.posZ + this.size + 1.0);
        final List list = this.world.getEntities(this.source, AxisAlignedBB.a().a(i, k, i3, j, l2, j3));
        final Vec3D vec3d = this.world.getVec3DPool().create(this.posX, this.posY, this.posZ);
        for (int k3 = 0; k3 < list.size(); ++k3) {
            final Entity entity = list.get(k3);
            final double d10 = entity.f(this.posX, this.posY, this.posZ) / this.size;
            if (d10 <= 1.0) {
                double d7 = entity.locX - this.posX;
                double d8 = entity.locY + entity.getHeadHeight() - this.posY;
                double d9 = entity.locZ - this.posZ;
                final double d11 = MathHelper.sqrt(d7 * d7 + d8 * d8 + d9 * d9);
                if (d11 != 0.0) {
                    d7 /= d11;
                    d8 /= d11;
                    d9 /= d11;
                    final double d12 = this.world.a(vec3d, entity.boundingBox);
                    final double d13 = (1.0 - d10) * d12;
                    final org.bukkit.entity.Entity damagee = (entity == null) ? null : entity.getBukkitEntity();
                    final int damageDone = (int)((d13 * d13 + d13) / 2.0 * 8.0 * this.size + 1.0);
                    if (damagee != null) {
                        if (this.source == null) {
                            final EntityDamageByBlockEvent event = new EntityDamageByBlockEvent(null, damagee, EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, damageDone);
                            Bukkit.getPluginManager().callEvent(event);
                            if (!event.isCancelled()) {
                                damagee.setLastDamageCause(event);
                                entity.damageEntity(DamageSource.explosion(this), event.getDamage());
                                final double d14 = EnchantmentProtection.a(entity, d13);
                                final Entity entity2 = entity;
                                entity2.motX += d7 * d14;
                                final Entity entity3 = entity;
                                entity3.motY += d8 * d14;
                                final Entity entity4 = entity;
                                entity4.motZ += d9 * d14;
                                if (entity instanceof EntityHuman) {
                                    this.l.put(entity, this.world.getVec3DPool().create(d7 * d13, d8 * d13, d9 * d13));
                                }
                            }
                        }
                        else {
                            final org.bukkit.entity.Entity damager = this.source.getBukkitEntity();
                            EntityDamageEvent.DamageCause damageCause;
                            if (damager instanceof TNTPrimed) {
                                damageCause = EntityDamageEvent.DamageCause.BLOCK_EXPLOSION;
                            }
                            else {
                                damageCause = EntityDamageEvent.DamageCause.ENTITY_EXPLOSION;
                            }
                            final EntityDamageByEntityEvent event2 = new EntityDamageByEntityEvent(damager, damagee, damageCause, damageDone);
                            Bukkit.getPluginManager().callEvent(event2);
                            if (!event2.isCancelled()) {
                                entity.getBukkitEntity().setLastDamageCause(event2);
                                entity.damageEntity(DamageSource.explosion(this), event2.getDamage());
                                final Entity entity5 = entity;
                                entity5.motX += d7 * d13;
                                final Entity entity6 = entity;
                                entity6.motY += d8 * d13;
                                final Entity entity7 = entity;
                                entity7.motZ += d9 * d13;
                                if (entity instanceof EntityHuman) {
                                    this.l.put(entity, this.world.getVec3DPool().create(d7 * d13, d8 * d13, d9 * d13));
                                }
                            }
                        }
                    }
                }
            }
        }
        this.size = f;
    }
    
    public void a(final boolean flag) {
        this.world.makeSound(this.posX, this.posY, this.posZ, "random.explode", 4.0f, (1.0f + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2f) * 0.7f);
        if (this.size >= 2.0f && this.b) {
            this.world.addParticle("hugeexplosion", this.posX, this.posY, this.posZ, 1.0, 0.0, 0.0);
        }
        else {
            this.world.addParticle("largeexplode", this.posX, this.posY, this.posZ, 1.0, 0.0, 0.0);
        }
        if (this.b) {
            final org.bukkit.World bworld = this.world.getWorld();
            final org.bukkit.entity.Entity explode = (this.source == null) ? null : this.source.getBukkitEntity();
            final Location location = new Location(bworld, this.posX, this.posY, this.posZ);
            final List<org.bukkit.block.Block> blockList = new ArrayList<org.bukkit.block.Block>();
            for (int i1 = this.blocks.size() - 1; i1 >= 0; --i1) {
                final ChunkPosition cpos = this.blocks.get(i1);
                final org.bukkit.block.Block block = bworld.getBlockAt(cpos.x, cpos.y, cpos.z);
                if (block.getType() != Material.AIR) {
                    blockList.add(block);
                }
            }
            final EntityExplodeEvent event = new EntityExplodeEvent(explode, location, blockList, 0.3f);
            this.world.getServer().getPluginManager().callEvent(event);
            this.blocks.clear();
            final Iterator i$ = event.blockList().iterator();
            while (i$.hasNext()) {
                final org.bukkit.block.Block block = i$.next();
                final ChunkPosition coords = new ChunkPosition(block.getX(), block.getY(), block.getZ());
                this.blocks.add(coords);
            }
            if (event.isCancelled()) {
                this.wasCanceled = true;
                return;
            }
            for (final ChunkPosition chunkposition : this.blocks) {
                final int j = chunkposition.x;
                final int k = chunkposition.y;
                final int l = chunkposition.z;
                final int m = this.world.getTypeId(j, k, l);
                OrebfuscatorManager.updateNearbyBlocks(this.world, j, k, l);
                if (flag) {
                    final double d0 = j + this.world.random.nextFloat();
                    final double d2 = k + this.world.random.nextFloat();
                    final double d3 = l + this.world.random.nextFloat();
                    double d4 = d0 - this.posX;
                    double d5 = d2 - this.posY;
                    double d6 = d3 - this.posZ;
                    final double d7 = MathHelper.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
                    d4 /= d7;
                    d5 /= d7;
                    d6 /= d7;
                    double d8 = 0.5 / (d7 / this.size + 0.1);
                    d8 *= this.world.random.nextFloat() * this.world.random.nextFloat() + 0.3f;
                    d4 *= d8;
                    d5 *= d8;
                    d6 *= d8;
                    this.world.addParticle("explode", (d0 + this.posX * 1.0) / 2.0, (d2 + this.posY * 1.0) / 2.0, (d3 + this.posZ * 1.0) / 2.0, d4, d5, d6);
                    this.world.addParticle("smoke", d0, d2, d3, d4, d5, d6);
                }
                if (m > 0) {
                    final Block block2 = Block.byId[m];
                    if (block2.a(this)) {
                        block2.dropNaturally(this.world, j, k, l, this.world.getData(j, k, l), event.getYield(), 0);
                    }
                    this.world.setTypeIdAndData(j, k, l, 0, 0, 3);
                    block2.wasExploded(this.world, j, k, l, this);
                }
            }
        }
        if (this.a) {
            for (final ChunkPosition chunkposition : this.blocks) {
                final int j = chunkposition.x;
                final int k = chunkposition.y;
                final int l = chunkposition.z;
                final int m = this.world.getTypeId(j, k, l);
                final int i2 = this.world.getTypeId(j, k - 1, l);
                if (m == 0 && Block.s[i2] && this.j.nextInt(3) == 0 && !CraftEventFactory.callBlockIgniteEvent(this.world, j, k, l, this).isCancelled()) {
                    this.world.setTypeIdUpdate(j, k, l, Block.FIRE.id);
                }
            }
        }
    }
    
    public Map b() {
        return this.l;
    }
    
    public EntityLiving c() {
        return (this.source == null) ? null : ((this.source instanceof EntityTNTPrimed) ? ((EntityTNTPrimed)this.source).getSource() : ((this.source instanceof EntityLiving) ? ((EntityLiving)this.source) : null));
    }
}
