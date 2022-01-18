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
     	if (!e.getInventory().getName().equalsIgnoreCase("���� ����")) return;
     	if (e.getCurrentItem() == null) return;
     	ItemStack i = e.getCurrentItem();
     	
     	if (e.getRawSlot() == 20 || e.getRawSlot() == 22 || e.getRawSlot() == 24) {
     		if (e.getCursor() != null && e.getCursor().getAmount() > 1) {
     			Player p = (Player) e.getWhoClicked();
    			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
     			p.sendMessage("��c������ ������ �����̸� �ø��� �� �����ϴ�.");
     			e.setCancelled(true);
     			return;
     		}
     	}
     	
     	if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && e.getRawSlot() > 37 && i.getAmount() > 1) {
 			Player p = (Player) e.getWhoClicked();
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
 			p.sendMessage("��c������ ������ �����̸� �ø��� �� �����ϴ�.");
 			e.setCancelled(true);
 			return;
     	}

		if (e.getCurrentItem().getItemMeta() != null) {
			ItemMeta m = i.getItemMeta();
			if (m.getDisplayName() != null) {
				if (m.getDisplayName().equalsIgnoreCase("��6������ ���ϼ�")) {
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
		if (!e.getInventory().getName().equalsIgnoreCase("���� ����")) return;
		
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getItem(20) != null) {
			ItemStack i = e.getInventory().getItem(20);
			if (P.getInfoString(p, "1.������ �̸�") != null && P.getInfoList(p, "1.������ ����").equals(i.getItemMeta().getLore())) {
				if (i.getAmount() > 1) {
					if (p.getInventory().firstEmpty() == -1) {
						ItemStack item = i.clone();
		            	item.setAmount(i.getAmount() - 1);
		            	p.getWorld().dropItemNaturally(p.getLocation(), item);
						p.sendMessage("��bù��° ������ �ΰ� �̻��̿��� �ٴڿ� ����Ǿ����ϴ�.");
						return;
					} else {
						ItemStack item = i.clone();
		            	item.setAmount(i.getAmount() - 1);
		    			p.getInventory().addItem(item);
						p.sendMessage("��bù��° ������ �ΰ� �̻��̿��� �κ��丮�� ���޵Ǿ����ϴ�.");
						return;
					}
				}
				
				return;
			} else {
				if (!i.getItemMeta().getDisplayName().contains("������ ���ϼ�")) {
					p.getWorld().dropItemNaturally(p.getLocation(), i);
					return;
				} else {
					P.setInfoInt(p, "1.������ �ڵ�", i.getTypeId());
					P.setInfoString(p, "1.������ �̸�", i.getItemMeta().getDisplayName());
					P.setInfoList(p, "1.������ ����", i.getItemMeta().getLore());
					
					if (i.getAmount() > 1) {
						if (p.getInventory().firstEmpty() == -1) {
							ItemStack item = i.clone();
			            	item.setAmount(i.getAmount() - 1);
			            	p.getWorld().dropItemNaturally(p.getLocation(), item);
							p.sendMessage("��bù��° ������ �ΰ� �̻��̿��� �ٴڿ� ����Ǿ����ϴ�.");
							return;
						} else {
							ItemStack item = i.clone();
			            	item.setAmount(i.getAmount() - 1);
			    			p.getInventory().addItem(item);
							p.sendMessage("��bù��° ������ �ΰ� �̻��̿��� �κ��丮�� ���޵Ǿ����ϴ�.");
							return;
						}
					}

					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);	
					p.sendMessage("��a���������� ù��° ������ �����Ͽ����ϴ�.");
					API.setData(p);
					return;
				}
			}
		} else {
			if (P.getInfoString(p, "1.������ �̸�") != null) {
				p.sendMessage("��c���������� ù��° ������ �����ϼ̽��ϴ�.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
				P.setInfoInt(p, "1.������ �ڵ�", 0);
				P.setInfoString(p, "1.������ �̸�", "NONE");
				P.setInfoList(p, "1.������ ����", Arrays.asList());
				API.removeMapData(p);
				return;
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onInventoryCloseTwo(InventoryCloseEvent e) {
		if (!e.getInventory().getName().equalsIgnoreCase("���� ����")) return;
		
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getItem(22) != null) {
			ItemStack i = e.getInventory().getItem(22);
			if (P.getInfoString(p, "2.������ �̸�") != null && P.getInfoList(p, "2.������ ����").equals(i.getItemMeta().getLore())) {
				if (i.getAmount() > 1) {
					if (p.getInventory().firstEmpty() == -1) {
						ItemStack item = i.clone();
		            	item.setAmount(i.getAmount() - 1);
		            	p.getWorld().dropItemNaturally(p.getLocation(), item);
						p.sendMessage("��b�ι�° ������ �ΰ� �̻��̿��� �ٴڿ� ����Ǿ����ϴ�.");
						return;
					} else {
						ItemStack item = i.clone();
		            	item.setAmount(i.getAmount() - 1);
		    			p.getInventory().addItem(item);
						p.sendMessage("��b�ι�° ������ �ΰ� �̻��̿��� �κ��丮�� ���޵Ǿ����ϴ�.");
						return;
					}
				}
				
				return;
			} else {
				if (!i.getItemMeta().getDisplayName().contains("������ ���ϼ�")) {
					p.getWorld().dropItemNaturally(p.getLocation(), i);
					return;
				} else {
					P.setInfoInt(p, "2.������ �ڵ�", i.getTypeId());
					P.setInfoString(p, "2.������ �̸�", i.getItemMeta().getDisplayName());
					P.setInfoList(p, "2.������ ����", i.getItemMeta().getLore());
					
					if (i.getAmount() > 1) {
						if (p.getInventory().firstEmpty() == -1) {
							ItemStack item = i.clone();
			            	item.setAmount(i.getAmount() - 1);
			            	p.getWorld().dropItemNaturally(p.getLocation(), item);
							p.sendMessage("��b�ι�° ������ �ΰ� �̻��̿��� �ٴڿ� ����Ǿ����ϴ�.");
							return;
						} else {
							ItemStack item = i.clone();
			            	item.setAmount(i.getAmount() - 1);
			    			p.getInventory().addItem(item);
							p.sendMessage("��b�ι�° ������ �ΰ� �̻��̿��� �κ��丮�� ���޵Ǿ����ϴ�.");
							return;
						}
					}

					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);	
					p.sendMessage("��a���������� �ι�° ������ �����Ͽ����ϴ�.");
					API.setData(p);
					return;
				}
			}
		} else {
			if (P.getInfoString(p, "2.������ �̸�") != null) {
				p.sendMessage("��c���������� �ι�° ������ �����ϼ̽��ϴ�.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
				P.setInfoInt(p, "2.������ �ڵ�", 0);
				P.setInfoString(p, "2.������ �̸�", "NONE");
				P.setInfoList(p, "2.������ ����", Arrays.asList());
				API.removeMapData(p);
				return;
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onInventoryCloseThree(InventoryCloseEvent e) {
		if (!e.getInventory().getName().equalsIgnoreCase("���� ����")) return;
		
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getItem(24) != null) {
			ItemStack i = e.getInventory().getItem(24);
			if (P.getInfoString(p, "3.������ �̸�") != null && P.getInfoList(p, "3.������ ����").equals(i.getItemMeta().getLore())) {
				if (i.getAmount() > 1) {
					if (p.getInventory().firstEmpty() == -1) {
						ItemStack item = i.clone();
		            	item.setAmount(i.getAmount() - 1);
		            	p.getWorld().dropItemNaturally(p.getLocation(), item);
						p.sendMessage("��b����° ������ �ΰ� �̻��̿��� �ٴڿ� ����Ǿ����ϴ�.");
						return;
					} else {
						ItemStack item = i.clone();
		            	item.setAmount(i.getAmount() - 1);
		    			p.getInventory().addItem(item);
						p.sendMessage("��b����° ������ �ΰ� �̻��̿��� �κ��丮�� ���޵Ǿ����ϴ�.");
						return;
					}
				}
				
				return;
			} else {
				if (!i.getItemMeta().getDisplayName().contains("������ ���ϼ�")) {
					p.getWorld().dropItemNaturally(p.getLocation(), i);
					return;
				} else {
					P.setInfoInt(p, "3.������ �ڵ�", i.getTypeId());
					P.setInfoString(p, "3.������ �̸�", i.getItemMeta().getDisplayName());
					P.setInfoList(p, "3.������ ����", i.getItemMeta().getLore());
					
					if (i.getAmount() > 1) {
						if (p.getInventory().firstEmpty() == -1) {
							ItemStack item = i.clone();
			            	item.setAmount(i.getAmount() - 1);
			            	p.getWorld().dropItemNaturally(p.getLocation(), item);
							p.sendMessage("��b����° ������ �ΰ� �̻��̿��� �ٴڿ� ����Ǿ����ϴ�.");
							return;
						} else {
							ItemStack item = i.clone();
			            	item.setAmount(i.getAmount() - 1);
			    			p.getInventory().addItem(item);
							p.sendMessage("��b����° ������ �ΰ� �̻��̿��� �κ��丮�� ���޵Ǿ����ϴ�.");
							return;
						}
					}

					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);	
					p.sendMessage("��a���������� ����° ������ �����Ͽ����ϴ�.");
					API.setData(p);
					return;
				}
			}
		} else {
			if (P.getInfoString(p, "3.������ �̸�") != null) {
				p.sendMessage("��c���������� ����° ������ �����ϼ̽��ϴ�.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
				P.setInfoInt(p, "3.������ �ڵ�", 0);
				P.setInfoString(p, "3.������ �̸�", "NONE");
				P.setInfoList(p, "3.������ ����", Arrays.asList());
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
							if (str.contains("ġ��Ÿ Ȯ��")) {
								cretprobe += Integer.parseInt(ChatColor.stripColor(str.split(" ")[0]).replaceAll("%", ""));
							}
							
							else if (str.contains("ġ��Ÿ ������")) {
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
					p.sendMessage("��f- ��cȸ�ǰ� 100 �̻��� ȸ�� ������ ������� �ʽ��ϴ�.");
					p.sendMessage("��cä��â�� /���� �� �Է��Ͽ� ������ ��ü���ֽñ� �ٶ��ϴ�.");
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
				p.sendMessage("��f- ��c������ ���Ұ� 100 �̻��� ������ ���� ������ ������� �ʽ��ϴ�.");
				p.sendMessage("��cä��â�� /���� �� �Է��Ͽ� ������ ��ü���ֽñ� �ٶ��ϴ�.");
				e.setDamage((int) (i * y));
				return;
			}
			
			e.setDamage((int) (i * y));
		}
	}
}
