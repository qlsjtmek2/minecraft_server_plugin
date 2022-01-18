package com.linmalu.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.linmalu.Main.AutomaticTriggers;

public class Config_Variables {
	private AutomaticTriggers plugin;
	private FileWriter fileWriter;
	private FileReader fileReader;
	private String fileName;

	public Config_Variables(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
		fileName = plugin.getDataFolder() + File.separator + "Variables.yml";
	}
	public void SaveVariable()
	{
		try {
			fileWriter = new FileWriter(fileName);
			String names[] = plugin.scriptVariables.getKeyScriptVariables();
			fileWriter.write("����:\n");
			for(int i = 0; i < names.length; i++)
				fileWriter.write("  " + names[i] + " : " + plugin.scriptVariables.getScriptVariables(names[i]) + "\n");
			fileWriter.close();
			plugin.getLogger().info("���� ���� �Ϸ�");
		} catch (IOException e) {}
	}
	public void ReloadVariable()
	{
		try {
			fileReader = new FileReader(fileName);
			//�����ͻ���
			plugin.scriptVariables.clearScriptVariables();;
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
			for(int i = 0; i < args.length; i++)
			{
				if(args[i].contains("  ") && args[i].contains(" : "))
				{
					args[i] = args[i].replace("  ", "");
					String name[] = args[i].split(" : ");
					if(name.length == 2)
					{
						name[0] = name[0].replace(" ", "");
						name[1] = name[1].replace(" ", "");
						plugin.scriptVariables.putScriptVariables(name[0], name[1]);
					}
				}
			}
			plugin.getLogger().info("���� �ҷ����� �Ϸ�");
		} catch (FileNotFoundException e) {	
			plugin.getLogger().info("���� �ҷ��� ���� ����");
			SaveVariable();
		} catch (IOException e) {}
	}
}
