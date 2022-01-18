package me.shinkhan.Quiz;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("deprecation")
public class mainEvent extends JavaPlugin implements Listener {
	main main;
		
	public mainEvent(main main)
	{
		this.main = main;
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		Player p = e.getPlayer();

		if (main.MatAnswer.get("Quiz") != null) {
			if (Method.isStringDouble(e.getMessage())) {
				if (main.MatAnswer.get("Quiz") == Integer.parseInt(e.getMessage())) {
					Bukkit.broadcastMessage("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
					Bukkit.broadcastMessage(main.mat + "§c" + p.getName() + "§6님, §c정답§6입니다!");
					Bukkit.broadcastMessage(main.mat + "§6보상으로 §c" + Method.getConfigInt("수학 퀴즈 보상") + "원 §6이 수령됩니다.  " + main.zd + "§f" + main.MatAnswer.get("Quiz"));
					Bukkit.broadcastMessage("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
					
					main.economy.depositPlayer(p.getName(), Method.getConfigInt("수학 퀴즈 보상"));
					main.MatAnswer.remove("Quiz");
					main.matOne.remove("Quiz");
					main.matTwo.remove("Quiz");
					main.matMark.remove("Quiz");
					Integer id = main.MatTimer.remove("Quiz");
					Bukkit.getServer().getScheduler().cancelTask(id);
				}
			}
		}
		
		if (main.quizStart.get("Quiz") != null) {
			if (main.quizPlayer.get("Quiz") != p.getName()) {
				if (main.quizAnswer.get("Quiz").equals(e.getMessage())) {
					Bukkit.broadcastMessage("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
					Bukkit.broadcastMessage(main.prx + "§c" + p.getName() + "§6님, §c정답§6입니다!");
					Bukkit.broadcastMessage(main.prx + "§6보상으로 §c" + main.quizMoney.get("Quiz") + "원 §6이 수령됩니다.  " + main.zd + "§f" + main.quizAnswer.get("Quiz"));
					Bukkit.broadcastMessage("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
					
					main.economy.depositPlayer(p.getName(), Integer.parseInt(main.quizMoney.get("Quiz")));
					main.quizAnswer.remove("Quiz");
					main.quizContent.remove("Quiz");
					main.quizMoney.remove("Quiz");
					main.quizPlayer.remove("Quiz");
					main.quizStart.remove("Quiz");
					main.QuizTime.remove("Quiz");
					main.QuizCoolTime.put("Quiz", "true");
					Integer id = main.QuizTimer.remove("Quiz");
					Bukkit.getServer().getScheduler().cancelTask(id);
					
					main.QuizCoolTimer.put("Quiz", new BukkitRunnable()
					{
						@Override
						public void run()
						{
							main.QuizCoolTime.remove("Quiz");
							Integer id = main.QuizCoolTimer.remove("Quiz");
							Bukkit.getServer().getScheduler().cancelTask(id);
							return;
						}
					}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHQuiz"), Method.getConfigInt("유저 퀴즈 쿨타임 (초)") * 20, 0).getTaskId());
				}
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if (main.quizPlayer.get("Quiz") == p.getName()) {
			Bukkit.broadcastMessage(main.prx + "§c출제자가 §6나가여서 문제가 §c취소 §6되었습니다. §b출제자 §7:: §c" + main.quizPlayer.get("Quiz"));
			if (main.quizMoney.get("Quiz") != null) main.economy.depositPlayer(p.getName(), Integer.parseInt(main.quizMoney.get("Quiz")));
			main.quizAnswer.remove("Quiz");
			main.quizContent.remove("Quiz");
			main.quizMoney.remove("Quiz");
			main.quizPlayer.remove("Quiz");
			main.quizStart.remove("Quiz");
			main.QuizTime.remove("Quiz");
			Integer id = main.QuizTimer.remove("Quiz");
			if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		Player p = e.getPlayer();

		if (main.quizPlayer.get("Quiz") == p.getName()) {
			Bukkit.broadcastMessage(main.prx + "§c출제자가 §6강퇴 당하여서 문제가 §c취소 §6되었습니다. §b출제자 §7:: §c" + main.quizPlayer.get("Quiz"));
			if (main.quizMoney.get("Quiz") != null) main.economy.depositPlayer(p.getName(), Integer.parseInt(main.quizMoney.get("Quiz")));
			main.quizAnswer.remove("Quiz");
			main.quizContent.remove("Quiz");
			main.quizMoney.remove("Quiz");
			main.quizPlayer.remove("Quiz");
			main.quizStart.remove("Quiz");
			main.QuizTime.remove("Quiz");
			Integer id = main.QuizTimer.remove("Quiz");
			if (id != null) Bukkit.getServer().getScheduler().cancelTask(id);
		}
	}
}
