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
		
		if (e.getInventory().getName().equalsIgnoreCase("설정할 아이템 코드를 클릭해 주시기 바랍니다.")) {
			if (main.Code.contains(p)) main.Code.remove(p);
		}
	}
	
	@SuppressWarnings("resource")
	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if (main.Name.contains(p)) {
			String Message = e.getMessage();
			
			if (Message.equalsIgnoreCase("취소")) {
				e.setCancelled(true);
				p.sendMessage("§6성공적으로 이름 수정을 §c취소 §6하셨습니다.");
				main.Name.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
			
			API.setPotalName(p.getName(), Message);
			e.setCancelled(true);
			p.sendMessage("§6성공적으로 이름을 수정하셨습니다. §e[ /유저포탈 ]");
			main.Name.remove(p);
			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
		}
		
		else if (main.Info.contains(p)) {
			String lore = e.getMessage();
			
			if (lore.equalsIgnoreCase("취소")) {
				e.setCancelled(true);
				p.sendMessage("§6성공적으로 설명 추가/삭제 수정을 §c취소 §6하셨습니다.");
				main.Info.remove(p);
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
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
					p.sendMessage("§6성공적으로 설명을 §c추가 §6하셨습니다.");
					p.sendMessage("§6추가된 부분: §f" + message);
					p.sendMessage("§f- §c\"취소\" 라고 입력시 취소가 가능합니다.");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
					return;
				}
				
				else if (lore.split(" ")[0].equals("-")) {
					String name = p.getName();
					Scanner scan = new Scanner(lore.split(" ")[1]);
					if (!scan.hasNextInt())
					{
						p.sendMessage("");
						p.sendMessage("§6설명을 §c삭제§6하시려면 §e'- <삭제할 라인>' §6을 입력해 주시기 바랍니다.");
						p.sendMessage("§6<삭제할 라인> 부분에는 숫자만 들어갈 수 있습니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
					
					int removeline = Integer.parseInt(lore.split(" ")[1]) - 1;
					
					if (removeline < 0) {
						p.sendMessage("");
						p.sendMessage("§6설명을 §c삭제§6하시려면 §e'- <삭제할 라인>' §6을 입력해 주시기 바랍니다.");
						p.sendMessage("§6<삭제할 라인> 부분에는 숫자만 들어갈 수 있습니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}

					List<String> list = API.getPotalInfo(name);
					if (list.size() > removeline) {
						String str = list.get(removeline);
						list.remove(removeline);
						API.setPotalInfo(name, list);

						e.setCancelled(true);
						p.sendMessage("");
						p.sendMessage("§6성공적으로 §c" + removeline + 1 + " §6번째 설명을 삭제하셨습니다.");
						p.sendMessage("§6삭제된 부분: §f" + str);
						p.sendMessage("§f- §c\"취소\" 라고 입력시 취소가 가능합니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						return;
					} else {
						e.setCancelled(true);
						p.sendMessage("");
						p.sendMessage("§c해당 부분은 존재하지 않는 라인입니다. 다시 입력해 주세요.");
						p.sendMessage("§f- §c\"취소\" 라고 입력시 취소가 가능합니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						return;
					}
				}
				
				else {
					p.sendMessage("");
					p.sendMessage("§6설명을 §c추가§6하시려면 §e'+ <설명>'§6, 설명을 §c삭제§6하시려면");
					p.sendMessage("§e'- <삭제할 라인>' §6을 입력해 주시기 바랍니다.");
					p.sendMessage("§f* §c\"취소\" 라고 입력시 취소가 가능합니다.");
					p.sendMessage("§f");
					p.sendMessage("§7예시) §f'+ 안녕하세요. OO 섬입니다.', '- 2'");
					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
					return;
				}
			} else {
				p.sendMessage("");
				p.sendMessage("§6설명을 §c추가§6하시려면 §e'+ <설명>'§6, 설명을 §c삭제§6하시려면");
				p.sendMessage("§e'- <삭제할 라인>' §6을 입력해 주시기 바랍니다.");
				p.sendMessage("§f* §c\"취소\" 라고 입력시 취소가 가능합니다.");
				p.sendMessage("§f");
				p.sendMessage("§7예시) §f'+ 안녕하세요. OO 섬입니다.', '- 2'");
				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
				return;
			}
		}
	}
	
	@EventHandler
    public void onMainClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
     	if (e.getInventory().getName().equalsIgnoreCase("포탈 옵션")) {
     		e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		ItemStack item = e.getCurrentItem();
    		String name = p.getName();
    		
    		if (item.hasItemMeta()) {
    			ItemMeta meta = item.getItemMeta();
    			
    			if (meta.hasDisplayName()) {
    				if (meta.getDisplayName().equalsIgnoreCase("§a이름 변경")) {
						if (main.Name.contains(p)) {
							p.sendMessage("§c당신은 이미 설명을 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
						
						if (main.Info.contains(p)) {
							p.sendMessage("§c당신은 이미 설명을 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
						
						if (main.Code.contains(p)) {
							p.sendMessage("§c당신은 이미 아이템 코드를 설정하고 계십니다.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
						
						main.Name.add(p);
						p.sendMessage("");
						p.sendMessage("§6변경할 이름을 §c채팅창§6에 입력해 주시기 바랍니다.");
    					p.sendMessage("§f- §c\"취소\" 라고 입력시 취소가 가능합니다.");
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b설명 추가/제거")) {
						if (main.Name.contains(p)) {
							p.sendMessage("§c당신은 이미 설명을 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
						
						if (main.Info.contains(p)) {
							p.sendMessage("§c당신은 이미 설명을 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
						
						if (main.Code.contains(p)) {
							p.sendMessage("§c당신은 이미 아이템 코드를 설정하고 계십니다.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
						
						main.Info.add(p);
						p.sendMessage("");
						p.sendMessage("§6설명을 §c추가§6하시려면 §e'+ <설명>'§6, 설명을 §c삭제§6하시려면");
						p.sendMessage("§e'- <삭제할 라인>' §6을 입력해 주시기 바랍니다.");
    					p.sendMessage("§f* §c\"취소\" 라고 입력시 취소가 가능합니다.");
						p.sendMessage("§f");
						p.sendMessage("§7예시) §f'+ 안녕하세요. OO 섬입니다.', '- 2'");
    					p.closeInventory();
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    				
    				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§d아이템 코드")) {
						if (main.Name.contains(p)) {
							p.sendMessage("§c당신은 이미 설명을 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
						
						if (main.Info.contains(p)) {
							p.sendMessage("§c당신은 이미 설명을 설정하고 계십니다. '취소' 를 채팅창에 입력 후 다시 실행해 주시기 바랍니다.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}
						
						if (main.Code.contains(p)) {
							p.sendMessage("§c당신은 이미 아이템 코드를 설정하고 계십니다.");
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.closeInventory();
							return;
						}

						main.Code.add(p);
						GUI.SetItemCodeGUI(p, name);
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    				}
    				
    				else if (meta.getDisplayName().equalsIgnoreCase("§4유저 포탈 삭제")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					GUI.CheckRemoveGUI(p, name);
    					return;
    				}
    				
    				else if (meta.getDisplayName().equalsIgnoreCase("§f뒤로가기")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
    					GUI.RankingGUI(p, 1);
    					return;
    				}
    				
    				else if (meta.getDisplayName().equalsIgnoreCase("§e섬 이동 비활성화")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					API.setPotalMove(name, false);
    					GUI.OptionGUI(p);
    					return;
    				}
    				
    				else if (meta.getDisplayName().equalsIgnoreCase("§e섬 이동 활성화")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					API.setPotalMove(name, true);
    					GUI.OptionGUI(p);
    					return;
    				}
    				
    				else if (meta.getDisplayName().equalsIgnoreCase("§6섬 워프 위치 설정")) {
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					p.closeInventory();
    					API.setIslandWarp(p, name);
    					return;
    				}
    			}
    		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("설정할 아이템 코드를 클릭해 주시기 바랍니다.")) {
         	e.setCancelled(true);
         	
    		HumanEntity h = e.getView().getPlayer();
    		Player p = Bukkit.getPlayerExact(h.getName());
    		ItemStack item = e.getCurrentItem();
    		
    		if (main.Code.contains(p)) {
        		if (item.hasItemMeta()) {
        			ItemMeta meta = item.getItemMeta();
        			
        			if (meta.hasDisplayName()) {
            			if (meta.getDisplayName().equalsIgnoreCase("§f뒤로 가기")) {
            				if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
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
        			p.sendMessage("§6성공적으로 정보 아이템 코드를 §c" + item.getTypeId() + ":" + item.getData().getData() + " §6으로 변경하셨습니다.");
        			GUI.OptionGUI(p);
        			if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
        			return;
    			}
    		}
     	}
     	
     	if (e.getInventory().getName().contains(" ")) {
     		String[] str = e.getInventory().getName().split(" ");
         	if (str[0].equalsIgnoreCase("유저") && str[1].equalsIgnoreCase("포탈")) {
         		e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		ItemStack item = e.getCurrentItem();
        		
        		if (item.getTypeId() == 119) return;
        		
        		if (item.hasItemMeta()) {
        			ItemMeta meta = item.getItemMeta();
        			
        			if (meta.hasDisplayName()) {
        				if (meta.getDisplayName().equalsIgnoreCase("§f창 닫기")) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        					p.closeInventory();
        				}
        				
    					else if (meta.getDisplayName().equalsIgnoreCase("§6다음 목록 확인")) {
    						GUI.RankingGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) + 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else if (meta.getDisplayName().equalsIgnoreCase("§6이전 목록 확인")) {
    						GUI.RankingGUI(p, Integer.parseInt((e.getInventory().getName().split(" ")[2]).split("/")[0]) - 1);
	    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
    					}
    					
    					else {
    						if (meta.hasLore()) {
    							List<String> lore = meta.getLore();
    							
    							if (lore.contains("§0§mDHPlantPotal")) {
    								String name = ChatColor.stripColor(lore.get(lore.size() - 1));
    								
    								if (e.getAction() == InventoryAction.PICKUP_ALL) {
    									if (!API.getPotalMove(name)) {
    										p.sendMessage("§c현재 유저 포탈이 비활성화되어 있어 입장하실 수 없습니다.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
        									return;
    									}
    									
    									if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.5F);
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
    										p.sendMessage("§c당신은 이 포탈을 수정할 권한이 없습니다.");
        			    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
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
         	
         	else if (str[1].equalsIgnoreCase("포탈") && str[2].equalsIgnoreCase("옵션")) {
         		e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		ItemStack item = e.getCurrentItem();
        		String name = str[0];
        		
        		if (item.hasItemMeta()) {
        			ItemMeta meta = item.getItemMeta();
        			
        			if (meta.hasDisplayName()) {
        				if (meta.getDisplayName().equalsIgnoreCase("§4유저 포탈 삭제")) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
            				GUI.CheckRemoveGUI(p, name);
        					return;
        				}
        				
        				else if (meta.getDisplayName().equalsIgnoreCase("§f뒤로가기")) {
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DOOR_CLOSE, 2.0F, 1.2F);
        					GUI.RankingGUI(p, 1);
        					return;
        				}
        			}
        		}
         	}
         	
         	else if (str[0].equalsIgnoreCase("정말로") && str[2].equalsIgnoreCase("님의") && str[3].equalsIgnoreCase("유저포탈을") && str[4].equalsIgnoreCase("제거하시겠습니까?")) {
         		e.setCancelled(true);
             	
        		HumanEntity h = e.getView().getPlayer();
        		Player p = Bukkit.getPlayerExact(h.getName());
        		ItemStack item = e.getCurrentItem();
        		String name = str[1];
        		
        		if (item.hasItemMeta()) {
        			ItemMeta meta = item.getItemMeta();
        			
        			if (meta.hasDisplayName()) {
        				if (meta.getDisplayName().equalsIgnoreCase("§f예, 제거하겠습니다.")) {
        					p.sendMessage("§6성공적으로 유저 포탈을 삭제하셨습니다.");
    						Config.deletePotal(name);
    			        	p.closeInventory();
        					return;
        				}
        				
        				else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f아니요, 제거하지 않겠습니다.")) {
        					if (p.getName().equalsIgnoreCase(name)) GUI.OptionGUI(p);
        					else if (p.isOp()) GUI.OpOptionGUI(p, name);
        					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 2.0F, 1.5F);
        					return;
        				}
        			}
        		}
         	}
     	}
	}
}
