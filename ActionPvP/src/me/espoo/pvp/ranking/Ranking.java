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
			sender.sendMessage("��6�ش� �÷��̾ ��ŷ���� ã������ �����ϴ�.");
		}
	}

	public static void displayRanking(CommandSender sender, int k) {
		List<String> rank = new ArrayList<String>(UpdateRanking.ranking);
		if ((k * 10 - 9 > rank.size()) || (k == 0)) {
			sender.sendMessage("��c�ش� ����� �������� �ʽ��ϴ�.");
			return;
		}
		if (rank.size() % 10 == 0)
			sender.sendMessage("��c" + rank.size() + "��6���� ������ ã�ҽ��ϴ�. ��c" + k + "��6/��c" + rank.size() / 10);
		else {
			sender.sendMessage("��c" + rank.size() + "��6���� ������ ã�ҽ��ϴ�. ��c" + k + "��6/��c" + (rank.size() / 10 + 1));
		}
		for (int j = (k - 1) * 10; j < k * 10; j++) {
			String[] var = ((String) rank.get(j)).split(" ");
			sender.sendMessage("��c" + (j + 1) + ": ��6" + var[2] + " ��e[Kill " + Integer.valueOf(var[0]) + "] [KD " + API.getPVPPlayera(var[2]).getKillDivDeath() + "]");
			if (rank.size() == j + 1) {
				break;
			}
			if ((k * 10 - 1 == j) && (rank.size() > j + 1))
				sender.sendMessage("��6���� ��ŷ�� ������ ��c/��� ��ŷ " + (k + 1));
		}
	}
}
