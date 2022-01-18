package me.espoo.rpg.exp;

import java.util.List;

import org.bukkit.entity.Player;

import me.espoo.rpg.Method;
import me.espoo.rpg.PlayerYml;
import me.espoo.rpg.guild.GuildAPI;
import me.espoo.rpg.party.PartyAPI;

public class ExpAPI {
	public static String getDayMessage(Player p) {
		return PlayerYml.getInfoString(p, "상태 메세지");
	}
	
	public static void addExp(Player p, String message, int amount) {
		if (PartyAPI.getJoinParty(p) == null) {
			float exp = (float) amount;
			float addBouns = 0;
			
			if (isBouns(p.getName())) {
				String str = getBounsType(p.getName());
				if (str.contains(",")) {
					for (int i = 0; i < str.split(",").length; i++) {
						float bouns = ((float) getBouns(p.getName(), str.split(",")[i]) / 100);
						addBouns += bouns;
					}
				} else {
					float bouns = ((float) getBouns(p.getName(), str) / 100);
					addBouns += bouns;
				}
			}
			
			if (GuildAPI.getJoinGuild(p) != null) {
				int i = GuildAPI.getExpStat(GuildAPI.getJoinGuild(p)); i *= 10; i += 100;
				addBouns += i / 100;
			}
			
			if (getServerBouns() / 100 > 1) {
				addBouns += getServerBouns() / 100;
			}
			
			message = message.replaceAll("<exp>", Integer.toString((int) exp));
			exp *= addBouns;
			if (addBouns != 0) {
				message = message.replaceAll("<bouns>", "§c(x" + Method.roundDot1Off(Float.toString(addBouns)) + ")§6");
			} else {
				message = message.replaceAll("<bouns>", "");
			}
			
			ExpYml.addPlayerExp(p.getName(), (int) exp);
			message = message.replaceAll("<playerexp>", Integer.toString(ExpAPI.getExp(p.getName())));
			p.sendMessage(message);
			
			if (GuildAPI.getJoinGuild(p) != null) {
				int i = (int) exp; i /= 5;
				GuildAPI.addPoint(GuildAPI.getJoinGuild(p), i);
			}
		} else {
			if (PartyAPI.getShare(PartyAPI.getJoinParty(p))) {
				float exp = (float) amount;
				float addBouns = 0;
				String name = PartyAPI.getJoinParty(p);
				
				if (isBouns(p.getName())) {
					String str = getBounsType(p.getName());
					if (str.contains(",")) {
						for (int i = 0; i < str.split(",").length; i++) {
							float bouns = ((float) getBouns(p.getName(), str.split(",")[i]) / 100);
							addBouns += bouns;
						}
					} else {
						float bouns = ((float) getBouns(p.getName(), str) / 100);
						addBouns += bouns;
					}
				}
				
				if (GuildAPI.getJoinGuild(p) != null) {
					int i = GuildAPI.getExpStat(GuildAPI.getJoinGuild(p)); i *= 10; i += 100;
					addBouns += i / 100;
				}
				
				if (getServerBouns() / 100 > 1) {
					addBouns += getServerBouns() / 100;
				}
				
				double y = 0;
				if (addBouns != 0) {
					message = message.replaceAll("<bouns>", "§c(x" + Method.roundDot1Off(Float.toString(addBouns)) + ")§6");
					y = exp * addBouns;
				} else {
					message = message.replaceAll("<bouns>", "");
					y = exp;
				}
				
				double total = 0.8;
				total += PartyAPI.getUserInteger(name) * 0.05;
				total *= y;
				ExpYml.addPlayerExp(p.getName(), (int) total);
				message = message.replaceAll("<exp>", Integer.toString((int) total));
				message = message.replaceAll("<playerexp>", Integer.toString(ExpAPI.getExp(p.getName())));
				p.sendMessage(message);
				
				if (GuildAPI.getJoinGuild(p) != null) {
					int i = (int) y; i /= 5;
					GuildAPI.addPoint(GuildAPI.getJoinGuild(p), i);
				}
				
				for (String str : PartyAPI.getUser(name)) {
					if (Method.getOnorOffLine(str) != null) {
						if (str.equalsIgnoreCase(p.getName())) continue;
						Player pl = Method.getOnorOffLine(str);
						double dtc = p.getLocation().distance(pl.getLocation());
						
						if (dtc <= 25) {
							total = 0.25;
							total += PartyAPI.getUserInteger(name) * 0.1;
							total *= exp;
							ExpYml.addPlayerExp(pl.getName(), (int) total);
							String mess = "§6당신은 §c" + p.getName() + "§6님에게 파티 보너스로 §c<exp> §6만큼 경험치를 받았습니다. §c<playerexp> §6EXP";
							mess = mess.replaceAll("<exp>", Integer.toString((int) total));
							mess = mess.replaceAll("<playerexp>", Integer.toString(ExpAPI.getExp(pl.getName())));
							pl.sendMessage(mess);
						}
					}
				}
			} else {
				float exp = (float) amount;
				float addBouns = 0;
				
				if (isBouns(p.getName())) {
					String str = getBounsType(p.getName());
					if (str.contains(",")) {
						for (int i = 0; i < str.split(",").length; i++) {
							float bouns = ((float) getBouns(p.getName(), str.split(",")[i]) / 100);
							addBouns += bouns;
						}
					} else {
						float bouns = ((float) getBouns(p.getName(), str) / 100);
						addBouns += bouns;
					}
				}
				
				if (GuildAPI.getJoinGuild(p) != null) {
					int i = GuildAPI.getExpStat(GuildAPI.getJoinGuild(p)); i *= 10; i += 100;
					addBouns += i / 100;
				}
				
				if (getServerBouns() / 100 > 1) {
					addBouns += getServerBouns() / 100;
				}
				
				message = message.replaceAll("<exp>", Integer.toString((int) exp));
				exp *= addBouns;
				if (addBouns != 0) {
					message = message.replaceAll("<bouns>", "§c(x" + Method.roundDot1Off(Float.toString(addBouns)) + ")§6");
				} else {
					message = message.replaceAll("<bouns>", "");
				}
				
				ExpYml.addPlayerExp(p.getName(), (int) exp);
				message = message.replaceAll("<playerexp>", Integer.toString(ExpAPI.getExp(p.getName())));
				p.sendMessage(message);
				
				if (GuildAPI.getJoinGuild(p) != null) {
					int i = (int) exp; i /= 5;
					GuildAPI.addPoint(GuildAPI.getJoinGuild(p), i);
				}
			}
		}
	}
	
	public static int getExp(String p) {
		return ExpYml.getPlayerExp(p);
	}
	
	public static boolean getSeeInfo(Player p) {
		return PlayerYml.getInfoBoolean(p, "보기");
	}
	
	public static int getServerBouns() {
		return ExpConfig.getExpInt("경험치 이벤트");
	}
	
	public static int getServerTimerBouns() {
		return ExpConfig.getExpInt("경험치 이벤트 타이머");
	}
	
	public static boolean isBounsType(String str) {
		return ExpConfig.getExpList("보너스." + str) != null;
	}
	
	public static boolean isBouns(String name) {
		for (String str : ExpConfig.getExpList("타입 목록"))
			for (String st : ExpConfig.getExpList("보너스." + str))
				if (st.contains(name)) return true;
		return false;
	}
	
	public static String getBounsType(String name) {
		StringBuilder bldr = new StringBuilder();
		
		for (String str : ExpConfig.getExpList("타입 목록")) {
			for (String st : ExpConfig.getExpList("보너스." + str)) {
				if (st.contains(name)) {
					if (bldr.length() == 0) {
						bldr.append(str);
					} else {
						bldr.append("," + str);
					}
				}
			}
		}
		
		if (bldr.length() == 0) return null;
		else return bldr.toString();
	}
	
	public static int getBouns(String name, String type) {
		for (String st : ExpConfig.getExpList("보너스." + type))
			if (st.contains(name)) return Integer.parseInt(st.split(",")[1]);
		return 0;
	}
	
	public static int getBounsTimer(String name, String type) {
		for (String st : ExpConfig.getExpList("보너스." + type))
			if (st.contains(name)) return Integer.parseInt(st.split(",")[2]);
		return 0;
	}
	
	public static void setBounsAll(String type, String name, int bouns, int time) {
		List<String> list = ExpConfig.getExpList("보너스." + type);
		boolean is = false; int i = 0;
		for (String str : list) {
			if (str.contains(name)) {
				is = true; break;
			} i++;
		}
		
		if (is) {
			list.set(i, name + "," + bouns + "," + time);
			ExpConfig.setExpList("보너스." + type, list);
		} else {
			list.add(name + "," + bouns + "," + time);
			ExpConfig.setExpList("보너스." + type, list);
		}
	}
	
	public static void removeBouns(String type, String name) {
		List<String> list = ExpConfig.getExpList("보너스." + type);
		List<String> test = ExpConfig.getExpList("보너스." + type);
		int i = 0;
		
		for (String str : test) {
			if (str.split(",")[0].equalsIgnoreCase(name)) {
				list.remove(i);
			} i++;
		} ExpConfig.setExpList("보너스." + type, list);
	}
	
	public static void setExp(Player p, int num) {
		ExpYml.setPlayerExp(p.getName(), num);
	}
	
	public static void setServerBouns(int num) {
		ExpConfig.setExpInt("경험치 이벤트", num);
	}
	
	public static void setServerBounsTimer(int num) {
		ExpConfig.setExpInt("경험치 이벤트 타이머", num);
	}
	
	public static int getRank(String name) {
		int num = 1;
		for (String str : ExpYml.ArrayExp()) {
			if (str.split(",")[0].equalsIgnoreCase(name)) {
				break;
			} else num++;
		}
		
		return num;
	}
}
