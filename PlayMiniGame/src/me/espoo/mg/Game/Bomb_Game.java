package me.espoo.mg.Game;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.confuser.barapi.BarAPI;
import me.espoo.mg.Method;
import me.espoo.mg.main;
import me.espoo.mg.Data.GameData;

public class Bomb_Game {
    public void GameStart() {
		GameData.gameNum = 7;
    	Bukkit.broadcastMessage("");
    	Bukkit.broadcastMessage(main.p + "§a잠시후 붐붐붐 게임이 시작되며 아이템이 초기화됩니다.");
    	Bukkit.broadcastMessage(main.p + "§e움직일 경우 게임에서 탈락할수도 있습니다.");
    	Bukkit.broadcastMessage("");
    	
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
		    			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tppos " + player.getName() + " " + Method.getWarp("붐붐붐 게임 이동"));
		    	        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40000, 1, true));
		    		}
		    	}
		    	Method.ChatClean();
		    	Bukkit.broadcastMessage(main.a + "§f§l━━━━━━━━━━━━━━━━━━━");
		    	Bukkit.broadcastMessage(main.a + "§a붐붐붐 게임은 폭탄을 다른사람에게 넘기는 게임입니다.");
		    	Bukkit.broadcastMessage(main.a + "§a남은시간이나 폭탄개인시간이 다되면 폭탄을 가진사람이");
		    	Bukkit.broadcastMessage(main.a + "§a탈락합니다. 탈락을 하면 투명상태가되며 날아다니면서");
		    	Bukkit.broadcastMessage(main.a + "§a구경할 수 있습니다. 최종 1인이 남을때까지 게임이 진행됩니다.");
		    	Bukkit.broadcastMessage(main.a + "§f§l━━━━━━━━━━━━━━━━━━━");
		    	Bukkit.broadcastMessage(main.a + "§6제한시간: §c10분");
		    	Bukkit.broadcastMessage(main.a + "§6공격 유무: §c비허용");
		    	Bukkit.broadcastMessage(main.a + "§6아이템지급시간 : §cX");
		    	Bukkit.broadcastMessage(main.a + "§6폭탄 폭발 시간: §c7초");
		    	Bukkit.broadcastMessage(main.a + "§f§l━━━━━━━━━━━━━━━━━━━");
				
				Timer timer2 = new Timer();
				Date timeToRun2 = new Date(System.currentTimeMillis() + 5 * 1000);
				timer2.schedule(new TimerTask() {
					public void run() {
				    	Bukkit.broadcastMessage("");
				    	Bukkit.broadcastMessage(main.p + "붐붐붐 게임이 시작됩니다.");
				    	Bukkit.broadcastMessage("");
				        GameData.bombTime = 7;
				    	GameData.gameState = true;
				    	GameData.count = 1;
						Bomb_Game.this.check();
				    	Bukkit.broadcastMessage(main.p + "§6붐붐붐 게임 참가 인원: §c" + GameData.playerNum + "명");
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
		
		GameData.gameTimer = 10;
		GameData.taskIds.put("Start", new BukkitRunnable()
		{
			public void run()
			{
				GameData.gameTimer -= 1;
    			if (GameData.gameTimer == 9) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c9분 §6남았습니다.");
     			else if (GameData.gameTimer == 8) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c8분 §6남았습니다.");
     			else if (GameData.gameTimer == 7) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c7분 §6남았습니다.");
     			else if (GameData.gameTimer == 6) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c6분 §6남았습니다.");
     			else if (GameData.gameTimer == 5) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c5분 §6남았습니다.");
     			else if (GameData.gameTimer == 4) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c4분 §6남았습니다.");
     			else if (GameData.gameTimer == 3) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c3분 §6남았습니다.");
    			else if (GameData.gameTimer == 2) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c2분 §6남았습니다.");
    			else if (GameData.gameTimer == 1) Bukkit.broadcastMessage(main.p + "§c미니게임 §6종료 시간이 §c1분 §6남았습니다.");
    			else if (GameData.gameTimer <= 0) {
    				GameData.GameStop();
			    	Bukkit.broadcastMessage("");
			    	Bukkit.broadcastMessage(main.p + "§c붐붐붐 게임§6이 타이머가 지나 종료되었습니다.");
			    	Bukkit.broadcastMessage("");
					return;
    			}
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("PlayMiniGame"), 1200L, 1200L).getTaskId());
    }
    
    private void check() {
        Player player;
        do {
            player = Bukkit.getPlayerExact(GameData.players.get(new Random().nextInt(GameData.players.size())));
        } while (player == null || GameData.diePlayers.contains(player.getName()));
        if (GameData.targetPlayer != null) {
            GameData.targetPlayer.getInventory().clear();
        }
        this.item(GameData.targetPlayer = player);
    	Bukkit.broadcastMessage(main.a + "§c" + player.getName() + "§6님에게 §c폭탄§6이 생겼습니다.");
    }
    
    public void check(final Player player) {
        if (player != null && !GameData.diePlayers.contains(player.getName())) {
            this.item(GameData.targetPlayer = player);
          	Bukkit.broadcastMessage(main.a + "§c" + player.getName() + "§6님에게 §c폭탄§6을 넘겼습니다.");
        }
    }

    public void item(Player player) {
    	ItemStack item = new ItemStack(Material.SNOW_BALL);
    	ItemMeta meta = item.getItemMeta();
    	meta.setDisplayName(ChatColor.RED + "폭탄");
    	item.setItemMeta(meta);
    	player.getInventory().clear();
	    player.getInventory().addItem(new ItemStack[] { item });
    }
	
	@SuppressWarnings("static-access")
	public void diePlayer(Player player) {
		if ((GameData.gameState) && (!GameData.diePlayers.contains(player.getName())) && (GameData.players.contains(player.getName())))
		{
			GameData.diePlayers.add(player.getName());
			Bukkit.broadcastMessage(main.p + "§6펑! §c" + player.getName() + "§6님이 탈락하셨습니다.");
	    	for (Player p : Bukkit.getOnlinePlayers()) {
				BarAPI.setMessage(p, "§6펑! §c폭탄§6이 터져 §c" + GameData.targetPlayer.getName() + "§6 님이 사망하셨습니다.");
	    	} BarAPI.removeBar(player);
			player.getWorld().createExplosion(player.getLocation(), 0);
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
					Bukkit.broadcastMessage(main.p + "§c" + playerName + "§6님이 붐붐붐 게임의 §c우승자§6입니다.");
					Bukkit.broadcastMessage("");
					Method.addScoreYes(Bukkit.getPlayerExact(playerName), 200, "§c미니게임§6에서 우승하여");
					Method.addMoneyNo(Bukkit.getPlayerExact(playerName), 800);
					break;
				}

				new GameData().GameStop();
			}
			
			else if (GameData.players.size() - GameData.diePlayers.size() > 1) {
				check();
			}
		}
	}

	public static void OdiePlayer(Player player) {
		if ((GameData.gameState))
		{
			GameData.obPlayers.add(player.getName());
			BarAPI.removeBar(player);
			player.getInventory().clear();
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 0));
			player.setAllowFlight(true);
			player.damage((int) 0.0D);
			player.sendMessage(main.a + "§c관전자§6는 날아서 구경할 수 있습니다.");
			player.setOp(true);
			player.chat("/goto world");
			if (!player.isOp()) player.setOp(false); player.setOp(false); player.setOp(false); player.setOp(false); player.setOp(false);
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tppos " + player.getName() + " " + Method.getWarp("붐붐붐 게임 이동"));
		}
	}
}
