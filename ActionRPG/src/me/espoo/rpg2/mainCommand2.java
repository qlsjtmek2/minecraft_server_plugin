package me.espoo.rpg2;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.diddiz.LogBlock.BlockChange;
import de.diddiz.LogBlock.QueryParams;
import to.oa.tpsw.rpgexpsystem.api.Rpg;
import to.oa.tpsw.rpgexpsystem.api.RpgPlayer;

public class mainCommand2 extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final main plugin;

	public mainCommand2(main instance)
	{
		this.plugin = instance;
	}
	
	public ItemStack setOwner(ItemStack item, String owner) {
		SkullMeta meta = (SkullMeta)item.getItemMeta();
		meta.setOwner(owner);
		item.setItemMeta(meta);
		return item;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if (commandLabel.equalsIgnoreCase("dropscript")) {
			if (sender.isOp()) {
                if (args.length == 2) {
                	PlayerTimer timer;
                	switch (args[0]) {
                	case "gold1-1":
                		Method.sendCommand("1�ܰ���ť�� " + args[1] + "");
                		Method.sendCommand("rpgitem ���ǰ��� give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 15 0 ���� ��");
                		break;
                		
                	case "gold1-2":
                		Method.sendCommand("1�ܰ���ť�� " + args[1] + "");
                		Method.sendCommand("rpgitem �������µ������ give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 15 0 ����� ����");
                		break;
                		
                	case "gold2":
                		Method.sendCommand("2�ܰ���ť�� " + args[1] + "");
                		Method.sendCommand("rpgitem �������ǿ�ȥ give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 40 0 ������ ������");
                		break;
                		
                	case "gold3":
                		Method.sendCommand("3�ܰ���ť�� " + args[1] + "");
                		Method.sendCommand("rpgitem �����ǻ��ڱ� give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 80 0 �ܴ��� ����");
                		break;
                		
                	case "gold4":
                		Method.sendCommand("4�ܰ���ť�� " + args[1] + "");
                		Method.sendCommand("rpgitem ���������� give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 160 0 �ܴ��� ��������");
                		break;
                		
                	case "gold5":
                		Method.sendCommand("5�ܰ���ť�� " + args[1] + "");
                		Method.sendCommand("rpgitem �ذ��ǻ� give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 320 0 ĳ����� �ذ�");
                		break;
                		
                	case "gold6":
                		Method.sendCommand("6�ܰ���ť�� " + args[1] + "");
                		Method.sendCommand("rpgitem �Ź��� give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 540 0 �����̴�");
                		break;
                		
                	case "gold7":
                		Method.sendCommand("7�ܰ���ť�� " + args[1] + "");
                		Method.sendCommand("rpgitem �������� give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 800 0 ��ħ�� �����̴�");
                		break;
                		
                	case "gold8":
                		Method.sendCommand("8�ܰ���ť�� " + args[1] + "");
                		Method.sendCommand("rpgitem �����Ǵٸ��� give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 1200 0 ����");
                		break;
                		
                	case "gold9":
                		Method.sendCommand("9�ܰ���ť�� " + args[1] + "");
                		Method.sendCommand("rpgitem ���̾� give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 1800 0 ������ ��");
                		break;
                		
                	case "gold10":
                		Method.sendCommand("10�ܰ���ť�� " + args[1] + "");
                		Method.sendCommand("rpgitem �Ʊ��Ǵ��� give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 3000 0 ���̺� �𵥵�");
                		break;
                		
                	case "gold11":
                		Method.sendCommand("11�ܰ���ť�� " + args[1] + "");
                		Method.sendCommand("rpgitem �����Ǽ��� give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 4400 0 ����");
                		break;
                		
                	case "gold12":
                		Method.sendCommand("12�ܰ���ť�� " + args[1] + "");
                		Method.sendCommand("rpgitem ��ŷ�� give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 5200 0 ���̷���");
                		break;
                		
                	case "gold13":
                		Method.sendCommand("13�ܰ���ť�� " + args[1] + "");
                		Method.sendCommand("rpgitem �����ǿ��� give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 6400 0 ����� �𵥵�");
                		break;
                		
                	case "gold14":
                		Method.sendCommand("14�ܰ���ť�� " + args[1] + "");
                		Method.sendCommand("rpgitem ��Ż������ give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 7400 0 ���� ��Ż");
                		break;
                		
                	case "gold15":
                		Method.sendCommand("15�ܰ���ť�� " + args[1] + "");
                		Method.sendCommand("rpgitem ����Ʈ�̱� give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 6400 0 ������ ������");
                		break;
                		
                	case "goldevent":
                		Method.sendCommand("rpgitem ��ũ���ڷ� give " + args[1] + " 1");
                		Method.sendCommand("eco give " + args[1] + " 400");
                		Method.sendCommand("expgive " + args[1] + " 200 0 AI-���̾��");
                		break;
                		
                	case "ü��1":
                		Method.sendCommand("rpgitem ü������1 give " + args[1] + " 1");
                		break;
                		
                	case "ü��2":
                		Method.sendCommand("rpgitem ü������2 give " + args[1] + " 1");
                		break;
                		
                	case "ü��3":
                		Method.sendCommand("rpgitem ü������3 give " + args[1] + " 1");
                		break;
                		
                	case "ü��4":
                		Method.sendCommand("rpgitem ü������4 give " + args[1] + " 1");
                		break;
                		
                	case "ü��5":
                		Method.sendCommand("rpgitem ü������5 give " + args[1] + " 1");
                		break;
                		
                	case "����1":
                		Method.sendCommand("rpgitem ��������1 give " + args[1] + " 1");
                		break;
                		
                	case "����2":
                		Method.sendCommand("rpgitem ��������2 give " + args[1] + " 1");
                		break;
                		
                	case "����3":
                		Method.sendCommand("rpgitem ��������3 give " + args[1] + " 1");
                		break;
                		
                	case "����4":
                		Method.sendCommand("rpgitem ��������4 give " + args[1] + " 1");
                		break;
                		
                	case "����5":
                		Method.sendCommand("rpgitem ��������5 give " + args[1] + " 1");
                		break;
                		
                	case "�ֻ�޼��ϼ�":
                		if (Method.getOnorOffLine(args[1]) != null) {
                			Method.getOnorOffLine(args[1]).sendMessage("��f[��a�ȳ���f] ��6�����մϴ�!! ��c�ֻ�� ���ϼ� ��6�� �εμ��� ������ ����Ͽ����ϴ�.");
                			Method.sendCommand("rpgitem �ֻ�޼��ϼ� give " + args[1] + " 1");
                		} break;
                		
                	case "�𵥵����˸�":
                		main.UDD = true;
                		
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("��c[ ���Ĺ��� �𵥵� ] �� ����߽��ϴ�. 10�� �� �罺�� �˴ϴ�.");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		
            			timer = new PlayerTimer("UDD", new Runnable() {
            				@Override
            				public void run() {
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("��e[ ���Ĺ��� �𵥵� ] ��6�� �罺�� �Ǿ����ϴ�!");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Method.sendCommand("eb boss spawn ���Ĺ���_�𵥵� ���Ĺ���_�𵥵�");
                        		main.UDD = false;
            				}
            			});
            			
            			timer.setTime(1);
            			timer.Start();
                		break;
                		
                	case "���̷������˸�":
                		main.SKLT = true;
                		
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("��c[ ������ ���̷��� ] �� ����߽��ϴ�. 10�� �� �罺�� �˴ϴ�.");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		
                		timer = new PlayerTimer("SKLT", new Runnable() {
            				@Override
            				public void run() {
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("��e[ ������ ���̷��� ] ��6�� �罺�� �Ǿ����ϴ�!");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Method.sendCommand("eb boss spawn ������_���̷��� ������_���̷���");
                        		main.SKLT = false;
            				}
            			});
            			
            			timer.setTime(1);
            			timer.Start();
                		break;
                		
                	case "��������˸�":
                		main.WD = true;
                		
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("��c[ ��ȭ�� ���� ] �� ����߽��ϴ�. 10�� �� �罺�� �˴ϴ�.");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		
                		timer = new PlayerTimer("WD", new Runnable() {
            				@Override
            				public void run() {
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("��e[ ��ȭ�� ���� ] ��6�� �罺�� �Ǿ����ϴ�!");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Method.sendCommand("eb boss spawn ��ȭ��_���� ��ȭ��_����");
                        		main.WD = false;
            				}
            			});
            			
            			timer.setTime(1);
            			timer.Start();
                		break;
                		
                	case "���������˸�":
                		main.BDL = true;
                		
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("��c[ ��� ����� ] �� ����߽��ϴ�. 10�� �� �罺�� �˴ϴ�.");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		
                		timer = new PlayerTimer("BDL", new Runnable() {
            				@Override
            				public void run() {
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("��e[ ��� ����� ] ��6�� �罺�� �Ǿ����ϴ�!");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Method.sendCommand("eb boss spawn ���_����� ���_�����");
                        		main.BDL = false;
            				}
            			});
            			
            			timer.setTime(1);
            			timer.Start();
                		break;
                		
                	case "�εμ������˸�":
                		main.BD = true;
                		
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("��c[ �εμ��� ] �� �Ǹ� ���ϸ� ���������ϴ�. 10�� �� �罺�� �˴ϴ�.");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		Method.sendCommand("�εμ������ �����ʱ�ȭ");
                		
                		timer = new PlayerTimer("BD", new Runnable() {
            				@Override
            				public void run() {
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("��e[ �εμ��� ] ��6�� ��� �����̻��� ȸ���߽��ϴ�! ( ��Ȱ )");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Method.sendCommand("eb boss spawn �εμ��� �εμ���");
                        		main.BD = false;
            				}
            			});
            			
            			timer.setTime(1);
            			timer.Start();
                		break;
                	}
                }
			} return false;
		}
		
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			if (commandLabel.equalsIgnoreCase("�Ӹ�") || commandLabel.equalsIgnoreCase("nbth")) {
				if (p.isOp()) {
	                if (args.length == 0) {
	                    p.getInventory().setItem(p.getInventory().firstEmpty(), this.setOwner(new ItemStack(Material.SKULL_ITEM, 1, (short)3), p.getName()));
	                    p.sendMessage(ChatColor.GREEN + "Spawned " + p.getName() + "'s head!");
	                }
	                else if (args.length > 0) {
	                    p.getInventory().setItem(p.getInventory().firstEmpty(), this.setOwner(new ItemStack(Material.SKULL_ITEM, 1, (short)3), args[0]));
	                    p.sendMessage(ChatColor.GREEN + "Spawned " + args[0] + "'s head!");
	                } return false;
				} return false;
			}
			
			else if (commandLabel.equalsIgnoreCase("�����Ȯ��")) {
				if (main.BDL) {
                	p.sendMessage("��c���� ��� ������� �׾��ִ� �����Դϴ�.");
                } else {
                	p.sendMessage("��6! ��� ������� ����ֽ��ϴ�.");
                } return true;
			}
			
			else if (commandLabel.equalsIgnoreCase("�𵥵�Ȯ��")) {
				if (main.UDD) {
                	p.sendMessage("��c���� ���Ĺ��� �𵥵尡 �׾��ִ� �����Դϴ�.");
                } else {
                	p.sendMessage("��6! ���Ĺ��� �𵥵尡 ����ֽ��ϴ�.");
                } return true;
			}
			
			else if (commandLabel.equalsIgnoreCase("����Ȯ��")) {
				if (main.WD) {
					p.sendMessage("��c���� ��ȭ�� ������ �׾��ִ� �����Դϴ�.");
				} else {
					p.sendMessage("��6! ��ȭ�� ������ ����ֽ��ϴ�.");
				} return true;
			}
			
			else if (commandLabel.equalsIgnoreCase("�ε�Ȯ��")) {
				if (main.BD) {
                	p.sendMessage("��c���� �εμ��簡 �׾��ִ� �����Դϴ�.");
                } else {
                	p.sendMessage("��6! �εμ��簡 ����ֽ��ϴ�.");
                } return true;
			}
			
			else if (commandLabel.equalsIgnoreCase("���̷���Ȯ��")) {
				if (main.BD) {
                	p.sendMessage("��c������ ���̷����� �׾��ִ� �����Դϴ�.");
                } else {
                	p.sendMessage("��6! ������ ���̷����� ����ֽ��ϴ�.");
                } return true;
			}
			
			else if (commandLabel.equalsIgnoreCase("��������")) {
				if (p.isOp()) {
	                if (args.length == 2) {
	                	File f = new File("plugins/ActionRPG/Player/" + args[1] + ".yml");
	                	if (f.exists()) {
	                		Method.setInfoInt(p, "���� ī��Ʈ", Integer.parseInt(args[0]));
	                	} else {
	                		p.sendMessage("��c�� �÷��̾�� �������� �ʽ��ϴ�");
	                		return false;
	                	}
	                } else {
	                	p.sendMessage("��6/�������� <0/1/2/3> <�÷��̾�>");
                		return false;
	                }
				} return false;
			}
		
			else if (commandLabel.equalsIgnoreCase("in")) {
				if (p.isOp()) {
					if (main.in.get(p.getName()) != null) {
						p.sendMessage("��6������ �̸� ���� ��尡 ��c������6�Ǿ����ϴ�.");
						main.in.remove(p.getName());
						return false;
					} else {
						p.sendMessage("��6������ �̸� ���� ��尡 ��c������6�Ǿ����ϴ�.");
						main.in.put(p.getName(), "true");
						return false;
					}
				} return false;
			}
		
			else if (commandLabel.equalsIgnoreCase("gp")) {
				if (p.isOp()) {
					if (main.gp.get(p.getName()) != null) {
						p.sendMessage("��6��ǥ ���� ��尡 ��c������6�Ǿ����ϴ�.");
						main.gp.remove(p.getName());
						return false;
					} else {
						p.sendMessage("��6��ǥ ���� ��尡 ��c������6�Ǿ����ϴ�.");
						main.gp.put(p.getName(), "true");
						return false;
					}
				} return false;
			}
			
			else if (commandLabel.equals("ǥ����")) {
				Player play = (Player)sender;
				if (!play.hasPermission("se.edit")) {
					sender.sendMessage("��f[��4����f] ��c����� ������ �����ϴ�.");
					return true;
				}
		
				Block b = play.getTargetBlock(null, 10);
				if (b == null) {
					sender.sendMessage("��f[��4����f] ��c����� �տ� ǥ������ �����ϴ�.");
					return true;
				}
				
				if (!(b.getState() instanceof Sign)) {
					sender.sendMessage("��f[��4����f] ��c����� �տ� ǥ������ �����ϴ�.");
					return true;
				}
	
				if (args.length == 0) {
					sender.sendMessage("��f[��4����f] ��c�� ��ȣ�� ǥ���� ������ �����ּ���.");
					return true;
				}
	
				try {
					Integer.parseInt(args[0]);
				} catch (NumberFormatException e) {
					sender.sendMessage("Could not parse '" + args[0] + "' as a number.");
					return true;
				}
	
				int line = Integer.parseInt(args[0]);
				if ((line < 1) || (line > 4)) {
					sender.sendMessage("��f[��4����f] ��c���� ���� 1~4�� �����ּ���.");
					return true;
				}
	
				line--;
				String message = "";
				for (int i = 1; i < args.length; i++) message = message + " " + args[i];
				if (play.hasPermission("se.editCol")) message = message.replaceAll("([^\\\\](\\\\\\\\)*)&(.)", "$1��$3").replaceAll("(([^\\\\])\\\\((\\\\\\\\)*))&(.)", "$2$3&$5").replaceAll("\\\\\\\\", "\\\\");
				message = message.trim();
				if (message.length() > 15) {
					sender.sendMessage("��f[��4����f] ��cǥ���� ������ �ʹ� ��ϴ�.");
					return true;
				}
				Bukkit.getScheduler().runTaskAsynchronously(this, new Runner(play, line, message, b));
				return true;
			}
			
			else if (commandLabel.equals("���Ǳ���"))
			{
				if (!sender.isOp()) {
					sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
					return false;
				}
				
				if (!(sender instanceof Player)) {
					return false;
				}
				
				if (args.length == 4) {
					p.closeInventory();
					String str = args[2].replaceAll("_", " ");
					p.sendMessage("��6�� ���� ��c" + str + " ��6��/�� �����Ͻðڽ��ϱ�?");
					p.sendMessage("��e( ���� ���� ��f" + args[1] + "��e �� )");
					main.shop.put(p.getName(), Integer.parseInt(args[1]));
					main.buyme.put(p.getName(), args[0]);
					main.buyto.put(p.getName(), str);
					main.buytype.put(p.getName(), Integer.parseInt(args[3]));
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("��������")) {
				if (p.hasPermission("*")) {
					RpgPlayer player = Rpg.getRpgPlayera(p.getName());
					
					if (args[0].equalsIgnoreCase("����") || args[0].equalsIgnoreCase("������") || args[0].equalsIgnoreCase("������") || args[0].equalsIgnoreCase("����") || args[0].equalsIgnoreCase("�ü�")) {
						if (Method.getInfoString(p, "����").equals("NONE")) {
							String Upwork = args[0];
							Method.setInfoString(p, "����", Upwork);
							Method.setInfoInt(p, "���� ī��Ʈ", 1);
							p.sendMessage("��6����� ��e[ " + Upwork  + " ] ��6������ ��a���á�6�ϼ̽��ϴ�!");
							Method.PlayerManuadd(p.getName(), Upwork);
							p.getInventory().clear();
							
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/Īȣ �߰� " + p.getName() + " &f[&e���谡&f]");
							p.chat("/Īȣ ��ǥ 0");
							Method.removeOP(p, is);
							
							main.TTla1.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									p.chat("/��");
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp tutorial " + p.getName());
									Integer id = main.TTla1.remove(p.getName());
									Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 10L, 10L).getTaskId());
							
							main.TTla2.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ʺ��ڿ���� give " + p.getName() + " 1");
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ʺ��ڿ방�� give " + p.getName() + " 1");
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ʺ��ڿ뷹�뽺 give " + p.getName() + " 1");
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem �ʺ��ڿ���� give " + p.getName() + " 1");
									p.chat("/����");
									main.TTla3.put(p.getName(), new BukkitRunnable()
									{
										public void run()
										{
											p.kickPlayer("���� �Ŀ��� �������� �ǹ������� �Ǿ��ֽ��ϴ�.");
											Integer id = main.TTla3.remove(p.getName());
											Bukkit.getServer().getScheduler().cancelTask(id);
											return;
										}
									}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 15L, 15L).getTaskId());
									
									Integer id = main.TTla2.remove(p.getName());
									Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 30L, 20L).getTaskId());
						
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("�Ͱ˻�") || args[0].equalsIgnoreCase("����Ʈ") || args[0].equalsIgnoreCase("���̿�") || args[0].equalsIgnoreCase("�����") || args[0].equalsIgnoreCase("��ī��") ||
							 args[0].equalsIgnoreCase("������") || args[0].equalsIgnoreCase("ī����") || args[0].equalsIgnoreCase("����Ʈ") || args[0].equalsIgnoreCase("�ƹٵ�") || args[0].equalsIgnoreCase("���")) {
						if (Method.getInfoInt(p, "���� ī��Ʈ") == 1) {
							if (player.getRpgLevel() < 59) {
								p.sendMessage("��c����� �䱸 ������ ��� �������� ���Ͽ����ϴ�.");
								return false;
							}

							String Upwork = args[0];
							Method.setInfoString(p, "����", Upwork);
							Method.setInfoInt(p, "���� ī��Ʈ", 2);
							p.sendMessage("��a���ϵ帳�ϴ�! ��e[ ��c" + Upwork  +" ��e] ��a(��)�� ������ �Ϸ��ϼ̽��ϴ�.");
							p.sendMessage("��6���ο� ��ų�� ������ϴ�. ��e[ /��ų ��� ��e] ��6���� Ȯ�����ּ���.");
							Method.PlayerManuadd(p.getName(), Upwork);
							
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/Īȣ �߰� " + p.getName() + " &f[&3����&f]");
							Method.removeOP(p, is);
							
							main.Lvup.put(p.getName(), new BukkitRunnable()
							{
								int i = 6;
								public void run()
								{
									i -= 1;
									Method.castLvup(p);
				        			if (i <= 0) {
										Integer id = main.Lvup.remove(p.getName());
										Bukkit.getServer().getScheduler().cancelTask(id);
										return;
				        			}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 4L, 4L).getTaskId());
							
							main.Zunzec.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									p.kickPlayer("���� �Ŀ��� �������� �ǹ������� �Ǿ��ֽ��ϴ�.");
									Integer id = main.Zunzec.remove(p.getName());
									Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 60L, 60L).getTaskId());
							return false;
						} else {
							p.sendMessage("��c����� �䱸 ������ ��� �������� ���Ͽ����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("����Ŀ") || args[0].equalsIgnoreCase("�ȶ��") || args[0].equalsIgnoreCase("������") || args[0].equalsIgnoreCase("���ڵ�") || args[0].equalsIgnoreCase("������") ||
							 args[0].equalsIgnoreCase("����Ŀ") || args[0].equalsIgnoreCase("���̹�") || args[0].equalsIgnoreCase("�����") || args[0].equalsIgnoreCase("����Ŀ") || args[0].equalsIgnoreCase("����")) {
						if (Method.getInfoInt(p, "���� ī��Ʈ") == 2) {
							if (player.getRpgLevel() < 119) {
								p.sendMessage("��c����� �䱸 ������ ��� �������� ���Ͽ����ϴ�.");
								return false;
							}
							
							String Upwork = args[0];
							Method.setInfoString(p, "����", Upwork);
							Method.setInfoInt(p, "���� ī��Ʈ", 3);
							p.sendMessage("��a���ϵ帳�ϴ�! ��e[ ��c" + Upwork + " ��e]��a�� ������ �Ϸ��ϼ̽��ϴ�.");
							p.sendMessage("��6���ο� ��ų�� ������ϴ�. ��e[ /��ų ��� ��e] ��6���� Ȯ�����ּ���.");
							Method.PlayerManuadd(p.getName(), Upwork);
							
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/Īȣ �߰� " + p.getName() + " &f[&c�����&f]");
							Method.removeOP(p, is);
							
							main.Lvup.put(p.getName(), new BukkitRunnable()
							{
								int i = 6;
								public void run()
								{
									i -= 1;
									Method.castLvup(p);
							        	if (i <= 0) {
										Integer id = main.Lvup.remove(p.getName());
										Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 4L, 4L).getTaskId());
							
							main.Zunzec.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									p.kickPlayer("���� �Ŀ��� �������� �ǹ������� �Ǿ��ֽ��ϴ�.");
									Integer id = main.Zunzec.remove(p.getName());
									Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 60L, 60L).getTaskId());
							return false;
						} else {
							p.sendMessage("��c����� �䱸 ������ ��� �������� ���Ͽ����ϴ�.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("���̽�") || args[0].equalsIgnoreCase("ī����") || args[0].equalsIgnoreCase("��ÿ�") || args[0].equalsIgnoreCase("���ε�") || args[0].equalsIgnoreCase("�̶�") ||
							 args[0].equalsIgnoreCase("Ʈ����") || args[0].equalsIgnoreCase("������") || args[0].equalsIgnoreCase("���÷�") || args[0].equalsIgnoreCase("������") || args[0].equalsIgnoreCase("����Ŀ")) {
						if (Method.getInfoInt(p, "���� ī��Ʈ") == 3) {
							if (player.getRpgLevel() < 239) {
								p.sendMessage("��c����� �䱸 ������ ��� �������� ���Ͽ����ϴ�.");
								return false;
							}
							
							String Upwork = args[0];
							Method.setInfoString(p, "����", Upwork);
							Method.setInfoInt(p, "���� ī��Ʈ", 4);
							Bukkit.broadcastMessage("��e----------------------------------------------------------------");
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("��6�������ּ���! ��e[ " + p.getName() + " ] ��6���� ��c" + Upwork + "��6�� ��c���� ������6�� �Ϸ��ϼ̽��ϴ�.");
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("��e----------------------------------------------------------------");
							p.sendMessage("��6���ο� ��ų�� ������ϴ�. ��e[ /��ų ��� ��e] ��6���� Ȯ�����ּ���.");
							Method.PlayerManuadd(p.getName(), Upwork);
							
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/Īȣ �߰� " + p.getName() + " &f[&3���&b����&f]");
							Method.removeOP(p, is);
							
							main.Lvup.put(p.getName(), new BukkitRunnable()
							{
								int i = 6;
								public void run()
								{
									i -= 1;
									Method.castLvup(p);
							        	if (i <= 0) {
										Integer id = main.Lvup.remove(p.getName());
										Bukkit.getServer().getScheduler().cancelTask(id);
										return;
									}
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 4L, 4L).getTaskId());
							
							main.Zunzec.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									p.kickPlayer("���� �Ŀ��� �������� �ǹ������� �Ǿ��ֽ��ϴ�.");
									Integer id = main.Zunzec.remove(p.getName());
									Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 60L, 60L).getTaskId());
							return false;
						} else {
							p.sendMessage("��c����� �䱸 ������ ��� �������� ���Ͽ����ϴ�.");
							return false;
						}
					}
				}
			}
		} else {
			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + 
							   ChatColor.WHITE + "]" + ChatColor.RED + " �ֿܼ��� ������ �Ұ��� �մϴ�.");
			return false;
		}
		
		return false;
	}
	class Runner implements Runnable 
	{ 
		Player play;
		int line;
		String message;
		Block b;

		public Runner(Player player, int l, String m, Block block) 
		{ 
			this.play = player;
			this.line = l;
			this.message = m;
			this.b = block; 
		}

		public void run()
		{
			try 
			{
				Sign s = (Sign)this.b.getState();
				if (!this.play.hasPermission("se.editAny")) 
				{
					if (main.logblock == null) 
					{
						return;
					}
					
					QueryParams params = new QueryParams(main.logblock);
					params.bct = QueryParams.BlockChangeType.CREATED;
					params.limit = 1;
					params.needPlayer = true;
					params.loc = this.b.getLocation();
					params.needSignText = true;
					List<BlockChange> changes = main.logblock.getBlockChanges(params);
					if ((changes == null) || (changes.size() == 0)) {
					this.play.sendMessage("��cLogBlock�� ����� ���ڵ带 �������� �ʽ��ϴ�.");
					return;
				}
					BlockChange bc = (BlockChange)changes.get(0);
					if (bc.playerName != this.play.getName()) 
					{
						return;
					}
					
					String[] message = bc.signtext.split("");
					for (int i = 0; i < 4; i++) 
					{
						if (message[i] != s.getLine(i)) 
						{
							return;
						}
					}
				}
				if (main.lbconsumer != null) main.lbconsumer.queueSignBreak(this.play.getName(), s);
				
				String[] lines = (String[])s.getLines().clone();
				String tline = lines[this.line];
				lines[this.line] = this.message;
				s.setLine(this.line, this.message);
				s.update();
				SignChangeEvent e = new SignChangeEvent(this.b, this.play, lines);
				Bukkit.getPluginManager().callEvent(e);
				if (!e.isCancelled()) 
				{
					s.setLine(this.line, this.message);
				}
				
				else 
				{
					s.setLine(this.line, tline);
				}
				s.update();
		    } 
			
			catch (Exception e) 
			{
			}
		}
		
	}
}
