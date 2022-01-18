package me.shinkhan.Quiz;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;

	public mainCommand(main instance)
	{
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if(commandLabel.equalsIgnoreCase("퀴즈") || commandLabel.equalsIgnoreCase("Quiz")) {
				if(args.length == 0) {
					displayHelp(sender);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("보기"))
				{
					if (!sender.isOp()) {
						sender.sendMessage(main.aprx + "§c당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (main.quizStart.get("Quiz") == null) {
						sender.sendMessage(main.aprx + "§c퀴즈가 시작되었을때만 사용이 가능합니다.");
						return false;
					}
					
					sender.sendMessage(main.czz + "§f" + main.quizPlayer.get("Quiz"));
					sender.sendMessage(main.mz + "§f" + main.quizContent.get("Quiz"));
					sender.sendMessage(main.zd + "§f" + main.quizAnswer.get("Quiz"));
					sender.sendMessage(main.bs + "§f" + main.quizMoney.get("Quiz"));
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("초기화"))
				{
					if (!sender.isOp()) {
						sender.sendMessage(main.aprx + "§c당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
					
					if (main.quizStart.get("Quiz") != null) {
						Bukkit.broadcastMessage(main.prx + "§6관리자가 문제를 §c취소 §6하였습니다. §b출제자 §7:: §c" + main.quizPlayer.get("Quiz"));
						Bukkit.getPlayer(main.quizPlayer.get("Quiz")).sendMessage(main.nprx + "§c관리자§6가 문제를 취소하여 §c" + main.quizMoney.get("Quiz") + "원 §6이 환급되었습니다.");
						main.economy.depositPlayer(main.quizPlayer.get("Quiz"), Integer.parseInt(main.quizMoney.get("Quiz")));
						main.quizAnswer.remove("Quiz");
						main.quizContent.remove("Quiz");
						main.quizMoney.remove("Quiz");
						main.quizPlayer.remove("Quiz");
						main.quizStart.remove("Quiz");
						main.QuizTime.remove("Quiz");
						main.QuizCoolTime.put("Quiz", "true");
						Integer id = main.QuizTimer.remove("Quiz");
						Bukkit.getServer().getScheduler().cancelTask(id);
						return false;
					} else {
						main.quizAnswer.remove("Quiz");
						main.quizContent.remove("Quiz");
						main.quizMoney.remove("Quiz");
						main.quizPlayer.remove("Quiz");
						main.quizStart.remove("Quiz");
						main.QuizTime.remove("Quiz");
						main.QuizCoolTime.put("Quiz", "true");
						sender.sendMessage(main.nprx + "§c초기화§6가 완료되었습니다.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("유저"))
				{
					if (sender.isOp()) {
						if (args[1].equalsIgnoreCase("차단"))
						{
							if (Method.getConfigBoolean("유저 퀴즈 차단")) {
								Method.setConfigBoolean("유저 퀴즈 차단", false);
								sender.sendMessage(main.nprx + "§6유저 퀴즈 시스템 §c허용 §6을 완료했습니다.");
								return false;
							}
							
							Method.setConfigBoolean("유저 퀴즈 차단", true);
							sender.sendMessage(main.nprx + "§6유저 퀴즈 시스템 §c차단 §6을 완료했습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("최소비용"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "§c최소비용 값을 적어주시기 바랍니다.");
								return false;
							}
							
							if (Method.getConfigInt("유저 퀴즈 최소 비용") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "§c기존의 최소 비용과 같습니다.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "§6유저 퀴즈 출제 §c최소 비용 §6을 §c" + args[2] + " §6원으로 설정했습니다.");
							Method.setConfigInt("유저 퀴즈 최소 비용", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("최대비용"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "§c최대비용 값을 적어주시기 바랍니다.");
								return false;
							}
							
							if (Method.getConfigInt("유저 퀴즈 최대 비용") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "§c기존의 최대 비용과 같습니다.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "§6유저 퀴즈 출제 §c최대 비용 §6을 §c" + args[2] + " §6원으로 설정했습니다.");
							Method.setConfigInt("유저 퀴즈 최대 비용", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("시간"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "§c시간 초 값을 적어주시기 바랍니다.");
								return false;
							}
							
							if (Method.getConfigInt("유저 퀴즈 시간 (초)") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "§c기존의 퀴즈 시간과 같습니다.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "§6유저 퀴즈 §c마감 시간 §6을 §c" + args[2] + " §6초로 설정했습니다.");
							Method.setConfigInt("유저 퀴즈 시간 (초)", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("딜레이"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "§c딜레이 초 값을 적어주시기 바랍니다.");
								return false;
							}
							
							if (Method.getConfigInt("유저 퀴즈 딜레이 (초)") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "§c기존의 퀴즈 딜레이 값과 같습니다.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "§6유저 퀴즈 §c딜레이 시간 §6을 §c" + args[2] + " §6초로 설정했습니다.");
							Method.setConfigInt("유저 퀴즈 딜레이 (초)", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("쿨타임"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "§c쿨타임 초 값을 적어주시기 바랍니다.");
								return false;
							}
							
							if (Method.getConfigInt("유저 퀴즈 쿨타임 (초)") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "§c기존의 퀴즈 쿨타임 값과 같습니다.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "§6유저 퀴즈 §c쿨타임 시간 §6을 §c" + args[2] + " §6초로 설정했습니다.");
							Method.setConfigInt("유저 퀴즈 쿨타임 (초)", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("출력쿨타임"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "§c출력 쿨타임 초 값을 적어주시기 바랍니다.");
								return false;
							}
							
							if (Method.getConfigInt("유저 퀴즈 출력 쿨타임 (초)") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "§c기존의 퀴즈 출력 쿨타임 값과 같습니다.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "§6유저 퀴즈 §c출력 쿨타임 §6을 §c" + args[2] + " §6초로 설정했습니다.");
							Method.setConfigInt("유저 퀴즈 출력 쿨타임 (초)", Integer.parseInt(args[2]));
							return false;
						}
					} else {
						sender.sendMessage(main.aprx + "§c당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("수학"))
				{
					if (sender.isOp()) {
						if (args[1].equalsIgnoreCase("차단"))
						{
							if (Method.getConfigBoolean("수학 퀴즈 차단")) {
								Method.setConfigBoolean("수학 퀴즈 차단", false);
								sender.sendMessage(main.nprx + "§6수학 퀴즈 시스템 §c허용 §6을 완료했습니다.");
								return false;
							}
							
							Method.setConfigBoolean("수학 퀴즈 차단", true);
							sender.sendMessage(main.nprx + "§6수학 퀴즈 시스템 §c차단 §6을 완료했습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("시간"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "§c시간 분 값을 적어주시기 바랍니다.");
								return false;
							}
							
							if (Method.getConfigInt("수학 퀴즈 시간 (분)") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "§c기존의 퀴즈 시간 값과 같습니다.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "§6수학 퀴즈 출제 §c시간 §6을 §c" + args[2] + " §6분으로 설정했습니다.");
							Method.setConfigInt("수학 퀴즈 시간 (분)", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("랜덤"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "§c랜덤 숫자 값을 적어주시기 바랍니다.");
								return false;
							}
							
							if (Method.getConfigInt("수학 퀴즈 랜덤 숫자") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "§c기존의 랜덤 숫자 값과 같습니다.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "§6수학 퀴즈 §c랜덤 숫자 §6값을 §c" + args[2] + " §6으로 설정했습니다.");
							Method.setConfigInt("수학 퀴즈 랜덤 숫자", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("보상"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "§c보상 값을 적어주시기 바랍니다.");
								return false;
							}
							
							if (Method.getConfigInt("수학 퀴즈 보상") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "§c기존의 보상 값과 같습니다.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "§6수학 퀴즈 §c보상 §6값을 §c" + args[2] + " §6으로 설정했습니다.");
							Method.setConfigInt("수학 퀴즈 보상", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("딜레이"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "§c딜레이 초 값을 적어주시기 바랍니다.");
								return false;
							}
							
							if (Method.getConfigInt("수학 퀴즈 딜레이 (초)") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "§c기존의 퀴즈 딜레이 값과 같습니다.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "§6수학 퀴즈 §c딜레이 §6값을 §c" + args[2] + " §6으로 설정했습니다.");
							Method.setConfigInt("수학 퀴즈 딜레이 (초)", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("종료"))
						{
							if (main.MatAnswer == null) {
								sender.sendMessage(main.aprx + "§c수학 퀴즈가 진행중일때만 명령어 사용이 가능합니다.");
								return false;
							}
							
							main.MatAnswer.remove("Quiz");
							main.matOne.remove("Quiz");
							main.matTwo.remove("Quiz");
							main.matMark.remove("Quiz");
							Integer id = main.MatTimer.remove("Quiz");
							Bukkit.getServer().getScheduler().cancelTask(id);
							Bukkit.broadcastMessage(main.prx + "§6관리자가 수학 문제를 §c취소 §6하였습니다. §b정답 §7:: §c" + main.MatAnswer.get("Quiz"));
							return false;
						}
					} else {
						sender.sendMessage(main.aprx + "§c당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("권한"))
				{
					if (sender.isOp()) {
						if (args[1].equalsIgnoreCase("차단"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "§c권한을 차단시킬 플레이어를 적어주십시오.");
								return false;
							}
							
							if (Method.isBlackList(args[2])) {
								sender.sendMessage(main.aprx + "§c그는 이미 차단 리스트에 있습니다.");
								return false;
							}
							
							Method.addBlackList(args[2]);
							sender.sendMessage(main.nprx + "§6당신은 §c" + args[2] + "§6 님의 권한을 §c차단§6시켰습니다.");
							Bukkit.getPlayer(args[2]).sendMessage(main.aprx + "§6당신은 퀴즈 출제 권한을 §c차단§6당하였습니다.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("허용"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "§c권한을 허용시킬 플레이어를 적어주십시오.");
								return false;
							}
							
							if (Method.isBlackList(args[2]) == false) {
								sender.sendMessage(main.aprx + "§c그는 차단 리스트에 없습니다.");
								return false;
							}
							
							Method.subBlackList(args[2]);
							sender.sendMessage(main.nprx + "§6당신은 §c" + args[2] + "§6 님의 권한을 §c허용§6시켰습니다.");
							Bukkit.getPlayer(args[2]).sendMessage(main.nprx + "§6당신은 퀴즈 출제 권한을 §c허용§6되었습니다.");
							return false;
						}
					} else {
						sender.sendMessage(main.aprx + "§c당신은 명령어를 실행할 권한이 없습니다.");
						return false;
					}
				}
				
				if (sender instanceof Player)
				{
					Player p = (Player) sender;
					
					if (args[0].equalsIgnoreCase("출제"))
					{
						if (!(args.length >= 2)) {
							p.sendMessage(main.aprx + "§c퀴즈 내용을 입력해 주십시오.");
							return false;
						}
						
						if (Method.getConfigBoolean("유저 퀴즈 차단")) {
							p.sendMessage(main.aprx + "§c현재 게임이용이 제한되어 있습니다.");
							return false;
						}
						
						if (Method.isBlackList(p.getName())) {
							p.sendMessage(main.aprx + "§c현재 본인의 출제 권한이 차단 된 상태입니다.");
							return false;
						}
						
						if (main.QuizCoolTime.get("Quiz") != null) {
							p.sendMessage(main.aprx + "§c현재 시스템을 정리하고 있습니다.");
							return false;
						}
						
						if (main.economy.getBalance(p.getName()) < Method.getConfigInt("유저 퀴즈 최소 비용")) {
							p.sendMessage(main.aprx + "§c최소 " + Method.getConfigInt("유저 퀴즈 최소 비용") + "원을 보유하셔야 출제가 가능합니다.");
							return false;
						}
						
						if (main.quizPlayer.get("Quiz") != null && main.quizPlayer.get("Quiz") != p.getName()) {
							p.sendMessage(main.aprx + "§c이미 §e" + main.quizPlayer.get("Quiz") + "§c 님이 문제를 출제했습니다.");
							return false;
						}
						
						if (main.quizStart.get("Quiz") != null) {
							p.sendMessage(main.aprx + "§c이미 진행중인 퀴즈가 있습니다.");
							return false;
						}
						
						main.quizContent.put("Quiz", Method.getFinalArg(args, 1));
						main.quizPlayer.put("Quiz", p.getName());
						p.sendMessage(main.nprx + "§a[ " + Method.getFinalArg(args, 1) + " ] §6문제 설정을 §c완료§6했습니다.");
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("보상"))
					{
						if (!(args.length == 2)) {
							p.sendMessage(main.aprx + "§c보상을 적어주시기 바랍니다.");
							return false;
						}
						
						if (Method.getConfigBoolean("유저 퀴즈 차단")) {
							p.sendMessage(main.aprx + "§c현재 게임이용이 제한되어 있습니다.");
							return false;
						}
						
						if (Method.isBlackList(p.getName())) {
							p.sendMessage(main.aprx + "§c현재 본인의 출제 권한이 차단 된 상태입니다.");
							return false;
						}
						
						if (main.quizContent.get("Quiz") == null) {
							p.sendMessage(main.aprx + "§c퀴즈 문제 출제부터 해주시기 바랍니다.");
							return false;
						}
						
						if (main.quizContent.get("Quiz") == null) {
							p.sendMessage(main.aprx + "§c이미 §e" + main.quizPlayer.get("Quiz") + "§c 님이 문제를 출제했습니다.");
							return false;
						}
						
						if (main.quizPlayer.get("Quiz") != null && main.quizPlayer.get("Quiz") != p.getName()) {
							p.sendMessage(main.aprx + "§c이미 §e" + main.quizPlayer.get("Quiz") + "§c 님이 문제를 출제했습니다.");
							return false;
						}
						
						if (main.quizStart.get("Quiz") != null) {
							p.sendMessage(main.aprx + "§c이미 진행중인 퀴즈가 있습니다.");
							return false;
						}
						
						if (Method.isStringDouble(args[1]) == false) {
							p.sendMessage(main.aprx + "§c보상 값에 숫자만 입력해 주시기 바랍니다.");
							return false;
						}
						
						if (Integer.parseInt(args[1]) < Method.getConfigInt("유저 퀴즈 최소 비용")) {
							p.sendMessage(main.aprx + "§c최소 " + Method.getConfigInt("유저 퀴즈 최소 비용") + "원보다 더 크게 입력해 주십시오.");
							return false;
						}
						
						if (Integer.parseInt(args[1]) > Method.getConfigInt("유저 퀴즈 최대 비용")) {
							p.sendMessage(main.aprx + "§c최대 " + Method.getConfigInt("유저 퀴즈 최대 비용") + "원보다 더 작게 입력해 주십시오.");
							return false;
						}
						
						if (main.quizMoney.get("Quiz") != null) {
							p.sendMessage(main.aprx + "§c보상금액을 이미 설정하셨습니다. 문제를 취소하시고 다시 출제해주세요!");
							return false;
						}
						
						main.quizMoney.put("Quiz", args[1]);
						main.economy.withdrawPlayer(p.getName(), Double.valueOf(args[1]).doubleValue());
						p.sendMessage(main.nprx + "§a[ " + args[1] + "원 ] §6보상 설정을 §c완료§6하셨습니다.");
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("정답"))
					{
						if (Method.getConfigBoolean("유저 퀴즈 차단")) {
							p.sendMessage(main.aprx + "§c현재 게임이용이 제한되어 있습니다.");
							return false;
						}
						
						if (Method.isBlackList(p.getName())) {
							p.sendMessage(main.aprx + "§c현재 본인의 출제 권한이 차단 된 상태입니다.");
							return false;
						}
						
						if (main.quizStart.get("Quiz") != null) {
							p.sendMessage(main.aprx + "§c이미 진행중인 퀴즈가 있습니다.");
							return false;
						}
						
						if (main.quizContent.get("Quiz") == null) {
							p.sendMessage(main.aprx + "§c퀴즈 문제 출제부터 해주시기 바랍니다.");
							return false;
						}
						
						if (main.quizContent.get("Quiz") == null) {
							p.sendMessage(main.aprx + "§c이미 §e" + main.quizPlayer.get("Quiz") + "§c 님이 문제를 출제했습니다.");
							return false;
						}
						
						if (main.quizPlayer.get("Quiz") != null && main.quizPlayer.get("Quiz") != p.getName()) {
							p.sendMessage(main.aprx + "§c이미 §e" + main.quizPlayer.get("Quiz") + "§c 님이 문제를 출제했습니다.");
							return false;
						}
						
						if (args.length > 2) {
							p.sendMessage(main.aprx + "§c정답에 띄어쓰기는 가능하지 않습니다. 다시 설정 해주세요!");
							return false;
						}
						
						if (!(args.length == 2)) {
							p.sendMessage(main.aprx + "§c정답을 적어주시기 바랍니다.");
							return false;
						}
						
						if (main.quizMoney.get("Quiz") == null) {
							p.sendMessage(main.aprx + "§c퀴즈 보상부터 설정해 주시기 바랍니다.");
							return false;
						}
						
						main.quizAnswer.put("Quiz", args[1]);
						main.quizStart.put("Quiz", "true");
						p.sendMessage(main.nprx + "§a[ " + args[1] + " ] §6정답 설정을 §c완료§6하셨습니다.");
						Bukkit.broadcastMessage(main.prx + main.quizContent.get("Quiz"));
						Bukkit.broadcastMessage(main.czz + main.quizPlayer.get("Quiz") + "  " + main.bs + main.quizMoney.get("Quiz") + "원");

						main.QuizTimer.put("Quiz", new BukkitRunnable()
						{
							int CoolDown = Method.getConfigInt("유저 퀴즈 시간 (초)");
							@Override
							public void run()
							{
								if (CoolDown <= 0) {
									Bukkit.broadcastMessage(main.prx + "§6제한시간이 §c종료§6되어 §c퀴즈 정답§6이 공개됩니다.");
									Bukkit.broadcastMessage(main.czz + "§f" + main.quizPlayer.get("Quiz"));
									Bukkit.broadcastMessage(main.mz + "§f" + main.quizContent.get("Quiz"));
									Bukkit.broadcastMessage(main.zd + "§f" + main.quizAnswer.get("Quiz"));
									Bukkit.broadcastMessage(main.bs + "§f" + main.quizMoney.get("Quiz") + "원");
									main.economy.depositPlayer(p.getName(), Integer.parseInt(main.quizMoney.get("Quiz")));
									p.sendMessage(main.aprx + "§c" + main.quizMoney.get("Quiz") + " §6원이 §c환급§6되었습니다.");
									main.quizAnswer.remove("Quiz");
									main.quizContent.remove("Quiz");
									main.quizMoney.remove("Quiz");
									main.quizPlayer.remove("Quiz");
									main.quizStart.remove("Quiz");
									main.QuizTime.remove("Quiz");
									main.QuizCoolTime.put("Quiz", "true");
									
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
						
									Integer id = main.QuizTimer.remove("Quiz");
									Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								} else {
									CoolDown -= Method.getConfigInt("유저 퀴즈 딜레이 (초)");
									Bukkit.broadcastMessage(main.prx + "§6제한시간 §c" + CoolDown + "초 §6남았습니다.");
									Bukkit.broadcastMessage(main.prx + main.quizContent.get("Quiz"));
									Bukkit.broadcastMessage(main.czz + main.quizPlayer.get("Quiz") + "  " + main.bs + main.quizMoney.get("Quiz") + "원");
									main.QuizTime.put(p.getName(), CoolDown);
								}
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHQuiz"),
						  Method.getConfigInt("유저 퀴즈 딜레이 (초)") * 20, Method.getConfigInt("유저 퀴즈 딜레이 (초)") * 20).getTaskId());
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("출력"))
					{
						if (main.quizStart.get("Quiz") == null) {
							sender.sendMessage(main.aprx + "§c퀴즈가 시작되었을때만 사용이 가능합니다.");
							return false;
						}
						
						if (main.QuizPut.get("Quiz") != null) {
							sender.sendMessage(main.aprx + "§c" + Method.getConfigInt("유저 퀴즈 출력 쿨타임 (초)") + "초 간격으로 사용이 가능합니다.");
							return false;
						}
						
						main.QuizPut.put("Quiz", "true");
						main.QuizPutTimer.put("Quiz", new BukkitRunnable()
						{
							@Override
							public void run()
							{
								main.QuizPut.remove("Quiz");
								Integer id = main.QuizPutTimer.remove("Quiz");
								Bukkit.getServer().getScheduler().cancelTask(id);
								return;
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHQuiz"), Method.getConfigInt("유저 퀴즈 출력 쿨타임 (초)") * 20, 0).getTaskId());
					}
					
					else if (args[0].equalsIgnoreCase("취소"))
					{
						if (main.quizStart.get("Quiz") == null) {
							sender.sendMessage(main.aprx + "§c퀴즈가 시작되었을때만 사용이 가능합니다.");
							return false;
						}
						
						if (main.quizPlayer.get("Quiz") != p.getName()) {
							sender.sendMessage(main.aprx + "§c당신은 이 퀴즈의 출제자가 아닙니다.");
							return false;
						}
						
						Bukkit.broadcastMessage(main.prx + "§6문제가 §c취소 §6되었습니다. §b출제자 §7:: §c" + main.quizPlayer.get("Quiz"));
						p.sendMessage(main.nprx + "§c" + main.quizMoney.get("Quiz") + "원 §6이 환급되었습니다.");
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
						return false;
					}
				} else {
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + 
									   ChatColor.WHITE + "]" + ChatColor.RED + " 콘솔에선 실행이 불가능 합니다.");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			displayHelp(sender);
			return false;
		} return false;
	}
	
	private void displayHelp(CommandSender sender)
	{	
		sender.sendMessage("§a■§7■                                          §a■§7■");
		sender.sendMessage("§7■§c■ §e----- §6DHQuiz §e-- §6도움말 §e----- §7■§c■");
		sender.sendMessage("§6/퀴즈, /Quiz §f- 퀴즈 명령어를 봅니다.");
		sender.sendMessage("§6/퀴즈 출제 <내용> §f- 퀴즈 문제를 출제합니다.");
		sender.sendMessage("§6/퀴즈 보상 <금액> §f- 퀴즈를 맞췄을때 주는 돈을 설정합니다.");
		sender.sendMessage("§6/퀴즈 정답 <내용> §f- 퀴즈의 정답을 설정합니다.");
		sender.sendMessage("§6/퀴즈 출력 §f- 문제를 모두에게 보여줍니다.");
		sender.sendMessage("§6/퀴즈 취소 §f- 문제를 취소합니다.");
		if (sender.isOp()) {
			sender.sendMessage("");
			sender.sendMessage("§b/퀴즈 보기 §f- 현재 설정 된 문제, 정답, 상품을 봅니다.");
			sender.sendMessage("§b/퀴즈 초기화 §f- 모든 설정을 초기화합니다.");
			sender.sendMessage("§b/퀴즈 유저 차단 §f- 유저 퀴즈 시스템 이용을 허용/차단합니다.");
			sender.sendMessage("§b/퀴즈 유저 최소비용 <값> §f- 유저 퀴즈 출제 최소 비용을 설정합니다.");
			sender.sendMessage("§b/퀴즈 유저 최대비용 <값> §f- 유저 퀴즈 출제 최대 비용을 설정합니다.");
			sender.sendMessage("§b/퀴즈 유저 시간 <초> §f- 유저 퀴즈의 마감 시간을 설정합니다.");
			sender.sendMessage("§b/퀴즈 유저 딜레이 <초> §f- 유저 퀴즈가 몇초다마 문제를 띄울 것인지 설정합니다.");
			sender.sendMessage("§b/퀴즈 유저 쿨타임 <초> §f- 퀴즈를 마치고 몇초동안 출제를 막을 것인지 설정합니다.");
			sender.sendMessage("§b/퀴즈 유저 출력쿨타임 <초> §f- 퀴즈 출력 명령어를 몇초 간격으로 사용할지 설정합니다.");
			sender.sendMessage("§b/퀴즈 수학 시간 <분> §f- 수학 퀴즈 시스템 딜레이를 설정합니다.");
			sender.sendMessage("§b/퀴즈 수학 차단 §f- 수학 퀴즈 시스템 이용을 허용/차단합니다.");
			sender.sendMessage("§b/퀴즈 수학 랜덤 <값> §f- 수학 퀴즈의 숫자 값을 설정합니다.");
			sender.sendMessage("§b/퀴즈 수학 보상 <값> §f- 수학 퀴즈의 보상을 설정합니다.");
			sender.sendMessage("§b/퀴즈 수학 딜레이 <초> §f- 수학 퀴즈의 마감 시간을 설정합니다.");
			sender.sendMessage("§b/퀴즈 수학 종료 §f- 수학 퀴즈를 강제로 종료시킵니다.");
			sender.sendMessage("§b/퀴즈 권한 <차단/허용> <닉네임> §f- 플레이어의 출제 권한을 설정합니다.");
		}
		sender.sendMessage("");
		sender.sendMessage("§c * 정답에 §c§l띄어쓰기 §c는 가능하지 않습니다.");
		sender.sendMessage("§c * 보상 금액은 본인의 §c§l통장 §c에서 차감됩니다.");
		sender.sendMessage("§c * 문제 설정 후 §c§l/퀴즈 출력 §c을 입력하실 때 마다 모두에게 출력됩니다.");
		sender.sendMessage("§c * §c§l출제 - > 보상 - > 정답 §c순으로 설정하셔야 작동합니다.");
		sender.sendMessage("§c * 제작자는 §e§lshinkhan §c이고, 아이디어는 §e§l__Vector§c님 이십니다.");
	}
}
