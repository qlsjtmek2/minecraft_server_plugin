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
     	if (!e.getInventory().getName().equalsIgnoreCase("���� ����")) return;
     	if (e.getCurrentItem() == null) return;
     	
     	ItemStack i = e.getCurrentItem();
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
	public void onInventoryClose(InventoryCloseEvent e) {
		if (!e.getInventory().getName().equalsIgnoreCase("���� ����")) return;
		
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getItem(20) != null) {
			ItemStack i = e.getInventory().getItem(20);
			if (P.getInfoInt(p, "1.������ �ڵ�") != i.getTypeId() 
			 || !P.getInfoString(p, "1.������ �̸�").equalsIgnoreCase(i.getItemMeta().getDisplayName()) 
			 || !P.getInfoList(p, "1.������ ����").equals(i.getItemMeta().getLore())) {
				P.setInfoInt(p, "1.������ �ڵ�", i.getTypeId());
				P.setInfoString(p, "1.������ �̸�", i.getItemMeta().getDisplayName());
				P.setInfoList(p, "1.������ ����", i.getItemMeta().getLore());
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
						p.sendMessage("��bù��° ������ �ΰ� �̻��̿��� �ٴڿ� ����Ǿ����ϴ�.");
		            } else {
		            	ItemStack item = i;
		            	item.setAmount(i.getAmount() - 1);
		    			p.getInventory().addItem(item);
						p.sendMessage("��bù��° ������ �ΰ� �̻��̿��� �κ��丮�� ���޵Ǿ����ϴ�.");
		            }
				} else {
					p.sendMessage("��a���������� ù��° ������ �����Ͽ����ϴ�.");
				}
				
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 1.0F);	
			}
		} else {
			if (P.getInfoInt(p, "1.������ �ڵ�") != 0 || P.getInfoString(p, "1.������ �̸�") != null || !P.getInfoList(p, "1.������ ����").equals(Arrays.asList())) {
				p.sendMessage("��c���������� ù��° ������ �����ϼ̽��ϴ�.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.0F);
				P.setInfoInt(p, "1.������ �ڵ�", 0);
				P.setInfoString(p, "1.������ �̸�", "NONE");
				P.setInfoList(p, "1.������ ����", Arrays.asList());
			}
		}
		
		if (e.getInventory().getItem(22) != null) {
			ItemStack i = e.getInventory().getItem(22);
			if (P.getInfoInt(p, "2.������ �ڵ�") != i.getTypeId() 
		    || !P.getInfoString(p, "2.������ �̸�").equalsIgnoreCase(i.getItemMeta().getDisplayName()) 
			|| !P.getInfoList(p, "2.������ ����").equals(i.getItemMeta().getLore())) {
				P.setInfoInt(p, "2.������ �ڵ�", i.getTypeId());
				P.setInfoString(p, "2.������ �̸�", i.getItemMeta().getDisplayName());
				P.setInfoList(p, "2.������ ����", i.getItemMeta().getLore());
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
						p.sendMessage("��b�ι�° ������ �ΰ� �̻��̿��� �ٴڿ� ����Ǿ����ϴ�.");
		            } else {
		            	ItemStack item = i;
		            	item.setAmount(i.getAmount() - 1);
		    			p.getInventory().addItem(item);
						p.sendMessage("��b�ι�° ������ �ΰ� �̻��̿��� �κ��丮�� ���޵Ǿ����ϴ�.");
		            }
				} else {
					p.sendMessage("��a���������� �ι�° ������ �����Ͽ����ϴ�.");
				}
				
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 2.0F);
			}
		} else {
			if (P.getInfoInt(p, "2.������ �ڵ�") != 0 || P.getInfoString(p, "2.������ �̸�") != null || !P.getInfoList(p, "2.������ ����").equals(Arrays.asList())) {
				p.sendMessage("��c���������� �ι�° ������ �����ϼ̽��ϴ�.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 2.0F);
				P.setInfoInt(p, "2.������ �ڵ�", 0);
				P.setInfoString(p, "2.������ �̸�", "NONE");
				P.setInfoList(p, "2.������ ����", Arrays.asList());
			}
		}
		
		if (e.getInventory().getItem(24) != null) {
			ItemStack i = e.getInventory().getItem(24);
			if (P.getInfoInt(p, "3.������ �ڵ�") != i.getTypeId() 
			|| !P.getInfoString(p, "3.������ �̸�").equalsIgnoreCase(i.getItemMeta().getDisplayName()) 
			|| !P.getInfoList(p, "3.������ ����").equals(i.getItemMeta().getLore())) {
				P.setInfoInt(p, "3.������ �ڵ�", i.getTypeId());
				P.setInfoString(p, "3.������ �̸�", i.getItemMeta().getDisplayName());
				P.setInfoList(p, "3.������ ����", i.getItemMeta().getLore());
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
						p.sendMessage("��b����° ������ �ΰ� �̻��̿��� �ٴڿ� ����Ǿ����ϴ�.");
		            } else {
		            	ItemStack item = i;
		            	item.setAmount(i.getAmount() - 1);
		    			p.getInventory().addItem(item);
						p.sendMessage("��b����° ������ �ΰ� �̻��̿��� �κ��丮�� ���޵Ǿ����ϴ�.");
		            }
				} else {
					p.sendMessage("��a���������� ����° ������ �����Ͽ����ϴ�.");
				}
				
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_PICKUP, 2.0F, 2.0F);
			}
		} else {
			if (P.getInfoInt(p, "3.������ �ڵ�") != 0 || P.getInfoString(p, "3.������ �̸�") != null || !P.getInfoList(p, "3.������ ����").equals(Arrays.asList())) {
				p.sendMessage("��c���������� ����° ������ �����ϼ̽��ϴ�.");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.getWorld().playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 2.0F);
				P.setInfoInt(p, "3.������ �ڵ�", 0);
				P.setInfoString(p, "3.������ �̸�", "NONE");
				P.setInfoList(p, "3.������ ����", Arrays.asList());
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

			if (PlayerYml.getInfoInt(p, "1.������ �ڵ�") != 0 && PlayerYml.getInfoString(p, "1.������ �̸�") != null && !PlayerYml.getInfoList(p, "1.������ ����").equals(Arrays.asList())) {
				String str = API.getLoreNum(PlayerYml.getInfoList(p, "1.������ ����"));

				if (str.equalsIgnoreCase("�߰� ������")) {
					damage += API.getPlusDamage(PlayerYml.getInfoList(p, "1.������ ����"));
				}
				
				else if (str.equalsIgnoreCase("ũ��Ƽ�� Ȯ��")) {
					cretprobe += API.getCretProbe(PlayerYml.getInfoList(p, "1.������ ����"));
				}
				
				else if (str.equalsIgnoreCase("ũ��Ƽ�� ������")) {
					cretdamage += API.getCretDamage(PlayerYml.getInfoList(p, "1.������ ����"));
				}
				
				else if (str.equalsIgnoreCase("ü�� ���")) {
					heart += API.getHeartGet(PlayerYml.getInfoList(p, "1.������ ����"));
				}
			}
			
			if (PlayerYml.getInfoInt(p, "2.������ �ڵ�") != 0 && PlayerYml.getInfoString(p, "2.������ �̸�") != null && !PlayerYml.getInfoList(p, "2.������ ����").equals(Arrays.asList())) {
				String str = API.getLoreNum(PlayerYml.getInfoList(p, "2.������ ����"));
				if (str.equalsIgnoreCase("�߰� ������")) {
					damage += API.getPlusDamage(PlayerYml.getInfoList(p, "2.������ ����"));
				}
				
				else if (str.equalsIgnoreCase("ũ��Ƽ�� Ȯ��")) {
					cretprobe += API.getCretProbe(PlayerYml.getInfoList(p, "2.������ ����"));
				}
				
				else if (str.equalsIgnoreCase("ũ��Ƽ�� ������")) {
					cretdamage += API.getCretDamage(PlayerYml.getInfoList(p, "2.������ ����"));
				}
				
				else if (str.equalsIgnoreCase("ü�� ���")) {
					heart += API.getHeartGet(PlayerYml.getInfoList(p, "2.������ ����"));
				}
			}
			
			if (PlayerYml.getInfoInt(p, "3.������ �ڵ�") != 0 && PlayerYml.getInfoString(p, "3.������ �̸�") != null && !PlayerYml.getInfoList(p, "3.������ ����").equals(Arrays.asList())) {
				String str = API.getLoreNum(PlayerYml.getInfoList(p, "3.������ ����"));
				if (str.equalsIgnoreCase("�߰� ������")) {
					damage += API.getPlusDamage(PlayerYml.getInfoList(p, "3.������ ����"));
				}
				
				else if (str.equalsIgnoreCase("ũ��Ƽ�� Ȯ��")) {
					cretprobe += API.getCretProbe(PlayerYml.getInfoList(p, "3.������ ����"));
				}
				
				else if (str.equalsIgnoreCase("ũ��Ƽ�� ������")) {
					cretdamage += API.getCretDamage(PlayerYml.getInfoList(p, "3.������ ����"));
				}
				
				else if (str.equalsIgnoreCase("ü�� ���")) {
					heart += API.getHeartGet(PlayerYml.getInfoList(p, "3.������ ����"));
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
			
			if (PlayerYml.getInfoInt(p, "1.������ �ڵ�") != 0 && PlayerYml.getInfoString(p, "1.������ �̸�") != null && !PlayerYml.getInfoList(p, "1.������ ����").equals(Arrays.asList())) {
				String str = API.getLoreNum(PlayerYml.getInfoList(p, "1.������ ����"));
				if (str.equalsIgnoreCase("������ ����")) {
					defense += API.getDamageDefense(PlayerYml.getInfoList(p, "1.������ ����"));
				}
				
				else if (str.equalsIgnoreCase("ȸ�Ƿ�")) {
					dodge += API.getDodge(PlayerYml.getInfoList(p, "1.������ ����"));
				}
			}
			
			if (PlayerYml.getInfoInt(p, "2.������ �ڵ�") != 0 && PlayerYml.getInfoString(p, "2.������ �̸�") != null && !PlayerYml.getInfoList(p, "2.������ ����").equals(Arrays.asList())) {
				String str = API.getLoreNum(PlayerYml.getInfoList(p, "2.������ ����"));
				if (str.equalsIgnoreCase("������ ����")) {
					defense += API.getDamageDefense(PlayerYml.getInfoList(p, "2.������ ����"));
				}
				
				else if (str.equalsIgnoreCase("ȸ�Ƿ�")) {
					dodge += API.getDodge(PlayerYml.getInfoList(p, "2.������ ����"));
				}
			}
			
			if (PlayerYml.getInfoInt(p, "1.������ �ڵ�") != 0 && PlayerYml.getInfoString(p, "3.������ �̸�") != null && !PlayerYml.getInfoList(p, "3.������ ����").equals(Arrays.asList())) {
				String str = API.getLoreNum(PlayerYml.getInfoList(p, "3.������ ����"));
				if (str.equalsIgnoreCase("������ ����")) {
					defense += API.getDamageDefense(PlayerYml.getInfoList(p, "3.������ ����"));
				}
				
				else if (str.equalsIgnoreCase("ȸ�Ƿ�")) {
					dodge += API.getDodge(PlayerYml.getInfoList(p, "3.������ ����"));
				}
			}
			
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
