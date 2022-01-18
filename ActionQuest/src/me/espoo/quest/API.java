package me.espoo.quest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.espoo.quest.yml.PlayerYml;
import think.rpgitems.item.RPGItem;
import to.oa.tpsw.rpgexpsystem.api.Rpg;
import to.oa.tpsw.rpgexpsystem.api.RpgCalculValue;
import to.oa.tpsw.rpgexpsystem.api.RpgPlayer;

public class API {
	public static String replaceAllColors(String message) {
        message = message.replaceAll("&1", "§1");
        message = message.replaceAll("&2", "§2");
        message = message.replaceAll("&3", "§3");
        message = message.replaceAll("&4", "§4");
        message = message.replaceAll("&5", "§5");
        message = message.replaceAll("&6", "§6");
        message = message.replaceAll("&7", "§7");
        message = message.replaceAll("&8", "§8");
        message = message.replaceAll("&9", "§9");
        message = message.replaceAll("&0", "§0");
        message = message.replaceAll("&a", "§a");
        message = message.replaceAll("&b", "§b");
        message = message.replaceAll("&c", "§c");
        message = message.replaceAll("&d", "§d");
        message = message.replaceAll("&e", "§e");
        message = message.replaceAll("&f", "§f");
        message = message.replaceAll("&k", "§k");
        message = message.replaceAll("&l", "§l");
        message = message.replaceAll("&m", "§m");
        message = message.replaceAll("&n", "§n");
        message = message.replaceAll("&o", "§o");
        message = message.replaceAll("&r", "§r");
        return message;
	}
	
	public static String getFinalArg(String[] args, int start) {
		StringBuilder bldr = new StringBuilder();
	    for (int i = start; i < args.length; i++) {
	    	if (i != start) {
	    		bldr.append(" ");
	    	} bldr.append(args[i]);
	    } return bldr.toString();
	}
	
	public static String replaceAllNull(String message) {
        message = message.replaceAll("_", " ");
		return message;
	}
	
	public static String replaceAllNullTo_(String message) {
        message = message.replaceAll(" ", "_");
		return message;
	}
	
	public static String CharToString(char c) {
		Character cr = new Character(c);
		return cr.toString();
	}
	
	public static boolean isMainQuestIng(Player p) {
        return PlayerYml.getBoolean(p, "메인 퀘스트.진행");
	}
	
	public static List<String> getMainPlayerYmlTask(Player p) {
        return PlayerYml.getList(p, "메인 퀘스트.과제");
	}
	
	public static void setMainPlayerYmlTask(Player p, List<String> list) {
        PlayerYml.setList(p, "메인 퀘스트.과제", list);
	}
	
	public static List<String> SetingTask(List<String> list) {
		List<String> Setlist = new ArrayList<String>();
		if (list == null || list.isEmpty()) return Setlist;
		for (String str : list) {
			if (str.contains("epicboss")) Setlist.add("epicboss " + str.split(" ")[1].split(",")[0] + ",0," + str.split(" ")[1].split(",")[1] + " false");
			if (str.contains("talk")) Setlist.add("talk " + str.split(" ")[1].split(",")[0] + " false");
			if (str.contains("command")) Setlist.add("command " + str.split(" ")[1].split(",")[0] + " false");
			if (str.contains("chat")) Setlist.add("chat " + str.split(" ")[1].split(",")[0] + " false");
			if (str.contains("playerkill")) Setlist.add("playerkill 0," + str.split(" ")[1].split(",")[0] + " false");
			if (str.contains("monsterkill")) Setlist.add("monsterkill 0," + str.split(" ")[1].split(",")[0] + " false");
		}
		
        return Setlist;
	}
	
	public static boolean isMainNextFinish(Player p) {
		if (API.getMainStep(p) <= main.mainMaxStep) {
			return false;
		} return true;
	}
	
	public static int getMainStep(Player p) {
		return PlayerYml.getInt(p, "메인 퀘스트.단계");
	}
	
	public static void addMainQuest(Player p) {
        int step = API.getMainStep(p); step++;
        PlayerYml.setInt(p, "메인 퀘스트.단계", step);
        PlayerYml.setList(p, "메인 퀘스트.과제", Arrays.asList());
        PlayerYml.setBoolean(p, "메인 퀘스트.진행", false);
	}
	
	public static String getMainNPCName() {
		return "§2NPC §7| §f튜토리얼";
	}
	
	public static String getMainQuestName(Player p) {
        if (!API.isMainNextFinish(p)) {
        	String str = QuestList.ListName.get(API.getMainStep(p) - 1);
        	return str;
        } else return null;
	}
	
	public static List<String> getMainReward(Player p) {
        if (!API.isMainNextFinish(p)) {
        	List<String> str = QuestList.ListReward.get(API.getMainStep(p) - 1);
        	return str;
        } else return null;
	}
	
	public static List<String> getMainTask(Player p) {
        if (!API.isMainNextFinish(p)) {
        	List<String> str = QuestList.ListTask.get(API.getMainStep(p) - 1);
        	return str;
        } else return null;
	}
	
	public static List<String> getMainTalk(Player p) {
        if (!API.isMainNextFinish(p)) {
        	List<String> str = QuestList.ListTalk.get(API.getMainStep(p) - 1);
        	return str;
        } else return null;
	}
	
	public static List<String> TalkStringToTalkList(Player p, String talk) {
    	List<String> list = new ArrayList<String>();
    	if (talk.contains(",")) {
    		for (String str : talk.split(",")) {
    			list.add(API.replaceAllNull(str));
    		}
    	} else {
    		list.add(API.replaceAllNull(talk));
    	}
    	
    	return list;
	}
	
	public static List<String> getMainRewardColor(Player p) {
        if (!API.isMainNextFinish(p)) {
        	List<String> list = QuestList.ListReward.get(API.getMainStep(p) - 1);
        	List<String> Setlist = new ArrayList<String>();
        	
    		if (list == null || list.isEmpty()) return Setlist;
        	for (String str : list) {
        		if (str.contains("rpgitem")) Setlist.add("§7- §f\"" + str.split(" ")[1].split(",")[0] + "\" 아이템 " + str.split(" ")[1].split(",")[1] + "개 지급");
        		else if (str.contains("money")) Setlist.add("§7- §f\"" + str.split(" ")[1] + "원\" 지급");
        		else if (str.contains("exp")) Setlist.add("§7- §f경험치 \"" + str.split(" ")[1] + "\" 지급");
        		else if (str.contains("item")) {
        			String[] strr = str.split(" ")[1].split(",");
        			String code = strr[0];
        			String name = null;
        			String num = strr[1];
        			if (strr.length >= 3) {
        				name = strr[2];
        			}
        			
        			if (name != null) {
        				Setlist.add("§7- §f\"" + API.replaceAllNull(API.replaceAllColors(name)) + "§f\" 아이템 " + num + "개 지급");
        			} else {
        				ItemStack item = null;
        				if (str.contains(":")) item = new MaterialData(Integer.parseInt(code.split(":")[0]), Byte.parseByte(code.split(":")[1])).toItemStack(1);
        				else item = new MaterialData(Integer.parseInt(code.split(":")[0]), (byte) 0).toItemStack(1);
        				
        				Setlist.add("§7- §f\"" + item.getType().toString() + "§f\" 아이템 " + num + "개 지급");
        			}
    			}
    			
        		else if (str.contains("give")) {
        			String[] strr = str.split(" ")[1].split(",");
        			String code = strr[0];
        			String num = strr[1];
        			
        			ItemStack item = null;
    				if (str.contains(":")) item = new MaterialData(Integer.parseInt(code.split(":")[0]), Byte.parseByte(code.split(":")[1])).toItemStack(1);
    				else item = new MaterialData(Integer.parseInt(code.split(":")[0]), (byte) 0).toItemStack(1);
    				
    				Setlist.add("§7- §f\"" + item.getType().toString() + "§f\" 아이템 " + num + "개 지급");
    			}
        	}
        	return Setlist;
        } else return null;
	}
	
	public static List<String> getMainTaskColor(Player p) {
        if (!API.isMainNextFinish(p)) {
        	List<String> list = QuestList.ListTask.get(API.getMainStep(p) - 1);
        	List<String> Setlist = new ArrayList<String>();

    		if (list == null || list.isEmpty()) return Setlist;
        	for (String str : list) {
    			if (str.contains("epicboss")) Setlist.add("§7- §f\"" + str.split(" ")[1].split(",")[0] + "\" 처치 §f§l(0/" + str.split(" ")[1].split(",")[1] + ")");
    			else if (str.contains("talk")) Setlist.add("§7- §f\"" + str.split(" ")[1] + "\" 와/과 대화 §f§l(0/1)");
    			else if (str.contains("command")) Setlist.add("§7- §f\"/" + API.replaceAllNull(str.split(" ")[1]) + "\" 명령어 입력 §f§l(0/1)");
    			else if (str.contains("chat")) Setlist.add("§7- §f\"" + API.replaceAllNull(str.split(" ")[1]) + "\" 채팅 입력 §f§l(0/1)");
    			else if (str.contains("playerkill")) Setlist.add("§7- §f플레이어 죽이기 §f§l(0/§f§l" + str.split(" ")[1] + ")");
    			else if (str.contains("monsterkill")) Setlist.add("§7- §f괴인 죽이기 §f§l(0/§f§l" + str.split(" ")[1] + ")");
    			else if (str.contains("rpgitem")) {
    				String[] strr = str.split(" ");
    				String rpgitem = strr[1].split(",")[0];
        			Setlist.add("§7- §f\"" + rpgitem + "§f\" 아이템 획득 §f§l(0/" + strr[1].split(",")[1] + ")");
    			}
    			
    			else if (str.contains("item")) {
    				if (str.split(" ")[1].split(",").length == 3) {
    					Setlist.add("§7- §f\"" + API.replaceAllNull(API.replaceAllColors(str.split(" ")[1].split(",")[2])) + "\" 아이템 획득 §f§l(0/" + str.split(" ")[1].split(",")[1] + ")");
    				} else {
            			String[] strr = str.split(" ")[1].split(",");
            			String code = strr[0];
            			String name = null;
            			if (strr.length == 3) {
            				name = strr[2];
            			}
            			
            			if (strr.length == 3) {
            				Setlist.add("§7- §f\"" + API.replaceAllNull(API.replaceAllColors(name)) + "§f\" 아이템 획득 §f§l(0/" + strr[1] + ")");
            			} else {
            				ItemStack item = null;
            				if (str.contains(":")) item = new MaterialData(Integer.parseInt(code.split(":")[0]), Byte.parseByte(code.split(":")[1])).toItemStack(1);
            				else item = new MaterialData(Integer.parseInt(code.split(":")[0]), (byte) 0).toItemStack(1);
            				
            				Setlist.add("§7- §f\"" + item.getType().toString() + "§f\" 아이템 획득 §f§l(0/" + strr[1] + ")");
            			}
    				}
    			}
        	}
        	
        	return Setlist;
        } else return null;
	}
	
	public static List<String> getMainPlayerTaskingColor(Player p) {
        if (!API.isMainNextFinish(p)) {
        	List<String> list1 = API.getMainPlayerYmlTask(p);
        	List<String> list2 = API.getMainTask(p);
        	List<String> Setlist = new ArrayList<String>();

    		if (list2 == null || list2.isEmpty()) return Setlist;
        	for (String str : list1) {
    			if (str.contains("epicboss")) Setlist.add("§7- §f\"" + str.split(" ")[1].split(",")[0] + "\" 처치 §f§l(" + str.split(" ")[1].split(",")[1] + "/" + str.split(" ")[1].split(",")[2] + ")");
    			else if (str.contains("playerkill")) Setlist.add("§7- §f플레이어 죽이기 §f§l(" + str.split(" ")[1].split(",")[0] + "/§f§l" + str.split(" ")[1].split(",")[1] + ")");
    			else if (str.contains("monsterkill")) Setlist.add("§7- §f괴인 죽이기 §f§l(" + str.split(" ")[1].split(",")[0] + "/§f§l" + str.split(" ")[1].split(",")[1] + ")");
    			else if (str.contains("talk")) { 
    				String[] strr = str.split(" ");
    				if (strr[2].equalsIgnoreCase("true")) Setlist.add("§7- §f\"" + strr[1] + "\" 와/과 대화 §f§l(1/1)");
    				else Setlist.add("§7- §f\"" + strr[1] + "\" 와/과 대화 §f§l(0/1)");
    			}
    			
    			else if (str.contains("command")) {
    				String[] strr = str.split(" ");
    				if (strr[2].equalsIgnoreCase("true")) Setlist.add("§7- §f\"/" + API.replaceAllNull(strr[1]) + "\" 명령어 입력 §f§l(1/1)");
    				else Setlist.add("§7- §f\"/" + API.replaceAllNull(strr[1]) + "\" 명령어 입력 §f§l(0/1)");
    			}
    			
    			else if (str.contains("chat")) {
    				String[] strr = str.split(" ");
    				if (strr[2].equalsIgnoreCase("true")) Setlist.add("§7- §f\"" + API.replaceAllNull(strr[1]) + "\" 채팅 입력 §f§l(1/1)");
    				else Setlist.add("§7- §f\"" + API.replaceAllNull(strr[1]) + "\" 채팅 입력 §f§l(0/1)");
    			}
        	}
        	
        	for (String str : list2) {
        		if (str.contains("rpgitem")) {
    				String[] strr = str.split(" ");
    				String rpgitem = strr[1].split(",")[0];
        			int num = API.getPlayerInvItemAmount(p, API.getRpgitemName(rpgitem), API.getRpgitemCode(rpgitem), true);
        			Setlist.add("§7- §f\"" + API.getRpgitemName(rpgitem) + "§f\" 아이템 획득 §f§l(" + num + "/" + strr[1].split(",")[1] + ")");
        		}
        		
        		else if (str.contains("item")) {
        			String[] strr = str.split(" ")[1].split(",");
        			String code = strr[0];
        			String name = null;
        			if (strr.length == 3) {
        				name = strr[2];
        			}
        			
        			int num = API.getPlayerInvItemAmount(p, name, code, false);
        			
        			if (strr.length == 3) {
        				Setlist.add("§7- §f\"" + API.replaceAllNull(API.replaceAllColors(name)) + "§f\" 아이템 획득 §f§l(" + num + "/" + strr[1] + ")");
        			} else {
        				ItemStack item = null;
        				if (str.contains(":")) item = new MaterialData(Integer.parseInt(code.split(":")[0]), Byte.parseByte(code.split(":")[1])).toItemStack(1);
        				else item = new MaterialData(Integer.parseInt(code.split(":")[0]), (byte) 0).toItemStack(1);
        				
        				Setlist.add("§7- §f\"" + item.getType().toString() + "§f\" 아이템 획득 §f§l(" + num + "/" + strr[1] + ")");
        			}
        		}
        	}
        	
        	return Setlist;
        } else return null;
	}
	
	public static String getRpgitemName(String name) {
		try {
            FileInputStream in = null;
            YamlConfiguration config = null;
            try {
                final File f = new File(new File("plugins/RPG Items"), "items.yml");
                in = new FileInputStream(f);
                final byte[] data = new byte[(int)f.length()];
                in.read(data);
                config = new YamlConfiguration();
                final String str = new String(data, "UTF-8");
                config.loadFromString(str);
            } catch (FileNotFoundException ex) {
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e2) {
                e2.printStackTrace();
            } finally {
                try { if (in != null) { in.close(); }
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            } try { if (in != null) { in.close(); }
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            
            ConfigurationSection section = config.getConfigurationSection("items");
            if (section == null) {
                return null;
            }
            
            for (String key : section.getKeys(false)) {
                RPGItem item = new RPGItem(section.getConfigurationSection(key));
                if (item.getName().equalsIgnoreCase(name)) {
                	return ChatColor.stripColor(item.getDisplay());
                }
            } return null;
        } catch (Exception e4) {
            return null;
        }
	}
	
	public static String getRpgitemCode(String name) {
		try {
            FileInputStream in = null;
            YamlConfiguration config = null;
            try {
                final File f = new File(new File("plugins/RPG Items"), "items.yml");
                in = new FileInputStream(f);
                final byte[] data = new byte[(int)f.length()];
                in.read(data);
                config = new YamlConfiguration();
                final String str = new String(data, "UTF-8");
                config.loadFromString(str);
            } catch (FileNotFoundException ex) {
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e2) {
                e2.printStackTrace();
            } finally {
                try { if (in != null) { in.close(); }
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            } try { if (in != null) { in.close(); }
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            
            ConfigurationSection section = config.getConfigurationSection("items");
            if (section == null) {
                return null;
            }
            
            for (String key : section.getKeys(false)) {
                RPGItem item = new RPGItem(section.getConfigurationSection(key));
                if (item.getName().equalsIgnoreCase(name)) {
                	String code = Integer.toString(item.getItem().getId());
                	String date = Integer.toString(item.getDataValue());
                	return code + ":" + date;
                }
            } return null;
        } catch (Exception e4) {
            return null;
        }
	}
	
	public static int getPlayerInvItemAmount(Player p, String name, String codeAdate, boolean rpgitem) {
		final ItemStack[] contents = p.getInventory().getContents();
		int num = 0;
        for (ItemStack item : contents) {
        	if (item != null) {
        		if (name != null) {
                    if (item.hasItemMeta()) {
                    	if (item.getItemMeta().hasDisplayName()) {
                    		if (rpgitem) {
                    			String codeAdate2 = Integer.toString(item.getTypeId()) + ":" + Byte.toString(item.getData().getData());
                        		if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase(API.replaceAllNull(name)) && codeAdate2.equalsIgnoreCase(codeAdate)) {
                        			num += item.getAmount();
                        		}
                    		} else {
                        		String codeAdate2 = Integer.toString(item.getTypeId()) + ":" + Byte.toString(item.getData().getData());
                        		if (item.getItemMeta().getDisplayName().equalsIgnoreCase(API.replaceAllNull(name)) && codeAdate2.equalsIgnoreCase(codeAdate)) {
                        			num += item.getAmount();
                        		}
                    		}
                    	}
                    }	
        		} else {
        			String codeAdate2 = Integer.toString(item.getTypeId()) + ":" + Byte.toString(item.getData().getData());
            		if (codeAdate2.equalsIgnoreCase(codeAdate)) {
            			num += item.getAmount();
            		}
        		}
        	}
        }
		
		return num;
	}
	
	public static boolean isMainQuestInvAir(Player p) {
		if (API.isMainQuestIng(p)) {
			List<String> reward = API.getMainReward(p);
			int num = 0;
			int air = 0;
			
			for (String str : reward) {
        		if (str.split(" ")[0].equalsIgnoreCase("rpgitem")) num++;
        		else if (str.split(" ")[0].equalsIgnoreCase("item")) num++;
        	}
			
			for (ItemStack item : p.getInventory().getContents()) {
				if (item == null || item.getType() == Material.AIR) air++;
			}
			
			if (num > air) return false;
			return true;
		} return false;
	}
	
	public static boolean isMainQuestComplete(Player p) {
        if (API.isMainQuestIng(p)) {
        	List<String> task = API.getMainPlayerYmlTask(p);
        	
        	for (String str : task)
				if (!str.split(" ")[2].equalsIgnoreCase("true")) return false;
        	
        	task = API.getMainTask(p);
        	for (String str : task) {
        		if (str.split(" ")[0].equalsIgnoreCase("rpgitem")) {
        			int num = Integer.parseInt(str.split(" ")[1].split(",")[1]);
        			String rpgitem = str.split(" ")[1].split(",")[0];
        			if (API.getPlayerInvItemAmount(p, API.getRpgitemName(rpgitem), API.getRpgitemCode(rpgitem), true) < num) {
        				return false;
        			}
        		}
        		
        		else if (str.split(" ")[0].equalsIgnoreCase("item")) {
        			String[] strr = str.split(" ")[1].split(",");
        			int num = Integer.parseInt(strr[1]);
        			String code = strr[0];
        			String name = null;
        			if (strr.length == 3) {
        				name = strr[2];
        			}
        			
        			if (API.getPlayerInvItemAmount(p, name, code, false) < num) {
        				return false;
        			}
        		}
        	}
        	
        	return true;
        } return false;
	}
	
	public static List<String> getItemLore(String str) {
		List<String> list = new ArrayList<String>();
		if (str.contains(";")) {
			String[] strr = str.split(";");
			for (String string : strr) {
				list.add(API.replaceAllNull(API.replaceAllColors(string)));
			}
		} else {
			list.add(API.replaceAllNull(API.replaceAllColors(str)));
		}
		
		return list;
	}
	
	public static void mainTakeRpgitemAndItem(Player p) {
		if (API.isMainQuestIng(p)) {
			List<String> task = API.getMainTask(p);
			for (String str : task) {
        		if (str.split(" ")[0].equalsIgnoreCase("rpgitem")) {
        			if (!str.split(" ")[2].equalsIgnoreCase("true")) continue;
        			final ItemStack[] contents = p.getInventory().getContents();
        			int num = Integer.parseInt(str.split(" ")[1].split(",")[1]);
        			String rpgitem = str.split(" ")[1].split(",")[0];
        			
        	        for (ItemStack item : contents) {
        	        	if (item != null) {
        	        		if (rpgitem != null) {
        	                    if (item.hasItemMeta()) {
        	                    	if (item.getItemMeta().hasDisplayName()) {
        	                    		String codeAdate2 = Integer.toString(item.getTypeId()) + ":" + Byte.toString(item.getData().getData());
        	                    		if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase(API.getRpgitemName(rpgitem)) && codeAdate2.equalsIgnoreCase(API.getRpgitemCode(rpgitem))) {
        	                    			if (num <= 0) break;
        	                    			if (item.getAmount() > num) {
        	                    				item.setAmount(item.getAmount() - num);
        	                    				break;
        	                    			}
        	                    			
        	                    			else if (item.getAmount() == num) {
        	                    				item.setAmount(0);
        	                    				break;
        	                    			}
        	                    			
        	                    			else {
        	                    				num -= item.getAmount();
        	                    				item.setAmount(0);
        	                    			}
        	                    		}
        	                    	}
        	                    }	
        	        		}
        	        	}
        	        }
        	        
        	        p.getInventory().setContents(contents);
        		}
        		
        		else if (str.split(" ")[0].equalsIgnoreCase("item")) {
        			if (!str.split(" ")[2].equalsIgnoreCase("true")) continue;
        			final ItemStack[] contents = p.getInventory().getContents();
        			String[] strr = str.split(" ")[1].split(",");
        			int num = Integer.parseInt(strr[1]);
        			String code = strr[0];
        			String name = null;
        			if (strr.length == 3) {
        				name = strr[2];
        			}
        			
        	        for (ItemStack item : contents) {
        	        	if (item != null) {
        	        		if (name != null) {
        	                    if (item.hasItemMeta()) {
        	                    	if (item.getItemMeta().hasDisplayName()) {
        	                    		String codeAdate2 = Integer.toString(item.getTypeId()) + ":" + Byte.toString(item.getData().getData());
        	                    		if (item.getItemMeta().getDisplayName().equalsIgnoreCase(name) && codeAdate2.equalsIgnoreCase(code)) {
        	                    			if (num <= 0) break;
        	                    			if (item.getAmount() > num) {
        	                    				item.setAmount(item.getAmount() - num);
        	                    				break;
        	                    			}
        	                    			
        	                    			else if (item.getAmount() == num) {
        	                    				item.setAmount(0);
        	                    				break;
        	                    			}
        	                    			
        	                    			else {
        	                    				num -= item.getAmount();
        	                    				item.setAmount(0);
        	                    			}
        	                    		}
        	                    	}
        	                    }	
        	        		} else {
        	        			String codeAdate2 = Integer.toString(item.getTypeId()) + ":" + Byte.toString(item.getData().getData());
        	        			if (codeAdate2.equalsIgnoreCase(code)) {
	                    			if (num <= 0) break;
	                    			if (item.getAmount() > num) {
	                    				item.setAmount(item.getAmount() - num);
	                    				break;
	                    			}
	                    			
	                    			else if (item.getAmount() == num) {
	                    				item.setAmount(0);
	                    				break;
	                    			}
	                    			
	                    			else {
	                    				num -= item.getAmount();
	                    				item.setAmount(0);
	                    			}
	                    		}
        	        		}
        	        	}
        	        }
        	        
        	        p.getInventory().setContents(contents);
        		}
        	}
		}
	}
	
	public static void GiveReward(Player p, List<String> reward) {
		if (reward == null || reward.isEmpty()) return;
		for (String str : reward) {
			if (str.split(" ")[0].equalsIgnoreCase("money"))
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + p.getName() + " " + str.split(" ")[1]);
			else if (str.split(" ")[0].equalsIgnoreCase("exp")) {
				RpgPlayer rp = Rpg.getRpgPlayera(p.getName());
				long exp = Long.parseLong(str.split(" ")[1]);
				double scale = Rpg.getDotsecond(rp.getScale());
				long value = (long)(exp * scale);
				rp.addRpgExp(value);
				p.sendMessage("§6당신은 퀘스트를 §c완료 §6하여서 §c" + value + "(" + scale + ")§6만큼 경험치를 받았습니다. §c" + rp.getRpgExp() + "§6/§c" + rp.getRpgNeedExp());
				RpgCalculValue.checkExp(rp);
			}
			
			else if (str.split(" ")[0].equalsIgnoreCase("command")) 
				p.chat("/" + str.split(" ")[1]);
			else if (str.split(" ")[0].equalsIgnoreCase("commandcon")) 
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), API.replaceAllNull(str.split(" ")[1]));
			else if (str.split(" ")[0].equalsIgnoreCase("commandop"))
				API.PlayerOpCommand(p, str.split(" ")[1]);
			else if (str.split(" ")[0].equalsIgnoreCase("rpgitem")) {
				String[] strr = str.split(" ")[1].split(",");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + strr[0] + " give " + p.getName() + " " + strr[1]);
			}
			else if (str.split(" ")[0].equalsIgnoreCase("give")) {
				String[] strr = str.split(" ")[1].split(",");
				p.getInventory().addItem(new MaterialData(Integer.parseInt(strr[0].split(":")[0]), Byte.parseByte(strr[0].split(":")[1])).toItemStack(Integer.parseInt(strr[1])));
			}
			else if (str.split(" ")[0].equalsIgnoreCase("item")) {
				String[] strr = str.split(" ")[1].split(",");
				ItemStack item = new MaterialData(Integer.parseInt(strr[0].split(":")[0]), Byte.parseByte(strr[0].split(":")[1])).toItemStack(Integer.parseInt(strr[1]));
				ItemMeta item_Meta = item.getItemMeta();
				item_Meta.setDisplayName(API.replaceAllColors(strr[2]));
				item_Meta.setLore(API.getItemLore(strr[3]));
				item.setItemMeta(item_Meta);
				p.getInventory().addItem(item);
			}
		}
	}
	
	public static void PlayerOpCommand(Player p, String command) {
        if (p.isOp()) p.performCommand(command);
        else {
            p.setOp(true);
            p.performCommand(command);
        	p.setOp(false);
        	p.setOp(false);
        	p.setOp(false);
        	p.setOp(false);
        	p.setOp(false);
        }
	}
}
