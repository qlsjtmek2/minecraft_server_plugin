package me.espoo.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.events.SpellCastEvent;
import com.nisovin.magicspells.mana.ManaHandler;
import com.nisovin.magicspells.zones.NoMagicZoneManager;

import yt.codebukkit.scoreboardapi.Scoreboard;
import yt.codebukkit.scoreboardapi.ScoreboardAPI;

public class main extends JavaPlugin implements Listener {
	public static org.bukkit.scoreboard.Scoreboard sb;
	  
	public void onEnable()
	{
		sb = getServer().getScoreboardManager().getMainScoreboard();
		getServer().getPluginManager().registerEvents(this, this);
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		ScoreboardAPI sa = ScoreboardAPI.getInstance();
		Player p = e.getPlayer();
		
		if (sa.getScoreboard(p.getName()) != null) {
			sa.removeScoreboard(sa.getScoreboard(p.getName()));
			Scoreboard b = sa.createScoreboard(p.getName(), 10);
			b.setScoreboardName("§7§l『 §a§l스킬 쿨타임 §7§l』");
		} else {
			Scoreboard b = sa.createScoreboard(p.getName(), 10);
			b.setScoreboardName("§7§l『 §a§l스킬 쿨타임 §7§l』");
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		ScoreboardAPI sa = ScoreboardAPI.getInstance();
		Player p = e.getPlayer();
		
		if (sa.getScoreboard(p.getName()) != null) {
			sa.removeScoreboard(sa.getScoreboard(p.getName()));
		}
	}
	
	
	@EventHandler(ignoreCancelled=true)
	public void onSpellCast(SpellCastEvent e)
	{
		Player p = e.getCaster();
		ScoreboardAPI sa = ScoreboardAPI.getInstance();
		int cooldown = (int) e.getCooldown();
		ManaHandler mana = MagicSpells.getManaHandler();
		Spell spell = e.getSpell();
		if (cooldown > 999) return;
		
		NoMagicZoneManager nmzm = MagicSpells.getNoMagicZoneManager();
		if (nmzm != null && nmzm.willFizzle(p, spell)) return;
		
		if (cooldown > 0 && spell.getCooldown(p) <= 0 && mana.hasMana(p, spell.getReagents().getMana())) {
			if (sa.getScoreboard(p.getName()) != null) {
				String name = spell.getInternalName().replaceAll("_", " ");
				Scoreboard b = sa.getScoreboard(p.getName());
				
				PlayerTimer timer = new PlayerTimer(p, name, b);
				timer.setTime(cooldown + 1);
				timer.Start();
			}
		}
	}
}
