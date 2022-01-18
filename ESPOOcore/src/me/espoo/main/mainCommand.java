package me.espoo.main;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import me.espoo.Banitem.itemcheck;
import me.espoo.main.main.EnchantmentResult;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class mainCommand extends JavaPlugin implements CommandExecutor, Listener {
	private final main plugin;

	public mainCommand(main instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try {
			if (commandLabel.equalsIgnoreCase("tppod")) {
				if ((sender instanceof Player)) {
					if ((sender.isOp()) || (sender.hasPermission("f.use"))) {
						Player pl = (Player) sender;

						double x = Double.parseDouble(args[0]);
						double y = Double.parseDouble(args[1]);
						double z = Double.parseDouble(args[2]);

						Location loc = new Location(pl.getWorld(), x, y, z, 0.0F, 0.0F);

						pl.teleport(loc);
						return false;
					}

					sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
					return false;
				}

				sender.sendMessage(
						ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "] ��Ŷ���� ������ �Ұ��� �մϴ�.");
				return false;
			}

			if (commandLabel.equalsIgnoreCase("f")) {
				if (args[0].equalsIgnoreCase("help")) {
					main.help(sender);
					return false;
				}

				if (args[0].equalsIgnoreCase("t")) {
					if (args.length == 6) {
						if ((sender.isOp()) || (sender.hasPermission("f.use"))) {
							Player pl = main.p(args[1]);

							if (pl != null) {
								double x = Double.parseDouble(args[3]);
								double y = Double.parseDouble(args[4]);
								double z = Double.parseDouble(args[5]);
								World w = Bukkit.getWorld(args[2]);

								Location loc = new Location(w, x, y, z, 0.0F, 0.0F);

								pl.teleport(loc);
								return false;
							}

							sender.sendMessage("��c�� �÷��̾�� �¶����� �ƴմϴ�.");
							return false;
						}

						sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
						return false;
					}

					if ((sender.isOp()) || (sender.hasPermission("f.use"))) {
						Player pl = main.p(args[1]);

						if (pl != null) {
							double x = Double.parseDouble(args[3]);
							double y = Double.parseDouble(args[4]);
							double z = Double.parseDouble(args[5]);
							double yaw = Double.parseDouble(args[6]);
							double pitch = Double.parseDouble(args[7]);
							World w = Bukkit.getWorld(args[2]);

							Location loc = new Location(w, x, y, z, (float) yaw, (float) pitch);

							pl.teleport(loc);
							return false;
						}

						sender.sendMessage("��c�� �÷��̾�� �¶����� �ƴմϴ�.");
						return false;
					}

					sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
					return false;
				}

				if (args[0].equalsIgnoreCase("tp")) {
					if ((sender.isOp()) || (sender.hasPermission("f.use"))) {
						Player a = main.p(args[1]);
						if (a != null) {
							if (args.length == 3) {
								Player b = main.p(args[2]);
								if (b != null) {
									Location bl = b.getLocation();
									double x = bl.getX();
									double y = bl.getY();
									double z = bl.getZ();
									double yaw = bl.getYaw();
									double pitch = bl.getPitch();
									World w = bl.getWorld();

									a.teleport(new Location(w, x, y, z, (float) yaw, (float) pitch));
									return false;
								}

								sender.sendMessage("��c�� �÷��̾�� �¶����� �ƴմϴ�.");
								return false;
							}

							if (args.length == 2) {
								if ((sender instanceof Player)) {
									Player p = (Player) sender;
									Location bl = a.getLocation();
									double x = bl.getX();
									double y = bl.getY();
									double z = bl.getZ();
									double yaw = bl.getYaw();
									double pitch = bl.getPitch();
									World w = bl.getWorld();

									p.teleport(new Location(w, x, y, z, (float) yaw, (float) pitch));
									return false;
								}

								sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE
										+ "] ��Ŷ���� ������ �Ұ��� �մϴ�.");
								return false;
							}

							main.help(sender);
							return false;
						}

						sender.sendMessage("��c�� �÷��̾�� �¶����� �ƴմϴ�.");
						return false;
					}

					sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
					return false;
				}

				if (args[0].equalsIgnoreCase("s")) {
					if ((sender.isOp()) || (sender.hasPermission("f.use"))) {
						Player a = main.p(args[1]);

						if (a != null) {
							if ((sender instanceof Player)) {

								Player p = (Player) sender;
								Location bl = p.getLocation();
								double x = bl.getX();
								double y = bl.getY();
								double z = bl.getZ();
								double yaw = bl.getYaw();
								double pitch = bl.getPitch();
								World w = bl.getWorld();

								a.teleport(new Location(w, x, y, z, (float) yaw, (float) pitch));
								return false;
							}

							sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE
									+ "] ��Ŷ���� ������ �Ұ��� �մϴ�.");
							return false;
						}

						sender.sendMessage("��c�� �÷��̾�� �¶����� �ƴմϴ�.");
						return false;
					}

					sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
					return false;
				}

				if (args[0].equalsIgnoreCase("tpall")) {
					if ((sender.isOp()) || (sender.hasPermission("f.use"))) {
						if (args.length == 2) {
							Player a = main.p(args[1]);

							if (a != null) {
								Location bl = a.getLocation();
								double x = bl.getX();
								double y = bl.getY();
								double z = bl.getZ();
								double yaw = bl.getYaw();
								double pitch = bl.getPitch();
								World w = bl.getWorld();

								for (Player p1 : Bukkit.getOnlinePlayers()) {
									p1.teleport(new Location(w, x, y, z, (float) yaw, (float) pitch));
								}

								return false;
							}

							sender.sendMessage("��c�� �÷��̾�� �¶����� �ƴմϴ�.");
							return false;
						}

						if (args.length == 1) {
							if ((sender instanceof Player)) {
								Player pp = (Player) sender;
								Location bl = pp.getLocation();
								double x = bl.getX();
								double y = bl.getY();
								double z = bl.getZ();
								double yaw = bl.getYaw();
								double pitch = bl.getPitch();
								World w = bl.getWorld();
								for (Player p1 : Bukkit.getOnlinePlayers()) {
									p1.teleport(new Location(w, x, y, z, (float) yaw, (float) pitch));
								}

								return false;
							}

							sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE
									+ "] ��Ŷ���� ������ �Ұ��� �մϴ�.");
							return false;
						}

						main.help(sender);
						return false;
					}

					sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
					return false;
				}
			}
		} catch (Exception e) {
			if ((sender.isOp())) {
				main.help(sender);
				return false;
			} else {
				sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
				return false;
			}
		}

		if (commandLabel.equals("o")) {
			if (sender.isOp()) {
				if ((sender instanceof Player)) {
					Player p = (Player) sender;
					Bukkit.broadcastMessage(p.getName() + "���� ������ �ݾҽ��ϴ�.");
				}
				
				main.stopE = true;
				main.blockE = true;
				main.healthE = true;
				main.chatE = true;

				Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
					public void run() {
						if (main.inte == 5) {
							for (int i = 0; i < 100; i++) {
								Bukkit.broadcastMessage("");
							}

							for (Player p : Bukkit.getOnlinePlayers()) {
								p.closeInventory();
							}

							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ ��c5�� ��6�� ����˴ϴ�.");
						}

						else if (main.inte == 4) {
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ ��c4�� ��6�� ����˴ϴ�.");
						}

						else if (main.inte == 3) {
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ ��c3�� ��6�� ����˴ϴ�.");
						}

						else if (main.inte == 2) {
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ ��c2�� ��6�� ����˴ϴ�.");
						}

						else if (main.inte == 1) {
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ ��c1�� ��6�� ����˴ϴ�.");
						}

						else if (main.inte <= 0) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
									"kickall ������ ����Ǿ����ϴ�.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stop");
						}

						--main.inte;
					}
				}, 20L, 20L);

				return false;
			}

			sender.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
			return false;
		}

		if (sender instanceof Player) {
			Player p = (Player) sender;
			Inventory pi = p.getInventory();
			int[] pib = new int[pi.getSize()];
			if (pi.firstEmpty() == -1) {
				for (int i = 0; i < pi.getSize(); i++) {
					pib[i] = pi.getItem(i).getAmount();
				}
			}

			ItemStack itemHand = p.getItemInHand();
			int id = itemHand.getType().getId();
			byte data = itemHand.getData().getData();
			String world = p.getWorld().getName();
			itemcheck itemmethod = new itemcheck(main.all, id, data, world);
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				sb.append(args[i]).append(" ");
			}
			String allArgs = sb.toString();

			try {
				if (commandLabel.equalsIgnoreCase("��")) {
					if (p.isOp() == true) {
						sender.sendMessage(ChatColor.RED + "=============================");
						sender.sendMessage(ChatColor.BLUE + "���� ��� �޸� ��Ȳ ���");
						sender.sendMessage(ChatColor.GREEN + "Total Memory: " + totalMem());
						sender.sendMessage(ChatColor.GREEN + "Free Memory: " + freeMem());
						sender.sendMessage(ChatColor.RED + "Allocated Memory: " + maxMem());
						sender.sendMessage(ChatColor.RED + "=============================");
						return true;
					} else {
						sender.sendMessage("��f[��4����f] ��c����� ������ �����ϴ�.");
						return true;
					}
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage(ChatColor.RED + "=============================");
				sender.sendMessage(ChatColor.BLUE + "���� ��� �޸� ��Ȳ ���");
				sender.sendMessage(ChatColor.GREEN + "Total Memory: " + totalMem());
				sender.sendMessage(ChatColor.GREEN + "Free Memory: " + freeMem());
				sender.sendMessage(ChatColor.RED + "Allocated Memory: " + maxMem());
				sender.sendMessage(ChatColor.RED + "=============================");
				return true;
			}

			try {
				if ((commandLabel.equalsIgnoreCase("�����۱���")) && (args.length == 0)) {
					p.sendMessage("��6/�����۱��� ��f- ������ ���ϴ�.");
					p.sendMessage("��6/�����۱��� �߰� <���޼���> ��f- ����ִ� �������� ������� ���ϰ� �մϴ�.");
					p.sendMessage("��6/�����۱��� �߰� ��� <���޼���> ��f- ����ִ� �������� ������ �ڵ嵵 ��� ������� ���ϰ� �մϴ�.");
					p.sendMessage("��6/�����۱��� ���� ��f- ����ִ� ������ ���� ���θ� �����մϴ�.");
					p.sendMessage("��6/�����۱��� Ȯ�� ��f- ����ִ� ������ �������θ� Ȯ���մϴ�.");
					return false;
				} else if ((commandLabel.equalsIgnoreCase("�����۱���")) && (args[0].equalsIgnoreCase("�߰�"))
						&& (args.length == 1)) {
					if ((p.hasPermission("banitem.add")) || (p.hasPermission("banitem.*")) || (p.isOp())) {
						p.sendMessage("��c��ɾ ����� ������ּ���. ��f- ��6/�����۱��� �߰� <���޼���>");
						return false;
					} else {
						p.sendMessage("��c����� ��ɾ ����� ������ �����ϴ�.");
						return false;
					}
				} else if ((commandLabel.equalsIgnoreCase("�����۱���")) && (args[0].equalsIgnoreCase("�߰�"))
						&& (args[1].equalsIgnoreCase("���")) && (args.length > 1)) {
					if ((p.hasPermission("banitem.add")) || (p.hasPermission("banitem.*")) || (p.isOp())) {
						if (itemmethod.getnumber() == 0) {
							StringBuilder sb1 = new StringBuilder();
							for (int i = 2; i < args.length; i++) {
								sb1.append(args[i]).append(" ");
							}
							String allArgs1 = sb1.toString();
							main.all.add(id + ":-1:" + allArgs1);

							p.sendMessage("��6������ �ڵ� ��e[��c" + id + ":*��e] ��6�� ������ ���� ������ ��c�����˴ϴ١�6.:");
							p.sendMessage(" " + allArgs1);

							reloadConfig();
							getConfig().set("Blacklist", main.all);
							saveConfig();
							return false;
						} else {
							p.sendMessage("��e[��c" + id + ":*" + "��e] ��6�������� �̹� �����Ǿ� �ֽ��ϴ�.");
							return false;
						}
					} else {
						p.sendMessage("��c����� ��ɾ ����� ������ �����ϴ�.");
						return false;
					}
				} else if ((commandLabel.equalsIgnoreCase("�����۱���")) && (args[0].equalsIgnoreCase("�߰�"))
						&& (args.length > 1)) {
					if ((p.hasPermission("banitem.add")) || (p.hasPermission("banitem.*")) || (p.isOp())) {
						if (itemmethod.getnumber() == 0) {
							main.all.add(id + ":" + data + ":" + allArgs);

							p.sendMessage("��6�������ڵ� ��e[��c" + id + ":" + data + "��e] ��6�� ������ ���� ������ ��c�����˴ϴ١�6.:");
							p.sendMessage(" " + allArgs);

							reloadConfig();
							getConfig().set("Blacklist", main.all);
							saveConfig();
							return false;
						} else {
							p.sendMessage("��e[��c" + id + ":" + data + "��e] ��6�������� �̹� �����Ǿ� �ֽ��ϴ�.");
							return false;
						}
					} else {
						p.sendMessage("��c����� ��ɾ ����� ������ �����ϴ�.");
						return false;
					}
				} else if ((commandLabel.equalsIgnoreCase("�����۱���")) && (args[0].equalsIgnoreCase("Ȯ��"))
						&& (args.length == 1)) {
					if ((p.hasPermission("banitem.*")) || (p.isOp()) || (p.hasPermission("banitem.check"))) {
						if (itemmethod.getnumber() == 1) {
							p.sendMessage("��e[��c" + itemmethod.getId() + ":" + itemmethod.getData()
									+ "��e] ��6�������� �̷��� ������ �����Ǿ� �ֽ��ϴ�:");
							p.sendMessage(" " + itemmethod.getReason());
							return false;
						} else {
							p.sendMessage("��c�� �������� �����Ǿ� ���� �ʽ��ϴ�.");
							return false;
						}
					} else {
						p.sendMessage("��c����� ��ɾ ����� ������ �����ϴ�.");
						return false;
					}
				} else if ((commandLabel.equalsIgnoreCase("�����۱���"))
						&& ((args[0].equalsIgnoreCase("����")) || (args[0].equalsIgnoreCase("����")))
						&& (args.length == 1)) {
					if ((p.hasPermission("banitem.remove")) || (p.hasPermission("banitem.del"))
							|| (p.hasPermission("banitem.*")) || (p.isOp())) {
						if (itemmethod.getnumber() == 1) {
							main.all.remove(
									itemmethod.getId() + ":" + itemmethod.getData() + ":" + itemmethod.getReason());

							p.sendMessage("��c�����ۡ�6�� ���������� ��c���� ��ϡ�6���� �����Ͽ����ϴ�.");
							reloadConfig();
							getConfig().set("Blacklist", main.all);
							saveConfig();
							return false;
						} else {
							p.sendMessage("��c�� �������� �����Ǿ� ���� �ʽ��ϴ�.");
							return false;
						}
					} else {
						p.sendMessage("��c����� ��ɾ ����� ������ �����ϴ�.");
						return false;
					}
				}
			} catch (Exception e) {
				if ((sender.isOp())) {
					p.sendMessage("��6/�����۱��� ��f- ������ ���ϴ�.");
					p.sendMessage("��6/�����۱��� �߰� <���޼���> ��f- ����ִ� �������� ������� ���ϰ� �մϴ�.");
					p.sendMessage("��6/�����۱��� �߰� ��� <���޼���> ��f- ����ִ� �������� ������ �ڵ嵵 ��� ������� ���ϰ� �մϴ�.");
					p.sendMessage("��6/�����۱��� ���� ��f- ����ִ� ������ ���� ���θ� �����մϴ�.");
					p.sendMessage("��6/�����۱��� Ȯ�� ��f- ����ִ� ������ �������θ� Ȯ���մϴ�.");
					return false;
				}

				else {
					p.sendMessage("��c����� ��ɾ ����� ������ �����ϴ�.");
					return false;
				}
			}

			try {
				if (commandLabel.equalsIgnoreCase("���չ���")) {
					if (args[0].equalsIgnoreCase("�߰�")) {
						if (p.hasPermission("blacklist.add")) {
							if (!main.list.contains(Integer.valueOf(p.getItemInHand().getTypeId()))) {
								main.list.add(Integer.valueOf(p.getItemInHand().getTypeId()));
								getConfig().set("blacklist.items", main.list);
								saveConfig();
								p.sendMessage("��c�����ۡ�6�� ���������� ��c������ ������6���׽��ϴ�.");
							}
						} else
							p.sendMessage("��c�̹� ������ �����Ǿ� �ִ� ������ �Դϴ�.");
					} else if ((args[0].equalsIgnoreCase("����"))
							|| (args[0].equalsIgnoreCase("����")) && (p.hasPermission("blacklist.remove"))) {
						if (main.list.contains(Integer.valueOf(p.getItemInHand().getTypeId()))) {
							for (int i = 0; i < main.list.size(); i++) {
								if (((Integer) main.list.get(i))
										.equals(Integer.valueOf(p.getItemInHand().getTypeId()))) {
									main.list.remove(i);
								}
							}
							getConfig().set("blacklist.items", main.list);
							saveConfig();
							p.sendMessage("��c�����ۡ�6�� ���������� ��c���� ������6�� �����߽��ϴ�.");
						} else {
							p.sendMessage(ChatColor.RED + "��c���� ���� ������ ��Ͽ� ���� ������ �Դϴ�.");
						}
					}
				}
			} catch (Exception e) {
				if ((sender.isOp())) {
					p.sendMessage("��6/���չ��� �߰� ��f- ���� ������ �������� �߰��մϴ�.");
					p.sendMessage("��6/���չ��� ���� ��f- ���� ������ �������� �����մϴ�.");
					return false;
				} else {
					p.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
					return false;
				}
			}

			try {
				if (commandLabel.equalsIgnoreCase("��������")) {
					main.T.TrashCan(p);
					return true;
				}
			} catch (NumberFormatException ex) {
				main.T.TrashCan(p);
				return true;
			}

			try {
				if (commandLabel.equalsIgnoreCase("ä��û��") || commandLabel.equalsIgnoreCase("cc")) {
					if (p.isOp() == true) {
						for (main.i = 0; main.i < 100; main.i++)
							Bukkit.broadcastMessage("");
					} else {
						p.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
					}
					return true;
				}
			} catch (NumberFormatException ex) {
				for (main.i = 0; main.i < 100; main.i++)
					Bukkit.broadcastMessage("");
				return true;
			}

			try {
				if (commandLabel.equalsIgnoreCase("��ǥ")) {
					String pname = p.getName();
					if (p.getInventory().firstEmpty() == -1) {
						p.sendMessage(ChatColor.RED + "�κ��丮 â�� �����Ͽ� ��ǥ�� ȯ���Ͻ� �� �����ϴ�.");
						return true;
					}

					if ((main.economy.getBalance(pname) >= Integer.valueOf(args[0]).intValue())
							&& (Integer.valueOf(args[0]).intValue() > 0)) {
						ItemStack item = new ItemStack(main.config_itemcode, 1);
						ItemMeta itemM = item.getItemMeta();
						itemM.setDisplayName("��e��a��c��6��o ��ǥ");
						itemM.setLore(Arrays.asList(new String[] { "��f��o�ѤѤѤѤѤѤѤ�", args[0], "��f��o�ѤѤѤѤѤѤѤ�", "��f",
								"��7��o������ Ŭ������", "��7��o������ ȯ�� ����", "��7", "��7��oby. ��a��oESPOO ����" }));
						item.setItemMeta(itemM);
						p.getInventory().addItem(new ItemStack[] { item });
						main.economy.withdrawPlayer(pname, Double.valueOf(args[0]).doubleValue());
						p.sendMessage("��6���������� �߱޵Ǿ����ϴ�.");
						return true;
					}

					p.sendMessage(ChatColor.RED + "�����ϰ� ���� ���� ������ ������ �� ���ų� 0���� ���� ���� �Է��Ͽ����ϴ�.");
					return true;
				}
			} catch (Exception e) {
				p.sendMessage("��6/��ǥ (��) ��f- ��ǥ�� �����մϴ�.");
				return false;
			}

			try {
				if (cmd.getName().equalsIgnoreCase("��纸��")) {
					if (args.length > 0) {
						if (args[0].equalsIgnoreCase("Reload")) {
							if (!sender.hasPermission("farmassist.reload")) {
								p.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
								return true;
							}
							main.loadYamls();
							sender.sendMessage("��f[��4�˸���f] ��aconfig ������ ���ε� �Ͽ����ϴ�.");
							return true;
						}

						if (args[0].equalsIgnoreCase("Toggle")) {
							if (!sender.hasPermission("farmassist.toggle")) {
								p.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
								return true;
							}

							if (main.playerList.contains(sender.getName())) {
								main.playerList.remove(sender.getName());
								sender.sendMessage("��f[��4�˸���f] ��aFarmAssist ����� ���� Ȱ��ȭ �Ǿ����ϴ�.");
							} else {
								main.playerList.add(sender.getName());
								sender.sendMessage("��f[��4�˸���f] ��cFarmAssist ����� ��Ȱ��ȭ �Ǿ����ϴ�.");
							}
							return true;
						}

						if (args[0].equalsIgnoreCase("Global")) {
							if (!sender.hasPermission("farmassist.toggle.global")) {
								p.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
								return true;
							}

							if (!main.Enabled) {
								main.Enabled = true;
								sender.sendMessage("��f[��4�˸���f] ��aFarmAssist �۷ι� ����� Ȱ��ȭ �߽��ϴ�!");
							} else {
								main.Enabled = false;
								sender.sendMessage("��f[��4�˸���f] ��cFarmAssist �۷ι� ����� ��Ȱ��ȭ �߽��ϴ�!");
							}
							return true;
						}
					}
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("��6/��纸�� Toggle ��f- ���� �ڵ� �ɱ� ����� ���ų� ŵ�ϴ�.");
				sender.sendMessage("��6/��纸�� Global ��f- ���� �ڵ� �ɱ� �۷ι� ����� ���ų� ŵ�ϴ�.");
				return true;
			}

			try {
				if (commandLabel.equals("����")) {
					if (p.hasPermission("AllPlayer.admin")) {
						if (!main.stopE) {
							Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ �� ���� �����Ǿ����ϴ�.");
							main.stopE = true;
							return true;
						}

						Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ �� �ְ� �����Ǿ����ϴ�.");
						main.stopE = false;
						return true;
					}

					p.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
					return true;
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("��6/���� ��f- ��� �÷��̾ ���� ��ŵ�ϴ�.");
				return true;
			}

			try {
				if (commandLabel.equals("��þƮ���")) {
					if ((args.length > 0)) {
						if (args[0].equalsIgnoreCase("��ȣ")) {
							p.sendMessage("��f[��a��þƮ��f] ��b��ȣ�迭 : 0 ��ȣ 1 ȭ����ȣ 2 ������ ���� 3 ���ߺ�ȣ 4 ���Ÿ� ��ȣ 5 ȣ�� 6 ģ���� 7 ����");
						}

						if (args[0].equalsIgnoreCase("Ȱ")) {
							p.sendMessage("��f[��a��þƮ��f] ��bȰ�迭 : 48 �� 49 �о�� 50 ȭ�� 51 ����");
						}
					}
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("��6/��þƮ��� ��ȣ ��f- ��ȣ ��þƮ �ڵ带 ���ϴ�.");
				sender.sendMessage("��6/��þƮ��� Ȱ ��f- Ȱ ��þƮ �ڵ带 ���ϴ�.");
				return true;
			}

			try {
				if (commandLabel.equals("��")) {
					if (p.hasPermission("AllPlayer.admin")) {
						if (!main.blockE) {
							main.blockE = true;
							Bukkit.broadcastMessage("��f[��4�˸���f] ��6���� �μ��� ���� �����Ǿ����ϴ�.");
							return true;
						}

						main.blockE = false;
						Bukkit.broadcastMessage("��f[��4�˸���f] ��6���� �μ��� �ְ� �����Ǿ����ϴ�.");
						return true;
					}

					p.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
					return true;
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("��6/�� ��f- ����÷��̾ ���� Ķ �� ���� �˴ϴ�.");
				return true;
			}

			try {
				if (commandLabel.equals("����")) {
					if (p.hasPermission("AllPlayer.admin")) {
						if (!main.healthE) {
							main.healthE = true;
							Bukkit.broadcastMessage("��f[��4�˸���f] ��6�������·� ����Ǿ����ϴ�.");
							return true;
						}

						main.healthE = false;
						Bukkit.broadcastMessage("��f[��4�˸���f] ��6�Ϲݻ��·� ����Ǿ����ϴ�.");
						return true;
					}

					p.sendMessage(ChatColor.RED + "����� ��ɾ ����� ������ �����ϴ�.");
					return true;
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("��6/���� ��f- ����÷��̾ ������ �˴ϴ�.");
				return true;
			}

			try {
				if (commandLabel.equals("ä��")) {
					if (p.hasPermission("AllPlayer.admin")) {
						if (!main.chatE) {
							main.chatE = true;
							Bukkit.broadcastMessage("��f[��4�˸���f] ��6ä���� ���ϵ��� �����Ǿ����ϴ�.");
							return true;
						}

						main.chatE = false;
						Bukkit.broadcastMessage("��f[��4�˸���f] ��6ä���� �Ҽ��ְ� �����Ǿ����ϴ�.");
						return true;
					}
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("��6/ä�� ��f- ����÷��̾ ä���� �� �� ���� �˴ϴ�.");
				return true;
			}

			try {
				if (commandLabel.equals("����")) {
					if (p.hasPermission("AllPlayer.admin")) {
						if (args.length == 0) {
							if (!main.levelE) {
								main.levelE = true;
								Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ �����Ǿ����ϴ�.");
								Bukkit.broadcastMessage(ChatColor.GOLD + "���� : " + main.levelF);
								return true;
							}

							main.levelE = false;
							Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ �����Ǿ����ϴ�.");
							return true;
						}

						if (args.length == 1) {
							main.levelF = Integer.parseInt(args[0]);
							Bukkit.broadcastMessage("��f[��4�˸���f] ��6������ ��c" + main.levelF + "��6�� �����Ǿ����ϴ�.");
							return true;
						}

						p.sendMessage(ChatColor.RED + "��ɾ� ����");
						p.sendMessage(ChatColor.RED + "/���� - ������ ���� �Ǵ� �����մϴ�.");
						p.sendMessage(ChatColor.RED + "/���� <0 �̻��� ����> - ������ <0 �̻��� ����>�� �����մϴ�.");
						return true;
					}
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("��6/���� ��f- ������ ���� �Ǵ� �����մϴ�.");
				sender.sendMessage("��6/���� <0 �̻��� ����> ��f- ������ <0 �̻��� ����>�� �����մϴ�.");
				return true;
			}

			try {
				if (commandLabel.equals("��þƮ")) {
					if (args.length > 0) {
						if (args[0].equalsIgnoreCase("�����")) {
							if (sender.hasPermission("enchanter.reload")) {
								loadEnchantments(sender);
								Bukkit.broadcastMessage("��f[��4�˸���f] ��a��þƮ �÷����� ��� �����");
								return true;
							}
							sender.sendMessage("��c����ε� ���ڰ��� �Է����ּ���.");
							return true;
						}

						if (p.hasPermission("enchanter.enchant")) {
							if (args[0].equalsIgnoreCase("���")) {
								boolean dirty = (args.length <= 1) || (!args[1].equalsIgnoreCase("����"));
								for (Enchantment enchantment : Enchantment.values()) {
									main.enchantItem(p, enchantment, dirty ? 32767 : enchantment.getMaxLevel());
								}
								sender.sendMessage("��c��� ��þƮ��6�� ��c32767��6�� �����߽��ϴ�.");
								return true;
							}
							int targetLevel = 1;
							if (args.length > 1) {
								try {
									targetLevel = Integer.valueOf(args[1]).intValue();
								} catch (NumberFormatException e) {
									if (args[1].equalsIgnoreCase("�ִ�")) {
										targetLevel = -1;
									}
								}
							}
							EnchantmentResult result = main.enchantItem(p, args[0], targetLevel);
							switch (result) {
							case CANNOT_ENCHANT:
								sender.sendMessage("��c�װ��� ��þƮ ID�� �ƴմϴ�.");
								break;
							}

							sender.sendMessage("��c�����ۡ�6�� ���������� ��c��þƮ��6�� �Ǿ����ϴ�.");
							return true;
						}
						return true;
					}
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("��6/��þƮ ��� ��f- �ڽ��� ����ִ°Ϳ� ��� ��þƮ�� �ο��մϴ�.");
				sender.sendMessage("��6/��þƮ ��� ���� ��f- �ڽ��� ��� �ִ°Ϳ� ���������� ��� ��þƮ�� �ο��մϴ�.");
				sender.sendMessage("��6/��þƮ <�ڵ�> <����> ��f- �ش� ���⿡ �ڵ常ŭ ������ �ο��մϴ�.");
				sender.sendMessage("��6/��þƮ <�ڵ�> �ִ� ��f- �ش� ���⿡ �ڵ� ��ŭ ������ �ִ�� �ο��մϴ�.");
				return true;
			}
		}

		else
			sender.sendMessage(
					ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "] ��Ŷ���� ������ �Ұ��� �մϴ�.");
		return true;
	}

	private void displayHelp(CommandSender sender) {
		sender.sendMessage("��6/save ��f- ��� ���带 �����մϴ�.");
	}

	public long freeMem() {
		return Runtime.getRuntime().freeMemory() / 1024L / 1024L;
	}

	public long maxMem() {
		return Runtime.getRuntime().maxMemory() / 1024L / 1024L;
	}

	public long totalMem() {
		return Runtime.getRuntime().totalMemory() / 1024L / 1024L;
	}

	public void reloadChances() {
		main.stone = (float) getConfig().getDouble("Chances.Stone");
		main.coal = ((float) getConfig().getDouble("Chances.Coal") + main.stone);
		main.iron = ((float) getConfig().getDouble("Chances.Iron") + main.coal);
		main.gold = ((float) getConfig().getDouble("Chances.Gold") + main.iron);
		main.redstone = ((float) getConfig().getDouble("Chances.Redstone") + main.gold);
		main.lapis = ((float) getConfig().getDouble("Chances.Lapis") + main.redstone);
		main.emerald = ((float) getConfig().getDouble("Chances.Emerald") + main.lapis);
		main.diamond = ((float) getConfig().getDouble("Chances.Diamond") + main.emerald);
	}

	private void loadEnchantments(CommandSender sender) {
		main.enchantmentNames.clear();
		saveDefaultConfig();
		reloadConfig();
		Map map = getConfig().getValues(false);
		StringBuilder builder = new StringBuilder();
		Collection registeredEnchantments = main.enchantmentNames.values();
		Enchantment[] arrayOfEnchantment;
		int localException1 = (arrayOfEnchantment = Enchantment.values()).length;
		for (main.e = 0; main.e < localException1; main.e++) {
			Enchantment enchantment = arrayOfEnchantment[main.e];
			if (!registeredEnchantments.contains(enchantment)) {
				builder.append(enchantment.getName()).append('(').append(enchantment.getId()).append(") ");
			}
		}

		if (builder.length() > 0) {
			builder.insert(0, "Unused enchantments: ").insert(0, ChatColor.YELLOW);
			sender.sendMessage(builder.toString());
		}
	}
}