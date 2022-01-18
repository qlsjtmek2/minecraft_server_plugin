package me.espoo.zsdh;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Method extends JavaPlugin {
	public static void CreateStartList(File f, File folder, YamlConfiguration config)
	{
		try
		{
    		List<String> list;
            list = config.getStringList("Player");
			folder.mkdir();
			f.createNewFile();
            config.set("Player", list);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}

	public static void CreateMainconfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			config.set("Start", false);
			config.set("Timersup", false);
            config.set("WarpName", "NONE");
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static void CreateMapList(File f, File folder, YamlConfiguration config)
	{
		try
		{
    		List<String> listW;
            listW = config.getStringList("WarpName");
    		List<String> listN;
            listN = config.getStringList("MapName");
			folder.mkdir();
			f.createNewFile();
            config.set("WarpName", listW);
            config.set("MapName", listN);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static void CreateTeamPlayer(File f, File folder, YamlConfiguration config)
	{
		try
		{
    		List<String> listW;
            listW = config.getStringList("Zombie");
    		List<String> listN;
            listN = config.getStringList("Human");
			folder.mkdir();
			f.createNewFile();
            config.set("Zombie", listW);
            config.set("Human", listN);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static void CreatePlayerInfo(Player p, File f, File folder, File folder2, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			folder2.mkdir();
			f.createNewFile();
			config.set("HumanKill", 0);
			config.set("HumanDeath", 0);
			config.set("ZombieKill", 0);
			config.set("ZombieDeath", 0);
			config.set("GameJoin", false);
			config.set("Seehandr", false);
			config.set("Observer", false);
			config.set("Rank", "��7F");
			config.set("F", true);
			config.set("E", false);
			config.set("D", false);
			config.set("C", false);
			config.set("B", false);
			config.set("A", false);
			config.set("S", false);
			config.set("SS", false);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static void CreateRanking(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			config.set("one", 0);
			config.set("two", 0);
			config.set("three", 0);
			config.set("four", 0);
			config.set("five", 0);
			config.set("oneName", "NONE");
			config.set("twoName", "NONE");
			config.set("threeName", "NONE");
			config.set("fourName", "NONE");
			config.set("fiveName", "NONE");
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static void Starting() {
		if (!getConfigBoolean("Start")) {
			File f = new File("plugins/ZombieSurvivalDH/MapList.yml");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
			File sf = new File("plugins/ZombieSurvivalDH/StartList.yml");
			YamlConfiguration configS = YamlConfiguration.loadConfiguration(sf);
    		List<String> listW;
    		List<String> listN;
    		List<String> list;
            listW = config.getStringList("WarpName");
            listN = config.getStringList("MapName");
            list = configS.getStringList("Player");
            setConfigBoolean("Start", true);
            
            if (listN.size() == 0) {
    			Bukkit.broadcastMessage(main.prx + "��c���� ���� �����Ǿ����� �ʽ��ϴ�. ���� �������ּ���.");
    			return;
            }
            
			int RandomMap = new Random().nextInt(listN.size());
			String mapName = listN.get(RandomMap);
			String warpName = listW.get(RandomMap);
			setConfigString("WarpName", warpName);
			main.stoppvp.put("server", "stop");
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage(main.prx + "��c���� �����̹� ��6������ ���۵Ǿ����ϴ�!");
			Bukkit.broadcastMessage(main.prx + "��6��÷ ��� ��c" + mapName + "��6������ �̵��մϴ�.");
			Bukkit.broadcastMessage(main.prx + "��6�Ǽ��� ��c�ѱ� ȹ�� ��6â�� ����������, ��f[ /�� ] ��6�� �Է��غ�����.");
			Bukkit.broadcastMessage("");
    		for (Player p : Bukkit.getOnlinePlayers()) {
    			p.getWorld().setStorm(false);
    			p.getWorld().setThundering(false);
    			p.getWorld().setTime(999999L);
    		}
			
			for (String la : list) {
				Player p = Bukkit.getPlayer(la);
				if (Method.getInfoBoolean(p, "Observer") == false) {
					setInfoBoolean(p, "GameJoin", true);
					openGunGUI(p);
					p.getInventory().clear();
					p.setFoodLevel(20);
					p.setLevel(0);
					p.setExhaustion(0.0F);
					p.setExp(0.0F);
					p.setHealth(20);
					
					if (!p.isOp()) {
						p.setOp(true);
						p.chat("/warp " + warpName);
						p.setOp(false);
					} else {
						p.chat("/warp " + warpName);
					}
				}
			}
			
			main.taskIds.put("GameRunTimer", new BukkitRunnable()
			{
				public void run()
				{
					main.StartTime -= 1;
        			if (main.StartTime == 15) Bukkit.broadcastMessage(main.prx + "��c15�� ��6�Ŀ� ��c���� �����6�� ź���մϴ�.");
        			else if (main.StartTime == 10) Bukkit.broadcastMessage(main.prx + "��c10�� ��6�Ŀ� ��c���� �����6�� ź���մϴ�.");
        			else if (main.StartTime == 5) Bukkit.broadcastMessage(main.prx + "��c5�� ��6�Ŀ� ��c���� �����6�� ź���մϴ�.");
        			else if (main.StartTime == 3) Bukkit.broadcastMessage(main.prx + "��c3�� ��6�Ŀ� ��c���� �����6�� ź���մϴ�.");
        			else if (main.StartTime == 2) Bukkit.broadcastMessage(main.prx + "��c2�� ��6�Ŀ� ��c���� �����6�� ź���մϴ�.");
        			else if (main.StartTime == 1) Bukkit.broadcastMessage(main.prx + "��c1�� ��6�Ŀ� ��c���� �����6�� ź���մϴ�.");
        			else if (main.StartTime <= 0) {
        				GameStart();
						Integer id = main.taskIds.remove("GameRunTimer");
						if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
						return;
        			}
				}
			}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ZombieSurvivalDH"), 20L, 20L).getTaskId());
		}
	}
	
	public static void GameStart() {
		File sf = new File("plugins/ZombieSurvivalDH/StartList.yml");
		YamlConfiguration configS = YamlConfiguration.loadConfiguration(sf);
		List<String> list = configS.getStringList("Player");
		int Size = list.size();
		String ZombieName = list.get(new Random().nextInt(Size));
		Bukkit.broadcastMessage("");
		main.stoppvp.remove("server");
		
		for (String la : list) {
			Player p = Bukkit.getPlayer(la);
			if (Method.getInfoBoolean(p, "Observer") == false) {
				if (p.getName().equals(ZombieName)) Method.addTeamZombie(ZombieName);
				else Method.addTeamHuman(p.getName());
				
				if (la.equals(ZombieName)) {
					main.Zombie.add(p);
					p.getInventory().clear();
					p.getInventory().setItem(0, new ItemStack(372, 1));
					p.getInventory().setHelmet(new ItemStack(91, 1));
					p.getInventory().setBoots(new ItemStack(301, 1));
					p.getInventory().setChestplate(new ItemStack(299, 1));
					p.getInventory().setLeggings(new ItemStack(300, 1));
			        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40000, 2, true));
			        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40000, 2, true));
	        		p.closeInventory();
					p.sendMessage(main.aprx + "��6����� ��c���� ��6�Դϴ�.");
				} else {
					p.getInventory().setHelmet(null);
					p.getInventory().setBoots(null);
					p.getInventory().setChestplate(null);
					p.getInventory().setLeggings(null);
					p.sendMessage(main.aprx + "��6����� ��c������ ��6�Դϴ�.");
				}
			}
		}
		
		Bukkit.broadcastMessage(main.prx + "��c4�� ��6�Ŀ� ������ ����˴ϴ�.");
		Bukkit.broadcastMessage("");
		
		main.taskIds.put("StartTimer", new BukkitRunnable()
		{
			public void run()
			{
				main.Time -= 1;
    			if (main.Time == 3) {
    				File f = new File("plugins/ZombieSurvivalDH/TeamPlayer.yml");
    				YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
    	            List<String> Hlist;
    	            Hlist = config.getStringList("Human");
    				Bukkit.broadcastMessage(main.prx + "��6���� ��c���� ���� ��6�ð��� ��c3�� ��6���ҽ��ϴ�.");
    				Bukkit.broadcastMessage(main.prx + "��6��l��������������������������������������");
    				Bukkit.broadcastMessage(main.prx + "��c���� ���� ������ ");
    				Bukkit.broadcastMessage(main.prx + Hlist);
    				Bukkit.broadcastMessage(main.prx + "��6��l��������������������������������������");
    			}
    			else if (main.Time == 2) {
    				File f = new File("plugins/ZombieSurvivalDH/TeamPlayer.yml");
    				YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
    	    		List<String> list;
    	            list = config.getStringList("Zombie");
    	            List<String> Hlist;
    	            Hlist = config.getStringList("Human");
    				int Random = new Random().nextInt(list.size());
    				String RName = list.get(Random);
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��� give " + RName + " 1");
    				Bukkit.broadcastMessage(main.prx + "��6���� ��c���� ���� ��6�ð��� ��c2�� ��6���ҽ��ϴ�.");
    				Bukkit.broadcastMessage(main.prx + "��c" + RName + "��6���� ��c��� ��6�� ȹ���ϼ̽��ϴ�!");
    				Bukkit.broadcastMessage(main.prx + "��6��l��������������������������������������");
    				Bukkit.broadcastMessage(main.prx + "��c���� ���� ������ ");
    				Bukkit.broadcastMessage(main.prx + Hlist);
    				Bukkit.broadcastMessage(main.prx + "��6��l��������������������������������������");
    			}
    			else if (main.Time == 1) {
    				File f = new File("plugins/ZombieSurvivalDH/TeamPlayer.yml");
    				YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
    	    		List<String> list;
    	            list = config.getStringList("Zombie");
    	            List<String> Hlist;
    	            Hlist = config.getStringList("Human");
    				int Random = new Random().nextInt(list.size());
    				String RName = list.get(Random);
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ��� give " + RName + " 1");
    				Bukkit.broadcastMessage(main.prx + "��6���� ��c���� ���� ��6�ð��� ��c1�� ��6���ҽ��ϴ�.");
    				Bukkit.broadcastMessage(main.prx + "��c" + RName + "��6���� ��c��� ��6�� ȹ���ϼ̽��ϴ�!");
    				Bukkit.broadcastMessage(main.prx + "��6��l��������������������������������������");
    				Bukkit.broadcastMessage(main.prx + "��c���� ���� ������ ");
    				Bukkit.broadcastMessage(main.prx + Hlist);
    				Bukkit.broadcastMessage(main.prx + "��6��l��������������������������������������");
    			}
       			else if (main.Time <= 0) {
    				GameStop();
    				return;
    			}
    			
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ZombieSurvivalDH"), 1200, 1200).getTaskId());
	}
	
	public static void GameStop() {
		File f = new File("plugins/ZombieSurvivalDH/StartList.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		List<String> list = config.getStringList("Player");
		setConfigBoolean("Start", false);
		setConfigString("WarpName", "NONE");
		main.sTimer = 120;
		main.StartTime = 30;
		main.Time = 4;
		Integer id = main.taskIds.remove("StartTimer");
		if (id == null) {
			return;
		}
		Bukkit.getServer().getScheduler().cancelTask(id);
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(main.prx + "��c���� �����̹� ��6������ ����Ǿ����ϴ�.");
		Bukkit.broadcastMessage(main.prx + "��6�����ڰ� ��c�¸���6�߽��ϴ�!");
		Bukkit.broadcastMessage("");
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getWorld().setStorm(false);
			p.getWorld().setThundering(false);
			p.getWorld().setTime(846000);
		}
		main.stoppvp.remove("server");
		for (String la : list) {
			Player p = Bukkit.getPlayer(la);
			if (main.Gun.get(p.getName()) != null) main.Gun.remove(p.getName());
			for (PotionEffect effect : p.getActivePotionEffects())
				p.removePotionEffect(effect.getType());
			setInfoBoolean(p, "GameJoin", false);
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
			if (getTeamHumanList().contains(p.getName())) {
				p.sendMessage(main.aprx + "��f�������� ��b[ 800�� ] ��f�� �����ص�Ƚ��ϴ�.");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + p.getName() + " 800");
			}
		}
		
		delTeamList();
	}
	
	public static void ZWinGameStop() {
		File f = new File("plugins/ZombieSurvivalDH/StartList.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		List<String> list = config.getStringList("Player");
		setConfigBoolean("Start", false);
		setConfigString("WarpName", "NONE");
		main.sTimer = 120;
		main.StartTime = 30;
		main.Time = 4;
		Integer id = main.taskIds.remove("StartTimer");
		if (id == null) {
			return;
		}
		Bukkit.getServer().getScheduler().cancelTask(id);
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(main.prx + "��c���� �����̹� ��6������ ����Ǿ����ϴ�.");
		Bukkit.broadcastMessage(main.prx + "��6�����ڰ� ��c�¸���6�߽��ϴ�!");
		Bukkit.broadcastMessage("");
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getWorld().setStorm(false);
			p.getWorld().setThundering(false);
			p.getWorld().setTime(846000);
		}
		main.stoppvp.remove("server");
		for (String la : list) {
			Player p = Bukkit.getPlayer(la);
			if (main.Gun.get(p.getName()) != null) main.Gun.remove(p.getName());
			for (PotionEffect effect : p.getActivePotionEffects())
				p.removePotionEffect(effect.getType());
			setInfoBoolean(p, "GameJoin", false);
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
			if (main.Zombie.contains(p)) {
				main.Zombie.remove(p);
				p.sendMessage(main.aprx + "��f�������� ��b[ 800�� ] ��f�� �����ص�Ƚ��ϴ�.");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + p.getName() + " 800");
			}
			else if (getTeamZombieList().contains(p.getName())) {
				p.sendMessage(main.aprx + "��f�������� ��b[ 400�� ] ��f�� �����ص�Ƚ��ϴ�.");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + p.getName() + " 400");
			}
		}
		
		delTeamList();
	}
	
	public static void HWin() {
		File f = new File("plugins/ZombieSurvivalDH/StartList.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		List<String> list = config.getStringList("Player");
		setConfigBoolean("Start", false);
		setConfigString("WarpName", "NONE");
		main.sTimer = 120;
		main.StartTime = 30;
		main.Time = 4;
		Integer id = main.taskIds.remove("StartTimer");
		if (id == null) {
			return;
		}
		Bukkit.getServer().getScheduler().cancelTask(id);
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(main.prx + "��c���� �����̹� ��6������ ����Ǿ����ϴ�.");
		Bukkit.broadcastMessage(main.prx + "��c�����ڡ�6���� ��� ���� �����ڰ� ��c�¸���6�߽��ϴ�!");
		Bukkit.broadcastMessage("");
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getWorld().setStorm(false);
			p.getWorld().setThundering(false);
			p.getWorld().setTime(846000);
		}
		main.stoppvp.remove("server");
		for (String la : list) {
			Player p = Bukkit.getPlayer(la);
			if (main.Gun.get(p.getName()) != null) main.Gun.remove(p.getName());
			for (PotionEffect effect : p.getActivePotionEffects())
				p.removePotionEffect(effect.getType());
			setInfoBoolean(p, "GameJoin", false);
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
			if (getTeamHumanList().contains(p.getName())) {
				p.sendMessage(main.aprx + "��f�������� ��b[ 800�� ] ��f�� �����ص�Ƚ��ϴ�.");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + p.getName() + " 800");
			}
		}
		
		delTeamList();
	}
	
	public static void ZWin() {
		File f = new File("plugins/ZombieSurvivalDH/StartList.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		List<String> list = config.getStringList("Player");
		setConfigBoolean("Start", false);
		delTeamList();
		setConfigString("WarpName", "NONE");
		main.sTimer = 120;
		main.StartTime = 30;
		main.Time = 4;
		Integer id = main.taskIds.remove("StartTimer");
		if (id == null) {
			return;
		}
		Bukkit.getServer().getScheduler().cancelTask(id);
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(main.prx + "��c���� �����̹� ��6������ ����Ǿ����ϴ�.");
		Bukkit.broadcastMessage(main.prx + "��c�����ڡ�6���� ��� ���� �����ڰ� ��c�¸���6�߽��ϴ�!");
		Bukkit.broadcastMessage("");
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getWorld().setStorm(false);
			p.getWorld().setThundering(false);
			p.getWorld().setTime(846000);
		}
		main.stoppvp.remove("server");
		for (String la : list) {
			Player p = Bukkit.getPlayer(la);
			if (main.Gun.get(p.getName()) != null) main.Gun.remove(p.getName());
			for (PotionEffect effect : p.getActivePotionEffects())
				p.removePotionEffect(effect.getType());
			setInfoBoolean(p, "GameJoin", false);
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
			if (main.Zombie.contains(p)) {
				main.Zombie.remove(p);
				p.sendMessage(main.aprx + "��f�������� ��b[ 800�� ] ��f�� �����ص�Ƚ��ϴ�.");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + p.getName() + " 800");
			}
			else if (getTeamZombieList().contains(p.getName())) {
				p.sendMessage(main.aprx + "��f�������� ��b[ 400�� ] ��f�� �����ص�Ƚ��ϴ�.");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + p.getName() + " 400");
			}
		}
	}
	
	public static void HWinBackin() {
		File f = new File("plugins/ZombieSurvivalDH/StartList.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		List<String> list = config.getStringList("Player");
		setConfigBoolean("Start", false);
		delTeamList();
		setConfigString("WarpName", "NONE");
		main.sTimer = 120;
		main.StartTime = 30;
		main.Time = 4;
		Integer id = main.taskIds.remove("StartTimer");
		if (id == null) {
			return;
		}
		Bukkit.getServer().getScheduler().cancelTask(id);
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(main.prx + "��c���� �����̹� ��6������ ����Ǿ����ϴ�.");
		Bukkit.broadcastMessage(main.prx + "��c�����ڡ�6���� ��� ��c��š�6�� ���� ġ���Ǿ� �����ڰ� ��c�¸���6�߽��ϴ�!");
		Bukkit.broadcastMessage("");
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getWorld().setStorm(false);
			p.getWorld().setThundering(false);
			p.getWorld().setTime(846000);
		}
		main.stoppvp.remove("server");
		for (String la : list) {
			Player p = Bukkit.getPlayer(la);
			if (main.Gun.get(p.getName()) != null) main.Gun.remove(p.getName());
			for (PotionEffect effect : p.getActivePotionEffects())
				p.removePotionEffect(effect.getType());
			setInfoBoolean(p, "GameJoin", false);
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
			if (getTeamZombieList().contains(p.getName())) {
				p.sendMessage(main.aprx + "��f�������� ��b[ 800�� ] ��f�� �����ص�Ƚ��ϴ�.");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + p.getName() + " 800");
			}
		}
	}

	public static void addStartList(String name) {
		File f = new File("plugins/ZombieSurvivalDH/StartList.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateMainconfig(f, folder, config);
			}
			
    		List<String> list;
            list = config.getStringList("Player");
			folder.mkdir();
			f.createNewFile();
			if (!list.contains(name)) list.add(name);
            config.set("Player", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void subStartList(String name) {
		File f = new File("plugins/ZombieSurvivalDH/StartList.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateMainconfig(f, folder, config);
			}
			
    		List<String> list;
            list = config.getStringList("Player");
			folder.mkdir();
			f.createNewFile();
			if (list.contains(name)) list.remove(name);
            config.set("Player", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void delStartList() {
		File f = new File("plugins/ZombieSurvivalDH/StartList.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateMainconfig(f, folder, config);
			}
			
    		List<String> list;
            list = config.getStringList("Player");
			folder.mkdir();
			f.createNewFile();
			list.clear();
            config.set("Player", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static Boolean getConfigBoolean(String name) {
		File f = new File("plugins/ZombieSurvivalDH/Mainconfig.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateMainconfig(f, folder, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getConfigInt(String name) {
		File f = new File("plugins/ZombieSurvivalDH/Mainconfig.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateMainconfig(f, folder, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getConfigString(String name) {
		File f = new File("plugins/ZombieSurvivalDH/Mainconfig.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateMainconfig(f, folder, config);
		}
		
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setConfigBoolean(String name, Boolean amount) {
		File f = new File("plugins/ZombieSurvivalDH/Mainconfig.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateMainconfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setConfigInt(String name, int amount) {
		File f = new File("plugins/ZombieSurvivalDH/Mainconfig.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateMainconfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setConfigString(String name, String amount) {
		File f = new File("plugins/ZombieSurvivalDH/Mainconfig.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateMainconfig(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static int getRankingI(String name) {
		File f = new File("plugins/ZombieSurvivalDH/Ranking.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateRanking(f, folder, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static String getRankingS(String name) {
		File f = new File("plugins/ZombieSurvivalDH/Ranking.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateRanking(f, folder, config);
		}
		
		String getString = config.getString(name);
		return getString;
	}
	
	public static void setRankingI(String name, int amount) {
		File f = new File("plugins/ZombieSurvivalDH/Ranking.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateRanking(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setRankingS(String name, String amount) {
		File f = new File("plugins/ZombieSurvivalDH/Ranking.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateRanking(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static String getInfoString(Player p, String name) {
		File f = new File("plugins/ZombieSurvivalDH/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		File folder2 = new File("plugins/ZombieSurvivalDH/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		String getString = config.getString(name);
		return getString;
	}
	
	public static Boolean getInfoBoolean(Player p, String name) {
		File f = new File("plugins/ZombieSurvivalDH/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		File folder2 = new File("plugins/ZombieSurvivalDH/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		Boolean getBoolean = config.getBoolean(name);
		return getBoolean;
	}
	
	public static int getInfoInt(Player p, String name) {
		File f = new File("plugins/ZombieSurvivalDH/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		File folder2 = new File("plugins/ZombieSurvivalDH/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		int getInt = config.getInt(name);
		return getInt;
	}
	
	public static void setInfoBoolean(Player p, String name, Boolean amount) {
		File f = new File("plugins/ZombieSurvivalDH/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		File folder2 = new File("plugins/ZombieSurvivalDH/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoInt(Player p, String name, int amount) {
		File f = new File("plugins/ZombieSurvivalDH/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		File folder2 = new File("plugins/ZombieSurvivalDH/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void setInfoString(Player p, String name, Boolean amount) {
		File f = new File("plugins/ZombieSurvivalDH/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		File folder2 = new File("plugins/ZombieSurvivalDH/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static String getPlayerRank(Player p) {
		File f = new File("plugins/ZombieSurvivalDH/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		File folder2 = new File("plugins/ZombieSurvivalDH/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		if (config.getBoolean("SS")) return "SS";
		else if (config.getBoolean("S")) return "S";
		else if (config.getBoolean("A")) return "A";
		else if (config.getBoolean("B")) return "B";
		else if (config.getBoolean("C")) return "C";
		else if (config.getBoolean("D")) return "D";
		else if (config.getBoolean("E")) return "E";
		else if (config.getBoolean("F")) return "F";
		else return null;
	}
	
	public static void openGunGUI(Player p) {
		File f = new File("plugins/ZombieSurvivalDH/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		File folder2 = new File("plugins/ZombieSurvivalDH/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		if (getPlayerRank(p).equals("SS")) Bukkit.getServer().dispatchCommand
		(Bukkit.getServer().getConsoleSender(), "chc open SSGun " + p.getName());
		else if (getPlayerRank(p).equals("S")) Bukkit.getServer().dispatchCommand
		(Bukkit.getServer().getConsoleSender(), "chc open SGun " + p.getName());
		else if (getPlayerRank(p).equals("A")) Bukkit.getServer().dispatchCommand
		(Bukkit.getServer().getConsoleSender(), "chc open AGun " + p.getName());
		else if (getPlayerRank(p).equals("B")) Bukkit.getServer().dispatchCommand
		(Bukkit.getServer().getConsoleSender(), "chc open BGun " + p.getName());
		else if (getPlayerRank(p).equals("C")) Bukkit.getServer().dispatchCommand
		(Bukkit.getServer().getConsoleSender(), "chc open CGun " + p.getName());
		else if (getPlayerRank(p).equals("D")) Bukkit.getServer().dispatchCommand
		(Bukkit.getServer().getConsoleSender(), "chc open DGun " + p.getName());
		else if (getPlayerRank(p).equals("E")) Bukkit.getServer().dispatchCommand
		(Bukkit.getServer().getConsoleSender(), "chc open EGun " + p.getName());
		else Bukkit.getServer().dispatchCommand
		(Bukkit.getServer().getConsoleSender(), "chc open FGun " + p.getName());
	}

	public static void RankingStat(Player p) {
		int killd = Method.getInfoInt(p, "ZombieKill") + Method.getInfoInt(p, "HumanKill");
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
			if (killd > one)
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
					
				Method.setRankingI("one", killd);
				Method.setRankingS("oneName", p.getName());
				Method.setRankingI("two", oneJunkI);
				Method.setRankingS("twoName", oneJunkS);
				Method.setRankingI("three", twoJunkI);
				Method.setRankingS("threeName", twoJunkS);
				Method.setRankingI("four", threeJunkI);
				Method.setRankingS("fourName", threeJunkS);
				Method.setRankingI("five", fourJunkI);
				Method.setRankingS("fiveName", fourJunkS);
				Bukkit.broadcastMessage("��f[��4�˸���f] ��b���� ų ��ŷ 1���� ��e" + p.getName() + " ��b������ ����Ǿ����ϴ�!");
				return;
			}
		}
			
		if (!fiveName.equalsIgnoreCase(p.getName()) && !fourName.equalsIgnoreCase(p.getName()) && 
			!twoName.equalsIgnoreCase(p.getName()) && !oneName.equalsIgnoreCase(p.getName())) {
			if (killd > two && killd <= one)
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
					
				Method.setRankingI("two", killd);
				Method.setRankingS("twoName", p.getName());
				Method.setRankingI("three", twoJunkI);
				Method.setRankingS("threeName", twoJunkS);
				Method.setRankingI("four", threeJunkI);
				Method.setRankingS("fourName", threeJunkS);
				Method.setRankingI("five", fourJunkI);
				Method.setRankingS("fiveName", fourJunkS);
				Bukkit.broadcastMessage("��f[��4�˸���f] ��b���� ų ��ŷ 2���� ��e" + p.getName() + " ��b������ ����Ǿ����ϴ�!");
				return;
			}
		}
		
		
		if (!fiveName.equalsIgnoreCase(p.getName()) && !threeName.equalsIgnoreCase(p.getName()) && 
			!twoName.equalsIgnoreCase(p.getName()) && !oneName.equalsIgnoreCase(p.getName())) {
			if (killd > three && killd <= two)
			{
				int threeJunkI = Method.getRankingI("three");
				String threeJunkS = Method.getRankingS("threeName");
				int fourJunkI = Method.getRankingI("four");
				String fourJunkS = Method.getRankingS("fourName");
				
				if (threeJunkS.equalsIgnoreCase(p.getName())) threeJunkS = "NONE";
				else if (fourJunkS.equalsIgnoreCase(p.getName())) fourJunkS = "NONE";
					
				Method.setRankingI("three", killd);
				Method.setRankingS("threeName", p.getName());
				Method.setRankingI("four", threeJunkI);
				Method.setRankingS("fourName", threeJunkS);
				Method.setRankingI("five", fourJunkI);
				Method.setRankingS("fiveName", fourJunkS);
				Bukkit.broadcastMessage("��f[��4�˸���f] ��b���� ų ��ŷ 3���� ��e" + p.getName() + " ��b������ ����Ǿ����ϴ�!");
				return;
			}
		}
			
		if (!fourName.equalsIgnoreCase(p.getName()) && !threeName.equalsIgnoreCase(p.getName()) && 
			!twoName.equalsIgnoreCase(p.getName()) && !oneName.equalsIgnoreCase(p.getName())) {
			if (killd > four && killd <= three)
			{
				int fourJunkI = Method.getRankingI("four");
				String fourJunkS = Method.getRankingS("fourName");
				
				if (fourJunkS.equalsIgnoreCase(p.getName())) fourJunkS = "NONE";
					
				Method.setRankingI("four", killd);
				Method.setRankingS("fourName", p.getName());
				Method.setRankingI("five", fourJunkI);
				Method.setRankingS("fiveName", fourJunkS);
				Bukkit.broadcastMessage("��f[��4�˸���f] ��b���� ų ��ŷ 4���� ��e" + p.getName() + " ��b������ ����Ǿ����ϴ�!");
				return;
			}
		}
		
		if (!fiveName.equalsIgnoreCase(p.getName()) && !fourName.equalsIgnoreCase(p.getName()) && 
			!threeName.equalsIgnoreCase(p.getName()) && !twoName.equalsIgnoreCase(p.getName()) && 
			!oneName.equalsIgnoreCase(p.getName())) {
			if (killd > five && killd <= four)
			{
				Method.setRankingI("five", killd);
				Method.setRankingS("fiveName", p.getName());
				Bukkit.broadcastMessage("��f[��4�˸���f] ��b���� ų ��ŷ 5���� ��e" + p.getName() + " ��b������ ����Ǿ����ϴ�!");
				return;
			}
		}
	}
	
	public static void addPlayerRank(Player p) {
		File f = new File("plugins/ZombieSurvivalDH/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		File folder2 = new File("plugins/ZombieSurvivalDH/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			if ((getInfoInt(p, "ZombieKill") + getInfoInt(p, "HumanKill") >= 800) && (!(getInfoBoolean(p, "SS"))))
			{
				config.set("Rank", "��4SS");
				truePlayerRank(p, "SS", f, folder, folder2, config);
				truePlayerRank(p, "S", f, folder, folder2, config);
				truePlayerRank(p, "A", f, folder, folder2, config);
				truePlayerRank(p, "B", f, folder, folder2, config);
				truePlayerRank(p, "C", f, folder, folder2, config);
				truePlayerRank(p, "D", f, folder, folder2, config);
				truePlayerRank(p, "E", f, folder, folder2, config);
				Bukkit.broadcastMessage("��e---------------------------------------------------------");
				Bukkit.broadcastMessage("��f");
				Bukkit.broadcastMessage("��6�������ּ���! ��e[ " + p.getName() + " ] ��6����");
				Bukkit.broadcastMessage("��c800ų��6�� �޼��� ��4SS RANK ��6�� �Ǿ����ϴ�!");
				Bukkit.broadcastMessage("��f");
				Bukkit.broadcastMessage("��e---------------------------------------------------------");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " SS world");
					p.chat("/manuadd " + p.getName() + " SS world_pvp");
					p.setOp(false);
				} else {
					p.chat("/manuadd " + p.getName() + " SS world");
					p.chat("/manuadd " + p.getName() + " SS world_pvp");
				}
				config.save(f);
				return;
			}
			
			else if ((getInfoInt(p, "ZombieKill") + getInfoInt(p, "HumanKill") >= 500) && (!(getInfoBoolean(p, "S"))))
			{
				config.set("Rank", "��4S");
				truePlayerRank(p, "S", f, folder, folder2, config);
				truePlayerRank(p, "A", f, folder, folder2, config);
				truePlayerRank(p, "B", f, folder, folder2, config);
				truePlayerRank(p, "C", f, folder, folder2, config);
				truePlayerRank(p, "D", f, folder, folder2, config);
				truePlayerRank(p, "E", f, folder, folder2, config);
				p.sendMessage("��6����� ��c500ų��6�� �޼��� ��eS RANK ��6�� �Ǿ����ϴ�.");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " S world");
					p.chat("/manuadd " + p.getName() + " S world_pvp");
					p.setOp(false);
				} else {
					p.chat("/manuadd " + p.getName() + " S world");
					p.chat("/manuadd " + p.getName() + " S world_pvp");
				}
				config.save(f);
				return;
			}
			
			else if ((getInfoInt(p, "ZombieKill") + getInfoInt(p, "HumanKill") >= 300) && (!(getInfoBoolean(p, "A"))))
			{
				config.set("Rank", "��cA");
				truePlayerRank(p, "A", f, folder, folder2, config);
				truePlayerRank(p, "B", f, folder, folder2, config);
				truePlayerRank(p, "C", f, folder, folder2, config);
				truePlayerRank(p, "D", f, folder, folder2, config);
				truePlayerRank(p, "E", f, folder, folder2, config);
				p.sendMessage("��6����� ��c300ų��6�� �޼��� ��cA RANK ��6�� �Ǿ����ϴ�.");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " A world");
					p.chat("/manuadd " + p.getName() + " A world_pvp");
					p.setOp(false);
				} else {
					p.chat("/manuadd " + p.getName() + " A world");
					p.chat("/manuadd " + p.getName() + " A world_pvp");
				}
				config.save(f);
				return;
			}
			
			else if ((getInfoInt(p, "ZombieKill") + getInfoInt(p, "HumanKill") >= 120) && (!(getInfoBoolean(p, "B"))))
			{
				config.set("Rank", "��dB");
				truePlayerRank(p, "B", f, folder, folder2, config);
				truePlayerRank(p, "C", f, folder, folder2, config);
				truePlayerRank(p, "D", f, folder, folder2, config);
				truePlayerRank(p, "E", f, folder, folder2, config);
				p.sendMessage("��6����� ��c120ų��6�� �޼��� ��dB RANK ��6�� �Ǿ����ϴ�.");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " B world");
					p.chat("/manuadd " + p.getName() + " B world_pvp");
					p.setOp(false);
				} else {
					p.chat("/manuadd " + p.getName() + " B world");
					p.chat("/manuadd " + p.getName() + " B world_pvp");
				}
				config.save(f);
				return;
			}
			
			else if ((getInfoInt(p, "ZombieKill") + getInfoInt(p, "HumanKill") >= 60) && (!(getInfoBoolean(p, "C"))))
			{
				config.set("Rank", "��bC");
				truePlayerRank(p, "C", f, folder, folder2, config);
				truePlayerRank(p, "D", f, folder, folder2, config);
				truePlayerRank(p, "E", f, folder, folder2, config);
				p.sendMessage("��6����� ��c60ų��6�� �޼��� ��bC RANK ��6�� �Ǿ����ϴ�.");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " C world");
					p.chat("/manuadd " + p.getName() + " C world_pvp");
					p.setOp(false);
				} else {
					p.chat("/manuadd " + p.getName() + " C world");
					p.chat("/manuadd " + p.getName() + " C world_pvp");
				}
				config.save(f);
				return;
			}
			
			else if ((getInfoInt(p, "ZombieKill") + getInfoInt(p, "HumanKill") >= 30) && (!(getInfoBoolean(p, "D"))))
			{
				config.set("Rank", "��6D");
				truePlayerRank(p, "D", f, folder, folder2, config);
				truePlayerRank(p, "E", f, folder, folder2, config);
				p.sendMessage("��6����� ��c30ų��6�� �޼��� ��6D RANK ��6�� �Ǿ����ϴ�.");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " D world");
					p.chat("/manuadd " + p.getName() + " D world_pvp");
					p.setOp(false);
				} else {
					p.chat("/manuadd " + p.getName() + " D world");
					p.chat("/manuadd " + p.getName() + " D world_pvp");
				}
				config.save(f);
				return;
			}
			
			else if ((getInfoInt(p, "ZombieKill") + getInfoInt(p, "HumanKill") >= 5) && (!(getInfoBoolean(p, "E"))))
			{
				config.set("Rank", "��eE");
				truePlayerRank(p, "E", f, folder, folder2, config);
				p.sendMessage("��6����� ��c5ų��6�� �޼��� ��eE RANK ��6�� �Ǿ����ϴ�.");
				if (!p.isOp()) {
					p.setOp(true);
					p.chat("/manuadd " + p.getName() + " E world");
					p.chat("/manuadd " + p.getName() + " E world_pvp");
					p.setOp(false);
				} else {
					p.chat("/manuadd " + p.getName() + " E world");
					p.chat("/manuadd " + p.getName() + " E world_pvp");
				}
				config.save(f);
				return;
			}
			
			else p.sendMessage("��c����� ��ũ�� �ø��� ���� �䱸������ �������� �ʾҽ��ϴ�.");
			

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void truePlayerRank(Player p, String rank, File f, File folder, File folder2, YamlConfiguration config) {
		try {
			if (!f.exists()) {
				CreatePlayerInfo(p, f, folder, folder2, config);
			}
			
			if (rank.equals("E")) config.set("E", true);
			else if (rank.equals("D")) config.set("D", true);
			else if (rank.equals("C")) config.set("C", true);
			else if (rank.equals("B")) config.set("B", true);
			else if (rank.equals("A")) config.set("A", true);
			else if (rank.equals("S")) config.set("S", true);
			else if (rank.equals("SS")) config.set("SS", true);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static List<String> getTeamZombieList() {
		File f = new File("plugins/ZombieSurvivalDH/TeamPlayer.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateMainconfig(f, folder, config);
		}
		
		List<String> list;
        list = config.getStringList("Zombie");
        return list;
	}
	
	public static List<String> getTeamHumanList() {
		File f = new File("plugins/ZombieSurvivalDH/TeamPlayer.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateMainconfig(f, folder, config);
		}
		
		List<String> list;
        list = config.getStringList("Human");
        return list;
	}
	
	public static void addTeamZombie(String name) {
		File f = new File("plugins/ZombieSurvivalDH/TeamPlayer.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateTeamPlayer(f, folder, config);
			}
			
    		List<String> list;
            list = config.getStringList("Zombie");
			folder.mkdir();
			f.createNewFile();
			if (!list.contains(name)) list.add(name);
            config.set("Zombie", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void addTeamHuman(String name) {
		File f = new File("plugins/ZombieSurvivalDH/TeamPlayer.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateTeamPlayer(f, folder, config);
			}
			
    		List<String> list;
            list = config.getStringList("Human");
			folder.mkdir();
			f.createNewFile();
			if (!list.contains(name)) list.add(name);
            config.set("Human", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void subTeamZombie(String name) {
		File f = new File("plugins/ZombieSurvivalDH/TeamPlayer.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateMainconfig(f, folder, config);
			}
			
    		List<String> list;
            list = config.getStringList("Zombie");
			folder.mkdir();
			f.createNewFile();
			if (list.contains(name)) list.remove(name);
            config.set("Zombie", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static void subTeamHuman(String name) {
		File f = new File("plugins/ZombieSurvivalDH/TeamPlayer.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateMainconfig(f, folder, config);
			}
			
    		List<String> list;
            list = config.getStringList("Human");
			folder.mkdir();
			f.createNewFile();
			if (list.contains(name)) list.remove(name);
            config.set("Human", list);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
	
	public static int getTeamZombie() {
		File f = new File("plugins/ZombieSurvivalDH/TeamPlayer.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateMainconfig(f, folder, config);
		}
			
		List<String> list;
		list = config.getStringList("Zombie");
		return list.size();
	}
	
	public static int getTeamHuman() {
		File f = new File("plugins/ZombieSurvivalDH/TeamPlayer.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateMainconfig(f, folder, config);
		}
			
		List<String> list;
		list = config.getStringList("Human");
		return list.size();
	}
	
	public static void delTeamList() {
		File f = new File("plugins/ZombieSurvivalDH/TeamPlayer.yml");
		File folder = new File("plugins/ZombieSurvivalDH");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateMainconfig(f, folder, config);
			}
			
    		List<String> Zlist;
            Zlist = config.getStringList("Zombie");
    		List<String> Hlist;
            Hlist = config.getStringList("Human");
			f.createNewFile();
			Zlist.clear();
			Hlist.clear();
            config.set("Zombie", Zlist);
            config.set("Human", Hlist);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[ZSDH] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
