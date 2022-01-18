package me.espoo.mg.Game;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.espoo.mg.Method;
import me.espoo.mg.main;
import me.espoo.mg.Data.GameData;

public class OneKnife_Game {
    public void GameStart() {
		GameData.gameNum = 5;
    	Bukkit.broadcastMessage("");
    	Bukkit.broadcastMessage(main.p + "��a����� �ѹ�Į�� ������ ���۵Ǹ� �������� �ʱ�ȭ�˴ϴ�.");
    	Bukkit.broadcastMessage(main.p + "��e������ ��� ���ӿ��� Ż���Ҽ��� �ֽ��ϴ�.");
    	Bukkit.broadcastMessage("");
     	try { Thread.sleep(2000L); } catch (InterruptedException e) {}
    	
		Timer timer = new Timer();
		Date timeToRun = new Date(System.currentTimeMillis() + 3 * 1000);
		timer.schedule(new TimerTask() {
			public void run() {
				GameData.playerNum = 0;
		    	for (Player player : Bukkit.getOnlinePlayers()) {
		    		if (!player.isDead()) {
		    			++GameData.playerNum;
		    			GameData.players.add(player.getName());
		    			player.getInventory().clear();
		    			player.setGameMode(GameMode.SURVIVAL);
		    			player.setAllowFlight(false);
		    			player.setFallDistance(0.0f);
		    			player.setFoodLevel(20);
		    			player.setHealth(20);
		    			player.getInventory().setHelmet(null);
		    			player.getInventory().setBoots(null);
		    			player.getInventory().setChestplate(null);
		    			player.getInventory().setLeggings(null);
		    			player.setOp(true);
		    			player.chat("/goto world");
		    			if (!player.isOp()) player.setOp(false); player.setOp(false); player.setOp(false); player.setOp(false); player.setOp(false);
		    			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tppos " + player.getName() + " " + Method.getWarp("�ѹ�Į�� ���� �̵�"));
		    	    	ItemStack item = new ItemStack(Material.SUGAR_CANE);
		    	    	ItemMeta meta = item.getItemMeta();
		    	    	meta.setDisplayName(ChatColor.GREEN + "��â");
		    	    	item.setItemMeta(meta);
		    	    	item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 32767);
		                player.getInventory().addItem(item);
		    		}
		    	}
		    	Method.ChatClean();
		    	Bukkit.broadcastMessage(main.a + "��f��l��������������������������������������");
		    	Bukkit.broadcastMessage(main.a + "��a�ѹ�Į�� ������ �ſ� ������ ��â�� ��� ������");
		    	Bukkit.broadcastMessage(main.a + "��a���̴� �����Դϴ�. ���浵 �ѹ�������, ����");
		    	Bukkit.broadcastMessage(main.a + "��a�ѹ��̹Ƿ� ������ �Ҷ� ������ ���ؾ� �մϴ�. ������");
		    	Bukkit.broadcastMessage(main.a + "��a���� ���°� �Ǿ� ���ƴٴϸ� �����Ͻ� �� �ֽ��ϴ�.");
		    	Bukkit.broadcastMessage(main.a + "��a���� 1���� ���������� ������ ����˴ϴ�.");
		    	Bukkit.broadcastMessage(main.a + "��f��l��������������������������������������");
		    	Bukkit.broadcastMessage(main.a + "��6���ѽð�: ��c4��");
		    	Bukkit.broadcastMessage(main.a + "��6���� ����: ��c���");
		    	Bukkit.broadcastMessage(main.a + "��6���������޽ð� : ��cX");
		    	Bukkit.broadcastMessage(main.a + "��f��l��������������������������������������");
				
				Timer timer2 = new Timer();
				Date timeToRun2 = new Date(System.currentTimeMillis() + 8 * 1000);
				timer2.schedule(new TimerTask() {
					public void run() {
				    	Bukkit.broadcastMessage("");
				    	Bukkit.broadcastMessage(main.p + "�ѹ�Į�� ������ ���۵˴ϴ�.");
				    	Bukkit.broadcastMessage("");
				    	GameData.gameState = true;
				    	GameData.count = 1;
				    	Bukkit.broadcastMessage(main.p + "��6�ѹ�Į�� ���� ���� �ο�: ��c" + GameData.playerNum + "��");
				    	if (GameData.playerNum <= 1) {
				    		Bukkit.broadcastMessage(main.w + "��c�ּ��ο��� ���� �ʽ��ϴ�.");
				    		GameData.GameStop();
				    	}
						return;
					}
				}, timeToRun2);
				return;
			}
		}, timeToRun);
		
		GameData.gameTimer = 4;
		GameData.taskIds.put("Start", new BukkitRunnable()
		{
			public void run()
			{
				GameData.gameTimer -= 1;
    			if (GameData.gameTimer == 3) Bukkit.broadcastMessage(main.p + "��c�̴ϰ��� ��6���� �ð��� ��c3�� ��6���ҽ��ϴ�.");
    			else if (GameData.gameTimer == 2) Bukkit.broadcastMessage(main.p + "��c�̴ϰ��� ��6���� �ð��� ��c2�� ��6���ҽ��ϴ�.");
    			else if (GameData.gameTimer == 1) Bukkit.broadcastMessage(main.p + "��c�̴ϰ��� ��6���� �ð��� ��c1�� ��6���ҽ��ϴ�.");
    			else if (GameData.gameTimer <= 0) {
    				GameData.GameStop();
			    	Bukkit.broadcastMessage("");
			    	Bukkit.broadcastMessage(main.p + "��c�ѹ�Į�� ���ӡ�6�� Ÿ�̸Ӱ� ���� ����Ǿ����ϴ�.");
			    	Bukkit.broadcastMessage("");
					return;
    			}
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("PlayMiniGame"), 1200L, 1200L).getTaskId());
    }
    
	@SuppressWarnings("static-access")
	public void diePlayer(Player player) {
		if ((GameData.gameState) && (!GameData.diePlayers.contains(player.getName())) && (GameData.players.contains(player.getName())))
		{
			GameData.diePlayers.add(player.getName());
			Bukkit.broadcastMessage(main.p + "��c" + player.getName() + "��6���� Ż���ϼ̽��ϴ�.");
			Bukkit.broadcastMessage(main.p + "��6�����ο���: ��c" + (GameData.players.size() - GameData.diePlayers.size()) + " ��6��");
			player.getInventory().clear();
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 0));
			player.setAllowFlight(true);
			player.damage((int) 0.0D);
			player.sendMessage(main.a + "��cŻ���ڡ�6�� ���Ƽ� ������ �� �ֽ��ϴ�.");
			if (GameData.players.size() - GameData.diePlayers.size() == 1)
			{
				for (String playerName : GameData.players)
				{
					if (GameData.diePlayers.contains(playerName)) {
						if (Method.getOnorOffLine(playerName) != null) {
							Method.addMoneyYes(Bukkit.getPlayerExact(playerName), 200);
							Method.addScoreNo(Bukkit.getPlayerExact(playerName), 40);
						}
						continue;
					}
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage(main.p + "��c" + playerName + "��6���� �ѹ�Į�� ������ ��c����ڡ�6�Դϴ�.");
					Bukkit.broadcastMessage("");
					Method.addScoreYes(Bukkit.getPlayerExact(playerName), 200, "��c�̴ϰ��ӡ�6���� ����Ͽ�");
					Method.addMoneyNo(Bukkit.getPlayerExact(playerName), 800);
					break;
				}

				new GameData().GameStop();
			}
		}
	}
	
	public static void OdiePlayer(Player player) {
		if ((GameData.gameState))
		{
			GameData.obPlayers.add(player.getName());
			player.getInventory().clear();
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 0));
			player.setAllowFlight(true);
			player.damage((int) 0.0D);
			player.sendMessage(main.a + "��c�����ڡ�6�� ���Ƽ� ������ �� �ֽ��ϴ�.");
			player.setOp(true);
			player.chat("/goto world");
			if (!player.isOp()) player.setOp(false); player.setOp(false); player.setOp(false); player.setOp(false); player.setOp(false);
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tppos " + player.getName() + " " + Method.getWarp("�ѹ�Į�� ���� �̵�"));
		}
	}
}
