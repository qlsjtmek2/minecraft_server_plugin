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
                		Method.sendCommand("1단계골드큐브 " + args[1] + "");
                		Method.sendCommand("rpgitem 소의가죽 give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 15 0 농사용 소");
                		break;
                		
                	case "gold1-2":
                		Method.sendCommand("1단계골드큐브 " + args[1] + "");
                		Method.sendCommand("rpgitem 냄새나는돼지고기 give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 15 0 가축용 돼지");
                		break;
                		
                	case "gold2":
                		Method.sendCommand("2단계골드큐브 " + args[1] + "");
                		Method.sendCommand("rpgitem 슬라임의영혼 give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 40 0 말랑한 슬라임");
                		break;
                		
                	case "gold3":
                		Method.sendCommand("3단계골드큐브 " + args[1] + "");
                		Method.sendCommand("rpgitem 좀비의살코기 give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 80 0 단단한 좀비");
                		break;
                		
                	case "gold4":
                		Method.sendCommand("4단계골드큐브 " + args[1] + "");
                		Method.sendCommand("rpgitem 돼지좀비가죽 give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 160 0 단단한 돼지좀비");
                		break;
                		
                	case "gold5":
                		Method.sendCommand("5단계골드큐브 " + args[1] + "");
                		Method.sendCommand("rpgitem 해골의뼈 give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 320 0 캐리비안 해골");
                		break;
                		
                	case "gold6":
                		Method.sendCommand("6단계골드큐브 " + args[1] + "");
                		Method.sendCommand("rpgitem 거미줄 give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 540 0 스파이더");
                		break;
                		
                	case "gold7":
                		Method.sendCommand("7단계골드큐브 " + args[1] + "");
                		Method.sendCommand("rpgitem 동굴석영 give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 800 0 음침한 스파이더");
                		break;
                		
                	case "gold8":
                		Method.sendCommand("8단계골드큐브 " + args[1] + "");
                		Method.sendCommand("rpgitem 광견의다리뼈 give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 1200 0 광견");
                		break;
                		
                	case "gold9":
                		Method.sendCommand("9단계골드큐브 " + args[1] + "");
                		Method.sendCommand("rpgitem 아이언 give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 1800 0 괴력의 골렘");
                		break;
                		
                	case "gold10":
                		Method.sendCommand("10단계골드큐브 " + args[1] + "");
                		Method.sendCommand("rpgitem 아기의눈물 give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 3000 0 베이비 언데드");
                		break;
                		
                	case "gold11":
                		Method.sendCommand("11단계골드큐브 " + args[1] + "");
                		Method.sendCommand("rpgitem 해적의석궁 give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 4400 0 해적");
                		break;
                		
                	case "gold12":
                		Method.sendCommand("12단계골드큐브 " + args[1] + "");
                		Method.sendCommand("rpgitem 해킹툴 give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 5200 0 바이러스");
                		break;
                		
                	case "gold13":
                		Method.sendCommand("13단계골드큐브 " + args[1] + "");
                		Method.sendCommand("rpgitem 박쥐의오물 give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 6400 0 사악한 언데드");
                		break;
                		
                	case "gold14":
                		Method.sendCommand("14단계골드큐브 " + args[1] + "");
                		Method.sendCommand("rpgitem 포탈의파편 give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 7400 0 엔더 포탈");
                		break;
                		
                	case "gold15":
                		Method.sendCommand("15단계골드큐브 " + args[1] + "");
                		Method.sendCommand("rpgitem 데저트이글 give " + args[1] + " 1");
                		Method.sendCommand("expgive " + args[1] + " 6400 0 서부의 총잡이");
                		break;
                		
                	case "goldevent":
                		Method.sendCommand("rpgitem 아크원자로 give " + args[1] + " 1");
                		Method.sendCommand("eco give " + args[1] + " 400");
                		Method.sendCommand("expgive " + args[1] + " 200 0 AI-아이언맨");
                		break;
                		
                	case "체포1":
                		Method.sendCommand("rpgitem 체력포션1 give " + args[1] + " 1");
                		break;
                		
                	case "체포2":
                		Method.sendCommand("rpgitem 체력포션2 give " + args[1] + " 1");
                		break;
                		
                	case "체포3":
                		Method.sendCommand("rpgitem 체력포션3 give " + args[1] + " 1");
                		break;
                		
                	case "체포4":
                		Method.sendCommand("rpgitem 체력포션4 give " + args[1] + " 1");
                		break;
                		
                	case "체포5":
                		Method.sendCommand("rpgitem 체력포션5 give " + args[1] + " 1");
                		break;
                		
                	case "마포1":
                		Method.sendCommand("rpgitem 마나포션1 give " + args[1] + " 1");
                		break;
                		
                	case "마포2":
                		Method.sendCommand("rpgitem 마나포션2 give " + args[1] + " 1");
                		break;
                		
                	case "마포3":
                		Method.sendCommand("rpgitem 마나포션3 give " + args[1] + " 1");
                		break;
                		
                	case "마포4":
                		Method.sendCommand("rpgitem 마나포션4 give " + args[1] + " 1");
                		break;
                		
                	case "마포5":
                		Method.sendCommand("rpgitem 마나포션5 give " + args[1] + " 1");
                		break;
                		
                	case "최상급소켓석":
                		if (Method.getOnorOffLine(args[1]) != null) {
                			Method.getOnorOffLine(args[1]).sendMessage("§f[§a안내§f] §6축하합니다!! §c최상급 소켓석 §6을 부두술사 보스가 드랍하였습니다.");
                			Method.sendCommand("rpgitem 최상급소켓석 give " + args[1] + " 1");
                		} break;
                		
                	case "언데드사망알림":
                		main.UDD = true;
                		
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("§c[ 미쳐버린 언데드 ] 가 사망했습니다. 10분 후 재스폰 됩니다.");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		
            			timer = new PlayerTimer("UDD", new Runnable() {
            				@Override
            				public void run() {
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("§e[ 미쳐버린 언데드 ] §6가 재스폰 되었습니다!");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Method.sendCommand("eb boss spawn 미쳐버린_언데드 미쳐버린_언데드");
                        		main.UDD = false;
            				}
            			});
            			
            			timer.setTime(1);
            			timer.Start();
                		break;
                		
                	case "스켈레톤사망알림":
                		main.SKLT = true;
                		
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("§c[ 각성한 스켈레톤 ] 이 사망했습니다. 10분 후 재스폰 됩니다.");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		
                		timer = new PlayerTimer("SKLT", new Runnable() {
            				@Override
            				public void run() {
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("§e[ 각성한 스켈레톤 ] §6이 재스폰 되었습니다!");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Method.sendCommand("eb boss spawn 각성한_스켈레톤 각성한_스켈레톤");
                        		main.SKLT = false;
            				}
            			});
            			
            			timer.setTime(1);
            			timer.Start();
                		break;
                		
                	case "위더사망알림":
                		main.WD = true;
                		
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("§c[ 진화한 위더 ] 가 사망했습니다. 10분 후 재스폰 됩니다.");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		
                		timer = new PlayerTimer("WD", new Runnable() {
            				@Override
            				public void run() {
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("§e[ 진화한 위더 ] §6가 재스폰 되었습니다!");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Method.sendCommand("eb boss spawn 진화한_위더 진화한_위더");
                        		main.WD = false;
            				}
            			});
            			
            			timer.setTime(1);
            			timer.Start();
                		break;
                		
                	case "베드락사망알림":
                		main.BDL = true;
                		
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("§c[ 깨어난 베드락 ] 이 사망했습니다. 10분 후 재스폰 됩니다.");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		
                		timer = new PlayerTimer("BDL", new Runnable() {
            				@Override
            				public void run() {
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("§e[ 깨어난 베드락 ] §6이 재스폰 되었습니다!");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Method.sendCommand("eb boss spawn 깨어난_베드락 깨어난_베드락");
                        		main.BDL = false;
            				}
            			});
            			
            			timer.setTime(1);
            			timer.Start();
                		break;
                		
                	case "부두술사사망알림":
                		main.BD = true;
                		
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("§c[ 부두술사 ] 가 피를 토하며 쓰러졌습니다. 10분 후 재스폰 됩니다.");
                		Bukkit.broadcastMessage("");
                		Bukkit.broadcastMessage("");
                		Method.sendCommand("부두술사실행 변수초기화");
                		
                		timer = new PlayerTimer("BD", new Runnable() {
            				@Override
            				public void run() {
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("§e[ 부두술사 ] §6가 모든 상태이상을 회복했습니다! ( 부활 )");
                        		Bukkit.broadcastMessage("");
                        		Bukkit.broadcastMessage("");
                        		Method.sendCommand("eb boss spawn 부두술사 부두술사");
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
			
			if (commandLabel.equalsIgnoreCase("머리") || commandLabel.equalsIgnoreCase("nbth")) {
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
			
			else if (commandLabel.equalsIgnoreCase("베드락확인")) {
				if (main.BDL) {
                	p.sendMessage("§c현재 깨어난 베드락이 죽어있는 상태입니다.");
                } else {
                	p.sendMessage("§6! 깨어난 베드락이 살아있습니다.");
                } return true;
			}
			
			else if (commandLabel.equalsIgnoreCase("언데드확인")) {
				if (main.UDD) {
                	p.sendMessage("§c현재 미쳐버린 언데드가 죽어있는 상태입니다.");
                } else {
                	p.sendMessage("§6! 미쳐버린 언데드가 살아있습니다.");
                } return true;
			}
			
			else if (commandLabel.equalsIgnoreCase("위더확인")) {
				if (main.WD) {
					p.sendMessage("§c현재 진화한 위더가 죽어있는 상태입니다.");
				} else {
					p.sendMessage("§6! 진화한 위더가 살아있습니다.");
				} return true;
			}
			
			else if (commandLabel.equalsIgnoreCase("부두확인")) {
				if (main.BD) {
                	p.sendMessage("§c현재 부두술사가 죽어있는 상태입니다.");
                } else {
                	p.sendMessage("§6! 부두술사가 살아있습니다.");
                } return true;
			}
			
			else if (commandLabel.equalsIgnoreCase("스켈레톤확인")) {
				if (main.BD) {
                	p.sendMessage("§c각성한 스켈레톤이 죽어있는 상태입니다.");
                } else {
                	p.sendMessage("§6! 각성한 스켈레톤이 살아있습니다.");
                } return true;
			}
			
			else if (commandLabel.equalsIgnoreCase("전직안정")) {
				if (p.isOp()) {
	                if (args.length == 2) {
	                	File f = new File("plugins/ActionRPG/Player/" + args[1] + ".yml");
	                	if (f.exists()) {
	                		Method.setInfoInt(p, "전직 카운트", Integer.parseInt(args[0]));
	                	} else {
	                		p.sendMessage("§c그 플레이어는 존재하지 않습니다");
	                		return false;
	                	}
	                } else {
	                	p.sendMessage("§6/전직안정 <0/1/2/3> <플레이어>");
                		return false;
	                }
				} return false;
			}
		
			else if (commandLabel.equalsIgnoreCase("in")) {
				if (p.isOp()) {
					if (main.in.get(p.getName()) != null) {
						p.sendMessage("§6아이템 이름 보기 모드가 §c해제§6되었습니다.");
						main.in.remove(p.getName());
						return false;
					} else {
						p.sendMessage("§6아이템 이름 보기 모드가 §c설정§6되었습니다.");
						main.in.put(p.getName(), "true");
						return false;
					}
				} return false;
			}
		
			else if (commandLabel.equalsIgnoreCase("gp")) {
				if (p.isOp()) {
					if (main.gp.get(p.getName()) != null) {
						p.sendMessage("§6좌표 보기 모드가 §c해제§6되었습니다.");
						main.gp.remove(p.getName());
						return false;
					} else {
						p.sendMessage("§6좌표 보기 모드가 §c설정§6되었습니다.");
						main.gp.put(p.getName(), "true");
						return false;
					}
				} return false;
			}
			
			else if (commandLabel.equals("표지판")) {
				Player play = (Player)sender;
				if (!play.hasPermission("se.edit")) {
					sender.sendMessage("§f[§4경고§f] §c당신은 권한이 없습니다.");
					return true;
				}
		
				Block b = play.getTargetBlock(null, 10);
				if (b == null) {
					sender.sendMessage("§f[§4경고§f] §c당신의 앞에 표지판이 없습니다.");
					return true;
				}
				
				if (!(b.getState() instanceof Sign)) {
					sender.sendMessage("§f[§4경고§f] §c당신의 앞에 표지판이 없습니다.");
					return true;
				}
	
				if (args.length == 0) {
					sender.sendMessage("§f[§4경고§f] §c행 번호와 표지판 내용을 적어주세요.");
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
					sender.sendMessage("§f[§4경고§f] §c라인 수를 1~4로 적어주세요.");
					return true;
				}
	
				line--;
				String message = "";
				for (int i = 1; i < args.length; i++) message = message + " " + args[i];
				if (play.hasPermission("se.editCol")) message = message.replaceAll("([^\\\\](\\\\\\\\)*)&(.)", "$1§$3").replaceAll("(([^\\\\])\\\\((\\\\\\\\)*))&(.)", "$2$3&$5").replaceAll("\\\\\\\\", "\\\\");
				message = message.trim();
				if (message.length() > 15) {
					sender.sendMessage("§f[§4경고§f] §c표지판 내용이 너무 깁니다.");
					return true;
				}
				Bukkit.getScheduler().runTaskAsynchronously(this, new Runner(play, line, message, b));
				return true;
			}
			
			else if (commandLabel.equals("물건구매"))
			{
				if (!sender.isOp()) {
					sender.sendMessage(ChatColor.RED + "당신은 명령어를 사용할 권한이 없습니다.");
					return false;
				}
				
				if (!(sender instanceof Player)) {
					return false;
				}
				
				if (args.length == 4) {
					p.closeInventory();
					String str = args[2].replaceAll("_", " ");
					p.sendMessage("§6몇 개의 §c" + str + " §6을/를 구매하시겠습니까?");
					p.sendMessage("§e( 물건 개당 §f" + args[1] + "§e 개 )");
					main.shop.put(p.getName(), Integer.parseInt(args[1]));
					main.buyme.put(p.getName(), args[0]);
					main.buyto.put(p.getName(), str);
					main.buytype.put(p.getName(), Integer.parseInt(args[3]));
					return false;
				}
			}
			
			else if (commandLabel.equalsIgnoreCase("전직하자")) {
				if (p.hasPermission("*")) {
					RpgPlayer player = Rpg.getRpgPlayera(p.getName());
					
					if (args[0].equalsIgnoreCase("전사") || args[0].equalsIgnoreCase("마법사") || args[0].equalsIgnoreCase("파이터") || args[0].equalsIgnoreCase("도적") || args[0].equalsIgnoreCase("궁수")) {
						if (Method.getInfoString(p, "전직").equals("NONE")) {
							String Upwork = args[0];
							Method.setInfoString(p, "전직", Upwork);
							Method.setInfoInt(p, "전직 카운트", 1);
							p.sendMessage("§6당신은 §e[ " + Upwork  + " ] §6전직을 §a선택§6하셨습니다!");
							Method.PlayerManuadd(p.getName(), Upwork);
							p.getInventory().clear();
							
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/칭호 추가 " + p.getName() + " &f[&e모험가&f]");
							p.chat("/칭호 대표 0");
							Method.removeOP(p, is);
							
							main.TTla1.put(p.getName(), new BukkitRunnable()
							{
								public void run()
								{
									p.chat("/섬");
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
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초보자용모자 give " + p.getName() + " 1");
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초보자용갑옷 give " + p.getName() + " 1");
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초보자용레깅스 give " + p.getName() + " 1");
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 초보자용부츠 give " + p.getName() + " 1");
									p.chat("/무기");
									main.TTla3.put(p.getName(), new BukkitRunnable()
									{
										public void run()
										{
											p.kickPlayer("전직 후에는 재접속이 의무적으로 되어있습니다.");
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
					
					else if (args[0].equalsIgnoreCase("귀검사") || args[0].equalsIgnoreCase("나이트") || args[0].equalsIgnoreCase("라이온") || args[0].equalsIgnoreCase("빙결사") || args[0].equalsIgnoreCase("메카닉") ||
							 args[0].equalsIgnoreCase("프라임") || args[0].equalsIgnoreCase("카오스") || args[0].equalsIgnoreCase("세인트") || args[0].equalsIgnoreCase("아바돈") || args[0].equalsIgnoreCase("어벤져")) {
						if (Method.getInfoInt(p, "전직 카운트") == 1) {
							if (player.getRpgLevel() < 59) {
								p.sendMessage("§c당신은 요구 조건을 모두 만족하지 못하였습니다.");
								return false;
							}

							String Upwork = args[0];
							Method.setInfoString(p, "전직", Upwork);
							Method.setInfoInt(p, "전직 카운트", 2);
							p.sendMessage("§a축하드립니다! §e[ §c" + Upwork  +" §e] §a(으)로 전직을 완료하셨습니다.");
							p.sendMessage("§6새로운 스킬이 생겼습니다. §e[ /스킬 목록 §e] §6으로 확인해주세요.");
							Method.PlayerManuadd(p.getName(), Upwork);
							
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/칭호 추가 " + p.getName() + " &f[&3기사단&f]");
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
									p.kickPlayer("전직 후에는 재접속이 의무적으로 되어있습니다.");
									Integer id = main.Zunzec.remove(p.getName());
									Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 60L, 60L).getTaskId());
							return false;
						} else {
							p.sendMessage("§c당신은 요구 조건을 모두 만족하지 못하였습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("버서커") || args[0].equalsIgnoreCase("팔라딘") || args[0].equalsIgnoreCase("메이지") || args[0].equalsIgnoreCase("위자드") || args[0].equalsIgnoreCase("레인저") ||
							 args[0].equalsIgnoreCase("스니커") || args[0].equalsIgnoreCase("세이버") || args[0].equalsIgnoreCase("가디언") || args[0].equalsIgnoreCase("테이커") || args[0].equalsIgnoreCase("블러드")) {
						if (Method.getInfoInt(p, "전직 카운트") == 2) {
							if (player.getRpgLevel() < 119) {
								p.sendMessage("§c당신은 요구 조건을 모두 만족하지 못하였습니다.");
								return false;
							}
							
							String Upwork = args[0];
							Method.setInfoString(p, "전직", Upwork);
							Method.setInfoInt(p, "전직 카운트", 3);
							p.sendMessage("§a축하드립니다! §e[ §c" + Upwork + " §e]§a로 전직을 완료하셨습니다.");
							p.sendMessage("§6새로운 스킬이 생겼습니다. §e[ /스킬 목록 §e] §6으로 확인해주세요.");
							Method.PlayerManuadd(p.getName(), Upwork);
							
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/칭호 추가 " + p.getName() + " &f[&c성기사&f]");
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
									p.kickPlayer("전직 후에는 재접속이 의무적으로 되어있습니다.");
									Integer id = main.Zunzec.remove(p.getName());
									Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 60L, 60L).getTaskId());
							return false;
						} else {
							p.sendMessage("§c당신은 요구 조건을 모두 만족하지 못하였습니다.");
							return false;
						}
					}
					
					else if (args[0].equalsIgnoreCase("루이스") || args[0].equalsIgnoreCase("카이저") || args[0].equalsIgnoreCase("루시엘") || args[0].equalsIgnoreCase("마인드") || args[0].equalsIgnoreCase("미라나") ||
							 args[0].equalsIgnoreCase("트루퍼") || args[0].equalsIgnoreCase("마스터") || args[0].equalsIgnoreCase("템플러") || args[0].equalsIgnoreCase("에스퍼") || args[0].equalsIgnoreCase("사이커")) {
						if (Method.getInfoInt(p, "전직 카운트") == 3) {
							if (player.getRpgLevel() < 239) {
								p.sendMessage("§c당신은 요구 조건을 모두 만족하지 못하였습니다.");
								return false;
							}
							
							String Upwork = args[0];
							Method.setInfoString(p, "전직", Upwork);
							Method.setInfoInt(p, "전직 카운트", 4);
							Bukkit.broadcastMessage("§e----------------------------------------------------------------");
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("§6축하해주세요! §e[ " + p.getName() + " ] §6님이 §c" + Upwork + "§6로 §c최종 전직§6을 완료하셨습니다.");
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("§e----------------------------------------------------------------");
							p.sendMessage("§6새로운 스킬이 생겼습니다. §e[ /스킬 목록 §e] §6으로 확인해주세요.");
							Method.PlayerManuadd(p.getName(), Upwork);
							
							boolean is = p.isOp();
							p.setOp(true);
							p.chat("/칭호 추가 " + p.getName() + " &f[&3기사&b단장&f]");
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
									p.kickPlayer("전직 후에는 재접속이 의무적으로 되어있습니다.");
									Integer id = main.Zunzec.remove(p.getName());
									Bukkit.getServer().getScheduler().cancelTask(id);
									return;
								}
							}.runTaskTimer(Bukkit.getPluginManager().getPlugin("ActionRPG"), 60L, 60L).getTaskId());
							return false;
						} else {
							p.sendMessage("§c당신은 요구 조건을 모두 만족하지 못하였습니다.");
							return false;
						}
					}
				}
			}
		} else {
			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + 
							   ChatColor.WHITE + "]" + ChatColor.RED + " 콘솔에선 실행이 불가능 합니다.");
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
					this.play.sendMessage("§cLogBlock이 블록의 레코드를 제공하지 않습니다.");
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
