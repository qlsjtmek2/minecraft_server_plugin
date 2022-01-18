package me.espoo.punchstat;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class GUI {
	static GUIMessage G;
	
	@SuppressWarnings("static-access")
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, G.getMessage(p, "스텟 GUI 이름"));
		
		if (G.getLoreList(p, "팔 근력.아이템 설명").size() == 0)
			Stack2(G.getMessage(p, "팔 근력.아이템 이름"), G.getItemCode("팔 근력.아이템 코드"), G.getItemCode("팔 근력.아이템 데이터코드"), 
				   G.getItemCode("팔 근력.아이템 수량"), Arrays.asList(), 10, GUI);
		else
			Stack(G.getMessage(p, "팔 근력.아이템 이름"), G.getItemCode("팔 근력.아이템 코드"), G.getItemCode("팔 근력.아이템 데이터코드"), 
				  G.getItemCode("팔 근력.아이템 수량"), G.getLoreList(p, "팔 근력.아이템 설명"), 10, GUI);
		
		if (G.getLoreList(p, "복근.아이템 설명").size() == 0)
			Stack2(G.getMessage(p, "복근.아이템 이름"), G.getItemCode("복근.아이템 코드"), G.getItemCode("복근.아이템 데이터코드"), 
				   G.getItemCode("복근.아이템 수량"), Arrays.asList(), 12, GUI);
		else
			Stack(G.getMessage(p, "복근.아이템 이름"), G.getItemCode("복근.아이템 코드"), G.getItemCode("복근.아이템 데이터코드"), 
				  G.getItemCode("복근.아이템 수량"), G.getLoreList(p, "복근.아이템 설명"), 12, GUI);
		
		if (G.getLoreList(p, "다리 근력.아이템 설명").size() == 0)
			Stack2(G.getMessage(p, "다리 근력.아이템 이름"), G.getItemCode("다리 근력.아이템 코드"), G.getItemCode("다리 근력.아이템 데이터코드"), 
				   G.getItemCode("다리 근력.아이템 수량"), Arrays.asList(), 14, GUI);
		else
			Stack(G.getMessage(p, "다리 근력.아이템 이름"), G.getItemCode("다리 근력.아이템 코드"), G.getItemCode("다리 근력.아이템 데이터코드"), 
				  G.getItemCode("다리 근력.아이템 수량"), G.getLoreList(p, "다리 근력.아이템 설명"), 14, GUI);
		
		if (G.getLoreList(p, "스피드.아이템 설명").size() == 0)
			Stack2(G.getMessage(p, "스피드.아이템 이름"), G.getItemCode("스피드.아이템 코드"), G.getItemCode("스피드.아이템 데이터코드"), 
				   G.getItemCode("스피드.아이템 수량"), Arrays.asList(), 16, GUI);
		else
			Stack(G.getMessage(p, "스피드.아이템 이름"), G.getItemCode("스피드.아이템 코드"), G.getItemCode("스피드.아이템 데이터코드"), 
				  G.getItemCode("스피드.아이템 수량"), G.getLoreList(p, "스피드.아이템 설명"), 16, GUI);
		
		if (G.getLoreList(p, "설명.아이템 설명").size() == 0)
			Stack2(G.getMessage(p, "설명.아이템 이름"), G.getItemCode("설명.아이템 코드"), G.getItemCode("설명.아이템 데이터코드"), 
				   G.getItemCode("설명.아이템 수량"), Arrays.asList(), 26, GUI);
		else
			Stack(G.getMessage(p, "설명.아이템 이름"), G.getItemCode("설명.아이템 코드"), G.getItemCode("설명.아이템 데이터코드"), 
				  G.getItemCode("설명.아이템 수량"), G.getLoreList(p, "설명.아이템 설명"), 26, GUI);
		
		if (G.getLoreList(p, "설명2.아이템 설명").size() == 0)
			Stack2(G.getMessage(p, "설명2.아이템 이름"), G.getItemCode("설명2.아이템 코드"), G.getItemCode("설명2.아이템 데이터코드"), 
				   G.getItemCode("설명2.아이템 수량"), Arrays.asList(), 18, GUI);
		else
			Stack(G.getMessage(p, "설명2.아이템 이름"), G.getItemCode("설명2.아이템 코드"), G.getItemCode("설명2.아이템 데이터코드"), 
				  G.getItemCode("설명2.아이템 수량"), G.getLoreList(p, "설명2.아이템 설명"), 18, GUI);
		
		p.openInventory(GUI);
	}
	
	@SuppressWarnings("static-access")
	public static void viewGUI(Player p, Player me)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, G.getMessage(p, "스텟 GUI 이름"));
		
		if (G.getLoreList(p, "팔 근력.아이템 설명").size() == 0)
			Stack2(G.getMessage(p, "팔 근력.아이템 이름"), G.getItemCode("팔 근력.아이템 코드"), G.getItemCode("팔 근력.아이템 데이터코드"), 
				   G.getItemCode("팔 근력.아이템 수량"), Arrays.asList(), 10, GUI);
		else
			Stack(G.getMessage(p, "팔 근력.아이템 이름"), G.getItemCode("팔 근력.아이템 코드"), G.getItemCode("팔 근력.아이템 데이터코드"), 
				  G.getItemCode("팔 근력.아이템 수량"), G.getLoreList(p, "팔 근력.아이템 설명"), 10, GUI);
		
		if (G.getLoreList(p, "복근.아이템 설명").size() == 0)
			Stack2(G.getMessage(p, "복근.아이템 이름"), G.getItemCode("복근.아이템 코드"), G.getItemCode("복근.아이템 데이터코드"), 
				   G.getItemCode("복근.아이템 수량"), Arrays.asList(), 12, GUI);
		else
			Stack(G.getMessage(p, "복근.아이템 이름"), G.getItemCode("복근.아이템 코드"), G.getItemCode("복근.아이템 데이터코드"), 
				  G.getItemCode("복근.아이템 수량"), G.getLoreList(p, "복근.아이템 설명"), 12, GUI);
		
		if (G.getLoreList(p, "다리 근력.아이템 설명").size() == 0)
			Stack2(G.getMessage(p, "다리 근력.아이템 이름"), G.getItemCode("다리 근력.아이템 코드"), G.getItemCode("다리 근력.아이템 데이터코드"), 
				   G.getItemCode("다리 근력.아이템 수량"), Arrays.asList(), 14, GUI);
		else
			Stack(G.getMessage(p, "다리 근력.아이템 이름"), G.getItemCode("다리 근력.아이템 코드"), G.getItemCode("다리 근력.아이템 데이터코드"), 
				  G.getItemCode("다리 근력.아이템 수량"), G.getLoreList(p, "다리 근력.아이템 설명"), 14, GUI);
		
		if (G.getLoreList(p, "스피드.아이템 설명").size() == 0)
			Stack2(G.getMessage(p, "스피드.아이템 이름"), G.getItemCode("스피드.아이템 코드"), G.getItemCode("스피드.아이템 데이터코드"), 
				   G.getItemCode("스피드.아이템 수량"), Arrays.asList(), 16, GUI);
		else
			Stack(G.getMessage(p, "스피드.아이템 이름"), G.getItemCode("스피드.아이템 코드"), G.getItemCode("스피드.아이템 데이터코드"), 
				  G.getItemCode("스피드.아이템 수량"), G.getLoreList(p, "스피드.아이템 설명"), 16, GUI);
		
		if (G.getLoreList(p, "설명.아이템 설명").size() == 0)
			Stack2(G.getMessage(p, "설명.아이템 이름"), G.getItemCode("설명.아이템 코드"), G.getItemCode("설명.아이템 데이터코드"), 
				   G.getItemCode("설명.아이템 수량"), Arrays.asList(), 26, GUI);
		else
			Stack(G.getMessage(p, "설명.아이템 이름"), G.getItemCode("설명.아이템 코드"), G.getItemCode("설명.아이템 데이터코드"), 
				  G.getItemCode("설명.아이템 수량"), G.getLoreList(p, "설명.아이템 설명"), 26, GUI);
		
		if (G.getLoreList(p, "설명2.아이템 설명").size() == 0)
			Stack2(G.getMessage(p, "설명2.아이템 이름"), G.getItemCode("설명2.아이템 코드"), G.getItemCode("설명2.아이템 데이터코드"), 
				   G.getItemCode("설명2.아이템 수량"), Arrays.asList(), 18, GUI);
		else
			Stack(G.getMessage(p, "설명2.아이템 이름"), G.getItemCode("설명2.아이템 코드"), G.getItemCode("설명2.아이템 데이터코드"), 
				  G.getItemCode("설명2.아이템 수량"), G.getLoreList(p, "설명2.아이템 설명"), 18, GUI);
		
		me.openInventory(GUI);
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
