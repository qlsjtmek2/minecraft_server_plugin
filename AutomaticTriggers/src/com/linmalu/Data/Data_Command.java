package com.linmalu.Data;

import java.util.ArrayList;

public class Data_Command
{
	public boolean override = false;
	public ArrayList<String> script= new ArrayList<>(0);

	public Data_Command()
	{
	}
	public Data_Command(String script)
	{
		this.script.add(script);
	}
	public Data_Command(String script, boolean overide)
	{
		this.override = overide;
		this.script.add(script);
	}
}
