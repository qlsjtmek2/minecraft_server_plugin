package me.espoo.mg;

import java.io.File;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.espoo.mg.Data.GameData;
import me.espoo.mg.Game.Anvil_Game;
import me.espoo.mg.Game.Bomb_Game;
import me.espoo.mg.Game.Climb_Game;
import me.espoo.mg.Game.OneKnife_Game;
import me.espoo.mg.Game.Running_Game;
import me.espoo.mg.Game.SpeedShot_Game;
import me.espoo.mg.Game.Spleef_Game;

public class mainEvent extends JavaPlugin implements Listener {
	main main;
		
	public mainEvent(main main)
	{
		this.main = main;
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File f = new File("plugins/PlayMiniGame/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/PlayMiniGame");
		File folder2 = new File("plugins/PlayMiniGame/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		
		if (!f.exists()) {
			Method.CreatePlayerInfo(p, f, folder, folder2, config);
		}
		
		p.getInventory().clear();
		p.setFoodLevel(20);
		p.setHealth(20);
		p.getInventory().setHelmet(null);
		p.getInventory().setBoots(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		
		Timer timer1 = new Timer();
		Date timeToRun = new Date(System.currentTimeMillis() + 300);
		timer1.schedule(new TimerTask() {
			public void run() {
				for (int i = 0; i < 100; i++) {
					p.sendMessage("");
				}
				p.sendMessage("§e■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				p.sendMessage("");
				p.sendMessage("§f저희 서버는 §b아워플랜 호스팅 §f에서 서비스를 받고 있습니다.");
				p.sendMessage("");
				p.sendMessage("§e사업자 등록번호 : 199-14-00137");
				p.sendMessage("§cKT IDC  - 420Gbps+ 디도스 방어 서비스.");
				p.sendMessage("");
				p.sendMessage("§4서버에 DDos, DrDos 공격시");
				p.sendMessage("§b아워플랜 호스팅 §c에서 §c직접 고소처리합니다.");
				p.sendMessage("");
				p.sendMessage("§e■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				p.sendMessage("");
				p.sendMessage(" 　　　　§6환영합니다 §e" + p.getName() + "§6님! 오늘도 즐거운 §c마인크래프트 §6하세요~");
				p.sendMessage("");
				p.sendMessage("§e■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				p.sendMessage("");
				return;
			}
		}, timeToRun);
		
		if (GameData.gameState)
			switch (GameData.gameNum)
			{
				case 1:
					new Running_Game().OdiePlayer(p); break;
				case 2:
					new Anvil_Game().OdiePlayer(p); break;
				case 3:
					new Climb_Game().OdiePlayer(p); break;
				case 4:
					new Spleef_Game().OdiePlayer(p); break;
				case 5:
					new OneKnife_Game().OdiePlayer(p); break;
				case 6:
					new SpeedShot_Game().OdiePlayer(p); break;
				case 7:
					new Bomb_Game().OdiePlayer(p); break;
			}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (GameData.mapPlayer != null && GameData.mapPlayer.equals(event.getPlayer().getName())) {
	     	Block block1 = Bukkit.getWorld("world_map").getBlockAt(Method.getLocation("맵1 다이아블럭"));
	     	Block block2 = Bukkit.getWorld("world_map").getBlockAt(Method.getLocation("맵2 다이아블럭"));
	     	Block block3 = Bukkit.getWorld("world_map").getBlockAt(Method.getLocation("맵3 다이아블럭"));
	     	block1.setType(Material.DIAMOND_BLOCK);
	       	block2.setType(Material.DIAMOND_BLOCK);
	       	block3.setType(Material.DIAMOND_BLOCK);
	    	Bukkit.broadcastMessage("");
	    	Bukkit.broadcastMessage(main.p + "§6맵을 고르던 §c플레이어§6가 퇴장하여 다시 시작합니다.");
	    	Bukkit.broadcastMessage("");
			GameData.mapWark = false;
			GameData.mapPlayer = null;
	    	
	    	for (Player player : Bukkit.getOnlinePlayers()) {
	    		player.performCommand("spawn");
	    	}
		}
		
		if (GameData.gameState)
			switch (GameData.gameNum)
			{
				case 1:
					new Running_Game().diePlayer(event.getPlayer()); break;
				case 2:
					new Anvil_Game().diePlayer(event.getPlayer()); break;
				case 3:
					new Climb_Game().OdiePlayer(event.getPlayer()); break;
				case 4:
					new Spleef_Game().diePlayer(event.getPlayer()); break;
				case 5:
					new OneKnife_Game().diePlayer(event.getPlayer()); break;
				case 6:
					new SpeedShot_Game().diePlayer(event.getPlayer()); break;
				case 7:
					new Bomb_Game().diePlayer(event.getPlayer()); break;
			}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onKick(PlayerKickEvent event) {
		if (GameData.mapPlayer != null && GameData.mapPlayer.equals(event.getPlayer().getName())) {
	     	Block block1 = Bukkit.getWorld("world_map").getBlockAt(Method.getLocation("맵1 다이아블럭"));
	     	Block block2 = Bukkit.getWorld("world_map").getBlockAt(Method.getLocation("맵2 다이아블럭"));
	     	Block block3 = Bukkit.getWorld("world_map").getBlockAt(Method.getLocation("맵3 다이아블럭"));
	     	block1.setType(Material.DIAMOND_BLOCK);
	       	block2.setType(Material.DIAMOND_BLOCK);
	       	block3.setType(Material.DIAMOND_BLOCK);
	    	Bukkit.broadcastMessage("");
	    	Bukkit.broadcastMessage(main.p + "§6맵을 고르던 §c플레이어§6가 강퇴당하여 다시 시작합니다.");
	    	Bukkit.broadcastMessage("");
			GameData.mapWark = false;
			GameData.mapPlayer = null;
	    	
	    	for (Player player : Bukkit.getOnlinePlayers()) {
	    		player.performCommand("spawn");
	    	}
		}
		
		if (GameData.gameState)
			switch (GameData.gameNum)
			{
				case 1:
					new Running_Game().diePlayer(event.getPlayer()); break;
				case 2:
					new Anvil_Game().diePlayer(event.getPlayer()); break;
				case 3:
					new Climb_Game().OdiePlayer(event.getPlayer()); break;
				case 4:
					new Spleef_Game().diePlayer(event.getPlayer()); break;
				case 5:
					new OneKnife_Game().diePlayer(event.getPlayer()); break;
				case 6:
					new SpeedShot_Game().diePlayer(event.getPlayer()); break;
				case 7:
					new Bomb_Game().diePlayer(event.getPlayer()); break;
			}
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if (GameData.gameState) {
			PlayerDeathEvent Event0 = (PlayerDeathEvent) event;
			Player p = Event0.getEntity();
			if ((p.getKiller() instanceof Player)) {
				Player kp = (Player) p.getKiller();
				Method.addScoreYes(kp, 10, "§6플레이어를 §c살해§6하여");
				Method.addMoneyNo(kp, 50);
			}
			
			switch (GameData.gameNum)
			{
				case 1:
					new Running_Game().diePlayer(event.getEntity()); break;
				case 2:
					new Anvil_Game().diePlayer(event.getEntity()); break;
				case 3:
					new Climb_Game().OdiePlayer(event.getEntity()); break;
				case 4:
					new Spleef_Game().diePlayer(event.getEntity()); break;
				case 5:
					new OneKnife_Game().diePlayer(event.getEntity()); break;
				case 6:
					new SpeedShot_Game().diePlayer(event.getEntity()); break;
				case 7:
					new Bomb_Game().diePlayer(event.getEntity()); break;
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		double pY = player.getLocation().getBlockY();
		if (pY <= 0.0D) {
			player.setFallDistance(0.0F);
			player.performCommand("spawn");
			if (GameData.gameState)
				switch (GameData.gameNum)
				{
					case 1:
						new Running_Game().diePlayer(event.getPlayer()); break;
					case 2:
						new Anvil_Game().diePlayer(event.getPlayer()); break;
					case 3:
						new Climb_Game().OdiePlayer(event.getPlayer()); break;
					case 4:
						new Spleef_Game().diePlayer(event.getPlayer()); break;
					case 5:
						new OneKnife_Game().diePlayer(event.getPlayer()); break;
					case 6:
						new SpeedShot_Game().diePlayer(event.getPlayer()); break;
					case 7:
						new Bomb_Game().diePlayer(event.getPlayer()); break;
				}
		}
		
		if (GameData.gameState && GameData.players.contains(player.getName()) && !GameData.diePlayers.contains(player.getName())) {
            final int xFrom = event.getFrom().getBlockX();
            final int xTo = event.getTo().getBlockX();
            final int yFrom = event.getFrom().getBlockY();
            final int yTo = event.getTo().getBlockY();
            final int zFrom = event.getFrom().getBlockZ();
            final int zTo = event.getTo().getBlockZ();
            final int X = GameData.RunningLocation.getBlockX();
            final int Z = GameData.RunningLocation.getBlockZ();
            
            switch (GameData.gameNum)
            {
            	case 1:
                    if (xFrom == xTo && yFrom == yTo && zFrom == zTo) return;
                   	Block block = player.getWorld().getBlockAt(xFrom, yFrom-1, zFrom);
                    
                    if (xTo <= X+30 && xTo >= X-30) {
                    	if (zTo <= Z+30 && zTo >= Z-30) {
                        	if (block.getTypeId() == 2) {
                            	block.setType(Material.AIR);
                        	}
                    	}
                    } break;
                    
            	case 3:
                    if ((yFrom == yTo) || (yTo < 53)) break;
                    if ((xTo > 538 && xTo < 570) && (zTo > 927 && zTo < 955) && !GameData.obPlayers.contains(player.getName()))
                    	new Climb_Game().GameOver(player);
                    break;
            }
        }
	}
	
	@EventHandler
	public void onPLaunch(ProjectileLaunchEvent event)
	{
		if ((GameData.gameState) && ((event.getEntity().getShooter() instanceof Player)) && (GameData.gameState))
		{
			Player player = (Player)event.getEntity().getShooter();
			GameData.item.put(event.getEntity(), player.getItemInHand().getItemMeta().getDisplayName());
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if (!GameData.gameStart && !GameData.gameState) event.setCancelled(true);
		if (GameData.gameStart && !GameData.gameState) event.setCancelled(true);
		if ((GameData.gameState) && ((event.getEntity() instanceof Player)))
		{
			if (GameData.gameNum != 5) event.setDamage((int) 0.0D);
			Entity entity = event.getDamager();
			if (((entity instanceof Player)) && (GameData.diePlayers.contains(((Player)entity).getName()))
			 || ((entity instanceof Player)) && (GameData.obPlayers.contains(((Player)entity).getName()))) 
				event.setCancelled(true);
			else if (((entity instanceof Player)) && GameData.gameNum == 6)
				event.setCancelled(true);
			else if (((entity instanceof Player)) && GameData.gameNum == 4)
				event.setCancelled(true);
			else if (((entity instanceof Player)) && GameData.gameNum == 7)
				event.setCancelled(true);
			else if (entity instanceof Snowball && ((Snowball)entity).getShooter() instanceof Player && GameData.diePlayers.contains(((Player)((Snowball)entity).getShooter()).getName())) 
				event.setCancelled(true);
			else if (GameData.diePlayers.contains(((Player) event.getEntity()).getName()))
				event.setCancelled(true);
			else if (GameData.obPlayers.contains(((Player) event.getEntity()).getName()))
				event.setCancelled(true);

			else if ((entity instanceof FallingBlock) && (GameData.gameNum == 2)) {
        		new Anvil_Game().diePlayer((Player)event.getEntity());
        	}
			
			else if (entity instanceof Egg && ((Egg)entity).getShooter() instanceof Player) {
				if (GameData.gameStart && GameData.gameState == false) event.setCancelled(true);
				else if (GameData.gameStart && GameData.gameState) event.setCancelled(false);
			}
        	
			else if (entity instanceof Snowball && ((Snowball)entity).getShooter() instanceof Player) {
		        Player player = (Player)event.getEntity();
		        String item = (String)GameData.item.get(entity);
		        if (item != null)
		        {
		            if (item.equals(ChatColor.GREEN + "독눈덩이")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, (new Random().nextInt(5) + 5) * 20, new Random().nextInt(5)));
					}
					else if (item.equals(ChatColor.GREEN + "느려지는 눈덩이")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (new Random().nextInt(5) + 5) * 20, new Random().nextInt(5)));
					}
					else if (item.equals(ChatColor.GREEN + "어지러운 눈덩이")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (new Random().nextInt(5) + 5) * 20, new Random().nextInt(5)));
					}
					else if (item.equals(ChatColor.GREEN + "안보이는 눈덩이")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (new Random().nextInt(5) + 5) * 20, new Random().nextInt(5)));
					}
					else if ((item.equals(ChatColor.RED + "폭탄")) && 
							(((Player)((Snowball)entity).getShooter()).getName().equals(GameData.targetPlayer.getName())) && (!GameData.diePlayers.contains(player.getName())))
						new Bomb_Game().check(player);
					else return;
		        }
			}
		}
	}
	
	@EventHandler
	public void onBlockCan(BlockCanBuildEvent event) {
		
		if ((GameData.gameState) && (GameData.gameNum == 2))
		{
			if (event.getMaterial() == Material.ANVIL)
			{
				event.setBuildable(false);
			}
		}
		else if ((GameData.gameStart) && (GameData.gameNum != 3))
	    	event.setBuildable(false); 
	}
	
	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event) {
		if (GameData.gameStart && GameData.gameNum != 6)
			event.setCancelled(true); 
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (GameData.gameStart)
			event.setCancelled(true); 
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (GameData.gameState && GameData.gameNum == 4 && event.getBlock().getType() == Material.SNOW_BLOCK 
		 && !GameData.diePlayers.contains(event.getPlayer().getName()) && !GameData.obPlayers.contains(event.getPlayer().getName()))
			event.setCancelled(false);
		else {
			if (!event.getPlayer().isOp()) event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		
	    int hand = p.getItemInHand().getTypeId();
	    
	    if ((hand == 268) || (hand == 270) || (hand == 271) || (hand == 269) || (hand == 290) || 
	    		(hand == 272) || (hand == 274) || (hand == 273) || (hand == 275) || (hand == 291) || 
	    		(hand == 267) || (hand == 257) || (hand == 258) || (hand == 256) || (hand == 292) || 
	    		(hand == 283) || (hand == 285) || (hand == 286) || (hand == 284) || (hand == 294) || 
	    		(hand == 276) || (hand == 278) || (hand == 279) || (hand == 277) || (hand == 293) || 
	    		(hand == 259) || (hand == 261) || (hand == 346) || (hand == 359))
	    {
	    	p.getItemInHand().setDurability((short) 0);
	    }
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerItemBreak(PlayerItemBreakEvent e) 
	{
		e.getBrokenItem().setAmount(1);
		e.getPlayer().updateInventory(); 
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (GameData.gameStart) {
			if (!event.getPlayer().isOp()) {
				event.setCancelled(true); 
			}
		}
	}
	
	@EventHandler
	public void Sneakjump(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if (GameData.gameStart && GameData.gameNum == 6) {
			int X = p.getLocation().getBlockX();
			int Y = p.getLocation().getBlockY();
			int Z = p.getLocation().getBlockZ();
		  	Block block = p.getWorld().getBlockAt(X, Y - 1, Z);
		  	if (block.getTypeId() != 0) {
				p.setVelocity(new Vector(p.getLocation().getDirection().multiply(3).getX(),1,p.getLocation().getDirection().multiply(3).getBlockZ()));
		  	}
		}
	}
	
	@EventHandler
	public void onProjectHit(ProjectileHitEvent event) {
		switch (GameData.gameNum)
		{
			case 7:
				if (!GameData.gameState) break;
				new Bomb_Game().item(GameData.targetPlayer);
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onPlayerCommnadPreprocess(PlayerCommandPreprocessEvent e)
	{
		String[] a = e.getMessage().split(" ");
		if (!e.getPlayer().isOp())
		{
			if (GameData.gameStart || GameData.gameState || GameData.mapPlayer != null) {
				if (a[0].equalsIgnoreCase("/스폰") && a[0].equalsIgnoreCase("/넴주") && a[0].equalsIgnoreCase("/spawn")
				 && a[0].equalsIgnoreCase("/tmvhs")) {
					e.getPlayer().sendMessage(main.w + "§c게임을 진행중에는 명령어를 입력하실 수 없습니다.");
					e.setCancelled(true);
				}
			}
		}
	}
}
