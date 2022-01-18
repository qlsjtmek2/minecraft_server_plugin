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
			if (commandLabel.equalsIgnoreCase("변수빼기")) {
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
			if (commandLabel.equalsIgnoreCase("괴인협회위치")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if (args.length == 1) {
						if (args[0].equalsIgnoreCase("대기실1")) {
							if (main.onePosition.contains(p)) {
								p.sendMessage("§c이미 첫번째 포지션을 지정중인 상태입니다.");
								return false;
							}
							
							if (main.twoPosition.contains(p)) {
								p.sendMessage("§c이미 두번째 포지션을 지정중인 상태입니다.");
								return false;
							}
							
							p.sendMessage("§6아무 아이템이나 들고 블럭을 좌클릭시 §c첫번째 좌표§6가 설정됩니다.");
							main.onePosition.add(p);
							return false;
						}
						
						else if (args[0].equalsIgnoreCase("대기실2")) {
							if (main.onePosition.contains(p)) {
								p.sendMessage("§c이미 첫번째 포지션을 지정중인 상태입니다.");
								return false;
							}
							
							if (main.twoPosition.contains(p)) {
								p.sendMessage("§c이미 두번째 포지션을 지정중인 상태입니다.");
								return false;
							}
							
							p.sendMessage("§6아무 아이템이나 들고 블럭을 좌클릭시 §c두번째 좌표§6가 설정됩니다.");
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
								p.sendMessage("§6성공적으로 괴인 1라운드 §c첫번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
							case "2":
								Position.setOne2(p.getLocation());
								p.sendMessage("§6성공적으로 괴인 1라운드 §c두번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
							case "3":
								Position.setOne3(p.getLocation());
								p.sendMessage("§6성공적으로 괴인 1라운드 §c세번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
							case "4":
								Position.setOne4(p.getLocation());
								p.sendMessage("§6성공적으로 괴인 1라운드 §c네번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
							default: HelpMessage(sender); return false;
							}
						}
						
						else if (args[0].equalsIgnoreCase("2")) {
							switch (args[1]) {
							case "1":
								Position.setTwo1(p.getLocation());
								p.sendMessage("§6성공적으로 괴인 2라운드 §c첫번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
							case "2":
								Position.setTwo2(p.getLocation());
								p.sendMessage("§6성공적으로 괴인 2라운드 §c두번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
							case "3":
								Position.setTwo3(p.getLocation());
								p.sendMessage("§6성공적으로 괴인 2라운드 §c세번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
							case "4":
								Position.setTwo4(p.getLocation());
								p.sendMessage("§6성공적으로 괴인 2라운드 §c네번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
							default: HelpMessage(sender); return false;
							}
						}
						
						else if (args[0].equalsIgnoreCase("3")) {
							switch (args[1]) {
							case "1":
								Position.setThree1(p.getLocation());
								p.sendMessage("§6성공적으로 괴인 3라운드 §c첫번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
							case "2":
								Position.setThree2(p.getLocation());
								p.sendMessage("§6성공적으로 괴인 3라운드 §c두번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
							case "3":
								Position.setThree3(p.getLocation());
								p.sendMessage("§6성공적으로 괴인 3라운드 §c세번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
							case "4":
								Position.setThree4(p.getLocation());
								p.sendMessage("§6성공적으로 괴인 3라운드 §c네번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
							default: HelpMessage(sender); return false;
							}
						}
						
						else if (args[0].equalsIgnoreCase("4")) {
							switch (args[1]) {
							case "1":
								Position.setFour1(p.getLocation());
								p.sendMessage("§6성공적으로 괴인 4라운드 §c첫번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
							case "2":
								Position.setFour2(p.getLocation());
								p.sendMessage("§6성공적으로 괴인 4라운드 §c두번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
							case "3":
								Position.setFour3(p.getLocation());
								p.sendMessage("§6성공적으로 괴인 4라운드 §c세번째 스폰 위치 §6를 설정하셨습니다.");
								return false;
							case "4":
								Position.setFour4(p.getLocation());
								p.sendMessage("§6성공적으로 괴인 4라운드 §c네번째 스폰 위치 §6를 설정하셨습니다.");
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
			if (commandLabel.equalsIgnoreCase("괴인협회입장")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					String party = PartyAPI.getJoinParty(p);
					if (main.CoolTime.get(p.getName()) != null) {
						p.sendMessage("§c현재 쿨타임이 " + main.CoolTime.get(p.getName()) + " 분 남아서 참가하실 수 없습니다.");
						return false;
					}
					
					if (party == null) {
    					if (main.Round) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c이미 누가 괴인 협회에서 전투중이므로 입장하실 수 없습니다.");
    						return false;
    					}
						
						if (!API.isInArea(p.getLocation())) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c괴인 협회 대기장에서만 입장이 가능합니다. [ Z시 -> 표지판 클릭 ]");
    						return false;
    					}
    					
						List<Player> pList = new ArrayList<Player>();
						pList.add(p);
						API.RoundStart(pList);
    					if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
    					p.closeInventory();
						return false;
					} else {
						if (!PartyAPI.getManager(party).equalsIgnoreCase(p.getName())) {
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.sendMessage("§c당신은 파티장이 아니므로 매칭을 진행하실 수 없습니다.");
							return false;
						}
    					
    					if (main.Round) {
    						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
    						p.sendMessage("§c이미 누가 괴인 협회에서 전투중이므로 입장하실 수 없습니다.");
    						return false;
    					}
						
						if (!API.isPartyUserInLocation(party)) {
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.sendMessage("§c파티원중 괴인 협회 입장 대기장 안에 있지 않는 사람이 있어 시작할 수 없습니다.");
							return false;
						}
						
						if (PartyAPI.getUserInteger(party) == 1) {
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							p.sendMessage("§c파티원이 한명일 때는 입장하실 수 없습니다.");
							return false;
						}
						
						for (String user : PartyAPI.getUser(party)) {
							Player pl = Method.getOnorOffLine(user);
							if (pl.equals(p)) continue;
							if (main.CoolTime.get(pl.getName()) != null) {
								p.sendMessage("§c현재 괴인 협회 입장 쿨타임이 남아있는 파티원이 존재하여 입장하실 수 없습니다. §l(" + pl.getName() + ")");
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
						p.sendMessage("§6파티원들에게 §c괴인 협회 입장 여부§6를 물어봅니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						return false;
					}
				}
			}
		} catch (NumberFormatException ex) { return false; }
		
		return false;
	}
	
	public static void HelpMessage(CommandSender sender) {
		sender.sendMessage("§e ---- §6괴인 협회 위치 설정 §e-- §6페이지 §c1§6/§c1 §e----");
		sender.sendMessage("§6/괴인협회위치 §f- 괴인협회 위치 설정 도움말을 봅니다.");
		sender.sendMessage("§6/괴인협회위치 <대기실1/대기실2> §f- 대기실 첫번쨰, 두번째 포지션을 지정합니다.");
		sender.sendMessage("§6/괴인협회위치 1 <1/2/3/4> §f- 1라운드 스폰되는 위치를 설정합니다.");
		sender.sendMessage("§6/괴인협회위치 2 <1/2/3/4> §f- 2라운드 스폰되는 위치를 설정합니다.");
		sender.sendMessage("§6/괴인협회위치 3 <1/2/3/4> §f- 3라운드 스폰되는 위치를 설정합니다.");
		sender.sendMessage("§6/괴인협회위치 4 <1/2/3/4> §f- 4라운드 스폰되는 위치를 설정합니다.");
	}
}
