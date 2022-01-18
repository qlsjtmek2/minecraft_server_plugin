package com.linmalu.Data;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Data_Cooldown_Methods
{
	private ArrayList<Data_Cooldown> list = new ArrayList<>(0);
	
	public void putData(Data_Cooldown data)
	{
		list.add(data);
	}
	public boolean containsData(String name, Player player)
	{
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).name.equals(name) && (list.get(i).player == null || list.get(i).player == player))
			{
				return true;
			}
		}
		return false;
	}
	public void removeData(Data_Cooldown data)
	{
		list.remove(data);
	}
}
