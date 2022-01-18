package me.shinkhan.inquiry;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	public static final ChatColor YELLOW = ChatColor.YELLOW;
	public static final ChatColor GOLD = ChatColor.GOLD;
	public static final ChatColor WHITE = ChatColor.WHITE;
	public static final ChatColor BLUE = ChatColor.AQUA;
	public static final ChatColor RED = ChatColor.RED;
	public static final ChatColor DARK_RED = ChatColor.DARK_RED;
	public static final ChatColor GRAY = ChatColor.GRAY;
	
	public mainCommand(main instance)
	{
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("����") || commandLabel.equalsIgnoreCase("q")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 0) {
						HelpMessage(sender);
						return false;
					}
					
					else {
						if (args[0].equalsIgnoreCase("����") || args[0].equalsIgnoreCase("is")) {
							String message = API.getInquiry(p.getName());
							if (message == null) {
								p.sendMessage("��a���� ������� ���� ��û�� �����ϴ�.");
							} else {
								p.sendMessage("��bó�� ������ ����: ��f" + message);
							} return false;
						}
						
						else if (args[0].equalsIgnoreCase("Ȯ��") || args[0].equalsIgnoreCase("c")) {
							if (!p.isOp()) {
								p.sendMessage("��c����� ������ �����ϴ�.");
								return false;
							}
							
							int k = 1;
							if (args.length == 2) k = Integer.parseInt(args[1]);
							if (args.length > 2 || args.length == 0) {
								HelpMessage(sender);
								return false;
							}

							List<String> list = Config.getList("����");
					        if (list == null || list.isEmpty() || k * 10 - 9 > list.size() || k == 0) {
					            sender.sendMessage(RED + "�ش� ����� �������� �ʽ��ϴ�.");
					            return false;
					        }
					        
					        if (list.size() % 10 == 0) {
					            sender.sendMessage(RED + "" + list.size() + GOLD + " ���� ���Ǹ� ã�ҽ��ϴ�. " + RED  + k + GOLD + "/" + RED + list.size() / 10);
					        } else {
					            sender.sendMessage(RED + "" + list.size() + GOLD + " ���� ���Ǹ� ã�ҽ��ϴ�. " + RED  + k + GOLD + "/" + RED + (list.size() / 10 + 1));
					        }
					        
					        for (int j = (k - 1) * 10; j < k * 10; ++j) {
								String[] str = list.get(j).split(",");
								p.sendMessage("��6" + str[0] + ": ��f" + str[1]);
								
					            if (list.size() == j + 1) {
					                break;
					            }
					            
					            if (k * 10 - 1 == j && list.size() > j + 1) {
									sender.sendMessage(GOLD + "���� ����� ���÷��� " + RED + "/���� Ȯ�� " + (k + 1) + " " + GOLD + "��/�� �Է����ּ���.");
					            }
					        } return false;
						}
						
						else if (args[0].equalsIgnoreCase("ó��") && args.length == 2 || args[0].equalsIgnoreCase("h") && args.length == 2) {
							if (!p.isOp()) {
								p.sendMessage("��c����� ������ �����ϴ�.");
								return false;
							}
							
							String name = args[1];
							if (API.isInquiry(name)) {
								p.sendMessage("��6���������� ��c" + name + " ��6���� ���ǰ� ó���Ǿ����ϴ�.");
								p.sendMessage("��6����: ��c" + API.getInquiry(name));
								API.removeInquiry(name);
							} else {
								p.sendMessage("��c�� �÷��̾��� ���Ǵ� �������� �ʽ��ϴ�.");
							} return false;
						}

						else if (args[0].equalsIgnoreCase("���ó��") || args[0].equalsIgnoreCase("ah")) {
							if (!p.isOp()) {
								p.sendMessage("��c����� ������ �����ϴ�.");
								return false;
							}
							
							if (Config.getList("����") == null) {
								p.sendMessage("��có���� ���ǰ� �������� �ʽ��ϴ�.");
								return false;
							}
							
							API.clearInquiry();
							p.sendMessage("��6��� ���ǰ� ���������� ��có����6�Ǿ����ϴ�.");
							return false;
						}
						
						else {
							if (API.isInquiry(p.getName())) {
								p.sendMessage("��c����� �̹� ������ ���ǰ� �����մϴ�.");
								return false;
							}
							
							String message = API.getFinalArg(args, 0);
							API.addInquiry(p.getName(), message);
							p.sendMessage("��6���������� ���Ǹ� ��c�����6�ϼ̽��ϴ�.");
							p.sendMessage("��6������ ����: ��f" + message);
							
							for (Player pl : Bukkit.getOnlinePlayers()) {
								if (pl.isOp()) {
									pl.sendMessage("��6���ο� ���ǰ� ���Խ��ϴ�. ��e[ /���� Ȯ�� ][ /q c ]");
								}
							} return false;
						}
					}
				} else {
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "]" + ChatColor.RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			if (sender instanceof Player) {
				HelpMessage(sender);
				return false;
			} else {
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "]" + ChatColor.RED + " �ֿܼ��� ������ �Ұ����� ��ɾ� �Դϴ�.");
				return false;
			}
		} return false;
	}
	
	public static void HelpMessage(CommandSender sender) {
		sender.sendMessage("��e ---- ��6���� ��e-- ��6������ ��c1��6/��c1 ��e----");
		sender.sendMessage("��6/����, /q ��f- ���� ��ɾ� ������ Ȯ���մϴ�.");
		sender.sendMessage("��6/���� <���� ����> ��f- ���Ǹ� �����մϴ�. (�ߺ� �Ұ���)");
		sender.sendMessage("��6/���� <����/is> ��f- ���ǰ� ó�� �Ǿ����� �ƴ��� ���θ� Ȯ���մϴ�.");
		if (sender.isOp()) {
			sender.sendMessage("��b/���� <Ȯ��/c> ��f- ���� ������ ä������ Ȯ���մϴ�.");
			sender.sendMessage("��b/���� <ó��/h> <�г���> ��f- �÷��̾��� ���Ǹ� ó���մϴ�.");
			sender.sendMessage("��b/���� <���ó��/ah> ��f- ��� ���Ǹ� ó���մϴ�.");
		}
		
		sender.sendMessage("");
		sender.sendMessage("��e * ���� ���뿡 ��Ӿ�, ������ ��Ұ� �� �� ó���� ������ �� �ֽ��ϴ�.");
	}
}
