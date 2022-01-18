package me.espoo.book;

import java.util.ArrayList;
import java.util.List;

public class GUIMessage {
	public static List<String> ML = new ArrayList<String>();
	public static List<String> Glass = new ArrayList<String>();
	public static List<String> Water = new ArrayList<String>();
	public static List<String> Lava = new ArrayList<String>();
	public static List<String> WaeponBook = new ArrayList<String>();
	public static List<String> XWaeponBook = new ArrayList<String>();
	public static List<String> nGearBook = new ArrayList<String>();
	public static List<String> uGearBook = new ArrayList<String>();
	public static List<String> LoonBook = new ArrayList<String>();
	public static List<String> XLoonBook = new ArrayList<String>();
	public static List<String> ToolBook = new ArrayList<String>();
	
	public static void setMainLore()
	{
		ML.add("§f아이템 클릭시 해당");
		ML.add("§f도감을 §e오픈§f합니다.");
	}
	
	public static void setGlass()
	{
		Glass.add("§7=== [ §e도감 등록하는 법 §7] ===");
		Glass.add("§f도감에 등록할 아이템을 집고");
		Glass.add("§f아이템 이름과 알맞는 도감에");
		Glass.add("§f집어 넣으면 등록됩니다.");
		Glass.add("§7아이템은 사라지지 않습니다.");
		Glass.add("§7========================");
	}
	
	public static void setWater()
	{
		Water.add("§f이전에 봤던 창으로 되돌아갑니다.");
	}
	
	public static void setLava()
	{
		Lava.add("§f다음 도감으로 이동합니다.");
	}
	
	public static void setWaeponBook()
	{
		WaeponBook.add("§7권장 직업 §6[§f사이타마§6]");
		WaeponBook.add("§f");
		WaeponBook.add("§f");
		WaeponBook.add("§7등급: §6★★★★★★");
		WaeponBook.add("§7분류: §f장갑");
		WaeponBook.add("§7공격 속도: §f빠름");
		WaeponBook.add("§f");
		WaeponBook.add("§f+80 공격력");
		WaeponBook.add("§f+20 팔 근력 스텟");
		WaeponBook.add("§f+20 복근 스텟");
		WaeponBook.add("§f+20 다리 근력 스텟");
		WaeponBook.add("§f+20 스피드 스텟");
		WaeponBook.add("§f");
		WaeponBook.add("§f[§6+§f] 도감을 다 채우면 지급");
		WaeponBook.add("§f[§6+§f] 되는 아이템 입니다.");
	}
	
	public static void setXWaeponBook()
	{
	    XWaeponBook.add("§7§l=================");
	    XWaeponBook.add("§f무기나 룬의 강화 수를");
	    XWaeponBook.add("§f무조건 10강 으로 맞춰주");
	    XWaeponBook.add("§f는 강화 주문서이다.");
	    XWaeponBook.add("");
	    XWaeponBook.add("§f이 아이템을 들고 강화");
	    XWaeponBook.add("§f하고 싶은 아이템에 올려");
	    XWaeponBook.add("§f두면 그 아이템이 강화된다.");
	    XWaeponBook.add("§7§l=================");
		XWaeponBook.add("§f");
		XWaeponBook.add("§f[§6+§f] 도감을 다 채우면 지급");
		XWaeponBook.add("§f[§6+§f] 되는 아이템 입니다.");
	}
	
	public static void setnGearBook()
	{
		nGearBook.add("§7아이템 계열 §8[§f일반§8]");
		nGearBook.add("§f");
		nGearBook.add("§7§l==[ §6나오는 아이템 §7§l]==");
		nGearBook.add("§7- §f사이타마 모자 §e(일반)");
		nGearBook.add("§7- §f사이타마 튜닉 §e(일반)");
		nGearBook.add("§7- §f사이타마 바지 §e(일반)");
		nGearBook.add("§7- §f사이타마 신발 §e(일반)");
		nGearBook.add("§7§l=================");
		nGearBook.add("§f");
		nGearBook.add("§f[§6+§f] 도감을 다 채우면 지급");
		nGearBook.add("§f[§6+§f] 되는 아이템 입니다.");
	}
	
	public static void setuGearBook()
	{
		uGearBook.add("§7아이템 계열 §6[§f유니크§6]");
		uGearBook.add("§f");
		uGearBook.add("§7§l==[ §6나오는 아이템 §7§l]==");
		uGearBook.add("§7- §f사이타마 모자 §6(유니크)");
		uGearBook.add("§7- §f사이타마 튜닉 §6(유니크)");
		uGearBook.add("§7- §f사이타마 바지 §6(유니크)");
		uGearBook.add("§7- §f사이타마 신발 §6(유니크)");
		uGearBook.add("§7§l=================");
		uGearBook.add("§f");
		uGearBook.add("§f[§6+§f] 도감을 다 채우면 지급");
		uGearBook.add("§f[§6+§f] 되는 아이템 입니다.");
	}
	
	public static void setLoonBook()
	{
		LoonBook.add("§f");
		LoonBook.add("§7등급: §6★");
		LoonBook.add("§7강화: §f+0");
		LoonBook.add("§7모든 스텟 효과: §f5");
		LoonBook.add("§7경험치 보너스: §f50%");
		LoonBook.add("§7특수 효과: §f모든");
		LoonBook.add("§f스텟 효과 보너스를");
		LoonBook.add("§f받고, PVP 경험치를");
		LoonBook.add("§f추가로 더 받는다.");
		LoonBook.add("§f");
		LoonBook.add("§7§l============");
		LoonBook.add("§f/룬 명령어 입력 후");
		LoonBook.add("§f창 안에 룬을 넣어");
		LoonBook.add("§f주시면 적용됩니다.");
		LoonBook.add("§7§l============");
		LoonBook.add("§f");
		LoonBook.add("§f[§6+§f] 도감을 다 채우면 지급");
		LoonBook.add("§f[§6+§f] 되는 아이템 입니다.");
	}
	
	public static void setXLoonBook()
	{
		XLoonBook.add("§f");
		XLoonBook.add("§7등급: §6★★★★★★");
		XLoonBook.add("§7강화: §f+10");
		XLoonBook.add("§7모든 스텟 효과: §f55");
		XLoonBook.add("§7경험치 보너스: §f250%");
		XLoonBook.add("§7특수 효과: §f모든");
		XLoonBook.add("§f스텟 효과 보너스를");
		XLoonBook.add("§f받고, PVP 경험치를");
		XLoonBook.add("§f추가로 더 받는다.");
		XLoonBook.add("§f");
		XLoonBook.add("§7§l============");
		XLoonBook.add("§f/룬 명령어 입력 후");
		XLoonBook.add("§f창 안에 룬을 넣어");
		XLoonBook.add("§f주시면 적용됩니다.");
		XLoonBook.add("§7§l============");
		XLoonBook.add("§f");
		XLoonBook.add("§f[§6+§f] 도감을 다 채우면 지급");
		XLoonBook.add("§f[§6+§f] 되는 아이템 입니다.");
	}
	
	public static void setToolBook()
	{
		ToolBook.add("§7§l=================");
		ToolBook.add("§f프리미엄 강화 주문서이다.");
		ToolBook.add("§f이 아이템을 들고 강화");
		ToolBook.add("§f하고 싶은 아이템에 올려");
		ToolBook.add("§f두면 그 아이템이 강화된다.");
		ToolBook.add("");
		ToolBook.add("§7특수 능력: §f강화 할 아이");
		ToolBook.add("§f템이 §c파괴§f되지 않고, 강화");
		ToolBook.add("§f성공 확률이 대폭 §b상승§f한다.");
	    ToolBook.add("§7§l=================");
	    ToolBook.add("§f");
	    ToolBook.add("§f[§6+§f] 도감을 다 채우면 지급");
	    ToolBook.add("§f[§6+§f] 되는 아이템 입니다.");
	}
	
	public static List<String> getNoLore(int i)
	{
		List<String> list = new ArrayList<String>();
		list.add("§7§l==============");
		if (i == 0) {
			list.add("§f강화가 안된 보통");
		} else {
			list.add("§f강화가 +" + i + "된 보통");
		}
		list.add("§f곡괭이다. 평범하게");
		list.add("§f채광을 시도해보자.");
		list.add("§7§l==============");
		return list;
	}
	
	public static List<String> getOneLore(int i)
	{
		List<String> list = new ArrayList<String>();
		list.add("§7§l==============");
		if (i == 0) {
			list.add("§f강화가 안된 원펀");
		} else {
			list.add("§f강화가 +" + i + "된 원펀");
		}
		list.add("§f곡괭이다. 진심으로");
		list.add("§f채광을 시도해보자.");
		list.add("§7§l==============");
		return list;
	}
	
	public static List<String> getMaxLore()
	{
		List<String> list = new ArrayList<String>();
		list.add("§7§l==============");
		list.add("§f강화가 안된 최고");
		list.add("§f곡괭이다. 여유있게");
		list.add("§f채광을 마무리하자.");
		list.add("§7§l==============");
		return list;
	}
}
