package me.shinkhan.Quiz;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.milkbowl.vault.economy.Economy;

public class main extends JavaPlugin implements Listener 
{
	public static HashMap<String, String> quizContent = new HashMap<String, String>();
	public static HashMap<String, String> quizPlayer = new HashMap<String, String>();
	public static HashMap<String, String> quizStart = new HashMap<String, String>();
	public static HashMap<String, String> quizMoney = new HashMap<String, String>();
	public static HashMap<String, String> quizAnswer = new HashMap<String, String>();
	static final Map <String, Integer> QuizPutTimer = new HashMap<>();
	public static HashMap<String, String> QuizPut = new HashMap<String, String>();
    static final Map <String, Integer> QuizTimer = new HashMap<>();
	public static HashMap<String, Integer> QuizTime = new HashMap<String, Integer>();
    static final Map <String, Integer> QuizCoolTimer = new HashMap<>();
	public static HashMap<String, String> QuizCoolTime = new HashMap<String, String>();
	public static HashMap<String, Integer> MatAnswer = new HashMap<String, Integer>();
	public static HashMap<String, Integer> matOne = new HashMap<String, Integer>();
	public static HashMap<String, Integer> matTwo = new HashMap<String, Integer>();
	public static HashMap<String, String> matMark = new HashMap<String, String>();
	static final Map <String, Integer> MatTimer = new HashMap<>();
	public static String mat = "§f[§a수학§f] §f";
	public static String prx = "§f[§b퀴즈§f] §e";
	public static String czz = "§f[§c출제자§f] §d";
	public static String mz = "§f[§c문제§f] ";
	public static String zd = "§f[§c정답§f] ";
	public static String bs = "§f[§c보상§f] §a";
	public static String aprx = "§f[§4경고§f] ";
	public static String nprx = "§f[§a안내§f] ";
	public static Economy economy = null;
	
	public void onEnable()
	{
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        economy = (Economy) economyProvider.getProvider();
		File f = new File("plugins/DHQuiz/config.yml");
		File bf = new File("plugins/DHQuiz/BlackList.yml");
		File folder = new File("plugins/DHQuiz");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		YamlConfiguration bconfig = YamlConfiguration.loadConfiguration(bf);
		if (!f.exists()) Method.Createconfig(f, folder, config);
		if (!bf.exists()) Method.CreateBlackList(bf, folder, bconfig);
		
		getCommand("퀴즈").setExecutor(new mainCommand(this));
		getCommand("Quiz").setExecutor(new mainCommand(this));
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
        	public void run() {
				if (Method.getConfigBoolean("수학 퀴즈 차단") == false) {
					if (main.MatAnswer.get("Quiz") == null) {
						int RandomOne = new Random().nextInt(Method.getConfigInt("수학 퀴즈 랜덤 숫자")) + 1;
						int RandomTwo = new Random().nextInt(Method.getConfigInt("수학 퀴즈 랜덤 숫자")) + 1;
						int RandomMark = new Random().nextInt(4) + 1;
						String Mark = null;
						switch (RandomMark) {
						case 1:
							Mark = "+"; break;
						case 2:
							Mark = "-"; break;
						case 3:
							Mark = "*"; break;
						case 4:
							Mark = "÷"; break;
						}
						
						Bukkit.broadcastMessage(main.mat + "§6수학 퀴즈 §7:: §c" + RandomOne + " " + Mark + " " + RandomTwo + " §6는 무엇일까요? §b( 소수 X )");
						
						main.matMark.put("Quiz", Mark);
						main.matOne.put("Quiz", RandomOne);
						main.matTwo.put("Quiz", RandomTwo);
						if (Mark.equals("+")) main.MatAnswer.put("Quiz", RandomOne + RandomTwo);
						else if (Mark.equals("-")) main.MatAnswer.put("Quiz", RandomOne - RandomTwo);
						else if (Mark.equals("*")) main.MatAnswer.put("Quiz", RandomOne * RandomTwo);
						else if (Mark.equals("÷")) main.MatAnswer.put("Quiz", RandomOne / RandomTwo);
						
						main.MatTimer.put("Quiz", new BukkitRunnable()
						{
							@Override
							public void run()
							{
								Bukkit.broadcastMessage(main.prx + "§6제한시간이 §c종료§6되어 §c수학 정답§6이 공개됩니다.");
								Bukkit.broadcastMessage(main.mat + "§c" + matOne.get("Quiz") + " " + matMark.get("Quiz") + " " + matTwo.get("Quiz") + "  " + main.zd + main.MatAnswer.get("Quiz"));
								main.MatAnswer.remove("Quiz");
								main.matOne.remove("Quiz");
								main.matTwo.remove("Quiz");
								main.matMark.remove("Quiz");
								Integer id = main.MatTimer.remove("Quiz");
								Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHQuiz"), Method.getConfigInt("수학 퀴즈 딜레이 (초)") * 20, 0).getTaskId());
					}
				}
			}
		}, Method.getConfigInt("수학 퀴즈 시간 (분)") * 1200, Method.getConfigInt("수학 퀴즈 시간 (분)") * 1200);
		
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

}
