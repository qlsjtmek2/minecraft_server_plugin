// 
// Decompiled by Procyon v0.5.30
// 

package kr.mcpaw.kr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class backupsystemcommand implements CommandExecutor
{
    @SuppressWarnings("unused")
	private static main plugin;
	public static final ChatColor YELLOW = ChatColor.YELLOW;
	public static final ChatColor GOLD = ChatColor.GOLD;
	public static final ChatColor WHITE = ChatColor.WHITE;
	public static final ChatColor BLUE = ChatColor.AQUA;
	public static final ChatColor RED = ChatColor.RED;
	public static final ChatColor DARK_RED = ChatColor.DARK_RED;
	public static final ChatColor GRAY = ChatColor.GRAY;
    
    public backupsystemcommand(final main instance) {
        backupsystemcommand.plugin = instance;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        try {
            if (label.equalsIgnoreCase("idplb")) {
                if (sender.hasPermission("idpld.use")) {
                    if (args.length == 0) {
                        sender.sendMessage(RED + "���ڰ��� �����ϴ�.");
                    }
                    else if (args[0].equalsIgnoreCase("createall")) {
                        if (args.length == 1) {
                        	 final SimpleDateFormat format = new SimpleDateFormat("MM��dd��HH��mm��");
                             final Date ds = new Date();
                             final String date = format.format(ds);
                             final File f = new File("plugins/idpld/folder/" + date);
                             if (!f.exists()) {
                                 f.mkdir();
                             }
                             Player[] onlinePlayers;
                             for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, j = 0; j < length; ++j) {
                                 final Player p = onlinePlayers[j];
                                 final String db = p.getName();
                                 final FileConfiguration d = (FileConfiguration)new YamlConfiguration();
                                 try {
                                     d.set("BOOTS", (Object)p.getInventory().getBoots());
                                     d.set("HElMET", (Object)p.getInventory().getHelmet());
                                     d.set("CHEST", (Object)p.getInventory().getChestplate());
                                     d.set("LEGGINGS", (Object)p.getInventory().getLeggings());
                                     for (int i = 0; i < 36; ++i) {
                                         d.set(new StringBuilder().append(i).toString(), (Object)p.getInventory().getItem(i));
                                     }
                                     d.save(new File("plugins/idpld/folder/" + date + "/" + db.replace("\\", "").replace(":", "").replace("/", "").replace(".", "").replace("\"", "").replace("<", "").replace(">", "").replace("*", "").replace("|", "")));
                                 }
                                 catch (FileNotFoundException ex) {}
                                 catch (IOException ex2) {}
                                 
                                 Bukkit.getLogger().info("[�˸�] �κ��丮 ����� �Ϸ��߽��ϴ�.");
                             }
                        }
                        else {
                            sender.sendMessage("��� ��� : /idplb createall");
                        }
                    }
                    else if (args[0].equalsIgnoreCase("read")) {
                        if (args.length >= 3) {
            				if (sender instanceof Player) {
            					Player p3 = (Player) sender;
                                FileConfiguration d = (FileConfiguration)new YamlConfiguration();
                                try {
                                    d.load(new File("plugins/idpld/folder/" + args[2] + "/" + args[1]));
                                    p3.getInventory().setBoots(d.getItemStack("BOOTS"));
                                    p3.getInventory().setHelmet(d.getItemStack("HElMET"));
                                    p3.getInventory().setChestplate(d.getItemStack("CHEST"));
                                    p3.getInventory().setLeggings(d.getItemStack("LEGGINGS"));
                                    for (int i = 0; i < 36; ++i) {
                                        p3.getInventory().setItem(i, d.getItemStack(new StringBuilder().append(i).toString()));
                                    }
                                    sender.sendMessage("�κ��丮�� �ε�Ǿ����ϴ�.");
                                }
                                catch (FileNotFoundException ex11) {}
                                catch (IOException ex12) {}
                                catch (InvalidConfigurationException ex13) {}
            				}
            				else {
            					sender.sendMessage(WHITE + "[" + DARK_RED + "���" + WHITE + "]" + RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
            				}
                        }
                        else {
                            sender.sendMessage("��� ��� : /idplb read �����̸� �ð�(MM��dd��HH��mm��)");
                        }
                    }
                    else if (args[0].equalsIgnoreCase("list")) {
                    	if (args.length == 1) {
                    		File[] list = new File("plugins/idpld/folder").listFiles();
							if (list == null || list.length == 0) {
								sender.sendMessage(RED + "�����ϴ� ������ �����ϴ�.");
								return false;
							} else {
								int num = list.length / 10; num++;
								sender.sendMessage(RED + "" + list.length + GOLD + "���� ����� ã�ҽ��ϴ�. " + RED + "1" + GOLD + "/" + RED + num);
								for (int i = 0; i < 10; i++) {
									if (list.length <= i) break;
									if (list[i].isDirectory()) {
	                                	sender.sendMessage(YELLOW + list[i].getName());         
	                                }
								}
								
								if (num > 1) {
									sender.sendMessage("");
									sender.sendMessage(GOLD + "��ɾ� " + RED + "/idplb list 2 " + GOLD + "�� �ļ� ������������ �Ѿ����.");
								} return false;
							}
						}
						
						else if (args.length == 2) {
							File[] list = new File("plugins/idpld/folder").listFiles();
							if (!isNumeric(args[1])) {
								sender.sendMessage(RED + "�ڿ��� ���ڸ� ���� �ֽñ� �ٶ��ϴ�.");
								return false;
							}
							
							int amount = Integer.parseInt(args[1]);
							
							if (list == null || list.length == 0) {
								sender.sendMessage(RED + "�����ϴ� ������ �����ϴ�.");
								return false;
							} else {
								int num = list.length / 10; num++;	
								if (amount <= 0 || amount > num) {
									sender.sendMessage(RED + "�ش� �������� �����ϴ� ������ �����ϴ�.");
									return false;
								}

								sender.sendMessage(RED + "" + list.length + GOLD + "���� ����� ã�ҽ��ϴ�. " + RED + amount + GOLD + "/" + RED + num);
								amount -= 1;
								for (int i = (amount * 10); i < ((amount * 10) + 10); i++) {
									if (list.length <= i) break;
									if (list[i].isDirectory()) {
	                                	sender.sendMessage(YELLOW + list[i].getName());         
	                                }
								}
								
								if (num > (amount + 1)) {
									sender.sendMessage("");
									sender.sendMessage(GOLD + "��ɾ� " + RED + "/idplb list " + (amount + 2) + " " + GOLD + "�� �ļ� ������������ �Ѿ����.");
								} return false;
							}
						}
						else {
							sender.sendMessage("��� ��� : /idplb list (��)");
						}
                    	
                        if (args.length == 1) {
                        	
                        	File[] fileList = new File("plugins/idpld/folder").listFiles();
                            for (int i = 0; i < fileList.length; i++) {
                                if (fileList[i].isDirectory()) {
                                	sender.sendMessage(fileList[i].getName());         
                                }
                            }
                        }
                        else {
                            sender.sendMessage("��� ��� : /idplb list (��)");
                        }
                    }
                    else {
                        sender.sendMessage("��� ���ڿ��� \ncreateall,read,list");
                    }
                }
                else {
                	sender.sendMessage("�޹̼� ������ �����ϴ�.");
                }
            }
        }
        catch (Exception ea) {
            sender.sendMessage("�߸��� ��û �Դϴ�.");
        }
        return true;
    }
    
	public static boolean isNumeric(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
}
