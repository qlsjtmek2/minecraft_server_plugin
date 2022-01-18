package me.espoo.is;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import me.espoo.rpg.exp.ExpAPI;
import me.espoo.rpg.guild.GuildAPI;
import me.espoo.rpg.party.PartyAPI;
import me.espoo.score.PlayerYml;
import me.nighteyes604.LoreAttributes.LoreAttributes;
import me.nighteyes604.LoreAttributes.LoreManager;
import me.shinkhan.fatigue.API;

public class Method {
	public static LoreManager L;
	static me.espoo.score.Method M;
	static me.espoo.socket.PlayerYml P;
	static me.espoo.socket.API A;
	
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
	
	@SuppressWarnings("static-access")
	public static List<String> getPlayerAllStat(Player p)
	{
		Method.L = LoreAttributes.loreManager;
		List<String> list = new ArrayList<String>();
		int damage = 0;
		int cretprobe = 0;
		int cretdamage = 0;
		int heart = 0;
		
		int hpUP = 0;
		int dodge = 0;
		int protect = 0;
		
		float speed = 0F;
		int jump = 0;
		
		damage += getStat1(p) * 0.3;
		damage += L.getOnlyDamage(p);
		cretprobe += L.getCriticalChance(p);
		cretdamage += L.getCriticalDamage(p);
		heart += L.getLifeSteal(p);

		hpUP += L.getRegenBonus(p);
		dodge += L.getDodgeBonus(p);
		protect += L.getArmorBonus(p);
		
		jump += getStat3(p) / 10;
		speed += getStat4(p) / 200F;
		
		if (speed <= 1F) {
			if (speed <= 0.2F) {
				speed = 0F;
			}
		} else {
			speed = 1F;
		}
		
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
		}

		list.add("");
		list.add("§7공격력: §c" + damage);
		list.add("§7치명타 확률: §c" + cretprobe + "%");
		list.add("§7치명타 데미지: §c" + cretdamage);
		list.add("§7생명력 흡수: §c+" + heart);
		list.add("");
		list.add("§7체력: §d" + p.getHealth() + "§f/§d" + p.getMaxHealth());
		list.add("§7회복력: §d" + hpUP);
		list.add("§7회피력: §d" + dodge + "%");
		list.add("§7보호력: §d" + protect + "%");
		list.add("");
		if (speed == 0F) list.add("§7스피드: §b기본");
		else list.add("§7스피드: §b" + speed + "§f/§b1");
		list.add("§7점프 강화: §b+" + jump);
		list.add("");
		return list;
	}
	
	public static List<String> getHunger(Player t)
	{
		List<String> list = new ArrayList<String>();
		
		list.add("§7배고픔: §e" + t.getFoodLevel() + "§f/§e" + 20);
		return list;
	}
	
	public static List<String> getFatigue(Player t)
	{
		List<String> list = new ArrayList<String>();
		
		list.add("§7피로도: §b" + ((int) API.getFatigue(t)) + "§f/§b" + 100);
		return list;
	}
	
	public static int getStat1(Player t)
	{
		if (me.espoo.file.PlayerYml.getInfoBoolean(t, "팔 근력 스텟 적용")) {
			int i = me.espoo.punchstat.Method.get1Stat(t) + me.espoo.file.PlayerYml.getInfoInt(t, "팔 근력") + me.espoo.punchstat.Method.get1StatEffect(t) + me.espoo.punchstat.Method.getLoon(t);
			return i;	
		} else {
			return 0;	
		}
	}
	
	public static List<String> getStat2(Player t)
	{
		List<String> list = new ArrayList<String>();
		if (me.espoo.file.PlayerYml.getInfoBoolean(t, "복근 스텟 적용")) {
			int i = me.espoo.punchstat.Method.get2Stat(t) + me.espoo.file.PlayerYml.getInfoInt(t, "복근") + me.espoo.punchstat.Method.get2StatEffect(t) + me.espoo.punchstat.Method.getLoon(t);
			list.add("§7스텟 값: §f" + i);
			return list;
		} else {
			list.add("§7스텟 값: §f0");
			return list;	
		}
	}
	
	public static int getStat3(Player t)
	{
		if (me.espoo.file.PlayerYml.getInfoBoolean(t, "다리 근력 스텟 적용")) {
			int i = me.espoo.punchstat.Method.get3Stat(t) + me.espoo.file.PlayerYml.getInfoInt(t, "다리 근력") + me.espoo.punchstat.Method.get3StatEffect(t) + me.espoo.punchstat.Method.getLoon(t);
			return i;
		} else {
			return 0;	
		}
	}
	
	public static float getStat4(Player t)
	{
		if (me.espoo.file.PlayerYml.getInfoBoolean(t, "스피드 스텟 적용")) {
			int i = 0;
			if (me.espoo.punchstat.Method.get4Stat(t) + me.espoo.file.PlayerYml.getInfoInt(t, "스피드") > 200) {
	    		i = 200;
			} else {
				i = me.espoo.punchstat.Method.get4Stat(t) + me.espoo.file.PlayerYml.getInfoInt(t, "스피드") + me.espoo.punchstat.Method.get4StatEffect(t) + me.espoo.punchstat.Method.getLoon(t);
			}
			
			return i;
		} else {
			return 0;	
		}
	}
	
	public static List<String> getStat(Player t)
	{
		List<String> list = new ArrayList<String>();
		list.add("§7아이템 클릭시 플레이어의 상태를 봅니다.");
		return list;
	}
	
	@SuppressWarnings("static-access")
	public static List<String> getPlayerHead(Player t, String openuser)
	{
		List<String> list = new ArrayList<String>();
		String party = PartyAPI.getJoinParty(t);
		String guild = GuildAPI.getJoinGuild(t);
		Double num = main.economy.getBalance(t.getName());
		NumberFormat f = NumberFormat.getInstance();
		f.setGroupingUsed(false);
		String val = f.format(num);
		String str;
		if (M.getPlayerRanking(t.getName()) <= 15) {
			str = "§4S";
		}
		
		else if (M.getPlayerScore(t.getName()) >= 30000 && M.getPlayerRanking(t.getName()) > 15) {
			str = "§cA";
		}
		
		else if (M.getPlayerScore(t.getName()) >= 4000 && M.getPlayerScore(t.getName()) < 30000 && M.getPlayerRanking(t.getName()) > 15) {
			str = "§bB";
		}
		
		else if (M.getPlayerScore(t.getName()) < 4000 && PlayerYml.getInfoBoolean(t, "등록") && M.getPlayerRanking(t.getName()) > 15)
		{
			str = "§eC";
		}
		
		else {
			str = "없음";
		}
		
		

		list.add("§7===============================");
		list.add("§f" + ExpAPI.getDayMessage(t));
		list.add("§7===============================");
		list.add("");
		list.add("§7히어로 랭크: §f" + str);
		list.add("§7히어로 점수: §f" + M.getPlayerScore(t.getName()));
		list.add("§7히어로 순위: §f" + M.getPlayerRanking(t.getName()) + "위");
		list.add("");
		list.add("§7경험치: §f" + ExpAPI.getExp(t.getName()));
		list.add("§7경험치 순위: §f" + ExpAPI.getRank(t.getName()) + "위");
		list.add("");
		list.add("§7보유한 돈: §f" + val + "원");
		
		if (party != null) {
			list.add("§7파티 가입 여부: §a가입");
		} else {
			list.add("§7파티 가입 여부: §c미가입");
		}
		

		if (guild != null) {
			list.add("§7가입한 길드: §f" + guild);
		} else {
			list.add("§7가입한 길드: §f없음");
		}

		list.add("");
		list.add("§8§l<HELP> §7클릭시 플레이어 상태를 봅니다.");
		
		if (t.getName().equalsIgnoreCase(openuser)) {
			list.add("§8§l<HELP> §7상태 메세지를 변경하고 싶으시면");
			list.add("§7/경험치 상메 <메세지> 를 채팅창에 입력하세요.");
		}
		
		return list;
	}
}
