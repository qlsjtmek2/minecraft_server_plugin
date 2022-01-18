// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import Physical.Fighters.MainModule.AbilityBase;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.event.Listener;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.Material;
import Physical.Fighters.MajorModule.AbilityList;
import Physical.Fighters.Script.MainScripter;
import Physical.Fighters.Script.MainScripter.ScriptStatus;
import Physical.Fighters.MainModule.CommandManager;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class PhysicalFighters extends JavaPlugin
{
    public static boolean DebugMode;
    public static boolean ProtoType;
    public static int BuildNumber;
    public static boolean Invincibility;
    public static boolean DefaultArmed;
    public static boolean SRankUsed;
    public static boolean Respawn;
    public static boolean AutoKick;
    public static boolean AutoBan;
    public static boolean HalfMonsterDamage;
    public static boolean AutoDifficultySetting;
    public static boolean MaxLevelSurvival;
    public static int Setlev;
    public static int EarlyInvincibleTime;
    public static boolean NoFoodMode;
    public static boolean KillerOutput;
    public static boolean AutoCoordinateOutput;
    public static boolean NoAnimal;
    public static boolean NoAbilitySetting;
    public static boolean NoClearInventory;
    public static boolean PrintTip;
    public static boolean ReverseMode;
    public static boolean AutoSave;
    public static boolean InventorySave;
    public static boolean AbilityOverLap;
    public static boolean InfinityDur;
    public static int RestrictionTime;
    public static boolean Kimiedition;
    public static boolean Specialability;
    public static boolean WoodGive;
    public static boolean TableGive;
    public static boolean Bapo;
    public static boolean Moone;
    public static boolean Gods;
    public static Logger log;
    public CommandManager cm;
    public MainScripter scripter;
    public AbilityList A_List;
    public static int sTimer = 240;
	public static int Time = 8;
    public static String w = ChatColor.WHITE + "[" + ChatColor.RED + "경고" + ChatColor.WHITE + "] " + ChatColor.RED;
    public static String p = ChatColor.WHITE + "[" + ChatColor.DARK_RED + "알림" + ChatColor.WHITE + "] " + ChatColor.GOLD;
    public static String a = ChatColor.WHITE + "[" + ChatColor.GREEN + "안내" + ChatColor.WHITE + "] " + ChatColor.WHITE;
    
    static {
        PhysicalFighters.DebugMode = false;
        PhysicalFighters.ProtoType = false;
        PhysicalFighters.BuildNumber = 120916;
        PhysicalFighters.Invincibility = false;
        PhysicalFighters.DefaultArmed = true;
        PhysicalFighters.SRankUsed = true;
        PhysicalFighters.Respawn = false;
        PhysicalFighters.AutoKick = true;
        PhysicalFighters.AutoBan = true;
        PhysicalFighters.HalfMonsterDamage = false;
        PhysicalFighters.AutoDifficultySetting = true;
        PhysicalFighters.MaxLevelSurvival = false;
        PhysicalFighters.Setlev = 222;
        PhysicalFighters.EarlyInvincibleTime = 10;
        PhysicalFighters.NoFoodMode = true;
        PhysicalFighters.KillerOutput = true;
        PhysicalFighters.AutoCoordinateOutput = true;
        PhysicalFighters.NoAnimal = false;
        PhysicalFighters.NoAbilitySetting = false;
        PhysicalFighters.NoClearInventory = false;
        PhysicalFighters.PrintTip = true;
        PhysicalFighters.ReverseMode = false;
        PhysicalFighters.AutoSave = false;
        PhysicalFighters.InventorySave = false;
        PhysicalFighters.AbilityOverLap = false;
        PhysicalFighters.InfinityDur = true;
        PhysicalFighters.RestrictionTime = 15;
        PhysicalFighters.Kimiedition = false;
        PhysicalFighters.Specialability = false;
        PhysicalFighters.WoodGive = false;
        PhysicalFighters.TableGive = false;
        PhysicalFighters.Bapo = true;
        PhysicalFighters.Moone = false;
        PhysicalFighters.Gods = false;
        PhysicalFighters.log = Logger.getLogger("Minecraft");
    }
    
    public void onEnable() {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "mw unload world_pvp");
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "mw load world_pvp");
        ACC.DefaultItem = Material.IRON_INGOT.getId();
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + PhysicalFighters.BuildNumber);
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
        this.cm = new CommandManager(this);
        this.getServer().getPluginManager().registerEvents((Listener)new EventManager(), (Plugin)this);
        PhysicalFighters.log.info(String.format("(!) 기본설정 로드중입니다.", new Object[0]));
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        Invincibility = getConfig().getBoolean("시작후 초반 무적");
        DefaultArmed = getConfig().getBoolean("기본 무장 제공");
        Respawn = getConfig().getBoolean("시작시 리스폰으로 이동");
        AutoKick = getConfig().getBoolean("사망시 자동으로 킥");
        AutoBan = getConfig().getBoolean("사망시 자동으로 밴(킥이 활성화 되어야 가능)");
        SRankUsed = getConfig().getBoolean("S랭크 능력 사용");
        HalfMonsterDamage = getConfig().getBoolean("몬스터의 공격력 반감");
        AutoDifficultySetting = getConfig().getBoolean("난이도 자동으로 Easy로 설정");
        MaxLevelSurvival = getConfig().getBoolean("레벨 지급");
        Setlev = getConfig().getInt("레벨 설정");
        EarlyInvincibleTime = getConfig().getInt("초반 무적 시간(분 단위)");
        NoFoodMode = getConfig().getBoolean("배고픔 무한 모드(관련 능력은 알아서 상향됨)");
        KillerOutput = getConfig().getBoolean("죽을 경우 죽인 사람을 보여줌");
        AutoCoordinateOutput = getConfig().getBoolean("일정시간마다 좌표 표시");
        NoAnimal = getConfig().getBoolean("동물 비활성화");
        NoAbilitySetting = getConfig().getBoolean("시작시 능력 추첨 안함");
        NoClearInventory = getConfig().getBoolean("시작시 인벤토리 초기화 안함");
        PrintTip = getConfig().getBoolean("시작후 팁 출력함");
        AutoSave = getConfig().getBoolean("서버 오토 세이브");
        InventorySave = getConfig().getBoolean("인벤토리 세이브");
        AbilityOverLap = getConfig().getBoolean("능력 중복 가능");
        InfinityDur = getConfig().getBoolean("내구도 무한");
        RestrictionTime = getConfig().getInt("일부 능력 금지 시간(분 단위, 0은 사용 안함)");
        Kimiedition = getConfig().getBoolean("극한모드");
        Specialability = getConfig().getBoolean("스페셜능력모드");
        TableGive = getConfig().getBoolean("책장 지급");
        WoodGive = getConfig().getBoolean("나무 지급");
        Gods = getConfig().getBoolean("신등급활성화");
        
        log.info(String.format("(!) 능력을 초기화합니다.", new Object[0]));
        AbilityBase.InitAbilityBase(this, this.cm);
        this.A_List = new AbilityList();
        log.info(String.format("(!) 스크립터를 초기화합니다.", new Object[0]));
        this.scripter = new MainScripter(this, this.cm);
        if (PhysicalFighters.Invincibility && PhysicalFighters.EarlyInvincibleTime <= 0) {
            log.info(String.format("(!) 초반무적이 1분으로 설정됩니다. [E.시간이 0분 이하입니다]", new Object[0]));
            PhysicalFighters.EarlyInvincibleTime = 1;
        }
        if (PhysicalFighters.RestrictionTime < 0) {
        	   log.info(String.format("(!) 제약 시간 값은 0보다 커야합니다. 0으로 설정됩니다.", new Object[0]));
            PhysicalFighters.RestrictionTime = 0;
        }
        PhysicalFighters.log.info(String.format("(!) 능력 %d개가 등록되있습니다.", AbilityList.AbilityList.size() - 1));
        if (PhysicalFighters.Kimiedition) {
            PhysicalFighters.log.info(String.format("(!) 극한모드 적용", new Object[0]));
        }
        if (PhysicalFighters.Specialability) {
            PhysicalFighters.log.info(String.format("(!) 스페셜능력모드 적용", new Object[0]));
        }
        if (PhysicalFighters.Gods) {
            PhysicalFighters.log.info(String.format("(!) '신' 등급 활성화!", new Object[0]));
        }
        if (PhysicalFighters.AutoSave) {
            for (final World w : Bukkit.getServer().getWorlds()) {
                w.setAutoSave(true);
            }
        }
        
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
        	public void run() {
        	    if (MainScripter.Scenario == ScriptStatus.NoPlay) {
        	    	sTimer -= 10;
        	    	
        			if (sTimer == 180) {
        				Bukkit.broadcastMessage(p + "§6현재 §c능력자 §6시작 카운트가 §c3분 §6남았습니다.");
        				for (Player p : Bukkit.getOnlinePlayers()) {
        					if (MainScripter.ExceptionList.contains(p)) {
        						p.sendMessage(a + "§f당신은 능력자를 §c불참§f중입니다. §b참여§f를 희망하시면 §e[ /참여 ]§f를 입력해주세요.");
        					}
        				}
        			}
        			else if (sTimer == 60) {
        				Bukkit.broadcastMessage(p + "§6현재 §c능력자 §6시작 카운트가 §c1분 §6남았습니다.");
        				for (Player p : Bukkit.getOnlinePlayers()) {
        					if (MainScripter.ExceptionList.contains(p)) {
        						p.sendMessage(a + "§f당신은 능력자를 §c불참§f중입니다. §b참여§f를 희망하시면 §e[ /참여 ]§f를 입력해주세요.");
        					}
        				}
        			}
        			else if (sTimer == 30) Bukkit.broadcastMessage(p + "§6현재 §c능력자 §6시작 카운트가 §c30초 §6남았습니다.");
         			else if (sTimer == 20) Bukkit.broadcastMessage(p + "§6현재 §c능력자 §6시작 카운트가 §c20초 §6남았습니다.");
         			else if (sTimer == 10) Bukkit.broadcastMessage(p + "§6현재 §c능력자 §6시작 카운트가 §c10초 §6남았습니다.");
        			else if (sTimer <= 0) {
        				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "va start");
        	   	    	sTimer = 240;
        			}
        	    } else {
        	    	sTimer = 240;
        	    }
        	}
		}, 200L, 200L);
    }
    
    public void onDisable() {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "mw unload world_pvp");
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "mw load world_pvp");
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + PhysicalFighters.BuildNumber);
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
    }
    
	public static boolean isStringDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
