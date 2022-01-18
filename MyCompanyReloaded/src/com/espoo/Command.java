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
			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "] ��Ŷ���� ������ �Ұ��� �մϴ�.");
			return false;
		}
		
		Player p = (Player) sender;
        String n = p.getName();
        Boolean Master = UserData.getBooleanYml(p, "����");
        Boolean User = UserData.getBooleanYml(p, "����");
        
		if(commandLabel.equalsIgnoreCase("ȸ��")) {
			if(args.length == 0 || args[0].equalsIgnoreCase("1")) {
				if(Master == false && (!p.hasPermission("MyCompany.*"))) {
					sender.sendMessage(" ��e---- ��6ȸ�� ��e-- ��6������ ��c1��6/��c1 ��e----");
				} else {
					sender.sendMessage(" ��e---- ��6ȸ�� ��e-- ��6������ ��c1��6/��c2 ��e----");
				}
				sender.sendMessage("��6/ȸ�� â�� <�̸�> ��f- ȸ�縦 â���մϴ�. ��b( �� 2õ���� �ҿ� )");
				sender.sendMessage("��6/ȸ�� �Ļ� ��f- ȸ�縦 �Ļ��մϴ�.");
				sender.sendMessage("��6/ȸ�� �ʴ� <�г���> ��f- <�г���>���� ȸ�� ������ �����մϴ�.");
				sender.sendMessage("��6/ȸ�� ���� ��f- ���� ������ �����մϴ�.");
				sender.sendMessage("��6/ȸ�� ���� ��f- ���� ������ �����մϴ�.");
				sender.sendMessage("��6/ȸ�� ���� <ȸ��> <����> ��f- ȸ�翡�� �ڱ�Ұ����� �����մϴ�.");
				sender.sendMessage("��6 - ��f����� \"��e_��f\"�� ǥ�����ּ���.");
				sender.sendMessage("��6/ȸ�� ��� <ȸ��> ��f- <ȸ��>�� ���� �̷¼��� ����մϴ�.");
				sender.sendMessage("��6/ȸ�� ���� <ȸ��> ��f- ȸ���� ������ ���ϴ�.");
				sender.sendMessage("��6/ȸ�� ��� ��f- �ڽ��� ȸ�� ������� ���ϴ�.");
				sender.sendMessage("��6/ȸ�� ä�� ��f- ȸ�� ���� ä���� �մϴ�.");
				sender.sendMessage("��6/ȸ�� ��ǥ ��f- �ڽ��� �������ִ� ȸ�縦 �����ϴ�.");
				sender.sendMessage("��6/ȸ�� ��ŷ ��f- ȸ���� ��ü ��ŷ�� ���ϴ�.");
				sender.sendMessage("��6/ȸ�� ���� ��f- ȸ�� �ý����� �⺻���� ������ ���ϴ�.");
				if(Master == false && (!p.hasPermission("MyCompany.*"))) {
					return false;
				} else {
					sender.sendMessage("��6��ɾ� ��cȸ�� 2 ��6�� �ļ� ������������ �Ѿ����.");
				}
				return false;
			}
			
			else if((args[0].equalsIgnoreCase("2")) && (Master == true) || 
					(args[0].equalsIgnoreCase("2")) && (p.hasPermission("MyCompany.*"))) {
				sender.sendMessage(" ��e---- ��6ȸ�� ��e-- ��6������ ��c2��6/��c2 ��e----");
				sender.sendMessage("��6/ȸ�� �� <�г���> <�޿�> ��f- <�г���>���� �޿��� �ְ� ���� �����մϴ�.");
				sender.sendMessage("��6/ȸ�� ��û���  ��f- �츮 ȸ���� ��û�� ����� ����� ���ϴ�.");
				sender.sendMessage("��6/ȸ�� ������� <�г���> ��f- ���� ��û�� ����մϴ�.");
				sender.sendMessage("��6/ȸ�� ���԰ź� <�г���> ��f- ���� ��û�� �ź��մϴ�.");
				sender.sendMessage("��6/ȸ�� ���� �̸� <�̸�> ��f- ȸ�� �̸��� �����մϴ�. ��b( �� 5�鸸�� �ҿ� )");
				sender.sendMessage("��6/ȸ�� ���� ���� �߰� <�޼���> ��f- ȸ�� ���� ������ �߰��մϴ�.");
				sender.sendMessage("��6/ȸ�� ���� ���� ���� <��> ��f- ȸ�� ���� ������ �����մϴ�.");
				sender.sendMessage("��6/ȸ�� �������� <����|��û|����> ��f- ȸ�� ���������� �����մϴ�.");
				sender.sendMessage("��6/ȸ�� ���� <�г���> ��f- �ڽ��� ȸ�縦 <�г���>���� �����մϴ�.");
				sender.sendMessage("��6/ȸ�� ���� <�г���> ��f- <�г���>�� ȸ�翡�� ������ ������ŵ�ϴ�.");
				sender.sendMessage("��6/ȸ�� ���׷��̵� ��f- ���� �ܰ�� ȸ�縦 ���׷��̵� �մϴ�.");
				sender.sendMessage("��6/ȸ�� ���׷��̵� ��� ��f- ȸ���� ���׷��̵� ����� ���ϴ�.");
				sender.sendMessage("��6/ȸ�� ���� ���� <����> <�г���> ��f- <�г���>�� ������ �����մϴ�.");
				sender.sendMessage("��6/ȸ�� ���� �ð� <����> <�ð�> ��f- �� ���� ������ ������ �Ǵ��� �����մϴ�.");
				sender.sendMessage("��6/ȸ�� ���� ��� ��f- ȸ���� ���� ����� ���ϴ�.");
				return false;
			}
			
			else if(args[0].equalsIgnoreCase("â��")) {
				if(args.length == 2) {
					if (Master == false && User == false) {
						if (args[1].matches("[��-����-�Ӱ�-�Ra-zA-Z0-9_]+")) {
							double balance = Main.economy.getBalance(n);
							if (balance > 20000000) {
								String str = args[1];
								if (Methods.getComBoolean(p, args[1])) {
									sender.sendMessage("��c�̹� ���� �̸��� ���� ȸ�簡 �ֽ��ϴ�.");
									return true;
								}
								
								try {
									Company.CreateComFile(n, args[1]);
								} catch (IOException e) {}
								Main.economy.withdrawPlayer(n, 20000000).transactionSuccess();
								UserData.SetBooleanData(p, "����", true);
								Methods.addComList(p, args[1]);
								UserData.SetStringData(p, "ȸ��", args[1]);
								sender.sendMessage("��6���������� ��c" + args[1] + " ��6ȸ�� �� �����߽��ϴ�.");
								return true;	
							} else {
								sender.sendMessage("��c����� ȸ�縦 â���ϱ����� ���� �����մϴ�. ��b( 2õ���� �ҿ� )");
								return true;
							}
						} else {
							sender.sendMessage("��c����� �Ұ����� ���ڰ� ���ԵǾ� �ֽ��ϴ�.");
							return true;
						}
					} else {
						sender.sendMessage("��c����� �̹� �����ϰų� â���� ȸ�簡 �ֽ��ϴ�.");
						return true;
					}
				} else {
					sender.sendMessage("��6/ȸ�� â�� <�̸�> ��f- ȸ�縦 â���մϴ�. ��b( �� 2õ���� �ҿ� )");
					return true;
				}
			}
			
			else if(args[0].equalsIgnoreCase("�Ļ�")) {
				if (Master == true) {
					String Cname = UserData.getStringYml(p, "ȸ��");
					File cf = new File("plugins/MyCompany/Company/" + Cname + ".yml");
					cf.delete();
					UserData.SetBooleanData(p, "����", false);
					UserData.SetStringData(p, "ȸ��", "NONE");
					Methods.remComList(p, Cname);
					sender.sendMessage("��c" + Cname + " ��6ȸ�縦 ���������� ��c�Ļ��6�߽��ϴ�.");
					return true;
				} else {
					sender.sendMessage("��c����� ȸ�� ȸ���� �ƴմϴ�.");
					return true;
				}
			} else {
				sender.sendMessage("��c����� ȸ�� ȸ���� �ƴմϴ�.");
				return true;
			}
		}
		
		else if(args[0].equalsIgnoreCase("�ʴ�")) {
			if(args.length == 2) {
				if (Master == true) {
					if (!(args[1] == p.getName())) {
						Player pl = Methods.getOnorOffLine(args[1]);
						if (pl != null)
						{
							if (UserData.getBooleanYml(pl, "����") == false
							 && UserData.getBooleanYml(pl, "����") == false) {
								String Cname = UserData.getStringYml(pl, "ȸ��");
								sender.sendMessage("��6���������� ��c" + args[1] + "��6 �Կ��� ���� �������� �ּ̽��ϴ�.");
								Methods.InvitePlayer(p, pl, Cname);
							} else {
								sender.sendMessage("��c�� �÷��̾�� �̹� ȸ�翡�� ���ϰ� ��ʴϴ�.");
							}
						} else {
							sender.sendMessage("��c�÷��̾ ã�� �� �����ϴ�.");
							return true;
						}
					} else {
						sender.sendMessage("��c�ڱ� �ڽ��� �ʴ��� �� �����ϴ�.");
						return true;
					}
				} else {
					sender.sendMessage("��c����� �ʴ��� ������ �����ϴ�.");
					return true;
				}
			} else {
				sender.sendMessage("��6/ȸ�� �ʴ� <�г���> ��f- <�г���>���� ȸ�� ������ �����մϴ�.");
				return true;
			}
		}
		
		else if(args[0].equalsIgnoreCase("����")) {
			String none = "NONE";
			String Cname = UserData.getStringYml(p, "�ʴ�");
			if (!(none.equals(Cname))) {
				if (Master == false && User == false) {
					File f = new File("plugins/MyCompany/CompanyList.yml");
					YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
					List<String> list;
			        list = config.getStringList("CompanyList");
			        int i = list.size();
					do {
						if(i == 0) {
							sender.sendMessage("��c����� �ʴ��� ȸ�簡 �Ļ�Ǿ� �ֽ��ϴ�.");
							UserData.SetStringData(p, "�ʴ�", "NONE");
							return true;
						}
						
		    			if(list.get(i) == Cname) {
		    				sender.sendMessage("��6���������� ��c" + Cname + "��6 ȸ�翡 �Ի��ϼ̽��ϴ�!");
		    				UserData.SetStringData(p, "�ʴ�", "NONE");
		    				UserData.SetStringData(p, "ȸ��", Cname);
		    				UserData.SetBooleanData(p, "����", true);
		    				try { Company.addUserList(args[1], Cname); } catch (IOException e) {}
		    			} i--;
					} while (i>-1);
				} else {
					sender.sendMessage("��c����� �̹� �Ի��� ȸ�簡 �ֽ��ϴ�.");
					return true;
				}
			} else {
				sender.sendMessage("��c����� �ʴ��� ȸ�簡 �������� �ʽ��ϴ�.");
				return true;
			}
		}
		
		else if(args[0].equalsIgnoreCase("����")) {
			String none = "NONE";
			String Cname = UserData.getStringYml(p, "�ʴ�");
			if (!(none.equals(Cname))) {
				if (Master == false && User == false) {
					File f = new File("plugins/MyCompany/CompanyList.yml");
					YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
					List<String> list;
			        list = config.getStringList("CompanyList");
			        int i = list.size();
					do {
						if(i == 0) {
							sender.sendMessage("��c����� �ʴ��� ȸ�簡 �Ļ�Ǿ� �ֽ��ϴ�.");
							UserData.SetStringData(p, "�ʴ�", "NONE");
							return true;
						}
						
						if(list.get(i) == Cname) {
							sender.sendMessage("��c" + Cname + "��6 ȸ���� �Ի� ������ ��c���� ��6�ϼ̽��ϴ�..");
							UserData.SetStringData(p, "�ʴ�", "NONE");
							return true;
						} i--;
					} while (i>-1);
				} else {
					sender.sendMessage("��c����� �̹� �Ի��� ȸ�簡 �ֽ��ϴ�.");
					return true;
				}
			} else {
				sender.sendMessage("��c����� �ʴ��� ȸ�簡 �������� �ʽ��ϴ�.");
				return true;
			}
		}
		
		else if(args[0].equalsIgnoreCase("����")) {
			if(args.length == 3) {
				if (Master == false && User == false) {
					if (Integer.parseInt(playerstat[3]) == 0) {
						String str = args[1];
						for (int i = Integer.parseInt(companyl[0])-1; i>-1; i--) {
							if (i == -1) {
								sender.sendMessage("��cȸ�簡 �������� �ʽ��ϴ�.");
								return true;
							}
							
							else if ((str.equals(companyn[i]))) {
								sender.sendMessage("��6���������� ��c" + args[1] + "��6 ȸ�翡 �̷¼��� �־����ϴ�.");
								Company.AddJoinList(p, args[1], args[2]);
								playerstat[3] = (Integer.parseInt(playerstat[3])+1)+"";
								UserData.SetFile(p, playerstat);
								try { 
									Player mnader = Method.getMasterName(args[1]); 
									if (Method.getOnorOffLine(mnader.getName()) == null) { return true; }
									else if (Method.getMaster(mnader, mnader.getName())) {
										mnader.sendMessage("��c" + p.getName() + "��6 ���� ����� ȸ�翡 ��c�̷¼���6�� �����̽��ϴ�.");
										mnader.sendMessage("��6/ȸ�� ��û���  ��f- �츮 ȸ���� ��û�� ����� ����� ���ϴ�.");
									}
								} catch (IOException e) {}
								return true;
							}
							
							else if (i==0) {
								sender.sendMessage("��cȸ�簡 �������� �ʽ��ϴ�.");
								return true;
							}
						}
						return true;
					} else {
						sender.sendMessage("��c����� �̹� ȸ�翡 �̷¼��� �����̽��ϴ�.");
						return true;
					}
				} else {
					sender.sendMessage("��c����� �̹� �Ի��� ȸ�簡 �ֽ��ϴ�.");
					return true;
				}
			} else {
				sender.sendMessage("��6/ȸ�� ���� <ȸ��> <����> ��f- ȸ�翡�� �ڱ�Ұ����� �����մϴ�.");
				sender.sendMessage("��f- ��b����� \"_\"�� ǥ�����ּ���.");
				return true;
			}
		}
		
		
		return false;
	}

}
