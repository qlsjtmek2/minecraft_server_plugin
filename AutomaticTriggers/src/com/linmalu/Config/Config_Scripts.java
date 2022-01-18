package com.linmalu.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.linmalu.Main.AutomaticTriggers;

public class Config_Scripts
{
	private AutomaticTriggers plugin;
	private FileReader fileReader;
	private String fileDir;
	private File file;

	public Config_Scripts(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
		fileDir = plugin.getDataFolder() + File.separator;
		file = new File(fileDir);
	}
	public void ReloadScript()
	{
		for(String fileName : file.list())
		{
			if(fileName.endsWith(".script.yml") && !fileName.startsWith("ex.script.yml"))
			{
				try {
					fileReader = new FileReader(fileDir + fileName);
					//데이터삭제
					plugin.scripts.clearScripts();
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
					fileName = fileName.replace(".script.yml", "");
					String scriptName = null;
					plugin.scripts.setScripts(fileName);
					for(int i = 0; i < args.length; i++)
					{
						if(args[i].startsWith("  ") && !(args[i].startsWith("    - ")) && args[i].endsWith(":"))
						{
							scriptName = args[i].substring(2, args[i].length() - 1);
							plugin.scripts.setScripts(fileName, scriptName);
						}
						else if(args[i].startsWith("    - ") && scriptName != null)
						{
							plugin.scripts.setScripts(fileName, scriptName, args[i].substring(6));
						}
					}
					plugin.getLogger().info(fileName + " 스크립트 불러오기 완료");
				} catch (FileNotFoundException e) {	
				} catch (IOException e) {}
			}
		}
	}
}
