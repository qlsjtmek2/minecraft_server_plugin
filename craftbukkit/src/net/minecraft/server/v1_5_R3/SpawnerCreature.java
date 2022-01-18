// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Collection;
import java.util.Random;
import org.bukkit.event.entity.CreatureSpawnEvent;
import java.util.Iterator;
import org.bukkit.craftbukkit.v1_5_R3.util.LongHash;
import org.bukkit.craftbukkit.v1_5_R3.util.LongObjectHashMap;

public final class SpawnerCreature
{
    private static LongObjectHashMap<Boolean> b;
    protected static final Class[] a;
    private static byte spawnRadius;
    
    protected static ChunkPosition getRandomPosition(final World world, final int i, final int j) {
        final Chunk chunk = world.getChunkAt(i, j);
        final int k = i * 16 + world.random.nextInt(16);
        final int l = j * 16 + world.random.nextInt(16);
        final int i2 = world.random.nextInt((chunk == null) ? world.R() : (chunk.h() + 16 - 1));
        return new ChunkPosition(k, i2, l);
    }
    
    public static final int getEntityCount(final WorldServer server, final Class oClass) {
        int i = 0;
        for (final Long coord : SpawnerCreature.b.keySet()) {
            final int x = LongHash.msw(coord);
            final int z = LongHash.lsw(coord);
            if (!server.chunkProviderServer.unloadQueue.contains(coord) && server.isChunkLoaded(x, z)) {
                i += server.getChunkAt(x, z).entityCount.get(oClass);
            }
        }
        return i;
    }
    
    public static final int spawnEntities(final WorldServer worldserver, final boolean flag, final boolean flag1, final boolean flag2) {
        if (!flag && !flag1) {
            return 0;
        }
        SpawnerCreature.b.clear();
        if (SpawnerCreature.spawnRadius == 0) {
            SpawnerCreature.spawnRadius = (byte)worldserver.getWorld().mobSpawnRange;
            if (SpawnerCreature.spawnRadius > (byte)worldserver.getServer().getViewDistance()) {
                SpawnerCreature.spawnRadius = (byte)worldserver.getServer().getViewDistance();
            }
            if (SpawnerCreature.spawnRadius > 8) {
                SpawnerCreature.spawnRadius = 8;
            }
        }
        for (int i = 0; i < worldserver.players.size(); ++i) {
            final EntityHuman entityhuman = worldserver.players.get(i);
            final int k = MathHelper.floor(entityhuman.locX / 16.0);
            final int j = MathHelper.floor(entityhuman.locZ / 16.0);
            final byte b0 = SpawnerCreature.spawnRadius;
            for (int l = -b0; l <= b0; ++l) {
                for (int i2 = -b0; i2 <= b0; ++i2) {
                    final boolean flag3 = l == -b0 || l == b0 || i2 == -b0 || i2 == b0;
                    final long chunkCoords = LongHash.toLong(l + k, i2 + j);
                    if (!flag3) {
                        SpawnerCreature.b.put(chunkCoords, false);
                    }
                    else if (!SpawnerCreature.b.containsKey(chunkCoords)) {
                        SpawnerCreature.b.put(chunkCoords, true);
                    }
                }
            }
        }
        int i = 0;
        final ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
        final EnumCreatureType[] aenumcreaturetype = EnumCreatureType.values();
        final int j = aenumcreaturetype.length;
    Label_0513_Outer:
        for (final EnumCreatureType enumcreaturetype : aenumcreaturetype) {
            int limit = enumcreaturetype.b();
            switch (enumcreaturetype) {
                case MONSTER: {
                    limit = worldserver.getWorld().getMonsterSpawnLimit();
                    break;
                }
                case CREATURE: {
                    limit = worldserver.getWorld().getAnimalSpawnLimit();
                    break;
                }
                case WATER_CREATURE: {
                    limit = worldserver.getWorld().getWaterAnimalSpawnLimit();
                    break;
                }
                case AMBIENT: {
                    limit = worldserver.getWorld().getAmbientSpawnLimit();
                    break;
                }
            }
            if (limit != 0) {
                int mobcnt = 0;
                if ((!enumcreaturetype.d() || flag1) && (enumcreaturetype.d() || flag) && (!enumcreaturetype.e() || flag2) && (mobcnt = getEntityCount(worldserver, enumcreaturetype.a())) <= limit * SpawnerCreature.b.size() / 256) {
                    final Iterator iterator = SpawnerCreature.b.keySet().iterator();
                    int moblimit = limit * SpawnerCreature.b.size() / 256 - mobcnt + 1;
                Label_0513:
                    while (true) {
                        while (iterator.hasNext() && moblimit > 0) {
                            final long key = iterator.next();
                            if (!SpawnerCreature.b.get(key)) {
                                final ChunkPosition chunkposition = getRandomPosition(worldserver, LongHash.msw(key), LongHash.lsw(key));
                                final int k2 = chunkposition.x;
                                final int l2 = chunkposition.y;
                                final int i3 = chunkposition.z;
                                if (worldserver.u(k2, l2, i3) || worldserver.getMaterial(k2, l2, i3) != enumcreaturetype.c()) {
                                    continue Label_0513_Outer;
                                }
                                int j3 = 0;
                                for (int k3 = 0; k3 < 3; ++k3) {
                                    int l3 = k2;
                                    int i4 = l2;
                                    int j4 = i3;
                                    final byte b2 = 6;
                                    BiomeMeta biomemeta = null;
                                    for (int k4 = 0; k4 < 4; ++k4) {
                                        l3 += worldserver.random.nextInt(b2) - worldserver.random.nextInt(b2);
                                        i4 += worldserver.random.nextInt(1) - worldserver.random.nextInt(1);
                                        j4 += worldserver.random.nextInt(b2) - worldserver.random.nextInt(b2);
                                        if (a(enumcreaturetype, worldserver, l3, i4, j4)) {
                                            final float f = l3 + 0.5f;
                                            final float f2 = i4;
                                            final float f3 = j4 + 0.5f;
                                            if (worldserver.findNearbyPlayer(f, f2, f3, 24.0) == null) {
                                                final float f4 = f - chunkcoordinates.x;
                                                final float f5 = f2 - chunkcoordinates.y;
                                                final float f6 = f3 - chunkcoordinates.z;
                                                final float f7 = f4 * f4 + f5 * f5 + f6 * f6;
                                                if (f7 >= 576.0f) {
                                                    if (biomemeta == null) {
                                                        biomemeta = worldserver.a(enumcreaturetype, l3, i4, j4);
                                                        if (biomemeta == null) {
                                                            break;
                                                        }
                                                    }
                                                    EntityLiving entityliving;
                                                    try {
                                                        entityliving = biomemeta.b.getConstructor(World.class).newInstance(worldserver);
                                                    }
                                                    catch (Exception exception) {
                                                        exception.printStackTrace();
                                                        return i;
                                                    }
                                                    entityliving.setPositionRotation(f, f2, f3, worldserver.random.nextFloat() * 360.0f, 0.0f);
                                                    if (entityliving.canSpawn()) {
                                                        ++j3;
                                                        a(entityliving, worldserver, f, f2, f3);
                                                        worldserver.addEntity(entityliving, CreatureSpawnEvent.SpawnReason.NATURAL);
                                                        if (--moblimit <= 0) {
                                                            continue Label_0513;
                                                        }
                                                        if (j3 >= entityliving.by()) {
                                                            continue Label_0513;
                                                        }
                                                    }
                                                    i += j3;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        return i;
    }
    
    public static boolean a(final EnumCreatureType enumcreaturetype, final World world, final int i, final int j, final int k) {
        if (enumcreaturetype.c() == Material.WATER) {
            return world.getMaterial(i, j, k).isLiquid() && world.getMaterial(i, j - 1, k).isLiquid() && !world.u(i, j + 1, k);
        }
        if (!world.w(i, j - 1, k)) {
            return false;
        }
        final int l = world.getTypeId(i, j - 1, k);
        return l != Block.BEDROCK.id && !world.u(i, j, k) && !world.getMaterial(i, j, k).isLiquid() && !world.u(i, j + 1, k);
    }
    
    private static void a(final EntityLiving entityliving, final World world, final float f, final float f1, final float f2) {
        entityliving.bJ();
    }
    
    public static void a(final World world, final BiomeBase biomebase, final int i, final int j, final int k, final int l, final Random random) {
        final List list = biomebase.getMobs(EnumCreatureType.CREATURE);
        if (!list.isEmpty()) {
            while (random.nextFloat() < biomebase.f()) {
                final BiomeMeta biomemeta = (BiomeMeta)WeightedRandom.a(world.random, list);
                final int i2 = biomemeta.c + random.nextInt(1 + biomemeta.d - biomemeta.c);
                int j2 = i + random.nextInt(k);
                int k2 = j + random.nextInt(l);
                final int l2 = j2;
                final int i3 = k2;
                for (int j3 = 0; j3 < i2; ++j3) {
                    boolean flag = false;
                    for (int k3 = 0; !flag && k3 < 4; ++k3) {
                        final int l3 = world.i(j2, k2);
                        if (a(EnumCreatureType.CREATURE, world, j2, l3, k2)) {
                            final float f = j2 + 0.5f;
                            final float f2 = l3;
                            final float f3 = k2 + 0.5f;
                            EntityLiving entityliving;
                            try {
                                entityliving = biomemeta.b.getConstructor(World.class).newInstance(world);
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                                continue;
                            }
                            entityliving.setPositionRotation(f, f2, f3, random.nextFloat() * 360.0f, 0.0f);
                            a(entityliving, world, f, f2, f3);
                            world.addEntity(entityliving, CreatureSpawnEvent.SpawnReason.CHUNK_GEN);
                            flag = true;
                        }
                        for (j2 += random.nextInt(5) - random.nextInt(5), k2 += random.nextInt(5) - random.nextInt(5); j2 < i || j2 >= i + k || k2 < j || k2 >= j + k; j2 = l2 + random.nextInt(5) - random.nextInt(5), k2 = i3 + random.nextInt(5) - random.nextInt(5)) {}
                    }
                }
            }
        }
    }
    
    static {
        SpawnerCreature.b = new LongObjectHashMap<Boolean>();
        a = new Class[] { EntitySpider.class, EntityZombie.class, EntitySkeleton.class };
        SpawnerCreature.spawnRadius = 0;
    }
}
