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
    	Bukkit.broadcastMessage(main.p + "§a잠시후 한방칼전 게임이 시작되며 아이템이 초기화됩니다.");
    	Bukkit.broadcastMessage(main.p + "§e움직일 경우 게임에서 탈락할수도 있습니다.");
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
		    			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tppos " + player.getName() + " " + Method.getWarp("한방칼전 게임 이동"));
		    	    	ItemStack item = new ItemStack(Material.SUGAR_CANE);
		    	    	ItemMeta meta = item.getItemMeta();
		    	    	meta.setDisplayName(ChatColor.GREEN + "죽창");
		    	    	item.setItemMeta(meta);
		    	    	item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 32767);
		                player.getInventory().addItem(item);
		    		}
		    	}
		    	Method.ChatClean();
		    	Bukkit.broadcastMessage(main.a + "§f§l━━━━━━━━━━━━━━━━━━━");
		    	Bukkit.broadcastMessage(main.a + "§a한방칼전 게임은 매우 강력한 죽창을 들고 상대방을");
		    	Bukkit.broadcastMessage(main.a + "§a죽이는 게임입니다. 상대방도 한방이지만, 나도");
		    	Bukkit.broadcastMessage(main.a + "§a한방이므로 공격을 할땐 신중을 가해야 합니다. 죽으면");
		    	Bukkit.broadcastMessage(main.a + "§a투명 상태가 되어 날아다니며 구경하실 수 있습니다.");
		    	Bukkit.broadcastMessage(main.a + "§a최종 1인이 남을때까지 게임이 진행됩니다.");
		    	Bukkit.broadcastMessage(main.a + "§f§l━━━━━━━━━━━━━━━━━━━");
		    	Bukkit.broadcastMessage(main.a + "§6제한시간: §c4분");
		    	Bukkit.broadcastMessage(main.a + "§6공격 유무: §c허용");
		    	Bukkit.broadcastMessage(main.a + "§6아이템지급시간 : §cX");
		    	Bukkit.broadcastMessage(main.a + "§f§l━━━━━━━━━━━━━━━━━━━");
				
				Timer timer2 = new Timer();
				Date timeToRun2 = new Date(System.currentTimeMillis() + 8 * 1000);
				timer2.schedule(new TimerTask() {
					public void run() {
				    	Bukkit.broadcastMessage("");
				    	Bukkit.broadcastMessage(main.p + "한방칼전 게임이 시작됩니다.");
				    	Bukkit.broadcastMessage("");
				    	GameData.gameState = true;
				    	GameData.count = 1;
				    	Bukkit.broadcastMessage(main.p + "§6한방칼전 게임 참가 인원: §c" + GameData.playerNum + "명");
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
		
		GameData.gameTimer = 4;
		GameData.taskIds.put("Start", new BukkitRunnable()
		{
			public void run()
			{
				GameData.gameTimer -= 1;
    			if (GameData.gameTimer == 3) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c3분 §6남았습니다.");
    			else if (GameData.gameTimer == 2) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c2분 §6남았습니다.");
    			else if (GameData.gameTimer == 1) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c1분 §6남았습니다.");
    			else if (GameData.gameTimer <= 0) {
    				GameData.GameStop();
			    	Bukkit.broadcastMessage("");
			    	Bukkit.broadcastMessage(main.p + "§c한방칼전 게임§6이 타이머가 지나 종료되었습니다.");
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
			Bukkit.broadcastMessage(main.p + "§c" + player.getName() + "§6님이 탈락하셨습니다.");
			Bukkit.broadcastMessage(main.p + "§6남은인원수: §c" + (GameData.players.size() - GameData.diePlayers.size()) + " §6명");
			player.getInventory().clear();
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 0));
			player.setAllowFlight(true);
			player.damage((int) 0.0D);
			player.sendMessage(main.a + "§c탈락자§6는 날아서 구경할 수 있습니다.");
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
					Bukkit.broadcastMessage(main.p + "§c" + playerName + "§6님이 한방칼전 게임의 §c우승자§6입니다.");
					Bukkit.broadcastMessage("");
					Method.addScoreYes(Bukkit.getPlayerExact(playerName), 200, "§c미니게임§6에서 우승하여");
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
			player.sendMessage(main.a + "§c관전자§6는 날아서 구경할 수 있습니다.");
			player.setOp(true);
			player.chat("/goto world");
			if (!player.isOp()) player.setOp(false); player.setOp(false); player.setOp(false); player.setOp(false); player.setOp(false);
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tppos " + player.getName() + " " + Method.getWarp("한방칼전 게임 이동"));
		}
	}
}
