package me.shinkhan.antibug;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import me.shinkhan.antibug.yml.OnlyKRYml;

public class API {
	public static boolean containString(String message, String target) {
		char[] tar = target.toCharArray();
		int i = 0;
		for (char c : message.toCharArray()) {
			if (c == tar[i])
				i++;
			if (i == tar.length)
				return true;
		}
		return i == tar.length;
	}
	
	public static String changeString(String message, String target) {
		char[] tar = target.toCharArray();
		char[] chr = message.toCharArray();
		int a = 0;
		for (int num = 0; num < tar.length; num++) {
			a = 0;
			for (char c : message.toCharArray()) {
				if (c == tar[num])
					chr[a] = '*';
				a++;
			}
		}
		
		return new String(chr, 0, chr.length);
	}

	public static boolean isCrashColor(String s) {
		return (s.contains("§k")) || (s.contains("&k"));
	}

	public static void sendMSGtoOP(String s) {
		for (Player p : Bukkit.getServer().getOnlinePlayers())
			if (p.isOp())
				p.sendMessage(s);
	}

	public static String colorize(String s) {
		return ChatColor.translateAlternateColorCodes("&".charAt(0), s);
	}

	public static boolean CheckFindRpgItem(PluginManager pm) {
		return pm.isPluginEnabled("RPG Items");
	}

	public static void SendOperator(String msg) {
		for (OfflinePlayer op : Bukkit.getOperators())
			if (op.isOnline())
				op.getPlayer().sendMessage(ChatColor.YELLOW + "[감시보고]" + ChatColor.AQUA + msg);
	}

	public static void LogInfo(String str) {
		main.log.info(DeleteColor(str));
	}

	public static String DeleteColor(String str) {
		str = str.replaceAll("&0", "");
		str = str.replaceAll("&1", "");
		str = str.replaceAll("&2", "");
		str = str.replaceAll("&3", "");
		str = str.replaceAll("&4", "");
		str = str.replaceAll("&5", "");
		str = str.replaceAll("&6", "");
		str = str.replaceAll("&7", "");
		str = str.replaceAll("&8", "");
		str = str.replaceAll("&9", "");
		str = str.replaceAll("&a", "");
		str = str.replaceAll("&b", "");
		str = str.replaceAll("&c", "");
		str = str.replaceAll("&d", "");
		str = str.replaceAll("&e", "");
		str = str.replaceAll("&f", "");
		str = str.replaceAll("&k", "");
		str = str.replaceAll("&l", "");
		str = str.replaceAll("&m", "");
		str = str.replaceAll("&n", "");
		str = str.replaceAll("&o", "");

		str = str.replaceAll("§0", "");
		str = str.replaceAll("§1", "");
		str = str.replaceAll("§2", "");
		str = str.replaceAll("§3", "");
		str = str.replaceAll("§4", "");
		str = str.replaceAll("§5", "");
		str = str.replaceAll("§6", "");
		str = str.replaceAll("§7", "");
		str = str.replaceAll("§8", "");
		str = str.replaceAll("§9", "");
		str = str.replaceAll("§a", "");
		str = str.replaceAll("§b", "");
		str = str.replaceAll("§c", "");
		str = str.replaceAll("§d", "");
		str = str.replaceAll("§e", "");
		str = str.replaceAll("§f", "");
		str = str.replaceAll("§k", "");
		str = str.replaceAll("§l", "");
		str = str.replaceAll("§m", "");
		str = str.replaceAll("§n", "");
		str = str.replaceAll("§o", "");

		return str;
	}

	public static void announceop(Player p) {
		for (Player op : Bukkit.getOnlinePlayers())
			if (op.isOp())
				op.sendMessage(main.prefix + " §e" + p.getName() + "§f님이 무한 아이템 만들기 버그를 시도 했습니다.");
	}

	public static void addAllowIP(String IP) {
		List<String> list = API.getAllowIPList();
		list.add(IP);
		main.allowIP.add(IP);
		API.setAllowIPList(list);
	}

	public static List<String> getAllowIPList() {
		return main.allowIP;
	}

	public static void setAllowIPList(List<String> list) {
		OnlyKRYml.setList("허용 해외 아이피", list);
	}

	public static void addAllowOpIP(String IP) {
		List<String> list = API.getAllowOpIPList();
		list.add(IP);
		main.allowOPIP.add(IP);
		API.setAllowOpIPList(list);
	}

	public static List<String> getAllowOpIPList() {
		return main.allowOPIP;
	}

	public static void setAllowOpIPList(List<String> list) {
		OnlyKRYml.setList("허용 오피계정 아이피", list);
	}

	public static boolean CheckIP(String IP) {
		try {
			URL url = new URL(
					"http://whois.kisa.or.kr/openapi/whois.jsp?query=" + IP + "&key=2016082116560731819479&answer=xml");
			URLConnection conn = url.openConnection();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(conn.getInputStream());
			NodeList nodelist = doc.getElementsByTagName("countryCode");
			NodeList LOOPIP = doc.getElementsByTagName("registry");
			Node LOOPNODE = LOOPIP.item(0).getChildNodes().item(0);
			Node tectNode = nodelist.item(0).getChildNodes().item(0);
			if (LOOPNODE.getNodeValue().trim().equalsIgnoreCase("SPECIAL")) {
				System.out.println("[OnlyKoreanIP] 루프백 아이피입니다. 접속 허가.");
				return true;
			}
			if (tectNode.getNodeValue().trim().equalsIgnoreCase("KR")) {
				System.out.println("[OnlyKoreanIP] " + IP + "- 한국 아이피 승인");
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("에외 발생! 이 리포트를 개발자한테 넘겨주시면 감사하겠습니다.");
			e.printStackTrace();
		}
		return false;
	}

	public boolean CheckProxy(String IP) {
		try {
			URL url = new URL("http://www.stopforumspam.com/api?ip=" + IP);
			URLConnection conn = url.openConnection();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(conn.getInputStream());
			NodeList nodelist = doc.getElementsByTagName("appears");
			Node tectNode = nodelist.item(0).getChildNodes().item(0);
			if (tectNode.getNodeValue().trim().equalsIgnoreCase("yes"))
				return false;
		} catch (Exception e) {
			System.out.println("에외 발생! 이 리포트를 개발자한테 넘겨주시면 감사하겠습니다.");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
