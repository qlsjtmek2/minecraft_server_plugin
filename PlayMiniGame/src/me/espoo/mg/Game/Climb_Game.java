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
    	Bukkit.broadcastMessage(main.p + "§a잠시후 등반 게임이 시작되며 아이템이 초기화됩니다.");
    	Bukkit.broadcastMessage(main.p + "§e움직일 경우 게임에서 탈락할수도 있습니다.");
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
		    			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tppos " + player.getName() + " " + Method.getWarp("등반 게임 이동"));
		    		}
		    	}
		    	Method.ChatClean();
		    	Bukkit.broadcastMessage(main.a + "§f§l━━━━━━━━━━━━━━━━━━━");
		    	Bukkit.broadcastMessage(main.a + "§a등반게임은 하늘에서 양털이 떨어져서 쌓이는 게임입니다.");
		    	Bukkit.broadcastMessage(main.a + "§a서로 공격할 수 있으며 아이템이 존재합니다.");
		    	Bukkit.broadcastMessage(main.a + "§a모루는 아래에 있는 블럭을 지웁니다.");
		    	Bukkit.broadcastMessage(main.a + "§a지정된 위치까지 올라가면 게임에서 승리하게 됩니다.");
		    	Bukkit.broadcastMessage(main.a + "§f§l━━━━━━━━━━━━━━━━━━━");
		    	Bukkit.broadcastMessage(main.a + "§6제한시간: §c8분");
		    	Bukkit.broadcastMessage(main.a + "§6공격 유무: §c허용");
		    	Bukkit.broadcastMessage(main.a + "§6아이템지급시간 : §c30초");
		    	Bukkit.broadcastMessage(main.a + "§f§l━━━━━━━━━━━━━━━━━━━");
				
				Timer timer2 = new Timer();
				Date timeToRun2 = new Date(System.currentTimeMillis() + 5 * 1000);
				timer2.schedule(new TimerTask() {
					public void run() {
				    	Bukkit.broadcastMessage("");
				    	Bukkit.broadcastMessage(main.p + "등반게임이 시작됩니다.");
				    	Bukkit.broadcastMessage("");
				    	GameData.gameState = true;
				        Climb_Game.this.check();
				    	GameData.count = 1;
				    	Bukkit.broadcastMessage(main.p + "§6등반게임 참가 인원: §c" + GameData.playerNum + "명");
				    	if (GameData.playerNum <= 1) {
				    		Bukkit.broadcastMessage(main.w + "§c최소인원이 되지 않습니다.");
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
    			if (GameData.gameTimer == 7) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c7분 §6남았습니다.");
    			else if (GameData.gameTimer == 6) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c6분 §6남았습니다.");
    			else if (GameData.gameTimer == 5) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c5분 §6남았습니다.");
    			else if (GameData.gameTimer == 4) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c4분 §6남았습니다.");
    			else if (GameData.gameTimer == 3) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c3분 §6남았습니다.");
    			else if (GameData.gameTimer == 2) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c2분 §6남았습니다.");
    			else if (GameData.gameTimer == 1) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c1분 §6남았습니다.");
    			else if (GameData.gameTimer <= 0) {
    				GameData.GameStop();
			    	Bukkit.broadcastMessage("");
			    	Bukkit.broadcastMessage(main.p + "§c등반게임§6이 타이머가 지나 종료되었습니다.");
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
			Bukkit.broadcastMessage(main.p + "§c" + player.getName() + "§6님이 등반게임의 §c우승자§6입니다.");
			Bukkit.broadcastMessage("");
			Method.addScoreYes(player, 200, "§c미니게임§6에서 우승하여");
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
			player.sendMessage(main.a + "§c관전자§6는 날아서 구경할 수 있습니다.");
			player.setOp(true);
			player.chat("/goto world");
			if (!player.isOp()) player.setOp(false); player.setOp(false); player.setOp(false); player.setOp(false); player.setOp(false);
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tppos " + player.getName() + " " + Method.getWarp("등반 게임 이동"));
		}
	}
}
