package me.espoo.mg.Game;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.data.DataException;

import me.espoo.mg.Method;
import me.espoo.mg.main;
import me.espoo.mg.Data.GameData;

public class Climb_Game {
    public void GameStart() {
		GameData.gameNum = 3;
    	Bukkit.broadcastMessage("");
    	Bukkit.broadcastMessage(main.p + "��a����� ��� ������ ���۵Ǹ� �������� �ʱ�ȭ�˴ϴ�.");
    	Bukkit.broadcastMessage(main.p + "��e������ ��� ���ӿ��� Ż���Ҽ��� �ֽ��ϴ�.");
    	Bukkit.broadcastMessage("");
     	try { Thread.sleep(2000L); } catch (InterruptedException e) {}
		File f = new File("plugins/PlayMiniGame/schematics/climb.schematic");
    	try { GameData.loadIslandSchematic(Bukkit.getWorld("world"), f, new Location(Bukkit.getWorld("world"), 554D, 41D, 941D)); } catch (MaxChangedBlocksException | DataException | IOException e1) {}
    	
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
		    			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tppos " + player.getName() + " " + Method.getWarp("��� ���� �̵�"));
		    		}
		    	}
		    	Method.ChatClean();
		    	Bukkit.broadcastMessage(main.a + "��f��l��������������������������������������");
		    	Bukkit.broadcastMessage(main.a + "��a��ݰ����� �ϴÿ��� ������ �������� ���̴� �����Դϴ�.");
		    	Bukkit.broadcastMessage(main.a + "��a���� ������ �� ������ �������� �����մϴ�.");
		    	Bukkit.broadcastMessage(main.a + "��a���� �Ʒ��� �ִ� ���� ����ϴ�.");
		    	Bukkit.broadcastMessage(main.a + "��a������ ��ġ���� �ö󰡸� ���ӿ��� �¸��ϰ� �˴ϴ�.");
		    	Bukkit.broadcastMessage(main.a + "��f��l��������������������������������������");
		    	Bukkit.broadcastMessage(main.a + "��6���ѽð�: ��c8��");
		    	Bukkit.broadcastMessage(main.a + "��6���� ����: ��c���");
		    	Bukkit.broadcastMessage(main.a + "��6���������޽ð� : ��c30��");
		    	Bukkit.broadcastMessage(main.a + "��f��l��������������������������������������");
				
				Timer timer2 = new Timer();
				Date timeToRun2 = new Date(System.currentTimeMillis() + 5 * 1000);
				timer2.schedule(new TimerTask() {
					public void run() {
				    	Bukkit.broadcastMessage("");
				    	Bukkit.broadcastMessage(main.p + "��ݰ����� ���۵˴ϴ�.");
				    	Bukkit.broadcastMessage("");
				    	GameData.gameState = true;
				        Climb_Game.this.check();
				    	GameData.count = 1;
				    	Bukkit.broadcastMessage(main.p + "��6��ݰ��� ���� �ο�: ��c" + GameData.playerNum + "��");
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
		
		GameData.gameTimer = 8;
		GameData.taskIds.put("Start", new BukkitRunnable()
		{
			public void run()
			{
				GameData.gameTimer -= 1;
    			if (GameData.gameTimer == 7) Bukkit.broadcastMessage(main.p + "��c�̴ϰ��� ��6���� �ð��� ��c7�� ��6���ҽ��ϴ�.");
    			else if (GameData.gameTimer == 6) Bukkit.broadcastMessage(main.p + "��c�̴ϰ��� ��6���� �ð��� ��c6�� ��6���ҽ��ϴ�.");
    			else if (GameData.gameTimer == 5) Bukkit.broadcastMessage(main.p + "��c�̴ϰ��� ��6���� �ð��� ��c5�� ��6���ҽ��ϴ�.");
    			else if (GameData.gameTimer == 4) Bukkit.broadcastMessage(main.p + "��c�̴ϰ��� ��6���� �ð��� ��c4�� ��6���ҽ��ϴ�.");
    			else if (GameData.gameTimer == 3) Bukkit.broadcastMessage(main.p + "��c�̴ϰ��� ��6���� �ð��� ��c3�� ��6���ҽ��ϴ�.");
    			else if (GameData.gameTimer == 2) Bukkit.broadcastMessage(main.p + "��c�̴ϰ��� ��6���� �ð��� ��c2�� ��6���ҽ��ϴ�.");
    			else if (GameData.gameTimer == 1) Bukkit.broadcastMessage(main.p + "��c�̴ϰ��� ��6���� �ð��� ��c1�� ��6���ҽ��ϴ�.");
    			else if (GameData.gameTimer <= 0) {
    				GameData.GameStop();
			    	Bukkit.broadcastMessage("");
			    	Bukkit.broadcastMessage(main.p + "��c��ݰ��ӡ�6�� Ÿ�̸Ӱ� ���� ����Ǿ����ϴ�.");
			    	Bukkit.broadcastMessage("");
					return;
    			}
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("PlayMiniGame"), 1200L, 1200L).getTaskId());
    }
    
    private void check() {
    	GameData.schedulerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.miniGames, new Runnable()
    	{
    		public void run()
    		{
    			if (!GameData.gameState)
    			{
    				Bukkit.getScheduler().cancelTask(GameData.schedulerID);
    				GameData.schedulerID = -1;
    				return;
    			}
    			int x = GameData.ClimbLocation.getBlockX() + new Random().nextInt(13 * 2 - 1) - 13 + 1;
    			int z = GameData.ClimbLocation.getBlockZ() + new Random().nextInt(10 * 2 - 1) - 10 + 1;
    			int random = new Random().nextInt(16);
     			Block block = Bukkit.getWorld("world").getBlockAt(x, 60, z);
    			if (block.getType() == Material.AIR)
    				Bukkit.getWorld("world").spawnFallingBlock(block.getLocation(), Material.WOOL, (byte)random);
    		}
        }, 1L, 1L);
    }
    
	@SuppressWarnings("static-access")
	public void GameOver(Player player) {
		if ((GameData.gameState) && (!GameData.diePlayers.contains(player.getName())) && (GameData.players.contains(player.getName())))
		{
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage(main.p + "��c" + player.getName() + "��6���� ��ݰ����� ��c����ڡ�6�Դϴ�.");
			Bukkit.broadcastMessage("");
			Method.addScoreYes(player, 200, "��c�̴ϰ��ӡ�6���� ����Ͽ�");
			Method.addMoneyNo(player, 800);
			for (String playerName : GameData.players)
			{
				if (GameData.obPlayers.contains(playerName)) {
					if (Method.getOnorOffLine(playerName) != null) {
						Method.addMoneyYes(Bukkit.getPlayerExact(playerName), 200);
						Method.addScoreNo(Bukkit.getPlayerExact(playerName), 40);
					}
					continue;
				}
				break;
			} new GameData().GameStop();
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
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tppos " + player.getName() + " " + Method.getWarp("��� ���� �̵�"));
		}
	}
}
