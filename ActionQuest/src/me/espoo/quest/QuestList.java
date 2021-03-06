package me.espoo.quest;

import java.util.ArrayList;
import java.util.List;

import me.espoo.quest.yml.mainYml;

public class QuestList {
	public static List<String> ListName = new ArrayList<String>();
	public static List<List<String>> ListReward = new ArrayList<List<String>>();
	public static List<List<String>> ListTask = new ArrayList<List<String>>();
	public static List<List<String>> ListTalk = new ArrayList<List<String>>();
	
	public static void clearQuestList()
	{
		ListName.clear();
		ListReward.clear();
		ListTask.clear();
		ListTalk.clear();
	}
	
	public static void setQuestList()
	{
		List<String> Reward = new ArrayList<String>();
		List<String> Task = new ArrayList<String>();
		List<String> Talk = new ArrayList<String>();
		
		for (int i = 1; i <= main.mainMaxStep; i++) {
			Reward = new ArrayList<String>();
			Task = new ArrayList<String>();
			Talk = new ArrayList<String>();
			
			for (String list : mainYml.getList(i + ".보상"))
				Reward.add(list);
			
			for (String list : mainYml.getList(i + ".과제"))
				Task.add(list);
			
			for (String list : mainYml.getList(i + ".내용"))
				Talk.add(list);
			
			ListName.add(mainYml.getString(i + ".이름"));
			ListReward.add(Reward);
			ListTask.add(Task);
			ListTalk.add(Talk);
		}
	}
}
