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
        ZombieNum = getConfig().getInt("숙주(명)");
        StartTime = getConfig().getInt("시작 대기시간(초)");
        Time = getConfig().getInt("게임 플레이 시간(분)");
        UseVac = getConfig().getBoolean("백신 사용");
        UseHero = getConfig().getBoolean("영웅 사용");
        UseKiller = getConfig().getBoolean("죽인사람 출력");
        UseHealth = getConfig().getBoolean("남은 체력 표시");
        UseBullet = getConfig().getBoolean("총알 장전 사용");
        GiveItem = getConfig().getBoolean("생존자 좀비 킬시 아이템 지급 사용");
        ItemCode = getConfig().getInt("아이템 코드");
        ItemCodeNum = getConfig().getInt("지급 개수");
        GiveItem2 = getConfig().getBoolean("좀비 생존자 킬시 아이템 지급 사용");
        ItemCode2 = getConfig().getInt("아이템 코드2");
        ItemCodeNum2 = getConfig().getInt("지급 개수2");
        GiveBullet = getConfig().getInt("권총 탄창 지급(개)");
        GiveBullet2 = getConfig().getInt("스나이퍼 탄창 지급(개)");
        GunBullet = getConfig().getInt("권총 총알(발)");
        SniperBullet = getConfig().getInt("스나이퍼 총알(발)");
        Gun = getConfig().getInt("권총 코드");
        GunDelay = getConfig().getInt("권총 장전 딜레이");
        GunShotDelay = getConfig().getInt("권총 발사 딜레이");
        GunShotDelay2 = getConfig().getInt("권총 발사 딜레이2");
        GunBulletCode = getConfig().getInt("권총 탄창 코드");
        Sniper = getConfig().getInt("스나이퍼 코드");
        SniperDelay = getConfig().getInt("스나이퍼 장전 딜레이");
        SniperShotDelay = getConfig().getInt("스나이퍼 발사 딜레이");
        SniperBulletCode = getConfig().getInt("스나이퍼 탄창 코드");
        UsePos = getConfig().getBoolean("좌표 출력 사용");
        PosTime = getConfig().getInt("좌표 출력 시간(분)");
        UserGunDamage = getConfig().getInt("생존자 권총 데미지");
        UserSniperDamage = getConfig().getInt("생존자 스나이퍼 데미지");
        HeroGunDamage = getConfig().getInt("영웅 권총 데미지");
        HeroSniperDamage = getConfig().getInt("영웅 스나이퍼 데미지");
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
    	log.info("[CroZombie] CroZombie 플러그인 비할성화");
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String Label, final String[] args) {
    	if (args.length == 0) {
			sender.sendMessage(" §e----- §6좀비 서바이벌 §e--- §6페이지 §c1§6/§c1 §e-----");
    		if (sender.isOp()) {
    			sender.sendMessage(new StringBuilder("§6/좀비 start §f-").append(" 서바이벌을 시작 합니다.").toString());
    			sender.sendMessage(new StringBuilder("§6/좀비 stop §f-").append(" 서바이벌을 중시 시킵니다.").toString());
    			sender.sendMessage(new StringBuilder("§6/좀비 spawn §f-").append(" 좀비의 스폰 지역으로 설정 합니다.").toString());
    			sender.sendMessage(new StringBuilder("§6/좀비 block §f-").append(" 블럭 수정여부를 설정 합니다.").toString());
    			sender.sendMessage(new StringBuilder("§6/좀비 d <닉네임> §f-").append(" <유저>를 팀에서 제외 시킵니다.").toString());
    			sender.sendMessage(new StringBuilder("§6/좀비 [1~3] <닉네임> §f- <유저> 를 팀에 넣어 줍니다. ").toString());
    	    	sender.sendMessage("§b[1] 숙주 [2] 감염자 [3] 생존자 [4] 영웅");
    		}
    		sender.sendMessage(new StringBuilder("§6/좀비 help §f-").append("플레이어의 팀을 알려줍니다.").toString());
        }
        else if (command.getName().equalsIgnoreCase("좀비")) {
            Zombie.Zombie(sender, args);
        }
        return true;
    }
}
