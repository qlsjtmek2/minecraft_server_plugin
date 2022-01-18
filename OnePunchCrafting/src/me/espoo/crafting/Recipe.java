package me.espoo.crafting;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class Recipe {
	public static ShapedRecipe getCoalBlock() {
		ItemStack i = new MaterialData(35, (byte) 15).toItemStack(1);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.WHITE + "석탄 블록");
	    i.setItemMeta(im);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "RRR", "RRR", "RRR" }).setIngredient('R', Material.COAL);
	    return Recipe;
	}
	
	public static ShapedRecipe getQuartzBlock() {
		ItemStack i = new MaterialData(155, (byte) 0).toItemStack(1);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.WHITE + "네더 석영 블록");
	    i.setItemMeta(im);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "RRR", "RRR", "RRR" }).setIngredient('R', Material.QUARTZ);
	    return Recipe;
	}
	
	public static ShapedRecipe getIronZipBlock() {
		ItemStack i = new MaterialData(35, (byte) 0).toItemStack(1);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.YELLOW + "철 압축 블록");
	    i.setItemMeta(im);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "RRR", "RRR", "RRR" }).setIngredient('R', Material.IRON_BLOCK);
	    return Recipe;
	}
	
	public static ShapedRecipe getGoldZipBlock() {
		ItemStack i = new MaterialData(35, (byte) 4).toItemStack(1);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.YELLOW + "금 압축 블록");
	    i.setItemMeta(im);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "RRR", "RRR", "RRR" }).setIngredient('R', Material.GOLD_BLOCK);
	    return Recipe;
	}
	
	public static ShapedRecipe getDiamondZipBlock() {
		ItemStack i = new MaterialData(35, (byte) 9).toItemStack(1);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.YELLOW + "다이아몬드 압축 블록");
	    i.setItemMeta(im);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "RRR", "RRR", "RRR" }).setIngredient('R', Material.DIAMOND_BLOCK);
	    return Recipe;
	}
	
	public static ShapedRecipe getEmeraldZipBlock() {
		ItemStack i = new MaterialData(35, (byte) 5).toItemStack(1);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.YELLOW + "에메랄드 압축 블록");
	    i.setItemMeta(im);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "RRR", "RRR", "RRR" }).setIngredient('R', Material.EMERALD_BLOCK);
	    return Recipe;
	}
	
	public static ShapedRecipe getRedstoneZipBlock() {
		ItemStack i = new MaterialData(35, (byte) 14).toItemStack(1);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.YELLOW + "레드스톤 압축 블록");
	    i.setItemMeta(im);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "RRR", "RRR", "RRR" }).setIngredient('R', Material.REDSTONE_BLOCK);
	    return Recipe;
	}
	
	public static ShapedRecipe getLapisZipBlock() {
		ItemStack i = new MaterialData(35, (byte) 11).toItemStack(1);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.YELLOW + "청금석 압축 블록");
	    i.setItemMeta(im);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "RRR", "RRR", "RRR" }).setIngredient('R', Material.LAPIS_BLOCK);
	    return Recipe;
	}
	
	public static ShapedRecipe getQuartzZipBlock() {
		ItemStack i = new MaterialData(35, (byte) 8).toItemStack(1);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.YELLOW + "네더 석영 압축 블록");
	    i.setItemMeta(im);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "RRR", "RRR", "RRR" }).setIngredient('R', Material.QUARTZ_BLOCK);
	    return Recipe;
	}
	
	public static ShapedRecipe getCoalZipBlock() {
		ItemStack i = new MaterialData(49, (byte) 0).toItemStack(1);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.YELLOW + "석탄 압축 블록");
	    i.setItemMeta(im);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "RRR", "RRR", "RRR" }).setIngredient('R', new MaterialData(35, (byte) 15));
	    return Recipe;
	}
	
	public static ShapedRecipe getunIronBlock() {
		ItemStack i = new MaterialData(42, (byte) 0).toItemStack(9);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "R" }).setIngredient('R', new MaterialData(35, (byte) 0));
	    return Recipe;
	}
	
	public static ShapedRecipe getunGoldBlock() {
		ItemStack i = new MaterialData(41, (byte) 0).toItemStack(9);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "R" }).setIngredient('R', new MaterialData(35, (byte) 4));
	    return Recipe;
	}
	
	public static ShapedRecipe getunDiamondBlock() {
		ItemStack i = new MaterialData(57, (byte) 0).toItemStack(9);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "R" }).setIngredient('R', new MaterialData(35, (byte) 9));
	    return Recipe;
	}
	
	public static ShapedRecipe getunEmeraldBlock() {
		ItemStack i = new MaterialData(133, (byte) 0).toItemStack(9);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "R" }).setIngredient('R', new MaterialData(35, (byte) 5));
	    return Recipe;
	}
	
	public static ShapedRecipe getunCoalBlock() {
		ItemStack i = new MaterialData(35, (byte) 15).toItemStack(9);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.WHITE + "석탄 블록");
	    i.setItemMeta(im);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "R" }).setIngredient('R', new MaterialData(49, (byte) 0));
	    return Recipe;
	}
	
	public static ShapedRecipe getunQuartzBlock() {
		ItemStack i = new MaterialData(155, (byte) 0).toItemStack(9);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.WHITE + "네더 석영 블록");
	    i.setItemMeta(im);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "R" }).setIngredient('R', new MaterialData(35, (byte) 8));
	    return Recipe;
	}
	
	public static ShapedRecipe getunRedstoneBlock() {
		ItemStack i = new MaterialData(152, (byte) 0).toItemStack(9);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "R" }).setIngredient('R', new MaterialData(35, (byte) 14));
	    return Recipe;
	}
	
	public static ShapedRecipe getunLapisBlock() {
		ItemStack i = new MaterialData(22, (byte) 0).toItemStack(9);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "R" }).setIngredient('R', new MaterialData(35, (byte) 11));
	    return Recipe;
	}
	
	public static ShapedRecipe getunCoal() {
		ItemStack i = new MaterialData(263, (byte) 0).toItemStack(9);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "R" }).setIngredient('R', new MaterialData(35, (byte) 15));
	    return Recipe;
	}
	
	public static ShapedRecipe getunQuartz() {
		ItemStack i = new MaterialData(406, (byte) 0).toItemStack(9);
	    ShapedRecipe Recipe = new ShapedRecipe(i).shape(new String[] { "R" }).setIngredient('R', new MaterialData(155, (byte) 0));
	    return Recipe;
	}
	
	public static int CheakRecipe(PrepareItemCraftEvent e, int typeid, ChatColor color, String str, int num) {
	    ItemStack o = new ItemStack(0, 0);
	    int y = 1;
	    int x = 1;
	    
	    for (int z = 0; z < 9; z++) {
	    	if (e.getInventory().getItem(z) == null) {
	    		continue;
	    	}
	    	
	    	if (e.getInventory().getItem(z).getItemMeta() == null) {
	    		continue;
	    	}
	    	
	    	if (!(e.getInventory().getItem(z).getTypeId() == typeid)) {
	    		continue;
	    	}
	    	
	    	if (e.getInventory().getItem(z).getItemMeta().getDisplayName() == null) {
	    		x++;
	    		continue;
	    	}
	    	
	    	if (!e.getInventory().getItem(z).getItemMeta().getDisplayName().equalsIgnoreCase(color + str)) {
	    		continue;
	    	}
	    	
    		y++;
	    }
	    
	    if (y == num) {
	    	return -1;
	    }
	    
	    if (x == num) {
			e.getInventory().setItem(0, o);
			return -2;
	    }
	    
	    return 0;
	}
	
	public static int CheakRecipeData(PrepareItemCraftEvent e, int typeid, int data, ChatColor color, String str, int num) {
	    ItemStack o = new ItemStack(0, 0);
	    int y = 1;
	    int x = 1;
	    
	    for (int z = 0; z < 9; z++) {
	    	if (e.getInventory().getItem(z) == null) {
	    		continue;
	    	}
	    	
	    	if (e.getInventory().getItem(z).getItemMeta() == null) {
	    		continue;
	    	}
	    	
	    	if (!(e.getInventory().getItem(z).getTypeId() == typeid) && !(e.getInventory().getItem(z).getData().getData() == data)) {
	    		continue;
	    	}
	    	
	    	if (e.getInventory().getItem(z).getItemMeta().getDisplayName() == null) {
	    		x++;
	    		continue;
	    	}
	    	
	    	if (!e.getInventory().getItem(z).getItemMeta().getDisplayName().equalsIgnoreCase(color + str)) {
	    		continue;
	    	}
	    	
    		y++;
	    }
	    
	    if (y == num) {
	    	return -1;
	    }
	    
	    if (x == num) {
			e.getInventory().setItem(0, o);
			return -2;
	    }

	    return 0;
	}
}
