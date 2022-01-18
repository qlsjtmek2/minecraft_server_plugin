package me.espoo.option;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.espoo.punchstat.Method;
import me.espoo.punchstat.StatsRunAPI;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	me.espoo.file.PlayerYml Ps;
	PlayerYml P;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
    public void onDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (P.getInfoBoolean(p, "아이템 버리기") == false) {
			e.setCancelled(true);
			p.sendMessage("");
			p.sendMessage("§c현재 아이템 버리기 옵션이 비활성화 되어있어 버릴 수 없습니다.");
			p.sendMessage("§f- §c아이템을 버리고 싶다면 '/옵션' 입력 후 기능을 활성화 해주시기 바랍니다.");
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onPlayerItemHeldEvent(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();

		if (P.getInfoBoolean(p, "효과음")) p.getLocation().getWorld().playSound(p.getLocation(), Sound.CLICK, 1.0F, 3.0F);
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void onPlayerPickupItemEvent(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();

		if (P.getInfoBoolean(p, "효과음")) p.getLocation().getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 3.0F);
	}

	@EventHandler
    public void onCancelClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	if (e.getInventory().getName().equalsIgnoreCase("옵션")) {
     		e.setCancelled(true);
     	}
	}
	
    @EventHandler
    public static void onPlayerQuit(PlayerQuitEvent e) {
        Clock.toggled.remove(e.getPlayer());
    }
    
    @SuppressWarnings("static-access")
	@EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
    	Player p = e.getPlayer();
    	if (!e.isCancelled()) {
    		if (e.getMessage().split(" ")[0].equalsIgnoreCase("/w") || e.getMessage().split(" ")[0].equalsIgnoreCase("/m") ||
    			e.getMessage().split(" ")[0].equalsIgnoreCase("/tell") || e.getMessage().split(" ")[0].equalsIgnoreCase("/me")) {
    			Player pl = Bukkit.getServer().getPlayerExact(searchOnlinePlayer(e.getMessage().split(" ")[1]));
    			
    			if (pl != null) {
        			if (P.getInfoBoolean(pl, "귓속말 거부")) {
        				e.setCancelled(true);
        				p.sendMessage("§c현재 그 플레이어의 귓속말이 비활성화 되어있어 귓속말을 보낼 수 없습니다.");
        			}
    			}
    		}
    	}
    }
	
	@SuppressWarnings("static-access")
	@EventHandler
    public void onClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
		HumanEntity h = e.getView().getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
     	ItemStack i = e.getCurrentItem();
     	
     	if (e.getInventory().getName().equalsIgnoreCase("옵션")) {
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		
     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §c팔 근력 스텟 §a§lON §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			Ps.setInfoBoolean(p, "팔 근력 스텟 적용", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §c팔 근력 스텟 §c§lOff §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			Ps.setInfoBoolean(p, "팔 근력 스텟 적용", true);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §d복근 스텟 §a§lON §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			Ps.setInfoBoolean(p, "복근 스텟 적용", false);
     			StatsRunAPI.PlayerHHealth(p);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §d복근 스텟 §c§lOFF §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			Ps.setInfoBoolean(p, "복근 스텟 적용", true);
     			StatsRunAPI.PlayerHealth(p);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §b다리 근력 스텟 §a§lON §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			Ps.setInfoBoolean(p, "다리 근력 스텟 적용", false);
     			p.removePotionEffect(PotionEffectType.JUMP);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §b다리 근력 스텟 §c§lOFF §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			Ps.setInfoBoolean(p, "다리 근력 스텟 적용", true);
				int im = (P.getInfoInt(p, "다리 근력") + Method.get3Stat(p) + Method.get3StatEffect(p) + Method.getLoon(p)) / 10;
				if (im > 0) {
					p.removePotionEffect(PotionEffectType.JUMP);
					p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, im - 1, true));
				}
				
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §a스피드 스텟 §a§lON §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			Ps.setInfoBoolean(p, "스피드 스텟 적용", false);
				p.setWalkSpeed(0.2F);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §a스피드 스텟 §c§lOFF §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			Ps.setInfoBoolean(p, "스피드 스텟 적용", true);
				float im = (float) (P.getInfoInt(p, "스피드") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
				if (im <= 1F) {
					if (im > 0.2F) {
						p.setWalkSpeed(im);
					} else {
						p.setWalkSpeed(0.2F);
					}
				} else {
					p.setWalkSpeed(1F);
				}
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §e아이템 버리기 §a§lON §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "아이템 버리기", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §e아이템 버리기 §c§lOFF §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "아이템 버리기", true);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §6채팅 보기 §a§lON §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "채팅 보기", false);
     			p.chat("/암호12414214e");
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §6채팅 보기 §c§lOFF §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "채팅 보기", true);
     			p.chat("/암호12414214g");
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §e플레이어 숨기기 §a§lON §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "플레이어 숨기기", false);
     			Clock.toggleClockOff(p);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §e플레이어 숨기기 §c§lOFF §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "플레이어 숨기기", true);
     			Clock.toggleClockOn(p);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §6플레이어 라이딩 §a§lON §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "플레이어 라이딩", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §6플레이어 라이딩 §c§lOFF §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "플레이어 라이딩", true);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §e정보 공개 여부 §a§lON §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "정보 공개 여부", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §e정보 공개 여부 §c§lOFF §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "정보 공개 여부", true);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §d상태 공개 여부 §a§lON §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "상태 공개 여부", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §d상태 공개 여부 §c§lOFF §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "상태 공개 여부", true);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §6효과음 §a§lON §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "효과음", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §6효과음 §c§lOFF §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "효과음", true);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §e귓속말 거부 §a§lON §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "귓속말 거부", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §e귓속말 거부 §c§lOFF §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "귓속말 거부", true);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §e파티 초대 §a§lON §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "파티 초대", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §e파티 초대 §c§lOFF §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "파티 초대", true);
     			GUI.openGUI(p);
     		}
     		

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §6길드 초대 §a§lON §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "길드 초대", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §6길드 초대 §c§lOFF §f]")) {
     			if (P.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "길드 초대", true);
     			GUI.openGUI(p);
     		}
     	}
	}
	
	public static String searchOnlinePlayer(String target) {
		List<String> list1 = new ArrayList<String>();
		for (int i = 0; i < 16; i++) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.getName().toLowerCase().indexOf(target.toLowerCase()) == i) {
					list1.add(player.getName());
				}
			}
			if (list1.size() == 0) {
				continue;
			} else {
				int len = 100;
				List<String> list3 = new ArrayList<String>();
				for (int j = 0; j < list1.size(); j++) {
					int l = list1.get(j).length();
					if (l < len) {
						len = l;
					}
				}
				for (int j = 0; j < list1.size(); j++) {
					if (list1.get(j).length() == len) {
						list3.add(list1.get(j));
					}
				}
				String[] list2 = new String[list3.size()];
				for (int j = 0; j < list3.size(); j++) {
					list2[j] = list3.get(j);
				}
				Arrays.sort(list2);
				return list2[0];
			}
		}
		return null;
	}
}
