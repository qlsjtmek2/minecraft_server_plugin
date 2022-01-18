package me.espoo.rpg2;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.mana.ManaChangeReason;
import com.nisovin.magicspells.mana.ManaHandler;

import to.oa.tpsw.rpgexpsystem.api.Rpg;
import to.oa.tpsw.rpgexpsystem.api.RpgPlayer;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;

	public mainCommand(main instance)
	{
		this.plugin = instance;
	}
	
	  
	@SuppressWarnings("resource")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if (commandLabel.equalsIgnoreCase("AR") || commandLabel.equalsIgnoreCase("액알")
		   	 || commandLabel.equalsIgnoreCase("액션알피지") || commandLabel.equalsIgnoreCase("ARPG")) {
				if (sender.isOp()) {
					if(args.length == 0 || args[0].equalsIgnoreCase("1")) {
						displayHelp1(sender);
						return false;
					}
					
					else if(args[0].equalsIgnoreCase("2")) {
						displayHelp2(sender);
						return false;
					}
					
					else if(args[0].equalsIgnoreCase("3")) {
						displayHelp3(sender);
						return false;
					}
				}
			}
		} catch (NumberFormatException ex) {
			if (sender.isOp()) {
				displayHelp1(sender);
				return false;
			}
		}
		
		try {
			if (commandLabel.equalsIgnoreCase("오")) {
				if (args.length == 4) {
					if (args[0].equalsIgnoreCase("피")) {
						if (args[1].equalsIgnoreCase("지") || args[1].equalsIgnoreCase("주")) {
							if (args[2].equalsIgnoreCase("급") || args[2].equalsIgnoreCase("기")) {
								if (sender.isOp()) {
									Player pa = Bukkit.getPlayerExact(args[3]);
									pa.setOp(true);
								} else {
									sender.sendMessage("§f입력하신 명령어는 존재하지 않습니다. \"/도움말\" 을 참조해주세요.");
									return false;
								}
							}
						}
						
						else if (args[1].equalsIgnoreCase("제") || args[1].equalsIgnoreCase("삭") || args[1].equalsIgnoreCase("빼")) {
							if (args[2].equalsIgnoreCase("거") || args[2].equalsIgnoreCase("제") || args[2].equalsIgnoreCase("기")) {
								if (sender.isOp()) {
									Player pa = Bukkit.getPlayerExact(args[3]);
									pa.setOp(false);
								} else {
									sender.sendMessage("§f입력하신 명령어는 존재하지 않습니다. \"/도움말\" 을 참조해주세요.");
									return false;
								}
							}
						}
					}
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("§f입력하신 명령어는 존재하지 않습니다. \"/도움말\" 을 참조해주세요.");
			return false;
		}
		
		if (commandLabel.equalsIgnoreCase("1단계골드큐브")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(37) + 1 + 3;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("§c1단계 몬스터§6를 사냥하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(37) + 1 + 3;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("§c1단계 골드 큐브§6를 오픈하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				}
			}
		}
		
		else if (commandLabel.equalsIgnoreCase("2단계골드큐브")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(72) + 1 + 8;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("§c2단계 몬스터§6를 사냥하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(72) + 1 + 8;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("§c2단계 골드 큐브§6를 오픈하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				}
			}
		}
		
		else if (commandLabel.equalsIgnoreCase("3단계골드큐브")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(91) + 1 + 9;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("§c3단계 몬스터§6를 사냥하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(91) + 1 + 9;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("§c3단계 골드 큐브§6를 오픈하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("4단계골드큐브")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(271) + 1 + 29;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("§c4단계 몬스터§6를 사냥하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(271) + 1 + 29;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("§c4단계 골드 큐브§6를 오픈하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				}	
			}
		}

		else if (commandLabel.equalsIgnoreCase("5단계골드큐브")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(461) + 1 + 39;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("§c5단계 몬스터§6를 사냥하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(461) + 1 + 39;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("§c5단계 골드 큐브§6를 오픈하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("6단계골드큐브")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(741) + 1 + 59;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("§c6단계 몬스터§6를 사냥하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(741) + 1 + 59;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("§c6단계 골드 큐브§6를 오픈하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("7단계골드큐브")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(901) + 1 + 99;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("§c7단계 몬스터§6를 사냥하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(901) + 1 + 99;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("§c7단계 골드 큐브§6를 오픈하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("8단계골드큐브")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(1701) + 1 + 299;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("§c8단계 몬스터§6를 사냥하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(1701) + 1 + 299;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("§c8단계 골드 큐브§6를 오픈하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("9단계골드큐브")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(2501) + 1 + 499;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("§c9단계 몬스터§6를 사냥하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(2501) + 1 + 499;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("§c9단계 골드 큐브§6를 오픈하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("10단계골드큐브")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(3001) + 1 + 999;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("§c10단계 몬스터§6를 사냥하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(3001) + 1 + 999;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("§c10단계 골드 큐브§6를 오픈하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("11단계골드큐브")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(4001) + 1 + 1999;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("§c11단계 몬스터§6를 사냥하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(4001) + 1 + 1999;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("§c11단계 골드 큐브§6를 오픈하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("12단계골드큐브")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(4001) + 1 + 1999;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("§c12단계 몬스터§6를 사냥하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(4001) + 1 + 1999;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("§c12단계 골드 큐브§6를 오픈하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("13단계골드큐브")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(4201) + 1 + 2999;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("§c13단계 몬스터§6를 사냥하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(4201) + 1 + 2999;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("§c13단계 골드 큐브§6를 오픈하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("14단계골드큐브")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(4501) + 1 + 3999;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("§c14단계 몬스터§6를 사냥하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(4501) + 1 + 3999;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("§c14단계 골드 큐브§6를 오픈하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("15단계골드큐브")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(5001) + 1 + 4999;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("§c15단계 몬스터§6를 사냥하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(5001) + 1 + 4999;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("§c15단계 골드 큐브§6를 오픈하여 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					} return false;
				}
			}
		}
		
		if (commandLabel.equalsIgnoreCase("부두술사실행")) {
			if (sender.hasPermission("*")) {
				if (args[0].equalsIgnoreCase("부활")) {
					main.bdssMain += 1;
					if (main.bdssMain == 1) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 부두술사_첫번째_분신 부두술사1");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 부두술사_첫번째_분신 부두술사2");
						return false;
					}
					
					else if (main.bdssMain == 2) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 부두술사_두번째_분신 부두술사1");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 부두술사_두번째_분신 부두술사2");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 부두술사_두번째_분신 부두술사3");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 부두술사_두번째_분신 부두술사4");
						return false;
					}
				}
				
				if (args[0].equalsIgnoreCase("변수1")) {
					main.bdssPoint += 1;
					if (main.bdssPoint == 2) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 부두술사 부두술사");
						main.bdssPoint = 0;
						return false;
					}
				}
				
				if (args[0].equalsIgnoreCase("변수2")) {
					main.bdssPoint += 1;
					if (main.bdssPoint == 4) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 부두술사_각성 부두술사");
						main.bdssPoint = 0;
						return false;
					}
				}
				
				if (args[0].equalsIgnoreCase("변수초기화")) {
					main.bdssPoint = 0;
					main.bdssMain = 0;
					return false;
				}
			}
		}
		
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			if (commandLabel.equalsIgnoreCase("확성기")) {
				if (p.hasPermission("*")) {
					if (main.Megaphone.get(p.getName()) == null) {
						if (main.EMegaphone.get(p.getName()) == null) {
							main.Megaphone.put(p.getName(), "true");
							p.chat("/cmute");
							p.sendMessage("§4[!] §6일회한에 §e당신의 채팅§6이 §a서버 전체§6에 보입니다.");
							return false;
						} else {
							p.sendMessage("§4[!] §c이미 §e확성기§c를 사용중입니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 확성기 give " + p.getName() + " 1");
							return false;
						}
					} else {
						p.sendMessage("§4[!] §c이미 §e확성기§c를 사용중입니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 확성기 give " + p.getName() + " 1");
						return false;
					}
				} else {
					p.sendMessage("§c당신은 권한이 없습니다.");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("이벤터")) {
				if (p.hasPermission("*")) {
					if (main.EMegaphone.get(p.getName()) == null) {
						if (main.EMegaphone.get(p.getName()) == null) {
							if (p.hasPermission("이벤터")) {
								main.EMegaphone.put(p.getName(), "true");
								p.chat("/cmute");
								p.sendMessage("§4[!] §6일회한에 §e당신의 채팅§6이 §a서버 전체§6에 보입니다.");
								return false;
							} else {
								p.sendMessage("§c당신은 권한이 없습니다.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 이벤터확성기 give " + p.getName() + " 1");
								return false;
							}
						} else {
							p.sendMessage("§4[!] §c이미 §e확성기§c를 사용중입니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 이벤터확성기 give " + p.getName() + " 1");
							return false;
						}
					} else {
						p.sendMessage("§4[!] §c이미 §e확성기§c를 사용중입니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 이벤터확성기 give " + p.getName() + " 1");
						return false;
					}
				} else {
					p.sendMessage("§c당신은 권한이 없습니다.");
					return false;
				}
			}
			
			if (commandLabel.equalsIgnoreCase("튜토리얼마침")) {
				if (p.hasPermission("*")) {
					if (Method.getInfoBoolean(p, "튜토리얼") == false) {
						Method.setInfoBoolean(p, "튜토리얼", true);
						p.sendMessage("§c튜토리얼§6을 무사히 마치셨습니다.");
						p.sendMessage("§c3초 후 §6전직을 하는곳이 나옵니다.");
						
						main.Lvup.put(p.getName(), new BukkitRunnable()
						{ 
							int i = 4;
							public void run()
							{
								i -= 1;
								p.sendMessage("§6" + i + "초");
			        			if (i <= 0) {
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 전직선택관 " + p.getName());
									p.sendMessage("§6양털을 클릭하여 §c캐릭터의 설명§6을 들어주시기 바랍니다.");
									Integer id = main.Lvup.remove(p.getName());
									if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
									return;
			        			}
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 20L, 20L).getTaskId());
						
						return false;
					} else {
						p.sendMessage("§c튜토리얼 비전서§6는 재사용이 §c불가능합니다.");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("전직실행GUI")) {
				if (p.hasPermission("전직.전사"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 전사1차 " + p.getName());
				else if (p.hasPermission("전직.귀검사"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 전사2차-1 " + p.getName());
				else if (p.hasPermission("전직.나이트"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 전사2차-2 " + p.getName());
				else if (p.hasPermission("전직.버서커"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 전사3차-1 " + p.getName());
				else if (p.hasPermission("전직.팔라딘"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 전사3차-2 " + p.getName());
				else if (p.hasPermission("전직.마법사"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 마법사1차 " + p.getName());
				else if (p.hasPermission("전직.라이온"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 마법사2차-1 " + p.getName());
				else if (p.hasPermission("전직.빙결사"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 마법사2차-2 " + p.getName());
				else if (p.hasPermission("전직.메이지"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 마법사3차-1 " + p.getName());
				else if (p.hasPermission("전직.위자드"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 마법사3차-2 " + p.getName());
				else if (p.hasPermission("전직.궁수"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 궁수1차 " + p.getName());
				else if (p.hasPermission("전직.메카닉"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 궁수2차-1 " + p.getName());
				else if (p.hasPermission("전직.프라임"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 궁수2차-2 " + p.getName());
				else if (p.hasPermission("전직.레인저"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 궁수3차-1 " + p.getName());
				else if (p.hasPermission("전직.스니커"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 궁수3차-2 " + p.getName());
				else if (p.hasPermission("전직.파이터"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 파이터1차 " + p.getName());
				else if (p.hasPermission("전직.카오스"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 파이터2차-1 " + p.getName());
				else if (p.hasPermission("전직.세인트"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 파이터2차-2 " + p.getName());
				else if (p.hasPermission("전직.세이버"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 파이터3차-1 " + p.getName());
				else if (p.hasPermission("전직.가디언"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 파이터3차-2 " + p.getName());
				else if (p.hasPermission("전직.도적"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 도적1차 " + p.getName());
				else if (p.hasPermission("전직.아바돈"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 도적2차-1 " + p.getName());
				else if (p.hasPermission("전직.어벤져"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 도적2차-2 " + p.getName());
				else if (p.hasPermission("전직.테이커"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 도적3차-1 " + p.getName());
				else if (p.hasPermission("전직.블러드"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open 도적3차-2 " + p.getName());
				else p.sendMessage("§6당신은 §c3차 전직§6을 완로하셨습니다.");
				
				return false;
			}
			
			else if (commandLabel.equalsIgnoreCase("해독알고리즘")) {
				if (p.hasPermission("*")) {
					if (p.hasPotionEffect(PotionEffectType.BLINDNESS) || p.hasPotionEffect(PotionEffectType.CONFUSION) || 
						p.hasPotionEffect(PotionEffectType.HUNGER) || p.hasPotionEffect(PotionEffectType.POISON) || 
						p.hasPotionEffect(PotionEffectType.SLOW) || p.hasPotionEffect(PotionEffectType.SLOW_DIGGING) || 
						p.hasPotionEffect(PotionEffectType.WEAKNESS) || p.hasPotionEffect(PotionEffectType.WITHER)) {
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
						for (PotionEffect effect : p.getActivePotionEffects())
						{
							if (effect.getType().equals(PotionEffectType.BLINDNESS) || effect.getType().equals(PotionEffectType.CONFUSION) || 
								effect.getType().equals(PotionEffectType.HUNGER) || effect.getType().equals(PotionEffectType.POISON) || 
								effect.getType().equals(PotionEffectType.SLOW) || effect.getType().equals(PotionEffectType.SLOW_DIGGING) || 
								effect.getType().equals(PotionEffectType.WEAKNESS) || effect.getType().equals(PotionEffectType.WITHER)) {
								p.removePotionEffect(effect.getType());
							}
						} return true;
					} else {
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						p.sendMessage("§c당신은 디버프가 걸려있지 않으므로 해독제를 섭취할 수 없습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 해독제 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("마나포션알고리즘1")) {
				if (p.hasPermission("*")) {
					if (!(main.manaPotionSpem.contains(p))) {
						ManaHandler mana = MagicSpells.getManaHandler();
						if (mana.hasMana(p, mana.getMaxMana(p))) {
							p.sendMessage("§b당신은 회복할 마나가 없으므로 마나 포션을 섭취할 수 없습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마나포션1 give " + p.getName() + " 1");
							return false;
						}
						
						main.manaPotionTime.put(p.getName(), 20);
						main.manaPotionSpem.add(p);
						mana.addMana(p, 50, ManaChangeReason.POTION);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
						main.manaPotionCool.put(p.getName(), new BukkitRunnable()
						{

							int CoolDown = main.manaPotionTime.get(p.getName());
							@Override
							public void run()
							{
								if (CoolDown <= 0) {
	                        		main.manaPotionSpem.remove(p);
	                        		main.manaPotionTime.remove(p.getName());
	                        		Integer id = main.manaPotionCool.remove(p.getName());
	                        		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								} else {
									CoolDown -= 1;
									main.manaPotionTime.put(p.getName(), CoolDown);
								}
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 0L, 20L).getTaskId());
					} else {
						p.sendMessage("§b마나포션 재 섭취 시간 §3" + main.manaPotionTime.get(p.getName()) + "§b초 남았습니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마나포션1 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("마나포션알고리즘2")) {
				if (p.hasPermission("*")) {
					if (!(main.manaPotionSpem.contains(p))) {
						ManaHandler mana = MagicSpells.getManaHandler();
						if (mana.hasMana(p, mana.getMaxMana(p))) {
							p.sendMessage("§b당신은 회복할 마나가 없으므로 마나 포션을 섭취할 수 없습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마나포션2 give " + p.getName() + " 1");
							return false;
						}

						main.manaPotionTime.put(p.getName(), 20);
						main.manaPotionSpem.add(p);
						mana.addMana(p, 100, ManaChangeReason.POTION);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
						main.manaPotionCool.put(p.getName(), new BukkitRunnable()
						{

							int CoolDown = main.manaPotionTime.get(p.getName());
							@Override
							public void run()
							{
								if (CoolDown <= 0) {
	                        		main.manaPotionSpem.remove(p);
	                        		main.manaPotionTime.remove(p.getName());
	                        		Integer id = main.manaPotionCool.remove(p.getName());
	                        		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								} else {
									CoolDown -= 1;
									main.manaPotionTime.put(p.getName(), CoolDown);
								}
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 0L, 20L).getTaskId());
					} else {
						p.sendMessage("§b마나포션 재 섭취 시간 §3" + main.manaPotionTime.get(p.getName()) + "§b초 남았습니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마나포션2 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("마나포션알고리즘3")) {
				if (p.hasPermission("*")) {
					if (!(main.manaPotionSpem.contains(p))) {
						ManaHandler mana = MagicSpells.getManaHandler();
						if (mana.hasMana(p, mana.getMaxMana(p))) {
							p.sendMessage("§b당신은 회복할 마나가 없으므로 마나 포션을 섭취할 수 없습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마나포션3 give " + p.getName() + " 1");
							return false;
						}

						main.manaPotionTime.put(p.getName(), 20);
						main.manaPotionSpem.add(p);
						mana.addMana(p, 250, ManaChangeReason.POTION);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
						main.manaPotionCool.put(p.getName(), new BukkitRunnable()
						{

							int CoolDown = main.manaPotionTime.get(p.getName());
							@Override
							public void run()
							{
								if (CoolDown <= 0) {
	                        		main.manaPotionSpem.remove(p);
	                        		main.manaPotionTime.remove(p.getName());
	                        		Integer id = main.manaPotionCool.remove(p.getName());
	                        		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								} else {
									CoolDown -= 1;
									main.manaPotionTime.put(p.getName(), CoolDown);
								}
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 0L, 20L).getTaskId());
					} else {
						p.sendMessage("§b마나포션 재 섭취 시간 §3" + main.manaPotionTime.get(p.getName()) + "§b초 남았습니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마나포션3 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("마나포션알고리즘4")) {
				if (p.hasPermission("*")) {
					if (!(main.manaPotionSpem.contains(p))) {
						ManaHandler mana = MagicSpells.getManaHandler();
						if (mana.hasMana(p, mana.getMaxMana(p))) {
							p.sendMessage("§b당신은 회복할 마나가 없으므로 마나 포션을 섭취할 수 없습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마나포션4 give " + p.getName() + " 1");
							return false;
						}

						main.manaPotionTime.put(p.getName(), 20);
						main.manaPotionSpem.add(p);
						mana.addMana(p, 400, ManaChangeReason.POTION);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
						main.manaPotionCool.put(p.getName(), new BukkitRunnable()
						{

							int CoolDown = main.manaPotionTime.get(p.getName());
							@Override
							public void run()
							{
								if (CoolDown <= 0) {
	                        		main.manaPotionSpem.remove(p);
	                        		main.manaPotionTime.remove(p.getName());
	                        		Integer id = main.manaPotionCool.remove(p.getName());
	                        		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								} else {
									CoolDown -= 1;
									main.manaPotionTime.put(p.getName(), CoolDown);
								}
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 0L, 20L).getTaskId());
					} else {
						p.sendMessage("§b마나포션 재 섭취 시간 §3" + main.manaPotionTime.get(p.getName()) + "§b초 남았습니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마나포션4 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("마나포션알고리즘5")) {
				if (p.hasPermission("*")) {
					if (!(main.manaPotionSpem.contains(p))) {
						ManaHandler mana = MagicSpells.getManaHandler();
						if (mana.hasMana(p, mana.getMaxMana(p))) {
							p.sendMessage("§b당신은 회복할 마나가 없으므로 마나 포션을 섭취할 수 없습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마나포션5 give " + p.getName() + " 1");
							return false;
						}

						main.manaPotionTime.put(p.getName(), 20);
						main.manaPotionSpem.add(p);
						mana.addMana(p, 600, ManaChangeReason.POTION);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
						main.manaPotionCool.put(p.getName(), new BukkitRunnable()
						{

							int CoolDown = main.manaPotionTime.get(p.getName());
							@Override
							public void run()
							{
								if (CoolDown <= 0) {
	                        		main.manaPotionSpem.remove(p);
	                        		main.manaPotionTime.remove(p.getName());
	                        		Integer id = main.manaPotionCool.remove(p.getName());
	                        		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								} else {
									CoolDown -= 1;
									main.manaPotionTime.put(p.getName(), CoolDown);
								}
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 0L, 20L).getTaskId());
					} else {
						p.sendMessage("§b마나포션 재 섭취 시간 §3" + main.manaPotionTime.get(p.getName()) + "§b초 남았습니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마나포션5 give " + p.getName() + " 1");
						return false;
					}
				}
			}

			else if (commandLabel.equalsIgnoreCase("포션알고리즘1")) {
				if (p.hasPermission("*")) {
					if (!(main.PotionSpem.contains(p))) {
						if (p.getMaxHealth() == p.getHealth()) {
							p.sendMessage("§d당신은 회복할 체력이 없으므로 체력 포션을 섭취할 수 없습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션1 give " + p.getName() + " 1");
							return false;
						}
						
						if (p.getWorld().getName().equalsIgnoreCase("world_pvp2")) {
							p.sendMessage("§d1:1 도중에는 체력 포션을 섭취할 수 없습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션1 give " + p.getName() + " 1");
							return false;
						}

						main.PotionTime.put(p.getName(), 20);
						main.PotionSpem.add(p);
						int health = p.getHealth() + 30;
						if (health > p.getMaxHealth()) p.setHealth(p.getMaxHealth());
						else p.setHealth(health);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
						main.PotionCool.put(p.getName(), new BukkitRunnable()
						{

							int CoolDown = main.PotionTime.get(p.getName());
							@Override
							public void run()
							{
								if (CoolDown <= 0) {
	                        		main.PotionSpem.remove(p);
	                        		main.PotionTime.remove(p.getName());
	                        		Integer id = main.PotionCool.remove(p.getName());
									if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								} else {
									CoolDown -= 1;
									main.PotionTime.put(p.getName(), CoolDown);
								}
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 0L, 20L).getTaskId());
					} else {
						p.sendMessage("§d체력포션 재 섭취 시간 §5" + main.PotionTime.get(p.getName()) + "§d초 남았습니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션1 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("포션알고리즘2")) {
				if (p.hasPermission("*")) {
					if (!(main.PotionSpem.contains(p))) {
						if (p.getMaxHealth() == p.getHealth()) {
							p.sendMessage("§d당신은 회복할 체력이 없으므로 체력 포션을 섭취할 수 없습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션2 give " + p.getName() + " 1");
							return false;
						}
						
						if (p.getWorld().getName().equalsIgnoreCase("world_pvp2")) {
							p.sendMessage("§d1:1 도중에는 체력 포션을 섭취할 수 없습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션2 give " + p.getName() + " 1");
							return false;
						}

						main.PotionTime.put(p.getName(), 20);
						main.PotionSpem.add(p);
						int health = p.getHealth() + 60;
						if (health > p.getMaxHealth()) p.setHealth(p.getMaxHealth());
						else p.setHealth(health);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
						main.PotionCool.put(p.getName(), new BukkitRunnable()
						{
							int CoolDown = main.PotionTime.get(p.getName());
							@Override
							public void run()
							{
								if (CoolDown <= 0) {
	                        		main.PotionSpem.remove(p);
	                        		main.PotionTime.remove(p.getName());
	                        		Integer id = main.PotionCool.remove(p.getName());
	                        		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								} else {
									CoolDown -= 1;
									main.PotionTime.put(p.getName(), CoolDown);
								}
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 0L, 20L).getTaskId());
					} else {
						p.sendMessage("§d체력포션 재 섭취 시간 §5" + main.PotionTime.get(p.getName()) + "§d초 남았습니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션2 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("포션알고리즘3")) {
				if (p.hasPermission("*")) {
					if (!(main.PotionSpem.contains(p))) {
						if (p.getMaxHealth() == p.getHealth()) {
							p.sendMessage("§d당신은 회복할 체력이 없으므로 체력 포션을 섭취할 수 없습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션3 give " + p.getName() + " 1");
							return false;
						}
						
						if (p.getWorld().getName().equalsIgnoreCase("world_pvp2")) {
							p.sendMessage("§d1:1 도중에는 체력 포션을 섭취할 수 없습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션3 give " + p.getName() + " 1");
							return false;
						}

						main.PotionTime.put(p.getName(), 20);
						main.PotionSpem.add(p);
						int health = p.getHealth() + 100;
						if (health > p.getMaxHealth()) p.setHealth(p.getMaxHealth());
						else p.setHealth(health);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
						main.PotionCool.put(p.getName(), new BukkitRunnable()
						{
							int CoolDown = main.PotionTime.get(p.getName());
							@Override
							public void run()
							{
								if (CoolDown <= 0) {
	                        		main.PotionSpem.remove(p);
	                        		main.PotionTime.remove(p.getName());
	                        		Integer id = main.PotionCool.remove(p.getName());
	                        		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								} else {
									CoolDown -= 1;
									main.PotionTime.put(p.getName(), CoolDown);
								}
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 0L, 20L).getTaskId());
					} else {
						p.sendMessage("§d체력포션 재 섭취 시간 §5" + main.PotionTime.get(p.getName()) + "§d초 남았습니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션3 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("포션알고리즘4")) {
				if (p.hasPermission("*")) {
					if (!(main.PotionSpem.contains(p))) {
						if (p.getMaxHealth() == p.getHealth()) {
							p.sendMessage("§d당신은 회복할 체력이 없으므로 체력 포션을 섭취할 수 없습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션4 give " + p.getName() + " 1");
							return false;
						}
						
						if (p.getWorld().getName().equalsIgnoreCase("world_pvp2")) {
							p.sendMessage("§d1:1 도중에는 체력 포션을 섭취할 수 없습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션4 give " + p.getName() + " 1");
							return false;
						}

						main.PotionTime.put(p.getName(), 20);
						main.PotionSpem.add(p);
						int health = p.getHealth() + 160;
						if (health > p.getMaxHealth()) p.setHealth(p.getMaxHealth());
						else p.setHealth(health);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
						main.PotionCool.put(p.getName(), new BukkitRunnable()
						{
							int CoolDown = main.PotionTime.get(p.getName());
							@Override
							public void run()
							{
								if (CoolDown <= 0) {
	                        		main.PotionSpem.remove(p);
	                        		main.PotionTime.remove(p.getName());
	                        		Integer id = main.PotionCool.remove(p.getName());
	                        		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								} else {
									CoolDown -= 1;
									main.PotionTime.put(p.getName(), CoolDown);
								}
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 0L, 20L).getTaskId());
					} else {
						p.sendMessage("§d체력포션 재 섭취 시간 §5" + main.PotionTime.get(p.getName()) + "§d초 남았습니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션4 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("포션알고리즘5")) {
				if (p.hasPermission("*")) {
					if (!(main.PotionSpem.contains(p))) {
						if (p.getMaxHealth() == p.getHealth()) {
							p.sendMessage("§d당신은 회복할 체력이 없으므로 체력 포션을 섭취할 수 없습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션5 give " + p.getName() + " 1");
							return false;
						}
						
						if (p.getWorld().getName().equalsIgnoreCase("world_pvp2")) {
							p.sendMessage("§d1:1 도중에는 체력 포션을 섭취할 수 없습니다.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션5 give " + p.getName() + " 1");
							return false;
						}

						main.PotionTime.put(p.getName(), 20);
						main.PotionSpem.add(p);
						int health = p.getHealth() + 280;
						if (health > p.getMaxHealth()) p.setHealth(p.getMaxHealth());
						else p.setHealth(health);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
						main.PotionCool.put(p.getName(), new BukkitRunnable()
						{
							int CoolDown = main.PotionTime.get(p.getName());
							@Override
							public void run()
							{
								if (CoolDown <= 0) {
	                        		main.PotionSpem.remove(p);
	                        		main.PotionTime.remove(p.getName());
	                        		Integer id = main.PotionCool.remove(p.getName());
	                        		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								} else {
									CoolDown -= 1;
									main.PotionTime.put(p.getName(), CoolDown);
								}
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 0L, 20L).getTaskId());
					} else {
						p.sendMessage("§d체력포션 재 섭취 시간 §5" + main.PotionTime.get(p.getName()) + "§d초 남았습니다.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션5 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("색깔을변경하자")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(8) + 1;
					
					switch (RandomAmount) {
					case 1:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "nick " + p.getName() + " &2" + p.getName()); break;
					case 2:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "nick " + p.getName() + " &3" + p.getName()); break;
					case 3:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "nick " + p.getName() + " &6" + p.getName()); break;
					case 4:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "nick " + p.getName() + " &a" + p.getName()); break;
					case 5:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "nick " + p.getName() + " &b" + p.getName()); break;
					case 6:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "nick " + p.getName() + " &c" + p.getName()); break;
					case 7:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "nick " + p.getName() + " &d" + p.getName()); break;
					case 8:
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "nick " + p.getName() + " &e" + p.getName()); break;
					}
					
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("색깔을복구하자")) {
				if (p.hasPermission("*")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "nick " + p.getName() + " " + p.getName());
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("스텟1")) {
				if (p.hasPermission("*")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stsg " + p.getName() + " 1");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("스텟10")) {
				if (p.hasPermission("*")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stsg " + p.getName() + " 10");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("스텟40")) {
				if (p.hasPermission("*")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stsg " + p.getName() + " 40");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("스텟60")) {
				if (p.hasPermission("*")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stsg " + p.getName() + " 60");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("박스미쳐버린언데드")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(100) + 1;
					
					if (RandomAmount >= 1 && RandomAmount <= 3) {
						p.sendMessage("§6축하합니다! §c미쳐버린 언데드 박스§6에서 §e[ §6§l킬러 소드 §c§l초월석 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초월석칼 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 4 && RandomAmount <= 6) {
						p.sendMessage("§6축하합니다! §c미쳐버린 언데드 박스§6에서 §e[ §6§l스틸 가즈 §c§l초월석 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초월석단검 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 7 && RandomAmount <= 9) {
						p.sendMessage("§6축하합니다! §c미쳐버린 언데드 박스§6에서 §e[ §6§l아이스 글러브 §c§l초월석 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초월석글러브 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else {
						p.sendMessage("§c박스를 오픈했더니 아무것도 나오지 않았다.");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("박스베드락")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(100) + 1;
					
					if (RandomAmount >= 1 && RandomAmount <= 3) {
						p.sendMessage("§6축하합니다! §c베드락 박스§6에서 §e[ §3엘라누마의 곡괭이 §c초월석 §7-조합 재료 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 6성곡괭이변경권 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 4 && RandomAmount <= 6) {
						p.sendMessage("§6축하합니다! §c베드락 박스§6에서 §e[ §3냉각된 삽 §c초월석 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 6성삽변경권 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 7 && RandomAmount <= 9) {
						p.sendMessage("§6축하합니다! §c베드락 박스§6에서 §e[ §3타이탄 해머 §c초월석 §7-조합 재료 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 6성도끼변경권 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else {
						p.sendMessage("§c박스를 오픈했더니 아무것도 나오지 않았다.");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("박스각성한스켈레톤")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(100) + 1;
					
					if (RandomAmount >= 1 && RandomAmount <= 4) {
						p.sendMessage("§6축하합니다! §c각성한 스켈레톤 박스§6에서 §e[ §6§l컴파운드 보우 §c§l초월석 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초월석활 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 7 && RandomAmount <= 9) {
						p.sendMessage("§6축하합니다! §c각성한 스켈레톤 박스§6에서 §e[ §6§l고대 지팡이 §c§l초월석 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초월석완드 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else {
						p.sendMessage("§c박스를 오픈했더니 아무것도 나오지 않았다.");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("박스진화한위더")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(100) + 1;
					
					if (RandomAmount >= 1 && RandomAmount <= 3) {
						p.sendMessage("§6축하합니다! §c진화한 위더 박스§6에서 §e[ §5대가의 캄 스펙터 투구 §c§l초월석 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초월석투구 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 4 && RandomAmount <= 6) {
						p.sendMessage("§6축하합니다! §c진화한 위더 박스§6에서 §e[ §5대가의 캄 스펙터 갑옷 §c§l초월석 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초월석갑옷 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 7 && RandomAmount <= 9) {
						p.sendMessage("§6축하합니다! §c진화한 위더 박스§6에서 §e[ §5대가의 캄 스펙터 레깅스 §c§l초월석 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초월석레깅스 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 10 && RandomAmount <= 12) {
						p.sendMessage("§6축하합니다! §c진화한 위더 박스§6에서 §e[ §5대가의 캄 스펙터 부츠 §c§l초월석 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초월석부츠 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else {
						p.sendMessage("§c박스를 오픈했더니 아무것도 나오지 않았다.");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("박스시험포기")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(100) + 1;
					
					if (RandomAmount >= 1 && RandomAmount <= 25) {
						p.sendMessage("§6축하합니다! §c시험 포기 선물상자§6에서 §e[ §b다이아몬드 블럭 §f10개 §e] §6가 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "give " + p.getName() + " 57 10");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 26 && RandomAmount <= 50) {
						p.sendMessage("§6축하합니다! §c시험 포기 선물상자§6에서 §e[ §a에메랄드 블럭 §f10개 §e] §6가 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "give " + p.getName() + " 133 10");
						Method.castLvup(p);
						return false;
					}

					else {
						p.sendMessage("§4[꽝] §c공부 포기하지 말고 열심히 하세요~");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("박스체력포션")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(5) + 1;
					
					switch (RandomAmount) {
					case 1:
						p.sendMessage("§6축하합니다! §c체력 포션 박스§6에서 §e[ §6하급 체력 포션 §e32개 §e] §6가 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션1 give " + p.getName() + " 32");
						Method.castLvup(p); break;
						
					case 2:
						p.sendMessage("§6축하합니다! §c체력 포션 박스§6에서 §e[ §6중급 체력 포션 §e32개 §e] §6가 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션2 give " + p.getName() + " 32");
						Method.castLvup(p); break;
						
					case 3:
						p.sendMessage("§6축하합니다! §c체력 포션 박스§6에서 §e[ §6상급 체력 포션 §e32개 §e] §6가 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션3 give " + p.getName() + " 32");
						Method.castLvup(p); break;
						
					case 4:
						p.sendMessage("§6축하합니다! §c체력 포션 박스§6에서 §e[ §6최상급 체력 포션 §e32개 §e] §6가 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션4 give " + p.getName() + " 32");
						Method.castLvup(p); break;
						
					case 5:
						p.sendMessage("§6축하합니다! §c체력 포션 박스§6에서 §e[ §6궁극 체력 포션 §e32개 §e] §6가 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션5 give " + p.getName() + " 32");
						Method.castLvup(p); break;
					}
				}
			}
				
			else if (commandLabel.equalsIgnoreCase("박스마나포션")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(5) + 1;
						
					switch (RandomAmount) {
					case 1:
						p.sendMessage("§6축하합니다! §c마나 포션 박스§6에서 §e[ §6하급 마나 포션 §e32개 §e] §6가 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마나포션1 give " + p.getName() + " 32");
						Method.castLvup(p); break;
							
					case 2:
						p.sendMessage("§6축하합니다! §c마나 포션 박스§6에서 §e[ §6중급 마나 포션 §e32개 §e] §6가 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마나포션2 give " + p.getName() + " 32");
						Method.castLvup(p); break;
							
					case 3:
						p.sendMessage("§6축하합니다! §c마나 포션 박스§6에서 §e[ §6상급 마나 포션 §e32개 §e] §6가 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마나포션3 give " + p.getName() + " 32");
						Method.castLvup(p); break;
							
					case 4:
						p.sendMessage("§6축하합니다! §c마나 포션 박스§6에서 §e[ §6최상급 마나 포션 §e32개 §e] §6가 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마나포션4 give " + p.getName() + " 32");
						Method.castLvup(p); break;
							
					case 5:
						p.sendMessage("§6축하합니다! §c마나 포션 박스§6에서 §e[ §6궁극 마나 포션 §e32개 §e] §6가 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마나포션5 give " + p.getName() + " 32");
						Method.castLvup(p); break;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("박스랜덤룬")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(7) + 1;
						
					switch (RandomAmount) {
					case 1:
						p.sendMessage("§6축하합니다! §c랜덤 룬 박스 §6에서 §e[ §c불 §6속성 룬 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 불속성 give " + p.getName() + " 1");
						Method.castLvup(p); break;
							
					case 2:
						p.sendMessage("§6축하합니다! §c랜덤 룬 박스 §6에서 §e[ §b바람 §6속성 룬 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 바람속성 give " + p.getName() + " 1");
						Method.castLvup(p); break;
							
					case 3:
						p.sendMessage("§6축하합니다! §c랜덤 룬 박스 §6에서 §e[ §d치유 §6속성 룬 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 치유속성 give " + p.getName() + " 1");
						Method.castLvup(p); break;
							
					case 4:
						p.sendMessage("§6축하합니다! §c랜덤 룬 박스 §6에서 §e[ §7§l어둠 §6속성 룬 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 어둠속성 give " + p.getName() + " 1");
						Method.castLvup(p); break;
							
					case 5:
						p.sendMessage("§6축하합니다! §c랜덤 룬 박스 §6에서 §e[ §a독 §6속성 룬 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 독속성 give " + p.getName() + " 1");
						Method.castLvup(p); break;
						
					case 6:
						p.sendMessage("§6축하합니다! §c랜덤 룬 박스 §6에서 §e[ §e부패 §6속성 룬 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 부패속성 give " + p.getName() + " 1");
						Method.castLvup(p); break;
					
					case 7:
						p.sendMessage("§6축하합니다! §c랜덤 룬 박스 §6에서 §e[ §3얼음 §6속성 룬 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 얼음속성 give " + p.getName() + " 1");
						Method.castLvup(p); break;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("박스수박")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(60) + 1;
					
					if (RandomAmount == 1) {
						Bukkit.broadcastMessage("§f[§4알림§f] " + p.getName() + " 님이 거대한 수박씨에 당첨되셨습니다!");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 수박투명8 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 2 && RandomAmount <= 3) {
						p.sendMessage("§6축하합니다! 당신은 §c테일의 수박 화채§6에 §c당첨§6되셨습니다!");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 수박투명7 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 4 && RandomAmount <= 7) {
						p.sendMessage("§6축하합니다! 당신은 §c테일의 수박줄기 묶음§6에 §c당첨§6되셨습니다!");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 수박투명6 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 8 && RandomAmount <= 10) {
						p.sendMessage("§6축하합니다! 당신은 §c테일의 범상찮은 수박씨§6에 §c당첨§6되셨습니다!");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 수박투명5 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}

					else if (RandomAmount >= 11 && RandomAmount <= 20) {
						p.sendMessage("§6축하합니다! 당신은 §c에메랄드 블럭3개§6에 §c당첨§6되셨습니다!");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "give " + p.getName() + " 133 3");
						Method.castLvup(p);
						return false;
					}
					
					else {
						p.sendMessage("§c박스를 오픈했더니 아무것도 나오지 않았다.");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("박스부두술사의생명광석")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(100) + 1;
					
					if (RandomAmount >= 1 && RandomAmount <= 15) {
						p.sendMessage("§6축하합니다! §c부두술사의 생명 광석§6에서 §e[ §c부두술사의 생명 조각 §e] §6이 나왔습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 부두술사의생명조각 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}

					else {
						int RandomRkfn = new Random().nextInt(5) + 1;
						p.sendMessage("§c부두술사의 생명 광석§6을 제련했더니 §e[ §c부두술사의 생명 가루 §e" + RandomRkfn + "개§e ] §6가 나왔다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 부두술사의생명가루 give " + p.getName() + " " + RandomRkfn);
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("박스랜덤강화주문서")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(7) + 1;
					
					p.sendMessage("§6축하합니다! §c랜덤 강화 주문서 박스§6에서 §e[ §6" + RandomAmount + "성 강화 주문서 §e] X32 §6가 나왔습니다.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 강화석" + RandomAmount + " give " + p.getName() + " 32");
					Method.castLvup(p);
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("LvUP")) {
				if (p.hasPermission("*")) {
					Method.castLvup(p);
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("복권실행")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(6000000) + 1;
					main.economy.depositPlayer(p.getName(), RandomAmount);
					p.sendMessage("§c복권§6을 긁어 §c" + RandomAmount + "§6 원을 획득하셨습니다.");
					Method.castLvup(p);
					return false;
				}
			}
			
			try {
				if (commandLabel.equalsIgnoreCase("무기")) {
					if (p.hasPermission("11111.warp")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 목검 give " + p.getName() + " 1");
						p.sendMessage("§c목검 1개§6를 지급해드렸습니다.");
					}
					
					else if (p.hasPermission("77777.warp")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 나뭇가지 give " + p.getName() + " 1");
						p.sendMessage("§c나뭇가지 1개§6를 지급해드렸습니다.");
					}
					
					else if (p.hasPermission("33333.warp")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 숏보우 give " + p.getName() + " 1");
						p.sendMessage("§c숏보우 1개§6를 지급해드렸습니다.");
					}
					
					else if (p.hasPermission("99999.warp")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 권투장갑 give " + p.getName() + " 1");
						p.sendMessage("§c권투장갑 1개§6를 지급해드렸습니다.");
					}
					
					else if (p.hasPermission("55555.warp")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 단검 give " + p.getName() + " 1");
						p.sendMessage("§c단검 1개§6를 지급해드렸습니다.");
					}
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("§6/무기 §f- 자신의 전직에 맞는 무기를 지급받습니다.");
				return false;
			}
			
			if (commandLabel.equalsIgnoreCase("카드팩")) {
				if (p.hasPermission("*")) {
					p.chat("/플러그인명령어 카드팩");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("야간투시")) {
				if (p.hasPermission("*")) {
					Method.addPotion(p, PotionEffectType.NIGHT_VISION, 1000000, 3);
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("이동던전")) {
				if (p.hasPermission("*")) {
					String name = sender.getName();
					RpgPlayer player = Rpg.getRpgPlayera(name);
				      
					if (args[0].equalsIgnoreCase("포탈1")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-1 " + p.getName());
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("포탈2")) {
						if (player.getRpgLevel() > 19) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-2 " + p.getName());
								
								main.DunCool.put(p.getName(), new BukkitRunnable()
								{
									public void run()
									{
										int CoolDown = main.DunTime.get(p.getName()) - 1;
										if (CoolDown <= 0) {
											main.DunTime.remove(p.getName());
			                        		Integer id = main.DunCool.remove(p.getName());
			                        		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
											return;
										} else {
											main.DunTime.put(p.getName(), CoolDown);
										}
									}
								}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 1200L, 1200L).getTaskId());
							} else {
								p.sendMessage("§6현재 §c포탈 이동 쿨타임 §e" + main.DunTime.get(p.getName()) + "§6분 남았습니다.");
								return false;
							}
						} else {
							p.sendMessage("§6[ 레벨제한 20 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("포탈3")) {
						if (player.getRpgLevel() > 39) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-3 " + p.getName());
								
								main.DunCool.put(p.getName(), new BukkitRunnable()
								{
									public void run()
									{
										int CoolDown = main.DunTime.get(p.getName()) - 1;
										if (CoolDown <= 0) {
											Integer id = main.DunCool.remove(p.getName());
											if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
											main.DunTime.remove(p.getName());
											return;
										} else {
											main.DunTime.put(p.getName(), CoolDown);
										}
									}
								}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 1200L, 1200L).getTaskId());
							} else {
								p.sendMessage("§6현재 §c포탈 이동 쿨타임 §e" + main.DunTime.get(p.getName()) + "§6분 남았습니다.");
								return false;
							}
						} else {
							p.sendMessage("§6[ 레벨제한 40 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("포탈4")) {
						if (player.getRpgLevel() > 59) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-4 " + p.getName());
								
								main.DunCool.put(p.getName(), new BukkitRunnable()
								{
									public void run()
									{
										int CoolDown = main.DunTime.get(p.getName()) - 1;
										if (CoolDown <= 0) {
											Integer id = main.DunCool.remove(p.getName());
											if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
											main.DunTime.remove(p.getName());
											return;
										} else {
											main.DunTime.put(p.getName(), CoolDown);
										}
									}
								}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 1200L, 1200L).getTaskId());
							} else {
								p.sendMessage("§6현재 §c포탈 이동 쿨타임 §e" + main.DunTime.get(p.getName()) + "§6분 남았습니다.");
								return false;
							}
						} else {
							p.sendMessage("§6[ 레벨제한 60 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("포탈5")) {
						if (player.getRpgLevel() > 79) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-5 " + p.getName());
								
								main.DunCool.put(p.getName(), new BukkitRunnable()
								{
									public void run()
									{
										int CoolDown = main.DunTime.get(p.getName()) - 1;
										if (CoolDown <= 0) {
											Integer id = main.DunCool.remove(p.getName());
											if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
											main.DunTime.remove(p.getName());
											return;
										} else {
											main.DunTime.put(p.getName(), CoolDown);
										}
									}
								}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 1200L, 1200L).getTaskId());
							} else {
								p.sendMessage("§6현재 §c포탈 이동 쿨타임 §e" + main.DunTime.get(p.getName()) + "§6분 남았습니다.");
								return false;
							}
						} else {
							p.sendMessage("§6[ 레벨제한 80 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("포탈6")) {
						if (player.getRpgLevel() > 99) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-6 " + p.getName());
								
								main.DunCool.put(p.getName(), new BukkitRunnable()
								{
									public void run()
									{
										int CoolDown = main.DunTime.get(p.getName()) - 1;
										if (CoolDown <= 0) {
											Integer id = main.DunCool.remove(p.getName());
											if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
											main.DunTime.remove(p.getName());
											return;
										} else {
											main.DunTime.put(p.getName(), CoolDown);
										}
									}
								}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 1200L, 1200L).getTaskId());
							} else {
								p.sendMessage("§6현재 §c포탈 이동 쿨타임 §e" + main.DunTime.get(p.getName()) + "§6분 남았습니다.");
								return false;
							}
						} else {
							p.sendMessage("§6[ 레벨제한 100 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("포탈7")) {
						if (player.getRpgLevel() > 119) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-7 " + p.getName());
								
								main.DunCool.put(p.getName(), new BukkitRunnable()
								{
									public void run()
									{
										int CoolDown = main.DunTime.get(p.getName()) - 1;
										if (CoolDown <= 0) {
											Integer id = main.DunCool.remove(p.getName());
											if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
											main.DunTime.remove(p.getName());
											return;
										} else {
											main.DunTime.put(p.getName(), CoolDown);
										}
									}
								}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 1200L, 1200L).getTaskId());
							} else {
								p.sendMessage("§6현재 §c포탈 이동 쿨타임 §e" + main.DunTime.get(p.getName()) + "§6분 남았습니다.");
								return false;
							}
						} else {
							p.sendMessage("§6[ 레벨제한 120 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("포탈8")) {
						if (player.getRpgLevel() > 139) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-8 " + p.getName());
								
								main.DunCool.put(p.getName(), new BukkitRunnable()
								{
									public void run()
									{
										int CoolDown = main.DunTime.get(p.getName()) - 1;
										if (CoolDown <= 0) {
											Integer id = main.DunCool.remove(p.getName());
											if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
											main.DunTime.remove(p.getName());
											return;
										} else {
											main.DunTime.put(p.getName(), CoolDown);
										}
									}
								}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 1200L, 1200L).getTaskId());
							} else {
								p.sendMessage("§6현재 §c포탈 이동 쿨타임 §e" + main.DunTime.get(p.getName()) + "§6분 남았습니다.");
								return false;
							}
						} else {
							p.sendMessage("§6[ 레벨제한 140 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("포탈9")) {
						if (player.getRpgLevel() > 159) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-9 " + p.getName());
								
								main.DunCool.put(p.getName(), new BukkitRunnable()
								{
									public void run()
									{
										int CoolDown = main.DunTime.get(p.getName()) - 1;
										if (CoolDown <= 0) {
											Integer id = main.DunCool.remove(p.getName());
											if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
											main.DunTime.remove(p.getName());
											return;
										} else {
											main.DunTime.put(p.getName(), CoolDown);
										}
									}
								}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 1200L, 1200L).getTaskId());
							} else {
								p.sendMessage("§6현재 §c포탈 이동 쿨타임 §e" + main.DunTime.get(p.getName()) + "§6분 남았습니다.");
								return false;
							}
						} else {
							p.sendMessage("§6[ 레벨제한 160 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("포탈10")) {
						if (player.getRpgLevel() > 179) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-10 " + p.getName());
								
								main.DunCool.put(p.getName(), new BukkitRunnable()
								{
									public void run()
									{
										int CoolDown = main.DunTime.get(p.getName()) - 1;
										if (CoolDown <= 0) {
											Integer id = main.DunCool.remove(p.getName());
											if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
											main.DunTime.remove(p.getName());
											return;
										} else {
											main.DunTime.put(p.getName(), CoolDown);
										}
									}
								}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 1200L, 1200L).getTaskId());
							} else {
								p.sendMessage("§6현재 §c포탈 이동 쿨타임 §e" + main.DunTime.get(p.getName()) + "§6분 남았습니다.");
								return false;
							}
						} else {
							p.sendMessage("§6[ 레벨제한 180 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("포탈11")) {
						if (player.getRpgLevel() > 199) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-11 " + p.getName());
								
								main.DunCool.put(p.getName(), new BukkitRunnable()
								{
									public void run()
									{
										int CoolDown = main.DunTime.get(p.getName()) - 1;
										if (CoolDown <= 0) {
											Integer id = main.DunCool.remove(p.getName());
											if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
											main.DunTime.remove(p.getName());
											return;
										} else {
											main.DunTime.put(p.getName(), CoolDown);
										}
									}
								}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 1200L, 1200L).getTaskId());
							} else {
								p.sendMessage("§6현재 §c포탈 이동 쿨타임 §e" + main.DunTime.get(p.getName()) + "§6분 남았습니다.");
								return false;
							}
						} else {
							p.sendMessage("§6[ 레벨제한 200 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("포탈12")) {
						if (player.getRpgLevel() > 219) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-12 " + p.getName());
								
								main.DunCool.put(p.getName(), new BukkitRunnable()
								{
									public void run()
									{
										int CoolDown = main.DunTime.get(p.getName()) - 1;
										if (CoolDown <= 0) {
											Integer id = main.DunCool.remove(p.getName());
											if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
											main.DunTime.remove(p.getName());
											return;
										} else {
											main.DunTime.put(p.getName(), CoolDown);
										}
									}
								}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 1200L, 1200L).getTaskId());
							} else {
								p.sendMessage("§6현재 §c포탈 이동 쿨타임 §e" + main.DunTime.get(p.getName()) + "§6분 남았습니다.");
								return false;
							}
						} else {
							p.sendMessage("§6[ 레벨제한 220 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("포탈13")) {
						if (player.getRpgLevel() > 239) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-13 " + p.getName());
								
								main.DunCool.put(p.getName(), new BukkitRunnable()
								{
									public void run()
									{
										int CoolDown = main.DunTime.get(p.getName()) - 1;
										if (CoolDown <= 0) {
											Integer id = main.DunCool.remove(p.getName());
											if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
											main.DunTime.remove(p.getName());
											return;
										} else {
											main.DunTime.put(p.getName(), CoolDown);
										}
									}
								}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 1200L, 1200L).getTaskId());
							} else {
								p.sendMessage("§6현재 §c포탈 이동 쿨타임 §e" + main.DunTime.get(p.getName()) + "§6분 남았습니다.");
								return false;
							}
						} else {
							p.sendMessage("§6[ 레벨제한 240 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("포탈14")) {
						if (player.getRpgLevel() > 259) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-14 " + p.getName());
								
								main.DunCool.put(p.getName(), new BukkitRunnable()
								{
									public void run()
									{
										int CoolDown = main.DunTime.get(p.getName()) - 1;
										if (CoolDown <= 0) {
											Integer id = main.DunCool.remove(p.getName());
											if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
											main.DunTime.remove(p.getName());
											return;
										} else {
											main.DunTime.put(p.getName(), CoolDown);
										}
									}
								}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 1200L, 1200L).getTaskId());
							} else {
								p.sendMessage("§6현재 §c포탈 이동 쿨타임 §e" + main.DunTime.get(p.getName()) + "§6분 남았습니다.");
								return false;
							}
						} else {
							p.sendMessage("§6[ 레벨제한 260 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("포탈15")) {
						if (player.getRpgLevel() > 279) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-15 " + p.getName());
								
								main.DunCool.put(p.getName(), new BukkitRunnable()
								{
									public void run()
									{
										int CoolDown = main.DunTime.get(p.getName()) - 1;
										if (CoolDown <= 0) {
											Integer id = main.DunCool.remove(p.getName());
											if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
											main.DunTime.remove(p.getName());
											return;
										} else {
											main.DunTime.put(p.getName(), CoolDown);
										}
									}
								}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 1200L, 1200L).getTaskId());
							} else {
								p.sendMessage("§6현재 §c포탈 이동 쿨타임 §e" + main.DunTime.get(p.getName()) + "§6분 남았습니다.");
								return false;
							}
						} else {
							p.sendMessage("§6[ 레벨제한 280 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("자살")) {
				if (p.hasPermission("[전사]") || p.hasPermission("[마법사]") || p.hasPermission("[궁수]") || p.hasPermission("[파이터]") || p.hasPermission("[도적]")) {
					if (main.SuicideCool.get(p.getName()) == null) {
						main.SuicideTime.put(p.getName(), 30);
						p.chat("/suicide");
						
						main.SuicideCool.put(p.getName(), new BukkitRunnable()
						{
							public void run()
							{
								int CoolDown = main.SuicideTime.get(p.getName()) - 1;
								if (CoolDown <= 0) {
									Integer id = main.SuicideCool.remove(p.getName());
									if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
									main.SuicideTime.remove(p.getName());
									return;
								} else {
									main.SuicideTime.put(p.getName(), CoolDown);
								}
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 20L, 20L).getTaskId());
					} else {
						p.sendMessage("§6현재 §c자살 쿨타임 §e" + main.SuicideTime.get(p.getName()) + "§6초 남았습니다.");
						return false;
					}
				} else {
					p.sendMessage("§c당신은 권한이 없습니다.");
					return false;
				}	
			}
			
			else if (commandLabel.equalsIgnoreCase("보스보상")) {
				if (p.hasPermission("*")) {
					if (args[0].equalsIgnoreCase("미쳐버린언데드")) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							if (DunzeonAPI.inDunzeonArea(player.getLocation(), "미쳐버린언데드")) {
								main.economy.depositPlayer(player.getName(), 50000);
								player.sendMessage("§c미쳐버린 언데드§6 보스를 처치하여 §c50000§6 원을 획득하셨습니다.");
				            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 미쳐버린언데드박스 give " + player.getName() + " 1");
							}
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("각성한스켈레톤")) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							if (DunzeonAPI.inDunzeonArea(player.getLocation(), "각성한스켈레톤")) {
								main.economy.depositPlayer(player.getName(), 50000);
								player.sendMessage("§c각성한 스켈레톤§6 보스를 처치하여 §c50000§6 원을 획득하셨습니다.");
				            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 각성한스켈레톤박스 give " + player.getName() + " 1");
							}
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("진화한위더")) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							if (DunzeonAPI.inDunzeonArea(player.getLocation(), "진화한위더")) {
								main.economy.depositPlayer(player.getName(), 50000);
								player.sendMessage("§c진화한 위더§6 보스를 처치하여 §c50000§6 원을 획득하셨습니다.");
				            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 진화한위더박스 give " + player.getName() + " 1");
							}
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("베드락")) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							if (DunzeonAPI.inDunzeonArea(player.getLocation(), "베드락")) {
								main.economy.depositPlayer(player.getName(), 50000);
								player.sendMessage("§c베드락§6 보스를 처치하여 §c50000§6 원을 획득하셨습니다.");
				            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 베드락박스 give " + player.getName() + " 1");
							}
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("부두술사")) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							if (DunzeonAPI.inDunzeonArea(player.getLocation(), "부두술사")) {
								main.economy.depositPlayer(player.getName(), 400000);
								player.sendMessage("§c부두술사§6 보스를 처치하여 §c400000§6 원을 획득하셨습니다.");
				            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 부두술사의생명광석 give " + player.getName() + " 1");
							}
						} return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("만렙보상")) {
				if (p.hasPermission("*")) {
					if (Method.getInfoBoolean(p, "만렙") == false) {
						main.Lvup.put(p.getName(), new BukkitRunnable()
						{
							int i = 10;
							public void run()
							{
								i -= 1;
								Method.castLvup(p);
						        	if (i <= 0) {
									Integer id = main.Lvup.remove(p.getName());
									if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								}
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 4L, 4L).getTaskId());
						Method.setInfoBoolean(p, "만렙", true);
						
						Bukkit.broadcastMessage("§e----------------------------------------------------------------");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("§6축하해주세요! §e[ " + p.getName() + " ] §6님이");
						Bukkit.broadcastMessage("§6300렙으로 만렙을 달성하셨습니다!");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("§e----------------------------------------------------------------");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 복권 give " + p.getName() + " 1");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 랜덤강화주문서 give " + p.getName() + " 5");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 체력포션박스 give " + p.getName() + " 10");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 마나포션박스 give " + p.getName() + " 10");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 스텟10 give " + p.getName() + " 2");
						
						p.setOp(false);
						if (p.hasPermission("[전사]")) {
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/칭호 추가 " + p.getName() + " &f[&cWarrior&eMaster&f]");
							Method.removeOP(p, is);
						}
						
						else if (p.hasPermission("[궁수]")) {
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/칭호 추가 " + p.getName() + " &f[&6Archer&eMaster&f]");
							Method.removeOP(p, is);
						}
						
						else if (p.hasPermission("[도적]")) {
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/칭호 추가 " + p.getName() + " &f[&9Rogue&eMaster&f]");
							Method.removeOP(p, is);
						}
						
						else if (p.hasPermission("[마법사]")) {
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/칭호 추가 " + p.getName() + " &f[&dWitch&eMaster&f]");
							Method.removeOP(p, is);
						}
						
						else if (p.hasPermission("[파이터]")) {
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/칭호 추가 " + p.getName() + " &f[&aFighter&eMaster&f]");
							Method.removeOP(p, is);
						}
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("전직초기화")) {
				if (p.hasPermission("*")) {
					if (!Method.getInfoString(p, "전직").equals("NONE")) {
						Scanner sc = new Scanner(p.getName());
						String input = sc.nextLine();
						char[] c = null;
						c = input.toCharArray();
						for (int i = 0; i < c.length; i++) {
							if (c[i] >= 65 && c[i] <= 90) {
								c[i] += 32;
							}
						}
						
						String change = "";
						change = new String(c, 0, c.length);
						File mf = new File("plugins/MagicSpells/spellbooks/" + change + ".txt");
						Method.setInfoString(p, "전직", "NONE");
						Method.setInfoInt(p, "전직 카운트", 0);
						p.sendMessage("§6성공적으로 §c전직 초기화§6를 마쳤습니다.");
						mf.delete();

						Method.PlayerManuadd(p.getName(), "전직초기화");
						
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 전직선택관 " + p.getName());
						return false;
					} else {
						p.sendMessage("당신은 전직을 안한 상태입니다.");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("물포탈")) {
				if (p.hasPermission("*")) {
					String name = sender.getName();
					RpgPlayer player = Rpg.getRpgPlayera(name);
					
					if (args[0].equalsIgnoreCase("2")) {
						if (player.getRpgLevel() > 19) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-2 " + p.getName());
							return false;
						} else {
							p.sendMessage("§6[ 레벨제한 20 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("3")) {
						if (player.getRpgLevel() > 39) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-3 " + p.getName());
							return false;
						} else {
							p.sendMessage("§6[ 레벨제한 40 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("4")) {
						if (player.getRpgLevel() > 59) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-4 " + p.getName());
							return false;
						} else {
							p.sendMessage("§6[ 레벨제한 60 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("5")) {
						if (player.getRpgLevel() > 79) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-5 " + p.getName());
							return false;
						} else {
							p.sendMessage("§6[ 레벨제한 80 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("6")) {
						if (player.getRpgLevel() > 99) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-6 " + p.getName());
							return false;
						} else {
							p.sendMessage("§6[ 레벨제한 100 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("7")) {
						if (player.getRpgLevel() > 119) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-7 " + p.getName());
							return false;
						} else {
							p.sendMessage("§6[ 레벨제한 120 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("8")) {
						if (player.getRpgLevel() > 139) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-8 " + p.getName());
							return false;
						} else {
							p.sendMessage("§6[ 레벨제한 140 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("9")) {
						if (player.getRpgLevel() > 159) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-9 " + p.getName());
							return false;
						} else {
							p.sendMessage("§6[ 레벨제한 160 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("10")) {
						if (player.getRpgLevel() > 179) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-10 " + p.getName());
							return false;
						} else {
							p.sendMessage("§6[ 레벨제한 180 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("11")) {
						if (player.getRpgLevel() > 199) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-11 " + p.getName());
							return false;
						} else {
							p.sendMessage("§6[ 레벨제한 200 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("12")) {
						if (player.getRpgLevel() > 219) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-12 " + p.getName());
							return false;
						} else {
							p.sendMessage("§6[ 레벨제한 220 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("13")) {
						if (player.getRpgLevel() > 239) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-13 " + p.getName());
							return false;
						} else {
							p.sendMessage("§6[ 레벨제한 240 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("14")) {
						if (player.getRpgLevel() > 259) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-14 " + p.getName());
							return false;
						} else {
							p.sendMessage("§6[ 레벨제한 260 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("15")) {
						if (player.getRpgLevel() > 279) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 던전-15 " + p.getName());
							return false;
						} else {
							p.sendMessage("§6[ 레벨제한 280 ] §c당신은 아직 §e다음 던전§c에 출입할 수 없습니다.");
							return false;
						}
					}
				}
			}
		} else {
			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + 
							   ChatColor.WHITE + "]" + ChatColor.RED + " 콘솔에선 실행이 불가능 합니다.");
			return false;
		}
		
		return false;
	}

	public static void displayHelp1(CommandSender sender)
	{
		sender.sendMessage(" §e---- §6액션 RPG §e-- §6페이지 §c1§6/§c3 §e----");
		sender.sendMessage("§6/확성기 §f- 확성기를 실행합니다. §b( rpgitem )");
		sender.sendMessage("§6/이벤터 §f- 이벤터 확성기를 실행합니다. §b( rpgitem )");
		sender.sendMessage("§6/튜토리얼마침 §f- 튜토리얼을 마치는 명령어입니다. §b( rpgitem )");
		sender.sendMessage("§6/전직실행GUI §f- 전직 GUI를 오픈합니다.");
		sender.sendMessage("§6/포션알고리즘<1~5> §f- 포션 효과를 줍니다. §b( rpgitem )");
		sender.sendMessage("§6/색깔을변경하자 §f- 닉네임 색깔을 변경합니다. §b( rpgitem )");
		sender.sendMessage("§6/색깔을복구하자 §f- 닉네임 색깔을 복구합니다. §b( rpgitem )");
		sender.sendMessage("§6/스텟<1/10/40/60> §f- 스텟을 줍니다. §b( rpgitem )");
		sender.sendMessage("");
		sender.sendMessage("§6명령어 §cAR 2 §6를 쳐서 다음페이지로 넘어가세요.");
	}
	
	private void displayHelp2(CommandSender sender)
	{
		sender.sendMessage(" §e---- §6액션 RPG §e-- §6페이지 §c2§6/§c3 §e----");
		sender.sendMessage("§6/박스<미쳐버린언데드/베드락/각성한스켈레톤/진화한위더/시험포기/랜덤강화주문서/체력포션/마나포션/부두술사의생명광석/수박/랜덤룬> §f- 박스 오픈 효과를 줍니다. §b( rpgitem )");
		sender.sendMessage("§6/<1~15>단계골드큐브 §f- 골드 큐브 오픈 효과를 줍니다. §b( rpgitem )");
		sender.sendMessage("§6/LvUP §f- 레벨업한 효과를 줍니다. §b( rpgitem )");
		sender.sendMessage("§6/복권실행 §f- 복권을 실행한 효과를 줍니다. §b( rpgitem )");
		sender.sendMessage("§6/문의알림 §f- 문의가 온 것을 알립니다.");
		sender.sendMessage("§6/무기 §f- 자신의 전직에 맞는 무기를 지급받습니다.");
		sender.sendMessage("§6/카드팩 §f- 카드팩 공급소로 이동합니다. §b( rpgitem )");
		sender.sendMessage("§6/야간투시 §f- 실행자에게 야간투시를 줍니다.");
		sender.sendMessage("§6/전직하자 <직업이름> §f- 전직 효과를 냅니다.");
		sender.sendMessage("§6/이동던전 포탈<1~15> §f- 일반 던전 스크롤 포탈 이동 효과를 냅니다.");
		sender.sendMessage("");
		sender.sendMessage("§6명령어 §cAR 3 §6를 쳐서 다음페이지로 넘어가세요.");
	}
	
	private void displayHelp3(CommandSender sender)
	{
		sender.sendMessage(" §e---- §6액션 RPG §e-- §6페이지 §c3§6/§c3 §e----");
		sender.sendMessage("§6/보스보상 <미쳐버린언데드/각성한스켈레톤/진화한위더/베드락/부두술사> §f- 리스트에 있는 플레이어에게 보상을 줍니다.");
		sender.sendMessage("§6/만렙보상 §f- 만렙 보상을 줍니다.");
		sender.sendMessage("§6/부두술사실행 <부활/변수1/변수2/변수초기화> §f- 부두술사에 관한 명령어입니다.");
		sender.sendMessage("§6/전직초기화 §f- 전직을 초기화 시킵니다. §b( rpgitem )");
		sender.sendMessage("§6/보상지급 §f- 서버 초기화 보상을 지급받습니다. §b( rpgitem )");
		sender.sendMessage("§6/물포탈 <1~15> §f- 물 포탈 이동명령어 효과를 냅니다.");
		sender.sendMessage("§6/표지판 <1~4> <내용> §f- 표지판 내용을 수정합니다");
		sender.sendMessage("§6/in §f- 아이템 이름을 봅니다.");
		sender.sendMessage("§6/gp §f- 좌표를 봅니다.");
		sender.sendMessage("§6/<베드락/언데드/위더/부두/스켈레톤>확인 §f- 보스가 죽었는지 사라졌는지 확인합니다.");
		sender.sendMessage("§6/<베드락/언데드/위더/부두/스켈레톤>안정 §f- 보스를 재스폰 시킵니다.");
	}
}
