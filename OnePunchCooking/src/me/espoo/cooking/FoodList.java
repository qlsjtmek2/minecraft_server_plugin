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
	public static List<Material> ī�� = new ArrayList<Material>();
	public static List<Material> ġŲ = new ArrayList<Material>();
	public static List<Material> ���� = new ArrayList<Material>();
	public static List<Material> ����ũ = new ArrayList<Material>();
	public static List<Material> ���Ǵ� = new ArrayList<Material>();
	public static List<Material> �ٴ尡�� = new ArrayList<Material>();
	public static List<Material> �ǽþ�Ĩ�� = new ArrayList<Material>();
	public static List<Material> ź��� = new ArrayList<Material>();
	public static List<Material> �Ұ�� = new ArrayList<Material>();
	public static List<Material> �쵿 = new ArrayList<Material>();
	public static List<Material> «�� = new ArrayList<Material>();
	public static List<Material> �߰��� = new ArrayList<Material>();
	public static List<Material> ��ġ� = new ArrayList<Material>();
	public static List<Material> ����Ĩ = new ArrayList<Material>();
	public static List<Material> �ܹ��� = new ArrayList<Material>();
	public static List<Material> ȣ������ = new ArrayList<Material>();
	public static List<Material> ���� = new ArrayList<Material>();
	public static List<Material> �ָԹ� = new ArrayList<Material>();
	public static List<Material> ���� = new ArrayList<Material>();
	public static List<Material> �������ٺ�ť = new ArrayList<Material>();
	public static List<Material> ����Ÿ��Ʈ = new ArrayList<Material>();
	public static List<Material> ��ä = new ArrayList<Material>();
	public static List<Material> ����ġ�佺Ʈ = new ArrayList<Material>();
	public static List<Material> ��ŰĨ = new ArrayList<Material>();
	public static List<Material> �������� = new ArrayList<Material>();
	public static List<Material> ��� = new ArrayList<Material>();
	public static List<Material> �߰��� = new ArrayList<Material>();
	public static List<Material> ����� = new ArrayList<Material>();
	public static List<Material> ������� = new ArrayList<Material>();
	public static List<Material> �����ֽ� = new ArrayList<Material>();
	public static List<Material> ��� = new ArrayList<Material>();
	public static List<Material> ũ�ξƻ� = new ArrayList<Material>();
	public static List<Material> ��ġ = new ArrayList<Material>();
	public static List<Material> �������� = new ArrayList<Material>();
	public static List<Material> ������ = new ArrayList<Material>();
	public static List<Material> ����̽��� = new ArrayList<Material>();
	public static List<Material> ����ֽ� = new ArrayList<Material>();
	public static List<Material> ��������� = new ArrayList<Material>();
	public static List<Material> �� = new ArrayList<Material>();
	public static List<Material> ���̽�ũ�� = new ArrayList<Material>();
	public static List<Material> ������� = new ArrayList<Material>();
	public static List<Material> �ݶ� = new ArrayList<Material>();
	public static List<Material> ���� = new ArrayList<Material>();
	public static List<Material> ������ũ = new ArrayList<Material>();
	public static List<Material> ������ = new ArrayList<Material>();
	public static List<Material> ���� = new ArrayList<Material>();
	public static List<Material> ���ݸ� = new ArrayList<Material>();
	public static List<Material> �������ֽ� = new ArrayList<Material>();
	public static List<Material> ���� = new ArrayList<Material>();
	public static List<Material> ���̴� = new ArrayList<Material>();
	
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
		ī��.add(getWheat(32));
		ī��.add(getCarrot(18));
		ī��.add(getPotato(18));
		ī��.add(getBowl(1));
		
		ġŲ.add(getWheat(32));
		ġŲ.add(getLily(12));
		ġŲ.add(getCanes(8));
		ġŲ.add(getWheat(32));
		
		����.add(getWheat(48));
		����.add(getPotato(32));
		����.add(getLily(16));
		����.add(getSeeds(5));
		
		����ũ.add(getWheat(48));
		����ũ.add(getLily(8));
		����ũ.add(getCanes(16));
		����ũ.add(getBowl(1));
		
		���Ǵ�.add(getCanes(16));
		���Ǵ�.add(getLily(20));
		���Ǵ�.add(getSeeds(32));
		���Ǵ�.add(getWater());
		
		�ٴ尡��.add(getLily(12));
		�ٴ尡��.add(getRose(16));
		�ٴ尡��.add(getYello(16));
		�ٴ尡��.add(getPotato(22));
		
		�ǽþ�Ĩ��.add(getLily(16));
		�ǽþ�Ĩ��.add(getSeeds(48));
		�ǽþ�Ĩ��.add(getRose(12));
		�ǽþ�Ĩ��.add(getYello(12));
		
		ź���.add(getSeeds(32));
		ź���.add(getCanes(16));
		ź���.add(getLily(16));
		ź���.add(getWater());
		
		�Ұ��.add(getYello(16));
		�Ұ��.add(getRose(16));
		�Ұ��.add(getSeeds(32));
		�Ұ��.add(getCanes(32));
		
		�쵿.add(getWheat(48));
		�쵿.add(getCarrot(32));
		�쵿.add(getSeeds(16));
		�쵿.add(getBowl(1));
		
		«��.add(getWheat(48));
		«��.add(getCanes(16));
		«��.add(getRose(16));
		«��.add(getBowl(1));
		
		�߰���.add(getYello(16));
		�߰���.add(getRose(16));
		�߰���.add(getWheat(16));
		�߰���.add(getSeeds(32));
		
		��ġ�.add(getRose(48));
		��ġ�.add(getYello(16));
		��ġ�.add(getPotato(16));
		��ġ�.add(getBowl(1));
		
		����Ĩ.add(getPotato(32));
		����Ĩ.add(getPotato(16));
		����Ĩ.add(getPotato(8));
		����Ĩ.add(getSeeds(16));
		
		�ܹ���.add(getWheat(20));
		�ܹ���.add(getPotato(16));
		�ܹ���.add(getSeeds(8));
		�ܹ���.add(getBowl(1));
		
		ȣ������.add(getWheat(18));
		ȣ������.add(getCarrot(10));
		ȣ������.add(getRose(20));
		ȣ������.add(getLily(8));
		
		����.add(getWheat(16));
		����.add(getSeeds(16));
		����.add(getYello(10));
		����.add(getRose(10));
		
		�ָԹ�.add(getWheat(32));
		�ָԹ�.add(getSeeds(32));
		�ָԹ�.add(getPotato(8));
		�ָԹ�.add(getCarrot(8));
		
		����.add(getRose(16));
		����.add(getYello(16));
		����.add(getSeeds(32));
		����.add(getCanes(8));
		
		�������ٺ�ť.add(getYello(16));
		�������ٺ�ť.add(getRose(16));
		�������ٺ�ť.add(getPotato(16));
		�������ٺ�ť.add(getWheat(32));
		
		����Ÿ��Ʈ.add(getPotato(32));
		����Ÿ��Ʈ.add(getWheat(16));
		����Ÿ��Ʈ.add(getYello(16));
		����Ÿ��Ʈ.add(getLily(8));
		
		��ä.add(getSeeds(48));
		��ä.add(getCanes(16));
		��ä.add(getCarrot(8));
		��ä.add(getPotato(8));
		
		����ġ�佺Ʈ.add(getWheat(32));
		����ġ�佺Ʈ.add(getPotato(32));
		����ġ�佺Ʈ.add(getLily(8));
		����ġ�佺Ʈ.add(getSeeds(8));
		
		��ŰĨ.add(getWheat(20));
		��ŰĨ.add(getCanes(16));
		��ŰĨ.add(getSeeds(12));
		��ŰĨ.add(getYello(4));
		
		��������.add(getSeeds(32));
		��������.add(getWheat(22));
		��������.add(getCanes(16));
		��������.add(getYello(8));
		
		���.add(getPotato(32));
		���.add(getWheat(32));
		���.add(getSeeds(16));
		���.add(getBowl(1));
		
		�߰���.add(getYello(16));
		�߰���.add(getRose(16));
		�߰���.add(getCarrot(48));
		�߰���.add(getPotato(24));
		
		�����.add(getWheat(32));
		�����.add(getWheat(32));
		�����.add(getCanes(16));
		�����.add(getBowl(1));
		
		�������.add(getWheat(16));
		�������.add(getCarrot(16));
		�������.add(getPotato(16));
		�������.add(getSeeds(16));
		
		�����ֽ�.add(getRose(48));
		�����ֽ�.add(getRose(16));
		�����ֽ�.add(getYello(8));
		�����ֽ�.add(getWater());
		
		���.add(getCanes(16));
		���.add(getPotato(20));
		���.add(getRose(8));
		���.add(getLily(4));
		
		ũ�ξƻ�.add(getWheat(48));
		ũ�ξƻ�.add(getYello(8));
		ũ�ξƻ�.add(getPotato(8));
		ũ�ξƻ�.add(getCanes(8));
		
		��ġ.add(getRose(42));
		��ġ.add(getCarrot(32));
		��ġ.add(getSeeds(16));
		��ġ.add(getCanes(8));
		
		��������.add(getWheat(48));
		��������.add(getPotato(16));
		��������.add(getCarrot(16));
		��������.add(getCanes(8));
		
		������.add(getWheat(48));
		������.add(getSeeds(16));
		������.add(getPotato(32));
		������.add(getLily(10));
		
		����̽���.add(getYello(16));
		����̽���.add(getRose(16));
		����̽���.add(getCarrot(32));
		����̽���.add(getBowl(1));
		
		����ֽ�.add(getCarrot(64));
		����ֽ�.add(getCanes(10));
		����ֽ�.add(getSeeds(16));
		����ֽ�.add(getWater());
		
		���������.add(getWheat(48));
		���������.add(getPotato(32));
		���������.add(getCarrot(32));
		���������.add(getLily(8));
		
		��.add(getWheat(64));
		��.add(getSeeds(32));
		��.add(getRose(2));
		��.add(getYello(2));
		
		���̽�ũ��.add(getCanes(20));
		���̽�ũ��.add(getSeeds(32));
		���̽�ũ��.add(getYello(8));
		���̽�ũ��.add(getWater());
		
		�������.add(getSeeds(32));
		�������.add(getLily(16));
		�������.add(getRose(16));
		�������.add(getYello(16));
		
		�ݶ�.add(getCanes(24));
		�ݶ�.add(getLily(16));
		�ݶ�.add(getRose(32));
		�ݶ�.add(getWater());
		
		����.add(getWheat(8));
		����.add(getCarrot(16));
		����.add(getSeeds(32));
		����.add(getWater());
		
		������ũ.add(getPotato(32));
		������ũ.add(getCarrot(32));
		������ũ.add(getRose(16));
		������ũ.add(getYello(16));
		
		������.add(getLily(24));
		������.add(getSeeds(24));
		������.add(getCanes(16));
		������.add(getWater());
		
		����.add(getCanes(16));
		����.add(getWheat(48));
		����.add(getRose(16));
		����.add(getYello(16));
		
		���ݸ�.add(getCanes(32));
		���ݸ�.add(getLily(16));
		���ݸ�.add(getYello(16));
		���ݸ�.add(getRose(16));
		
		�������ֽ�.add(getCarrot(48));
		�������ֽ�.add(getYello(16));
		�������ֽ�.add(getSeeds(16));
		�������ֽ�.add(getWater());
	
		����.add(getLily(16));
		����.add(getCanes(20));
		����.add(getYello(16));
		����.add(getRose(16));
		
		���̴�.add(getCanes(24));
		���̴�.add(getLily(16));
		���̴�.add(getYello(32));
		���̴�.add(getWater());
		
		FoodList.add(ī��);
		FoodList.add(ġŲ);
		FoodList.add(����);
		FoodList.add(����ũ);
		FoodList.add(���Ǵ�);
		FoodList.add(�ٴ尡��);
		FoodList.add(�ǽþ�Ĩ��);
		FoodList.add(ź���);
		FoodList.add(�Ұ��);
		FoodList.add(�쵿);
		FoodList.add(«��);
		FoodList.add(�߰���);
		FoodList.add(��ġ�);
		FoodList.add(����Ĩ);
		FoodList.add(�ܹ���);
		FoodList.add(ȣ������);
		FoodList.add(����);
		FoodList.add(�ָԹ�);
		FoodList.add(����);
		FoodList.add(�������ٺ�ť);
		FoodList.add(����Ÿ��Ʈ);
		FoodList.add(��ä);
		FoodList.add(����ġ�佺Ʈ);
		FoodList.add(��ŰĨ);
		FoodList.add(��������);
		FoodList.add(���);
		FoodList.add(�߰���);
		FoodList.add(�����);
		FoodList.add(�������);
		FoodList.add(�����ֽ�);
		FoodList.add(���);
		FoodList.add(ũ�ξƻ�);
		FoodList.add(��ġ);
		FoodList.add(��������);
		FoodList.add(������);
		FoodList.add(����̽���);
		FoodList.add(����ֽ�);
		FoodList.add(���������);
		FoodList.add(��);
		FoodList.add(���̽�ũ��);
		FoodList.add(�������);
		FoodList.add(�ݶ�);
		FoodList.add(����);
		FoodList.add(������ũ);
		FoodList.add(������);
		FoodList.add(����);
		FoodList.add(���ݸ�);
		FoodList.add(�������ֽ�);
		FoodList.add(����);
		FoodList.add(���̴�);
		StringList.add("ī��");
		StringList.add("ġŲ");
		StringList.add("����");
		StringList.add("����ũ");
		StringList.add("���� ��");
		StringList.add("�ٴ尡��");
		StringList.add("�ǽ� �� Ĩ��");
		StringList.add("ź���");
		StringList.add("�Ұ��");
		StringList.add("�쵿");
		StringList.add("«��");
		StringList.add("�߰���");
		StringList.add("��ġ�");
		StringList.add("���� Ĩ");
		StringList.add("�ܹ���");
		StringList.add("ȣ�� ����");
		StringList.add("����");
		StringList.add("�ָԹ�");
		StringList.add("����");
		StringList.add("������� �ٺ�ť");
		StringList.add("���� Ÿ��Ʈ");
		StringList.add("��ä");
		StringList.add("����ġ �佺Ʈ");
		StringList.add("��ŰĨ");
		StringList.add("���� ����");
		StringList.add("���");
		StringList.add("�߰���");
		StringList.add("�����");
		StringList.add("�������");
		StringList.add("���� �ֽ�");
		StringList.add("���");
		StringList.add("ũ�ξƻ�");
		StringList.add("��ġ");
		StringList.add("��������");
		StringList.add("������");
		StringList.add("����� ����");
		StringList.add("��� �ֽ�");
		StringList.add("�� ��������");
		StringList.add("��");
		StringList.add("���̽�ũ��");
		StringList.add("���� ���");
		StringList.add("�ݶ�");
		StringList.add("����");
		StringList.add("������ũ");
		StringList.add("���� ��");
		StringList.add("����");
		StringList.add("���ݸ�");
		StringList.add("������ �ֽ�");
		StringList.add("����");
		StringList.add("���̴�");
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
		NameList.add("��0��0��0��0��0��7��6��4��7��l��eī��");
		NameList.add("��0��0��0��0��0��7��6��5��7��l��eġŲ");
		NameList.add("��0��0��0��0��0��7��6��6��7��l��e����");
		NameList.add("��0��0��0��0��0��7��6��7��7��l��e����ũ");
		NameList.add("��0��0��0��0��0��7��b��7��7��l��e���� ��");
		NameList.add("��0��0��0��0��0��7��6��9��7��l��e�ٴ尡��");
		NameList.add("��0��0��0��0��0��7��6��a��7��l��e�ǽ� �� Ĩ��");
		NameList.add("��0��0��0��0��0��7��6��b��7��l��eź���");
		NameList.add("��0��0��0��0��0��7��6��c��7��l��e�Ұ��");
		NameList.add("��0��0��0��0��0��7��6��d��7��l��e�쵿");
		NameList.add("��0��0��0��0��0��7��6��e��7��l��e«��");
		NameList.add("��0��0��0��0��0��7��6��f��7��l��e�߰���");
		NameList.add("��0��0��0��0��0��7��7��0��7��l��e��ġ�");
		NameList.add("��0��0��0��0��0��7��7��1��7��l��e���� Ĩ");
		NameList.add("��0��0��0��0��0��7��7��2��7��l��e�ܹ���");
		NameList.add("��0��0��0��0��0��7��7��3��7��l��eȣ�� ����");
		NameList.add("��0��0��0��0��0��7��7��4��7��l��e����");
		NameList.add("��0��0��0��0��0��7��7��5��7��l��e�ָԹ�");
		NameList.add("��0��0��0��0��0��7��7��6��7��l��e����");
		NameList.add("��0��0��0��0��0��7��7��7��7��l��e������� �ٺ�ť");
		NameList.add("��0��0��0��0��0��7��7��8��7��l��e���� Ÿ��Ʈ");
		NameList.add("��0��0��0��0��0��7��7��9��7��l��e��ä");
		NameList.add("��0��0��0��0��0��7��7��a��7��l��e����ġ �佺Ʈ");
		NameList.add("��0��0��0��0��0��7��7��b��7��l��e��ŰĨ");
		NameList.add("��0��0��0��0��0��7��7��c��7��l��e���� ����");
		NameList.add("��0��0��0��0��0��7��9��7��7��l��e���");
		NameList.add("��0��0��0��0��0��7��9��8��7��l��e�߰���");
		NameList.add("��0��0��0��0��0��7��9��9��7��l��e�����");
		NameList.add("��0��0��0��0��0��7��9��a��7��l��e�������");
		NameList.add("��0��0��0��0��0��7��b��8��7��l��e���� �ֽ�");
		NameList.add("��0��0��0��0��0��7��9��c��7��l��e���");
		NameList.add("��0��0��0��0��0��7��9��d��7��l��eũ�ξƻ�");
		NameList.add("��0��0��0��0��0��7��9��e��7��l��e��ġ");
		NameList.add("��0��0��0��0��0��7��9��f��7��l��e��������");
		NameList.add("��0��0��0��0��0��7��a��0��7��l��e������");
		NameList.add("��0��0��0��0��0��7��a��1��7��l��e����� ����");
		NameList.add("��0��0��0��0��0��7��b��9��7��l��e��� �ֽ�");
		NameList.add("��0��0��0��0��0��7��a��3��7��l��e�� ��������");
		NameList.add("��0��0��0��0��0��7��a��4��7��l��e��");
		NameList.add("��0��0��0��0��0��7��a��5��7��l��e���̽�ũ��");
		NameList.add("��0��0��0��0��0��7��a��6��7��l��e���� ���");
		NameList.add("��0��0��0��0��0��7��b��a��7��l��e�ݶ�");
		NameList.add("��0��0��0��0��0��7��a��8��7��l��e����");
		NameList.add("��0��0��0��0��0��7��a��9��7��l��e������ũ");
		NameList.add("��0��0��0��0��0��7��b��b��7��l��e���� ��");
		NameList.add("��0��0��0��0��0��7��a��b��7��l��e����");
		NameList.add("��0��0��0��0��0��7��a��e��7��l��e���ݸ�");
		NameList.add("��0��0��0��0��0��7��b��c��7��l��e������ �ֽ�");
		NameList.add("��0��0��0��0��0��7��a��c��7��l��e����");
		NameList.add("��0��0��0��0��0��7��b��d��7��l��e���̴�");
		EffectList.add("�Ƿε� +10");
		EffectList.add("�Ƿε� +15");
		EffectList.add("�Ƿε� +15");
		EffectList.add("�Ƿε� +10");
		EffectList.add("�Ƿε� +10");
		EffectList.add("�Ƿε� +10");
		EffectList.add("�Ƿε� +10");
		EffectList.add("�Ƿε� +12");
		EffectList.add("�Ƿε� +10");
		EffectList.add("�Ƿε� +10");
		EffectList.add("�Ƿε� +10");
		EffectList.add("�Ƿε� +12");
		EffectList.add("�Ƿε� +8");
		EffectList.add("�Ƿε� +10");
		EffectList.add("����� +10");
		EffectList.add("����� +10");
		EffectList.add("����� +10");
		EffectList.add("����� +8");
		EffectList.add("����� +12");
		EffectList.add("����� +10");
		EffectList.add("����� +10");
		EffectList.add("����� +10");
		EffectList.add("����� +8");
		EffectList.add("����� +6");
		EffectList.add("����� +10");
		EffectList.add("����� +9");
		EffectList.add("����� +10");
		EffectList.add("����� +10");
		EffectList.add("ü�� +25");
		EffectList.add("ü�� +35");
		EffectList.add("ü�� +25");
		EffectList.add("ü�� +25");
		EffectList.add("ü�� +35");
		EffectList.add("ü�� +25");
		EffectList.add("ü�� +30");
		EffectList.add("30�ʰ� ������ +2");
		EffectList.add("60�ʰ� �߰����� +2");
		EffectList.add("10�ʰ� ��� ���� ȿ�� +10");
		EffectList.add("10�ʰ� ��� ���� ȿ�� +10");
		EffectList.add("15�ʰ� �� �ٷ� ���� +30");
		EffectList.add("15�ʰ� �� �ٷ� ���� +30");
		EffectList.add("15�ʰ� �� �ٷ� ���� +30");
		EffectList.add("15�ʰ� ���� ���� +30");
		EffectList.add("15�ʰ� ���� ���� +30");
		EffectList.add("15�ʰ� ���� ���� +30");
		EffectList.add("15�ʰ� �ٸ� �ٷ� ���� +30");
		EffectList.add("15�ʰ� �ٸ� �ٷ� ���� +30");
		EffectList.add("15�ʰ� �ٸ� �ٷ� ���� +30");
		EffectList.add("15�ʰ� ���ǵ� ���� +30");
		EffectList.add("15�ʰ� ���ǵ� ���� +30");
	}
}
