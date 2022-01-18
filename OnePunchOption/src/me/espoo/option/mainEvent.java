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
		if (P.getInfoBoolean(p, "������ ������") == false) {
			e.setCancelled(true);
			p.sendMessage("");
			p.sendMessage("��c���� ������ ������ �ɼ��� ��Ȱ��ȭ �Ǿ��־� ���� �� �����ϴ�.");
			p.sendMessage("��f- ��c�������� ������ �ʹٸ� '/�ɼ�' �Է� �� ����� Ȱ��ȭ ���ֽñ� �ٶ��ϴ�.");
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onPlayerItemHeldEvent(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();

		if (P.getInfoBoolean(p, "ȿ����")) p.getLocation().getWorld().playSound(p.getLocation(), Sound.CLICK, 1.0F, 3.0F);
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void onPlayerPickupItemEvent(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();

		if (P.getInfoBoolean(p, "ȿ����")) p.getLocation().getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 3.0F);
	}

	@EventHandler
    public void onCancelClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	if (e.getInventory().getName().equalsIgnoreCase("�ɼ�")) {
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
        			if (P.getInfoBoolean(pl, "�ӼӸ� �ź�")) {
        				e.setCancelled(true);
        				p.sendMessage("��c���� �� �÷��̾��� �ӼӸ��� ��Ȱ��ȭ �Ǿ��־� �ӼӸ��� ���� �� �����ϴ�.");
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
     	
     	if (e.getInventory().getName().equalsIgnoreCase("�ɼ�")) {
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		
     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��c�� �ٷ� ���� ��a��lON ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			Ps.setInfoBoolean(p, "�� �ٷ� ���� ����", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��c�� �ٷ� ���� ��c��lOff ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			Ps.setInfoBoolean(p, "�� �ٷ� ���� ����", true);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��d���� ���� ��a��lON ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			Ps.setInfoBoolean(p, "���� ���� ����", false);
     			StatsRunAPI.PlayerHHealth(p);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��d���� ���� ��c��lOFF ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			Ps.setInfoBoolean(p, "���� ���� ����", true);
     			StatsRunAPI.PlayerHealth(p);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��b�ٸ� �ٷ� ���� ��a��lON ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			Ps.setInfoBoolean(p, "�ٸ� �ٷ� ���� ����", false);
     			p.removePotionEffect(PotionEffectType.JUMP);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��b�ٸ� �ٷ� ���� ��c��lOFF ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			Ps.setInfoBoolean(p, "�ٸ� �ٷ� ���� ����", true);
				int im = (P.getInfoInt(p, "�ٸ� �ٷ�") + Method.get3Stat(p) + Method.get3StatEffect(p) + Method.getLoon(p)) / 10;
				if (im > 0) {
					p.removePotionEffect(PotionEffectType.JUMP);
					p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, im - 1, true));
				}
				
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��a���ǵ� ���� ��a��lON ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			Ps.setInfoBoolean(p, "���ǵ� ���� ����", false);
				p.setWalkSpeed(0.2F);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��a���ǵ� ���� ��c��lOFF ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			Ps.setInfoBoolean(p, "���ǵ� ���� ����", true);
				float im = (float) (P.getInfoInt(p, "���ǵ�") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) / 200F;
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
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��e������ ������ ��a��lON ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "������ ������", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��e������ ������ ��c��lOFF ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "������ ������", true);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��6ä�� ���� ��a��lON ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "ä�� ����", false);
     			p.chat("/��ȣ12414214e");
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��6ä�� ���� ��c��lOFF ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "ä�� ����", true);
     			p.chat("/��ȣ12414214g");
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��e�÷��̾� ����� ��a��lON ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "�÷��̾� �����", false);
     			Clock.toggleClockOff(p);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��e�÷��̾� ����� ��c��lOFF ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "�÷��̾� �����", true);
     			Clock.toggleClockOn(p);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��6�÷��̾� ���̵� ��a��lON ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "�÷��̾� ���̵�", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��6�÷��̾� ���̵� ��c��lOFF ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "�÷��̾� ���̵�", true);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��e���� ���� ���� ��a��lON ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "���� ���� ����", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��e���� ���� ���� ��c��lOFF ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "���� ���� ����", true);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��d���� ���� ���� ��a��lON ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "���� ���� ����", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��d���� ���� ���� ��c��lOFF ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "���� ���� ����", true);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��6ȿ���� ��a��lON ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "ȿ����", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��6ȿ���� ��c��lOFF ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "ȿ����", true);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��e�ӼӸ� �ź� ��a��lON ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "�ӼӸ� �ź�", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��e�ӼӸ� �ź� ��c��lOFF ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "�ӼӸ� �ź�", true);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��e��Ƽ �ʴ� ��a��lON ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "��Ƽ �ʴ�", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��e��Ƽ �ʴ� ��c��lOFF ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "��Ƽ �ʴ�", true);
     			GUI.openGUI(p);
     		}
     		

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��6��� �ʴ� ��a��lON ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "��� �ʴ�", false);
     			GUI.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��6��� �ʴ� ��c��lOFF ��f]")) {
     			if (P.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);
     			P.setInfoBoolean(p, "��� �ʴ�", true);
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
