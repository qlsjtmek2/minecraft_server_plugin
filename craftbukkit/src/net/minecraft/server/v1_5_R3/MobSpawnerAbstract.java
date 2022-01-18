// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import java.util.Iterator;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.List;

public abstract class MobSpawnerAbstract
{
    public int spawnDelay;
    private String mobName;
    private List mobs;
    private TileEntityMobSpawnerData spawnData;
    public double c;
    public double d;
    private int minSpawnDelay;
    private int maxSpawnDelay;
    private int spawnCount;
    private Entity j;
    private int maxNearbyEntities;
    private int requiredPlayerRange;
    private int spawnRange;
    
    public MobSpawnerAbstract() {
        this.spawnDelay = 20;
        this.mobName = "Pig";
        this.mobs = null;
        this.spawnData = null;
        this.d = 0.0;
        this.minSpawnDelay = 200;
        this.maxSpawnDelay = 800;
        this.spawnCount = 4;
        this.maxNearbyEntities = 6;
        this.requiredPlayerRange = 16;
        this.spawnRange = 4;
    }
    
    public String getMobName() {
        if (this.i() == null) {
            if (this.mobName.equals("Minecart")) {
                this.mobName = "MinecartRideable";
            }
            return this.mobName;
        }
        return this.i().c;
    }
    
    public void a(final String s) {
        this.mobName = s;
    }
    
    public boolean f() {
        return this.a().findNearbyPlayer(this.b() + 0.5, this.c() + 0.5, this.d() + 0.5, this.requiredPlayerRange) != null;
    }
    
    public void g() {
        if (this.f()) {
            if (this.a().isStatic) {
                final double d1 = this.b() + this.a().random.nextFloat();
                final double d2 = this.c() + this.a().random.nextFloat();
                final double d3 = this.d() + this.a().random.nextFloat();
                this.a().addParticle("smoke", d1, d2, d3, 0.0, 0.0, 0.0);
                this.a().addParticle("flame", d1, d2, d3, 0.0, 0.0, 0.0);
                if (this.spawnDelay > 0) {
                    --this.spawnDelay;
                }
                this.d = this.c;
                this.c = (this.c + 1000.0f / (this.spawnDelay + 200.0f)) % 360.0;
            }
            else {
                if (this.spawnDelay == -1) {
                    this.j();
                }
                if (this.spawnDelay > 0) {
                    --this.spawnDelay;
                    return;
                }
                boolean flag = false;
                for (int i = 0; i < this.spawnCount; ++i) {
                    final Entity entity = EntityTypes.createEntityByName(this.getMobName(), this.a());
                    if (entity == null) {
                        return;
                    }
                    final int j = this.a().a(entity.getClass(), AxisAlignedBB.a().a(this.b(), this.c(), this.d(), this.b() + 1, this.c() + 1, this.d() + 1).grow(this.spawnRange * 2, 4.0, this.spawnRange * 2)).size();
                    if (j >= this.maxNearbyEntities) {
                        this.j();
                        return;
                    }
                    final double d3 = this.b() + (this.a().random.nextDouble() - this.a().random.nextDouble()) * this.spawnRange;
                    final double d4 = this.c() + this.a().random.nextInt(3) - 1;
                    final double d5 = this.d() + (this.a().random.nextDouble() - this.a().random.nextDouble()) * this.spawnRange;
                    final EntityLiving entityliving = (entity instanceof EntityLiving) ? ((EntityLiving)entity) : null;
                    entity.setPositionRotation(d3, d4, d5, this.a().random.nextFloat() * 360.0f, 0.0f);
                    if (entityliving == null || entityliving.canSpawn()) {
                        this.a(entity);
                        this.a().triggerEffect(2004, this.b(), this.c(), this.d(), 0);
                        if (entityliving != null) {
                            entityliving.aU();
                        }
                        flag = true;
                    }
                }
                if (flag) {
                    this.j();
                }
            }
        }
    }
    
    public Entity a(final Entity entity) {
        if (this.i() != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            entity.d(nbttagcompound);
            for (final NBTBase nbtbase : this.i().b.c()) {
                nbttagcompound.set(nbtbase.getName(), nbtbase.clone());
            }
            entity.f(nbttagcompound);
            if (entity.world != null) {
                final SpawnerSpawnEvent event = CraftEventFactory.callSpawnerSpawnEvent(entity, this.b(), this.c(), this.d());
                if (!event.isCancelled()) {
                    entity.world.addEntity(entity, CreatureSpawnEvent.SpawnReason.SPAWNER);
                }
            }
            Entity entity2 = entity;
            while (nbttagcompound.hasKey("Riding")) {
                final NBTTagCompound nbttagcompound2 = nbttagcompound.getCompound("Riding");
                final Entity entity3 = EntityTypes.createEntityByName(nbttagcompound2.getString("id"), this.a());
                Label_0328: {
                    if (entity3 != null) {
                        final NBTTagCompound nbttagcompound3 = new NBTTagCompound();
                        entity3.d(nbttagcompound3);
                        for (final NBTBase nbtbase2 : nbttagcompound2.c()) {
                            nbttagcompound3.set(nbtbase2.getName(), nbtbase2.clone());
                        }
                        entity3.f(nbttagcompound3);
                        entity3.setPositionRotation(entity2.locX, entity2.locY, entity2.locZ, entity2.yaw, entity2.pitch);
                        final SpawnerSpawnEvent event2 = CraftEventFactory.callSpawnerSpawnEvent(entity3, this.b(), this.c(), this.d());
                        if (event2.isCancelled()) {
                            break Label_0328;
                        }
                        this.a().addEntity(entity3, CreatureSpawnEvent.SpawnReason.SPAWNER);
                        entity2.mount(entity3);
                    }
                    entity2 = entity3;
                }
                nbttagcompound = nbttagcompound2;
            }
        }
        else if (entity instanceof EntityLiving && entity.world != null) {
            ((EntityLiving)entity).bJ();
            final SpawnerSpawnEvent event3 = CraftEventFactory.callSpawnerSpawnEvent(entity, this.b(), this.c(), this.d());
            if (!event3.isCancelled()) {
                this.a().addEntity(entity, CreatureSpawnEvent.SpawnReason.SPAWNER);
            }
        }
        return entity;
    }
    
    private void j() {
        if (this.maxSpawnDelay <= this.minSpawnDelay) {
            this.spawnDelay = this.minSpawnDelay;
        }
        else {
            final int i = this.maxSpawnDelay - this.minSpawnDelay;
            this.spawnDelay = this.minSpawnDelay + this.a().random.nextInt(i);
        }
        if (this.mobs != null && this.mobs.size() > 0) {
            this.a((TileEntityMobSpawnerData)WeightedRandom.a(this.a().random, this.mobs));
        }
        this.a(1);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        this.mobName = nbttagcompound.getString("EntityId");
        this.spawnDelay = nbttagcompound.getShort("Delay");
        if (nbttagcompound.hasKey("SpawnPotentials")) {
            this.mobs = new ArrayList();
            final NBTTagList nbttaglist = nbttagcompound.getList("SpawnPotentials");
            for (int i = 0; i < nbttaglist.size(); ++i) {
                this.mobs.add(new TileEntityMobSpawnerData(this, (NBTTagCompound)nbttaglist.get(i)));
            }
        }
        else {
            this.mobs = null;
        }
        if (nbttagcompound.hasKey("SpawnData")) {
            this.a(new TileEntityMobSpawnerData(this, nbttagcompound.getCompound("SpawnData"), this.mobName));
        }
        else {
            this.a((TileEntityMobSpawnerData)null);
        }
        if (nbttagcompound.hasKey("MinSpawnDelay")) {
            this.minSpawnDelay = nbttagcompound.getShort("MinSpawnDelay");
            this.maxSpawnDelay = nbttagcompound.getShort("MaxSpawnDelay");
            this.spawnCount = nbttagcompound.getShort("SpawnCount");
        }
        if (nbttagcompound.hasKey("MaxNearbyEntities")) {
            this.maxNearbyEntities = nbttagcompound.getShort("MaxNearbyEntities");
            this.requiredPlayerRange = nbttagcompound.getShort("RequiredPlayerRange");
        }
        if (nbttagcompound.hasKey("SpawnRange")) {
            this.spawnRange = nbttagcompound.getShort("SpawnRange");
        }
        if (this.a() != null && this.a().isStatic) {
            this.j = null;
        }
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setString("EntityId", this.getMobName());
        nbttagcompound.setShort("Delay", (short)this.spawnDelay);
        nbttagcompound.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
        nbttagcompound.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
        nbttagcompound.setShort("SpawnCount", (short)this.spawnCount);
        nbttagcompound.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
        nbttagcompound.setShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
        nbttagcompound.setShort("SpawnRange", (short)this.spawnRange);
        if (this.i() != null) {
            nbttagcompound.setCompound("SpawnData", (NBTTagCompound)this.i().b.clone());
        }
        if (this.i() != null || (this.mobs != null && this.mobs.size() > 0)) {
            final NBTTagList nbttaglist = new NBTTagList();
            if (this.mobs != null && this.mobs.size() > 0) {
                for (final TileEntityMobSpawnerData tileentitymobspawnerdata : this.mobs) {
                    nbttaglist.add(tileentitymobspawnerdata.a());
                }
            }
            else {
                nbttaglist.add(this.i().a());
            }
            nbttagcompound.set("SpawnPotentials", nbttaglist);
        }
    }
    
    public boolean b(final int i) {
        if (i == 1 && this.a().isStatic) {
            this.spawnDelay = this.minSpawnDelay;
            return true;
        }
        return false;
    }
    
    public TileEntityMobSpawnerData i() {
        return this.spawnData;
    }
    
    public void a(final TileEntityMobSpawnerData tileentitymobspawnerdata) {
        this.spawnData = tileentitymobspawnerdata;
    }
    
    public abstract void a(final int p0);
    
    public abstract World a();
    
    public abstract int b();
    
    public abstract int c();
    
    public abstract int d();
}
