// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.HashSet;
import org.bukkit.craftbukkit.v1_5_R3.util.LongHash;
import org.bukkit.WeatherType;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.entity.LightningStrike;
import org.bukkit.Location;
import java.util.Random;
import org.bukkit.craftbukkit.v1_5_R3.generator.InternalChunkGenerator;
import org.bukkit.craftbukkit.v1_5_R3.generator.NormalChunkGenerator;
import org.bukkit.craftbukkit.v1_5_R3.generator.SkyLandsChunkGenerator;
import org.bukkit.craftbukkit.v1_5_R3.generator.NetherChunkGenerator;
import org.bukkit.craftbukkit.v1_5_R3.generator.CustomChunkGenerator;
import org.bukkit.block.BlockState;
import gnu.trove.iterator.TLongShortIterator;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.Event;
import org.bukkit.event.weather.WeatherChangeEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R3.CraftTravelAgent;
import org.bukkit.generator.ChunkGenerator;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Set;
import org.bukkit.craftbukkit.v1_5_R3.util.LongObjectHashMap;
import org.bukkit.BlockChangeDelegate;

public class WorldServer extends World implements BlockChangeDelegate
{
    private final MinecraftServer server;
    public EntityTracker tracker;
    private final PlayerChunkMap manager;
    private LongObjectHashMap<Set<NextTickListEntry>> tickEntriesByChunk;
    private TreeSet<NextTickListEntry> tickEntryQueue;
    public ChunkProviderServer chunkProviderServer;
    public boolean savingDisabled;
    private boolean N;
    private int emptyTime;
    private final PortalTravelAgent P;
    private NoteDataList[] Q;
    private int R;
    private static final StructurePieceTreasure[] S;
    private ArrayList<NextTickListEntry> pendingTickEntries;
    private int nextPendingTickEntry;
    private IntHashMap entitiesById;
    private int aggregateTicks;
    public final int dimension;
    
    public WorldServer(final MinecraftServer minecraftserver, final IDataManager idatamanager, final String s, final int i, final WorldSettings worldsettings, final MethodProfiler methodprofiler, final IConsoleLogManager iconsolelogmanager, final org.bukkit.World.Environment env, final ChunkGenerator gen) {
        super(idatamanager, s, worldsettings, WorldProvider.byDimension(env.getId()), methodprofiler, iconsolelogmanager, gen, env);
        this.emptyTime = 0;
        this.Q = new NoteDataList[] { new NoteDataList((EmptyClass2)null), new NoteDataList((EmptyClass2)null) };
        this.R = 0;
        this.pendingTickEntries = new ArrayList<NextTickListEntry>();
        this.aggregateTicks = 1;
        this.dimension = i;
        this.pvpMode = minecraftserver.getPvP();
        this.server = minecraftserver;
        this.tracker = new EntityTracker(this);
        this.manager = new PlayerChunkMap(this, this.getWorld().viewDistance);
        if (this.entitiesById == null) {
            this.entitiesById = new IntHashMap();
        }
        if (this.tickEntriesByChunk == null) {
            this.tickEntriesByChunk = new LongObjectHashMap<Set<NextTickListEntry>>();
        }
        if (this.tickEntryQueue == null) {
            this.tickEntryQueue = new TreeSet<NextTickListEntry>();
        }
        this.P = new CraftTravelAgent(this);
        this.scoreboard = new ScoreboardServer(minecraftserver);
        ScoreboardSaveData scoreboardsavedata = (ScoreboardSaveData)this.worldMaps.get(ScoreboardSaveData.class, "scoreboard");
        if (scoreboardsavedata == null) {
            scoreboardsavedata = new ScoreboardSaveData();
            this.worldMaps.a("scoreboard", scoreboardsavedata);
        }
        scoreboardsavedata.a(this.scoreboard);
        ((ScoreboardServer)this.scoreboard).a(scoreboardsavedata);
    }
    
    public TileEntity getTileEntity(final int i, final int j, final int k) {
        TileEntity result = super.getTileEntity(i, j, k);
        final int type = this.getTypeId(i, j, k);
        if (type == Block.CHEST.id) {
            if (!(result instanceof TileEntityChest)) {
                result = this.fixTileEntity(i, j, k, type, result);
            }
        }
        else if (type == Block.FURNACE.id) {
            if (!(result instanceof TileEntityFurnace)) {
                result = this.fixTileEntity(i, j, k, type, result);
            }
        }
        else if (type == Block.DROPPER.id) {
            if (!(result instanceof TileEntityDropper)) {
                result = this.fixTileEntity(i, j, k, type, result);
            }
        }
        else if (type == Block.DISPENSER.id) {
            if (!(result instanceof TileEntityDispenser)) {
                result = this.fixTileEntity(i, j, k, type, result);
            }
        }
        else if (type == Block.JUKEBOX.id) {
            if (!(result instanceof TileEntityRecordPlayer)) {
                result = this.fixTileEntity(i, j, k, type, result);
            }
        }
        else if (type == Block.NOTE_BLOCK.id) {
            if (!(result instanceof TileEntityNote)) {
                result = this.fixTileEntity(i, j, k, type, result);
            }
        }
        else if (type == Block.MOB_SPAWNER.id) {
            if (!(result instanceof TileEntityMobSpawner)) {
                result = this.fixTileEntity(i, j, k, type, result);
            }
        }
        else if (type == Block.SIGN_POST.id || type == Block.WALL_SIGN.id) {
            if (!(result instanceof TileEntitySign)) {
                result = this.fixTileEntity(i, j, k, type, result);
            }
        }
        else if (type == Block.ENDER_CHEST.id) {
            if (!(result instanceof TileEntityEnderChest)) {
                result = this.fixTileEntity(i, j, k, type, result);
            }
        }
        else if (type == Block.BREWING_STAND.id) {
            if (!(result instanceof TileEntityBrewingStand)) {
                result = this.fixTileEntity(i, j, k, type, result);
            }
        }
        else if (type == Block.BEACON.id) {
            if (!(result instanceof TileEntityBeacon)) {
                result = this.fixTileEntity(i, j, k, type, result);
            }
        }
        else if (type == Block.HOPPER.id && !(result instanceof TileEntityHopper)) {
            result = this.fixTileEntity(i, j, k, type, result);
        }
        return result;
    }
    
    private TileEntity fixTileEntity(final int x, final int y, final int z, final int type, final TileEntity found) {
        this.getServer().getLogger().severe("Block at " + x + "," + y + "," + z + " is " + Material.getMaterial(type).toString() + " but has " + found + ". " + "Bukkit will attempt to fix this, but there may be additional damage that we cannot recover.");
        if (Block.byId[type] instanceof BlockContainer) {
            final TileEntity replacement = ((BlockContainer)Block.byId[type]).b(this);
            (replacement.world = this).setTileEntity(x, y, z, replacement);
            return replacement;
        }
        this.getServer().getLogger().severe("Don't know how to fix for this type... Can't do anything! :(");
        return found;
    }
    
    private boolean canSpawn(final int x, final int z) {
        if (this.generator != null) {
            return this.generator.canSpawn(this.getWorld(), x, z);
        }
        return this.worldProvider.canSpawn(x, z);
    }
    
    public void doTick() {
        super.doTick();
        if (this.getWorldData().isHardcore() && this.difficulty < 3) {
            this.difficulty = 3;
        }
        this.worldProvider.d.b();
        if (this.everyoneDeeplySleeping()) {
            final boolean flag = false;
            if (!this.allowMonsters || this.difficulty >= 1) {}
            if (!flag) {
                final long i = this.worldData.getDayTime() + 24000L;
                this.worldData.setDayTime(i - i % 24000L);
                this.d();
            }
        }
        this.methodProfiler.a("mobSpawner");
        final long time = this.worldData.getTime();
        if (this.getGameRules().getBoolean("doMobSpawning") && (this.allowMonsters || this.allowAnimals) && this instanceof WorldServer && this.players.size() > 0) {
            this.timings.mobSpawn.startTiming();
            SpawnerCreature.spawnEntities(this, this.allowMonsters && this.ticksPerMonsterSpawns != 0L && time % this.ticksPerMonsterSpawns == 0L, this.allowAnimals && this.ticksPerAnimalSpawns != 0L && time % this.ticksPerAnimalSpawns == 0L, this.worldData.getTime() % 400L == 0L);
            this.timings.mobSpawn.stopTiming();
        }
        this.timings.doChunkUnload.startTiming();
        this.methodProfiler.c("chunkSource");
        this.chunkProvider.unloadChunks();
        final int j = this.a(1.0f);
        if (j != this.j) {
            this.j = j;
        }
        this.worldData.setTime(this.worldData.getTime() + 1L);
        this.worldData.setDayTime(this.worldData.getDayTime() + 1L);
        this.timings.doChunkUnload.stopTiming();
        this.methodProfiler.c("tickPending");
        this.timings.doTickPending.startTiming();
        this.a(false);
        this.timings.doTickPending.stopTiming();
        this.methodProfiler.c("tickTiles");
        this.timings.doTickTiles.startTiming();
        this.g();
        this.timings.doTickTiles.stopTiming();
        this.methodProfiler.c("chunkMap");
        this.timings.doChunkMap.startTiming();
        this.manager.flush();
        this.timings.doChunkMap.stopTiming();
        this.methodProfiler.c("village");
        this.timings.doVillages.startTiming();
        this.villages.tick();
        this.siegeManager.a();
        this.timings.doVillages.stopTiming();
        this.methodProfiler.c("portalForcer");
        this.timings.doPortalForcer.startTiming();
        this.P.a(this.getTime());
        this.timings.doPortalForcer.stopTiming();
        this.methodProfiler.b();
        this.timings.doSounds.startTiming();
        this.Z();
        this.timings.doSounds.stopTiming();
        this.timings.doChunkGC.startTiming();
        this.getWorld().processChunkGC();
        this.timings.doChunkGC.stopTiming();
    }
    
    public BiomeMeta a(final EnumCreatureType enumcreaturetype, final int i, final int j, final int k) {
        final List list = this.K().getMobsFor(enumcreaturetype, i, j, k);
        return (list != null && !list.isEmpty()) ? ((BiomeMeta)WeightedRandom.a(this.random, list)) : null;
    }
    
    public void everyoneSleeping() {
        this.N = !this.players.isEmpty();
        for (final EntityHuman entityhuman : this.players) {
            if (!entityhuman.isSleeping() && !entityhuman.fauxSleeping) {
                this.N = false;
                break;
            }
        }
    }
    
    protected void d() {
        this.N = false;
        for (final EntityHuman entityhuman : this.players) {
            if (entityhuman.isSleeping()) {
                entityhuman.a(false, false, true);
            }
        }
        this.Y();
    }
    
    private void Y() {
        final WeatherChangeEvent weather = new WeatherChangeEvent(this.getWorld(), false);
        this.getServer().getPluginManager().callEvent(weather);
        final ThunderChangeEvent thunder = new ThunderChangeEvent(this.getWorld(), false);
        this.getServer().getPluginManager().callEvent(thunder);
        if (!weather.isCancelled()) {
            this.worldData.setWeatherDuration(0);
            this.worldData.setStorm(false);
        }
        if (!thunder.isCancelled()) {
            this.worldData.setThunderDuration(0);
            this.worldData.setThundering(false);
        }
    }
    
    public boolean everyoneDeeplySleeping() {
        if (this.N && !this.isStatic) {
            final Iterator iterator = this.players.iterator();
            boolean foundActualSleepers = false;
            while (iterator.hasNext()) {
                final EntityHuman entityhuman = iterator.next();
                if (entityhuman.isDeeplySleeping()) {
                    foundActualSleepers = true;
                }
                if (!entityhuman.isDeeplySleeping() && !entityhuman.fauxSleeping) {
                    return false;
                }
            }
            return foundActualSleepers;
        }
        return false;
    }
    
    protected void g() {
        final int aggregateTicks = this.aggregateTicks - 1;
        this.aggregateTicks = aggregateTicks;
        if (aggregateTicks != 0) {
            return;
        }
        this.aggregateTicks = this.getWorld().aggregateTicks;
        super.g();
        int i = 0;
        int j = 0;
        final TLongShortIterator iter = this.chunkTickList.iterator();
        while (iter.hasNext()) {
            iter.advance();
            final long chunkCoord = iter.key();
            final int chunkX = World.keyToX(chunkCoord);
            final int chunkZ = World.keyToZ(chunkCoord);
            if (!this.isChunkLoaded(chunkX, chunkZ) || this.chunkProviderServer.unloadQueue.contains(chunkX, chunkZ)) {
                iter.remove();
            }
            else {
                final int k = chunkX * 16;
                final int l = chunkZ * 16;
                this.methodProfiler.a("getChunk");
                final Chunk chunk = this.getChunkAt(chunkX, chunkZ);
                this.a(k, l, chunk);
                this.methodProfiler.c("tickChunk");
                chunk.k();
                this.methodProfiler.c("thunder");
                if (this.random.nextInt(100000) == 0 && this.P() && this.O()) {
                    this.k = this.k * 3 + 1013904223;
                    final int i2 = this.k >> 2;
                    final int j2 = k + (i2 & 0xF);
                    final int k2 = l + (i2 >> 8 & 0xF);
                    final int l2 = this.h(j2, k2);
                    if (this.F(j2, l2, k2)) {
                        this.strikeLightning(new EntityLightning(this, j2, l2, k2));
                    }
                }
                this.methodProfiler.c("iceandsnow");
                if (this.random.nextInt(16) == 0) {
                    this.k = this.k * 3 + 1013904223;
                    final int i2 = this.k >> 2;
                    final int j2 = i2 & 0xF;
                    final int k2 = i2 >> 8 & 0xF;
                    final int l2 = this.h(j2 + k, k2 + l);
                    if (this.y(j2 + k, l2 - 1, k2 + l)) {
                        final BlockState blockState = this.getWorld().getBlockAt(j2 + k, l2 - 1, k2 + l).getState();
                        blockState.setTypeId(Block.ICE.id);
                        final BlockFormEvent iceBlockForm = new BlockFormEvent(blockState.getBlock(), blockState);
                        this.getServer().getPluginManager().callEvent(iceBlockForm);
                        if (!iceBlockForm.isCancelled()) {
                            blockState.update(true);
                        }
                    }
                    if (this.P() && this.z(j2 + k, l2, k2 + l)) {
                        final BlockState blockState = this.getWorld().getBlockAt(j2 + k, l2, k2 + l).getState();
                        blockState.setTypeId(Block.SNOW.id);
                        final BlockFormEvent snow = new BlockFormEvent(blockState.getBlock(), blockState);
                        this.getServer().getPluginManager().callEvent(snow);
                        if (!snow.isCancelled()) {
                            blockState.update(true);
                        }
                    }
                    if (this.P()) {
                        final BiomeBase biomebase = this.getBiome(j2 + k, k2 + l);
                        if (biomebase.d()) {
                            final int i3 = this.getTypeId(j2 + k, l2 - 1, k2 + l);
                            if (i3 != 0) {
                                Block.byId[i3].g(this, j2 + k, l2 - 1, k2 + l);
                            }
                        }
                    }
                }
                this.methodProfiler.c("tickTiles");
                for (final ChunkSection chunksection : chunk.i()) {
                    if (chunksection != null && chunksection.shouldTick()) {
                        for (int j3 = 0; j3 < 3; ++j3) {
                            this.k = this.k * 3 + 1013904223;
                            final int i3 = this.k >> 2;
                            final int k3 = i3 & 0xF;
                            final int l3 = i3 >> 8 & 0xF;
                            final int i4 = i3 >> 16 & 0xF;
                            final int j4 = chunksection.getTypeId(k3, i4, l3);
                            ++j;
                            final Block block = Block.byId[j4];
                            if (block != null && block.isTicking()) {
                                ++i;
                                this.growthOdds = ((iter.value() < 1) ? this.modifiedOdds : 100.0f);
                                for (int c = 0; c < ((block.id == Block.SAPLING.id) ? 1 : this.getWorld().aggregateTicks); ++c) {
                                    block.a(this, k3 + k, i4 + chunksection.getYPosition(), l3 + l, this.random);
                                }
                            }
                        }
                    }
                }
                this.methodProfiler.b();
            }
        }
    }
    
    public boolean a(final int i, final int j, final int k, final int l) {
        for (int te_cnt = this.pendingTickEntries.size(), idx = this.nextPendingTickEntry; idx < te_cnt; ++idx) {
            final NextTickListEntry ent = this.pendingTickEntries.get(idx);
            if (ent.a == i && ent.b == j && ent.c == k && Block.b(ent.d, l)) {
                return true;
            }
        }
        return false;
    }
    
    public void a(final int i, final int j, final int k, final int l, final int i1) {
        this.a(i, j, k, l, i1, 0);
    }
    
    public void a(final int i, final int j, final int k, final int l, int i1, final int j1) {
        final NextTickListEntry nextticklistentry = new NextTickListEntry(i, j, k, l);
        final byte b0 = 0;
        if (this.d && l > 0) {
            if (Block.byId[l].l()) {
                if (this.e(nextticklistentry.a - b0, nextticklistentry.b - b0, nextticklistentry.c - b0, nextticklistentry.a + b0, nextticklistentry.b + b0, nextticklistentry.c + b0)) {
                    final int k2 = this.getTypeId(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c);
                    if (k2 == nextticklistentry.d && k2 > 0) {
                        Block.byId[k2].a(this, nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, this.random);
                    }
                }
                return;
            }
            i1 = 1;
        }
        if (this.e(i - b0, j - b0, k - b0, i + b0, j + b0, k + b0)) {
            if (l > 0) {
                nextticklistentry.a(i1 + this.worldData.getTime());
                nextticklistentry.a(j1);
            }
            this.addNextTickIfNeeded(nextticklistentry);
        }
    }
    
    public void b(final int i, final int j, final int k, final int l, final int i1, final int j1) {
        final NextTickListEntry nextticklistentry = new NextTickListEntry(i, j, k, l);
        nextticklistentry.a(j1);
        if (l > 0) {
            nextticklistentry.a(i1 + this.worldData.getTime());
        }
        this.addNextTickIfNeeded(nextticklistentry);
    }
    
    public void tickEntities() {
        this.i();
        super.tickEntities();
    }
    
    public void i() {
        this.emptyTime = 0;
    }
    
    public boolean a(final boolean flag) {
        int i = this.tickEntryQueue.size();
        this.nextPendingTickEntry = 0;
        if (i > 1000) {
            if (i > 20000) {
                i /= 20;
            }
            else {
                i = 1000;
            }
        }
        this.methodProfiler.a("cleaning");
        for (int j = 0; j < i; ++j) {
            final NextTickListEntry nextticklistentry = this.tickEntryQueue.first();
            if (!flag && nextticklistentry.e > this.worldData.getTime()) {
                break;
            }
            this.removeNextTickIfNeeded(nextticklistentry);
            this.pendingTickEntries.add(nextticklistentry);
        }
        this.methodProfiler.b();
        this.methodProfiler.a("ticking");
        for (int j = 0, te_cnt = this.pendingTickEntries.size(); j < te_cnt; ++j) {
            final NextTickListEntry nextticklistentry = this.pendingTickEntries.get(j);
            this.nextPendingTickEntry = j + 1;
            final byte b0 = 0;
            if (this.e(nextticklistentry.a - b0, nextticklistentry.b - b0, nextticklistentry.c - b0, nextticklistentry.a + b0, nextticklistentry.b + b0, nextticklistentry.c + b0)) {
                final int k = this.getTypeId(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c);
                if (k > 0 && Block.b(k, nextticklistentry.d)) {
                    try {
                        Block.byId[k].a(this, nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, this.random);
                    }
                    catch (Throwable throwable) {
                        final CrashReport crashreport = CrashReport.a(throwable, "Exception while ticking a block");
                        final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being ticked");
                        int l;
                        try {
                            l = this.getData(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c);
                        }
                        catch (Throwable throwable2) {
                            l = -1;
                        }
                        CrashReportSystemDetails.a(crashreportsystemdetails, nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, k, l);
                        throw new ReportedException(crashreport);
                    }
                }
            }
            else {
                this.a(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, nextticklistentry.d, 0);
            }
        }
        this.methodProfiler.b();
        this.pendingTickEntries.clear();
        this.nextPendingTickEntry = 0;
        return !this.tickEntryQueue.isEmpty();
    }
    
    public List a(final Chunk chunk, final boolean flag) {
        return this.getNextTickEntriesForChunk(chunk, flag);
    }
    
    public void entityJoinedWorld(final Entity entity, final boolean flag) {
        if (!this.server.getSpawnNPCs() && entity instanceof NPC) {
            entity.die();
        }
        if (!(entity.passenger instanceof EntityHuman)) {
            super.entityJoinedWorld(entity, flag);
        }
    }
    
    public void vehicleEnteredWorld(final Entity entity, final boolean flag) {
        super.entityJoinedWorld(entity, flag);
    }
    
    protected IChunkProvider j() {
        final IChunkLoader ichunkloader = this.dataManager.createChunkLoader(this.worldProvider);
        InternalChunkGenerator gen;
        if (this.generator != null) {
            gen = new CustomChunkGenerator(this, this.getSeed(), this.generator);
        }
        else if (this.worldProvider instanceof WorldProviderHell) {
            gen = new NetherChunkGenerator(this, this.getSeed());
        }
        else if (this.worldProvider instanceof WorldProviderTheEnd) {
            gen = new SkyLandsChunkGenerator(this, this.getSeed());
        }
        else {
            gen = new NormalChunkGenerator(this, this.getSeed());
        }
        return this.chunkProviderServer = new ChunkProviderServer(this, ichunkloader, gen);
    }
    
    public List getTileEntities(final int i, final int j, final int k, final int l, final int i1, final int j1) {
        final ArrayList arraylist = new ArrayList();
        for (int chunkX = i >> 4; chunkX <= l - 1 >> 4; ++chunkX) {
            for (int chunkZ = k >> 4; chunkZ <= j1 - 1 >> 4; ++chunkZ) {
                final Chunk chunk = this.getChunkAt(chunkX, chunkZ);
                if (chunk != null) {
                    for (final Object te : chunk.tileEntities.values()) {
                        final TileEntity tileentity = (TileEntity)te;
                        if (tileentity.x >= i && tileentity.y >= j && tileentity.z >= k && tileentity.x < l && tileentity.y < i1 && tileentity.z < j1) {
                            arraylist.add(tileentity);
                        }
                    }
                }
            }
        }
        return arraylist;
    }
    
    public boolean a(final EntityHuman entityhuman, final int i, final int j, final int k) {
        return !this.server.a(this, i, j, k, entityhuman);
    }
    
    protected void a(final WorldSettings worldsettings) {
        if (this.entitiesById == null) {
            this.entitiesById = new IntHashMap();
        }
        if (this.tickEntriesByChunk == null) {
            this.tickEntriesByChunk = new LongObjectHashMap<Set<NextTickListEntry>>();
        }
        if (this.tickEntryQueue == null) {
            this.tickEntryQueue = new TreeSet<NextTickListEntry>();
        }
        this.b(worldsettings);
        super.a(worldsettings);
    }
    
    protected void b(final WorldSettings worldsettings) {
        if (!this.worldProvider.e()) {
            this.worldData.setSpawn(0, this.worldProvider.getSeaLevel(), 0);
        }
        else {
            this.isLoading = true;
            final WorldChunkManager worldchunkmanager = this.worldProvider.d;
            final List list = worldchunkmanager.a();
            final Random random = new Random(this.getSeed());
            final ChunkPosition chunkposition = worldchunkmanager.a(0, 0, 256, list, random);
            int i = 0;
            final int j = this.worldProvider.getSeaLevel();
            int k = 0;
            if (this.generator != null) {
                final Random rand = new Random(this.getSeed());
                final Location spawn = this.generator.getFixedSpawnLocation(this.getWorld(), rand);
                if (spawn != null) {
                    if (spawn.getWorld() != this.getWorld()) {
                        throw new IllegalStateException("Cannot set spawn point for " + this.worldData.getName() + " to be in another world (" + spawn.getWorld().getName() + ")");
                    }
                    this.worldData.setSpawn(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
                    this.isLoading = false;
                    return;
                }
            }
            if (chunkposition != null) {
                i = chunkposition.x;
                k = chunkposition.z;
            }
            else {
                this.getLogger().warning("Unable to find spawn biome");
            }
            int l = 0;
            while (!this.canSpawn(i, k)) {
                i += random.nextInt(64) - random.nextInt(64);
                k += random.nextInt(64) - random.nextInt(64);
                if (++l == 1000) {
                    break;
                }
            }
            this.worldData.setSpawn(i, j, k);
            this.isLoading = false;
            if (worldsettings.c()) {
                this.k();
            }
        }
    }
    
    protected void k() {
        final WorldGenBonusChest worldgenbonuschest = new WorldGenBonusChest(WorldServer.S, 10);
        for (int i = 0; i < 10; ++i) {
            final int j = this.worldData.c() + this.random.nextInt(6) - this.random.nextInt(6);
            final int k = this.worldData.e() + this.random.nextInt(6) - this.random.nextInt(6);
            final int l = this.i(j, k) + 1;
            if (worldgenbonuschest.a(this, this.random, j, l, k)) {
                break;
            }
        }
    }
    
    public ChunkCoordinates getDimensionSpawn() {
        return this.worldProvider.h();
    }
    
    public void save(final boolean flag, final IProgressUpdate iprogressupdate) throws ExceptionWorldConflict {
        if (this.chunkProvider.canSave()) {
            if (iprogressupdate != null) {
                iprogressupdate.a("Saving level");
            }
            this.a();
            if (iprogressupdate != null) {
                iprogressupdate.c("Saving chunks");
            }
            this.chunkProvider.saveChunks(flag, iprogressupdate);
        }
    }
    
    public void flushSave() {
        if (this.chunkProvider.canSave()) {
            this.chunkProvider.b();
        }
    }
    
    protected void a() throws ExceptionWorldConflict {
        this.F();
        this.dataManager.saveWorldData(this.worldData, this.server.getPlayerList().q());
        this.worldMaps.a();
    }
    
    protected void a(final Entity entity) {
        super.a(entity);
        this.entitiesById.a(entity.id, entity);
        final Entity[] aentity = entity.an();
        if (aentity != null) {
            for (int i = 0; i < aentity.length; ++i) {
                this.entitiesById.a(aentity[i].id, aentity[i]);
            }
        }
    }
    
    protected void b(final Entity entity) {
        super.b(entity);
        this.entitiesById.d(entity.id);
        final Entity[] aentity = entity.an();
        if (aentity != null) {
            for (int i = 0; i < aentity.length; ++i) {
                this.entitiesById.d(aentity[i].id);
            }
        }
    }
    
    public Entity getEntity(final int i) {
        return (Entity)this.entitiesById.get(i);
    }
    
    public boolean strikeLightning(final Entity entity) {
        final LightningStrikeEvent lightning = new LightningStrikeEvent(this.getWorld(), (LightningStrike)entity.getBukkitEntity());
        this.getServer().getPluginManager().callEvent(lightning);
        if (lightning.isCancelled()) {
            return false;
        }
        if (super.strikeLightning(entity)) {
            this.server.getPlayerList().sendPacketNearby(entity.locX, entity.locY, entity.locZ, 512.0, this.dimension, new Packet71Weather(entity));
            return true;
        }
        return false;
    }
    
    public void broadcastEntityEffect(final Entity entity, final byte b0) {
        final Packet38EntityStatus packet38entitystatus = new Packet38EntityStatus(entity.id, b0);
        this.getTracker().sendPacketToEntity(entity, packet38entitystatus);
    }
    
    public Explosion createExplosion(final Entity entity, final double d0, final double d1, final double d2, final float f, final boolean flag, final boolean flag1) {
        final Explosion explosion = super.createExplosion(entity, d0, d1, d2, f, flag, flag1);
        if (explosion.wasCanceled) {
            return explosion;
        }
        if (!flag1) {
            explosion.blocks.clear();
        }
        for (final EntityHuman entityhuman : this.players) {
            if (entityhuman.e(d0, d1, d2) < 4096.0) {
                ((EntityPlayer)entityhuman).playerConnection.sendPacket(new Packet60Explosion(d0, d1, d2, f, explosion.blocks, explosion.b().get(entityhuman)));
            }
        }
        return explosion;
    }
    
    public void playNote(final int i, final int j, final int k, final int l, final int i1, final int j1) {
        final NoteBlockData noteblockdata = new NoteBlockData(i, j, k, l, i1, j1);
        for (final NoteBlockData noteblockdata2 : this.Q[this.R]) {
            if (noteblockdata2.equals(noteblockdata)) {
                return;
            }
        }
        this.Q[this.R].add(noteblockdata);
    }
    
    private void Z() {
        while (!this.Q[this.R].isEmpty()) {
            final int i = this.R;
            this.R ^= 0x1;
            for (final NoteBlockData noteblockdata : this.Q[i]) {
                if (this.a(noteblockdata)) {
                    this.server.getPlayerList().sendPacketNearby(noteblockdata.a(), noteblockdata.b(), noteblockdata.c(), 64.0, this.dimension, new Packet54PlayNoteBlock(noteblockdata.a(), noteblockdata.b(), noteblockdata.c(), noteblockdata.f(), noteblockdata.d(), noteblockdata.e()));
                }
            }
            this.Q[i].clear();
        }
    }
    
    private boolean a(final NoteBlockData noteblockdata) {
        final int i = this.getTypeId(noteblockdata.a(), noteblockdata.b(), noteblockdata.c());
        return i == noteblockdata.f() && Block.byId[i].b(this, noteblockdata.a(), noteblockdata.b(), noteblockdata.c(), noteblockdata.d(), noteblockdata.e());
    }
    
    public void saveLevel() {
        this.dataManager.a();
    }
    
    protected void o() {
        final boolean flag = this.P();
        super.o();
        if (flag != this.P()) {
            for (int i = 0; i < this.players.size(); ++i) {
                if (this.players.get(i).world == this) {
                    this.players.get(i).setPlayerWeather(flag ? WeatherType.CLEAR : WeatherType.DOWNFALL, false);
                }
            }
        }
    }
    
    public MinecraftServer getMinecraftServer() {
        return this.server;
    }
    
    public EntityTracker getTracker() {
        return this.tracker;
    }
    
    public PlayerChunkMap getPlayerChunkMap() {
        return this.manager;
    }
    
    public PortalTravelAgent t() {
        return this.P;
    }
    
    public boolean setRawTypeId(final int x, final int y, final int z, final int typeId) {
        return this.setTypeIdAndData(x, y, z, typeId, 0, 4);
    }
    
    public boolean setRawTypeIdAndData(final int x, final int y, final int z, final int typeId, final int data) {
        return this.setTypeIdAndData(x, y, z, typeId, data, 4);
    }
    
    public boolean setTypeId(final int x, final int y, final int z, final int typeId) {
        return this.setTypeIdAndData(x, y, z, typeId, 0, 3);
    }
    
    public boolean setTypeIdAndData(final int x, final int y, final int z, final int typeId, final int data) {
        return this.setTypeIdAndData(x, y, z, typeId, data, 3);
    }
    
    private void addNextTickIfNeeded(final NextTickListEntry ent) {
        final long coord = LongHash.toLong(ent.a >> 4, ent.c >> 4);
        Set<NextTickListEntry> chunkset = this.tickEntriesByChunk.get(coord);
        if (chunkset == null) {
            chunkset = new HashSet<NextTickListEntry>();
            this.tickEntriesByChunk.put(coord, chunkset);
        }
        else if (chunkset.contains(ent)) {
            return;
        }
        chunkset.add(ent);
        this.tickEntryQueue.add(ent);
    }
    
    private void removeNextTickIfNeeded(final NextTickListEntry ent) {
        final long coord = LongHash.toLong(ent.a >> 4, ent.c >> 4);
        final Set<NextTickListEntry> chunkset = this.tickEntriesByChunk.get(coord);
        if (chunkset != null) {
            chunkset.remove(ent);
            if (chunkset.isEmpty()) {
                this.tickEntriesByChunk.remove(coord);
            }
        }
        this.tickEntryQueue.remove(ent);
    }
    
    private List<NextTickListEntry> getNextTickEntriesForChunk(final Chunk chunk, final boolean remove) {
        final long coord = LongHash.toLong(chunk.x, chunk.z);
        final Set<NextTickListEntry> chunkset = this.tickEntriesByChunk.get(coord);
        List<NextTickListEntry> list = null;
        if (chunkset != null) {
            list = new ArrayList<NextTickListEntry>(chunkset);
            if (remove) {
                this.tickEntriesByChunk.remove(coord);
                this.tickEntryQueue.removeAll(list);
                chunkset.clear();
            }
        }
        if (this.nextPendingTickEntry < this.pendingTickEntries.size()) {
            final int xmin = chunk.x << 4;
            final int xmax = xmin + 16;
            final int zmin = chunk.z << 4;
            final int zmax = zmin + 16;
            for (int te_cnt = this.pendingTickEntries.size(), i = this.nextPendingTickEntry; i < te_cnt; ++i) {
                final NextTickListEntry ent = this.pendingTickEntries.get(i);
                if (ent.a >= xmin && ent.a < xmax && ent.c >= zmin && ent.c < zmax) {
                    if (list == null) {
                        list = new ArrayList<NextTickListEntry>();
                    }
                    list.add(ent);
                }
            }
        }
        return list;
    }
    
    static {
        S = new StructurePieceTreasure[] { new StructurePieceTreasure(Item.STICK.id, 0, 1, 3, 10), new StructurePieceTreasure(Block.WOOD.id, 0, 1, 3, 10), new StructurePieceTreasure(Block.LOG.id, 0, 1, 3, 10), new StructurePieceTreasure(Item.STONE_AXE.id, 0, 1, 1, 3), new StructurePieceTreasure(Item.WOOD_AXE.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.STONE_PICKAXE.id, 0, 1, 1, 3), new StructurePieceTreasure(Item.WOOD_PICKAXE.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.APPLE.id, 0, 2, 3, 5), new StructurePieceTreasure(Item.BREAD.id, 0, 2, 3, 3) };
    }
}
