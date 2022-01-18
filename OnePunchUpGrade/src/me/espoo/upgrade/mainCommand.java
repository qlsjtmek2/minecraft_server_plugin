package me.espoo.upgrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	
	public mainCommand(main instance)
	{
		this.plugin = instance;
	}

	@SuppressWarnings({ "deprecation", "resource" })
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("도구")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					GUI.openGUI(p);
					return false;
				} else {
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				GUI.openGUI(p);
				return false;
			} else {
				return false;
			}
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("주문서지급")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 0) {
			            int t = 0;
			            ItemStack[] contents;
			            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			                ItemStack i = contents[j];
			                if (i == null) {
			                    ++t;
			                }
			            }
			            
		                if (t <= 3) {
		                	p.sendMessage("§c인벤토리 창을 세칸 비워주시고 사용해 주시기 바랍니다.");
		                    return false;
		                }
		                
		                p.getInventory().addItem(new ItemStack[] { main.gang1 });
		                p.getInventory().addItem(new ItemStack[] { main.gang2 });
		                p.getInventory().addItem(new ItemStack[] { main.gang3 });
		                p.updateInventory();
		                p.sendMessage("§a주문서 지급을 완료했습니다.");
		                return false;
					}
					
					else if (args.length == 1) {
						int t = 0;
			            ItemStack[] contents;
			            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
			                ItemStack i = contents[j];
			                if (i == null) {
			                    ++t;
			                }
			            }
			            
		                if (t <= 1) {
		                	p.sendMessage("§c인벤토리 창을 한칸 비워주시고 사용해 주시기 바랍니다.");
		                    return false;
		                }
		                
		                Scanner scan = new Scanner(args[0]);
						if (!scan.hasNextInt())
						{
							sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
							return false;
						}
						
						int i = Integer.parseInt(args[0]);
						if (i < 1 || i > 10) {
							sender.sendMessage("§c강화 수가 0 이하, 10 초과되지 않게 해주시길 바랍니다.");
							return false;
						}
						
						ItemStack item = main.getUpGrade();
						ItemMeta meta = item.getItemMeta();
						String st = meta.getDisplayName().replaceAll("<num>", args[0]);
						List<String> lore = new ArrayList<String>();
						for (String str : item.getItemMeta().getLore()) {
							if (str.contains("<num>")) {
								str = str.replace("<num>", args[0]);
							} lore.add(str);
						}
						
						meta.setDisplayName(st);
						meta.setLore(lore);
						item.setItemMeta(meta);
		                p.getInventory().addItem(new ItemStack[] { item });
		                p.updateInventory();
		                p.sendMessage("§a주문서 지급을 완료했습니다.");
		                return false;
					}
					
					else return false;
				} else {
					if (args.length == 2) {
						Player pl = Bukkit.getPlayerExact(args[1]);
						int t = 0;
			            ItemStack[] contents;
			            for (int length = (contents = pl.getInventory().getContents()).length, j = 0; j < length; ++j) {
			                ItemStack i = contents[j];
			                if (i == null) {
			                    ++t;
			                }
			            }
			            
		                if (t <= 1) {
		                    return false;
		                }
		                
		                Scanner scan = new Scanner(args[0]);
						if (!scan.hasNextInt())
						{
							if (args[0].equalsIgnoreCase("일반")) {
								pl.getInventory().addItem(new ItemStack[] { main.gang1 });
								return false;
							}
							
							return false;
						}
						
						int i = Integer.parseInt(args[0]);
						if (i < 1 || i > 10) {
							return false;
						}
						
						ItemStack item = main.getUpGrade();
						ItemMeta meta = item.getItemMeta();
						String st = meta.getDisplayName().replaceAll("<num>", args[0]);
						List<String> lore = new ArrayList<String>();
						for (String str : item.getItemMeta().getLore()) {
							if (str.contains("<num>")) {
								str = str.replace("<num>", args[0]);
							} lore.add(str);
						}
						
						meta.setDisplayName(st);
						meta.setLore(lore);
						item.setItemMeta(meta);
		                pl.getInventory().addItem(new ItemStack[] { item });
		                pl.updateInventory();
		                return false;
					}
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				
				if (args.length == 0) {
		            int t = 0;
		            ItemStack[] contents;
		            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
		                ItemStack i = contents[j];
		                if (i == null) {
		                    ++t;
		                }
		            }
		            
	                if (t <= 3) {
	                	p.sendMessage("§c인벤토리 창을 세칸 비워주시고 사용해 주시기 바랍니다.");
	                    return false;
	                }
	                
	                p.getInventory().addItem(new ItemStack[] { main.gang1 });
	                p.getInventory().addItem(new ItemStack[] { main.gang2 });
	                p.getInventory().addItem(new ItemStack[] { main.gang3 });
	                p.updateInventory();
	                p.sendMessage("§a주문서 지급을 완료했습니다.");
	                return false;
				}
				
				else if (args.length == 1) {
					int t = 0;
		            ItemStack[] contents;
		            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
		                ItemStack i = contents[j];
		                if (i == null) {
		                    ++t;
		                }
		            }
		            
	                if (t <= 1) {
	                	p.sendMessage("§c인벤토리 창을 세칸 비워주시고 사용해 주시기 바랍니다.");
	                    return false;
	                }
	                
	                Scanner scan = new Scanner(args[0]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
						return false;
					}
					
					int i = Integer.parseInt(args[0]);
					if (i < 1 || i > 10) {
						sender.sendMessage("§c강화 수가 0 이하, 10 초과되지 않게 해주시길 바랍니다.");
						return false;
					}
					
					ItemStack item = main.getUpGrade();
					ItemMeta meta = item.getItemMeta();
					String st = meta.getDisplayName().replaceAll("<num>", args[0]);
					List<String> lore = new ArrayList<String>();
					for (String str : item.getItemMeta().getLore()) {
						if (str.contains("<num>")) {
							str = str.replace("<num>", args[0]);
						} lore.add(str);
					}
					
					meta.setDisplayName(st);
					meta.setLore(lore);
					item.setItemMeta(meta);
	                p.getInventory().addItem(new ItemStack[] { item });
	                p.updateInventory();
	                p.sendMessage("§a주문서 지급을 완료했습니다.");
	                return false;
				}
				
				else return false;
			} else {
				return false;
			}
		} return false;
	}
}
