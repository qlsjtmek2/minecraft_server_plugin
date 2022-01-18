package me.shinkhan.pgui;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class QuitTime {
	public static void CreateQuitTime(File f, File folder, YamlConfiguration config)
	{
		try
		{
			folder.mkdir();
			f.createNewFile();
			config.set("Time", null);
			config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[DHPrivateGUI] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	}
	
	public static boolean isFile(String p) {
		File f = new File("plugins/DHPrivateGUI/" + p + ".yml");
		return f.exists() ? true : false;
	}
	
	public static int getYear(Player p) {
		if (QuitTime.getString(p, "Time").contains(",")) return Integer.parseInt(QuitTime.getString(p, "Time").split(",")[0]);
		else return -1;
	}
	
	public static int getMonth(Player p) {
		if (QuitTime.getString(p, "Time").contains(",")) return Integer.parseInt(QuitTime.getString(p, "Time").split(",")[1]);
		else return -1;
	}
	
	public static int getDate(Player p) {
		if (QuitTime.getString(p, "Time").contains(",")) return Integer.parseInt(QuitTime.getString(p, "Time").split(",")[2]);
		else return -1;
	}
	
	public static int getTime(Player p) {
		if (QuitTime.getString(p, "Time").contains(",")) return Integer.parseInt(QuitTime.getString(p, "Time").split(",")[3]);
		else return -1;
	}
	
	public static int getMinute(Player p) {
		if (QuitTime.getString(p, "Time").contains(",")) return Integer.parseInt(QuitTime.getString(p, "Time").split(",")[4]);
		else return -1;
	}
	
	public static String getMakeDate(OfflinePlayer p) {
		return QuitTime.getString(p, "Time");
	}
	
	public static String getNowTime(Player p) {
		Calendar now = Calendar.getInstance();
		StringBuilder st = new StringBuilder();
		st.append(now.get(Calendar.YEAR)); st.append(","); st.append(now.get(Calendar.MONTH) + 1); st.append(","); 
		st.append(now.get(Calendar.DAY_OF_MONTH)); st.append(","); st.append(now.get(Calendar.HOUR)); st.append(","); st.append(now.get(Calendar.MINUTE));
		return st.toString();
	}
	
	public static void setMakeDate(Player p, String time) {
		QuitTime.setString(p, "Time", time);
	}
	
	public static String getString(OfflinePlayer p, String name) {
		File f = new File("plugins/DHPrivateGUI/" + p.getName() + ".yml");
		File folder = new File("plugins/DHPrivateGUI");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) {
			CreateQuitTime(f, folder, config);
		}
		
		String getString = config.getString(name);
		if (getString == null) return null;
		return getString;
	}
	
	public static void setString(OfflinePlayer pl, String name, String amount) {
		File f = new File("plugins/DHPrivateGUI/" + pl.getName() + ".yml");
		File folder = new File("plugins/DHPrivateGUI");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		try {
			if (!f.exists()) {
				CreateQuitTime(f, folder, config);
			}
			
			config.set(name, amount);
			config.save(f);

		} catch (IOException ioex) {
			System.out.println("[DHPrivateGUI] Exception Occured: " + ioex.getMessage());
			System.out.println("========================");
			ioex.printStackTrace();
			System.out.println("========================");
		}
	}
}
