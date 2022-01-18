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
				
				//1. �����ϰ��� �ϴ� position ���������� �̵��ϸ� dummy�� ����
				String line;
				for(int i=0; i<1; i++) {
				    line = br.readLine(); //������ �̵�
				    dummy += (line + "\r\n" ); 
				}
				
				//2. �����ϰ��� �ϴ� �����ʹ� �ǳʶٱ�
				
				//3. �����ϰ��� �ϴ� position ���ĺ��� dummy�� ����
				while((line = br.readLine())!=null) {
					dummy += (line + "\r\n" ); 
				}
				
				//4. FileWriter�� �̿��ؼ� �����
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
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.GREEN + "Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public void onDisable()
	{
		PluginDescriptionFile pdFile = getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "��" + ChatColor.GRAY + "�� " + ChatColor.WHITE + pdFile.getName() + ChatColor.YELLOW + " ���� " + ChatColor.WHITE + pdFile.getVersion());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "��" + ChatColor.RED + "�� " + ChatColor.RED + "��Ȱ��ȭ�� �Ϸ�Ǿ����ϴ�.");
	}
	
	public static List<String> getFileList()
	{
		List<String> list = new ArrayList<String>();
		File directory = new File("plugins/RemoveChest");
		/** ������ ���丮 ���� ������ ���� **/
	    File[] childs = directory.listFiles(new FileFilter() {
	    	public boolean accept(File pathname) { return pathname.isFile(); }
	    });

	    //childs.length�� �ش� ���� ���� ����+�������� ������ ���Ѵ�.
	    for (int i = 0; i < childs.length; i++) {
	    	String childName = childs[i].toString().toLowerCase();
	    	
	    	//���������� �ʿ���� ���ϵ��� �����ϰ� �ʿ��� �������ϵ鸸 ����Ѵ�.
	    	if ((childName.endsWith(".yml"))) {
	    		list.add(childs[i].getName());
	    	}
	    } return list;
	}
}
