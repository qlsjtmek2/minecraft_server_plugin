package me.espoo.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import me.espoo.rpg.exp.ExpAPI;
import me.espoo.score.Method;
import me.espoo.score.PlayerYml;
import yt.codebukkit.scoreboardapi.Scoreboard;
import yt.codebukkit.scoreboardapi.ScoreboardAPI;

public class API {
	public static void update(Player p) {
		String str;
		if (Method.getPlayerRanking(p.getName()) <= 15) {
			str = Message.SRank;
		}
		
		else if (Method.getPlayerScore(p.getName()) >= 30000 && Method.getPlayerRanking(p.getName()) > 15) {
			str = Message.ARank;
		}
		
		else if (Method.getPlayerScore(p.getName()) >= 4000 && Method.getPlayerScore(p.getName()) < 30000 && Method.getPlayerRanking(p.getName()) > 15) {
			str = Message.BRank;
		}
		
		else if (Method.getPlayerScore(p.getName()) < 4000 && PlayerYml.getInfoBoolean(p, "????") && Method.getPlayerRanking(p.getName()) > 15)
		{
			str = Message.CRank;
		}
		
		else {
			str = Message.XRank;
		}
		
		if (p.isOp() && p.getName().equalsIgnoreCase("shinkhan")) {
			str = Message.CG;
		}
		
		else if (p.isOp()) {
			str = Message.G;
		}
		
		setPrefix(p, str);
	}
	
	public static void setPrefix(Player player, String prefix)
	{
		if (prefix.length() > 16) {
			prefix = prefix.substring(0, 16);
		}

		String teamName = "CT" + prefix;

		if (teamName.length() > 16) {
			teamName = teamName.substring(0, 16);
		}
		
		Team t = main.sb.getTeam(teamName);

	    if (t == null) {
	    	t = main.sb.registerNewTeam(teamName);
	    	t.setPrefix(prefix);
	    	t.setCanSeeFriendlyInvisibles(false);
	    }
	    
		t.addPlayer(player);
	}
	
	public static void setScoreboard(Player p)
	{
	    ScoreboardAPI sa = ScoreboardAPI.getInstance();
	  	if (sa.getScoreboard(p.getName()) != null) {
	  		Scoreboard b = sa.getScoreboard(p.getName());
	  		b.setScoreboardName(Message.Display[main.num]);
		    b.setItem("??f?? ??c" + ExpAPI.getExp(p.getName()), 9);
		    b.setItem("??f?? ??c" + Method.getPlayerScore(p.getName()), 6);
		    b.setItem("??f?? ??c" + Bukkit.getOnlinePlayers().length + "??7/??c2016", 3);
		    b.showToPlayer(p);
		    if (!main.Manager.contains(p)) main.Manager.add(p);	
	  	} else {
		    Scoreboard b = sa.createScoreboard(p.getName(), 10);
		    b.setScoreboardName(Message.Display[main.num]);
		    b.setItem("??f??f??l????????????????", 12);
		    b.setItem("??f??f??f??f", 11);
		    b.setItem("??a????ġ", 10);
		    b.setItem("??f?? ??c" + ExpAPI.getExp(p.getName()), 9);
		    b.setItem("??f??f??f", 8);
		    b.setItem("??b?????? ????", 7);
		    b.setItem("??f?? ??c" + Method.getPlayerScore(p.getName()), 6);
		    b.setItem("??f??f", 5);
		    b.setItem("??e???? ?ο?", 4);
		    b.setItem("??f?? ??c" + Bukkit.getOnlinePlayers().length + "??7/??c2016", 3);
		    b.setItem("??f", 2);
		    b.setItem("??f??l????????????????", 1);
		    b.showToPlayer(p);
		    if (!main.Manager.contains(p)) main.Manager.add(p);	
	  	}
	}
}
