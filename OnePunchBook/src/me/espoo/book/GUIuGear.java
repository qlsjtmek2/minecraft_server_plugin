package me.espoo.book;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GUIuGear {
	static PlayerYml P;
	static GUIMessage G;
	static GUImain M;
	
	@SuppressWarnings("static-access")
	public static void openGUI1(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 54, "����ũ ��� ���� 1/3");
		int num = 0;

		for (int i = 0; i < 10; i ++) {
			if (i == 0) num = 0;
			else if (i == 1) num = 9;
			else if (i == 2) num = 18;
			else if (i == 3) num = 27;
			else if (i == 4) num = 36;
			else if (i == 5) num = 8;
			else if (i == 6) num = 17;
			else if (i == 7) num = 26;
			else if (i == 8) num = 35;
			else if (i == 9) num = 44;
			
			M.Stack2("��f����", 119, 0, 1, Arrays.asList(), num, GUI);
		}
		
		for (int i = 0; i < 14; i ++) {
			if (i == 0) num = 1;
			else if (i == 1) num = 2;
			else if (i == 2) num = 3;
			else if (i == 3) num = 4;
			else if (i == 4) num = 5;
			else if (i == 5) num = 6;
			else if (i == 6) num = 7;
			else if (i == 7) num = 46;
			else if (i == 8) num = 47;
			else if (i == 9) num = 48;
			else if (i == 10) num = 49;
			else if (i == 11) num = 50;
			else if (i == 12) num = 51;
			else if (i == 13) num = 52;
			
			M.Stack2("��f����", 102, 0, 1, Arrays.asList(), num, GUI);
		}
		
		M.Stack("��f[ ��7�ڷΰ��� ��f]", 8, 0, 1, G.Water, 45, GUI);
		M.Stack("��f[ ��e�������� ��f]", 10, 0, 1, G.Lava, 53, GUI);
		
		if (P.getInfoBoolean(p, "����ũ ���.����������")) {
			M.Stack2("��0��0��0��0��0��1��b��b��7��l��6 ������� ����", 1, 0, 1, Arrays.asList(), 10, GUI);
		} else {
			M.Stack("��6 ������� ���� ��f��l����", 389, 0, 1, G.Glass, 10, GUI);
		}
 		
		if (P.getInfoBoolean(p, "����ũ ���.�������Ʃ��")) {
			M.Stack2("��0��0��0��0��0��1��b��c��7��l��6 ������� ����", 1, 0, 1, Arrays.asList(), 19, GUI);
		} else {
			M.Stack("��6 ������� ���� ��f��l����", 389, 0, 1, G.Glass, 19, GUI);
		}
 		
		if (P.getInfoBoolean(p, "����ũ ���.����������")) {
			M.Stack2("��0��0��0��0��0��1��b��d��7��l��6 ������� ���뽺", 1, 0, 1, Arrays.asList(), 28, GUI);
		} else {
			M.Stack("��6 ������� ���뽺 ��f��l����", 389, 0, 1, G.Glass, 28, GUI);
		}
 		
		if (P.getInfoBoolean(p, "����ũ ���.�������Ź�")) {
			M.Stack2("��0��0��0��0��0��1��b��e��7��l��6 ������� ����", 1, 0, 1, Arrays.asList(), 37, GUI);
		} else {
			M.Stack("��6 ������� ���� ��f��l����", 389, 0, 1, G.Glass, 37, GUI);
		}
 		
		if (P.getInfoBoolean(p, "����ũ ���.�ݼӹ�Ʈ����")) {
			M.Stack2("��0��0��0��0��0��2��6��b��7��l��6 �ݼ� ��Ʈ ����", 1, 0, 1, Arrays.asList(), 11, GUI);
		} else {
			M.Stack("��6 �ݼ� ��Ʈ ���� ��f��l����", 389, 0, 1, G.Glass, 11, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�ݼӹ�ƮƩ��")) {
			M.Stack2("��0��0��0��0��0��2��6��c��7��l��6 �ݼ� ��Ʈ Ʃ��", 1, 0, 1, Arrays.asList(), 20, GUI);
		} else {
			M.Stack("��6 �ݼ� ��Ʈ Ʃ�� ��f��l����", 389, 0, 1, G.Glass, 20, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�ݼӹ�Ʈ����")) {
			M.Stack2("��0��0��0��0��0��2��6��d��7��l��6 �ݼ� ��Ʈ ����", 1, 0, 1, Arrays.asList(), 29, GUI);
		} else {
			M.Stack("��6 �ݼ� ��Ʈ ���� ��f��l����", 389, 0, 1, G.Glass, 29, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�ݼӹ�Ʈ�Ź�")) {
			M.Stack2("��0��0��0��0��0��2��6��e��7��l��6 �ݼ� ��Ʈ �Ź�", 1, 0, 1, Arrays.asList(), 38, GUI);
		} else {
			M.Stack("��6 �ݼ� ��Ʈ �Ź� ��f��l����", 389, 0, 1, G.Glass, 38, GUI);
		}
		
		if (P.getInfoBoolean(p, "����ũ ���.���Ÿ���")) {
			M.Stack2("��0��0��0��0��0��1��c��b��7��l��6 ���� ����", 1, 0, 1, Arrays.asList(), 12, GUI);
		} else {
			M.Stack("��6 ���� ���� ��f��l����", 389, 0, 1, G.Glass, 12, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.����Ʃ��")) {
			M.Stack2("��0��0��0��0��0��1��c��c��7��l��6 ���� Ʃ��", 1, 0, 1, Arrays.asList(), 21, GUI);
		} else {
			M.Stack("��6 ���� Ʃ�� ��f��l����", 389, 0, 1, G.Glass, 21, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.���Ź���")) {
			M.Stack2("��0��0��0��0��0��1��c��d��7��l��6 ���� ����", 1, 0, 1, Arrays.asList(), 30, GUI);
		} else {
			M.Stack("��6 ���� ���� ��f��l����", 389, 0, 1, G.Glass, 30, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.���ŽŹ�")) {
			M.Stack2("��0��0��0��0��0��1��c��e��7��l��6 ���� �Ź�", 1, 0, 1, Arrays.asList(), 39, GUI);
		} else {
			M.Stack("��6 ���� �Ź� ��f��l����", 389, 0, 1, G.Glass, 39, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.��������")) {
			M.Stack2("��0��0��0��0��0��3��5��f��7��l��6 ���� ����", 1, 0, 1, Arrays.asList(), 13, GUI);
		} else {
			M.Stack("��6 ���� ���� ��f��l����", 389, 0, 1, G.Glass, 13, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.����Ʃ��")) {
			M.Stack2("��0��0��0��0��0��3��6��0��7��l��6 ���� Ʃ��", 1, 0, 1, Arrays.asList(), 22, GUI);
		} else {
			M.Stack("��6 ���� Ʃ�� ��f��l����", 389, 0, 1, G.Glass, 22, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.��������")) {
			M.Stack2("��0��0��0��0��0��3��6��1��7��l��6 ���� ����", 1, 0, 1, Arrays.asList(), 31, GUI);
		} else {
			M.Stack("��6 ���� ���� ��f��l����", 389, 0, 1, G.Glass, 31, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�����Ź�")) {
			M.Stack2("��0��0��0��0��0��3��6��2��7��l��6 ���� �Ź�", 1, 0, 1, Arrays.asList(), 40, GUI);
		} else {
			M.Stack("��6 ���� �Ź� ��f��l����", 389, 0, 1, G.Glass, 40, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.��Ż����Ʈ����")) {
			M.Stack2("��0��0��0��0��0��1��9��b��7��l��6 ��Ż����Ʈ ����", 1, 0, 1, Arrays.asList(), 14, GUI);
		} else {
			M.Stack("��6 ��Ż����Ʈ ���� ��f��l����", 389, 0, 1, G.Glass, 14, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.��Ż����ƮƩ��")) {
			M.Stack2("��0��0��0��0��0��1��9��c��7��l��6 ��Ż����Ʈ ����", 1, 0, 1, Arrays.asList(), 23, GUI);
		} else {
			M.Stack("��6 ��Ż����Ʈ ���� ��f��l����", 389, 0, 1, G.Glass, 23, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.��Ż����Ʈ����")) {
			M.Stack2("��0��0��0��0��0��1��9��d��7��l��6 ��Ż����Ʈ ���뽺", 1, 0, 1, Arrays.asList(), 32, GUI);
		} else {
			M.Stack("��6 ��Ż����Ʈ ���뽺 ��f��l����", 389, 0, 1, G.Glass, 32, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.��Ż����Ʈ�Ź�")) {
			M.Stack2("��0��0��0��0��0��1��9��e��7��l��6 ��Ż����Ʈ ����", 1, 0, 1, Arrays.asList(), 41, GUI);
		} else {
			M.Stack("��6 ��Ż����Ʈ ���� ��f��l����", 389, 0, 1, G.Glass, 41, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.���߸Ǹ���")) {
			M.Stack2("��0��0��0��0��0��1��e��b��7��l��6 ���߸� ����", 1, 0, 1, Arrays.asList(), 15, GUI);
		} else {
			M.Stack("��6 ���߸� ���� ��f��l����", 389, 0, 1, G.Glass, 15, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.���߸�Ʃ��")) {
			M.Stack2("��0��0��0��0��0��1��e��c��7��l��6 ���߸� Ʃ��", 1, 0, 1, Arrays.asList(), 24, GUI);
		} else {
			M.Stack("��6 ���߸� Ʃ�� ��f��l����", 389, 0, 1, G.Glass, 24, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.���߸ǹ���")) {
			M.Stack2("��0��0��0��0��0��1��e��d��7��l��6 ���߸� ����", 1, 0, 1, Arrays.asList(), 33, GUI);
		} else {
			M.Stack("��6 ���߸� ���� ��f��l����", 389, 0, 1, G.Glass, 33, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.���߸ǽŹ�")) {
			M.Stack2("��0��0��0��0��0��1��e��e��7��l��6 ���߸� �Ź�", 1, 0, 1, Arrays.asList(), 42, GUI);
		} else {
			M.Stack("��6 ���߸� �Ź� ��f��l����", 389, 0, 1, G.Glass, 42, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�������÷��ø���")) {
			M.Stack2("��0��0��0��0��0��4��d��d��7��l��6 ������ �÷��� ����", 1, 0, 1, Arrays.asList(), 16, GUI);
		} else {
			M.Stack("��6 ������ �÷��� ���� ��f��l����", 389, 0, 1, G.Glass, 16, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�������÷���Ʃ��")) {
			M.Stack2("��0��0��0��0��0��4��d��e��7��l��6 ������ �÷��� Ʃ��", 1, 0, 1, Arrays.asList(), 25, GUI);
		} else {
			M.Stack("��6 ������ �÷��� Ʃ�� ��f��l����", 389, 0, 1, G.Glass, 25, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�������÷��ù���")) {
			M.Stack2("��0��0��0��0��0��4��d��f��7��l��6 ������ �÷��� ����", 1, 0, 1, Arrays.asList(), 34, GUI);
		} else {
			M.Stack("��6 ������ �÷��� ���� ��f��l����", 389, 0, 1, G.Glass, 34, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�������÷��ýŹ�")) {
			M.Stack2("��0��0��0��0��0��4��e��0��7��l��6 ������ �÷��� �Ź�", 1, 0, 1, Arrays.asList(), 43, GUI);
		} else {
			M.Stack("��6 ������ �÷��� �Ź� ��f��l����", 389, 0, 1, G.Glass, 43, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	@SuppressWarnings("static-access")
	public static void openGUI2(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 54, "����ũ ��� ���� 2/3");
		int num = 0;

		for (int i = 0; i < 10; i ++) {
			if (i == 0) num = 0;
			else if (i == 1) num = 9;
			else if (i == 2) num = 18;
			else if (i == 3) num = 27;
			else if (i == 4) num = 36;
			else if (i == 5) num = 8;
			else if (i == 6) num = 17;
			else if (i == 7) num = 26;
			else if (i == 8) num = 35;
			else if (i == 9) num = 44;
			
			M.Stack2("��f����", 119, 0, 1, Arrays.asList(), num, GUI);
		}
		
		for (int i = 0; i < 14; i ++) {
			if (i == 0) num = 1;
			else if (i == 1) num = 2;
			else if (i == 2) num = 3;
			else if (i == 3) num = 4;
			else if (i == 4) num = 5;
			else if (i == 5) num = 6;
			else if (i == 6) num = 7;
			else if (i == 7) num = 46;
			else if (i == 8) num = 47;
			else if (i == 9) num = 48;
			else if (i == 10) num = 49;
			else if (i == 11) num = 50;
			else if (i == 12) num = 51;
			else if (i == 13) num = 52;
			
			M.Stack2("��f����", 102, 0, 1, Arrays.asList(), num, GUI);
		}
		
		M.Stack("��f[ ��7�ڷΰ��� ��f]", 8, 0, 1, G.Water, 45, GUI);
		M.Stack("��f[ ��e�������� ��f]", 10, 0, 1, G.Lava, 53, GUI);
		
		if (P.getInfoBoolean(p, "����ũ ���.�ǹ��ظ���")) {
			M.Stack2("��0��0��0��0��0��3��4��f��7��l��6 �ǹ��� ����", 1, 0, 1, Arrays.asList(), 10, GUI);
		} else {
			M.Stack("��6 �ǹ��� ���� ��f��l����", 389, 0, 1, G.Glass, 10, GUI);
		}
 		
		if (P.getInfoBoolean(p, "����ũ ���.�ǹ���Ʃ��")) {
			M.Stack2("��0��0��0��0��0��3��5��0��7��l��6 �ǹ��� Ʃ��", 1, 0, 1, Arrays.asList(), 19, GUI);
		} else {
			M.Stack("��6 �ǹ��� Ʃ�� ��f��l����", 389, 0, 1, G.Glass, 19, GUI);
		}
 		
		if (P.getInfoBoolean(p, "����ũ ���.�ǹ��ع���")) {
			M.Stack2("��0��0��0��0��0��3��5��1��7��l��6 �ǹ��� ���뽺", 1, 0, 1, Arrays.asList(), 28, GUI);
		} else {
			M.Stack("��6 �ǹ��� ���뽺 ��f��l����", 389, 0, 1, G.Glass, 28, GUI);
		}
 		
		if (P.getInfoBoolean(p, "����ũ ���.�ǹ��ؽŹ�")) {
			M.Stack2("��0��0��0��0��0��3��5��2��7��l��6 �ǹ��� �Ź�", 1, 0, 1, Arrays.asList(), 37, GUI);
		} else {
			M.Stack("��6 �ǹ��� �Ź� ��f��l����", 389, 0, 1, G.Glass, 37, GUI);
		}
 		
		if (P.getInfoBoolean(p, "����ũ ���.�Ƹ��̸���ũ����")) {
			M.Stack2("��0��0��0��0��0��2��9��b��7��l��6 �Ƹ��̸���ũ ����", 1, 0, 1, Arrays.asList(), 11, GUI);
		} else {
			M.Stack("��6 �Ƹ��̸���ũ ���� ��f��l����", 389, 0, 1, G.Glass, 11, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�Ƹ��̸���ũƩ��")) {
			M.Stack2("��0��0��0��0��0��2��9��c��7��l��6 �Ƹ��̸���ũ Ʃ��", 1, 0, 1, Arrays.asList(), 20, GUI);
		} else {
			M.Stack("��6 �Ƹ��̸���ũ Ʃ�� ��f��l����", 389, 0, 1, G.Glass, 20, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�Ƹ��̸���ũ����")) {
			M.Stack2("��0��0��0��0��0��2��9��d��7��l��6 �Ƹ��̸���ũ ����", 1, 0, 1, Arrays.asList(), 29, GUI);
		} else {
			M.Stack("��6 �Ƹ��̸���ũ ���� ��f��l����", 389, 0, 1, G.Glass, 29, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�Ƹ��̸���ũ�Ź�")) {
			M.Stack2("��0��0��0��0��0��2��9��e��7��l��6 �Ƹ��̸���ũ �Ź�", 1, 0, 1, Arrays.asList(), 38, GUI);
		} else {
			M.Stack("��6 �Ƹ��̸���ũ �Ź� ��f��l����", 389, 0, 1, G.Glass, 38, GUI);
		}
		
		if (P.getInfoBoolean(p, "����ũ ���.����͸���")) {
			M.Stack2("��0��0��0��0��0��1��4��b��7��l��6 ����� �繫���� ����", 1, 0, 1, Arrays.asList(), 12, GUI);
		} else {
			M.Stack("��6 ����� �繫���� ���� ��f��l����", 389, 0, 1, G.Glass, 12, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�����Ʃ��")) {
			M.Stack2("��0��0��0��0��0��1��4��c��7��l��6 ����� �繫���� Ʃ��", 1, 0, 1, Arrays.asList(), 21, GUI);
		} else {
			M.Stack("��6 ����� �繫���� Ʃ�� ��f��l����", 389, 0, 1, G.Glass, 21, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.����͹���")) {
			M.Stack2("��0��0��0��0��0��1��4��d��7��l��6 ����� �繫���� ����", 1, 0, 1, Arrays.asList(), 30, GUI);
		} else {
			M.Stack("��6 ����� �繫���� ���� ��f��l����", 389, 0, 1, G.Glass, 30, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.����ͽŹ�")) {
			M.Stack2("��0��0��0��0��0��1��4��e��7��l��6 ����� �繫���� �Ź�", 1, 0, 1, Arrays.asList(), 39, GUI);
		} else {
			M.Stack("��6 ����� �繫���� �Ź� ��f��l����", 389, 0, 1, G.Glass, 39, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�����ǼҴи���")) {
			M.Stack2("��0��0��0��0��0��2��c��b��7��l��6 ������ �Ҵ� ����", 1, 0, 1, Arrays.asList(), 13, GUI);
		} else {
			M.Stack("��6 ������ �Ҵ� ���� ��f��l����", 389, 0, 1, G.Glass, 13, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�����ǼҴ�Ʃ��")) {
			M.Stack2("��0��0��0��0��0��2��c��c��7��l��6 ������ �Ҵ� Ʃ��", 1, 0, 1, Arrays.asList(), 22, GUI);
		} else {
			M.Stack("��6 ������ �Ҵ� Ʃ�� ��f��l����", 389, 0, 1, G.Glass, 22, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�����ǼҴй���")) {
			M.Stack2("��0��0��0��0��0��2��c��d��7��l��6 ������ �Ҵ� ����", 1, 0, 1, Arrays.asList(), 31, GUI);
		} else {
			M.Stack("��6 ������ �Ҵ� ���� ��f��l����", 389, 0, 1, G.Glass, 31, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�����ǼҴнŹ�")) {
			M.Stack2("��0��0��0��0��0��2��c��e��7��l��6 ������ �Ҵ� �Ź�", 1, 0, 1, Arrays.asList(), 40, GUI);
		} else {
			M.Stack("��6 ������ �Ҵ� �Ź� ��f��l����", 389, 0, 1, G.Glass, 40, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.���뽺����")) {
			M.Stack2("��0��0��0��0��0��2��4��b��7��l��6 ���뽺 ����", 1, 0, 1, Arrays.asList(), 14, GUI);
		} else {
			M.Stack("��6 ���뽺 ���� ��f��l����", 389, 0, 1, G.Glass, 14, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.���뽺Ʃ��")) {
			M.Stack2("��0��0��0��0��0��2��4��c��7��l��6 ���뽺 ����", 1, 0, 1, Arrays.asList(), 23, GUI);
		} else {
			M.Stack("��6 ���뽺 ���� ��f��l����", 389, 0, 1, G.Glass, 23, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.���뽺����")) {
			M.Stack2("��0��0��0��0��0��2��4��d��7��l��6 ���뽺 ����", 1, 0, 1, Arrays.asList(), 32, GUI);
		} else {
			M.Stack("��6 ���뽺 ���� ��f��l����", 389, 0, 1, G.Glass, 32, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.���뽺�Ź�")) {
			M.Stack2("��0��0��0��0��0��2��4��e��7��l��6 ���뽺 �Ź�", 1, 0, 1, Arrays.asList(), 41, GUI);
		} else {
			M.Stack("��6 ���뽺 �Ź� ��f��l����", 389, 0, 1, G.Glass, 41, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.����Ǹ���")) {
			M.Stack2("��0��0��0��0��0��1��a��b��7��l��6 ����� ����", 1, 0, 1, Arrays.asList(), 15, GUI);
		} else {
			M.Stack("��6 ����� ���� ��f��l����", 389, 0, 1, G.Glass, 15, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�����Ʃ��")) {
			M.Stack2("��0��0��0��0��0��1��a��c��7��l��6 ����� Ʃ��", 1, 0, 1, Arrays.asList(), 24, GUI);
		} else {
			M.Stack("��6 ����� Ʃ�� ��f��l����", 389, 0, 1, G.Glass, 24, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.����ǹ���")) {
			M.Stack2("��0��0��0��0��0��1��a��d��7��l��6 ����� ����", 1, 0, 1, Arrays.asList(), 33, GUI);
		} else {
			M.Stack("��6 ����� ���� ��f��l����", 389, 0, 1, G.Glass, 33, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.����ǽŹ�")) {
			M.Stack2("��0��0��0��0��0��1��a��e��7��l��6 ����� �Ź�", 1, 0, 1, Arrays.asList(), 42, GUI);
		} else {
			M.Stack("��6 ����� �Ź� ��f��l����", 389, 0, 1, G.Glass, 42, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.���ձݰ���������")) {
			M.Stack2("��0��0��0��0��0��2��b��f��7��l��6 ���ձ� ������ ����", 1, 0, 1, Arrays.asList(), 16, GUI);
		} else {
			M.Stack("��6 ���ձ� ������ ���� ��f��l����", 389, 0, 1, G.Glass, 16, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.���ձݰ�����Ʃ��")) {
			M.Stack2("��0��0��0��0��0��2��c��0��7��l��6 ���ձ� ������ Ʃ��", 1, 0, 1, Arrays.asList(), 25, GUI);
		} else {
			M.Stack("��6 ���ձ� ������ Ʃ�� ��f��l����", 389, 0, 1, G.Glass, 25, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.���ձݰ���������")) {
			M.Stack2("��0��0��0��0��0��2��0��d��7��l��6 ���ձ� ������ ����", 1, 0, 1, Arrays.asList(), 34, GUI);
		} else {
			M.Stack("��6 ���ձ� ������ ���� ��f��l����", 389, 0, 1, G.Glass, 34, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.���ձݰ������Ź�")) {
			M.Stack2("��0��0��0��0��0��2��0��e��7��l��6 ���ձ� ������ �Ź�", 1, 0, 1, Arrays.asList(), 43, GUI);
		} else {
			M.Stack("��6 ���ձ� ������ �Ź� ��f��l����", 389, 0, 1, G.Glass, 43, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	@SuppressWarnings("static-access")
	public static void openGUI3(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 54, "����ũ ��� ���� 3/3");
		int num = 0;

		for (int i = 0; i < 11; i ++) {
			if (i == 0) num = 0;
			else if (i == 1) num = 9;
			else if (i == 2) num = 18;
			else if (i == 3) num = 27;
			else if (i == 4) num = 36;
			else if (i == 5) num = 8;
			else if (i == 6) num = 17;
			else if (i == 7) num = 26;
			else if (i == 8) num = 35;
			else if (i == 9) num = 44;
			else if (i == 10) num = 53;
			
			M.Stack2("��f����", 119, 0, 1, Arrays.asList(), num, GUI);
		}
		
		for (int i = 0; i < 21; i ++) {
			if (i == 0) num = 1;
			else if (i == 1) num = 2;
			else if (i == 2) num = 3;
			else if (i == 3) num = 4;
			else if (i == 4) num = 5;
			else if (i == 5) num = 6;
			else if (i == 6) num = 7;
			else if (i == 7) num = 46;
			else if (i == 8) num = 47;
			else if (i == 9) num = 48;
			else if (i == 10) num = 49;
			else if (i == 11) num = 50;
			else if (i == 12) num = 51;
			else if (i == 13) num = 52;
			else if (i == 14) num = 15;
			else if (i == 15) num = 16;
			else if (i == 16) num = 24;
			else if (i == 17) num = 25;
			else if (i == 18) num = 33;
			else if (i == 19) num = 34;
			else if (i == 20) num = 42;
			
			M.Stack2("��f����", 102, 0, 1, Arrays.asList(), num, GUI);
		}
		
		M.Stack("��f[ ��7�ڷΰ��� ��f]", 8, 0, 1, G.Water, 45, GUI);
		
		if (P.getInfoBoolean(p, "����ũ ���.���� ȹ��")) {
			M.Stack2("��f������ �����̽��ϴ�.", 323, 0, 1, Arrays.asList(), 43, GUI);
		} else {
			M.Stack("��6 ����Ÿ�� ��� ť��", 124, 0, 1, G.uGearBook, 43, GUI);
		}
		
		if (P.getInfoBoolean(p, "����ũ ���.ŷ����")) {
			M.Stack2("��0��0��0��0��0��1��8��b��7��l��6 ŷ ����", 1, 0, 1, Arrays.asList(), 10, GUI);
		} else {
			M.Stack("��6 ŷ ���� ��f��l����", 389, 0, 1, G.Glass, 10, GUI);
		}
 		
		if (P.getInfoBoolean(p, "����ũ ���.ŷƩ��")) {
			M.Stack2("��0��0��0��0��0��1��8��c��7��l��6 ŷ Ʃ��", 1, 0, 1, Arrays.asList(), 19, GUI);
		} else {
			M.Stack("��6 ŷ Ʃ�� ��f��l����", 389, 0, 1, G.Glass, 19, GUI);
		}
 		
		if (P.getInfoBoolean(p, "����ũ ���.ŷ����")) {
			M.Stack2("��0��0��0��0��0��1��8��d��7��l��6 ŷ ����", 1, 0, 1, Arrays.asList(), 28, GUI);
		} else {
			M.Stack("��6 ŷ ���� ��f��l����", 389, 0, 1, G.Glass, 28, GUI);
		}
 		
		if (P.getInfoBoolean(p, "����ũ ���.ŷ�Ź�")) {
			M.Stack2("��0��0��0��0��0��1��8��e��7��l��6 ŷ �Ź�", 1, 0, 1, Arrays.asList(), 37, GUI);
		} else {
			M.Stack("��6 ŷ �Ź� ��f��l����", 389, 0, 1, G.Glass, 37, GUI);
		}
 		
		if (P.getInfoBoolean(p, "����ũ ���.Ÿ����Ű����")) {
			M.Stack2("��0��0��0��0��0��3��3��f��7��l��6 Ÿ����Ű ����", 1, 0, 1, Arrays.asList(), 11, GUI);
		} else {
			M.Stack("��6 Ÿ����Ű ���� ��f��l����", 389, 0, 1, G.Glass, 11, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.Ÿ����ŰƩ��")) {
			M.Stack2("��0��0��0��0��0��3��4��0��7��l��6 Ÿ����Ű Ʃ��", 1, 0, 1, Arrays.asList(), 20, GUI);
		} else {
			M.Stack("��6 Ÿ����Ű Ʃ�� ��f��l����", 389, 0, 1, G.Glass, 20, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.Ÿ����Ű����")) {
			M.Stack2("��0��0��0��0��0��3��4��1��7��l��6 Ÿ����Ű ����", 1, 0, 1, Arrays.asList(), 29, GUI);
		} else {
			M.Stack("��6 Ÿ����Ű ���� ��f��l����", 389, 0, 1, G.Glass, 29, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.Ÿ����Ű�Ź�")) {
			M.Stack2("��0��0��0��0��0��3��4��2��7��l��6 Ÿ����Ű �Ź�", 1, 0, 1, Arrays.asList(), 38, GUI);
		} else {
			M.Stack("��6 Ÿ����Ű �Ź� ��f��l����", 389, 0, 1, G.Glass, 38, GUI);
		}
		
		if (P.getInfoBoolean(p, "����ũ ���.��ũ�鸶���͸���")) {
			M.Stack2("��0��0��0��0��0��2��7��b��7��l��6 ��ũ�� ������ ����", 1, 0, 1, Arrays.asList(), 12, GUI);
		} else {
			M.Stack("��6 ��ũ�� ������ ���� ��f��l����", 389, 0, 1, G.Glass, 12, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.��ũ�鸶����Ʃ��")) {
			M.Stack2("��0��0��0��0��0��2��7��c��7��l��6 ��ũ�� ������ Ʃ��", 1, 0, 1, Arrays.asList(), 21, GUI);
		} else {
			M.Stack("��6 ��ũ�� ������ Ʃ�� ��f��l����", 389, 0, 1, G.Glass, 21, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.��ũ�鸶���͹���")) {
			M.Stack2("��0��0��0��0��0��2��7��d��7��l��6 ��ũ�� ������ ����", 1, 0, 1, Arrays.asList(), 30, GUI);
		} else {
			M.Stack("��6 ��ũ�� ������ ���� ��f��l����", 389, 0, 1, G.Glass, 30, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.��ũ�鸶���ͽŹ�")) {
			M.Stack2("��0��0��0��0��0��2��7��e��7��l��6 ��ũ�� ������ �Ź�", 1, 0, 1, Arrays.asList(), 39, GUI);
		} else {
			M.Stack("��6 ��ũ�� ������ �Ź� ��f��l����", 389, 0, 1, G.Glass, 39, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.������ʸ���")) {
			M.Stack2("��0��0��0��0��0��2��8��b��7��l��6 �������� ������� ����", 1, 0, 1, Arrays.asList(), 13, GUI);
		} else {
			M.Stack("��6 �������� ������� ���� ��f��l����", 389, 0, 1, G.Glass, 13, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�������Ʃ��")) {
			M.Stack2("��0��0��0��0��0��2��8��c��7��l��6 �������� ������� Ʃ��", 1, 0, 1, Arrays.asList(), 22, GUI);
		} else {
			M.Stack("��6 �������� ������� Ʃ�� ��f��l����", 389, 0, 1, G.Glass, 22, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.������ʹ���")) {
			M.Stack2("��0��0��0��0��0��2��8��d��7��l��6 �������� ������� ����", 1, 0, 1, Arrays.asList(), 31, GUI);
		} else {
			M.Stack("��6 �������� ������� ���� ��f��l����", 389, 0, 1, G.Glass, 31, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.������ʽŹ�")) {
			M.Stack2("��0��0��0��0��0��2��8��e��7��l��6 �������� ������� �Ź�", 1, 0, 1, Arrays.asList(), 40, GUI);
		} else {
			M.Stack("��6 �������� ������� �Ź� ��f��l����", 389, 0, 1, G.Glass, 40, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�ĺ�Ű����")) {
			M.Stack2("��0��0��0��0��0��2��a��b��7��l��6 �ĺ�Ű ����", 1, 0, 1, Arrays.asList(), 14, GUI);
		} else {
			M.Stack("��6 �ĺ�Ű ���� ��f��l����", 389, 0, 1, G.Glass, 14, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�ĺ�ŰƩ��")) {
			M.Stack2("��0��0��0��0��0��2��a��c��7��l��6 �ĺ�Ű Ʃ��", 1, 0, 1, Arrays.asList(), 23, GUI);
		} else {
			M.Stack("��6 �ĺ�Ű Ʃ�� ��f��l����", 389, 0, 1, G.Glass, 23, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�ĺ�Ű����")) {
			M.Stack2("��0��0��0��0��0��2��a��d��7��l��6 �ĺ�Ű ����", 1, 0, 1, Arrays.asList(), 32, GUI);
		} else {
			M.Stack("��6 �ĺ�Ű ���� ��f��l����", 389, 0, 1, G.Glass, 32, GUI);
		}

		if (P.getInfoBoolean(p, "����ũ ���.�ĺ�Ű�Ź�")) {
			M.Stack2("��0��0��0��0��0��2��a��e��7��l��6 �ĺ�Ű �Ź�", 1, 0, 1, Arrays.asList(), 41, GUI);
		} else {
			M.Stack("��6 �ĺ�Ű �Ź� ��f��l����", 389, 0, 1, G.Glass, 41, GUI);
		}
		
		p.openInventory(GUI);
	}
}
