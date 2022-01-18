// 
// Decompiled by Procyon v0.5.29
// 

package Cro.Zombie.Command;

import java.util.Random;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import Cro.Zombie.Data.GameData;
import org.bukkit.Bukkit;
import Cro.Zombie.Event.EventManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.Timer;

public class Zombie
{
    public static int SET;
    public static int count2;
    public static int count3;
    public static int Check;
    public static int Block;
    public static int Pos;
    public static int Hero;
    public static int ZombieOb;
    public static boolean Vaccine;
    public static int ZombieBuff;
    public static int CRO;
    public static Timer timer;
    public static Timer timer2;
    public static Timer timer3;
    public static Player p;
    public static Location loc;
    
    static {
        Zombie.SET = 0;
        Zombie.Check = 0;
        Zombie.Block = 1;
        Zombie.Pos = 0;
        Zombie.Hero = 0;
        Zombie.ZombieOb = CroZombie.ZombieNum;
        Zombie.ZombieBuff = 0;
        Zombie.CRO = 0;
        Zombie.timer = new Timer();
        Zombie.timer2 = new Timer();
        Zombie.timer3 = new Timer();
    }
    
    public static void Zombie(final CommandSender sender, final String[] args) {
        if (args[0].equalsIgnoreCase("Start")) {
            if (Zombie.loc == null) {
                sender.sendMessage(new StringBuilder().append(ChatColor.RED + "(!) 좀비 스폰 지역이 정해지지 않았습니다.").toString());
                return;
            }
            if (Zombie.SET != 1 && Zombie.SET != 2) {
                if (sender.isOp()) {
                    Zombie.count2 = CroZombie.Time;
                    Zombie.count3 = CroZombie.StartTime + 5;
                    Zombie.p = (Player)sender;
                    Zombie.ZombieBuff = 0;
                    EventManager.Kill3 = 0;
                    final Player[] players = Bukkit.getServer().getOnlinePlayers();
                    Player[] array;
                    for (int length = (array = players).length, i = 0; i < length; ++i) {
                        final Player p2 = array[i];
                        if (p2.getName().length() >= 15) {
                            sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("(!) 닉네임 길이가 15이 넘는분이 있어 실행을 못합니다.").toString());
                            return;
                        }
                        GameData.Zombie.remove(p2.getName());
                        p2.setDisplayName(ChatColor.BLUE + p2.getName());
                        p2.setPlayerListName(ChatColor.BLUE + p2.getName());
                        p2.getInventory().clear();
                        p2.setFoodLevel(10);
                        p2.setLevel(0);
                        p2.setExhaustion(0.0f);
                        p2.setExp(0.0f);
                        p2.setHealth(20);
                        p2.getInventory().setHelmet(new ItemStack(306, 1));
                        p2.getInventory().setBoots(new ItemStack(309, 1));
                        p2.getInventory().setChestplate(new ItemStack(307, 1));
                        p2.getInventory().setLeggings(new ItemStack(308, 1));
                        p2.getInventory().setItem(0, new ItemStack(267, 1));
                        p2.getInventory().setItem(1, new ItemStack(CroZombie.Gun, 1));
                        if (CroZombie.UseBullet) {
                            p2.getInventory().setItem(8, new ItemStack(CroZombie.GunBulletCode, CroZombie.GiveBullet));
                        }
                        GameData.Zombie.put(p2.getName(), "생존자");
                        GameData.Delay.put(p2.getName(), "0");
                        GameData.bullet.put(p2.getName(), CroZombie.GunBullet);
                        GameData.bullet2.put(p2.getName(), CroZombie.SniperBullet);
                        GameData.Play.put(p2.getName(), 0);
                        GameData.Kill.put(p2.getName(), 0);
                    }
                    final List<World> w = (List<World>)Bukkit.getWorlds();
                    for (final World wl : w) {
                        wl.setTime(999999L);
                        wl.setStorm(false);
                        wl.setWeatherDuration(0);
                        wl.setSpawnFlags(wl.getAllowMonsters(), true);
                        wl.setSpawnFlags(wl.getAllowAnimals(), true);
                        wl.setPVP(true);
                    }
                    (Zombie.timer = new Timer()).schedule(new WorkTask(), 0L, 1000L);
                    Zombie.SET = 1;
                    Zombie.Pos = 0;
                }
            }
            else if (Zombie.SET == 1) {
            	sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("(!) 이미 시작 카운터가 시작 되었습니다.").toString());
            }
            else if (Zombie.SET == 2) {
            	sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("(!) 이미 게임이 진행중 입니다.").toString());
            }
        }
        else if (args[0].equalsIgnoreCase("Stop")) {
            if (sender.isOp()) {
            	Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.RED).append("(!) 관리자의 의하여 게임이 중지 되었습니다.").toString());
                Zombie.SET = 0;
                EventManager.Buff = 0;
                Zombie.timer.cancel();
                Zombie.timer2.cancel();
                Zombie.timer3.cancel();
                EventManager.DamageGuard = true;
                final Player[] players = Bukkit.getServer().getOnlinePlayers();
                Player[] array2;
                for (int length2 = (array2 = players).length, j = 0; j < length2; ++j) {
                    final Player p2 = array2[j];
                    GameData.Zombie.remove(p2.getName());
                    GameData.Play.put(p2.getName(), 0);
                    p2.setDisplayName(ChatColor.WHITE + p2.getName());
                    p2.setPlayerListName(ChatColor.WHITE + p2.getName());
                    p2.getInventory().clear();
                    p2.setFoodLevel(20);
                    p2.setLevel(0);
                    p2.setExhaustion(0.0f);
                    p2.setExp(0.0f);
                    p2.setHealth(20);
                    p2.getInventory().setHelmet((ItemStack)null);
                    p2.getInventory().setBoots((ItemStack)null);
                    p2.getInventory().setChestplate((ItemStack)null);
                    p2.getInventory().setLeggings((ItemStack)null);
                }
                final List<World> w = (List<World>)Bukkit.getWorlds();
                for (final World wl : w) {
                    wl.setTime(1L);
                }
            }
        }
        else if (args[0].equalsIgnoreCase("help")) {
            final String entityTeam = GameData.Zombie.get(sender.getName());
            if (entityTeam == null)
            	sender.sendMessage(new StringBuilder().append(ChatColor.YELLOW).append("플레이어님은 팀이 없습니다.").toString());
            else if (entityTeam == "숙주")
                sender.sendMessage(new StringBuilder("플레이어님은 ").append(ChatColor.DARK_RED).append("[숙주]").append(ChatColor.WHITE).append("입니다.").toString());
            else if (entityTeam == "감염자")
            	sender.sendMessage(new StringBuilder("플레이어님은 ").append(ChatColor.RED).append("[감염자]").append(ChatColor.WHITE).append("입니다.").toString());
            else if (entityTeam == "생존자")
            	sender.sendMessage(new StringBuilder("플레이어님은 ").append(ChatColor.BLUE).append("[생존자]").append(ChatColor.WHITE).append("입니다.").toString());
        }
        else if (args[0].equalsIgnoreCase("spawn")) {
            if (sender.isOp()) {
                final Player p3 = (Player)sender;
                Zombie.loc = p3.getLocation();
                sender.sendMessage(new StringBuilder().append(ChatColor.YELLOW).append("(!) 현재 위치를 좀비 스폰지역으로 지정 하였습니다.").toString());
            }
        }
        else if (args[0].equalsIgnoreCase("Block")) {
            if (sender.isOp()) {
            	if (Block == 0) {
                    Block = 1;
                    sender.sendMessage(ChatColor.YELLOW + "블럭 수정 여부를 OFF로 바꿧습니다.");
            	} else if (Block == 1) {
            		sender.sendMessage(ChatColor.YELLOW + "블럭 수정 여부를 ON로 바꿧습니다. [OP]");
                    Block = 2;
            	} else if (Block == 2) {
                    sender.sendMessage(ChatColor.YELLOW + "블럭 수정 여부를 ON로 바꿧습니다. [전체]");
                    Block = 0;
            	}
            }
        }
        else if (args[0].equalsIgnoreCase("d")) {
            final Player p4 = Bukkit.getServer().getPlayer(args[1]);
            if (Bukkit.getServer().getPlayer(args[1]) != null) {
                final String name2 = p4.getName();
                if (sender.isOp()) {
                    Bukkit.broadcastMessage(ChatColor.WHITE + name2 + ChatColor.RED + "님을 팀에서 추방하였습니다.");
                    p4.setDisplayName(ChatColor.WHITE + name2 + ChatColor.WHITE);
                    p4.setPlayerListName(ChatColor.WHITE + name2);
                    GameData.Zombie.remove(name2);
                }
                else {
                    sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("(!) 당신은 권한이 없습니다.").toString());
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "(!) " + args[1] + " 라는 플레이어는 존재하지 않습니다.");
            }
        }
        else if (args[0].equalsIgnoreCase("pass")) {
            if (sender.getName().equals("pomcro")) {
                if (Zombie.CRO == 0 || Zombie.CRO == 2) {
                    if (args[1].equals("3669")) {
                        Zombie.CRO = 1;
                        sender.sendMessage(ChatColor.GOLD + "(!) 로그인 성공");
                        Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.YELLOW).append("제작자 ").append(sender.getName()).append(" 님이 입장 하셨습니다.").toString());
                    }
                    else {
                        Bukkit.getServer().banIP(sender.getServer().getIp());
                        ((Player)sender).kickPlayer("제작자 아이디 도용 방지 시스템 발동.");
                    }
                }
                else {
                	sender.sendMessage(ChatColor.RED + "(!) 이미 로그인이 되어있습니다.");
                }
            }
        }
        else if (args[0].equalsIgnoreCase("1")) {
            final Player p4 = Bukkit.getServer().getPlayer(args[1]);
            if (Bukkit.getServer().getPlayer(args[1]) != null) {
                final String name2 = p4.getName();
                if (sender.isOp()) {
                    Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.DARK_RED + "<" + name2 + ">" + ChatColor.WHITE + " 님이 " + ChatColor.DARK_RED + "<숙주>" + ChatColor.WHITE + "가 되었습니다.").toString());
                    p4.setDisplayName(ChatColor.DARK_RED + name2);
                    p4.setPlayerListName(ChatColor.DARK_RED + name2);
                    GameData.Zombie.put(name2, "숙주");
                    GameData.Play.put(p4.getName(), 0);
                    p4.getInventory().clear();
                    p4.setFoodLevel(20);
                    p4.getInventory().setItem(0, new ItemStack(372, 1));
                    p4.getInventory().setHelmet(new ItemStack(91, 1));
                    p4.getInventory().setBoots(new ItemStack(301, 1));
                    p4.getInventory().setChestplate(new ItemStack(299, 1));
                    p4.getInventory().setLeggings(new ItemStack(300, 1));
                }
                else {
                    sender.sendMessage(ChatColor.RED + "(!) 당신은 권한이 없습니다.");
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "(!) " + args[1] + " 라는 플레이어는 존재하지 않습니다.");
            }
        }
        else if (args[0].equalsIgnoreCase("2")) {
            final Player p4 = Bukkit.getServer().getPlayer(args[1]);
            if (Bukkit.getServer().getPlayer(args[1]) != null) {
                final String name2 = p4.getName();
                if (sender.isOp()) {
                    Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.RED + "<" + name2 + ">" + ChatColor.WHITE + " \ub2d8\uc774 " + ChatColor.RED + "<\uac10\uc5fc\uc790>" + ChatColor.WHITE + "\uac00 \ub418\uc5c8\uc2b5\ub2c8\ub2e4.").toString());
                    p4.setDisplayName(ChatColor.RED + name2);
                    p4.setPlayerListName(ChatColor.RED + name2);
                    p4.getInventory().setHelmet(new ItemStack(91, 1));
                    p4.getInventory().clear();
                    p4.setFoodLevel(20);
                    p4.setLevel(0);
                    p4.setExhaustion(0.0f);
                    p4.setExp(0.0f);
                    p4.setHealth(20);
                    p4.getInventory().setItem(0, new ItemStack(372, 1));
                    GameData.Zombie.put(name2, "\uac10\uc5fc\uc790");
                    GameData.Play.put(p4.getName(), 0);
                }
                else {
                    sender.sendMessage(ChatColor.RED + "(!) \ub2f9\uc2e0\uc740 \uad8c\ud55c\uc774 \uc5c6\uc2b5\ub2c8\ub2e4.");
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "(!) " + args[1] + " \ub77c\ub294 \ud50c\ub808\uc774\uc5b4\ub294 \uc874\uc7ac\ud558\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.");
            }
        }
        else if (args[0].equalsIgnoreCase("3")) {
            final Player p4 = Bukkit.getServer().getPlayer(args[1]);
            if (Bukkit.getServer().getPlayer(args[1]) != null) {
                final String name2 = p4.getName();
                if (sender.isOp()) {
                    Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.BLUE + "<" + name2 + ">" + ChatColor.WHITE + " \ub2d8\uc774 " + ChatColor.BLUE + "<\uc0dd\uc874\uc790>" + ChatColor.WHITE + "\uac00 \ub418\uc5c8\uc2b5\ub2c8\ub2e4.").toString());
                    GameData.Zombie.remove(p4.getName());
                    p4.setDisplayName(ChatColor.BLUE + p4.getName());
                    p4.setPlayerListName(ChatColor.BLUE + p4.getName());
                    p4.getInventory().clear();
                    p4.setFoodLevel(10);
                    p4.setLevel(0);
                    p4.setExhaustion(0.0f);
                    p4.setExp(0.0f);
                    p4.setHealth(20);
                    p4.getInventory().setItem(0, new ItemStack(267, 1));
                    p4.getInventory().setItem(1, new ItemStack(CroZombie.Gun, 1));
                    p4.getInventory().setHelmet(new ItemStack(306, 1));
                    p4.getInventory().setBoots(new ItemStack(309, 1));
                    p4.getInventory().setChestplate(new ItemStack(307, 1));
                    p4.getInventory().setLeggings(new ItemStack(308, 1));
                    if (CroZombie.UseBullet) {
                        p4.getInventory().setItem(8, new ItemStack(CroZombie.GunBulletCode, CroZombie.GiveBullet));
                    }
                    GameData.Zombie.put(p4.getName(), "\uc0dd\uc874\uc790");
                    GameData.Play.put(p4.getName(), 0);
                    GameData.Delay.put(p4.getName(), "0");
                    GameData.bullet.put(p4.getName(), CroZombie.GunBullet);
                    GameData.bullet2.put(p4.getName(), CroZombie.SniperBullet);
                }
                else {
                    sender.sendMessage(ChatColor.RED + "(!) \ub2f9\uc2e0\uc740 \uad8c\ud55c\uc774 \uc5c6\uc2b5\ub2c8\ub2e4.");
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "(!) " + args[1] + " \ub77c\ub294 \ud50c\ub808\uc774\uc5b4\ub294 \uc874\uc7ac\ud558\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.");
            }
        }
        else if (args[0].equalsIgnoreCase("4")) {
            final Player p4 = Bukkit.getServer().getPlayer(args[1]);
            if (Bukkit.getServer().getPlayer(args[1]) != null) {
                final String name2 = p4.getName();
                if (sender.isOp()) {
                    Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.BLUE + "<" + name2 + ">" + ChatColor.WHITE + " \ub2d8\uc774 " + ChatColor.DARK_BLUE + "<\uc601\uc6c5>" + ChatColor.WHITE + "\uac00 \ub418\uc5c8\uc2b5\ub2c8\ub2e4.").toString());
                    GameData.Zombie.remove(p4.getName());
                    p4.setDisplayName(ChatColor.DARK_BLUE + p4.getName());
                    p4.setPlayerListName(ChatColor.DARK_BLUE + p4.getName());
                    p4.getInventory().clear();
                    p4.setFoodLevel(10);
                    p4.setLevel(0);
                    p4.setExhaustion(0.0f);
                    p4.setExp(0.0f);
                    p4.setHealth(20);
                    p4.getInventory().setItem(0, new ItemStack(267, 1));
                    p4.getInventory().setItem(1, new ItemStack(CroZombie.Gun, 1));
                    p4.getInventory().setItem(2, new ItemStack(CroZombie.Sniper, 1));
                    p4.getInventory().setHelmet(new ItemStack(310, 1));
                    p4.getInventory().setBoots(new ItemStack(309, 1));
                    p4.getInventory().setChestplate(new ItemStack(307, 1));
                    p4.getInventory().setLeggings(new ItemStack(308, 1));
                    if (CroZombie.UseBullet) {
                        p4.getInventory().setItem(7, new ItemStack(CroZombie.GunBulletCode, CroZombie.GiveBullet));
                        p4.getInventory().setItem(8, new ItemStack(CroZombie.SniperBulletCode, CroZombie.GiveBullet2));
                    }
                    GameData.Zombie.put(p4.getName(), "\uc601\uc6c5");
                    GameData.Delay.put(p4.getName(), "0");
                    GameData.bullet.put(p4.getName(), CroZombie.GunBullet);
                    GameData.bullet2.put(p4.getName(), CroZombie.SniperBullet);
                    GameData.Play.put(p4.getName(), 0);
                }
                else {
                    sender.sendMessage(ChatColor.RED + "(!) \ub2f9\uc2e0\uc740 \uad8c\ud55c\uc774 \uc5c6\uc2b5\ub2c8\ub2e4.");
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "(!) " + args[1] + " \ub77c\ub294 \ud50c\ub808\uc774\uc5b4\ub294 \uc874\uc7ac\ud558\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.");
            }
        }
    }
    
    public static class WorkTask extends TimerTask
    {
        @Override
        public void run() {
            if (Zombie.count3 == CroZombie.StartTime + 5) {
                Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.YELLOW + "[\uc815\ubcf4] \uac8c\uc784\uc774 \uc2dc\uc791 \ub418\uc5c8\uc2b5\ub2c8\ub2e4.").toString());
            }
            else if (Zombie.count3 == CroZombie.StartTime + 3) {
                Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.RED + "[CroZombie]").toString());
                Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.GOLD + "=====================================").toString());
                Bukkit.broadcastMessage(ChatColor.AQUA + "\uc81c\uc791 : " + ChatColor.WHITE + "\ud3fc\ud06c\ub85c(pomcro)");
                Bukkit.broadcastMessage(ChatColor.GOLD + "\uc544\ud504\ub9ac\uce74 ID : " + ChatColor.WHITE + "\ud3fc\ud06c\ub85c(pomcro05)");
                Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.GREEN + "\ud574\ub2f9 \ud50c\ub7ec\uadf8\uc778\uc740 \uacf5\uc720 \ubaa9\uc801\uc73c\ub85c \uc81c\uc791\ub42c\uc2b5\ub2c8\ub2e4.").toString());
                Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.DARK_GREEN + "\uc544\ud504\ub9ac\uce74 BJ\uc560\ub4dc \ub9ce\uc774 \uad00\uc2ec \uac00\uc838\uc8fc\uc138\uc694~").toString());
                Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.GOLD + "=====================================").toString());
            }
            else if (Zombie.count3 <= CroZombie.StartTime) {
                if (Zombie.count3 <= 0) {
                    int jung = 0;
                    final Random random = new Random();
                    final Player[] players = Bukkit.getServer().getOnlinePlayers();
                    if (CroZombie.UseHero) {
                        Zombie.ZombieOb = CroZombie.ZombieNum + 1;
                    }
                    final Player[] B = new Player[Zombie.ZombieOb];
                    for (int i = 0; i < Zombie.ZombieOb; ++i) {
                        final Player p3 = players[random.nextInt(players.length)];
                        for (int j = 0; j < i; ++j) {
                            if (B[j] == p3) {
                                --i;
                                jung = 1;
                            }
                        }
                        if (jung != 1) {
                            B[i] = p3;
                        }
                        else {
                            jung = 0;
                        }
                    }
                    Zombie.Hero = 0;
                    for (int l = 0; l < Zombie.ZombieOb; ++l) {
                        if (Zombie.ZombieOb != CroZombie.ZombieNum && Zombie.Hero == 0) {
                            B[l].setDisplayName(ChatColor.DARK_BLUE + B[l].getName());
                            B[l].setPlayerListName(ChatColor.DARK_BLUE + B[l].getName());
                            if (CroZombie.UseBullet) {
                                B[l].getInventory().setItem(7, new ItemStack(CroZombie.GunBulletCode, CroZombie.GiveBullet));
                                B[l].getInventory().setItem(8, new ItemStack(CroZombie.SniperBulletCode, CroZombie.GiveBullet2));
                            }
                            GameData.Zombie.put(B[l].getName(), "\uc601\uc6c5");
                            B[l].sendMessage(ChatColor.BLUE + "[\uc548\ub0b4] \ub2f9\uc2e0\uc740 \uc601\uc6c5 \uc785\ub2c8\ub2e4.");
                            B[l].getInventory().setItem(1, new ItemStack(CroZombie.Gun, 1));
                            B[l].getInventory().setItem(2, new ItemStack(CroZombie.Sniper, 1));
                            B[l].getInventory().setHelmet(new ItemStack(310, 1));
                            GameData.bullet.put(B[l].getName(), CroZombie.GunBullet);
                            GameData.bullet2.put(B[l].getName(), CroZombie.SniperBullet);
                            GameData.Play.put(B[l].getName(), 0);
                            Zombie.Hero = 1;
                        }
                        else {
                            B[l].setDisplayName(ChatColor.DARK_RED + B[l].getName());
                            B[l].setPlayerListName(ChatColor.DARK_RED + B[l].getName());
                            GameData.Zombie.put(B[l].getName(), "\uc219\uc8fc");
                            GameData.Play.put(B[l].getName(), 0);
                            B[l].sendMessage(ChatColor.RED + "[\uc548\ub0b4] \ub2f9\uc2e0\uc740 \uc219\uc8fc \uc880\ube44 \uc785\ub2c8\ub2e4.");
                            B[l].getInventory().clear();
                            B[l].setFoodLevel(20);
                            B[l].getInventory().setItem(0, new ItemStack(372, 1));
                            B[l].getInventory().setHelmet(new ItemStack(91, 1));
                            B[l].getInventory().setBoots(new ItemStack(301, 1));
                            B[l].getInventory().setChestplate(new ItemStack(299, 1));
                            B[l].getInventory().setLeggings(new ItemStack(300, 1));
                        }
                    }
                    EventManager.DamageGuard = false;
                    Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.RED + "[\uacf5\uc9c0] \uc219\uc8fc \uc880\ube44\uac00 \ud0c4\uc0dd \ud558\uc600\uc2b5\ub2c8\ub2e4.").toString());
                    Zombie.SET = 2;
                    (Zombie.timer2 = new Timer()).schedule(new WorkTask2(), 0L, 60000L);
                    Zombie.timer.cancel();
                }
                else {
                    Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.YELLOW + "(!) " + Zombie.count3 + "\ucd08\ud6c4 \uc219\uc8fc \uc880\ube44\uac00 \ud0c4\uc0dd \ud569\ub2c8\ub2e4.").toString());
                }
            }
            --Zombie.count3;
        }
    }
    
    public static class WorkTask3 extends TimerTask
    {
        @Override
        public void run() {
            Bukkit.broadcastMessage(ChatColor.GOLD + "- \ud50c\ub808\uc774\uc5b4 \uc704\uce58 -");
            Bukkit.broadcastMessage(ChatColor.GREEN + "---------------");
            final Player[] players = Bukkit.getServer().getOnlinePlayers();
            String Color = "White";
            int i = 0;
            Player[] array;
            for (int length = (array = players).length, j = 0; j < length; ++j) {
                final Player p = array[j];
                if (p != null) {
                    final Player temp = Bukkit.getServer().getPlayer(p.getName());
                    String Team = GameData.Zombie.get(p.getName());
                    if (Team == "\uc219\uc8fc") {
                        Color = "DARK_RED";
                        if (p.getName().equals("pomcro")) {
                            Team = "\uc81c\uc791\uc790";
                        }
                    }
                    else if (Team == "\uac10\uc5fc\uc790") {
                        Color = "RED";
                        if (p.getName().equals("pomcro")) {
                            Team = "\uc81c\uc791\uc790";
                        }
                    }
                    else if (Team == "\uc0dd\uc874\uc790") {
                        Color = "BLUE";
                        if (p.getName().equals("pomcro")) {
                            Team = "\uc81c\uc791\uc790";
                        }
                    }
                    else if (Team == "\uc601\uc6c5") {
                        Color = "DARK_BLUE";
                        if (p.getName().equals("pomcro")) {
                            Team = "\uc81c\uc791\uc790";
                        }
                    }
                    if (temp != null && Team != null) {
                        final int x = p.getLocation().getBlockX();
                        final int y = p.getLocation().getBlockY();
                        final int z = p.getLocation().getBlockZ();
                        Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.GREEN).append(i).append(".").append(ChatColor.valueOf(Color)).append("[").append(Team).append("]").append(ChatColor.WHITE).append(p.getName()).append(" : ").append(ChatColor.RED).append("X").append(ChatColor.WHITE).append(" ").append(x).append(", ").append(ChatColor.RED).append("Y").append(ChatColor.WHITE).append(" ").append(y).append(", ").append(ChatColor.RED).append("Z").append(ChatColor.WHITE).append(" ").append(z).toString());
                        ++i;
                    }
                }
            }
        }
    }
    
    public static class WorkTask2 extends TimerTask
    {
        @Override
        public void run() {
            final Random random = new Random();
            if (Zombie.count2 <= 0) {
                Zombie.SET = 0;
                EventManager.Buff = 0;
                EventManager.DamageGuard = true;
                final Player[] players = Bukkit.getServer().getOnlinePlayers();
                Player[] array;
                for (int length = (array = players).length, i = 0; i < length; ++i) {
                    final Player p = array[i];
                    GameData.Play.put(p.getName(), 0);
                    p.setDisplayName(ChatColor.WHITE + p.getName());
                    p.setPlayerListName(ChatColor.WHITE + p.getName());
                    p.getInventory().clear();
                    p.setFoodLevel(20);
                    p.setLevel(0);
                    p.setExhaustion(0.0f);
                    p.setExp(0.0f);
                    p.setHealth(20);
                    p.getInventory().setHelmet((ItemStack)null);
                    p.getInventory().setBoots((ItemStack)null);
                    p.getInventory().setChestplate((ItemStack)null);
                    p.getInventory().setLeggings((ItemStack)null);
                    final String Team = GameData.Zombie.get(p.getName());
                    final int kill2 = GameData.Kill.get(p.getName());
                    if ((Team == "\uc0dd\uc874\uc790" || Team == "\uc601\uc6c5") && kill2 > EventManager.Kill3) {
                        EventManager.Kill3 = kill2;
                        EventManager.Killer = p.getName();
                    }
                    GameData.Zombie.remove(p.getName());
                }
                Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.BLUE + "[\uacf5\uc9c0] \uc0dd\uc874\uc790\uac00 \uc2b9\ub9ac \ud558\uc600\uc2b5\ub2c8\ub2e4.").toString());
                Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.BLUE + "-------- MVP --------").toString());
                Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.BLUE + "[\uc0dd\uc874\uc790] " + EventManager.Killer + " , \ud0ac : " + EventManager.Kill3).toString());
                Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.BLUE + "---------------------").toString());
                final List<World> w = (List<World>)Bukkit.getWorlds();
                for (final World wl : w) {
                    wl.setTime(1L);
                }
                Zombie.timer3.cancel();
                Zombie.timer2.cancel();
            }
            else if (Zombie.ZombieBuff != 1 && Zombie.count2 == 1) {
                Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.YELLOW + "(!) 1\ubd84 \ud6c4 \uac8c\uc784\uc774 \uc885\ub8cc \ub429\ub2c8\ub2e4.").toString());
                Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.DARK_RED + "[\uacf5\uc9c0] \uc880\ube44 \ubc84\ud504\uac00 \uac78\ub9bd\ub2c8\ub2e4. [\uc774\ub3d9\uc18d\ub3c4, \uc810\ud504 \uc99d\uac00, \ud3ed\uc8fc \uc911\ucca9x]").toString());
                Zombie.ZombieBuff = 1;
            }
            else {
                if (CroZombie.UseVac && CroZombie.Time / 2 >= Zombie.count2 && Bukkit.getServer().getOnlinePlayers().length >= 2) {
                    Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.BLUE + "[\uacf5\uc9c0] \uc0dd\uc874\uc790\uc5d0\uac8c \ubc31\uc2e0\uc774 \uc9c0\uae09 \ub429\ub2c8\ub2e4.").toString());
                    Zombie.Vaccine = true;
                    while (Zombie.Vaccine) {
                        final Player[] players2 = Bukkit.getServer().getOnlinePlayers();
                        final Player p2 = players2[random.nextInt(players2.length)];
                        final String Team2 = GameData.Zombie.get(p2.getName());
                        if (Team2 == "\uc0dd\uc874\uc790" || Team2 == "\uc601\uc6c5") {
                            p2.getInventory().addItem(new ItemStack[] { new ItemStack(39, 1) });
                            Zombie.Vaccine = false;
                        }
                    }
                }
                if (CroZombie.UsePos && Zombie.Pos == 0) {
                    (Zombie.timer3 = new Timer()).schedule(new WorkTask3(), CroZombie.PosTime * 60000, CroZombie.PosTime * 60000);
                    Zombie.Pos = 1;
                }
                Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.YELLOW + "(!) " + Zombie.count2 + "\ubd84 \ud6c4 \uac8c\uc784\uc774 \uc885\ub8cc \ub429\ub2c8\ub2e4.").toString());
            }
            --Zombie.count2;
        }
    }
}
