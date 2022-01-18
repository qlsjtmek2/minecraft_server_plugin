package com.linmalu.Data;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import com.linmalu.Main.AutomaticTriggers;

public class Data_ScriptField_Methods
{
	private AutomaticTriggers plugin;
	private HashMap<String, Integer> scriptFields = new HashMap<String, Integer>(0);

	public Data_ScriptField_Methods(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
		scriptFields.put("triggername", 1);
		scriptFields.put("gamehour", 2);
		scriptFields.put("gamemin", 3);
		scriptFields.put("playername", 4); 
		scriptFields.put("displayname", 5);
		scriptFields.put("itemid", 6);
		scriptFields.put("subitemid", 7);
		scriptFields.put("itemname", 8);
		scriptFields.put("triggerloc", 9);
		scriptFields.put("playerloc", 10);
		scriptFields.put("sneaking", 11);
		scriptFields.put("sprinting", 12);
		scriptFields.put("health", 13);
		scriptFields.put("worldname", 14);
		scriptFields.put("biome", 15);
		scriptFields.put("gamemode", 16);
		scriptFields.put("haspermission", 17);
		scriptFields.put("random", 18);
		scriptFields.put("exptotal", 19);
		scriptFields.put("explevel", 20);
		scriptFields.put("playercheck", 21);
		scriptFields.put("itemlore", 22);
		//채팅,커맨드
		scriptFields.put("chatarg", 23);
		scriptFields.put("chatline", 24);
		//죽었을떄
		scriptFields.put("deathbyplayer", 25);
		scriptFields.put("deathname", 26);
		scriptFields.put("killedbyplayer", 27);
		scriptFields.put("killername", 28);
		//블럭
		scriptFields.put("blockid", 29);
		scriptFields.put("blocksub", 30);
		//scriptFields.put("blockname", 31);
		//엔티티스폰이나 상호작용
		scriptFields.put("entityname", 32);
		scriptFields.put("entitbyplayer", 33);
		//공간
		scriptFields.put("areaisenter", 34);
		//돈
		scriptFields.put("hasmoney", 35);
		scriptFields.put("givemoney", 36);
		scriptFields.put("takemoney", 37);
		//일반
		scriptFields.put("areacheck", 38);
		scriptFields.put("opcheck", 39);
		scriptFields.put("flying", 40);
		scriptFields.put("food", 41);
		scriptFields.put("playerlocx", 42);
		scriptFields.put("playerlocy", 43);
		scriptFields.put("playerlocz", 44);
		scriptFields.put("realyear", 45);
		scriptFields.put("realmon", 46);
		scriptFields.put("realdaday", 47);
		scriptFields.put("realhour", 48);
		scriptFields.put("realmin", 49);
		scriptFields.put("realsec", 50);
		scriptFields.put("maxhealth", 51);
		scriptFields.put("blockid", 52);
		scriptFields.put("hashelmet", 53);
		scriptFields.put("haschestplate", 54);
		scriptFields.put("hasleggings", 55);
		scriptFields.put("hasboots", 56);
		//강퇴
		scriptFields.put("kickmsg", 57);
		//레벨
		scriptFields.put("oldlevel", 58);
		scriptFields.put("newlevel", 59);
	}
	public String ScriptField(String args[], String script, Player player, String triggerloc, Data_Sctipt_Runnable scriptData)
	{
		script = plugin.scriptVariables.ScriptVariable(script, player);
		int prefixIndex = script.length();
		int suffixIndex = script.length();
		while(true)
		{
			prefixIndex = script.lastIndexOf("{", prefixIndex - 1);
			suffixIndex = script.indexOf("}", prefixIndex) + 1;
			if(prefixIndex == -1 || suffixIndex == 0)
				break;
			script = script.substring(0, prefixIndex) + ScriptFields(args, script.substring(prefixIndex, suffixIndex), player, triggerloc, scriptData) + script.substring(suffixIndex);
		}
		return script;
	}
	public String ScriptFields(String args[], String script, Player player, String triggerloc, Data_Sctipt_Runnable scriptData)
	{
		String field = null;
		for(String fields : scriptFields.keySet())
		{
			if(script.startsWith("{" + fields + "}") || script.startsWith("{" + fields + ":"))
			{
				field = fields;
				break;
			}
		}
		try{
			String subField[] = script.replace("{" + field + ":", "").replace("{" + field, "").replace("}", "").split(":");
			Server server = plugin.getServer();
			Player subPlayer = plugin.getServer().getPlayerExact(subField[0]);
			switch(scriptFields.get(field))
			{
			case 1://triggername
				return args[0];
			case 2://gamehour
				if(subField[0].equals(""))
					return String.valueOf(((server.getWorld("world").getTime() / 1000) + 6) % 24);
				else if(server.getWorld(subField[0]) != null)
					return String.valueOf(((server.getWorld(subField[0]).getTime() / 1000) + 6) % 24);
				else
					return null;
			case 3://gamemin
				if(subField[0].equals(""))
					return String.valueOf(((server.getWorld("world").getTime() % 1000) * 60 / 1000));
				else if(server.getWorld(subField[0]) != null)
					return String.valueOf(((server.getWorld(subField[0]).getTime() % 1000) * 60 / 1000));
				else
					return null;
			case 4://playername
				if(player != null)
					return player.getName();
				else
					return null;
			case 5://displayname
				if(subField[0].equals(""))
					return player.getDisplayName();
				else if(plugin.getServer().getPlayerExact(subField[0]) != null)
					return plugin.getServer().getPlayerExact(subField[0]).getDisplayName();
				else
					return null;
			case 6://itemid
				if(subField[0].equals(""))
					return String.valueOf(player.getItemInHand().getTypeId());
				else if(subPlayer != null)
					return String.valueOf(subPlayer.getItemInHand().getTypeId());
				else
					return null;
			case 7://subitemid
				if(subField[0].equals(""))
					return String.valueOf(player.getItemInHand().getDurability());
				else if(subPlayer != null)
					return String.valueOf(subPlayer.getItemInHand().getDurability());
				else
					return null;
			case 8://itemname
				if(subField[0].equals(""))
					if(player.getItemInHand().getItemMeta() != null)
					{
						if(player.getItemInHand().getItemMeta().getDisplayName() != null)
							return player.getItemInHand().getItemMeta().getDisplayName();
						return String.valueOf(player.getItemInHand().getType());
					}
					else
						return "손";
				else if(subPlayer != null)
					if(subPlayer.getItemInHand().getItemMeta() != null)
					{
						if(subPlayer.getItemInHand().getItemMeta().getDisplayName() != null)
							return subPlayer.getItemInHand().getItemMeta().getDisplayName();
						return String.valueOf(subPlayer.getItemInHand().getType());
					}
					else
						return "손";
				else
					return null;
			case 9://triggerloc
				return triggerloc;
			case 10://playerloc
				if(subField[0].equals(""))
					return String.valueOf(player.getLocation().getBlockX() + "," + player.getLocation().getBlockY() + "," + player.getLocation().getBlockZ());
				else if(subPlayer != null)
					return String.valueOf(subPlayer.getLocation().getBlockX() + "," + subPlayer.getLocation().getBlockY() + "," + subPlayer.getLocation().getBlockZ());
				else
					return null;
			case 11://sneaking
				if(subField[0].equals(""))
					return String.valueOf(player.isSneaking());
				else if(subPlayer != null)
					return String.valueOf(subPlayer.isSneaking());
				else
					return null;
			case 12://sprinting
				if(subField[0].equals(""))
					return String.valueOf(player.isSprinting());
				else if(subPlayer != null)
					return String.valueOf(subPlayer.isSprinting());
				else
					return null;
			case 13://health
				if(subField[0].equals(""))
					return String.valueOf(player.getHealth());
				else if(subPlayer != null)
					return String.valueOf(subPlayer.getHealth());
				else
					return null;
			case 14://worldname
				if(subField[0].equals(""))
					return player.getWorld().getName();
				else if(subPlayer != null)
					return subPlayer.getWorld().getName();
				else
					return null;
			case 15://biome
				if(subField[0].equals(""))
					return String.valueOf(player.getWorld().getBiome(player.getLocation().getBlockX(), player.getLocation().getBlockZ()));
				else if(subPlayer != null)
					return String.valueOf(subPlayer.getWorld().getBiome(subPlayer.getLocation().getBlockX(), subPlayer.getLocation().getBlockZ()));
				else
					return null;
			case 16://gamemode
				if(subField[0].equals(""))
					return String.valueOf(player.getGameMode().getValue());
				else if(subPlayer != null)
					return String.valueOf(subPlayer.getGameMode().getValue());
				else
					return null;
			case 17://haspermission
				if(subField[0].equals(""))
					return String.valueOf(player.hasPermission(subField[1]));
				else if(subPlayer != null)
					return String.valueOf(subPlayer.hasPermission(subField[1]));
				else
					return null;
			case 18://random
				return String.valueOf(new Random().nextInt(Integer.parseInt(subField[0])));
			case 19://exptotal
				if(subField[0].equals(""))
					return String.valueOf(player.getTotalExperience());
				else if(subPlayer != null)
					return String.valueOf(subPlayer.getTotalExperience());
				else
					return null;
			case 20://explevel
				if(subField[0].equals(""))
					return String.valueOf(player.getLevel());
				else if(subPlayer != null)
					return String.valueOf(subPlayer.getLevel());
				else
					return null;
			case 21://playercheck
				if(subField[0].equals(""))
					return "true";
				else if(subPlayer != null)
					return "true";
				else
					return "false";
			case 22://itemlore
				if(subField[0].equals(""))
					if(player.getItemInHand().getItemMeta().getLore().size() < Integer.parseInt(subField[0]) -1)
						return player.getItemInHand().getItemMeta().getLore().get(Integer.parseInt(subField[0]) -1);
				if(subPlayer != null)
					if(subPlayer.getItemInHand().getItemMeta().getLore().size() < Integer.parseInt(subField[0]) -1)
						return subPlayer.getItemInHand().getItemMeta().getLore().get(Integer.parseInt(subField[0]) -1);
				return null;
			case 23://chatarg
				if(Integer.parseInt(subField[0]) <= (args.length -1))
					return args[Integer.parseInt(subField[0])];
				return null;
			case 24://chatline
				if(scriptData.eventName.equals("command"))
					return plugin.ScriptLine(args, 1);
				return plugin.ScriptLine(args, 0);
			case 25://deathbyplayer
				if(scriptData.eventPlayerDeath != null)
					return "true";
				return "false";
			case 26://deathname
				if(scriptData.eventPlayerDeath != null)
					return ((Player)scriptData.eventPlayerDeath.getEntity()).getName();
				if(scriptData.eventEntityDeath.getEntity().getCustomName() != null)
					return scriptData.eventEntityDeath.getEntity().getCustomName();
				return scriptData.eventEntityDeath.getEntityType().getName();
			case 27://killedbyplayer
				if(scriptData.eventPlayerDeath != null)
					if(scriptData.eventPlayerDeath.getEntity().getKiller() instanceof Player)
						return "true";
				if(scriptData.eventEntityDeath != null)
					if(scriptData.eventEntityDeath.getEntity().getKiller() != null)
						return "true";
				return "false";
			case 28://killername
				if(scriptData.eventPlayerDeath != null)
					if(scriptData.eventPlayerDeath.getEntity().getKiller() != null)
						return scriptData.eventPlayerDeath.getEntity().getKiller().getName();
					else
						return null;
				else
					if(scriptData.eventEntityDeath.getEntity().getKiller() != null)
						return scriptData.eventEntityDeath.getEntity().getKiller().getName();
					else
						return null;
			case 29://blockid
				if(scriptData.eventBlockBreak != null)
					return String.valueOf(scriptData.eventBlockBreak.getBlock().getTypeId());
				return String.valueOf(scriptData.eventBlockPlaced.getBlock().getTypeId());
			case 30://blocksub
				if(scriptData.eventBlockBreak != null)
					return String.valueOf(scriptData.eventBlockBreak.getBlock().getData());
				return String.valueOf(scriptData.eventBlockPlaced.getBlock().getData());
			case 31://blockname
				return null;
			case 32://entityname
				if(scriptData.eventInteract != null)
					return scriptData.eventInteract.getRightClicked().getType().getName();
				return scriptData.eventEntitySpawn.getEntityType().getName();
			case 33://entitbyplayer
				if(scriptData.eventInteract != null)
					if(scriptData.eventInteract.getRightClicked() instanceof Player)
						return "true";
				return "false";
			case 34://areaisenter
				if(scriptData.eventName.equals("areaenter"))
					return "true";
				return "false";
			case 35://hasmoney
				if(subField[0].equals(""))
					return String.valueOf((int)plugin.vault.economy.getBalance(player.getName()));
				else if(subPlayer != null)
					return String.valueOf((int)plugin.vault.economy.getBalance(subPlayer.getName()));
				else
					return null;
			case 36://givemoney
				if(subField[0].equals(""))
					return String.valueOf(plugin.vault.economy.depositPlayer(player.getName(), Double.parseDouble(subField[1])).transactionSuccess());
				else if(subPlayer != null)
					return String.valueOf(plugin.vault.economy.depositPlayer(subPlayer.getName(), Double.parseDouble(subField[1])).transactionSuccess());
				else
					return null;
			case 37://takemoney
				if(subField[0].equals(""))
					return String.valueOf(plugin.vault.economy.withdrawPlayer(player.getName(), Double.parseDouble(subField[1])).transactionSuccess());
				else if(subPlayer != null)
					return String.valueOf(plugin.vault.economy.withdrawPlayer(subPlayer.getName(), Double.parseDouble(subField[1])).transactionSuccess());
				else
					return null;
			case 38://areacheck
				if(subField[0].equals(""))
					return String.valueOf(plugin.areas.equalsAreas(player.getWorld().getName(), subField[1], 0));
				else if(subPlayer != null)
					return String.valueOf(plugin.areas.equalsAreas(subPlayer.getWorld().getName(), subField[1], 0));
				else
					return null;
			case 39://opcheck
				if(subField[0].equals(""))
					return String.valueOf(player.isOp());
				else if(subPlayer != null)
					return String.valueOf(subPlayer.isOp());
				else
					return null;
			case 40://flying
				if(subField[0].equals(""))
					return String.valueOf(player.isFlying());
				else if(subPlayer != null)
					return String.valueOf(subPlayer.isFlying());
				else
					return null;
			case 41://food
				if(subField[0].equals(""))
					return String.valueOf(player.getFoodLevel());
				else if(subPlayer != null)
					return String.valueOf(subPlayer.getFoodLevel());
				else
					return null;
			case 42://playerlocx
				if(subField[0].equals(""))
					return String.valueOf(player.getLocation().getBlockX());
				else if(subPlayer != null)
					return String.valueOf(subPlayer.getLocation().getBlockX());
				else
					return null;
			case 43://playerlocy
				if(subField[0].equals(""))
					return String.valueOf(player.getLocation().getBlockY());
				else if(subPlayer != null)
					return String.valueOf(subPlayer.getLocation().getBlockY());
				else
					return null;
			case 44://playerlocz
				if(subField[0].equals(""))
					return String.valueOf(player.getLocation().getBlockZ());
				else if(subPlayer != null)
					return String.valueOf(subPlayer.getLocation().getBlockZ());
				else
					return null;
			case 45://realyear
				return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			case 46://realmon
				return String.valueOf(Calendar.getInstance().get(Calendar.MONTH) +1);
			case 47://realdaday
				return String.valueOf(Calendar.getInstance().get(Calendar.DATE));
			case 48://realhour
				return String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
			case 49://realmin
				return String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
			case 50://realsec
				return String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
			case 51://maxhealth
				if(subField[0].equals(""))
					return String.valueOf(player.getMaxHealth());
				else if(subPlayer != null)
					return String.valueOf(subPlayer.getMaxHealth());
				else
					return null;
			case 52://blockid
				Location loc = new Location(plugin.getServer().getWorld(subField[1]), Double.parseDouble(subField[0].split(",")[0]), Double.parseDouble(subField[0].split(",")[1]), Double.parseDouble(subField[0].split(",")[2]));
				return String.valueOf(loc.getBlock().getTypeId());
			case 53://hashelmet
				if(subField[0].equals(""))
				{
					if(player.getInventory().getHelmet() == null)
						return String.valueOf(false);
					return String.valueOf(true);
				}
				else if(subPlayer != null)
				{
					if(subPlayer.getInventory().getHelmet() == null)
						return String.valueOf(false);
					return String.valueOf(true);
				}
				else
					return null;
			case 54://haschestplate
				if(subField[0].equals(""))
				{
					if(player.getInventory().getChestplate() == null)
						return String.valueOf(false);
					return String.valueOf(true);
				}
				else if(subPlayer != null)
				{
					if(subPlayer.getInventory().getChestplate() == null)
						return String.valueOf(false);
					return String.valueOf(true);
				}
				else
					return null;
			case 55://hasleggings
				if(subField[0].equals(""))
				{
					if(player.getInventory().getLeggings() == null)
						return String.valueOf(false);
					return String.valueOf(true);
				}
				else if(subPlayer != null)
				{
					if(subPlayer.getInventory().getLeggings() == null)
						return String.valueOf(false);
					return String.valueOf(true);	
				}
				else
					return null;
			case 56://hasboots
				if(subField[0].equals(""))
				{
					if(player.getInventory().getBoots() == null)
						return String.valueOf(false);
					return String.valueOf(true);	
				}
				else if(subPlayer != null)
				{
					if(subPlayer.getInventory().getBoots() == null)
						return String.valueOf(false);
					return String.valueOf(true);	
				}
				else
					return null;
			case 57://kickmsg
				return scriptData.eventKick.getReason();
			case 58://oldlevel
				return String.valueOf(scriptData.eventLevel.getOldLevel());
			case 59://newlevel
				return String.valueOf(scriptData.eventLevel.getNewLevel());
			}
		}catch(Exception e){
			if(plugin.config.errorLog)
			{
				script = script + " 에러발생";
				plugin.getLogger().warning(script);
				if(player != null)
					player.sendMessage(ChatColor.RED + script);
			}
		}
		return null;
	}
}
