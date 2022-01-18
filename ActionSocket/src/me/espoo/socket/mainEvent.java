package me.espoo.socket;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.nisovin.magicspells.spells.targeted.PainSpell;

import me.nighteyes604.LoreAttributes.LoreAttributes;
import me.nighteyes604.LoreAttributes.LoreManager;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	PlayerYml P;
	
	public mainEvent(main main)
	{
		this.M = main;
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File f = new File("plugins/ActionSocket/Player/" + p.getName() + ".yml");
		if (!f.exists()) {
			File folder = new File("plugins/ActionSocket");
			File folder2 = new File("plugins/ActionSocket/Player");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
			P.CreatePlayerInfo(f, folder, folder2, config);
		}
		
		API.setData(p);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		API.removeMapData(e.getPlayer());
	}

	@EventHandler
    public void onCancelClick(InventoryClickEvent e) {
     	if (!e.getInventory().getName().equalsIgnoreCase("소켓 장착")) return;
     	if (e.getCurrentItem() == null) return;
     	ItemStack i = e.getCurrentItem();
     	
     	if (e.getRawSlot() == 20 || e.getRawSlot() == 22 || e.getRawSlot() == 24) {
     		if (e.getCursor() != null && e.getCursor().getAmount() > 1) {
     			Player p = (Player) e.getWhoClicked();
    			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
     			p.sendMessage("§c소켓이 겹쳐진 상태이면 올리실 수 없습니다.");
     			e.setCancelled(true);
     			return;
     		}
     	}
     	
     	if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && e.getRawSlot() > 37 && i.getAmount() > 1) {
 			Player p = (Player) e.getWhoClicked();
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
 			p.sendMessage("§c소켓이 겹쳐진 상태이면 올리실 수 없습니다.");
 			e.setCancelled(true);
 			return;
     	}

		if (e.getCurrentItem().getItemMeta() != null) {
			ItemMeta m = i.getItemMeta();
			if (m.getDisplayName() != null) {
				if (m.getDisplayName().equalsIgnoreCase("§6감정된 소켓석")) {
					return;
				} else e.setCancelled(true);
			} else e.setCancelled(true);
		} 
		
		else if (i.getType() == Material.AIR) return;
		else e.setCancelled(true);
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onInventoryCloseOne(InventoryCloseEvent e) {
		if (!e.getInventory().getName().equalsIgnoreCase("소켓 장착")) return;
		
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getItem(20) != null) {
			ItemStack i = e.getInventory().getItem(20);
			if (P.getInfoString(p, "1.아이템 이름") != null && P.getInfoList(p, "1.아이템 설명").equals(i.getItemMeta().getLore())) {
				if (i.getAmount() > 1) {
					if (p.getInventory().firstEmpty() == -1) {
						ItemStack item = i.clone();
		            	item.setAmount(i.getAmount() - 1);
		            	p.getWorld().dropItemNaturally(p.getLocation(), item);
						p.sendMessage("§b첫번째 소켓이 두개 이상이여서 바닥에 드랍되었습니다.");
						return;
					} else {
						ItemStack item = i.clone();
		            	item.setAmount(i.getAmount() - 1);
		    			p.getInventory().addItem(item);
						p.sendMessage("§b첫번째 소켓이 두개 이상이여서 인벤토리에 지급되었습니다.");
						return;
					}
				}
				
				return;
			} else {
				if (!i.getItemMeta().getDisplayName().contains("감정된 소켓석")) {
					p.getWorld().dropItemNaturally(p.getLocation(), i);
					return;
				} else {
					P.setInfoInt(p, "1.아이템 코드", i.getTypeId());
					P.setInfoString(p, "1.아이템 이름", i.getItemMeta().getDisplayName());
					P.setInfoList(p, "1.아이템 설명", i.getItemMeta().getLore());
					
					if (i.getAmount() > 1) {
						if (p.getInventory().firstEmpty() == -1) {
							ItemStack item = i.clone();
			            	item.setAmount(i.getAmount() - 1);
			            	p.getWorld().dropItemNaturally(p.getLocation(), item);
							p.sendMessage("§b첫번째 소켓이 두개 이상이여서 바닥에 드랍되었습니다.");
							return;
						} else {
							ItemStack item = i.clone();
			            	item.setAmount(i.getAmount() - 1);
			    			p.getInventory().addItem(item);
							p.sendMessage("§b첫번째 소켓이 두개 이상이여서 인벤토리에 지급되었습니다.");
							return;
						}
					}

					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);	
					p.sendMessage("§a성공적으로 첫번째 소켓을 장착하였습니다.");
					API.setData(p);
					return;
				}
			}
		} else {
			if (P.getInfoString(p, "1.아이템 이름") != null) {
				p.sendMessage("§c성공적으로 첫번째 소켓을 해제하셨습니다.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
				P.setInfoInt(p, "1.아이템 코드", 0);
				P.setInfoString(p, "1.아이템 이름", "NONE");
				P.setInfoList(p, "1.아이템 설명", Arrays.asList());
				API.removeMapData(p);
				return;
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onInventoryCloseTwo(InventoryCloseEvent e) {
		if (!e.getInventory().getName().equalsIgnoreCase("소켓 장착")) return;
		
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getItem(22) != null) {
			ItemStack i = e.getInventory().getItem(22);
			if (P.getInfoString(p, "2.아이템 이름") != null && P.getInfoList(p, "2.아이템 설명").equals(i.getItemMeta().getLore())) {
				if (i.getAmount() > 1) {
					if (p.getInventory().firstEmpty() == -1) {
						ItemStack item = i.clone();
		            	item.setAmount(i.getAmount() - 1);
		            	p.getWorld().dropItemNaturally(p.getLocation(), item);
						p.sendMessage("§b두번째 소켓이 두개 이상이여서 바닥에 드랍되었습니다.");
						return;
					} else {
						ItemStack item = i.clone();
		            	item.setAmount(i.getAmount() - 1);
		    			p.getInventory().addItem(item);
						p.sendMessage("§b두번째 소켓이 두개 이상이여서 인벤토리에 지급되었습니다.");
						return;
					}
				}
				
				return;
			} else {
				if (!i.getItemMeta().getDisplayName().contains("감정된 소켓석")) {
					p.getWorld().dropItemNaturally(p.getLocation(), i);
					return;
				} else {
					P.setInfoInt(p, "2.아이템 코드", i.getTypeId());
					P.setInfoString(p, "2.아이템 이름", i.getItemMeta().getDisplayName());
					P.setInfoList(p, "2.아이템 설명", i.getItemMeta().getLore());
					
					if (i.getAmount() > 1) {
						if (p.getInventory().firstEmpty() == -1) {
							ItemStack item = i.clone();
			            	item.setAmount(i.getAmount() - 1);
			            	p.getWorld().dropItemNaturally(p.getLocation(), item);
							p.sendMessage("§b두번째 소켓이 두개 이상이여서 바닥에 드랍되었습니다.");
							return;
						} else {
							ItemStack item = i.clone();
			            	item.setAmount(i.getAmount() - 1);
			    			p.getInventory().addItem(item);
							p.sendMessage("§b두번째 소켓이 두개 이상이여서 인벤토리에 지급되었습니다.");
							return;
						}
					}

					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);	
					p.sendMessage("§a성공적으로 두번째 소켓을 장착하였습니다.");
					API.setData(p);
					return;
				}
			}
		} else {
			if (P.getInfoString(p, "2.아이템 이름") != null) {
				p.sendMessage("§c성공적으로 두번째 소켓을 해제하셨습니다.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
				P.setInfoInt(p, "2.아이템 코드", 0);
				P.setInfoString(p, "2.아이템 이름", "NONE");
				P.setInfoList(p, "2.아이템 설명", Arrays.asList());
				API.removeMapData(p);
				return;
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onInventoryCloseThree(InventoryCloseEvent e) {
		if (!e.getInventory().getName().equalsIgnoreCase("소켓 장착")) return;
		
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getItem(24) != null) {
			ItemStack i = e.getInventory().getItem(24);
			if (P.getInfoString(p, "3.아이템 이름") != null && P.getInfoList(p, "3.아이템 설명").equals(i.getItemMeta().getLore())) {
				if (i.getAmount() > 1) {
					if (p.getInventory().firstEmpty() == -1) {
						ItemStack item = i.clone();
		            	item.setAmount(i.getAmount() - 1);
		            	p.getWorld().dropItemNaturally(p.getLocation(), item);
						p.sendMessage("§b세번째 소켓이 두개 이상이여서 바닥에 드랍되었습니다.");
						return;
					} else {
						ItemStack item = i.clone();
		            	item.setAmount(i.getAmount() - 1);
		    			p.getInventory().addItem(item);
						p.sendMessage("§b세번째 소켓이 두개 이상이여서 인벤토리에 지급되었습니다.");
						return;
					}
				}
				
				return;
			} else {
				if (!i.getItemMeta().getDisplayName().contains("감정된 소켓석")) {
					p.getWorld().dropItemNaturally(p.getLocation(), i);
					return;
				} else {
					P.setInfoInt(p, "3.아이템 코드", i.getTypeId());
					P.setInfoString(p, "3.아이템 이름", i.getItemMeta().getDisplayName());
					P.setInfoList(p, "3.아이템 설명", i.getItemMeta().getLore());
					
					if (i.getAmount() > 1) {
						if (p.getInventory().firstEmpty() == -1) {
							ItemStack item = i.clone();
			            	item.setAmount(i.getAmount() - 1);
			            	p.getWorld().dropItemNaturally(p.getLocation(), item);
							p.sendMessage("§b세번째 소켓이 두개 이상이여서 바닥에 드랍되었습니다.");
							return;
						} else {
							ItemStack item = i.clone();
			            	item.setAmount(i.getAmount() - 1);
			    			p.getInventory().addItem(item);
							p.sendMessage("§b세번째 소켓이 두개 이상이여서 인벤토리에 지급되었습니다.");
							return;
						}
					}

					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);	
					p.sendMessage("§a성공적으로 세번째 소켓을 장착하였습니다.");
					API.setData(p);
					return;
				}
			}
		} else {
			if (P.getInfoString(p, "3.아이템 이름") != null) {
				p.sendMessage("§c성공적으로 세번째 소켓을 해제하셨습니다.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
				P.setInfoInt(p, "3.아이템 코드", 0);
				P.setInfoString(p, "3.아이템 이름", "NONE");
				P.setInfoList(p, "3.아이템 설명", Arrays.asList());
				API.removeMapData(p);
				return;
			}
		}
	}
	
	@EventHandler(ignoreCancelled=true)
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
			Player p = (Player) e.getDamager();
			LoreManager L = LoreAttributes.loreManager;

			if (PainSpell.playerCheckPluginsplayer.contains(p)) {
				PainSpell.playerCheckPluginsplayer.remove(p);
				return;
			}
			
			int damage = 0;
			if (main.damage.containsKey(p)) damage = main.damage.get(p);
			int cretprobe = 0;
			cretprobe += L.getCriticalChance(p);
			if (main.cretprobe.containsKey(p)) cretprobe = main.cretprobe.get(p);
			
			int cretdamage = 0;
			cretdamage += L.getCriticalDamage(p);
			if (main.cretdamage.containsKey(p)) cretdamage = main.cretdamage.get(p);
			int heart = 0;
			if (main.heart.containsKey(p)) heart = main.heart.get(p);
			
			if (damage + cretprobe + cretdamage + heart == 0) return;
			
			int num = new Random().nextInt(100) + 1;
			
			if (p.getItemInHand() != null) {
				ItemStack i = p.getItemInHand();
				
				if (i.hasItemMeta()) {
					if (!i.getItemMeta().hasLore()) {
						for (String str : i.getItemMeta().getLore()) {
							if (str.contains("치명타 확률")) {
								cretprobe += Integer.parseInt(ChatColor.stripColor(str.split(" ")[0]).replaceAll("%", ""));
							}
							
							else if (str.contains("치명타 데미지")) {
								cretdamage += Integer.parseInt(ChatColor.stripColor(str.split(" ")[0]));
							}
						}
					}
				}
			}
			
			if (cretprobe != 0) {
				if (cretprobe >= num || cretprobe >= 100) {
					damage += cretdamage;
					if (main.cret.get(p.getName()) == null) main.cret.put(p.getName(), "true");
				}
			}
			
			if (p.getHealth() + heart > p.getMaxHealth()) {
				p.setHealth(p.getMaxHealth());
			} else {
				p.setHealth(p.getHealth() + heart);
			}
			
			int i = e.getDamage() + damage;
			
			if (i <= 0) e.setDamage(0);
			else e.setDamage(i);
		}
		
		if (e.getEntity() instanceof Player && e.getDamager() instanceof LivingEntity) {
			Player p = (Player) e.getEntity();
			LoreManager L = LoreAttributes.loreManager;
			
			int defense = 0;
			defense += L.getArmour(p);
			if (main.defense.containsKey(p)) defense = main.defense.get(p);
			int dodge = 0;
			if (main.dodge.containsKey(p)) dodge = main.dodge.get(p);
			
			if (defense + dodge == 0) return;
			
			int num = new Random().nextInt(100) + 1;
			
			double i = e.getDamage() / 100D;
			double y = 100 - defense;
			
			if (dodge != 0) {
				if (dodge >= 100) {
					p.sendMessage("");
					p.sendMessage("§f- §c회피가 100 이상은 회피 소켓이 적용되지 않습니다.");
					p.sendMessage("§c채팅창에 /소켓 을 입력하여 소켓을 교체해주시길 바랍니다.");
					e.setDamage((int) (i * y));
					return;
				}
				
				if (dodge >= num) {
					p.getWorld().playSound(p.getLocation(), Sound.FIZZ, 1.0F, 10.0F);
					e.getEntity().getWorld().playEffect(e.getEntity().getLocation(), Effect.SMOKE, 5);
					e.setCancelled(true);
					return;
				}
			}
			
			if (dodge >= 100) {
				p.sendMessage("");
				p.sendMessage("§f- §c데미지 감소가 100 이상은 데미지 감소 소켓이 적용되지 않습니다.");
				p.sendMessage("§c채팅창에 /소켓 을 입력하여 소켓을 교체해주시길 바랍니다.");
				e.setDamage((int) (i * y));
				return;
			}
			
			e.setDamage((int) (i * y));
		}
	}
}
