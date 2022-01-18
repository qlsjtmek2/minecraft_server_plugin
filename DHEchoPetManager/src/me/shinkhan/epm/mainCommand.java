package me.shinkhan.epm;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;
	
	public mainCommand(main instance)
	{
		this.plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + 
								   ChatColor.WHITE + "]" + ChatColor.RED + " �ֿܼ��� ������ �Ұ��� �մϴ�.");
				return false;
			}
			
			Player p = (Player) sender;
			if (commandLabel.equalsIgnoreCase("��")) {
				if ((args.length == 0)) {
					displayHelp(sender);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("����") && args.length == 1) {
					API.sendCommand("chc open getar " + p.getName());
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("���") && args.length == 1) {
					if (API.getPetList(p) == null || API.getPetList(p).isEmpty()) {
						p.sendMessage("��c����� �������� ���� �������� �ʽ��ϴ�.");
						return false;
					}
					
					p.playSound(p.getLocation(), Sound.DOOR_OPEN, 1.8F, 1.5F);
					GUI.openGUI(p);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("�̸�") && args.length >= 2) {
					String name = API.repixColor(API.getFinalArg(args, 1));
					boolean is = p.isOp();
					p.setOp(true);
					p.chat("/pet name " + name);
					API.setPetName(p, name);
					API.removeOP(p, is);
					p.sendMessage("��6�� �̸��� ���������� ��e[ " + name + " ��e] ���� ��c���� ��6�Ͽ����ϴ�.");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("�̸�") && args.length == 1) {
					sender.sendMessage("��6/�� �̸� <�̸�> ��f- �� �̸��� �����մϴ�.");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("�Ϲݻ̱�")) {
					if (!p.isOp()) {
						p.sendMessage("��c����� �� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!API.isShowPet(p)) {
						p.sendMessage("��c����� �� ��Ȱ��ȭ ���̹Ƿ� ���ڸ� �����Ͻ� �� �����ϴ�. ��e[ /�� ��� -> �� ���� Ŭ�� ]");
						API.sendCommand("rpgitem �Ϲݷ�����ڽ� give " + p.getName() + " 1");
						return false;
					}
					
					String pet = API.rdomNormalPet();
					String name = API.PetToString(pet);
					if (API.isPet(p, pet)) {
						p.sendMessage("��c��Ÿ���Ե� �̹� �������� ���� ��ȯ�Ǿ����ϴ�. ��e[ ��6" + name + " ��e]");
						return false;
					}
					
					API.addPet(p, pet);
					p.sendMessage("��6�����մϴ�! ��c�Ϲ� ���� �� �ڽ���6���� ��e[ ��6" + name + " ��e] ��6��/�� ���Խ��ϴ�.");
				    p.sendMessage("��c�� �̸���6�� ��e[ /�� �̸� <�̸�> ] ��6���� ������ �� �ֽ��ϴ�.");
				    API.appCommandPet(p, pet);
				    API.setShowPet(p, true);
					API.castLvup(p);
				}
				
				else if (args[0].equalsIgnoreCase("����̱�")) {
					if (!p.isOp()) {
						sender.sendMessage("��c����� �� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!API.isShowPet(p)) {
						p.sendMessage("��c����� �� ��Ȱ��ȭ ���̹Ƿ� ���ڸ� �����Ͻ� �� �����ϴ�. ��e[ /�� ��� -> �� ���� Ŭ�� ]");
						API.sendCommand("rpgitem �������ڽ� give " + p.getName() + " 1");
						return false;
					}
					
					String pet = API.rdomRarePet();
					String name = API.PetToString(pet);
					if (API.isPet(p, pet)) {
						p.sendMessage("��c��Ÿ���Ե� �̹� �������� ���� ��ȯ�Ǿ����ϴ�. ��e[ ��6" + name + " ��e]");
						return false;
					}
					
					API.addPet(p, pet);
					p.sendMessage("��6�����մϴ�! ��c���� ���� �� �ڽ���6���� ��e[ ��6" + name + " ��e] ��6��/�� ���Խ��ϴ�.");
				    p.sendMessage("��c�� �̸���6�� ��e[ /�� �̸� <�̸�> ] ��6���� ������ �� �ֽ��ϴ�.");
				    API.appCommandPet(p, pet);
				    API.setShowPet(p, true);
					API.castLvup(p);
				}
				
				else if (args[0].equalsIgnoreCase("���л̱�")) {
					if (!p.isOp()) {
						sender.sendMessage("��c����� �� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!API.isShowPet(p)) {
						p.sendMessage("��c����� �� ��Ȱ��ȭ ���̹Ƿ� ���ڸ� �����Ͻ� �� �����ϴ�. ��e[ /�� ��� -> �� ���� Ŭ�� ]");
						API.sendCommand("rpgitem ���л������ give " + p.getName() + " 1");
						return false;
					}
					
					String pet = API.rdomColorPet();
					String name = API.PetToString(pet);
					if (API.isPet(p, pet)) {
						p.sendMessage("��c��Ÿ���Ե� �̹� �������� ���� ��ȯ�Ǿ����ϴ�. ��e[ ��6" + name + " ��e]");
						return false;
					}
					
					API.addPet(p, pet);
					p.sendMessage("��6�����մϴ�! ��c�� �� �ڽ���6���� ��e[ ��6" + name + " ��e] ��6��/�� ���Խ��ϴ�.");
				    p.sendMessage("��c�� �̸���6�� ��e[ /�� �̸� <�̸�> ] ��6���� ������ �� �ֽ��ϴ�.");
				    API.appCommandPet(p, pet);
				    API.setShowPet(p, true);
					API.castLvup(p);
				}
				
				return false;
			}
		} catch (NumberFormatException ex) {
			displayHelp(sender);
			return false;
		}
		
		return false;
	}
	
	private void displayHelp(CommandSender sender)
	{
		sender.sendMessage(" ��e----- ��6�� ��e--- ��6���� ��e-----");
		sender.sendMessage("��6/�� ��f- �� ������ Ȯ���մϴ�.");
		sender.sendMessage("��6/�� ���� ��f- �� ������ �����մϴ�.");
		sender.sendMessage("��6/�� �̸� <�̸�> ��f- �� �̸��� �����մϴ�.");
		sender.sendMessage("��6/�� ��� ��f- �� ���� ����� Ȯ���մϴ�.");
		
		if (sender.isOp()) {
			sender.sendMessage("��6/�� �Ϲݻ̱� ��f- �Ϲ� ���� �̽��ϴ�.");
			sender.sendMessage("��6/�� ����̱� ��f- ���� ���� �̽��ϴ�.");
			sender.sendMessage("��6/�� ���л̱� ��f- ���� ���� �̽��ϴ�.");
		}
	}
}
