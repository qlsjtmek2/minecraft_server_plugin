package me.espoo.oneman;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class RideAPI {
	public static boolean playerCanRide(Player player)
	{
		return (player.getPassenger() == null);
	}

	public static boolean duckEjectPassenger(Player duck, Entity passenger) {
		if (passenger.equals(duck.getPassenger())) {
			duck.eject();
			
			return true;
		}

		return false;
	}

	public static Player getRootVehicle(Player vehicle) {
		while (getVehicle(vehicle) != null) {
			vehicle = getVehicle(vehicle);
		}

		return vehicle;
	}

	public static Player getLastPassenger(Player vehicle) {
		while ((vehicle.getPassenger() != null) && ((vehicle.getPassenger() instanceof Player))) {
			vehicle = (Player)vehicle.getPassenger();
		}

		return vehicle;
	}

	public static Player getVehicle(Player player) {
		for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
			Entity passenger = onlinePlayer.getPassenger();
	      
			if (((passenger instanceof Player)) && (passenger.getEntityId() == player.getEntityId())) {
				return onlinePlayer;
			}
		}

		return null;
	}
}
