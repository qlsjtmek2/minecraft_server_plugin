package me.espoo.book;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GUIXWaepon {
	static PlayerYml P;
	static GUIMessage G;
	static GUImain M;
	
	@SuppressWarnings("static-access")
	public static void openGUI(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 54, "Ǯ�� ���� ����");
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
		
		for (int i = 0; i < 22; i ++) {
			if (i == 0) num = 1;
			else if (i == 1) num = 2;
			else if (i == 2) num = 3;
			else if (i == 3) num = 4;
			else if (i == 4) num = 5;
			else if (i == 5) num = 6;
			else if (i == 6) num = 7;
			else if (i == 7) num = 10;
			else if (i == 8) num = 19;
			else if (i == 9) num = 28;
			else if (i == 10) num = 37;
			else if (i == 11) num = 46;
			else if (i == 12) num = 47;
			else if (i == 13) num = 48;
			else if (i == 14) num = 49;
			else if (i == 15) num = 50;
			else if (i == 16) num = 51;
			else if (i == 17) num = 52;
			else if (i == 18) num = 43;
			else if (i == 19) num = 16;
			else if (i == 20) num = 25;
			else if (i == 21) num = 34;
			
			M.Stack2("��f����", 102, 0, 1, Arrays.asList(), num, GUI);
		}
		
		M.Stack("��f[ ��7�ڷΰ��� ��f]", 8, 0, 1, G.Water, 45, GUI);
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.���� ȹ��")) {
			M.Stack2("��f������ �����̽��ϴ�.", 323, 0, 1, Arrays.asList(), 42, GUI);
		} else {
			M.Stack("��a��l��  ��b10�� ��6��ȭ �ֹ���  ��a��l��", 339, 0, 1, G.XWaeponBook, 42, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.�������")) {
			M.Stack2("��0��0��0��0��0��5��d��5��7��l��6 ������ �� ��f+10", 1, 0, 1, Arrays.asList(), 11, GUI);
		} else {
			M.Stack("��6 ������ �� ��f+10 ��l����", 389, 0, 1, G.Glass, 11, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.�ݼӹ�Ʈ")) {
			M.Stack2("��0��0��0��0��0��5��e��0��7��l��6 �ݼ� ��Ʈ ��f+10", 1, 0, 1, Arrays.asList(), 12, GUI);
		} else {
			M.Stack("��6 �ݼ� ��Ʈ ��f+10 ��l����", 389, 0, 1, G.Glass, 12, GUI);
		}

		if (P.getInfoBoolean(p, "Ǯ�� ����.����")) {
			M.Stack2("��0��0��0��0��0��5��e��b��7��l��6 ������ ��f+10", 1, 0, 1, Arrays.asList(), 13, GUI);
		} else {
			M.Stack("��6 ������ ��f+10 ��l����", 389, 0, 1, G.Glass, 13, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.����")) {
			M.Stack2("��0��0��0��0��0��6��9��1��7��l��6 ���� �ӽŰ� ��f+10", 1, 0, 1, Arrays.asList(), 14, GUI);
		} else {
			M.Stack("��6 ���� �ӽŰ� ��f+10 ��l����", 389, 0, 1, G.Glass, 14, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.��Ż����Ʈ")) {
			M.Stack2("��0��0��0��0��0��5��f��6��7��l��6 ��Ż����Ʈ �̻��� ��f+10", 1, 0, 1, Arrays.asList(), 15, GUI);
		} else {
			M.Stack("��6 ��Ż����Ʈ �̻��� ��f+10 ��l����", 389, 0, 1, G.Glass, 15, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.���߸�")) {
			M.Stack2("��0��0��0��0��0��6��0��1��7��l��6 ���߸��� ���� ��f+10", 1, 0, 1, Arrays.asList(), 20, GUI);
		} else {
			M.Stack("��6 ���߸��� ���� ��f+10 ��l����", 389, 0, 1, G.Glass, 20, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.�������÷���")) {
			M.Stack2("��0��0��0��0��0��6��1��7��7��l��6 ������ �÷��� �� ��f+10", 1, 0, 1, Arrays.asList(), 21, GUI);
		} else {
			M.Stack("��6 ������ �÷��� �� ��f+10 ��l����", 389, 0, 1, G.Glass, 21, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.�ǹ���")) {
			M.Stack2("��0��0��0��0��0��4��3��4��7��l��6 �����ϼ�� ��f+10", 1, 0, 1, Arrays.asList(), 22, GUI);
		} else {
			M.Stack("��6 �����ϼ�� ��f+10 ��l����", 389, 0, 1, G.Glass, 22, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.�Ƹ��̸���ũ")) {
			M.Stack2("��0��0��0��0��0��6��2��2��7��l��6 �ɹ̳� ���� ��f+10", 1, 0, 1, Arrays.asList(), 23, GUI);
		} else {
			M.Stack("��6 �ɹ̳� ���� ��f+10 ��l����", 389, 0, 1, G.Glass, 23, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.�����")) {
			M.Stack2("��0��0��0��0��0��6��2��d��7��l��6 ����� �繫���� �� ��f+10", 1, 0, 1, Arrays.asList(), 24, GUI);
		} else {
			M.Stack("��6 ����� �繫���� �� ��f+10 ��l����", 389, 0, 1, G.Glass, 24, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.�����ǼҴ�")) {
			M.Stack2("��0��0��0��0��0��5��c��a��7��l��6 ������ �Ҵ� Į ��f+10", 1, 0, 1, Arrays.asList(), 29, GUI);
		} else {
			M.Stack("��6 ������ �Ҵ� Į ��f+10 ��l����", 389, 0, 1, G.Glass, 29, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.���뽺")) {
			M.Stack2("��0��0��0��0��0��6��3��8��7��l��6 ���뽺�� ���� ��f+10", 1, 0, 1, Arrays.asList(), 30, GUI);
		} else {
			M.Stack("��6 ���뽺�� ���� ��f+10 ��l����", 389, 0, 1, G.Glass, 30, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.�����")) {
			M.Stack2("��0��0��0��0��0��6��4��3��7��l��6 ����� ���� ��f+10", 1, 0, 1, Arrays.asList(), 31, GUI);
		} else {
			M.Stack("��6 ����� ���� ��f+10 ��l����", 389, 0, 1, G.Glass, 31, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.���ձݰ�����")) {
			M.Stack2("��0��0��0��0��0��6��4��e��7��l��6 �������� ���� ��f+10", 1, 0, 1, Arrays.asList(), 32, GUI);
		} else {
			M.Stack("��6 �������� ���� ��f+10 ��l����", 389, 0, 1, G.Glass, 32, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.ŷ")) {
			M.Stack2("��0��0��0��0��0��4��6��2��7��l��6 ŷ�� ���ӱ� ��f+10", 1, 0, 1, Arrays.asList(), 33, GUI);
		} else {
			M.Stack("��6 ŷ�� ���ӱ� ��f+10 ��l����", 389, 0, 1, G.Glass, 33, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.Ÿ����Ű")) {
			M.Stack2("��0��0��0��0��0��4��2��9��7��l��6 Ÿ����Ű ������ ��f+10", 1, 0, 1, Arrays.asList(), 38, GUI);
		} else {
			M.Stack("��6 Ÿ����Ű ������ ��f+10 ��l����", 389, 0, 1, G.Glass, 38, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.��ũ�鸶����")) {
			M.Stack2("��0��0��0��0��0��6��5��9��7��l��6 ��ũ�� ������ ���� ��f+10", 1, 0, 1, Arrays.asList(), 39, GUI);
		} else {
			M.Stack("��6 ��ũ�� ������ ���� ��f+10 ��l����", 389, 0, 1, G.Glass, 39, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.�������")) {
			M.Stack2("��0��0��0��0��0��6��6��4��7��l��6 ���� ��Ÿ�� ��f+10", 1, 0, 1, Arrays.asList(), 40, GUI);
		} else {
			M.Stack("��6 ���� ��Ÿ�� ��f+10 ��l����", 389, 0, 1, G.Glass, 40, GUI);
		}
		
		if (P.getInfoBoolean(p, "Ǯ�� ����.�ĺ�Ű")) {
			M.Stack2("��0��0��0��0��0��6��6��f��7��l��6 �ĺ�Ű ������ ��f+10", 1, 0, 1, Arrays.asList(), 41, GUI);
		} else {
			M.Stack("��6 �ĺ�Ű ������ ��f+10 ��l����", 389, 0, 1, G.Glass, 41, GUI);
		}
		
		p.openInventory(GUI);
	}
}
