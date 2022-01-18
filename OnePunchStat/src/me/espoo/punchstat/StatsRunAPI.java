package me.espoo.punchstat;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import me.espoo.file.PlayerYml;
import me.nighteyes604.LoreAttributes.LoreAttributes;

public class StatsRunAPI
{
	static PlayerYml P;
	
	@SuppressWarnings("static-access")
	public static void PlayerHealth(Player p) {
		int bonushp = (int) Math.round((P.getInfoInt(p, "º¹±Ù") + Method.get2Stat(p) + Method.get2StatEffect(p) + Method.getLoon(p)) * 0.5) + 20;
		bonushp += LoreAttributes.loreManager.getHpBonus(p);
		p.setMaxHealth(bonushp);
	}
	
	public static void PlayerHHealth(Player p) {
		int bonushp = 20;
		bonushp += LoreAttributes.loreManager.getHpBonus(p);
		p.setMaxHealth(bonushp);
	}

	@SuppressWarnings("static-access")
	public static void EntityDamage(Player p, EntityDamageByEntityEvent event) {
		event.setDamage(event.getDamage() + (int) Math.round((P.getInfoInt(p, "ÆÈ ±Ù·Â") + Method.get1Stat(p) + Method.get1StatEffect(p) + Method.getLoon(p)) * 0.3));
	}
}