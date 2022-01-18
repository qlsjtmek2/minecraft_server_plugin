// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.CreatureSpawnEvent;
import java.util.Iterator;
import java.util.List;

public class VillageSiege
{
    private World world;
    private boolean b;
    private int c;
    private int d;
    private int e;
    private Village f;
    private int g;
    private int h;
    private int i;
    
    public VillageSiege(final World world) {
        this.b = false;
        this.c = -1;
        this.world = world;
    }
    
    public void a() {
        final boolean flag = false;
        if (flag) {
            if (this.c == 2) {
                this.d = 100;
                return;
            }
        }
        else {
            if (this.world.v()) {
                this.c = 0;
                return;
            }
            if (this.c == 2) {
                return;
            }
            if (this.c == 0) {
                final float f = this.world.c(0.0f);
                if (f < 0.5 || f > 0.501) {
                    return;
                }
                this.c = ((this.world.random.nextInt(10) == 0) ? 1 : 2);
                this.b = false;
                if (this.c == 2) {
                    return;
                }
            }
        }
        if (!this.b) {
            if (!this.b()) {
                return;
            }
            this.b = true;
        }
        if (this.e > 0) {
            --this.e;
        }
        else {
            this.e = 2;
            if (this.d > 0) {
                this.c();
                --this.d;
            }
            else {
                this.c = 2;
            }
        }
    }
    
    private boolean b() {
        final List list = this.world.players;
        for (final EntityHuman entityhuman : list) {
            this.f = this.world.villages.getClosestVillage((int)entityhuman.locX, (int)entityhuman.locY, (int)entityhuman.locZ, 1);
            if (this.f != null && this.f.getDoorCount() >= 10 && this.f.d() >= 20 && this.f.getPopulationCount() >= 20) {
                final ChunkCoordinates chunkcoordinates = this.f.getCenter();
                final float f = this.f.getSize();
                boolean flag = false;
                for (int i = 0; i < 10; ++i) {
                    this.g = chunkcoordinates.x + (int)(MathHelper.cos(this.world.random.nextFloat() * 3.1415927f * 2.0f) * f * 0.9);
                    this.h = chunkcoordinates.y;
                    this.i = chunkcoordinates.z + (int)(MathHelper.sin(this.world.random.nextFloat() * 3.1415927f * 2.0f) * f * 0.9);
                    flag = false;
                    for (final Village village : this.world.villages.getVillages()) {
                        if (village != this.f && village.a(this.g, this.h, this.i)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        break;
                    }
                }
                if (flag) {
                    return false;
                }
                final Vec3D vec3d = this.a(this.g, this.h, this.i);
                if (vec3d != null) {
                    this.e = 0;
                    this.d = 20;
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    private boolean c() {
        final Vec3D vec3d = this.a(this.g, this.h, this.i);
        if (vec3d == null) {
            return false;
        }
        EntityZombie entityzombie;
        try {
            entityzombie = new EntityZombie(this.world);
            entityzombie.bJ();
            entityzombie.setVillager(false);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        entityzombie.setPositionRotation(vec3d.c, vec3d.d, vec3d.e, this.world.random.nextFloat() * 360.0f, 0.0f);
        this.world.addEntity(entityzombie, CreatureSpawnEvent.SpawnReason.VILLAGE_INVASION);
        final ChunkCoordinates chunkcoordinates = this.f.getCenter();
        entityzombie.b(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, this.f.getSize());
        return true;
    }
    
    private Vec3D a(final int i, final int j, final int k) {
        for (int l = 0; l < 10; ++l) {
            final int i2 = i + this.world.random.nextInt(16) - 8;
            final int j2 = j + this.world.random.nextInt(6) - 3;
            final int k2 = k + this.world.random.nextInt(16) - 8;
            if (this.f.a(i2, j2, k2) && SpawnerCreature.a(EnumCreatureType.MONSTER, this.world, i2, j2, k2)) {
                return this.world.getVec3DPool().create(i2, j2, k2);
            }
        }
        return null;
    }
}
