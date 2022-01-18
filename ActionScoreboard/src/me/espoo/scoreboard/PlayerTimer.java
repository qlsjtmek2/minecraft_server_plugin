package me.espoo.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import yt.codebukkit.scoreboardapi.Scoreboard;

public class PlayerTimer implements Runnable {
	private Player player;
	private int time;
	private boolean run = false;
	private String spellname;
	private Scoreboard board;
	private int id;

	protected PlayerTimer(Player player, String spellname, Scoreboard board) {
		this.player = player;
		this.spellname = spellname;
		this.board = board;
		this.id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("ActionScoreboard"), this, 0L, 20L);
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

	@Override
	public void run() {
		if (this.time > 0) {
			if (this.run) {
				this.time -= 1;
				this.board.setItem("§6▤ §f" + this.spellname, this.time);
				this.board.showToPlayer(this.player, true);
			}
		}

		if ((this.time <= 0) && (this.run)) {
			this.run = false;
			this.board.removeItem("§6▤ §f" + this.spellname);
			if (!this.board.isVacant()) this.board.showToPlayer(player, true);
			else this.board.showToPlayer(player, false);
			Bukkit.getServer().getScheduler().cancelTask(this.id);
			return;
			// 타이머가 종료되었습니다.
		}
	}

	public void Start() {
		if (this.run) {
			// 타이머가 이미 시작되었습니다.
		} else if (this.time < 0) {
			// 타이머의 시간이 설정되지 않았습니다.
		} else {
			this.run = true;
			// 타이머가 시작됩니다.
		}
	}

	public void Stop() {
		if (this.time < 0) {
			// 타이머가 이미 종료되었습니다.
		} else {
			this.time = 0;
			this.run = false;
			Bukkit.getServer().getScheduler().cancelTask(this.id);
		}
	}

	public void Pause() {
		if (!this.run) {
			// 타이머가 이미 정지되었습니다.
		} else {
			this.run = false;
			// 타이머가 정지되었습니다.
		}
	}
}
