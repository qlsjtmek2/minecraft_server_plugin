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
		
		list.add("��7����� ���� ȿ��: ");
		if (t.hasPotionEffect(PotionEffectType.POISON)) list.add("��f- ��2�ߵ�");
		if (t.hasPotionEffect(PotionEffectType.NIGHT_VISION)) list.add("��f- ��1�߰�����");
		if (t.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) list.add("��f- ��6ȭ������");
		if (t.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) list.add("��f- ��c���ݷ� ����");
		if (t.hasPotionEffect(PotionEffectType.WEAKNESS)) list.add("��f- ��5������");
		if (t.hasPotionEffect(PotionEffectType.SPEED)) list.add("��f- ��b�ӵ� ����");
		if (t.hasPotionEffect(PotionEffectType.SLOW)) list.add("��f- ��9�ӵ� ����");
		if (t.hasPotionEffect(PotionEffectType.INVISIBILITY)) list.add("��f- ��f����ȭ");
		if (t.hasPotionEffect(PotionEffectType.WITHER)) list.add("��f- ��0ȥ��");
		if (t.hasPotionEffect(PotionEffectType.JUMP)) list.add("��f- ��b������ ��ȭ");
		if (t.hasPotionEffect(PotionEffectType.HUNGER)) list.add("��f- ��2���");
		if (t.hasPotionEffect(PotionEffectType.FAST_DIGGING)) list.add("��f- ��eä���ӵ� ���");
		if (t.hasPotionEffect(PotionEffectType.REGENERATION)) list.add("��f- ��dȸ���� ���");
		if (t.hasPotionEffect(PotionEffectType.WATER_BREATHING)) list.add("��f- ��3���� ȣȩ");
		if (t.hasPotionEffect(PotionEffectType.CONFUSION)) list.add("��f- ��dȥ��");
		if (t.hasPotionEffect(PotionEffectType.BLINDNESS)) list.add("��f- ��8�Ǹ�");
		if (!t.hasPotionEffect(PotionEffectType.POISON) && !t.hasPotionEffect(PotionEffectType.NIGHT_VISION) && !t.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE) &&
			!t.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE) && !t.hasPotionEffect(PotionEffectType.WEAKNESS) && !t.hasPotionEffect(PotionEffectType.SPEED) &&
			!t.hasPotionEffect(PotionEffectType.SLOW) && !t.hasPotionEffect(PotionEffectType.INVISIBILITY) && !t.hasPotionEffect(PotionEffectType.WITHER) &&
			!t.hasPotionEffect(PotionEffectType.JUMP) && !t.hasPotionEffect(PotionEffectType.HUNGER) && !t.hasPotionEffect(PotionEffectType.FAST_DIGGING) && 
			!t.hasPotionEffect(PotionEffectType.REGENERATION) && !t.hasPotionEffect(PotionEffectType.WATER_BREATHING) && !t.hasPotionEffect(PotionEffectType.CONFUSION) &&
			!t.hasPotionEffect(PotionEffectType.BLINDNESS)) list.add("��f- ��a����");
		
		return list;
	}
	
	public static int getDamageStat(StatsPlayer sp)
	{
		return sp.getStatPoint("��c���ݷ¡�0_��6");
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
		
		if (P.getInfoInt(p, "1.������ �ڵ�") != 0 && P.getInfoString(p, "1.������ �̸�") != null && !P.getInfoList(p, "1.������ ����").equals(Arrays.asList())) {
			String str = A.getLoreNum(P.getInfoList(p, "1.������ ����"));

			if (str.equalsIgnoreCase("�߰� ������")) {
				damage += A.getPlusDamage(P.getInfoList(p, "1.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ũ��Ƽ�� Ȯ��")) {
				cretprobe += A.getCretProbe(P.getInfoList(p, "1.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ũ��Ƽ�� ������")) {
				cretdamage += A.getCretDamage(P.getInfoList(p, "1.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ü�� ���")) {
				heart += A.getHeartGet(P.getInfoList(p, "1.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("������ ����")) {
				protect += A.getDamageDefense(P.getInfoList(p, "1.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ȸ�Ƿ�")) {
				dodge += A.getDodge(P.getInfoList(p, "1.������ ����"));
			}
		}
		
		if (P.getInfoInt(p, "2.������ �ڵ�") != 0 && P.getInfoString(p, "2.������ �̸�") != null && !P.getInfoList(p, "2.������ ����").equals(Arrays.asList())) {
			String str = A.getLoreNum(P.getInfoList(p, "2.������ ����"));
			if (str.equalsIgnoreCase("�߰� ������")) {
				damage += A.getPlusDamage(P.getInfoList(p, "2.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ũ��Ƽ�� Ȯ��")) {
				cretprobe += A.getCretProbe(P.getInfoList(p, "2.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ũ��Ƽ�� ������")) {
				cretdamage += A.getCretDamage(P.getInfoList(p, "2.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ü�� ���")) {
				heart += A.getHeartGet(P.getInfoList(p, "2.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("������ ����")) {
				protect += A.getDamageDefense(P.getInfoList(p, "2.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ȸ�Ƿ�")) {
				dodge += A.getDodge(P.getInfoList(p, "2.������ ����"));
			}
		}
		
		if (P.getInfoInt(p, "3.������ �ڵ�") != 0 && P.getInfoString(p, "3.������ �̸�") != null && !P.getInfoList(p, "3.������ ����").equals(Arrays.asList())) {
			String str = A.getLoreNum(P.getInfoList(p, "3.������ ����"));
			if (str.equalsIgnoreCase("�߰� ������")) {
				damage += A.getPlusDamage(P.getInfoList(p, "3.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ũ��Ƽ�� Ȯ��")) {
				cretprobe += A.getCretProbe(P.getInfoList(p, "3.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ũ��Ƽ�� ������")) {
				cretdamage += A.getCretDamage(P.getInfoList(p, "3.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ü�� ���")) {
				heart += A.getHeartGet(P.getInfoList(p, "3.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("������ ����")) {
				protect += A.getDamageDefense(P.getInfoList(p, "3.������ ����"));
			}
			
			else if (str.equalsIgnoreCase("ȸ�Ƿ�")) {
				dodge += A.getDodge(P.getInfoList(p, "3.������ ����"));
			}
		}

		list.add("");
		list.add("��7���ݷ�: ��c" + damage);
		if (cretprobe > 100) list.add("��7ġ��Ÿ Ȯ��: ��c100%");
		else list.add("��7ġ��Ÿ Ȯ��: ��c" + cretprobe + "%");
		list.add("��7ġ��Ÿ ������: ��c" + cretdamage);
		list.add("��7����� ���: ��c+" + heart);
		list.add("");
		list.add("��7ü��: ��d" + p.getHealth() + "��f/��d" + p.getMaxHealth());
		list.add("��7ȸ����: ��d" + hpUP);
		list.add("��7ȸ�Ƿ�: ��d" + dodge + "%");
		list.add("��7��ȣ��: ��d" + protect + "%");
		list.add("");
		return list;
	}
	
	public static List<String> getHunger(Player t)
	{
		List<String> list = new ArrayList<String>();
		
		list.add("��7�����: ��e" + t.getFoodLevel() + "��f/��e" + 20);
		return list;
	}
	
	public static List<String> getStat(Player t)
	{
		List<String> list = new ArrayList<String>();
		list.add("��7������ Ŭ���� �÷��̾��� ���¸� ���ϴ�.");
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
		if (t.hasPermission("[����]")) zunzec = "��c";
		else if (t.hasPermission("[������]")) zunzec = "��d";
		else if (t.hasPermission("[����]")) zunzec = "��l";
		else if (t.hasPermission("[������]")) zunzec = "��a";
		else if (t.hasPermission("[�ü�]")) zunzec = "��e";
		else zunzec = "��f";
		
		PVPPlayer pp = V.getPVPPlayera(name);
		if (pp != null) {
			list.add("��f");
			list.add("��7PVP Ŭ����: ��c" + pp.getclassExpress());
			list.add("��7ų ����: ��c" + pp.getKill());
			list.add("��7���� ����: ��c" + pp.getDeath());
			list.add("��7����� óġ: ��c" + pp.getKillDivDeath());
			list.add("��7PVP ��ŷ: ��c" + getPvPRanking(t) + "��");
		} else {
			list.add("��f");
			list.add("��7PVP Ŭ����: ��7F");
			list.add("��7ų ����: ��c0");
			list.add("��7���� ����: ��c0");
			list.add("��7����� óġ: ��c0.0");
			list.add("��7PVP ��ŷ: ��c0��");
		}
		
		list.add("��f");
		if (t.getName().equalsIgnoreCase("shinkhan")) list.add("��7����: ��c�Ѱ�����");
		else if (t.isOp()) list.add("��7����: ��c������");
		else {
			list.add("��7����: " + zunzec + "" + me.espoo.rpg2.Method.getInfoString(t, "����"));
			list.add("��7���� �ܰ�: ��f" + (me.espoo.rpg2.Method.getInfoInt(t, "���� ī��Ʈ") - 1) + "�� ����");
		}
		
		list.add("��7����: ��e" + rp.getRpgLevel());
		list.add("��7����ġ: ��e" + rp.getRpgExp() + "��7/��e" + rp.getRpgNeedExp());
		list.add("��7����ġ ��ŷ: ��e" + getExpRanking(t) + "��");
		list.add("��f");
		list.add("��7������ ��: ��f$" + val);
		
		if (party != null) {
			list.add("��7��Ƽ ���� ����: ��f����");
		} else {
			list.add("��7��Ƽ ���� ����: ��f�̰���");
		}
		

		if (guild != null) {
			list.add("��7������ ���: ��f" + guild);
		} else {
			list.add("��7������ ���: ��f����");
		}

		if (me.espoo.option.PlayerYml.getInfoBoolean(t, "��ȯ ��û")) {
			list.add("��f");
			list.add("��8��l* ��fŬ���� ���¸� ���ϴ�.");	
		}
		
		return list;
	}
}
