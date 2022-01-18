package me.espoo.pet;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener 
{
	HashMap<String, String> petName = new HashMap<String, String>();
	  
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	  
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (!(sender instanceof Player))
			{
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + 
								   ChatColor.WHITE + "]" + ChatColor.RED + " 콘솔에선 실행이 불가능 합니다.");
				return false;
			}
			
			Player p = (Player) sender;
			if (commandLabel.equalsIgnoreCase("펫"))
			{
				if ((args.length == 0))
				{
					displayHelp(sender);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("방생") && args.length == 1)
				{
					if (!(Methods.getBooleanYml(p, "havepet"))) {
						sender.sendMessage("§c당신은 소유중인 펫이 없습니다.");
						Methods.setBooleanYml(p, "defaultpet", false);
						return false;
					}
					else {
						sender.sendMessage("§c나 없이도 행복한 삶을 살아 펫아..");
						sender.sendMessage("§6 방생을 §c완료 §6했습니다.");
						Methods.setBooleanYml(p, "havepet", false);
						Methods.setBooleanYml(p, "defaultpet", false);
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet default remove");
							p.chat("/pet remove");
							p.setOp(false);
						} else {
							p.chat("/pet default remove");
							p.chat("/pet remove");
						}
						
						if (Methods.getBooleanYml(p, "chicken")) {
							Methods.setBooleanYml(p, "chicken", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "cow")) {
							Methods.setBooleanYml(p, "cow", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "creeper")) {
							Methods.setBooleanYml(p, "creeper", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "pig")) {
							Methods.setBooleanYml(p, "pig", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "sheep")) {
							Methods.setBooleanYml(p, "sheep", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "skeleton")) {
							Methods.setBooleanYml(p, "skeleton", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "slime")) {
							Methods.setBooleanYml(p, "slime", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "wolf")) {
							Methods.setBooleanYml(p, "wolf", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "zombie")) {
							Methods.setBooleanYml(p, "zombie", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "bat")) {
							Methods.setBooleanYml(p, "bat", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "blaze")) {
							Methods.setBooleanYml(p, "blaze", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "irongolem")) {
							Methods.setBooleanYml(p, "irongolem", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "magmacube")) {
							Methods.setBooleanYml(p, "magmacube", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "magmacube")) {
							Methods.setBooleanYml(p, "magmacube", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "pigzombie")) {
							Methods.setBooleanYml(p, "pigzombie", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "mushroomcow")) {
							Methods.setBooleanYml(p, "mushroomcow", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "ocelot")) {
							Methods.setBooleanYml(p, "ocelot", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "ocelotB")) {
							Methods.setBooleanYml(p, "ocelotB", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "ocelotS")) {
							Methods.setBooleanYml(p, "ocelotS", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "villager")) {
							Methods.setBooleanYml(p, "villager", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "sheepOrange")) {
							Methods.setBooleanYml(p, "sheepOrange", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "sheepMagenta")) {
							Methods.setBooleanYml(p, "sheepMagenta", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "sheepLightblue")) {
							Methods.setBooleanYml(p, "sheepLightblue", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "sheepYellow")) {
							Methods.setBooleanYml(p, "sheepYellow", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "sheepLime")) {
							Methods.setBooleanYml(p, "sheepLime", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "sheepPink")) {
							Methods.setBooleanYml(p, "sheepPink", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "sheepGray")) {
							Methods.setBooleanYml(p, "sheepGray", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "sheepSilver")) {
							Methods.setBooleanYml(p, "sheepSilver", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "sheepCyan")) {
							Methods.setBooleanYml(p, "sheepCyan", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "sheepPurple")) {
							Methods.setBooleanYml(p, "sheepPurple", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "sheepBlue")) {
							Methods.setBooleanYml(p, "sheepBlue", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "sheepBrown")) {
							Methods.setBooleanYml(p, "sheepBrown", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "sheepGreen")) {
							Methods.setBooleanYml(p, "sheepGreen", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "sheepRed")) {
							Methods.setBooleanYml(p, "sheepRed", false);
							return false;
						}
						
						else if (Methods.getBooleanYml(p, "sheepBlack")) {
							Methods.setBooleanYml(p, "sheepBlack", false);
							return false;
						}
						
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("해제") && args.length == 1)
				{
					if (Methods.getBooleanYml(p, "defaultpet")) {
						sender.sendMessage("§c펫§6이 성공적으로 §c해제§6되었습니다.");
						Methods.setBooleanYml(p, "defaultpet", false);
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet default remove");
							p.chat("/pet remove");
							p.setOp(false);
						} else {
							p.chat("/pet default remove");
							p.chat("/pet remove");
						}
						
						return false;
					} else {
						sender.sendMessage("§c당신의 펫은 이미 해제되어있습니다.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("일반뽑기"))
				{
					if (sender.isOp()) {
						if (Methods.getBooleanYml(p, "havepet")) {
							this.getServer().dispatchCommand(this.getServer().getConsoleSender(), "rpgitem 일반랜덤펫박스 give " + p.getName() + " 1");
							sender.sendMessage("§c당신은 이미 소유중인 펫이 있습니다.");
							return false;
						} else {
							Methods.setBooleanYml(p, "defaultpet", true);
							int RandomPet = new Random().nextInt(12) + 1;
							switch (RandomPet) {
								case 1:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "chicken", true);
									sender.sendMessage("§6축하합니다! §c일반 랜덤 펫 박스§6에서 §e[ §6아기 닭 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet chicken:baby");

										p.setOp(false);
									} else {
										p.chat("/pet chicken:baby");

									} break;
									
								case 2:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "cow", true);
									sender.sendMessage("§6축하합니다! §c일반 랜덤 펫 박스§6에서 §e[ §6아기 소 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet cow:baby");

										p.setOp(false);
									} else {
										p.chat("/pet cow:baby");

									} break;
									
								case 3:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "creeper", true);
									sender.sendMessage("§6축하합니다! §c일반 랜덤 펫 박스§6에서 §e[ §6크리퍼 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet creeper");

										p.setOp(false);
									} else {
										p.chat("/pet creeper");

									} break;
									
								case 4:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "pig", true);
									sender.sendMessage("§6축하합니다! §c일반 랜덤 펫 박스§6에서 §e[ §6아기 돼지 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet pig:baby");

										p.setOp(false);
									} else {
										p.chat("/pet pig:baby");

									} break;
									
								case 5:
									Methods.setBooleanYml(p, "petsheep", true);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "sheep", true);
									sender.sendMessage("§6축하합니다! §c일반 랜덤 펫 박스§6에서 §e[ §6아기 양 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet sheep:baby");

										p.setOp(false);
									} else {
										p.chat("/pet sheep:baby");

									} break;
									
								case 6:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "skeleton", true);
									sender.sendMessage("§6축하합니다! §c일반 랜덤 펫 박스§6에서 §e[ §6스켈레톤 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet skeleton");

										p.setOp(false);
									} else {
										p.chat("/pet skeleton");

									} break;
									
								case 7:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "slime", true);
									sender.sendMessage("§6축하합니다! §c일반 랜덤 펫 박스§6에서 §e[ §6아기 슬라임 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet slime:small");

										p.setOp(false);
									} else {
										p.chat("/pet slime:small");

									} break;
									
								case 8:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "wolf", true);
									sender.sendMessage("§6축하합니다! §c일반 랜덤 펫 박스§6에서 §e[ §6아기 늑대 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet wolf:baby");

										p.setOp(false);
									} else {
										p.chat("/pet wolf:baby");

									} break;
									
								case 9:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "zombie", true);
									sender.sendMessage("§6축하합니다! §c일반 랜덤 펫 박스§6에서 §e[ §6아기 좀비 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet zombie:baby");

										p.setOp(false);
									} else {
										p.chat("/pet zombie:baby");

									} break;
									
								case 10:
									Methods.setBooleanYml(p, "petsheep", true);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "sheep", true);
									sender.sendMessage("§6축하합니다! §c일반 랜덤 펫 박스§6에서 §e[ §6아기 양 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet sheep:baby");

										p.setOp(false);
									} else {
										p.chat("/pet sheep:baby");

									} break;
									
								case 11:
									Methods.setBooleanYml(p, "petsheep", true);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "sheep", true);
									sender.sendMessage("§6축하합니다! §c일반 랜덤 펫 박스§6에서 §e[ §6아기 양 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet sheep:baby");

										p.setOp(false);
									} else {
										p.chat("/pet sheep:baby");

									} break;
									
								case 12:
									Methods.setBooleanYml(p, "petsheep", true);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "sheep", true);
									sender.sendMessage("§6축하합니다! §c일반 랜덤 펫 박스§6에서 §e[ §6아기 양 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet sheep:baby");

										p.setOp(false);
									} else {
										p.chat("/pet sheep:baby");

									} break;
							}
							return false;
						}
					} else {
						sender.sendMessage("§c당신은 이 명령어를 실행할 권한이 없습니다.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("레어뽑기"))
				{
					if (sender.isOp()) {
						if (Methods.getBooleanYml(p, "havepet")) {
							this.getServer().dispatchCommand(this.getServer().getConsoleSender(), "rpgitem 레어랜덤펫박스 give " + p.getName() + " 1");
							sender.sendMessage("§c당신은 이미 소유중인 펫이 있습니다.");
							return false;
						} else {
							Methods.setBooleanYml(p, "defaultpet", true);
							int RandomPet = new Random().nextInt(10) + 1;
							switch (RandomPet) {
								case 1:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "bat", true);
									sender.sendMessage("§6축하합니다! §c레어 랜덤 펫 박스§6에서 §e[ §6박쥐 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet bat");

										p.setOp(false);
									} else {
										p.chat("/pet bat");

									} break;
									
								case 2:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "blaze", true);
									sender.sendMessage("§6축하합니다! §c레어 랜덤 펫 박스§6에서 §e[ §6블레이즈 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet blaze");

										p.setOp(false);
									} else {
										p.chat("/pet blaze");

									} break;
									
								case 3:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "irongolem", true);
									sender.sendMessage("§6축하합니다! §c레어 랜덤 펫 박스§6에서 §e[ §6철 골렘 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet irongolem");

										p.setOp(false);
									} else {
										p.chat("/pet irongolem");

									} break;
									
								case 4:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "magmacube", true);
									sender.sendMessage("§6축하합니다! §c레어 랜덤 펫 박스§6에서 §e[ §6아기 마그마 큐브 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet magmacube:small");

										p.setOp(false);
									} else {
										p.chat("/pet magmacube:small");

									} break;
									
								case 5:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "ocelot", true);
									sender.sendMessage("§6축하합니다! §c레어 랜덤 펫 박스§6에서 §e[ §6아기 오셀롯 (오리지널) §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet ocelot:baby");

										p.setOp(false);
									} else {
										p.chat("/pet ocelot:baby");

									} break;
									
								case 6:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "pigzombie", true);
									sender.sendMessage("§6축하합니다! §c레어 랜덤 펫 박스§6에서 §e[ §6아기 돼지좀비§e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet pigzombie:baby");

										p.setOp(false);
									} else {
										p.chat("/pet pigzombie:baby");

									} break;
									
								case 7:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "mushroomcow", true);
									sender.sendMessage("§6축하합니다! §c레어 랜덤 펫 박스§6에서 §e[ §6아기 버섯소 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet mushroomcow:baby");

										p.setOp(false);
									} else {
										p.chat("/pet mushroomcow:baby");

									} break;
									
								case 8:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "ocelotB", true);
									sender.sendMessage("§6축하합니다! §c레어 랜덤 펫 박스§6에서 §e[ §6아기 오셀롯 (블랙) §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet ocelot:black:baby");

										p.setOp(false);
									} else {
										p.chat("/pet ocelot:black:baby");

									} break;
									
								case 9:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "ocelotS", true);
									sender.sendMessage("§6축하합니다! §c레어 랜덤 펫 박스§6에서 §e[ §6아기 오셀롯 (점박이) §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet ocelot:siamese:baby");

										p.setOp(false);
									} else {
										p.chat("/pet ocelot:siamese:baby");

									} break;
									
								case 10:
									Methods.setBooleanYml(p, "petsheep", false);
									Methods.setBooleanYml(p, "havepet", true);
									Methods.setBooleanYml(p, "villager", true);
									sender.sendMessage("§6축하합니다! §c레어 랜덤 펫 박스§6에서 §e[ §6아기 주민 §e] §6이 나왔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet villager:baby");

										p.setOp(false);
									} else {
										p.chat("/pet villager:baby");

									} break;
							}
							return false;
						}
					} else {
						sender.sendMessage("§c당신은 이 명령어를 실행할 권한이 없습니다.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("양털뽑기"))
				{
					if (sender.isOp()) {
						if (Methods.getBooleanYml(p, "petsheep") == false && Methods.getBooleanYml(p, "havepet") == false) {
							this.getServer().dispatchCommand(this.getServer().getConsoleSender(), "rpgitem 양털색변경권 give " + p.getName() + " 1");
							sender.sendMessage("§c당신은 펫이 없거나 펫이 양이 아닙니다.");
							return false;
						} else {
							Methods.setBooleanYml(p, "defaultpet", true);
							int RandomPet = new Random().nextInt(15) + 1;
							switch (RandomPet) {
								case 1:
									Methods.setBooleanYml(p, "sheepOrange", true);
									Methods.setBooleanYml(p, "sheep", false);
									sender.sendMessage("§6양털 색깔 §c주황색§6을 성공적으로 입혔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:orange");
										p.setOp(false);
									} else {
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:orange");
									} break;
									
								case 2:
									Methods.setBooleanYml(p, "sheepMagenta", true);
									Methods.setBooleanYml(p, "sheep", false);
									sender.sendMessage("§6양털 색깔 §c자홍색§6을 성공적으로 입혔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:magenta");
										p.setOp(false);
									} else {
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:magenta");
									} break;
									
								case 3:
									Methods.setBooleanYml(p, "sheepLightblue", true);
									Methods.setBooleanYml(p, "sheep", false);
									sender.sendMessage("§6양털 색깔 §c하늘색§6을 성공적으로 입혔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:lightblue");
										p.setOp(false);
									} else {
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:lightblue");
									} break;
									
								case 4:
									Methods.setBooleanYml(p, "sheepYellow", true);
									Methods.setBooleanYml(p, "sheep", false);
									sender.sendMessage("§6양털 색깔 §c노랑색§6을 성공적으로 입혔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:yellow");
										p.setOp(false);
									} else {
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:yellow");
									} break;
									
								case 5:
									Methods.setBooleanYml(p, "sheepLime", true);
									sender.sendMessage("§6양털 색깔 §c연두색§6을 성공적으로 입혔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:lime");
										p.setOp(false);
									} else {
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:lime");
									} break;
									
								case 6:
									Methods.setBooleanYml(p, "sheepPink", true);
									Methods.setBooleanYml(p, "sheep", false);
									sender.sendMessage("§6양털 색깔 §c분홍색§6을 성공적으로 입혔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:pink");
										p.setOp(false);
									} else {
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:pink");
									} break;
									
								case 7:
									Methods.setBooleanYml(p, "sheepGray", true);
									Methods.setBooleanYml(p, "sheep", false);
									sender.sendMessage("§6양털 색깔 §c회색§6을 성공적으로 입혔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:gray");
										p.setOp(false);
									} else {
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:gray");
									} break;
									
								case 8:
									Methods.setBooleanYml(p, "sheepSilver", true);
									Methods.setBooleanYml(p, "sheep", false);
									sender.sendMessage("§6양털 색깔 §c연한 회색§6을 성공적으로 입혔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:silver");
										p.setOp(false);
									} else {
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:silver");
									} break;
									
								case 9:
									Methods.setBooleanYml(p, "sheepCyan", true);
									Methods.setBooleanYml(p, "sheep", false);
									sender.sendMessage("§6양털 색깔 §c청색§6을 성공적으로 입혔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:cyan");
										p.setOp(false);
									} else {
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:cyan");
									} break;
									
								case 10:
									Methods.setBooleanYml(p, "sheepPurple", true);
									Methods.setBooleanYml(p, "sheep", false);
									sender.sendMessage("§6양털 색깔 §c보라색§6을 성공적으로 입혔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:purple");
										p.setOp(false);
									} else {
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:purple");
									} break;
									
								case 11:
									Methods.setBooleanYml(p, "sheepBlue", true);
									Methods.setBooleanYml(p, "sheep", false);
									sender.sendMessage("§6양털 색깔 §c파랑색§6을 성공적으로 입혔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:blue");
										p.setOp(false);
									} else {
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:blue");
									} break;
									
								case 12:
									Methods.setBooleanYml(p, "sheepBrown", true);
									Methods.setBooleanYml(p, "sheep", false);
									sender.sendMessage("§6양털 색깔 §c갈색§6을 성공적으로 입혔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:brown");
										p.setOp(false);
									} else {
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:brown");
									} break;
									
								case 13:
									Methods.setBooleanYml(p, "sheepGreen", true);
									Methods.setBooleanYml(p, "sheep", false);
									sender.sendMessage("§6양털 색깔 §c초록색§6을 성공적으로 입혔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:green");
										p.setOp(false);
									} else {
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:green");
									} break;
									
								case 14:
									Methods.setBooleanYml(p, "sheepRed", true);
									Methods.setBooleanYml(p, "sheep", false);
									sender.sendMessage("§6양털 색깔 §c빨강색§6을 성공적으로 입혔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:red");
										p.setOp(false);
									} else {
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:red");
									} break;
									
								case 15:
									Methods.setBooleanYml(p, "sheepBlack", true);
									Methods.setBooleanYml(p, "sheep", false);
									sender.sendMessage("§6양털 색깔 §c검정색§6을 성공적으로 입혔습니다.");
								    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
								    
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:black");
										p.setOp(false);
									} else {
										p.chat("/pet remove");
										p.chat("/pet sheep:baby:black");
									} break;
							}
							return false;
						}
					} else {
						sender.sendMessage("§c당신은 이 명령어를 실행할 권한이 없습니다.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("소환") && args.length == 1)
				{
					if (Methods.getBooleanYml(p, "havepet") == false) {
						sender.sendMessage("§c당신은 소환할 펫이 존재하지 않습니다.");
						return false;
					}
					
					if (Methods.getBooleanYml(p, "defaultpet")) {
						sender.sendMessage("§c당신의 펫은 이미 소환되어 있습니다.");
						return false;
					}
					
					if (Methods.getBooleanYml(p, "chicken")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet chicken:baby");
							p.setOp(false);
						} else {
							p.chat("/pet chicken:baby");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "cow")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet cow:baby");
							p.setOp(false);
						} else {
							p.chat("/pet cow:baby");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "creeper")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet creeper");
							p.setOp(false);
						} else {
							p.chat("/pet creeper");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "pig")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet pig:baby");
							p.setOp(false);
						} else {
							p.chat("/pet pig:baby");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "sheep")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet sheep:baby");
							p.setOp(false);
						} else {
							p.chat("/pet sheep:baby");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "skeleton")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet skeleton");
							p.setOp(false);
						} else {
							p.chat("/pet skeleton");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "slime")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet slime:small");
							p.setOp(false);
						} else {
							p.chat("/pet slime:small");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "wolf")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet wolf:baby");
							p.setOp(false);
						} else {
							p.chat("/pet wolf:baby");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "zombie")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet zombie:baby");
							p.setOp(false);
						} else {
							p.chat("/pet zombie:baby");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "bat")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet bat");
							p.setOp(false);
						} else {
							p.chat("/pet bat");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "blaze")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet blaze");
							p.setOp(false);
						} else {
							p.chat("/pet blaze");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "irongolem")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet irongolem");
							p.setOp(false);
						} else {
							p.chat("/pet irongolem");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "magmacube")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet magmacube:small");
							p.setOp(false);
						} else {
							p.chat("/pet magmacube:small");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "magmacube")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet magmacube:small");
							p.setOp(false);
						} else {
							p.chat("/pet magmacube:small");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "pigzombie")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet pigzombie:baby");
							p.setOp(false);
						} else {
							p.chat("/pet pigzombie:baby");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "mushroomcow")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet mushroomcow:baby");
							p.setOp(false);
						} else {
							p.chat("/pet mushroomcow:baby");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "ocelot")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet ocelot:baby");
							p.setOp(false);
						} else {
							p.chat("/pet ocelot:baby");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "ocelotB")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet ocelot:black:baby");
							p.setOp(false);
						} else {
							p.chat("/pet ocelot:black:baby");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "ocelotS")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet ocelot:siamese:baby");
							p.setOp(false);
						} else {
							p.chat("/pet ocelot:siamese:baby");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "villager")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet villager:baby");
							p.setOp(false);
						} else {
							p.chat("/pet villager:baby");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "sheepOrange")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet sheep:baby:orange");
							p.setOp(false);
						} else {
							p.chat("/pet sheep:baby:orange");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "sheepMagenta")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet sheep:baby:magenta");
							p.setOp(false);
						} else {
							p.chat("/pet sheep:baby:magenta");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "sheepLightblue")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet sheep:baby:lightblue");
							p.setOp(false);
						} else {
							p.chat("/pet sheep:baby:lightblue");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "sheepYellow")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet sheep:baby:yellow");
							p.setOp(false);
						} else {
							p.chat("/pet sheep:baby:yellow");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "sheepLime")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet sheep:baby:lime");
							p.setOp(false);
						} else {
							p.chat("/pet sheep:baby:lime");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "sheepPink")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet sheep:baby:pink");
							p.setOp(false);
						} else {
							p.chat("/pet sheep:baby:pink");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "sheepGray")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet sheep:baby:gray");
							p.setOp(false);
						} else {
							p.chat("/pet sheep:baby:gray");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "sheepSilver")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet sheep:baby:silver");
							p.setOp(false);
						} else {
							p.chat("/pet sheep:baby:silver");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "sheepCyan")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet sheep:baby:cyan");
							p.setOp(false);
						} else {
							p.chat("/pet sheep:baby:cyan");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "sheepPurple")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet sheep:baby:purple");
							p.setOp(false);
						} else {
							p.chat("/pet sheep:baby:purple");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "sheepBlue")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet sheep:baby:blue");
							p.setOp(false);
						} else {
							p.chat("/pet sheep:baby:blue");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "sheepBrown")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet sheep:baby:brown");
							p.setOp(false);
						} else {
							p.chat("/pet sheep:baby:brown");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "sheepGreen")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet sheep:baby:green");
							p.setOp(false);
						} else {
							p.chat("/pet sheep:baby:green");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "sheepRed")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet sheep:baby:red");
							p.setOp(false);
						} else {
							p.chat("/pet sheep:baby:red");
						}
						
						return false;
					}
					
					else if (Methods.getBooleanYml(p, "sheepBlack")) {
					    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
					    sender.sendMessage("§c펫 이름§6은 §e[ /펫이름 <이름> ] §6으로 변경할 수 있습니다.");
					    Methods.setBooleanYml(p, "defaultpet", true);
					    
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/pet sheep:baby:black");
							p.setOp(false);
						} else {
							p.chat("/pet sheep:baby:black");
						}
						
						return false;
					}
					
					return false;
				}
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
		sender.sendMessage("§6/펫 방생 §f- 자신의 펫을 자연으로 돌려 보냅니다.");
		sender.sendMessage("§6/펫 소환 §f- 펫을 소환시킵니다.");
		sender.sendMessage("§6/펫 해제 §f- 펫을 해제시킵니다.");
		sender.sendMessage("§6/펫이름 §f- 펫 이름을 변경합니다.");
		sender.sendMessage("§f");
		sender.sendMessage("§e* 펫 이름을 고정시키고 싶으시면 펫 이름을");
		sender.sendMessage("§e* 변경 후 재접속을 해주시면 됩니다.");
		sender.sendMessage("§c* 펫을 방생하면 다신 주인 곁으로 돌아오지");
		sender.sendMessage("§c* 않으므로 신중하게 해주시기 바랍니다.");
		
		if (sender.isOp()) {
			sender.sendMessage("§6/펫 일반뽑기 §f- 일반 펫을 뽑습니다.");
			sender.sendMessage("§6/펫 레어뽑기 §f- 레어 펫을 뽑습니다.");
			sender.sendMessage("§6/펫 양털뽑기 §f- 양털 색을 뽑습니다.");
		}
	}
}
