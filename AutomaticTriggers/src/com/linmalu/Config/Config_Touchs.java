package com.linmalu.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.linmalu.Main.AutomaticTriggers;

public class Config_Touchs
{
	private AutomaticTriggers plugin;
	private FileWriter fileWriter;
	private FileReader fileReader;
	private String fileName;

	public Config_Touchs(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
		fileName = plugin.getDataFolder() + File.separator + "Touchs.yml";
	}
	public void SaveTouch()
	{
		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.write("��ġ Ʈ����:\n");
			String worldNames[] = plugin.touchs.getWorldNames();
			for(int i = 0; i < worldNames.length; i++)
			{
				fileWriter.write("  " + worldNames[i] + ":\n");
				String locations[] = plugin.touchs.getLocations(worldNames[i]);
				for(int j = 0; j < locations.length; j++)
				{
					fileWriter.write("    " + locations[j] + ":\n");
					String scripts[] = plugin.touchs.getScripts(worldNames[i], locations[j]);
					for(int k = 0; k < scripts.length; k++)
						fileWriter.write("    - " + scripts[k] + "\n");
				}
			}
			fileWriter.close();
			plugin.getLogger().info("��ġ Ʈ���� ���� �Ϸ�");
		} catch (IOException e) {}
	}
	public void ReloadTouch()
	{
		try {
			fileReader = new FileReader(fileName);
			//�����ͻ���
			plugin.touchs.clearTouchs();
			//�����Ͱ�������
			String yml = "";
			while(true)
			{
				int i = fileReader.read();
				yml += (char)i;
				if(i == -1)
					break;
			}
			fileReader.close();
			//�����ͳֱ�
			String args[] = yml.split("\n");
			String worldName = null;
			String location = null;
			for(int i = 0; i < args.length; i++)
			{
				if(args[i].startsWith("  ") && !(args[i].startsWith("    ")) && args[i].endsWith(":"))
				{
					worldName = args[i].substring(2, args[i].length() - 1);
					plugin.touchs.setTouchs(worldName);
				}
				else if(args[i].startsWith("    ") && args[i].endsWith(":") && worldName != null)
				{
					location = args[i].substring(4, args[i].length() - 1);
					plugin.touchs.setTouchs(worldName, location);
				}
				else if(args[i].startsWith("    - ") && worldName != null)
				{
					plugin.touchs.setTouchs(worldName, location, args[i].substring(6));
				}
			}
			plugin.getLogger().info("��ġ Ʈ���� �ҷ����� �Ϸ�");
		} catch (FileNotFoundException e) {	
			plugin.getLogger().info("��ġ Ʈ���� �ҷ��� ���� ����");
			SaveTouch();
		} catch (IOException e) {}
	}
}
