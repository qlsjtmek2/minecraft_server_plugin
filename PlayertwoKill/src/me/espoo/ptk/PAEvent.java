package me.espoo.ptk;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class PAEvent extends JavaPlugin implements Listener {
	private final main plugin;

	public PAEvent(main instance)
	{
		this.plugin = instance;
	}
	@EventHandler
	public void onPlayerJoinEvent(PlayerLoginEvent e) {
		try {
			if (!main.command2.PlayerInfoB(e.getPlayer().getName()))
				main.command2.CreateNewInfo(e.getPlayer().getName());
		}
		catch (IOException ex) {
			Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) {
		main.command2.PVPTime.put(e.getPlayer().getName(), "");

		String[] Spawn3 = this.plugin.getConfig().getString("PATele4").split(", ");
		double Spawn3x = Double.parseDouble(Spawn3[0]);
		double Spawn3y = Double.parseDouble(Spawn3[1]);
		double Spawn3z = Double.parseDouble(Spawn3[2]);
		float Spawn3yaw = Float.parseFloat(Spawn3[3]);
		float Spawn3pitch = Float.parseFloat(Spawn3[4]);
		World Spawn3world = Bukkit.getWorld(Spawn3[5]);
		try {
			if (main.command2.PlayerInfoS(e.getPlayer().getName(), "QUIT").equals("O")) {
				Location loc1 = new Location(Spawn3world, Spawn3x, Spawn3y, Spawn3z, Spawn3yaw, Spawn3pitch);
				e.getPlayer().teleport(loc1);
				main.command2.PlayerInfoSave(e.getPlayer().getName(), "QUIT", "X");
			}
		} catch (IOException ex) {
			Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		int lasttime = this.plugin.getConfig().getInt("PVPLastExitTime");
		if (main.command2.Start)
		{
			int Death;
			if (e.getPlayer().getName() == main.command2.blue) {
				Player op = Bukkit.getPlayer(main.command2.red);
				for (Player ap : Bukkit.getOnlinePlayers()) {
					main.command2.PVPTime.put(ap.getName(), "");
				}
				
				ItemStack item = new ItemStack(Material.EXP_BOTTLE, 4);
				op.getInventory().addItem(new ItemStack[] { item });
				op.sendMessage("¡×c" + lasttime + "¡×6ÃÊµÚ ½ºÆùÀ¸·Î ¡×cÀÚµ¿ ÀÌµ¿¡×6µË´Ï´Ù.");
				main.command2.PVPTime.put(op.getName(), "LastExitTime, " + op.getName() + ", " + (lasttime + 1));
				try {
					int Kill = main.command2.PlayerInfo(main.command2.red, "KILL");
					Death = main.command2.PlayerInfo(e.getPlayer().getName(), "DEATH");
					main.command2.PlayerInfoSave(main.command2.red, "KILL", Kill + 1);
					main.command2.PlayerInfoSave(e.getPlayer().getName(), "DEATH", Death + 1);
					main.command2.PlayerInfoSave(e.getPlayer().getName(), "QUIT", "O");
				} catch (IOException ex) {
					Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, (Throwable)ex);
				}
			} else if (e.getPlayer().getName() == main.command2.red) {
				Player op = Bukkit.getPlayer(main.command2.blue);
				for (Player ap : Bukkit.getOnlinePlayers()) {
					main.command2.PVPTime.put(ap.getName(), "");
				}
				
				ItemStack item = new ItemStack(Material.EXP_BOTTLE, 4);
				op.getInventory().addItem(new ItemStack[] { item });
				op.sendMessage("¡×c" + lasttime + "¡×6ÃÊµÚ ½ºÆùÀ¸·Î ¡×cÀÚµ¿ ÀÌµ¿¡×6µË´Ï´Ù.");
				main.command2.PVPTime.put(op.getName(), "LastExitTime, " + op.getName() + ", " + (lasttime + 1));
				try {
					int Kill = main.command2.PlayerInfo(op.getName(), "KILL");
					int Death1 = main.command2.PlayerInfo(e.getPlayer().getName(), "DEATH");
					main.command2.PlayerInfoSave(op.getName(), "KILL", Kill + 1);
					main.command2.PlayerInfoSave(e.getPlayer().getName(), "DEATH", Death1 + 1);
					main.command2.PlayerInfoSave(e.getPlayer().getName(), "QUIT", "O");
				} catch (IOException ex) {
					Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		int lasttime = this.plugin.getConfig().getInt("PVPLastExitTime");
		int Death;
		if (main.command2.blue.equals(e.getEntity().getName())) {
			Player op = Bukkit.getPlayer(main.command2.red);
			if (main.command2.Start) {
				for (Player ap : Bukkit.getOnlinePlayers()) {
					main.command2.PVPTime.put(ap.getName(), "");
				}
				
				ItemStack item = new ItemStack(Material.EXP_BOTTLE, 4);
				op.getInventory().addItem(new ItemStack[] { item });
				op.sendMessage("¡×c" + lasttime + "¡×6ÃÊµÚ ½ºÆùÀ¸·Î ¡×cÀÚµ¿ ÀÌµ¿¡×6µË´Ï´Ù.");
				main.command2.PVPTime.put(op.getName(), "LastExitTime, " + main.command2.blue + ", " + (lasttime + 1));
			}
			try {
				int Kill = main.command2.PlayerInfo(op.getName(), "KILL");
				Death = main.command2.PlayerInfo(e.getEntity().getName(), "DEATH");
				main.command2.PlayerInfoSave(op.getName(), "KILL", Kill + 1);
				main.command2.PlayerInfoSave(e.getEntity().getName(), "DEATH", Death + 1);
			} catch (IOException ex) {
				Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, (Throwable)ex);
			}
		}
		else if (main.command2.red.equals(e.getEntity().getName())) {
			Player op = Bukkit.getPlayer(main.command2.blue);
			if (main.command2.Start) {
				for (Player ap : Bukkit.getOnlinePlayers()) {
					main.command2.PVPTime.put(ap.getName(), "");
				}
				
				ItemStack item = new ItemStack(Material.EXP_BOTTLE, 4);
				op.getInventory().addItem(new ItemStack[] { item });
				op.sendMessage("¡×c" + lasttime + "¡×6ÃÊµÚ ½ºÆùÀ¸·Î ¡×cÀÚµ¿ ÀÌµ¿¡×6µË´Ï´Ù.");
				main.command2.PVPTime.put(op.getName(), "LastExitTime, " + main.command2.blue + ", " + (lasttime + 1));
			}
			try {
				int Kill = main.command2.PlayerInfo(op.getName(), "KILL");
				int Death1 = main.command2.PlayerInfo(e.getEntity().getName(), "DEATH");
				main.command2.PlayerInfoSave(op.getName(), "KILL", Kill + 1);
				main.command2.PlayerInfoSave(e.getEntity().getName(), "DEATH", Death1 + 1);
			} catch (IOException ex) {
				Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;
		Player damager = (Player)event.getDamager();
		if (main.command2.GodTime) {
			if (damager.getName() == main.command2.blue) event.setCancelled(true);
			else if (damager.getName() == main.command2.red) event.setCancelled(true);
		}
		if (main.command2.Start) {
			String[] a = ((String)main.command2.PVPTime.get(damager.getName())).split(", ");
			if (a[0].equals("LastExitTime")) event.setCancelled(true); 
		}
	}

	@EventHandler
	public void onMovePlayer(PlayerMoveEvent event) {
		String[] Spawn1 = this.plugin.getConfig().getString("PATele1").split(", ");
		String[] Spawn2 = this.plugin.getConfig().getString("PATele2").split(", ");

	    double Spawn1x = Double.parseDouble(Spawn1[0]);
	    double Spawn1y = Double.parseDouble(Spawn1[1]);
	    double Spawn1z = Double.parseDouble(Spawn1[2]);
	    float Spawn1yaw = Float.parseFloat(Spawn1[3]);
	    float Spawn1pitch = Float.parseFloat(Spawn1[4]);
	    double Spawn2x = Double.parseDouble(Spawn2[0]);
	    double Spawn2y = Double.parseDouble(Spawn2[1]);
	    double Spawn2z = Double.parseDouble(Spawn2[2]);
	    float Spawn2yaw = Float.parseFloat(Spawn2[3]);
	    float Spawn2pitch = Float.parseFloat(Spawn2[4]);
		World Spawn2world = Bukkit.getWorld(Spawn2[5]);

	    if (main.command2.GodTime)
	    	if (event.getPlayer().getName() == main.command2.blue) {
	    		Location loc = new Location(Spawn2world, Spawn1x, Spawn1y, Spawn1z, Spawn1yaw, Spawn1pitch);
	    		event.getPlayer().teleport(loc);
	    	} else if (event.getPlayer().getName() == main.command2.red) {
	    		Location loc = new Location(Spawn2world, Spawn2x, Spawn2y, Spawn2z, Spawn2yaw, Spawn2pitch);
	    		event.getPlayer().teleport(loc);
	    	}
	}

	@EventHandler
	public void onMovePlayer(InventoryClickEvent e) {
		if (main.command2.Start)
			if (e.getWhoClicked().getName().equals(main.command2.blue)) e.setCancelled(true);
			else if (e.getWhoClicked().getName().equals(main.command2.red)) e.setCancelled(true); 
	}

	@EventHandler
	public void onPlayerCommnadPreprocess(PlayerCommandPreprocessEvent e)
	{
		String[] a = e.getMessage().split(" ");
		if (main.command2.Start)
			if (((e.getPlayer().getName().equals(main.command2.blue)) || (e.getPlayer().getName().equals(main.command2.red)))) {
				if (!(a[0].equalsIgnoreCase("/µµ¿ò¸»")) && !(a[0].equalsIgnoreCase("/heal")) && !(a[0].equalsIgnoreCase("/gm")) && !(a[0].equalsIgnoreCase("/give")) && !(a[0].equalsIgnoreCase("/cast")) 
				 && !(a[0].equalsIgnoreCase("/c")) && !(a[0].equalsIgnoreCase("/fly")) && !(a[0].equalsIgnoreCase("/ÄíÅ°Áö±ÞÀÌ´ç41RSA")) && !(a[0].equalsIgnoreCase("/±¸¸§¸ÀÄíÅ°41RSA")) 
				 && !(a[0].equalsIgnoreCase("/µþ±â¸ÀÄíÅ°41RSA")) && !(a[0].equalsIgnoreCase("/Ä¿ÇÇ¸ÀÄíÅ°41RSA")) && !(a[0].equalsIgnoreCase("/¿ë»ç¸ÀÄíÅ°41RSA")) && !(a[0].equalsIgnoreCase("/Á»ºñ¸ÀÄíÅ°41RSA"))
				 && !(a[0].equalsIgnoreCase("/Ãµ»ç¸ÀÄíÅ°41RSA")) && !(a[0].equalsIgnoreCase("/´ÑÀÚ¸ÀÄíÅ°41RSA")) && !(a[0].equalsIgnoreCase("/¸¶¹ý»ç¸ÀÄíÅ°41RSA")) && !(a[0].equalsIgnoreCase("/¾Ç¸¶¸ÀÄíÅ°41RSA"))
				 && !(a[0].equalsIgnoreCase("/¼Ò´Ù¸ÀÄíÅ°41RSA")) && !(a[0].equalsIgnoreCase("/ÄÚÄÚ¾Æ¸ÀÄíÅ°41RSA")) && !(a[0].equalsIgnoreCase("/·¹¸ó¸ÀÄíÅ°41RSA")) && !(a[0].equalsIgnoreCase("/ÇÎÅ©ÃÊÄÚÄíÅ°41RSA"))
				 && !(a[0].equalsIgnoreCase("/Å°À§¸ÀÄíÅ°41RSA")) && !(a[0].equalsIgnoreCase("/¶ô½ºÅ¸¸ÀÄíÅ°41RSA")) && !(a[0].equalsIgnoreCase("/¿äÁ¤¸ÀÄíÅ°41RSA")) && !(a[0].equalsIgnoreCase("/Æ¯Àü»ç¸ÀÄíÅ°41RSA"))
				 && !(a[0].equalsIgnoreCase("/´ëÃß¸ÀÄíÅ°41RSA")) && !(a[0].equalsIgnoreCase("/ºÒ²ÉÁ¤·ÉÄíÅ°41RSA")) && !(a[0].equalsIgnoreCase("/´Þºû¼ú»çÄíÅ°41RSA")) && !(a[0].equalsIgnoreCase("/fs"))
				 && !(a[0].equalsIgnoreCase("/´ë·Ã"))) {
					e.getPlayer().sendMessage("¡×c´ë·ÃÁß¿¡´Â ÇØ´ç ¸í·É¾î¸¦ »ç¿ëÇÒ ¼ö ¾ø½À´Ï´Ù.");
					e.setCancelled(true);
				}
			}
	}
}