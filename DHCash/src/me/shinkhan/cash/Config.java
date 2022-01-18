package me.shinkhan.cash;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Config extends YamlConfiguration {
	public void setLocation(String n, Location l) {
		set(n, "org.bukkit.Location|" + l.getWorld().getName() + "|" + Double.toString(l.getX()) + "|"
				+ Double.toString(l.getY()) + "|" + Double.toString(l.getZ()) + "|" + Float.toString(l.getYaw()) + "|"
				+ Float.toString(l.getPitch()));
	}

	public boolean isLocation(String n) {
		return getString(n).startsWith("org.bukkit.Location|");
	}

	public Location getLocation(String n) {
		String[] nsplit = getString(n).split("\\|");
		return new Location(Bukkit.getWorld(nsplit[1]), Double.parseDouble(nsplit[2]), Double.parseDouble(nsplit[3]),
				Double.parseDouble(nsplit[4]), Float.parseFloat(nsplit[5]), Float.parseFloat(nsplit[6]));
	}

	public static Inventory loadInventoryFromYaml(File file) throws IOException, InvalidConfigurationException {
		YamlConfiguration yaml = new Config();
		yaml.load(file);

		int inventorySize = yaml.getInt("size", 54);
		Inventory inventory = Bukkit.getServer().createInventory(null, inventorySize);

		ConfigurationSection items = yaml.getConfigurationSection("items");
		for (int slot = 0; slot < inventorySize; slot++) {
			String slotString = String.valueOf(slot);
			if (items.isItemStack(slotString)) {
				ItemStack itemStack = items.getItemStack(slotString);
				inventory.setItem(slot, itemStack);
			}
		}

		return inventory;
	}

	public static void saveInventoryToYaml(Inventory inventory, File file) throws IOException {
		YamlConfiguration yaml = new Config();
		int inventorySize = inventory.getSize();
		yaml.set("size", Integer.valueOf(inventorySize));

		ConfigurationSection items = yaml.createSection("items");
		for (int slot = 0; slot < inventorySize; slot++) {
			ItemStack stack = inventory.getItem(slot);
			if (stack != null) {
				items.set(String.valueOf(slot), stack);
			}
		}

		yaml.save(file);
	}
}