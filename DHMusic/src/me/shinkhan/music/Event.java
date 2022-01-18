package me.shinkhan.music;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.xxmicloxx.NoteBlockAPI.SongEndEvent;

import me.espoo.option.PlayerYml;

public class Event extends JavaPlugin implements Listener {
	main M;
	
	public Event(main main)
	{
		this.M = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		final String n = p.getName();
		
		if (n.equalsIgnoreCase("shinkhan")) {
			Timer timer = new Timer();
			Date timeToRun = new Date(System.currentTimeMillis() + 400);
			timer.schedule(new TimerTask() {
				public void run() {
					p.sendMessage("§6이 서버에는 당신의 플러그인인 §cDHMusic " + main.var + "v §6가 들어있습니다!");
					return;
				}
			}, timeToRun);
		}
		
		if (main.Type.get(p) != null) {
			main.Type.remove(p);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if (main.Type.get(p) != null) {
			main.Type.remove(p);
		}
	}
	
	@EventHandler
	public void onSoundEnd(final SongEndEvent e) {
		Timer timer1 = new Timer();
		Date timeToRun = new Date(System.currentTimeMillis() + 2000);
		timer1.schedule(new TimerTask() {
			public void run() {
				List<String> list = e.getSongPlayer().getPlayerList();
				if (list.isEmpty()) return;
				List<String> list2 = new ArrayList<String>();
				int y = 0;
				while (list.size() > y) {
					list2.add(list.get(y));
					y++;
				}
			
				for (String name : list2) {
					Player p = Method.getOnorOffLine(name);
					if (main.Type.get(p) == null) {
						if (main.Song.get(p) != null) main.Song.remove(p);
						if (p.getOpenInventory().getTitle().split(" ")[0].equalsIgnoreCase("음악") && p.getOpenInventory().getTitle().split(" ")[1].equalsIgnoreCase("목록")) {
							GUI.updateGUI(p);
						} continue;
					}
					
					else {
						String type = main.Type.get(p);
						if (type.equalsIgnoreCase("순차")) {
							int i = main.Song.get(p) + 1;
							
							if (Method.getFileList().size() <= i) {
								i = 0;
							}
							
							main.Song.put(p, i);
							Method.play(new Player[] { p }, Method.getFileList().get(i));
							
							if (p.getOpenInventory().getTitle().contains(" ")) {
								if (p.getOpenInventory().getTitle().split(" ")[0].equalsIgnoreCase("음악") && p.getOpenInventory().getTitle().split(" ")[1].equalsIgnoreCase("목록")) {
	    							GUI.updateGUI(p);
								}
							}
						}
						
						else if (type.equalsIgnoreCase("무작위")) {
							int i = new Random().nextInt(Method.getFileList().size());
							main.Song.put(p, i);
							Method.play(new Player[] { p }, Method.getFileList().get(i));
						}
						
						else if (type.equalsIgnoreCase("한곡")) Method.play(new Player[] { p }, Method.getFileList().get(main.Song.get(p)));
						else {
							if (main.Song.get(p) != null) main.Song.remove(p);
							continue;
						}
					}
				}
				return;
			}
		}, timeToRun);
	}

	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	
     	if (e.getInventory().getName().contains(" ")) {
     		if (e.getInventory().getName().split(" ")[0].equalsIgnoreCase("음악") && e.getInventory().getName().split(" ")[1].equalsIgnoreCase("목록")) {
             	e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		
        		if (e.getCurrentItem().getTypeId() == 119) return;
        		
        		if (e.getCurrentItem().hasItemMeta()) {
        			if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f창 닫기")) {
        					if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        					p.closeInventory();
        				}
        				
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6다음 목록 확인")) {
    						GUI.openGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
    					}
    					
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6이전 목록 확인")) {
    						GUI.openGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
    					}
        				
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e음악 정지")) {
    						Method.stop(p);
    					}
        				
    					else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b음악 재생 타입")) {
    						if (main.Type.get(p) != null) {
    							if (main.Type.get(p).equalsIgnoreCase("순차")) main.Type.put(p, "무작위");
    							else if (main.Type.get(p).equalsIgnoreCase("무작위")) main.Type.put(p, "한곡");
    							else if (main.Type.get(p).equalsIgnoreCase("한곡")) main.Type.remove(p);
    							else main.Type.put(p, "순차");
    						} else {
    							main.Type.put(p, "순차");
    						}
    						
    						int i = Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]);
    						GUI.openGUI(p, i);
    					}
        				
    					else {
    						String str = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
    						if (Method.getFileList().contains(str)) {
    							Method.play(new Player[] { p }, str);
    							GUI.updateGUI(p);
    						}
    					}
        			}
        		}
         	}
     	}
	}
}
