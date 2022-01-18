// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.Spigot;
import java.awt.GraphicsEnvironment;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import org.bukkit.command.CommandSender;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.craftbukkit.v1_5_R3.util.Waitable;
import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import org.bukkit.craftbukkit.v1_5_R3.chunkio.ChunkIOExecutor;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.spigotmc.WatchdogThread;
import org.spigotmc.CustomTimingsHandler;
import org.bukkit.craftbukkit.v1_5_R3.SpigotTimings;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.event.Event;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.craftbukkit.v1_5_R3.scoreboard.CraftScoreboardManager;
import org.bukkit.generator.BlockPopulator;
import java.util.Collection;
import com.google.common.io.Files;
import org.bukkit.World;
import java.net.UnknownHostException;
import org.bukkit.craftbukkit.v1_5_R3.util.ServerShutdownThread;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.craftbukkit.Main;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.ArrayList;
import java.util.Queue;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import java.security.KeyPair;
import java.util.List;
import java.io.File;

public abstract class MinecraftServer implements ICommandListener, Runnable, IMojangStatistics
{
    private static MinecraftServer k;
    public Convertable convertable;
    private final MojangStatisticsGenerator m;
    public File universe;
    private final List o;
    private final ICommandHandler p;
    public final MethodProfiler methodProfiler;
    private String serverIp;
    private int r;
    public WorldServer[] worldServer;
    private PlayerList s;
    private boolean isRunning;
    private boolean isStopped;
    private int ticks;
    public String c;
    public int d;
    private boolean onlineMode;
    private boolean spawnAnimals;
    private boolean spawnNPCs;
    private boolean pvpMode;
    private boolean allowFlight;
    private String motd;
    private int C;
    private long D;
    private long E;
    private long F;
    private long G;
    public final long[] e;
    public final long[] f;
    public final long[] g;
    public final long[] h;
    public final long[] i;
    public long[][] j;
    private KeyPair H;
    private String I;
    private String J;
    private boolean demoMode;
    private boolean M;
    private boolean N;
    private String O;
    private boolean P;
    private long Q;
    private String R;
    private boolean S;
    private boolean T;
    public List<WorldServer> worlds;
    public CraftServer server;
    public OptionSet options;
    public ConsoleCommandSender console;
    public RemoteConsoleCommandSender remoteConsole;
    public ConsoleReader reader;
    public static int currentTick;
    public final Thread primaryThread;
    public Queue<Runnable> processQueue;
    public int autosavePeriod;
    private static final int TPS = 20;
    private static final int TICK_TIME = 50000000;
    public static double currentTPS;
    private static long catchupTime;
    
    public MinecraftServer(final OptionSet options) {
        this.m = new MojangStatisticsGenerator("server", this);
        this.o = new ArrayList();
        this.methodProfiler = new MethodProfiler();
        this.r = -1;
        this.isRunning = true;
        this.isStopped = false;
        this.ticks = 0;
        this.e = new long[100];
        this.f = new long[100];
        this.g = new long[100];
        this.h = new long[100];
        this.i = new long[100];
        this.O = "";
        this.P = false;
        this.T = false;
        this.worlds = new ArrayList<WorldServer>();
        this.processQueue = new ConcurrentLinkedQueue<Runnable>();
        MinecraftServer.k = this;
        this.p = new CommandDispatcher();
        this.an();
        this.options = options;
        try {
            (this.reader = new ConsoleReader(System.in, System.out)).setExpandEvents(false);
        }
        catch (Exception e) {
            try {
                System.setProperty("org.bukkit.craftbukkit.libs.jline.terminal", "org.bukkit.craftbukkit.libs.jline.UnsupportedTerminal");
                System.setProperty("user.language", "en");
                Main.useJline = false;
                (this.reader = new ConsoleReader(System.in, System.out)).setExpandEvents(false);
            }
            catch (IOException ex) {
                Logger.getLogger(MinecraftServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Runtime.getRuntime().addShutdownHook(new ServerShutdownThread(this));
        this.primaryThread = new ThreadServerApplication(this, "Server thread");
    }
    
    public abstract PropertyManager getPropertyManager();
    
    private void an() {
        DispenserRegistry.a();
    }
    
    protected abstract boolean init() throws UnknownHostException;
    
    protected void b(final String s) {
        if (this.getConvertable().isConvertable(s)) {
            this.getLogger().info("Converting map!");
            this.c("menu.convertingLevel");
            this.getConvertable().convert(s, new ConvertProgressUpdater(this));
        }
    }
    
    protected synchronized void c(final String s) {
        this.R = s;
    }
    
    protected void a(final String s, final String s1, final long i, final WorldType worldtype, final String s2) {
        this.b(s);
        this.c("menu.loadingLevel");
        this.worldServer = new WorldServer[3];
        final IDataManager idatamanager = this.convertable.a(s, true);
        final WorldData worlddata = idatamanager.getWorldData();
        for (int worldCount = 3, j = 0; j < worldCount; ++j) {
            int dimension = 0;
            if (j == 1) {
                if (!this.getAllowNether()) {
                    continue;
                }
                dimension = -1;
            }
            if (j == 2) {
                if (!this.server.getAllowEnd()) {
                    continue;
                }
                dimension = 1;
            }
            final String worldType = World.Environment.getEnvironment(dimension).toString().toLowerCase();
            final String name = (dimension == 0) ? s : (s + "_" + worldType);
            final ChunkGenerator gen = this.server.getGenerator(name);
            final WorldSettings worldsettings = new WorldSettings(i, this.getGamemode(), this.getGenerateStructures(), this.isHardcore(), worldtype);
            worldsettings.a(s2);
            WorldServer world;
            if (j == 0) {
                if (this.M()) {
                    world = new DemoWorldServer(this, new ServerNBTManager(this.server.getWorldContainer(), s1, true), s1, dimension, this.methodProfiler, this.getLogger());
                }
                else {
                    world = new WorldServer(this, new ServerNBTManager(this.server.getWorldContainer(), s1, true), s1, dimension, worldsettings, this.methodProfiler, this.getLogger(), World.Environment.getEnvironment(dimension), gen);
                }
            }
            else {
                final String dim = "DIM" + dimension;
                final File newWorld = new File(new File(name), dim);
                final File oldWorld = new File(new File(s), dim);
                if (!newWorld.isDirectory() && oldWorld.isDirectory()) {
                    final IConsoleLogManager log = this.getLogger();
                    log.info("---- Migration of old " + worldType + " folder required ----");
                    log.info("Unfortunately due to the way that Minecraft implemented multiworld support in 1.6, Bukkit requires that you move your " + worldType + " folder to a new location in order to operate correctly.");
                    log.info("We will move this folder for you, but it will mean that you need to move it back should you wish to stop using Bukkit in the future.");
                    log.info("Attempting to move " + oldWorld + " to " + newWorld + "...");
                    if (newWorld.exists()) {
                        log.severe("A file or folder already exists at " + newWorld + "!");
                        log.info("---- Migration of old " + worldType + " folder failed ----");
                    }
                    else if (newWorld.getParentFile().mkdirs()) {
                        if (oldWorld.renameTo(newWorld)) {
                            log.info("Success! To restore " + worldType + " in the future, simply move " + newWorld + " to " + oldWorld);
                            try {
                                Files.copy(new File(new File(s), "level.dat"), new File(new File(name), "level.dat"));
                            }
                            catch (IOException exception) {
                                log.severe("Unable to migrate world data.");
                            }
                            log.info("---- Migration of old " + worldType + " folder complete ----");
                        }
                        else {
                            log.severe("Could not move folder " + oldWorld + " to " + newWorld + "!");
                            log.info("---- Migration of old " + worldType + " folder failed ----");
                        }
                    }
                    else {
                        log.severe("Could not create path for " + newWorld + "!");
                        log.info("---- Migration of old " + worldType + " folder failed ----");
                    }
                }
                this.c(name);
                world = new SecondaryWorldServer(this, new ServerNBTManager(this.server.getWorldContainer(), name, true), name, dimension, worldsettings, this.worlds.get(0), this.methodProfiler, this.getLogger(), World.Environment.getEnvironment(dimension), gen);
            }
            if (gen != null) {
                world.getWorld().getPopulators().addAll(gen.getDefaultPopulators(world.getWorld()));
            }
            this.server.scoreboardManager = new CraftScoreboardManager(this, world.getScoreboard());
            this.server.getPluginManager().callEvent(new WorldInitEvent(world.getWorld()));
            world.addIWorldAccess(new WorldManager(this, world));
            if (!this.I()) {
                world.getWorldData().setGameType(this.getGamemode());
            }
            this.worlds.add(world);
            this.s.setPlayerFileData(this.worlds.toArray(new WorldServer[this.worlds.size()]));
        }
        this.c(this.getDifficulty());
        this.e();
    }
    
    protected void e() {
        long i = System.currentTimeMillis();
        this.c("menu.generatingTerrain");
        final byte b0 = 0;
        for (int j = 0; j < this.worlds.size(); ++j) {
            final WorldServer worldserver = this.worlds.get(j);
            this.getLogger().info("Preparing start region for level " + j + " (Seed: " + worldserver.getSeed() + ")");
            if (worldserver.getWorld().getKeepSpawnInMemory()) {
                final ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
                for (int k = -192; k <= 192 && this.isRunning(); k += 16) {
                    for (int l = -192; l <= 192 && this.isRunning(); l += 16) {
                        final long i2 = System.currentTimeMillis();
                        if (i2 < i) {
                            i = i2;
                        }
                        if (i2 > i + 1000L) {
                            final int j2 = 148225;
                            final int k2 = (k + 192) * 385 + l + 1;
                            this.a_("Preparing spawn area", k2 * 100 / j2);
                            i = i2;
                        }
                        worldserver.chunkProviderServer.getChunkAt(chunkcoordinates.x + k >> 4, chunkcoordinates.z + l >> 4);
                    }
                }
            }
        }
        this.j();
    }
    
    public abstract boolean getGenerateStructures();
    
    public abstract EnumGamemode getGamemode();
    
    public abstract int getDifficulty();
    
    public abstract boolean isHardcore();
    
    protected void a_(final String s, final int i) {
        this.c = s;
        this.d = i;
        this.getLogger().info(s + ": " + i + "%");
    }
    
    protected void j() {
        this.c = null;
        this.d = 0;
        this.server.enablePlugins(PluginLoadOrder.POSTWORLD);
    }
    
    protected void saveChunks(final boolean flag) throws ExceptionWorldConflict {
        if (!this.N) {
            for (int j = 0; j < this.worlds.size(); ++j) {
                final WorldServer worldserver = this.worlds.get(j);
                if (worldserver != null) {
                    if (!flag) {
                        this.getLogger().info("Saving chunks for level '" + worldserver.getWorldData().getName() + "'/" + worldserver.worldProvider.getName());
                    }
                    worldserver.save(true, null);
                    worldserver.saveLevel();
                    final WorldSaveEvent event = new WorldSaveEvent(worldserver.getWorld());
                    this.server.getPluginManager().callEvent(event);
                }
            }
        }
    }
    
    public void stop() throws ExceptionWorldConflict {
        if (!this.N) {
            this.getLogger().info("Stopping server");
            if (this.server != null) {
                this.server.disablePlugins();
            }
            if (this.ae() != null) {
                this.ae().a();
            }
            if (this.s != null) {
                this.getLogger().info("Saving players");
                this.s.savePlayers();
                this.s.r();
            }
            this.getLogger().info("Saving worlds");
            this.saveChunks(false);
            if (this.m != null && this.m.d()) {
                this.m.e();
            }
        }
    }
    
    public String getServerIp() {
        return this.serverIp;
    }
    
    public void d(final String s) {
        this.serverIp = s;
    }
    
    public boolean isRunning() {
        return this.isRunning;
    }
    
    public void safeShutdown() {
        this.isRunning = false;
    }
    
    public void run() {
        try {
            if (this.init()) {
                long lastTick = 0L;
                while (this.isRunning) {
                    final long curTime = System.nanoTime();
                    final long wait = 50000000L - (curTime - lastTick) - MinecraftServer.catchupTime;
                    if (wait > 0L) {
                        Thread.sleep(wait / 1000000L);
                        MinecraftServer.catchupTime = 0L;
                    }
                    else {
                        MinecraftServer.catchupTime = Math.min(1000000000L, Math.abs(wait));
                        MinecraftServer.currentTPS = MinecraftServer.currentTPS * 0.95 + 1.0E9 / (curTime - lastTick) * 0.05;
                        lastTick = curTime;
                        ++MinecraftServer.currentTick;
                        SpigotTimings.serverTickTimer.startTiming();
                        this.q();
                        SpigotTimings.serverTickTimer.stopTiming();
                        CustomTimingsHandler.tick();
                        WatchdogThread.tick();
                    }
                    this.P = true;
                }
            }
            else {
                this.a((CrashReport)null);
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            this.getLogger().severe("Encountered an unexpected exception " + throwable.getClass().getSimpleName(), throwable);
            CrashReport crashreport = null;
            if (throwable instanceof ReportedException) {
                crashreport = this.b(((ReportedException)throwable).a());
            }
            else {
                crashreport = this.b(new CrashReport("Exception in server tick loop", throwable));
            }
            final File file1 = new File(new File(this.o(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt");
            if (crashreport.a(file1, this.getLogger())) {
                this.getLogger().severe("This crash report has been saved to: " + file1.getAbsolutePath());
            }
            else {
                this.getLogger().severe("We were unable to save this crash report to disk.");
            }
            this.a(crashreport);
            try {
                WatchdogThread.doStop();
                this.stop();
                this.isStopped = true;
            }
            catch (Throwable throwable2) {
                throwable2.printStackTrace();
            }
            finally {
                try {
                    this.reader.getTerminal().restore();
                }
                catch (Exception ex) {}
                this.p();
            }
        }
        finally {
            try {
                WatchdogThread.doStop();
                this.stop();
                this.isStopped = true;
            }
            catch (Throwable throwable3) {
                throwable3.printStackTrace();
                try {
                    this.reader.getTerminal().restore();
                }
                catch (Exception ex2) {}
                this.p();
            }
            finally {
                try {
                    this.reader.getTerminal().restore();
                }
                catch (Exception ex3) {}
                this.p();
            }
        }
    }
    
    protected File o() {
        return new File(".");
    }
    
    protected void a(final CrashReport crashreport) {
    }
    
    protected void p() {
    }
    
    protected void q() throws ExceptionWorldConflict {
        final long i = System.nanoTime();
        AxisAlignedBB.a().a();
        ++this.ticks;
        if (this.S) {
            this.S = false;
            this.methodProfiler.a = true;
            this.methodProfiler.a();
        }
        this.methodProfiler.a("root");
        this.r();
        if (this.autosavePeriod > 0 && this.ticks % this.autosavePeriod == 0) {
            this.methodProfiler.a("save");
            this.s.savePlayers();
            this.saveChunks(true);
            this.methodProfiler.b();
        }
        this.methodProfiler.a("tallying");
        this.i[this.ticks % 100] = System.nanoTime() - i;
        this.e[this.ticks % 100] = Packet.q - this.D;
        this.D = Packet.q;
        this.f[this.ticks % 100] = Packet.r - this.E;
        this.E = Packet.r;
        this.g[this.ticks % 100] = Packet.o - this.F;
        this.F = Packet.o;
        this.h[this.ticks % 100] = Packet.p - this.G;
        this.G = Packet.p;
        this.methodProfiler.b();
        this.methodProfiler.a("snooper");
        if (!this.m.d() && this.ticks > 100) {
            this.m.a();
        }
        if (this.ticks % 6000 == 0) {
            this.m.b();
        }
        this.methodProfiler.b();
        this.methodProfiler.b();
    }
    
    public void r() {
        this.methodProfiler.a("levels");
        SpigotTimings.schedulerTimer.startTiming();
        this.server.getScheduler().mainThreadHeartbeat(this.ticks);
        while (!this.processQueue.isEmpty()) {
            this.processQueue.remove().run();
        }
        SpigotTimings.schedulerTimer.stopTiming();
        SpigotTimings.chunkIOTickTimer.startTiming();
        ChunkIOExecutor.tick();
        SpigotTimings.chunkIOTickTimer.stopTiming();
        if (this.ticks % 20 == 0) {
            for (int i = 0; i < this.getPlayerList().players.size(); ++i) {
                final EntityPlayer entityplayer = this.getPlayerList().players.get(i);
                entityplayer.playerConnection.sendPacket(new Packet4UpdateTime(entityplayer.world.getTime(), entityplayer.getPlayerTime()));
            }
        }
        for (int i = 0; i < this.worlds.size(); ++i) {
            final long j = System.nanoTime();
            final WorldServer worldserver = this.worlds.get(i);
            this.methodProfiler.a(worldserver.getWorldData().getName());
            this.methodProfiler.a("pools");
            worldserver.getVec3DPool().a();
            this.methodProfiler.b();
            this.methodProfiler.a("tick");
            try {
                worldserver.doTick();
            }
            catch (Throwable throwable) {
                final CrashReport crashreport = CrashReport.a(throwable, "Exception ticking world");
                worldserver.a(crashreport);
                throw new ReportedException(crashreport);
            }
            try {
                worldserver.tickEntities();
            }
            catch (Throwable throwable2) {
                final CrashReport crashreport = CrashReport.a(throwable2, "Exception ticking world entities");
                worldserver.a(crashreport);
                throw new ReportedException(crashreport);
            }
            this.methodProfiler.b();
            this.methodProfiler.a("tracker");
            worldserver.timings.tracker.startTiming();
            worldserver.getTracker().updatePlayers();
            worldserver.timings.tracker.stopTiming();
            this.methodProfiler.b();
            this.methodProfiler.b();
        }
        this.methodProfiler.c("connection");
        SpigotTimings.connectionTimer.startTiming();
        this.ae().b();
        SpigotTimings.connectionTimer.stopTiming();
        this.methodProfiler.c("players");
        SpigotTimings.playerListTimer.startTiming();
        this.s.tick();
        SpigotTimings.playerListTimer.stopTiming();
        this.methodProfiler.c("tickables");
        SpigotTimings.tickablesTimer.startTiming();
        for (int i = 0; i < this.o.size(); ++i) {
            this.o.get(i).a();
        }
        SpigotTimings.tickablesTimer.stopTiming();
        this.methodProfiler.b();
    }
    
    public boolean getAllowNether() {
        return true;
    }
    
    public void a(final IUpdatePlayerListBox iupdateplayerlistbox) {
        this.o.add(iupdateplayerlistbox);
    }
    
    public static void main(final OptionSet options) {
        StatisticList.a();
        IConsoleLogManager iconsolelogmanager = null;
        try {
            final DedicatedServer dedicatedserver = new DedicatedServer(options);
            iconsolelogmanager = dedicatedserver.getLogger();
            if (options.has("port")) {
                final int port = (int)options.valueOf("port");
                if (port > 0) {
                    dedicatedserver.setPort(port);
                }
            }
            if (options.has("universe")) {
                dedicatedserver.universe = (File)options.valueOf("universe");
            }
            if (options.has("world")) {
                dedicatedserver.l((String)options.valueOf("world"));
            }
            dedicatedserver.primaryThread.start();
        }
        catch (Exception exception) {
            if (iconsolelogmanager != null) {
                iconsolelogmanager.severe("Failed to start the minecraft server", exception);
            }
            else {
                Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to start the minecraft server", exception);
            }
        }
    }
    
    public void t() {
    }
    
    public File e(final String s) {
        return new File(this.o(), s);
    }
    
    public void info(final String s) {
        this.getLogger().info(s);
    }
    
    public void warning(final String s) {
        this.getLogger().warning(s);
    }
    
    public WorldServer getWorldServer(final int i) {
        for (final WorldServer world : this.worlds) {
            if (world.dimension == i) {
                return world;
            }
        }
        return this.worlds.get(0);
    }
    
    public String u() {
        return this.serverIp;
    }
    
    public int v() {
        return this.r;
    }
    
    public String w() {
        return this.motd;
    }
    
    public String getVersion() {
        return "1.5.2";
    }
    
    public int y() {
        return this.s.getPlayerCount();
    }
    
    public int z() {
        return this.s.getMaxPlayers();
    }
    
    public String[] getPlayers() {
        return this.s.d();
    }
    
    public String getPlugins() {
        final StringBuilder result = new StringBuilder();
        final Plugin[] plugins = this.server.getPluginManager().getPlugins();
        result.append(this.server.getName());
        result.append(" on Bukkit ");
        result.append(this.server.getBukkitVersion());
        if (plugins.length > 0 && this.server.getQueryPlugins()) {
            result.append(": ");
            for (int i = 0; i < plugins.length; ++i) {
                if (i > 0) {
                    result.append("; ");
                }
                result.append(plugins[i].getDescription().getName());
                result.append(" ");
                result.append(plugins[i].getDescription().getVersion().replaceAll(";", ","));
            }
        }
        return result.toString();
    }
    
    public String h(final String s) {
        final Waitable<String> waitable = new Waitable<String>() {
            protected String evaluate() {
                RemoteControlCommandListener.instance.c();
                final RemoteServerCommandEvent event = new RemoteServerCommandEvent(MinecraftServer.this.remoteConsole, s);
                MinecraftServer.this.server.getPluginManager().callEvent(event);
                final ServerCommand servercommand = new ServerCommand(event.getCommand(), RemoteControlCommandListener.instance);
                MinecraftServer.this.server.dispatchServerCommand(MinecraftServer.this.remoteConsole, servercommand);
                return RemoteControlCommandListener.instance.d();
            }
        };
        this.processQueue.add(waitable);
        try {
            return waitable.get();
        }
        catch (ExecutionException e) {
            throw new RuntimeException("Exception processing rcon command " + s, e.getCause());
        }
        catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted processing rcon command " + s, e2);
        }
    }
    
    public boolean isDebugging() {
        return this.getPropertyManager().getBoolean("debug", false);
    }
    
    public void i(final String s) {
        this.getLogger().severe(s);
    }
    
    public void j(final String s) {
        if (this.isDebugging()) {
            this.getLogger().info(s);
        }
    }
    
    public String getServerModName() {
        return "craftbukkit";
    }
    
    public CrashReport b(final CrashReport crashreport) {
        crashreport.g().a("Profiler Position", new CrashReportProfilerPosition(this));
        if (this.worlds != null && this.worlds.size() > 0 && this.worlds.get(0) != null) {
            crashreport.g().a("Vec3 Pool Size", new CrashReportVec3DPoolSize(this));
        }
        if (this.s != null) {
            crashreport.g().a("Player Count", new CrashReportPlayerCount(this));
        }
        return crashreport;
    }
    
    public List a(final ICommandListener icommandlistener, final String s) {
        return this.server.tabComplete(icommandlistener, s);
    }
    
    public static MinecraftServer getServer() {
        return MinecraftServer.k;
    }
    
    public String getName() {
        return "Server";
    }
    
    public void sendMessage(final String s) {
        this.getLogger().info(StripColor.a(s));
    }
    
    public boolean a(final int i, final String s) {
        return true;
    }
    
    public String a(final String s, final Object... aobject) {
        return LocaleLanguage.a().a(s, aobject);
    }
    
    public ICommandHandler getCommandHandler() {
        return this.p;
    }
    
    public KeyPair F() {
        return this.H;
    }
    
    public int G() {
        return this.r;
    }
    
    public void setPort(final int i) {
        this.r = i;
    }
    
    public String H() {
        return this.I;
    }
    
    public void k(final String s) {
        this.I = s;
    }
    
    public boolean I() {
        return this.I != null;
    }
    
    public String J() {
        return this.J;
    }
    
    public void l(final String s) {
        this.J = s;
    }
    
    public void a(final KeyPair keypair) {
        this.H = keypair;
    }
    
    public void c(final int i) {
        for (int j = 0; j < this.worlds.size(); ++j) {
            final WorldServer worldserver = this.worlds.get(j);
            if (worldserver != null) {
                if (worldserver.getWorldData().isHardcore()) {
                    worldserver.difficulty = 3;
                    worldserver.setSpawnFlags(true, true);
                }
                else if (this.I()) {
                    worldserver.difficulty = i;
                    worldserver.setSpawnFlags(worldserver.difficulty > 0, true);
                }
                else {
                    worldserver.difficulty = i;
                    worldserver.setSpawnFlags(this.getSpawnMonsters(), this.spawnAnimals);
                }
            }
        }
    }
    
    protected boolean getSpawnMonsters() {
        return true;
    }
    
    public boolean M() {
        return this.demoMode;
    }
    
    public void b(final boolean flag) {
        this.demoMode = flag;
    }
    
    public void c(final boolean flag) {
        this.M = flag;
    }
    
    public Convertable getConvertable() {
        return this.convertable;
    }
    
    public void P() {
        this.N = true;
        this.getConvertable().d();
        for (int i = 0; i < this.worlds.size(); ++i) {
            final WorldServer worldserver = this.worlds.get(i);
            if (worldserver != null) {
                worldserver.saveLevel();
            }
        }
        this.getConvertable().e(this.worlds.get(0).getDataManager().g());
        this.safeShutdown();
    }
    
    public String getTexturePack() {
        return this.O;
    }
    
    public void setTexturePack(final String s) {
        this.O = s;
    }
    
    public void a(final MojangStatisticsGenerator mojangstatisticsgenerator) {
        mojangstatisticsgenerator.a("whitelist_enabled", false);
        mojangstatisticsgenerator.a("whitelist_count", 0);
        mojangstatisticsgenerator.a("players_current", this.y());
        mojangstatisticsgenerator.a("players_max", this.z());
        mojangstatisticsgenerator.a("players_seen", this.s.getSeenPlayers().length);
        mojangstatisticsgenerator.a("uses_auth", this.onlineMode);
        mojangstatisticsgenerator.a("gui_state", this.ag() ? "enabled" : "disabled");
        mojangstatisticsgenerator.a("avg_tick_ms", (int)(MathHelper.a(this.i) * 1.0E-6));
        mojangstatisticsgenerator.a("avg_sent_packet_count", (int)MathHelper.a(this.e));
        mojangstatisticsgenerator.a("avg_sent_packet_size", (int)MathHelper.a(this.f));
        mojangstatisticsgenerator.a("avg_rec_packet_count", (int)MathHelper.a(this.g));
        mojangstatisticsgenerator.a("avg_rec_packet_size", (int)MathHelper.a(this.h));
        int i = 0;
        for (int j = 0; j < this.worlds.size(); ++j) {
            final WorldServer worldserver = this.worlds.get(j);
            final WorldData worlddata = worldserver.getWorldData();
            mojangstatisticsgenerator.a("world[" + i + "][dimension]", worldserver.worldProvider.dimension);
            mojangstatisticsgenerator.a("world[" + i + "][mode]", worlddata.getGameType());
            mojangstatisticsgenerator.a("world[" + i + "][difficulty]", worldserver.difficulty);
            mojangstatisticsgenerator.a("world[" + i + "][hardcore]", worlddata.isHardcore());
            mojangstatisticsgenerator.a("world[" + i + "][generator_name]", worlddata.getType().name());
            mojangstatisticsgenerator.a("world[" + i + "][generator_version]", worlddata.getType().getVersion());
            mojangstatisticsgenerator.a("world[" + i + "][height]", this.C);
            mojangstatisticsgenerator.a("world[" + i + "][chunks_loaded]", worldserver.K().getLoadedChunks());
            ++i;
        }
        mojangstatisticsgenerator.a("worlds", i);
    }
    
    public void b(final MojangStatisticsGenerator mojangstatisticsgenerator) {
        mojangstatisticsgenerator.a("singleplayer", this.I());
        mojangstatisticsgenerator.a("server_brand", this.getServerModName());
        mojangstatisticsgenerator.a("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
        mojangstatisticsgenerator.a("dedicated", this.T());
    }
    
    public boolean getSnooperEnabled() {
        return true;
    }
    
    public int S() {
        return Spigot.textureResolution;
    }
    
    public abstract boolean T();
    
    public boolean getOnlineMode() {
        return this.server.getOnlineMode();
    }
    
    public void setOnlineMode(final boolean flag) {
        this.onlineMode = flag;
    }
    
    public boolean getSpawnAnimals() {
        return this.spawnAnimals;
    }
    
    public void setSpawnAnimals(final boolean flag) {
        this.spawnAnimals = flag;
    }
    
    public boolean getSpawnNPCs() {
        return this.spawnNPCs;
    }
    
    public void setSpawnNPCs(final boolean flag) {
        this.spawnNPCs = flag;
    }
    
    public boolean getPvP() {
        return this.pvpMode;
    }
    
    public void setPvP(final boolean flag) {
        this.pvpMode = flag;
    }
    
    public boolean getAllowFlight() {
        return this.allowFlight;
    }
    
    public void setAllowFlight(final boolean flag) {
        this.allowFlight = flag;
    }
    
    public abstract boolean getEnableCommandBlock();
    
    public String getMotd() {
        return this.motd;
    }
    
    public void setMotd(final String s) {
        this.motd = s;
    }
    
    public int getMaxBuildHeight() {
        return this.C;
    }
    
    public void d(final int i) {
        this.C = i;
    }
    
    public boolean isStopped() {
        return this.isStopped;
    }
    
    public PlayerList getPlayerList() {
        return this.s;
    }
    
    public void a(final PlayerList playerlist) {
        this.s = playerlist;
    }
    
    public void a(final EnumGamemode enumgamemode) {
        for (int i = 0; i < this.worlds.size(); ++i) {
            getServer().worlds.get(i).getWorldData().setGameType(enumgamemode);
        }
    }
    
    public abstract ServerConnection ae();
    
    public boolean ag() {
        return false;
    }
    
    public abstract String a(final EnumGamemode p0, final boolean p1);
    
    public int ah() {
        return this.ticks;
    }
    
    public void ai() {
        this.S = true;
    }
    
    public ChunkCoordinates b() {
        return new ChunkCoordinates(0, 0, 0);
    }
    
    public int getSpawnProtection() {
        return 16;
    }
    
    public boolean a(final net.minecraft.server.v1_5_R3.World world, final int i, final int j, final int k, final EntityHuman entityhuman) {
        return false;
    }
    
    public abstract IConsoleLogManager getLogger();
    
    public void setForceGamemode(final boolean flag) {
        this.T = flag;
    }
    
    public boolean getForceGamemode() {
        return this.T;
    }
    
    public static PlayerList a(final MinecraftServer minecraftserver) {
        return minecraftserver.s;
    }
    
    static {
        MinecraftServer.k = null;
        MinecraftServer.currentTick = (int)(System.currentTimeMillis() / 50L);
        MinecraftServer.currentTPS = 0.0;
        MinecraftServer.catchupTime = 0L;
    }
}
