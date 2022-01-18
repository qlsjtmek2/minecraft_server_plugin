package me.shinkhan.plantpotal;

import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("deprecation")
public class mainEvent extends JavaPlugin implements Listener {
	main M;
	
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onInventoryClose(InventoryCloseEvent e) {
		HumanEntity h = e.getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
		
		if (e.getInventory().getName().equalsIgnoreCase("������ ������ �ڵ带 Ŭ���� �ֽñ� �ٶ��ϴ�.")) {
			if (main.Code.contains(p)) main.Code.remove(p);
		}
	}
	
	@SuppressWarnings("resource")
	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if (main.Name.contains(p)) {
			String Message = e.getMessage();
			
			if (Message.equalsIgnoreCase("���")) {
				e.setCancelled(true);
				p.sendMessage("��6���������� �̸� ������ ��c��� ��6�ϼ̽��ϴ�.");
				main.Name.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			API.setPotalName(p.getName(), Message);
			e.setCancelled(true);
			p.sendMessage("��6���������� �̸��� �����ϼ̽��ϴ�. ��e[ /������Ż ]");
			main.Name.remove(p);
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
		}
		
		else if (main.Info.contains(p)) {
			String lore = e.getMessage();
			
			if (lore.equalsIgnoreCase("���")) {
				e.setCancelled(true);
				p.sendMessage("��6���������� ���� �߰�/���� ������ ��c��� ��6�ϼ̽��ϴ�.");
				main.Info.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			if (lore.contains(" ")) {
				if (lore.split(" ")[0].equals("+")) {
					String name = p.getName();
					String message = API.getFinalArg(lore.split(" "), 1);
					message = API.replaceAllColors(message);
					List<String> list = API.getPotalInfo(name);
					list.add(message);
					API.setPotalInfo(name, list);

					e.setCancelled(true);
					p.sendMessage("");
					p.sendMessage("��6���������� ������ ��c�߰� ��6�ϼ̽��ϴ�.");
					p.sendMessage("��6�߰��� �κ�: ��f" + message);
					p.sendMessage("��f- ��c\"���\" ��� �Է½� ��Ұ� �����մϴ�.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
					return;
				}
				
				else if (lore.split(" ")[0].equals("-")) {
					String name = p.getName();
					Scanner scan = new Scanner(lore.split(" ")[1]);
					if (!scan.hasNextInt())
					{
						p.sendMessage("");
						p.sendMessage("��6������ ��c������6�Ͻ÷��� ��e'- <������ ����>' ��6�� �Է��� �ֽñ� �ٶ��ϴ�.");
						p.sendMessage("��6<������ ����> �κп��� ���ڸ� �� �� �ֽ��ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					int removeline = Integer.parseInt(lore.split(" ")[1]) - 1;
					
					if (removeline < 0) {
						p.sendMessage("");
						p.sendMessage("��6������ ��c������6�Ͻ÷��� ��e'- <������ ����>' ��6�� �Է��� �ֽñ� �ٶ��ϴ�.");
						p.sendMessage("��6<������ ����> �κп��� ���ڸ� �� �� �ֽ��ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}

					List<String> list = API.getPotalInfo(name);
					if (list.size() > removeline) {
						String str = list.get(removeline);
						list.remove(removeline);
						API.setPotalInfo(name, list);

						e.setCancelled(true);
						p.sendMessage("");
						p.sendMessage("��6���������� ��c" + removeline + 1 + " ��6��° ������ �����ϼ̽��ϴ�.");
						p.sendMessage("��6������ �κ�: ��f" + str);
						p.sendMessage("��f- ��c\"���\" ��� �Է½� ��Ұ� �����մϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						return;
					} else {
						e.setCancelled(true);
						p.sendMessage("");
						p.sendMessage("��c�ش� �κ��� �������� �ʴ� �����Դϴ�. �ٽ� �Է��� �ּ���.");
						p.sendMessage("��f- ��c\"���\" ��� �Է½� ��Ұ� �����մϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
				}
				
				else {
					p.sendMessage("");
					p.sendMessage("��6������ ��c�߰���6�Ͻ÷��� ��e'+ <����>'��6, ������ ��c������6�Ͻ÷���");
					p.sendMessage("��e'- <������ ����>' ��6�� �Է��� �ֽñ� �ٶ��ϴ�.");
					p.sendMessage("��f* ��c\"���\" ��� �Է½� ��Ұ� �����մϴ�.");
					p.sendMessage("��f");
					p.sendMessage("��7����) ��f'+ �ȳ��ϼ���. OO ���Դϴ�.', '- 2'");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
			} else {
				p.sendMessage("");
				p.sendMessage("��6������ ��c�߰���6�Ͻ÷��� ��e'+ <����>'��6, ������ ��c������6�Ͻ÷���");
				p.sendMessage("��e'- <������ ����>' ��6�� �Է��� �ֽñ� �ٶ��ϴ�.");
				p.sendMessage("��f* ��c\"���\" ��� �Է½� ��Ұ� �����մϴ�.");
				p.sendMessage("��f");
				p.sendMessage("��7����) ��f'+ �ȳ��ϼ���. OO ���Դϴ�.', '- 2'");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
		}
	}
	
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	if (e.getInventory().getName().equalsIgnoreCase("��Ż �ɼ�")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		ItemStack item = e.getCurrentItem();
    		String name = p.getName();
    		
    		if (item.hasItemMeta()) {
    			ItemMeta meta = item.getItemMeta();
    			
    			if (meta.hasDisplayName()) {
    				if (meta.getDisplayName().equalsIgnoreCase("��a�̸� ����")) {
						if (main.Name.contains(p)) {
							p.sendMessage("��c����� �̹� ������ �����ϰ� ��ʴϴ�. '���' �� ä��â�� �Է� �� �ٽ� ������ �ֽñ� �ٶ��ϴ�.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
						
						if (main.Info.contains(p)) {
							p.sendMessage("��c����� �̹� ������ �����ϰ� ��ʴϴ�. '���' �� ä��â�� �Է� �� �ٽ� ������ �ֽñ� �ٶ��ϴ�.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
						
						if (main.Code.contains(p)) {
							p.sendMessage("��c����� �̹� ������ �ڵ带 �����ϰ� ��ʴϴ�.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
						
						main.Name.add(p);
						p.sendMessage("");
						p.sendMessage("��6������ �̸��� ��cä��â��6�� �Է��� �ֽñ� �ٶ��ϴ�.");
    					p.sendMessage("��f- ��c\"���\" ��� �Է½� ��Ұ� �����մϴ�.");
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��b���� �߰�/����")) {
						if (main.Name.contains(p)) {
							p.sendMessage("��c����� �̹� ������ �����ϰ� ��ʴϴ�. '���' �� ä��â�� �Է� �� �ٽ� ������ �ֽñ� �ٶ��ϴ�.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
						
						if (main.Info.contains(p)) {
							p.sendMessage("��c����� �̹� ������ �����ϰ� ��ʴϴ�. '���' �� ä��â�� �Է� �� �ٽ� ������ �ֽñ� �ٶ��ϴ�.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
						
						if (main.Code.contains(p)) {
							p.sendMessage("��c����� �̹� ������ �ڵ带 �����ϰ� ��ʴϴ�.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
						
						main.Info.add(p);
						p.sendMessage("");
						p.sendMessage("��6������ ��c�߰���6�Ͻ÷��� ��e'+ <����>'��6, ������ ��c������6�Ͻ÷���");
						p.sendMessage("��e'- <������ ����>' ��6�� �Է��� �ֽñ� �ٶ��ϴ�.");
    					p.sendMessage("��f* ��c\"���\" ��� �Է½� ��Ұ� �����մϴ�.");
						p.sendMessage("��f");
						p.sendMessage("��7����) ��f'+ �ȳ��ϼ���. OO ���Դϴ�.', '- 2'");
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��d������ �ڵ�")) {
						if (main.Name.contains(p)) {
							p.sendMessage("��c����� �̹� ������ �����ϰ� ��ʴϴ�. '���' �� ä��â�� �Է� �� �ٽ� ������ �ֽñ� �ٶ��ϴ�.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
						
						if (main.Info.contains(p)) {
							p.sendMessage("��c����� �̹� ������ �����ϰ� ��ʴϴ�. '���' �� ä��â�� �Է� �� �ٽ� ������ �ֽñ� �ٶ��ϴ�.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
						
						if (main.Code.contains(p)) {
							p.sendMessage("��c����� �̹� ������ �ڵ带 �����ϰ� ��ʴϴ�.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}

						main.Code.add(p);
						GUI.SetItemCodeGUI(p, name);
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    				
    				else if (meta.getDisplayName().equalsIgnoreCase("��4���� ��Ż ����")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					GUI.CheckRemoveGUI(p, name);
    					return;
    				}
    				
    				else if (meta.getDisplayName().equalsIgnoreCase("��f�ڷΰ���")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    					GUI.RankingGUI(p, 1);
    					return;
    				}
    				
    				else if (meta.getDisplayName().equalsIgnoreCase("��e�� �̵� ��Ȱ��ȭ")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					API.setPotalMove(name, false);
    					GUI.OptionGUI(p);
    					return;
    				}
    				
    				else if (meta.getDisplayName().equalsIgnoreCase("��e�� �̵� Ȱ��ȭ")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					API.setPotalMove(name, true);
    					GUI.OptionGUI(p);
    					return;
    				}
    				
    				else if (meta.getDisplayName().equalsIgnoreCase("��6�� ���� ��ġ ����")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					p.closeInventory();
    					API.setIslandWarp(p, name);
    					return;
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("������ ������ �ڵ带 Ŭ���� �ֽñ� �ٶ��ϴ�.")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		ItemStack item = e.getCurrentItem();
    		
    		if (main.Code.contains(p)) {
        		if (item.hasItemMeta()) {
        			ItemMeta meta = item.getItemMeta();
        			
        			if (meta.hasDisplayName()) {
            			if (meta.getDisplayName().equalsIgnoreCase("��f�ڷ� ����")) {
            				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
            				GUI.OptionGUI(p);
                			main.Code.remove(p);
            				return;
            			}
        			}
        		}
    			
    			if (item.getTypeId() != 0) {
        			API.setPotalCode(p.getName(), item.getTypeId());
        			if (item.getData().getData() != (byte) 0) {
            			API.setPotalDateCode(p.getName(), item.getData().getData());
        			}
        			
        			main.Code.remove(p);
        			p.sendMessage("��6���������� ���� ������ �ڵ带 ��c" + item.getTypeId() + ":" + item.getData().getData() + " ��6���� �����ϼ̽��ϴ�.");
        			GUI.OptionGUI(p);
        			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        			return;
    			}
    		}
     	}
     	
     	if (e.getInventory().getName().contains(" ")) {
     		String[] str = e.getInventory().getName().split(" ");
         	if (str[0].equalsIgnoreCase("����") && str[1].equalsIgnoreCase("��Ż")) {
         		e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		ItemStack item = e.getCurrentItem();
        		
        		if (item.getTypeId() == 119) return;
        		
        		if (item.hasItemMeta()) {
        			ItemMeta meta = item.getItemMeta();
        			
        			if (meta.hasDisplayName()) {
        				if (meta.getDisplayName().equalsIgnoreCase("��fâ �ݱ�")) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        					p.closeInventory();
        				}
        				
    					else if (meta.getDisplayName().equalsIgnoreCase("��6���� ��� Ȯ��")) {
    						GUI.RankingGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else if (meta.getDisplayName().equalsIgnoreCase("��6���� ��� Ȯ��")) {
    						GUI.RankingGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else {
    						if (meta.hasLore()) {
    							List<String> lore = meta.getLore();
    							
    							if (lore.contains("��0��mDHPlantPotal")) {
    								String name = ChatColor.stripColor(lore.get(lore.size() - 1));
    								
    								if (e.getAction() == InventoryAction.PICKUP_ALL) {
    									if (!API.getPotalMove(name)) {
    										p.sendMessage("��c���� ���� ��Ż�� ��Ȱ��ȭ�Ǿ� �־� �����Ͻ� �� �����ϴ�.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        									return;
    									}
    									
    									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
    									API.GoToThePlant(p, name);
    			    					p.closeInventory();
    									return;
    								}
    								
    								else if (e.getAction() == InventoryAction.PICKUP_HALF) {
    									if (p.getName().equalsIgnoreCase(name)) {
    										GUI.OptionGUI(p);
    										return;
    									}
    									
    									else if (p.isOp()) {
    										GUI.OpOptionGUI(p, name);
    										return;
    									}
    									
    									else {
    										p.sendMessage("��c����� �� ��Ż�� ������ ������ �����ϴ�.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        			    					p.closeInventory();
        									return;
    									}
    								}
    							}
    						}
    					}
        			}
        		}
         	}
         	
         	else if (str[1].equalsIgnoreCase("��Ż") && str[2].equalsIgnoreCase("�ɼ�")) {
         		e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		ItemStack item = e.getCurrentItem();
        		String name = str[0];
        		
        		if (item.hasItemMeta()) {
        			ItemMeta meta = item.getItemMeta();
        			
        			if (meta.hasDisplayName()) {
        				if (meta.getDisplayName().equalsIgnoreCase("��4���� ��Ż ����")) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
            				GUI.CheckRemoveGUI(p, name);
        					return;
        				}
        				
        				else if (meta.getDisplayName().equalsIgnoreCase("��f�ڷΰ���")) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        					GUI.RankingGUI(p, 1);
        					return;
        				}
        			}
        		}
         	}
         	
         	else if (str[0].equalsIgnoreCase("������") && str[2].equalsIgnoreCase("����") && str[3].equalsIgnoreCase("������Ż��") && str[4].equalsIgnoreCase("�����Ͻðڽ��ϱ�?")) {
         		e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		ItemStack item = e.getCurrentItem();
        		String name = str[1];
        		
        		if (item.hasItemMeta()) {
        			ItemMeta meta = item.getItemMeta();
        			
        			if (meta.hasDisplayName()) {
        				if (meta.getDisplayName().equalsIgnoreCase("��f��, �����ϰڽ��ϴ�.")) {
        					p.sendMessage("��6���������� ���� ��Ż�� �����ϼ̽��ϴ�.");
    						Config.deletePotal(name);
    			        	p.closeInventory();
        					return;
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��f�ƴϿ�, �������� �ʰڽ��ϴ�.")) {
        					if (p.getName().equalsIgnoreCase(name)) GUI.OptionGUI(p);
        					else if (p.isOp()) GUI.OpOptionGUI(p, name);
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					return;
        				}
        			}
        		}
         	}
     	}
	}
}
