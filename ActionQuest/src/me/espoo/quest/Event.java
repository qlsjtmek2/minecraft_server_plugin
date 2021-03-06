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
		File f = new File("plugins/ActionQuest/Player/" + p.getName() + ".yml");
		if (!f.exists()) {
			File folder = new File("plugins/ActionQuest");
			File folder2 = new File("plugins/ActionQuest/Player");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
			PlayerYml.CreatePlayerInfo(f, folder, folder2, config);
		}
	}

	@EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		if ((event.getRightClicked() instanceof Player)) {
			Player player = event.getPlayer();
			OfflinePlayer object = (OfflinePlayer) event.getRightClicked();
			if (object.getName().equalsIgnoreCase(API.getMainNPCName()))
				GUI.NPCQuestListGUI(player, object.getName());
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
						
						p.sendMessage("??f[??a??????f] ??6\"<??6??l??????6> " + API.getMainQuestName(p) + "\" ?????? ??c" + e.getBossName() + "??6 ???? ??c??l(" + i + "/" + max + ")");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.3F);
					}
				} else check.add(str);
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
					
					p.sendMessage("??f[??a??????f] ??6\"<??6??l??????6> " + API.getMainQuestName(p) + "\" ?????? ??c???? ??6?????? ??c??l(" + i + "/" + max + ")");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.3F);
				}
			}
			
			else check.add(str);
		}
		
		if (is) API.setMainPlayerYmlTask(p, check);
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
						
						p.sendMessage("??f[??a??????f] ??6\"<??6??l??????6> " + API.getMainQuestName(p) + "\" ?????? ??c/" + cmd + "??6 ?????? ???? ??c??l(1/1)");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.3F);
					}
				} else check.add(str);
			} else check.add(str);
		}
		
		if (is) API.setMainPlayerYmlTask(p, check);
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
						
						p.sendMessage("??f[??a??????f] ??6\"<??6??l??????6> " + API.getMainQuestName(p) + "\" ?????? ??c" + cmd + "??6 ???? ???? ??c??l(1/1)");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.3F);
					}
				} else check.add(str);
			} else check.add(str);
		}
		
		if (is) API.setMainPlayerYmlTask(p, check);
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
						
						kp.sendMessage("??f[??a??????f] ??6\"<??6??l??????6> " + API.getMainQuestName(kp) + "\" ?????? ??c???????? ??6?????? ??c??l(" + i + "/" + max + ")");
						if (me.espoo.option.PlayerYml.getInfoBoolean(kp, "??????")) kp.playSound(kp.getLocation(), Sound.BURP, 1.0F, 1.3F);
					}
				} else check.add(str);
			}
			
			if (is) API.setMainPlayerYmlTask(kp, check);
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onInventoryClose(InventoryCloseEvent e) {		
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (main.QuestTalk.containsKey(p)) {
			List<String> talk = main.QuestTalk.get(p);
			main.QuestTalk.remove(p);
			p.sendMessage("??c???????? ???? ???? ??c??l" + API.getMainQuestName(p) + " ??c???????? ???????? ??????????????.");
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			
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
	
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	ItemStack item = e.getCurrentItem();
     	
     	if (e.getInventory().getName().contains(API.getMainNPCName())) {
     		if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
     			HumanEntity h = e.getView().getPlayer();
 				Player p = Bukkit.getPlayerExact(h.getName());
     			ItemMeta meta = item.getItemMeta();
     			
     			e.setCancelled(true);
     			
     			if (meta.getDisplayName().equalsIgnoreCase("??6??l?? " + API.getMainQuestName(p))) {
     				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
 					GUI.QuestIngGUI(p, API.getMainQuestName(p));
 					main.QuestTalk.put(p, API.getMainTalk(p));
 					return;
 				}
 				
 				else if (meta.getDisplayName().equalsIgnoreCase("??6??l?? " + API.getMainQuestName(p))) {
     				if (!API.isMainQuestInvAir(p)) {
         				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
     					p.sendMessage("??c?????? ???? ???????? ?????? ???????? ???????? ???????? ?? ????????.");
     					p.closeInventory();
     					return;
     				}
     				
 					p.sendMessage("??f[??a??????f] ??6\"<??6??l??????6> " + API.getMainQuestName(p) + "\" ???????? ??c??l???? ??6???? ?????? ????????????.");
 					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.2F);
 					API.mainTakeRpgitemAndItem(p);
 					API.GiveReward(p, API.getMainReward(p));
 					API.addMainQuest(p);
 					p.closeInventory();
 					return;
 				}
     			
 				else {
 					if (e.getRawSlot() == 13) {
 						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
 					}
 				}
     		}
     	}
     	
     	else if (e.getInventory().getName().contains("??????:")) {
     		e.setCancelled(true);
     		
     		if (item != null) {
     			if (item.hasItemMeta()) {
             		ItemMeta meta = item.getItemMeta();
             		
             		if (meta.hasDisplayName()) {
             			if (meta.getDisplayName().equalsIgnoreCase("??f??l??n?????? ???????? ??????????.")) {
             				HumanEntity h = e.getView().getPlayer();
             				Player p = Bukkit.getPlayerExact(h.getName());
             				e.setCancelled(true);
             				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
             				if (main.QuestTalk.containsKey(p)) {
             					List<String> talk = main.QuestTalk.get(p);
             					
             					if (talk.isEmpty()) return;
             					else {
             						String npcname = API.getMainNPCName();
             						String content = API.replaceAllColors(talk.get(0));
             						String questname = API.getMainQuestName(p);
             						talk.remove(0);
             						main.QuestTalk.remove(p);
             						main.QuestTalk.put(p, talk);
             						
             						Show show = new Show(p, questname, npcname, 397, 0, content);
             						if (main.ThreadShow.containsKey(p)) main.ThreadShow.remove(p);
             						main.ThreadShow.put(p, show);
             						show.start();
             						return;
             					}
             				}
             			}
             			
             			else if (meta.getDisplayName().equalsIgnoreCase("??a?????? ???????? ??????????.")) {
             				HumanEntity h = e.getView().getPlayer();
             				Player p = Bukkit.getPlayerExact(h.getName());
             				e.setCancelled(true);
         					List<String> list = API.getMainTask(p);
         					if (list == null || list.isEmpty()) {
             					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.2F);
                 				p.sendMessage("??a??l" + API.getMainQuestName(p) + " ??a???????? ??????????????.");
             					p.sendMessage("??f[??a??????f] ??6\"<??6??l??????6> " + API.getMainQuestName(p) + "\" ???????? ??c??l???? ??6???? ?????? ????????????.");
             					API.GiveReward(p, API.getMainReward(p));
             					API.addMainQuest(p);
             					p.closeInventory();
             					return;
         					}
         					
             				PlayerYml.setList(p, "???? ??????.????", API.SetingTask(list));
             				PlayerYml.setBoolean(p, "???? ??????.????", true);
             				p.sendMessage("??a??l" + API.getMainQuestName(p) + " ??a???????? ??????????????.");
             				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
             				p.closeInventory();
             				return;
             			}
             			
             			else if (meta.getDisplayName().equalsIgnoreCase("??c?????? ???????? ??????????.")) {
             				HumanEntity h = e.getView().getPlayer();
             				Player p = Bukkit.getPlayerExact(h.getName());
             				e.setCancelled(true);
             				p.sendMessage("??c??l" + API.getMainQuestName(p) + " ??c???????? ??????????????.");
             				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
             				p.closeInventory();
             				return;
             			}
             		}
             		
             		if (meta.hasLore()) {
             			boolean is = false;
             			for (String str : meta.getLore()) {
             				if (ChatColor.stripColor(str).equalsIgnoreCase("??> ?????? ???? ?????? ??????.")) is = true;
             			}
             			
             			if (is) {
             				HumanEntity h = e.getView().getPlayer();
             				Player p = Bukkit.getPlayerExact(h.getName());
             				e.setCancelled(true);
             				if (main.QuestTalk.containsKey(p)) {
             					List<String> talk = main.QuestTalk.get(p);
             					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "??????")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
             					
             					if (talk.isEmpty()) {
             						main.QuestTalk.remove(p);
             						GUI.QuestMainADGUI(p, API.getMainQuestName(p));
             						return;
             					} else {
             						String npcname = API.getMainNPCName();
             						String content = API.replaceAllColors(talk.get(0));
             						String questname = API.getMainQuestName(p);
             						talk.remove(0);
             						main.QuestTalk.remove(p);
             						main.QuestTalk.put(p, talk);
             						
             						Show show = new Show(p, questname, npcname, 397, 0, content);
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
}
