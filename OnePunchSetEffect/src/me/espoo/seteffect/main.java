package me.espoo.seteffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.espoo.loon.PlayerYml;
import me.espoo.rpg.exp.ExpAPI;

public class main  extends JavaPlugin implements Listener {
    static final Map <String, Integer> Timer = new HashMap<>();
    public static HashMap<String, Integer> Sn = new HashMap<String, Integer>();
    public static ArrayList<Player> flySpem = new ArrayList<Player>();
    static final Map <String, Integer> flyCool = new HashMap<>();
	public static HashMap<String, Integer> flyTime = new HashMap<String, Integer>();
    public static ArrayList<Player> fly2Spem = new ArrayList<Player>();
    static final Map <String, Integer> fly2Cool = new HashMap<>();
	public static HashMap<String, Integer> fly2Time = new HashMap<String, Integer>();
	public static ArrayList<Player> DongJaInfo = new ArrayList<Player>();
	public static ArrayList<Player> DongJaPos = new ArrayList<Player>();
	public static ArrayList<Player> DongJaState = new ArrayList<Player>();
	public static ArrayList<String> Waepon = new ArrayList<String>();
    
	public void onEnable()
	{   
		 Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			 public void run() {
				 for (Player p : Bukkit.getOnlinePlayers()) {
					 KingTimer(p);
					 KnightTimer(p);
					 FreezeTimer(p);
					 MaskTimer(p);
					 SonicTimer(p);
					 RegLoonTimer(p);
					 SpeedLoonTimer(p);
					 HedenLoonTimer(p);
					 SocketTimer(p);
				 }
			 }
		 }, 60L, 60L);
		 
		Waepon.add("타츠마키");
		Waepon.add("레이저");
		Waepon.add("금속");
		Waepon.add("숟가락");
		Waepon.add("메탈나이트");
		Waepon.add("번견맨의");
		Waepon.add("섬광의");
		Waepon.add("유수암쇄권");
		Waepon.add("꽃미남");
		Waepon.add("아토믹");
		Waepon.add("음속의");
		Waepon.add("제노스의");
		Waepon.add("좀비맨");
		Waepon.add("검은빛의");
		Waepon.add("킹의");
		Waepon.add("탱크톱");
		Waepon.add("엔젤");
		Waepon.add("후부키");
		Waepon.add("동제");
	    
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
	public static void SocketTimer(Player p)
	{
		int num = 0;
		int num2 = 0;
		
		if (me.espoo.socket.PlayerYml.getInfoInt(p, "1.아이템 코드") != 0 && me.espoo.socket.PlayerYml.getInfoString(p, "1.아이템 이름") != null && !me.espoo.socket.PlayerYml.getInfoList(p, "1.아이템 설명").equals(Arrays.asList())) {
			String str = me.espoo.socket.API.getLoreNum(me.espoo.socket.PlayerYml.getInfoList(p, "1.아이템 설명"));
			if (str.equalsIgnoreCase("경험치 보너스")) {
				num += me.espoo.socket.API.getExpBouns(me.espoo.socket.PlayerYml.getInfoList(p, "1.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("히어로 점수 추가")) {
				num2 += me.espoo.socket.API.getHeroScore(me.espoo.socket.PlayerYml.getInfoList(p, "1.아이템 설명"));
			}
		}
		
		if (me.espoo.socket.PlayerYml.getInfoInt(p, "2.아이템 코드") != 0 && me.espoo.socket.PlayerYml.getInfoString(p, "2.아이템 이름") != null && !me.espoo.socket.PlayerYml.getInfoList(p, "2.아이템 설명").equals(Arrays.asList())) {
			String str = me.espoo.socket.API.getLoreNum(me.espoo.socket.PlayerYml.getInfoList(p, "2.아이템 설명"));
			if (str.equalsIgnoreCase("경험치 보너스")) {
				num += me.espoo.socket.API.getExpBouns(me.espoo.socket.PlayerYml.getInfoList(p, "2.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("히어로 점수 추가")) {
				num2 += me.espoo.socket.API.getHeroScore(me.espoo.socket.PlayerYml.getInfoList(p, "2.아이템 설명"));
			}
		}
		
		if (me.espoo.socket.PlayerYml.getInfoInt(p, "3.아이템 코드") != 0 && me.espoo.socket.PlayerYml.getInfoString(p, "3.아이템 이름") != null && !me.espoo.socket.PlayerYml.getInfoList(p, "3.아이템 설명").equals(Arrays.asList())) {
			String str = me.espoo.socket.API.getLoreNum(me.espoo.socket.PlayerYml.getInfoList(p, "3.아이템 설명"));
			if (str.equalsIgnoreCase("경험치 보너스")) {
				num += me.espoo.socket.API.getExpBouns(me.espoo.socket.PlayerYml.getInfoList(p, "3.아이템 설명"));
			}

			else if (str.equalsIgnoreCase("히어로 점수 추가")) {
				num2 += me.espoo.socket.API.getHeroScore(me.espoo.socket.PlayerYml.getInfoList(p, "3.아이템 설명"));
			}
		}
		
		if (num != 0) {
			ExpAPI.setBounsAll("소켓", p.getName(), num + 100, -1);
		} else {
			ExpAPI.removeBouns("소켓", p.getName());
		}
		
		if (num2 != 0) {
			me.espoo.score.Method.setPlayerBouns(p, num2 + 100);
		} else {
			me.espoo.score.Method.setPlayerBouns(p, 100);
		}
	}
	
	public static void HedenLoonTimer(Player p)
	{
		if (PlayerYml.getInfoInt(p, "룬.아이템 코드") != 0 && PlayerYml.getInfoString(p, "룬.아이템 이름") != null && !PlayerYml.getInfoList(p, "룬.아이템 설명").equals(Arrays.asList())) {
			String[] str = ChatColor.stripColor(PlayerYml.getInfoString(p, "룬.아이템 이름")).split(" ");
			if (str[1].equalsIgnoreCase("히든")) {
				if (str.length == 5) {
					int i = Integer.parseInt(str[4].replace("+", ""));
						switch (i) {
						case 1:
							ExpAPI.setBounsAll("히든", p.getName(), 150, -1);
							break;
							
						case 2:
							ExpAPI.setBounsAll("히든", p.getName(), 200, -1);
							break;
							
						case 3:
							ExpAPI.setBounsAll("히든", p.getName(), 200, -1);
							break;
							
						case 4:
							ExpAPI.setBounsAll("히든", p.getName(), 250, -1);
							break;
							
						case 5:
							ExpAPI.setBounsAll("히든", p.getName(), 250, -1);
							break;
							
						case 6:
							ExpAPI.setBounsAll("히든", p.getName(), 250, -1);
							break;
							
						case 7:
							ExpAPI.setBounsAll("히든", p.getName(), 300, -1);
							break;
							
						case 8:
							ExpAPI.setBounsAll("히든", p.getName(), 300, -1);
							break;
							
						case 9:
							ExpAPI.setBounsAll("히든", p.getName(), 300, -1);
							break;
							
						case 10:
							ExpAPI.setBounsAll("히든", p.getName(), 350, -1);
							break;
					}
				} else {
					ExpAPI.setBounsAll("히든", p.getName(), 150, -1);
				}
			} else {
				ExpAPI.removeBouns("히든", p.getName());
			}
		} else {
			ExpAPI.removeBouns("히든", p.getName());
		}
	}
	
	public static void RegLoonTimer(Player p)
	{
		if (Method.isHelmetItem(p, " 아마이마스크 모자") && Method.isChestplateItem(p, " 아마이마스크 튜닉")
		 && Method.isLeggingsItem(p, " 아마이마스크 바지") && Method.isBootsItem(p, " 아마이마스크 신발")) {
			return;
		} else {
			if (PlayerYml.getInfoInt(p, "룬.아이템 코드") != 0 && PlayerYml.getInfoString(p, "룬.아이템 이름") != null && !PlayerYml.getInfoList(p, "룬.아이템 설명").equals(Arrays.asList())) {
				String[] str = ChatColor.stripColor(PlayerYml.getInfoString(p, "룬.아이템 이름")).split(" ");
				if (str[1].equalsIgnoreCase("치유")) {
					if (str.length == 5) {
						int i = Integer.parseInt(str[4].replace("+", ""));
						
						switch (i) {
						case 1:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 0, true));
							break;
							
						case 2:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 0, true));
							break;
							
						case 3:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 1, true));
							break;
							
						case 4:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 1, true));
							break;
							
						case 5:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 1, true));
							break;
							
						case 6:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2, true));
							break;
							
						case 7:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2, true));
							break;
							
						case 8:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 3, true));
							break;
							
						case 9:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 5, true));
							break;
							
						case 10:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 6, true));
							break;
						}
					} else {
						p.removePotionEffect(PotionEffectType.REGENERATION);
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 0, true));
					}
				}
			}
		}
	}
	
	public static void SpeedLoonTimer(Player p)
	{
		if (Method.isHelmetItem(p, " 구동기사 투구") && Method.isChestplateItem(p, " 구동기사 갑옷")
		 && Method.isLeggingsItem(p, " 구동기사 레깅스") && Method.isBootsItem(p, " 구동기사 부츠") ||
		 	Method.isHelmetItem(p, " 음속의 소닉 모자") && Method.isChestplateItem(p, " 음속의 소닉 튜닉")
		 && Method.isLeggingsItem(p, " 음속의 소닉 바지") && Method.isBootsItem(p, " 음속의 소닉 신발")) {
			return;
		} else {
			if (PlayerYml.getInfoInt(p, "룬.아이템 코드") != 0 && PlayerYml.getInfoString(p, "룬.아이템 이름") != null && !PlayerYml.getInfoList(p, "룬.아이템 설명").equals(Arrays.asList())) {
				String[] str = ChatColor.stripColor(PlayerYml.getInfoString(p, "룬.아이템 이름")).split(" ");
				if (str[1].equalsIgnoreCase("바람")) {
					if (str.length == 5) {
						int i = Integer.parseInt(str[4].replace("+", ""));
						
						switch (i) {
						case 1:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 0, true));
							break;
							
						case 2:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 0, true));
							break;
							
						case 3:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 1, true));
							break;
							
						case 4:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 1, true));
							break;
							
						case 5:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 2, true));
							break;
							
						case 6:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 3, true));
							break;
							
						case 7:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 3, true));
							break;
							
						case 8:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 4, true));
							break;
							
						case 9:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 6, true));
							break;
							
						case 10:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 7, true));
							break;
						}
					} else {
						p.removePotionEffect(PotionEffectType.SPEED);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 0, true));
					}
				}
			}
		}
	}
	
	public static void KnightTimer(Player p)
	{
		if (Method.isHelmetItem(p, " 구동기사 투구") && Method.isChestplateItem(p, " 구동기사 갑옷")
		 && Method.isLeggingsItem(p, " 구동기사 레깅스") && Method.isBootsItem(p, " 구동기사 부츠")) {
			if (PlayerYml.getInfoInt(p, "룬.아이템 코드") != 0 && PlayerYml.getInfoString(p, "룬.아이템 이름") != null && !PlayerYml.getInfoList(p, "룬.아이템 설명").equals(Arrays.asList())) {
				String[] str = ChatColor.stripColor(PlayerYml.getInfoString(p, "룬.아이템 이름")).split(" ");
				if (str[1].equalsIgnoreCase("바람")) {
					if (str.length == 5) {
						int i = Integer.parseInt(str[4].replace("+", ""));
						
						switch (i) {
						case 1:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 2, true));
							break;
							
						case 2:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 2, true));
							break;
							
						case 3:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 3, true));
							break;
							
						case 4:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 3, true));
							break;
							
						case 5:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 4, true));
							break;
							
						case 6:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 5, true));
							break;
							
						case 7:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 5, true));
							break;
							
						case 8:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 6, true));
							break;
							
						case 9:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 8, true));
							break;
							
						case 10:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 9, true));
							break;
						}
					} else {
						p.removePotionEffect(PotionEffectType.SPEED);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 2, true));
					}
				} else {
					p.removePotionEffect(PotionEffectType.SPEED);
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 1, true));
				}
			} else {
				p.removePotionEffect(PotionEffectType.SPEED);
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 1, true));
			}
		}
	}
	
	public static void SonicTimer(Player p)
	{
		if (Method.isHelmetItem(p, " 음속의 소닉 모자") && Method.isChestplateItem(p, " 음속의 소닉 튜닉")
		 && Method.isLeggingsItem(p, " 음속의 소닉 바지") && Method.isBootsItem(p, " 음속의 소닉 신발")) {
			if (PlayerYml.getInfoInt(p, "룬.아이템 코드") != 0 && PlayerYml.getInfoString(p, "룬.아이템 이름") != null && !PlayerYml.getInfoList(p, "룬.아이템 설명").equals(Arrays.asList())) {
				String[] str = ChatColor.stripColor(PlayerYml.getInfoString(p, "룬.아이템 이름")).split(" ");
				if (str[1].equalsIgnoreCase("바람")) {
					if (str.length == 5) {
						int i = Integer.parseInt(str[4].replace("+", ""));
						
						switch (i) {
						case 1:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 4, true));
							break;
							
						case 2:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 4, true));
							break;
							
						case 3:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 5, true));
							break;
							
						case 4:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 5, true));
							break;
							
						case 5:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 6, true));
							break;
							
						case 6:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 7, true));
							break;
							
						case 7:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 7, true));
							break;
							
						case 8:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 8, true));
							break;
							
						case 9:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 10, true));
							break;
							
						case 10:
							p.removePotionEffect(PotionEffectType.SPEED);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 11, true));
							break;
						}
					} else {
						p.removePotionEffect(PotionEffectType.SPEED);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 4, true));
					}
				} else {
					p.removePotionEffect(PotionEffectType.SPEED);
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 3, true));
				}
			} else {
				p.removePotionEffect(PotionEffectType.SPEED);
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 3, true));
			}
		}
	}
	
	public static void MaskTimer(Player p)
	{
		if (Method.isHelmetItem(p, " 아마이마스크 모자") && Method.isChestplateItem(p, " 아마이마스크 튜닉")
	 	 && Method.isLeggingsItem(p, " 아마이마스크 바지") && Method.isBootsItem(p, " 아마이마스크 신발")) {
			p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 1, true));
			
			if (PlayerYml.getInfoInt(p, "룬.아이템 코드") != 0 && PlayerYml.getInfoString(p, "룬.아이템 이름") != null && !PlayerYml.getInfoList(p, "룬.아이템 설명").equals(Arrays.asList())) {
				String[] str = ChatColor.stripColor(PlayerYml.getInfoString(p, "룬.아이템 이름")).split(" ");
				if (str[1].equalsIgnoreCase("치유")) {
					if (str.length == 5) {
						int i = Integer.parseInt(str[4].replace("+", ""));
						
						switch (i) {
						case 1:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2, true));
							break;
							
						case 2:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2, true));
							break;
							
						case 3:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 3, true));
							break;
							
						case 4:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 3, true));
							break;
							
						case 5:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 3, true));
							break;
							
						case 6:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 4, true));
							break;
							
						case 7:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 4, true));
							break;
							
						case 8:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 5, true));
							break;
							
						case 9:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 7, true));
							break;
							
						case 10:
							p.removePotionEffect(PotionEffectType.REGENERATION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 8, true));
							break;
						}
					} else {
						p.removePotionEffect(PotionEffectType.REGENERATION);
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2, true));
					}
				} else {
					p.removePotionEffect(PotionEffectType.REGENERATION);
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 1, true));
				}
			} else {
				p.removePotionEffect(PotionEffectType.REGENERATION);
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 1, true));
			}
		}
	}
	
	public static void KingTimer(Player p)
	{
		if (Method.isHelmetItem(p, " 킹 모자") && Method.isChestplateItem(p, " 킹 튜닉")
		 && Method.isLeggingsItem(p, " 킹 바지") && Method.isBootsItem(p, " 킹 신발")) {
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ZOMBIE_WOOD, 2.0F, 1.0F);
		}
	}
	
	public static void FreezeTimer(Player p)
	{
		if (Method.isHelmetItem(p, " 포동포동 프리즈너 모자") && Method.isChestplateItem(p, " 포동포동 프리즈너 튜닉")
		 && Method.isLeggingsItem(p, " 포동포동 프리즈너 바지") && Method.isBootsItem(p, " 포동포동 프리즈너 신발")) {
			p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 2, true));
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("abcd1")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (p.isOp()) {
						if (!(DongJaInfo.contains(p)) && !(DongJaPos.contains(p)) && !(DongJaState.contains(p))) {
							DongJaInfo.add(p);
							p.sendMessage("§6채팅창에 정보를 열람할 플레이어를 입력해 주세요.");
						} return false;
					} return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.isOp()) {
					if (!(DongJaInfo.contains(p)) && !(DongJaPos.contains(p)) && !(DongJaState.contains(p))) {
						DongJaInfo.add(p);
						p.sendMessage("§6채팅창에 정보를 열람할 플레이어를 입력해 주세요.");
					} return false;
				} return false;
			} else {
				return false;
			}
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("abcd2")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (p.isOp()) {
						if (!(DongJaInfo.contains(p)) && !(DongJaPos.contains(p)) && !(DongJaState.contains(p))) {
							DongJaPos.add(p);
							p.sendMessage("§6채팅창에 좌표를 열람할 플레이어를 입력해 주세요.");
						} return false;
					} return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.isOp()) {
					if (!(DongJaInfo.contains(p)) && !(DongJaPos.contains(p)) && !(DongJaState.contains(p))) {
						DongJaPos.add(p);
						p.sendMessage("§6채팅창에 좌표를 열람할 플레이어를 입력해 주세요.");
					} return false;
				} return false;
			} else {
				return false;
			}
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("abcd3")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (p.isOp()) {
						if (!(DongJaInfo.contains(p)) && !(DongJaPos.contains(p)) && !(DongJaState.contains(p))) {
							DongJaState.add(p);
							p.sendMessage("§6채팅창에 상태를 열람할 플레이어를 입력해 주세요.");
						} return false;
					} return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.isOp()) {
					if (!(DongJaInfo.contains(p)) && !(DongJaPos.contains(p)) && !(DongJaState.contains(p))) {
						DongJaState.add(p);
						p.sendMessage("§6채팅창에 상태를 열람할 플레이어를 입력해 주세요.");
					} return false;
				} return false;
			} else {
				return false;
			}
		}
		
		return false;
	}
}
