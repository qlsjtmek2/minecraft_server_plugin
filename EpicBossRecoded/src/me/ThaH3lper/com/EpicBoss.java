// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import me.ThaH3lper.com.Commands.CommandsHandler;
import me.ThaH3lper.com.Spawning.SpawnListener;
import me.ThaH3lper.com.Chunk.ChunkUnload;
import me.ThaH3lper.com.egg.BossEggListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import java.util.ArrayList;
import me.ThaH3lper.com.Timer.Timer;
import me.ThaH3lper.com.locations.Locations;
import me.ThaH3lper.com.LoadBosses.LoadBoss;
import me.ThaH3lper.com.Boss.Boss;
import me.ThaH3lper.com.Spawning.Spawnings;
import java.util.List;
import com.herocraftonline.heroes.Heroes;
import me.ThaH3lper.com.Spawning.LoadSpawnings;
import me.ThaH3lper.com.Damage.DamageListener;
import me.ThaH3lper.com.Damage.HeroListener;
import me.ThaH3lper.com.Api.Api;
import me.ThaH3lper.com.Timer.TimerStuff;
import me.ThaH3lper.com.locations.LocationStuff;
import me.ThaH3lper.com.LoadBosses.LoadBossEquip;
import me.ThaH3lper.com.Skills.SkillsHandler;
import me.ThaH3lper.com.egg.BossEgg;
import me.ThaH3lper.com.Damage.DamageMethods;
import me.ThaH3lper.com.LoadBosses.LoadItems;
import me.ThaH3lper.com.LoadBosses.DropItems;
import me.ThaH3lper.com.LoadBosses.LoadConfigs;
import me.ThaH3lper.com.Timer.TimerSeconds;
import me.ThaH3lper.com.Boss.BossCalculations;
import org.bukkit.plugin.PluginManager;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class EpicBoss extends JavaPlugin
{
    public final Logger logger;
    public boolean SpoutEnabled;
    public boolean HeroesEnabled;
    PluginManager manager;
    public EpicBoss plugin;
    public BossCalculations bossCalculator;
    public Mobs mobs;
    public TimerSeconds timer;
    public SaveLoad Bosses;
    public SaveLoad Options;
    public SaveLoad SavedData;
    public SaveLoad Spawning;
    public LoadConfigs loadconfig;
    public DropItems dropitems;
    public LoadItems loaditems;
    public DamageMethods damagemethods;
    public BossEgg bossegg;
    public SkillsHandler skillhandler;
    public LoadBossEquip loadbossequip;
    public LocationStuff locationstuff;
    public TimerStuff timerstuff;
    public Api api;
    public HeroListener heroListener;
    public DamageListener damageListener;
    public LoadSpawnings loadSpawnings;
    public Heroes heroes;
    public String name;
    public boolean percentage;
    public boolean regain;
    public List<Spawnings> SpawningsList;
    public List<Boss> BossList;
    public List<LoadBoss> BossLoadList;
    public List<Locations> LocationList;
    public List<Timer> TimersList;
    public List<String> CustomSkills;
    
    public EpicBoss() {
        this.logger = Logger.getLogger("Minecraft");
        this.regain = false;
        this.SpawningsList = new ArrayList<Spawnings>();
        this.BossList = new ArrayList<Boss>();
        this.BossLoadList = new ArrayList<LoadBoss>();
        this.LocationList = new ArrayList<Locations>();
        this.TimersList = new ArrayList<Timer>();
        this.CustomSkills = new ArrayList<String>();
    }
    
    public void onDisable() {
        this.loadconfig.SaveAllBosses();
        final PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info("[EpicBoss-Recoded] " + pdfFile.getVersion() + " Has Been Disabled!");
    }
    
    public void onEnable() {
        this.plugin = this;
        this.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, (Runnable)new Runnable() {
            @Override
            public void run() {
                EpicBoss.this.damageListener = new DamageListener(EpicBoss.this.plugin);
                (EpicBoss.this.manager = EpicBoss.this.plugin.getServer().getPluginManager()).registerEvents((Listener)EpicBoss.this.damageListener, (Plugin)EpicBoss.this.plugin);
                EpicBoss.this.manager.registerEvents((Listener)new BossEggListener(EpicBoss.this.plugin), (Plugin)EpicBoss.this.plugin);
                EpicBoss.this.manager.registerEvents((Listener)new ChunkUnload(EpicBoss.this.plugin), (Plugin)EpicBoss.this.plugin);
                EpicBoss.this.manager.registerEvents((Listener)new SpawnListener(EpicBoss.this.plugin), (Plugin)EpicBoss.this.plugin);
                final PluginDescriptionFile pdfFile = EpicBoss.this.plugin.getDescription();
                EpicBoss.this.plugin.logger.info("[EpicBoss-Recoded] " + pdfFile.getVersion() + " Has Been Enabled!");
                EpicBoss.this.getCommand("EpicBoss").setExecutor((CommandExecutor)new CommandsHandler(EpicBoss.this.plugin));
                EpicBoss.this.bossCalculator = new BossCalculations(EpicBoss.this.plugin);
                EpicBoss.this.mobs = new Mobs();
                EpicBoss.this.Bosses = new SaveLoad(EpicBoss.this.plugin, "Bosses.yml");
                EpicBoss.this.Options = new SaveLoad(EpicBoss.this.plugin, "Options.yml");
                EpicBoss.this.SavedData = new SaveLoad(EpicBoss.this.plugin, "SavedData.yml");
                EpicBoss.this.Spawning = new SaveLoad(EpicBoss.this.plugin, "Spawning.yml");
                EpicBoss.this.loadconfig = new LoadConfigs(EpicBoss.this.plugin);
                EpicBoss.this.dropitems = new DropItems(EpicBoss.this.plugin);
                EpicBoss.this.loaditems = new LoadItems();
                EpicBoss.this.damagemethods = new DamageMethods(EpicBoss.this.plugin);
                EpicBoss.this.bossegg = new BossEgg(EpicBoss.this.plugin);
                EpicBoss.this.skillhandler = new SkillsHandler(EpicBoss.this.plugin);
                EpicBoss.this.loadbossequip = new LoadBossEquip(EpicBoss.this.plugin);
                EpicBoss.this.locationstuff = new LocationStuff(EpicBoss.this.plugin);
                EpicBoss.this.timerstuff = new TimerStuff(EpicBoss.this.plugin);
                EpicBoss.this.timer = new TimerSeconds(EpicBoss.this.plugin);
                EpicBoss.this.loadSpawnings = new LoadSpawnings(EpicBoss.this.plugin);
                EpicBoss.this.api = new Api(EpicBoss.this.plugin);
                if (EpicBoss.this.plugin.manager.isPluginEnabled("Spout")) {
                    EpicBoss.this.SpoutEnabled = true;
                }
                if (EpicBoss.this.plugin.manager.isPluginEnabled("Heroes")) {
                    EpicBoss.this.HeroesEnabled = true;
                    EpicBoss.this.heroes = (Heroes)Bukkit.getPluginManager().getPlugin("Heroes");
                    EpicBoss.this.heroListener = new HeroListener(EpicBoss.this.plugin, EpicBoss.this.damageListener);
                    EpicBoss.this.manager.registerEvents((Listener)EpicBoss.this.heroListener, (Plugin)EpicBoss.this.plugin);
                }
                EpicBoss.this.name = EpicBoss.this.Options.getCustomConfig().getString("BossTitle");
                EpicBoss.this.name = ChatColor.translateAlternateColorCodes('&', EpicBoss.this.name);
                EpicBoss.this.percentage = EpicBoss.this.Options.getCustomConfig().getBoolean("percentage");
                EpicBoss.this.regain = EpicBoss.this.Options.getCustomConfig().getBoolean("RegainHealth");
                EpicBoss.this.loadconfig.LoadAllBosses();
            }
        }, 1L);
    }
}
