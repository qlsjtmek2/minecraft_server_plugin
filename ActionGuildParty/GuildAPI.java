package me.espoo.rpg.guild;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.espoo.rpg.PlayerYml;
import me.espoo.rpg.main;
import us.talabrek.ultimateskyblock.IslandCommand;
import us.talabrek.ultimateskyblock.PlayerInfo;
import us.talabrek.ultimateskyblock.Settings;
import us.talabrek.ultimateskyblock.WorldGuardHandler;
import us.talabrek.ultimateskyblock.uSkyBlock;

public class GuildAPI {
	public static boolean checkInputOnlyNumberAndAlphabet(String textInput) {
		char chrInput;
		
		for (int i = 0; i < textInput.length(); i++) {
			chrInput = textInput.charAt(i); // �Է¹��� �ؽ�Ʈ���� ���� �ϳ��ϳ� �����ͼ� üũ
		   
			if (chrInput >= 0x61 && chrInput <= 0x7A) {
				// ����(�ҹ���) OK!
			}
			else if (chrInput >= 0x41 && chrInput <= 0x5A) {
				// ����(�빮��) OK!
			}
			else if (chrInput >= 0x30 && chrInput <= 0x39) {
				// ���� OK!
			}
			else if (chrInput == 0x5F) {
				// _ OK!
			}
			else {
				return false;   // �����ڵ� �ƴϰ� ���ڵ� �ƴ�!
			}
		}
		 
		return true;
	}
	
	public static String getCreateMoney() {
		return GuildConfig.getGuildString("��� â�� ���");
	}
	
	public static boolean getGuildLand(String name) {
		return GuildYml.getBoolean(name + ".�ε���.���� ����");
	}
	
	public static int getLandAcceptUse(String name) {
		return GuildYml.getInt(name + ".�ε���.����ȭ�α���");
	}
	
	public static int getLandAcceptBlock(String name) {
		return GuildYml.getInt(name + ".�ε���.��ġ���ű���");
	}
	
	public static boolean getLandPvPType(String name) {
		return GuildYml.getBoolean(name + ".�ε���.PvP����");
	}
	
	public static void setLandPvPType(String name, boolean is) {
		GuildYml.setBoolean(name + ".�ε���.PvP����", is);
	}
	
	public static void setLandAcceptBlock(String name, int type) {
		GuildYml.setInt(name + ".�ε���.��ġ���ű���", type);
	}
	
	public static void setLandAcceptUse(String name, int type) {
		GuildYml.setInt(name + ".�ε���.����ȭ�α���", type);
	}
	
	public static void setGuildLand(String name, boolean is) {
		GuildYml.setBoolean(name + ".�ε���.���� ����", is);
	}
	
	public static String getJoinGuild(String p) {
		return PlayerYml.getInfoString(p, "���Ե� ���");
	}
	
	public static void setJoinGuild(Player p, String guild) {
		PlayerYml.setInfoString(p, "���Ե� ���", guild);
	}
	
	public static void setJoinMaster(Player p, String day) {
		String name = GuildAPI.getJoinGuild(p.getName());
		if (isGuild(name)) {
			GuildYml.setString(name + ".�Ŵ��� ������ ���ӽð�", day);
		}
	}
	
	public static void setWarGuild(String guild, String guild2) {
		if (isGuild(guild)) {
			GuildYml.setString(guild + ".���� ���", guild2);
		}
	}
	
	public static String getWarGuild(String name) {
		if (isGuild(name)) {
			return GuildYml.getString(name + ".���� ���");
		} return null;
	}
	
	public static boolean isWar(String name) {
		if (isGuild(name)) {
			if (GuildYml.getString(name + ".���� ���") != null) return true;
			else return false;
		} return false;
	}
	
	public static boolean isSubmit(String name) {
		if (isGuild(name)) {
			return GuildYml.getBoolean(name + ".��� ��� �׺� ��û");
		} return false;
	}
	
	public static void setSubmit(String name, boolean is) {
		if (isGuild(name)) {
			GuildYml.setBoolean(name + ".��� ��� �׺� ��û", is);
		}
	}
	
	public static int getYear(String name) {
		if (isGuild(name)) {
			return Integer.parseInt(GuildYml.getString(name + ".��� ������ ��¥").split(",")[0]);
		} return -1;
	}
	
	public static int getMonth(String name) {
		if (isGuild(name)) {
			return Integer.parseInt(GuildYml.getString(name + ".��� ������ ��¥").split(",")[1]);
		} return -1;
	}
	
	public static int getDate(String name) {
		if (isGuild(name)) {
			return Integer.parseInt(GuildYml.getString(name + ".��� ������ ��¥").split(",")[2]);
		} return -1;
	}
	
	public static String getMakeDate(String name) {
		if (isGuild(name)) {
			return GuildYml.getString(name + ".��� ������ ��¥");
		} return null;
	}
	
	public static String getJoinMaster(String name) {
		if (isGuild(name)) {
			return GuildYml.getString(name + ".�Ŵ��� ������ ���ӽð�");
		} return null;
	}
	
	public static void changeGuildMaster(Player p, Player pl) {
		String guild = GuildAPI.getJoinGuild(p.getName());
		String userClass = GuildAPI.getUserClass(guild, pl.getName());
		GuildAPI.setUserClass(guild, p.getName(), userClass);
		GuildAPI.setUserClass(guild, pl.getName(), "�Ŵ���");
		GuildAPI.setMaster(guild, pl.getName());

        IslandCommand is = new IslandCommand();
        String tempTargetPlayer = GuildAPI.getManager(guild);
        is.removePlayerFromParty(pl.getName(), tempTargetPlayer);
        is.removePlayerFromParty(p.getName(), tempTargetPlayer);
        is.addPlayertoParty(p.getName(), tempTargetPlayer);
        is.addPlayertoParty(pl.getName(), tempTargetPlayer);

        uSkyBlock.getInstance().transferIsland(p.getName(), tempTargetPlayer);

        if (Settings.island_protectWithWorldGuard && Bukkit.getServer().getPluginManager().isPluginEnabled("WorldGuard")) {
        	WorldGuardHandler.transferRegion(p.getName(), tempTargetPlayer, p);
        }
	}
	
	public static void addUser(String guild, Player p) {
		PlayerYml.setInfoString(p, "���Ե� ���", guild);
		List<String> list = GuildAPI.getUser(guild);
		list.add(p.getName() + ",����");
		GuildAPI.setUser(guild, list);
		
		if (!uSkyBlock.getInstance().hasParty(GuildAPI.getManager(guild)))
        {
			PlayerInfo pi = (PlayerInfo) uSkyBlock.getInstance().getActivePlayers().get(p.getName());
			if (pi.getHasIsland())
	            uSkyBlock.getInstance().deletePlayerIsland(p.getName());
			IslandCommand is = new IslandCommand();
	        is.addPlayertoParty(p.getName(), GuildAPI.getManager(guild));
	        is.addPlayertoParty(GuildAPI.getManager(guild), GuildAPI.getManager(guild));
        } else {
			PlayerInfo pi = (PlayerInfo) uSkyBlock.getInstance().getActivePlayers().get(p.getName());
			if (pi.getHasIsland())
	            uSkyBlock.getInstance().deletePlayerIsland(p.getName());
			IslandCommand is = new IslandCommand();
	        is.addPlayertoParty(p.getName(), GuildAPI.getManager(guild));
        }
	}
	
	public static void addPoint(String guild, int amount) {
		int num = GuildAPI.getPoint(guild) + amount;
		int i = 0;
		for (String str : GuildYml.getGuildList()) {
			if (str.equalsIgnoreCase(guild)) {
				break;
			} else i++;
		}
		
		List<Integer> list = GuildYml.getPointList();
		list.set(i, num);
		GuildYml.setPointList(list);
	}
	
	public static void subPoint(String guild, int amount) {
		int num = GuildAPI.getPoint(guild) - amount;
		int i = 0;
		for (String str : GuildYml.getGuildList()) {
			if (str.equalsIgnoreCase(guild)) {
				break;
			} else i++;
		}
		
		List<Integer> list = GuildYml.getPointList();
		list.set(i, num);
		GuildYml.setPointList(list);
	}
	
	public static int getPoint(String name) {
		if (isGuild(name)) {
			int num = 0;
			for (String str : GuildYml.getGuildList()) {
				if (str.equalsIgnoreCase(name)) {
					break;
				} else num++;
			} return GuildYml.getPointList().get(num);
		} return -1;
	}
	
	public static boolean isSubPoint(String name, int i) {
		if (isGuild(name)) {
			int num = 0;
			for (String str : GuildYml.getGuildList()) {
				if (str.equalsIgnoreCase(name)) {
					break;
				} else num++;
			}
			
			if (GuildYml.getPointList().get(num) < i) return false;
			else return true;
		} return false;
	}
	
	public static void subUser(String guild, OfflinePlayer pl) {
		PlayerYml.setInfoString(pl, "���Ե� ���", null);
		List<String> list = GuildAPI.getUser(guild);
		for (String str : getUser(guild))
			if (str.split(",")[0].equalsIgnoreCase(pl.getName()))
				list.remove(str);
		
		GuildAPI.setUser(guild, list);
        if (uSkyBlock.getInstance().hasParty(pl.getName()))
        {
    	    PlayerInfo pi = (PlayerInfo)uSkyBlock.getInstance().getActivePlayers().get(pl.getName());
    		
    		String tempLeader = GuildAPI.getManager(guild);
    		IslandCommand is = new IslandCommand();
            is.removePlayerFromParty(pl.getName(), tempLeader);
    		pi.getMembers().remove(pl.getName());
            if (pi.getMembers().size() < 2) {
            	is.removePlayerFromParty(tempLeader, tempLeader);
            }
            
            if (Settings.island_protectWithWorldGuard && Bukkit.getServer().getPluginManager().isPluginEnabled("WorldGuard"))
            	WorldGuardHandler.removePlayerFromRegion(tempLeader, pl.getName());
        }
	}
	
	public static boolean isGuild(String name) {
		if (GuildYml.getGuildList().contains(name)) return true;
		else return false;
	}
	
	public static String getManager(String name) {
		if (isGuild(name)) {
			return GuildYml.getString(name + ".�Ŵ���");
		} return null;
	}
	
	public static int getUpGrade(String name) {
		if (isGuild(name)) {
			return GuildYml.getInt(name + ".���׷��̵� �ܰ�");
		} return -1;
	}

	public static void setUpGrade(String name, int amount) {
		if (isGuild(name)) {
			GuildYml.setInt(name + ".���׷��̵� �ܰ�", amount);
		}
	}
	
	public static void setMaster(String name, String player) {
		if (isGuild(name)) {
			GuildYml.setString(name + ".�Ŵ���", player);
		}
	}
	
	public static void setInfoCode(String name, String code) {
		if (isGuild(name)) {
			if (code.contains(":")) if (code.split(":")[1].equalsIgnoreCase("0"))
			if (code.split(":")[0].equalsIgnoreCase("324") || code.split(":")[0].equalsIgnoreCase("10") 
			 || code.split(":")[0].equalsIgnoreCase("8") || code.split(":")[0].equalsIgnoreCase("119")) return;
			
			else if (code.equalsIgnoreCase("324") || code.equalsIgnoreCase("10") 
				  || code.equalsIgnoreCase("8") || code.equalsIgnoreCase("119")) return;
			
			GuildYml.setString(name + ".����.������ �ڵ�", code);
		}
	}
	
	public static int getInfoCode(String name) {
		if (isGuild(name)) {
			String code = GuildYml.getString(name + ".����.������ �ڵ�");
			if (code.contains(":")) {
				return Integer.parseInt(code.split(":")[0]);
			} else return Integer.parseInt(code);
		} return 0;
	}
	
	public static int getInfoCodeData(String name) {
		if (isGuild(name)) {
			String code = GuildYml.getString(name + ".����.������ �ڵ�");
			if (code.contains(":")) {
				return Integer.parseInt(code.split(":")[1]);
			} else return 0;
		} return 0;
	}
	
	public static String getInfoMessage(String name) {
		if (isGuild(name)) {
			return GuildYml.getString(name + ".����.���� �޼���");
		} return null;
	}
	
	public static int getUserInteger(String name) {
		if (isGuild(name)) {
			return GuildYml.getList(name + ".���� ���").size();
		} return -1;
	}
	
	public static List<String> getInfoLore(String name) {
		if (isGuild(name)) {
			return GuildYml.getList(name + ".����.��� ����");
		} return null;
	}
	
	public static List<Player> getOnLineUser(String name) {
		if (isGuild(name)) {
			List<Player> list = new ArrayList<Player>();
			
			for (Player p : Bukkit.getOnlinePlayers()) {
				for (String str : main.GuildUserList.get(name)) {
					if (p.getName().equalsIgnoreCase(str)) {
						list.add(p);
					}
				}
			} return list;
		} return null;
	}
	
	public static List<String> getClassPlayer(String name, String user, String Class) {
		if (isGuild(name)) {
			List<String> list = new ArrayList<String>();
			
			for (String str : getUser(name)) {
				if (str.split(",")[1].equalsIgnoreCase(Class)) {
					list.add(str.split(",")[0]);
				}
			} return list;
		} return null;
	}
	
	public static String getUserClass(String name, String user) {
		if (isGuild(name)) {
			for (String str : getUser(name)) {
				if (str.split(",")[0].equalsIgnoreCase(user)) {
					return str.split(",")[1];
				}
			}
		} return null;
	}
	
	public static List<String> getUser(String name) {
		if (isGuild(name)) {
			return GuildYml.getList(name + ".���� ���");
		} return null;
	}
	
	public static List<String> getUserOrg(String name) {
		if (isGuild(name)) {
			List<String> list = new ArrayList<String>();
			for (String str : getUser(name)) {
				list.add(str.split(",")[0]);
			} return list;
		} return null;
	}
	
	public static void setUserClass(String name, String user, String Class) {
		if (isGuild(name)) {
			List<String> list = getUser(name); int num = 0;
			for (String str : list) {
				if (str.split(",")[0].equalsIgnoreCase(user)) {
					list.set(num, user + "," + Class);
				} num++;
			} setUser(name, list);
		}
	}
	
	public static void setUser(String name, List<String> list) {
		if (isGuild(name)) {
			GuildYml.setList(name + ".���� ���", list);
			main.GuildUserList.put(name, GuildAPI.getUserOrg(name));
		}
	}
	
	public static void setInfoMessage(String name, String message) {
		if (isGuild(name)) {
			GuildYml.setString(name + ".����.���� �޼���", message);
		}
	}
	
	public static void setInfoLore(String name, List<String> list) {
		if (isGuild(name)) {
			GuildYml.setList(name + ".����.��� ����", list);
		}
	}
	
	public static int getClassAmount(String name, String Class) {
		if (isGuild(name)) {
			int num = 0;
			if (Class.equalsIgnoreCase("����")) {
				for (String str : getUser(name)) {
					if (str.contains("����")) num++;
				} return num;
			}
			
			else if (Class.equalsIgnoreCase("��ȸ��")) {
				for (String str : getUser(name)) {
					if (str.contains("��ȸ��")) num++;
				} return num;
			}
			
			else if (Class.equalsIgnoreCase("�����̳�")) {
				for (String str : getUser(name)) {
					if (str.contains("�����̳�")) num++;
				} return num;
			}

			else if (Class.equalsIgnoreCase("����")) {
				for (String str : getUser(name)) {
					if (str.contains("����")) num++;
				} return num;
			}
		} return -1;
	}
	
	// ���� = 1, ��ȸ�� = 2, �����̳� = 3, ���� = 4
	public static int getClassConfig(String name, int upgrade, int num) {
		if (isGuild(name)) {
			List<String> list = GuildConfig.getGuildList("��� ���׷��̵�");
			String str = list.get(upgrade + 1);
			return Integer.parseInt(str.split(" ")[num + 1]);
		} return -1;
	}
	
	public static int getMaxUser(String name) {
		if (isGuild(name)) {
			int upgrade = GuildAPI.getUpGrade(name);
			List<String> list = GuildConfig.getGuildList("��� ���׷��̵�");
			String str = list.get(upgrade + 1);
			return Integer.parseInt(str.split(" ")[2]);
		} return -1;
	}
	
	public static void setExpStat(String name) {
		if (isGuild(name)) {
			GuildYml.setBoolean(name + ".����.����ġ Ȱ��ȭ", !isExpStat(name));
		}
	}
	
	public static void setPlantStat(String name) {
		if (isGuild(name)) {
			GuildYml.setBoolean(name + ".����.��� Ȱ��ȭ", !isPlantStat(name));
		}
	}
	
	public static void setMineStat(String name) {
		if (isGuild(name)) {
			GuildYml.setBoolean(name + ".����.ä�� Ȱ��ȭ", !isMineStat(name));
		}
	}
	
	public static boolean isExpStat(String name) {
		if (isGuild(name)) {
			return GuildYml.getBoolean(name + ".����.����ġ Ȱ��ȭ");
		} return false;
	}
	
	public static boolean isPlantStat(String name) {
		if (isGuild(name)) {
			return GuildYml.getBoolean(name + ".����.��� Ȱ��ȭ");
		} return false;
	}
	
	public static boolean isMineStat(String name) {
		if (isGuild(name)) {
			return GuildYml.getBoolean(name + ".����.ä�� Ȱ��ȭ");
		} return false;
	}
	
	public static int getExpStat(String name) {
		if (isGuild(name)) {
			return GuildYml.getInt(name + ".����.����ġ");
		} return -1;
	}
	
	public static int getPlantStat(String name) {
		if (isGuild(name)) {
			return GuildYml.getInt(name + ".����.���");
		} return -1;
	}
	
	public static int getMineStat(String name) {
		if (isGuild(name)) {
			return GuildYml.getInt(name + ".����.ä��");
		} return -1;
	}
	
	public static int getMaxExpStat(String name) {
		if (isGuild(name)) {
			int i = GuildAPI.getUpGrade(name) + 1;
			return Integer.parseInt(GuildConfig.getGuildList("��� ���׷��̵�").get(i).split(" ")[6]);
		} return -1;
	}
	
	public static int getMaxPlantStat(String name) {
		if (isGuild(name)) {
			int i = GuildAPI.getUpGrade(name) + 1;
			return Integer.parseInt(GuildConfig.getGuildList("��� ���׷��̵�").get(i).split(" ")[7]);
		} return -1;
	}
	
	public static int getMaxMineStat(String name) {
		if (isGuild(name)) {
			int i = GuildAPI.getUpGrade(name) + 1;
			return Integer.parseInt(GuildConfig.getGuildList("��� ���׷��̵�").get(i).split(" ")[8]);
		} return -1;
	}
	
	public static void setExpStat(String name, int amount) {
		if (isGuild(name)) {
			GuildYml.setInt(name + ".����.����ġ", amount);
		}
	}
	
	public static String getPlayerChat(Player p) {
		return PlayerYml.getInfoString(p.getName(), "ä�� ���");
	}
	
	public static void setPlayerChat(Player p, String mode) {
		PlayerYml.setInfoString(p, "ä�� ���", mode);
	}
	
	public static String getJoinType(String name) {
		if (isGuild(name)) {
			switch (GuildYml.getInt(name + ".���� Ÿ��")) {
			case 0:
				return "����"; 
			case 1:
				return "��û��"; 
			case 2:
				return "�����";
			}
		} return null;
	}
	
	// 0 - ����, 1 - ��û��, 2 - �����
	public static void setJoinType(String name, int amount) {
		if (isGuild(name)) {
			GuildYml.setInt(name + ".���� Ÿ��", amount);
		}
	}
	
	public static List<String> getJoinList(String name) {
		return GuildYml.getList(name + ".���� ��û ���");
	}
	
	public static void addJoinList(String name, String player) {
		if (isGuild(name)) {
			List<String> list = getJoinList(name);
			if (!list.contains(player)) list.add(player);
			GuildYml.setList(name + ".���� ��û ���", list);
		}
	}
	
	public static void subJoinList(String name, String player) {
		if (isGuild(name)) {
			List<String> list = getJoinList(name);
			if (list.contains(player)) list.remove(player);
			GuildYml.setList(name + ".���� ��û ���", list);
		}
	}
	
	public static void setPlantStat(String name, int amount) {
		if (isGuild(name)) {
			GuildYml.setInt(name + ".����.���", amount);
			main.GuildPlantStat.put(name, amount);
		}
	}
	
	public static void setMineStat(String name, int amount) {
		if (isGuild(name)) {
			GuildYml.setInt(name + ".����.ä��", amount);
			main.GuildMineStat.put(name, amount);
		}
	}
	
	public static void addExpStat(String name, int amount) {
		if (isGuild(name)) {
			int num = getExpStat(name) + amount;
			GuildYml.setInt(name + ".����.����ġ", num);
		}
	}
	
	public static void addPlantStat(String name, int amount) {
		if (isGuild(name)) {
			int num = getPlantStat(name) + amount;
			GuildYml.setInt(name + ".����.���", num);
			main.GuildPlantStat.put(name, num);
		}
	}
	
	public static void addMineStat(String name, int amount) {
		if (isGuild(name)) {
			int num = getMineStat(name) + amount;
			GuildYml.setInt(name + ".����.ä��", num);
			main.GuildMineStat.put(name, num);
		}
	}
	
	public static ItemStack getInfoItem(String name) {
		if (isGuild(name)) {
			ItemStack item = new MaterialData(getInfoCode(name), (byte) getInfoCodeData(name)).toItemStack(1);
			ItemMeta item_Meta = item.getItemMeta();
			List<String> list = new ArrayList<String>();
			list.add("��7���� �޼���: ��f" + getInfoMessage(name));
			list.add("��7��� ����Ʈ: ��f" + getPoint(name));
			list.add("��7��� ���� ��: ��f" + getUserInteger(name));
			list.add("��7��� ���׷��̵� �ܰ�: ��f" + getUpGrade(name));
			for (String str : getInfoLore(name)) list.add(str);
			item_Meta.setLore(list);
			item_Meta.setDisplayName("��6" + name);
			item.setItemMeta(item_Meta);
			return item;
		} return null;
	}
	
	public static Location StringToLocation(String s) {
		String[] loc = s.split(",");
		Location loc2 = new Location(Bukkit.getWorld(loc[3]), Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]));
		return loc2;
	}
	
	public static void sendCommand(String s) {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
	}
	
	public static void dropBlock(Player p, Block b, ItemStack item) {
		ItemStack item2 = null;

		if (item.getType() == Material.CROPS) {
			item2 = new ItemStack(295, 1);
		}

		ItemMeta meta = item.getItemMeta();
		List<String> list = new ArrayList<String>();
		list.add("��7�� ���۹��� �丮 ��������,");
		list.add("��7�ɰų� �Ǹ��Ͻ� �� �����ϴ�.");
		list.add("��fä���� ��7������ ���� �� �ֽ��ϴ�.");
		meta.setLore(list);
		item.setItemMeta(meta);
		int num = b.getDrops().size();

		while (num > 0) {
			num--;
			p.getWorld().dropItemNaturally(b.getLocation(), item);
		}

		if (item2 != null) {
			int Random = new Random().nextInt(4) + 1;
			switch (Random) {
			case 1:
				break;
			default:
				item2.setItemMeta(meta);
				p.getWorld().dropItemNaturally(b.getLocation(), item2);
				break;
			}
		}
	}
}
