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
    public static String w = ChatColor.WHITE + "[" + ChatColor.RED + "���" + ChatColor.WHITE + "] " + ChatColor.RED;
    public static String p = ChatColor.WHITE + "[" + ChatColor.DARK_RED + "�˸�" + ChatColor.WHITE + "] " + ChatColor.GOLD;
    public static String a = ChatColor.WHITE + "[" + ChatColor.GREEN + "�ȳ�" + ChatColor.WHITE + "] " + ChatColor.WHITE;
    
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
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + PhysicalFighters.BuildNumber);
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
        this.cm = new CommandManager(this);
        this.getServer().getPluginManager().registerEvents((Listener)new EventManager(), (Plugin)this);
        PhysicalFighters.log.info(String.format("(!) �⺻���� �ε����Դϴ�.", new Object[0]));
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        Invincibility = getConfig().getBoolean("������ �ʹ� ����");
        DefaultArmed = getConfig().getBoolean("�⺻ ���� ����");
        Respawn = getConfig().getBoolean("���۽� ���������� �̵�");
        AutoKick = getConfig().getBoolean("����� �ڵ����� ű");
        AutoBan = getConfig().getBoolean("����� �ڵ����� ��(ű�� Ȱ��ȭ �Ǿ�� ����)");
        SRankUsed = getConfig().getBoolean("S��ũ �ɷ� ���");
        HalfMonsterDamage = getConfig().getBoolean("������ ���ݷ� �ݰ�");
        AutoDifficultySetting = getConfig().getBoolean("���̵� �ڵ����� Easy�� ����");
        MaxLevelSurvival = getConfig().getBoolean("���� ����");
        Setlev = getConfig().getInt("���� ����");
        EarlyInvincibleTime = getConfig().getInt("�ʹ� ���� �ð�(�� ����)");
        NoFoodMode = getConfig().getBoolean("����� ���� ���(���� �ɷ��� �˾Ƽ� �����)");
        KillerOutput = getConfig().getBoolean("���� ��� ���� ����� ������");
        AutoCoordinateOutput = getConfig().getBoolean("�����ð����� ��ǥ ǥ��");
        NoAnimal = getConfig().getBoolean("���� ��Ȱ��ȭ");
        NoAbilitySetting = getConfig().getBoolean("���۽� �ɷ� ��÷ ����");
        NoClearInventory = getConfig().getBoolean("���۽� �κ��丮 �ʱ�ȭ ����");
        PrintTip = getConfig().getBoolean("������ �� �����");
        AutoSave = getConfig().getBoolean("���� ���� ���̺�");
        InventorySave = getConfig().getBoolean("�κ��丮 ���̺�");
        AbilityOverLap = getConfig().getBoolean("�ɷ� �ߺ� ����");
        InfinityDur = getConfig().getBoolean("������ ����");
        RestrictionTime = getConfig().getInt("�Ϻ� �ɷ� ���� �ð�(�� ����, 0�� ��� ����)");
        Kimiedition = getConfig().getBoolean("���Ѹ��");
        Specialability = getConfig().getBoolean("����ȴɷ¸��");
        TableGive = getConfig().getBoolean("å�� ����");
        WoodGive = getConfig().getBoolean("���� ����");
        Gods = getConfig().getBoolean("�ŵ��Ȱ��ȭ");
        
        log.info(String.format("(!) �ɷ��� �ʱ�ȭ�մϴ�.", new Object[0]));
        AbilityBase.InitAbilityBase(this, this.cm);
        this.A_List = new AbilityList();
        log.info(String.format("(!) ��ũ���͸� �ʱ�ȭ�մϴ�.", new Object[0]));
        this.scripter = new MainScripter(this, this.cm);
        if (PhysicalFighters.Invincibility && PhysicalFighters.EarlyInvincibleTime <= 0) {
            log.info(String.format("(!) �ʹݹ����� 1������ �����˴ϴ�. [E.�ð��� 0�� �����Դϴ�]", new Object[0]));
            PhysicalFighters.EarlyInvincibleTime = 1;
        }
        if (PhysicalFighters.RestrictionTime < 0) {
        	   log.info(String.format("(!) ���� �ð� ���� 0���� Ŀ���մϴ�. 0���� �����˴ϴ�.", new Object[0]));
            PhysicalFighters.RestrictionTime = 0;
        }
        PhysicalFighters.log.info(String.format("(!) �ɷ� %d���� ��ϵ��ֽ��ϴ�.", AbilityList.AbilityList.size() - 1));
        if (PhysicalFighters.Kimiedition) {
            PhysicalFighters.log.info(String.format("(!) ���Ѹ�� ����", new Object[0]));
        }
        if (PhysicalFighters.Specialability) {
            PhysicalFighters.log.info(String.format("(!) ����ȴɷ¸�� ����", new Object[0]));
        }
        if (PhysicalFighters.Gods) {
            PhysicalFighters.log.info(String.format("(!) '��' ��� Ȱ��ȭ!", new Object[0]));
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
        				Bukkit.broadcastMessage(p + "��6���� ��c�ɷ��� ��6���� ī��Ʈ�� ��c3�� ��6���ҽ��ϴ�.");
        				for (Player p : Bukkit.getOnlinePlayers()) {
        					if (MainScripter.ExceptionList.contains(p)) {
        						p.sendMessage(a + "��f����� �ɷ��ڸ� ��c������f���Դϴ�. ��b������f�� ����Ͻø� ��e[ /���� ]��f�� �Է����ּ���.");
        					}
        				}
        			}
        			else if (sTimer == 60) {
        				Bukkit.broadcastMessage(p + "��6���� ��c�ɷ��� ��6���� ī��Ʈ�� ��c1�� ��6���ҽ��ϴ�.");
        				for (Player p : Bukkit.getOnlinePlayers()) {
        					if (MainScripter.ExceptionList.contains(p)) {
        						p.sendMessage(a + "��f����� �ɷ��ڸ� ��c������f���Դϴ�. ��b������f�� ����Ͻø� ��e[ /���� ]��f�� �Է����ּ���.");
        					}
        				}
        			}
        			else if (sTimer == 30) Bukkit.broadcastMessage(p + "��6���� ��c�ɷ��� ��6���� ī��Ʈ�� ��c30�� ��6���ҽ��ϴ�.");
         			else if (sTimer == 20) Bukkit.broadcastMessage(p + "��6���� ��c�ɷ��� ��6���� ī��Ʈ�� ��c20�� ��6���ҽ��ϴ�.");
         			else if (sTimer == 10) Bukkit.broadcastMessage(p + "��6���� ��c�ɷ��� ��6���� ī��Ʈ�� ��c10�� ��6���ҽ��ϴ�.");
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
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + PhysicalFighters.BuildNumber);
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
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
