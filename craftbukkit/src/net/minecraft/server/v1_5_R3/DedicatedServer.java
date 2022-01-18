// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.File;
import org.bukkit.event.Event;
import org.bukkit.command.CommandSender;
import org.bukkit.event.server.ServerCommandEvent;
import java.util.concurrent.Callable;
import java.net.UnknownHostException;
import org.bukkit.craftbukkit.v1_5_R3.command.CraftRemoteConsoleCommandSender;
import java.util.Random;
import org.spigotmc.MultiplexingServerConnection;
import java.net.InetAddress;
import java.io.OutputStream;
import java.io.PrintStream;
import org.bukkit.craftbukkit.v1_5_R3.LoggerOutputStream;
import java.util.logging.Level;
import java.util.Collections;
import java.util.ArrayList;
import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
import java.util.List;

public class DedicatedServer extends MinecraftServer implements IMinecraftServer
{
    private final List k;
    private final IConsoleLogManager l;
    private RemoteStatusListener m;
    private RemoteControlListener n;
    public PropertyManager propertyManager;
    private boolean generateStructures;
    private EnumGamemode q;
    private ServerConnection r;
    private boolean s;
    
    public DedicatedServer(final OptionSet options) {
        super(options);
        this.k = Collections.synchronizedList(new ArrayList<Object>());
        this.s = false;
        this.l = new ConsoleLogManager("Minecraft-Server", null, null);
    }
    
    protected boolean init() throws UnknownHostException {
        final ThreadCommandReader threadcommandreader = new ThreadCommandReader(this);
        threadcommandreader.setDaemon(true);
        threadcommandreader.start();
        System.setOut(new PrintStream(new LoggerOutputStream(this.getLogger().getLogger(), Level.INFO), true));
        System.setErr(new PrintStream(new LoggerOutputStream(this.getLogger().getLogger(), Level.SEVERE), true));
        this.getLogger().info("Starting minecraft server version 1.5.2");
        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
            this.getLogger().warning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }
        this.getLogger().info("Loading properties");
        this.propertyManager = new PropertyManager(this.options, this.getLogger());
        this.a(new DedicatedPlayerList(this));
        if (this.I()) {
            this.d("127.0.0.1");
        }
        else {
            this.setOnlineMode(this.propertyManager.getBoolean("online-mode", true));
            this.d(this.propertyManager.getString("server-ip", ""));
        }
        this.setSpawnAnimals(this.propertyManager.getBoolean("spawn-animals", true));
        this.setSpawnNPCs(this.propertyManager.getBoolean("spawn-npcs", true));
        this.setPvP(this.propertyManager.getBoolean("pvp", true));
        this.setAllowFlight(this.propertyManager.getBoolean("allow-flight", false));
        this.setTexturePack(this.propertyManager.getString("texture-pack", ""));
        this.setMotd(this.propertyManager.getString("motd", "A Minecraft Server"));
        this.setForceGamemode(this.propertyManager.getBoolean("force-gamemode", false));
        if (this.propertyManager.getInt("difficulty", 1) < 0) {
            this.propertyManager.a("difficulty", 0);
        }
        else if (this.propertyManager.getInt("difficulty", 1) > 3) {
            this.propertyManager.a("difficulty", 3);
        }
        this.generateStructures = this.propertyManager.getBoolean("generate-structures", true);
        final int i = this.propertyManager.getInt("gamemode", EnumGamemode.SURVIVAL.a());
        this.q = WorldSettings.a(i);
        this.getLogger().info("Default game type: " + this.q);
        InetAddress inetaddress = null;
        if (this.getServerIp().length() > 0) {
            inetaddress = InetAddress.getByName(this.getServerIp());
        }
        if (this.G() < 0) {
            this.setPort(this.propertyManager.getInt("server-port", 25565));
        }
        this.getLogger().info("Generating keypair");
        this.a(MinecraftEncryption.b());
        this.getLogger().info("Starting Minecraft server on " + ((this.getServerIp().length() == 0) ? "*" : this.getServerIp()) + ":" + this.G());
        try {
            this.r = new MultiplexingServerConnection(this);
        }
        catch (Throwable ioexception) {
            this.getLogger().warning("**** FAILED TO BIND TO PORT!");
            this.getLogger().warning("The exception was: {0}", ioexception.toString());
            this.getLogger().warning("Perhaps a server is already running on that port?");
            return false;
        }
        if (!this.getOnlineMode()) {
            this.getLogger().warning("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            this.getLogger().warning("The server will make no attempt to authenticate usernames. Beware.");
            this.getLogger().warning("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
            this.getLogger().warning("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
        }
        this.convertable = new WorldLoaderServer(this.server.getWorldContainer());
        final long j = System.nanoTime();
        if (this.J() == null) {
            this.l(this.propertyManager.getString("level-name", "world"));
        }
        final String s = this.propertyManager.getString("level-seed", "");
        final String s2 = this.propertyManager.getString("level-type", "DEFAULT");
        final String s3 = this.propertyManager.getString("generator-settings", "");
        long k = new Random().nextLong();
        if (s.length() > 0) {
            try {
                final long l = Long.parseLong(s);
                if (l != 0L) {
                    k = l;
                }
            }
            catch (NumberFormatException numberformatexception) {
                k = s.hashCode();
            }
        }
        WorldType worldtype = WorldType.getType(s2);
        if (worldtype == null) {
            worldtype = WorldType.NORMAL;
        }
        this.d(this.propertyManager.getInt("max-build-height", 256));
        this.d((this.getMaxBuildHeight() + 8) / 16 * 16);
        this.d(MathHelper.a(this.getMaxBuildHeight(), 64, 256));
        this.propertyManager.a("max-build-height", this.getMaxBuildHeight());
        this.getLogger().info("Preparing level \"" + this.J() + "\"");
        this.a(this.J(), this.J(), k, worldtype, s3);
        final long i2 = System.nanoTime() - j;
        final String s4 = String.format("%.3fs", i2 / 1.0E9);
        this.getLogger().info("Done (" + s4 + ")! For help, type \"help\" or \"?\"");
        if (this.propertyManager.getBoolean("enable-query", false)) {
            this.getLogger().info("Starting GS4 status listener");
            (this.m = new RemoteStatusListener(this)).a();
        }
        if (this.propertyManager.getBoolean("enable-rcon", false)) {
            this.getLogger().info("Starting remote control listener");
            (this.n = new RemoteControlListener(this)).a();
            this.remoteConsole = new CraftRemoteConsoleCommandSender();
        }
        if (this.server.getBukkitSpawnRadius() > -1) {
            this.getLogger().info("'settings.spawn-radius' in bukkit.yml has been moved to 'spawn-protection' in server.properties. I will move your config for you.");
            this.propertyManager.properties.remove("spawn-protection");
            this.propertyManager.getInt("spawn-protection", this.server.getBukkitSpawnRadius());
            this.server.removeBukkitSpawnRadius();
            this.propertyManager.savePropertiesFile();
        }
        return true;
    }
    
    public PropertyManager getPropertyManager() {
        return this.propertyManager;
    }
    
    public boolean getGenerateStructures() {
        return this.generateStructures;
    }
    
    public EnumGamemode getGamemode() {
        return this.q;
    }
    
    public int getDifficulty() {
        return Math.max(0, Math.min(3, this.propertyManager.getInt("difficulty", 1)));
    }
    
    public boolean isHardcore() {
        return this.propertyManager.getBoolean("hardcore", false);
    }
    
    protected void a(final CrashReport crashreport) {
        while (this.isRunning()) {
            this.an();
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException interruptedexception) {
                interruptedexception.printStackTrace();
            }
        }
    }
    
    public CrashReport b(CrashReport crashreport) {
        crashreport = super.b(crashreport);
        crashreport.g().a("Is Modded", new CrashReportModded(this));
        crashreport.g().a("Type", new CrashReportType(this));
        return crashreport;
    }
    
    protected void p() {
        System.exit(0);
    }
    
    public void r() {
        super.r();
        this.an();
    }
    
    public boolean getAllowNether() {
        return this.propertyManager.getBoolean("allow-nether", true);
    }
    
    public boolean getSpawnMonsters() {
        return this.propertyManager.getBoolean("spawn-monsters", true);
    }
    
    public void a(final MojangStatisticsGenerator mojangstatisticsgenerator) {
        mojangstatisticsgenerator.a("whitelist_enabled", this.ao().getHasWhitelist());
        mojangstatisticsgenerator.a("whitelist_count", this.ao().getWhitelisted().size());
        super.a(mojangstatisticsgenerator);
    }
    
    public boolean getSnooperEnabled() {
        return this.propertyManager.getBoolean("snooper-enabled", true);
    }
    
    public void issueCommand(final String s, final ICommandListener icommandlistener) {
        this.k.add(new ServerCommand(s, icommandlistener));
    }
    
    public void an() {
        while (!this.k.isEmpty()) {
            ServerCommand servercommand = this.k.remove(0);
            final ServerCommandEvent event = new ServerCommandEvent(this.console, servercommand.command);
            this.server.getPluginManager().callEvent(event);
            servercommand = new ServerCommand(event.getCommand(), servercommand.source);
            this.server.dispatchServerCommand(this.console, servercommand);
        }
    }
    
    public boolean T() {
        return true;
    }
    
    public DedicatedPlayerList ao() {
        return (DedicatedPlayerList)super.getPlayerList();
    }
    
    public ServerConnection ae() {
        return this.r;
    }
    
    public int a(final String s, final int i) {
        return this.propertyManager.getInt(s, i);
    }
    
    public String a(final String s, final String s1) {
        return this.propertyManager.getString(s, s1);
    }
    
    public boolean a(final String s, final boolean flag) {
        return this.propertyManager.getBoolean(s, flag);
    }
    
    public void a(final String s, final Object object) {
        this.propertyManager.a(s, object);
    }
    
    public void a() {
        this.propertyManager.savePropertiesFile();
    }
    
    public String b_() {
        final File file1 = this.propertyManager.c();
        return (file1 != null) ? file1.getAbsolutePath() : "No settings file";
    }
    
    public void ap() {
        ServerGUI.a(this);
        this.s = true;
    }
    
    public boolean ag() {
        return this.s;
    }
    
    public String a(final EnumGamemode enumgamemode, final boolean flag) {
        return "";
    }
    
    public boolean getEnableCommandBlock() {
        return this.propertyManager.getBoolean("enable-command-block", false);
    }
    
    public int getSpawnProtection() {
        return this.propertyManager.getInt("spawn-protection", super.getSpawnProtection());
    }
    
    public boolean a(final World world, final int i, final int j, final int k, final EntityHuman entityhuman) {
        if (world.worldProvider.dimension != 0) {
            return false;
        }
        if (this.ao().getOPs().isEmpty()) {
            return false;
        }
        if (this.ao().isOp(entityhuman.name)) {
            return false;
        }
        if (this.getSpawnProtection() <= 0) {
            return false;
        }
        final ChunkCoordinates chunkcoordinates = world.getSpawn();
        final int l = MathHelper.a(i - chunkcoordinates.x);
        final int i2 = MathHelper.a(k - chunkcoordinates.z);
        final int j2 = Math.max(l, i2);
        return j2 <= this.getSpawnProtection();
    }
    
    public IConsoleLogManager getLogger() {
        return this.l;
    }
    
    public PlayerList getPlayerList() {
        return this.ao();
    }
}
