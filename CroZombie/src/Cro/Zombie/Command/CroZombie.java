// 
// Decompiled by Procyon v0.5.29
// 

package Cro.Zombie.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.Iterator;
import java.util.List;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import Cro.Zombie.Event.EventManager;
import org.bukkit.command.CommandExecutor;
import Cro.Zombie.Moudule.Util;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.Bukkit;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class CroZombie extends JavaPlugin
{
    public static int ZombieNum;
    public static int StartTime;
    public static int Time;
    public static boolean UseVac;
    public static boolean UseHero;
    public static boolean UseBullet;
    public static boolean UseHealth;
    public static boolean UseKiller;
    public static boolean GiveItem;
    public static int ItemCode;
    public static int ItemCodeNum;
    public static boolean GiveItem2;
    public static int ItemCode2;
    public static int ItemCodeNum2;
    public static int GiveBullet;
    public static int GiveBullet2;
    public static int GunBullet;
    public static int GunBulletCode;
    public static int Gun;
    public static int GunDelay;
    public static int GunShotDelay;
    public static int GunShotDelay2;
    public static int SniperBullet;
    public static int SniperBulletCode;
    public static int Sniper;
    public static int SniperDelay;
    public static int SniperShotDelay;
    public static boolean UsePos;
    public static int PosTime;
    public static int UserGunDamage;
    public static int UserSniperDamage;
    public static int HeroGunDamage;
    public static int HeroSniperDamage;
    public static Logger log;
    
    static {
        CroZombie.ZombieNum = 10;
        CroZombie.StartTime = 10;
        CroZombie.Time = 10;
        CroZombie.UseVac = false;
        CroZombie.UseHero = true;
        CroZombie.UseBullet = false;
        CroZombie.UseHealth = true;
        CroZombie.UseKiller = true;
        CroZombie.GiveItem = false;
        CroZombie.GiveItem2 = false;
        CroZombie.UserGunDamage = 1;
        CroZombie.UserSniperDamage = 6;
        CroZombie.HeroGunDamage = 2;
        CroZombie.HeroSniperDamage = 4;
        CroZombie.log = Logger.getLogger("MineCraft");
    }
    
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        ZombieNum = getConfig().getInt("����(��)");
        StartTime = getConfig().getInt("���� ���ð�(��)");
        Time = getConfig().getInt("���� �÷��� �ð�(��)");
        UseVac = getConfig().getBoolean("��� ���");
        UseHero = getConfig().getBoolean("���� ���");
        UseKiller = getConfig().getBoolean("���λ�� ���");
        UseHealth = getConfig().getBoolean("���� ü�� ǥ��");
        UseBullet = getConfig().getBoolean("�Ѿ� ���� ���");
        GiveItem = getConfig().getBoolean("������ ���� ų�� ������ ���� ���");
        ItemCode = getConfig().getInt("������ �ڵ�");
        ItemCodeNum = getConfig().getInt("���� ����");
        GiveItem2 = getConfig().getBoolean("���� ������ ų�� ������ ���� ���");
        ItemCode2 = getConfig().getInt("������ �ڵ�2");
        ItemCodeNum2 = getConfig().getInt("���� ����2");
        GiveBullet = getConfig().getInt("���� źâ ����(��)");
        GiveBullet2 = getConfig().getInt("�������� źâ ����(��)");
        GunBullet = getConfig().getInt("���� �Ѿ�(��)");
        SniperBullet = getConfig().getInt("�������� �Ѿ�(��)");
        Gun = getConfig().getInt("���� �ڵ�");
        GunDelay = getConfig().getInt("���� ���� ������");
        GunShotDelay = getConfig().getInt("���� �߻� ������");
        GunShotDelay2 = getConfig().getInt("���� �߻� ������2");
        GunBulletCode = getConfig().getInt("���� źâ �ڵ�");
        Sniper = getConfig().getInt("�������� �ڵ�");
        SniperDelay = getConfig().getInt("�������� ���� ������");
        SniperShotDelay = getConfig().getInt("�������� �߻� ������");
        SniperBulletCode = getConfig().getInt("�������� źâ �ڵ�");
        UsePos = getConfig().getBoolean("��ǥ ��� ���");
        PosTime = getConfig().getInt("��ǥ ��� �ð�(��)");
        UserGunDamage = getConfig().getInt("������ ���� ������");
        UserSniperDamage = getConfig().getInt("������ �������� ������");
        HeroGunDamage = getConfig().getInt("���� ���� ������");
        HeroSniperDamage = getConfig().getInt("���� �������� ������");
        final List<World> w = (List<World>)Bukkit.getWorlds();
        for (final World wl : w) {
            wl.setDifficulty(Difficulty.PEACEFUL);
            wl.setSpawnFlags(wl.getAllowMonsters(), false);
            wl.setSpawnFlags(wl.getAllowAnimals(), true);
        }
        Util.Initialize(this);
        this.getCommand("Zombie").setExecutor((CommandExecutor)this);
        this.getServer().getPluginManager().registerEvents((Listener)new EventManager(), (Plugin)this);
    }
    
    public void onDisable() {
    	log.info("[CroZombie] CroZombie �÷����� ���Ҽ�ȭ");
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String Label, final String[] args) {
    	if (args.length == 0) {
			sender.sendMessage(" ��e----- ��6���� �����̹� ��e--- ��6������ ��c1��6/��c1 ��e-----");
    		if (sender.isOp()) {
    			sender.sendMessage(new StringBuilder("��6/���� start ��f-").append(" �����̹��� ���� �մϴ�.").toString());
    			sender.sendMessage(new StringBuilder("��6/���� stop ��f-").append(" �����̹��� �߽� ��ŵ�ϴ�.").toString());
    			sender.sendMessage(new StringBuilder("��6/���� spawn ��f-").append(" ������ ���� �������� ���� �մϴ�.").toString());
    			sender.sendMessage(new StringBuilder("��6/���� block ��f-").append(" �� �������θ� ���� �մϴ�.").toString());
    			sender.sendMessage(new StringBuilder("��6/���� d <�г���> ��f-").append(" <����>�� ������ ���� ��ŵ�ϴ�.").toString());
    			sender.sendMessage(new StringBuilder("��6/���� [1~3] <�г���> ��f- <����> �� ���� �־� �ݴϴ�. ").toString());
    	    	sender.sendMessage("��b[1] ���� [2] ������ [3] ������ [4] ����");
    		}
    		sender.sendMessage(new StringBuilder("��6/���� help ��f-").append("�÷��̾��� ���� �˷��ݴϴ�.").toString());
        }
        else if (command.getName().equalsIgnoreCase("����")) {
            Zombie.Zombie(sender, args);
        }
        return true;
    }
}
