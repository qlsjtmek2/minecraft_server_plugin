package me.shinkhan.pgui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Method {
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
	
	public static boolean checkInputOnlyNumberAndAlphabet(String textInput) {
		char chrInput;
		
		for (int i = 0; i < textInput.length(); i++) {
			chrInput = textInput.charAt(i); // �Է¹��� �ؽ�Ʈ���� ���� �ϳ��ϳ� �����ͼ� üũ
		   
			if (chrInput >= 0x61 && chrInput <= 0x7A) {
				// ����(�ҹ���) OK!
			}
			else if (chrInput >= 0x41 && chrInput <= 0x5A) {
				// ����(�빮��) OK!
			}
			else if (chrInput >= 0x30 && chrInput <= 0x39) {
				// ���� OK!
			}
			else if (chrInput == 0x5F) {
				// _ OK!
			}
			else {
				return false;   // �����ڵ� �ƴϰ� ���ڵ� �ƴ�!
			}
		}
		 
		return true;
	}
}
