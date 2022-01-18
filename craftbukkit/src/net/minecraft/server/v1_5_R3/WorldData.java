// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;

public class WorldData
{
    private long seed;
    private WorldType type;
    private String generatorOptions;
    private int spawnX;
    private int spawnY;
    private int spawnZ;
    private long time;
    private long dayTime;
    private long lastPlayed;
    private long sizeOnDisk;
    private NBTTagCompound playerData;
    private int dimension;
    private String name;
    private int version;
    private boolean isRaining;
    private int rainTicks;
    private boolean isThundering;
    private int thunderTicks;
    private EnumGamemode gameType;
    private boolean useMapFeatures;
    private boolean hardcore;
    private boolean allowCommands;
    private boolean initialized;
    private GameRules gameRules;
    
    protected WorldData() {
        this.type = WorldType.NORMAL;
        this.generatorOptions = "";
        this.gameRules = new GameRules();
    }
    
    public WorldData(final NBTTagCompound nbtTagCompound) {
        this.type = WorldType.NORMAL;
        this.generatorOptions = "";
        this.gameRules = new GameRules();
        this.seed = nbtTagCompound.getLong("RandomSeed");
        if (nbtTagCompound.hasKey("generatorName")) {
            this.type = WorldType.getType(nbtTagCompound.getString("generatorName"));
            if (this.type == null) {
                this.type = WorldType.NORMAL;
            }
            else if (this.type.e()) {
                int int1 = 0;
                if (nbtTagCompound.hasKey("generatorVersion")) {
                    int1 = nbtTagCompound.getInt("generatorVersion");
                }
                this.type = this.type.a(int1);
            }
            if (nbtTagCompound.hasKey("generatorOptions")) {
                this.generatorOptions = nbtTagCompound.getString("generatorOptions");
            }
        }
        this.gameType = EnumGamemode.a(nbtTagCompound.getInt("GameType"));
        if (nbtTagCompound.hasKey("MapFeatures")) {
            this.useMapFeatures = nbtTagCompound.getBoolean("MapFeatures");
        }
        else {
            this.useMapFeatures = true;
        }
        this.spawnX = nbtTagCompound.getInt("SpawnX");
        this.spawnY = nbtTagCompound.getInt("SpawnY");
        this.spawnZ = nbtTagCompound.getInt("SpawnZ");
        this.time = nbtTagCompound.getLong("Time");
        if (nbtTagCompound.hasKey("DayTime")) {
            this.dayTime = nbtTagCompound.getLong("DayTime");
        }
        else {
            this.dayTime = this.time;
        }
        this.lastPlayed = nbtTagCompound.getLong("LastPlayed");
        this.sizeOnDisk = nbtTagCompound.getLong("SizeOnDisk");
        this.name = nbtTagCompound.getString("LevelName");
        this.version = nbtTagCompound.getInt("version");
        this.rainTicks = nbtTagCompound.getInt("rainTime");
        this.isRaining = nbtTagCompound.getBoolean("raining");
        this.thunderTicks = nbtTagCompound.getInt("thunderTime");
        this.isThundering = nbtTagCompound.getBoolean("thundering");
        this.hardcore = nbtTagCompound.getBoolean("hardcore");
        if (nbtTagCompound.hasKey("initialized")) {
            this.initialized = nbtTagCompound.getBoolean("initialized");
        }
        else {
            this.initialized = true;
        }
        if (nbtTagCompound.hasKey("allowCommands")) {
            this.allowCommands = nbtTagCompound.getBoolean("allowCommands");
        }
        else {
            this.allowCommands = (this.gameType == EnumGamemode.CREATIVE);
        }
        if (nbtTagCompound.hasKey("Player")) {
            this.playerData = nbtTagCompound.getCompound("Player");
            this.dimension = this.playerData.getInt("Dimension");
        }
        if (nbtTagCompound.hasKey("GameRules")) {
            this.gameRules.a(nbtTagCompound.getCompound("GameRules"));
        }
    }
    
    public WorldData(final WorldSettings worldSettings, final String name) {
        this.type = WorldType.NORMAL;
        this.generatorOptions = "";
        this.gameRules = new GameRules();
        this.seed = worldSettings.d();
        this.gameType = worldSettings.e();
        this.useMapFeatures = worldSettings.g();
        this.name = name;
        this.hardcore = worldSettings.f();
        this.type = worldSettings.h();
        this.generatorOptions = worldSettings.j();
        this.allowCommands = worldSettings.i();
        this.initialized = false;
    }
    
    public WorldData(final WorldData worldData) {
        this.type = WorldType.NORMAL;
        this.generatorOptions = "";
        this.gameRules = new GameRules();
        this.seed = worldData.seed;
        this.type = worldData.type;
        this.generatorOptions = worldData.generatorOptions;
        this.gameType = worldData.gameType;
        this.useMapFeatures = worldData.useMapFeatures;
        this.spawnX = worldData.spawnX;
        this.spawnY = worldData.spawnY;
        this.spawnZ = worldData.spawnZ;
        this.time = worldData.time;
        this.dayTime = worldData.dayTime;
        this.lastPlayed = worldData.lastPlayed;
        this.sizeOnDisk = worldData.sizeOnDisk;
        this.playerData = worldData.playerData;
        this.dimension = worldData.dimension;
        this.name = worldData.name;
        this.version = worldData.version;
        this.rainTicks = worldData.rainTicks;
        this.isRaining = worldData.isRaining;
        this.thunderTicks = worldData.thunderTicks;
        this.isThundering = worldData.isThundering;
        this.hardcore = worldData.hardcore;
        this.allowCommands = worldData.allowCommands;
        this.initialized = worldData.initialized;
        this.gameRules = worldData.gameRules;
    }
    
    public NBTTagCompound a() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.a(nbtTagCompound, this.playerData);
        return nbtTagCompound;
    }
    
    public NBTTagCompound a(final NBTTagCompound nbtTagCompound) {
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        this.a(nbtTagCompound2, nbtTagCompound);
        return nbtTagCompound2;
    }
    
    private void a(final NBTTagCompound nbtTagCompound, final NBTTagCompound nbtTagCompound2) {
        nbtTagCompound.setLong("RandomSeed", this.seed);
        nbtTagCompound.setString("generatorName", this.type.name());
        nbtTagCompound.setInt("generatorVersion", this.type.getVersion());
        nbtTagCompound.setString("generatorOptions", this.generatorOptions);
        nbtTagCompound.setInt("GameType", this.gameType.a());
        nbtTagCompound.setBoolean("MapFeatures", this.useMapFeatures);
        nbtTagCompound.setInt("SpawnX", this.spawnX);
        nbtTagCompound.setInt("SpawnY", this.spawnY);
        nbtTagCompound.setInt("SpawnZ", this.spawnZ);
        nbtTagCompound.setLong("Time", this.time);
        nbtTagCompound.setLong("DayTime", this.dayTime);
        nbtTagCompound.setLong("SizeOnDisk", this.sizeOnDisk);
        nbtTagCompound.setLong("LastPlayed", System.currentTimeMillis());
        nbtTagCompound.setString("LevelName", this.name);
        nbtTagCompound.setInt("version", this.version);
        nbtTagCompound.setInt("rainTime", this.rainTicks);
        nbtTagCompound.setBoolean("raining", this.isRaining);
        nbtTagCompound.setInt("thunderTime", this.thunderTicks);
        nbtTagCompound.setBoolean("thundering", this.isThundering);
        nbtTagCompound.setBoolean("hardcore", this.hardcore);
        nbtTagCompound.setBoolean("allowCommands", this.allowCommands);
        nbtTagCompound.setBoolean("initialized", this.initialized);
        nbtTagCompound.setCompound("GameRules", this.gameRules.a());
        if (nbtTagCompound2 != null) {
            nbtTagCompound.setCompound("Player", nbtTagCompound2);
        }
    }
    
    public long getSeed() {
        return this.seed;
    }
    
    public int c() {
        return this.spawnX;
    }
    
    public int d() {
        return this.spawnY;
    }
    
    public int e() {
        return this.spawnZ;
    }
    
    public long getTime() {
        return this.time;
    }
    
    public long getDayTime() {
        return this.dayTime;
    }
    
    public NBTTagCompound i() {
        return this.playerData;
    }
    
    public int j() {
        return this.dimension;
    }
    
    public void setTime(final long time) {
        this.time = time;
    }
    
    public void setDayTime(final long dayTime) {
        this.dayTime = dayTime;
    }
    
    public void setSpawn(final int spawnX, final int spawnY, final int spawnZ) {
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.spawnZ = spawnZ;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public int l() {
        return this.version;
    }
    
    public void e(final int version) {
        this.version = version;
    }
    
    public boolean isThundering() {
        return this.isThundering;
    }
    
    public void setThundering(final boolean isThundering) {
        this.isThundering = isThundering;
    }
    
    public int getThunderDuration() {
        return this.thunderTicks;
    }
    
    public void setThunderDuration(final int thunderTicks) {
        this.thunderTicks = thunderTicks;
    }
    
    public boolean hasStorm() {
        return this.isRaining;
    }
    
    public void setStorm(final boolean isRaining) {
        this.isRaining = isRaining;
    }
    
    public int getWeatherDuration() {
        return this.rainTicks;
    }
    
    public void setWeatherDuration(final int rainTicks) {
        this.rainTicks = rainTicks;
    }
    
    public EnumGamemode getGameType() {
        return this.gameType;
    }
    
    public boolean shouldGenerateMapFeatures() {
        return this.useMapFeatures;
    }
    
    public void setGameType(final EnumGamemode gameType) {
        this.gameType = gameType;
    }
    
    public boolean isHardcore() {
        return this.hardcore;
    }
    
    public WorldType getType() {
        return this.type;
    }
    
    public void setType(final WorldType type) {
        this.type = type;
    }
    
    public String getGeneratorOptions() {
        return this.generatorOptions;
    }
    
    public boolean allowCommands() {
        return this.allowCommands;
    }
    
    public boolean isInitialized() {
        return this.initialized;
    }
    
    public void d(final boolean initialized) {
        this.initialized = initialized;
    }
    
    public GameRules getGameRules() {
        return this.gameRules;
    }
    
    public void a(final CrashReportSystemDetails crashReportSystemDetails) {
        crashReportSystemDetails.a("Level seed", new CrashReportLevelSeed(this));
        crashReportSystemDetails.a("Level generator", new CrashReportLevelGenerator(this));
        crashReportSystemDetails.a("Level generator options", new CrashReportLevelGeneratorOptions(this));
        crashReportSystemDetails.a("Level spawn location", new CrashReportLevelSpawnLocation(this));
        crashReportSystemDetails.a("Level time", new CrashReportLevelTime(this));
        crashReportSystemDetails.a("Level dimension", new CrashReportLevelDimension(this));
        crashReportSystemDetails.a("Level storage version", new CrashReportLevelStorageVersion(this));
        crashReportSystemDetails.a("Level weather", new CrashReportLevelWeather(this));
        crashReportSystemDetails.a("Level game mode", new CrashReportLevelGameMode(this));
    }
}
