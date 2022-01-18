package me.espoo.pvp.ranking;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Bukkit;

import me.espoo.pvp.API;
import me.espoo.pvp.yml.PVPPlayer;

public class UpdateRanking implements Runnable {
	public static List<String> ranking = new ArrayList<String>();

	public void run() {
		long time = System.currentTimeMillis();
		Set<PVPPlayer> set = new TreeSet<PVPPlayer>(new PvPComparator<PVPPlayer>());
		for (PVPPlayer player : API.pvpplayer.values()) {
			set.add(player);
		}
		UpdateRanking.ranking.clear();
		for (PVPPlayer player : set) {
			UpdateRanking.ranking.add(player.getKill() + " a " + player.getName());
		}
		double delay = (System.currentTimeMillis() - time) / 1000.0D;
		Bukkit.broadcastMessage("§c[ActionPvP] §6유저 랭킹 업데이트 완료. 소요시간: " + delay + "초");
	}
}
