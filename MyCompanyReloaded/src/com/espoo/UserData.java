package com.espoo;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class UserData 
{	 
	 public static void CreateNewFile(Player p, File f, File folder, File folder2, YamlConfiguration config)
	 {
		 try
		 {
			 folder.mkdir();
			 folder2.mkdir();
			 f.createNewFile();
			 config.set("사장", false);
			 config.set("직원", false);
			 config.set("초대", "NONE");
			 config.set("이력서", false);
			 config.set("회사", "NONE");
			 config.save(f);
		 } catch (IOException ioex) {
			 System.out.println("[MyCompany] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	 }
	 
	 public static String getStringYml(Player p, String name) {
			File f = new File("plugins/MyCompany/UserData/" + p.getName() + ".yml");
			File folder = new File("plugins/MyCompany");
			File folder2 = new File("plugins/MyCompany/UserData");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
			if (!f.exists()) {
				CreateNewFile(p, f, folder, folder2, config);
			}
			
		 String getString = config.getString(name);
		 return getString;
	 }
	 
	 public static Boolean getBooleanYml(Player p, String name) {
			File f = new File("plugins/MyCompany/UserData/" + p.getName() + ".yml");
			File folder = new File("plugins/MyCompany");
			File folder2 = new File("plugins/MyCompany/UserData");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
			if (!f.exists()) {
				CreateNewFile(p, f, folder, folder2, config);
			}
			
		 Boolean getBoolean = config.getBoolean(name);
		 return getBoolean;
	 }
	 
	 public static void SetBooleanData(Player p, String name, Boolean amount) {
			File f = new File("plugins/MyCompany/UserData/" + p.getName() + ".yml");
			File folder = new File("plugins/MyCompany");
			File folder2 = new File("plugins/MyCompany/UserData");
		 YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		 try {
			 if (!f.exists()) {
				 CreateNewFile(p, f, folder, folder2, config);
			 }
				
			 config.set(name, amount);
			 config.save(f);

		 } catch (IOException ioex) {
			 System.out.println("[MyCompany] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	 }
	 
	 public static void SetStringData(Player p, String name, String amount) {
			File f = new File("plugins/MyCompany/UserData/" + p.getName() + ".yml");
			File folder = new File("plugins/MyCompany");
			File folder2 = new File("plugins/MyCompany/UserData");
		 YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		 try {
			 if (!f.exists()) {
				 CreateNewFile(p, f, folder, folder2, config);
			 }
				
			 config.set(name, amount);
			 config.save(f);

		 } catch (IOException ioex) {
			 System.out.println("[MyCompany] Exception Occured: " + ioex.getMessage());
			 System.out.println("========================");
			 ioex.printStackTrace();
			 System.out.println("========================");	
		 }
	 }
}