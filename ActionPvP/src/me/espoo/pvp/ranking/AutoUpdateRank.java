package me.espoo.pvp.ranking;

public class AutoUpdateRank implements Runnable {
	public void run()
	{
	    UpdateRanking rank = new UpdateRanking();
	    Thread thread = new Thread(rank);
	    thread.start();
	}
}
