package me.shinkhan.plantpotal;

import java.util.ArrayList;
import java.util.List;

public class GUIMessage {
	public static List<String> CloseGUI = new ArrayList<String>();
	public static List<String> BackRanking = new ArrayList<String>();
	public static List<String> Name = new ArrayList<String>();
	public static List<String> Info = new ArrayList<String>();
	public static List<String> Code = new ArrayList<String>();
	public static List<String> MoveOn = new ArrayList<String>();
	public static List<String> MoveOff = new ArrayList<String>();
	public static List<String> Delete = new ArrayList<String>();
	public static List<String> OpDelete = new ArrayList<String>();
	public static List<String> setwarp = new ArrayList<String>();

	public static void setGUIMessage()
	{
		CloseGUI.add("��7������ Ŭ���� â�� �ݽ��ϴ�.");
		BackRanking.add("��7������ Ŭ���� ���� â���� ���ư��ϴ�.");
		Name.add("��7������ Ŭ���� ������ �̸��� �����մϴ�.");
		Info.add("��7������ Ŭ���� ������ �߰�/������ �� �ֽ��ϴ�.");
		Code.add("��7������ Ŭ���� ������ �ڵ带 �����մϴ�.");
		Delete.add("��7������ Ŭ���� ���� ��Ż�� �����մϴ�.");
		OpDelete.add("��7������ Ŭ���� ���� ��Ż�� ���� �������� �����մϴ�.");
		MoveOn.add("��7������ Ŭ���� �� �ڷ���Ʈ ���θ� ��Ȱ��ȭ �մϴ�.");
		MoveOn.add("��7���� ����: ��aȰ��ȭ");
		MoveOff.add("��7������ Ŭ���� �� �ڷ���Ʈ ���θ� Ȱ��ȭ �մϴ�.");
		MoveOff.add("��7���� ����: ��c��Ȱ��ȭ");
		setwarp.add("��7������ Ŭ���� �� ���� ��ġ�� �����մϴ�.");
		setwarp.add("��7�� ������ �� �ʼ��� ���ּž� �մϴ�.");
		setwarp.add("��f");
		setwarp.add("��7* ��f�ڽ��� ������ Ŭ���ؾ� �����˴ϴ�.");
	}
}
