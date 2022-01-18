package me.espoo.NameSings;

import org.bukkit.Location;
import org.bukkit.entity.Player;

@Deprecated
public abstract class Changer {
	private final String key;
	private final String perm;

	@Deprecated
	public Changer(String key, String permission) {
		if ((key == null) || (permission == null))
			throw new IllegalArgumentException(
					"The key and the permissions node inside the Changer constructor must not be null!");
		if (key.length() > 15)
			throw new IllegalArgumentException("The key inside the Changer constructor must not be longer then 15!");
		this.key = key;
		this.perm = permission;
	}

	public String getKey() {
		return this.key;
	}

	public String getPerm() {
		return this.perm;
	}

	public abstract String getValue(Player paramPlayer, Location paramLocation);
}