package me.espoo.crafting;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main  extends JavaPlugin implements Listener {
	public void onEnable()
	{
		File folder = new File("plugins/OnePunchCrafting");
		File file = new File("plugins/OnePunchCrafting/Whitelist.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (!file.exists()) Whitelist.CreateWhitelistConfig(file, folder, config);

	    getServer().addRecipe(Recipe.getCoalBlock());
	    getServer().addRecipe(Recipe.getQuartzBlock());
	    getServer().addRecipe(Recipe.getIronZipBlock());
	    getServer().addRecipe(Recipe.getGoldZipBlock());
	    getServer().addRecipe(Recipe.getDiamondZipBlock());
	    getServer().addRecipe(Recipe.getEmeraldZipBlock());
	    getServer().addRecipe(Recipe.getRedstoneZipBlock());
	    getServer().addRecipe(Recipe.getLapisZipBlock());
	    getServer().addRecipe(Recipe.getCoalZipBlock());
	    getServer().addRecipe(Recipe.getQuartzZipBlock());
	    getServer().addRecipe(Recipe.getunIronBlock());
	    getServer().addRecipe(Recipe.getunGoldBlock());
	    getServer().addRecipe(Recipe.getunDiamondBlock());
	    getServer().addRecipe(Recipe.getunEmeraldBlock());
	    getServer().addRecipe(Recipe.getunRedstoneBlock());
	    getServer().addRecipe(Recipe.getunLapisBlock());
	    getServer().addRecipe(Recipe.getunCoalBlock());
	    getServer().addRecipe(Recipe.getunQuartzBlock());
	    getServer().addRecipe(Recipe.getunCoal());
	    getServer().addRecipe(Recipe.getunQuartz());
		getServer().getPluginManager().registerEvents(new mainEvent(this), this);
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
}
