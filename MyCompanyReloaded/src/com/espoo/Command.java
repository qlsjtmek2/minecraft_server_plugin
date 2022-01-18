package com.espoo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Command extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final Main plugin;

	public Command(Main instance)
	{
		this.plugin = instance;
	}
	
	@SuppressWarnings("unused")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "°æ°í" + ChatColor.WHITE + "] ¹öÅ¶¿¡¼± ½ÇÇàÀÌ ºÒ°¡´É ÇÕ´Ï´Ù.");
			return false;
		}
		
		Player p = (Player) sender;
        String n = p.getName();
        Boolean Master = UserData.getBooleanYml(p, "»çÀå");
        Boolean User = UserData.getBooleanYml(p, "Á÷¿ø");
        
		if(commandLabel.equalsIgnoreCase("È¸»ç")) {
			if(args.length == 0 || args[0].equalsIgnoreCase("1")) {
				if(Master == false && (!p.hasPermission("MyCompany.*"))) {
					sender.sendMessage(" ¡×e---- ¡×6È¸»ç ¡×e-- ¡×6ÆäÀÌÁö ¡×c1¡×6/¡×c1 ¡×e----");
				} else {
					sender.sendMessage(" ¡×e---- ¡×6È¸»ç ¡×e-- ¡×6ÆäÀÌÁö ¡×c1¡×6/¡×c2 ¡×e----");
				}
				sender.sendMessage("¡×6/È¸»ç Ã¢¸³ <ÀÌ¸§> ¡×f- È¸»ç¸¦ Ã¢¸³ÇÕ´Ï´Ù. ¡×b( µ· 2Ãµ¸¸¿ø ¼Ò¿ä )");
				sender.sendMessage("¡×6/È¸»ç ÆÄ»ê ¡×f- È¸»ç¸¦ ÆÄ»êÇÕ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç ÃÊ´ë <´Ğ³×ÀÓ> ¡×f- <´Ğ³×ÀÓ>¿¡°Ô È¸»ç °¡ÀÔÀ» ±ÇÀ¯ÇÕ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç ¼ö¶ô ¡×f- °¡ÀÔ ±ÇÀ¯¸¦ ¼ö¶ôÇÕ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç °ÅÀı ¡×f- °¡ÀÔ ±ÇÀ¯¸¦ °ÅÀıÇÕ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç ÃëÁ÷ <È¸»ç> <°¢¿À> ¡×f- È¸»ç¿¡°Ô ÀÚ±â¼Ò°³¼­¸¦ Á¦ÃâÇÕ´Ï´Ù.");
				sender.sendMessage("¡×6 - ¡×f¶ç¾î¾²±â´Â \"¡×e_¡×f\"·Î Ç¥±âÇØÁÖ¼¼¿ä.");
				sender.sendMessage("¡×6/È¸»ç Ãë¼Ò <È¸»ç> ¡×f- <È¸»ç>ÀÇ ÃëÁ÷ ÀÌ·Â¼­¸¦ Ãë¼ÒÇÕ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç Á¤º¸ <È¸»ç> ¡×f- È¸»çÀÇ Á¤º¸¸¦ º¾´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç ¸ñ·Ï ¡×f- ÀÚ½ÅÀÇ È¸»ç »ç¶÷µéÀ» º¾´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç Ã¤ÆÃ ¡×f- È¸»ç Àü¿ë Ã¤ÆÃÀ» ÄÕ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç »çÇ¥ ¡×f- ÀÚ½ÅÀÌ °¡ÀÔÇØÀÖ´Â È¸»ç¸¦ ³ª°©´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç ·©Å· ¡×f- È¸»çÀÇ ÀüÃ¼ ·©Å·À» º¾´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç µµ¿ò¸» ¡×f- È¸»ç ½Ã½ºÅÛÀÇ ±âº»ÀûÀÎ µµ¿ò¸»À» º¾´Ï´Ù.");
				if(Master == false && (!p.hasPermission("MyCompany.*"))) {
					return false;
				} else {
					sender.sendMessage("¡×6¸í·É¾î ¡×cÈ¸»ç 2 ¡×6¸¦ ÃÄ¼­ ´ÙÀ½ÆäÀÌÁö·Î ³Ñ¾î°¡¼¼¿ä.");
				}
				return false;
			}
			
			else if((args[0].equalsIgnoreCase("2")) && (Master == true) || 
					(args[0].equalsIgnoreCase("2")) && (p.hasPermission("MyCompany.*"))) {
				sender.sendMessage(" ¡×e---- ¡×6È¸»ç ¡×e-- ¡×6ÆäÀÌÁö ¡×c2¡×6/¡×c2 ¡×e----");
				sender.sendMessage("¡×6/È¸»ç ÀÏ <´Ğ³×ÀÓ> <±Ş¿©> ¡×f- <´Ğ³×ÀÓ>¿¡°Ô ±Ş¿©¸¦ ÁÖ°í ÀÏÀ» ±ÇÀ¯ÇÕ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç ½ÅÃ»¸ñ·Ï  ¡×f- ¿ì¸® È¸»çÀÇ ½ÅÃ»ÇÑ »ç¶÷µé ¸ñ·ÏÀ» º¾´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç °¡ÀÔÇã¿ë <´Ğ³×ÀÓ> ¡×f- °¡ÀÔ ½ÅÃ»À» Çã¿ëÇÕ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç °¡ÀÔ°ÅºÎ <´Ğ³×ÀÓ> ¡×f- °¡ÀÔ ½ÅÃ»À» °ÅºÎÇÕ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç Á¤º¸ ÀÌ¸§ <ÀÌ¸§> ¡×f- È¸»ç ÀÌ¸§À» º¯°æÇÕ´Ï´Ù. ¡×b( µ· 5¹é¸¸¿ø ¼Ò¿ä )");
				sender.sendMessage("¡×6/È¸»ç Á¤º¸ ¼³¸í Ãß°¡ <¸Ş¼¼Áö> ¡×f- È¸»ç Á¤º¸ ¼³¸íÀ» Ãß°¡ÇÕ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç Á¤º¸ ¼³¸í Á¦°Å <ÁÙ> ¡×f- È¸»ç Á¤º¸ ¼³¸íÀ» Á¦°ÅÇÕ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç °¡ÀÔÁ¶°Ç <°ø°³|½ÅÃ»|±İÁö> ¡×f- È¸»ç °¡ÀÔÁ¶°ÇÀ» º¯°æÇÕ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç À§ÀÓ <´Ğ³×ÀÓ> ¡×f- ÀÚ½ÅÀÇ È¸»ç¸¦ <´Ğ³×ÀÓ>¿¡°Ô À§ÀÓÇÕ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç ÅğÁ÷ <´Ğ³×ÀÓ> ¡×f- <´Ğ³×ÀÓ>À» È¸»ç¿¡¼­ °­Á¦·Î ÅğÁ÷½ÃÅµ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç ¾÷±×·¹ÀÌµå ¡×f- ´ÙÀ½ ´Ü°è·Î È¸»ç¸¦ ¾÷±×·¹ÀÌµå ÇÕ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç ¾÷±×·¹ÀÌµå ¸ñ·Ï ¡×f- È¸»çÀÇ ¾÷±×·¹ÀÌµå ¸ñ·ÏÀ» º¾´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç Á÷±Ş ¼³Á¤ <Á÷±Ş> <´Ğ³×ÀÓ> ¡×f- <´Ğ³×ÀÓ>ÀÇ Á÷±ŞÀ» ¼³Á¤ÇÕ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç Á÷±Ş ½Ã°£ <Á÷±Ş> <½Ã°£> ¡×f- ¸î ºĞÀÌ Áö³ª¸é Á÷±ŞÀÌ µÇ´ÂÁö ¼³Á¤ÇÕ´Ï´Ù.");
				sender.sendMessage("¡×6/È¸»ç Á÷±Ş ¸ñ·Ï ¡×f- È¸»çÀÇ Á÷±Ş ¸ñ·ÏÀ» º¾´Ï´Ù.");
				return false;
			}
			
			else if(args[0].equalsIgnoreCase("Ã¢¸³")) {
				if(args.length == 2) {
					if (Master == false && User == false) {
						if (args[1].matches("[¤¡-¤¾¤¿-¤Ó°¡-ÆRa-zA-Z0-9_]+")) {
							double balance = Main.economy.getBalance(n);
							if (balance > 20000000) {
								String str = args[1];
								if (Methods.getComBoolean(p, args[1])) {
									sender.sendMessage("¡×cÀÌ¹Ì °°Àº ÀÌ¸§À» °¡Áø È¸»ç°¡ ÀÖ½À´Ï´Ù.");
									return true;
								}
								
								try {
									Company.CreateComFile(n, args[1]);
								} catch (IOException e) {}
								Main.economy.withdrawPlayer(n, 20000000).transactionSuccess();
								UserData.SetBooleanData(p, "»çÀå", true);
								Methods.addComList(p, args[1]);
								UserData.SetStringData(p, "È¸»ç", args[1]);
								sender.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + args[1] + " ¡×6È¸»ç ¸¦ »ı¼ºÇß½À´Ï´Ù.");
								return true;	
							} else {
								sender.sendMessage("¡×c´ç½ÅÀº È¸»ç¸¦ Ã¢¸³ÇÏ±âÀ§ÇÑ µ·ÀÌ ºÎÁ·ÇÕ´Ï´Ù. ¡×b( 2Ãµ¸¸¿ø ¼Ò¿ä )");
								return true;
							}
						} else {
							sender.sendMessage("¡×c»ç¿ëÀÌ ºÒ°¡´ÉÇÑ ¹®ÀÚ°¡ Æ÷ÇÔµÇ¾î ÀÖ½À´Ï´Ù.");
							return true;
						}
					} else {
						sender.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ÃëÁ÷ÇÏ°Å³ª Ã¢¸³ÇÑ È¸»ç°¡ ÀÖ½À´Ï´Ù.");
						return true;
					}
				} else {
					sender.sendMessage("¡×6/È¸»ç Ã¢¸³ <ÀÌ¸§> ¡×f- È¸»ç¸¦ Ã¢¸³ÇÕ´Ï´Ù. ¡×b( µ· 2Ãµ¸¸¿ø ¼Ò¿ä )");
					return true;
				}
			}
			
			else if(args[0].equalsIgnoreCase("ÆÄ»ê")) {
				if (Master == true) {
					String Cname = UserData.getStringYml(p, "È¸»ç");
					File cf = new File("plugins/MyCompany/Company/" + Cname + ".yml");
					cf.delete();
					UserData.SetBooleanData(p, "»çÀå", false);
					UserData.SetStringData(p, "È¸»ç", "NONE");
					Methods.remComList(p, Cname);
					sender.sendMessage("¡×c" + Cname + " ¡×6È¸»ç¸¦ Á¤»óÀûÀ¸·Î ¡×cÆÄ»ê¡×6Çß½À´Ï´Ù.");
					return true;
				} else {
					sender.sendMessage("¡×c´ç½ÅÀº È¸»ç È¸ÀåÀÌ ¾Æ´Õ´Ï´Ù.");
					return true;
				}
			} else {
				sender.sendMessage("¡×c´ç½ÅÀº È¸»ç È¸ÀåÀÌ ¾Æ´Õ´Ï´Ù.");
				return true;
			}
		}
		
		else if(args[0].equalsIgnoreCase("ÃÊ´ë")) {
			if(args.length == 2) {
				if (Master == true) {
					if (!(args[1] == p.getName())) {
						Player pl = Methods.getOnorOffLine(args[1]);
						if (pl != null)
						{
							if (UserData.getBooleanYml(pl, "»çÀå") == false
							 && UserData.getBooleanYml(pl, "Á÷¿ø") == false) {
								String Cname = UserData.getStringYml(pl, "È¸»ç");
								sender.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + args[1] + "¡×6 ´Ô¿¡°Ô °¡ÀÔ ±ÇÀ¯ÀåÀ» ÁÖ¼Ì½À´Ï´Ù.");
								Methods.InvitePlayer(p, pl, Cname);
							} else {
								sender.sendMessage("¡×c±× ÇÃ·¹ÀÌ¾î´Â ÀÌ¹Ì È¸»ç¿¡¼­ ÀÏÇÏ°í °è½Ê´Ï´Ù.");
							}
						} else {
							sender.sendMessage("¡×cÇÃ·¹ÀÌ¾î¸¦ Ã£À» ¼ö ¾ø½À´Ï´Ù.");
							return true;
						}
					} else {
						sender.sendMessage("¡×cÀÚ±â ÀÚ½ÅÀº ÃÊ´ëÇÒ ¼ö ¾ø½À´Ï´Ù.");
						return true;
					}
				} else {
					sender.sendMessage("¡×c´ç½ÅÀº ÃÊ´ëÇÒ ±ÇÇÑÀÌ ¾ø½À´Ï´Ù.");
					return true;
				}
			} else {
				sender.sendMessage("¡×6/È¸»ç ÃÊ´ë <´Ğ³×ÀÓ> ¡×f- <´Ğ³×ÀÓ>¿¡°Ô È¸»ç °¡ÀÔÀ» ±ÇÀ¯ÇÕ´Ï´Ù.");
				return true;
			}
		}
		
		else if(args[0].equalsIgnoreCase("¼ö¶ô")) {
			String none = "NONE";
			String Cname = UserData.getStringYml(p, "ÃÊ´ë");
			if (!(none.equals(Cname))) {
				if (Master == false && User == false) {
					File f = new File("plugins/MyCompany/CompanyList.yml");
					YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
					List<String> list;
			        list = config.getStringList("CompanyList");
			        int i = list.size();
					do {
						if(i == 0) {
							sender.sendMessage("¡×c´ç½ÅÀ» ÃÊ´ëÇÑ È¸»ç°¡ ÆÄ»êµÇ¾î ÀÖ½À´Ï´Ù.");
							UserData.SetStringData(p, "ÃÊ´ë", "NONE");
							return true;
						}
						
		    			if(list.get(i) == Cname) {
		    				sender.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + Cname + "¡×6 È¸»ç¿¡ ÀÔ»çÇÏ¼Ì½À´Ï´Ù!");
		    				UserData.SetStringData(p, "ÃÊ´ë", "NONE");
		    				UserData.SetStringData(p, "È¸»ç", Cname);
		    				UserData.SetBooleanData(p, "Á÷¿ø", true);
		    				try { Company.addUserList(args[1], Cname); } catch (IOException e) {}
		    			} i--;
					} while (i>-1);
				} else {
					sender.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ÀÔ»çÇÑ È¸»ç°¡ ÀÖ½À´Ï´Ù.");
					return true;
				}
			} else {
				sender.sendMessage("¡×c´ç½ÅÀ» ÃÊ´ëÇÑ È¸»ç°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
				return true;
			}
		}
		
		else if(args[0].equalsIgnoreCase("°ÅÀı")) {
			String none = "NONE";
			String Cname = UserData.getStringYml(p, "ÃÊ´ë");
			if (!(none.equals(Cname))) {
				if (Master == false && User == false) {
					File f = new File("plugins/MyCompany/CompanyList.yml");
					YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
					List<String> list;
			        list = config.getStringList("CompanyList");
			        int i = list.size();
					do {
						if(i == 0) {
							sender.sendMessage("¡×c´ç½ÅÀ» ÃÊ´ëÇÑ È¸»ç°¡ ÆÄ»êµÇ¾î ÀÖ½À´Ï´Ù.");
							UserData.SetStringData(p, "ÃÊ´ë", "NONE");
							return true;
						}
						
						if(list.get(i) == Cname) {
							sender.sendMessage("¡×c" + Cname + "¡×6 È¸»çÀÇ ÀÔ»ç ±ÇÀ¯¸¦ ¡×c°ÅÀı ¡×6ÇÏ¼Ì½À´Ï´Ù..");
							UserData.SetStringData(p, "ÃÊ´ë", "NONE");
							return true;
						} i--;
					} while (i>-1);
				} else {
					sender.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ÀÔ»çÇÑ È¸»ç°¡ ÀÖ½À´Ï´Ù.");
					return true;
				}
			} else {
				sender.sendMessage("¡×c´ç½ÅÀ» ÃÊ´ëÇÑ È¸»ç°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
				return true;
			}
		}
		
		else if(args[0].equalsIgnoreCase("ÃëÁ÷")) {
			if(args.length == 3) {
				if (Master == false && User == false) {
					if (Integer.parseInt(playerstat[3]) == 0) {
						String str = args[1];
						for (int i = Integer.parseInt(companyl[0])-1; i>-1; i--) {
							if (i == -1) {
								sender.sendMessage("¡×cÈ¸»ç°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
								return true;
							}
							
							else if ((str.equals(companyn[i]))) {
								sender.sendMessage("¡×6¼º°øÀûÀ¸·Î ¡×c" + args[1] + "¡×6 È¸»ç¿¡ ÀÌ·Â¼­¸¦ ³Ö¾ú½À´Ï´Ù.");
								Company.AddJoinList(p, args[1], args[2]);
								playerstat[3] = (Integer.parseInt(playerstat[3])+1)+"";
								UserData.SetFile(p, playerstat);
								try { 
									Player mnader = Method.getMasterName(args[1]); 
									if (Method.getOnorOffLine(mnader.getName()) == null) { return true; }
									else if (Method.getMaster(mnader, mnader.getName())) {
										mnader.sendMessage("¡×c" + p.getName() + "¡×6 ´ÔÀÌ ´ç½ÅÀÇ È¸»ç¿¡ ¡×cÀÌ·Â¼­¡×6¸¦ ³ÖÀ¸¼Ì½À´Ï´Ù.");
										mnader.sendMessage("¡×6/È¸»ç ½ÅÃ»¸ñ·Ï  ¡×f- ¿ì¸® È¸»çÀÇ ½ÅÃ»ÇÑ »ç¶÷µé ¸ñ·ÏÀ» º¾´Ï´Ù.");
									}
								} catch (IOException e) {}
								return true;
							}
							
							else if (i==0) {
								sender.sendMessage("¡×cÈ¸»ç°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
								return true;
							}
						}
						return true;
					} else {
						sender.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì È¸»ç¿¡ ÀÌ·Â¼­¸¦ ³ÖÀ¸¼Ì½À´Ï´Ù.");
						return true;
					}
				} else {
					sender.sendMessage("¡×c´ç½ÅÀº ÀÌ¹Ì ÀÔ»çÇÑ È¸»ç°¡ ÀÖ½À´Ï´Ù.");
					return true;
				}
			} else {
				sender.sendMessage("¡×6/È¸»ç ÃëÁ÷ <È¸»ç> <°¢¿À> ¡×f- È¸»ç¿¡°Ô ÀÚ±â¼Ò°³¼­¸¦ Á¦ÃâÇÕ´Ï´Ù.");
				sender.sendMessage("¡×f- ¡×b¶ç¾î¾²±â´Â \"_\"·Î Ç¥±âÇØÁÖ¼¼¿ä.");
				return true;
			}
		}
		
		
		return false;
	}

}
