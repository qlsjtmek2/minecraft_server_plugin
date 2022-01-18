package com.linmalu.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.linmalu.Main.AutomaticTriggers;

public class Config_Areas
{
	private AutomaticTriggers plugin;
	private FileWriter fileWriter;
	private FileReader fileReader;
	private String fileName;

	public Config_Areas(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
		fileName = plugin.getDataFolder() + File.separator + "Areas.yml";
	}
	public void SaveArea()
	{
		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.write("공간트리거:\n");
			String worldNames[] = plugin.areas.getWorldNames();
			for(int i = 0; i < worldNames.length; i++)
			{
				fileWriter.write("  " + worldNames[i] + ":\n");
				String locations[] = plugin.areas.getLocations(worldNames[i]);
				for(int j = 0; j < locations.length; j++)
				{
					fileWriter.write("    " + locations[j] + ":\n");
					String scripts[] = plugin.areas.getScripts(worldNames[i], locations[j]);
					for(int k = 0; k < scripts.length; k++)
						fileWriter.write("    - " + scripts[k] + "\n");
				}
			}
			fileWriter.close();
			plugin.getLogger().info("공간 트리거 저장 완료");
		} catch (IOException e) {}
	}
	public void ReloadArea()
	{
		try {
			fileReader = new FileReader(fileName);
			//데이터삭제
			plugin.areas.clearAreas();
			//데이터가져오기
			String yml = "";
			while(true)
			{
				int i = fileReader.read();
				yml += (char)i;
				if(i == -1)
					break;
			}
			fileReader.close();
			//데이터넣기
			String args[] = yml.split("\n");
			String worldName = null;
			String location = null;
			for(int i = 0; i < args.length; i++)
			{
				if(args[i].startsWith("  ") && !(args[i].startsWith("    ")) && args[i].endsWith(":"))
				{
					worldName = args[i].substring(2, args[i].length() - 1);
					plugin.areas.setAreas(worldName);
				}
				else if(args[i].startsWith("    ") && args[i].endsWith(":") && worldName != null)
				{
					location = args[i].substring(4, args[i].length() - 1);
					plugin.areas.setAreas(worldName, location);
				}
				else if(args[i].startsWith("    - ") && worldName != null)
				{
					plugin.areas.setAreas(worldName, location, args[i].substring(6));
				}
			}
			plugin.getLogger().info("공간 트리거 불러오기 완료");
		} catch (FileNotFoundException e) {	
			plugin.getLogger().info("공간 트리거 불러올 파일 없음");
			SaveArea();
		} catch (IOException e) {}
	}
}
