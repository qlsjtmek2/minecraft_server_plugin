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

					sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
					return false;
				}

				sender.sendMessage(
						ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "] 버킷에선 실행이 불가능 합니다.");
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

							sender.sendMessage("§c이 플레이어는 온라인이 아닙니다.");
							return false;
						}

						sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
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

						sender.sendMessage("§c이 플레이어는 온라인이 아닙니다.");
						return false;
					}

					sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
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

								sender.sendMessage("§c이 플레이어는 온라인이 아닙니다.");
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

								sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE
										+ "] 버킷에선 실행이 불가능 합니다.");
								return false;
							}

							main.help(sender);
							return false;
						}

						sender.sendMessage("§c이 플레이어는 온라인이 아닙니다.");
						return false;
					}

					sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
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

							sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE
									+ "] 버킷에선 실행이 불가능 합니다.");
							return false;
						}

						sender.sendMessage("§c이 플레이어는 온라인이 아닙니다.");
						return false;
					}

					sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
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

							sender.sendMessage("§c이 플레이어는 온라인이 아닙니다.");
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

							sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE
									+ "] 버킷에선 실행이 불가능 합니다.");
							return false;
						}

						main.help(sender);
						return false;
					}

					sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
					return false;
				}
			}
		} catch (Exception e) {
			if ((sender.isOp())) {
				main.help(sender);
				return false;
			} else {
				sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
				return false;
			}
		}

		if (commandLabel.equals("o")) {
			if (sender.isOp()) {
				if ((sender instanceof Player)) {
					Player p = (Player) sender;
					Bukkit.broadcastMessage(p.getName() + "님이 서버를 닫았습니다.");
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
							Bukkit.broadcastMessage("§f[§4알림§f] §6서버가 §c5초 §6후 종료됩니다.");
						}

						else if (main.inte == 4) {
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("§f[§4알림§f] §6서버가 §c4초 §6후 종료됩니다.");
						}

						else if (main.inte == 3) {
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("§f[§4알림§f] §6서버가 §c3초 §6후 종료됩니다.");
						}

						else if (main.inte == 2) {
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("§f[§4알림§f] §6서버가 §c2초 §6후 종료됩니다.");
						}

						else if (main.inte == 1) {
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("§f[§4알림§f] §6서버가 §c1초 §6후 종료됩니다.");
						}

						else if (main.inte <= 0) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
									"kickall 서버가 종료되었습니다.");
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stop");
						}

						--main.inte;
					}
				}, 20L, 20L);

				return false;
			}

			sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
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
				if (commandLabel.equalsIgnoreCase("램")) {
					if (p.isOp() == true) {
						sender.sendMessage(ChatColor.RED + "=============================");
						sender.sendMessage(ChatColor.BLUE + "현재 모든 메모리 상황 통계");
						sender.sendMessage(ChatColor.GREEN + "Total Memory: " + totalMem());
						sender.sendMessage(ChatColor.GREEN + "Free Memory: " + freeMem());
						sender.sendMessage(ChatColor.RED + "Allocated Memory: " + maxMem());
						sender.sendMessage(ChatColor.RED + "=============================");
						return true;
					} else {
						sender.sendMessage("§f[§4경고§f] §c당신은 권한이 없습니다.");
						return true;
					}
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage(ChatColor.RED + "=============================");
				sender.sendMessage(ChatColor.BLUE + "현재 모든 메모리 상황 통계");
				sender.sendMessage(ChatColor.GREEN + "Total Memory: " + totalMem());
				sender.sendMessage(ChatColor.GREEN + "Free Memory: " + freeMem());
				sender.sendMessage(ChatColor.RED + "Allocated Memory: " + maxMem());
				sender.sendMessage(ChatColor.RED + "=============================");
				return true;
			}

			try {
				if ((commandLabel.equalsIgnoreCase("아이템금지")) && (args.length == 0)) {
					p.sendMessage("§6/아이템금지 §f- 도움말을 봅니다.");
					p.sendMessage("§6/아이템금지 추가 <경고메세지> §f- 들고있는 아이템을 사용하지 못하게 합니다.");
					p.sendMessage("§6/아이템금지 추가 모두 <경고메세지> §f- 들고있는 아이템을 데이터 코드도 모두 사용하지 못하게 합니다.");
					p.sendMessage("§6/아이템금지 제거 §f- 들고있는 아이템 금지 여부를 제거합니다.");
					p.sendMessage("§6/아이템금지 확인 §f- 들고있는 아이템 금지여부를 확인합니다.");
					return false;
				} else if ((commandLabel.equalsIgnoreCase("아이템금지")) && (args[0].equalsIgnoreCase("추가"))
						&& (args.length == 1)) {
					if ((p.hasPermission("banitem.add")) || (p.hasPermission("banitem.*")) || (p.isOp())) {
						p.sendMessage("§c명령어를 제대로 사용해주세요. §f- §6/아이템금지 추가 <경고메세지>");
						return false;
					} else {
						p.sendMessage("§c당신은 명령어를 사용할 권한이 없습니다.");
						return false;
					}
				} else if ((commandLabel.equalsIgnoreCase("아이템금지")) && (args[0].equalsIgnoreCase("추가"))
						&& (args[1].equalsIgnoreCase("모두")) && (args.length > 1)) {
					if ((p.hasPermission("banitem.add")) || (p.hasPermission("banitem.*")) || (p.isOp())) {
						if (itemmethod.getnumber() == 0) {
							StringBuilder sb1 = new StringBuilder();
							for (int i = 2; i < args.length; i++) {
								sb1.append(args[i]).append(" ");
							}
							String allArgs1 = sb1.toString();
							main.all.add(id + ":-1:" + allArgs1);

							p.sendMessage("§6아이템 코드 §e[§c" + id + ":*§e] §6는 다음과 같은 이유로 §c금지됩니다§6.:");
							p.sendMessage(" " + allArgs1);

							reloadConfig();
							getConfig().set("Blacklist", main.all);
							saveConfig();
							return false;
						} else {
							p.sendMessage("§e[§c" + id + ":*" + "§e] §6아이템은 이미 금지되어 있습니다.");
							return false;
						}
					} else {
						p.sendMessage("§c당신은 명령어를 사용할 권한이 없습니다.");
						return false;
					}
				} else if ((commandLabel.equalsIgnoreCase("아이템금지")) && (args[0].equalsIgnoreCase("추가"))
						&& (args.length > 1)) {
					if ((p.hasPermission("banitem.add")) || (p.hasPermission("banitem.*")) || (p.isOp())) {
						if (itemmethod.getnumber() == 0) {
							main.all.add(id + ":" + data + ":" + allArgs);

							p.sendMessage("§6아이템코드 §e[§c" + id + ":" + data + "§e] §6는 다음과 같은 이유로 §c금지됩니다§6.:");
							p.sendMessage(" " + allArgs);

							reloadConfig();
							getConfig().set("Blacklist", main.all);
							saveConfig();
							return false;
						} else {
							p.sendMessage("§e[§c" + id + ":" + data + "§e] §6아이템은 이미 금지되어 있습니다.");
							return false;
						}
					} else {
						p.sendMessage("§c당신은 명령어를 사용할 권한이 없습니다.");
						return false;
					}
				} else if ((commandLabel.equalsIgnoreCase("아이템금지")) && (args[0].equalsIgnoreCase("확인"))
						&& (args.length == 1)) {
					if ((p.hasPermission("banitem.*")) || (p.isOp()) || (p.hasPermission("banitem.check"))) {
						if (itemmethod.getnumber() == 1) {
							p.sendMessage("§e[§c" + itemmethod.getId() + ":" + itemmethod.getData()
									+ "§e] §6아이템은 이러한 이유로 금지되어 있습니다:");
							p.sendMessage(" " + itemmethod.getReason());
							return false;
						} else {
							p.sendMessage("§c이 아이템은 금지되어 있지 않습니다.");
							return false;
						}
					} else {
						p.sendMessage("§c당신은 명령어를 사용할 권한이 없습니다.");
						return false;
					}
				} else if ((commandLabel.equalsIgnoreCase("아이템금지"))
						&& ((args[0].equalsIgnoreCase("삭제")) || (args[0].equalsIgnoreCase("제거")))
						&& (args.length == 1)) {
					if ((p.hasPermission("banitem.remove")) || (p.hasPermission("banitem.del"))
							|| (p.hasPermission("banitem.*")) || (p.isOp())) {
						if (itemmethod.getnumber() == 1) {
							main.all.remove(
									itemmethod.getId() + ":" + itemmethod.getData() + ":" + itemmethod.getReason());

							p.sendMessage("§c아이템§6을 성공적으로 §c금지 목록§6에서 제거하였습니다.");
							reloadConfig();
							getConfig().set("Blacklist", main.all);
							saveConfig();
							return false;
						} else {
							p.sendMessage("§c이 아이템은 금지되어 있지 않습니다.");
							return false;
						}
					} else {
						p.sendMessage("§c당신은 명령어를 사용할 권한이 없습니다.");
						return false;
					}
				}
			} catch (Exception e) {
				if ((sender.isOp())) {
					p.sendMessage("§6/아이템금지 §f- 도움말을 봅니다.");
					p.sendMessage("§6/아이템금지 추가 <경고메세지> §f- 들고있는 아이템을 사용하지 못하게 합니다.");
					p.sendMessage("§6/아이템금지 추가 모두 <경고메세지> §f- 들고있는 아이템을 데이터 코드도 모두 사용하지 못하게 합니다.");
					p.sendMessage("§6/아이템금지 제거 §f- 들고있는 아이템 금지 여부를 제거합니다.");
					p.sendMessage("§6/아이템금지 확인 §f- 들고있는 아이템 금지여부를 확인합니다.");
					return false;
				}

				else {
					p.sendMessage("§c당신은 명령어를 사용할 권한이 없습니다.");
					return false;
				}
			}

			try {
				if (commandLabel.equalsIgnoreCase("조합방지")) {
					if (args[0].equalsIgnoreCase("추가")) {
						if (p.hasPermission("blacklist.add")) {
							if (!main.list.contains(Integer.valueOf(p.getItemInHand().getTypeId()))) {
								main.list.add(Integer.valueOf(p.getItemInHand().getTypeId()));
								getConfig().set("blacklist.items", main.list);
								saveConfig();
								p.sendMessage("§c아이템§6을 성공적으로 §c조합을 방지§6시켰습니다.");
							}
						} else
							p.sendMessage("§c이미 조합이 방지되어 있는 아이템 입니다.");
					} else if ((args[0].equalsIgnoreCase("제거"))
							|| (args[0].equalsIgnoreCase("삭제")) && (p.hasPermission("blacklist.remove"))) {
						if (main.list.contains(Integer.valueOf(p.getItemInHand().getTypeId()))) {
							for (int i = 0; i < main.list.size(); i++) {
								if (((Integer) main.list.get(i))
										.equals(Integer.valueOf(p.getItemInHand().getTypeId()))) {
									main.list.remove(i);
								}
							}
							getConfig().set("blacklist.items", main.list);
							saveConfig();
							p.sendMessage("§c아이템§6을 성공적으로 §c조합 방지§6를 제거했습니다.");
						} else {
							p.sendMessage(ChatColor.RED + "§c조합 방지 아이템 목록에 없는 아이템 입니다.");
						}
					}
				}
			} catch (Exception e) {
				if ((sender.isOp())) {
					p.sendMessage("§6/조합방지 추가 §f- 조합 방지할 아이템을 추가합니다.");
					p.sendMessage("§6/조합방지 제거 §f- 조합 방지할 아이템을 제거합니다.");
					return false;
				} else {
					p.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
					return false;
				}
			}

			try {
				if (commandLabel.equalsIgnoreCase("쓰레기통")) {
					main.T.TrashCan(p);
					return true;
				}
			} catch (NumberFormatException ex) {
				main.T.TrashCan(p);
				return true;
			}

			try {
				if (commandLabel.equalsIgnoreCase("채팅청소") || commandLabel.equalsIgnoreCase("cc")) {
					if (p.isOp() == true) {
						for (main.i = 0; main.i < 100; main.i++)
							Bukkit.broadcastMessage("");
					} else {
						p.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
					}
					return true;
				}
			} catch (NumberFormatException ex) {
				for (main.i = 0; main.i < 100; main.i++)
					Bukkit.broadcastMessage("");
				return true;
			}

			try {
				if (commandLabel.equalsIgnoreCase("수표")) {
					String pname = p.getName();
					if (p.getInventory().firstEmpty() == -1) {
						p.sendMessage(ChatColor.RED + "인벤토리 창이 부족하여 수표를 환전하실 수 없습니다.");
						return true;
					}

					if ((main.economy.getBalance(pname) >= Integer.valueOf(args[0]).intValue())
							&& (Integer.valueOf(args[0]).intValue() > 0)) {
						ItemStack item = new ItemStack(main.config_itemcode, 1);
						ItemMeta itemM = item.getItemMeta();
						itemM.setDisplayName("§e§a§c§6§o 수표");
						itemM.setLore(Arrays.asList(new String[] { "§f§oㅡㅡㅡㅡㅡㅡㅡㅡ", args[0], "§f§oㅡㅡㅡㅡㅡㅡㅡㅡ", "§f",
								"§7§o오른쪽 클릭으로", "§7§o돈으로 환전 가능", "§7", "§7§oby. §a§oESPOO 은행" }));
						item.setItemMeta(itemM);
						p.getInventory().addItem(new ItemStack[] { item });
						main.economy.withdrawPlayer(pname, Double.valueOf(args[0]).doubleValue());
						p.sendMessage("§6정상적으로 발급되었습니다.");
						return true;
					}

					p.sendMessage(ChatColor.RED + "발행하고 싶은 돈이 보유한 돈보다 더 많거나 0보다 작은 값을 입력하였습니다.");
					return true;
				}
			} catch (Exception e) {
				p.sendMessage("§6/수표 (돈) §f- 수표를 발행합니다.");
				return false;
			}

			try {
				if (cmd.getName().equalsIgnoreCase("농사보조")) {
					if (args.length > 0) {
						if (args[0].equalsIgnoreCase("Reload")) {
							if (!sender.hasPermission("farmassist.reload")) {
								p.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
								return true;
							}
							main.loadYamls();
							sender.sendMessage("§f[§4알림§f] §aconfig 파일을 리로드 하였습니다.");
							return true;
						}

						if (args[0].equalsIgnoreCase("Toggle")) {
							if (!sender.hasPermission("farmassist.toggle")) {
								p.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
								return true;
							}

							if (main.playerList.contains(sender.getName())) {
								main.playerList.remove(sender.getName());
								sender.sendMessage("§f[§4알림§f] §aFarmAssist 기능이 정상 활성화 되었습니다.");
							} else {
								main.playerList.add(sender.getName());
								sender.sendMessage("§f[§4알림§f] §cFarmAssist 기능이 비활성화 되었습니다.");
							}
							return true;
						}

						if (args[0].equalsIgnoreCase("Global")) {
							if (!sender.hasPermission("farmassist.toggle.global")) {
								p.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
								return true;
							}

							if (!main.Enabled) {
								main.Enabled = true;
								sender.sendMessage("§f[§4알림§f] §aFarmAssist 글로벌 기능을 활성화 했습니다!");
							} else {
								main.Enabled = false;
								sender.sendMessage("§f[§4알림§f] §cFarmAssist 글로벌 기능을 비활성화 했습니다!");
							}
							return true;
						}
					}
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("§6/농사보조 Toggle §f- 씨앗 자동 심기 기능을 끄거나 킵니다.");
				sender.sendMessage("§6/농사보조 Global §f- 씨앗 자동 심기 글로벌 기능을 끄거나 킵니다.");
				return true;
			}

			try {
				if (commandLabel.equals("정지")) {
					if (p.hasPermission("AllPlayer.admin")) {
						if (!main.stopE) {
							Bukkit.broadcastMessage("§f[§4알림§f] §6움직일 수 없게 설정되었습니다.");
							main.stopE = true;
							return true;
						}

						Bukkit.broadcastMessage("§f[§4알림§f] §6움직일 수 있게 설정되었습니다.");
						main.stopE = false;
						return true;
					}

					p.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
					return true;
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("§6/정지 §f- 모든 플레이어를 정지 시킵니다.");
				return true;
			}

			try {
				if (commandLabel.equals("인첸트목록")) {
					if ((args.length > 0)) {
						if (args[0].equalsIgnoreCase("보호")) {
							p.sendMessage("§f[§a인첸트§f] §b보호계열 : 0 보호 1 화염보호 2 가벼운 착지 3 폭발보호 4 원거리 보호 5 호흡 6 친수성 7 가시");
						}

						if (args[0].equalsIgnoreCase("활")) {
							p.sendMessage("§f[§a인첸트§f] §b활계열 : 48 힘 49 밀어내기 50 화염 51 무한");
						}
					}
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("§6/인첸트목록 보호 §f- 보호 인첸트 코드를 봅니다.");
				sender.sendMessage("§6/인첸트목록 활 §f- 활 인첸트 코드를 봅니다.");
				return true;
			}

			try {
				if (commandLabel.equals("블럭")) {
					if (p.hasPermission("AllPlayer.admin")) {
						if (!main.blockE) {
							main.blockE = true;
							Bukkit.broadcastMessage("§f[§4알림§f] §6블럭을 부술수 없게 설정되었습니다.");
							return true;
						}

						main.blockE = false;
						Bukkit.broadcastMessage("§f[§4알림§f] §6블럭을 부술수 있게 설정되었습니다.");
						return true;
					}

					p.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
					return true;
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("§6/블럭 §f- 모든플레이어가 블럭을 캘 수 없게 됩니다.");
				return true;
			}

			try {
				if (commandLabel.equals("무적")) {
					if (p.hasPermission("AllPlayer.admin")) {
						if (!main.healthE) {
							main.healthE = true;
							Bukkit.broadcastMessage("§f[§4알림§f] §6무적상태로 변경되었습니다.");
							return true;
						}

						main.healthE = false;
						Bukkit.broadcastMessage("§f[§4알림§f] §6일반상태로 변경되었습니다.");
						return true;
					}

					p.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
					return true;
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("§6/무적 §f- 모든플레이어가 무적이 됩니다.");
				return true;
			}

			try {
				if (commandLabel.equals("채팅")) {
					if (p.hasPermission("AllPlayer.admin")) {
						if (!main.chatE) {
							main.chatE = true;
							Bukkit.broadcastMessage("§f[§4알림§f] §6채팅을 못하도록 설정되었습니다.");
							return true;
						}

						main.chatE = false;
						Bukkit.broadcastMessage("§f[§4알림§f] §6채팅을 할수있게 설정되었습니다.");
						return true;
					}
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("§6/채팅 §f- 모든플레이어가 채팅을 할 수 없게 됩니다.");
				return true;
			}

			try {
				if (commandLabel.equals("만렙")) {
					if (p.hasPermission("AllPlayer.admin")) {
						if (args.length == 0) {
							if (!main.levelE) {
								main.levelE = true;
								Bukkit.broadcastMessage("§f[§4알림§f] §6만렙이 설정되었습니다.");
								Bukkit.broadcastMessage(ChatColor.GOLD + "만렙 : " + main.levelF);
								return true;
							}

							main.levelE = false;
							Bukkit.broadcastMessage("§f[§4알림§f] §6만렙이 해제되었습니다.");
							return true;
						}

						if (args.length == 1) {
							main.levelF = Integer.parseInt(args[0]);
							Bukkit.broadcastMessage("§f[§4알림§f] §6만렙이 §c" + main.levelF + "§6로 설정되었습니다.");
							return true;
						}

						p.sendMessage(ChatColor.RED + "명령어 오류");
						p.sendMessage(ChatColor.RED + "/만렙 - 만렙을 설정 또는 해제합니다.");
						p.sendMessage(ChatColor.RED + "/만렙 <0 이상의 숫자> - 만렙을 <0 이상의 숫자>로 설정합니다.");
						return true;
					}
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("§6/만렙 §f- 만렙을 설정 또는 해제합니다.");
				sender.sendMessage("§6/만렙 <0 이상의 숫자> §f- 만렙을 <0 이상의 숫자>로 설정합니다.");
				return true;
			}

			try {
				if (commandLabel.equals("인첸트")) {
					if (args.length > 0) {
						if (args[0].equalsIgnoreCase("재시작")) {
							if (sender.hasPermission("enchanter.reload")) {
								loadEnchantments(sender);
								Bukkit.broadcastMessage("§f[§4알림§f] §a인첸트 플러그인 목록 재시작");
								return true;
							}
							sender.sendMessage("§c제대로된 인자값을 입력해주세요.");
							return true;
						}

						if (p.hasPermission("enchanter.enchant")) {
							if (args[0].equalsIgnoreCase("모두")) {
								boolean dirty = (args.length <= 1) || (!args[1].equalsIgnoreCase("정상"));
								for (Enchantment enchantment : Enchantment.values()) {
									main.enchantItem(p, enchantment, dirty ? 32767 : enchantment.getMaxLevel());
								}
								sender.sendMessage("§c모든 인첸트§6를 §c32767§6씩 설정했습니다.");
								return true;
							}
							int targetLevel = 1;
							if (args.length > 1) {
								try {
									targetLevel = Integer.valueOf(args[1]).intValue();
								} catch (NumberFormatException e) {
									if (args[1].equalsIgnoreCase("최대")) {
										targetLevel = -1;
									}
								}
							}
							EnchantmentResult result = main.enchantItem(p, args[0], targetLevel);
							switch (result) {
							case CANNOT_ENCHANT:
								sender.sendMessage("§c그것은 인첸트 ID가 아닙니다.");
								break;
							}

							sender.sendMessage("§c아이템§6이 정상적으로 §c인첸트§6가 되었습니다.");
							return true;
						}
						return true;
					}
				}
			} catch (NumberFormatException ex) {
				sender.sendMessage("§6/인첸트 모두 §f- 자신이 들고있는것에 모든 인첸트를 부여합니다.");
				sender.sendMessage("§6/인첸트 모두 정상 §f- 자신이 들고 있는것에 정상적으로 모든 인첸트를 부여합니다.");
				sender.sendMessage("§6/인첸트 <코드> <레벨> §f- 해당 무기에 코드만큼 레벨을 부여합니다.");
				sender.sendMessage("§6/인첸트 <코드> 최대 §f- 해당 무기에 코드 만큼 레벨을 최대로 부여합니다.");
				return true;
			}
		}

		else
			sender.sendMessage(
					ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "] 버킷에선 실행이 불가능 합니다.");
		return true;
	}

	private void displayHelp(CommandSender sender) {
		sender.sendMessage("§6/save §f- 모든 월드를 저장합니다.");
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