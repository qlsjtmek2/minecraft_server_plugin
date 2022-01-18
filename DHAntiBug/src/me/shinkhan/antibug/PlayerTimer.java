package me.shinkhan.antibug;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerTimer implements Runnable {
	private Player player;
	private int time;
	private boolean run = false;
	private Runnable runnable;
	private int id;

	protected PlayerTimer(Player player, Runnable runnable) {
		this.player = player;
		this.runnable = runnable;
		this.id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("DHAntiBug"), this, 0L, 2L);
	}

	public Player getPlayer() {
		return this.player;
	}

	public int getTime() {
		return this.time;
	}

	public int getId() {
	    return this.id;
	}

	public void setTime(int time) {
	    this.time = time;
	}

	public void setRunnable(Runnable runnable) {
	    this.runnable = runnable;
	}

	@Override
	public void run() {
		if (this.time >= 0) {
			if (this.run) {
				this.time -= 1;
			}
		}

		if ((this.time < 0) && (this.run)) {
			this.run = false;
			runnable.run();
			Bukkit.getServer().getScheduler().cancelTask(this.id);
			// Ÿ�̸Ӱ� ����Ǿ����ϴ�.
		}
	}

	public void Start() {
		if (this.run) {
			// Ÿ�̸Ӱ� �̹� ���۵Ǿ����ϴ�.
		} else if (this.time < 0) {
			// Ÿ�̸��� �ð��� �������� �ʾҽ��ϴ�.
		} else {
			this.run = true;
			// Ÿ�̸Ӱ� ���۵˴ϴ�.
		}
	}

	public void Stop() {
		if (this.time < 0) {
			// Ÿ�̸Ӱ� �̹� ����Ǿ����ϴ�.
		} else {
			this.time = 0;
			this.run = false;
			Bukkit.getServer().getScheduler().cancelTask(this.id);
		}
	}

	public void Pause() {
		if (!this.run) {
			// Ÿ�̸Ӱ� �̹� �����Ǿ����ϴ�.
		} else {
			this.run = false;
			// Ÿ�̸Ӱ� �����Ǿ����ϴ�.
		}
	}
}
