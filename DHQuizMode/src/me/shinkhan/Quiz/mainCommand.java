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
			if(commandLabel.equalsIgnoreCase("����") || commandLabel.equalsIgnoreCase("Quiz")) {
				if(args.length == 0) {
					displayHelp(sender);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("����"))
				{
					if (!sender.isOp()) {
						sender.sendMessage(main.aprx + "��c����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (main.quizStart.get("Quiz") == null) {
						sender.sendMessage(main.aprx + "��c��� ���۵Ǿ������� ����� �����մϴ�.");
						return false;
					}
					
					sender.sendMessage(main.czz + "��f" + main.quizPlayer.get("Quiz"));
					sender.sendMessage(main.mz + "��f" + main.quizContent.get("Quiz"));
					sender.sendMessage(main.zd + "��f" + main.quizAnswer.get("Quiz"));
					sender.sendMessage(main.bs + "��f" + main.quizMoney.get("Quiz"));
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("�ʱ�ȭ"))
				{
					if (!sender.isOp()) {
						sender.sendMessage(main.aprx + "��c����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (main.quizStart.get("Quiz") != null) {
						Bukkit.broadcastMessage(main.prx + "��6�����ڰ� ������ ��c��� ��6�Ͽ����ϴ�. ��b������ ��7:: ��c" + main.quizPlayer.get("Quiz"));
						Bukkit.getPlayer(main.quizPlayer.get("Quiz")).sendMessage(main.nprx + "��c�����ڡ�6�� ������ ����Ͽ� ��c" + main.quizMoney.get("Quiz") + "�� ��6�� ȯ�޵Ǿ����ϴ�.");
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
						sender.sendMessage(main.nprx + "��c�ʱ�ȭ��6�� �Ϸ�Ǿ����ϴ�.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("����"))
				{
					if (sender.isOp()) {
						if (args[1].equalsIgnoreCase("����"))
						{
							if (Method.getConfigBoolean("���� ���� ����")) {
								Method.setConfigBoolean("���� ���� ����", false);
								sender.sendMessage(main.nprx + "��6���� ���� �ý��� ��c��� ��6�� �Ϸ��߽��ϴ�.");
								return false;
							}
							
							Method.setConfigBoolean("���� ���� ����", true);
							sender.sendMessage(main.nprx + "��6���� ���� �ý��� ��c���� ��6�� �Ϸ��߽��ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("�ּҺ��"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "��c�ּҺ�� ���� �����ֽñ� �ٶ��ϴ�.");
								return false;
							}
							
							if (Method.getConfigInt("���� ���� �ּ� ���") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "��c������ �ּ� ���� �����ϴ�.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "��6���� ���� ���� ��c�ּ� ��� ��6�� ��c" + args[2] + " ��6������ �����߽��ϴ�.");
							Method.setConfigInt("���� ���� �ּ� ���", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("�ִ���"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "��c�ִ��� ���� �����ֽñ� �ٶ��ϴ�.");
								return false;
							}
							
							if (Method.getConfigInt("���� ���� �ִ� ���") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "��c������ �ִ� ���� �����ϴ�.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "��6���� ���� ���� ��c�ִ� ��� ��6�� ��c" + args[2] + " ��6������ �����߽��ϴ�.");
							Method.setConfigInt("���� ���� �ִ� ���", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("�ð�"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "��c�ð� �� ���� �����ֽñ� �ٶ��ϴ�.");
								return false;
							}
							
							if (Method.getConfigInt("���� ���� �ð� (��)") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "��c������ ���� �ð��� �����ϴ�.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "��6���� ���� ��c���� �ð� ��6�� ��c" + args[2] + " ��6�ʷ� �����߽��ϴ�.");
							Method.setConfigInt("���� ���� �ð� (��)", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("������"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "��c������ �� ���� �����ֽñ� �ٶ��ϴ�.");
								return false;
							}
							
							if (Method.getConfigInt("���� ���� ������ (��)") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "��c������ ���� ������ ���� �����ϴ�.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "��6���� ���� ��c������ �ð� ��6�� ��c" + args[2] + " ��6�ʷ� �����߽��ϴ�.");
							Method.setConfigInt("���� ���� ������ (��)", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("��Ÿ��"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "��c��Ÿ�� �� ���� �����ֽñ� �ٶ��ϴ�.");
								return false;
							}
							
							if (Method.getConfigInt("���� ���� ��Ÿ�� (��)") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "��c������ ���� ��Ÿ�� ���� �����ϴ�.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "��6���� ���� ��c��Ÿ�� �ð� ��6�� ��c" + args[2] + " ��6�ʷ� �����߽��ϴ�.");
							Method.setConfigInt("���� ���� ��Ÿ�� (��)", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("�����Ÿ��"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "��c��� ��Ÿ�� �� ���� �����ֽñ� �ٶ��ϴ�.");
								return false;
							}
							
							if (Method.getConfigInt("���� ���� ��� ��Ÿ�� (��)") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "��c������ ���� ��� ��Ÿ�� ���� �����ϴ�.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "��6���� ���� ��c��� ��Ÿ�� ��6�� ��c" + args[2] + " ��6�ʷ� �����߽��ϴ�.");
							Method.setConfigInt("���� ���� ��� ��Ÿ�� (��)", Integer.parseInt(args[2]));
							return false;
						}
					} else {
						sender.sendMessage(main.aprx + "��c����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("����"))
				{
					if (sender.isOp()) {
						if (args[1].equalsIgnoreCase("����"))
						{
							if (Method.getConfigBoolean("���� ���� ����")) {
								Method.setConfigBoolean("���� ���� ����", false);
								sender.sendMessage(main.nprx + "��6���� ���� �ý��� ��c��� ��6�� �Ϸ��߽��ϴ�.");
								return false;
							}
							
							Method.setConfigBoolean("���� ���� ����", true);
							sender.sendMessage(main.nprx + "��6���� ���� �ý��� ��c���� ��6�� �Ϸ��߽��ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("�ð�"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "��c�ð� �� ���� �����ֽñ� �ٶ��ϴ�.");
								return false;
							}
							
							if (Method.getConfigInt("���� ���� �ð� (��)") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "��c������ ���� �ð� ���� �����ϴ�.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "��6���� ���� ���� ��c�ð� ��6�� ��c" + args[2] + " ��6������ �����߽��ϴ�.");
							Method.setConfigInt("���� ���� �ð� (��)", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("����"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "��c���� ���� ���� �����ֽñ� �ٶ��ϴ�.");
								return false;
							}
							
							if (Method.getConfigInt("���� ���� ���� ����") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "��c������ ���� ���� ���� �����ϴ�.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "��6���� ���� ��c���� ���� ��6���� ��c" + args[2] + " ��6���� �����߽��ϴ�.");
							Method.setConfigInt("���� ���� ���� ����", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("����"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "��c���� ���� �����ֽñ� �ٶ��ϴ�.");
								return false;
							}
							
							if (Method.getConfigInt("���� ���� ����") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "��c������ ���� ���� �����ϴ�.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "��6���� ���� ��c���� ��6���� ��c" + args[2] + " ��6���� �����߽��ϴ�.");
							Method.setConfigInt("���� ���� ����", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("������"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "��c������ �� ���� �����ֽñ� �ٶ��ϴ�.");
								return false;
							}
							
							if (Method.getConfigInt("���� ���� ������ (��)") == Integer.parseInt(args[2])) {
								sender.sendMessage(main.aprx + "��c������ ���� ������ ���� �����ϴ�.");
								return false;
							}
							
							sender.sendMessage(main.nprx + "��6���� ���� ��c������ ��6���� ��c" + args[2] + " ��6���� �����߽��ϴ�.");
							Method.setConfigInt("���� ���� ������ (��)", Integer.parseInt(args[2]));
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("����"))
						{
							if (main.MatAnswer == null) {
								sender.sendMessage(main.aprx + "��c���� ��� �������϶��� ��ɾ� ����� �����մϴ�.");
								return false;
							}
							
							main.MatAnswer.remove("Quiz");
							main.matOne.remove("Quiz");
							main.matTwo.remove("Quiz");
							main.matMark.remove("Quiz");
							Integer id = main.MatTimer.remove("Quiz");
							Bukkit.getServer().getScheduler().cancelTask(id);
							Bukkit.broadcastMessage(main.prx + "��6�����ڰ� ���� ������ ��c��� ��6�Ͽ����ϴ�. ��b���� ��7:: ��c" + main.MatAnswer.get("Quiz"));
							return false;
						}
					} else {
						sender.sendMessage(main.aprx + "��c����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("����"))
				{
					if (sender.isOp()) {
						if (args[1].equalsIgnoreCase("����"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "��c������ ���ܽ�ų �÷��̾ �����ֽʽÿ�.");
								return false;
							}
							
							if (Method.isBlackList(args[2])) {
								sender.sendMessage(main.aprx + "��c�״� �̹� ���� ����Ʈ�� �ֽ��ϴ�.");
								return false;
							}
							
							Method.addBlackList(args[2]);
							sender.sendMessage(main.nprx + "��6����� ��c" + args[2] + "��6 ���� ������ ��c���ܡ�6���׽��ϴ�.");
							Bukkit.getPlayer(args[2]).sendMessage(main.aprx + "��6����� ���� ���� ������ ��c���ܡ�6���Ͽ����ϴ�.");
							return false;
						}
						
						else if (args[1].equalsIgnoreCase("���"))
						{
							if (!(args.length == 3)) {
								sender.sendMessage(main.aprx + "��c������ ����ų �÷��̾ �����ֽʽÿ�.");
								return false;
							}
							
							if (Method.isBlackList(args[2]) == false) {
								sender.sendMessage(main.aprx + "��c�״� ���� ����Ʈ�� �����ϴ�.");
								return false;
							}
							
							Method.subBlackList(args[2]);
							sender.sendMessage(main.nprx + "��6����� ��c" + args[2] + "��6 ���� ������ ��c����6���׽��ϴ�.");
							Bukkit.getPlayer(args[2]).sendMessage(main.nprx + "��6����� ���� ���� ������ ��c����6�Ǿ����ϴ�.");
							return false;
						}
					} else {
						sender.sendMessage(main.aprx + "��c����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
				}
				
				if (sender instanceof Player)
				{
					Player p = (Player) sender;
					
					if (args[0].equalsIgnoreCase("����"))
					{
						if (!(args.length >= 2)) {
							p.sendMessage(main.aprx + "��c���� ������ �Է��� �ֽʽÿ�.");
							return false;
						}
						
						if (Method.getConfigBoolean("���� ���� ����")) {
							p.sendMessage(main.aprx + "��c���� �����̿��� ���ѵǾ� �ֽ��ϴ�.");
							return false;
						}
						
						if (Method.isBlackList(p.getName())) {
							p.sendMessage(main.aprx + "��c���� ������ ���� ������ ���� �� �����Դϴ�.");
							return false;
						}
						
						if (main.QuizCoolTime.get("Quiz") != null) {
							p.sendMessage(main.aprx + "��c���� �ý����� �����ϰ� �ֽ��ϴ�.");
							return false;
						}
						
						if (main.economy.getBalance(p.getName()) < Method.getConfigInt("���� ���� �ּ� ���")) {
							p.sendMessage(main.aprx + "��c�ּ� " + Method.getConfigInt("���� ���� �ּ� ���") + "���� �����ϼž� ������ �����մϴ�.");
							return false;
						}
						
						if (main.quizPlayer.get("Quiz") != null && main.quizPlayer.get("Quiz") != p.getName()) {
							p.sendMessage(main.aprx + "��c�̹� ��e" + main.quizPlayer.get("Quiz") + "��c ���� ������ �����߽��ϴ�.");
							return false;
						}
						
						if (main.quizStart.get("Quiz") != null) {
							p.sendMessage(main.aprx + "��c�̹� �������� ��� �ֽ��ϴ�.");
							return false;
						}
						
						main.quizContent.put("Quiz", Method.getFinalArg(args, 1));
						main.quizPlayer.put("Quiz", p.getName());
						p.sendMessage(main.nprx + "��a[ " + Method.getFinalArg(args, 1) + " ] ��6���� ������ ��c�Ϸ��6�߽��ϴ�.");
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("����"))
					{
						if (!(args.length == 2)) {
							p.sendMessage(main.aprx + "��c������ �����ֽñ� �ٶ��ϴ�.");
							return false;
						}
						
						if (Method.getConfigBoolean("���� ���� ����")) {
							p.sendMessage(main.aprx + "��c���� �����̿��� ���ѵǾ� �ֽ��ϴ�.");
							return false;
						}
						
						if (Method.isBlackList(p.getName())) {
							p.sendMessage(main.aprx + "��c���� ������ ���� ������ ���� �� �����Դϴ�.");
							return false;
						}
						
						if (main.quizContent.get("Quiz") == null) {
							p.sendMessage(main.aprx + "��c���� ���� �������� ���ֽñ� �ٶ��ϴ�.");
							return false;
						}
						
						if (main.quizContent.get("Quiz") == null) {
							p.sendMessage(main.aprx + "��c�̹� ��e" + main.quizPlayer.get("Quiz") + "��c ���� ������ �����߽��ϴ�.");
							return false;
						}
						
						if (main.quizPlayer.get("Quiz") != null && main.quizPlayer.get("Quiz") != p.getName()) {
							p.sendMessage(main.aprx + "��c�̹� ��e" + main.quizPlayer.get("Quiz") + "��c ���� ������ �����߽��ϴ�.");
							return false;
						}
						
						if (main.quizStart.get("Quiz") != null) {
							p.sendMessage(main.aprx + "��c�̹� �������� ��� �ֽ��ϴ�.");
							return false;
						}
						
						if (Method.isStringDouble(args[1]) == false) {
							p.sendMessage(main.aprx + "��c���� ���� ���ڸ� �Է��� �ֽñ� �ٶ��ϴ�.");
							return false;
						}
						
						if (Integer.parseInt(args[1]) < Method.getConfigInt("���� ���� �ּ� ���")) {
							p.sendMessage(main.aprx + "��c�ּ� " + Method.getConfigInt("���� ���� �ּ� ���") + "������ �� ũ�� �Է��� �ֽʽÿ�.");
							return false;
						}
						
						if (Integer.parseInt(args[1]) > Method.getConfigInt("���� ���� �ִ� ���")) {
							p.sendMessage(main.aprx + "��c�ִ� " + Method.getConfigInt("���� ���� �ִ� ���") + "������ �� �۰� �Է��� �ֽʽÿ�.");
							return false;
						}
						
						if (main.quizMoney.get("Quiz") != null) {
							p.sendMessage(main.aprx + "��c����ݾ��� �̹� �����ϼ̽��ϴ�. ������ ����Ͻð� �ٽ� �������ּ���!");
							return false;
						}
						
						main.quizMoney.put("Quiz", args[1]);
						main.economy.withdrawPlayer(p.getName(), Double.valueOf(args[1]).doubleValue());
						p.sendMessage(main.nprx + "��a[ " + args[1] + "�� ] ��6���� ������ ��c�Ϸ��6�ϼ̽��ϴ�.");
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("����"))
					{
						if (Method.getConfigBoolean("���� ���� ����")) {
							p.sendMessage(main.aprx + "��c���� �����̿��� ���ѵǾ� �ֽ��ϴ�.");
							return false;
						}
						
						if (Method.isBlackList(p.getName())) {
							p.sendMessage(main.aprx + "��c���� ������ ���� ������ ���� �� �����Դϴ�.");
							return false;
						}
						
						if (main.quizStart.get("Quiz") != null) {
							p.sendMessage(main.aprx + "��c�̹� �������� ��� �ֽ��ϴ�.");
							return false;
						}
						
						if (main.quizContent.get("Quiz") == null) {
							p.sendMessage(main.aprx + "��c���� ���� �������� ���ֽñ� �ٶ��ϴ�.");
							return false;
						}
						
						if (main.quizContent.get("Quiz") == null) {
							p.sendMessage(main.aprx + "��c�̹� ��e" + main.quizPlayer.get("Quiz") + "��c ���� ������ �����߽��ϴ�.");
							return false;
						}
						
						if (main.quizPlayer.get("Quiz") != null && main.quizPlayer.get("Quiz") != p.getName()) {
							p.sendMessage(main.aprx + "��c�̹� ��e" + main.quizPlayer.get("Quiz") + "��c ���� ������ �����߽��ϴ�.");
							return false;
						}
						
						if (args.length > 2) {
							p.sendMessage(main.aprx + "��c���信 ����� �������� �ʽ��ϴ�. �ٽ� ���� ���ּ���!");
							return false;
						}
						
						if (!(args.length == 2)) {
							p.sendMessage(main.aprx + "��c������ �����ֽñ� �ٶ��ϴ�.");
							return false;
						}
						
						if (main.quizMoney.get("Quiz") == null) {
							p.sendMessage(main.aprx + "��c���� ������� ������ �ֽñ� �ٶ��ϴ�.");
							return false;
						}
						
						main.quizAnswer.put("Quiz", args[1]);
						main.quizStart.put("Quiz", "true");
						p.sendMessage(main.nprx + "��a[ " + args[1] + " ] ��6���� ������ ��c�Ϸ��6�ϼ̽��ϴ�.");
						Bukkit.broadcastMessage(main.prx + main.quizContent.get("Quiz"));
						Bukkit.broadcastMessage(main.czz + main.quizPlayer.get("Quiz") + "  " + main.bs + main.quizMoney.get("Quiz") + "��");

						main.QuizTimer.put("Quiz", new BukkitRunnable()
						{
							int CoolDown = Method.getConfigInt("���� ���� �ð� (��)");
							@Override
							public void run()
							{
								if (CoolDown <= 0) {
									Bukkit.broadcastMessage(main.prx + "��6���ѽð��� ��c�����6�Ǿ� ��c���� �����6�� �����˴ϴ�.");
									Bukkit.broadcastMessage(main.czz + "��f" + main.quizPlayer.get("Quiz"));
									Bukkit.broadcastMessage(main.mz + "��f" + main.quizContent.get("Quiz"));
									Bukkit.broadcastMessage(main.zd + "��f" + main.quizAnswer.get("Quiz"));
									Bukkit.broadcastMessage(main.bs + "��f" + main.quizMoney.get("Quiz") + "��");
									main.economy.depositPlayer(p.getName(), Integer.parseInt(main.quizMoney.get("Quiz")));
									p.sendMessage(main.aprx + "��c" + main.quizMoney.get("Quiz") + " ��6���� ��cȯ�ޡ�6�Ǿ����ϴ�.");
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
									}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHQuiz"), Method.getConfigInt("���� ���� ��Ÿ�� (��)") * 20, 0).getTaskId());
						
									Integer id = main.QuizTimer.remove("Quiz");
									Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								} else {
									CoolDown -= Method.getConfigInt("���� ���� ������ (��)");
									Bukkit.broadcastMessage(main.prx + "��6���ѽð� ��c" + CoolDown + "�� ��6���ҽ��ϴ�.");
									Bukkit.broadcastMessage(main.prx + main.quizContent.get("Quiz"));
									Bukkit.broadcastMessage(main.czz + main.quizPlayer.get("Quiz") + "  " + main.bs + main.quizMoney.get("Quiz") + "��");
									main.QuizTime.put(p.getName(), CoolDown);
								}
							}
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHQuiz"),
						  Method.getConfigInt("���� ���� ������ (��)") * 20, Method.getConfigInt("���� ���� ������ (��)") * 20).getTaskId());
						return false;
					}
					
					else if (args[0].equalsIgnoreCase("���"))
					{
						if (main.quizStart.get("Quiz") == null) {
							sender.sendMessage(main.aprx + "��c��� ���۵Ǿ������� ����� �����մϴ�.");
							return false;
						}
						
						if (main.QuizPut.get("Quiz") != null) {
							sender.sendMessage(main.aprx + "��c" + Method.getConfigInt("���� ���� ��� ��Ÿ�� (��)") + "�� �������� ����� �����մϴ�.");
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
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHQuiz"), Method.getConfigInt("���� ���� ��� ��Ÿ�� (��)") * 20, 0).getTaskId());
					}
					
					else if (args[0].equalsIgnoreCase("���"))
					{
						if (main.quizStart.get("Quiz") == null) {
							sender.sendMessage(main.aprx + "��c��� ���۵Ǿ������� ����� �����մϴ�.");
							return false;
						}
						
						if (main.quizPlayer.get("Quiz") != p.getName()) {
							sender.sendMessage(main.aprx + "��c����� �� ������ �����ڰ� �ƴմϴ�.");
							return false;
						}
						
						Bukkit.broadcastMessage(main.prx + "��6������ ��c��� ��6�Ǿ����ϴ�. ��b������ ��7:: ��c" + main.quizPlayer.get("Quiz"));
						p.sendMessage(main.nprx + "��c" + main.quizMoney.get("Quiz") + "�� ��6�� ȯ�޵Ǿ����ϴ�.");
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
						}.runTaskTimer(Bukkit.getPluginManager().getPlugin("DHQuiz"), Method.getConfigInt("���� ���� ��Ÿ�� (��)") * 20, 0).getTaskId());
						return false;
					}
				} else {
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + 
									   ChatColor.WHITE + "]" + ChatColor.RED + " �ֿܼ��� ������ �Ұ��� �մϴ�.");
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
		sender.sendMessage("��a���7��                                          ��a���7��");
		sender.sendMessage("��7���c�� ��e----- ��6DHQuiz ��e-- ��6���� ��e----- ��7���c��");
		sender.sendMessage("��6/����, /Quiz ��f- ���� ��ɾ ���ϴ�.");
		sender.sendMessage("��6/���� ���� <����> ��f- ���� ������ �����մϴ�.");
		sender.sendMessage("��6/���� ���� <�ݾ�> ��f- ��� �������� �ִ� ���� �����մϴ�.");
		sender.sendMessage("��6/���� ���� <����> ��f- ������ ������ �����մϴ�.");
		sender.sendMessage("��6/���� ��� ��f- ������ ��ο��� �����ݴϴ�.");
		sender.sendMessage("��6/���� ��� ��f- ������ ����մϴ�.");
		if (sender.isOp()) {
			sender.sendMessage("");
			sender.sendMessage("��b/���� ���� ��f- ���� ���� �� ����, ����, ��ǰ�� ���ϴ�.");
			sender.sendMessage("��b/���� �ʱ�ȭ ��f- ��� ������ �ʱ�ȭ�մϴ�.");
			sender.sendMessage("��b/���� ���� ���� ��f- ���� ���� �ý��� �̿��� ���/�����մϴ�.");
			sender.sendMessage("��b/���� ���� �ּҺ�� <��> ��f- ���� ���� ���� �ּ� ����� �����մϴ�.");
			sender.sendMessage("��b/���� ���� �ִ��� <��> ��f- ���� ���� ���� �ִ� ����� �����մϴ�.");
			sender.sendMessage("��b/���� ���� �ð� <��> ��f- ���� ������ ���� �ð��� �����մϴ�.");
			sender.sendMessage("��b/���� ���� ������ <��> ��f- ���� ��� ���ʴٸ� ������ ��� ������ �����մϴ�.");
			sender.sendMessage("��b/���� ���� ��Ÿ�� <��> ��f- ��� ��ġ�� ���ʵ��� ������ ���� ������ �����մϴ�.");
			sender.sendMessage("��b/���� ���� �����Ÿ�� <��> ��f- ���� ��� ��ɾ ���� �������� ������� �����մϴ�.");
			sender.sendMessage("��b/���� ���� �ð� <��> ��f- ���� ���� �ý��� �����̸� �����մϴ�.");
			sender.sendMessage("��b/���� ���� ���� ��f- ���� ���� �ý��� �̿��� ���/�����մϴ�.");
			sender.sendMessage("��b/���� ���� ���� <��> ��f- ���� ������ ���� ���� �����մϴ�.");
			sender.sendMessage("��b/���� ���� ���� <��> ��f- ���� ������ ������ �����մϴ�.");
			sender.sendMessage("��b/���� ���� ������ <��> ��f- ���� ������ ���� �ð��� �����մϴ�.");
			sender.sendMessage("��b/���� ���� ���� ��f- ���� ��� ������ �����ŵ�ϴ�.");
			sender.sendMessage("��b/���� ���� <����/���> <�г���> ��f- �÷��̾��� ���� ������ �����մϴ�.");
		}
		sender.sendMessage("");
		sender.sendMessage("��c * ���信 ��c��l���� ��c�� �������� �ʽ��ϴ�.");
		sender.sendMessage("��c * ���� �ݾ��� ������ ��c��l���� ��c���� �����˴ϴ�.");
		sender.sendMessage("��c * ���� ���� �� ��c��l/���� ��� ��c�� �Է��Ͻ� �� ���� ��ο��� ��µ˴ϴ�.");
		sender.sendMessage("��c * ��c��l���� - > ���� - > ���� ��c������ �����ϼž� �۵��մϴ�.");
		sender.sendMessage("��c * �����ڴ� ��e��lshinkhan ��c�̰�, ���̵��� ��e��l__Vector��c�� �̽ʴϴ�.");
	}
}
