package me.espoo.dunzeon;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.rpg.Method;
import me.espoo.rpg.party.PartyAPI;

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
			if (commandLabel.equalsIgnoreCase("��������")) {
				if (args.length == 1) {
					switch (args[0]) {
					case "1":
						if (main.oneRound <= 0) return false;
						main.oneRound--; return false;
					case "2":
						if (main.twoRound <= 0) return false;
						main.twoRound--; return false;
					case "3":
						if (main.threeRound <= 0) return false;
						main.threeRound--; return false;
					case "4":
						if (main.fourRound <= 0) return false;
						main.fourRound--; return false;
					}
				}
			}
		} catch (NumberFormatException ex) { return false; }
		
		try {
			if (commandLabel.equalsIgnoreCase("������ȸ��ġ")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 1) {
						if (args[0].equalsIgnoreCase("����1")) {
							if (main.onePosition.contains(p)) {
								p.sendMessage("��c�̹� ù��° �������� �������� �����Դϴ�.");
								return false;
							}
							
							if (main.twoPosition.contains(p)) {
								p.sendMessage("��c�̹� �ι�° �������� �������� �����Դϴ�.");
								return false;
							}
							
							p.sendMessage("��6�ƹ� �������̳� ��� ���� ��Ŭ���� ��cù��° ��ǥ��6�� �����˴ϴ�.");
							main.onePosition.add(p);
							return false;
						}
						
						else if (args[0].equalsIgnoreCase("����2")) {
							if (main.onePosition.contains(p)) {
								p.sendMessage("��c�̹� ù��° �������� �������� �����Դϴ�.");
								return false;
							}
							
							if (main.twoPosition.contains(p)) {
								p.sendMessage("��c�̹� �ι�° �������� �������� �����Դϴ�.");
								return false;
							}
							
							p.sendMessage("��6�ƹ� �������̳� ��� ���� ��Ŭ���� ��c�ι�° ��ǥ��6�� �����˴ϴ�.");
							main.twoPosition.add(p);
							return false;
						}
						
						else {
							HelpMessage(sender);
							return false;
						}
					}
					
					else if (args.length == 2) {
						if (args[0].equalsIgnoreCase("1")) {
							switch (args[1]) {
							case "1":
								Position.setOne1(p.getLocation());
								p.sendMessage("��6���������� ���� 1���� ��cù��° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
							case "2":
								Position.setOne2(p.getLocation());
								p.sendMessage("��6���������� ���� 1���� ��c�ι�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
							case "3":
								Position.setOne3(p.getLocation());
								p.sendMessage("��6���������� ���� 1���� ��c����° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
							case "4":
								Position.setOne4(p.getLocation());
								p.sendMessage("��6���������� ���� 1���� ��c�׹�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
							default: HelpMessage(sender); return false;
							}
						}
						
						else if (args[0].equalsIgnoreCase("2")) {
							switch (args[1]) {
							case "1":
								Position.setTwo1(p.getLocation());
								p.sendMessage("��6���������� ���� 2���� ��cù��° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
							case "2":
								Position.setTwo2(p.getLocation());
								p.sendMessage("��6���������� ���� 2���� ��c�ι�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
							case "3":
								Position.setTwo3(p.getLocation());
								p.sendMessage("��6���������� ���� 2���� ��c����° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
							case "4":
								Position.setTwo4(p.getLocation());
								p.sendMessage("��6���������� ���� 2���� ��c�׹�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
							default: HelpMessage(sender); return false;
							}
						}
						
						else if (args[0].equalsIgnoreCase("3")) {
							switch (args[1]) {
							case "1":
								Position.setThree1(p.getLocation());
								p.sendMessage("��6���������� ���� 3���� ��cù��° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
							case "2":
								Position.setThree2(p.getLocation());
								p.sendMessage("��6���������� ���� 3���� ��c�ι�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
							case "3":
								Position.setThree3(p.getLocation());
								p.sendMessage("��6���������� ���� 3���� ��c����° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
							case "4":
								Position.setThree4(p.getLocation());
								p.sendMessage("��6���������� ���� 3���� ��c�׹�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
							default: HelpMessage(sender); return false;
							}
						}
						
						else if (args[0].equalsIgnoreCase("4")) {
							switch (args[1]) {
							case "1":
								Position.setFour1(p.getLocation());
								p.sendMessage("��6���������� ���� 4���� ��cù��° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
							case "2":
								Position.setFour2(p.getLocation());
								p.sendMessage("��6���������� ���� 4���� ��c�ι�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
							case "3":
								Position.setFour3(p.getLocation());
								p.sendMessage("��6���������� ���� 4���� ��c����° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
							case "4":
								Position.setFour4(p.getLocation());
								p.sendMessage("��6���������� ���� 4���� ��c�׹�° ���� ��ġ ��6�� �����ϼ̽��ϴ�.");
								return false;
							default: HelpMessage(sender); return false;
							}
						}
						
						else {
							HelpMessage(sender);
							return false;
						}
					}
					
					else {
						HelpMessage(sender);
						return false;
					}
				}
			}
		} catch (NumberFormatException ex) { return false; }
		
		try {
			if (commandLabel.equalsIgnoreCase("������ȸ����")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					String party = PartyAPI.getJoinParty(p);
					if (main.CoolTime.get(p.getName()) != null) {
						p.sendMessage("��c���� ��Ÿ���� " + main.CoolTime.get(p.getName()) + " �� ���Ƽ� �����Ͻ� �� �����ϴ�.");
						return false;
					}
					
					if (party == null) {
    					if (main.Round) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c�̹� ���� ���� ��ȸ���� �������̹Ƿ� �����Ͻ� �� �����ϴ�.");
    						return false;
    					}
						
						if (!API.isInArea(p.getLocation())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c���� ��ȸ ����忡���� ������ �����մϴ�. [ Z�� -> ǥ���� Ŭ�� ]");
    						return false;
    					}
    					
						List<Player> pList = new ArrayList<Player>();
						pList.add(p);
						API.RoundStart(pList);
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					p.closeInventory();
						return false;
					} else {
						if (!PartyAPI.getManager(party).equalsIgnoreCase(p.getName())) {
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.sendMessage("��c����� ��Ƽ���� �ƴϹǷ� ��Ī�� �����Ͻ� �� �����ϴ�.");
							return false;
						}
    					
    					if (main.Round) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("��c�̹� ���� ���� ��ȸ���� �������̹Ƿ� �����Ͻ� �� �����ϴ�.");
    						return false;
    					}
						
						if (!API.isPartyUserInLocation(party)) {
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.sendMessage("��c��Ƽ���� ���� ��ȸ ���� ����� �ȿ� ���� �ʴ� ����� �־� ������ �� �����ϴ�.");
							return false;
						}
						
						if (PartyAPI.getUserInteger(party) == 1) {
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.sendMessage("��c��Ƽ���� �Ѹ��� ���� �����Ͻ� �� �����ϴ�.");
							return false;
						}
						
						for (String user : PartyAPI.getUser(party)) {
							Player pl = Method.getOnorOffLine(user);
							if (pl.equals(p)) continue;
							if (main.CoolTime.get(pl.getName()) != null) {
								p.sendMessage("��c���� ���� ��ȸ ���� ��Ÿ���� �����ִ� ��Ƽ���� �����Ͽ� �����Ͻ� �� �����ϴ�. ��l(" + pl.getName() + ")");
								return false;
							}
						}
						
						for (String user : PartyAPI.getUser(party)) {
							Player pl = Method.getOnorOffLine(user);
							if (pl.equals(p)) continue;
							GUI.CheckPvPGUI(pl, PartyAPI.getUserInteger(party) - 1);
						}
						
						if (main.PartyCheck.get(party) != null) main.PartyCheck.remove(party);
						main.PartyCheck.put(party, PartyAPI.getUserInteger(party) - 1);
						p.sendMessage("��6��Ƽ���鿡�� ��c���� ��ȸ ���� ���Ρ�6�� ����ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						return false;
					}
				}
			}
		} catch (NumberFormatException ex) { return false; }
		
		return false;
	}
	
	public static void HelpMessage(CommandSender sender) {
		sender.sendMessage("��e ---- ��6���� ��ȸ ��ġ ���� ��e-- ��6������ ��c1��6/��c1 ��e----");
		sender.sendMessage("��6/������ȸ��ġ ��f- ������ȸ ��ġ ���� ������ ���ϴ�.");
		sender.sendMessage("��6/������ȸ��ġ <����1/����2> ��f- ���� ù����, �ι�° �������� �����մϴ�.");
		sender.sendMessage("��6/������ȸ��ġ 1 <1/2/3/4> ��f- 1���� �����Ǵ� ��ġ�� �����մϴ�.");
		sender.sendMessage("��6/������ȸ��ġ 2 <1/2/3/4> ��f- 2���� �����Ǵ� ��ġ�� �����մϴ�.");
		sender.sendMessage("��6/������ȸ��ġ 3 <1/2/3/4> ��f- 3���� �����Ǵ� ��ġ�� �����մϴ�.");
		sender.sendMessage("��6/������ȸ��ġ 4 <1/2/3/4> ��f- 4���� �����Ǵ� ��ġ�� �����մϴ�.");
	}
}
