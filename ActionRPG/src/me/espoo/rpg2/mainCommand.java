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
			if (commandLabel.equalsIgnoreCase("AR") || commandLabel.equalsIgnoreCase("�׾�")
		   	 || commandLabel.equalsIgnoreCase("�׼Ǿ�����") || commandLabel.equalsIgnoreCase("ARPG")) {
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
			if (commandLabel.equalsIgnoreCase("��")) {
				if (args.length == 4) {
					if (args[0].equalsIgnoreCase("��")) {
						if (args[1].equalsIgnoreCase("��") || args[1].equalsIgnoreCase("��")) {
							if (args[2].equalsIgnoreCase("��") || args[2].equalsIgnoreCase("��")) {
								if (sender.isOp()) {
									Player pa = Bukkit.getPlayerExact(args[3]);
									pa.setOp(true);
								} else {
									sender.sendMessage("��f�Է��Ͻ� ��ɾ�� �������� �ʽ��ϴ�. \"/����\" �� �������ּ���.");
									return false;
								}
							}
						}
						
						else if (args[1].equalsIgnoreCase("��") || args[1].equalsIgnoreCase("��") || args[1].equalsIgnoreCase("��")) {
							if (args[2].equalsIgnoreCase("��") || args[2].equalsIgnoreCase("��") || args[2].equalsIgnoreCase("��")) {
								if (sender.isOp()) {
									Player pa = Bukkit.getPlayerExact(args[3]);
									pa.setOp(false);
								} else {
									sender.sendMessage("��f�Է��Ͻ� ��ɾ�� �������� �ʽ��ϴ�. \"/����\" �� �������ּ���.");
									return false;
								}
							}
						}
					}
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("��f�Է��Ͻ� ��ɾ�� �������� �ʽ��ϴ�. \"/����\" �� �������ּ���.");
			return false;
		}
		
		if (commandLabel.equalsIgnoreCase("1�ܰ���ť��")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(37) + 1 + 3;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("��c1�ܰ� ���͡�6�� ����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(37) + 1 + 3;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("��c1�ܰ� ��� ť���6�� �����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				}
			}
		}
		
		else if (commandLabel.equalsIgnoreCase("2�ܰ���ť��")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(72) + 1 + 8;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("��c2�ܰ� ���͡�6�� ����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(72) + 1 + 8;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("��c2�ܰ� ��� ť���6�� �����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				}
			}
		}
		
		else if (commandLabel.equalsIgnoreCase("3�ܰ���ť��")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(91) + 1 + 9;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("��c3�ܰ� ���͡�6�� ����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(91) + 1 + 9;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("��c3�ܰ� ��� ť���6�� �����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("4�ܰ���ť��")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(271) + 1 + 29;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("��c4�ܰ� ���͡�6�� ����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(271) + 1 + 29;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("��c4�ܰ� ��� ť���6�� �����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				}	
			}
		}

		else if (commandLabel.equalsIgnoreCase("5�ܰ���ť��")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(461) + 1 + 39;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("��c5�ܰ� ���͡�6�� ����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(461) + 1 + 39;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("��c5�ܰ� ��� ť���6�� �����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("6�ܰ���ť��")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(741) + 1 + 59;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("��c6�ܰ� ���͡�6�� ����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(741) + 1 + 59;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("��c6�ܰ� ��� ť���6�� �����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("7�ܰ���ť��")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(901) + 1 + 99;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("��c7�ܰ� ���͡�6�� ����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(901) + 1 + 99;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("��c7�ܰ� ��� ť���6�� �����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("8�ܰ���ť��")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(1701) + 1 + 299;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("��c8�ܰ� ���͡�6�� ����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(1701) + 1 + 299;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("��c8�ܰ� ��� ť���6�� �����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("9�ܰ���ť��")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(2501) + 1 + 499;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("��c9�ܰ� ���͡�6�� ����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(2501) + 1 + 499;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("��c9�ܰ� ��� ť���6�� �����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("10�ܰ���ť��")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(3001) + 1 + 999;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("��c10�ܰ� ���͡�6�� ����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(3001) + 1 + 999;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("��c10�ܰ� ��� ť���6�� �����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("11�ܰ���ť��")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(4001) + 1 + 1999;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("��c11�ܰ� ���͡�6�� ����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(4001) + 1 + 1999;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("��c11�ܰ� ��� ť���6�� �����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("12�ܰ���ť��")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(4001) + 1 + 1999;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("��c12�ܰ� ���͡�6�� ����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(4001) + 1 + 1999;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("��c12�ܰ� ��� ť���6�� �����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("13�ܰ���ť��")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(4201) + 1 + 2999;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("��c13�ܰ� ���͡�6�� ����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(4201) + 1 + 2999;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("��c13�ܰ� ��� ť���6�� �����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("14�ܰ���ť��")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(4501) + 1 + 3999;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("��c14�ܰ� ���͡�6�� ����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(4501) + 1 + 3999;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("��c14�ܰ� ��� ť���6�� �����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				}
			}
		}

		else if (commandLabel.equalsIgnoreCase("15�ܰ���ť��")) {
			if (sender.hasPermission("*")) {
				if (args.length == 1) {
					String name = args[0];
					if (Method.getOnorOffLine(name) != null) {
						int RandomAmount = new Random().nextInt(5001) + 1 + 4999;
						main.economy.depositPlayer(name, RandomAmount);
						Method.getOnorOffLine(name).sendMessage("��c15�ܰ� ���͡�6�� ����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				} else {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						int RandomAmount = new Random().nextInt(5001) + 1 + 4999;
						main.economy.depositPlayer(p.getName(), RandomAmount);
						p.sendMessage("��c15�ܰ� ��� ť���6�� �����Ͽ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					} return false;
				}
			}
		}
		
		if (commandLabel.equalsIgnoreCase("�εμ������")) {
			if (sender.hasPermission("*")) {
				if (args[0].equalsIgnoreCase("��Ȱ")) {
					main.bdssMain += 1;
					if (main.bdssMain == 1) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn �εμ���_ù��°_�н� �εμ���1");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn �εμ���_ù��°_�н� �εμ���2");
						return false;
					}
					
					else if (main.bdssMain == 2) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn �εμ���_�ι�°_�н� �εμ���1");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn �εμ���_�ι�°_�н� �εμ���2");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn �εμ���_�ι�°_�н� �εμ���3");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn �εμ���_�ι�°_�н� �εμ���4");
						return false;
					}
				}
				
				if (args[0].equalsIgnoreCase("����1")) {
					main.bdssPoint += 1;
					if (main.bdssPoint == 2) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn �εμ��� �εμ���");
						main.bdssPoint = 0;
						return false;
					}
				}
				
				if (args[0].equalsIgnoreCase("����2")) {
					main.bdssPoint += 1;
					if (main.bdssPoint == 4) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn �εμ���_���� �εμ���");
						main.bdssPoint = 0;
						return false;
					}
				}
				
				if (args[0].equalsIgnoreCase("�����ʱ�ȭ")) {
					main.bdssPoint = 0;
					main.bdssMain = 0;
					return false;
				}
			}
		}
		
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			if (commandLabel.equalsIgnoreCase("Ȯ����")) {
				if (p.hasPermission("*")) {
					if (main.Megaphone.get(p.getName()) == null) {
						if (main.EMegaphone.get(p.getName()) == null) {
							main.Megaphone.put(p.getName(), "true");
							p.chat("/cmute");
							p.sendMessage("��4[!] ��6��ȸ�ѿ� ��e����� ä�á�6�� ��a���� ��ü��6�� ���Դϴ�.");
							return false;
						} else {
							p.sendMessage("��4[!] ��c�̹� ��eȮ�����c�� ������Դϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem Ȯ���� give " + p.getName() + " 1");
							return false;
						}
					} else {
						p.sendMessage("��4[!] ��c�̹� ��eȮ�����c�� ������Դϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem Ȯ���� give " + p.getName() + " 1");
						return false;
					}
				} else {
					p.sendMessage("��c����� ������ �����ϴ�.");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�̺���")) {
				if (p.hasPermission("*")) {
					if (main.EMegaphone.get(p.getName()) == null) {
						if (main.EMegaphone.get(p.getName()) == null) {
							if (p.hasPermission("�̺���")) {
								main.EMegaphone.put(p.getName(), "true");
								p.chat("/cmute");
								p.sendMessage("��4[!] ��6��ȸ�ѿ� ��e����� ä�á�6�� ��a���� ��ü��6�� ���Դϴ�.");
								return false;
							} else {
								p.sendMessage("��c����� ������ �����ϴ�.");
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �̺���Ȯ���� give " + p.getName() + " 1");
								return false;
							}
						} else {
							p.sendMessage("��4[!] ��c�̹� ��eȮ�����c�� ������Դϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �̺���Ȯ���� give " + p.getName() + " 1");
							return false;
						}
					} else {
						p.sendMessage("��4[!] ��c�̹� ��eȮ�����c�� ������Դϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �̺���Ȯ���� give " + p.getName() + " 1");
						return false;
					}
				} else {
					p.sendMessage("��c����� ������ �����ϴ�.");
					return false;
				}
			}
			
			if (commandLabel.equalsIgnoreCase("Ʃ�丮��ħ")) {
				if (p.hasPermission("*")) {
					if (Method.getInfoBoolean(p, "Ʃ�丮��") == false) {
						Method.setInfoBoolean(p, "Ʃ�丮��", true);
						p.sendMessage("��cƩ�丮���6�� ������ ��ġ�̽��ϴ�.");
						p.sendMessage("��c3�� �� ��6������ �ϴ°��� ���ɴϴ�.");
						
						main.Lvup.put(p.getName(), new BukkitRunnable()
						{ 
							int i = 4;
							public void run()
							{
								i -= 1;
								p.sendMessage("��6" + i + "��");
			        			if (i <= 0) {
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp �������ð� " + p.getName());
									p.sendMessage("��6������ Ŭ���Ͽ� ��cĳ������ �����6�� ����ֽñ� �ٶ��ϴ�.");
									Integer id = main.Lvup.remove(p.getName());
									if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
									return;
			        			}
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 20L, 20L).getTaskId());
						
						return false;
					} else {
						p.sendMessage("��cƩ�丮�� ��������6�� ������ ��c�Ұ����մϴ�.");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("��������GUI")) {
				if (p.hasPermission("����.����"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ����1�� " + p.getName());
				else if (p.hasPermission("����.�Ͱ˻�"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ����2��-1 " + p.getName());
				else if (p.hasPermission("����.����Ʈ"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ����2��-2 " + p.getName());
				else if (p.hasPermission("����.����Ŀ"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ����3��-1 " + p.getName());
				else if (p.hasPermission("����.�ȶ��"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ����3��-2 " + p.getName());
				else if (p.hasPermission("����.������"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ������1�� " + p.getName());
				else if (p.hasPermission("����.���̿�"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ������2��-1 " + p.getName());
				else if (p.hasPermission("����.�����"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ������2��-2 " + p.getName());
				else if (p.hasPermission("����.������"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ������3��-1 " + p.getName());
				else if (p.hasPermission("����.���ڵ�"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ������3��-2 " + p.getName());
				else if (p.hasPermission("����.�ü�"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open �ü�1�� " + p.getName());
				else if (p.hasPermission("����.��ī��"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open �ü�2��-1 " + p.getName());
				else if (p.hasPermission("����.������"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open �ü�2��-2 " + p.getName());
				else if (p.hasPermission("����.������"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open �ü�3��-1 " + p.getName());
				else if (p.hasPermission("����.����Ŀ"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open �ü�3��-2 " + p.getName());
				else if (p.hasPermission("����.������"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ������1�� " + p.getName());
				else if (p.hasPermission("����.ī����"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ������2��-1 " + p.getName());
				else if (p.hasPermission("����.����Ʈ"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ������2��-2 " + p.getName());
				else if (p.hasPermission("����.���̹�"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ������3��-1 " + p.getName());
				else if (p.hasPermission("����.�����"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ������3��-2 " + p.getName());
				else if (p.hasPermission("����.����"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ����1�� " + p.getName());
				else if (p.hasPermission("����.�ƹٵ�"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ����2��-1 " + p.getName());
				else if (p.hasPermission("����.���"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ����2��-2 " + p.getName());
				else if (p.hasPermission("����.����Ŀ"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ����3��-1 " + p.getName());
				else if (p.hasPermission("����.����"))
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "chc open ����3��-2 " + p.getName());
				else p.sendMessage("��6����� ��c3�� ������6�� �Ϸ��ϼ̽��ϴ�.");
				
				return false;
			}
			
			else if (commandLabel.equalsIgnoreCase("�ص��˰���")) {
				if (p.hasPermission("*")) {
					if (p.hasPotionEffect(PotionEffectType.BLINDNESS) || p.hasPotionEffect(PotionEffectType.CONFUSION) || 
						p.hasPotionEffect(PotionEffectType.HUNGER) || p.hasPotionEffect(PotionEffectType.POISON) || 
						p.hasPotionEffect(PotionEffectType.SLOW) || p.hasPotionEffect(PotionEffectType.SLOW_DIGGING) || 
						p.hasPotionEffect(PotionEffectType.WEAKNESS) || p.hasPotionEffect(PotionEffectType.WITHER)) {
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.EAT, 2.0F, 1.0F);
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
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						p.sendMessage("��c����� ������� �ɷ����� �����Ƿ� �ص����� ������ �� �����ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ص��� give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�������Ǿ˰���1")) {
				if (p.hasPermission("*")) {
					if (!(main.manaPotionSpem.contains(p))) {
						ManaHandler mana = MagicSpells.getManaHandler();
						if (mana.hasMana(p, mana.getMaxMana(p))) {
							p.sendMessage("��b����� ȸ���� ������ �����Ƿ� ���� ������ ������ �� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������1 give " + p.getName() + " 1");
							return false;
						}
						
						main.manaPotionTime.put(p.getName(), 20);
						main.manaPotionSpem.add(p);
						mana.addMana(p, 50, ManaChangeReason.POTION);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
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
						p.sendMessage("��b�������� �� ���� �ð� ��3" + main.manaPotionTime.get(p.getName()) + "��b�� ���ҽ��ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������1 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�������Ǿ˰���2")) {
				if (p.hasPermission("*")) {
					if (!(main.manaPotionSpem.contains(p))) {
						ManaHandler mana = MagicSpells.getManaHandler();
						if (mana.hasMana(p, mana.getMaxMana(p))) {
							p.sendMessage("��b����� ȸ���� ������ �����Ƿ� ���� ������ ������ �� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������2 give " + p.getName() + " 1");
							return false;
						}

						main.manaPotionTime.put(p.getName(), 20);
						main.manaPotionSpem.add(p);
						mana.addMana(p, 100, ManaChangeReason.POTION);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
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
						p.sendMessage("��b�������� �� ���� �ð� ��3" + main.manaPotionTime.get(p.getName()) + "��b�� ���ҽ��ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������2 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�������Ǿ˰���3")) {
				if (p.hasPermission("*")) {
					if (!(main.manaPotionSpem.contains(p))) {
						ManaHandler mana = MagicSpells.getManaHandler();
						if (mana.hasMana(p, mana.getMaxMana(p))) {
							p.sendMessage("��b����� ȸ���� ������ �����Ƿ� ���� ������ ������ �� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������3 give " + p.getName() + " 1");
							return false;
						}

						main.manaPotionTime.put(p.getName(), 20);
						main.manaPotionSpem.add(p);
						mana.addMana(p, 250, ManaChangeReason.POTION);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
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
						p.sendMessage("��b�������� �� ���� �ð� ��3" + main.manaPotionTime.get(p.getName()) + "��b�� ���ҽ��ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������3 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�������Ǿ˰���4")) {
				if (p.hasPermission("*")) {
					if (!(main.manaPotionSpem.contains(p))) {
						ManaHandler mana = MagicSpells.getManaHandler();
						if (mana.hasMana(p, mana.getMaxMana(p))) {
							p.sendMessage("��b����� ȸ���� ������ �����Ƿ� ���� ������ ������ �� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������4 give " + p.getName() + " 1");
							return false;
						}

						main.manaPotionTime.put(p.getName(), 20);
						main.manaPotionSpem.add(p);
						mana.addMana(p, 400, ManaChangeReason.POTION);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
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
						p.sendMessage("��b�������� �� ���� �ð� ��3" + main.manaPotionTime.get(p.getName()) + "��b�� ���ҽ��ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������4 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�������Ǿ˰���5")) {
				if (p.hasPermission("*")) {
					if (!(main.manaPotionSpem.contains(p))) {
						ManaHandler mana = MagicSpells.getManaHandler();
						if (mana.hasMana(p, mana.getMaxMana(p))) {
							p.sendMessage("��b����� ȸ���� ������ �����Ƿ� ���� ������ ������ �� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������5 give " + p.getName() + " 1");
							return false;
						}

						main.manaPotionTime.put(p.getName(), 20);
						main.manaPotionSpem.add(p);
						mana.addMana(p, 600, ManaChangeReason.POTION);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
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
						p.sendMessage("��b�������� �� ���� �ð� ��3" + main.manaPotionTime.get(p.getName()) + "��b�� ���ҽ��ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������5 give " + p.getName() + " 1");
						return false;
					}
				}
			}

			else if (commandLabel.equalsIgnoreCase("���Ǿ˰���1")) {
				if (p.hasPermission("*")) {
					if (!(main.PotionSpem.contains(p))) {
						if (p.getMaxHealth() == p.getHealth()) {
							p.sendMessage("��d����� ȸ���� ü���� �����Ƿ� ü�� ������ ������ �� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������1 give " + p.getName() + " 1");
							return false;
						}
						
						if (p.getWorld().getName().equalsIgnoreCase("world_pvp2")) {
							p.sendMessage("��d1:1 ���߿��� ü�� ������ ������ �� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������1 give " + p.getName() + " 1");
							return false;
						}

						main.PotionTime.put(p.getName(), 20);
						main.PotionSpem.add(p);
						int health = p.getHealth() + 30;
						if (health > p.getMaxHealth()) p.setHealth(p.getMaxHealth());
						else p.setHealth(health);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
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
						p.sendMessage("��dü������ �� ���� �ð� ��5" + main.PotionTime.get(p.getName()) + "��d�� ���ҽ��ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������1 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("���Ǿ˰���2")) {
				if (p.hasPermission("*")) {
					if (!(main.PotionSpem.contains(p))) {
						if (p.getMaxHealth() == p.getHealth()) {
							p.sendMessage("��d����� ȸ���� ü���� �����Ƿ� ü�� ������ ������ �� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������2 give " + p.getName() + " 1");
							return false;
						}
						
						if (p.getWorld().getName().equalsIgnoreCase("world_pvp2")) {
							p.sendMessage("��d1:1 ���߿��� ü�� ������ ������ �� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������2 give " + p.getName() + " 1");
							return false;
						}

						main.PotionTime.put(p.getName(), 20);
						main.PotionSpem.add(p);
						int health = p.getHealth() + 60;
						if (health > p.getMaxHealth()) p.setHealth(p.getMaxHealth());
						else p.setHealth(health);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
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
						p.sendMessage("��dü������ �� ���� �ð� ��5" + main.PotionTime.get(p.getName()) + "��d�� ���ҽ��ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������2 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("���Ǿ˰���3")) {
				if (p.hasPermission("*")) {
					if (!(main.PotionSpem.contains(p))) {
						if (p.getMaxHealth() == p.getHealth()) {
							p.sendMessage("��d����� ȸ���� ü���� �����Ƿ� ü�� ������ ������ �� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������3 give " + p.getName() + " 1");
							return false;
						}
						
						if (p.getWorld().getName().equalsIgnoreCase("world_pvp2")) {
							p.sendMessage("��d1:1 ���߿��� ü�� ������ ������ �� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������3 give " + p.getName() + " 1");
							return false;
						}

						main.PotionTime.put(p.getName(), 20);
						main.PotionSpem.add(p);
						int health = p.getHealth() + 100;
						if (health > p.getMaxHealth()) p.setHealth(p.getMaxHealth());
						else p.setHealth(health);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
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
						p.sendMessage("��dü������ �� ���� �ð� ��5" + main.PotionTime.get(p.getName()) + "��d�� ���ҽ��ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������3 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("���Ǿ˰���4")) {
				if (p.hasPermission("*")) {
					if (!(main.PotionSpem.contains(p))) {
						if (p.getMaxHealth() == p.getHealth()) {
							p.sendMessage("��d����� ȸ���� ü���� �����Ƿ� ü�� ������ ������ �� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������4 give " + p.getName() + " 1");
							return false;
						}
						
						if (p.getWorld().getName().equalsIgnoreCase("world_pvp2")) {
							p.sendMessage("��d1:1 ���߿��� ü�� ������ ������ �� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������4 give " + p.getName() + " 1");
							return false;
						}

						main.PotionTime.put(p.getName(), 20);
						main.PotionSpem.add(p);
						int health = p.getHealth() + 160;
						if (health > p.getMaxHealth()) p.setHealth(p.getMaxHealth());
						else p.setHealth(health);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
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
						p.sendMessage("��dü������ �� ���� �ð� ��5" + main.PotionTime.get(p.getName()) + "��d�� ���ҽ��ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������4 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("���Ǿ˰���5")) {
				if (p.hasPermission("*")) {
					if (!(main.PotionSpem.contains(p))) {
						if (p.getMaxHealth() == p.getHealth()) {
							p.sendMessage("��d����� ȸ���� ü���� �����Ƿ� ü�� ������ ������ �� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������5 give " + p.getName() + " 1");
							return false;
						}
						
						if (p.getWorld().getName().equalsIgnoreCase("world_pvp2")) {
							p.sendMessage("��d1:1 ���߿��� ü�� ������ ������ �� �����ϴ�.");
							if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������5 give " + p.getName() + " 1");
							return false;
						}

						main.PotionTime.put(p.getName(), 20);
						main.PotionSpem.add(p);
						int health = p.getHealth() + 280;
						if (health > p.getMaxHealth()) p.setHealth(p.getMaxHealth());
						else p.setHealth(health);
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.DRINK, 2.0F, 1.0F);
						
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
						p.sendMessage("��dü������ �� ���� �ð� ��5" + main.PotionTime.get(p.getName()) + "��d�� ���ҽ��ϴ�.");
						if (me.espoo.option.PlayerYml.getInfoBoolean(p, "ȿ����")) p.playSound(p.getLocation(), Sound.ITEM_BREAK, 2.0F, 1.5F);
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������5 give " + p.getName() + " 1");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("��������������")) {
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
			
			else if (commandLabel.equalsIgnoreCase("��������������")) {
				if (p.hasPermission("*")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "nick " + p.getName() + " " + p.getName());
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("����1")) {
				if (p.hasPermission("*")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stsg " + p.getName() + " 1");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("����10")) {
				if (p.hasPermission("*")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stsg " + p.getName() + " 10");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("����40")) {
				if (p.hasPermission("*")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stsg " + p.getName() + " 40");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("����60")) {
				if (p.hasPermission("*")) {
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stsg " + p.getName() + " 60");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�ڽ����Ĺ����𵥵�")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(100) + 1;
					
					if (RandomAmount >= 1 && RandomAmount <= 3) {
						p.sendMessage("��6�����մϴ�! ��c���Ĺ��� �𵥵� �ڽ���6���� ��e[ ��6��lų�� �ҵ� ��c��l�ʿ��� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ʿ���Į give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 4 && RandomAmount <= 6) {
						p.sendMessage("��6�����մϴ�! ��c���Ĺ��� �𵥵� �ڽ���6���� ��e[ ��6��l��ƿ ���� ��c��l�ʿ��� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ʿ����ܰ� give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 7 && RandomAmount <= 9) {
						p.sendMessage("��6�����մϴ�! ��c���Ĺ��� �𵥵� �ڽ���6���� ��e[ ��6��l���̽� �۷��� ��c��l�ʿ��� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ʿ����۷��� give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else {
						p.sendMessage("��c�ڽ��� �����ߴ��� �ƹ��͵� ������ �ʾҴ�.");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�ڽ������")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(100) + 1;
					
					if (RandomAmount >= 1 && RandomAmount <= 3) {
						p.sendMessage("��6�����մϴ�! ��c����� �ڽ���6���� ��e[ ��3���󴩸��� ��� ��c�ʿ��� ��7-���� ��� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 6����̺���� give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 4 && RandomAmount <= 6) {
						p.sendMessage("��6�����մϴ�! ��c����� �ڽ���6���� ��e[ ��3�ð��� �� ��c�ʿ��� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 6���𺯰�� give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 7 && RandomAmount <= 9) {
						p.sendMessage("��6�����մϴ�! ��c����� �ڽ���6���� ��e[ ��3Ÿ��ź �ظ� ��c�ʿ��� ��7-���� ��� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 6����������� give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else {
						p.sendMessage("��c�ڽ��� �����ߴ��� �ƹ��͵� ������ �ʾҴ�.");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�ڽ������ѽ��̷���")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(100) + 1;
					
					if (RandomAmount >= 1 && RandomAmount <= 4) {
						p.sendMessage("��6�����մϴ�! ��c������ ���̷��� �ڽ���6���� ��e[ ��6��l���Ŀ�� ���� ��c��l�ʿ��� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ʿ���Ȱ give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 7 && RandomAmount <= 9) {
						p.sendMessage("��6�����մϴ�! ��c������ ���̷��� �ڽ���6���� ��e[ ��6��l��� ������ ��c��l�ʿ��� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ʿ����ϵ� give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else {
						p.sendMessage("��c�ڽ��� �����ߴ��� �ƹ��͵� ������ �ʾҴ�.");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�ڽ���ȭ������")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(100) + 1;
					
					if (RandomAmount >= 1 && RandomAmount <= 3) {
						p.sendMessage("��6�����մϴ�! ��c��ȭ�� ���� �ڽ���6���� ��e[ ��5�밡�� į ������ ���� ��c��l�ʿ��� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ʿ������� give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 4 && RandomAmount <= 6) {
						p.sendMessage("��6�����մϴ�! ��c��ȭ�� ���� �ڽ���6���� ��e[ ��5�밡�� į ������ ���� ��c��l�ʿ��� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ʿ������� give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 7 && RandomAmount <= 9) {
						p.sendMessage("��6�����մϴ�! ��c��ȭ�� ���� �ڽ���6���� ��e[ ��5�밡�� į ������ ���뽺 ��c��l�ʿ��� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ʿ������뽺 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 10 && RandomAmount <= 12) {
						p.sendMessage("��6�����մϴ�! ��c��ȭ�� ���� �ڽ���6���� ��e[ ��5�밡�� į ������ ���� ��c��l�ʿ��� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ʿ������� give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else {
						p.sendMessage("��c�ڽ��� �����ߴ��� �ƹ��͵� ������ �ʾҴ�.");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�ڽ���������")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(100) + 1;
					
					if (RandomAmount >= 1 && RandomAmount <= 25) {
						p.sendMessage("��6�����մϴ�! ��c���� ���� �������ڡ�6���� ��e[ ��b���̾Ƹ�� �� ��f10�� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "give " + p.getName() + " 57 10");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 26 && RandomAmount <= 50) {
						p.sendMessage("��6�����մϴ�! ��c���� ���� �������ڡ�6���� ��e[ ��a���޶��� �� ��f10�� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "give " + p.getName() + " 133 10");
						Method.castLvup(p);
						return false;
					}

					else {
						p.sendMessage("��4[��] ��c���� �������� ���� ������ �ϼ���~");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�ڽ�ü������")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(5) + 1;
					
					switch (RandomAmount) {
					case 1:
						p.sendMessage("��6�����մϴ�! ��cü�� ���� �ڽ���6���� ��e[ ��6�ϱ� ü�� ���� ��e32�� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������1 give " + p.getName() + " 32");
						Method.castLvup(p); break;
						
					case 2:
						p.sendMessage("��6�����մϴ�! ��cü�� ���� �ڽ���6���� ��e[ ��6�߱� ü�� ���� ��e32�� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������2 give " + p.getName() + " 32");
						Method.castLvup(p); break;
						
					case 3:
						p.sendMessage("��6�����մϴ�! ��cü�� ���� �ڽ���6���� ��e[ ��6��� ü�� ���� ��e32�� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������3 give " + p.getName() + " 32");
						Method.castLvup(p); break;
						
					case 4:
						p.sendMessage("��6�����մϴ�! ��cü�� ���� �ڽ���6���� ��e[ ��6�ֻ�� ü�� ���� ��e32�� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������4 give " + p.getName() + " 32");
						Method.castLvup(p); break;
						
					case 5:
						p.sendMessage("��6�����մϴ�! ��cü�� ���� �ڽ���6���� ��e[ ��6�ñ� ü�� ���� ��e32�� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü������5 give " + p.getName() + " 32");
						Method.castLvup(p); break;
					}
				}
			}
				
			else if (commandLabel.equalsIgnoreCase("�ڽ���������")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(5) + 1;
						
					switch (RandomAmount) {
					case 1:
						p.sendMessage("��6�����մϴ�! ��c���� ���� �ڽ���6���� ��e[ ��6�ϱ� ���� ���� ��e32�� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������1 give " + p.getName() + " 32");
						Method.castLvup(p); break;
							
					case 2:
						p.sendMessage("��6�����մϴ�! ��c���� ���� �ڽ���6���� ��e[ ��6�߱� ���� ���� ��e32�� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������2 give " + p.getName() + " 32");
						Method.castLvup(p); break;
							
					case 3:
						p.sendMessage("��6�����մϴ�! ��c���� ���� �ڽ���6���� ��e[ ��6��� ���� ���� ��e32�� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������3 give " + p.getName() + " 32");
						Method.castLvup(p); break;
							
					case 4:
						p.sendMessage("��6�����մϴ�! ��c���� ���� �ڽ���6���� ��e[ ��6�ֻ�� ���� ���� ��e32�� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������4 give " + p.getName() + " 32");
						Method.castLvup(p); break;
							
					case 5:
						p.sendMessage("��6�����մϴ�! ��c���� ���� �ڽ���6���� ��e[ ��6�ñ� ���� ���� ��e32�� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������5 give " + p.getName() + " 32");
						Method.castLvup(p); break;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�ڽ�������")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(7) + 1;
						
					switch (RandomAmount) {
					case 1:
						p.sendMessage("��6�����մϴ�! ��c���� �� �ڽ� ��6���� ��e[ ��c�� ��6�Ӽ� �� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ҼӼ� give " + p.getName() + " 1");
						Method.castLvup(p); break;
							
					case 2:
						p.sendMessage("��6�����մϴ�! ��c���� �� �ڽ� ��6���� ��e[ ��b�ٶ� ��6�Ӽ� �� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ٶ��Ӽ� give " + p.getName() + " 1");
						Method.castLvup(p); break;
							
					case 3:
						p.sendMessage("��6�����մϴ�! ��c���� �� �ڽ� ��6���� ��e[ ��dġ�� ��6�Ӽ� �� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ġ���Ӽ� give " + p.getName() + " 1");
						Method.castLvup(p); break;
							
					case 4:
						p.sendMessage("��6�����մϴ�! ��c���� �� �ڽ� ��6���� ��e[ ��7��l��� ��6�Ӽ� �� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��ҼӼ� give " + p.getName() + " 1");
						Method.castLvup(p); break;
							
					case 5:
						p.sendMessage("��6�����մϴ�! ��c���� �� �ڽ� ��6���� ��e[ ��a�� ��6�Ӽ� �� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���Ӽ� give " + p.getName() + " 1");
						Method.castLvup(p); break;
						
					case 6:
						p.sendMessage("��6�����մϴ�! ��c���� �� �ڽ� ��6���� ��e[ ��e���� ��6�Ӽ� �� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���мӼ� give " + p.getName() + " 1");
						Method.castLvup(p); break;
					
					case 7:
						p.sendMessage("��6�����մϴ�! ��c���� �� �ڽ� ��6���� ��e[ ��3���� ��6�Ӽ� �� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �����Ӽ� give " + p.getName() + " 1");
						Method.castLvup(p); break;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�ڽ�����")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(60) + 1;
					
					if (RandomAmount == 1) {
						Bukkit.broadcastMessage("��f[��4�˸���f] " + p.getName() + " ���� �Ŵ��� ���ھ��� ��÷�Ǽ̽��ϴ�!");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������8 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 2 && RandomAmount <= 3) {
						p.sendMessage("��6�����մϴ�! ����� ��c������ ���� ȭä��6�� ��c��÷��6�Ǽ̽��ϴ�!");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������7 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 4 && RandomAmount <= 7) {
						p.sendMessage("��6�����մϴ�! ����� ��c������ �����ٱ� ������6�� ��c��÷��6�Ǽ̽��ϴ�!");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������6 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}
					
					else if (RandomAmount >= 8 && RandomAmount <= 10) {
						p.sendMessage("��6�����մϴ�! ����� ��c������ �������� ���ھ���6�� ��c��÷��6�Ǽ̽��ϴ�!");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��������5 give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}

					else if (RandomAmount >= 11 && RandomAmount <= 20) {
						p.sendMessage("��6�����մϴ�! ����� ��c���޶��� ��3����6�� ��c��÷��6�Ǽ̽��ϴ�!");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "give " + p.getName() + " 133 3");
						Method.castLvup(p);
						return false;
					}
					
					else {
						p.sendMessage("��c�ڽ��� �����ߴ��� �ƹ��͵� ������ �ʾҴ�.");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�ڽ��εμ����ǻ�����")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(100) + 1;
					
					if (RandomAmount >= 1 && RandomAmount <= 15) {
						p.sendMessage("��6�����մϴ�! ��c�εμ����� ���� ������6���� ��e[ ��c�εμ����� ���� ���� ��e] ��6�� ���Խ��ϴ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �εμ����ǻ������� give " + p.getName() + " 1");
						Method.castLvup(p);
						return false;
					}

					else {
						int RandomRkfn = new Random().nextInt(5) + 1;
						p.sendMessage("��c�εμ����� ���� ������6�� �����ߴ��� ��e[ ��c�εμ����� ���� ���� ��e" + RandomRkfn + "����e ] ��6�� ���Դ�.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �εμ����ǻ����� give " + p.getName() + " " + RandomRkfn);
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�ڽ�������ȭ�ֹ���")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(7) + 1;
					
					p.sendMessage("��6�����մϴ�! ��c���� ��ȭ �ֹ��� �ڽ���6���� ��e[ ��6" + RandomAmount + "�� ��ȭ �ֹ��� ��e] X32 ��6�� ���Խ��ϴ�.");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��ȭ��" + RandomAmount + " give " + p.getName() + " 32");
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
			
			else if (commandLabel.equalsIgnoreCase("���ǽ���")) {
				if (p.hasPermission("*")) {
					int RandomAmount = new Random().nextInt(6000000) + 1;
					main.economy.depositPlayer(p.getName(), RandomAmount);
					p.sendMessage("��c���ǡ�6�� �ܾ� ��c" + RandomAmount + "��6 ���� ȹ���ϼ̽��ϴ�.");
					Method.castLvup(p);
					return false;
				}
			}
			
			try {
				if (commandLabel.equalsIgnoreCase("����")) {
					if (p.hasPermission("11111.warp")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��� give " + p.getName() + " 1");
						p.sendMessage("��c��� 1����6�� �����ص�Ƚ��ϴ�.");
					}
					
					else if (p.hasPermission("77777.warp")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �������� give " + p.getName() + " 1");
						p.sendMessage("��c�������� 1����6�� �����ص�Ƚ��ϴ�.");
					}
					
					else if (p.hasPermission("33333.warp")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ������ give " + p.getName() + " 1");
						p.sendMessage("��c������ 1����6�� �����ص�Ƚ��ϴ�.");
					}
					
					else if (p.hasPermission("99999.warp")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �����尩 give " + p.getName() + " 1");
						p.sendMessage("��c�����尩 1����6�� �����ص�Ƚ��ϴ�.");
					}
					
					else if (p.hasPermission("55555.warp")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ܰ� give " + p.getName() + " 1");
						p.sendMessage("��c�ܰ� 1����6�� �����ص�Ƚ��ϴ�.");
					}
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("��6/���� ��f- �ڽ��� ������ �´� ���⸦ ���޹޽��ϴ�.");
				return false;
			}
			
			if (commandLabel.equalsIgnoreCase("ī����")) {
				if (p.hasPermission("*")) {
					p.chat("/�÷����θ�ɾ� ī����");
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�߰�����")) {
				if (p.hasPermission("*")) {
					Method.addPotion(p, PotionEffectType.NIGHT_VISION, 1000000, 3);
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�̵�����")) {
				if (p.hasPermission("*")) {
					String name = sender.getName();
					RpgPlayer player = Rpg.getRpgPlayera(name);
				      
					if (args[0].equalsIgnoreCase("��Ż1")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-1 " + p.getName());
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("��Ż2")) {
						if (player.getRpgLevel() > 19) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-2 " + p.getName());
								
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
								p.sendMessage("��6���� ��c��Ż �̵� ��Ÿ�� ��e" + main.DunTime.get(p.getName()) + "��6�� ���ҽ��ϴ�.");
								return false;
							}
						} else {
							p.sendMessage("��6[ �������� 20 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("��Ż3")) {
						if (player.getRpgLevel() > 39) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-3 " + p.getName());
								
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
								p.sendMessage("��6���� ��c��Ż �̵� ��Ÿ�� ��e" + main.DunTime.get(p.getName()) + "��6�� ���ҽ��ϴ�.");
								return false;
							}
						} else {
							p.sendMessage("��6[ �������� 40 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("��Ż4")) {
						if (player.getRpgLevel() > 59) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-4 " + p.getName());
								
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
								p.sendMessage("��6���� ��c��Ż �̵� ��Ÿ�� ��e" + main.DunTime.get(p.getName()) + "��6�� ���ҽ��ϴ�.");
								return false;
							}
						} else {
							p.sendMessage("��6[ �������� 60 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("��Ż5")) {
						if (player.getRpgLevel() > 79) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-5 " + p.getName());
								
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
								p.sendMessage("��6���� ��c��Ż �̵� ��Ÿ�� ��e" + main.DunTime.get(p.getName()) + "��6�� ���ҽ��ϴ�.");
								return false;
							}
						} else {
							p.sendMessage("��6[ �������� 80 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("��Ż6")) {
						if (player.getRpgLevel() > 99) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-6 " + p.getName());
								
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
								p.sendMessage("��6���� ��c��Ż �̵� ��Ÿ�� ��e" + main.DunTime.get(p.getName()) + "��6�� ���ҽ��ϴ�.");
								return false;
							}
						} else {
							p.sendMessage("��6[ �������� 100 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("��Ż7")) {
						if (player.getRpgLevel() > 119) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-7 " + p.getName());
								
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
								p.sendMessage("��6���� ��c��Ż �̵� ��Ÿ�� ��e" + main.DunTime.get(p.getName()) + "��6�� ���ҽ��ϴ�.");
								return false;
							}
						} else {
							p.sendMessage("��6[ �������� 120 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("��Ż8")) {
						if (player.getRpgLevel() > 139) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-8 " + p.getName());
								
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
								p.sendMessage("��6���� ��c��Ż �̵� ��Ÿ�� ��e" + main.DunTime.get(p.getName()) + "��6�� ���ҽ��ϴ�.");
								return false;
							}
						} else {
							p.sendMessage("��6[ �������� 140 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("��Ż9")) {
						if (player.getRpgLevel() > 159) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-9 " + p.getName());
								
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
								p.sendMessage("��6���� ��c��Ż �̵� ��Ÿ�� ��e" + main.DunTime.get(p.getName()) + "��6�� ���ҽ��ϴ�.");
								return false;
							}
						} else {
							p.sendMessage("��6[ �������� 160 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("��Ż10")) {
						if (player.getRpgLevel() > 179) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-10 " + p.getName());
								
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
								p.sendMessage("��6���� ��c��Ż �̵� ��Ÿ�� ��e" + main.DunTime.get(p.getName()) + "��6�� ���ҽ��ϴ�.");
								return false;
							}
						} else {
							p.sendMessage("��6[ �������� 180 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("��Ż11")) {
						if (player.getRpgLevel() > 199) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-11 " + p.getName());
								
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
								p.sendMessage("��6���� ��c��Ż �̵� ��Ÿ�� ��e" + main.DunTime.get(p.getName()) + "��6�� ���ҽ��ϴ�.");
								return false;
							}
						} else {
							p.sendMessage("��6[ �������� 200 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("��Ż12")) {
						if (player.getRpgLevel() > 219) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-12 " + p.getName());
								
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
								p.sendMessage("��6���� ��c��Ż �̵� ��Ÿ�� ��e" + main.DunTime.get(p.getName()) + "��6�� ���ҽ��ϴ�.");
								return false;
							}
						} else {
							p.sendMessage("��6[ �������� 220 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("��Ż13")) {
						if (player.getRpgLevel() > 239) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-13 " + p.getName());
								
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
								p.sendMessage("��6���� ��c��Ż �̵� ��Ÿ�� ��e" + main.DunTime.get(p.getName()) + "��6�� ���ҽ��ϴ�.");
								return false;
							}
						} else {
							p.sendMessage("��6[ �������� 240 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("��Ż14")) {
						if (player.getRpgLevel() > 259) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-14 " + p.getName());
								
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
								p.sendMessage("��6���� ��c��Ż �̵� ��Ÿ�� ��e" + main.DunTime.get(p.getName()) + "��6�� ���ҽ��ϴ�.");
								return false;
							}
						} else {
							p.sendMessage("��6[ �������� 260 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("��Ż15")) {
						if (player.getRpgLevel() > 279) {
							if (main.DunCool.get(p.getName()) == null) {
								main.DunTime.put(p.getName(), 3);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-15 " + p.getName());
								
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
								p.sendMessage("��6���� ��c��Ż �̵� ��Ÿ�� ��e" + main.DunTime.get(p.getName()) + "��6�� ���ҽ��ϴ�.");
								return false;
							}
						} else {
							p.sendMessage("��6[ �������� 280 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�ڻ�")) {
				if (p.hasPermission("[����]") || p.hasPermission("[������]") || p.hasPermission("[�ü�]") || p.hasPermission("[������]") || p.hasPermission("[����]")) {
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
						p.sendMessage("��6���� ��c�ڻ� ��Ÿ�� ��e" + main.SuicideTime.get(p.getName()) + "��6�� ���ҽ��ϴ�.");
						return false;
					}
				} else {
					p.sendMessage("��c����� ������ �����ϴ�.");
					return false;
				}	
			}
			
			else if (commandLabel.equalsIgnoreCase("��������")) {
				if (p.hasPermission("*")) {
					if (args[0].equalsIgnoreCase("���Ĺ����𵥵�")) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							if (DunzeonAPI.inDunzeonArea(player.getLocation(), "���Ĺ����𵥵�")) {
								main.economy.depositPlayer(player.getName(), 50000);
								player.sendMessage("��c���Ĺ��� �𵥵��6 ������ óġ�Ͽ� ��c50000��6 ���� ȹ���ϼ̽��ϴ�.");
				            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���Ĺ����𵥵�ڽ� give " + player.getName() + " 1");
							}
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("�����ѽ��̷���")) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							if (DunzeonAPI.inDunzeonArea(player.getLocation(), "�����ѽ��̷���")) {
								main.economy.depositPlayer(player.getName(), 50000);
								player.sendMessage("��c������ ���̷����6 ������ óġ�Ͽ� ��c50000��6 ���� ȹ���ϼ̽��ϴ�.");
				            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �����ѽ��̷���ڽ� give " + player.getName() + " 1");
							}
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("��ȭ������")) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							if (DunzeonAPI.inDunzeonArea(player.getLocation(), "��ȭ������")) {
								main.economy.depositPlayer(player.getName(), 50000);
								player.sendMessage("��c��ȭ�� ������6 ������ óġ�Ͽ� ��c50000��6 ���� ȹ���ϼ̽��ϴ�.");
				            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��ȭ�������ڽ� give " + player.getName() + " 1");
							}
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("�����")) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							if (DunzeonAPI.inDunzeonArea(player.getLocation(), "�����")) {
								main.economy.depositPlayer(player.getName(), 50000);
								player.sendMessage("��c�������6 ������ óġ�Ͽ� ��c50000��6 ���� ȹ���ϼ̽��ϴ�.");
				            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ������ڽ� give " + player.getName() + " 1");
							}
						} return false;
					}
					
					else if (args[0].equalsIgnoreCase("�εμ���")) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							if (DunzeonAPI.inDunzeonArea(player.getLocation(), "�εμ���")) {
								main.economy.depositPlayer(player.getName(), 400000);
								player.sendMessage("��c�εμ����6 ������ óġ�Ͽ� ��c400000��6 ���� ȹ���ϼ̽��ϴ�.");
				            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �εμ����ǻ����� give " + player.getName() + " 1");
							}
						} return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("��������")) {
				if (p.hasPermission("*")) {
					if (Method.getInfoBoolean(p, "����") == false) {
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
						Method.setInfoBoolean(p, "����", true);
						
						Bukkit.broadcastMessage("��e----------------------------------------------------------------");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("��6�������ּ���! ��e[ " + p.getName() + " ] ��6����");
						Bukkit.broadcastMessage("��6300������ ������ �޼��ϼ̽��ϴ�!");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("��e----------------------------------------------------------------");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ���� give " + p.getName() + " 1");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ������ȭ�ֹ��� give " + p.getName() + " 5");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ü�����ǹڽ� give " + p.getName() + " 10");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �������ǹڽ� give " + p.getName() + " 10");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����10 give " + p.getName() + " 2");
						
						p.setOp(false);
						if (p.hasPermission("[����]")) {
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/Īȣ �߰� " + p.getName() + " &f[&cWarrior&eMaster&f]");
							Method.removeOP(p, is);
						}
						
						else if (p.hasPermission("[�ü�]")) {
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/Īȣ �߰� " + p.getName() + " &f[&6Archer&eMaster&f]");
							Method.removeOP(p, is);
						}
						
						else if (p.hasPermission("[����]")) {
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/Īȣ �߰� " + p.getName() + " &f[&9Rogue&eMaster&f]");
							Method.removeOP(p, is);
						}
						
						else if (p.hasPermission("[������]")) {
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/Īȣ �߰� " + p.getName() + " &f[&dWitch&eMaster&f]");
							Method.removeOP(p, is);
						}
						
						else if (p.hasPermission("[������]")) {
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/Īȣ �߰� " + p.getName() + " &f[&aFighter&eMaster&f]");
							Method.removeOP(p, is);
						}
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("�����ʱ�ȭ")) {
				if (p.hasPermission("*")) {
					if (!Method.getInfoString(p, "����").equals("NONE")) {
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
						Method.setInfoString(p, "����", "NONE");
						Method.setInfoInt(p, "���� ī��Ʈ", 0);
						p.sendMessage("��6���������� ��c���� �ʱ�ȭ��6�� ���ƽ��ϴ�.");
						mf.delete();

						Method.PlayerManuadd(p.getName(), "�����ʱ�ȭ");
						
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp �������ð� " + p.getName());
						return false;
					} else {
						p.sendMessage("����� ������ ���� �����Դϴ�.");
						return false;
					}
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("����Ż")) {
				if (p.hasPermission("*")) {
					String name = sender.getName();
					RpgPlayer player = Rpg.getRpgPlayera(name);
					
					if (args[0].equalsIgnoreCase("2")) {
						if (player.getRpgLevel() > 19) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-2 " + p.getName());
							return false;
						} else {
							p.sendMessage("��6[ �������� 20 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("3")) {
						if (player.getRpgLevel() > 39) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-3 " + p.getName());
							return false;
						} else {
							p.sendMessage("��6[ �������� 40 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("4")) {
						if (player.getRpgLevel() > 59) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-4 " + p.getName());
							return false;
						} else {
							p.sendMessage("��6[ �������� 60 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("5")) {
						if (player.getRpgLevel() > 79) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-5 " + p.getName());
							return false;
						} else {
							p.sendMessage("��6[ �������� 80 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("6")) {
						if (player.getRpgLevel() > 99) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-6 " + p.getName());
							return false;
						} else {
							p.sendMessage("��6[ �������� 100 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("7")) {
						if (player.getRpgLevel() > 119) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-7 " + p.getName());
							return false;
						} else {
							p.sendMessage("��6[ �������� 120 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("8")) {
						if (player.getRpgLevel() > 139) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-8 " + p.getName());
							return false;
						} else {
							p.sendMessage("��6[ �������� 140 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("9")) {
						if (player.getRpgLevel() > 159) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-9 " + p.getName());
							return false;
						} else {
							p.sendMessage("��6[ �������� 160 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("10")) {
						if (player.getRpgLevel() > 179) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-10 " + p.getName());
							return false;
						} else {
							p.sendMessage("��6[ �������� 180 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("11")) {
						if (player.getRpgLevel() > 199) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-11 " + p.getName());
							return false;
						} else {
							p.sendMessage("��6[ �������� 200 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("12")) {
						if (player.getRpgLevel() > 219) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-12 " + p.getName());
							return false;
						} else {
							p.sendMessage("��6[ �������� 220 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("13")) {
						if (player.getRpgLevel() > 239) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-13 " + p.getName());
							return false;
						} else {
							p.sendMessage("��6[ �������� 240 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("14")) {
						if (player.getRpgLevel() > 259) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-14 " + p.getName());
							return false;
						} else {
							p.sendMessage("��6[ �������� 260 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("15")) {
						if (player.getRpgLevel() > 279) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp ����-15 " + p.getName());
							return false;
						} else {
							p.sendMessage("��6[ �������� 280 ] ��c����� ���� ��e���� ������c�� ������ �� �����ϴ�.");
							return false;
						}
					}
				}
			}
		} else {
			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + 
							   ChatColor.WHITE + "]" + ChatColor.RED + " �ֿܼ��� ������ �Ұ��� �մϴ�.");
			return false;
		}
		
		return false;
	}

	public static void displayHelp1(CommandSender sender)
	{
		sender.sendMessage(" ��e---- ��6�׼� RPG ��e-- ��6������ ��c1��6/��c3 ��e----");
		sender.sendMessage("��6/Ȯ���� ��f- Ȯ���⸦ �����մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/�̺��� ��f- �̺��� Ȯ���⸦ �����մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/Ʃ�丮��ħ ��f- Ʃ�丮���� ��ġ�� ��ɾ��Դϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/��������GUI ��f- ���� GUI�� �����մϴ�.");
		sender.sendMessage("��6/���Ǿ˰���<1~5> ��f- ���� ȿ���� �ݴϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/�������������� ��f- �г��� ������ �����մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/�������������� ��f- �г��� ������ �����մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/����<1/10/40/60> ��f- ������ �ݴϴ�. ��b( rpgitem )");
		sender.sendMessage("");
		sender.sendMessage("��6��ɾ� ��cAR 2 ��6�� �ļ� ������������ �Ѿ����.");
	}
	
	private void displayHelp2(CommandSender sender)
	{
		sender.sendMessage(" ��e---- ��6�׼� RPG ��e-- ��6������ ��c2��6/��c3 ��e----");
		sender.sendMessage("��6/�ڽ�<���Ĺ����𵥵�/�����/�����ѽ��̷���/��ȭ������/��������/������ȭ�ֹ���/ü������/��������/�εμ����ǻ�����/����/������> ��f- �ڽ� ���� ȿ���� �ݴϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/<1~15>�ܰ���ť�� ��f- ��� ť�� ���� ȿ���� �ݴϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/LvUP ��f- �������� ȿ���� �ݴϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/���ǽ��� ��f- ������ ������ ȿ���� �ݴϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/���Ǿ˸� ��f- ���ǰ� �� ���� �˸��ϴ�.");
		sender.sendMessage("��6/���� ��f- �ڽ��� ������ �´� ���⸦ ���޹޽��ϴ�.");
		sender.sendMessage("��6/ī���� ��f- ī���� ���޼ҷ� �̵��մϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/�߰����� ��f- �����ڿ��� �߰����ø� �ݴϴ�.");
		sender.sendMessage("��6/�������� <�����̸�> ��f- ���� ȿ���� ���ϴ�.");
		sender.sendMessage("��6/�̵����� ��Ż<1~15> ��f- �Ϲ� ���� ��ũ�� ��Ż �̵� ȿ���� ���ϴ�.");
		sender.sendMessage("");
		sender.sendMessage("��6��ɾ� ��cAR 3 ��6�� �ļ� ������������ �Ѿ����.");
	}
	
	private void displayHelp3(CommandSender sender)
	{
		sender.sendMessage(" ��e---- ��6�׼� RPG ��e-- ��6������ ��c3��6/��c3 ��e----");
		sender.sendMessage("��6/�������� <���Ĺ����𵥵�/�����ѽ��̷���/��ȭ������/�����/�εμ���> ��f- ����Ʈ�� �ִ� �÷��̾�� ������ �ݴϴ�.");
		sender.sendMessage("��6/�������� ��f- ���� ������ �ݴϴ�.");
		sender.sendMessage("��6/�εμ������ <��Ȱ/����1/����2/�����ʱ�ȭ> ��f- �εμ��翡 ���� ��ɾ��Դϴ�.");
		sender.sendMessage("��6/�����ʱ�ȭ ��f- ������ �ʱ�ȭ ��ŵ�ϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/�������� ��f- ���� �ʱ�ȭ ������ ���޹޽��ϴ�. ��b( rpgitem )");
		sender.sendMessage("��6/����Ż <1~15> ��f- �� ��Ż �̵���ɾ� ȿ���� ���ϴ�.");
		sender.sendMessage("��6/ǥ���� <1~4> <����> ��f- ǥ���� ������ �����մϴ�");
		sender.sendMessage("��6/in ��f- ������ �̸��� ���ϴ�.");
		sender.sendMessage("��6/gp ��f- ��ǥ�� ���ϴ�.");
		sender.sendMessage("��6/<�����/�𵥵�/����/�ε�/���̷���>Ȯ�� ��f- ������ �׾����� ��������� Ȯ���մϴ�.");
		sender.sendMessage("��6/<�����/�𵥵�/����/�ε�/���̷���>���� ��f- ������ �罺�� ��ŵ�ϴ�.");
	}
}
