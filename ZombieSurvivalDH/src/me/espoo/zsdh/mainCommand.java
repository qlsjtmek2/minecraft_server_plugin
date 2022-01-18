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
			if(commandLabel.equalsIgnoreCase("zs") || commandLabel.equalsIgnoreCase("좀비")) {
				if(args.length == 0) {
					displayHelp(sender);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("전적") && args.length == 2)
				{
					File f = new File("plugins/ZombieSurvivalDH/Player/" + args[1] + ".yml");
					if (!f.exists()) {
						sender.sendMessage("해당 플레이어는 존재하지 않습니다.");
						return false;
					}
					Player argp = Bukkit.getPlayer(args[1]);
					
					if (sender.isOp()) {
						sender.sendMessage("§6-----------------------------------------------------------------");
						sender.sendMessage("§e * " + args[1] + " §6님의 §c킬 §6or §c데스 §6전적");
						sender.sendMessage("§e ┗━━ §a생존자 §6KILL §7:: §f" + Method.getInfoInt(argp, "HumanKill"));
						sender.sendMessage("§e ┗━━ §a생존자 §6DEATH §7:: §f" + Method.getInfoInt(argp, "HumanDeath"));
						sender.sendMessage("§e ┗━━ §c감염자 §6KILL §7:: §f" + Method.getInfoInt(argp, "ZombieKill"));
						sender.sendMessage("§e ┗━━ §c감염자 §6DEATH §7:: §f" + Method.getInfoInt(argp, "ZombieDeath"));
						sender.sendMessage("§6-----------------------------------------------------------------");
						return false;
					}
					
					else if (Method.getInfoBoolean(argp, "Seehandr") == true)
					{
						sender.sendMessage("§6-----------------------------------------------------------------");
						sender.sendMessage("§e * " + args[1] + " §6님의 §c킬 §6or §c데스 §6전적");
						sender.sendMessage("§e ┗━━ §a생존자 §6KILL §7:: §f" + Method.getInfoInt(argp, "HumanKill"));
						sender.sendMessage("§e ┗━━ §a생존자 §6DEATH §7:: §f" + Method.getInfoInt(argp, "HumanDeath"));
						sender.sendMessage("§e ┗━━ §c감염자 §6KILL §7:: §f" + Method.getInfoInt(argp, "ZombieKill"));
						sender.sendMessage("§e ┗━━ §c감염자 §6DEATH §7:: §f" + Method.getInfoInt(argp, "ZombieDeath"));
						sender.sendMessage("§6-----------------------------------------------------------------");
						return false;
					} else {
						sender.sendMessage("§c상대방이 전적 보기를 허가하지 않았습니다.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("순위"))
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
						sender.sendMessage("§e---------------------------------------------------");
						sender.sendMessage("§f 현재 랭크에 오른 사람이 존재하지 않습니다.");
						sender.sendMessage("§e---------------------------------------------------");
						return false;
					}
					
					sender.sendMessage("§e---------------------------------------------------");
					if (oneName.equals("NONE") == false) sender.sendMessage("§6 ┗ §cRANK 1위 §7::§f " + oneName + " / " + one);
					if (twoName.equals("NONE") == false) sender.sendMessage("§6 ┗━ §cRANK 2위 §7::§f " + twoName + " / " + two);
					if (threeName.equals("NONE") == false) sender.sendMessage("§6 ┗━━ §cRANK 3위 §7::§f " + threeName + " / " + three);
					if (fourName.equals("NONE") == false) sender.sendMessage("§6 ┗━━━ §cRANK 4위 §7::§f " + fourName + " / " + four);
					if (fiveName.equals("NONE") == false) sender.sendMessage("§6 ┗━━━━ §cRANK 5위 §7::§f " + fiveName + " / " + five);
					sender.sendMessage("§e---------------------------------------------------");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("계급도"))
				{
					sender.sendMessage("§6---------------------------------");
					sender.sendMessage("§e * §7F §f[§e5킬§f]-> §eE");
					sender.sendMessage("§e * §eE §f[§e30킬§f]-> §6D");
					sender.sendMessage("§e * §6D §f[§e60킬§f]-> §bC");
					sender.sendMessage("§e * §bC §f[§e120킬§f]-> §dB");
					sender.sendMessage("§e * §dB §f[§e300킬§f]-> §cA");
					sender.sendMessage("§e * §cA §f[§e500킬§f]-> §4S");
					sender.sendMessage("§e * §4S §f[§e800킬§f]-> §4SS");
					sender.sendMessage("§6---------------------------------");
					return false;
				}

				else if (args[0].equalsIgnoreCase("설정"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!(args.length == 5))
					{
						sender.sendMessage("§6/좀비 <설정/추가/빼기> <닉네임> <값> <K/D> <Z/H> §f- 플레이어의 감염자/생존자의 킬/뎃을 설정합니다.");
						return false;
					}
					
					File f = new File("plugins/ZombieSurvivalDH/Player/" + args[1] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("§c그 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
						return false;
					}
					
					if (args[4].equalsIgnoreCase("Z"))
					{
						if (args[3].equalsIgnoreCase("K"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							Method.setInfoInt(argsP, "ZombieKill", Integer.parseInt(args[2]));
							sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 감염자 킬 수를 §c" + args[2] + " §6만큼 설정하였습니다.");
							Method.RankingStat(argsP);
							return false;
						}
						
						if (args[1].equalsIgnoreCase("D"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							Method.setInfoInt(argsP, "ZombieDeath", Integer.parseInt(args[2]));
							sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 감염자 사망 수를 §c" + args[2] + " §6만큼 설정하였습니다.");
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
							sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 생존자 킬 수를 §c" + args[2] + " §6만큼 설정하였습니다.");
							Method.RankingStat(argsP);
							return false;
						}
						
						if (args[1].equalsIgnoreCase("D"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							Method.setInfoInt(argsP, "HumanDeath", Integer.parseInt(args[2]));
							sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 생존자 사망 수를 §c" + args[2] + " §6만큼 설정하였습니다.");
							Method.RankingStat(argsP);
							return false;
						}
					}
					
					else
					{
						sender.sendMessage("§6/좀비 <설정/추가/빼기> <닉네임> <값> <K/D> <Z/H>");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("추가"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!(args.length == 5))
					{
						sender.sendMessage("§6/좀비 <설정/추가/빼기> <닉네임> <값> <K/D> <Z/H> §f- 플레이어의 감염자/생존자의 킬/뎃을 설정합니다.");
						return false;
					}
					
					File f = new File("plugins/ZombieSurvivalDH/Player/" + args[1] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("§c그 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
						return false;
					}
					
					if (args[4].equalsIgnoreCase("Z"))
					{
						if (args[3].equalsIgnoreCase("K"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							int ZK = Method.getInfoInt(argsP, "ZombieKill") + Integer.parseInt(args[2]);
							Method.setInfoInt(argsP, "ZombieKill", ZK);
							sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 감염자 킬 수를 §c" + args[2] + " §6만큼 추가하였습니다.");
							Method.RankingStat(argsP);
							return false;
						}
						
						if (args[1].equalsIgnoreCase("D"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							int ZK = Method.getInfoInt(argsP, "ZombieDeath") + Integer.parseInt(args[2]);
							Method.setInfoInt(argsP, "ZombieDeath", ZK);
							sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 감염자 사망 수를 §c" + args[2] + " §6만큼 추가하였습니다.");
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
							sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 생존자 킬 수를 §c" + args[2] + " §6만큼 추가하였습니다.");
							Method.RankingStat(argsP);
							return false;
						}
						
						if (args[1].equalsIgnoreCase("D"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							int ZK = Method.getInfoInt(argsP, "HumanDeath") + Integer.parseInt(args[2]);
							Method.setInfoInt(argsP, "HumanDeath", ZK);
							sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 생존자 사망 수를 §c" + args[2] + " §6만큼 추가하였습니다.");
							Method.RankingStat(argsP);
							return false;
						}
					}
					
					else
					{
						sender.sendMessage("§6/좀비 <설정/추가/빼기> <닉네임> <값> <K/D> <Z/H> §f- 플레이어의 감염자/생존자의 킬/뎃을 설정합니다.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("추가"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (!(args.length == 5))
					{
						sender.sendMessage("§6/좀비 <설정/추가/빼기> <닉네임> <값> <K/D> <Z/H> §f- 플레이어의 감염자/생존자의 킬/뎃을 설정합니다.");
						return false;
					}
					
					File f = new File("plugins/ZombieSurvivalDH/Player/" + args[1] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("§c그 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("§c<값>에 숫자값만 입력해 주십시오.");
						return false;
					}
					
					if (args[4].equalsIgnoreCase("Z"))
					{
						if (args[3].equalsIgnoreCase("K"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							int ZK = Method.getInfoInt(argsP, "ZombieKill") - Integer.parseInt(args[2]);
							Method.setInfoInt(argsP, "ZombieKill", ZK);
							sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 감염자 킬 수를 §c" + args[2] + " §6만큼 뻈습니다.");
							Method.RankingStat(argsP);
							return false;
						}
						
						if (args[1].equalsIgnoreCase("D"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							int ZK = Method.getInfoInt(argsP, "ZombieDeath") - Integer.parseInt(args[2]);
							Method.setInfoInt(argsP, "ZombieDeath", ZK);
							sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 감염자 사망 수를 §c" + args[2] + " §6만큼 뻈습니다.");
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
							sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 생존자 킬 수를 §c" + args[2] + " §6만큼 뻈습니다.");
							Method.RankingStat(argsP);
							return false;
						}
						
						if (args[1].equalsIgnoreCase("D"))
						{
							Player argsP = Bukkit.getPlayer(args[1]);
							int ZK = Method.getInfoInt(argsP, "HumanDeath") - Integer.parseInt(args[2]);
							Method.setInfoInt(argsP, "HumanDeath", ZK);
							sender.sendMessage("§6성공적으로 §c" + args[1] + "§6님의 생존자 사망 수를 §c" + args[2] + " §6만큼 뻈습니다.");
							Method.RankingStat(argsP);
							return false;
						}
					}
					
					else
					{
						sender.sendMessage("§6/좀비 <설정/추가/빼기> <닉네임> <값> <K/D> <Z/H> §f- 플레이어의 감염자/생존자의 킬/뎃을 설정합니다.");
						return false;
					}
				}
					
					else if (args[0].equalsIgnoreCase("강제종료"))
					{
						if (!sender.isOp())
						{
							sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
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
							Bukkit.broadcastMessage(main.prx + "§c관리자§6가 진행중인 게임을 강제로 종료시켰습니다.");
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
							sender.sendMessage("§c이미 게임이 종료되어 있어 종료할 수 없습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("강제시작"))
					{
						if (!sender.isOp())
						{
							sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
							return false;
						}
						
						if (Method.getConfigBoolean("Start") == false) {
							File f = new File("plugins/ZombieSurvivalDH/StartList.yml");
	        				YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
	        	    		List<String> list = config.getStringList("Player");
	        				if (list.size() > 2) {
								Method.Starting();
	        				} else {
	        					Bukkit.broadcastMessage("§c인원 수가 부족합니다.");
	        					return false;
	        				}
						} else {
							sender.sendMessage("§c게임이 진행중일때는 강제 시작이 불가능합니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("카운트정지"))
					{
						if (!sender.isOp())
						{
							sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
							return false;
						}
						
						if (Method.getConfigBoolean("Timersup") == false) {
							if (Method.getConfigBoolean("Start") == false) {
								Method.setConfigBoolean("Timersup", true);
								sender.sendMessage("§c카운트§6를 정상적으로 §c정지§6했습니다.");
							} else {
								sender.sendMessage("§c게임이 진행중일때는 타이머를 강제로 종료할 수 없습니다.");
								return false;
							}
						} else {
							sender.sendMessage("§c이미 타이머가 종료되어있습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("카운트시작"))
					{
						if (!sender.isOp())
						{
							sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
							return false;
						}
						
						if (Method.getConfigBoolean("Timersup") == true) {
							if (Method.getConfigBoolean("Start") == false) {
								Method.setConfigBoolean("Timersup", false);
								sender.sendMessage("§c카운트§6를 정상적으로 §c시작§6했습니다.");
								main.sTimer = 120;
							} else {
								sender.sendMessage("§c게임이 진행중일때는 타이머를 강제로 시작할 수 없습니다.");
								return false;
							}
						} else {
							sender.sendMessage("§c이미 타이머가 시작되어있습니다.");
							return false;
						}
					}
				
				
				if (sender instanceof Player)
				{
					Player p = (Player) sender;
					
					if (args[0].equalsIgnoreCase("승급"))
					{
						Method.addPlayerRank(p);
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("보기") || args.length == 2)
					{
						if (args[1].equalsIgnoreCase("허용"))
						{
							if (Method.getInfoBoolean(p, "Seehandr") == true)
							{
								sender.sendMessage("§c이미 전적 보기를 허용하셨습니다.");
								return false;
							}
							  
							Method.setInfoBoolean(p, "Seehandr", true);
							sender.sendMessage("§6당신은 상대방이 전적을 보는 것을 §c허용§6했습니다.");
							return false;
						}
						  
						else if (args[1].equalsIgnoreCase("거부"))
						{
							if (Method.getInfoBoolean(p, "Seehandr") == false)
							{
								sender.sendMessage("§c이미 전적 보기를 불허하셨습니다.");
								return false;
							}
							  
							Method.setInfoBoolean(p, "Seehandr", false);
							sender.sendMessage("§6당신은 상대방이 전적을 보는 것을 §c거부§6했습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("전적") && args.length == 1)
					{
						sender.sendMessage("§6-----------------------------------------------------------------");
						sender.sendMessage("§e * " + sender.getName() + " §6님의 §c킬 §6or §c데스 §6전적");
						sender.sendMessage("§e ┗━━ §a생존자 §6KILL §7:: §f" + Method.getInfoInt(p, "HumanKill"));
						sender.sendMessage("§e ┗━━ §a생존자 §6DEATH §7:: §f" + Method.getInfoInt(p, "HumanDeath"));
						sender.sendMessage("§e ┗━━ §c감염자 §6KILL §7:: §f" + Method.getInfoInt(p, "ZombieKill"));
						sender.sendMessage("§e ┗━━ §c감염자 §6DEATH §7:: §f" + Method.getInfoInt(p, "ZombieDeath"));
						sender.sendMessage("§6-----------------------------------------------------------------");
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("참여"))
					{
						if (!main.permission.playerInGroup(Bukkit.getServer().getWorld("world"), p.getName(), "튜토리얼") && !main.permission.playerInGroup(Bukkit.getServer().getWorld("world"), p.getName(), "튜토리얼중")) {
							if (Method.getConfigBoolean("Start") == false) {
								if (Method.getInfoBoolean(p, "Observer") == true) {
									Method.setInfoBoolean(p, "Observer", false);
									Method.setInfoBoolean(p, "GameJoin", false);
									Method.addStartList(p.getName());
									p.sendMessage(main.aprx + "§f다음 판부터 당신은 §c좀비 서바이벌 §f게임에 참여합니다.");
								} else {
									p.sendMessage("§c당신은 이미 참여한 상태입니다.");
									return false;
								}
							} else {
								if (Method.getInfoBoolean(p, "Observer") == true) {
									p.sendMessage(main.aprx + "§c게임 진행중에 감염자로 참가하셨습니다!");
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
									p.sendMessage("§c당신은 이미 관전되어있는 상태입니다.");
									return false;
								}
							}
						} else {
							p.sendMessage("§c튜토리얼을 먼저 마치고 입력해 주시기 바랍니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("관전"))
					{
						if (!main.permission.playerInGroup(Bukkit.getServer().getWorld("world"), p.getName(), "튜토리얼") && !main.permission.playerInGroup(Bukkit.getServer().getWorld("world"), p.getName(), "튜토리얼중")) {
							if (Method.getConfigBoolean("Start") == false) {
								if (Method.getInfoBoolean(p, "Observer") == false) {
									Method.setInfoBoolean(p, "Observer", true);
									Method.setInfoBoolean(p, "GameJoin", false);
									Method.subStartList(p.getName());
									p.sendMessage(main.aprx + "§f당신은 이제부터 §c좀비 서바이벌 §f게임에 불참합니다.");
								} else {
									p.sendMessage("§c당신은 이미 관전되어있는 상태입니다.");
									return false;
								}
							} else {
								if (Method.getInfoBoolean(p, "Observer") == false) {
									p.sendMessage(main.aprx + "§c게임 진행중에 나오셨습니다!");
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
									p.sendMessage("§c당신은 이미 관전되어있는 상태입니다.");
									return false;
								}
							} return false;
						} else {
							p.sendMessage("§c튜토리얼을 먼저 마치고 입력해 주시기 바랍니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("비밀명령어"))
					{
						if (p.isOp()) {
							main.Gun.put(p.getName(), "true");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("백신비밀명령어FSAI54"))
					{
						if (Method.getConfigBoolean("Start") == true) {
							p.sendMessage(main.aprx + "당신은 §a백신 §f을 사용하여 생존자로 치유되었습니다!");
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
					
					else if (args[0].equalsIgnoreCase("로그인명령어"))
					{
						if (p.isOp()) {
							p.performCommand("spawn");
							Method.subStartList(p.getName());
							
							for (PotionEffect effect : p.getActivePotionEffects())
								p.removePotionEffect(effect.getType());
							
							if (main.permission.playerInGroup(Bukkit.getServer().getWorld("world"), p.getName(), "튜토리얼")) {
								p.performCommand("warp 튜토리얼");
								return false;
							}
							
							else if (main.permission.playerInGroup(Bukkit.getServer().getWorld("world"), p.getName(), "튜토리얼중")) {
								p.performCommand("warp 튜토리얼-6");
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
												p.sendMessage(main.aprx + "§6당신은 §c게임 타이머 §6진행중에 접속했습니다.");
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
												p.sendMessage(main.aprx + "§6당신은 §c게임 진행 §6중에 접속했습니다.");
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
			if(commandLabel.equalsIgnoreCase("총")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (Method.getInfoBoolean(p, "Observer") == false) {
						if (Method.getConfigBoolean("Start")) {
							if (main.Gun.get(p.getName()) == null) {
								Method.openGunGUI(p);
								return false;
							} else {
								p.sendMessage("§c당신은 이미 총을 획득하셨습니다.");
							}
						} else {
							p.sendMessage("§c게임이 종료되었을때는 총을 획득하실 수 없습니다.");
						}
					} else {
						p.sendMessage("§c관전일떄는 총을 획득할 수 없습니다.");
					}
				}
			}
		} catch (NumberFormatException ex) {
			sender.sendMessage("§6/총 §f- 게임을 시작했을때 실수로 총 획득창을 닫았다면 다시 오픈합니다.");
			return false;
		}
		return false;
	}
	
	private void displayHelp(CommandSender sender)
	{	
		sender.sendMessage(" §e---- §6좀비 서바이벌 §e-- §6페이지 §c1§6/§c1 §e----");
		sender.sendMessage("§6/좀비 §f- 좀비 서바이벌 명령어를 봅니다.");
		sender.sendMessage("§6/좀비 순위 §f- 감염자/생존자 킬 순위권 TOP 5명을 봅니다.");
		sender.sendMessage("§6/좀비 승급 §f- 자신보다 한단계 높은 단계로 승급합니다.");
		sender.sendMessage("§6/좀비 관전 §f- 좀비 서바이벌에 참여하지 않습니다. (잠수할때 유용)");
		sender.sendMessage("§6/좀비 참여 §f- 좀비 서바이벌을 참여합니다. §b( 기본적으로 자동참여 )");
		sender.sendMessage("§6/좀비 전적 §f- 좀비 서바이벌을 불람합니다.");
		sender.sendMessage("§6/좀비 전적 <닉네임> §f- 다른 사람의 킬 뎃 전적을 봅니다.");
		sender.sendMessage("§6/좀비 계급도 §f- 랭크의 계급도를 봅니다.");
		sender.sendMessage("§6/좀비 보기 <허용/거부> §f- 남이 자신의 전적을 보는 것을 설정합니다. §b( 기본값 비허용 )");
		sender.sendMessage("§6/총 §f- 게임을 시작했을때 실수로 총 획득창을 닫았다면 다시 오픈합니다.");
		if (sender.isOp()) {
			sender.sendMessage("§6");
			sender.sendMessage("§b/좀비 강제종료 §f- 좀비 서바이벌 게임을 강제로 종료합니다.");
			sender.sendMessage("§b/좀비 강제시작 §f- 좀비 서바이벌 게임을 강제로 시작합니다.");
			sender.sendMessage("§b/좀비 카운트정지 §f- 3분동안 카운트를 세고 시작하는 타이머를 정지합니다.");
			sender.sendMessage("§b/좀비 카운트시작 §f- 3분동안 카운트를 세고 시작하는 타이머를 시작합니다.");
			sender.sendMessage("§b/좀비 <설정/추가/빼기> <닉네임> <값> <K/D> <Z/H> §f- 플레이어의 감염자/생존자의 킬/뎃을 설정합니다.");
		}
	}
}
