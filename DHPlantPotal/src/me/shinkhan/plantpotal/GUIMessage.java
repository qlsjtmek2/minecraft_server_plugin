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
		CloseGUI.add("§7아이템 클릭시 창을 닫습니다.");
		BackRanking.add("§7아이템 클릭시 이전 창으로 돌아갑니다.");
		Name.add("§7아이템 클릭시 아이템 이름을 수정합니다.");
		Info.add("§7아이템 클릭시 설명을 추가/제거할 수 있습니다.");
		Code.add("§7아이템 클릭시 아이템 코드를 변경합니다.");
		Delete.add("§7아이템 클릭시 유저 포탈을 삭제합니다.");
		OpDelete.add("§7아이템 클릭시 유저 포탈을 오피 권한으로 삭제합니다.");
		MoveOn.add("§7아이템 클릭시 섬 텔레포트 여부를 비활성화 합니다.");
		MoveOn.add("§7현재 상태: §a활성화");
		MoveOff.add("§7아이템 클릭시 섬 텔레포트 여부를 활성화 합니다.");
		MoveOff.add("§7현재 상태: §c비활성화");
		setwarp.add("§7아이템 클릭시 섬 워프 위치를 변경합니다.");
		setwarp.add("§7이 설정은 꼭 필수로 해주셔야 합니다.");
		setwarp.add("§f");
		setwarp.add("§7* §f자신의 섬에서 클릭해야 설정됩니다.");
	}
}
