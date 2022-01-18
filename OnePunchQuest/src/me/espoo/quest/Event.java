package me.espoo.quest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.ThaH3lper.com.Api.BossDeathEvent;
import me.espoo.quest.yml.PlayerYml;

@SuppressWarnings("deprecation")
public class Event extends JavaPlugin implements Listener {
	main M;
	
	public Event(main main)
	{
		this.M = main;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player p = event.getPlayer();
		File folder = new File("plugins/OnePunchQuest");
		File folder2 = new File("plugins/OnePunchQuest/Player");
		File f = new File("plugins/OnePunchQuest/Player/" + p.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) PlayerYml.CreatePlayerInfo(f, folder, folder2, config);
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		if ((event.getRightClicked() instanceof Player)) {
			Player player = event.getPlayer();
			OfflinePlayer object = (OfflinePlayer) event.getRightClicked();
			
			if (!player.isSneaking()) {
				if (API.isMainQuestIng(player)) {
					List<String> task = API.getMainPlayerYmlTask(player);
					for (String str : task) {
						if (str.contains("talk")) {
							if (object.getName().equalsIgnoreCase(str.split(" ")[1].split(",")[0])) {
								if (!str.split(" ")[2].equalsIgnoreCase("true")) {
									String content = null;
									for (String strr : API.getMainTask(player)) {
										if (strr.split(" ")[1].equalsIgnoreCase(object.getName())) {
											content = strr.split(" ")[2];
											break;
										}
									}
									
									if (content != null) {
										List<String> list = API.TalkStringToTalkList(player, content);
										list.add(object.getName());
										main.QuestTalk.put(player, list);
										main.QuestType.put(player, "talk");
										GUI.QuestIngGUI(player, API.getMainQuestName(player), API.isMainSkip(player));
										return;
									}
								}
							}
						}
					}
				}
				
				if (API.isSubExist(object.getName())) {
					if (API.isSubQuestIng(player, object.getName())) {
						List<String> task = API.getSubPlayerYmlTask(player, object.getName());
						for (String str : task) {
							if (str.contains("talk")) {
								if (object.getName().equalsIgnoreCase(str.split(" ")[1].split(",")[0])) {
									if (!str.split(" ")[2].equalsIgnoreCase("true")) {
										String content = null;
										for (String strr : API.getSubTask(player, object.getName())) {
											if (strr.split(" ")[1].equalsIgnoreCase(object.getName())) {
												content = strr.split(" ")[2];
												break;
											}
										}
										
										if (content != null) {
											List<String> list = API.TalkStringToTalkList(player, content);
											list.add(object.getName());
											main.QuestTalk.put(player, list);
											main.QuestType.put(player, "talk");
											main.QuestNPC.put(player, object.getName());
											GUI.QuestIngGUI(player, API.getSubQuestName(player, object.getName()), API.isSubSkip(player, object.getName()));
											return;
										}
									}
								}
							}
						}
					}
				}
				
				GUI.NPCQuestListGUI(player, object.getName());
			}
		}
	}
	
	@EventHandler(ignoreCancelled=true)
	public void onBossDeath(BossDeathEvent e) {
		Player p = e.getPlayer();
		List<String> task = API.getMainPlayerYmlTask(p);
		List<String> check = new ArrayList<String>();
		boolean is = false;
		
		for (String str : task) {
			if (str.contains("epicboss")) {
				if (e.getBossName().equalsIgnoreCase(str.split(" ")[1].split(",")[0])) {
					if (!str.split(" ")[2].equalsIgnoreCase("true")) {
						is = true;
						int i = Integer.parseInt(str.split(" ")[1].split(",")[1]);
						int max = Integer.parseInt(str.split(" ")[1].split(",")[2]);
						i++;
						
						if (max == i) {
							check.add("epicboss " + e.getBossName() + "," + i + "," + i + " true");
						} else {
							check.add("epicboss " + e.getBossName() + "," + i + "," + max + " false");
						}
						
						p.sendMessage("§f[§a알림§f] §6\"<§6§l메인§6> " + API.getMainQuestName(p) + "\" 퀘스트 §c" + e.getBossName() + "§6 처치 §c§l(" + i + "/" + max + ")");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.3F);
					}
				}
			}
			
			else if (str.contains("monsterkill")) {
				if (!str.split(" ")[2].equalsIgnoreCase("true")) {
					is = true;
					int i = Integer.parseInt(str.split(" ")[1].split(",")[0]);
					int max = Integer.parseInt(str.split(" ")[1].split(",")[1]);
					i++;
					
					if (max == i) {
						check.add("monsterkill " + i + "," + i + " true");
					} else {
						check.add("monsterkill " + i + "," + max + " false");
					}
					
					p.sendMessage("§f[§a알림§f] §6\"<§6§l메인§6> " + API.getMainQuestName(p) + "\" 퀘스트 §c괴인 §6죽이기 §c§l(" + i + "/" + max + ")");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.3F);
				}
			}
			
			else check.add(str);
		}
		
		if (is) API.setMainPlayerYmlTask(p, check);
		
		if (API.getSubNPCList(p) != null && !API.getSubNPCList(p).isEmpty()) {
			for (String npc : API.getSubNPCList(p)) {
				task = API.getSubPlayerYmlTask(p, npc);
				check = new ArrayList<String>();
				is = false;
				
				for (String str : task) {
					if (str.contains("epicboss")) {
						if (e.getBossName().equalsIgnoreCase(str.split(" ")[1].split(",")[0])) {
							if (!str.split(" ")[2].equalsIgnoreCase("true")) {
								is = true;
								int i = Integer.parseInt(str.split(" ")[1].split(",")[1]);
								int max = Integer.parseInt(str.split(" ")[1].split(",")[2]);
								i++;
								
								if (max == i) {
									check.add("epicboss " + e.getBossName() + "," + i + "," + i + " true");
								} else {
									check.add("epicboss " + e.getBossName() + "," + i + "," + max + " false");
								}
								
								p.sendMessage("§f[§a알림§f] §6\"<" + API.getSubRank(p, npc) + "§6> " + API.getSubQuestName(p, npc) + "\" 퀘스트 §c" + e.getBossName() + "§6 처치 §c§l(" + i + "/" + max + ")");
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.3F);
							}
						}
					}
					
					else if (str.contains("monsterkill")) {
						if (!str.split(" ")[2].equalsIgnoreCase("true")) {
							is = true;
							int i = Integer.parseInt(str.split(" ")[1].split(",")[0]);
							int max = Integer.parseInt(str.split(" ")[1].split(",")[1]);
							i++;
							
							if (max == i) {
								check.add("monsterkill " + i + "," + i + " true");
							} else {
								check.add("monsterkill " + i + "," + max + " false");
							}
							
							p.sendMessage("§f[§a알림§f] §6\"<" + API.getSubRank(p, npc) + "§6> " + API.getSubQuestName(p, npc) + "\" 퀘스트 §c괴인 §6죽이기 §c§l(" + i + "/" + max + ")");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.3F);
						}
					}
					
					else check.add(str);
				}
				
				if (is) API.setSubPlayerYmlTask(p, npc, check);
			}	
		}
	}
	
	@EventHandler(ignoreCancelled=true)
	public void onPlayerCommnadPreprocess(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		List<String> task = API.getMainPlayerYmlTask(p);
		List<String> check = new ArrayList<String>();
		String cmd = e.getMessage().replaceAll("/", "");
		boolean is = false;
		
		for (String str : task) {
			if (str.contains("command")) {
				if (cmd.equalsIgnoreCase(API.replaceAllNull(str.split(" ")[1].split(",")[0]))) {
					if (!str.split(" ")[2].equalsIgnoreCase("true")) {
						is = true;
						check.add("command " + API.replaceAllNullTo_(cmd) + " true");
						
						p.sendMessage("§f[§a알림§f] §6\"<§6§l메인§6> " + API.getMainQuestName(p) + "\" 퀘스트 §c/" + cmd + "§6 명령어 입력 §c§l(1/1)");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.3F);
					}
				} else check.add(str);
			} else check.add(str);
		}
		
		if (is) API.setMainPlayerYmlTask(p, check);
		
		if (API.getSubNPCList(p) != null && !API.getSubNPCList(p).isEmpty()) {
			for (String npc : API.getSubNPCList(p)) {
				task = API.getSubPlayerYmlTask(p, npc);
				check = new ArrayList<String>();
				is = false;
				
				for (String str : task) {
					if (str.contains("command")) {
						if (cmd.equalsIgnoreCase(API.replaceAllNull(str.split(" ")[1].split(",")[0]))) {
							if (!str.split(" ")[2].equalsIgnoreCase("true")) {
								is = true;
								check.add("command " + API.replaceAllNullTo_(cmd) + " true");
								
								p.sendMessage("§f[§a알림§f] §6\"<" + API.getSubRank(p, npc) + "§6> " + API.getSubQuestName(p, npc) + "\" 퀘스트 §c/" + cmd + "§6 명령어 입력 §c§l(1/1)");
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.3F);
							}
						} else check.add(str);
					} else check.add(str);
				}
				
				if (is) API.setSubPlayerYmlTask(p, npc, check);
			}
		}
	}
	
	@EventHandler(ignoreCancelled=true)
	public void onPlayerChat(PlayerChatEvent e) {
		Player p = e.getPlayer();
		List<String> task = API.getMainPlayerYmlTask(p);
		List<String> check = new ArrayList<String>();
		String cmd = e.getMessage();
		boolean is = false;
		
		for (String str : task) {
			if (str.contains("chat")) {
				if (cmd.equalsIgnoreCase(API.replaceAllNull(str.split(" ")[1].split(",")[0]))) {
					if (!str.split(" ")[2].equalsIgnoreCase("true")) {
						is = true;
						check.add("chat " + API.replaceAllNullTo_(cmd) + " true");
						
						p.sendMessage("§f[§a알림§f] §6\"<§6§l메인§6> " + API.getMainQuestName(p) + "\" 퀘스트 §c" + cmd + "§6 채팅 입력 §c§l(1/1)");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.3F);
					}
				} else check.add(str);
			} else check.add(str);
		}
		
		if (is) API.setMainPlayerYmlTask(p, check);
		
		if (API.getSubNPCList(p) != null && !API.getSubNPCList(p).isEmpty()) {
			for (String npc : API.getSubNPCList(p)) {
				task = API.getSubPlayerYmlTask(p, npc);
				check = new ArrayList<String>();
				is = false;
				
				for (String str : task) {
					if (str.contains("chat")) {
						if (cmd.equalsIgnoreCase(API.replaceAllNull(str.split(" ")[1].split(",")[0]))) {
							if (!str.split(" ")[2].equalsIgnoreCase("true")) {
								is = true;
								check.add("chat " + API.replaceAllNullTo_(cmd) + " true");
								
								p.sendMessage("§f[§a알림§f] §6\"<" + API.getSubRank(p, npc) + "§6> " + API.getSubQuestName(p, npc) + "\" 퀘스트 §c" + cmd + "§6 채팅 입력 §c§l(1/1)");
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.3F);
							}
						} else check.add(str);
					} else check.add(str);
				}
				
				if (is) API.setSubPlayerYmlTask(p, npc, check);
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Entity kn = p.getKiller();
		if (kn instanceof Player) {
			Player kp = (Player) p.getKiller();
			List<String> task = API.getMainPlayerYmlTask(kp);
			List<String> check = new ArrayList<String>();
			boolean is = false;

			for (String str : task) {
				if (str.contains("playerkill")) {
					if (!str.split(" ")[2].equalsIgnoreCase("true")) {
						is = true;
						int i = Integer.parseInt(str.split(" ")[1].split(",")[0]);
						int max = Integer.parseInt(str.split(" ")[1].split(",")[1]);
						i++;
						
						if (max == i) {
							check.add("playerkill " + i + "," + i + " true");
						} else {
							check.add("playerkill " + i + "," + max + " false");
						}
						
						kp.sendMessage("§f[§a알림§f] §6\"<§6§l메인§6> " + API.getMainQuestName(kp) + "\" 퀘스트 §c플레이어 §6죽이기 §c§l(" + i + "/" + max + ")");
						if (me.espoo.option.PlayerYml.getInfoBoolean(kp, "효과음")) kp.playSound(kp.getLocation(), Sound.BURP, 1.0F, 1.3F);
					}
				} else check.add(str);
			}
			
			if (is) API.setMainPlayerYmlTask(kp, check);
			
			if (API.getSubNPCList(kp) != null && !API.getSubNPCList(kp).isEmpty()) {
				for (String npc : API.getSubNPCList(kp)) {
					task = API.getSubPlayerYmlTask(kp, npc);
					check = new ArrayList<String>();
					is = false;

					for (String str : task) {
						if (str.contains("playerkill")) {
							if (!str.split(" ")[2].equalsIgnoreCase("true")) {
								is = true;
								int i = Integer.parseInt(str.split(" ")[1].split(",")[0]);
								int max = Integer.parseInt(str.split(" ")[1].split(",")[1]);
								i++;
								
								if (max == i) {
									check.add("playerkill " + i + "," + i + " true");
								} else {
									check.add("playerkill " + i + "," + max + " false");
								}
								
								kp.sendMessage("§f[§a알림§f] §6\"<" + API.getSubRank(kp, npc) + "§6> " + API.getSubQuestName(kp, npc) + "\" 퀘스트 §c플레이어 §6죽이기 §c§l(" + i + "/" + max + ")");
								if (me.espoo.option.PlayerYml.getInfoBoolean(kp, "효과음")) kp.playSound(kp.getLocation(), Sound.BURP, 1.0F, 1.3F);
							}
						} else check.add(str);
					}
					
					if (is) API.setSubPlayerYmlTask(kp, npc, check);
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onInventoryClose(InventoryCloseEvent e) {		
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (!main.QuestType.containsKey(p)) return;
		if (main.QuestType.get(p).equalsIgnoreCase("main")) {
			if (main.QuestTalk.containsKey(p)) {
				List<String> talk = main.QuestTalk.get(p);
				main.QuestTalk.remove(p);
				main.QuestType.remove(p);
				p.sendMessage("§c인벤토리 창을 닫아 §c§l" + API.getMainQuestName(p) + " §c퀘스트가 자동으로 취소되었습니다.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				
				if (main.ThreadShow.containsKey(p)) {
					if (talk.isEmpty()) return;
					else {
						Show show = main.ThreadShow.get(p);
						show.stop();
						return;
					}
				}
			}
		}
		
		else if (main.QuestType.get(p).equalsIgnoreCase("REmain")) {
			if (main.QuestTalk.containsKey(p)) {
				List<String> talk = main.QuestTalk.get(p);
				main.QuestTalk.remove(p);
				main.QuestType.remove(p);
				
				if (main.ThreadShow.containsKey(p)) {
					if (talk.isEmpty()) return;
					else {
						Show show = main.ThreadShow.get(p);
						show.stop();
						return;
					}
				}
			}
		}
		
		else if (main.QuestType.get(p).equalsIgnoreCase("talk")) {
			if (main.QuestTalk.containsKey(p)) {
				if (main.QuestNPC.containsKey(p)) {
					String npc = main.QuestNPC.get(p);
					main.QuestNPC.remove(p);
					List<String> talk = main.QuestTalk.get(p);
					main.QuestTalk.remove(p);
					main.QuestType.remove(p);
					p.sendMessage("§c인벤토리 창을 닫아 §c§l" + API.getSubQuestName(p, npc) + " §c퀘스트 수행이 취소되었습니다.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					
					if (main.ThreadShow.containsKey(p)) {
						if (talk.isEmpty()) return;
						else {
							Show show = main.ThreadShow.get(p);
							show.stop();
							return;
						}
					}
				} else {
					List<String> talk = main.QuestTalk.get(p);
					main.QuestTalk.remove(p);
					main.QuestType.remove(p);
					p.sendMessage("§c인벤토리 창을 닫아 §c§l" + API.getMainQuestName(p) + " §c퀘스트 수행이 취소되었습니다.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					
					if (main.ThreadShow.containsKey(p)) {
						if (talk.isEmpty()) return;
						else {
							Show show = main.ThreadShow.get(p);
							show.stop();
							return;
						}
					}	
				}
			}
		}
		
		else if (main.QuestType.get(p).equalsIgnoreCase("sub")) {
			if (main.QuestTalk.containsKey(p) && main.QuestNPC.containsKey(p)) {
				List<String> talk = main.QuestTalk.get(p);
				String npc = main.QuestNPC.get(p);
				main.QuestTalk.remove(p);
				main.QuestType.remove(p);
				main.QuestNPC.remove(p);
				p.sendMessage("§c인벤토리 창을 닫아 §c§l" + API.getSubQuestName(p, npc) + " §c퀘스트가 자동으로 취소되었습니다.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				
				if (main.ThreadShow.containsKey(p)) {
					if (talk.isEmpty()) return;
					else {
						Show show = main.ThreadShow.get(p);
						show.stop();
						return;
					}
				}
			}
		}
		
		else if (main.QuestType.get(p).equalsIgnoreCase("REsub")) {
			if (main.QuestTalk.containsKey(p) && main.QuestNPC.containsKey(p)) {
				List<String> talk = main.QuestTalk.get(p);
				main.QuestTalk.remove(p);
				main.QuestType.remove(p);
				main.QuestNPC.remove(p);
				
				if (main.ThreadShow.containsKey(p)) {
					if (talk.isEmpty()) return;
					else {
						Show show = main.ThreadShow.get(p);
						show.stop();
						return;
					}
				}
			}
		}
	}
	
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	ItemStack item = e.getCurrentItem();
     	
     	if (e.getInventory().getName().contains("퀘스트:")) {
     		e.setCancelled(true);
     		
     		if (item != null) {
     			if (item.hasItemMeta()) {
             		ItemMeta meta = item.getItemMeta();
             		
             		if (meta.hasDisplayName()) {
             			if (meta.getDisplayName().equalsIgnoreCase("§f§l§n클릭시 퀘스트를 진행합니다.")) {
             				HumanEntity h = e.getView().getPlayer();
             				Player p = Bukkit.getPlayerExact(h.getName());
             				if (!main.QuestType.containsKey(p)) return;
             				e.setCancelled(true);
             				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
             				
             				if (main.QuestType.get(p).equalsIgnoreCase("main") || main.QuestType.get(p).equalsIgnoreCase("REmain")) {
             					if (main.QuestTalk.containsKey(p)) {
                 					List<String> talk = main.QuestTalk.get(p);
                 					
                 					if (talk.isEmpty()) return;
                 					else {
                 						String npcname = API.getMainNPCName(p);
                 						String content = API.replaceAllColors(talk.get(0));
                 						String questname = API.getMainQuestName(p);
                 						boolean skip = API.isMainSkip(p);
                 						String[] devcon = content.split("│");
                 						String[] strr = API.getCodeDateName(devcon[0], p.getName(), npcname);
                 						int code = Integer.parseInt(strr[0]);
                 						int date = Integer.parseInt(strr[1]);
                 						String itemname = strr[2];
                 						talk.remove(0);
                 						main.QuestTalk.remove(p);
                 						main.QuestTalk.put(p, talk);
                 						
                 						Show show = new Show(p, questname, itemname, code, date, skip, API.replaceAllPlayerOrNpc(devcon[1], p.getName(), npcname));
                 						if (main.ThreadShow.containsKey(p)) main.ThreadShow.remove(p);
                 						main.ThreadShow.put(p, show);
                 						show.start();
                 						return;
                 					}
                 				}
             				}
             				
             				else if (main.QuestType.get(p).equalsIgnoreCase("talk")) {
             					if (main.QuestTalk.containsKey(p)) {
             						if (main.QuestNPC.containsKey(p)) {
             							String npc = main.QuestNPC.get(p);
             							List<String> talk = main.QuestTalk.get(p);
                     					
                     					if (talk.isEmpty()) return;
                     					else {
                     						int num = talk.size() - 1;
                     						String npcname = talk.get(num);
                     						String content = API.replaceAllColors(talk.get(0));
                     						String questname = API.getSubQuestName(p, npc);
                     						boolean skip = API.isSubSkip(p, npc);
                     						String[] devcon = content.split("│");
                     						String[] strr = API.getCodeDateName(devcon[0], p.getName(), npcname);
                     						int code = Integer.parseInt(strr[0]);
                     						int date = Integer.parseInt(strr[1]);
                     						String itemname = strr[2];
                     						talk.remove(0);
                     						main.QuestTalk.remove(p);
                     						main.QuestTalk.put(p, talk);
                     						
                     						Show show = new Show(p, questname, itemname, code, date, skip, API.replaceAllPlayerOrNpc(devcon[1], p.getName(), npcname));
                     						if (main.ThreadShow.containsKey(p)) main.ThreadShow.remove(p);
                     						main.ThreadShow.put(p, show);
                     						show.start();
                     						return;
                     					}
             						} else {
             							List<String> talk = main.QuestTalk.get(p);
                     					
                     					if (talk.isEmpty()) return;
                     					else {
                     						int num = talk.size() - 1;
                     						String npcname = talk.get(num);
                     						String content = API.replaceAllColors(talk.get(0));
                     						String questname = API.getMainQuestName(p);
                     						boolean skip = API.isMainSkip(p);
                     						String[] devcon = content.split("│");
                     						String[] strr = API.getCodeDateName(devcon[0], p.getName(), npcname);
                     						int code = Integer.parseInt(strr[0]);
                     						int date = Integer.parseInt(strr[1]);
                     						String itemname = strr[2];
                     						talk.remove(0);
                     						main.QuestTalk.remove(p);
                     						main.QuestTalk.put(p, talk);
                     						
                     						Show show = new Show(p, questname, itemname, code, date, skip, API.replaceAllPlayerOrNpc(devcon[1], p.getName(), npcname));
                     						if (main.ThreadShow.containsKey(p)) main.ThreadShow.remove(p);
                     						main.ThreadShow.put(p, show);
                     						show.start();
                     						return;
                     					}
             						}
             					}
             				}
             				
             				else if (main.QuestType.get(p).equalsIgnoreCase("sub") || main.QuestType.get(p).equalsIgnoreCase("REsub")) {
             					if (main.QuestTalk.containsKey(p) && main.QuestNPC.containsKey(p)) {
                 					List<String> talk = main.QuestTalk.get(p);
                 					String npc = main.QuestNPC.get(p);
                 					
                 					if (talk.isEmpty()) return;
                 					else {
                 						String content = API.replaceAllColors(talk.get(0));
                 						String questname = API.getSubQuestName(p, npc);
                 						boolean skip = API.isSubSkip(p, npc);
                 						String[] devcon = content.split("│");
                 						String[] strr = API.getCodeDateName(devcon[0], p.getName(), npc);
                 						int code = Integer.parseInt(strr[0]);
                 						int date = Integer.parseInt(strr[1]);
                 						String itemname = strr[2];
                 						talk.remove(0);
                 						main.QuestTalk.remove(p);
                 						main.QuestTalk.put(p, talk);
                 						
                 						Show show = new Show(p, questname, itemname, code, date, skip, API.replaceAllPlayerOrNpc(devcon[1], p.getName(), npc));
                 						if (main.ThreadShow.containsKey(p)) main.ThreadShow.remove(p);
                 						main.ThreadShow.put(p, show);
                 						show.start();
                 						return;
                 					}
                 				}
             				}
             			}
             			
             			else if (meta.getDisplayName().equalsIgnoreCase("§e§l스킵하기")) {
             				HumanEntity h = e.getView().getPlayer();
             				Player p = Bukkit.getPlayerExact(h.getName());
             				e.setCancelled(true);
             				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
             				
             				if (main.QuestType.get(p).equalsIgnoreCase("main")) {
                 				if (main.QuestTalk.containsKey(p)) main.QuestTalk.remove(p);
                 				if (main.ThreadShow.containsKey(p)) {
                 					Show show = main.ThreadShow.get(p);
                 					show.stop();
                 				}
                 				
         						GUI.QuestMainADGUI(p, API.getMainQuestName(p));
         						return;
             				}
             				
             				else if (main.QuestType.get(p).equalsIgnoreCase("REmain")) {
         						if (main.QuestTalk.containsKey(p)) main.QuestTalk.remove(p);
         						if (main.QuestType.containsKey(p)) main.QuestType.remove(p);
                 				if (main.ThreadShow.containsKey(p)) {
                 					Show show = main.ThreadShow.get(p);
                 					show.stop();
                 				}
                 				
                 				GUI.NPCQuestInfoGUI(p, API.getMainNPCName(p), "§6메인");
         						return;
             				}
             				
             				else if (main.QuestType.get(p).equalsIgnoreCase("talk")) {
             					if (main.QuestNPC.containsKey(p)) {
         							String npc = main.QuestNPC.get(p);
                     				if (main.ThreadShow.containsKey(p)) {
                     					Show show = main.ThreadShow.get(p);
                     					show.stop();
                     				}
                     				
             						if (main.QuestTalk.containsKey(p)) main.QuestTalk.remove(p);
             						if (main.QuestType.containsKey(p)) main.QuestType.remove(p);
             						main.QuestNPC.remove(p);
             						p.sendMessage("§f[§a알림§f] §6\"<" + API.getSubRank(p, npc) + "§6> " + API.getSubQuestName(p, npc) + "\" 퀘스트 §c" + npc + "§6 와/과 대화 §c§l(1/1)");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.3F);

                     				List<String> task = API.getSubPlayerYmlTask(p, npc);
                    				List<String> check = new ArrayList<String>();
                    				boolean iss = false;
                    				for (String str : task) {
                    					if (str.contains("talk")) {
                    						if (npc.equalsIgnoreCase(str.split(" ")[1].split(",")[0])) {
                    							if (!str.split(" ")[2].equalsIgnoreCase("true")) {
                    								iss = true;
                    								check.add("talk " + npc + " true");
                    							}
                    						}
                    					} else check.add(str);
                    				}

                    				if (iss) API.setSubPlayerYmlTask(p, npc, check);
                    				p.closeInventory();
             						return;
             					} else {
             						List<String> talk = main.QuestTalk.get(p);
                     				if (main.ThreadShow.containsKey(p)) {
                     					Show show = main.ThreadShow.get(p);
                     					show.stop();
                     				}
                     				
             						if (main.QuestTalk.containsKey(p)) main.QuestTalk.remove(p);
             						if (main.QuestType.containsKey(p)) main.QuestType.remove(p);
             						String npcname = talk.get(0);
             						p.sendMessage("§f[§a알림§f] §6\"<§6§l메인§6> " + API.getMainQuestName(p) + "\" 퀘스트 §c" + npcname + "§6 와/과 대화 §c§l(1/1)");
             						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.3F);
                     				
                     				List<String> task = API.getMainPlayerYmlTask(p);
                    				List<String> check = new ArrayList<String>();
                    				boolean iss = false;
                    				for (String str : task) {
                    					if (str.contains("talk")) {
                    						if (npcname.equalsIgnoreCase(str.split(" ")[1].split(",")[0])) {
                    							if (!str.split(" ")[2].equalsIgnoreCase("true")) {
                    								iss = true;
                    								check.add("talk " + npcname + " true");
                    							}
                    						}
                    					} else check.add(str);
                    				}
                    				
                    				if (iss) API.setMainPlayerYmlTask(p, check);
                    				p.closeInventory();
             						return;
             					}
             				}
             				
             				else if (main.QuestType.get(p).equalsIgnoreCase("sub")) {
             					String npc = main.QuestNPC.get(p);
                 				if (main.QuestTalk.containsKey(p)) main.QuestTalk.remove(p);
                 				if (main.ThreadShow.containsKey(p)) {
                 					Show show = main.ThreadShow.get(p);
                 					show.stop();
                 				}
                 				
         						GUI.QuestSubADGUI(p, npc, API.getSubQuestName(p, npc));
         						return;
             				}
             				
             				else if (main.QuestType.get(p).equalsIgnoreCase("REsub")) {
         						if (main.QuestTalk.containsKey(p)) main.QuestTalk.remove(p);
         						if (main.QuestType.containsKey(p)) main.QuestType.remove(p);
                 				if (main.ThreadShow.containsKey(p)) {
                 					Show show = main.ThreadShow.get(p);
                 					show.stop();
                 				}
                 				
                 				String npc = main.QuestNPC.get(p);
                 				GUI.NPCQuestInfoGUI(p, npc, API.getSubRank(p, npc));
                 				if (main.QuestNPC.containsKey(p)) main.QuestNPC.remove(p);
         						return;
             				}
             			}
             			
             			else if (meta.getDisplayName().equalsIgnoreCase("§a클릭시 퀘스트를 수락합니다.")) {
             				HumanEntity h = e.getView().getPlayer();
             				Player p = Bukkit.getPlayerExact(h.getName());
             				e.setCancelled(true);
             				
             				if (main.QuestType.get(p).equalsIgnoreCase("main")) {
             					List<String> list = API.getMainTask(p);
             					if (list == null || list.isEmpty()) {
                 					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.2F);
                     				p.sendMessage("§a§l" + API.getMainQuestName(p) + " §a퀘스트를 수락하셨습니다.");
                 					p.sendMessage("§f[§a알림§f] §6\"<§6§l메인§6> " + API.getMainQuestName(p) + "\" 퀘스트를 §c§l완료 §6하여 보상을 받으셨습니다.");
                 					API.GiveReward(p, API.getMainReward(p));
                 					API.addMainQuest(p);
                     				main.QuestType.remove(p);
                 					p.closeInventory();
                 					return;
             					}
             					
                 				PlayerYml.setList(p, "메인 퀘스트.과제", API.SetingTask(list));
                 				PlayerYml.setBoolean(p, "메인 퀘스트.진행", true);
                 				p.sendMessage("§a§l" + API.getMainQuestName(p) + " §a퀘스트를 수락하셨습니다.");
                 				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
                 				main.QuestType.remove(p);
                 				p.closeInventory();
                 				return;
             				}
             				
             				else if (main.QuestType.get(p).equalsIgnoreCase("sub")) {
             					String npc = main.QuestNPC.get(p);
             					List<String> list = API.getSubTask(p, npc);
             					if (list == null || list.isEmpty()) {
                 					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.2F);
                     				p.sendMessage("§a§l" + API.getSubQuestName(p, npc) + " §a퀘스트를 수락하셨습니다.");
                 					p.sendMessage("§f[§a알림§f] §6\"<" + API.getSubRank(p, npc) + "§6> " + API.getMainQuestName(p) + "\" 퀘스트를 §c§l완료 §6하여 보상을 받으셨습니다.");
                 					API.GiveReward(p, API.getSubReward(p, npc));
                 					API.addSubQuest(p, npc);
                     				main.QuestType.remove(p);
                     				main.QuestNPC.remove(p);
                 					p.closeInventory();
                 					return;
             					}
             					
                 				PlayerYml.setList(p, npc + ".과제", API.SetingTask(list));
                 				PlayerYml.setBoolean(p, npc + ".진행", true);
                 				API.addSubNPCList(p, npc);
                 				p.sendMessage("§a§l" + API.getSubQuestName(p, npc) + " §a퀘스트를 수락하셨습니다.");
                 				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
                 				main.QuestType.remove(p);
                 				main.QuestNPC.remove(p);
                 				p.closeInventory();
                 				return;
             				}
             			}
             			
             			else if (meta.getDisplayName().equalsIgnoreCase("§c클릭시 퀘스트를 거절합니다.")) {
             				HumanEntity h = e.getView().getPlayer();
             				Player p = Bukkit.getPlayerExact(h.getName());
             				e.setCancelled(true);
             				
             				if (main.QuestType.get(p).equalsIgnoreCase("main")) {
                 				p.sendMessage("§c§l" + API.getMainQuestName(p) + " §c퀘스트를 거절하셨습니다.");
                 				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
                 				p.closeInventory();
                				main.QuestType.remove(p);
                 				return;
             				}
             				
             				if (main.QuestType.get(p).equalsIgnoreCase("sub")) {
             					String npc = main.QuestNPC.get(p);
                 				p.sendMessage("§c§l" + API.getSubQuestName(p, npc) + " §c퀘스트를 거절하셨습니다.");
                 				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
                 				p.closeInventory();
                				main.QuestType.remove(p);
                 				main.QuestNPC.remove(p);
                 				return;
             				}
             			}
             		}
             		
             		if (meta.hasLore()) {
             			boolean is = false;
             			for (String str : meta.getLore()) {
             				if (ChatColor.stripColor(str).equalsIgnoreCase("ㅡ> 클릭시 다음 대화를 봅니다.")) is = true;
             			}
             			
             			if (is) {
             				HumanEntity h = e.getView().getPlayer();
             				Player p = Bukkit.getPlayerExact(h.getName());
             				if (!main.QuestType.containsKey(p)) return;
             				e.setCancelled(true);
             				
             				if (main.QuestType.get(p).equalsIgnoreCase("main")) {
                 				if (main.QuestTalk.containsKey(p)) {
                 					List<String> talk = main.QuestTalk.get(p);
                 					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
                 					
                 					if (talk.isEmpty()) {
                 						main.QuestTalk.remove(p);
                 						GUI.QuestMainADGUI(p, API.getMainQuestName(p));
                 						return;
                 					} else {
                 						String npcname = API.getMainNPCName(p);
                 						String content = API.replaceAllColors(talk.get(0));
                 						String questname = API.getMainQuestName(p);
                 						boolean skip = API.isMainSkip(p);
                 						String[] devcon = content.split("│");
                 						String[] strr = API.getCodeDateName(devcon[0], p.getName(), npcname);
                 						int code = Integer.parseInt(strr[0]);
                 						int date = Integer.parseInt(strr[1]);
                 						String itemname = strr[2];
                 						talk.remove(0);
                 						main.QuestTalk.remove(p);
                 						main.QuestTalk.put(p, talk);
                 						
                 						Show show = new Show(p, questname, itemname, code, date, skip, API.replaceAllPlayerOrNpc(devcon[1], p.getName(), npcname));
                 						show.start();
                 						return;
                 					}
                 				}
             				}
             				
             				else if (main.QuestType.get(p).equalsIgnoreCase("REmain")) {
                 				if (main.QuestTalk.containsKey(p)) {
                 					List<String> talk = main.QuestTalk.get(p);
                 					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
                 					
                 					if (talk.isEmpty()) {
                 						main.QuestTalk.remove(p);
                 						main.QuestType.remove(p);
                         				GUI.NPCQuestInfoGUI(p, API.getMainNPCName(p), "§6메인");
                 						return;
                 					} else {
                 						String npcname = API.getMainNPCName(p);
                 						String content = API.replaceAllColors(talk.get(0));
                 						String questname = API.getMainQuestName(p);
                 						boolean skip = API.isMainSkip(p);
                 						String[] devcon = content.split("│");
                 						String[] strr = API.getCodeDateName(devcon[0], p.getName(), npcname);
                 						int code = Integer.parseInt(strr[0]);
                 						int date = Integer.parseInt(strr[1]);
                 						String itemname = strr[2];
                 						talk.remove(0);
                 						main.QuestTalk.remove(p);
                 						main.QuestTalk.put(p, talk);
                 						
                 						Show show = new Show(p, questname, itemname, code, date, skip, API.replaceAllPlayerOrNpc(devcon[1], p.getName(), npcname));
                 						show.start();
                 						return;
                 					}
                 				}
             				}
             				
             				else if (main.QuestType.get(p).equalsIgnoreCase("talk")) {
             					if (main.QuestTalk.containsKey(p)) {
             						if (main.QuestNPC.containsKey(p)) {
             							String npc = main.QuestNPC.get(p);
             							List<String> talk = main.QuestTalk.get(p);
                     					int num = talk.size() - 1;
                     					
                     					if (!talk.get(0).contains("│")) {
                     						main.QuestTalk.remove(p);
                     						main.QuestType.remove(p);
                     						main.QuestNPC.remove(p);
                     						String npcname = talk.get(0);
                     						p.sendMessage("§f[§a알림§f] §6\"<" + API.getSubRank(p, npc) + "§6> " + API.getSubQuestName(p, npc) + "\" 퀘스트 §c" + npc + "§6 와/과 대화 §c§l(1/1)");
                     						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.3F);
                             				
                             				List<String> task = API.getSubPlayerYmlTask(p, npc);
                            				List<String> check = new ArrayList<String>();
                            				boolean iss = false;
                            				for (String str : task) {
                            					if (str.contains("talk")) {
                            						if (npcname.equalsIgnoreCase(str.split(" ")[1].split(",")[0])) {
                            							if (!str.split(" ")[2].equalsIgnoreCase("true")) {
                            								iss = true;
                            								check.add("talk " + npc + " true");
                            							}
                            						}
                            					} else check.add(str);
                            				}
                            				
                            				if (iss) API.setSubPlayerYmlTask(p, npc, check);
                            				p.closeInventory();
                     						return;
                     					} else {
                     						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
                     						String npcname = talk.get(num);
                     						String content = API.replaceAllColors(talk.get(0));
                     						String questname = API.getMainQuestName(p);
                     						boolean skip = API.isMainSkip(p);
                     						String[] devcon = content.split("│");
                     						String[] strr = API.getCodeDateName(devcon[0], p.getName(), npcname);
                     						int code = Integer.parseInt(strr[0]);
                     						int date = Integer.parseInt(strr[1]);
                     						String itemname = strr[2];
                     						talk.remove(0);
                     						main.QuestTalk.remove(p);
                     						main.QuestTalk.put(p, talk);
                     						
                     						Show show = new Show(p, questname, itemname, code, date, skip, API.replaceAllPlayerOrNpc(devcon[1], p.getName(), npcname));
                     						show.start();
                     						return;
                     					}
             						} else {
             							List<String> talk = main.QuestTalk.get(p);
                     					int num = talk.size() - 1;
                     					
                     					if (!talk.get(0).contains("│")) {
                     						main.QuestTalk.remove(p);
                     						main.QuestType.remove(p);
                     						String npcname = talk.get(0);
                     						p.sendMessage("§f[§a알림§f] §6\"<§6§l메인§6> " + API.getMainQuestName(p) + "\" 퀘스트 §c" + npcname + "§6 와/과 대화 §c§l(1/1)");
                     						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.3F);
                             				
                             				List<String> task = API.getMainPlayerYmlTask(p);
                            				List<String> check = new ArrayList<String>();
                            				boolean iss = false;
                            				for (String str : task) {
                            					if (str.contains("talk")) {
                            						if (npcname.equalsIgnoreCase(str.split(" ")[1].split(",")[0])) {
                            							if (!str.split(" ")[2].equalsIgnoreCase("true")) {
                            								iss = true;
                            								check.add("talk " + npcname + " true");
                            							}
                            						}
                            					} else check.add(str);
                            				}
                            				
                            				if (iss) API.setMainPlayerYmlTask(p, check);
                            				p.closeInventory();
                     						return;
                     					} else {
                     						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
                     						String npcname = talk.get(num);
                     						String content = API.replaceAllColors(talk.get(0));
                     						String questname = API.getMainQuestName(p);
                     						boolean skip = API.isMainSkip(p);
                     						String[] devcon = content.split("│");
                     						String[] strr = API.getCodeDateName(devcon[0], p.getName(), npcname);
                     						int code = Integer.parseInt(strr[0]);
                     						int date = Integer.parseInt(strr[1]);
                     						String itemname = strr[2];
                     						talk.remove(0);
                     						main.QuestTalk.remove(p);
                     						main.QuestTalk.put(p, talk);
                     						
                     						Show show = new Show(p, questname, itemname, code, date, skip, API.replaceAllPlayerOrNpc(devcon[1], p.getName(), npcname));
                     						show.start();
                     						return;
                     					}
             						}
                 				}
             				}
             				
             				else if (main.QuestType.get(p).equalsIgnoreCase("sub")) {
                 				if (main.QuestTalk.containsKey(p) && main.QuestNPC.containsKey(p)) {
                 					List<String> talk = main.QuestTalk.get(p);
                 					String npc = main.QuestNPC.get(p);
                 					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
                 					
                 					if (talk.isEmpty()) {
                 						main.QuestTalk.remove(p);
                 						GUI.QuestSubADGUI(p, npc, API.getSubQuestName(p, npc));
                 						return;
                 					} else {
                 						String content = API.replaceAllColors(talk.get(0));
                 						String questname = API.getSubQuestName(p, npc);
                 						boolean skip = API.isSubSkip(p, npc);
                 						String[] devcon = content.split("│");
                 						String[] strr = API.getCodeDateName(devcon[0], p.getName(), npc);
                 						int code = Integer.parseInt(strr[0]);
                 						int date = Integer.parseInt(strr[1]);
                 						String itemname = strr[2];
                 						talk.remove(0);
                 						main.QuestTalk.remove(p);
                 						main.QuestTalk.put(p, talk);
                 						
                 						Show show = new Show(p, questname, itemname, code, date, skip, API.replaceAllPlayerOrNpc(devcon[1], p.getName(), npc));
                 						show.start();
                 						return;
                 					}
                 				}
             				}
             				
             				else if (main.QuestType.get(p).equalsIgnoreCase("REsub")) {
                 				if (main.QuestTalk.containsKey(p) && main.QuestNPC.containsKey(p)) {
                 					List<String> talk = main.QuestTalk.get(p);
                 					String npc = main.QuestNPC.get(p);
                 					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
                 					
                 					if (talk.isEmpty()) {
                 						main.QuestTalk.remove(p);
                 						main.QuestType.remove(p);
                 						main.QuestNPC.remove(p);
                         				GUI.NPCQuestInfoGUI(p, npc, API.getSubRank(p, npc));
                 						return;
                 					} else {
                 						String content = API.replaceAllColors(talk.get(0));
                 						String questname = API.getSubQuestName(p, npc);
                 						boolean skip = API.isSubSkip(p, npc);
                 						String[] devcon = content.split("│");
                 						String[] strr = API.getCodeDateName(devcon[0], p.getName(), npc);
                 						int code = Integer.parseInt(strr[0]);
                 						int date = Integer.parseInt(strr[1]);
                 						String itemname = strr[2];
                 						talk.remove(0);
                 						main.QuestTalk.remove(p);
                 						main.QuestTalk.put(p, talk);
                 						
                 						Show show = new Show(p, questname, itemname, code, date, skip, API.replaceAllPlayerOrNpc(devcon[1], p.getName(), npc));
                 						show.start();
                 						return;
                 					}
                 				}
             				}
             			}
             		}
             	}
     		}
     	}
     	
     	else if (e.getInventory().getName().contains("퀘스트 목록")) {
         	e.setCancelled(true);
         	
     		if (item != null) {
     			if (item.hasItemMeta()) {
             		ItemMeta meta = item.getItemMeta();
             		
             		if (meta.hasDisplayName()) {
                		HumanEntity h = e.getView().getPlayer();
                		Player p = Bukkit.getPlayerExact(h.getName());
                		
                		if (meta.getDisplayName().equalsIgnoreCase("§f창 닫기")) {
                			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
                			p.closeInventory();
                			return;
                		}
                		
                		else if (meta.getDisplayName().equalsIgnoreCase("§f§l클릭시 다음 퀘스트를 주는 NPC에게로 이동합니다.")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					p.closeInventory();
                			p.teleport(API.getMainPosition(p));
    						p.getLocation().getChunk().load();
	    					return;
                		}
                		
                		else if (meta.getDisplayName().equalsIgnoreCase("§f모든 메인 퀘스트를 완료하셨습니다.")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					return;
                		}
                		
                		else if (meta.getDisplayName().contains("§6§l▷")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					List<String> lore = meta.getLore();
	    					if (lore.get(0).contains("NPC 이름"))
	    						GUI.NPCQuestInfoGUI(p, API.getFinalArg(lore.get(0).split(" "), 2), API.getFinalArg(lore.get(1).split(" "), 2));
	    					return;
                		}
                		
                		else if (meta.getDisplayName().contains("§6§l▶")) {
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
	    					List<String> lore = meta.getLore();
	    					if (lore.get(0).contains("NPC 이름"))
	    						GUI.NPCQuestInfoGUI(p, API.getFinalArg(lore.get(0).split(" "), 2), API.getFinalArg(lore.get(1).split(" "), 2));
	    					return;
                		}
             		}
     			} else {
            		if (item.getTypeId() == 119) {
                		HumanEntity h = e.getView().getPlayer();
                		Player p = Bukkit.getPlayerExact(h.getName());
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					return;
         			}
     			}
     		}
     	}
     	
     	else if (e.getInventory().getName().contains("퀘스트 정보")) {
         	e.setCancelled(true);
         	
     		if (item != null) {
     			if (item.hasItemMeta()) {
             		ItemMeta meta = item.getItemMeta();
             		
             		if (meta.hasDisplayName()) {
                		HumanEntity h = e.getView().getPlayer();
                		Player p = Bukkit.getPlayerExact(h.getName());
                		
                		if (meta.getDisplayName().equalsIgnoreCase("§7뒤로 가기")) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					GUI.QuestListGUI(p, 1);
                			return;
                		}
                		
                		else if (meta.getDisplayName().equalsIgnoreCase("§a퀘스트 팁")) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
                			return;
                		}
                		
                		else if (meta.getDisplayName().equalsIgnoreCase("§6바로 이동")) {
                			String str = e.getInventory().getTitle();
                			str = str.substring(8);
                			if (ChatColor.stripColor(str.split("§0┃")[1]).equalsIgnoreCase("메인")) {
                    			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.0F);
                				p.closeInventory();
                    			p.teleport(API.getMainPosition(p));
        						p.getLocation().getChunk().load();
             					return;
                			} else {
                    			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.0F);
                				p.closeInventory();
                    			p.teleport(API.getSubPosition(p, str.split("§0┃")[0]));
        						p.getLocation().getChunk().load();
                    			return;
                			}
                		}
                		
                		else if (meta.getDisplayName().equalsIgnoreCase("§b내용 다시보기")) {
                			String str = e.getInventory().getTitle();
                			str = str.substring(8);
                			if (ChatColor.stripColor(str.split("§0┃")[1]).equalsIgnoreCase("메인")) {
                				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
             					GUI.QuestIngGUI(p, API.getMainQuestName(p), API.isMainSkip(p));
             					main.QuestTalk.put(p, API.getMainTalk(p));
             					main.QuestType.put(p, "REmain");
             					return;
                			} else {
                				String npc = str.split("§0┃")[0];
                				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
             					GUI.QuestIngGUI(p, API.getSubQuestName(p, npc), API.isSubSkip(p, npc));
             					main.QuestTalk.put(p, API.getSubTalk(p, npc));
             					main.QuestType.put(p, "REsub");
             					main.QuestNPC.put(p, npc);
                    			return;
                			}
                		}
             		}
     			}
     		}
     	}
     	
     	if (item != null) {
     		if (item.hasItemMeta()) {
     			ItemMeta meta = item.getItemMeta();
     			
     			if (meta.hasDisplayName()) {
     				HumanEntity h = e.getView().getPlayer();
     				Player p = Bukkit.getPlayerExact(h.getName());
         			String npc = e.getInventory().getTitle();
     				
     				if (meta.getDisplayName().equalsIgnoreCase("§6§l◇ " + API.getMainQuestName(p))) {
         				e.setCancelled(true);
         				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
     					GUI.QuestIngGUI(p, API.getMainQuestName(p), API.isMainSkip(p));
     					main.QuestTalk.put(p, API.getMainTalk(p));
     					main.QuestType.put(p, "main");
     					return;
     				}
     				
     				else if (meta.getDisplayName().equalsIgnoreCase("§6§l▶ " + API.getMainQuestName(p))) {
         				e.setCancelled(true);
         				if (!API.isMainQuestInvAir(p)) {
             				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
         					p.sendMessage("§c보상을 받을 인벤토리 공간이 부족하여 퀘스트를 완료하실 수 없습니다.");
         					p.closeInventory();
         					return;
         				}
         				
     					p.sendMessage("§f[§a알림§f] §6\"<§6§l메인§6> " + API.getMainQuestName(p) + "\" 퀘스트를 §c§l완료 §6하여 보상을 받으셨습니다.");
     					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.2F);
     					API.mainTakeRpgitemAndItem(p);
     					API.GiveReward(p, API.getMainReward(p));
     					API.addMainQuest(p);
     					p.closeInventory();
     					return;
     				}
     				
     				else if (meta.getDisplayName().equalsIgnoreCase("§6§l◇ " + API.getSubQuestName(p, npc))) {
         				e.setCancelled(true);
         				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
     					GUI.QuestIngGUI(p, API.getSubQuestName(p, npc), API.isSubSkip(p, npc));
     					main.QuestTalk.put(p, API.getSubTalk(p, npc));
     					main.QuestType.put(p, "sub");
     					main.QuestNPC.put(p, npc);
     					return;
     				}
     				
     				else if (meta.getDisplayName().equalsIgnoreCase("§6§l▶ " + API.getSubQuestName(p, npc))) {
         				e.setCancelled(true);
         				if (!API.isSubQuestInvAir(p, npc)) {
             				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
         					p.sendMessage("§c보상을 받을 인벤토리 공간이 부족하여 퀘스트를 완료하실 수 없습니다.");
         					p.closeInventory();
         					return;
         				}
         				
     					p.sendMessage("§f[§a알림§f] §6\"<" + API.getSubRank(p, npc) + "§6> " + API.getMainQuestName(p) + "\" 퀘스트를 §c§l완료 §6하여 보상을 받으셨습니다.");
     					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.2F);
     					API.subTakeRpgitemAndItem(p, npc);
     					API.GiveReward(p, API.getSubReward(p, npc));
     					API.addSubQuest(p, npc);
     					p.closeInventory();
     					return;
     				}
     				
     				else if (meta.getDisplayName().contains("§6§l▷ " + API.getMainQuestName(p)) ||
     						 meta.getDisplayName().contains("§6§l▷ " + API.getSubQuestName(p, npc))) {
     					e.setCancelled(true);
     					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
     					return;
     				}
     			}
     		}
     	}
	}
}
