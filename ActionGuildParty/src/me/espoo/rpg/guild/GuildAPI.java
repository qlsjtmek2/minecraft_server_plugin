package me.espoo.rpg.guild;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.espoo.rpg.PlayerYml;
import me.espoo.rpg.main;
import us.talabrek.ultimateskyblock.IslandCommand;
import us.talabrek.ultimateskyblock.PlayerInfo;
import us.talabrek.ultimateskyblock.Settings;
import us.talabrek.ultimateskyblock.WorldGuardHandler;
import us.talabrek.ultimateskyblock.uSkyBlock;

public class GuildAPI {
	public static boolean checkInputOnlyNumberAndAlphabet(String textInput) {
		char chrInput;
		
		for (int i = 0; i < textInput.length(); i++) {
			chrInput = textInput.charAt(i); // 입력받은 텍스트에서 문자 하나하나 가져와서 체크
		   
			if (chrInput >= 0x61 && chrInput <= 0x7A) {
				// 영문(소문자) OK!
			}
			else if (chrInput >= 0x41 && chrInput <= 0x5A) {
				// 영문(대문자) OK!
			}
			else if (chrInput >= 0x30 && chrInput <= 0x39) {
				// 숫자 OK!
			}
			else if (chrInput == 0x5F) {
				// _ OK!
			}
			else {
				return false;   // 영문자도 아니고 숫자도 아님!
			}
		}
		 
		return true;
	}
	
	public static boolean isGuild(String name) {
		if (Guild.guilds.containsKey(name)) return true;
		else return false;
	}
	
	public static void setPlayerChat(Player p, String mode) {
		PlayerYml.setInfoString(p, "채팅 모드", mode);
	}
	
	public static String getPlayerChat(Player p) {
		return PlayerYml.getInfoString(p.getName(), "채팅 모드");
	}
	
	// 유저 = 1, 정회원 = 2, 디자이너 = 3, 스탭 = 4
	public static int getClassConfig(Guild guild, int upgrade, int num) {
		if (guild != null) {
			List<String> list = main.guildConfig;
			String str = list.get(upgrade + 1);
			return Integer.parseInt(str.split(" ")[num + 1]);
		} return -1;
	}
	
	public static int getClassAmount(Guild guild, String Class) {
		if (guild != null) {
			int num = 0;
			for (String str : guild.getUsers()) {
				if (str.contains(Class)) num++;
			} return num;
		} return -1;
	}
}
