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
                        sender.sendMessage(RED + "인자값이 없습니다.");
                    }
                    else if (args[0].equalsIgnoreCase("createall")) {
                        if (args.length == 1) {
                        	 final SimpleDateFormat format = new SimpleDateFormat("MM월dd일HH시mm분");
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
                                 
                                 Bukkit.getLogger().info("[알림] 인벤토리 백업을 완료했습니다.");
                             }
                        }
                        else {
                            sender.sendMessage("사용 방법 : /idplb createall");
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
                                    sender.sendMessage("인벤토리가 로드되었습니다.");
                                }
                                catch (FileNotFoundException ex11) {}
                                catch (IOException ex12) {}
                                catch (InvalidConfigurationException ex13) {}
            				}
            				else {
            					sender.sendMessage(WHITE + "[" + DARK_RED + "경고" + WHITE + "]" + RED + " 콘솔에선 실행이 불가능한 명령어 입니다.");
            				}
                        }
                        else {
                            sender.sendMessage("사용 방법 : /idplb read 유저이름 시간(MM월dd일HH시mm분)");
                        }
                    }
                    else if (args[0].equalsIgnoreCase("list")) {
                    	if (args.length == 1) {
                    		File[] list = new File("plugins/idpld/folder").listFiles();
							if (list == null || list.length == 0) {
								sender.sendMessage(RED + "존재하는 파일이 없습니다.");
								return false;
							} else {
								int num = list.length / 10; num++;
								sender.sendMessage(RED + "" + list.length + GOLD + "개의 목록을 찾았습니다. " + RED + "1" + GOLD + "/" + RED + num);
								for (int i = 0; i < 10; i++) {
									if (list.length <= i) break;
									if (list[i].isDirectory()) {
	                                	sender.sendMessage(YELLOW + list[i].getName());         
	                                }
								}
								
								if (num > 1) {
									sender.sendMessage("");
									sender.sendMessage(GOLD + "명령어 " + RED + "/idplb list 2 " + GOLD + "를 쳐서 다음페이지로 넘어가세요.");
								} return false;
							}
						}
						
						else if (args.length == 2) {
							File[] list = new File("plugins/idpld/folder").listFiles();
							if (!isNumeric(args[1])) {
								sender.sendMessage(RED + "뒤에는 숫자만 적어 주시기 바랍니다.");
								return false;
							}
							
							int amount = Integer.parseInt(args[1]);
							
							if (list == null || list.length == 0) {
								sender.sendMessage(RED + "존재하는 파일이 없습니다.");
								return false;
							} else {
								int num = list.length / 10; num++;	
								if (amount <= 0 || amount > num) {
									sender.sendMessage(RED + "해당 페이지에 존재하는 파일이 없습니다.");
									return false;
								}

								sender.sendMessage(RED + "" + list.length + GOLD + "개의 목록을 찾았습니다. " + RED + amount + GOLD + "/" + RED + num);
								amount -= 1;
								for (int i = (amount * 10); i < ((amount * 10) + 10); i++) {
									if (list.length <= i) break;
									if (list[i].isDirectory()) {
	                                	sender.sendMessage(YELLOW + list[i].getName());         
	                                }
								}
								
								if (num > (amount + 1)) {
									sender.sendMessage("");
									sender.sendMessage(GOLD + "명령어 " + RED + "/idplb list " + (amount + 2) + " " + GOLD + "를 쳐서 다음페이지로 넘어가세요.");
								} return false;
							}
						}
						else {
							sender.sendMessage("사용 방법 : /idplb list (값)");
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
                            sender.sendMessage("사용 방법 : /idplb list (값)");
                        }
                    }
                    else {
                        sender.sendMessage("명령 문자열들 \ncreateall,read,list");
                    }
                }
                else {
                	sender.sendMessage("펄미션 권한이 없습니다.");
                }
            }
        }
        catch (Exception ea) {
            sender.sendMessage("잘못된 요청 입니다.");
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
