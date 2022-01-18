// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.craftbukkit.v1_5_R3.Spigot;
import java.util.Collection;
import java.util.Iterator;
import org.bukkit.event.Cancellable;
import org.bukkit.entity.Projectile;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.entity.CreatureSpawnEvent;
import java.util.concurrent.Callable;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPhysicsEvent;
import java.util.HashSet;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.craftbukkit.v1_5_R3.SpigotTimings;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import gnu.trove.map.hash.TLongShortHashMap;
import org.bukkit.craftbukkit.v1_5_R3.util.UnsafeList;
import java.util.Calendar;
import java.util.Random;
import java.util.Set;
import java.util.List;

public abstract class World implements IBlockAccess
{
    public boolean d;
    public List entityList;
    protected List f;
    public Set tileEntityList;
    private List a;
    private List b;
    public List players;
    public List i;
    private long c;
    public int j;
    protected int k;
    protected final int l = 1013904223;
    protected float m;
    protected float n;
    protected float o;
    protected float p;
    public int q;
    public boolean callingPlaceEvent;
    public int difficulty;
    public Random random;
    public WorldProvider worldProvider;
    protected List u;
    public IChunkProvider chunkProvider;
    protected final IDataManager dataManager;
    public WorldData worldData;
    public boolean isLoading;
    public WorldMapCollection worldMaps;
    public final VillageCollection villages;
    protected final VillageSiege siegeManager;
    public final MethodProfiler methodProfiler;
    private final Vec3DPool J;
    private final Calendar K;
    public Scoreboard scoreboard;
    private final IConsoleLogManager logAgent;
    private UnsafeList M;
    private boolean N;
    public boolean allowMonsters;
    public boolean allowAnimals;
    public long ticksPerAnimalSpawns;
    public long ticksPerMonsterSpawns;
    private int O;
    int[] H;
    public boolean isStatic;
    protected final TLongShortHashMap chunkTickList;
    protected float growthOdds;
    protected float modifiedOdds;
    private final byte chunkTickRadius;
    private final CraftWorld world;
    public boolean pvpMode;
    public boolean keepSpawnInMemory;
    public ChunkGenerator generator;
    Chunk lastChunkAccessed;
    int lastXAccessed;
    int lastZAccessed;
    final Object chunkLock;
    public final SpigotTimings.WorldTimingsHandler timings;
    
    public static long chunkToKey(final int x, final int z) {
        long k = (x & 0xFFFF0000L) << 16 | (x & 0xFFFFL) << 0;
        k |= ((z & 0xFFFF0000L) << 32 | (z & 0xFFFFL) << 16);
        return k;
    }
    
    public static int keyToX(final long k) {
        return (int)((k >> 16 & 0xFFFFFFFFFFFF0000L) | (k & 0xFFFFL));
    }
    
    public static int keyToZ(final long k) {
        return (int)((k >> 32 & 0xFFFF0000L) | (k >> 16 & 0xFFFFL));
    }
    
    public BiomeBase getBiome(final int i, final int j) {
        if (this.isLoaded(i, 0, j)) {
            final Chunk chunk = this.getChunkAtWorldCoords(i, j);
            if (chunk != null) {
                return chunk.a(i & 0xF, j & 0xF, this.worldProvider.d);
            }
        }
        return this.worldProvider.d.getBiome(i, j);
    }
    
    public WorldChunkManager getWorldChunkManager() {
        return this.worldProvider.d;
    }
    
    public CraftWorld getWorld() {
        return this.world;
    }
    
    public CraftServer getServer() {
        return (CraftServer)Bukkit.getServer();
    }
    
    public World(final IDataManager idatamanager, final String s, final WorldSettings worldsettings, final WorldProvider worldprovider, final MethodProfiler methodprofiler, final IConsoleLogManager iconsolelogmanager, final ChunkGenerator gen, final org.bukkit.World.Environment env) {
        this.d = false;
        this.entityList = new ArrayList();
        this.f = new ArrayList();
        this.tileEntityList = new HashSet();
        this.a = new ArrayList();
        this.b = new ArrayList();
        this.players = new ArrayList();
        this.i = new ArrayList();
        this.c = 16777215L;
        this.j = 0;
        this.k = new Random().nextInt();
        this.q = 0;
        this.callingPlaceEvent = false;
        this.random = new Random();
        this.u = new ArrayList();
        this.siegeManager = new VillageSiege(this);
        this.J = new Vec3DPool(300, 2000);
        this.K = Calendar.getInstance();
        this.scoreboard = new Scoreboard();
        this.M = new UnsafeList();
        this.allowMonsters = true;
        this.allowAnimals = true;
        this.growthOdds = 100.0f;
        this.modifiedOdds = 100.0f;
        this.keepSpawnInMemory = true;
        this.lastXAccessed = Integer.MIN_VALUE;
        this.lastZAccessed = Integer.MIN_VALUE;
        this.chunkLock = new Object();
        this.generator = gen;
        this.world = new CraftWorld((WorldServer)this, gen, env, s);
        this.ticksPerAnimalSpawns = this.getServer().getTicksPerAnimalSpawns();
        this.ticksPerMonsterSpawns = this.getServer().getTicksPerMonsterSpawns();
        this.chunkTickRadius = (byte)((this.getServer().getViewDistance() < 7) ? this.getServer().getViewDistance() : 7);
        (this.chunkTickList = new TLongShortHashMap(this.world.growthPerTick * 5, 0.7f, Long.MIN_VALUE, (short)(-32768))).setAutoCompactionFactor(0.0f);
        this.O = this.random.nextInt(12000);
        this.H = new int[32768];
        this.isStatic = false;
        this.dataManager = idatamanager;
        this.methodProfiler = methodprofiler;
        this.worldMaps = new WorldMapCollection(idatamanager);
        this.logAgent = iconsolelogmanager;
        this.worldData = idatamanager.getWorldData();
        if (worldprovider != null) {
            this.worldProvider = worldprovider;
        }
        else if (this.worldData != null && this.worldData.j() != 0) {
            this.worldProvider = WorldProvider.byDimension(this.worldData.j());
        }
        else {
            this.worldProvider = WorldProvider.byDimension(0);
        }
        if (this.worldData == null) {
            this.worldData = new WorldData(worldsettings, s);
        }
        else {
            this.worldData.setName(s);
        }
        this.worldProvider.a(this);
        this.chunkProvider = this.j();
        if (!this.worldData.isInitialized()) {
            try {
                this.a(worldsettings);
            }
            catch (Throwable throwable) {
                final CrashReport crashreport = CrashReport.a(throwable, "Exception initializing level");
                try {
                    this.a(crashreport);
                }
                catch (Throwable t) {}
                throw new ReportedException(crashreport);
            }
            this.worldData.d(true);
        }
        final VillageCollection villagecollection = (VillageCollection)this.worldMaps.get(VillageCollection.class, "villages");
        if (villagecollection == null) {
            this.villages = new VillageCollection(this);
            this.worldMaps.a("villages", this.villages);
        }
        else {
            (this.villages = villagecollection).a(this);
        }
        this.z();
        this.a();
        this.getServer().addWorld(this.world);
        this.timings = new SpigotTimings.WorldTimingsHandler(this);
    }
    
    protected abstract IChunkProvider j();
    
    protected void a(final WorldSettings worldsettings) {
        this.worldData.d(true);
    }
    
    public int b(final int i, final int j) {
        int k;
        for (k = 63; !this.isEmpty(i, k + 1, j); ++k) {}
        return this.getTypeId(i, k, j);
    }
    
    public int getTypeId(final int i, final int j, final int k) {
        if (i >= -30000000 && k >= -30000000 && i < 30000000 && k < 30000000) {
            if (j < 0) {
                return 0;
            }
            if (j >= 256) {
                return 0;
            }
            Chunk chunk = null;
            try {
                chunk = this.getChunkAt(i >> 4, k >> 4);
                return chunk.getTypeId(i & 0xF, j, k & 0xF);
            }
            catch (Throwable throwable) {
                final CrashReport crashreport = CrashReport.a(throwable, "Exception getting block type in world");
                final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Requested block coordinates");
                crashreportsystemdetails.a("Found chunk", chunk == null);
                crashreportsystemdetails.a("Location", CrashReportSystemDetails.a(i, j, k));
                throw new ReportedException(crashreport);
            }
        }
        return 0;
    }
    
    public boolean isEmpty(final int i, final int j, final int k) {
        return this.getTypeId(i, j, k) == 0;
    }
    
    public boolean isTileEntity(final int i, final int j, final int k) {
        final int l = this.getTypeId(i, j, k);
        return Block.byId[l] != null && Block.byId[l].t();
    }
    
    public int e(final int i, final int j, final int k) {
        final int l = this.getTypeId(i, j, k);
        return (Block.byId[l] != null) ? Block.byId[l].d() : -1;
    }
    
    public boolean isLoaded(final int i, final int j, final int k) {
        return j >= 0 && j < 256 && this.isChunkLoaded(i >> 4, k >> 4);
    }
    
    public boolean areChunksLoaded(final int i, final int j, final int k, final int l) {
        return this.e(i - l, j - l, k - l, i + l, j + l, k + l);
    }
    
    public boolean e(int i, final int j, int k, int l, final int i1, int j1) {
        if (i1 >= 0 && j < 256) {
            i >>= 4;
            k >>= 4;
            l >>= 4;
            j1 >>= 4;
            for (int k2 = i; k2 <= l; ++k2) {
                for (int l2 = k; l2 <= j1; ++l2) {
                    if (!this.isChunkLoaded(k2, l2) || ((WorldServer)this).chunkProviderServer.unloadQueue.contains(k2, l2)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    protected boolean isChunkLoaded(final int i, final int j) {
        return this.chunkProvider.isChunkLoaded(i, j);
    }
    
    public Chunk getChunkAtWorldCoords(final int i, final int j) {
        return this.getChunkAt(i >> 4, j >> 4);
    }
    
    public Chunk getChunkAt(final int i, final int j) {
        Chunk result = this.lastChunkAccessed;
        if (result == null || result.x != i || result.z != j) {
            result = this.chunkProvider.getOrCreateChunk(i, j);
            this.lastChunkAccessed = result;
        }
        return result;
    }
    
    public boolean setTypeIdAndData(final int i, final int j, final int k, final int l, final int i1, final int j1) {
        if (i < -30000000 || k < -30000000 || i >= 30000000 || k >= 30000000) {
            return false;
        }
        if (j < 0) {
            return false;
        }
        if (j >= 256) {
            return false;
        }
        final Chunk chunk = this.getChunkAt(i >> 4, k >> 4);
        int k2 = 0;
        if ((j1 & 0x1) != 0x0) {
            k2 = chunk.getTypeId(i & 0xF, j, k & 0xF);
        }
        final boolean flag = chunk.a(i & 0xF, j, k & 0xF, l, i1);
        this.methodProfiler.a("checkLight");
        this.A(i, j, k);
        this.methodProfiler.b();
        if (flag) {
            if ((j1 & 0x2) != 0x0 && (!this.isStatic || (j1 & 0x4) == 0x0)) {
                this.notify(i, j, k);
            }
            if (!this.isStatic && (j1 & 0x1) != 0x0) {
                this.update(i, j, k, k2);
                final Block block = Block.byId[l];
                if (block != null && block.q_()) {
                    this.m(i, j, k, l);
                }
            }
        }
        return flag;
    }
    
    public Material getMaterial(final int i, final int j, final int k) {
        final int l = this.getTypeId(i, j, k);
        return (l == 0) ? Material.AIR : Block.byId[l].material;
    }
    
    public int getData(int i, final int j, int k) {
        if (i < -30000000 || k < -30000000 || i >= 30000000 || k >= 30000000) {
            return 0;
        }
        if (j < 0) {
            return 0;
        }
        if (j >= 256) {
            return 0;
        }
        final Chunk chunk = this.getChunkAt(i >> 4, k >> 4);
        i &= 0xF;
        k &= 0xF;
        return chunk.getData(i, j, k);
    }
    
    public boolean setData(final int i, final int j, final int k, final int l, final int i1) {
        if (i < -30000000 || k < -30000000 || i >= 30000000 || k >= 30000000) {
            return false;
        }
        if (j < 0) {
            return false;
        }
        if (j >= 256) {
            return false;
        }
        final Chunk chunk = this.getChunkAt(i >> 4, k >> 4);
        final int j2 = i & 0xF;
        final int k2 = k & 0xF;
        final boolean flag = chunk.b(j2, j, k2, l);
        if (flag) {
            final int l2 = chunk.getTypeId(j2, j, k2);
            if ((i1 & 0x2) != 0x0 && (!this.isStatic || (i1 & 0x4) == 0x0)) {
                this.notify(i, j, k);
            }
            if (!this.isStatic && (i1 & 0x1) != 0x0) {
                this.update(i, j, k, l2);
                final Block block = Block.byId[l2];
                if (block != null && block.q_()) {
                    this.m(i, j, k, l2);
                }
            }
        }
        return flag;
    }
    
    public boolean setAir(final int i, final int j, final int k) {
        return this.setTypeIdAndData(i, j, k, 0, 0, 3);
    }
    
    public boolean setAir(final int i, final int j, final int k, final boolean flag) {
        final int l = this.getTypeId(i, j, k);
        if (l > 0) {
            final int i2 = this.getData(i, j, k);
            this.triggerEffect(2001, i, j, k, l + (i2 << 12));
            if (flag) {
                Block.byId[l].c(this, i, j, k, i2, 0);
            }
            return this.setTypeIdAndData(i, j, k, 0, 0, 3);
        }
        return false;
    }
    
    public boolean setTypeIdUpdate(final int i, final int j, final int k, final int l) {
        return this.setTypeIdAndData(i, j, k, l, 0, 3);
    }
    
    public void notify(final int i, final int j, final int k) {
        for (int l = 0; l < this.u.size(); ++l) {
            this.u.get(l).a(i, j, k);
        }
    }
    
    public void update(final int i, final int j, final int k, final int l) {
        this.applyPhysics(i, j, k, l);
    }
    
    public void e(final int i, final int j, int k, int l) {
        if (k > l) {
            final int i2 = l;
            l = k;
            k = i2;
        }
        if (!this.worldProvider.f) {
            for (int i2 = k; i2 <= l; ++i2) {
                this.c(EnumSkyBlock.SKY, i, i2, j);
            }
        }
        this.g(i, k, j, i, l, j);
    }
    
    public void g(final int i, final int j, final int k, final int l, final int i1, final int j1) {
        for (int k2 = 0; k2 < this.u.size(); ++k2) {
            this.u.get(k2).a(i, j, k, l, i1, j1);
        }
    }
    
    public void applyPhysics(final int i, final int j, final int k, final int l) {
        this.g(i - 1, j, k, l);
        this.g(i + 1, j, k, l);
        this.g(i, j - 1, k, l);
        this.g(i, j + 1, k, l);
        this.g(i, j, k - 1, l);
        this.g(i, j, k + 1, l);
    }
    
    public void c(final int i, final int j, final int k, final int l, final int i1) {
        if (i1 != 4) {
            this.g(i - 1, j, k, l);
        }
        if (i1 != 5) {
            this.g(i + 1, j, k, l);
        }
        if (i1 != 0) {
            this.g(i, j - 1, k, l);
        }
        if (i1 != 1) {
            this.g(i, j + 1, k, l);
        }
        if (i1 != 2) {
            this.g(i, j, k - 1, l);
        }
        if (i1 != 3) {
            this.g(i, j, k + 1, l);
        }
    }
    
    public void g(final int i, final int j, final int k, final int l) {
        if (!this.isStatic) {
            final int i2 = this.getTypeId(i, j, k);
            final Block block = Block.byId[i2];
            if (block != null) {
                try {
                    final CraftWorld world = ((WorldServer)this).getWorld();
                    if (world != null) {
                        final BlockPhysicsEvent event = new BlockPhysicsEvent(world.getBlockAt(i, j, k), l);
                        this.getServer().getPluginManager().callEvent(event);
                        if (event.isCancelled()) {
                            return;
                        }
                    }
                    block.doPhysics(this, i, j, k, l);
                }
                catch (Throwable throwable) {
                    final CrashReport crashreport = CrashReport.a(throwable, "Exception while updating neighbours");
                    final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being updated");
                    int j2;
                    try {
                        j2 = this.getData(i, j, k);
                    }
                    catch (Throwable throwable2) {
                        j2 = -1;
                    }
                    crashreportsystemdetails.a("Source block type", new CrashReportSourceBlockType(this, l));
                    CrashReportSystemDetails.a(crashreportsystemdetails, i, j, k, i2, j2);
                    throw new ReportedException(crashreport);
                }
            }
        }
    }
    
    public boolean a(final int i, final int j, final int k, final int l) {
        return false;
    }
    
    public boolean l(final int i, final int j, final int k) {
        return this.getChunkAt(i >> 4, k >> 4).d(i & 0xF, j, k & 0xF);
    }
    
    public int m(final int i, int j, final int k) {
        if (j < 0) {
            return 0;
        }
        if (j >= 256) {
            j = 255;
        }
        return this.getChunkAt(i >> 4, k >> 4).c(i & 0xF, j, k & 0xF, 0);
    }
    
    public int getLightLevel(final int i, final int j, final int k) {
        return this.b(i, j, k, true);
    }
    
    public int b(int i, int j, int k, final boolean flag) {
        if (i < -30000000 || k < -30000000 || i >= 30000000 || k >= 30000000) {
            return 15;
        }
        if (flag) {
            final int l = this.getTypeId(i, j, k);
            if (Block.w[l]) {
                int i2 = this.b(i, j + 1, k, false);
                final int j2 = this.b(i + 1, j, k, false);
                final int k2 = this.b(i - 1, j, k, false);
                final int l2 = this.b(i, j, k + 1, false);
                final int i3 = this.b(i, j, k - 1, false);
                if (j2 > i2) {
                    i2 = j2;
                }
                if (k2 > i2) {
                    i2 = k2;
                }
                if (l2 > i2) {
                    i2 = l2;
                }
                if (i3 > i2) {
                    i2 = i3;
                }
                return i2;
            }
        }
        if (j < 0) {
            return 0;
        }
        if (j >= 256) {
            j = 255;
        }
        final Chunk chunk = this.getChunkAt(i >> 4, k >> 4);
        i &= 0xF;
        k &= 0xF;
        return chunk.c(i, j, k, this.j);
    }
    
    public int getHighestBlockYAt(final int i, final int j) {
        if (i < -30000000 || j < -30000000 || i >= 30000000 || j >= 30000000) {
            return 0;
        }
        if (!this.isChunkLoaded(i >> 4, j >> 4)) {
            return 0;
        }
        final Chunk chunk = this.getChunkAt(i >> 4, j >> 4);
        return chunk.b(i & 0xF, j & 0xF);
    }
    
    public int g(final int i, final int j) {
        if (i < -30000000 || j < -30000000 || i >= 30000000 || j >= 30000000) {
            return 0;
        }
        if (!this.isChunkLoaded(i >> 4, j >> 4)) {
            return 0;
        }
        final Chunk chunk = this.getChunkAt(i >> 4, j >> 4);
        return chunk.p;
    }
    
    public int b(final EnumSkyBlock enumskyblock, final int i, int j, final int k) {
        if (j < 0) {
            j = 0;
        }
        if (j >= 256) {
            j = 255;
        }
        if (i < -30000000 || k < -30000000 || i >= 30000000 || k >= 30000000) {
            return enumskyblock.c;
        }
        final int l = i >> 4;
        final int i2 = k >> 4;
        if (!this.isChunkLoaded(l, i2)) {
            return enumskyblock.c;
        }
        final Chunk chunk = this.getChunkAt(l, i2);
        return chunk.getBrightness(enumskyblock, i & 0xF, j, k & 0xF);
    }
    
    public void b(final EnumSkyBlock enumskyblock, final int i, final int j, final int k, final int l) {
        if (i >= -30000000 && k >= -30000000 && i < 30000000 && k < 30000000 && j >= 0 && j < 256 && this.isChunkLoaded(i >> 4, k >> 4)) {
            final Chunk chunk = this.getChunkAt(i >> 4, k >> 4);
            chunk.a(enumskyblock, i & 0xF, j, k & 0xF, l);
            for (int i2 = 0; i2 < this.u.size(); ++i2) {
                this.u.get(i2).b(i, j, k);
            }
        }
    }
    
    public void p(final int i, final int j, final int k) {
        for (int l = 0; l < this.u.size(); ++l) {
            this.u.get(l).b(i, j, k);
        }
    }
    
    public float q(final int i, final int j, final int k) {
        return this.worldProvider.g[this.getLightLevel(i, j, k)];
    }
    
    public boolean v() {
        return this.j < 4;
    }
    
    public MovingObjectPosition a(final Vec3D vec3d, final Vec3D vec3d1) {
        return this.rayTrace(vec3d, vec3d1, false, false);
    }
    
    public MovingObjectPosition rayTrace(final Vec3D vec3d, final Vec3D vec3d1, final boolean flag) {
        return this.rayTrace(vec3d, vec3d1, flag, false);
    }
    
    public MovingObjectPosition rayTrace(final Vec3D vec3d, final Vec3D vec3d1, final boolean flag, final boolean flag1) {
        if (Double.isNaN(vec3d.c) || Double.isNaN(vec3d.d) || Double.isNaN(vec3d.e)) {
            return null;
        }
        if (!Double.isNaN(vec3d1.c) && !Double.isNaN(vec3d1.d) && !Double.isNaN(vec3d1.e)) {
            final int i = MathHelper.floor(vec3d1.c);
            final int j = MathHelper.floor(vec3d1.d);
            final int k = MathHelper.floor(vec3d1.e);
            int l = MathHelper.floor(vec3d.c);
            int i2 = MathHelper.floor(vec3d.d);
            int j2 = MathHelper.floor(vec3d.e);
            int k2 = this.getTypeId(l, i2, j2);
            final int l2 = this.getData(l, i2, j2);
            final Block block = Block.byId[k2];
            if ((!flag1 || block == null || block.b(this, l, i2, j2) != null) && k2 > 0 && block.a(l2, flag)) {
                final MovingObjectPosition movingobjectposition = block.a(this, l, i2, j2, vec3d, vec3d1);
                if (movingobjectposition != null) {
                    return movingobjectposition;
                }
            }
            k2 = 200;
            while (k2-- >= 0) {
                if (Double.isNaN(vec3d.c) || Double.isNaN(vec3d.d) || Double.isNaN(vec3d.e)) {
                    return null;
                }
                if (l == i && i2 == j && j2 == k) {
                    return null;
                }
                boolean flag2 = true;
                boolean flag3 = true;
                boolean flag4 = true;
                double d0 = 999.0;
                double d2 = 999.0;
                double d3 = 999.0;
                if (i > l) {
                    d0 = l + 1.0;
                }
                else if (i < l) {
                    d0 = l + 0.0;
                }
                else {
                    flag2 = false;
                }
                if (j > i2) {
                    d2 = i2 + 1.0;
                }
                else if (j < i2) {
                    d2 = i2 + 0.0;
                }
                else {
                    flag3 = false;
                }
                if (k > j2) {
                    d3 = j2 + 1.0;
                }
                else if (k < j2) {
                    d3 = j2 + 0.0;
                }
                else {
                    flag4 = false;
                }
                double d4 = 999.0;
                double d5 = 999.0;
                double d6 = 999.0;
                final double d7 = vec3d1.c - vec3d.c;
                final double d8 = vec3d1.d - vec3d.d;
                final double d9 = vec3d1.e - vec3d.e;
                if (flag2) {
                    d4 = (d0 - vec3d.c) / d7;
                }
                if (flag3) {
                    d5 = (d2 - vec3d.d) / d8;
                }
                if (flag4) {
                    d6 = (d3 - vec3d.e) / d9;
                }
                final boolean flag5 = false;
                byte b0;
                if (d4 < d5 && d4 < d6) {
                    if (i > l) {
                        b0 = 4;
                    }
                    else {
                        b0 = 5;
                    }
                    vec3d.c = d0;
                    vec3d.d += d8 * d4;
                    vec3d.e += d9 * d4;
                }
                else if (d5 < d6) {
                    if (j > i2) {
                        b0 = 0;
                    }
                    else {
                        b0 = 1;
                    }
                    vec3d.c += d7 * d5;
                    vec3d.d = d2;
                    vec3d.e += d9 * d5;
                }
                else {
                    if (k > j2) {
                        b0 = 2;
                    }
                    else {
                        b0 = 3;
                    }
                    vec3d.c += d7 * d6;
                    vec3d.d += d8 * d6;
                    vec3d.e = d3;
                }
                final Vec3D create;
                final Vec3D vec3d2 = create = this.getVec3DPool().create(vec3d.c, vec3d.d, vec3d.e);
                final double c = MathHelper.floor(vec3d.c);
                create.c = c;
                l = (int)c;
                if (b0 == 5) {
                    --l;
                    final Vec3D vec3D = vec3d2;
                    ++vec3D.c;
                }
                final Vec3D vec3D2 = vec3d2;
                final double d10 = MathHelper.floor(vec3d.d);
                vec3D2.d = d10;
                i2 = (int)d10;
                if (b0 == 1) {
                    --i2;
                    final Vec3D vec3D3 = vec3d2;
                    ++vec3D3.d;
                }
                final Vec3D vec3D4 = vec3d2;
                final double e = MathHelper.floor(vec3d.e);
                vec3D4.e = e;
                j2 = (int)e;
                if (b0 == 3) {
                    --j2;
                    final Vec3D vec3D5 = vec3d2;
                    ++vec3D5.e;
                }
                final int i3 = this.getTypeId(l, i2, j2);
                final int j3 = this.getData(l, i2, j2);
                final Block block2 = Block.byId[i3];
                if ((!flag1 || block2 == null || block2.b(this, l, i2, j2) != null) && i3 > 0 && block2.a(j3, flag)) {
                    final MovingObjectPosition movingobjectposition2 = block2.a(this, l, i2, j2, vec3d, vec3d1);
                    if (movingobjectposition2 != null) {
                        vec3d2.b.release(vec3d2);
                        return movingobjectposition2;
                    }
                }
                vec3d2.b.release(vec3d2);
            }
            return null;
        }
        return null;
    }
    
    public void makeSound(final Entity entity, final String s, final float f, final float f1) {
        if (entity != null && s != null) {
            for (int i = 0; i < this.u.size(); ++i) {
                this.u.get(i).a(s, entity.locX, entity.locY - entity.height, entity.locZ, f, f1);
            }
        }
    }
    
    public void a(final EntityHuman entityhuman, final String s, final float f, final float f1) {
        if (entityhuman != null && s != null) {
            for (int i = 0; i < this.u.size(); ++i) {
                this.u.get(i).a(entityhuman, s, entityhuman.locX, entityhuman.locY - entityhuman.height, entityhuman.locZ, f, f1);
            }
        }
    }
    
    public void makeSound(final double d0, final double d1, final double d2, final String s, final float f, final float f1) {
        if (s != null) {
            for (int i = 0; i < this.u.size(); ++i) {
                this.u.get(i).a(s, d0, d1, d2, f, f1);
            }
        }
    }
    
    public void a(final double d0, final double d1, final double d2, final String s, final float f, final float f1, final boolean flag) {
    }
    
    public void a(final String s, final int i, final int j, final int k) {
        for (int l = 0; l < this.u.size(); ++l) {
            this.u.get(l).a(s, i, j, k);
        }
    }
    
    public void addParticle(final String s, final double d0, final double d1, final double d2, final double d3, final double d4, final double d5) {
        for (int i = 0; i < this.u.size(); ++i) {
            this.u.get(i).a(s, d0, d1, d2, d3, d4, d5);
        }
    }
    
    public boolean strikeLightning(final Entity entity) {
        this.i.add(entity);
        return true;
    }
    
    public boolean addEntity(final Entity entity) {
        return this.addEntity(entity, CreatureSpawnEvent.SpawnReason.DEFAULT);
    }
    
    public boolean addEntity(final Entity entity, final CreatureSpawnEvent.SpawnReason spawnReason) {
        if (Thread.currentThread() != MinecraftServer.getServer().primaryThread) {
            throw new IllegalStateException("Asynchronous entity add!");
        }
        if (entity == null) {
            return false;
        }
        final int i = MathHelper.floor(entity.locX / 16.0);
        final int j = MathHelper.floor(entity.locZ / 16.0);
        boolean flag = entity.p;
        if (entity instanceof EntityHuman) {
            flag = true;
        }
        Cancellable event = null;
        if (entity instanceof EntityLiving && !(entity instanceof EntityPlayer)) {
            final boolean isAnimal = entity instanceof EntityAnimal || entity instanceof EntityWaterAnimal || entity instanceof EntityGolem;
            final boolean isMonster = entity instanceof EntityMonster || entity instanceof EntityGhast || entity instanceof EntitySlime;
            if (spawnReason != CreatureSpawnEvent.SpawnReason.CUSTOM && ((isAnimal && !this.allowAnimals) || (isMonster && !this.allowMonsters))) {
                entity.dead = true;
                return false;
            }
            event = CraftEventFactory.callCreatureSpawnEvent((EntityLiving)entity, spawnReason);
        }
        else if (entity instanceof EntityItem) {
            event = CraftEventFactory.callItemSpawnEvent((EntityItem)entity);
        }
        else if (entity.getBukkitEntity() instanceof Projectile) {
            event = CraftEventFactory.callProjectileLaunchEvent(entity);
        }
        else if (entity instanceof EntityExperienceOrb) {
            final EntityExperienceOrb xp = (EntityExperienceOrb)entity;
            final double radius = this.getWorld().expMergeRadius;
            if (radius > 0.0) {
                final List<Entity> entities = (List<Entity>)this.getEntities(entity, entity.boundingBox.grow(radius, radius, radius));
                for (final Entity e : entities) {
                    if (e instanceof EntityExperienceOrb) {
                        final EntityExperienceOrb loopItem = (EntityExperienceOrb)e;
                        if (loopItem.dead) {
                            continue;
                        }
                        final EntityExperienceOrb entityExperienceOrb = xp;
                        entityExperienceOrb.value += loopItem.value;
                        loopItem.die();
                    }
                }
            }
        }
        if (event != null && (event.isCancelled() || entity.dead)) {
            entity.dead = true;
            return false;
        }
        if (!flag && !this.isChunkLoaded(i, j)) {
            entity.dead = true;
            return false;
        }
        if (entity instanceof EntityHuman) {
            final EntityHuman entityhuman = (EntityHuman)entity;
            this.players.add(entityhuman);
            this.everyoneSleeping();
        }
        this.getChunkAt(i, j).a(entity);
        this.entityList.add(entity);
        this.a(entity);
        return true;
    }
    
    protected void a(final Entity entity) {
        for (int i = 0; i < this.u.size(); ++i) {
            this.u.get(i).a(entity);
        }
        entity.valid = true;
    }
    
    protected void b(final Entity entity) {
        for (int i = 0; i < this.u.size(); ++i) {
            this.u.get(i).b(entity);
        }
        entity.valid = false;
    }
    
    public void kill(final Entity entity) {
        if (entity.passenger != null) {
            entity.passenger.mount(null);
        }
        if (entity.vehicle != null) {
            entity.mount(null);
        }
        entity.die();
        if (entity instanceof EntityHuman) {
            this.players.remove(entity);
            this.everyoneSleeping();
        }
    }
    
    public void removeEntity(final Entity entity) {
        if (Thread.currentThread() != MinecraftServer.getServer().primaryThread) {
            throw new IllegalStateException("Asynchronous entity remove!");
        }
        entity.die();
        if (entity instanceof EntityHuman) {
            this.players.remove(entity);
            this.everyoneSleeping();
        }
        final int i = entity.aj;
        final int j = entity.al;
        if (entity.ai && this.isChunkLoaded(i, j)) {
            this.getChunkAt(i, j).b(entity);
        }
        this.entityList.remove(entity);
        this.b(entity);
    }
    
    public void addIWorldAccess(final IWorldAccess iworldaccess) {
        this.u.add(iworldaccess);
    }
    
    public List getCubes(final Entity entity, final AxisAlignedBB axisalignedbb) {
        this.M.clear();
        final int i = MathHelper.floor(axisalignedbb.a);
        final int j = MathHelper.floor(axisalignedbb.d + 1.0);
        final int k = MathHelper.floor(axisalignedbb.b);
        final int l = MathHelper.floor(axisalignedbb.e + 1.0);
        final int i2 = MathHelper.floor(axisalignedbb.c);
        final int j2 = MathHelper.floor(axisalignedbb.f + 1.0);
        final int ystart = (k - 1 < 0) ? 0 : (k - 1);
        for (int chunkx = i >> 4; chunkx <= j - 1 >> 4; ++chunkx) {
            final int cx = chunkx << 4;
            for (int chunkz = i2 >> 4; chunkz <= j2 - 1 >> 4; ++chunkz) {
                if (this.isChunkLoaded(chunkx, chunkz)) {
                    final int cz = chunkz << 4;
                    final Chunk chunk = this.getChunkAt(chunkx, chunkz);
                    final int xstart = (i < cx) ? cx : i;
                    final int xend = (j < cx + 16) ? j : (cx + 16);
                    final int zstart = (i2 < cz) ? cz : i2;
                    final int zend = (j2 < cz + 16) ? j2 : (cz + 16);
                    for (int x = xstart; x < xend; ++x) {
                        for (int z = zstart; z < zend; ++z) {
                            for (int y = ystart; y < l; ++y) {
                                final int blkid = chunk.getTypeId(x - cx, y, z - cz);
                                if (blkid > 0) {
                                    final Block block = Block.byId[blkid];
                                    if (block != null) {
                                        block.a(this, x, y, z, axisalignedbb, this.M, entity);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        final double d0 = 0.25;
        final List list = this.getEntities(entity, axisalignedbb.grow(d0, d0, d0));
        for (int j3 = 0; j3 < list.size(); ++j3) {
            AxisAlignedBB axisalignedbb2 = list.get(j3).D();
            if (axisalignedbb2 != null && axisalignedbb2.a(axisalignedbb)) {
                this.M.add(axisalignedbb2);
            }
            axisalignedbb2 = entity.g(list.get(j3));
            if (axisalignedbb2 != null && axisalignedbb2.a(axisalignedbb)) {
                this.M.add(axisalignedbb2);
            }
        }
        return this.M;
    }
    
    public List a(final AxisAlignedBB axisalignedbb) {
        this.M.clear();
        final int i = MathHelper.floor(axisalignedbb.a);
        final int j = MathHelper.floor(axisalignedbb.d + 1.0);
        final int k = MathHelper.floor(axisalignedbb.b);
        final int l = MathHelper.floor(axisalignedbb.e + 1.0);
        final int i2 = MathHelper.floor(axisalignedbb.c);
        final int j2 = MathHelper.floor(axisalignedbb.f + 1.0);
        for (int k2 = i; k2 < j; ++k2) {
            for (int l2 = i2; l2 < j2; ++l2) {
                if (this.isLoaded(k2, 64, l2)) {
                    for (int i3 = k - 1; i3 < l; ++i3) {
                        final Block block = Block.byId[this.getTypeId(k2, i3, l2)];
                        if (block != null) {
                            block.a(this, k2, i3, l2, axisalignedbb, this.M, null);
                        }
                    }
                }
            }
        }
        return this.M;
    }
    
    public int a(final float f) {
        final float f2 = this.c(f);
        float f3 = 1.0f - (MathHelper.cos(f2 * 3.1415927f * 2.0f) * 2.0f + 0.5f);
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f3 > 1.0f) {
            f3 = 1.0f;
        }
        f3 = 1.0f - f3;
        f3 *= (float)(1.0 - this.i(f) * 5.0f / 16.0);
        f3 *= (float)(1.0 - this.h(f) * 5.0f / 16.0);
        f3 = 1.0f - f3;
        return (int)(f3 * 11.0f);
    }
    
    public float c(final float f) {
        return this.worldProvider.a(this.worldData.getDayTime(), f);
    }
    
    public int w() {
        return this.worldProvider.a(this.worldData.getDayTime());
    }
    
    public float d(final float f) {
        final float f2 = this.c(f);
        return f2 * 3.1415927f * 2.0f;
    }
    
    public int h(final int i, final int j) {
        return this.getChunkAtWorldCoords(i, j).d(i & 0xF, j & 0xF);
    }
    
    public int i(int i, int j) {
        final Chunk chunk = this.getChunkAtWorldCoords(i, j);
        int k = chunk.h() + 15;
        i &= 0xF;
        j &= 0xF;
        while (k > 0) {
            final int l = chunk.getTypeId(i, k, j);
            if (l != 0 && Block.byId[l].material.isSolid() && Block.byId[l].material != Material.LEAVES) {
                return k + 1;
            }
            --k;
        }
        return -1;
    }
    
    public void a(final int i, final int j, final int k, final int l, final int i1) {
    }
    
    public void a(final int i, final int j, final int k, final int l, final int i1, final int j1) {
    }
    
    public void b(final int i, final int j, final int k, final int l, final int i1, final int j1) {
    }
    
    public void tickEntities() {
        this.methodProfiler.a("entities");
        this.methodProfiler.a("global");
        for (int i = 0; i < this.i.size(); ++i) {
            final Entity entity = this.i.get(i);
            if (entity != null) {
                final ChunkProviderServer chunkProviderServer = ((WorldServer)this).chunkProviderServer;
                if (!chunkProviderServer.unloadQueue.contains(MathHelper.floor(entity.locX) >> 4, MathHelper.floor(entity.locZ) >> 4)) {
                    try {
                        final Entity entity2 = entity;
                        ++entity2.ticksLived;
                        entity.l_();
                    }
                    catch (Throwable throwable) {
                        final CrashReport crashreport = CrashReport.a(throwable, "Ticking entity");
                        final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being ticked");
                        if (entity == null) {
                            crashreportsystemdetails.a("Entity", "~~NULL~~");
                        }
                        else {
                            entity.a(crashreportsystemdetails);
                        }
                        throw new ReportedException(crashreport);
                    }
                    if (entity.dead) {
                        this.i.remove(i--);
                    }
                }
            }
        }
        this.methodProfiler.c("remove");
        this.entityList.removeAll(this.f);
        for (int i = 0; i < this.f.size(); ++i) {
            final Entity entity = this.f.get(i);
            final int j = entity.aj;
            final int k = entity.al;
            if (entity.ai && this.isChunkLoaded(j, k)) {
                this.getChunkAt(j, k).b(entity);
            }
        }
        for (int i = 0; i < this.f.size(); ++i) {
            this.b(this.f.get(i));
        }
        this.f.clear();
        this.methodProfiler.c("regular");
        Spigot.activateEntities(this);
        this.timings.entityTick.startTiming();
        for (int i = 0; i < this.entityList.size(); ++i) {
            final Entity entity = this.entityList.get(i);
            final ChunkProviderServer chunkProviderServer2 = ((WorldServer)this).chunkProviderServer;
            if (!chunkProviderServer2.unloadQueue.contains(MathHelper.floor(entity.locX) >> 4, MathHelper.floor(entity.locZ) >> 4)) {
                if (entity.vehicle != null) {
                    if (!entity.vehicle.dead && entity.vehicle.passenger == entity) {
                        continue;
                    }
                    entity.vehicle.passenger = null;
                    entity.vehicle = null;
                }
                this.methodProfiler.a("tick");
                if (!entity.dead) {
                    try {
                        SpigotTimings.tickEntityTimer.startTiming();
                        this.playerJoinedWorld(entity);
                        SpigotTimings.tickEntityTimer.stopTiming();
                    }
                    catch (Throwable throwable2) {
                        final CrashReport crashreport = CrashReport.a(throwable2, "Ticking entity");
                        final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being ticked");
                        entity.a(crashreportsystemdetails);
                        throw new ReportedException(crashreport);
                    }
                }
                this.methodProfiler.b();
                this.methodProfiler.a("remove");
                if (entity.dead) {
                    final int j = entity.aj;
                    final int k = entity.al;
                    if (entity.ai && this.isChunkLoaded(j, k)) {
                        this.getChunkAt(j, k).b(entity);
                    }
                    this.entityList.remove(i--);
                    this.b(entity);
                }
                this.methodProfiler.b();
            }
        }
        this.timings.entityTick.stopTiming();
        this.methodProfiler.c("tileEntities");
        this.timings.tileEntityTick.startTiming();
        this.N = true;
        final Iterator iterator = this.tileEntityList.iterator();
        while (iterator.hasNext()) {
            final TileEntity tileentity = iterator.next();
            if (tileentity == null) {
                this.getServer().getLogger().severe("Spigot has detected a null entity and has removed it, preventing a crash");
                iterator.remove();
            }
            else {
                final ChunkProviderServer chunkProviderServer3 = ((WorldServer)this).chunkProviderServer;
                if (chunkProviderServer3.unloadQueue.contains(tileentity.x >> 4, tileentity.z >> 4)) {
                    continue;
                }
                if (!tileentity.r() && tileentity.o() && this.isLoaded(tileentity.x, tileentity.y, tileentity.z)) {
                    try {
                        tileentity.tickTimer.startTiming();
                        tileentity.h();
                        tileentity.tickTimer.stopTiming();
                    }
                    catch (Throwable throwable3) {
                        tileentity.tickTimer.stopTiming();
                        final CrashReport crashreport = CrashReport.a(throwable3, "Ticking tile entity");
                        final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Tile entity being ticked");
                        tileentity.a(crashreportsystemdetails);
                        throw new ReportedException(crashreport);
                    }
                }
                if (!tileentity.r()) {
                    continue;
                }
                iterator.remove();
                if (!this.isChunkLoaded(tileentity.x >> 4, tileentity.z >> 4)) {
                    continue;
                }
                final Chunk chunk = this.getChunkAt(tileentity.x >> 4, tileentity.z >> 4);
                if (chunk == null) {
                    continue;
                }
                chunk.f(tileentity.x & 0xF, tileentity.y, tileentity.z & 0xF);
            }
        }
        this.timings.tileEntityTick.stopTiming();
        this.timings.tileEntityPending.startTiming();
        this.N = false;
        if (!this.b.isEmpty()) {
            this.tileEntityList.removeAll(this.b);
            this.b.clear();
        }
        this.methodProfiler.c("pendingTileEntities");
        if (!this.a.isEmpty()) {
            for (int l = 0; l < this.a.size(); ++l) {
                final TileEntity tileentity2 = this.a.get(l);
                if (!tileentity2.r()) {
                    if (this.isChunkLoaded(tileentity2.x >> 4, tileentity2.z >> 4)) {
                        final Chunk chunk2 = this.getChunkAt(tileentity2.x >> 4, tileentity2.z >> 4);
                        if (chunk2 != null) {
                            chunk2.a(tileentity2.x & 0xF, tileentity2.y, tileentity2.z & 0xF, tileentity2);
                            if (!this.tileEntityList.contains(tileentity2)) {
                                this.tileEntityList.add(tileentity2);
                            }
                        }
                    }
                    this.notify(tileentity2.x, tileentity2.y, tileentity2.z);
                }
            }
            this.a.clear();
        }
        this.timings.tileEntityPending.stopTiming();
        this.methodProfiler.b();
        this.methodProfiler.b();
    }
    
    public void a(final Collection collection) {
        if (this.N) {
            this.a.addAll(collection);
        }
        else {
            this.tileEntityList.addAll(collection);
        }
    }
    
    public void playerJoinedWorld(final Entity entity) {
        this.entityJoinedWorld(entity, true);
    }
    
    public void entityJoinedWorld(final Entity entity, final boolean flag) {
        final int i = MathHelper.floor(entity.locX);
        final int j = MathHelper.floor(entity.locZ);
        final byte b0 = 32;
        if (!Spigot.checkIfActive(entity)) {
            ++entity.ticksLived;
            entity.inactiveTick();
        }
        else {
            entity.tickTimer.startTiming();
            entity.U = entity.locX;
            entity.V = entity.locY;
            entity.W = entity.locZ;
            entity.lastYaw = entity.yaw;
            entity.lastPitch = entity.pitch;
            if (flag && entity.ai) {
                if (entity.vehicle != null) {
                    entity.T();
                }
                else {
                    ++entity.ticksLived;
                    entity.l_();
                }
            }
            this.methodProfiler.a("chunkCheck");
            if (Double.isNaN(entity.locX) || Double.isInfinite(entity.locX)) {
                entity.locX = entity.U;
            }
            if (Double.isNaN(entity.locY) || Double.isInfinite(entity.locY)) {
                entity.locY = entity.V;
            }
            if (Double.isNaN(entity.locZ) || Double.isInfinite(entity.locZ)) {
                entity.locZ = entity.W;
            }
            if (Double.isNaN(entity.pitch) || Double.isInfinite(entity.pitch)) {
                entity.pitch = entity.lastPitch;
            }
            if (Double.isNaN(entity.yaw) || Double.isInfinite(entity.yaw)) {
                entity.yaw = entity.lastYaw;
            }
            final int k = MathHelper.floor(entity.locX / 16.0);
            final int l = MathHelper.floor(entity.locY / 16.0);
            final int i2 = MathHelper.floor(entity.locZ / 16.0);
            if (!entity.ai || entity.aj != k || entity.ak != l || entity.al != i2) {
                if (entity.ai && this.isChunkLoaded(entity.aj, entity.al)) {
                    this.getChunkAt(entity.aj, entity.al).a(entity, entity.ak);
                }
                if (this.isChunkLoaded(k, i2)) {
                    entity.ai = true;
                    this.getChunkAt(k, i2).a(entity);
                }
                else {
                    entity.ai = false;
                }
            }
            this.methodProfiler.b();
            if (flag && entity.ai && entity.passenger != null) {
                if (!entity.passenger.dead && entity.passenger.vehicle == entity) {
                    this.playerJoinedWorld(entity.passenger);
                }
                else {
                    entity.passenger.vehicle = null;
                    entity.passenger = null;
                }
            }
            entity.tickTimer.stopTiming();
        }
    }
    
    public boolean b(final AxisAlignedBB axisalignedbb) {
        return this.a(axisalignedbb, (Entity)null);
    }
    
    public boolean a(final AxisAlignedBB axisalignedbb, final Entity entity) {
        final List list = this.getEntities(null, axisalignedbb);
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity2 = list.get(i);
            if (!entity2.dead && entity2.m && entity2 != entity) {
                return false;
            }
        }
        return true;
    }
    
    public boolean c(final AxisAlignedBB axisalignedbb) {
        int i = MathHelper.floor(axisalignedbb.a);
        final int j = MathHelper.floor(axisalignedbb.d + 1.0);
        int k = MathHelper.floor(axisalignedbb.b);
        final int l = MathHelper.floor(axisalignedbb.e + 1.0);
        int i2 = MathHelper.floor(axisalignedbb.c);
        final int j2 = MathHelper.floor(axisalignedbb.f + 1.0);
        if (axisalignedbb.a < 0.0) {
            --i;
        }
        if (axisalignedbb.b < 0.0) {
            --k;
        }
        if (axisalignedbb.c < 0.0) {
            --i2;
        }
        for (int k2 = i; k2 < j; ++k2) {
            for (int l2 = k; l2 < l; ++l2) {
                for (int i3 = i2; i3 < j2; ++i3) {
                    final Block block = Block.byId[this.getTypeId(k2, l2, i3)];
                    if (block != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean containsLiquid(final AxisAlignedBB axisalignedbb) {
        int i = MathHelper.floor(axisalignedbb.a);
        final int j = MathHelper.floor(axisalignedbb.d + 1.0);
        int k = MathHelper.floor(axisalignedbb.b);
        final int l = MathHelper.floor(axisalignedbb.e + 1.0);
        int i2 = MathHelper.floor(axisalignedbb.c);
        final int j2 = MathHelper.floor(axisalignedbb.f + 1.0);
        if (axisalignedbb.a < 0.0) {
            --i;
        }
        if (axisalignedbb.b < 0.0) {
            --k;
        }
        if (axisalignedbb.c < 0.0) {
            --i2;
        }
        for (int k2 = i; k2 < j; ++k2) {
            for (int l2 = k; l2 < l; ++l2) {
                for (int i3 = i2; i3 < j2; ++i3) {
                    final Block block = Block.byId[this.getTypeId(k2, l2, i3)];
                    if (block != null && block.material.isLiquid()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean e(final AxisAlignedBB axisalignedbb) {
        final int i = MathHelper.floor(axisalignedbb.a);
        final int j = MathHelper.floor(axisalignedbb.d + 1.0);
        final int k = MathHelper.floor(axisalignedbb.b);
        final int l = MathHelper.floor(axisalignedbb.e + 1.0);
        final int i2 = MathHelper.floor(axisalignedbb.c);
        final int j2 = MathHelper.floor(axisalignedbb.f + 1.0);
        if (this.e(i, k, i2, j, l, j2)) {
            for (int k2 = i; k2 < j; ++k2) {
                for (int l2 = k; l2 < l; ++l2) {
                    for (int i3 = i2; i3 < j2; ++i3) {
                        final int j3 = this.getTypeId(k2, l2, i3);
                        if (j3 == Block.FIRE.id || j3 == Block.LAVA.id || j3 == Block.STATIONARY_LAVA.id) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public boolean a(final AxisAlignedBB axisalignedbb, final Material material, final Entity entity) {
        final int i = MathHelper.floor(axisalignedbb.a);
        final int j = MathHelper.floor(axisalignedbb.d + 1.0);
        final int k = MathHelper.floor(axisalignedbb.b);
        final int l = MathHelper.floor(axisalignedbb.e + 1.0);
        final int i2 = MathHelper.floor(axisalignedbb.c);
        final int j2 = MathHelper.floor(axisalignedbb.f + 1.0);
        if (!this.e(i, k, i2, j, l, j2)) {
            return false;
        }
        boolean flag = false;
        Vec3D vec3d = this.getVec3DPool().create(0.0, 0.0, 0.0);
        for (int k2 = i; k2 < j; ++k2) {
            for (int l2 = k; l2 < l; ++l2) {
                for (int i3 = i2; i3 < j2; ++i3) {
                    final Block block = Block.byId[this.getTypeId(k2, l2, i3)];
                    if (block != null && block.material == material) {
                        final double d0 = l2 + 1 - BlockFluids.d(this.getData(k2, l2, i3));
                        if (l >= d0) {
                            flag = true;
                            block.a(this, k2, l2, i3, entity, vec3d);
                        }
                    }
                }
            }
        }
        if (vec3d.b() > 0.0 && entity.aw()) {
            vec3d = vec3d.a();
            final double d2 = 0.014;
            entity.motX += vec3d.c * d2;
            entity.motY += vec3d.d * d2;
            entity.motZ += vec3d.e * d2;
        }
        vec3d.b.release(vec3d);
        return flag;
    }
    
    public boolean a(final AxisAlignedBB axisalignedbb, final Material material) {
        final int i = MathHelper.floor(axisalignedbb.a);
        final int j = MathHelper.floor(axisalignedbb.d + 1.0);
        final int k = MathHelper.floor(axisalignedbb.b);
        final int l = MathHelper.floor(axisalignedbb.e + 1.0);
        final int i2 = MathHelper.floor(axisalignedbb.c);
        final int j2 = MathHelper.floor(axisalignedbb.f + 1.0);
        for (int k2 = i; k2 < j; ++k2) {
            for (int l2 = k; l2 < l; ++l2) {
                for (int i3 = i2; i3 < j2; ++i3) {
                    final Block block = Block.byId[this.getTypeId(k2, l2, i3)];
                    if (block != null && block.material == material) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean b(final AxisAlignedBB axisalignedbb, final Material material) {
        final int i = MathHelper.floor(axisalignedbb.a);
        final int j = MathHelper.floor(axisalignedbb.d + 1.0);
        final int k = MathHelper.floor(axisalignedbb.b);
        final int l = MathHelper.floor(axisalignedbb.e + 1.0);
        final int i2 = MathHelper.floor(axisalignedbb.c);
        final int j2 = MathHelper.floor(axisalignedbb.f + 1.0);
        for (int k2 = i; k2 < j; ++k2) {
            for (int l2 = k; l2 < l; ++l2) {
                for (int i3 = i2; i3 < j2; ++i3) {
                    final Block block = Block.byId[this.getTypeId(k2, l2, i3)];
                    if (block != null && block.material == material) {
                        final int j3 = this.getData(k2, l2, i3);
                        double d0 = l2 + 1;
                        if (j3 < 8) {
                            d0 = l2 + 1 - j3 / 8.0;
                        }
                        if (d0 >= axisalignedbb.b) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public Explosion explode(final Entity entity, final double d0, final double d1, final double d2, final float f, final boolean flag) {
        return this.createExplosion(entity, d0, d1, d2, f, false, flag);
    }
    
    public Explosion createExplosion(final Entity entity, final double d0, final double d1, final double d2, final float f, final boolean flag, final boolean flag1) {
        final Explosion explosion = new Explosion(this, entity, d0, d1, d2, f);
        explosion.a = flag;
        explosion.b = flag1;
        explosion.a();
        explosion.a(true);
        return explosion;
    }
    
    public float a(final Vec3D vec3d, final AxisAlignedBB axisalignedbb) {
        final double d0 = 1.0 / ((axisalignedbb.d - axisalignedbb.a) * 2.0 + 1.0);
        final double d2 = 1.0 / ((axisalignedbb.e - axisalignedbb.b) * 2.0 + 1.0);
        final double d3 = 1.0 / ((axisalignedbb.f - axisalignedbb.c) * 2.0 + 1.0);
        int i = 0;
        int j = 0;
        final Vec3D vec3d2 = vec3d.b.create(0.0, 0.0, 0.0);
        for (float f = 0.0f; f <= 1.0f; f += (float)d0) {
            for (float f2 = 0.0f; f2 <= 1.0f; f2 += (float)d2) {
                for (float f3 = 0.0f; f3 <= 1.0f; f3 += (float)d3) {
                    final double d4 = axisalignedbb.a + (axisalignedbb.d - axisalignedbb.a) * f;
                    final double d5 = axisalignedbb.b + (axisalignedbb.e - axisalignedbb.b) * f2;
                    final double d6 = axisalignedbb.c + (axisalignedbb.f - axisalignedbb.c) * f3;
                    if (this.a(vec3d2.b(d4, d5, d6), vec3d) == null) {
                        ++i;
                    }
                    ++j;
                }
            }
        }
        vec3d2.b.release(vec3d2);
        return i / j;
    }
    
    public boolean douseFire(final EntityHuman entityhuman, int i, int j, int k, final int l) {
        if (l == 0) {
            --j;
        }
        if (l == 1) {
            ++j;
        }
        if (l == 2) {
            --k;
        }
        if (l == 3) {
            ++k;
        }
        if (l == 4) {
            --i;
        }
        if (l == 5) {
            ++i;
        }
        if (this.getTypeId(i, j, k) == Block.FIRE.id) {
            this.a(entityhuman, 1004, i, j, k, 0);
            this.setAir(i, j, k);
            return true;
        }
        return false;
    }
    
    public TileEntity getTileEntity(final int i, final int j, final int k) {
        if (j >= 0 && j < 256) {
            TileEntity tileentity = null;
            if (this.N) {
                for (int l = 0; l < this.a.size(); ++l) {
                    final TileEntity tileentity2 = this.a.get(l);
                    if (!tileentity2.r() && tileentity2.x == i && tileentity2.y == j && tileentity2.z == k) {
                        tileentity = tileentity2;
                        break;
                    }
                }
            }
            if (tileentity == null) {
                final Chunk chunk = this.getChunkAt(i >> 4, k >> 4);
                if (chunk != null) {
                    tileentity = chunk.e(i & 0xF, j, k & 0xF);
                }
            }
            if (tileentity == null) {
                for (int l = 0; l < this.a.size(); ++l) {
                    final TileEntity tileentity2 = this.a.get(l);
                    if (!tileentity2.r() && tileentity2.x == i && tileentity2.y == j && tileentity2.z == k) {
                        tileentity = tileentity2;
                        break;
                    }
                }
            }
            return tileentity;
        }
        return null;
    }
    
    public void setTileEntity(final int i, final int j, final int k, final TileEntity tileentity) {
        if (tileentity != null && !tileentity.r()) {
            if (this.N) {
                tileentity.x = i;
                tileentity.y = j;
                tileentity.z = k;
                final Iterator iterator = this.a.iterator();
                while (iterator.hasNext()) {
                    final TileEntity tileentity2 = iterator.next();
                    if (tileentity2.x == i && tileentity2.y == j && tileentity2.z == k) {
                        tileentity2.w_();
                        iterator.remove();
                    }
                }
                this.a.add(tileentity);
            }
            else {
                this.tileEntityList.add(tileentity);
                final Chunk chunk = this.getChunkAt(i >> 4, k >> 4);
                if (chunk != null) {
                    chunk.a(i & 0xF, j, k & 0xF, tileentity);
                }
            }
        }
    }
    
    public void s(final int i, final int j, final int k) {
        final TileEntity tileentity = this.getTileEntity(i, j, k);
        if (tileentity != null && this.N) {
            tileentity.w_();
            this.a.remove(tileentity);
        }
        else {
            if (tileentity != null) {
                this.a.remove(tileentity);
                this.tileEntityList.remove(tileentity);
            }
            final Chunk chunk = this.getChunkAt(i >> 4, k >> 4);
            if (chunk != null) {
                chunk.f(i & 0xF, j, k & 0xF);
            }
        }
    }
    
    public void a(final TileEntity tileentity) {
        this.b.add(tileentity);
    }
    
    public boolean t(final int i, final int j, final int k) {
        final Block block = Block.byId[this.getTypeId(i, j, k)];
        return block != null && block.c();
    }
    
    public boolean u(final int i, final int j, final int k) {
        return Block.l(this.getTypeId(i, j, k));
    }
    
    public boolean v(final int i, final int j, final int k) {
        final int l = this.getTypeId(i, j, k);
        if (l != 0 && Block.byId[l] != null) {
            final AxisAlignedBB axisalignedbb = Block.byId[l].b(this, i, j, k);
            return axisalignedbb != null && axisalignedbb.b() >= 1.0;
        }
        return false;
    }
    
    public boolean w(final int i, final int j, final int k) {
        final Block block = Block.byId[this.getTypeId(i, j, k)];
        return this.a(block, this.getData(i, j, k));
    }
    
    public boolean a(final Block block, final int i) {
        return block != null && ((block.material.k() && block.b()) || ((block instanceof BlockStairs) ? ((i & 0x4) == 0x4) : ((block instanceof BlockStepAbstract) ? ((i & 0x8) == 0x8) : (block instanceof BlockHopper || (block instanceof BlockSnow && (i & 0x7) == 0x7)))));
    }
    
    public boolean c(final int i, final int j, final int k, final boolean flag) {
        if (i < -30000000 || k < -30000000 || i >= 30000000 || k >= 30000000) {
            return flag;
        }
        final Chunk chunk = this.chunkProvider.getOrCreateChunk(i >> 4, k >> 4);
        if (chunk != null && !chunk.isEmpty()) {
            final Block block = Block.byId[this.getTypeId(i, j, k)];
            return block != null && (block.material.k() && block.b());
        }
        return flag;
    }
    
    public void z() {
        final int i = this.a(1.0f);
        if (i != this.j) {
            this.j = i;
        }
    }
    
    public void setSpawnFlags(final boolean flag, final boolean flag1) {
        this.allowMonsters = flag;
        this.allowAnimals = flag1;
    }
    
    public void doTick() {
        this.o();
    }
    
    private void a() {
        if (this.worldData.hasStorm()) {
            this.n = 1.0f;
            if (this.worldData.isThundering()) {
                this.p = 1.0f;
            }
        }
    }
    
    protected void o() {
        if (!this.worldProvider.f) {
            int i = this.worldData.getThunderDuration();
            if (i <= 0) {
                if (this.worldData.isThundering()) {
                    this.worldData.setThunderDuration(this.random.nextInt(12000) + 3600);
                }
                else {
                    this.worldData.setThunderDuration(this.random.nextInt(168000) + 12000);
                }
            }
            else {
                --i;
                this.worldData.setThunderDuration(i);
                if (i <= 0) {
                    final ThunderChangeEvent thunder = new ThunderChangeEvent(this.getWorld(), !this.worldData.isThundering());
                    this.getServer().getPluginManager().callEvent(thunder);
                    if (!thunder.isCancelled()) {
                        this.worldData.setThundering(!this.worldData.isThundering());
                    }
                }
            }
            int j = this.worldData.getWeatherDuration();
            if (j <= 0) {
                if (this.worldData.hasStorm()) {
                    this.worldData.setWeatherDuration(this.random.nextInt(12000) + 12000);
                }
                else {
                    this.worldData.setWeatherDuration(this.random.nextInt(168000) + 12000);
                }
            }
            else {
                --j;
                this.worldData.setWeatherDuration(j);
                if (j <= 0) {
                    final WeatherChangeEvent weather = new WeatherChangeEvent(this.getWorld(), !this.worldData.hasStorm());
                    this.getServer().getPluginManager().callEvent(weather);
                    if (!weather.isCancelled()) {
                        this.worldData.setStorm(!this.worldData.hasStorm());
                    }
                }
            }
            this.m = this.n;
            if (this.worldData.hasStorm()) {
                this.n += 0.01;
            }
            else {
                this.n -= 0.01;
            }
            if (this.n < 0.0f) {
                this.n = 0.0f;
            }
            if (this.n > 1.0f) {
                this.n = 1.0f;
            }
            this.o = this.p;
            if (this.worldData.isThundering()) {
                this.p += 0.01;
            }
            else {
                this.p -= 0.01;
            }
            if (this.p < 0.0f) {
                this.p = 0.0f;
            }
            if (this.p > 1.0f) {
                this.p = 1.0f;
            }
        }
    }
    
    public void A() {
        this.worldData.setWeatherDuration(1);
    }
    
    protected void B() {
        this.methodProfiler.a("buildList");
        final int optimalChunks = this.getWorld().growthPerTick;
        if (optimalChunks <= 0 || this.players.isEmpty()) {
            return;
        }
        final int chunksPerPlayer = Math.min(200, Math.max(1, (int)((optimalChunks - this.players.size()) / this.players.size() + 0.5)));
        int randRange = 3 + chunksPerPlayer / 30;
        randRange = ((randRange > this.chunkTickRadius) ? this.chunkTickRadius : randRange);
        final float max = Math.max(35.0f, Math.min(100.0f, (chunksPerPlayer + 1) * 100.0f / 15.0f));
        this.modifiedOdds = max;
        this.growthOdds = max;
        for (int i = 0; i < this.players.size(); ++i) {
            final EntityHuman entityhuman = this.players.get(i);
            final int j = MathHelper.floor(entityhuman.locX / 16.0);
            final int k = MathHelper.floor(entityhuman.locZ / 16.0);
            final byte b0 = 7;
            final long key = chunkToKey(j, k);
            final int existingPlayers = Math.max(0, this.chunkTickList.get(key));
            this.chunkTickList.put(key, (short)(existingPlayers + 1));
            for (int chunk = 0; chunk < chunksPerPlayer; ++chunk) {
                final int dx = (this.random.nextBoolean() ? 1 : -1) * this.random.nextInt(randRange);
                final int dz = (this.random.nextBoolean() ? 1 : -1) * this.random.nextInt(randRange);
                final long hash = chunkToKey(dx + j, dz + k);
                if (!this.chunkTickList.contains(hash) && this.isChunkLoaded(dx + j, dz + k)) {
                    this.chunkTickList.put(hash, (short)(-1));
                }
            }
        }
        this.methodProfiler.b();
        if (this.O > 0) {
            --this.O;
        }
        this.methodProfiler.a("playerCheckLight");
        if (!this.players.isEmpty()) {
            final int i = this.random.nextInt(this.players.size());
            final EntityHuman entityhuman = this.players.get(i);
            final int j = MathHelper.floor(entityhuman.locX) + this.random.nextInt(11) - 5;
            final int k = MathHelper.floor(entityhuman.locY) + this.random.nextInt(11) - 5;
            final int j2 = MathHelper.floor(entityhuman.locZ) + this.random.nextInt(11) - 5;
            this.A(j, k, j2);
        }
        this.methodProfiler.b();
    }
    
    protected void a(final int i, final int j, final Chunk chunk) {
        this.methodProfiler.c("moodSound");
        if (this.O == 0 && !this.isStatic) {
            this.k = this.k * 3 + 1013904223;
            final int k = this.k >> 2;
            int l = k & 0xF;
            int i2 = k >> 8 & 0xF;
            final int j2 = k >> 16 & 0xFF;
            final int k2 = chunk.getTypeId(l, j2, i2);
            l += i;
            i2 += j;
            if (k2 == 0 && this.m(l, j2, i2) <= this.random.nextInt(8) && this.b(EnumSkyBlock.SKY, l, j2, i2) <= 0) {
                final EntityHuman entityhuman = this.findNearbyPlayer(l + 0.5, j2 + 0.5, i2 + 0.5, 8.0);
                if (entityhuman != null && entityhuman.e(l + 0.5, j2 + 0.5, i2 + 0.5) > 4.0) {
                    this.makeSound(l + 0.5, j2 + 0.5, i2 + 0.5, "ambient.cave.cave", 0.7f, 0.8f + this.random.nextFloat() * 0.2f);
                    this.O = this.random.nextInt(12000) + 6000;
                }
            }
        }
        this.methodProfiler.c("checkLight");
        chunk.o();
    }
    
    protected void g() {
        this.B();
    }
    
    public boolean x(final int i, final int j, final int k) {
        return this.d(i, j, k, false);
    }
    
    public boolean y(final int i, final int j, final int k) {
        return this.d(i, j, k, true);
    }
    
    public boolean d(final int i, final int j, final int k, final boolean flag) {
        final BiomeBase biomebase = this.getBiome(i, k);
        final float f = biomebase.j();
        if (f > 0.15f) {
            return false;
        }
        if (j >= 0 && j < 256 && this.b(EnumSkyBlock.BLOCK, i, j, k) < 10) {
            final int l = this.getTypeId(i, j, k);
            if ((l == Block.STATIONARY_WATER.id || l == Block.WATER.id) && this.getData(i, j, k) == 0) {
                if (!flag) {
                    return true;
                }
                boolean flag2 = true;
                if (flag2 && this.getMaterial(i - 1, j, k) != Material.WATER) {
                    flag2 = false;
                }
                if (flag2 && this.getMaterial(i + 1, j, k) != Material.WATER) {
                    flag2 = false;
                }
                if (flag2 && this.getMaterial(i, j, k - 1) != Material.WATER) {
                    flag2 = false;
                }
                if (flag2 && this.getMaterial(i, j, k + 1) != Material.WATER) {
                    flag2 = false;
                }
                if (!flag2) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean z(final int i, final int j, final int k) {
        final BiomeBase biomebase = this.getBiome(i, k);
        final float f = biomebase.j();
        if (f > 0.15f) {
            return false;
        }
        if (j >= 0 && j < 256 && this.b(EnumSkyBlock.BLOCK, i, j, k) < 10) {
            final int l = this.getTypeId(i, j - 1, k);
            final int i2 = this.getTypeId(i, j, k);
            if (i2 == 0 && Block.SNOW.canPlace(this, i, j, k) && l != 0 && l != Block.ICE.id && Block.byId[l].material.isSolid()) {
                return true;
            }
        }
        return false;
    }
    
    public void A(final int i, final int j, final int k) {
        if (!this.worldProvider.f) {
            this.c(EnumSkyBlock.SKY, i, j, k);
        }
        this.c(EnumSkyBlock.BLOCK, i, j, k);
    }
    
    private int a(final int i, final int j, final int k, final EnumSkyBlock enumskyblock) {
        if (enumskyblock == EnumSkyBlock.SKY && this.l(i, j, k)) {
            return 15;
        }
        final int l = this.getTypeId(i, j, k);
        int i2 = (enumskyblock == EnumSkyBlock.SKY) ? 0 : Block.lightEmission[l];
        int j2 = Block.lightBlock[l];
        if (j2 >= 15 && Block.lightEmission[l] > 0) {
            j2 = 1;
        }
        if (j2 < 1) {
            j2 = 1;
        }
        if (j2 >= 15) {
            return 0;
        }
        if (i2 >= 14) {
            return i2;
        }
        for (int k2 = 0; k2 < 6; ++k2) {
            final int l2 = i + Facing.b[k2];
            final int i3 = j + Facing.c[k2];
            final int j3 = k + Facing.d[k2];
            final int k3 = this.b(enumskyblock, l2, i3, j3) - j2;
            if (k3 > i2) {
                i2 = k3;
            }
            if (i2 >= 14) {
                return i2;
            }
        }
        return i2;
    }
    
    public void c(final EnumSkyBlock enumskyblock, final int i, final int j, final int k) {
        if (this.areChunksLoaded(i, j, k, 17)) {
            int l = 0;
            int i2 = 0;
            this.methodProfiler.a("getBrightness");
            final int j2 = this.b(enumskyblock, i, j, k);
            final int k2 = this.a(i, j, k, enumskyblock);
            if (k2 > j2) {
                this.H[i2++] = 133152;
            }
            else if (k2 < j2) {
                this.H[i2++] = (0x20820 | j2 << 18);
                while (l < i2) {
                    final int l2 = this.H[l++];
                    final int i3 = (l2 & 0x3F) - 32 + i;
                    final int j3 = (l2 >> 6 & 0x3F) - 32 + j;
                    final int k3 = (l2 >> 12 & 0x3F) - 32 + k;
                    final int l3 = l2 >> 18 & 0xF;
                    int i4 = this.b(enumskyblock, i3, j3, k3);
                    if (i4 == l3) {
                        this.b(enumskyblock, i3, j3, k3, 0);
                        if (l3 <= 0) {
                            continue;
                        }
                        final int j4 = MathHelper.a(i3 - i);
                        final int l4 = MathHelper.a(j3 - j);
                        final int k4 = MathHelper.a(k3 - k);
                        if (j4 + l4 + k4 >= 17) {
                            continue;
                        }
                        for (int i5 = 0; i5 < 6; ++i5) {
                            final int j5 = i3 + Facing.b[i5];
                            final int k5 = j3 + Facing.c[i5];
                            final int l5 = k3 + Facing.d[i5];
                            final int i6 = Math.max(1, Block.lightBlock[this.getTypeId(j5, k5, l5)]);
                            i4 = this.b(enumskyblock, j5, k5, l5);
                            if (i4 == l3 - i6 && i2 < this.H.length) {
                                this.H[i2++] = (j5 - i + 32 | k5 - j + 32 << 6 | l5 - k + 32 << 12 | l3 - i6 << 18);
                            }
                        }
                    }
                }
                l = 0;
            }
            this.methodProfiler.b();
            this.methodProfiler.a("checkedPosition < toCheckCount");
            while (l < i2) {
                final int l2 = this.H[l++];
                final int i3 = (l2 & 0x3F) - 32 + i;
                final int j3 = (l2 >> 6 & 0x3F) - 32 + j;
                final int k3 = (l2 >> 12 & 0x3F) - 32 + k;
                final int l3 = this.b(enumskyblock, i3, j3, k3);
                final int i4 = this.a(i3, j3, k3, enumskyblock);
                if (i4 != l3) {
                    this.b(enumskyblock, i3, j3, k3, i4);
                    if (i4 <= l3) {
                        continue;
                    }
                    final int j4 = Math.abs(i3 - i);
                    final int l4 = Math.abs(j3 - j);
                    final int k4 = Math.abs(k3 - k);
                    final boolean flag = i2 < this.H.length - 6;
                    if (j4 + l4 + k4 >= 17 || !flag) {
                        continue;
                    }
                    if (this.b(enumskyblock, i3 - 1, j3, k3) < i4) {
                        this.H[i2++] = i3 - 1 - i + 32 + (j3 - j + 32 << 6) + (k3 - k + 32 << 12);
                    }
                    if (this.b(enumskyblock, i3 + 1, j3, k3) < i4) {
                        this.H[i2++] = i3 + 1 - i + 32 + (j3 - j + 32 << 6) + (k3 - k + 32 << 12);
                    }
                    if (this.b(enumskyblock, i3, j3 - 1, k3) < i4) {
                        this.H[i2++] = i3 - i + 32 + (j3 - 1 - j + 32 << 6) + (k3 - k + 32 << 12);
                    }
                    if (this.b(enumskyblock, i3, j3 + 1, k3) < i4) {
                        this.H[i2++] = i3 - i + 32 + (j3 + 1 - j + 32 << 6) + (k3 - k + 32 << 12);
                    }
                    if (this.b(enumskyblock, i3, j3, k3 - 1) < i4) {
                        this.H[i2++] = i3 - i + 32 + (j3 - j + 32 << 6) + (k3 - 1 - k + 32 << 12);
                    }
                    if (this.b(enumskyblock, i3, j3, k3 + 1) >= i4) {
                        continue;
                    }
                    this.H[i2++] = i3 - i + 32 + (j3 - j + 32 << 6) + (k3 + 1 - k + 32 << 12);
                }
            }
            this.methodProfiler.b();
        }
    }
    
    public boolean a(final boolean flag) {
        return false;
    }
    
    public List a(final Chunk chunk, final boolean flag) {
        return null;
    }
    
    public List getEntities(final Entity entity, final AxisAlignedBB axisalignedbb) {
        return this.getEntities(entity, axisalignedbb, null);
    }
    
    public List getEntities(final Entity entity, final AxisAlignedBB axisalignedbb, final IEntitySelector ientityselector) {
        final ArrayList arraylist = new ArrayList();
        final int i = MathHelper.floor((axisalignedbb.a - 2.0) / 16.0);
        final int j = MathHelper.floor((axisalignedbb.d + 2.0) / 16.0);
        final int k = MathHelper.floor((axisalignedbb.c - 2.0) / 16.0);
        final int l = MathHelper.floor((axisalignedbb.f + 2.0) / 16.0);
        for (int i2 = i; i2 <= j; ++i2) {
            for (int j2 = k; j2 <= l; ++j2) {
                if (this.isChunkLoaded(i2, j2)) {
                    this.getChunkAt(i2, j2).a(entity, axisalignedbb, arraylist, ientityselector);
                }
            }
        }
        return arraylist;
    }
    
    public List a(final Class oclass, final AxisAlignedBB axisalignedbb) {
        return this.a(oclass, axisalignedbb, (IEntitySelector)null);
    }
    
    public List a(final Class oclass, final AxisAlignedBB axisalignedbb, final IEntitySelector ientityselector) {
        final int i = MathHelper.floor((axisalignedbb.a - 2.0) / 16.0);
        final int j = MathHelper.floor((axisalignedbb.d + 2.0) / 16.0);
        final int k = MathHelper.floor((axisalignedbb.c - 2.0) / 16.0);
        final int l = MathHelper.floor((axisalignedbb.f + 2.0) / 16.0);
        final ArrayList arraylist = new ArrayList();
        for (int i2 = i; i2 <= j; ++i2) {
            for (int j2 = k; j2 <= l; ++j2) {
                if (this.isChunkLoaded(i2, j2)) {
                    this.getChunkAt(i2, j2).a(oclass, axisalignedbb, arraylist, ientityselector);
                }
            }
        }
        return arraylist;
    }
    
    public Entity a(final Class oclass, final AxisAlignedBB axisalignedbb, final Entity entity) {
        final List list = this.a(oclass, axisalignedbb);
        Entity entity2 = null;
        double d0 = Double.MAX_VALUE;
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity3 = list.get(i);
            if (entity3 != entity) {
                final double d2 = entity.e(entity3);
                if (d2 <= d0) {
                    entity2 = entity3;
                    d0 = d2;
                }
            }
        }
        return entity2;
    }
    
    public abstract Entity getEntity(final int p0);
    
    public void b(final int i, final int j, final int k, final TileEntity tileentity) {
        if (this.isLoaded(i, j, k)) {
            this.getChunkAtWorldCoords(i, k).e();
        }
    }
    
    public int a(final Class oclass) {
        int i = 0;
        for (int j = 0; j < this.entityList.size(); ++j) {
            final Entity entity = this.entityList.get(j);
            if (entity instanceof EntityLiving) {
                final EntityLiving entityliving = (EntityLiving)entity;
                if (entityliving.isTypeNotPersistent() && entityliving.bU()) {
                    continue;
                }
            }
            if (oclass.isAssignableFrom(entity.getClass())) {
                ++i;
            }
        }
        return i;
    }
    
    public void a(final List list) {
        if (Thread.currentThread() != MinecraftServer.getServer().primaryThread) {
            throw new IllegalStateException("Asynchronous entity world add!");
        }
        Entity entity = null;
        for (int i = 0; i < list.size(); ++i) {
            entity = list.get(i);
            if (entity != null) {
                this.entityList.add(entity);
                this.a(list.get(i));
            }
        }
    }
    
    public void b(final List list) {
        this.f.addAll(list);
    }
    
    public boolean mayPlace(final int i, final int j, final int k, final int l, final boolean flag, final int i1, final Entity entity, final ItemStack itemstack) {
        final int j2 = this.getTypeId(j, k, l);
        Block block = Block.byId[j2];
        final Block block2 = Block.byId[i];
        AxisAlignedBB axisalignedbb = block2.b(this, j, k, l);
        if (flag) {
            axisalignedbb = null;
        }
        boolean defaultReturn;
        if (axisalignedbb != null && !this.a(axisalignedbb, entity)) {
            defaultReturn = false;
        }
        else {
            if (block != null && (block == Block.WATER || block == Block.STATIONARY_WATER || block == Block.LAVA || block == Block.STATIONARY_LAVA || block == Block.FIRE || block.material.isReplaceable())) {
                block = null;
            }
            defaultReturn = ((block != null && block.material == Material.ORIENTABLE && block2 == Block.ANVIL) || (i > 0 && block == null && block2.canPlace(this, j, k, l, i1, itemstack)));
        }
        final BlockCanBuildEvent event = new BlockCanBuildEvent(this.getWorld().getBlockAt(j, k, l), i, defaultReturn);
        this.getServer().getPluginManager().callEvent(event);
        return event.isBuildable();
    }
    
    public PathEntity findPath(final Entity entity, final Entity entity1, final float f, final boolean flag, final boolean flag1, final boolean flag2, final boolean flag3) {
        this.methodProfiler.a("pathfind");
        final int i = MathHelper.floor(entity.locX);
        final int j = MathHelper.floor(entity.locY + 1.0);
        final int k = MathHelper.floor(entity.locZ);
        final int l = (int)(f + 16.0f);
        final int i2 = i - l;
        final int j2 = j - l;
        final int k2 = k - l;
        final int l2 = i + l;
        final int i3 = j + l;
        final int j3 = k + l;
        final ChunkCache chunkcache = new ChunkCache(this, i2, j2, k2, l2, i3, j3, 0);
        final PathEntity pathentity = new Pathfinder(chunkcache, flag, flag1, flag2, flag3).a(entity, entity1, f);
        this.methodProfiler.b();
        return pathentity;
    }
    
    public PathEntity a(final Entity entity, final int i, final int j, final int k, final float f, final boolean flag, final boolean flag1, final boolean flag2, final boolean flag3) {
        this.methodProfiler.a("pathfind");
        final int l = MathHelper.floor(entity.locX);
        final int i2 = MathHelper.floor(entity.locY);
        final int j2 = MathHelper.floor(entity.locZ);
        final int k2 = (int)(f + 8.0f);
        final int l2 = l - k2;
        final int i3 = i2 - k2;
        final int j3 = j2 - k2;
        final int k3 = l + k2;
        final int l3 = i2 + k2;
        final int i4 = j2 + k2;
        final ChunkCache chunkcache = new ChunkCache(this, l2, i3, j3, k3, l3, i4, 0);
        final PathEntity pathentity = new Pathfinder(chunkcache, flag, flag1, flag2, flag3).a(entity, i, j, k, f);
        this.methodProfiler.b();
        return pathentity;
    }
    
    public int getBlockPower(final int i, final int j, final int k, final int l) {
        final int i2 = this.getTypeId(i, j, k);
        return (i2 == 0) ? 0 : Block.byId[i2].c(this, i, j, k, l);
    }
    
    public int getBlockPower(final int i, final int j, final int k) {
        final byte b0 = 0;
        int l = Math.max(b0, this.getBlockPower(i, j - 1, k, 0));
        if (l >= 15) {
            return l;
        }
        l = Math.max(l, this.getBlockPower(i, j + 1, k, 1));
        if (l >= 15) {
            return l;
        }
        l = Math.max(l, this.getBlockPower(i, j, k - 1, 2));
        if (l >= 15) {
            return l;
        }
        l = Math.max(l, this.getBlockPower(i, j, k + 1, 3));
        if (l >= 15) {
            return l;
        }
        l = Math.max(l, this.getBlockPower(i - 1, j, k, 4));
        if (l >= 15) {
            return l;
        }
        l = Math.max(l, this.getBlockPower(i + 1, j, k, 5));
        return (l >= 15) ? l : l;
    }
    
    public boolean isBlockFacePowered(final int i, final int j, final int k, final int l) {
        return this.getBlockFacePower(i, j, k, l) > 0;
    }
    
    public int getBlockFacePower(final int i, final int j, final int k, final int l) {
        if (this.u(i, j, k)) {
            return this.getBlockPower(i, j, k);
        }
        final int i2 = this.getTypeId(i, j, k);
        return (i2 == 0) ? 0 : Block.byId[i2].b(this, i, j, k, l);
    }
    
    public boolean isBlockIndirectlyPowered(final int i, final int j, final int k) {
        return this.getBlockFacePower(i, j - 1, k, 0) > 0 || this.getBlockFacePower(i, j + 1, k, 1) > 0 || this.getBlockFacePower(i, j, k - 1, 2) > 0 || this.getBlockFacePower(i, j, k + 1, 3) > 0 || this.getBlockFacePower(i - 1, j, k, 4) > 0 || this.getBlockFacePower(i + 1, j, k, 5) > 0;
    }
    
    public int getHighestNeighborSignal(final int i, final int j, final int k) {
        int l = 0;
        for (int i2 = 0; i2 < 6; ++i2) {
            final int j2 = this.getBlockFacePower(i + Facing.b[i2], j + Facing.c[i2], k + Facing.d[i2], i2);
            if (j2 >= 15) {
                return 15;
            }
            if (j2 > l) {
                l = j2;
            }
        }
        return l;
    }
    
    public EntityHuman findNearbyPlayer(final Entity entity, final double d0) {
        return this.findNearbyPlayer(entity.locX, entity.locY, entity.locZ, d0);
    }
    
    public EntityHuman findNearbyPlayer(final double d0, final double d1, final double d2, final double d3) {
        double d4 = -1.0;
        EntityHuman entityhuman = null;
        for (int i = 0; i < this.players.size(); ++i) {
            final EntityHuman entityhuman2 = this.players.get(i);
            if (entityhuman2 != null) {
                if (!entityhuman2.dead) {
                    final double d5 = entityhuman2.e(d0, d1, d2);
                    if ((d3 < 0.0 || d5 < d3 * d3) && (d4 == -1.0 || d5 < d4)) {
                        d4 = d5;
                        entityhuman = entityhuman2;
                    }
                }
            }
        }
        return entityhuman;
    }
    
    public EntityHuman findNearbyVulnerablePlayer(final Entity entity, final double d0) {
        return this.findNearbyVulnerablePlayer(entity.locX, entity.locY, entity.locZ, d0);
    }
    
    public EntityHuman findNearbyVulnerablePlayer(final double d0, final double d1, final double d2, final double d3) {
        double d4 = -1.0;
        EntityHuman entityhuman = null;
        for (int i = 0; i < this.players.size(); ++i) {
            final EntityHuman entityhuman2 = this.players.get(i);
            if (entityhuman2 != null) {
                if (!entityhuman2.dead) {
                    if (!entityhuman2.abilities.isInvulnerable && entityhuman2.isAlive()) {
                        final double d5 = entityhuman2.e(d0, d1, d2);
                        double d6 = d3;
                        if (entityhuman2.isSneaking()) {
                            d6 = d3 * 0.800000011920929;
                        }
                        if (entityhuman2.isInvisible()) {
                            float f = entityhuman2.cc();
                            if (f < 0.1f) {
                                f = 0.1f;
                            }
                            d6 *= 0.7f * f;
                        }
                        if ((d3 < 0.0 || d5 < d6 * d6) && (d4 == -1.0 || d5 < d4)) {
                            d4 = d5;
                            entityhuman = entityhuman2;
                        }
                    }
                }
            }
        }
        return entityhuman;
    }
    
    public EntityHuman a(final String s) {
        for (int i = 0; i < this.players.size(); ++i) {
            if (s.equals(this.players.get(i).name)) {
                return this.players.get(i);
            }
        }
        return null;
    }
    
    public void F() throws ExceptionWorldConflict {
        this.dataManager.checkSession();
    }
    
    public long getSeed() {
        return this.worldData.getSeed();
    }
    
    public long getTime() {
        return this.worldData.getTime();
    }
    
    public long getDayTime() {
        return this.worldData.getDayTime();
    }
    
    public void setDayTime(final long i) {
        this.worldData.setDayTime(i);
    }
    
    public ChunkCoordinates getSpawn() {
        return new ChunkCoordinates(this.worldData.c(), this.worldData.d(), this.worldData.e());
    }
    
    public boolean a(final EntityHuman entityhuman, final int i, final int j, final int k) {
        return true;
    }
    
    public void broadcastEntityEffect(final Entity entity, final byte b0) {
    }
    
    public IChunkProvider K() {
        return this.chunkProvider;
    }
    
    public void playNote(final int i, final int j, final int k, final int l, final int i1, final int j1) {
        if (l > 0) {
            Block.byId[l].b(this, i, j, k, i1, j1);
        }
    }
    
    public IDataManager getDataManager() {
        return this.dataManager;
    }
    
    public WorldData getWorldData() {
        return this.worldData;
    }
    
    public GameRules getGameRules() {
        return this.worldData.getGameRules();
    }
    
    public void everyoneSleeping() {
    }
    
    public void checkSleepStatus() {
        if (!this.isStatic) {
            this.everyoneSleeping();
        }
    }
    
    public float h(final float f) {
        return (this.o + (this.p - this.o) * f) * this.i(f);
    }
    
    public float i(final float f) {
        return this.m + (this.n - this.m) * f;
    }
    
    public boolean O() {
        return this.h(1.0f) > 0.9;
    }
    
    public boolean P() {
        return this.i(1.0f) > 0.2;
    }
    
    public boolean F(final int i, final int j, final int k) {
        if (!this.P()) {
            return false;
        }
        if (!this.l(i, j, k)) {
            return false;
        }
        if (this.h(i, k) > j) {
            return false;
        }
        final BiomeBase biomebase = this.getBiome(i, k);
        return !biomebase.c() && biomebase.d();
    }
    
    public boolean G(final int i, final int j, final int k) {
        final BiomeBase biomebase = this.getBiome(i, k);
        return biomebase.e();
    }
    
    public void a(final String s, final WorldMapBase worldmapbase) {
        this.worldMaps.a(s, worldmapbase);
    }
    
    public WorldMapBase a(final Class oclass, final String s) {
        return this.worldMaps.get(oclass, s);
    }
    
    public int b(final String s) {
        return this.worldMaps.a(s);
    }
    
    public void d(final int i, final int j, final int k, final int l, final int i1) {
        for (int j2 = 0; j2 < this.u.size(); ++j2) {
            this.u.get(j2).a(i, j, k, l, i1);
        }
    }
    
    public void triggerEffect(final int i, final int j, final int k, final int l, final int i1) {
        this.a(null, i, j, k, l, i1);
    }
    
    public void a(final EntityHuman entityhuman, final int i, final int j, final int k, final int l, final int i1) {
        try {
            for (int j2 = 0; j2 < this.u.size(); ++j2) {
                this.u.get(j2).a(entityhuman, i, j, k, l, i1);
            }
        }
        catch (Throwable throwable) {
            final CrashReport crashreport = CrashReport.a(throwable, "Playing level event");
            final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Level event being played");
            crashreportsystemdetails.a("Block coordinates", CrashReportSystemDetails.a(j, k, l));
            crashreportsystemdetails.a("Event source", entityhuman);
            crashreportsystemdetails.a("Event type", i);
            crashreportsystemdetails.a("Event data", i1);
            throw new ReportedException(crashreport);
        }
    }
    
    public int getHeight() {
        return 256;
    }
    
    public int R() {
        return this.worldProvider.f ? 128 : 256;
    }
    
    public IUpdatePlayerListBox a(final EntityMinecartAbstract entityminecartabstract) {
        return null;
    }
    
    public Random H(final int i, final int j, final int k) {
        final long l = i * 341873128712L + j * 132897987541L + this.getWorldData().getSeed() + k;
        this.random.setSeed(l);
        return this.random;
    }
    
    public ChunkPosition b(final String s, final int i, final int j, final int k) {
        return this.K().findNearestMapFeature(this, s, i, j, k);
    }
    
    public CrashReportSystemDetails a(final CrashReport crashreport) {
        final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Affected level", 1);
        crashreportsystemdetails.a("Level name", (this.worldData == null) ? "????" : this.worldData.getName());
        crashreportsystemdetails.a("All players", new CrashReportPlayers(this));
        crashreportsystemdetails.a("Chunk stats", new CrashReportChunkStats(this));
        try {
            this.worldData.a(crashreportsystemdetails);
        }
        catch (Throwable throwable) {
            crashreportsystemdetails.a("Level Data Unobtainable", throwable);
        }
        return crashreportsystemdetails;
    }
    
    public void f(final int i, final int j, final int k, final int l, final int i1) {
        for (int j2 = 0; j2 < this.u.size(); ++j2) {
            final IWorldAccess iworldaccess = this.u.get(j2);
            iworldaccess.b(i, j, k, l, i1);
        }
    }
    
    public Vec3DPool getVec3DPool() {
        return this.J;
    }
    
    public Calendar V() {
        if (this.getTime() % 600L == 0L) {
            this.K.setTimeInMillis(System.currentTimeMillis());
        }
        return this.K;
    }
    
    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }
    
    public void m(final int i, final int j, final int k, final int l) {
        for (int i2 = 0; i2 < 4; ++i2) {
            int j2 = i + Direction.a[i2];
            int k2 = k + Direction.b[i2];
            int l2 = this.getTypeId(j2, j, k2);
            if (l2 != 0) {
                Block block = Block.byId[l2];
                if (Block.REDSTONE_COMPARATOR_OFF.g(l2)) {
                    block.doPhysics(this, j2, j, k2, l);
                }
                else if (Block.l(l2)) {
                    j2 += Direction.a[i2];
                    k2 += Direction.b[i2];
                    l2 = this.getTypeId(j2, j, k2);
                    block = Block.byId[l2];
                    if (Block.REDSTONE_COMPARATOR_OFF.g(l2)) {
                        block.doPhysics(this, j2, j, k2, l);
                    }
                }
            }
        }
    }
    
    public IConsoleLogManager getLogger() {
        return this.logAgent;
    }
}
