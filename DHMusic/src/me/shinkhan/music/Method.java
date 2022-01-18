package me.shinkhan.music;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.NoteBlockPlayerMain;
import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.Song;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;

public class Method {
	public static String getFinalArg(String[] args, int start) {
		StringBuilder bldr = new StringBuilder();
	    for (int i = start; i < args.length; i++) {
	    	if (i != start) {
	    		bldr.append(" ");
	    	} bldr.append(args[i]);
	    } return bldr.toString();
	}
	
	public static String replaceAllColors(String message) {
        message = message.replaceAll("&1", "§1");
        message = message.replaceAll("&2", "§2");
        message = message.replaceAll("&3", "§3");
        message = message.replaceAll("&4", "§4");
        message = message.replaceAll("&5", "§5");
        message = message.replaceAll("&6", "§6");
        message = message.replaceAll("&7", "§7");
        message = message.replaceAll("&8", "§8");
        message = message.replaceAll("&9", "§9");
        message = message.replaceAll("&0", "§0");
        message = message.replaceAll("&a", "§a");
        message = message.replaceAll("&b", "§b");
        message = message.replaceAll("&c", "§c");
        message = message.replaceAll("&d", "§d");
        message = message.replaceAll("&e", "§e");
        message = message.replaceAll("&f", "§f");
        message = message.replaceAll("&k", "§k");
        message = message.replaceAll("&l", "§l");
        message = message.replaceAll("&m", "§m");
        message = message.replaceAll("&n", "§n");
        message = message.replaceAll("&o", "§o");
        message = message.replaceAll("&r", "§r");
        return message;
	}
	
	public static String searchOnlinePlayer(String target) {
		List<String> list1 = new ArrayList<String>();
		for (int i = 0; i < 16; i++) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.getName().toLowerCase().indexOf(target.toLowerCase()) == i) {
					list1.add(player.getName());
				}
			}
			if (list1.size() == 0) {
				continue;
			} else {
				int len = 100;
				List<String> list3 = new ArrayList<String>();
				for (int j = 0; j < list1.size(); j++) {
					int l = list1.get(j).length();
					if (l < len) {
						len = l;
					}
				}
				for (int j = 0; j < list1.size(); j++) {
					if (list1.get(j).length() == len) {
						list3.add(list1.get(j));
					}
				}
				String[] list2 = new String[list3.size()];
				for (int j = 0; j < list3.size(); j++) {
					list2[j] = list3.get(j);
				}
				Arrays.sort(list2);
				return list2[0];
			}
		}
		return null;
	}
	
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
	
	public static void play(Player[] players, String file)
	{
		Song s = NBSDecoder.parse(new File(main.getInstance().getDataFolder(), file.replaceAll(".nbs", "") + ".nbs"));
	    SongPlayer sp = new RadioSongPlayer(s);
	    sp.setAutoDestroy(true);

	    for (Player p : players) {
	    	if (Comand.songMap.containsKey(p.getName())) {
	    		((SongPlayer) Comand.songMap.get(p.getName())).removePlayer(p);
	    	}
	    	
	    	sp.addPlayer(p);
	    	Comand.songMap.put(p.getName(), sp);
	    	
			int i = 0;
			for (String str : Method.getFileList()) {
				if (str.equalsIgnoreCase(file)) break;
				i++;
			}
			
			if (main.Song.get(p) != null) main.Song.remove(p);
			main.Song.put(p, i);
	    } sp.setPlaying(true);
	}
	
	public static void stop(Player p)
	{
		NoteBlockPlayerMain.stopPlaying(p);
	}
	
	public static List<String> getFileList()
	{
		List<String> list = new ArrayList<String>();
		File directory = new File("plugins/DHMusic");
		/** 지정한 디렉토리 하위 파일의 갯수 **/
	    File[] childs = directory.listFiles(new FileFilter() {
	    	public boolean accept(File pathname) { return pathname.isFile(); }
	    });

	    //childs.length가 해당 폴더 안의 파일+하위폴더 갯수를 뜻한다.
	    for (int i = 0; i < childs.length; i++) {
	    	String childName = childs[i].toString().toLowerCase();
	    	
	    	//하위폴더와 필요없는 파일들을 제외하고 필요한 음악파일들만 출력한다.
	    	if ((childName.endsWith(".nbs"))) {
	    		list.add(childs[i].getName().replaceAll(".nbs", ""));
	    	}
	    } return list;
	}
}
