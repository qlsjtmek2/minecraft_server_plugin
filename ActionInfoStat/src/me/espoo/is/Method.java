package me.espoo.is;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import me.espoo.pvp.yml.PVPPlayer;
import me.espoo.rpg.guild.GuildAPI;
import me.espoo.rpg.party.PartyAPI;
import me.nighteyes604.LoreAttributes.LoreAttributes;
import me.nighteyes604.LoreAttributes.LoreManager;
import me.tpsw.RpgStatsSystem.api.StatsAPI;
import me.tpsw.RpgStatsSystem.api.StatsPlayer;
import to.oa.tpsw.rpgexpsystem.api.Rpg;
import to.oa.tpsw.rpgexpsystem.api.RpgPlayer;

public class Method {
	public static LoreManager L;
	static me.espoo.socket.PlayerYml P;
	static me.espoo.socket.API A;
	static me.espoo.pvp.API V;
	
	public static Player getOnorOffLine(String s) {
		for (Player all : Bukkit.getOnlinePlayers())
		{
			if (all.getName().equals(s))
			{
				return all;
			}
		}
		return null;
	}
	
	public static String searchOnlinePlayer(String target) {
		List<String> list1 = new ArrayList<String>();
		for (int i = 0; i < 16; i++) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.getName().toLowerCase().indexOf(target.toLowerCase()) == i) {
					list1.add(player.getName());
				}
			}
			if (list1.size() == 0) {
				continue;
			} else {
				int len = 100;
				List<String> list3 = new ArrayList<String>();
				for (int j = 0; j < list1.size(); j++) {
					int l = list1.get(j).length();
					if (l < len) {
						len = l;
					}
				}
				for (int j = 0; j < list1.size(); j++) {
					if (list1.get(j).length() == len) {
						list3.add(list1.get(j));
					}
				}
				String[] list2 = new String[list3.size()];
				for (int j = 0; j < list3.size(); j++) {
					list2[j] = list3.get(j);
				}
				Arrays.sort(list2);
				return list2[0];
			}
		}
		return null;
	}
	
	public static List<String> getPotion(Player t)
	{
		List<String> list = new ArrayList<String>();
		
		list.add("§7적용된 포션 효과: ");
		if (t.hasPotionEffect(PotionEffectType.POISON)) list.add("§f- §2중독");
		if (t.hasPotionEffect(PotionEffectType.NIGHT_VISION)) list.add("§f- §1야간투시");
		if (t.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) list.add("§f- §6화염저항");
		if (t.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) list.add("§f- §c공격력 증가");
		if (t.hasPotionEffect(PotionEffectType.WEAKNESS)) list.add("§f- §5나약함");
		if (t.hasPotionEffect(PotionEffectType.SPEED)) list.add("§f- §b속도 증가");
		if (t.hasPotionEffect(PotionEffectType.SLOW)) list.add("§f- §9속도 감소");
		if (t.hasPotionEffect(PotionEffectType.INVISIBILITY)) list.add("§f- §f투명화");
		if (t.hasPotionEffect(PotionEffectType.WITHER)) list.add("§f- §0혼돈");
		if (t.hasPotionEffect(PotionEffectType.JUMP)) list.add("§f- §b점프력 강화");
		if (t.hasPotionEffect(PotionEffectType.HUNGER)) list.add("§f- §2허기");
		if (t.hasPotionEffect(PotionEffectType.FAST_DIGGING)) list.add("§f- §e채굴속도 향상");
		if (t.hasPotionEffect(PotionEffectType.REGENERATION)) list.add("§f- §d회복력 향상");
		if (t.hasPotionEffect(PotionEffectType.WATER_BREATHING)) list.add("§f- §3수중 호홉");
		if (t.hasPotionEffect(PotionEffectType.CONFUSION)) list.add("§f- §d혼란");
		if (t.hasPotionEffect(PotionEffectType.BLINDNESS)) list.add("§f- §8실명");
		if (!t.hasPotionEffect(PotionEffectType.POISON) && !t.hasPotionEffect(PotionEffectType.NIGHT_VISION) && !t.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE) &&
			!t.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE) && !t.hasPotionEffect(PotionEffectType.WEAKNESS) && !t.hasPotionEffect(PotionEffectType.SPEED) &&
			!t.hasPotionEffect(PotionEffectType.SLOW) && !t.hasPotionEffect(PotionEffectType.INVISIBILITY) && !t.hasPotionEffect(PotionEffectType.WITHER) &&
			!t.hasPotionEffect(PotionEffectType.JUMP) && !t.hasPotionEffect(PotionEffectType.HUNGER) && !t.hasPotionEffect(PotionEffectType.FAST_DIGGING) && 
			!t.hasPotionEffect(PotionEffectType.REGENERATION) && !t.hasPotionEffect(PotionEffectType.WATER_BREATHING) && !t.hasPotionEffect(PotionEffectType.CONFUSION) &&
			!t.hasPotionEffect(PotionEffectType.BLINDNESS)) list.add("§f- §a정상");
		
		return list;
	}
	
	public static int getDamageStat(StatsPlayer sp)
	{
		return sp.getStatPoint("§c공격력§0_§6");
	}
	
	@SuppressWarnings("static-access")
	public static List<String> getPlayerAllStat(Player p)
	{
		Method.L = LoreAttributes.loreManager;
		List<String> list = new ArrayList<String>();
		StatsPlayer sp = StatsAPI.getStatsPlayer(p.getName());
		
		int damage = 0;
		int cretprobe = 0;
		int cretdamage = 0;
		int heart = 0;
		
		int hpUP = 0;
		int dodge = 0;
		int protect = 0;
		
		damage += L.getOnlyDamage(p);
		damage += getDamageStat(sp);
		cretprobe += L.getCriticalChance(p);
		cretdamage += L.getCriticalDamage(p);
		heart += L.getLifeSteal(p);

		hpUP += L.getRegenBonus(p);
		dodge += L.getDodgeBonus(p);
		protect += L.getArmour(p);
		
		if (P.getInfoInt(p, "1.아이템 코드") != 0 && P.getInfoString(p, "1.아이템 이름") != null && !P.getInfoList(p, "1.아이템 설명").equals(Arrays.asList())) {
			String str = A.getLoreNum(P.getInfoList(p, "1.아이템 설명"));

			if (str.equalsIgnoreCase("추가 데미지")) {
				damage += A.getPlusDamage(P.getInfoList(p, "1.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("크리티컬 확률")) {
				cretprobe += A.getCretProbe(P.getInfoList(p, "1.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("크리티컬 데미지")) {
				cretdamage += A.getCretDamage(P.getInfoList(p, "1.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("체력 흡수")) {
				heart += A.getHeartGet(P.getInfoList(p, "1.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("데미지 감소")) {
				protect += A.getDamageDefense(P.getInfoList(p, "1.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("회피력")) {
				dodge += A.getDodge(P.getInfoList(p, "1.아이템 설명"));
			}
		}
		
		if (P.getInfoInt(p, "2.아이템 코드") != 0 && P.getInfoString(p, "2.아이템 이름") != null && !P.getInfoList(p, "2.아이템 설명").equals(Arrays.asList())) {
			String str = A.getLoreNum(P.getInfoList(p, "2.아이템 설명"));
			if (str.equalsIgnoreCase("추가 데미지")) {
				damage += A.getPlusDamage(P.getInfoList(p, "2.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("크리티컬 확률")) {
				cretprobe += A.getCretProbe(P.getInfoList(p, "2.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("크리티컬 데미지")) {
				cretdamage += A.getCretDamage(P.getInfoList(p, "2.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("체력 흡수")) {
				heart += A.getHeartGet(P.getInfoList(p, "2.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("데미지 감소")) {
				protect += A.getDamageDefense(P.getInfoList(p, "2.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("회피력")) {
				dodge += A.getDodge(P.getInfoList(p, "2.아이템 설명"));
			}
		}
		
		if (P.getInfoInt(p, "3.아이템 코드") != 0 && P.getInfoString(p, "3.아이템 이름") != null && !P.getInfoList(p, "3.아이템 설명").equals(Arrays.asList())) {
			String str = A.getLoreNum(P.getInfoList(p, "3.아이템 설명"));
			if (str.equalsIgnoreCase("추가 데미지")) {
				damage += A.getPlusDamage(P.getInfoList(p, "3.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("크리티컬 확률")) {
				cretprobe += A.getCretProbe(P.getInfoList(p, "3.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("크리티컬 데미지")) {
				cretdamage += A.getCretDamage(P.getInfoList(p, "3.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("체력 흡수")) {
				heart += A.getHeartGet(P.getInfoList(p, "3.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("데미지 감소")) {
				protect += A.getDamageDefense(P.getInfoList(p, "3.아이템 설명"));
			}
			
			else if (str.equalsIgnoreCase("회피력")) {
				dodge += A.getDodge(P.getInfoList(p, "3.아이템 설명"));
			}
		}

		list.add("");
		list.add("§7공격력: §c" + damage);
		if (cretprobe > 100) list.add("§7치명타 확률: §c100%");
		else list.add("§7치명타 확률: §c" + cretprobe + "%");
		list.add("§7치명타 데미지: §c" + cretdamage);
		list.add("§7생명력 흡수: §c+" + heart);
		list.add("");
		list.add("§7체력: §d" + p.getHealth() + "§f/§d" + p.getMaxHealth());
		list.add("§7회복력: §d" + hpUP);
		list.add("§7회피력: §d" + dodge + "%");
		list.add("§7보호력: §d" + protect + "%");
		list.add("");
		return list;
	}
	
	public static List<String> getHunger(Player t)
	{
		List<String> list = new ArrayList<String>();
		
		list.add("§7배고픔: §e" + t.getFoodLevel() + "§f/§e" + 20);
		return list;
	}
	
	public static List<String> getStat(Player t)
	{
		List<String> list = new ArrayList<String>();
		list.add("§7아이템 클릭시 플레이어의 상태를 봅니다.");
		return list;
	}
	
	public static int getPvPRanking(Player t)
	{
		int index = me.espoo.pvp.ranking.Ranking.searchRanking(t.getName());
		if (index != -1) {
			index /= 10;
			index += 1;
			List<String> rank = new ArrayList<String>(me.espoo.pvp.ranking.UpdateRanking.ranking);
			if ((index * 10 - 9 > rank.size()) || (index == 0)) {
				return 0;
			}
			for (int j = (index - 1) * 10; j < index * 10; j++) {
				String[] var = ((String) rank.get(j)).split(" ");
				if (var[2].equalsIgnoreCase(t.getName())) return j + 1;
				if (rank.size() == j + 1) {
					break;
				}
			}
		} else {
			return 0;
		} return 0;
	}
	
	public static int getExpRanking(Player t)
	{
		int index = to.oa.tpsw.rpgexpsystem.raking.Ranking.searchRanking(t.getName());
		if (index != -1) {
			index /= 10;
			index += 1;
			List<String> rank = new ArrayList<String>(to.oa.tpsw.rpgexpsystem.raking.UpdateRanking.ranking);
			if ((index * 10 - 9 > rank.size()) || (index == 0)) {
				return 0;
			}
			for (int j = (index - 1) * 10; j < index * 10; j++) {
				String[] var = ((String) rank.get(j)).split(" ");
				if (var[2].equalsIgnoreCase(t.getName())) return j + 1;
				if (rank.size() == j + 1) {
					break;
				}
			}
		} else {
			return 0;
		} return 0;
	}
	
	@SuppressWarnings("static-access")
	public static List<String> getPlayerHead(Player t, String openuser)
	{
		List<String> list = new ArrayList<String>();
		String party = PartyAPI.getJoinParty(t);
		String guild = GuildAPI.getJoinGuild(t.getName());
		RpgPlayer rp = Rpg.getRpgPlayera(t.getName());
		Double num = main.economy.getBalance(t.getName());
		NumberFormat f = NumberFormat.getInstance();
		f.setGroupingUsed(false);
		String val = f.format(num);
		String name = t.getName();
		String zunzec = null;
		if (t.hasPermission("[전사]")) zunzec = "§c";
		else if (t.hasPermission("[마법사]")) zunzec = "§d";
		else if (t.hasPermission("[도적]")) zunzec = "§l";
		else if (t.hasPermission("[파이터]")) zunzec = "§a";
		else if (t.hasPermission("[궁수]")) zunzec = "§e";
		else zunzec = "§f";
		
		PVPPlayer pp = V.getPVPPlayera(name);
		if (pp != null) {
			list.add("§f");
			list.add("§7PVP 클래스: §c" + pp.getclassExpress());
			list.add("§7킬 전적: §c" + pp.getKill());
			list.add("§7데스 전적: §c" + pp.getDeath());
			list.add("§7목숨당 처치: §c" + pp.getKillDivDeath());
			list.add("§7PVP 랭킹: §c" + getPvPRanking(t) + "위");
		} else {
			list.add("§f");
			list.add("§7PVP 클래스: §7F");
			list.add("§7킬 전적: §c0");
			list.add("§7데스 전적: §c0");
			list.add("§7목숨당 처치: §c0.0");
			list.add("§7PVP 랭킹: §c0위");
		}
		
		list.add("§f");
		if (t.getName().equalsIgnoreCase("shinkhan")) list.add("§7전직: §c총관리자");
		else if (t.isOp()) list.add("§7전직: §c관리자");
		else {
			list.add("§7전직: " + zunzec + "" + me.espoo.rpg2.Method.getInfoString(t, "전직"));
			list.add("§7전직 단계: §f" + (me.espoo.rpg2.Method.getInfoInt(t, "전직 카운트") - 1) + "차 전직");
		}
		
		list.add("§7레벨: §e" + rp.getRpgLevel());
		list.add("§7경험치: §e" + rp.getRpgExp() + "§7/§e" + rp.getRpgNeedExp());
		list.add("§7경험치 랭킹: §e" + getExpRanking(t) + "위");
		list.add("§f");
		list.add("§7보유한 돈: §f$" + val);
		
		if (party != null) {
			list.add("§7파티 가입 여부: §f가입");
		} else {
			list.add("§7파티 가입 여부: §f미가입");
		}
		

		if (guild != null) {
			list.add("§7가입한 길드: §f" + guild);
		} else {
			list.add("§7가입한 길드: §f없음");
		}

		if (me.espoo.option.PlayerYml.getInfoBoolean(t, "교환 신청")) {
			list.add("§f");
			list.add("§8§l* §f클릭시 상태를 봅니다.");	
		}
		
		return list;
	}
}
