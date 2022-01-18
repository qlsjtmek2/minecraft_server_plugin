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
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
    }
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
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
				  
				else if (args[0].equalsIgnoreCase("����") && args.length == 2)
				{
					File f = new File("plugins/FlyScore/Player/" + args[1] + ".yml");
					if (!f.exists()) {
						sender.sendMessage("�ش� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					Player argp = Bukkit.getPlayer(args[1]);
					sender.sendMessage("��e * " + args[1] + " ��6���� ��c���ھ� ��6����");
					sender.sendMessage("��e ������ ��c��� ��7:: ��f" + Method.getClass(argp));
					sender.sendMessage("��e ������ ��c�ְ� ���ھ� ��7:: ��f" + Method.getMaxScore(argp));
					sender.sendMessage("��e ������ ��c���� ���ھ� ��7:: ��f" + Method.getTotalScore(argp));
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("��޵�"))
				{
					sender.sendMessage("��6--------------------------------------------");
					sender.sendMessage("��e * ��3�Թ��� ��f[��e2000 Score��f]-> ��d�ʱ���");
					sender.sendMessage("��e * ��d�ʱ��� ��f[��e8000 Score��f]-> ��c�߱���");
					sender.sendMessage("��e * ��c�߱��� ��f[��e26000 Score��f]-> ��5�����");
					sender.sendMessage("��e * ��5����� ��f[��e54000 Score��f]-> ��9�ֻ����");
					sender.sendMessage("��e * ��9�ֻ���� ��f[��e80000 Score��f]-> ��2������");
					sender.sendMessage("��6--------------------------------------------");
					return false;
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
			}
			
			if ((sender instanceof Player))
			{
				Player p = (Player) sender;
				
				if (args[0].equalsIgnoreCase("����") && args.length == 1)
				{
					sender.sendMessage("��e * ��6����� ��c���ھ� ��6����");
					sender.sendMessage("��e ������ ��c��� ��7:: ��f" + Method.getClass(p));
					sender.sendMessage("��e ������ ��c�ְ� ���ھ� ��7:: ��f" + Method.getMaxScore(p));
					sender.sendMessage("��e ������ ��c���� ���ھ� ��7:: ��f" + Method.getTotalScore(p));
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("�±�"))
				{
					if (p.isOp()) {
						p.sendMessage("��c���Ǵ� �±��� �Ұ����մϴ�.");
						return false;
					}
					
					Method.addPlayerRank(p);
					return false;
				}
				
				if (p.isOp()) {
					if (args[0].equalsIgnoreCase("��и�ɾ�"))
					{
						if (p.getWorld().getName().equalsIgnoreCase("world_fly"))
						{
							if (!p.isOp())
							{
								p.setOp(true);
								p.chat("/spawn");
								p.chat("/fs ����");
								p.chat("/speed 1");
								p.setOp(false);
								p.sendMessage("��c����� ���� ������ �������Ƿ� ������ �������� �̵��˴ϴ�.");
								return false;
							} else {
								return false;
							}
						}
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("����"))
					{
			        	int fl = p.getFoodLevel();
						if (fl <= 6) {
							p.sendMessage("��c����� ���� 6���� ���� ������ ������ �� �����ϴ�!");
							return false;
						}
						
						this.getServer().dispatchCommand(this.getServer().getConsoleSender(), "play " + p.getName() + " ��Ű��BGM");
						this.getServer().dispatchCommand(this.getServer().getConsoleSender(), "fly " + p.getName() + " on");
						if (!p.isOp()) {
							p.setOp(true);
							p.chat("/warp FlyStart");
							p.chat("/speed 2");
							p.chat("/��� 1");
							p.setOp(false);
						} else {
							p.chat("/warp FlyStart");
							p.chat("/speed 2");
							p.chat("/��� 1");
						}
						
						p.sendMessage("��a��Ű �ö��� ��c1��6/��c8 ��6���带 ��c���ۡ�6�Ͽ����ϴ�!");
						
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
										p.sendMessage("��cŸ�̸ӡ�6�� �������Ƿ� ��a��Ű �ö��̡�6�� ����Ǿ����ϴ�.");
										if (Score.get(p.getName()) != null) Score.remove(p.getName());
										if (Timer.get(p.getName()) != null) Timer.remove(p.getName());
										if (Method.getMaxScore(p) < score) {
											Method.setMaxScore(p, score);
											p.sendMessage("��c���� ��� ��b(" + score + ") ��6�� ��c���� ��� ��b(" + bscore + ") ��6���� ��c" + mascore + "�� ��6�� ���� ��e�ְ��� ��6�� �޼��߽��ϴ�!");
											Integer id = taskIds.remove(p.getName());
											if (id != null) getServer().getScheduler().cancelTask(id);
											return;
										} else {
											Integer id = taskIds.remove(p.getName());
											if (id != null) getServer().getScheduler().cancelTask(id);
											return;
										}
									}
										
									BarAPI.setMessage(p, "��f[ ��a��l" + p.getName() + " ��f] ��6Ÿ�̸�: ��c" + loopTimer + "��");
									loopTimer -= 1;
									Timer.put(p.getName(), loopTimer);
								}
							}.runTaskTimer(this, 0, 20).getTaskId());
							return false;
						} else {
							List<String> lores = new ArrayList<String>();
							if (im.getLore() != null) lores = im.getLore();
								
							for(String line : lores) {
								if(line.contains("���� Ÿ�̸�")) {
									String[] lines = line.split("��f");
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
												p.sendMessage("��cŸ�̸ӡ�6�� �������Ƿ� ��a��Ű �ö��̡�6�� ����Ǿ����ϴ�.");
												if (Score.get(p.getName()) != null) Score.remove(p.getName());
												if (Timer.get(p.getName()) != null) Timer.remove(p.getName());
												if (Method.getMaxScore(p) < score) {
													Method.setMaxScore(p, score);
													p.sendMessage("��c���� ��� ��b(" + score + ") ��6�� ��c���� ��� ��b(" + bscore + ") ��6���� ��c" + mascore + "�� ��6�� ���� ��e�ְ��� ��6�� �޼��߽��ϴ�!");
													Integer id = taskIds.remove(p.getName());
													if (id != null) getServer().getScheduler().cancelTask(id);
													return;
												} else {
													Integer id = taskIds.remove(p.getName());
													if (id != null) getServer().getScheduler().cancelTask(id);
													return;
												}
											}
											BarAPI.setMessage(p, "��f[ ��a��l" + p.getName() + " ��f] ��6Ÿ�̸�: ��c" + loopTimer + "��");
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
										p.sendMessage("��cŸ�̸ӡ�6�� �������Ƿ� ��a��Ű �ö��̡�6�� ����Ǿ����ϴ�.");
										if (Score.get(p.getName()) != null) Score.remove(p.getName());
										if (Timer.get(p.getName()) != null) Timer.remove(p.getName());
										if (Method.getMaxScore(p) < score) {
											Method.setMaxScore(p, score);
											p.sendMessage("��c���� ��� ��b(" + score + ") ��6�� ��c���� ��� ��b(" + bscore + ") ��6���� ��c" + mascore + "�� ��6�� ���� ��e�ְ��� ��6�� �޼��߽��ϴ�!");
											Integer id = taskIds.remove(p.getName());
											if (id != null) getServer().getScheduler().cancelTask(id);
											return;
										} else {
											Integer id = taskIds.remove(p.getName());
											if (id != null) getServer().getScheduler().cancelTask(id);
											return;
										}
									}
									
									BarAPI.setMessage(p, "��f[ ��a��l" + p.getName() + " ��f] ��6Ÿ�̸�: ��c" + loopTimer + "��");
									loopTimer -= 1;
									Timer.put(p.getName(), loopTimer);
								}
							}.runTaskTimer(this, 0, 20).getTaskId());
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("����"))
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
							p.sendMessage("��c���� ��� ��b(" + score + ") ��6�� ��c���� ��� ��b(" + bscore + ") ��6���� ��c" + mascore + "�� ��6�� ���� ��e�ְ��� ��6�� �޼��߽��ϴ�!");
							return false;
						}
						
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("�����߰�"))
					{
						if (args.length == 2) {
							int score = Score.get(p.getName());
							ItemStack item = p.getItemInHand();
							ItemMeta im = item.getItemMeta();
							if (im == null) {
								score += Integer.parseInt(args[1]);
								Score.put(p.getName(), score);
								p.sendMessage("��6���� ���ھ�� ��c" + args[1] + "�� ��6ȹ���Ͽ� ��c" + score + "����6�� �Ǿ����ϴ�.");
								return false;
							} else {
								List<String> lores = new ArrayList<String>();
								if (im.getLore() != null) lores = im.getLore();
								for(String line : lores) {
									if(line.contains("���� ���ϱ�")) {
										String[] lines = line.split("��f");
										lines = lines[1].split(" ");
										score += Integer.parseInt(lines[0]);
										score += Integer.parseInt(args[1]);
										Score.put(p.getName(), score);
										p.sendMessage("��6���� ���ھ�� ��c" + args[1] + "(" + lines[0] + ")�� ��6ȹ���Ͽ� ��c" + score + "����6�� �Ǿ����ϴ�.");
										return false;
									}
									
									else if(line.contains("���� ���ϱ�")) {
										String[] lines = line.split("x");
										lines = lines[1].split(" ");
										int inter = Integer.parseInt(args[1]) * Integer.parseInt(lines[0]);
										score += inter;
										Score.put(p.getName(), score);
										p.sendMessage("��6���� ���ھ�� ��c" + args[1] + "(x" + lines[0] + ")�� ��6ȹ���Ͽ� ��c" + score + "����6�� �Ǿ����ϴ�.");
										return false;
									}
								}
								
								score += Integer.parseInt(args[1]);
								Score.put(p.getName(), score);
								p.sendMessage("��6���� ���ھ�� ��c" + args[1] + "�� ��6ȹ���Ͽ� ��c" + score + "����6�� �Ǿ����ϴ�.");
								return false;
							}
						}
					}
					
					else if (args[0].equalsIgnoreCase("Ÿ�̸��߰�"))
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
									if(line.contains("Ÿ�̸� ���ϱ�")) {
										String[] lines = line.split("��f");
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
					sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
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
		sender.sendMessage(" ��e----- ��6Fly Score ��e--- ��6���� ��e-----");
		sender.sendMessage("��6/FS ��f- Fly Score ������ ���ϴ�.");
		sender.sendMessage("��6/FS �±� ��f- �ڽź��� �Ѵܰ� ���� �ܰ�� �±��մϴ�.");
		sender.sendMessage("��6/FS ���� ��f- �ڽ��� ������ Ȯ���մϴ�.");
		sender.sendMessage("��6/FS ���� <�г���> ��f- �ٸ� ����� ������ Ȯ���մϴ�.");
		sender.sendMessage("��6/FS ���� ��f- ���ھ� ������ TOP 5���� ���ϴ�.");
		sender.sendMessage("��6/FS ��޵� ��f- �󸶳� ���ھ ���� �±��� �������� Ȯ���մϴ�.");
		if (p.isOp())
		{
			sender.sendMessage("��f");
			sender.sendMessage("��b/FS ���� ��f- Ÿ�̸Ӹ� ���۽�ŵ�ϴ�.");
			sender.sendMessage("��b/FS ���� ��f- Ÿ�̸Ӹ� �����ŵ�ϴ�.");
			sender.sendMessage("��b/FS �����߰� <����> ��f- ������ �߰���ŵ�ϴ�.");
			sender.sendMessage("��b/FS Ÿ�̸��߰� <�ð�> ��f- Ÿ�̸Ӹ� �߰���ŵ�ϴ�.");
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
				Bukkit.broadcastMessage("��f[��4�˸���f] ��b���ھ� ��ŷ 1���� ��e" + p.getName() + " ��b������ ����Ǿ����ϴ�!");
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
				Bukkit.broadcastMessage("��f[��4�˸���f] ��b���ھ� ��ŷ 2���� ��e" + p.getName() + " ��b������ ����Ǿ����ϴ�!");
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
				Bukkit.broadcastMessage("��f[��4�˸���f] ��b���ھ� ��ŷ 3���� ��e" + p.getName() + " ��b������ ����Ǿ����ϴ�!");
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
				Bukkit.broadcastMessage("��f[��4�˸���f] ��b���ھ� ��ŷ 4���� ��e" + p.getName() + " ��b������ ����Ǿ����ϴ�!");
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
				Bukkit.broadcastMessage("��f[��4�˸���f] ��b���ھ� ��ŷ 5���� ��e" + p.getName() + " ��b������ ����Ǿ����ϴ�!");
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
				if (!(a[0].equalsIgnoreCase("/����")) && !(a[0].equalsIgnoreCase("/����")) && e.getPlayer().isOp() == false
		 	 	 && !(a[0].equalsIgnoreCase("/�α���")) && !(a[0].equalsIgnoreCase("/l")) && !(a[0].equalsIgnoreCase("/login"))
			 	 && !(a[0].equalsIgnoreCase("/register")) && !(a[0].equalsIgnoreCase("/ȸ������")) && !(a[0].equalsIgnoreCase("/fs"))
			 	 && !(a[0].equalsIgnoreCase("/speed")) && !(a[0].equalsIgnoreCase("/warp")) && !(a[0].equalsIgnoreCase("/heal"))
			 	 && !(a[0].equalsIgnoreCase("/��������")) && !(a[0].equalsIgnoreCase("/â��")) && !(a[0].equalsIgnoreCase("/���մ�"))
			 	 && !(a[0].equalsIgnoreCase("/list")) && !(a[0].equalsIgnoreCase("/ī���ּ�"))) {
					e.getPlayer().sendMessage("��c��Ű �ö��� �����߿��� ��ɾ �Է��Ͻ� �� �����ϴ�.");
					e.setCancelled(true);
				}
			}
		}
		
		if (e.getPlayer().getWorld().getName().equalsIgnoreCase("world_cookfly"))
		{
			if (!e.getPlayer().isOp())
			{
				if (!(a[0].equalsIgnoreCase("/����")) && !(a[0].equalsIgnoreCase("/����")) && e.getPlayer().isOp() == false
		 	 	 && !(a[0].equalsIgnoreCase("/�α���")) && !(a[0].equalsIgnoreCase("/l")) && !(a[0].equalsIgnoreCase("/login"))
			 	 && !(a[0].equalsIgnoreCase("/register")) && !(a[0].equalsIgnoreCase("/ȸ������")) && !(a[0].equalsIgnoreCase("/fs"))
			 	 && !(a[0].equalsIgnoreCase("/speed")) && !(a[0].equalsIgnoreCase("/warp")) && !(a[0].equalsIgnoreCase("/heal"))
			 	 && !(a[0].equalsIgnoreCase("/��������")) && !(a[0].equalsIgnoreCase("/â��")) && !(a[0].equalsIgnoreCase("/���մ�"))
			 	 && !(a[0].equalsIgnoreCase("/list")) && !(a[0].equalsIgnoreCase("/ī���ּ�")) && !(a[0].equalsIgnoreCase("/sethome"))
			 	 && !(a[0].equalsIgnoreCase("/home")) && !(a[0].equalsIgnoreCase("/fly")) && !(a[0].equalsIgnoreCase("/spawn"))) {
					e.getPlayer().sendMessage("��c������ �����߿��� ��ɾ �Է��Ͻ� �� �����ϴ�.");
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
			p.chat("/�ö�������۸�ɾ�");
		}
	}
}
