package me.espoo.upgrade;

import java.io.File;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import think.rpgitems.data.Locale;
import think.rpgitems.item.RPGItem;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File f = new File("plugins/ActionUpgrade/Player/" + p.getName() + ".yml");
		
		if (!f.exists()) {
			File folder = new File("plugins/ActionUpgrade");
			File folder2 = new File("plugins/ActionUpgrade/Player");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
			PlayerYml.CreatePlayerInfo(p, f, folder, folder2, config);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getName().contains("��ȭ �ϱ�")) {
			if (main.playerList.contains(p)) {
				main.playerList.remove(p);
				return;
			}
			
			List<String> lore = e.getInventory().getItem(8).getItemMeta().getLore();
			String name = lore.get(1);
			String[] str = API.getUpgradeStr(name, Integer.parseInt(lore.get(2)));
			int num = Integer.parseInt(str[2]) - 1;
			ItemStack item = API.getPaperItemStack(num, Integer.parseInt(str[3]));
			
			if (API.isInventoryClearSpace(p)) {
				p.getInventory().addItem(item);
			    ItemStack[] contents;
			    for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			        ItemStack it = contents[j];
			        if (it != null && item.isSimilar(it)) {
			        	RPGItem.updateItem(it, Locale.getPlayerLocale(p));
						p.updateInventory();
			        }
			    }
			} else {
				p.getWorld().dropItemNaturally(p.getLocation(), item);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onClickItem(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getName().contains("��ȭ �ϱ�")) {
			if (e.getCurrentItem() == null) return;
			e.setCancelled(true);
			
			if (e.getRawSlot() == 11) {
				if (API.getPlayerPremiumAmount(p) <= 0) {
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					p.sendMessage("��c�ູ ������ ��� Ȱ��ȭ/��Ȱ��ȭ ����� ����Ͻ� �� �����ϴ�.");
					return;
				} else {
    				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				List<String> lore = e.getInventory().getItem(8).getItemMeta().getLore();
					API.changePremium(p);
					GUI.Stack3(API.getPremiumItemStack(p), 10, e.getInventory());
					GUI.Stack3(API.getUpgradeItemStack(p, lore.get(0), lore.get(1), Integer.parseInt(lore.get(2))), 13, e.getInventory());
					p.updateInventory();
					return;
				}
			}
			
			else if (e.getRawSlot() == 13) {
				List<String> lore = e.getInventory().getItem(8).getItemMeta().getLore();
				int num = Integer.parseInt(lore.get(2));
				String name = lore.get(1);
				String display = lore.get(0);
				
				ItemStack current = p.getInventory().getContents()[Integer.parseInt(lore.get(3))];
				if (!current.hasItemMeta()) {
					p.closeInventory();
	    			p.sendMessage("��cERROR CODE 2: �����ڿ��� ������ �ּ���. \"/����\"");
	    			return;
				}
				
				ItemMeta currentm = current.getItemMeta();
				
				String[] str = null;
				if (API.isPremium(p) && API.getPlayerPremiumAmount(p) > 0) {
					str = API.getUpgradeStr(name, num)[1].split(",");
					API.subPlayerPremiumAmount(p);
				} else {
					str = API.getUpgradeStr(name, num)[0].split(",");
				}

				int random = new Random().nextInt(100);
				int success = Integer.parseInt(str[0]);
				int minus = Integer.parseInt(str[1]);
				int broken = Integer.parseInt(str[2]);
				
				if (random <= success) {
					num++;
					currentm.setDisplayName(API.getUpgradeItemName(name, num));
					current.setItemMeta(currentm);
					e.setCurrentItem(current);
					API.castLvup(p);
					p.getWorld().playSound(p.getLocation(), Sound.ANVIL_USE, 10.0F, 1.0F);
					p.sendMessage("��e[ " + display + " ��e] ��6�������� ���������� ��c��ȭ��6�ϼ̽��ϴ�!");
					RPGItem.updateItem(current, Locale.getPlayerLocale(p));
					if (main.playerList.contains(p)) main.playerList.remove(p);
					main.playerList.add(p);
					p.closeInventory();
					return;
				}
				
				else if (minus != 0) {
					if (random > success && random <= success + minus) {
						num--;
						currentm.setDisplayName(API.getUpgradeItemName(name, num));
						current.setItemMeta(currentm);
						e.setCurrentItem(current);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 1.5F);
						p.sendMessage("��e[ " + display + " ��e] ��c������ ��ȭ�� �Ѵܰ� ���������ϴ�.");
						RPGItem.updateItem(current, Locale.getPlayerLocale(p));
						if (main.playerList.contains(p)) main.playerList.remove(p);
						main.playerList.add(p);
						p.closeInventory();
						return;
					}
					
					else if (broken != 0) {
						if (random > success + minus && random <= success + minus + broken) {
							num = 0;
							currentm.setDisplayName(API.getUpgradeItemName(name, num));
							current.setItemMeta(currentm);
							e.setCurrentItem(current);
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.8F);
							p.sendMessage("��e[ " + display + " ��e] ��c������ ��ȭ�� �ı��Ǿ����ϴ�. (�ʱ�ȭ)");
							RPGItem.updateItem(current, Locale.getPlayerLocale(p));
							if (main.playerList.contains(p)) main.playerList.remove(p);
							main.playerList.add(p);
							p.closeInventory();
							return;
						}
					}
				}
				
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 1.5F);
				p.sendMessage("��e[ " + display + " ��e] ��c��ȭ�� �����ϼ̽��ϴ�.");
				if (main.playerList.contains(p)) main.playerList.remove(p);
				main.playerList.add(p);
				p.closeInventory();
				return;
			}
			
			else if (e.getRawSlot() == 26) {
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
				p.closeInventory();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGHEST)
    public void onUpGradeItem(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getCursor() == null || e.getCurrentItem() == null) return;
		if (!e.getSlotType().name().equalsIgnoreCase("QUICKBAR") && !e.getSlotType().name().equalsIgnoreCase("CONTAINER")) return;
		ItemStack cursor = e.getCursor();
		ItemStack current = e.getCurrentItem();
		
		if (cursor.getType() != null && cursor.hasItemMeta() && cursor.getItemMeta().hasDisplayName()
		 && current.hasItemMeta() && current.getItemMeta().hasDisplayName() && current.getItemMeta().hasLore()) {
			ItemMeta cursorm = cursor.getItemMeta();
			ItemMeta currentm = current.getItemMeta();
			String paper = cursorm.getDisplayName();
			String display = currentm.getDisplayName();
			List<String> lore = currentm.getLore();
			
			if (API.isUpgradePaper(paper)) {
				String name = API.getUpgradeNameN(display);
				if (name == null) {
					name = API.getUpgradeNameL(lore);
					if (name == null) return;
				}
				
				if (current.getAmount() != 1) {
					p.sendMessage("��c��ȭ�� �������� ������ �ʹ� �����ϴ�.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
				
				if (API.isUpgradeMax(name, display)) {
					p.sendMessage("��c�� �������� ��� ��ȭ�� ��ģ ������ �Դϴ�.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
				
				int num = API.getUpgradeNum(name, display);
				if (num == -1) {
					p.sendMessage("��c�� �������� ��ȭ�Ͻ� �� �����ϴ�.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
				
				int papernum = Integer.parseInt(API.getUpgradeStr(name, num)[2]) - 1;
				int paperamount = Integer.parseInt(API.getUpgradeStr(name, num)[3]);
				if (!API.getUpgradePaperList().get(papernum).equalsIgnoreCase(paper)) {
					p.sendMessage("��c�� �������� ������ ��e[ " + API.getUpgradePaperList().get(papernum) + " ��e] ��c��l" + paperamount + " ��c���θ� ��ȭ�Ͻ� �� �ֽ��ϴ�.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
				
				if (cursor.getAmount() != paperamount) {
					p.sendMessage("��c�� �������� �ֹ��� ��c��l" + paperamount + " ��c���� ��ȭ�Ͻ� �� �ֽ��ϴ�.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
				
				e.setCancelled(true);
				e.setCursor(null);
				
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.5F);
				if (e.getRawSlot() > 35) GUI.openGUI(p, display, name, num, e.getRawSlot() - 36);
				else GUI.openGUI(p, display, name, num, e.getRawSlot());
				return;
			}
		}
	}
}
