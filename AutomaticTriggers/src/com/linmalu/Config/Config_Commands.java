package com.linmalu.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.linmalu.Data.Data_Command;
import com.linmalu.Main.AutomaticTriggers;

public class Config_Commands
{
	private AutomaticTriggers plugin;
	private FileWriter fileWriter;
	private FileReader fileReader;
	private String fileName;

	public Config_Commands(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
		fileName = plugin.getDataFolder() + File.separator + "Commands.yml";
	}
	public void SaveCommand()
	{
		try {
			fileWriter = new FileWriter(fileName);
			String names[] = plugin.commands.getKeyCommands();
			fileWriter.write("��ɾ� Ʈ����:\n");
			for(int i = 0; i < names.length; i++)
			{
				fileWriter.write("  " + names[i] + ":\n");
				fileWriter.write("    �����: " + plugin.commands.getOverride(names[i]) + "\n");
				String scripts[] = plugin.commands.getScripts(names[i]);
				for(int j = 0; j < scripts.length; j++)
					fileWriter.write("    - " + scripts[j] + "\n");
			}
			fileWriter.close();
			plugin.getLogger().info("��ɾ� Ʈ���� ���� �Ϸ�");
		} catch (IOException e) {}
	}
	public void ReloadCommand()
	{
		try {
			fileReader = new FileReader(fileName);
			//�����ͻ���
			plugin.commands.clearCommands();
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
			String name = null;
			for(int i = 0; i < args.length; i++)
			{
				if(args[i].startsWith("  ") && !args[i].startsWith("    - ") && args[i].endsWith(":"))
				{
					name = args[i].substring(2, args[i].length() - 1);
					plugin.commands.putCommands(name, new Data_Command());
				}
				else if(args[i].equals("    �����: true") && name != null)
				{
					plugin.commands.setOverride(name, true);
				}
				else if(args[i].startsWith("    - ") && name != null)
				{
					plugin.commands.addScripts(name, args[i].substring(6));
				}
			}
			plugin.getLogger().info("��ɾ� Ʈ���� �ҷ����� �Ϸ�");
		} catch (FileNotFoundException e) {	
			plugin.getLogger().info("��ɾ� Ʈ���� �ҷ��� ���� ����");
			SaveCommand();
		} catch (IOException e) {}
	}
}
