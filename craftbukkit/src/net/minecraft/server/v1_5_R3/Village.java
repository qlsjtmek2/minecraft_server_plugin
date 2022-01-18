// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import org.bukkit.event.entity.CreatureSpawnEvent;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;

public class Village
{
    private World world;
    private final List doors;
    private final ChunkCoordinates c;
    private final ChunkCoordinates center;
    private int size;
    private int f;
    private int time;
    private int population;
    private int noBreedTicks;
    private TreeMap playerStandings;
    private List aggressors;
    private int ironGolemCount;
    
    public Village() {
        this.doors = new ArrayList();
        this.c = new ChunkCoordinates(0, 0, 0);
        this.center = new ChunkCoordinates(0, 0, 0);
        this.size = 0;
        this.f = 0;
        this.time = 0;
        this.population = 0;
        this.playerStandings = new TreeMap();
        this.aggressors = new ArrayList();
        this.ironGolemCount = 0;
    }
    
    public Village(final World world) {
        this.doors = new ArrayList();
        this.c = new ChunkCoordinates(0, 0, 0);
        this.center = new ChunkCoordinates(0, 0, 0);
        this.size = 0;
        this.f = 0;
        this.time = 0;
        this.population = 0;
        this.playerStandings = new TreeMap();
        this.aggressors = new ArrayList();
        this.ironGolemCount = 0;
        this.world = world;
    }
    
    public void a(final World world) {
        this.world = world;
    }
    
    public void tick(final int i) {
        this.time = i;
        this.m();
        this.l();
        if (i % 20 == 0) {
            this.k();
        }
        if (i % 30 == 0) {
            this.countPopulation();
        }
        final int j = this.population / 10;
        if (this.ironGolemCount < j && this.doors.size() > 20 && this.world.random.nextInt(7000) == 0) {
            final Vec3D vec3d = this.a(MathHelper.d(this.center.x), MathHelper.d(this.center.y), MathHelper.d(this.center.z), 2, 4, 2);
            if (vec3d != null) {
                final EntityIronGolem entityirongolem = new EntityIronGolem(this.world);
                entityirongolem.setPosition(vec3d.c, vec3d.d, vec3d.e);
                this.world.addEntity(entityirongolem, CreatureSpawnEvent.SpawnReason.VILLAGE_DEFENSE);
                ++this.ironGolemCount;
            }
        }
    }
    
    private Vec3D a(final int i, final int j, final int k, final int l, final int i1, final int j1) {
        for (int k2 = 0; k2 < 10; ++k2) {
            final int l2 = i + this.world.random.nextInt(16) - 8;
            final int i2 = j + this.world.random.nextInt(6) - 3;
            final int j2 = k + this.world.random.nextInt(16) - 8;
            if (this.a(l2, i2, j2) && this.b(l2, i2, j2, l, i1, j1)) {
                return this.world.getVec3DPool().create(l2, i2, j2);
            }
        }
        return null;
    }
    
    private boolean b(final int i, final int j, final int k, final int l, final int i1, final int j1) {
        if (!this.world.w(i, j - 1, k)) {
            return false;
        }
        final int k2 = i - l / 2;
        final int l2 = k - j1 / 2;
        for (int i2 = k2; i2 < k2 + l; ++i2) {
            for (int j2 = j; j2 < j + i1; ++j2) {
                for (int k3 = l2; k3 < l2 + j1; ++k3) {
                    if (this.world.u(i2, j2, k3)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private void countPopulation() {
        final List list = this.world.a(EntityIronGolem.class, AxisAlignedBB.a().a(this.center.x - this.size, this.center.y - 4, this.center.z - this.size, this.center.x + this.size, this.center.y + 4, this.center.z + this.size));
        this.ironGolemCount = list.size();
    }
    
    private void k() {
        final List list = this.world.a(EntityVillager.class, AxisAlignedBB.a().a(this.center.x - this.size, this.center.y - 4, this.center.z - this.size, this.center.x + this.size, this.center.y + 4, this.center.z + this.size));
        this.population = list.size();
        if (this.population == 0) {
            this.playerStandings.clear();
        }
    }
    
    public ChunkCoordinates getCenter() {
        return this.center;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public int getDoorCount() {
        return this.doors.size();
    }
    
    public int d() {
        return this.time - this.f;
    }
    
    public int getPopulationCount() {
        return this.population;
    }
    
    public boolean a(final int i, final int j, final int k) {
        return this.center.e(i, j, k) < this.size * this.size;
    }
    
    public List getDoors() {
        return this.doors;
    }
    
    public VillageDoor b(final int i, final int j, final int k) {
        VillageDoor villagedoor = null;
        int l = Integer.MAX_VALUE;
        for (final VillageDoor villagedoor2 : this.doors) {
            final int i2 = villagedoor2.b(i, j, k);
            if (i2 < l) {
                villagedoor = villagedoor2;
                l = i2;
            }
        }
        return villagedoor;
    }
    
    public VillageDoor c(final int i, final int j, final int k) {
        VillageDoor villagedoor = null;
        int l = Integer.MAX_VALUE;
        for (final VillageDoor villagedoor2 : this.doors) {
            int i2 = villagedoor2.b(i, j, k);
            if (i2 > 256) {
                i2 *= 1000;
            }
            else {
                i2 = villagedoor2.f();
            }
            if (i2 < l) {
                villagedoor = villagedoor2;
                l = i2;
            }
        }
        return villagedoor;
    }
    
    public VillageDoor e(final int i, final int j, final int k) {
        if (this.center.e(i, j, k) > this.size * this.size) {
            return null;
        }
        for (final VillageDoor villagedoor : this.doors) {
            if (villagedoor.locX == i && villagedoor.locZ == k && Math.abs(villagedoor.locY - j) <= 1) {
                return villagedoor;
            }
        }
        return null;
    }
    
    public void addDoor(final VillageDoor villagedoor) {
        this.doors.add(villagedoor);
        final ChunkCoordinates c = this.c;
        c.x += villagedoor.locX;
        final ChunkCoordinates c2 = this.c;
        c2.y += villagedoor.locY;
        final ChunkCoordinates c3 = this.c;
        c3.z += villagedoor.locZ;
        this.n();
        this.f = villagedoor.addedTime;
    }
    
    public boolean isAbandoned() {
        return this.doors.isEmpty();
    }
    
    public void a(final EntityLiving entityliving) {
        for (final VillageAggressor villageaggressor : this.aggressors) {
            if (villageaggressor.a == entityliving) {
                villageaggressor.b = this.time;
                return;
            }
        }
        this.aggressors.add(new VillageAggressor(this, entityliving, this.time));
    }
    
    public EntityLiving b(final EntityLiving entityliving) {
        double d0 = Double.MAX_VALUE;
        VillageAggressor villageaggressor = null;
        for (int i = 0; i < this.aggressors.size(); ++i) {
            final VillageAggressor villageaggressor2 = this.aggressors.get(i);
            final double d2 = villageaggressor2.a.e(entityliving);
            if (d2 <= d0) {
                villageaggressor = villageaggressor2;
                d0 = d2;
            }
        }
        return (villageaggressor != null) ? villageaggressor.a : null;
    }
    
    public EntityHuman c(final EntityLiving entityliving) {
        double d0 = Double.MAX_VALUE;
        EntityHuman entityhuman = null;
        for (final String s : this.playerStandings.keySet()) {
            if (this.d(s)) {
                final EntityHuman entityhuman2 = this.world.a(s);
                if (entityhuman2 == null) {
                    continue;
                }
                final double d2 = entityhuman2.e(entityliving);
                if (d2 > d0) {
                    continue;
                }
                entityhuman = entityhuman2;
                d0 = d2;
            }
        }
        return entityhuman;
    }
    
    private void l() {
        final Iterator iterator = this.aggressors.iterator();
        while (iterator.hasNext()) {
            final VillageAggressor villageaggressor = iterator.next();
            if (!villageaggressor.a.isAlive() || Math.abs(this.time - villageaggressor.b) > 300) {
                iterator.remove();
            }
        }
    }
    
    private void m() {
        boolean flag = false;
        final boolean flag2 = this.world.random.nextInt(50) == 0;
        final Iterator iterator = this.doors.iterator();
        while (iterator.hasNext()) {
            final VillageDoor villagedoor = iterator.next();
            if (flag2) {
                villagedoor.d();
            }
            if (!this.isDoor(villagedoor.locX, villagedoor.locY, villagedoor.locZ) || Math.abs(this.time - villagedoor.addedTime) > 1200) {
                final ChunkCoordinates c = this.c;
                c.x -= villagedoor.locX;
                final ChunkCoordinates c2 = this.c;
                c2.y -= villagedoor.locY;
                final ChunkCoordinates c3 = this.c;
                c3.z -= villagedoor.locZ;
                flag = true;
                villagedoor.removed = true;
                iterator.remove();
            }
        }
        if (flag) {
            this.n();
        }
    }
    
    private boolean isDoor(final int i, final int j, final int k) {
        final int l = this.world.getTypeId(i, j, k);
        return l > 0 && l == Block.WOODEN_DOOR.id;
    }
    
    private void n() {
        final int i = this.doors.size();
        if (i == 0) {
            this.center.b(0, 0, 0);
            this.size = 0;
        }
        else {
            this.center.b(this.c.x / i, this.c.y / i, this.c.z / i);
            int j = 0;
            for (final VillageDoor villagedoor : this.doors) {
                j = Math.max(villagedoor.b(this.center.x, this.center.y, this.center.z), j);
            }
            this.size = Math.max(32, (int)Math.sqrt(j) + 1);
        }
    }
    
    public int a(final String s) {
        final Integer integer = this.playerStandings.get(s);
        return (integer != null) ? integer : 0;
    }
    
    public int a(final String s, final int i) {
        final int j = this.a(s);
        final int k = MathHelper.a(j + i, -30, 10);
        this.playerStandings.put(s, k);
        return k;
    }
    
    public boolean d(final String s) {
        return this.a(s) <= -15;
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        this.population = nbttagcompound.getInt("PopSize");
        this.size = nbttagcompound.getInt("Radius");
        this.ironGolemCount = nbttagcompound.getInt("Golems");
        this.f = nbttagcompound.getInt("Stable");
        this.time = nbttagcompound.getInt("Tick");
        this.noBreedTicks = nbttagcompound.getInt("MTick");
        this.center.x = nbttagcompound.getInt("CX");
        this.center.y = nbttagcompound.getInt("CY");
        this.center.z = nbttagcompound.getInt("CZ");
        this.c.x = nbttagcompound.getInt("ACX");
        this.c.y = nbttagcompound.getInt("ACY");
        this.c.z = nbttagcompound.getInt("ACZ");
        final NBTTagList nbttaglist = nbttagcompound.getList("Doors");
        for (int i = 0; i < nbttaglist.size(); ++i) {
            final NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbttaglist.get(i);
            final VillageDoor villagedoor = new VillageDoor(nbttagcompound2.getInt("X"), nbttagcompound2.getInt("Y"), nbttagcompound2.getInt("Z"), nbttagcompound2.getInt("IDX"), nbttagcompound2.getInt("IDZ"), nbttagcompound2.getInt("TS"));
            this.doors.add(villagedoor);
        }
        final NBTTagList nbttaglist2 = nbttagcompound.getList("Players");
        for (int j = 0; j < nbttaglist2.size(); ++j) {
            final NBTTagCompound nbttagcompound3 = (NBTTagCompound)nbttaglist2.get(j);
            this.playerStandings.put(nbttagcompound3.getString("Name"), nbttagcompound3.getInt("S"));
        }
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setInt("PopSize", this.population);
        nbttagcompound.setInt("Radius", this.size);
        nbttagcompound.setInt("Golems", this.ironGolemCount);
        nbttagcompound.setInt("Stable", this.f);
        nbttagcompound.setInt("Tick", this.time);
        nbttagcompound.setInt("MTick", this.noBreedTicks);
        nbttagcompound.setInt("CX", this.center.x);
        nbttagcompound.setInt("CY", this.center.y);
        nbttagcompound.setInt("CZ", this.center.z);
        nbttagcompound.setInt("ACX", this.c.x);
        nbttagcompound.setInt("ACY", this.c.y);
        nbttagcompound.setInt("ACZ", this.c.z);
        final NBTTagList nbttaglist = new NBTTagList("Doors");
        for (final VillageDoor villagedoor : this.doors) {
            final NBTTagCompound nbttagcompound2 = new NBTTagCompound("Door");
            nbttagcompound2.setInt("X", villagedoor.locX);
            nbttagcompound2.setInt("Y", villagedoor.locY);
            nbttagcompound2.setInt("Z", villagedoor.locZ);
            nbttagcompound2.setInt("IDX", villagedoor.d);
            nbttagcompound2.setInt("IDZ", villagedoor.e);
            nbttagcompound2.setInt("TS", villagedoor.addedTime);
            nbttaglist.add(nbttagcompound2);
        }
        nbttagcompound.set("Doors", nbttaglist);
        final NBTTagList nbttaglist2 = new NBTTagList("Players");
        for (final String s : this.playerStandings.keySet()) {
            final NBTTagCompound nbttagcompound3 = new NBTTagCompound(s);
            nbttagcompound3.setString("Name", s);
            nbttagcompound3.setInt("S", this.playerStandings.get(s));
            nbttaglist2.add(nbttagcompound3);
        }
        nbttagcompound.set("Players", nbttaglist2);
    }
    
    public void h() {
        this.noBreedTicks = this.time;
    }
    
    public boolean i() {
        return this.noBreedTicks == 0 || this.time - this.noBreedTicks >= 3600;
    }
    
    public void b(final int i) {
        for (final String s : this.playerStandings.keySet()) {
            this.a(s, i);
        }
    }
}
