package me.espoo.removechest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener 
{	
	public void onEnable()
	{
		for (String str : getFileList())  {
			String sumFilePath = "plugins/RemoveChest/" + str;
			File file = new File(sumFilePath);		
			String dummy = "";
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
				
				//1. 삭제하고자 하는 position 이전까지는 이동하며 dummy에 저장
				String line;
				for(int i=0; i<1; i++) {
				    line = br.readLine(); //읽으며 이동
				    dummy += (line + "\r\n" ); 
				}
				
				//2. 삭제하고자 하는 데이터는 건너뛰기
				
				//3. 삭제하고자 하는 position 이후부터 dummy에 저장
				while((line = br.readLine())!=null) {
					dummy += (line + "\r\n" ); 
				}
				
				//4. FileWriter를 이용해서 덮어쓰기
				FileWriter fw = new FileWriter(sumFilePath);
				fw.write(dummy);			
				
				//bw.close();
				fw.close();
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.GREEN + "활성화가 완료되었습니다.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "■" + ChatColor.GRAY + "■ " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " 버전 " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "■" + ChatColor.RED + "■ " + ChatColor.RED + "비활성화가 완료되었습니다.");
	}
	
	public static List<String> getFileList()
	{
		List<String> list = new ArrayList<String>();
		File directory = new File("plugins/RemoveChest");
		/** 지정한 디렉토리 하위 파일의 갯수 **/
	    File[] childs = directory.listFiles(new FileFilter() {
	    	public boolean accept(File pathname) { return pathname.isFile(); }
	    });

	    //childs.length가 해당 폴더 안의 파일+하위폴더 갯수를 뜻한다.
	    for (int i = 0; i < childs.length; i++) {
	    	String childName = childs[i].toString().toLowerCase();
	    	
	    	//하위폴더와 필요없는 파일들을 제외하고 필요한 음악파일들만 출력한다.
	    	if ((childName.endsWith(".yml"))) {
	    		list.add(childs[i].getName());
	    	}
	    } return list;
	}
}
