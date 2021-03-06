package me.shinkhan.music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.espoo.option.PlayerYml;

public class GUI {
	public static void openGUI(Player p, int k)
	{
		p.closeInventory();
		p.sendMessage("§6음악 목록을 불러오는 중입니다..");

		List<String> lore = Config.getList("아이템.설명");
		List<String> list = Method.getFileList();
        if (list.size() < 1 || k * 45 - 44 > list.size() || k == 0) {//
			if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
			p.sendMessage("§c해당 목록은 존재하지 않습니다. DHMusic 폴더 안에 .nbs 파일을 추가해 주시기 바랍니다.");
			p.closeInventory();
            return;
        }
        
        Inventory GUI = null;
        if (list.size() % 45 == 0) {//
            GUI = Bukkit.createInventory(null, 54, "음악 목록 " + k + "/" + list.size() / 45);
        } else {
            GUI = Bukkit.createInventory(null, 54, "음악 목록 " + k + "/" + (list.size() / 45 + 1));
        }
		
        if (GUI == null) {
        	p.sendMessage("§cGUI 창을 다시 오픈해주세요.");
        	return;
        }
        
        int num = 0;
        for (int j = (k - 1) * 45; j < k * 45; ++j) {//
			Stack("§f" + list.get(j), Config.getInt("아이템.코드"), Config.getInt("아이템.데이터 코드"), 1, lore, num, GUI);
            if (list.size() == j + 1) {
    			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
                break;
            }
            if (k * 45 - 1 == j && list.size() > j + 1) {//
    			Stack2("§6다음 목록 확인", 10, 0, 1, Arrays.asList(), 53, GUI);
            } num++;
        }
		
		if (k == 1) {
			Stack("§f창 닫기", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
		} else {
			Stack("§6이전 목록 확인", 8, 0, 1, GUIMessage.BackRanking, 45, GUI);
		}
		
		for (int x = 46; x < 53; x ++) {
			if (x == 46) {
				Stack("§e음악 정지", Config.getInt("Stop 아이템.코드"), Config.getInt("Stop 아이템.데이터 코드"), 1, Config.getList("Stop 아이템.설명"), x, GUI);
				continue;
			}
			
			else if (x == 47) {
				Stack("§b음악 재생 타입", Config.getInt("타입 아이템.코드"), Config.getInt("타입 아이템.데이터 코드"), 1, GUIMessage.getType(p), x, GUI);
				continue;
			}
			
			else if (x == 48) {
				Stack("§a현재 재생중인 음악", 69, 0, 1, GUIMessage.getSong(p), x, GUI);
				continue;
			}
			
			else if (x == 51) {
				if (p.isOp()) {
					List<String> list2 = Config.getList("오피 도움말.설명");
					list2.add("§7제작자: §fshinkhan");
					Stack(Config.getString("오피 도움말.이름"), Config.getInt("오피 도움말.코드"), Config.getInt("오피 도움말.데이터 코드"), 1, list2, x, GUI);
					continue;
				}
			}
			
			List<String> list2 = new ArrayList<String>();
			list2.add("§7제작자: §fshinkhan");
			Stack("", 119, 0, 1, list2, x, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	@SuppressWarnings("deprecation")
	public static void updateGUI(Player p)
	{
		InventoryView inv = p.getOpenInventory();
		ItemStack item = new MaterialData(69, (byte) 0).toItemStack(1);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName("§a현재 재생중인 음악");
		item_Meta.setLore(GUIMessage.getSong(p));
		item.setItemMeta(item_Meta);
		inv.setItem(48, item);
		p.updateInventory();
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
