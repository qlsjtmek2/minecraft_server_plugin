package me.espoo.quest;

import org.bukkit.entity.Player;

public class Show extends Thread {
	Player p;
	String questname;
	String itemname;
	String devcon1;
	int code;
	int date;
	boolean skip;
	
	public Show(Player p, String questname, String itemname, int code, int date, boolean skip, String devcon1) {
		this.p = p;
		this.questname = questname;
		this.itemname = itemname;
		this.code = code;
		this.date = date;
		this.skip = skip;
		this.devcon1 = devcon1;
	}
	
	public void run() {
		for (int i = 1; i <= devcon1.length(); i++) {
			String trash = null;
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < i; j++) {
				String str = devcon1.toUpperCase();
				sb.append(API.CharToString(str.charAt(j)));
				trash = sb.toString();
			}
			
			try { sleep(65); } catch (InterruptedException e) {}
			GUI.updateQuestIngGUI(p, p.getOpenInventory(), questname, itemname, trash, code, date, skip);
		}
		
		GUI.updateQuestIngGUI(p, p.getOpenInventory(), questname, itemname, devcon1 + ";;§8§lㅡ> §7클릭시 다음 대화를 봅니다.", code, date, skip);
		if (main.ThreadShow.containsKey(p)) main.ThreadShow.remove(p);
	}
}
