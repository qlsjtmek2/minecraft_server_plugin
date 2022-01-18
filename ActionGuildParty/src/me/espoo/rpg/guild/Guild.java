package me.espoo.rpg.guild;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
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

public class Guild {
	public static HashMap<String, Guild> players = new HashMap<String, Guild>();
	public static HashMap<String, Guild> guilds = new HashMap<String, Guild>();
	public static HashMap<String, Integer> guildpoint = new HashMap<String, Integer>();
    private String name;
    private String manager;
    private List<String> list;
    private List<String> joinlist;
    private String jointype;
    private int upgrade;
    private int point;
    
    private String itemcode;
    private String statmessage;
    private List<String> info;
    
    private int exp;
    private int plant;
    private int mine;
    private boolean isexp;
    private boolean isplant;
    private boolean ismine;
    
    private boolean isisland;
    private int chest;
    private int build;
    private boolean pvp;

    private String managerlastjoin;
    private String guildcreateday;
    
    private String warguild;
    private boolean iswarsurrender;
    
    public Guild(String name, String manager) {
		Calendar cal = Calendar.getInstance();
		StringBuilder str = new StringBuilder();
		str.append(cal.get(Calendar.MONTH) + 1);
		str.append(",");
		str.append(cal.get(Calendar.DATE));
		
		StringBuilder st = new StringBuilder();
		st.append(cal.get(Calendar.YEAR));
		st.append(",");
		st.append(cal.get(Calendar.MONTH) + 1);
		st.append(",");
		st.append(cal.get(Calendar.DATE));
		
        List<String> list = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
		list.add("§f새로 창설한 " + name + " 길드 입니다.");
		list.add("§f많은 관심과 사랑 부탁합니다.");
		list2.add(manager + ",매니저");
		
		this.name = name;
		this.manager = manager;
		this.list = list2;
		this.joinlist = new ArrayList<String>();
		this.jointype = "공개";
		this.upgrade = 0;
		this.point = 0;
		
		this.itemcode = "323";
		this.statmessage = "§f상태 메세지가 존재하지 않습니다.";
		this.info = list;
		
		this.exp = 0;
		this.plant = 0;
		this.mine = 0;
		this.isexp = true;
		this.isplant = true;
		this.ismine = true;
		
		this.isisland = false;
		this.chest = 0;
		this.build = 0;
		this.pvp = false;
		
		this.managerlastjoin = str.toString();
		this.guildcreateday = st.toString();

		this.warguild = null;
		this.iswarsurrender = false;
		
		this.saveGuildData();
    }
    
    public void saveGuildData() {
    	File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				GuildYml.CreateGuildYml(f, folder, config);
			}
			
			config.set(name + ".매니저", manager);
			config.set(name + ".매니저 마지막 접속시간", managerlastjoin);
			config.set(name + ".길드 생성된 날짜", guildcreateday);
			config.set(name + ".업그레이드 단계", upgrade);
			config.set(name + ".가입 타입", jointype);
			config.set(name + ".정보.아이템 코드", itemcode);
			config.set(name + ".전쟁 길드", warguild);
			config.set(name + ".상대 길드 항복 요청", iswarsurrender);
			config.set(name + ".정보.상태 메세지", statmessage);
			config.set(name + ".정보.길드 설명", info);
			config.set(name + ".스텟.경험치", exp);
			config.set(name + ".스텟.농사", plant);
			config.set(name + ".스텟.채광", mine);
			config.set(name + ".스텟.경험치 활성화", isexp);
			config.set(name + ".스텟.농사 활성화", isplant);
			config.set(name + ".스텟.채광 활성화", ismine);
			config.set(name + ".부동산.존재 여부", isisland);
			config.set(name + ".부동산.상자화로권한", chest);
			config.set(name + ".부동산.설치제거권한", build);
			config.set(name + ".부동산.PvP여부", pvp);
			config.set(name + ".유저 목록", list);
			config.set(name + ".가입 신청 목록", joinlist);
			config.save(f);
			
			if (!players.containsKey(manager)) 
				players.put(manager, this);
			if (!guilds.containsKey(name)) 
				guilds.put(name, this);
			if (!guildpoint.containsKey(name)) 
				guildpoint.put(name, point);
			if (!GuildYml.getGuildList().contains(name)) 
				GuildYml.pluGuildList(name);
			else {
				guildpoint.remove(name);
				guildpoint.put(name, point);
			}
			
			GuildYml.sortGuildPoint();
			List<String> list = new ArrayList<String>();
			List<Integer> list2 = new ArrayList<Integer>(guildpoint.values());
			list.addAll(guildpoint.keySet());
			GuildYml.setGuildList(list);
			GuildYml.setPointList(list2);
		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
    }
    
    public void deleteGuildData() {
    	File f = new File("plugins/ActionGuildParty/Guild.yml");
		File folder = new File("plugins/ActionGuildParty");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				GuildYml.CreateGuildYml(f, folder, config);
			}
			
			OfflinePlayer op = Bukkit.getOfflinePlayer(manager);
	        
	        if (op != null) {
	        	PlayerYml.setInfoString(op, "가입된 길드", null);
	    		if (players.containsKey(op.getName()))
	    			players.remove(op.getName());
		        PlayerInfo pi = uSkyBlock.getInstance().readPlayerFile(op.getName());
		        
		        if (pi != null) {
		        	if (pi.getIslandLocation() != null) {
		        		uSkyBlock.getInstance().deletePlayerIsland(op.getName());
		        	}
		        }
	        }
	        
            List<String> list = config.getStringList(name);
            list.clear();
			config.set(name, list);
			config.save(f);
			

			if (guildpoint.containsKey(name))
				guildpoint.remove(name);
			if (guilds.containsKey(name))
				guilds.remove(name);
			
			GuildYml.delGuildList(name);
		} catch (IOException ioex) {
			System.out.println("[ActionGuildParty] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
    }
    
    public void changeGuildManager(String newreader) {
		String userClass = getUserClass(newreader);
		String manager = this.manager;
		setUserClass(this.manager, userClass);
		setUserClass(newreader, "매니저");
		this.manager = newreader;
        uSkyBlock.getInstance().transferIsland(newreader, manager);
    }
    
	public String getUserClass(String user) {
		for (String str : list) {
			if (str.split(",")[0].equalsIgnoreCase(user)) {
				return str.split(",")[1];
			}
		} return null;
	}
	
	public void setUserClass(String user, String Class) {
		List<String> list = this.list; int num = 0;
		for (String str : this.list) {
			if (str.split(",")[0].equalsIgnoreCase(user)) {
				list.set(num, user + "," + Class);
			} num++;
		} this.list = list;
	}
	
	public void addUser(Player p) {
		PlayerYml.setInfoString(p, "가입된 길드", name);
		List<String> list = this.list;
		list.add(p.getName() + ",유저");
		this.list = list;
		if (players.containsKey(p.getName()))
			players.remove(p.getName());
		players.put(p.getName(), this);
		
		if (!uSkyBlock.getInstance().hasParty(this.manager))
        {
			PlayerInfo pi = (PlayerInfo) uSkyBlock.getInstance().getActivePlayers().get(p.getName());
			if (pi.getHasIsland())
	            uSkyBlock.getInstance().deletePlayerIsland(p.getName());
			IslandCommand is = new IslandCommand();
	        is.addPlayertoParty(p.getName(), this.manager);
	        is.addPlayertoParty(this.manager, this.manager);
        } else {
			PlayerInfo pi = (PlayerInfo) uSkyBlock.getInstance().getActivePlayers().get(p.getName());
			if (pi.getHasIsland())
	            uSkyBlock.getInstance().deletePlayerIsland(p.getName());
			IslandCommand is = new IslandCommand();
	        is.addPlayertoParty(p.getName(), this.manager);
        }
	}
	
	public void subUser(OfflinePlayer pl) {
		PlayerYml.setInfoString(pl, "가입된 길드", null);
		if (pl.isOnline())
			((Player) pl).closeInventory();
		List<String> list = this.list;
		for (String str : this.list)
			if (str.split(",")[0].equalsIgnoreCase(pl.getName()))
				list.remove(str);
		if (players.containsKey(pl.getName()))
			players.remove(pl.getName());
		
		this.list = list;
        if (uSkyBlock.getInstance().hasParty(pl.getName()))
        {
    	    PlayerInfo pi = (PlayerInfo)uSkyBlock.getInstance().getActivePlayers().get(pl.getName());
    		
    		String tempLeader = this.manager;
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
	
    public String getName() {
    	return this.name;
    }
	
    public String getManager() {
    	return this.manager;
    }
	
    public void setManager(String manager) {
    	this.manager = manager;
    }
    
    public List<String> getUsers() {
    	return this.list;
    }
    
	public List<String> getUserOrg() {
		List<String> list = new ArrayList<String>();
		for (String str : this.list) {
			list.add(str.split(",")[0]);
		} return list;
	}
	
	public List<Player> getOnLineUser() {
		List<Player> list = new ArrayList<Player>();
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			for (String str : this.list) {
				if (p.getName().equalsIgnoreCase(str)) {
					list.add(p);
				}
			}
		} return list;
	}
    
    public List<String> getJoinUsers() {
    	return this.joinlist;
    }
	
    public String getJoinType() {
    	return this.jointype;
    }
	
    public void setJoinType(String jointype) {
    	this.jointype = jointype;
    }
	
    public int getUpgrade() {
    	return this.upgrade;
    }
	
    public void setUpgrade(int upgrade) {
    	this.upgrade = upgrade;
    }
	
    public int getPoint() {
    	return this.point;
    }
	
    public void setPoint(int point) {
    	this.point = point;
    }
	
    public String getInfo_ItemCode() {
    	return this.itemcode;
    }
	
    public void setInfo_ItemCode(String itemcode) {
    	this.itemcode = itemcode;
    }
    
	public int getInfoCode() {
		String code = itemcode;
		if (code.contains(":")) {
			return Integer.parseInt(code.split(":")[0]);
		} else return Integer.parseInt(code);
	}
	
	public int getInfoCodeData() {
		String code = itemcode;
		if (code.contains(":")) {
			return Integer.parseInt(code.split(":")[1]);
		} else return 0;
	}
	
    public String getInfo_StatMessage() {
    	return this.statmessage;
    }
	
    public void setInfo_StatMessage(String statmessage) {
    	this.statmessage = statmessage;
    }
	
    public List<String> getInfo_Lore() {
    	return this.info;
    }
	
    public void setInfo_Lore(List<String> info) {
    	this.info = info;
    }
    
    public int getStat_Exp() {
    	return this.exp;
    }
	
    public void setStat_Exp(int exp) {
    	this.exp = exp;
    }
    
    public int getStat_Plant() {
    	return this.plant;
    }
	
    public void setStat_Plant(int plant) {
    	this.plant = plant;
    }
    
    public int getStat_Mine() {
    	return this.mine;
    }
	
    public void setStat_Mine(int mine) {
    	this.mine = mine;
    }
    
    public boolean getStat_IsExp() {
    	return this.isexp;
    }
	
    public void setStat_IsExp(boolean isexp) {
    	this.isexp = isexp;
    }
    
    public boolean getStat_IsPlant() {
    	return this.isplant;
    }
	
    public void setStat_IsPlant(boolean isplant) {
    	this.isplant = isplant;
    }
    
    public boolean getStat_IsMine() {
    	return this.ismine;
    }
	
    public void setStat_IsMine(boolean ismine) {
    	this.ismine = ismine;
    }
    
    public boolean getIsland_Boolean() {
    	return this.isisland;
    }
	
    public void setIsland_Boolean(boolean isisland) {
    	this.isisland = isisland;
    }
    
    public int getIsland_Chest() {
    	return this.chest;
    }
	
    public void setIsland_Chest(int chest) {
    	this.chest = chest;
    }
    
    public int getIsland_Build() {
    	return this.build;
    }
	
    public void setIsland_Build(int build) {
    	this.build = build;
    }
    
    public boolean getIsland_PVP() {
    	return this.pvp;
    }
	
    public void setIsland_PVP(boolean pvp) {
    	this.pvp = pvp;
    }
    
    public String getCreateDay() {
    	return this.guildcreateday;
    }
	
    public String getManagerLastJoin() {
    	return this.managerlastjoin;
    }
	
    public void setManagerLastJoin(String managerlastjoin) {
    	this.managerlastjoin = managerlastjoin;
    }
	
    public String getWar_Guild() {
    	return this.warguild;
    }
	
    public void setWar_Guild(String warguild) {
    	this.warguild = warguild;
    }
	
    public boolean getWar_IsSurrender() {
    	return this.iswarsurrender;
    }
	
    public void setWar_IsSurrender(boolean iswarsurrender) {
    	this.iswarsurrender = iswarsurrender;
    }
    
	public ItemStack getInfoItem() {
		ItemStack item = new MaterialData(getInfoCode(), (byte) getInfoCodeData()).toItemStack(1);
		ItemMeta item_Meta = item.getItemMeta();
		List<String> list = new ArrayList<String>();
		list.add("§7상태 메세지: §f" + this.statmessage);
		list.add("§7길드 포인트: §f" + this.point);
		list.add("§7길드 유저 수: §f" + this.list.size());
		list.add("§7길드 업그레이드 단계: §f" + this.upgrade);
		item_Meta.setLore(this.info);
		item_Meta.setDisplayName("§6" + name);
		item.setItemMeta(item_Meta);
		return item;
	}
	
	public int getYear() {
		return Integer.parseInt(this.guildcreateday.split(",")[0]);
	}
	
	public int getMonth() {
		return Integer.parseInt(this.guildcreateday.split(",")[1]);
	}
	
	public int getDate() {
		return Integer.parseInt(this.guildcreateday.split(",")[2]);
	}
	
	public int getMaxExpStat() {
		int i = upgrade + 1;
		return Integer.parseInt(main.guildConfig.get(i).split(" ")[6]);
	}
	
	public int getMaxPlantStat() {
		int i = upgrade + 1;
		return Integer.parseInt(main.guildConfig.get(i).split(" ")[7]);
	}
	
	public int getMaxMineStat() {
		int i = upgrade + 1;
		return Integer.parseInt(main.guildConfig.get(i).split(" ")[8]);
	}
}
