package me.espoo.pvp.ranking;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import me.espoo.pvp.API;

public class Ranking {
	public static void updateRanking() {
		UpdateRanking rank = new UpdateRanking();
		Thread thread = new Thread(rank);
		thread.start();
	}

	public static int searchRanking(String target) {
		List<String> list = new ArrayList<String>(UpdateRanking.ranking);
		for (int i = 0; i < list.size(); i++) {
			if (((String) list.get(i)).split(" ")[2].equals(target)) {
				return i;
			}
		}
		return -1;
	}

	public static void searchRanking(CommandSender sender, String name) {
		int index = searchRanking(name);
		if (index != -1) {
			index /= 10;
			displayRanking(sender, index + 1);
		} else {
			sender.sendMessage("§6해당 플레이어를 랭킹에서 찾을수가 없습니다.");
		}
	}

	public static void displayRanking(CommandSender sender, int k) {
		List<String> rank = new ArrayList<String>(UpdateRanking.ranking);
		if ((k * 10 - 9 > rank.size()) || (k == 0)) {
			sender.sendMessage("§c해당 목록은 존재하지 않습니다.");
			return;
		}
		if (rank.size() % 10 == 0)
			sender.sendMessage("§c" + rank.size() + "§6명의 유저를 찾았습니다. §c" + k + "§6/§c" + rank.size() / 10);
		else {
			sender.sendMessage("§c" + rank.size() + "§6명의 유저를 찾았습니다. §c" + k + "§6/§c" + (rank.size() / 10 + 1));
		}
		for (int j = (k - 1) * 10; j < k * 10; j++) {
			String[] var = ((String) rank.get(j)).split(" ");
			sender.sendMessage("§c" + (j + 1) + ": §6" + var[2] + " §e[Kill " + Integer.valueOf(var[0]) + "] [KD " + API.getPVPPlayera(var[2]).getKillDivDeath() + "]");
			if (rank.size() == j + 1) {
				break;
			}
			if ((k * 10 - 1 == j) && (rank.size() > j + 1))
				sender.sendMessage("§6다음 랭킹을 보려면 §c/대련 랭킹 " + (k + 1));
		}
	}
}
