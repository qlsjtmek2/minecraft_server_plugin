package me.espoo.cooking;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	GUIMessage G;
	PlayerYml P;
	
	public mainEvent(main main)
	{
		this.M = main;
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File folder = new File("plugins/OnePunchCooking");
		File folder2 = new File("plugins/OnePunchCooking/Player");
		File f = new File("plugins/OnePunchCooking/Player/" + p.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) P.CreatePlayerInfo(f, folder, folder2, config);
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
    public void onCancelClick(InventoryClickEvent e) {
		//System.out.println(e.getCurrentItem().getItemMeta().getDisplayName());
		//e.setCancelled(true);
     	if (!e.getInventory().getName().equalsIgnoreCase("요리 조합대")) return;
     	if (e.getCurrentItem() == null) return;
     	
		HumanEntity h = e.getView().getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
     	ItemStack i = e.getCurrentItem();
		if (i.getTypeId() == 338 || i.getTypeId() == 111 || i.getTypeId() == 295 || i.getTypeId() == 37 || 
			i.getTypeId() == 38 || i.getTypeId() == 296 || i.getTypeId() == 391 || i.getTypeId() == 392 || 
			i.getTypeId() == 281 || i.getTypeId() == 373) {
			if (P.getInfoInt(p, "남은 시간") == -1) {
				return;
			} else {
				p.sendMessage("§c현재 요리가 진행중이므로 재료를 올리실 수 없습니다.");
				e.setCancelled(true);
			}
		}
		else if (i.getType() == Material.AIR) return;
		else e.setCancelled(true);
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (!e.getInventory().getName().equalsIgnoreCase("요리 조합대")) return;
     	if (e.getCurrentItem() == null) return;
     	
		HumanEntity h = e.getView().getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (P.getInfoInt(p, "남은 시간") == -1) {
			Timer timer1 = new Timer();
			Date timeToRun = new Date(System.currentTimeMillis() + 50);
			timer1.schedule(new TimerTask() {
				public void run() {
					GUI.updateGUI(p, p.getOpenInventory());
					return;
				}
			}, timeToRun);
			
			if (e.getCurrentItem().hasItemMeta()) {
				if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
					if (e.getCurrentItem().getTypeId() == 58 && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §e요리 하기 §f]")) {
						if (e.getView().getItem(11) != null && e.getView().getItem(11).getType() == Material.AIR) {
							p.sendMessage("§c요리 재료 1을 재료 1칸에 올려주시기 바랍니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						if (!e.getView().getItem(11).hasItemMeta() && e.getView().getItem(11).getTypeId() != 281) {
							p.sendMessage("§c요리 재료 1에 채집장에서 채집한 아이템을 올려주시기 바랍니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						if (e.getView().getItem(20) != null && e.getView().getItem(20).getType() == Material.AIR) {
							p.sendMessage("§c요리 재료 2을 재료 2칸에 올려주시기 바랍니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						if (!e.getView().getItem(20).hasItemMeta() && e.getView().getItem(20).getTypeId() != 281) {
							p.sendMessage("§c요리 재료 2에 채집장에서 채집한 아이템을 올려주시기 바랍니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						if (e.getView().getItem(29) != null && e.getView().getItem(29).getType() == Material.AIR) {
							p.sendMessage("§c요리 재료 3을 재료 3칸에 올려주시기 바랍니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						if (!e.getView().getItem(29).hasItemMeta() && e.getView().getItem(29).getTypeId() != 281) {
							p.sendMessage("§c요리 재료 3에 채집장에서 채집한 아이템을 올려주시기 바랍니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						if (e.getView().getItem(38) != null && e.getView().getItem(38).getType() == Material.AIR) {
							p.sendMessage("§c요리 재료 4을 재료 4칸에 올려주시기 바랍니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						if (!e.getView().getItem(38).hasItemMeta() && e.getView().getItem(38).getTypeId() != 281) {
							p.sendMessage("§c요리 재료 4에 채집장에서 채집한 아이템을 올려주시기 바랍니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						boolean isFood = false;
						int num = 0;
						List<Material> list = new ArrayList<Material>();
						list.add(e.getView().getItem(11).getType());
						list.add(e.getView().getItem(20).getType());
						list.add(e.getView().getItem(29).getType());
						list.add(e.getView().getItem(38).getType());
						
						for (List<Material> it : FoodList.FoodList) {
							if (it.equals(list)) {
								isFood = true;
								break;
							} num++;
						}
						
						if (isFood) {
							P.setInfoInt(p, "남은 시간", FoodList.IntegerList.get(num));
							P.setInfoInt(p, "요리 코드", num);
							P.setInfoString(p, "만들고 있는 요리", FoodList.StringList.get(num));
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
							p.sendMessage("§e[ " + FoodList.StringList.get(num) + " ] §c요리§6를 만들기 시작하셨습니다.");
							timer1.schedule(new TimerTask() {
								public void run() {
									GUI.openGUI(p);
									return;
								}
							}, timeToRun);
							return;
						} else {
							p.sendMessage("§c이 재료들로 만들 수 있는 요리가 없습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
    public void onItemClick(InventoryClickEvent e) {
     	if (!e.getInventory().getName().equalsIgnoreCase("요리 조합대")) return;
     	if (e.getCurrentItem() == null) return;
     	
		HumanEntity h = e.getView().getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (P.getInfoInt(p, "남은 시간") == 0) {
			if (e.getSlot() == 42) {
	            int t = 0;
	            ItemStack[] contents;
	            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
	                ItemStack it = contents[j];
	                if (it == null) {
	                    ++t;
	                }
	            }
	            
	            if (t <= 1) {
	            	p.sendMessage("§c인벤토리를 한칸 비워주시고 요리를 받아주시기 바랍니다.");
	    			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
	            } else {
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
					ItemStack item = new MaterialData(340, (byte) 0).toItemStack(1);
					ItemMeta item_Meta = item.getItemMeta();
					item_Meta.setDisplayName(FoodList.NameList.get(P.getInfoInt(p, "요리 코드")));
					List<String> list = new ArrayList<String>();
					list.add("§7아이템을 들고 '왼클릭'하면 요리로 변합니다.");
					item_Meta.setLore(list);
					item.setItemMeta(item_Meta);
    			 	p.getInventory().addItem(item);
					p.sendMessage("§e[ " + FoodList.StringList.get(P.getInfoInt(p, "요리 코드")) + " ] §c요리§6를 성공적으로 만들었습니다.");
					p.closeInventory();
					P.setInfoInt(p, "남은 시간", -1);
					P.setInfoInt(p, "요리 코드", -1);
					P.setInfoString(p, "만들고 있는 요리", "NONE");
					GUI.openGUI(p);
	            }
			}
		}
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (!e.getInventory().getName().equalsIgnoreCase("요리 조합대")) return;
		
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		InventoryView i = e.getView();

		if (P.getInfoInt(p, "남은 시간") == -1) {
	    	if (i.getItem(11) != null && i.getItem(11).getType() != Material.AIR) {
				p.getWorld().dropItemNaturally(p.getLocation(), i.getItem(11));
			}
			
	    	if (i.getItem(20) != null && i.getItem(20).getType() != Material.AIR) {
				p.getWorld().dropItemNaturally(p.getLocation(), i.getItem(20));
			}
	    	
	    	if (i.getItem(29) != null && i.getItem(29).getType() != Material.AIR) {
				p.getWorld().dropItemNaturally(p.getLocation(), i.getItem(29));
			}
	    	
	    	if (i.getItem(38) != null && i.getItem(38).getType() != Material.AIR) {
				p.getWorld().dropItemNaturally(p.getLocation(), i.getItem(38));
			}
		}
	}
}
