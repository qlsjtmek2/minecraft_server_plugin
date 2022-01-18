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
		 
		Waepon.add("Ÿ����Ű");
		Waepon.add("������");
		Waepon.add("�ݼ�");
		Waepon.add("������");
		Waepon.add("��Ż����Ʈ");
		Waepon.add("���߸���");
		Waepon.add("������");
		Waepon.add("�����ϼ��");
		Waepon.add("�ɹ̳�");
		Waepon.add("�����");
		Waepon.add("������");
		Waepon.add("���뽺��");
		Waepon.add("�����");
		Waepon.add("��������");
		Waepon.add("ŷ��");
		Waepon.add("��ũ��");
		Waepon.add("����");
		Waepon.add("�ĺ�Ű");
		Waepon.add("����");
	    
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public static void SocketTimer(Player p)
	{
		int num = 0;
		int num2 = 0;
		
		if (me.espoo.socket.PlayerYml.getInfoInt(p, "1.������ �ڵ�") != 0 && me.espoo.socket.PlayerYml.getInfoString(p, "1.������ �̸�") != null && !me.espoo.socket.PlayerYml.getInfoList(p, "1.������ ����").equals(Arrays.asList())) {
			String str = me.espoo.socket.API.getLoreNum(me.espoo.socket.PlayerYml.getInfoList(p, "1.������ ����"));
			if (str.equalsIgnoreCase("����ġ ���ʽ�")) {
				num += me.espoo.socket.API.getExpBouns(me.espoo.socket.PlayerYml.getInfoList(p, "1.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("����� ���� �߰�")) {
				num2 += me.espoo.socket.API.getHeroScore(me.espoo.socket.PlayerYml.getInfoList(p, "1.������ ����"));
			}
		}
		
		if (me.espoo.socket.PlayerYml.getInfoInt(p, "2.������ �ڵ�") != 0 && me.espoo.socket.PlayerYml.getInfoString(p, "2.������ �̸�") != null && !me.espoo.socket.PlayerYml.getInfoList(p, "2.������ ����").equals(Arrays.asList())) {
			String str = me.espoo.socket.API.getLoreNum(me.espoo.socket.PlayerYml.getInfoList(p, "2.������ ����"));
			if (str.equalsIgnoreCase("����ġ ���ʽ�")) {
				num += me.espoo.socket.API.getExpBouns(me.espoo.socket.PlayerYml.getInfoList(p, "2.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("����� ���� �߰�")) {
				num2 += me.espoo.socket.API.getHeroScore(me.espoo.socket.PlayerYml.getInfoList(p, "2.������ ����"));
			}
		}
		
		if (me.espoo.socket.PlayerYml.getInfoInt(p, "3.������ �ڵ�") != 0 && me.espoo.socket.PlayerYml.getInfoString(p, "3.������ �̸�") != null && !me.espoo.socket.PlayerYml.getInfoList(p, "3.������ ����").equals(Arrays.asList())) {
			String str = me.espoo.socket.API.getLoreNum(me.espoo.socket.PlayerYml.getInfoList(p, "3.������ ����"));
			if (str.equalsIgnoreCase("����ġ ���ʽ�")) {
				num += me.espoo.socket.API.getExpBouns(me.espoo.socket.PlayerYml.getInfoList(p, "3.������ ����"));
			}

			else if (str.equalsIgnoreCase("����� ���� �߰�")) {
				num2 += me.espoo.socket.API.getHeroScore(me.espoo.socket.PlayerYml.getInfoList(p, "3.������ ����"));
			}
		}
		
		if (num != 0) {
			ExpAPI.setBounsAll("����", p.getName(), num + 100, -1);
		} else {
			ExpAPI.removeBouns("����", p.getName());
		}
		
		if (num2 != 0) {
			me.espoo.score.Method.setPlayerBouns(p, num2 + 100);
		} else {
			me.espoo.score.Method.setPlayerBouns(p, 100);
		}
	}
	
	public static void HedenLoonTimer(Player p)
	{
		if (PlayerYml.getInfoInt(p, "��.������ �ڵ�") != 0 && PlayerYml.getInfoString(p, "��.������ �̸�") != null && !PlayerYml.getInfoList(p, "��.������ ����").equals(Arrays.asList())) {
			String[] str = ChatColor.stripColor(PlayerYml.getInfoString(p, "��.������ �̸�")).split(" ");
			if (str[1].equalsIgnoreCase("����")) {
				if (str.length == 5) {
					int i = Integer.parseInt(str[4].replace("+", ""));
						switch (i) {
						case 1:
							ExpAPI.setBounsAll("����", p.getName(), 150, -1);
							break;
							
						case 2:
							ExpAPI.setBounsAll("����", p.getName(), 200, -1);
							break;
							
						case 3:
							ExpAPI.setBounsAll("����", p.getName(), 200, -1);
							break;
							
						case 4:
							ExpAPI.setBounsAll("����", p.getName(), 250, -1);
							break;
							
						case 5:
							ExpAPI.setBounsAll("����", p.getName(), 250, -1);
							break;
							
						case 6:
							ExpAPI.setBounsAll("����", p.getName(), 250, -1);
							break;
							
						case 7:
							ExpAPI.setBounsAll("����", p.getName(), 300, -1);
							break;
							
						case 8:
							ExpAPI.setBounsAll("����", p.getName(), 300, -1);
							break;
							
						case 9:
							ExpAPI.setBounsAll("����", p.getName(), 300, -1);
							break;
							
						case 10:
							ExpAPI.setBounsAll("����", p.getName(), 350, -1);
							break;
					}
				} else {
					ExpAPI.setBounsAll("����", p.getName(), 150, -1);
				}
			} else {
				ExpAPI.removeBouns("����", p.getName());
			}
		} else {
			ExpAPI.removeBouns("����", p.getName());
		}
	}
	
	public static void RegLoonTimer(Player p)
	{
		if (Method.isHelmetItem(p, " �Ƹ��̸���ũ ����") && Method.isChestplateItem(p, " �Ƹ��̸���ũ Ʃ��")
		 && Method.isLeggingsItem(p, " �Ƹ��̸���ũ ����") && Method.isBootsItem(p, " �Ƹ��̸���ũ �Ź�")) {
			return;
		} else {
			if (PlayerYml.getInfoInt(p, "��.������ �ڵ�") != 0 && PlayerYml.getInfoString(p, "��.������ �̸�") != null && !PlayerYml.getInfoList(p, "��.������ ����").equals(Arrays.asList())) {
				String[] str = ChatColor.stripColor(PlayerYml.getInfoString(p, "��.������ �̸�")).split(" ");
				if (str[1].equalsIgnoreCase("ġ��")) {
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
		if (Method.isHelmetItem(p, " ������� ����") && Method.isChestplateItem(p, " ������� ����")
		 && Method.isLeggingsItem(p, " ������� ���뽺") && Method.isBootsItem(p, " ������� ����") ||
		 	Method.isHelmetItem(p, " ������ �Ҵ� ����") && Method.isChestplateItem(p, " ������ �Ҵ� Ʃ��")
		 && Method.isLeggingsItem(p, " ������ �Ҵ� ����") && Method.isBootsItem(p, " ������ �Ҵ� �Ź�")) {
			return;
		} else {
			if (PlayerYml.getInfoInt(p, "��.������ �ڵ�") != 0 && PlayerYml.getInfoString(p, "��.������ �̸�") != null && !PlayerYml.getInfoList(p, "��.������ ����").equals(Arrays.asList())) {
				String[] str = ChatColor.stripColor(PlayerYml.getInfoString(p, "��.������ �̸�")).split(" ");
				if (str[1].equalsIgnoreCase("�ٶ�")) {
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
		if (Method.isHelmetItem(p, " ������� ����") && Method.isChestplateItem(p, " ������� ����")
		 && Method.isLeggingsItem(p, " ������� ���뽺") && Method.isBootsItem(p, " ������� ����")) {
			if (PlayerYml.getInfoInt(p, "��.������ �ڵ�") != 0 && PlayerYml.getInfoString(p, "��.������ �̸�") != null && !PlayerYml.getInfoList(p, "��.������ ����").equals(Arrays.asList())) {
				String[] str = ChatColor.stripColor(PlayerYml.getInfoString(p, "��.������ �̸�")).split(" ");
				if (str[1].equalsIgnoreCase("�ٶ�")) {
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
		if (Method.isHelmetItem(p, " ������ �Ҵ� ����") && Method.isChestplateItem(p, " ������ �Ҵ� Ʃ��")
		 && Method.isLeggingsItem(p, " ������ �Ҵ� ����") && Method.isBootsItem(p, " ������ �Ҵ� �Ź�")) {
			if (PlayerYml.getInfoInt(p, "��.������ �ڵ�") != 0 && PlayerYml.getInfoString(p, "��.������ �̸�") != null && !PlayerYml.getInfoList(p, "��.������ ����").equals(Arrays.asList())) {
				String[] str = ChatColor.stripColor(PlayerYml.getInfoString(p, "��.������ �̸�")).split(" ");
				if (str[1].equalsIgnoreCase("�ٶ�")) {
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
		if (Method.isHelmetItem(p, " �Ƹ��̸���ũ ����") && Method.isChestplateItem(p, " �Ƹ��̸���ũ Ʃ��")
	 	 && Method.isLeggingsItem(p, " �Ƹ��̸���ũ ����") && Method.isBootsItem(p, " �Ƹ��̸���ũ �Ź�")) {
			p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 1, true));
			
			if (PlayerYml.getInfoInt(p, "��.������ �ڵ�") != 0 && PlayerYml.getInfoString(p, "��.������ �̸�") != null && !PlayerYml.getInfoList(p, "��.������ ����").equals(Arrays.asList())) {
				String[] str = ChatColor.stripColor(PlayerYml.getInfoString(p, "��.������ �̸�")).split(" ");
				if (str[1].equalsIgnoreCase("ġ��")) {
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
		if (Method.isHelmetItem(p, " ŷ ����") && Method.isChestplateItem(p, " ŷ Ʃ��")
		 && Method.isLeggingsItem(p, " ŷ ����") && Method.isBootsItem(p, " ŷ �Ź�")) {
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ZOMBIE_WOOD, 2.0F, 1.0F);
		}
	}
	
	public static void FreezeTimer(Player p)
	{
		if (Method.isHelmetItem(p, " �������� ������� ����") && Method.isChestplateItem(p, " �������� ������� Ʃ��")
		 && Method.isLeggingsItem(p, " �������� ������� ����") && Method.isBootsItem(p, " �������� ������� �Ź�")) {
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
							p.sendMessage("��6ä��â�� ������ ������ �÷��̾ �Է��� �ּ���.");
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
						p.sendMessage("��6ä��â�� ������ ������ �÷��̾ �Է��� �ּ���.");
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
							p.sendMessage("��6ä��â�� ��ǥ�� ������ �÷��̾ �Է��� �ּ���.");
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
						p.sendMessage("��6ä��â�� ��ǥ�� ������ �÷��̾ �Է��� �ּ���.");
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
							p.sendMessage("��6ä��â�� ���¸� ������ �÷��̾ �Է��� �ּ���.");
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
						p.sendMessage("��6ä��â�� ���¸� ������ �÷��̾ �Է��� �ּ���.");
					} return false;
				} return false;
			} else {
				return false;
			}
		}
		
		return false;
	}
}
