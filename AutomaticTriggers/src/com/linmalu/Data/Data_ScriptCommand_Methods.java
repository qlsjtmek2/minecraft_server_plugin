package com.linmalu.Data;

import java.util.HashMap;
import java.util.Stack;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.linmalu.Main.AutomaticTriggers;

public class Data_ScriptCommand_Methods
{
	private AutomaticTriggers plugin;
	private HashMap<String, Integer> scriptCommands = new HashMap<String, Integer>(0);
	private String triggerloc = "";

	public Data_ScriptCommand_Methods(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
		scriptCommands.put("@BROADCAST", 0);
		scriptCommands.put("@PLAYER", 1);
		scriptCommands.put("@TELL", 2);
		scriptCommands.put("@CALL", 10);
		scriptCommands.put("@PAUSE", 11);
		scriptCommands.put("@EXIT", 12);
		scriptCommands.put("@COOLDOWN", 13);
		scriptCommands.put("@COOLDOWNALL", 14);
		scriptCommands.put("@CMD", 20);
		scriptCommands.put("@CMDOP", 21);
		scriptCommands.put("@CMDCON", 22);
		scriptCommands.put("@SETHEALTH", 30);
		scriptCommands.put("@SETMAXHEALTH", 31);
		scriptCommands.put("@SETFOOD", 32);
		scriptCommands.put("@LOOP", 100);
		scriptCommands.put("@ENDLOOP", 101);
		scriptCommands.put("@WHILE", 102);
		scriptCommands.put("@ENDWHILE", 103);
		scriptCommands.put("@BREAK", 104);
		scriptCommands.put("@ALL", 105);
		scriptCommands.put("@ENDALL", 106);
		scriptCommands.put("@IF", 110);
		scriptCommands.put("@ELSE", 111);
		scriptCommands.put("@ENDIF", 112);
		scriptCommands.put("@AND", 113);
		scriptCommands.put("@OR", 114);
		scriptCommands.put("@SETVAR", 120);
		scriptCommands.put("@CONVAR", 121);
		scriptCommands.put("@DELVAR", 122);
		scriptCommands.put("@ADDVAR", 123);
		scriptCommands.put("@SUBVAR", 124);
		scriptCommands.put("@MULVAR", 125);
		scriptCommands.put("@DIVVAR", 126);
		scriptCommands.put("@TP", 1000);
		scriptCommands.put("@LIGHTNING", 2000);
		scriptCommands.put("@ENTITY", 2001);
		scriptCommands.put("@DROPITEM", 2002);
		scriptCommands.put("@SIGNTEXT", 3000);
		scriptCommands.put("@BLOCK", 3001);
		scriptCommands.put("@EXPLOSION", 4000);
		scriptCommands.put("@SOUND", 4001);
	}
	public void ScriptCommand(String args[], String scripts[], Player player, Data_Sctipt_Runnable scriptData)
	{
		if(plugin.cooldown.containsData(args[0], player))
			return;
		Stack<Data_ScriptCommand_LoopData> loopCommand = new Stack<Data_ScriptCommand_LoopData>();
		Stack<Integer> whileCommand = new Stack<Integer>();
		Stack<Boolean> breakCommand = new Stack<Boolean>();
		Stack<Boolean> ifCommand = new Stack<Boolean>();
		Stack<Boolean> elseCommand = new Stack<Boolean>();
		Data_ScriptCommand_LoopData allCommand = null;
		Player allplayers[] = null;
		Player allPlayer = null;
		int whileCount = plugin.config.whileCount;
		if(player != null)
			triggerloc = String.valueOf(player.getLocation().getBlockX() + "," + player.getLocation().getBlockY() + "," + player.getLocation().getBlockZ());
		boolean loopBool = true;
		boolean whileBool = true;
		boolean allBool = true;
		boolean ifBool = true;
		boolean endIf;
		boolean endElse;
		for(int i = 0; i < scripts.length; i++)
		{
			String script = plugin.scriptFields.ScriptField(args, scripts[i], player, triggerloc, scriptData).replace("&", "§");
			String chat = "";
			String scriptArgs[] = {script};
			Location loc = null;
			try{
				scriptArgs = script.substring(script.indexOf("@")).split(" ");
				String contains[] = script.substring(script.indexOf("@")).replace(scriptArgs[0] + " ", "").split(" == ");
				if(scriptCommands.get(scriptArgs[0]) >= 1000)
					loc = new Location(plugin.getServer().getWorld(scriptArgs[2]), Double.parseDouble(scriptArgs[1].split(",")[0]), Double.parseDouble(scriptArgs[1].split(",")[1]), Double.parseDouble(scriptArgs[1].split(",")[2]));
				switch(scriptCommands.get(scriptArgs[0]))
				{
				case 100://@LOOP
					if(breakCommand.isEmpty())
					{
						loopBool = false;
						if(Integer.parseInt(scriptArgs[1]) > 0)
							loopBool = true;
					}else
						breakCommand.push(true);
					loopCommand.push(new Data_ScriptCommand_LoopData(i, Integer.parseInt(scriptArgs[1])));
					break;
				case 101://@ENDLOOP
					if(breakCommand.isEmpty())
					{
						loopBool = true;
						if(!loopCommand.isEmpty())
						{
							Data_ScriptCommand_LoopData cmdData = loopCommand.pop();
							if(cmdData.count > 1)
							{
								i = cmdData.index;
								cmdData.count--;
								loopCommand.push(cmdData);
							}
						}
					}else{
						breakCommand.pop();
						loopCommand.pop();
					}
					break;
				case 102://@WHILE
					if(breakCommand.isEmpty())
					{
						whileBool = false;
						if((script.contains(" == ") && contains[0].equals(contains[1])) || (scriptArgs[2].equals("!=") && !scriptArgs[1].equals(scriptArgs[3])) || (scriptArgs[2].equals("<") && Integer.parseInt(scriptArgs[1]) < Integer.parseInt(scriptArgs[3])) || (scriptArgs[2].equals("<=") && Integer.parseInt(scriptArgs[1]) <= Integer.parseInt(scriptArgs[3])) || (scriptArgs[2].equals(">") && Integer.parseInt(scriptArgs[1]) > Integer.parseInt(scriptArgs[3])) || (scriptArgs[2].equals(">=") && Integer.parseInt(scriptArgs[1]) >= Integer.parseInt(scriptArgs[3])))
							whileBool = true;
					}else
						breakCommand.push(true);
					whileCommand.push(i);
					if(--whileCount < 0)
						return;
					break;
				case 103://@ENDWHILE
					if(breakCommand.isEmpty())
					{
						whileBool = true;
						if(!whileCommand.isEmpty())
							i = whileCommand.pop() - 1;
					}else{
						breakCommand.pop();
						whileCommand.pop();
					}
					break;
				case 104://@BREAK
					if(ifBool)
						breakCommand.push(true);
					break;
				case 105://@ALL
					allPlayer = player;
					allplayers = plugin.getServer().getOnlinePlayers();
					allCommand = new Data_ScriptCommand_LoopData(i, allplayers.length -1);
					if(allplayers.length == 0)
						allBool = false;
					else
						player = allplayers[allCommand.count];
					break;
				case 106://@ENDALL
					allBool = true;
					player = allPlayer;
					if(allCommand.count > 0)
					{
						allCommand.count --;
						i = allCommand.index;
						player = allplayers[allCommand.count];
					}
					break;
				case 110://@IF
					if(ifBool)
						if((script.contains(" == ") && contains[0].equals(contains[1])) || (scriptArgs[2].equals("!=") && !scriptArgs[1].equals(scriptArgs[3])) || (scriptArgs[2].equals("<") && Integer.parseInt(scriptArgs[1]) < Integer.parseInt(scriptArgs[3])) || (scriptArgs[2].equals("<=") && Integer.parseInt(scriptArgs[1]) <= Integer.parseInt(scriptArgs[3])) || (scriptArgs[2].equals(">") && Integer.parseInt(scriptArgs[1]) > Integer.parseInt(scriptArgs[3])) || (scriptArgs[2].equals(">=") && Integer.parseInt(scriptArgs[1]) >= Integer.parseInt(scriptArgs[3])))
						{
							ifCommand.push(true);
							elseCommand.push(false);
						}
						else
						{
							ifCommand.push(false);
							elseCommand.push(true);
							ifBool = false;
						}
					else
					{
						ifCommand.push(false);
						elseCommand.push(false);
					}
					break;
				case 111://@ELSE
					ifBool = elseCommand.peek();
					break;
				case 112://@ENDIF
					endIf = ifCommand.pop();
					endElse = elseCommand.pop();
					if(endIf || endElse)
						ifBool = true;
					break;
				case 113://@AND
					if(!(ifBool && ((script.contains(" == ") && contains[0].equals(contains[1])) || (scriptArgs[2].equals("!=") && !scriptArgs[1].equals(scriptArgs[3])) || (scriptArgs[2].equals("<") && Integer.parseInt(scriptArgs[1]) < Integer.parseInt(scriptArgs[3])) || (scriptArgs[2].equals("<=") && Integer.parseInt(scriptArgs[1]) <= Integer.parseInt(scriptArgs[3])) || (scriptArgs[2].equals(">") && Integer.parseInt(scriptArgs[1]) > Integer.parseInt(scriptArgs[3])) || (scriptArgs[2].equals(">=") && Integer.parseInt(scriptArgs[1]) >= Integer.parseInt(scriptArgs[3])))))
					{
						endIf = ifCommand.pop();
						endElse = elseCommand.pop();
						if(!endIf && !endElse)
						{
							ifCommand.push(false);
							elseCommand.push(false);
						}
						else
						{
							ifCommand.push(false);
							elseCommand.push(true);
						}
						ifBool = false;
					}
					break;
				case 114://@OR
					if(ifBool)
						if(!((script.contains(" == ") && contains[0].equals(contains[1])) || (scriptArgs[2].equals("!=") && !scriptArgs[1].equals(scriptArgs[3])) || (scriptArgs[2].equals("<") && Integer.parseInt(scriptArgs[1]) < Integer.parseInt(scriptArgs[3])) || (scriptArgs[2].equals("<=") && Integer.parseInt(scriptArgs[1]) <= Integer.parseInt(scriptArgs[3])) || (scriptArgs[2].equals(">") && Integer.parseInt(scriptArgs[1]) > Integer.parseInt(scriptArgs[3])) || (scriptArgs[2].equals(">=") && Integer.parseInt(scriptArgs[1]) >= Integer.parseInt(scriptArgs[3]))))
						{
							endIf = ifCommand.pop();
							endElse = elseCommand.pop();
							if(!endIf && !endElse)
							{
								ifCommand.push(false);
								elseCommand.push(false);
							}
							else
							{
								ifCommand.push(false);
								elseCommand.push(true);
							}
							ifBool = false;
						}
					break;
				}
				if(loopBool && whileBool && allBool && ifBool)
				{
					switch(scriptCommands.get(scriptArgs[0]))
					{
					case 0://@BROADCAST
						chat = plugin.ScriptLine(scriptArgs, 1);
						plugin.getServer().broadcastMessage(chat);
						break;
					case 1://@PLAYER
						chat = plugin.ScriptLine(scriptArgs, 1);
						player.sendMessage(chat);
						break;
					case 2://@TELL
						if(scriptArgs.length > 1)
						{
							Player tellPlayer = plugin.getServer().getPlayerExact(scriptArgs[1]);
							if(tellPlayer != null)
							{
								chat = plugin.ScriptLine(scriptArgs, 2);
								tellPlayer.sendMessage(chat);
							}
						}
						break;
					case 10://@CALL
						String [] call = scriptArgs[1].split(":");
						if(plugin.scripts.equalScripts(call[0], call[1]))
							plugin.scriptCommands.ScriptCommand(args, plugin.scripts.getScripts(call[0], call[1]), player, scriptData);
						break;
					case 11://@PAUSE
						Thread.sleep(Integer.parseInt(scriptArgs[1]) * 100);
						break;
					case 12://@EXIT
						return;
					case 13://@COOLDOWN
						new Thread(new Data_Cooldown_Runnable(plugin, args[0], player, Integer.parseInt(scriptArgs[1]) * 100)).start();
						break;
					case 14://@COOLDOWNALL
						new Thread(new Data_Cooldown_Runnable(plugin, args[0], null, Integer.parseInt(scriptArgs[1]) * 100)).start();
						break;
					case 20://@CMD
						chat = plugin.ScriptLine(scriptArgs, 1);
						plugin.getServer().dispatchCommand(player, chat);
						break;
					case 21://@CMDOP
						chat = plugin.ScriptLine(scriptArgs, 1);
						boolean isOp = player.isOp();
						player.setOp(true);
						plugin.getServer().dispatchCommand(player, chat);
						player.setOp(isOp);
						break;
					case 22://@CMDCON
						chat = plugin.ScriptLine(scriptArgs, 1);
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), chat);
						break;
					case 30://@SETHEALTH
						plugin.getServer().getPlayerExact(scriptArgs[1]).setHealth(Integer.parseInt(scriptArgs[2]));
						break;
					case 31://@SETMAXHEALTH
						plugin.getServer().getPlayerExact(scriptArgs[1]).setMaxHealth(Integer.parseInt(scriptArgs[2]));
						break;
					case 32://@SETFOOD
						plugin.getServer().getPlayerExact(scriptArgs[1]).setFoodLevel(Integer.parseInt(scriptArgs[2]));
						break;
					case 120://@SETVAR
						plugin.scriptVariables.putScriptVariables(scriptArgs[1], scriptArgs[2]);
						break;
					case 121://@CONVAR
						plugin.scriptVariables.putScriptVariables(scriptArgs[1], plugin.scriptVariables.getScriptVariables(scriptArgs[1]) + scriptArgs[2]);
						break;
					case 122://@DELVAR
						plugin.scriptVariables.removeScriptVariables(scriptArgs[1]);
						break;
					case 123://@ADDVAR
						plugin.scriptVariables.putScriptVariables(scriptArgs[1], String.valueOf(Integer.parseInt(plugin.scriptVariables.getScriptVariables(scriptArgs[1])) + Integer.parseInt(scriptArgs[2])));
						break;
					case 124://@SUBVAR
						plugin.scriptVariables.putScriptVariables(scriptArgs[1], String.valueOf(Integer.parseInt(plugin.scriptVariables.getScriptVariables(scriptArgs[1])) - Integer.parseInt(scriptArgs[2])));
						break;
					case 125://@MULVAR
						plugin.scriptVariables.putScriptVariables(scriptArgs[1], String.valueOf(Integer.parseInt(plugin.scriptVariables.getScriptVariables(scriptArgs[1])) * Integer.parseInt(scriptArgs[2])));
						break;
					case 126://@DIVVAR
						plugin.scriptVariables.putScriptVariables(scriptArgs[1], String.valueOf(Integer.parseInt(plugin.scriptVariables.getScriptVariables(scriptArgs[1])) / Integer.parseInt(scriptArgs[2])));
						break;
					case 1000://@TP
						player.teleport(new Location(plugin.getServer().getWorld(scriptArgs[2]), Double.parseDouble(scriptArgs[1].split(",")[0]), Double.parseDouble(scriptArgs[1].split(",")[1]), Double.parseDouble(scriptArgs[1].split(",")[2]), player.getLocation().getYaw(), player.getLocation().getPitch()));
						break;
					case 2000://@LIGHTNING
						if(scriptArgs[3].equals("true"))
							loc.getWorld().strikeLightning(loc);
						else
							loc.getWorld().strikeLightningEffect(loc);
						break;
					case 2001://@ENTITY
						loc.getWorld().spawnEntity(loc, EntityType.valueOf(scriptArgs[3]));
						break;
					case 2002://@DROPITEM
						loc.getWorld().dropItem(loc, new ItemStack(Integer.parseInt(scriptArgs[3])));
						break;
					case 3000://@SIGNTEXT
						Sign sign = (Sign)loc.getWorld().getBlockAt(loc).getState();
						sign.setLine(Integer.parseInt(scriptArgs[3]) -1, plugin.ScriptLine(scriptArgs, 4));
						sign.update();
						break;
					case 3001://@BLOCK
						loc.getWorld().getBlockAt(loc).setTypeId(Integer.parseInt(scriptArgs[3]));
						loc.getWorld().getBlockAt(loc).setData(Byte.parseByte(scriptArgs[4]));
						break;
					case 4000://@EXPLOSION
						loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), Float.parseFloat(scriptArgs[3]), Boolean.parseBoolean(scriptArgs[4]), Boolean.parseBoolean(scriptArgs[5]));
						break;
					case 4001://@SOUND
						loc.getWorld().playSound(loc, Sound.valueOf(scriptArgs[3]), Float.parseFloat(scriptArgs[4]), Float.parseFloat(scriptArgs[5]));
						break;
					}
				}
			}catch (Exception e) {
				if(plugin.config.errorLog)
				{
					scriptArgs[0] = scriptArgs[0] + " 에러발생";
					plugin.getLogger().warning(scriptArgs[0]);
					if(player != null)
						player.sendMessage(ChatColor.RED + scriptArgs[0]);
				}
			}
		}
	}
	public boolean EqualScriptCommand(CommandSender sender, String args[], String eventName, int line)
	{
		int num = 0;
		if(eventName.startsWith("area") || eventName.startsWith("command") || eventName.startsWith("event"))
			num++;
		if(eventName.endsWith("add") || eventName.endsWith("edit"))
			num++;
		if(scriptCommands.containsKey(args[num]) && (scriptCommands.get(args[num]) / 1000) != 0 && (scriptCommands.get(args[num]) / 1000) >= (args.length - num))
		{
			if(eventName.startsWith("area") || eventName.startsWith("command") || eventName.startsWith("event"))
				plugin.listener.setListener(sender.getName(), args[num], plugin.ScriptLine(args, num +1), line, eventName, false, args[0]);
			else
				plugin.listener.setListener(sender.getName(), args[num], plugin.ScriptLine(args, num +1), line, eventName, false);
			sender.sendMessage(ChatColor.YELLOW + args[num] + ChatColor.GREEN + "의 위치를 도구로 우클릭하세요.");
			return true;
		}
		return false;
	}
}
