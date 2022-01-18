package me.espoo.crafting;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler
	public void onCraftItem(PrepareItemCraftEvent e) {
		ItemStack i = e.getRecipe().getResult();
	    ItemStack o = new ItemStack(0, 0);

	    int num0 = Recipe.CheakRecipe(e, 22, null, null, 1);
	    if (num0 == -1) return;
	    if (num0 == -2) return;
	    
	    int num1 = Recipe.CheakRecipeData(e, 35, 15, ChatColor.WHITE, "석탄 블록", 9);
	    if (num1 == -1) return;
	    if (num1 == -2) return;

	    int num2 = Recipe.CheakRecipe(e, 155, ChatColor.WHITE, "네더 석영 블록", 9);
	    if (num2 == -1) return;
	    if (num2 == -2) return;
	    
	    int num3 = Recipe.CheakRecipe(e, 35, ChatColor.YELLOW, "철 압축 블록", 2);
	    if (num3 == -1) return;
	    if (num3 == -2) return;
	    
	    int num4 = Recipe.CheakRecipeData(e, 35, 4, ChatColor.YELLOW, "금 압축 블록", 2);
	    if (num4 == -1) return;
	    if (num4 == -2) return;
	    
	    int num5 = Recipe.CheakRecipeData(e, 35, 9, ChatColor.YELLOW, "다이아몬드 압축 블록", 2);
	    if (num5 == -1) return;
	    if (num5 == -2) return;
	    
	    int num6 = Recipe.CheakRecipeData(e, 35, 5, ChatColor.YELLOW, "에메랄드 압축 블록", 2);
	    if (num6 == -1) return;
	    if (num6 == -2) return;
	    
	    int num7 = Recipe.CheakRecipe(e, 49, ChatColor.YELLOW, "석탄 압축 블록", 2);
	    if (num7 == -1) return;
	    if (num7 == -2) return;
	    
	    int num8 = Recipe.CheakRecipeData(e, 35, 8, ChatColor.YELLOW, "네더 석영 압축 블록", 2);
	    if (num8 == -1) return;
	    if (num8 == -2) return;
	    
	    int num9 = Recipe.CheakRecipeData(e, 35, 14, ChatColor.YELLOW, "레드스톤 압축 블록", 2);
	    if (num9 == -1) return;
	    if (num9 == -2) return;
	    
	    int num10 = Recipe.CheakRecipeData(e, 35, 11, ChatColor.YELLOW, "청금석 압축 블록", 2);
	    if (num10 == -1) return;
	    if (num10 == -2) return;
	    
	    int num11 = Recipe.CheakRecipeData(e, 35, 15, ChatColor.WHITE, "석탄 블록", 1);
	    if (num11 == -1) return;
	    if (num11 == -2) return;
	    
	    int num12 = Recipe.CheakRecipe(e, 155, ChatColor.WHITE, "네더 석영 블록", 1);
	    if (num12 == -1) return;
	    if (num12 == -2) return;
	    
	    System.out.println("ㄴ");
		int z = 0;
		for (String str : Whitelist.getWhitelistList("조합 가능한 아이템 목록")) {
			   System.out.println("ㄷ");
			String[] item = str.split(":");
			
			if (item[2].equalsIgnoreCase("null")) {
				System.out.println(i.getData().getData());
				System.out.println(Byte.parseByte(item[1]));
				if (i.getTypeId() == Integer.parseInt(item[0]) && i.getData().getData() == Byte.parseByte(item[1])) {
					break;
				} else {
					z++; continue;
				}
			} else {
				if (i.getItemMeta() == null) {
					z++; continue;
				}
				
				if (i.getItemMeta().getDisplayName() == null) {
					z++; continue;
				}
				
				if (!i.getItemMeta().getDisplayName().equalsIgnoreCase(item[2])) {
					z++; continue;
				}
				
				if (i.getTypeId() != Integer.parseInt(item[0]) || i.getData().getData() != Byte.parseByte(item[1])) {
					z++; continue;
				}
				
				break;
			}
		}
		
		if (z == Whitelist.getWhitelistList("조합 가능한 아이템 목록").size()) {   // 만약 화이트 리스트에 아이템 값이 없다면
			e.getInventory().setItem(0, o);
			return;
		}
	}
	
	@EventHandler
	public void onCraftItem(CraftItemEvent e) {
		if (me.espoo.option.PlayerYml.getInfoBoolean((Player) e.getView().getPlayer(), "효과음")) ((Player) e.getView().getPlayer()).playSound(e.getView().getPlayer().getLocation(), Sound.ANVIL_USE, 1.2F, 1.0F);
	}
}
