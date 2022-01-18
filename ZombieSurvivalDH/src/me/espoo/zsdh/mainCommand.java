package me.espoo.zsdh;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


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
			if(commandLabel.equalsIgnoreCase("zs") || commandLabel.equalsIgnoreCase("����")) {
				if(args.length == 0) {
					displayHelp(sender);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("����") && args.length == 2)
				{
					File f = new File("plugins/ZombieSurvivalDH/Player/" + args[1] + ".yml");
					if (!f.exists()) {
						sender.sendMessage("�ش� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					Player argp = Bukkit.getPlayer(args[1]);
					
					if (sender.isOp()) {
						sender.sendMessage("��6-----------------------------------------------------------------");
						sender.sendMessage("��e * " + args[1] + " ��6���� ��cų ��6or ��c���� ��6����");
						sender.sendMessage("��e ������ ��a������ ��6KILL ��7:: ��f" + Method.getInfoInt(argp, "HumanKill"));
						sender.sendMessage("��e ������ ��a������ ��6DEATH ��7:: ��f" + Method.getInfoInt(argp, "HumanDeath"));
						sender.sendMessage("��e ������ ��c������ ��6KILL ��7:: ��f" + Method.getInfoInt(argp, "ZombieKill"));
						sender.sendMessage("��e ������ ��c������ ��6DEATH ��7:: ��f" + Method.getInfoInt(argp, "ZombieDeath"));
						sender.sendMessage("��6-----------------------------------------------------------------");
						return false;
					}
					
					else if (Method.getInfoBoolean(argp, "Seehandr") == true)
					{
						sender.sendMessage("��6-----------------------------------------------------------------");
						sender.sendMessage("��e * " + args[1] + " ��6���� ��cų ��6or ��c���� ��6����");
						sender.sendMessage("��e ������ ��a������ ��6KILL ��7:: ��f" + Method.getInfoInt(argp, "HumanKill"));
						sender.sendMessage("��e ������ ��a������ ��6DEATH ��7:: ��f" + Method.getInfoInt(argp, "HumanDeath"));
						sender.sendMessage("��e ������ ��c������ ��6KILL ��7:: ��f" + Method.getInfoInt(argp, "ZombieKill"));
						sender.sendMessage("��e ������ ��c������ ��6DEATH ��7:: ��f" + Method.getInfoInt(argp, "ZombieDeath"));
						sender.sendMessage("��6-----------------------------------------------------------------");
						return false;
					} else {
						sender.sendMessage("��c������ ���� ���⸦ �㰡���� �ʾҽ��ϴ�.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("����"))
				{
					int one = Method.getRankingI("one");
					int two = Method.getRankingI("two");
					int three = Method.getRankingI("three");
					int four = Method.getRankingI("four");
					int five = Method.getRankingI("five");
					String oneName = Method.getRankingS("oneName");
					String twoName = Method.getRankingS("twoName");
					String threeName = Method.getRankingS("threeName");
					String fourName = Method.getRankingS("fourName");
					String fiveName = Method.getRankingS("fiveName");
					
					if (one == 0 && two == 0 && three == 0 && four == 0 && five == 0)
					{
						sender.sendMessage("��e---------------------------------------------------");
						sender.sendMessage("��f ���� ��ũ�� ���� ����� �������� �ʽ��ϴ�.");
						sender.sendMessage("��e---------------------------------------------------");
						return false;
					}
					
					sender.sendMessage("��e---------------------------------------------------");
					if (oneName.equals("NONE") == false) sender.sendMessage("��6 �� ��cRANK 1�� ��7::��f " + oneName + " / " + one);
					if (twoName.equals("NONE") == false) sender.sendMessage("��6 ���� ��cRANK 2�� ��7::��f " + twoName + " / " + two);
					if (threeName.equals("NONE") == false) sender.sendMessage("��6 ������ ��cRANK 3�� ��7::��f " + threeName + " / " + three);
					if (fourName.equals("NONE") == false) sender.sendMessage("��6 �������� ��cRANK 4�� ��7::��f " + fourName + " / " + four);
					if (fiveName.equals("NONE") == false) sender.sendMessage("��6 ���������� ��cRANK 5�� ��7::��f " + fiveName + " / " + five);
					sender.sendMessage("��e---------------------------------------------------");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("��޵�"))
				{
					sender.sendMessage("��6---------------------------------");
					sender.sendMessage("��e * ��7F ��f[��e5ų��f]-> ��eE");
					sender.sendMessage("��e * ��eE ��f[��e30ų��f]-> ��6D");
					sender.sendMessage("��e * ��6D ��f[��e60ų��f]-> ��bC");
					sender.sendMessage("��e * ��bC ��f[��e120ų��f]-> ��dB");
					sender.sendMessage("��e * ��dB ��f[��e300ų��f]-> ��cA");
					sender.sendMessage("��e * ��cA ��f[��e500ų��f]-> ��4S");
					sender.sendMessage("��e * ��4S ��f[��e800ų��f]-> ��4SS");
					sender.sendMessage("��6---------------------------------");
					return false;
				}

				else if (args[0].equalsIgnoreCase("����"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!(args.length == 5))
					{
						sender.sendMessage("��6/���� <����/�߰�/����> <�г���> <��> <K/D> <Z/H> ��f- �÷��̾��� ������/�������� ų/���� �����մϴ�.");
						return false;
					}
					
					File f = new File("plugins/ZombieSurvivalDH/Player/" + args[1] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("��c�� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
						return false;
					}
					
					if (args[4].equalsIgnoreCase("Z"))
					{
						if (args[3].equalsIgnoreCase("K"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							Method.setInfoInt(argsP, "ZombieKill", Integer.parseInt(args[2]));
							sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ������ ų ���� ��c" + args[2] + " ��6��ŭ �����Ͽ����ϴ�.");
							Method.RankingStat(argsP);
							return false;
						}
						
						if (args[1].equalsIgnoreCase("D"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							Method.setInfoInt(argsP, "ZombieDeath", Integer.parseInt(args[2]));
							sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ������ ��� ���� ��c" + args[2] + " ��6��ŭ �����Ͽ����ϴ�.");
							Method.RankingStat(argsP);
							return false;
						}
					}
					
					if (args[4].equalsIgnoreCase("H"))
					{
						if (args[3].equalsIgnoreCase("K"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							Method.setInfoInt(argsP, "HumanKill", Integer.parseInt(args[2]));
							sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ������ ų ���� ��c" + args[2] + " ��6��ŭ �����Ͽ����ϴ�.");
							Method.RankingStat(argsP);
							return false;
						}
						
						if (args[1].equalsIgnoreCase("D"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							Method.setInfoInt(argsP, "HumanDeath", Integer.parseInt(args[2]));
							sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ������ ��� ���� ��c" + args[2] + " ��6��ŭ �����Ͽ����ϴ�.");
							Method.RankingStat(argsP);
							return false;
						}
					}
					
					else
					{
						sender.sendMessage("��6/���� <����/�߰�/����> <�г���> <��> <K/D> <Z/H>");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("�߰�"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!(args.length == 5))
					{
						sender.sendMessage("��6/���� <����/�߰�/����> <�г���> <��> <K/D> <Z/H> ��f- �÷��̾��� ������/�������� ų/���� �����մϴ�.");
						return false;
					}
					
					File f = new File("plugins/ZombieSurvivalDH/Player/" + args[1] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("��c�� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
						return false;
					}
					
					if (args[4].equalsIgnoreCase("Z"))
					{
						if (args[3].equalsIgnoreCase("K"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							int ZK = Method.getInfoInt(argsP, "ZombieKill") + Integer.parseInt(args[2]);
							Method.setInfoInt(argsP, "ZombieKill", ZK);
							sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ������ ų ���� ��c" + args[2] + " ��6��ŭ �߰��Ͽ����ϴ�.");
							Method.RankingStat(argsP);
							return false;
						}
						
						if (args[1].equalsIgnoreCase("D"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							int ZK = Method.getInfoInt(argsP, "ZombieDeath") + Integer.parseInt(args[2]);
							Method.setInfoInt(argsP, "ZombieDeath", ZK);
							sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ������ ��� ���� ��c" + args[2] + " ��6��ŭ �߰��Ͽ����ϴ�.");
							Method.RankingStat(argsP);
							return false;
						}
					}
					
					if (args[4].equalsIgnoreCase("H"))
					{
						if (args[3].equalsIgnoreCase("K"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							int ZK = Method.getInfoInt(argsP, "HumanKill") + Integer.parseInt(args[2]);
							Method.setInfoInt(argsP, "HumanKill", ZK);
							sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ������ ų ���� ��c" + args[2] + " ��6��ŭ �߰��Ͽ����ϴ�.");
							Method.RankingStat(argsP);
							return false;
						}
						
						if (args[1].equalsIgnoreCase("D"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							int ZK = Method.getInfoInt(argsP, "HumanDeath") + Integer.parseInt(args[2]);
							Method.setInfoInt(argsP, "HumanDeath", ZK);
							sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ������ ��� ���� ��c" + args[2] + " ��6��ŭ �߰��Ͽ����ϴ�.");
							Method.RankingStat(argsP);
							return false;
						}
					}
					
					else
					{
						sender.sendMessage("��6/���� <����/�߰�/����> <�г���> <��> <K/D> <Z/H> ��f- �÷��̾��� ������/�������� ų/���� �����մϴ�.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("�߰�"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!(args.length == 5))
					{
						sender.sendMessage("��6/���� <����/�߰�/����> <�г���> <��> <K/D> <Z/H> ��f- �÷��̾��� ������/�������� ų/���� �����մϴ�.");
						return false;
					}
					
					File f = new File("plugins/ZombieSurvivalDH/Player/" + args[1] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("��c�� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
						return false;
					}
					
					if (args[4].equalsIgnoreCase("Z"))
					{
						if (args[3].equalsIgnoreCase("K"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							int ZK = Method.getInfoInt(argsP, "ZombieKill") - Integer.parseInt(args[2]);
							Method.setInfoInt(argsP, "ZombieKill", ZK);
							sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ������ ų ���� ��c" + args[2] + " ��6��ŭ �Q���ϴ�.");
							Method.RankingStat(argsP);
							return false;
						}
						
						if (args[1].equalsIgnoreCase("D"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							int ZK = Method.getInfoInt(argsP, "ZombieDeath") - Integer.parseInt(args[2]);
							Method.setInfoInt(argsP, "ZombieDeath", ZK);
							sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ������ ��� ���� ��c" + args[2] + " ��6��ŭ �Q���ϴ�.");
							Method.RankingStat(argsP);
							return false;
						}
					}
					
					if (args[4].equalsIgnoreCase("H"))
					{
						if (args[3].equalsIgnoreCase("K"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							int ZK = Method.getInfoInt(argsP, "HumanKill") - Integer.parseInt(args[2]);
							Method.setInfoInt(argsP, "HumanKill", ZK);
							sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ������ ų ���� ��c" + args[2] + " ��6��ŭ �Q���ϴ�.");
							Method.RankingStat(argsP);
							return false;
						}
						
						if (args[1].equalsIgnoreCase("D"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							int ZK = Method.getInfoInt(argsP, "HumanDeath") - Integer.parseInt(args[2]);
							Method.setInfoInt(argsP, "HumanDeath", ZK);
							sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ������ ��� ���� ��c" + args[2] + " ��6��ŭ �Q���ϴ�.");
							Method.RankingStat(argsP);
							return false;
						}
					}
					
					else
					{
						sender.sendMessage("��6/���� <����/�߰�/����> <�г���> <��> <K/D> <Z/H> ��f- �÷��̾��� ������/�������� ų/���� �����մϴ�.");
						return false;
					}
				}
					
					else if (args[0].equalsIgnoreCase("��������"))
					{
						if (!sender.isOp())
						{
							sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
							return false;
						}
						
						if (Method.getConfigBoolean("Start") == true) {
							File f = new File("plugins/ZombieSurvivalDH/StartList.yml");
							YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
							List<String> list = config.getStringList("Player");
							Method.setConfigBoolean("Start", false);
							Method.setConfigString("WarpName", "NONE");
							Method.delTeamList();
							main.sTimer = 120;
							main.StartTime = 30;
							main.Time = 4;
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage(main.prx + "��c�����ڡ�6�� �������� ������ ������ ������׽��ϴ�.");
							Bukkit.broadcastMessage("");
							Integer id = main.taskIds.remove("StartTimer");
							Bukkit.getServer().getScheduler().cancelTask(id);
							for (String la : list) {
								Player p = Bukkit.getPlayer(la);
								for (PotionEffect effect : p.getActivePotionEffects())
									p.removePotionEffect(effect.getType());
								Method.setInfoBoolean(p, "GameJoin", false);
								if (main.Gun.get(p.getName()) != null) main.Gun.remove(p.getName());
								p.getInventory().clear();
								p.setFoodLevel(20);
								p.setLevel(0);
								p.setExhaustion(0.0F);
								p.setExp(0.0F);
								p.setHealth(20);
								p.getInventory().setHelmet(null);
								p.getInventory().setBoots(null);
								p.getInventory().setChestplate(null);
								p.getInventory().setLeggings(null);
								if (!p.isOp()) {
									p.setOp(true);
									p.chat("/spawn");
									p.setOp(false);
								} else {
									p.chat("/spawn");
								}
							}
							return false;
						} else {
							sender.sendMessage("��c�̹� ������ ����Ǿ� �־� ������ �� �����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("��������"))
					{
						if (!sender.isOp())
						{
							sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
							return false;
						}
						
						if (Method.getConfigBoolean("Start") == false) {
							File f = new File("plugins/ZombieSurvivalDH/StartList.yml");
	        				YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
	        	    		List<String> list = config.getStringList("Player");
	        				if (list.size() > 2) {
								Method.Starting();
	        				} else {
	        					Bukkit.broadcastMessage("��c�ο� ���� �����մϴ�.");
	        					return false;
	        				}
						} else {
							sender.sendMessage("��c������ �������϶��� ���� ������ �Ұ����մϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("ī��Ʈ����"))
					{
						if (!sender.isOp())
						{
							sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
							return false;
						}
						
						if (Method.getConfigBoolean("Timersup") == false) {
							if (Method.getConfigBoolean("Start") == false) {
								Method.setConfigBoolean("Timersup", true);
								sender.sendMessage("��cī��Ʈ��6�� ���������� ��c������6�߽��ϴ�.");
							} else {
								sender.sendMessage("��c������ �������϶��� Ÿ�̸Ӹ� ������ ������ �� �����ϴ�.");
								return false;
							}
						} else {
							sender.sendMessage("��c�̹� Ÿ�̸Ӱ� ����Ǿ��ֽ��ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("ī��Ʈ����"))
					{
						if (!sender.isOp())
						{
							sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
							return false;
						}
						
						if (Method.getConfigBoolean("Timersup") == true) {
							if (Method.getConfigBoolean("Start") == false) {
								Method.setConfigBoolean("Timersup", false);
								sender.sendMessage("��cī��Ʈ��6�� ���������� ��c���ۡ�6�߽��ϴ�.");
								main.sTimer = 120;
							} else {
								sender.sendMessage("��c������ �������϶��� Ÿ�̸Ӹ� ������ ������ �� �����ϴ�.");
								return false;
							}
						} else {
							sender.sendMessage("��c�̹� Ÿ�̸Ӱ� ���۵Ǿ��ֽ��ϴ�.");
							return false;
						}
					}
				
				
				if (sender instanceof Player)
				{
					Player p = (Player) sender;
					
					if (args[0].equalsIgnoreCase("�±�"))
					{
						Method.addPlayerRank(p);
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("����") || args.length == 2)
					{
						if (args[1].equalsIgnoreCase("���"))
						{
							if (Method.getInfoBoolean(p, "Seehandr") == true)
							{
								sender.sendMessage("��c�̹� ���� ���⸦ ����ϼ̽��ϴ�.");
								return false;
							}
							  
							Method.setInfoBoolean(p, "Seehandr", true);
							sender.sendMessage("��6����� ������ ������ ���� ���� ��c����6�߽��ϴ�.");
							return false;
						}
						  
						else if (args[1].equalsIgnoreCase("�ź�"))
						{
							if (Method.getInfoBoolean(p, "Seehandr") == false)
							{
								sender.sendMessage("��c�̹� ���� ���⸦ �����ϼ̽��ϴ�.");
								return false;
							}
							  
							Method.setInfoBoolean(p, "Seehandr", false);
							sender.sendMessage("��6����� ������ ������ ���� ���� ��c�źΡ�6�߽��ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("����") && args.length == 1)
					{
						sender.sendMessage("��6-----------------------------------------------------------------");
						sender.sendMessage("��e * " + sender.getName() + " ��6���� ��cų ��6or ��c���� ��6����");
						sender.sendMessage("��e ������ ��a������ ��6KILL ��7:: ��f" + Method.getInfoInt(p, "HumanKill"));
						sender.sendMessage("��e ������ ��a������ ��6DEATH ��7:: ��f" + Method.getInfoInt(p, "HumanDeath"));
						sender.sendMessage("��e ������ ��c������ ��6KILL ��7:: ��f" + Method.getInfoInt(p, "ZombieKill"));
						sender.sendMessage("��e ������ ��c������ ��6DEATH ��7:: ��f" + Method.getInfoInt(p, "ZombieDeath"));
						sender.sendMessage("��6-----------------------------------------------------------------");
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("����"))
					{
						if (!main.permission.playerInGroup(Bukkit.getServer().getWorld("world"), p.getName(), "Ʃ�丮��") && !main.permission.playerInGroup(Bukkit.getServer().getWorld("world"), p.getName(), "Ʃ�丮����")) {
							if (Method.getConfigBoolean("Start") == false) {
								if (Method.getInfoBoolean(p, "Observer") == true) {
									Method.setInfoBoolean(p, "Observer", false);
									Method.setInfoBoolean(p, "GameJoin", false);
									Method.addStartList(p.getName());
									p.sendMessage(main.aprx + "��f���� �Ǻ��� ����� ��c���� �����̹� ��f���ӿ� �����մϴ�.");
								} else {
									p.sendMessage("��c����� �̹� ������ �����Դϴ�.");
									return false;
								}
							} else {
								if (Method.getInfoBoolean(p, "Observer") == true) {
									p.sendMessage(main.aprx + "��c���� �����߿� �����ڷ� �����ϼ̽��ϴ�!");
									Method.setInfoBoolean(p, "Observer", false);
									Method.setInfoBoolean(p, "GameJoin", true);
									String warpName = Method.getConfigString("WarpName");
							        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40000, 2, true));
							        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40000, 1, true));
									Method.setInfoBoolean(p, "GameJoin", true);
									if (Method.getTeamZombieList().contains(p.getName())) Method.subTeamZombie(p.getName());
									if (Method.getTeamHumanList().contains(p.getName())) Method.subTeamHuman(p.getName());
									if (main.Gun.get(p.getName()) == null) main.Gun.put(p.getName(), "true");
									Method.addStartList(p.getName());
									Method.addTeamZombie(p.getName());
									p.getInventory().setItem(0, new ItemStack(372, 1));
									p.getInventory().setHelmet(new ItemStack(91, 1));
										
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/warp " + warpName);
										p.setOp(false);
									} else {
										p.chat("/warp " + warpName);
									} return false;
								} else {
									p.sendMessage("��c����� �̹� �����Ǿ��ִ� �����Դϴ�.");
									return false;
								}
							}
						} else {
							p.sendMessage("��cƩ�丮���� ���� ��ġ�� �Է��� �ֽñ� �ٶ��ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("����"))
					{
						if (!main.permission.playerInGroup(Bukkit.getServer().getWorld("world"), p.getName(), "Ʃ�丮��") && !main.permission.playerInGroup(Bukkit.getServer().getWorld("world"), p.getName(), "Ʃ�丮����")) {
							if (Method.getConfigBoolean("Start") == false) {
								if (Method.getInfoBoolean(p, "Observer") == false) {
									Method.setInfoBoolean(p, "Observer", true);
									Method.setInfoBoolean(p, "GameJoin", false);
									Method.subStartList(p.getName());
									p.sendMessage(main.aprx + "��f����� �������� ��c���� �����̹� ��f���ӿ� �����մϴ�.");
								} else {
									p.sendMessage("��c����� �̹� �����Ǿ��ִ� �����Դϴ�.");
									return false;
								}
							} else {
								if (Method.getInfoBoolean(p, "Observer") == false) {
									p.sendMessage(main.aprx + "��c���� �����߿� �����̽��ϴ�!");
									Method.setInfoBoolean(p, "Observer", true);
									Method.setInfoBoolean(p, "GameJoin", false);
									Method.subStartList(p.getName());
									p.getInventory().clear();
									p.setFoodLevel(20);
									p.setLevel(0);
									p.setExhaustion(0.0F);
									p.setExp(0.0F);
									p.setHealth(20);
									p.getInventory().setHelmet(null);
									p.getInventory().setBoots(null);
									p.getInventory().setChestplate(null);
									p.getInventory().setLeggings(null);
									if (main.Gun.get(p.getName()) != null) main.Gun.remove(p.getName());
									for (PotionEffect effect : p.getActivePotionEffects())
										p.removePotionEffect(effect.getType());
									
									if (main.Zombie.contains(p)) {
										Method.subTeamZombie(p.getName());
										if (Method.getTeamZombie() <= 0) {
											Method.HWin();
										}
									}
									
									else if (Method.getTeamHumanList().contains(p.getName())) {
										Method.subTeamHuman(p.getName());
										if (Method.getTeamHuman() <= 0) {
											Method.ZWin();
										}
									}
									
									if (!p.isOp()) {
										p.setOp(true);
										p.chat("/spawn");
										p.setOp(false);
									} else {
										p.chat("/spawn");
									} return false;
								} else {
									p.sendMessage("��c����� �̹� �����Ǿ��ִ� �����Դϴ�.");
									return false;
								}
							} return false;
						} else {
							p.sendMessage("��cƩ�丮���� ���� ��ġ�� �Է��� �ֽñ� �ٶ��ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("��и�ɾ�"))
					{
						if (p.isOp()) {
							main.Gun.put(p.getName(), "true");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("��ź�и�ɾ�FSAI54"))
					{
						if (Method.getConfigBoolean("Start") == true) {
							p.sendMessage(main.aprx + "����� ��a��� ��f�� ����Ͽ� �����ڷ� ġ���Ǿ����ϴ�!");
							if (Method.getTeamZombieList().contains(p.getName())) Method.subTeamZombie(p.getName());
							if (Method.getTeamHumanList().contains(p.getName())) Method.subTeamHuman(p.getName());
							if (main.Gun.get(p.getName()) != null) main.Gun.remove(p.getName());
							for (PotionEffect effect : p.getActivePotionEffects())
								p.removePotionEffect(effect.getType());
							p.getInventory().clear();
							p.setFoodLevel(20);
							p.setLevel(0);
							p.setExhaustion(0.0F);
							p.setExp(0.0F);
							p.setHealth(20);
							p.getInventory().setHelmet(null);
							p.getInventory().setBoots(null);
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							Method.openGunGUI(p);
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 100.0f, 200.0f);
							Method.subTeamZombie(p.getName());
							Method.addTeamHuman(p.getName());
							if (Method.getTeamZombie() <= 0) {
								Method.HWinBackin();
								return false;
							} return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("�α��θ�ɾ�"))
					{
						if (p.isOp()) {
							p.performCommand("spawn");
							Method.subStartList(p.getName());
							
							for (PotionEffect effect : p.getActivePotionEffects())
								p.removePotionEffect(effect.getType());
							
							if (main.permission.playerInGroup(Bukkit.getServer().getWorld("world"), p.getName(), "Ʃ�丮��")) {
								p.performCommand("warp Ʃ�丮��");
								return false;
							}
							
							else if (main.permission.playerInGroup(Bukkit.getServer().getWorld("world"), p.getName(), "Ʃ�丮����")) {
								p.performCommand("warp Ʃ�丮��-6");
								return false;
							}
							
							if (Method.getInfoBoolean(p, "Observer") == false) {
								Method.addStartList(p.getName());
							}
							
							if (main.Gun.get(p.getName()) != null) {
								main.Gun.remove(p.getName());
							}
							
							p.getInventory().clear();
							p.setFoodLevel(20);
							p.setLevel(0);
							p.setExhaustion(0.0F);
							p.setExp(0.0F);
							p.setHealth(20);
							p.getInventory().setHelmet(null);
							p.getInventory().setBoots(null);
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							
							if (Method.getConfigBoolean("Start")) {
								if (Method.getTeamZombie() == 0) {
									if (Method.getInfoBoolean(p, "Observer") == false) {
										String warpName = Method.getConfigString("WarpName");
										Timer timer12 = new Timer();
										Date timeToRun2 = new Date(System.currentTimeMillis() + 1 * 1000);
										timer12.schedule(new TimerTask() {
											public void run() {
												p.sendMessage(main.aprx + "��6����� ��c���� Ÿ�̸� ��6�����߿� �����߽��ϴ�.");
												return;
											}
										}, timeToRun2);
										
										p.chat("/warp " + warpName);
									} return false;
								} else {
									if (Method.getInfoBoolean(p, "Observer") == false) {
										String warpName = Method.getConfigString("WarpName");
										Timer timer12 = new Timer();
										Date timeToRun2 = new Date(System.currentTimeMillis() + 1 * 1000);
										timer12.schedule(new TimerTask() {
											public void run() {
												p.sendMessage(main.aprx + "��6����� ��c���� ���� ��6�߿� �����߽��ϴ�.");
												return;
											}
										}, timeToRun2);
								        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40000, 2, true));
								        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40000, 1, true));
										Method.setInfoBoolean(p, "GameJoin", true);
										if (Method.getTeamZombieList().contains(p.getName())) Method.subTeamZombie(p.getName());
										if (Method.getTeamHumanList().contains(p.getName())) Method.subTeamHuman(p.getName());
										Method.addTeamZombie(p.getName());
										if (main.Gun.get(p.getName()) == null) main.Gun.put(p.getName(), "true");
										p.getInventory().setItem(0, new ItemStack(372, 1));
										p.getInventory().setHelmet(new ItemStack(91, 1));
											
										p.chat("/warp " + warpName);
									} return false;
								}
							}
							
							if (Method.getInfoBoolean(p, "GameJoin")) {
								if (!Method.getConfigBoolean("Start")) {
									Method.setInfoBoolean(p, "GameJoin", false);
									p.chat("/spawn");
								} return false;
							}
						}
					}
				}
			}
		} catch (NumberFormatException ex) {
			displayHelp(sender);
			return false;
		}
		
		try {
			if(commandLabel.equalsIgnoreCase("��")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (Method.getInfoBoolean(p, "Observer") == false) {
						if (Method.getConfigBoolean("Start")) {
							if (main.Gun.get(p.getName()) == null) {
								Method.openGunGUI(p);
								return false;
							} else {
								p.sendMessage("��c����� �̹� ���� ȹ���ϼ̽��ϴ�.");
							}
						} else {
							p.sendMessage("��c������ ����Ǿ������� ���� ȹ���Ͻ� �� �����ϴ�.");
						}
					} else {
						p.sendMessage("��c�����ϋ��� ���� ȹ���� �� �����ϴ�.");
					}
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("��6/�� ��f- ������ ���������� �Ǽ��� �� ȹ��â�� �ݾҴٸ� �ٽ� �����մϴ�.");
			return false;
		}
		return false;
	}
	
	private void displayHelp(CommandSender sender)
	{	
		sender.sendMessage(" ��e---- ��6���� �����̹� ��e-- ��6������ ��c1��6/��c1 ��e----");
		sender.sendMessage("��6/���� ��f- ���� �����̹� ��ɾ ���ϴ�.");
		sender.sendMessage("��6/���� ���� ��f- ������/������ ų ������ TOP 5���� ���ϴ�.");
		sender.sendMessage("��6/���� �±� ��f- �ڽź��� �Ѵܰ� ���� �ܰ�� �±��մϴ�.");
		sender.sendMessage("��6/���� ���� ��f- ���� �����̹��� �������� �ʽ��ϴ�. (����Ҷ� ����)");
		sender.sendMessage("��6/���� ���� ��f- ���� �����̹��� �����մϴ�. ��b( �⺻������ �ڵ����� )");
		sender.sendMessage("��6/���� ���� ��f- ���� �����̹��� �Ҷ��մϴ�.");
		sender.sendMessage("��6/���� ���� <�г���> ��f- �ٸ� ����� ų �� ������ ���ϴ�.");
		sender.sendMessage("��6/���� ��޵� ��f- ��ũ�� ��޵��� ���ϴ�.");
		sender.sendMessage("��6/���� ���� <���/�ź�> ��f- ���� �ڽ��� ������ ���� ���� �����մϴ�. ��b( �⺻�� ����� )");
		sender.sendMessage("��6/�� ��f- ������ ���������� �Ǽ��� �� ȹ��â�� �ݾҴٸ� �ٽ� �����մϴ�.");
		if (sender.isOp()) {
			sender.sendMessage("��6");
			sender.sendMessage("��b/���� �������� ��f- ���� �����̹� ������ ������ �����մϴ�.");
			sender.sendMessage("��b/���� �������� ��f- ���� �����̹� ������ ������ �����մϴ�.");
			sender.sendMessage("��b/���� ī��Ʈ���� ��f- 3�е��� ī��Ʈ�� ���� �����ϴ� Ÿ�̸Ӹ� �����մϴ�.");
			sender.sendMessage("��b/���� ī��Ʈ���� ��f- 3�е��� ī��Ʈ�� ���� �����ϴ� Ÿ�̸Ӹ� �����մϴ�.");
			sender.sendMessage("��b/���� <����/�߰�/����> <�г���> <��> <K/D> <Z/H> ��f- �÷��̾��� ������/�������� ų/���� �����մϴ�.");
		}
	}
}
