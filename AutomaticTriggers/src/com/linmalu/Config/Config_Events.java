package com.linmalu.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.linmalu.Main.AutomaticTriggers;

public class Config_Events
{
	private AutomaticTriggers plugin;
	private FileWriter fileWriter;
	private FileReader fileReader;
	private String fileName;

	public Config_Events(AutomaticTriggers plugin)
	{
		this.plugin = plugin;
		fileName = plugin.getDataFolder() + File.separator + "Events.yml";
	}
	public void SaveEvent()
	{
		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.write("�̺�Ʈ Ʈ����:\n");
			String eventNames[] = plugin.events.getEventNames();
			for(int i = 0; i < eventNames.length; i++)
			{
				fileWriter.write("  " + eventNames[i] + ":\n");
				String scripts[] = plugin.events.getScripts(eventNames[i]);
				for(int j = 0; j < scripts.length; j++)
					fileWriter.write("    - " + scripts[j] + "\n");
			}
			fileWriter.close();
			plugin.getLogger().info("�̺�Ʈ Ʈ���� ���� �Ϸ�");
		} catch (IOException e) {}
	}
	public void ReloadEvent()
	{
		try {
			fileReader = new FileReader(fileName);
			//�����ͻ���
			plugin.events.clearEvents();
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
			String eventName = null;
			for(int i = 0; i < args.length; i++)
			{
				if(args[i].startsWith("  ") && !(args[i].startsWith("    - ")) && args[i].endsWith(":"))
					eventName = args[i].substring(2, args[i].length() - 1);
				else if(args[i].startsWith("    - ") && eventName != null)
					plugin.events.setEvent(eventName, args[i].substring(6));
			}
			plugin.getLogger().info("�̺�Ʈ Ʈ���� �ҷ����� �Ϸ�");
		} catch (FileNotFoundException e) {	
			plugin.getLogger().info("�̺�Ʈ Ʈ���� �ҷ��� ���� ����");
			SaveEvent();
		} catch (IOException e) {}
	}
}
