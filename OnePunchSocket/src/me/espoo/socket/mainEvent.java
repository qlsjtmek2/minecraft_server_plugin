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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

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
		File folder = new File("plugins/OnePunchSocket");
		File folder2 = new File("plugins/OnePunchSocket/Player");
		File f = new File("plugins/OnePunchSocket/Player/" + p.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) P.CreatePlayerInfo(f, folder, folder2, config);
	}

	@EventHandler
    public void onCancelClick(InventoryClickEvent e) {
     	if (!e.getInventory().getName().equalsIgnoreCase("소켓 장착")) return;
     	if (e.getCurrentItem() == null) return;
     	
     	ItemStack i = e.getCurrentItem();
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
	public void onInventoryClose(InventoryCloseEvent e) {
		if (!e.getInventory().getName().equalsIgnoreCase("소켓 장착")) return;
		
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getItem(20) != null) {
			ItemStack i = e.getInventory().getItem(20);
			if (P.getInfoInt(p, "1.아이템 코드") != i.getTypeId() 
			 || !P.getInfoString(p, "1.아이템 이름").equalsIgnoreCase(i.getItemMeta().getDisplayName()) 
			 || !P.getInfoList(p, "1.아이템 설명").equals(i.getItemMeta().getLore())) {
				P.setInfoInt(p, "1.아이템 코드", i.getTypeId());
				P.setInfoString(p, "1.아이템 이름", i.getItemMeta().getDisplayName());
				P.setInfoList(p, "1.아이템 설명", i.getItemMeta().getLore());
				if (i.getAmount() != 1) {
		            int t = 0;
		            ItemStack[] contents;
		            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
		                ItemStack it = contents[j];
		                if (it == null) {
		                    ++t;
		                }
		            }
		            
		            if (t < 1) {
		            	ItemStack item = i;
		            	item.setAmount(i.getAmount() - 1);
		            	p.getWorld().dropItemNaturally(p.getLocation(), item);
						p.sendMessage("§b첫번째 소켓이 두개 이상이여서 바닥에 드랍되었습니다.");
		            } else {
		            	ItemStack item = i;
		            	item.setAmount(i.getAmount() - 1);
		    			p.getInventory().addItem(item);
						p.sendMessage("§b첫번째 소켓이 두개 이상이여서 인벤토리에 지급되었습니다.");
		            }
				} else {
					p.sendMessage("§a성공적으로 첫번째 소켓을 장착하였습니다.");
				}
				
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);	
			}
		} else {
			if (P.getInfoInt(p, "1.아이템 코드") != 0 || P.getInfoString(p, "1.아이템 이름") != null || !P.getInfoList(p, "1.아이템 설명").equals(Arrays.asList())) {
				p.sendMessage("§c성공적으로 첫번째 소켓을 해제하셨습니다.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
				P.setInfoInt(p, "1.아이템 코드", 0);
				P.setInfoString(p, "1.아이템 이름", "NONE");
				P.setInfoList(p, "1.아이템 설명", Arrays.asList());
			}
		}
		
		if (e.getInventory().getItem(22) != null) {
			ItemStack i = e.getInventory().getItem(22);
			if (P.getInfoInt(p, "2.아이템 코드") != i.getTypeId() 
		    || !P.getInfoString(p, "2.아이템 이름").equalsIgnoreCase(i.getItemMeta().getDisplayName()) 
			|| !P.getInfoList(p, "2.아이템 설명").equals(i.getItemMeta().getLore())) {
				P.setInfoInt(p, "2.아이템 코드", i.getTypeId());
				P.setInfoString(p, "2.아이템 이름", i.getItemMeta().getDisplayName());
				P.setInfoList(p, "2.아이템 설명", i.getItemMeta().getLore());
				if (i.getAmount() != 1) {
		            int t = 0;
		            ItemStack[] contents;
		            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
		                ItemStack it = contents[j];
		                if (it == null) {
		                    ++t;
		                }
		            }
		            
		            if (t < 1) {
		            	ItemStack item = i;
		            	item.setAmount(i.getAmount() - 1);
		            	p.getWorld().dropItemNaturally(p.getLocation(), item);
						p.sendMessage("§b두번째 소켓이 두개 이상이여서 바닥에 드랍되었습니다.");
		            } else {
		            	ItemStack item = i;
		            	item.setAmount(i.getAmount() - 1);
		    			p.getInventory().addItem(item);
						p.sendMessage("§b두번째 소켓이 두개 이상이여서 인벤토리에 지급되었습니다.");
		            }
				} else {
					p.sendMessage("§a성공적으로 두번째 소켓을 장착하였습니다.");
				}
				
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 2.0F);
			}
		} else {
			if (P.getInfoInt(p, "2.아이템 코드") != 0 || P.getInfoString(p, "2.아이템 이름") != null || !P.getInfoList(p, "2.아이템 설명").equals(Arrays.asList())) {
				p.sendMessage("§c성공적으로 두번째 소켓을 해제하셨습니다.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 2.0F);
				P.setInfoInt(p, "2.아이템 코드", 0);
				P.setInfoString(p, "2.아이템 이름", "NONE");
				P.setInfoList(p, "2.아이템 설명", Arrays.asList());
			}
		}
		
		if (e.getInventory().getItem(24) != null) {
			ItemStack i = e.getInventory().getItem(24);
			if (P.getInfoInt(p, "3.아이템 코드") != i.getTypeId() 
			|| !P.getInfoString(p, "3.아이템 이름").equalsIgnoreCase(i.getItemMeta().getDisplayName()) 
			|| !P.getInfoList(p, "3.아이템 설명").equals(i.getItemMeta().getLore())) {
				P.setInfoInt(p, "3.아이템 코드", i.getTypeId());
				P.setInfoString(p, "3.아이템 이름", i.getItemMeta().getDisplayName());
				P.setInfoList(p, "3.아이템 설명", i.getItemMeta().getLore());
				if (i.getAmount() != 1) {
		            int t = 0;
		            ItemStack[] contents;
		            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
		                ItemStack it = contents[j];
		                if (it == null) {
		                    ++t;
		                }
		            }
		            
		            if (t < 1) {
		            	ItemStack item = i;
		            	item.setAmount(i.getAmount() - 1);
		            	p.getWorld().dropItemNaturally(p.getLocation(), item);
						p.sendMessage("§b세번째 소켓이 두개 이상이여서 바닥에 드랍되었습니다.");
		            } else {
		            	ItemStack item = i;
		            	item.setAmount(i.getAmount() - 1);
		    			p.getInventory().addItem(item);
						p.sendMessage("§b세번째 소켓이 두개 이상이여서 인벤토리에 지급되었습니다.");
		            }
				} else {
					p.sendMessage("§a성공적으로 세번째 소켓을 장착하였습니다.");
				}
				
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 2.0F);
			}
		} else {
			if (P.getInfoInt(p, "3.아이템 코드") != 0 || P.getInfoString(p, "3.아이템 이름") != null || !P.getInfoList(p, "3.아이템 설명").equals(Arrays.asList())) {
				p.sendMessage("§c성공적으로 세번째 소켓을 해제하셨습니다.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 2.0F);
				P.setInfoInt(p, "3.아이템 코드", 0);
				P.setInfoString(p, "3.아이템 이름", "NONE");
				P.setInfoList(p, "3.아이템 설명", Arrays.asList());
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
			Player p = (Player) e.getDamager();
			int damage = 0;
			int cretprobe = 0;
			int cretdamage = 0;
			int heart = 0;
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

			if (PlayerYml.getInfoInt(p, "1.아이템 코드") != 0 && PlayerYml.getInfoString(p, "1.아이템 이름") != null && !PlayerYml.getInfoList(p, "1.아이템 설명").equals(Arrays.asList())) {
				String str = API.getLoreNum(PlayerYml.getInfoList(p, "1.아이템 설명"));

				if (str.equalsIgnoreCase("추가 데미지")) {
					damage += API.getPlusDamage(PlayerYml.getInfoList(p, "1.아이템 설명"));
				}
				
				else if (str.equalsIgnoreCase("크리티컬 확률")) {
					cretprobe += API.getCretProbe(PlayerYml.getInfoList(p, "1.아이템 설명"));
				}
				
				else if (str.equalsIgnoreCase("크리티컬 데미지")) {
					cretdamage += API.getCretDamage(PlayerYml.getInfoList(p, "1.아이템 설명"));
				}
				
				else if (str.equalsIgnoreCase("체력 흡수")) {
					heart += API.getHeartGet(PlayerYml.getInfoList(p, "1.아이템 설명"));
				}
			}
			
			if (PlayerYml.getInfoInt(p, "2.아이템 코드") != 0 && PlayerYml.getInfoString(p, "2.아이템 이름") != null && !PlayerYml.getInfoList(p, "2.아이템 설명").equals(Arrays.asList())) {
				String str = API.getLoreNum(PlayerYml.getInfoList(p, "2.아이템 설명"));
				if (str.equalsIgnoreCase("추가 데미지")) {
					damage += API.getPlusDamage(PlayerYml.getInfoList(p, "2.아이템 설명"));
				}
				
				else if (str.equalsIgnoreCase("크리티컬 확률")) {
					cretprobe += API.getCretProbe(PlayerYml.getInfoList(p, "2.아이템 설명"));
				}
				
				else if (str.equalsIgnoreCase("크리티컬 데미지")) {
					cretdamage += API.getCretDamage(PlayerYml.getInfoList(p, "2.아이템 설명"));
				}
				
				else if (str.equalsIgnoreCase("체력 흡수")) {
					heart += API.getHeartGet(PlayerYml.getInfoList(p, "2.아이템 설명"));
				}
			}
			
			if (PlayerYml.getInfoInt(p, "3.아이템 코드") != 0 && PlayerYml.getInfoString(p, "3.아이템 이름") != null && !PlayerYml.getInfoList(p, "3.아이템 설명").equals(Arrays.asList())) {
				String str = API.getLoreNum(PlayerYml.getInfoList(p, "3.아이템 설명"));
				if (str.equalsIgnoreCase("추가 데미지")) {
					damage += API.getPlusDamage(PlayerYml.getInfoList(p, "3.아이템 설명"));
				}
				
				else if (str.equalsIgnoreCase("크리티컬 확률")) {
					cretprobe += API.getCretProbe(PlayerYml.getInfoList(p, "3.아이템 설명"));
				}
				
				else if (str.equalsIgnoreCase("크리티컬 데미지")) {
					cretdamage += API.getCretDamage(PlayerYml.getInfoList(p, "3.아이템 설명"));
				}
				
				else if (str.equalsIgnoreCase("체력 흡수")) {
					heart += API.getHeartGet(PlayerYml.getInfoList(p, "3.아이템 설명"));
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
			
			int i = e.getDamage() + damage - heart;
			
			if (i <= 0) e.setDamage(0);
			else e.setDamage(i);
		}
		
		if (e.getEntity() instanceof Player && e.getDamager() instanceof LivingEntity) {
			Player p = (Player) e.getEntity();
			int defense = 0;
			int dodge = 0;
			int num = new Random().nextInt(100) + 1;
			
			if (PlayerYml.getInfoInt(p, "1.아이템 코드") != 0 && PlayerYml.getInfoString(p, "1.아이템 이름") != null && !PlayerYml.getInfoList(p, "1.아이템 설명").equals(Arrays.asList())) {
				String str = API.getLoreNum(PlayerYml.getInfoList(p, "1.아이템 설명"));
				if (str.equalsIgnoreCase("데미지 감소")) {
					defense += API.getDamageDefense(PlayerYml.getInfoList(p, "1.아이템 설명"));
				}
				
				else if (str.equalsIgnoreCase("회피력")) {
					dodge += API.getDodge(PlayerYml.getInfoList(p, "1.아이템 설명"));
				}
			}
			
			if (PlayerYml.getInfoInt(p, "2.아이템 코드") != 0 && PlayerYml.getInfoString(p, "2.아이템 이름") != null && !PlayerYml.getInfoList(p, "2.아이템 설명").equals(Arrays.asList())) {
				String str = API.getLoreNum(PlayerYml.getInfoList(p, "2.아이템 설명"));
				if (str.equalsIgnoreCase("데미지 감소")) {
					defense += API.getDamageDefense(PlayerYml.getInfoList(p, "2.아이템 설명"));
				}
				
				else if (str.equalsIgnoreCase("회피력")) {
					dodge += API.getDodge(PlayerYml.getInfoList(p, "2.아이템 설명"));
				}
			}
			
			if (PlayerYml.getInfoInt(p, "1.아이템 코드") != 0 && PlayerYml.getInfoString(p, "3.아이템 이름") != null && !PlayerYml.getInfoList(p, "3.아이템 설명").equals(Arrays.asList())) {
				String str = API.getLoreNum(PlayerYml.getInfoList(p, "3.아이템 설명"));
				if (str.equalsIgnoreCase("데미지 감소")) {
					defense += API.getDamageDefense(PlayerYml.getInfoList(p, "3.아이템 설명"));
				}
				
				else if (str.equalsIgnoreCase("회피력")) {
					dodge += API.getDodge(PlayerYml.getInfoList(p, "3.아이템 설명"));
				}
			}
			
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
