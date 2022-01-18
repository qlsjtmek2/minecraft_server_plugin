package me.shinkhan.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.espoo.option.PlayerYml;

public class GUI {
	public static void openGUI(Player p, int k, String name)
	{
		p.closeInventory();
		p.sendMessage("§6상점 목록을 불러오는 중입니다..");
		
		List<String> list = Config.getList(name);
        if (list.size() < 1 || k * 45 - 44 > list.size() || k == 0) {//
			if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			p.sendMessage("§c해당 목록은 존재하지 않습니다.");
			p.closeInventory();
            return;
        }
		
        Inventory GUI = null;
        if (list.size() % 45 == 0) {//
            GUI = Bukkit.createInventory(null, 54, "§l● §0" + name + " " + k + "/" + list.size() / 45);
        } else {
            GUI = Bukkit.createInventory(null, 54, "§l● §0" + name + " " + k + "/" + (list.size() / 45 + 1));
        }
		
        if (GUI == null) {
        	p.sendMessage("§cGUI 창을 다시 오픈해주세요.");
        	return;
        }
		
        int num = 0;
        for (int j = (k - 1) * 45; j < k * 45; ++j) {//
			String st = list.get(j);
			String[] s = st.split(",");
			List<String> lore = new ArrayList<String>();
			
			lore.add("§f- §7아이템 수량: §f" + s[2]);
			if (s.length == 5) {
				if (s[3].contains("S") || s[3].contains("s")) {
					lore.add("§f- §7왼클릭시 §f" + s[3].replace("S", "").replace("s", "") + " §7원에 §f판매§7합니다.");
				}
				
				else if (s[3].contains("B") || s[3].contains("b")) {
					lore.add("§f- §7우클릭시 §f" + s[3].replace("B", "").replace("b", "") + " §7원에 §f구매§7합니다.");
				}
				
				if (s[4].contains("S") || s[4].contains("s")) {
					lore.add("§f- §7왼클릭시 §f" + s[4].replace("S", "").replace("s", "") + " §7원에 §f판매§7합니다.");
				}
				
				else if (s[4].contains("B") || s[4].contains("b")) {
					lore.add("§f- §7우클릭시 §f" + s[4].replace("B", "").replace("b", "") + " §7원에 §f구매§7합니다.");
				}
			}
			
			else if (s.length == 4) {
				if (s[3].contains("S") || s[3].contains("s")) {
					lore.add("§f- §7왼클릭시 §f" + s[3].replace("S", "").replace("s", "") + " §7원에 §f판매§7합니다.");
				}
				
				else if (s[3].contains("B") || s[3].contains("b")) {
					lore.add("§f- §7우클릭시 §f" + s[3].replace("B", "").replace("b", "") + " §7원에 §f구매§7합니다.");
				}
			}
			
			else {
				continue;
			}
			
			if (Integer.parseInt(s[2]) > 64) {
				Stack(null, Integer.parseInt(s[0]), Integer.parseInt(s[1]), 1, lore, num, GUI);
			} else {
				Stack(null, Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), lore, num, GUI);
			}
        	
        	if (list.size() == j + 1) {
    			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
                break;
            }
            if (k * 45 - 1 == j && list.size() > j + 1) {//
    			Stack2("§6다음 목록 확인", 10, 0, 1, Arrays.asList(), 53, GUI);
            } num++;
        }
        
		if (k == 1) {
			Stack("§f뒤로 가기", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
		} else {
			Stack("§6이전 목록 확인", 8, 0, 1, GUIMessage.BackRanking, 45, GUI);
		}
		
		for (int x = 46; x < 53; x ++) {
			List<String> list2 = new ArrayList<String>();
			list2.add("§7제작자: §fshinkhan");
			Stack("", 119, 0, 1, list2, x, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	public static void Stack(String Display, int ID, int DATA, int STACK, List<String> lore, int loc, Inventory inv) {
		ItemStack item = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName(Display);
		item_Meta.setLore(lore);
		item.setItemMeta(item_Meta);
		inv.setItem(loc, item);
	}

	public static void Stack2(String Display, int ID, int DATA, int STACK, List<Object> lore, int loc, Inventory inv) {
		ItemStack item = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName(Display);
		item.setItemMeta(item_Meta);
		inv.setItem(loc, item);
	}
}
