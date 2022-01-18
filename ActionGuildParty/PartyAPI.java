package me.espoo.rpg.party;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.espoo.rpg.Method;
import me.espoo.rpg.PlayerYml;

public class PartyAPI {
	public static String getJoinParty(Player p) {
		return PlayerYml.getInfoString(p.getName(), "가입된 파티");
	}
	
	public static boolean isParty(String name) {
		if (PartyYml.getPartyList().contains(name)) return true;
		else return false;
	}
	
	public static String getManager(String name) {
		if (isParty(name)) {
			return PartyYml.getString(name + ".파티장");
		} return null;
	}
	
	public static List<String> getUser(String name) {
		if (isParty(name)) {
			return PartyYml.getList(name + ".파티원 목록");
		} return null;
	}
	
	public static void subUser(String party, OfflinePlayer pl) {
		PlayerYml.setInfoString(pl, "가입된 파티", null);
		List<String> list = PartyAPI.getUser(party);
		for (String str : getUser(party))
			if (str.equalsIgnoreCase(pl.getName()))
				list.remove(str);
		
		PartyAPI.setUser(party, list);
		if (pl.getPlayer() != null) pl.getPlayer().closeInventory();
	}
	
	public static void setUser(String name, List<String> list) {
		if (isParty(name)) {
			PartyYml.setList(name + ".파티원 목록", list);
		}
	}
	
	public static List<String> getInfo(String name) {
		if (isParty(name)) {
			List<String> list = PartyYml.getList(name + ".파티 설명");
			return list;
		} return null;
	}
	
	public static void setShare(String name, boolean is) {
		if (isParty(name)) {
			PartyYml.setBoolean(name + ".경험치 분배", is);
		}
	}
	
	public static boolean getShare(String name) {
		if (isParty(name)) {
			return PartyYml.getBoolean(name + ".경험치 분배");
		} return false;
	}
	
	public static void setInfo(String name, List<String> list) {
		if (isParty(name)) {
			PartyYml.setList(name + ".파티 설명", list);
		}
	}
	
	public static String getJoinType(String name) {
		if (isParty(name)) {
			switch (PartyYml.getInt(name + ".가입 타입")) {
			case 0:
				return "공개"; 
			case 1:
				return "신청"; 
			case 2:
				return "비공개";
			}
		} return null;
	}
	
	// 0 - 공개, 1 - 신청, 2 - 비공개
	public static void setJoinType(String name, int amount) {
		if (isParty(name)) {
			PartyYml.setInt(name + ".가입 타입", amount);
		}
	}
	
	public static String getPlayerChat(Player p) {
		return PlayerYml.getInfoString(p.getName(), "채팅 모드");
	}
	
	public static void setPlayerChat(Player p, String mode) {
		if (PartyAPI.getJoinParty(p) != null) {
			PlayerYml.setInfoString(p, "채팅 모드", mode);
		} else {
			PlayerYml.setInfoString(p, "채팅 모드", "전체");
		}
	}
	
	public static int getUserInteger(String name) {
		if (isParty(name)) {
			return PartyYml.getList(name + ".파티원 목록").size();
		} return -1;
	}
	
	public static void addUser(String party, Player p) {
		PlayerYml.setInfoString(p, "가입된 파티", party);
		List<String> list = PartyAPI.getUser(party);
		list.add(p.getName());
		PartyAPI.setUser(party, list);
	}
	
	public static void setMaster(String name, String player) {
		if (isParty(name)) {
			PartyYml.setString(name + ".파티장", player);
		}
	}
	
	public static void changePartyMaster(Player p, Player pl) {
		String party = PartyAPI.getJoinParty(p);
		List<String> user = PartyAPI.getUser(party);
		List<String> info = PartyAPI.getInfo(party);
		int type = PartyYml.getInt(party + ".가입 타입");
		boolean exp = getShare(party);
		PartyYml.deleteParty(party, p);
		PlayerYml.setInfoString(p, "가입된 파티", pl.getName());
		PartyYml.setParty(pl.getName(), user, info, type, exp);
		
		for (String str : user) {
			if (Method.getOnorOffLine(str) != null) {
				PlayerYml.setInfoString(Method.getOnorOffLine(str), "가입된 파티", pl.getName());
			} else {
				PartyAPI.subUser(pl.getName(), Bukkit.getOfflinePlayer(str));
			}
		}
	}
	
	public static String RandomParty(List<String> user) {
		int random = new Random().nextInt(user.size());
		if (random == (user.size() - 1)) RandomParty(user);
		return user.get(random);
	}
	
	public static void AutoKickPlayer(Player p) {
		String party = PartyAPI.getJoinParty(p);
		if (party != null && PartyAPI.isParty(party)) {
			if (PartyAPI.getManager(party).equalsIgnoreCase(p.getName())) {
				if (PartyAPI.getUserInteger(party) == 1) {
					PartyYml.deleteParty(party, p);
				} else {
					Player player = Bukkit.getPlayerExact(PartyAPI.RandomParty(PartyAPI.getUser(party)));
					PartyAPI.changePartyMaster(p, player);
					PartyAPI.subUser(player.getName(), p);
					if (me.espoo.option.PlayerYml.getInfoBoolean(player, "효과음")) player.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
					for (String pl : PartyAPI.getUser(player.getName())) Bukkit.getPlayerExact(pl).sendMessage("§b파티장이신 §e[ " + p.getName() + " ] §b님이 서버에서 퇴장하여 파티장이 §e" + player.getName() + " §b님으로 변경되었습니다.");
				}
			} else {
				PartyAPI.subUser(party, p);
				for (String pl : PartyAPI.getUser(party)) Bukkit.getPlayerExact(pl).sendMessage("§c파티에 §e[ " + p.getName() + " ] §c님이 나가셨습니다.");
			}
		} else {
			PlayerYml.setInfoString(p, "가입된 파티", null);
		}
	}
}
