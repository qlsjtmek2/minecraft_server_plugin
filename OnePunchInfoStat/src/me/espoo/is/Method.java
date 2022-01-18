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
		}

		list.add("");
		list.add("��7���ݷ�: ��c" + damage);
		list.add("��7ġ��Ÿ Ȯ��: ��c" + cretprobe + "%");
		list.add("��7ġ��Ÿ ������: ��c" + cretdamage);
		list.add("��7����� ���: ��c+" + heart);
		list.add("");
		list.add("��7ü��: ��d" + p.getHealth() + "��f/��d" + p.getMaxHealth());
		list.add("��7ȸ����: ��d" + hpUP);
		list.add("��7ȸ�Ƿ�: ��d" + dodge + "%");
		list.add("��7��ȣ��: ��d" + protect + "%");
		list.add("");
		if (speed == 0F) list.add("��7���ǵ�: ��b�⺻");
		else list.add("��7���ǵ�: ��b" + speed + "��f/��b1");
		list.add("��7���� ��ȭ: ��b+" + jump);
		list.add("");
		return list;
	}
	
	public static List<String> getHunger(Player t)
	{
		List<String> list = new ArrayList<String>();
		
		list.add("��7�����: ��e" + t.getFoodLevel() + "��f/��e" + 20);
		return list;
	}
	
	public static List<String> getFatigue(Player t)
	{
		List<String> list = new ArrayList<String>();
		
		list.add("��7�Ƿε�: ��b" + ((int) API.getFatigue(t)) + "��f/��b" + 100);
		return list;
	}
	
	public static int getStat1(Player t)
	{
		if (me.espoo.file.PlayerYml.getInfoBoolean(t, "�� �ٷ� ���� ����")) {
			int i = me.espoo.punchstat.Method.get1Stat(t) + me.espoo.file.PlayerYml.getInfoInt(t, "�� �ٷ�") + me.espoo.punchstat.Method.get1StatEffect(t) + me.espoo.punchstat.Method.getLoon(t);
			return i;	
		} else {
			return 0;	
		}
	}
	
	public static List<String> getStat2(Player t)
	{
		List<String> list = new ArrayList<String>();
		if (me.espoo.file.PlayerYml.getInfoBoolean(t, "���� ���� ����")) {
			int i = me.espoo.punchstat.Method.get2Stat(t) + me.espoo.file.PlayerYml.getInfoInt(t, "����") + me.espoo.punchstat.Method.get2StatEffect(t) + me.espoo.punchstat.Method.getLoon(t);
			list.add("��7���� ��: ��f" + i);
			return list;
		} else {
			list.add("��7���� ��: ��f0");
			return list;	
		}
	}
	
	public static int getStat3(Player t)
	{
		if (me.espoo.file.PlayerYml.getInfoBoolean(t, "�ٸ� �ٷ� ���� ����")) {
			int i = me.espoo.punchstat.Method.get3Stat(t) + me.espoo.file.PlayerYml.getInfoInt(t, "�ٸ� �ٷ�") + me.espoo.punchstat.Method.get3StatEffect(t) + me.espoo.punchstat.Method.getLoon(t);
			return i;
		} else {
			return 0;	
		}
	}
	
	public static float getStat4(Player t)
	{
		if (me.espoo.file.PlayerYml.getInfoBoolean(t, "���ǵ� ���� ����")) {
			int i = 0;
			if (me.espoo.punchstat.Method.get4Stat(t) + me.espoo.file.PlayerYml.getInfoInt(t, "���ǵ�") > 200) {
	    		i = 200;
			} else {
				i = me.espoo.punchstat.Method.get4Stat(t) + me.espoo.file.PlayerYml.getInfoInt(t, "���ǵ�") + me.espoo.punchstat.Method.get4StatEffect(t) + me.espoo.punchstat.Method.getLoon(t);
			}
			
			return i;
		} else {
			return 0;	
		}
	}
	
	public static List<String> getStat(Player t)
	{
		List<String> list = new ArrayList<String>();
		list.add("��7������ Ŭ���� �÷��̾��� ���¸� ���ϴ�.");
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
			str = "��4S";
		}
		
		else if (M.getPlayerScore(t.getName()) >= 30000 && M.getPlayerRanking(t.getName()) > 15) {
			str = "��cA";
		}
		
		else if (M.getPlayerScore(t.getName()) >= 4000 && M.getPlayerScore(t.getName()) < 30000 && M.getPlayerRanking(t.getName()) > 15) {
			str = "��bB";
		}
		
		else if (M.getPlayerScore(t.getName()) < 4000 && PlayerYml.getInfoBoolean(t, "���") && M.getPlayerRanking(t.getName()) > 15)
		{
			str = "��eC";
		}
		
		else {
			str = "����";
		}
		
		

		list.add("��7===============================");
		list.add("��f" + ExpAPI.getDayMessage(t));
		list.add("��7===============================");
		list.add("");
		list.add("��7����� ��ũ: ��f" + str);
		list.add("��7����� ����: ��f" + M.getPlayerScore(t.getName()));
		list.add("��7����� ����: ��f" + M.getPlayerRanking(t.getName()) + "��");
		list.add("");
		list.add("��7����ġ: ��f" + ExpAPI.getExp(t.getName()));
		list.add("��7����ġ ����: ��f" + ExpAPI.getRank(t.getName()) + "��");
		list.add("");
		list.add("��7������ ��: ��f" + val + "��");
		
		if (party != null) {
			list.add("��7��Ƽ ���� ����: ��a����");
		} else {
			list.add("��7��Ƽ ���� ����: ��c�̰���");
		}
		

		if (guild != null) {
			list.add("��7������ ���: ��f" + guild);
		} else {
			list.add("��7������ ���: ��f����");
		}

		list.add("");
		list.add("��8��l<HELP> ��7Ŭ���� �÷��̾� ���¸� ���ϴ�.");
		
		if (t.getName().equalsIgnoreCase(openuser)) {
			list.add("��8��l<HELP> ��7���� �޼����� �����ϰ� �����ø�");
			list.add("��7/����ġ ��� <�޼���> �� ä��â�� �Է��ϼ���.");
		}
		
		return list;
	}
}
