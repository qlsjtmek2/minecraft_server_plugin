package me.espoo.mg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Ranking {
	public static void RankingStat(Player p) {
		int score = Method.getInfoInt(p, "점수");
		int one = Method.getRankingI("one");
		int two = Method.getRankingI("two");
		int three = Method.getRankingI("three");
		int four = Method.getRankingI("four");
		int five = Method.getRankingI("five");
		String oneName = Method.getRankingS("oneName");
		String twoName = Method.getRankingS("twoName");
		String threeName = Method.getRankingS("threeName");
		String fourName = Method.getRankingS("fourName");
		String fiveName = Method.getRankingS("fiveName");
		
		if (!fiveName.equalsIgnoreCase(p.getName()) && !fourName.equalsIgnoreCase(p.getName()) && 
			!threeName.equalsIgnoreCase(p.getName()) && !oneName.equalsIgnoreCase(p.getName())) {
			if (score > one)
			{
				int oneJunkI = Method.getRankingI("one");
				String oneJunkS = Method.getRankingS("oneName");
				int twoJunkI = Method.getRankingI("two");
				String twoJunkS = Method.getRankingS("twoName");
				int threeJunkI = Method.getRankingI("three");
				String threeJunkS = Method.getRankingS("threeName");
				int fourJunkI = Method.getRankingI("four");
				String fourJunkS = Method.getRankingS("fourName");
				
				if (oneJunkS.equalsIgnoreCase(p.getName())) oneJunkS = "NONE";
				else if (twoJunkS.equalsIgnoreCase(p.getName())) twoJunkS = "NONE";
				else if (threeJunkS.equalsIgnoreCase(p.getName())) threeJunkS = "NONE";
				else if (fourJunkS.equalsIgnoreCase(p.getName())) fourJunkS = "NONE";
					
				Method.setRankingI("one", score);
				Method.setRankingS("oneName", p.getName());
				Method.setRankingI("two", oneJunkI);
				Method.setRankingS("twoName", oneJunkS);
				Method.setRankingI("three", twoJunkI);
				Method.setRankingS("threeName", twoJunkS);
				Method.setRankingI("four", threeJunkI);
				Method.setRankingS("fourName", threeJunkS);
				Method.setRankingI("five", fourJunkI);
				Method.setRankingS("fiveName", fourJunkS);
				Bukkit.broadcastMessage("§f[§4알림§f] §b스코어 랭킹 1위가 §e" + p.getName() + " §b님으로 변경되었습니다!");
				return;
			}
		}
			
		if (!fiveName.equalsIgnoreCase(p.getName()) && !fourName.equalsIgnoreCase(p.getName()) && 
			!twoName.equalsIgnoreCase(p.getName()) && !oneName.equalsIgnoreCase(p.getName())) {
			if (score > two && score <= one)
			{
				int twoJunkI = Method.getRankingI("two");
				String twoJunkS = Method.getRankingS("twoName");
				int threeJunkI = Method.getRankingI("three");
				String threeJunkS = Method.getRankingS("threeName");
				int fourJunkI = Method.getRankingI("four");
				String fourJunkS = Method.getRankingS("fourName");
				
				if (twoJunkS.equalsIgnoreCase(p.getName())) twoJunkS = "NONE";
				else if (threeJunkS.equalsIgnoreCase(p.getName())) threeJunkS = "NONE";
				else if (fourJunkS.equalsIgnoreCase(p.getName())) fourJunkS = "NONE";
					
				Method.setRankingI("two", score);
				Method.setRankingS("twoName", p.getName());
				Method.setRankingI("three", twoJunkI);
				Method.setRankingS("threeName", twoJunkS);
				Method.setRankingI("four", threeJunkI);
				Method.setRankingS("fourName", threeJunkS);
				Method.setRankingI("five", fourJunkI);
				Method.setRankingS("fiveName", fourJunkS);
				Bukkit.broadcastMessage("§f[§4알림§f] §b스코어 랭킹 2위가 §e" + p.getName() + " §b님으로 변경되었습니다!");
				return;
			}
		}
			
		if (!fiveName.equalsIgnoreCase(p.getName()) && !threeName.equalsIgnoreCase(p.getName()) && 
			!twoName.equalsIgnoreCase(p.getName()) && !oneName.equalsIgnoreCase(p.getName())) {
			if (score > three && score <= two)
			{
				int threeJunkI = Method.getRankingI("three");
				String threeJunkS = Method.getRankingS("threeName");
				int fourJunkI = Method.getRankingI("four");
				String fourJunkS = Method.getRankingS("fourName");
				
				if (threeJunkS.equalsIgnoreCase(p.getName())) threeJunkS = "NONE";
				else if (fourJunkS.equalsIgnoreCase(p.getName())) fourJunkS = "NONE";
					
				Method.setRankingI("three", score);
				Method.setRankingS("threeName", p.getName());
				Method.setRankingI("four", threeJunkI);
				Method.setRankingS("fourName", threeJunkS);
				Method.setRankingI("five", fourJunkI);
				Method.setRankingS("fiveName", fourJunkS);
				Bukkit.broadcastMessage("§f[§4알림§f] §b스코어 랭킹 3위가 §e" + p.getName() + " §b님으로 변경되었습니다!");
				return;
			}
		}
			
		if (!fourName.equalsIgnoreCase(p.getName()) && !threeName.equalsIgnoreCase(p.getName()) && 
			!twoName.equalsIgnoreCase(p.getName()) && !oneName.equalsIgnoreCase(p.getName())) {
			if (score > four && score <= three)
			{
				int fourJunkI = Method.getRankingI("four");
				String fourJunkS = Method.getRankingS("fourName");
				
				if (fourJunkS.equalsIgnoreCase(p.getName())) fourJunkS = "NONE";
					
				Method.setRankingI("four", score);
				Method.setRankingS("fourName", p.getName());
				Method.setRankingI("five", fourJunkI);
				Method.setRankingS("fiveName", fourJunkS);
				Bukkit.broadcastMessage("§f[§4알림§f] §b스코어 랭킹 4위가 §e" + p.getName() + " §b님으로 변경되었습니다!");
				return;
			}
		}
		
		if (!fiveName.equalsIgnoreCase(p.getName()) && !fourName.equalsIgnoreCase(p.getName()) && 
			!threeName.equalsIgnoreCase(p.getName()) && !twoName.equalsIgnoreCase(p.getName()) && 
			!oneName.equalsIgnoreCase(p.getName())) {
			if (score > five && score <= four)
			{
				Method.setRankingI("five", score);
				Method.setRankingS("fiveName", p.getName());
				Bukkit.broadcastMessage("§f[§4알림§f] §b스코어 랭킹 5위가 §e" + p.getName() + " §b님으로 변경되었습니다!");
				return;
			}
		}
	}
}
