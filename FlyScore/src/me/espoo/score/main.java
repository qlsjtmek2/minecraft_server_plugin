package me.espoo.score;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class main extends JavaPlugin implements Listener {
	private static final Map <String, Integer> taskIds = new HashMap<>();
	HashMap<String, String> Stop = new HashMap<String, String>();
	HashMap<String, Integer> Score = new HashMap<String, Integer>();
	HashMap<String, Integer> Timer = new HashMap<String, Integer>();
	  
	public void onEnable()
    {	
		getServer().getPluginManager().registerEvents(this, this);
		PluginDescriptionFile pdFile = this.getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
    }
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if(commandLabel.equalsIgnoreCase("FS"))
			{
				if (args.length == 0)
				{
					displayHelp(sender);
					return false;
				}
				  
				else if (args[0].equalsIgnoreCase("정보") && args.length == 2)
				{
					File f = new File("plugins/FlyScore/Player/" + args[1] + ".yml");
					if (!f.exists()) {
						sender.sendMessage("해당 플레이어는 존재하지 않습니다.");
						return false;
					}
					
					Player argp = Bukkit.getPlayer(args[1]);
					sender.sendMessage("§e * " + args[1] + " §6님의 §c스코어 §6정보");
					sender.sendMessage("§e ┗━━ §c계급 §7:: §f" + Method.getClass(argp));
					sender.sendMessage("§e ┗━━ §c최고 스코어 §7:: §f" + Method.getMaxScore(argp));
					sender.sendMessage("§e ┗━━ §c종합 스코어 §7:: §f" + Method.getTotalScore(argp));
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("계급도"))
				{
					sender.sendMessage("§6--------------------------------------------");
					sender.sendMessage("§e * §3입문자 §f[§e2000 Score§f]-> §d초급자");
					sender.sendMessage("§e * §d초급자 §f[§e8000 Score§f]-> §c중급자");
					sender.sendMessage("§e * §c중급자 §f[§e26000 Score§f]-> §5상급자");
					sender.sendMessage("§e * §5상급자 §f[§e54000 Score§f]-> §9최상급자");
					sender.sendMessage("§e * §9최상급자 §f[§e80000 Score§f]-> §2숙련자");
					sender.sendMessage("§6--------------------------------------------");
					return false;
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
			}
			
			if ((sender instanceof Player))
			{
				Player p = (Player) sender;
				
				if (args[0].equalsIgnoreCase("정보") && args.length == 1)
				{
					sender.sendMessage("§e * §6당신의 §c스코어 §6정보");
					sender.sendMessage("§e ┗━━ §c계급 §7:: §f" + Method.getClass(p));
					sender.sendMessage("§e ┗━━ §c최고 스코어 §7:: §f" + Method.getMaxScore(p));
					sender.sendMessage("§e ┗━━ §c종합 스코어 §7:: §f" + Method.getTotalScore(p));
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("승급"))
				{
					if (p.isOp()) {
						p.sendMessage("§c오피는 승급이 불가능합니다.");
						return false;
					}
					
					Method.addPlayerRank(p);
					return false;
				}
				
				if (p.isOp()) {
					if (args[0].equalsIgnoreCase("비밀명령어"))
					{
						if (p.getWorld().getName().equalsIgnoreCase("world_fly"))
						{
							if (!p.isOp())
							{
								p.setOp(true);
								p.chat("/spawn");
								p.chat("/fs 종료");
								p.chat("/speed 1");
								p.setOp(false);
								p.sendMessage("§c당신은 게임 진행중 나갔으므로 강제로 스폰으로 이동됩니다.");
								return false;
							} else {
								return false;
							}
						}
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("시작"))
					{
			        	int fl = p.getFoodLevel();
						if (fl <= 6) {
							p.sendMessage("§c당신의 허기는 6보다 낮아 게임을 진행할 수 없습니다!");
							return false;
						}
						
						this.getServer().dispatchCommand(this.getServer().getConsoleSender(), "play " + p.getName() + " 쿠키런BGM");
						this.getServer().dispatchCommand(this.getServer().getConsoleSender(), "fly " + p.getName() + " on");
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/warp FlyStart");
							p.chat("/speed 2");
							p.chat("/허기 1");
							p.setOp(false);
						} else {
							p.chat("/warp FlyStart");
							p.chat("/speed 2");
							p.chat("/허기 1");
						}
						
						p.sendMessage("§a쿠키 플라이 §c1§6/§c8 §6라운드를 §c시작§6하였습니다!");
						
						Score.put(p.getName(), 0);
						int pTimer = 10;
						ItemStack item = p.getItemInHand();
						ItemMeta im = item.getItemMeta();
						if (im == null) {
							Timer.put(p.getName(), pTimer);
							taskIds.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									if (Stop.get(p.getName()) != null) {
										Stop.remove(p.getName());
										BarAPI.removeBar(p);
										Integer id = taskIds.remove(p.getName());
										if (id != null) getServer().getScheduler().cancelTask(id);
										return;
									}
										
									int loopTimer = Timer.get(p.getName());
									if (loopTimer <= 0) {
										BarAPI.removeBar(p);
										if (!p.isOp()) {
											p.setOp(true);
											p.chat("/warp CookieFly");
											p.chat("/speed 1");
											p.setOp(false);
										} else {
											p.chat("/warp CookieFly");
											p.chat("/speed 1");
										}
										
										if (Stop.get(p.getName()) != null) Stop.remove(p.getName());
										BarAPI.removeBar(p);
										int score = Score.get(p.getName());
										int bscore = Method.getMaxScore(p);
										int mascore = score-bscore;
										RankingStat(p);
										getServer().dispatchCommand(getServer().getConsoleSender(), "play " + p.getName() + " stop");
										Method.addTotalScore(p, Score.get(p.getName()));
										p.sendMessage("§c타이머§6가 끝났으므로 §a쿠키 플라이§6가 종료되었습니다.");
										if (Score.get(p.getName()) != null) Score.remove(p.getName());
										if (Timer.get(p.getName()) != null) Timer.remove(p.getName());
										if (Method.getMaxScore(p) < score) {
											Method.setMaxScore(p, score);
											p.sendMessage("§c현재 기록 §b(" + score + ") §6이 §c지난 기록 §b(" + bscore + ") §6보다 §c" + mascore + "점 §6더 높아 §e최고기록 §6을 달성했습니다!");
											Integer id = taskIds.remove(p.getName());
											if (id != null) getServer().getScheduler().cancelTask(id);
											return;
										} else {
											Integer id = taskIds.remove(p.getName());
											if (id != null) getServer().getScheduler().cancelTask(id);
											return;
										}
									}
										
									BarAPI.setMessage(p, "§f[ §a§l" + p.getName() + " §f] §6타이머: §c" + loopTimer + "초");
									loopTimer -= 1;
									Timer.put(p.getName(), loopTimer);
								}
							}.runTaskTimer(this, 0, 20).getTaskId());
							return false;
						} else {
							List<String> lores = new ArrayList<String>();
							if (im.getLore() != null) lores = im.getLore();
								
							for(String line : lores) {
								if(line.contains("시작 타이머")) {
									String[] lines = line.split("§f");
									lines = lines[1].split(" ");
									pTimer += Integer.parseInt(lines[0]);
									Timer.put(p.getName(), pTimer);
									taskIds.put(p.getName(), new BukkitRunnable()
									{
										public void run()
										{
											if (Stop.get(p.getName()) != null) {
												Stop.remove(p.getName());
												BarAPI.removeBar(p);
												Integer id = taskIds.remove(p.getName());
												if (id != null) getServer().getScheduler().cancelTask(id);
												return;
											}
												
											int loopTimer = Timer.get(p.getName());
											if (loopTimer <= 0) {
												BarAPI.removeBar(p);
												if (!p.isOp()) {
													p.setOp(true);
													p.chat("/warp CookieFly");
													p.chat("/speed 1");
													p.setOp(false);
												} else {
													p.chat("/warp CookieFly");
													p.chat("/speed 1");
												}
												
												if (Stop.get(p.getName()) != null) Stop.remove(p.getName());
												BarAPI.removeBar(p);
												int score = Score.get(p.getName());
												int bscore = Method.getMaxScore(p);
												int mascore = score-bscore;
												RankingStat(p);
												getServer().dispatchCommand(getServer().getConsoleSender(), "play " + p.getName() + " stop");
												Method.addTotalScore(p, Score.get(p.getName()));
												p.sendMessage("§c타이머§6가 끝났으므로 §a쿠키 플라이§6가 종료되었습니다.");
												if (Score.get(p.getName()) != null) Score.remove(p.getName());
												if (Timer.get(p.getName()) != null) Timer.remove(p.getName());
												if (Method.getMaxScore(p) < score) {
													Method.setMaxScore(p, score);
													p.sendMessage("§c현재 기록 §b(" + score + ") §6이 §c지난 기록 §b(" + bscore + ") §6보다 §c" + mascore + "점 §6더 높아 §e최고기록 §6을 달성했습니다!");
													Integer id = taskIds.remove(p.getName());
													if (id != null) getServer().getScheduler().cancelTask(id);
													return;
												} else {
													Integer id = taskIds.remove(p.getName());
													if (id != null) getServer().getScheduler().cancelTask(id);
													return;
												}
											}
											BarAPI.setMessage(p, "§f[ §a§l" + p.getName() + " §f] §6타이머: §c" + loopTimer + "초");
											loopTimer -= 1;
											Timer.put(p.getName(), loopTimer);
										}
									}.runTaskTimer(this, 0, 20).getTaskId());
									return false;
								}
							}
							
							Timer.put(p.getName(), pTimer);
							taskIds.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									if (Stop.get(p.getName()) != null) {
										Stop.remove(p.getName());
										BarAPI.removeBar(p);
										Integer id = taskIds.remove(p.getName());
										if (id != null) getServer().getScheduler().cancelTask(id);
										return;
									}
									
									int loopTimer = Timer.get(p.getName());
									if (loopTimer <= 0) {
										BarAPI.removeBar(p);
										if (!p.isOp()) {
											p.setOp(true);
											p.chat("/warp CookieFly");
											p.chat("/speed 1");
											p.setOp(false);
										} else {
											p.chat("/warp CookieFly");
											p.chat("/speed 1");
										}
										
										if (Stop.get(p.getName()) != null) Stop.remove(p.getName());
										BarAPI.removeBar(p);
										int score = Score.get(p.getName());
										int bscore = Method.getMaxScore(p);
										int mascore = score-bscore;
										RankingStat(p);
										getServer().dispatchCommand(getServer().getConsoleSender(), "play " + p.getName() + " stop");
										Method.addTotalScore(p, Score.get(p.getName()));
										p.sendMessage("§c타이머§6가 끝났으므로 §a쿠키 플라이§6가 종료되었습니다.");
										if (Score.get(p.getName()) != null) Score.remove(p.getName());
										if (Timer.get(p.getName()) != null) Timer.remove(p.getName());
										if (Method.getMaxScore(p) < score) {
											Method.setMaxScore(p, score);
											p.sendMessage("§c현재 기록 §b(" + score + ") §6이 §c지난 기록 §b(" + bscore + ") §6보다 §c" + mascore + "점 §6더 높아 §e최고기록 §6을 달성했습니다!");
											Integer id = taskIds.remove(p.getName());
											if (id != null) getServer().getScheduler().cancelTask(id);
											return;
										} else {
											Integer id = taskIds.remove(p.getName());
											if (id != null) getServer().getScheduler().cancelTask(id);
											return;
										}
									}
									
									BarAPI.setMessage(p, "§f[ §a§l" + p.getName() + " §f] §6타이머: §c" + loopTimer + "초");
									loopTimer -= 1;
									Timer.put(p.getName(), loopTimer);
								}
							}.runTaskTimer(this, 0, 20).getTaskId());
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("종료"))
					{
						Method.addTotalScore(p, Score.get(p.getName()));
						Stop.put(p.getName(), "true");
						int score = Score.get(p.getName());
						int bscore = Method.getMaxScore(p);
						int mascore = score-bscore;
						if (Score.get(p.getName()) != null) Score.remove(p.getName());
						if (Timer.get(p.getName()) != null) Timer.remove(p.getName());
						RankingStat(p);
						if (Method.getMaxScore(p) < score) {
							Method.setMaxScore(p, score);
							RankingStat(p);
							p.sendMessage("§c현재 기록 §b(" + score + ") §6이 §c지난 기록 §b(" + bscore + ") §6보다 §c" + mascore + "점 §6더 높아 §e최고기록 §6을 달성했습니다!");
							return false;
						}
						
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("점수추가"))
					{
						if (args.length == 2) {
							int score = Score.get(p.getName());
							ItemStack item = p.getItemInHand();
							ItemMeta im = item.getItemMeta();
							if (im == null) {
								score += Integer.parseInt(args[1]);
								Score.put(p.getName(), score);
								p.sendMessage("§6현재 스코어에서 §c" + args[1] + "점 §6획득하여 §c" + score + "점§6이 되었습니다.");
								return false;
							} else {
								List<String> lores = new ArrayList<String>();
								if (im.getLore() != null) lores = im.getLore();
								for(String line : lores) {
									if(line.contains("점수 더하기")) {
										String[] lines = line.split("§f");
										lines = lines[1].split(" ");
										score += Integer.parseInt(lines[0]);
										score += Integer.parseInt(args[1]);
										Score.put(p.getName(), score);
										p.sendMessage("§6현재 스코어에서 §c" + args[1] + "(" + lines[0] + ")점 §6획득하여 §c" + score + "점§6이 되었습니다.");
										return false;
									}
									
									else if(line.contains("점수 곱하기")) {
										String[] lines = line.split("x");
										lines = lines[1].split(" ");
										int inter = Integer.parseInt(args[1]) * Integer.parseInt(lines[0]);
										score += inter;
										Score.put(p.getName(), score);
										p.sendMessage("§6현재 스코어에서 §c" + args[1] + "(x" + lines[0] + ")점 §6획득하여 §c" + score + "점§6이 되었습니다.");
										return false;
									}
								}
								
								score += Integer.parseInt(args[1]);
								Score.put(p.getName(), score);
								p.sendMessage("§6현재 스코어에서 §c" + args[1] + "점 §6획득하여 §c" + score + "점§6이 되었습니다.");
								return false;
							}
						}
					}
					
					else if (args[0].equalsIgnoreCase("타이머추가"))
					{
						if (args.length == 2) {
							int timer = Timer.get(p.getName());
							ItemStack item = p.getItemInHand();
							ItemMeta im = item.getItemMeta();
							if (im == null) {
								timer += Integer.parseInt(args[1]);
								Timer.put(p.getName(), timer);
								return false;
							} else {
								List<String> lores = new ArrayList<String>();
								if (im.getLore() != null) lores = im.getLore();
								for(String line : lores) {
									if(line.contains("타이머 더하기")) {
										String[] lines = line.split("§f");
										lines = lines[1].split(" ");
										timer += Integer.parseInt(lines[0]);
										timer += Integer.parseInt(args[1]);
										Timer.put(p.getName(), timer);
										return false;
									}
								}
								
								timer += Integer.parseInt(args[1]);
								Timer.put(p.getName(), timer);
								return false;
							}
						}
					}
				} else {
					sender.sendMessage("§c당신은 명령어를 실행할 권한이 없습니다.");
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
		Player p = (Player) sender;
		sender.sendMessage(" §e----- §6Fly Score §e--- §6도움말 §e-----");
		sender.sendMessage("§6/FS §f- Fly Score 도움말을 봅니다.");
		sender.sendMessage("§6/FS 승급 §f- 자신보다 한단계 높은 단계로 승급합니다.");
		sender.sendMessage("§6/FS 정보 §f- 자신의 정보를 확인합니다.");
		sender.sendMessage("§6/FS 정보 <닉네임> §f- 다른 사람의 정보를 확인합니다.");
		sender.sendMessage("§6/FS 순위 §f- 스코어 순위권 TOP 5명을 봅니다.");
		sender.sendMessage("§6/FS 계급도 §f- 얼마나 스코어를 얻어야 승급이 가능한지 확인합니다.");
		if (p.isOp())
		{
			sender.sendMessage("§f");
			sender.sendMessage("§b/FS 시작 §f- 타이머를 시작시킵니다.");
			sender.sendMessage("§b/FS 종료 §f- 타이머를 종료시킵니다.");
			sender.sendMessage("§b/FS 점수추가 <점수> §f- 점수를 추가시킵니다.");
			sender.sendMessage("§b/FS 타이머추가 <시간> §f- 타이머를 추가시킵니다.");
		}
	}
	
	public void RankingStat(Player p) {
		int score = Method.getMaxScore(p);
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
		
		if (!fiveName.equalsIgnoreCase(p.getName()) && !fourName.equalsIgnoreCase(p.getName()) && 
			!threeName.equalsIgnoreCase(p.getName()) && !oneName.equalsIgnoreCase(p.getName())) {
			if (score > one)
			{
				int oneJunkI = Method.getRankingI("one");
				String oneJunkS = Method.getRankingS("oneName");
				int twoJunkI = Method.getRankingI("two");
				String twoJunkS = Method.getRankingS("twoName");
				int threeJunkI = Method.getRankingI("three");
				String threeJunkS = Method.getRankingS("threeName");
				int fourJunkI = Method.getRankingI("four");
				String fourJunkS = Method.getRankingS("fourName");
				
				if (oneJunkS.equalsIgnoreCase(p.getName())) oneJunkS = "NONE";
				else if (twoJunkS.equalsIgnoreCase(p.getName())) twoJunkS = "NONE";
				else if (threeJunkS.equalsIgnoreCase(p.getName())) threeJunkS = "NONE";
				else if (fourJunkS.equalsIgnoreCase(p.getName())) fourJunkS = "NONE";
					
				Method.setRankingI("one", score);
				Method.setRankingS("oneName", p.getName());
				Method.setRankingI("two", oneJunkI);
				Method.setRankingS("twoName", oneJunkS);
				Method.setRankingI("three", twoJunkI);
				Method.setRankingS("threeName", twoJunkS);
				Method.setRankingI("four", threeJunkI);
				Method.setRankingS("fourName", threeJunkS);
				Method.setRankingI("five", fourJunkI);
				Method.setRankingS("fiveName", fourJunkS);
				Bukkit.broadcastMessage("§f[§4알림§f] §b스코어 랭킹 1위가 §e" + p.getName() + " §b님으로 변경되었습니다!");
				return;
			}
		}
			
		if (!fiveName.equalsIgnoreCase(p.getName()) && !fourName.equalsIgnoreCase(p.getName()) && 
			!twoName.equalsIgnoreCase(p.getName()) && !oneName.equalsIgnoreCase(p.getName())) {
			if (score > two && score <= one)
			{
				int twoJunkI = Method.getRankingI("two");
				String twoJunkS = Method.getRankingS("twoName");
				int threeJunkI = Method.getRankingI("three");
				String threeJunkS = Method.getRankingS("threeName");
				int fourJunkI = Method.getRankingI("four");
				String fourJunkS = Method.getRankingS("fourName");
				
				if (twoJunkS.equalsIgnoreCase(p.getName())) twoJunkS = "NONE";
				else if (threeJunkS.equalsIgnoreCase(p.getName())) threeJunkS = "NONE";
				else if (fourJunkS.equalsIgnoreCase(p.getName())) fourJunkS = "NONE";
					
				Method.setRankingI("two", score);
				Method.setRankingS("twoName", p.getName());
				Method.setRankingI("three", twoJunkI);
				Method.setRankingS("threeName", twoJunkS);
				Method.setRankingI("four", threeJunkI);
				Method.setRankingS("fourName", threeJunkS);
				Method.setRankingI("five", fourJunkI);
				Method.setRankingS("fiveName", fourJunkS);
				Bukkit.broadcastMessage("§f[§4알림§f] §b스코어 랭킹 2위가 §e" + p.getName() + " §b님으로 변경되었습니다!");
				return;
			}
		}
			
		if (!fiveName.equalsIgnoreCase(p.getName()) && !threeName.equalsIgnoreCase(p.getName()) && 
			!twoName.equalsIgnoreCase(p.getName()) && !oneName.equalsIgnoreCase(p.getName())) {
			if (score > three && score <= two)
			{
				int threeJunkI = Method.getRankingI("three");
				String threeJunkS = Method.getRankingS("threeName");
				int fourJunkI = Method.getRankingI("four");
				String fourJunkS = Method.getRankingS("fourName");
				
				if (threeJunkS.equalsIgnoreCase(p.getName())) threeJunkS = "NONE";
				else if (fourJunkS.equalsIgnoreCase(p.getName())) fourJunkS = "NONE";
					
				Method.setRankingI("three", score);
				Method.setRankingS("threeName", p.getName());
				Method.setRankingI("four", threeJunkI);
				Method.setRankingS("fourName", threeJunkS);
				Method.setRankingI("five", fourJunkI);
				Method.setRankingS("fiveName", fourJunkS);
				Bukkit.broadcastMessage("§f[§4알림§f] §b스코어 랭킹 3위가 §e" + p.getName() + " §b님으로 변경되었습니다!");
				return;
			}
		}
			
		if (!fourName.equalsIgnoreCase(p.getName()) && !threeName.equalsIgnoreCase(p.getName()) && 
			!twoName.equalsIgnoreCase(p.getName()) && !oneName.equalsIgnoreCase(p.getName())) {
			if (score > four && score <= three)
			{
				int fourJunkI = Method.getRankingI("four");
				String fourJunkS = Method.getRankingS("fourName");
				
				if (fourJunkS.equalsIgnoreCase(p.getName())) fourJunkS = "NONE";
					
				Method.setRankingI("four", score);
				Method.setRankingS("fourName", p.getName());
				Method.setRankingI("five", fourJunkI);
				Method.setRankingS("fiveName", fourJunkS);
				Bukkit.broadcastMessage("§f[§4알림§f] §b스코어 랭킹 4위가 §e" + p.getName() + " §b님으로 변경되었습니다!");
				return;
			}
		}
		
		if (!fiveName.equalsIgnoreCase(p.getName()) && !fourName.equalsIgnoreCase(p.getName()) && 
			!threeName.equalsIgnoreCase(p.getName()) && !twoName.equalsIgnoreCase(p.getName()) && 
			!oneName.equalsIgnoreCase(p.getName())) {
			if (score > five && score <= four)
			{
				Method.setRankingI("five", score);
				Method.setRankingS("fiveName", p.getName());
				Bukkit.broadcastMessage("§f[§4알림§f] §b스코어 랭킹 5위가 §e" + p.getName() + " §b님으로 변경되었습니다!");
				return;
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File f = new File("plugins/FlyScore/Player/" + p.getName() + ".yml");
		File rf = new File("plugins/FlyScore/Ranking.yml");
		File folder = new File("plugins/FlyScore");
		File folder2 = new File("plugins/FlyScore/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);

		if (!f.exists()) {
			Method.CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		if (!rf.exists()) {
			Method.CreateRanking(folder, folder2, config);
		}
	}
	
	@EventHandler
	public void onPlayerCommnadPreprocess(PlayerCommandPreprocessEvent e)
	{
		String[] a = e.getMessage().split(" ");
		if (e.getPlayer().getWorld().getName().equalsIgnoreCase("world_fly"))
		{
			if (!e.getPlayer().isOp())
			{
				if (!(a[0].equalsIgnoreCase("/도움말")) && !(a[0].equalsIgnoreCase("/상점")) && e.getPlayer().isOp() == false
		 	 	 && !(a[0].equalsIgnoreCase("/로그인")) && !(a[0].equalsIgnoreCase("/l")) && !(a[0].equalsIgnoreCase("/login"))
			 	 && !(a[0].equalsIgnoreCase("/register")) && !(a[0].equalsIgnoreCase("/회원가입")) && !(a[0].equalsIgnoreCase("/fs"))
			 	 && !(a[0].equalsIgnoreCase("/speed")) && !(a[0].equalsIgnoreCase("/warp")) && !(a[0].equalsIgnoreCase("/heal"))
			 	 && !(a[0].equalsIgnoreCase("/쓰레기통")) && !(a[0].equalsIgnoreCase("/창고")) && !(a[0].equalsIgnoreCase("/조합대"))
			 	 && !(a[0].equalsIgnoreCase("/list")) && !(a[0].equalsIgnoreCase("/카페주소"))) {
					e.getPlayer().sendMessage("§c쿠키 플라이 진행중에는 명령어를 입력하실 수 없습니다.");
					e.setCancelled(true);
				}
			}
		}
		
		if (e.getPlayer().getWorld().getName().equalsIgnoreCase("world_cookfly"))
		{
			if (!e.getPlayer().isOp())
			{
				if (!(a[0].equalsIgnoreCase("/도움말")) && !(a[0].equalsIgnoreCase("/상점")) && e.getPlayer().isOp() == false
		 	 	 && !(a[0].equalsIgnoreCase("/로그인")) && !(a[0].equalsIgnoreCase("/l")) && !(a[0].equalsIgnoreCase("/login"))
			 	 && !(a[0].equalsIgnoreCase("/register")) && !(a[0].equalsIgnoreCase("/회원가입")) && !(a[0].equalsIgnoreCase("/fs"))
			 	 && !(a[0].equalsIgnoreCase("/speed")) && !(a[0].equalsIgnoreCase("/warp")) && !(a[0].equalsIgnoreCase("/heal"))
			 	 && !(a[0].equalsIgnoreCase("/쓰레기통")) && !(a[0].equalsIgnoreCase("/창고")) && !(a[0].equalsIgnoreCase("/조합대"))
			 	 && !(a[0].equalsIgnoreCase("/list")) && !(a[0].equalsIgnoreCase("/카페주소")) && !(a[0].equalsIgnoreCase("/sethome"))
			 	 && !(a[0].equalsIgnoreCase("/home")) && !(a[0].equalsIgnoreCase("/fly")) && !(a[0].equalsIgnoreCase("/spawn"))) {
					e.getPlayer().sendMessage("§c점프맵 진행중에는 명령어를 입력하실 수 없습니다.");
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void EntityCombustByBlockEvent(EntityCombustEvent e) 
	{
		Player p = (Player) e.getEntity();
		if (p.getWorld().getName().equalsIgnoreCase("world_fly"))
		{
			p.setFireTicks(0);
			p.chat("/플라이재시작명령어");
		}
	}
}
