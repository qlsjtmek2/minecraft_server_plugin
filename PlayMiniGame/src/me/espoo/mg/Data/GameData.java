package me.espoo.mg.Data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;

import me.confuser.barapi.BarAPI;
import me.espoo.mg.Method;
import me.espoo.mg.Game.Anvil_Game;
import me.espoo.mg.Game.Bomb_Game;
import me.espoo.mg.Game.Climb_Game;
import me.espoo.mg.Game.OneKnife_Game;
import me.espoo.mg.Game.Running_Game;
import me.espoo.mg.Game.SpeedShot_Game;
import me.espoo.mg.Game.Spleef_Game;

public class GameData {
	public static final Map <String, Integer> taskIds = new HashMap<>();
	public static boolean gameStart = false;
	public static boolean gameState = false;
	public static boolean knifeStart = false;
	public static boolean mapWark = false;
	public static String mapPlayer = null;
    public static ArrayList<String> players = new ArrayList<String>();
    public static ArrayList<String> diePlayers = new ArrayList<String>();
    public static ArrayList<String> obPlayers = new ArrayList<String>();
    public static Location RunningLocation = Method.getLocation("런닝 게임 스폰");
    public static Location AnvilLocation = Method.getLocation("모루피하기 게임 스폰");
    public static Location ClimbLocation = Method.getLocation("등반 게임 스폰");
    public static HashMap<Projectile, String> item = new HashMap<Projectile, String>();
    public static World world;
    public static int gameNum = 0;
    public static int mapNum = 0;
    public static int count = 0;
    public static int playerNum = 0;
    public static int gameTimer = 0;
    public static int schedulerID = -1;
    public static int bombTime = 7;
    public static Player targetPlayer;
    
	public static void GameStart(int gameNum)
	{
		gameStart = true;
		mapPlayer = null;
		switch (gameNum)
		{
			case 1:
				new Running_Game().GameStart(); break;
			case 2:
				new Anvil_Game().GameStart(); break;
			case 3:
				new Climb_Game().GameStart(); break;
			case 4:
				new Spleef_Game().GameStart(); break;
			case 5:
				new OneKnife_Game().GameStart(); break;
			case 6:
				new SpeedShot_Game().GameStart(); break;
			case 7:
				new Bomb_Game().GameStart(); break;
		}
	}
	
    public static void GameStop() {
     	Block block1 = Bukkit.getWorld("world_map").getBlockAt(Method.getLocation("맵1 다이아블럭"));
     	Block block2 = Bukkit.getWorld("world_map").getBlockAt(Method.getLocation("맵2 다이아블럭"));
     	Block block3 = Bukkit.getWorld("world_map").getBlockAt(Method.getLocation("맵3 다이아블럭"));
     	block1.setType(Material.DIAMOND_BLOCK);
       	block2.setType(Material.DIAMOND_BLOCK);
       	block3.setType(Material.DIAMOND_BLOCK);
        GameData.gameStart = false;
        GameData.gameState = false;
        GameData.knifeStart = false;
        GameData.mapWark = false;
        GameData.mapPlayer = null;
        GameData.gameTimer = 0;
        GameData.bombTime = 5;
        GameData.players.clear();
        GameData.diePlayers.clear();
        GameData.obPlayers.clear();
        if (GameData.schedulerID != -1) {
    		Bukkit.getScheduler().cancelTask(GameData.schedulerID);
    		GameData.schedulerID = -1;	
        }
	    Method.SetMap();
		Integer id = GameData.taskIds.remove("Start");
		if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
        
    	try { Thread.sleep(2000L); } catch (InterruptedException e) {}
        switch (GameData.gameNum)
        {
        	case 1:
        		File rf = new File("plugins/PlayMiniGame/schematics/running.schematic");
            	try { GameData.loadIslandSchematic(Bukkit.getWorld("world"), rf, new Location(Bukkit.getWorld("world"), 1061D, 19D, 614D)); } catch (MaxChangedBlocksException | DataException | IOException e1) {}
        		break;
        	case 2:
        		File af = new File("plugins/PlayMiniGame/schematics/anvil.schematic");
            	try { GameData.loadIslandSchematic(Bukkit.getWorld("world"), af, new Location(Bukkit.getWorld("world"), 1015D, 28D, 979D)); } catch (MaxChangedBlocksException | DataException | IOException e1) {}
        		break;
        	case 3:
        		File cf = new File("plugins/PlayMiniGame/schematics/climb.schematic");
            	try { GameData.loadIslandSchematic(Bukkit.getWorld("world"), cf, new Location(Bukkit.getWorld("world"), 554D, 41D, 941D)); } catch (MaxChangedBlocksException | DataException | IOException e1) {}
        		break;
        	case 4:
        		File sf = new File("plugins/PlayMiniGame/schematics/spleef.schematic");
            	try { GameData.loadIslandSchematic(Bukkit.getWorld("world"), sf, new Location(Bukkit.getWorld("world"), 1212D, 59D, 841D)); } catch (MaxChangedBlocksException | DataException | IOException e1) {}
        		break;
           	case 5:
        		break;
           	case 6:
        		break;
           	case 7:
           		break;
        }
        GameData.gameNum = 0;
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.getInventory().clear();
			player.setFoodLevel(20);
			player.setHealth(20);
			player.getInventory().setHelmet(null);
			player.getInventory().setBoots(null);
			player.getInventory().setChestplate(null);
			player.getInventory().setLeggings(null);
			player.performCommand("spawn");
			BarAPI.removeBar(player);
			if (player.getGameMode() != GameMode.CREATIVE) player.setAllowFlight(false);
			for (PotionEffect effect : player.getActivePotionEffects())
				player.removePotionEffect(effect.getType());
        }
    }
    
    public void check() {
        ++GameData.count;
        Item();
        DiePlayer();
        Bomb();
    }
    
    private void DiePlayer() {
    	if (GameData.diePlayers == null) return;
        for (final String playerName : GameData.diePlayers) {
            final Player player = Bukkit.getPlayerExact(playerName);
            if (player != null) {
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 0));
            }
        }
        
        for (final String playerName : GameData.obPlayers) {
            final Player player = Bukkit.getPlayerExact(playerName);
            if (player != null) {
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 0));
            }
        }
    }
    
    private void Item() {
    	if (GameData.gameState) {
    		if (GameData.gameNum == 1) {
    			if (GameData.count >= 30) {
    				GameData.count = 0;
    				ItemData.item();
    			}
    		}
    		
    		if (GameData.gameNum == 2) {
    			if (GameData.count >= 20) {
    				GameData.count = 0;
    				ItemData.item();
    			}
    		}
    		
    		if (GameData.gameNum == 3) {
    			if (GameData.count >= 30) {
    				GameData.count = 0;
    				ItemData.item();
    			}
    		}
    		
    		if (GameData.gameNum == 4) {
    			if (GameData.count >= 30) {
    				GameData.count = 0;
    				ItemData.Speeditem();
    			}
    		}
    		
    		if (GameData.gameNum == 6) {
    			if (GameData.count >= 30) {
    				GameData.count = 0;
    				ItemData.Speeditem();
    			}
    		}
    	}
    }
    
    private void Bomb() {
    	switch (gameNum)
    	{
    		case 7:
    			bombTime -= 1;
    			if (!GameData.gameState) 
    				for (Player player : Bukkit.getOnlinePlayers()) BarAPI.removeBar(player);
    			if (bombTime < 1) {
    				new Bomb_Game().diePlayer(targetPlayer);
    				bombTime = 7; 
    				break;
    			} else {
    		    	for (Player player : Bukkit.getOnlinePlayers()) {
        				BarAPI.setMessage(player, "§f[ §a§l" + targetPlayer.getName() + " §f] §6폭탄 타이머: §c" + GameData.bombTime + "초");
    		    	}
    			}
    	}
    }
    
    public static boolean loadIslandSchematic(World world, File file, Location origin) throws DataException, IOException, MaxChangedBlocksException {
        Vector v = new Vector(origin.getBlockX(), origin.getBlockY(), origin.getBlockZ());
        SchematicFormat format = SchematicFormat.getFormat(file);
        if (format == null)
        {
        	return false;
        }
        EditSession es = new EditSession(new BukkitWorld(world), 999999999);
        CuboidClipboard cc = format.load(file);
        cc.paste(es, v, false);
        return true;
    }
}
