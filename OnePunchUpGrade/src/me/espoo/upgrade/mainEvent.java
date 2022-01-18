package me.espoo.upgrade;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.rpg.guild.GuildAPI;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler
    public void onClickItem(InventoryClickEvent e) {
    	Player p = (Player) e.getWhoClicked();
    	//for (String str : e.getCurrentItem().getItemMeta().getLore()) {
    	//	System.out.println(str);
    //	}
    	//System.out.println(e.getCurrentItem().getItemMeta().getDisplayName());
    	//e.setCancelled(true);
    	if (e.getInventory().getName().equalsIgnoreCase(GUIMessage.getMessage(p, "���� ��ȭ GUI �̸�"))) {
    		if (e.getCurrentItem() != null) {
    			ItemStack i = e.getCurrentItem();
    			if (e.getCurrentItem().getItemMeta() != null) {
    				ItemMeta m = i.getItemMeta();
    				if (m.getDisplayName() != null) {
    					if (ChatColor.stripColor(i.getItemMeta().getDisplayName()).equalsIgnoreCase("��ź ���") 
						 || ChatColor.stripColor(i.getItemMeta().getDisplayName()).equalsIgnoreCase("�״� ���� ���")
						 || ChatColor.stripColor(i.getItemMeta().getDisplayName()).equalsIgnoreCase("ö ���� ���")
						 || ChatColor.stripColor(i.getItemMeta().getDisplayName()).equalsIgnoreCase("�� ���� ���")
						 || ChatColor.stripColor(i.getItemMeta().getDisplayName()).equalsIgnoreCase("���̾Ƹ�� ���� ���")
						 || ChatColor.stripColor(i.getItemMeta().getDisplayName()).equalsIgnoreCase("���޶��� ���� ���")
						 || ChatColor.stripColor(i.getItemMeta().getDisplayName()).equalsIgnoreCase("��ź ���� ���")
						 || ChatColor.stripColor(i.getItemMeta().getDisplayName()).equalsIgnoreCase("�״� ���� ���� ���")
						 || ChatColor.stripColor(i.getItemMeta().getDisplayName()).equalsIgnoreCase("���彺�� ���� ���")
						 || ChatColor.stripColor(i.getItemMeta().getDisplayName()).equalsIgnoreCase("û�ݼ� ���� ���")) {
    						Timer timer1 = new Timer();
    						Date timeToRun = new Date(System.currentTimeMillis() + 50);
    						timer1.schedule(new TimerTask() {
    							public void run() {
    								if (p.getOpenInventory().getItem(10) != null) {
    									GUI.updateGUI(p, p.getOpenInventory());
    								} return;
    							}
    						}, timeToRun);
    						e.setCancelled(false);
    					} else {
        					for (int num = 1; ; num++) {
        	    				if (UpGrade.getUpGradeString(num + ".������ �̸�") == null) {
        	    					e.setCancelled(true);
        	    					break;
        	    				}
        	    				
        	    				if (ChatColor.stripColor(UpGrade.getUpGradeString(num + ".������ �̸�")).equalsIgnoreCase(ChatColor.stripColor(m.getDisplayName()))) {
            						Timer timer1 = new Timer();
            						Date timeToRun = new Date(System.currentTimeMillis() + 50);
            						timer1.schedule(new TimerTask() {
            							public void run() {
            	        					if (p.getOpenInventory().getItem(10) != null) {
            	        						GUI.updateGUI(p, p.getOpenInventory());
            	        					} return;
            							}
            						}, timeToRun);
        	    					break;
        	    				}
        	    				
        	    				if (p.getItemOnCursor() != null) {
        	    					if (p.getItemOnCursor().getItemMeta() != null) {
        	    						if (ChatColor.stripColor(UpGrade.getUpGradeString(num + ".������ �̸�")).equalsIgnoreCase(ChatColor.stripColor(p.getItemOnCursor().getItemMeta().getDisplayName()))) {
        	    	    					break;
        	    						}
            	    				}
        	    				}
        					}
    					}
    				} else {
    					if (i.getTypeId() == 406 || i.getTypeId() == 388 || i.getTypeId() == 263 || i.getTypeId() == 264 || i.getTypeId() == 331 || 
    						i.getTypeId() == 351 && i.getData().getData() == 4 || i.getTypeId() == 266 || i.getTypeId() == 265 || i.getTypeId() == 42 || 
    						i.getTypeId() == 41 || i.getTypeId() == 57 || i.getTypeId() == 133 || i.getTypeId() == 152 || i.getTypeId() == 22 || i.getTypeId() == 4) {
    						Timer timer1 = new Timer();
    						Date timeToRun = new Date(System.currentTimeMillis() + 50);
    						timer1.schedule(new TimerTask() {
    							public void run() {
    	        					if (p.getOpenInventory().getItem(10) != null) {
    	        						GUI.updateGUI(p, p.getOpenInventory());
    	        					} return;
    							}
    						}, timeToRun);
        					e.setCancelled(false);
    					} else {
        					e.setCancelled(true);
    					}
    				}
    			} else {
    				Timer timer1 = new Timer();
    				Date timeToRun = new Date(System.currentTimeMillis() + 50);
    				timer1.schedule(new TimerTask() {
    					public void run() {
        					if (p.getOpenInventory().getItem(10) != null) {
        						GUI.updateGUI(p, p.getOpenInventory());
        					} return;
    					}
    				}, timeToRun);
    			}
    		}
    	}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void anvilClickItem(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getName().equalsIgnoreCase(GUIMessage.getMessage(p, "���� ��ȭ GUI �̸�"))) {
    		if (e.getCurrentItem() != null) {
    			if (e.getCurrentItem().getItemMeta() != null) {
    				if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
    					if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(GUIMessage.getMessage(p, "���.������ �̸�"))) {
    		    			for (int i = 1; ; i++) {
    		    				if (UpGrade.getUpGradeString(i + ".������ �̸�") == null) {
    		    					p.sendMessage("��c�� �������� ��ȭ�� �Ұ����մϴ�.");
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    					return;
    		    				}
    		    				
    		    				if (p.getOpenInventory().getItem(10) != null && p.getOpenInventory().getItem(10).getType() != Material.AIR) {
    		    					if (p.getOpenInventory().getItem(10).getItemMeta() != null) {
    		    						if (p.getOpenInventory().getItem(10).getItemMeta().getDisplayName() != null) {
    		        						if (ChatColor.stripColor(p.getOpenInventory().getItem(10).getItemMeta().getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(UpGrade.getUpGradeString(i + ".������ �̸�")))) {
    		            						if (p.getOpenInventory().getItem(16) == null || p.getOpenInventory().getItem(16).getType() == Material.AIR) {
    		            							p.sendMessage("��c��ȭ ��Ḧ ��� ĭ�� �÷��ֽñ� �ٶ��ϴ�.");
    		            							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		            							return;
    		            						} else {
    		            							if (p.getOpenInventory().getItem(16).getType() == Material.DIAMOND && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���̾Ƹ��")
    		            							 || p.getOpenInventory().getItem(16).getType() == Material.IRON_INGOT && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("ö")
    		            							 || p.getOpenInventory().getItem(16).getType() == Material.GOLD_INGOT && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("��")
    		            							 || p.getOpenInventory().getItem(16).getType() == Material.COAL && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("��ź")
    		            							 || p.getOpenInventory().getItem(16).getType() == Material.EMERALD && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���޶���")
    		            							 || p.getOpenInventory().getItem(16).getType() == Material.REDSTONE && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���彺��")
    		            							 || p.getOpenInventory().getItem(16).getType() == Material.LAPIS_ORE && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("û�ݼ�")
    		            							 || p.getOpenInventory().getItem(16).getType() == Material.QUARTZ && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("�״� ����")
    		            							 || p.getOpenInventory().getItem(16).getTypeId() == 42 && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("ö ���")
    		            							 || p.getOpenInventory().getItem(16).getTypeId() == 41 && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("�� ���")
    		            							 || p.getOpenInventory().getItem(16).getTypeId() == 57 && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���̾Ƹ�� ���")
    		            							 || p.getOpenInventory().getItem(16).getTypeId() == 133 && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���޶��� ���")
    		            							 || p.getOpenInventory().getItem(16).getTypeId() == 152 && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���彺�� ���")
    		            							 || p.getOpenInventory().getItem(16).getTypeId() == 22 && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("û�ݼ� ���")
    		            							 || p.getOpenInventory().getItem(16).getTypeId() == 4 && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("��")) {
    		            								if (UpGrade.getUpGradeInt(i + ".��� ����") - p.getOpenInventory().getItem(16).getAmount() < 0) {
    		            									ItemStack item = p.getOpenInventory().getItem(16);
    		            									item.setAmount(p.getOpenInventory().getItem(16).getAmount() - UpGrade.getUpGradeInt(i + ".��� ����"));
    		            									p.getOpenInventory().setItem(16, item);
    		            								}
    		            								
    		            								else if (UpGrade.getUpGradeInt(i + ".��� ����") - p.getOpenInventory().getItem(16).getAmount() == 0) {
    		            									p.getOpenInventory().setItem(16, null);
    		            								}
    		            								
    		            								else {
    		            									p.sendMessage("��c��ᰡ �����Ͽ� ��ȭ�� �Ͻ� �� �����ϴ�.");
    		            									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		            									return;
    		            								}
    		            								
    		            								int z = i + 1;
    		            								if (UpGrade.getUpGradeString(z + ".������ �̸�") != null) {
        		            								ItemStack item = new MaterialData(UpGrade.getUpGradeInt(z + ".������ �ڵ�"), (byte) UpGrade.getUpGradeInt(z + ".������ �������ڵ�")).toItemStack(1);
        		            								ItemMeta item_Meta = item.getItemMeta();
        		            								item_Meta.setDisplayName(UpGrade.getUpGradeString(z + ".������ �̸�"));
        		            								if (UpGrade.getUpGradeList(z + ".������ ����").size() != 0) item_Meta.setLore(UpGrade.getUpGradeList(z + ".������ ����"));
        		            								if (UpGrade.getUpGradeInt(z + ".ȿ��") != -1) item_Meta.addEnchant(Enchantment.DIG_SPEED, UpGrade.getUpGradeInt(z + ".ȿ��"), true);
        		            								if (UpGrade.getUpGradeInt(z + ".���") != -1) item_Meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, UpGrade.getUpGradeInt(z + ".���"), true);
        		            								item.setItemMeta(item_Meta);
        		            								p.getOpenInventory().setItem(10, item);
    		            								} else {
    		            									p.sendMessage("��c�� �������� ���̻� ��ȭ�� �Ұ��� �մϴ�.");
    		            									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		            									return;
    		            								}
    		            								
    		            								p.updateInventory();
    		            								Method.castLvup(p);
    		            								p.sendMessage("��e[ " + UpGrade.getUpGradeString(i + ".������ �̸�") + " ��e] ��6���� ��c+1 ��ȭ��6�� �����ϼ̽��ϴ�!");
    		            								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.2F, 1.1F);
    		            								GUI.updateGUI(p, p.getOpenInventory());
    		            								break;
    		            							}
    		            							
    		            							else if (p.getOpenInventory().getItem(16).getTypeId() == 35 || p.getOpenInventory().getItem(16).getTypeId() == 155 || p.getOpenInventory().getItem(16).getTypeId() == 49) {
    		            								ItemStack i1 = p.getOpenInventory().getItem(16);
    		            								if (i1.getItemMeta() != null) {
    		            	    							if (i1.getItemMeta().getDisplayName() != null) {
    		            	    								if (i1.getData().getData() == 15 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("��ź ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("��ź ���")
    		                   	    							 || i1.getData().getData() == 0 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("�״� ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("�״� ���� ���")
    		                   	    							 || i1.getData().getData() == 0 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("ö ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("ö ���� ���")
    		                   	    							 || i1.getData().getData() == 4 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("�� ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("�� ���� ���")
    		                   	    							 || i1.getData().getData() == 9 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("���̾Ƹ�� ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���̾Ƹ�� ���� ���")
    		                   	    							 || i1.getData().getData() == 5 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("���޶��� ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���޶��� ���� ���")
    		                   	    							 || i1.getData().getData() == 0 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("��ź ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("��ź ���� ���")
    		                   	    							 || i1.getData().getData() == 8 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("�״� ���� ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("�״� ���� ���� ���")
    		                   	    							 || i1.getData().getData() == 14 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("���彺�� ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("���彺�� ���� ���")
    		                   	    							 || i1.getData().getData() == 11 && ChatColor.stripColor(i1.getItemMeta().getDisplayName()).equalsIgnoreCase("û�ݼ� ���� ���") && UpGrade.getUpGradeString(i + ".������ ���").equalsIgnoreCase("û�ݼ� ���� ���")) {
    		            	    									if (UpGrade.getUpGradeInt(i + ".��� ����") - p.getOpenInventory().getItem(16).getAmount() < 0) {
    		    		            									ItemStack item = p.getOpenInventory().getItem(16);
    		    		            									item.setAmount(p.getOpenInventory().getItem(16).getAmount() - UpGrade.getUpGradeInt(i + ".��� ����"));
    		    		            									p.getOpenInventory().setItem(16, item);
    		    		            								}
    		    		            								
    		    		            								else if (UpGrade.getUpGradeInt(i + ".��� ����") - p.getOpenInventory().getItem(16).getAmount() == 0) {
    		    		            									p.getOpenInventory().setItem(16, null);
    		    		            								}
    		    		            								
    		    		            								else {
    		    		            									p.sendMessage("��c��ᰡ �����Ͽ� ��ȭ�� �Ͻ� �� �����ϴ�.");
    		    		            									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    		            									return;
    		    		            								}
    		    		            								
    		    		            								int z = i + 1;
    		    		            								if (UpGrade.getUpGradeString(z + ".������ �̸�") != null) {
    		        		            								ItemStack item = new MaterialData(UpGrade.getUpGradeInt(z + ".������ �ڵ�"), (byte) UpGrade.getUpGradeInt(z + ".������ �������ڵ�")).toItemStack(1);
    		        		            								ItemMeta item_Meta = item.getItemMeta();
    		        		            								item_Meta.setDisplayName(UpGrade.getUpGradeString(z + ".������ �̸�"));
    		        		            								if (UpGrade.getUpGradeList(z + ".������ ����").size() != 0) item_Meta.setLore(UpGrade.getUpGradeList(z + ".������ ����"));
    		        		            								if (UpGrade.getUpGradeInt(z + ".ȿ��") != -1) item_Meta.addEnchant(Enchantment.DIG_SPEED, UpGrade.getUpGradeInt(z + ".ȿ��"), true);
    		        		            								if (UpGrade.getUpGradeInt(z + ".���") != -1) item_Meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, UpGrade.getUpGradeInt(z + ".���"), true);
    		        		            								item.setItemMeta(item_Meta);
    		        		            								p.getOpenInventory().setItem(10, item);
    		    		            								} else {
    		    		            									p.sendMessage("��c�� �������� ���̻� ��ȭ�� �Ұ��� �մϴ�.");
    		    		            									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    		            									return;
    		    		            								}
    		    		            								
    		    		            								p.updateInventory();
    		    		            								Method.castLvup(p);
    		    		            								p.sendMessage("��e[ " + UpGrade.getUpGradeString(i + ".������ �̸�") + " ��e] ��6���� ��c+1 ��ȭ��6�� �����ϼ̽��ϴ�!");
    		    		            								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.2F, 1.1F);
    		    		            								GUI.updateGUI(p, p.getOpenInventory());
    		    		            								break;
    		            	    								}
    		            	    							}
    		            								}
    		            							}
    		            						}
    		        						}
    		    						} else {
            		    					p.sendMessage("��c��Ȯ�� ���� �������� �÷��ֽñ� �ٶ��ϴ�.");
            		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
            		    					return;
            		    				}
    		    					} else {
        		    					p.sendMessage("��c��Ȯ�� ���� �������� �÷��ֽñ� �ٶ��ϴ�.");
        		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        		    					return;
        		    				}
    		    				} else {
    		    					p.sendMessage("��c��ȭ�� �������� ��ȭ â �ȿ� �÷��ֽñ� �ٶ��ϴ�.");
    		    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    		    					return;
    		    				}
    		    			}
    					}
    				}
    			}
    		}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGHEST)
    public void onUpGradeItem(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		try {
			if (e.getCursor().getType() == null) {
				return;
			}
			
			if (!e.getCursor().hasItemMeta()) {
				return;
			}
			
			if (!e.getCursor().getItemMeta().hasDisplayName()) {
				return;
			}
			
			if (!e.getCurrentItem().hasItemMeta()) {
				return;
			}
			
			if (!e.getCurrentItem().getItemMeta().hasDisplayName()) {
				return;
			}
			if (e.getCursor().getType() == main.gang1.getType() && e.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase(main.gang1.getItemMeta().getDisplayName())) {
				boolean isOk = false;
				int one = 0;
				int two = 0;
				
				for (ArrayList<String> list : Waepon.WaeponList) {
					if (isOk == true) break;
					one++;
					two = 0;
					for (String str : list) {
						two++;
						if (Method.replaceAllColors(str).equalsIgnoreCase(e.getCurrentItem().getItemMeta().getDisplayName())) {
							isOk = true;
							break;
						}
					}
				}
				
				if (isOk == true) {
					//System.out.println("5");
					int num = new Random().nextInt(100);
					
					if (Chance.getChanceOrString(two + "�� ���� Ȯ��") == null) {
						p.sendMessage("��c�� �������� ��� ��ȭ�� ��ģ ������ �Դϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					//System.out.println("6");
					int z = two + 1;
					if (Waepon.getWaeponString(one + "." + z) == null) {
						p.sendMessage("��c�� �������� ��� ��ȭ�� ��ģ ������ �Դϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					//System.out.println("7");
					String[] str = Chance.getChanceOrString(two + "�� ���� Ȯ��").split(",");
					int success = Integer.parseInt(str[0]);
					if (GuildAPI.getJoinGuild(p) != null) {
						if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 1 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 4)
							success += 2;
						
						else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 4 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 8)
							success += 4;
						
						else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 8 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 12)
							success += 6;
						
						else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 12 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) < 16)
							success += 8;
						
						else if (GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) >= 16 && GuildAPI.getLuckStat(GuildAPI.getJoinGuild(p)) <= 20)
							success += 10;
					}
					
					int minus = Integer.parseInt(str[1]);
					int broken = Integer.parseInt(str[2]);
					
					//System.out.println("9");
					if (num <= success) {
						//System.out.println("10");
						if (e.getCurrentItem().getAmount() != 1) {
							p.sendMessage("��c��ȭ�� �������� ������ �ʹ� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						if (e.getCursor().getAmount() != 1) {
							p.sendMessage("��c��ȭ �ֹ����� ������ �ʹ� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						e.setCancelled(true);
						ItemStack gangitem = e.getCurrentItem();
						ItemMeta im = gangitem.getItemMeta();
						int y = two + 1;
						im.setDisplayName(Waepon.getWaeponString(one + "." + y));
						gangitem.setItemMeta(im);
						e.setCurrentItem(gangitem);
						e.setCursor(null);
						p.getWorld().playSound(p.getLocation(), Sound.ANVIL_USE, 10.0F, 1.0F);
						Method.castLvup(p);
						p.sendMessage("��e[ " + Waepon.getWaeponString(one + "." + two) + " ��e] ��6�������� ���������� ��c��ȭ��6�ϼ̽��ϴ�!");
						return;
						
					}
					
					else if (minus != 0) {
						//System.out.println("11");
						if (num > success && num <= success + minus) {
							if (e.getCurrentItem().getAmount() != 1) {
								p.sendMessage("��c��ȭ�� �������� ������ �ʹ� �����ϴ�.");
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
								return;
							}
							
							if (e.getCursor().getAmount() != 1) {
								p.sendMessage("��c��ȭ �ֹ����� ������ �ʹ� �����ϴ�.");
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
								return;
							}
							
							e.setCancelled(true);
							ItemStack gangitem = e.getCurrentItem();
							ItemMeta im = gangitem.getItemMeta();
							int y = two - 1;
							im.setDisplayName(Waepon.getWaeponString(one + "." + y));
							gangitem.setItemMeta(im);
							e.setCurrentItem(gangitem);
							e.setCursor(null);
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 1.5F);
							p.sendMessage("��e[ " + Waepon.getWaeponString(one + "." + two) + " ��e] ��c������ ��ȭ�� �Ѵܰ� ���������ϴ�.");
							return;
						}
						
						else if (broken != 0) {
							//System.out.println("12");
							if (num > success + minus && num <= success + minus + broken) {
								if (e.getCurrentItem().getAmount() != 1) {
									p.sendMessage("��c��ȭ�� �������� ������ �ʹ� �����ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
									return;
								} 
								
								if (e.getCursor().getAmount() != 1) {
									p.sendMessage("��c��ȭ �ֹ����� ������ �ʹ� �����ϴ�.");
									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
									return;
								}
								
								e.setCancelled(true);
								ItemStack gangitem = e.getCurrentItem();
								ItemMeta im = gangitem.getItemMeta();
								im.setDisplayName(Waepon.getWaeponString(one + ".1"));
								gangitem.setItemMeta(im);
								e.setCurrentItem(gangitem);
								e.setCursor(null);
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 2.0F, 1.8F);
								p.sendMessage("��e[ " + Waepon.getWaeponString(one + "." + two) + " ��e] ��c������ ��ȭ�� �ı��Ǿ����ϴ�. (�ʱ�ȭ)");
								return;
							} else {
								e.setCancelled(true);
								e.setCursor(null);
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 1.5F);
								p.sendMessage("��e[ " + Waepon.getWaeponString(one + "." + two) + " ��e] ��c��ȭ�� �����ϼ̽��ϴ�.");
								return;
							}
						} else {
							//System.out.println("13");
							e.setCancelled(true);
							e.setCursor(null);
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 1.5F);
							p.sendMessage("��e[ " + Waepon.getWaeponString(one + "." + two) + " ��e] ��c��ȭ�� �����ϼ̽��ϴ�.");
							return;
						}
					} else {
						//System.out.println("14");
						e.setCancelled(true);
						e.setCursor(null);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 1.5F);
						p.sendMessage("��e[ " + Waepon.getWaeponString(one + "." + two) + " ��e] ��c��ȭ�� �����ϼ̽��ϴ�.");
						return;
					}
				} else {
					//System.out.println("15");
					p.sendMessage("��c�� �������� ��ȭ�Ͻ� �� �����ϴ�.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
			}
			
			else if (e.getCursor().getType() == main.gang2.getType() && e.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase(main.gang2.getItemMeta().getDisplayName())) {
				boolean isOk = false;
				int one = 0;
				int two = 0;
				
				for (ArrayList<String> list : Waepon.WaeponList) {
					if (isOk == true) break;
					one++;
					two = 0;
					for (String str : list) {
						two++;
						if (Method.replaceAllColors(str).equalsIgnoreCase(e.getCurrentItem().getItemMeta().getDisplayName())) {
							isOk = true;
							break;
						}
					}
				}
				
				if (isOk == true) {
					//System.out.println("5");
					int num = new Random().nextInt(100);
					
					if (Chance.getChanceOrString(two + "�� Ư�� Ȯ��") == null) {
						p.sendMessage("��c�� �������� ��� ��ȭ�� ��ģ ������ �Դϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					//System.out.println("6");
					int z = two + 1;
					if (Waepon.getWaeponString(one + "." + z) == null) {
						p.sendMessage("��c�� �������� ��� ��ȭ�� ��ģ ������ �Դϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					//System.out.println("7");
					String[] str = Chance.getChanceOrString(two + "�� Ư�� Ȯ��").split(",");
					int success = Integer.parseInt(str[0]);
					int minus = Integer.parseInt(str[1]);
					
					//System.out.println("9");
					if (num <= success) {
						//System.out.println("10");
						if (e.getCurrentItem().getAmount() != 1) {
							p.sendMessage("��c��ȭ�� �������� ������ �ʹ� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						if (e.getCursor().getAmount() != 1) {
							p.sendMessage("��c��ȭ �ֹ����� ������ �ʹ� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						e.setCancelled(true);
						ItemStack gangitem = e.getCurrentItem();
						ItemMeta im = gangitem.getItemMeta();
						int y = two + 1;
						im.setDisplayName(Waepon.getWaeponString(one + "." + y));
						gangitem.setItemMeta(im);
						e.setCurrentItem(gangitem);
						e.setCursor(null);
						p.getWorld().playSound(p.getLocation(), Sound.ANVIL_USE, 10.0F, 1.0F);
						Method.castLvup(p);
						p.sendMessage("��e[ " + Waepon.getWaeponString(one + "." + two) + " ��e] ��6�������� ���������� ��c��ȭ��6�ϼ̽��ϴ�!");
						return;
						
					}
					
					else if (minus != 0) {
						//System.out.println("11");
						if (num > success && num <= success + minus) {
							if (e.getCurrentItem().getAmount() != 1) {
								p.sendMessage("��c��ȭ�� �������� ������ �ʹ� �����ϴ�.");
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
								return;
							}
							
							if (e.getCursor().getAmount() != 1) {
								p.sendMessage("��c��ȭ �ֹ����� ������ �ʹ� �����ϴ�.");
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
								return;
							}
							
							e.setCancelled(true);
							ItemStack gangitem = e.getCurrentItem();
							ItemMeta im = gangitem.getItemMeta();
							int y = two - 1;
							im.setDisplayName(Waepon.getWaeponString(one + "." + y));
							gangitem.setItemMeta(im);
							e.setCurrentItem(gangitem);
							e.setCursor(null);
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 1.5F);
							p.sendMessage("��e[ " + Waepon.getWaeponString(one + "." + two) + " ��e] ��c������ ��ȭ�� �Ѵܰ� ���������ϴ�.");
							return;
						} else {
							//System.out.println("13");
							e.setCancelled(true);
							e.setCursor(null);
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 1.5F);
							p.sendMessage("��e[ " + Waepon.getWaeponString(one + "." + two) + " ��e] ��c��ȭ�� �����ϼ̽��ϴ�.");
							return;
						}
					} else {
						//System.out.println("14");
						e.setCancelled(true);
						e.setCursor(null);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 1.5F);
						p.sendMessage("��e[ " + Waepon.getWaeponString(one + "." + two) + " ��e] ��c��ȭ�� �����ϼ̽��ϴ�.");
						return;
					}
				} else {
					//System.out.println("15");
					p.sendMessage("��c�� �������� ��ȭ�Ͻ� �� �����ϴ�.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
			}
			
			else if (e.getCursor().getType() == main.gang3.getType() && e.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase(main.gang3.getItemMeta().getDisplayName())) {
				boolean isOk = false;
				int one = 0;
				int two = 0;
				
				for (ArrayList<String> list : Waepon.WaeponList) {
					if (isOk == true) break;
					one++;
					two = 0;
					for (String str : list) {
						two++;
						if (Method.replaceAllColors(str).equalsIgnoreCase(e.getCurrentItem().getItemMeta().getDisplayName())) {
							isOk = true;
							break;
						}
					}
				}
				
				if (isOk == true) {
					//System.out.println("5");
					int num = new Random().nextInt(100);
					
					if (Chance.getChanceOrString(two + "�� �����̾� Ȯ��") == null) {
						p.sendMessage("��c�� �������� ��� ��ȭ�� ��ģ ������ �Դϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					//System.out.println("6");
					int z = two + 1;
					if (Waepon.getWaeponString(one + "." + z) == null) {
						p.sendMessage("��c�� �������� ��� ��ȭ�� ��ģ ������ �Դϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					//System.out.println("7");
					String[] str = Chance.getChanceOrString(two + "�� �����̾� Ȯ��").split(",");
					int success = Integer.parseInt(str[0]);
					int minus = Integer.parseInt(str[1]);
					
					//System.out.println("9");
					if (num <= success) {
						//System.out.println("10");
						if (e.getCurrentItem().getAmount() != 1) {
							p.sendMessage("��c��ȭ�� �������� ������ �ʹ� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						if (e.getCursor().getAmount() != 1) {
							p.sendMessage("��c��ȭ �ֹ����� ������ �ʹ� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						e.setCancelled(true);
						ItemStack gangitem = e.getCurrentItem();
						ItemMeta im = gangitem.getItemMeta();
						int y = two + 1;
						im.setDisplayName(Waepon.getWaeponString(one + "." + y));
						gangitem.setItemMeta(im);
						e.setCurrentItem(gangitem);
						e.setCursor(null);
						p.getWorld().playSound(p.getLocation(), Sound.ANVIL_USE, 10.0F, 1.0F);
						Method.castLvup(p);
						p.sendMessage("��e[ " + Waepon.getWaeponString(one + "." + two) + " ��e] ��6�������� ���������� ��c��ȭ��6�ϼ̽��ϴ�!");
						return;
						
					}
					
					else if (minus != 0) {
						//System.out.println("11");
						if (num > success && num <= success + minus) {
							if (e.getCurrentItem().getAmount() != 1) {
								p.sendMessage("��c��ȭ�� �������� ������ �ʹ� �����ϴ�.");
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
								return;
							}
							
							if (e.getCursor().getAmount() != 1) {
								p.sendMessage("��c��ȭ �ֹ����� ������ �ʹ� �����ϴ�.");
								if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
								return;
							}
							
							e.setCancelled(true);
							ItemStack gangitem = e.getCurrentItem();
							ItemMeta im = gangitem.getItemMeta();
							int y = two - 1;
							im.setDisplayName(Waepon.getWaeponString(one + "." + y));
							gangitem.setItemMeta(im);
							e.setCurrentItem(gangitem);
							e.setCursor(null);
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 1.5F);
							p.sendMessage("��e[ " + Waepon.getWaeponString(one + "." + two) + " ��e] ��c������ ��ȭ�� �Ѵܰ� ���������ϴ�.");
							return;
						} else {
							//System.out.println("13");
							e.setCancelled(true);
							e.setCursor(null);
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 1.5F);
							p.sendMessage("��e[ " + Waepon.getWaeponString(one + "." + two) + " ��e] ��c��ȭ�� �����ϼ̽��ϴ�.");
							return;
						}
					} else {
						//System.out.println("14");
						e.setCancelled(true);
						e.setCursor(null);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 1.5F);
						p.sendMessage("��e[ " + Waepon.getWaeponString(one + "." + two) + " ��e] ��c��ȭ�� �����ϼ̽��ϴ�.");
						return;
					}
				} else {
					//System.out.println("15");
					p.sendMessage("��c�� �������� ��ȭ�Ͻ� �� �����ϴ�.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
			}
			
			else if (e.getCursor().getType() == main.getUpGrade().getType()) {
				boolean is = false;
				int o = 1;
				for (int z = 1; z < 11; z++) {
					if (e.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase(main.getUpGrade().getItemMeta().getDisplayName().replaceAll("<num>", Integer.toString(z)))) {
						is = true;
						o = z;
						break;
					}
				}

				if (is) {
					boolean isOk = false;
					int one = 0;
					
					for (ArrayList<String> list : Waepon.WaeponList) {
						if (isOk == true) break;
						one++;
						for (String str : list) {
							if (Method.replaceAllColors(str).equalsIgnoreCase(e.getCurrentItem().getItemMeta().getDisplayName())) {
								isOk = true;
								break;
							}
						}
					}
					
					if (isOk == true) {
						if (e.getCurrentItem().getAmount() != 1) {
							p.sendMessage("��c��ȭ�� �������� ������ �ʹ� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						if (e.getCursor().getAmount() != 1) {
							p.sendMessage("��c��ȭ �ֹ����� ������ �ʹ� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							return;
						}
						
						int y = o + 1;
						e.setCancelled(true);
						ItemStack gangitem = e.getCurrentItem();
						ItemMeta im = gangitem.getItemMeta();
						im.setDisplayName(Waepon.getWaeponString(one + "." + y));
						gangitem.setItemMeta(im);
						e.setCurrentItem(gangitem);
						e.setCursor(null);
						p.getWorld().playSound(p.getLocation(), Sound.ANVIL_USE, 10.0F, 1.0F);
						Method.castLvup(p);
						p.sendMessage("��e[ " + Waepon.getWaeponString(one + "." + y) + " ��e] ��6�������� ���������� ��c��ȭ��6�ϼ̽��ϴ�!");
						return;
					} else {
						p.sendMessage("��c�� �������� ��ȭ�Ͻ� �� �����ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
				} else {
					p.sendMessage("��c�߸��� ��ȭ �ֹ��� �Դϴ�.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
			}
		} catch (Exception localException) {}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		InventoryView i = e.getView();
		
		if (i.getTitle().equalsIgnoreCase(GUIMessage.getMessage(p, "���� ��ȭ GUI �̸�"))) {
            int t = 0;
            ItemStack[] contents;
            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
                ItemStack it = contents[j];
                if (it == null) {
                    ++t;
                }
            }
            
            if (t <= 1) {
    			if (i.getItem(10) != null && i.getItem(10).getType() != Material.AIR) {
    				p.getWorld().dropItemNaturally(p.getLocation(), i.getItem(10));
    			}
    			
    			if (i.getItem(16) != null && i.getItem(16).getType() != Material.AIR) {
    				p.getWorld().dropItemNaturally(p.getLocation(), i.getItem(16));
    			}
            } else {
            	if (i.getItem(10) != null && i.getItem(10).getType() != Material.AIR) {
                	p.getInventory().addItem(i.getItem(10));
    			}
    			
    			if (i.getItem(16) != null && i.getItem(16).getType() != Material.AIR) {
    			 	p.getInventory().addItem(i.getItem(16));
    			}
            }
		}
	}
}
