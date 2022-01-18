package me.espoo.punchstat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.espoo.file.PlayerYml;

public class GUIMessage {
	static PlayerYml P;
	
	public static void CreateGUIMessageConfig(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
            config.set("스텟 GUI 이름", "사이타마 스텟");

     		List<String> list1;
            list1 = config.getStringList("팔 근력.아이템 설명");
            list1.add("&7&l===========================");
            list1.add("&f벽을 앞에 두고 왼클릭을 하여 팔굽혀");
            list1.add("&f펴기 자세를 취하고 &b100번 &f누적하면");
            list1.add("&f공격력이 &c0.3 &f씩 상승한다.");
            list1.add("&7&l===========================");
            list1.add("&f");
            list1.add("&6현재 당신의 &c팔 근력 &6스텟은 &c<1stat> &6입니다.");
            list1.add("&6당신은 &c<1effect> &6만큼의 &c공격력 &6효과를 냅니다.");
            list1.add("&6팔 근력 스텟 적용 여부는 &c<1true> &6입니다.");
            config.set("팔 근력.아이템 이름", "&c팔 근력 &6스텟");
            config.set("팔 근력.아이템 설명", list1);
            config.set("팔 근력.아이템 코드", 276);
            config.set("팔 근력.아이템 데이터코드", 0);
            config.set("팔 근력.아이템 수량", 1);

     		List<String> list2;
            list2 = config.getStringList("복근.아이템 설명");
            list2.add("&7&l===========================");
            list2.add("&f쉬프트를 누른 상태에서 점프를 &b100번");
            list2.add("&f누적하면 윗몸 일으키기 효과를 내서");
            list2.add("&f복근이 강화되어 체력이 &c0.5 &f씩 상승한다.");
            list2.add("&7&l===========================");
            list2.add("&f");
            list2.add("&6현재 당신의 &c복근 &6스텟은 &c<2stat> &6입니다.");
            list2.add("&6당신은 &c<2effect> &6만큼의 &c체력 &6효과를 냅니다.");
            list2.add("&6복근 스텟 적용 여부는 &c<2true> &6입니다.");
            config.set("복근.아이템 이름", "&d복근 &6스텟");
            config.set("복근.아이템 설명", list2);
            config.set("복근.아이템 코드", 311);
            config.set("복근.아이템 데이터코드", 0);
            config.set("복근.아이템 수량", 1);
            
     		List<String> list3;
            list3 = config.getStringList("다리 근력.아이템 설명");
            list3.add("&7&l===========================");
            list3.add("&f쉬프트키를 &b100번 &f누적하면 스쿼트");
            list3.add("&f100개의 효과를 내어서 10스텟당");
            list3.add("&f점프 강화가 &cI &f씩 상승한다.");
            list3.add("&7&l===========================");
            list3.add("&f");
            list3.add("&6현재 당신의 &c다리 근력 &6스텟은 &c<3stat> &6입니다.");
            list3.add("&6당신은 &c<3effect> &6만큼의 &b점프 강화 &6효과를 냅니다.");
            list3.add("&6다리 근력 스텟 적용 여부는 &c<3true> &6입니다.");
            config.set("다리 근력.아이템 이름", "&b다리 근력 &6스텟");
            config.set("다리 근력.아이템 설명", list3);
            config.set("다리 근력.아이템 코드", 312);
            config.set("다리 근력.아이템 데이터코드", 0);
            config.set("다리 근력.아이템 수량", 1);
            
     		List<String> list4;
            list4 = config.getStringList("스피드.아이템 설명");
            list4.add("&7&l===========================");
            list4.add("&f스피드는 블럭을 &b1000칸 &f이동하면");
            list4.add("&f런닝 10km의 효과를 내어서 스피드가");
            list4.add("&c0.005 &f씩 상승한다.");
            list4.add("&7&l===========================");
            list4.add("&f");
            list4.add("&6현재 당신의 &c스피드 &6스텟은 &c<4stat>&6/&c200 &6입니다.");
            list4.add("&6당신은 &c<4effect> &6만큼의 &a스피드 &6효과를 냅니다.");
            list4.add("&6스피드 스텟 적용 여부는 &c<4true> &6입니다.");
            config.set("스피드.아이템 이름", "&a스피드 &6스텟");
            config.set("스피드.아이템 설명", list4);
            config.set("스피드.아이템 코드", 313);
            config.set("스피드.아이템 데이터코드", 0);
            config.set("스피드.아이템 수량", 1);
            
     		List<String> list5;
            list5 = config.getStringList("설명.아이템 설명");
            list5.add("&6사이타마의 &c&l하드코어 &6트레이닝");
            list5.add("&63년 동안 &c꾸준히 &6해보자!");
            list5.add("&f");
            list5.add("&71. &f팔굽혀펴기 100회");
            list5.add("&72. &f윗몸 일으키기 100회");
            list5.add("&73. &f스쿼트 100회");
            list5.add("&74. &f런닝 10km");
            config.set("설명.아이템 이름", "&e트레이닝 목록");
            config.set("설명.아이템 설명", list5);
            config.set("설명.아이템 코드", 386);
            config.set("설명.아이템 데이터코드", 0);
            config.set("설명.아이템 수량", 1);
            
     		List<String> list6;
            list6 = config.getStringList("설명2.아이템 설명");
            list6.add("&f[&c팔 근력&f] &6연속 보통 펀치 &f- &c200 스텟");
            list6.add("&f[&c팔 근력&f] &6진심 펀치 &f- &c500 스텟");
            list6.add("&f[&d복근&f] &6진심 박치기 &f- &c200 스텟");
            list6.add("&f[&d복근&f] &6진심 강화 &f- &c500 스텟");
            list6.add("&f[&b근력 강화&f] &6진심 점프 &f- &c200 스텟");
            list6.add("&f[&b근력 강화&f] &6진심 니킥 &f- &c500 스텟");
            list6.add("&f[&a스피드&f] &6진심 좌우 반복뛰기 &f- &c100 스텟");
            list6.add("&f[&a스피드&f] &6진심 런닝 &f- &c200 스텟");
            config.set("설명2.아이템 이름", "&e스킬 획득 목록");
            config.set("설명2.아이템 설명", list6);
            config.set("설명2.아이템 코드", 386);
            config.set("설명2.아이템 데이터코드", 0);
            config.set("설명2.아이템 수량", 1);
            
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[OnePunchStat] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static String getMessage(Player p, String name) {
		File f = new File("plugins/OnePunchStat/GUIMessage.yml");
		File folder = new File("plugins/OnePunchStat");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		String Message = config.getString(name);
		Message = Method.replaceAllColors(Message);
		return Message;
	}
	
	public static int getItemCode(String name) {
		File f = new File("plugins/OnePunchStat/GUIMessage.yml");
		File folder = new File("plugins/OnePunchStat");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		int Code = config.getInt(name);
		return Code;
	}
	
	@SuppressWarnings("static-access")
	public static List<String> getLoreList(Player p, String name) {
		File f = new File("plugins/OnePunchStat/GUIMessage.yml");
		File folder = new File("plugins/OnePunchStat");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateGUIMessageConfig(f, folder, config);
		}
		
		List<String> list = config.getStringList(name);
		List<String> Returnlist = config.getStringList(name);
		Returnlist.clear();
        for (String str : list) {
    		str = Method.replaceAllColors(str);
    		str = str.replaceAll("<1stat>", Integer.toString(Method.get1Stat(p) + P.getInfoInt(p, "팔 근력") + Method.get1StatEffect(p) + Method.getLoon(p)));
    		str = str.replaceAll("<2stat>", Integer.toString(Method.get2Stat(p) + P.getInfoInt(p, "복근") + Method.get2StatEffect(p) + Method.getLoon(p)));
    		str = str.replaceAll("<3stat>", Integer.toString(Method.get3Stat(p) + P.getInfoInt(p, "다리 근력") + Method.get3StatEffect(p) + Method.getLoon(p)));
    		if (Method.get4Stat(p) + P.getInfoInt(p, "스피드") > 200) {
        		str = str.replaceAll("<4stat>", Integer.toString(200));
    		} else {
        		str = str.replaceAll("<4stat>", Integer.toString(Method.get4Stat(p) + P.getInfoInt(p, "스피드") + Method.get4StatEffect(p) + Method.getLoon(p)));
    		}
    		str = str.replaceAll("<1true>", String.valueOf(P.getInfoBoolean(p, "팔 근력 스텟 적용")));
    		str = str.replaceAll("<2true>", String.valueOf(P.getInfoBoolean(p, "복근 스텟 적용")));
    		str = str.replaceAll("<3true>", String.valueOf(P.getInfoBoolean(p, "다리 근력 스텟 적용")));
    		str = str.replaceAll("<4true>", String.valueOf(P.getInfoBoolean(p, "스피드 스텟 적용")));
    		double i = (P.getInfoInt(p, "팔 근력") + Method.get1Stat(p) + Method.get1StatEffect(p) + Method.getLoon(p)) * 0.3;
    		str = str.replaceAll("<1effect>", Double.toString(i));
    		i = (P.getInfoInt(p, "복근") + Method.get2Stat(p) + Method.get2StatEffect(p) + Method.getLoon(p)) * 0.6;
    		str = str.replaceAll("<2effect>", Double.toString(i));
    		int y = (P.getInfoInt(p, "다리 근력") + Method.get3Stat(p) + Method.get3StatEffect(p) + Method.getLoon(p)) / 10;
    		str = str.replaceAll("<3effect>", Integer.toString(y));
    		i = (P.getInfoInt(p, "스피드") + Method.get4Stat(p) + Method.get4StatEffect(p) + Method.getLoon(p)) * 0.005;
    		if (i < 0.2) {
    			str = str.replaceAll("<4effect>", "기본");
    		} else {
    			if (i > 1) {
        			str = str.replaceAll("<4effect>", Double.toString(1D));
    			} else {
        			str = str.replaceAll("<4effect>", Double.toString(i));
    			}
    		}
    		Returnlist.add(str);
        } return Returnlist;
	}
}
