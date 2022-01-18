package me.shinkhan.fatigue;

import org.bukkit.entity.Player;

public class API {
	public static double getFatigue(Player p) {
		return PlayerYml.getInfoDouble(p, "�Ƿε�");
	}
	
	public static boolean isCheck(Player p) {
		if (getFatigue(p) <= 0) return false;
		else return true;
	}
	
	public static void addFatigue(Player p, double num) {
		if (getFatigue(p) + num >= 100) PlayerYml.setInfoDouble(p, "�Ƿε�", 100);
		else PlayerYml.setInfoDouble(p, "�Ƿε�", getFatigue(p) + num);
		p.setLevel((int) getFatigue(p));
		p.setExp((float) getFatigue(p) / 100);
	}
	
	public static void subFatigue(Player p, double num) {
		if (getFatigue(p) - num <= 0) 	PlayerYml.setInfoDouble(p, "�Ƿε�", 0D);
		else PlayerYml.setInfoDouble(p, "�Ƿε�", getFatigue(p) - num);
		p.setLevel((int) getFatigue(p));
		p.setExp((float) getFatigue(p) / 100);
	}
	
	public static void setFatigue(Player p, double num) {
		if (num >= 100) PlayerYml.setInfoDouble(p, "�Ƿε�", 100D);
		else if (num <= 0) PlayerYml.setInfoDouble(p, "�Ƿε�", 0D);
		else PlayerYml.setInfoDouble(p, "�Ƿε�", num);
		p.setLevel((int) getFatigue(p));
		p.setExp((float) getFatigue(p) / 100);
	}
	
	public static void sayMessage(Player p, String str) {
		p.sendMessage("");
		p.sendMessage("��c" + str);
		p.sendMessage("��f- ��c�����̳� �������� �Ƿε��� ȸ�����ֽñ� �ٶ��ϴ�.");
	}
}
