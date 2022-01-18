package me.espoo.mg;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.mg.Data.GameData;
import me.espoo.mg.Game.Anvil_Game;
import me.espoo.mg.Game.Bomb_Game;
import me.espoo.mg.Game.Climb_Game;
import me.espoo.mg.Game.OneKnife_Game;
import me.espoo.mg.Game.Running_Game;
import me.espoo.mg.Game.SpeedShot_Game;
import me.espoo.mg.Game.Spleef_Game;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;

	public mainCommand(main instance)
	{
		this.plugin = instance;
	}
	
	@SuppressWarnings({ "resource", "static-access" })
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		try {
			if(commandLabel.equalsIgnoreCase("����") || commandLabel.equalsIgnoreCase("���ھ�") 
			|| commandLabel.equalsIgnoreCase("score") || commandLabel.equalsIgnoreCase("wjatn") || commandLabel.equalsIgnoreCase("tmzhdj")) {
				if(args.length == 0) {
					displayHelp2(sender);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("����") && args.length == 2)
				{
					File f = new File("plugins/PlayMiniGame/Player/" + args[1] + ".yml");
					if (!f.exists()) {
						sender.sendMessage("�ش� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					Player argp = Bukkit.getPlayer(args[1]);
					sender.sendMessage("��e * " + args[1] + " ��6���� ��c���ھ� ��6����");
					sender.sendMessage("��e ������ ��c���� ��7:: ��f" + Method.getInfoInt(argp, "����"));
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("����"))
				{
					int one = Method.getRankingI("one");
					int two = Method.getRankingI("two");
					int three = Method.getRankingI("three");
					int four = Method.getRankingI("four");
					int five = Method.getRankingI("five");
					String oneName = Method.getRankingS("oneName");
					String twoName = Method.getRankingS("twoName");
					String threeName = Method.getRankingS("threeName");
					String fourName = Method.getRankingS("fourName");
					String fiveName = Method.getRankingS("fiveName");
					
					if (one == 0 && two == 0 && three == 0 && four == 0 && five == 0)
					{
						sender.sendMessage("��e---------------------------------------------------");
						sender.sendMessage("��f ���� ��ũ�� ���� ����� �������� �ʽ��ϴ�.");
						sender.sendMessage("��e---------------------------------------------------");
						return false;
					}
					
					sender.sendMessage("��e---------------------------------------------------");
					if (oneName.equals("NONE") == false) sender.sendMessage("��6 �� ��cRANK 1�� ��7::��f " + oneName + " / " + one);
					if (twoName.equals("NONE") == false) sender.sendMessage("��6 ���� ��cRANK 2�� ��7::��f " + twoName + " / " + two);
					if (threeName.equals("NONE") == false) sender.sendMessage("��6 ������ ��cRANK 3�� ��7::��f " + threeName + " / " + three);
					if (fourName.equals("NONE") == false) sender.sendMessage("��6 �������� ��cRANK 4�� ��7::��f " + fourName + " / " + four);
					if (fiveName.equals("NONE") == false) sender.sendMessage("��6 ���������� ��cRANK 5�� ��7::��f " + fiveName + " / " + five);
					sender.sendMessage("��e---------------------------------------------------");
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("����"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!(args.length == 3))
					{
						sender.sendMessage("��6/���� <����/�߰�/����> <�г���> <��> ��f- �÷��̾��� ������ �����մϴ�.");
						return false;
					}
					
					File f = new File("plugins/PlayMiniGame/Player/" + args[1] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("��c�� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
						return false;
					}
					

					Player argsP = Bukkit.getPlayer(args[1]);
					Method.setInfoInt(argsP, "����", Integer.parseInt(args[2]));
					sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ������ ��c" + args[2] + " ��6��ŭ �����Ͽ����ϴ�.");
					Ranking.RankingStat(argsP);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("�߰�"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!(args.length == 3))
					{
						sender.sendMessage("��6/���� <����/�߰�/����> <�г���> <��> ��f- �÷��̾��� ������ �����մϴ�.");
						return false;
					}
					
					File f = new File("plugins/PlayMiniGame/Player/" + args[1] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("��c�� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
						return false;
					}
					
					Player argsP = Bukkit.getPlayer(args[1]);
					int Score = Method.getInfoInt(argsP, "����") + Integer.parseInt(args[2]);
					Method.setInfoInt(argsP, "����", Score);
					sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ������ ��c" + args[2] + " ��6��ŭ �߰��Ͽ����ϴ�.");
					Ranking.RankingStat(argsP);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("����"))
				{
					if (!sender.isOp())
					{
						sender.sendMessage("��c����� ��ɾ ������ ������ �����ϴ�.");
						return false;
					}
					
					if (!(args.length == 3))
					{
						sender.sendMessage("��6/���� <����/�߰�/����> <�г���> <��> ��f- �÷��̾��� ������ �����մϴ�.");
						return false;
					}
					
					File f = new File("plugins/PlayMiniGame/Player/" + args[1] + ".yml");
					if (!f.exists())
					{
						sender.sendMessage("��c�� �÷��̾�� �������� �ʽ��ϴ�.");
						return false;
					}
					
					Scanner scan = new Scanner(args[2]);
					if (!scan.hasNextInt())
					{
						sender.sendMessage("��c<��>�� ���ڰ��� �Է��� �ֽʽÿ�.");
						return false;
					}
					
					Player argsP = Bukkit.getPlayer(args[1]);
					int Score = Method.getInfoInt(argsP, "����") - Integer.parseInt(args[2]);
					if (Score < 0) Score = 0;
					Method.setInfoInt(argsP, "����", Score);
					sender.sendMessage("��6���������� ��c" + args[1] + "��6���� ������ ��c" + args[2] + " ��6��ŭ �����ϴ�.");
					Ranking.RankingStat(argsP);
					return false;
				}
				
				if ((sender instanceof Player))
				{
					Player p = (Player) sender;
					
					if (args[0].equalsIgnoreCase("����") && args.length == 1)
					{
						sender.sendMessage("��e * ��6����� ��c���ھ� ��6����");
						sender.sendMessage("��e ������ ��c���� ��7:: ��f" + Method.getInfoInt(p, "����"));
						return false;
					}
				}
			}
		} catch (NumberFormatException ex) {
			displayHelp2(sender);
			return false;
		}
		
		try {
			if(commandLabel.equalsIgnoreCase("�̴ϰ���") || commandLabel.equalsIgnoreCase("mg") || commandLabel.equalsIgnoreCase("MiniGame")) {
				if (!sender.isOp()) {
					sender.sendMessage(main.w + "��c����� ������ �����ϴ�.");
					return false;
				}
				
				if (!(sender instanceof Player))
				{
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + 
							   		   ChatColor.WHITE + "]" + ChatColor.RED + " �ֿܼ��� ������ �Ұ��� �մϴ�.");
					return false;
				}
				
				Player p = (Player) sender;
				
				if(args.length == 0) {
					displayHelp(sender);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("����"))
				{					
					if (Bukkit.getOnlinePlayers().length < 2) {
						Bukkit.broadcastMessage(main.p + "��c�÷��̾��6�� ��c2�� �̸���6�̹Ƿ� ������ ������ �� �����ϴ�.");
						return false;
					}
					
					if (GameData.gameStart) {
						return false;
					}
					
					GameData.GameStart(1);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("������ϱ�"))
				{					
					if (Bukkit.getOnlinePlayers().length < 2) {
						Bukkit.broadcastMessage(main.p + "��c�÷��̾��6�� ��c2�� �̸���6�̹Ƿ� ������ ������ �� �����ϴ�.");
						return false;
					}
					
					if (GameData.gameStart) {
						return false;
					}
					
					GameData.GameStart(2);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("���"))
				{					
					if (Bukkit.getOnlinePlayers().length < 2) {
						Bukkit.broadcastMessage(main.p + "��c�÷��̾��6�� ��c2�� �̸���6�̹Ƿ� ������ ������ �� �����ϴ�.");
						return false;
					}
					
					if (GameData.gameStart) {
						return false;
					}
					
					GameData.GameStart(3);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("���ø���"))
				{					
					if (Bukkit.getOnlinePlayers().length < 2) {
						Bukkit.broadcastMessage(main.p + "��c�÷��̾��6�� ��c2�� �̸���6�̹Ƿ� ������ ������ �� �����ϴ�.");
						return false;
					}
					
					if (GameData.gameStart) {
						return false;
					}
					
					GameData.GameStart(4);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("�ѹ�Į��"))
				{					
					if (Bukkit.getOnlinePlayers().length < 2) {
						Bukkit.broadcastMessage(main.p + "��c�÷��̾��6�� ��c2�� �̸���6�̹Ƿ� ������ ������ �� �����ϴ�.");
						return false;
					}
					
					if (GameData.gameStart) {
						return false;
					}
					
					GameData.GameStart(5);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("���ǵ弦��"))
				{					
					if (Bukkit.getOnlinePlayers().length < 2) {
						Bukkit.broadcastMessage(main.p + "��c�÷��̾��6�� ��c2�� �̸���6�̹Ƿ� ������ ������ �� �����ϴ�.");
						return false;
					}
					
					if (GameData.gameStart) {
						return false;
					}
					
					GameData.GameStart(6);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("�պպ�"))
				{					
					if (Bukkit.getOnlinePlayers().length < 2) {
						Bukkit.broadcastMessage(main.p + "��c�÷��̾��6�� ��c2�� �̸���6�̹Ƿ� ������ ������ �� �����ϴ�.");
						return false;
					}
					
					if (GameData.gameStart) {
						return false;
					}
					
					GameData.GameStart(7);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("����"))
				{
					if (Bukkit.getOnlinePlayers().length < 2) {
						Bukkit.broadcastMessage(main.p + "��c�÷��̾��6�� ��c2�� �̸���6�̹Ƿ� ������ ������ �� �����ϴ�.");
						return false;
					}
					
					if (GameData.gameStart) {
						return false;
					}
					
					int RandomNum = new Random().nextInt(7) + 1;
					GameData.GameStart(RandomNum);
					return false;
				}
				
				else if (args[0].equalsIgnoreCase("��������"))
				{
					if (GameData.gameState) {
						GameData.GameStop();
				    	Bukkit.broadcastMessage("");
				    	Bukkit.broadcastMessage(main.p + "��c�����ڡ�6�� �������� ��c�̴ϰ��� ��6�� ������ ������׽��ϴ�.");
				    	Bukkit.broadcastMessage("");
						return false;
					} else {
						p.sendMessage(main.w + "��c������ ������ �����Ͻ� �� �����ϴ�.");
						return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("�ʼ����̵�"))
				{
					if (GameData.mapWark == false) {
						GameData.mapWark = true;
						GameData.mapPlayer = p.getName();
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp MAP " + p.getName());
						p.sendMessage(main.a + "��6����� ��c�̴ϰ��� ��������6�� �����ϼ̽��ϴ�.");
						p.sendMessage(main.a + "��6�ϰ���� ��c�̴ϰ��ӡ�6�� ��� ��c����Ż��6�� �����ϼ���!");
			         	Block block1 = Bukkit.getWorld("world_map").getBlockAt(Method.getLocation("��1 ���̾ƺ�"));
			         	Block block2 = Bukkit.getWorld("world_map").getBlockAt(Method.getLocation("��2 ���̾ƺ�"));
			         	Block block3 = Bukkit.getWorld("world_map").getBlockAt(Method.getLocation("��3 ���̾ƺ�"));
			         	block1.setType(Material.AIR);
			           	block2.setType(Material.AIR);
			           	block3.setType(Material.AIR);

			           	for (Player player : Bukkit.getOnlinePlayers()) {
			           		player.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 120.0f, 200.0f);
			           	} return false;
					}
				}
				
				else if (args[0].equalsIgnoreCase("�α���"))
				{
					if (GameData.gameStart) {
						switch (GameData.gameNum)
						{
							case 1:
								new Running_Game().OdiePlayer(p); break;
							case 2:
								new Anvil_Game().OdiePlayer(p); break;
							case 3:
								new Climb_Game().OdiePlayer(p); break;
							case 4:
								new Spleef_Game().OdiePlayer(p); break;
							case 5:
								new OneKnife_Game().OdiePlayer(p); break;
							case 6:
								new SpeedShot_Game().OdiePlayer(p); break;
							case 7:
								new Bomb_Game().OdiePlayer(p); break;
						}
					}
				}
				
				else if (args[0].equalsIgnoreCase("���������"))
				{
					if (GameData.gameStart && GameData.gameState) {
				        switch (GameData.gameNum) {
				        	case 1:
				        		if (GameData.diePlayers.contains(p.getName())) p.teleport(Method.getLocation("���� ���� ����"));
				        		else Running_Game.OdiePlayer(p);
				        		break;
				        	case 2:
				        		if (GameData.diePlayers.contains(p.getName())) p.teleport(Method.getLocation("������ϱ� ���� ����"));
				        		else Anvil_Game.OdiePlayer(p);
				        		break;
				        	case 3:
				        		if (GameData.diePlayers.contains(p.getName())) p.teleport(Method.getLocation("��� ���� ����"));
				        		else Climb_Game.OdiePlayer(p);
				        		break;
				         	case 4:
				        		if (GameData.diePlayers.contains(p.getName())) p.teleport(Method.getLocation("���ø��� ���� ����"));
				        		else Spleef_Game.OdiePlayer(p);
				        		break;
				        	case 5:
				        		if (GameData.diePlayers.contains(p.getName())) p.teleport(Method.getLocation("�ѹ�Į�� ���� ����"));
				        		else OneKnife_Game.OdiePlayer(p);
				        		break;
				        	case 6:
				        		if (GameData.diePlayers.contains(p.getName())) p.teleport(Method.getLocation("���ǵ弦�� ���� ����"));
				        		else SpeedShot_Game.OdiePlayer(p);
				        		break;
				        	case 7:
				        		if (GameData.diePlayers.contains(p.getName())) p.teleport(Method.getLocation("�պպ� ���� ����"));
				        		else Bomb_Game.OdiePlayer(p);
				        		break;
				        } return false;
					}
					
					else if (GameData.gameStart && GameData.gameState == false) {
						Method.GotoMap(p);
						p.sendMessage(main.a + "��c��ȣ���6�� ��� ���� ���� ������ �̵��غ�����!");
						return false;
					}
					
					else if (GameData.gameStart == false && GameData.gameState == false) {
						p.sendMessage(main.a + "��c��ȣ���6�� ��� ���� ���� ������ �̵��غ�����!");
						
						Method.GotoMap(p);
						return false;
					}
					
					else return false;
				}
			}
		} catch (NumberFormatException ex) {
			displayHelp(sender);
			return false;
		}
		
		return false;
	}
	
	private void displayHelp(CommandSender sender)
	{	
		sender.sendMessage(" ��e---- ��6�̴ϰ��� ��e-- ��6������ ��c1��6/��c1 ��e----");
		sender.sendMessage("��6/�̴ϰ���, /MiniGmae, /MG ��f- �̴ϰ��� ��ɾ ���ϴ�.");
		sender.sendMessage("��6/�̴ϰ��� ���� ��f- ������ �����մϴ�.");
		sender.sendMessage("��6/�̴ϰ��� ������ϱ� ��f- ������ϱ⸦ �����մϴ�.");
		sender.sendMessage("��6/�̴ϰ��� ��� ��f- ����� �����մϴ�.");
		sender.sendMessage("��6/�̴ϰ��� ���ø��� ��f- ���ø����� �����մϴ�.");
		sender.sendMessage("��6/�̴ϰ��� �ѹ�Į�� ��f- �ѹ�Į���� �����մϴ�.");
		sender.sendMessage("��6/�̴ϰ��� ���ǵ弦�� ��f- ���ǵ弦���� �����մϴ�.");
		sender.sendMessage("��6/�̴ϰ��� �պպ� ��f- �պպ��� �����մϴ�.");
		sender.sendMessage("��6/�̴ϰ��� ���� ��f- �������� ���� �����ؼ� �����մϴ�.");
		sender.sendMessage("��6/�̴ϰ��� �������� ��f- ������ ���������Ѵ�.");
		sender.sendMessage("��6/�̴ϰ��� �ʼ����̵� ��f- ���� �����ϴ� ������ �̵��Ѵ�.");
	}
	
	private void displayHelp2(CommandSender sender)
	{
		Player p = (Player) sender;
		sender.sendMessage(" ��e----- ��6�̴ϰ��� ���ھ� ��e--- ��6���� ��e-----");
		sender.sendMessage("��6/����, /���ھ�, /score ��f- �̴ϰ��� ���ھ� ������ ���ϴ�.");
		sender.sendMessage("��6/���� ���� ��f- �ڽ��� ������ Ȯ���մϴ�.");
		sender.sendMessage("��6/���� ���� <�г���> ��f- �ٸ� ����� ������ Ȯ���մϴ�.");
		sender.sendMessage("��6/���� ���� ��f- ���ھ� ������ TOP 5���� ���ϴ�.");
		if (p.isOp())
		{
			sender.sendMessage("��f");
			sender.sendMessage("��b/���� <����/�߰�/����> <�г���> <��> ��f- �÷��̾��� ������ �����մϴ�.");
		}
	}
}
