package me.shinkhan.ender;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
    
	public void onEnable()
	{
		 Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			 public void run() {
				 int i = Method.getConfigInt() - 5;
				 if (i <= -5) {
					 Method.setConfigInt(0);
					 return;
				 }
				 
				 else if (i == 0) {
					 Bukkit.broadcastMessage("");
					 Bukkit.broadcastMessage("§f[§4알림§f] §c엔더드래곤§6이 다시 소환되었습니다!");
					 Bukkit.broadcastMessage("");
					 setZombieStats((LivingEntity) Bukkit.getWorld("world_the_end").spawnEntity(new Location(Bukkit.getWorld("world_the_end"), 35D, 84D, 26D), EntityType.ENDER_DRAGON));
					 Method.setConfigInt(0);
					 return;
				 }
				 
				 else {
					 Method.setConfigInt(i);
					 return;
				 }
			 }
		 }, 6000L, 6000L);
		 
		 File f = new File("plugins/DHEnderSet/config.yml");
		 File folder = new File("plugins/DHEnderSet");
		 YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		 if (!f.exists()) Method.Createconfig(f, folder, config);
			
		 Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "killall enderdragon world_the_end");
		 Bukkit.getWorld("world_the_end").spawn(new Location(Bukkit.getWorld("world_the_end"), 35D, 84D, 26D), EnderDragon.class);
		 getServer().getPluginManager().registerEvents(this, this);
		 PluginDescriptionFile pdFile = getDescription();
		 Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		 Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	
	public void onDisable()
	{
		Method.setConfigInt(0);
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "killall enderdragon world_the_end");
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
	public void setZombieStats(LivingEntity entity) {
		entity.setMaxHealth(1000);
		entity.setHealth(1000);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager().getType().equals(EntityType.ENDER_DRAGON)) {
			e.setDamage(80);
		}
	}

	@EventHandler
	public void FountainCreation(EntityCreatePortalEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void PlayerInteractEgg(PlayerInteractEvent event) {
		Block block = event.getClickedBlock();
		if (((event.getAction() == Action.RIGHT_CLICK_BLOCK) || (event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK)) && (block != null) && (block.getTypeId() == 122))
			event.setCancelled(true);
	}
	
	@EventHandler
	public void DragonDeath(EntityDeathEvent event) {
		if (event.getEntity().getKiller() == null) return;
		if ((event.getEntityType() == EntityType.ENDER_DRAGON) && (event.getEntity().getKiller().getType() == EntityType.PLAYER)) {
			Player k = event.getEntity().getKiller();
			String kn = k.getName();
			
			k.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DRAGON_EGG) });
			
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage("§f[§4알림§f] §c" + kn + "§6 님이 §c엔더드래곤§6을 사냥하셨습니다!");
			Bukkit.broadcastMessage("§f[§4알림§f] 엔더드래곤은 60분 후에 다시 뜹니다.");
			Bukkit.broadcastMessage("");
			Method.setConfigInt(70);
			
			event.setDroppedExp(0);
		}
	}
}
