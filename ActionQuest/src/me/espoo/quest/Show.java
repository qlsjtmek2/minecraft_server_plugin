package me.espoo.quest;

import org.bukkit.entity.Player;

public class Show extends Thread {
	Player p;
	String questname;
	String itemname;
	String content;
	int code;
	int date;
	
	public Show(Player p, String questname, String itemname, int code, int date, String content) {
		this.p = p;
		this.questname = questname;
		this.itemname = itemname;
		this.code = code;
		this.date = date;
		this.content = content;
	}
	
	public void run() {
		for (int i = 1; i <= content.length(); i++) {
			String trash = null;
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < i; j++) {
				String str = content.toUpperCase();
				sb.append(API.CharToString(str.charAt(j)));
				trash = sb.toString();
			}
			
			try { sleep(45); } catch (InterruptedException e) {}
			GUI.updateQuestIngGUI(p, p.getOpenInventory(), questname, itemname, trash, code, date);
		}
		
		GUI.updateQuestIngGUI(p, p.getOpenInventory(), questname, itemname, content + ";;§8§lㅡ> §7클릭시 다음 대화를 봅니다.", code, date);
		if (main.ThreadShow.containsKey(p)) main.ThreadShow.remove(p);
	}
}
