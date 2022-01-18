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
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + 
								   ChatColor.WHITE + "]" + ChatColor.RED + " 콘솔에선 실행이 불가능 합니다.");
				return false;
			}
			
			Player p = (Player) sender;
			if (commandLabel.equalsIgnoreCase("펫")) {
				if ((args.length == 0)) {
					displayHelp(sender);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("상점") && args.length == 1) {
					API.sendCommand("chc open getar " + p.getName());
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("목록") && args.length == 1) {
					if (API.getPetList(p) == null || API.getPetList(p).isEmpty()) {
						p.sendMessage("§c당신은 소유중인 펫이 존재하지 않습니다.");
						return false;
					}
					
					p.playSound(p.getLocation(), Sound.DOOR_OPEN, 1.8F, 1.5F);
					GUI.openGUI(p);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("이름") && args.length >= 2) {
					String name = API.repixColor(API.getFinalArg(args, 1));
					boolean is = p.isOp();
					p.setOp(true);
					p.chat("/pet name " + name);
					API.setPetName(p, name);
					API.removeOP(p, is);
					p.sendMessage("§6펫 이름을 성공적으로 §e[ " + name + " §e] 으로 §c변경 §6하였습니다.");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("이름") && args.length == 1) {
					sender.sendMessage("§6/펫 이름 <이름> §f- 펫 이름을 변경합니다.");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("일반뽑기")) {
					if (!p.isOp()) {
						p.sendMessage("§c당신은 이 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!API.isShowPet(p)) {
						p.sendMessage("§c당신은 펫 비활성화 중이므로 상자를 오픈하실 수 없습니다. §e[ /펫 목록 -> 펫 정보 클릭 ]");
						API.sendCommand("rpgitem 일반랜덤펫박스 give " + p.getName() + " 1");
						return false;
					}
					
					String pet = API.rdomNormalPet();
					String name = API.PetToString(pet);
					if (API.isPet(p, pet)) {
						p.sendMessage("§c안타깝게도 이미 보유중인 펫이 소환되었습니다. §e[ §6" + name + " §e]");
						return false;
					}
					
					API.addPet(p, pet);
					p.sendMessage("§6축하합니다! §c일반 랜덤 펫 박스§6에서 §e[ §6" + name + " §e] §6이/가 나왔습니다.");
				    p.sendMessage("§c펫 이름§6은 §e[ /펫 이름 <이름> ] §6으로 변경할 수 있습니다.");
				    API.appCommandPet(p, pet);
				    API.setShowPet(p, true);
					API.castLvup(p);
				}
				
				else if (args[0].equalsIgnoreCase("레어뽑기")) {
					if (!p.isOp()) {
						sender.sendMessage("§c당신은 이 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!API.isShowPet(p)) {
						p.sendMessage("§c당신은 펫 비활성화 중이므로 상자를 오픈하실 수 없습니다. §e[ /펫 목록 -> 펫 정보 클릭 ]");
						API.sendCommand("rpgitem 레어랜덤펫박스 give " + p.getName() + " 1");
						return false;
					}
					
					String pet = API.rdomRarePet();
					String name = API.PetToString(pet);
					if (API.isPet(p, pet)) {
						p.sendMessage("§c안타깝게도 이미 보유중인 펫이 소환되었습니다. §e[ §6" + name + " §e]");
						return false;
					}
					
					API.addPet(p, pet);
					p.sendMessage("§6축하합니다! §c레어 랜덤 펫 박스§6에서 §e[ §6" + name + " §e] §6이/가 나왔습니다.");
				    p.sendMessage("§c펫 이름§6은 §e[ /펫 이름 <이름> ] §6으로 변경할 수 있습니다.");
				    API.appCommandPet(p, pet);
				    API.setShowPet(p, true);
					API.castLvup(p);
				}
				
				else if (args[0].equalsIgnoreCase("양털뽑기")) {
					if (!p.isOp()) {
						sender.sendMessage("§c당신은 이 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!API.isShowPet(p)) {
						p.sendMessage("§c당신은 펫 비활성화 중이므로 상자를 오픈하실 수 없습니다. §e[ /펫 목록 -> 펫 정보 클릭 ]");
						API.sendCommand("rpgitem 양털색변경권 give " + p.getName() + " 1");
						return false;
					}
					
					String pet = API.rdomColorPet();
					String name = API.PetToString(pet);
					if (API.isPet(p, pet)) {
						p.sendMessage("§c안타깝게도 이미 보유중인 펫이 소환되었습니다. §e[ §6" + name + " §e]");
						return false;
					}
					
					API.addPet(p, pet);
					p.sendMessage("§6축하합니다! §c양 펫 박스§6에서 §e[ §6" + name + " §e] §6이/가 나왔습니다.");
				    p.sendMessage("§c펫 이름§6은 §e[ /펫 이름 <이름> ] §6으로 변경할 수 있습니다.");
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
		sender.sendMessage(" §e----- §6펫 §e--- §6도움말 §e-----");
		sender.sendMessage("§6/펫 §f- 펫 도움말을 확인합니다.");
		sender.sendMessage("§6/펫 상점 §f- 펫 상점을 오픈합니다.");
		sender.sendMessage("§6/펫 이름 <이름> §f- 펫 이름을 변경합니다.");
		sender.sendMessage("§6/펫 목록 §f- 내 펫의 목록을 확인합니다.");
		
		if (sender.isOp()) {
			sender.sendMessage("§6/펫 일반뽑기 §f- 일반 펫을 뽑습니다.");
			sender.sendMessage("§6/펫 레어뽑기 §f- 레어 펫을 뽑습니다.");
			sender.sendMessage("§6/펫 양털뽑기 §f- 양털 색을 뽑습니다.");
		}
	}
}
