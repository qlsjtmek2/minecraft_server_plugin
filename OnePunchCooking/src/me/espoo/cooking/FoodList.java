package me.espoo.cooking;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class FoodList {
	public static List<List<Material>> FoodList = new ArrayList<List<Material>>();
	public static List<String> StringList = new ArrayList<String>();
	public static List<String> EffectList = new ArrayList<String>();
	public static List<Integer> IntegerList = new ArrayList<Integer>();
	public static List<String> NameList = new ArrayList<String>();
	public static List<Material> 카레 = new ArrayList<Material>();
	public static List<Material> 치킨 = new ArrayList<Material>();
	public static List<Material> 피자 = new ArrayList<Material>();
	public static List<Material> 케이크 = new ArrayList<Material>();
	public static List<Material> 솔의눈 = new ArrayList<Material>();
	public static List<Material> 바닷가재 = new ArrayList<Material>();
	public static List<Material> 피시앤칩스 = new ArrayList<Material>();
	public static List<Material> 탄산수 = new ArrayList<Material>();
	public static List<Material> 불고기 = new ArrayList<Material>();
	public static List<Material> 우동 = new ArrayList<Material>();
	public static List<Material> 짬뽕 = new ArrayList<Material>();
	public static List<Material> 닭강정 = new ArrayList<Material>();
	public static List<Material> 김치찌개 = new ArrayList<Material>();
	public static List<Material> 감자칩 = new ArrayList<Material>();
	public static List<Material> 햄버거 = new ArrayList<Material>();
	public static List<Material> 호박파이 = new ArrayList<Material>();
	public static List<Material> 도넛 = new ArrayList<Material>();
	public static List<Material> 주먹밥 = new ArrayList<Material>();
	public static List<Material> 갈비 = new ArrayList<Material>();
	public static List<Material> 돼지고기바비큐 = new ArrayList<Material>();
	public static List<Material> 에그타르트 = new ArrayList<Material>();
	public static List<Material> 잡채 = new ArrayList<Material>();
	public static List<Material> 프렌치토스트 = new ArrayList<Material>();
	public static List<Material> 쿠키칩 = new ArrayList<Material>();
	public static List<Material> 버터팝콘 = new ArrayList<Material>();
	public static List<Material> 라면 = new ArrayList<Material>();
	public static List<Material> 닭갈비 = new ArrayList<Material>();
	public static List<Material> 자장면 = new ArrayList<Material>();
	public static List<Material> 통옥수수 = new ArrayList<Material>();
	public static List<Material> 딸기주스 = new ArrayList<Material>();
	public static List<Material> 문어빵 = new ArrayList<Material>();
	public static List<Material> 크로아상 = new ArrayList<Material>();
	public static List<Material> 김치 = new ArrayList<Material>();
	public static List<Material> 볶음국수 = new ArrayList<Material>();
	public static List<Material> 탕수육 = new ArrayList<Material>();
	public static List<Material> 양송이스프 = new ArrayList<Material>();
	public static List<Material> 당근주스 = new ArrayList<Material>();
	public static List<Material> 통오리구이 = new ArrayList<Material>();
	public static List<Material> 밥 = new ArrayList<Material>();
	public static List<Material> 아이스크림 = new ArrayList<Material>();
	public static List<Material> 훈제고기 = new ArrayList<Material>();
	public static List<Material> 콜라 = new ArrayList<Material>();
	public static List<Material> 우유 = new ArrayList<Material>();
	public static List<Material> 스테이크 = new ArrayList<Material>();
	public static List<Material> 녹차즙 = new ArrayList<Material>();
	public static List<Material> 삼겹살 = new ArrayList<Material>();
	public static List<Material> 초콜릿 = new ArrayList<Material>();
	public static List<Material> 오렌지주스 = new ArrayList<Material>();
	public static List<Material> 빙수 = new ArrayList<Material>();
	public static List<Material> 사이다 = new ArrayList<Material>();
	
	public static Material getWheat(int STACK) { return new MaterialData(296, (byte) 0).toItemStack(STACK).getType(); }
	public static Material getCarrot(int STACK) { return new MaterialData(391, (byte) 0).toItemStack(STACK).getType(); }
	public static Material getPotato(int STACK) { return new MaterialData(392, (byte) 0).toItemStack(STACK).getType(); }
	public static Material getLily(int STACK) { return new MaterialData(111, (byte) 0).toItemStack(STACK).getType(); }
	public static Material getYello(int STACK) { return new MaterialData(37, (byte) 0).toItemStack(STACK).getType(); }
	public static Material getRose(int STACK) { return new MaterialData(38, (byte) 0).toItemStack(STACK).getType(); }
	public static Material getCanes(int STACK) { return new MaterialData(338, (byte) 0).toItemStack(STACK).getType(); }
	public static Material getWater() { return new MaterialData(373, (byte) 0).toItemStack(1).getType(); }
	public static Material getBowl(int STACK) { return new MaterialData(281, (byte) 0).toItemStack(STACK).getType(); }
	public static Material getSeeds(int STACK) { return new MaterialData(295, (byte) 0).toItemStack(STACK).getType(); }
	
	public static void setFood() {
		카레.add(getWheat(32));
		카레.add(getCarrot(18));
		카레.add(getPotato(18));
		카레.add(getBowl(1));
		
		치킨.add(getWheat(32));
		치킨.add(getLily(12));
		치킨.add(getCanes(8));
		치킨.add(getWheat(32));
		
		피자.add(getWheat(48));
		피자.add(getPotato(32));
		피자.add(getLily(16));
		피자.add(getSeeds(5));
		
		케이크.add(getWheat(48));
		케이크.add(getLily(8));
		케이크.add(getCanes(16));
		케이크.add(getBowl(1));
		
		솔의눈.add(getCanes(16));
		솔의눈.add(getLily(20));
		솔의눈.add(getSeeds(32));
		솔의눈.add(getWater());
		
		바닷가재.add(getLily(12));
		바닷가재.add(getRose(16));
		바닷가재.add(getYello(16));
		바닷가재.add(getPotato(22));
		
		피시앤칩스.add(getLily(16));
		피시앤칩스.add(getSeeds(48));
		피시앤칩스.add(getRose(12));
		피시앤칩스.add(getYello(12));
		
		탄산수.add(getSeeds(32));
		탄산수.add(getCanes(16));
		탄산수.add(getLily(16));
		탄산수.add(getWater());
		
		불고기.add(getYello(16));
		불고기.add(getRose(16));
		불고기.add(getSeeds(32));
		불고기.add(getCanes(32));
		
		우동.add(getWheat(48));
		우동.add(getCarrot(32));
		우동.add(getSeeds(16));
		우동.add(getBowl(1));
		
		짬뽕.add(getWheat(48));
		짬뽕.add(getCanes(16));
		짬뽕.add(getRose(16));
		짬뽕.add(getBowl(1));
		
		닭강정.add(getYello(16));
		닭강정.add(getRose(16));
		닭강정.add(getWheat(16));
		닭강정.add(getSeeds(32));
		
		김치찌개.add(getRose(48));
		김치찌개.add(getYello(16));
		김치찌개.add(getPotato(16));
		김치찌개.add(getBowl(1));
		
		감자칩.add(getPotato(32));
		감자칩.add(getPotato(16));
		감자칩.add(getPotato(8));
		감자칩.add(getSeeds(16));
		
		햄버거.add(getWheat(20));
		햄버거.add(getPotato(16));
		햄버거.add(getSeeds(8));
		햄버거.add(getBowl(1));
		
		호박파이.add(getWheat(18));
		호박파이.add(getCarrot(10));
		호박파이.add(getRose(20));
		호박파이.add(getLily(8));
		
		도넛.add(getWheat(16));
		도넛.add(getSeeds(16));
		도넛.add(getYello(10));
		도넛.add(getRose(10));
		
		주먹밥.add(getWheat(32));
		주먹밥.add(getSeeds(32));
		주먹밥.add(getPotato(8));
		주먹밥.add(getCarrot(8));
		
		갈비.add(getRose(16));
		갈비.add(getYello(16));
		갈비.add(getSeeds(32));
		갈비.add(getCanes(8));
		
		돼지고기바비큐.add(getYello(16));
		돼지고기바비큐.add(getRose(16));
		돼지고기바비큐.add(getPotato(16));
		돼지고기바비큐.add(getWheat(32));
		
		에그타르트.add(getPotato(32));
		에그타르트.add(getWheat(16));
		에그타르트.add(getYello(16));
		에그타르트.add(getLily(8));
		
		잡채.add(getSeeds(48));
		잡채.add(getCanes(16));
		잡채.add(getCarrot(8));
		잡채.add(getPotato(8));
		
		프렌치토스트.add(getWheat(32));
		프렌치토스트.add(getPotato(32));
		프렌치토스트.add(getLily(8));
		프렌치토스트.add(getSeeds(8));
		
		쿠키칩.add(getWheat(20));
		쿠키칩.add(getCanes(16));
		쿠키칩.add(getSeeds(12));
		쿠키칩.add(getYello(4));
		
		버터팝콘.add(getSeeds(32));
		버터팝콘.add(getWheat(22));
		버터팝콘.add(getCanes(16));
		버터팝콘.add(getYello(8));
		
		라면.add(getPotato(32));
		라면.add(getWheat(32));
		라면.add(getSeeds(16));
		라면.add(getBowl(1));
		
		닭갈비.add(getYello(16));
		닭갈비.add(getRose(16));
		닭갈비.add(getCarrot(48));
		닭갈비.add(getPotato(24));
		
		자장면.add(getWheat(32));
		자장면.add(getWheat(32));
		자장면.add(getCanes(16));
		자장면.add(getBowl(1));
		
		통옥수수.add(getWheat(16));
		통옥수수.add(getCarrot(16));
		통옥수수.add(getPotato(16));
		통옥수수.add(getSeeds(16));
		
		딸기주스.add(getRose(48));
		딸기주스.add(getRose(16));
		딸기주스.add(getYello(8));
		딸기주스.add(getWater());
		
		문어빵.add(getCanes(16));
		문어빵.add(getPotato(20));
		문어빵.add(getRose(8));
		문어빵.add(getLily(4));
		
		크로아상.add(getWheat(48));
		크로아상.add(getYello(8));
		크로아상.add(getPotato(8));
		크로아상.add(getCanes(8));
		
		김치.add(getRose(42));
		김치.add(getCarrot(32));
		김치.add(getSeeds(16));
		김치.add(getCanes(8));
		
		볶음국수.add(getWheat(48));
		볶음국수.add(getPotato(16));
		볶음국수.add(getCarrot(16));
		볶음국수.add(getCanes(8));
		
		탕수육.add(getWheat(48));
		탕수육.add(getSeeds(16));
		탕수육.add(getPotato(32));
		탕수육.add(getLily(10));
		
		양송이스프.add(getYello(16));
		양송이스프.add(getRose(16));
		양송이스프.add(getCarrot(32));
		양송이스프.add(getBowl(1));
		
		당근주스.add(getCarrot(64));
		당근주스.add(getCanes(10));
		당근주스.add(getSeeds(16));
		당근주스.add(getWater());
		
		통오리구이.add(getWheat(48));
		통오리구이.add(getPotato(32));
		통오리구이.add(getCarrot(32));
		통오리구이.add(getLily(8));
		
		밥.add(getWheat(64));
		밥.add(getSeeds(32));
		밥.add(getRose(2));
		밥.add(getYello(2));
		
		아이스크림.add(getCanes(20));
		아이스크림.add(getSeeds(32));
		아이스크림.add(getYello(8));
		아이스크림.add(getWater());
		
		훈제고기.add(getSeeds(32));
		훈제고기.add(getLily(16));
		훈제고기.add(getRose(16));
		훈제고기.add(getYello(16));
		
		콜라.add(getCanes(24));
		콜라.add(getLily(16));
		콜라.add(getRose(32));
		콜라.add(getWater());
		
		우유.add(getWheat(8));
		우유.add(getCarrot(16));
		우유.add(getSeeds(32));
		우유.add(getWater());
		
		스테이크.add(getPotato(32));
		스테이크.add(getCarrot(32));
		스테이크.add(getRose(16));
		스테이크.add(getYello(16));
		
		녹차즙.add(getLily(24));
		녹차즙.add(getSeeds(24));
		녹차즙.add(getCanes(16));
		녹차즙.add(getWater());
		
		삼겹살.add(getCanes(16));
		삼겹살.add(getWheat(48));
		삼겹살.add(getRose(16));
		삼겹살.add(getYello(16));
		
		초콜릿.add(getCanes(32));
		초콜릿.add(getLily(16));
		초콜릿.add(getYello(16));
		초콜릿.add(getRose(16));
		
		오렌지주스.add(getCarrot(48));
		오렌지주스.add(getYello(16));
		오렌지주스.add(getSeeds(16));
		오렌지주스.add(getWater());
	
		빙수.add(getLily(16));
		빙수.add(getCanes(20));
		빙수.add(getYello(16));
		빙수.add(getRose(16));
		
		사이다.add(getCanes(24));
		사이다.add(getLily(16));
		사이다.add(getYello(32));
		사이다.add(getWater());
		
		FoodList.add(카레);
		FoodList.add(치킨);
		FoodList.add(피자);
		FoodList.add(케이크);
		FoodList.add(솔의눈);
		FoodList.add(바닷가재);
		FoodList.add(피시앤칩스);
		FoodList.add(탄산수);
		FoodList.add(불고기);
		FoodList.add(우동);
		FoodList.add(짬뽕);
		FoodList.add(닭강정);
		FoodList.add(김치찌개);
		FoodList.add(감자칩);
		FoodList.add(햄버거);
		FoodList.add(호박파이);
		FoodList.add(도넛);
		FoodList.add(주먹밥);
		FoodList.add(갈비);
		FoodList.add(돼지고기바비큐);
		FoodList.add(에그타르트);
		FoodList.add(잡채);
		FoodList.add(프렌치토스트);
		FoodList.add(쿠키칩);
		FoodList.add(버터팝콘);
		FoodList.add(라면);
		FoodList.add(닭갈비);
		FoodList.add(자장면);
		FoodList.add(통옥수수);
		FoodList.add(딸기주스);
		FoodList.add(문어빵);
		FoodList.add(크로아상);
		FoodList.add(김치);
		FoodList.add(볶음국수);
		FoodList.add(탕수육);
		FoodList.add(양송이스프);
		FoodList.add(당근주스);
		FoodList.add(통오리구이);
		FoodList.add(밥);
		FoodList.add(아이스크림);
		FoodList.add(훈제고기);
		FoodList.add(콜라);
		FoodList.add(우유);
		FoodList.add(스테이크);
		FoodList.add(녹차즙);
		FoodList.add(삼겹살);
		FoodList.add(초콜릿);
		FoodList.add(오렌지주스);
		FoodList.add(빙수);
		FoodList.add(사이다);
		StringList.add("카레");
		StringList.add("치킨");
		StringList.add("피자");
		StringList.add("케이크");
		StringList.add("솔의 눈");
		StringList.add("바닷가재");
		StringList.add("피시 앤 칩스");
		StringList.add("탄산수");
		StringList.add("불고기");
		StringList.add("우동");
		StringList.add("짬뽕");
		StringList.add("닭강정");
		StringList.add("김치찌개");
		StringList.add("감자 칩");
		StringList.add("햄버거");
		StringList.add("호박 파이");
		StringList.add("도넛");
		StringList.add("주먹밥");
		StringList.add("갈비");
		StringList.add("돼지고기 바비큐");
		StringList.add("에그 타르트");
		StringList.add("잡채");
		StringList.add("프렌치 토스트");
		StringList.add("쿠키칩");
		StringList.add("버터 팝콘");
		StringList.add("라면");
		StringList.add("닭갈비");
		StringList.add("자장면");
		StringList.add("통옥수수");
		StringList.add("딸기 주스");
		StringList.add("문어빵");
		StringList.add("크로아상");
		StringList.add("김치");
		StringList.add("볶음국수");
		StringList.add("탕수육");
		StringList.add("양송이 스프");
		StringList.add("당근 주스");
		StringList.add("통 오리구이");
		StringList.add("밥");
		StringList.add("아이스크림");
		StringList.add("훈제 고기");
		StringList.add("콜라");
		StringList.add("우유");
		StringList.add("스테이크");
		StringList.add("녹차 즙");
		StringList.add("삼겹살");
		StringList.add("초콜릿");
		StringList.add("오렌지 주스");
		StringList.add("빙수");
		StringList.add("사이다");
		IntegerList.add(4);
		IntegerList.add(6);
		IntegerList.add(6);
		IntegerList.add(4);
		IntegerList.add(4);
		IntegerList.add(4);
		IntegerList.add(4);
		IntegerList.add(5);
		IntegerList.add(4);
		IntegerList.add(4);
		IntegerList.add(4);
		IntegerList.add(5);
		IntegerList.add(3);
		IntegerList.add(4);
		IntegerList.add(3);
		IntegerList.add(3);
		IntegerList.add(3);
		IntegerList.add(2);
		IntegerList.add(4);
		IntegerList.add(3);
		IntegerList.add(3);
		IntegerList.add(3);
		IntegerList.add(2);
		IntegerList.add(1);
		IntegerList.add(3);
		IntegerList.add(3);
		IntegerList.add(3);
		IntegerList.add(3);
		IntegerList.add(8);
		IntegerList.add(9);
		IntegerList.add(8);
		IntegerList.add(8);
		IntegerList.add(9);
		IntegerList.add(8);
		IntegerList.add(9);
		IntegerList.add(8);
		IntegerList.add(8);
		IntegerList.add(6);
		IntegerList.add(6);
		IntegerList.add(6);
		IntegerList.add(6);
		IntegerList.add(6);
		IntegerList.add(6);
		IntegerList.add(6);
		IntegerList.add(6);
		IntegerList.add(6);
		IntegerList.add(6);
		IntegerList.add(6);
		IntegerList.add(6);
		IntegerList.add(6);
		NameList.add("§0§0§0§0§0§7§6§4§7§l§e카레");
		NameList.add("§0§0§0§0§0§7§6§5§7§l§e치킨");
		NameList.add("§0§0§0§0§0§7§6§6§7§l§e피자");
		NameList.add("§0§0§0§0§0§7§6§7§7§l§e케이크");
		NameList.add("§0§0§0§0§0§7§b§7§7§l§e솔의 눈");
		NameList.add("§0§0§0§0§0§7§6§9§7§l§e바닷가재");
		NameList.add("§0§0§0§0§0§7§6§a§7§l§e피시 앤 칩스");
		NameList.add("§0§0§0§0§0§7§6§b§7§l§e탄산수");
		NameList.add("§0§0§0§0§0§7§6§c§7§l§e불고기");
		NameList.add("§0§0§0§0§0§7§6§d§7§l§e우동");
		NameList.add("§0§0§0§0§0§7§6§e§7§l§e짬뽕");
		NameList.add("§0§0§0§0§0§7§6§f§7§l§e닭강정");
		NameList.add("§0§0§0§0§0§7§7§0§7§l§e김치찌개");
		NameList.add("§0§0§0§0§0§7§7§1§7§l§e감자 칩");
		NameList.add("§0§0§0§0§0§7§7§2§7§l§e햄버거");
		NameList.add("§0§0§0§0§0§7§7§3§7§l§e호박 파이");
		NameList.add("§0§0§0§0§0§7§7§4§7§l§e도넛");
		NameList.add("§0§0§0§0§0§7§7§5§7§l§e주먹밥");
		NameList.add("§0§0§0§0§0§7§7§6§7§l§e갈비");
		NameList.add("§0§0§0§0§0§7§7§7§7§l§e돼지고기 바비큐");
		NameList.add("§0§0§0§0§0§7§7§8§7§l§e에그 타르트");
		NameList.add("§0§0§0§0§0§7§7§9§7§l§e잡채");
		NameList.add("§0§0§0§0§0§7§7§a§7§l§e프렌치 토스트");
		NameList.add("§0§0§0§0§0§7§7§b§7§l§e쿠키칩");
		NameList.add("§0§0§0§0§0§7§7§c§7§l§e버터 팝콘");
		NameList.add("§0§0§0§0§0§7§9§7§7§l§e라면");
		NameList.add("§0§0§0§0§0§7§9§8§7§l§e닭갈비");
		NameList.add("§0§0§0§0§0§7§9§9§7§l§e자장면");
		NameList.add("§0§0§0§0§0§7§9§a§7§l§e통옥수수");
		NameList.add("§0§0§0§0§0§7§b§8§7§l§e딸기 주스");
		NameList.add("§0§0§0§0§0§7§9§c§7§l§e문어빵");
		NameList.add("§0§0§0§0§0§7§9§d§7§l§e크로아상");
		NameList.add("§0§0§0§0§0§7§9§e§7§l§e김치");
		NameList.add("§0§0§0§0§0§7§9§f§7§l§e볶음국수");
		NameList.add("§0§0§0§0§0§7§a§0§7§l§e탕수육");
		NameList.add("§0§0§0§0§0§7§a§1§7§l§e양송이 스프");
		NameList.add("§0§0§0§0§0§7§b§9§7§l§e당근 주스");
		NameList.add("§0§0§0§0§0§7§a§3§7§l§e통 오리구이");
		NameList.add("§0§0§0§0§0§7§a§4§7§l§e밥");
		NameList.add("§0§0§0§0§0§7§a§5§7§l§e아이스크림");
		NameList.add("§0§0§0§0§0§7§a§6§7§l§e훈제 고기");
		NameList.add("§0§0§0§0§0§7§b§a§7§l§e콜라");
		NameList.add("§0§0§0§0§0§7§a§8§7§l§e우유");
		NameList.add("§0§0§0§0§0§7§a§9§7§l§e스테이크");
		NameList.add("§0§0§0§0§0§7§b§b§7§l§e녹차 즙");
		NameList.add("§0§0§0§0§0§7§a§b§7§l§e삼겹살");
		NameList.add("§0§0§0§0§0§7§a§e§7§l§e초콜릿");
		NameList.add("§0§0§0§0§0§7§b§c§7§l§e오렌지 주스");
		NameList.add("§0§0§0§0§0§7§a§c§7§l§e빙수");
		NameList.add("§0§0§0§0§0§7§b§d§7§l§e사이다");
		EffectList.add("피로도 +10");
		EffectList.add("피로도 +15");
		EffectList.add("피로도 +15");
		EffectList.add("피로도 +10");
		EffectList.add("피로도 +10");
		EffectList.add("피로도 +10");
		EffectList.add("피로도 +10");
		EffectList.add("피로도 +12");
		EffectList.add("피로도 +10");
		EffectList.add("피로도 +10");
		EffectList.add("피로도 +10");
		EffectList.add("피로도 +12");
		EffectList.add("피로도 +8");
		EffectList.add("피로도 +10");
		EffectList.add("배고픔 +10");
		EffectList.add("배고픔 +10");
		EffectList.add("배고픔 +10");
		EffectList.add("배고픔 +8");
		EffectList.add("배고픔 +12");
		EffectList.add("배고픔 +10");
		EffectList.add("배고픔 +10");
		EffectList.add("배고픔 +10");
		EffectList.add("배고픔 +8");
		EffectList.add("배고픔 +6");
		EffectList.add("배고픔 +10");
		EffectList.add("배고픔 +9");
		EffectList.add("배고픔 +10");
		EffectList.add("배고픔 +10");
		EffectList.add("체력 +25");
		EffectList.add("체력 +35");
		EffectList.add("체력 +25");
		EffectList.add("체력 +25");
		EffectList.add("체력 +35");
		EffectList.add("체력 +25");
		EffectList.add("체력 +30");
		EffectList.add("30초간 성급함 +2");
		EffectList.add("60초간 야간투시 +2");
		EffectList.add("10초간 모든 스텟 효과 +10");
		EffectList.add("10초간 모든 스텟 효과 +10");
		EffectList.add("15초간 팔 근력 스텟 +30");
		EffectList.add("15초간 팔 근력 스텟 +30");
		EffectList.add("15초간 팔 근력 스텟 +30");
		EffectList.add("15초간 복근 스텟 +30");
		EffectList.add("15초간 복근 스텟 +30");
		EffectList.add("15초간 복근 스텟 +30");
		EffectList.add("15초간 다리 근력 스텟 +30");
		EffectList.add("15초간 다리 근력 스텟 +30");
		EffectList.add("15초간 다리 근력 스텟 +30");
		EffectList.add("15초간 스피드 스텟 +30");
		EffectList.add("15초간 스피드 스텟 +30");
	}
}
