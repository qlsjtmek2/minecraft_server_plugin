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
			chrInput = textInput.charAt(i); // 입력받은 텍스트에서 문자 하나하나 가져와서 체크
		   
			if (chrInput >= 0x61 && chrInput <= 0x7A) {
				// 영문(소문자) OK!
			}
			else if (chrInput >= 0x41 && chrInput <= 0x5A) {
				// 영문(대문자) OK!
			}
			else if (chrInput >= 0x30 && chrInput <= 0x39) {
				// 숫자 OK!
			}
			else if (chrInput == 0x5F) {
				// _ OK!
			}
			else {
				return false;   // 영문자도 아니고 숫자도 아님!
			}
		}
		 
		return true;
	}
	
	public static String getCreateMoney() {
		return GuildConfig.getGuildString("길드 창설 비용");
	}
	
	public static boolean getGuildLand(String name) {
		return GuildYml.getBoolean(name + ".부동산.존재 여부");
	}
	
	public static int getLandAcceptUse(String name) {
		return GuildYml.getInt(name + ".부동산.상자화로권한");
	}
	
	public static int getLandAcceptBlock(String name) {
		return GuildYml.getInt(name + ".부동산.설치제거권한");
	}
	
	public static boolean getLandPvPType(String name) {
		return GuildYml.getBoolean(name + ".부동산.PvP여부");
	}
	
	public static void setLandPvPType(String name, boolean is) {
		GuildYml.setBoolean(name + ".부동산.PvP여부", is);
	}
	
	public static void setLandAcceptBlock(String name, int type) {
		GuildYml.setInt(name + ".부동산.설치제거권한", type);
	}
	
	public static void setLandAcceptUse(String name, int type) {
		GuildYml.setInt(name + ".부동산.상자화로권한", type);
	}
	
	public static void setGuildLand(String name, boolean is) {
		GuildYml.setBoolean(name + ".부동산.존재 여부", is);
	}
	
	public static String getJoinGuild(String p) {
		return PlayerYml.getInfoString(p, "가입된 길드");
	}
	
	public static void setJoinGuild(Player p, String guild) {
		PlayerYml.setInfoString(p, "가입된 길드", guild);
	}
	
	public static void setJoinMaster(Player p, String day) {
		String name = GuildAPI.getJoinGuild(p.getName());
		if (isGuild(name)) {
			GuildYml.setString(name + ".매니저 마지막 접속시간", day);
		}
	}
	
	public static void setWarGuild(String guild, String guild2) {
		if (isGuild(guild)) {
			GuildYml.setString(guild + ".전쟁 길드", guild2);
		}
	}
	
	public static String getWarGuild(String name) {
		if (isGuild(name)) {
			return GuildYml.getString(name + ".전쟁 길드");
		} return null;
	}
	
	public static boolean isWar(String name) {
		if (isGuild(name)) {
			if (GuildYml.getString(name + ".전쟁 길드") != null) return true;
			else return false;
		} return false;
	}
	
	public static boolean isSubmit(String name) {
		if (isGuild(name)) {
			return GuildYml.getBoolean(name + ".상대 길드 항복 요청");
		} return false;
	}
	
	public static void setSubmit(String name, boolean is) {
		if (isGuild(name)) {
			GuildYml.setBoolean(name + ".상대 길드 항복 요청", is);
		}
	}
	
	public static int getYear(String name) {
		if (isGuild(name)) {
			return Integer.parseInt(GuildYml.getString(name + ".길드 생성된 날짜").split(",")[0]);
		} return -1;
	}
	
	public static int getMonth(String name) {
		if (isGuild(name)) {
			return Integer.parseInt(GuildYml.getString(name + ".길드 생성된 날짜").split(",")[1]);
		} return -1;
	}
	
	public static int getDate(String name) {
		if (isGuild(name)) {
			return Integer.parseInt(GuildYml.getString(name + ".길드 생성된 날짜").split(",")[2]);
		} return -1;
	}
	
	public static String getMakeDate(String name) {
		if (isGuild(name)) {
			return GuildYml.getString(name + ".길드 생성된 날짜");
		} return null;
	}
	
	public static String getJoinMaster(String name) {
		if (isGuild(name)) {
			return GuildYml.getString(name + ".매니저 마지막 접속시간");
		} return null;
	}
	
	public static void changeGuildMaster(Player p, Player pl) {
		String guild = GuildAPI.getJoinGuild(p.getName());
		String userClass = GuildAPI.getUserClass(guild, pl.getName());
		GuildAPI.setUserClass(guild, p.getName(), userClass);
		GuildAPI.setUserClass(guild, pl.getName(), "매니저");
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
		PlayerYml.setInfoString(p, "가입된 길드", guild);
		List<String> list = GuildAPI.getUser(guild);
		list.add(p.getName() + ",유저");
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
		PlayerYml.setInfoString(pl, "가입된 길드", null);
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
			return GuildYml.getString(name + ".매니저");
		} return null;
	}
	
	public static int getUpGrade(String name) {
		if (isGuild(name)) {
			return GuildYml.getInt(name + ".업그레이드 단계");
		} return -1;
	}

	public static void setUpGrade(String name, int amount) {
		if (isGuild(name)) {
			GuildYml.setInt(name + ".업그레이드 단계", amount);
		}
	}
	
	public static void setMaster(String name, String player) {
		if (isGuild(name)) {
			GuildYml.setString(name + ".매니저", player);
		}
	}
	
	public static void setInfoCode(String name, String code) {
		if (isGuild(name)) {
			if (code.contains(":")) if (code.split(":")[1].equalsIgnoreCase("0"))
			if (code.split(":")[0].equalsIgnoreCase("324") || code.split(":")[0].equalsIgnoreCase("10") 
			 || code.split(":")[0].equalsIgnoreCase("8") || code.split(":")[0].equalsIgnoreCase("119")) return;
			
			else if (code.equalsIgnoreCase("324") || code.equalsIgnoreCase("10") 
				  || code.equalsIgnoreCase("8") || code.equalsIgnoreCase("119")) return;
			
			GuildYml.setString(name + ".정보.아이템 코드", code);
		}
	}
	
	public static int getInfoCode(String name) {
		if (isGuild(name)) {
			String code = GuildYml.getString(name + ".정보.아이템 코드");
			if (code.contains(":")) {
				return Integer.parseInt(code.split(":")[0]);
			} else return Integer.parseInt(code);
		} return 0;
	}
	
	public static int getInfoCodeData(String name) {
		if (isGuild(name)) {
			String code = GuildYml.getString(name + ".정보.아이템 코드");
			if (code.contains(":")) {
				return Integer.parseInt(code.split(":")[1]);
			} else return 0;
		} return 0;
	}
	
	public static String getInfoMessage(String name) {
		if (isGuild(name)) {
			return GuildYml.getString(name + ".정보.상태 메세지");
		} return null;
	}
	
	public static int getUserInteger(String name) {
		if (isGuild(name)) {
			return GuildYml.getList(name + ".유저 목록").size();
		} return -1;
	}
	
	public static List<String> getInfoLore(String name) {
		if (isGuild(name)) {
			return GuildYml.getList(name + ".정보.길드 설명");
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
			return GuildYml.getList(name + ".유저 목록");
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
			GuildYml.setList(name + ".유저 목록", list);
			main.GuildUserList.put(name, GuildAPI.getUserOrg(name));
		}
	}
	
	public static void setInfoMessage(String name, String message) {
		if (isGuild(name)) {
			GuildYml.setString(name + ".정보.상태 메세지", message);
		}
	}
	
	public static void setInfoLore(String name, List<String> list) {
		if (isGuild(name)) {
			GuildYml.setList(name + ".정보.길드 설명", list);
		}
	}
	
	public static int getClassAmount(String name, String Class) {
		if (isGuild(name)) {
			int num = 0;
			if (Class.equalsIgnoreCase("유저")) {
				for (String str : getUser(name)) {
					if (str.contains("유저")) num++;
				} return num;
			}
			
			else if (Class.equalsIgnoreCase("정회원")) {
				for (String str : getUser(name)) {
					if (str.contains("정회원")) num++;
				} return num;
			}
			
			else if (Class.equalsIgnoreCase("디자이너")) {
				for (String str : getUser(name)) {
					if (str.contains("디자이너")) num++;
				} return num;
			}

			else if (Class.equalsIgnoreCase("스탭")) {
				for (String str : getUser(name)) {
					if (str.contains("스탭")) num++;
				} return num;
			}
		} return -1;
	}
	
	// 유저 = 1, 정회원 = 2, 디자이너 = 3, 스탭 = 4
	public static int getClassConfig(String name, int upgrade, int num) {
		if (isGuild(name)) {
			List<String> list = GuildConfig.getGuildList("길드 업그레이드");
			String str = list.get(upgrade + 1);
			return Integer.parseInt(str.split(" ")[num + 1]);
		} return -1;
	}
	
	public static int getMaxUser(String name) {
		if (isGuild(name)) {
			int upgrade = GuildAPI.getUpGrade(name);
			List<String> list = GuildConfig.getGuildList("길드 업그레이드");
			String str = list.get(upgrade + 1);
			return Integer.parseInt(str.split(" ")[2]);
		} return -1;
	}
	
	public static void setExpStat(String name) {
		if (isGuild(name)) {
			GuildYml.setBoolean(name + ".스텟.경험치 활성화", !isExpStat(name));
		}
	}
	
	public static void setPlantStat(String name) {
		if (isGuild(name)) {
			GuildYml.setBoolean(name + ".스텟.농사 활성화", !isPlantStat(name));
		}
	}
	
	public static void setMineStat(String name) {
		if (isGuild(name)) {
			GuildYml.setBoolean(name + ".스텟.채광 활성화", !isMineStat(name));
		}
	}
	
	public static boolean isExpStat(String name) {
		if (isGuild(name)) {
			return GuildYml.getBoolean(name + ".스텟.경험치 활성화");
		} return false;
	}
	
	public static boolean isPlantStat(String name) {
		if (isGuild(name)) {
			return GuildYml.getBoolean(name + ".스텟.농사 활성화");
		} return false;
	}
	
	public static boolean isMineStat(String name) {
		if (isGuild(name)) {
			return GuildYml.getBoolean(name + ".스텟.채광 활성화");
		} return false;
	}
	
	public static int getExpStat(String name) {
		if (isGuild(name)) {
			return GuildYml.getInt(name + ".스텟.경험치");
		} return -1;
	}
	
	public static int getPlantStat(String name) {
		if (isGuild(name)) {
			return GuildYml.getInt(name + ".스텟.농사");
		} return -1;
	}
	
	public static int getMineStat(String name) {
		if (isGuild(name)) {
			return GuildYml.getInt(name + ".스텟.채광");
		} return -1;
	}
	
	public static int getMaxExpStat(String name) {
		if (isGuild(name)) {
			int i = GuildAPI.getUpGrade(name) + 1;
			return Integer.parseInt(GuildConfig.getGuildList("길드 업그레이드").get(i).split(" ")[6]);
		} return -1;
	}
	
	public static int getMaxPlantStat(String name) {
		if (isGuild(name)) {
			int i = GuildAPI.getUpGrade(name) + 1;
			return Integer.parseInt(GuildConfig.getGuildList("길드 업그레이드").get(i).split(" ")[7]);
		} return -1;
	}
	
	public static int getMaxMineStat(String name) {
		if (isGuild(name)) {
			int i = GuildAPI.getUpGrade(name) + 1;
			return Integer.parseInt(GuildConfig.getGuildList("길드 업그레이드").get(i).split(" ")[8]);
		} return -1;
	}
	
	public static void setExpStat(String name, int amount) {
		if (isGuild(name)) {
			GuildYml.setInt(name + ".스텟.경험치", amount);
		}
	}
	
	public static String getPlayerChat(Player p) {
		return PlayerYml.getInfoString(p.getName(), "채팅 모드");
	}
	
	public static void setPlayerChat(Player p, String mode) {
		PlayerYml.setInfoString(p, "채팅 모드", mode);
	}
	
	public static String getJoinType(String name) {
		if (isGuild(name)) {
			switch (GuildYml.getInt(name + ".가입 타입")) {
			case 0:
				return "공개"; 
			case 1:
				return "신청서"; 
			case 2:
				return "비공개";
			}
		} return null;
	}
	
	// 0 - 공개, 1 - 신청서, 2 - 비공개
	public static void setJoinType(String name, int amount) {
		if (isGuild(name)) {
			GuildYml.setInt(name + ".가입 타입", amount);
		}
	}
	
	public static List<String> getJoinList(String name) {
		return GuildYml.getList(name + ".가입 신청 목록");
	}
	
	public static void addJoinList(String name, String player) {
		if (isGuild(name)) {
			List<String> list = getJoinList(name);
			if (!list.contains(player)) list.add(player);
			GuildYml.setList(name + ".가입 신청 목록", list);
		}
	}
	
	public static void subJoinList(String name, String player) {
		if (isGuild(name)) {
			List<String> list = getJoinList(name);
			if (list.contains(player)) list.remove(player);
			GuildYml.setList(name + ".가입 신청 목록", list);
		}
	}
	
	public static void setPlantStat(String name, int amount) {
		if (isGuild(name)) {
			GuildYml.setInt(name + ".스텟.농사", amount);
			main.GuildPlantStat.put(name, amount);
		}
	}
	
	public static void setMineStat(String name, int amount) {
		if (isGuild(name)) {
			GuildYml.setInt(name + ".스텟.채광", amount);
			main.GuildMineStat.put(name, amount);
		}
	}
	
	public static void addExpStat(String name, int amount) {
		if (isGuild(name)) {
			int num = getExpStat(name) + amount;
			GuildYml.setInt(name + ".스텟.경험치", num);
		}
	}
	
	public static void addPlantStat(String name, int amount) {
		if (isGuild(name)) {
			int num = getPlantStat(name) + amount;
			GuildYml.setInt(name + ".스텟.농사", num);
			main.GuildPlantStat.put(name, num);
		}
	}
	
	public static void addMineStat(String name, int amount) {
		if (isGuild(name)) {
			int num = getMineStat(name) + amount;
			GuildYml.setInt(name + ".스텟.채광", num);
			main.GuildMineStat.put(name, num);
		}
	}
	
	public static ItemStack getInfoItem(String name) {
		if (isGuild(name)) {
			ItemStack item = new MaterialData(getInfoCode(name), (byte) getInfoCodeData(name)).toItemStack(1);
			ItemMeta item_Meta = item.getItemMeta();
			List<String> list = new ArrayList<String>();
			list.add("§7상태 메세지: §f" + getInfoMessage(name));
			list.add("§7길드 포인트: §f" + getPoint(name));
			list.add("§7길드 유저 수: §f" + getUserInteger(name));
			list.add("§7길드 업그레이드 단계: §f" + getUpGrade(name));
			for (String str : getInfoLore(name)) list.add(str);
			item_Meta.setLore(list);
			item_Meta.setDisplayName("§6" + name);
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
		list.add("§7이 농작물은 요리 전용으로,");
		list.add("§7심거나 판매하실 수 없습니다.");
		list.add("§f채집장 §7에서만 얻을 수 있습니다.");
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
