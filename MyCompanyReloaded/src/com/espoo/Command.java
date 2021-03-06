package com.espoo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Command extends JavaPlugin implements CommandExecutor, Listener {
	@SuppressWarnings("unused")
	private final Main plugin;

	public Command(Main instance)
	{
		this.plugin = instance;
	}
	
	@SuppressWarnings("unused")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "] 버킷에선 실행이 불가능 합니다.");
			return false;
		}
		
		Player p = (Player) sender;
        String n = p.getName();
        Boolean Master = UserData.getBooleanYml(p, "사장");
        Boolean User = UserData.getBooleanYml(p, "직원");
        
		if(commandLabel.equalsIgnoreCase("회사")) {
			if(args.length == 0 || args[0].equalsIgnoreCase("1")) {
				if(Master == false && (!p.hasPermission("MyCompany.*"))) {
					sender.sendMessage(" §e---- §6회사 §e-- §6페이지 §c1§6/§c1 §e----");
				} else {
					sender.sendMessage(" §e---- §6회사 §e-- §6페이지 §c1§6/§c2 §e----");
				}
				sender.sendMessage("§6/회사 창립 <이름> §f- 회사를 창립합니다. §b( 돈 2천만원 소요 )");
				sender.sendMessage("§6/회사 파산 §f- 회사를 파산합니다.");
				sender.sendMessage("§6/회사 초대 <닉네임> §f- <닉네임>에게 회사 가입을 권유합니다.");
				sender.sendMessage("§6/회사 수락 §f- 가입 권유를 수락합니다.");
				sender.sendMessage("§6/회사 거절 §f- 가입 권유를 거절합니다.");
				sender.sendMessage("§6/회사 취직 <회사> <각오> §f- 회사에게 자기소개서를 제출합니다.");
				sender.sendMessage("§6 - §f띄어쓰기는 \"§e_§f\"로 표기해주세요.");
				sender.sendMessage("§6/회사 취소 <회사> §f- <회사>의 취직 이력서를 취소합니다.");
				sender.sendMessage("§6/회사 정보 <회사> §f- 회사의 정보를 봅니다.");
				sender.sendMessage("§6/회사 목록 §f- 자신의 회사 사람들을 봅니다.");
				sender.sendMessage("§6/회사 채팅 §f- 회사 전용 채팅을 켭니다.");
				sender.sendMessage("§6/회사 사표 §f- 자신이 가입해있는 회사를 나갑니다.");
				sender.sendMessage("§6/회사 랭킹 §f- 회사의 전체 랭킹을 봅니다.");
				sender.sendMessage("§6/회사 도움말 §f- 회사 시스템의 기본적인 도움말을 봅니다.");
				if(Master == false && (!p.hasPermission("MyCompany.*"))) {
					return false;
				} else {
					sender.sendMessage("§6명령어 §c회사 2 §6를 쳐서 다음페이지로 넘어가세요.");
				}
				return false;
			}
			
			else if((args[0].equalsIgnoreCase("2")) && (Master == true) || 
					(args[0].equalsIgnoreCase("2")) && (p.hasPermission("MyCompany.*"))) {
				sender.sendMessage(" §e---- §6회사 §e-- §6페이지 §c2§6/§c2 §e----");
				sender.sendMessage("§6/회사 일 <닉네임> <급여> §f- <닉네임>에게 급여를 주고 일을 권유합니다.");
				sender.sendMessage("§6/회사 신청목록  §f- 우리 회사의 신청한 사람들 목록을 봅니다.");
				sender.sendMessage("§6/회사 가입허용 <닉네임> §f- 가입 신청을 허용합니다.");
				sender.sendMessage("§6/회사 가입거부 <닉네임> §f- 가입 신청을 거부합니다.");
				sender.sendMessage("§6/회사 정보 이름 <이름> §f- 회사 이름을 변경합니다. §b( 돈 5백만원 소요 )");
				sender.sendMessage("§6/회사 정보 설명 추가 <메세지> §f- 회사 정보 설명을 추가합니다.");
				sender.sendMessage("§6/회사 정보 설명 제거 <줄> §f- 회사 정보 설명을 제거합니다.");
				sender.sendMessage("§6/회사 가입조건 <공개|신청|금지> §f- 회사 가입조건을 변경합니다.");
				sender.sendMessage("§6/회사 위임 <닉네임> §f- 자신의 회사를 <닉네임>에게 위임합니다.");
				sender.sendMessage("§6/회사 퇴직 <닉네임> §f- <닉네임>을 회사에서 강제로 퇴직시킵니다.");
				sender.sendMessage("§6/회사 업그레이드 §f- 다음 단계로 회사를 업그레이드 합니다.");
				sender.sendMessage("§6/회사 업그레이드 목록 §f- 회사의 업그레이드 목록을 봅니다.");
				sender.sendMessage("§6/회사 직급 설정 <직급> <닉네임> §f- <닉네임>의 직급을 설정합니다.");
				sender.sendMessage("§6/회사 직급 시간 <직급> <시간> §f- 몇 분이 지나면 직급이 되는지 설정합니다.");
				sender.sendMessage("§6/회사 직급 목록 §f- 회사의 직급 목록을 봅니다.");
				return false;
			}
			
			else if(args[0].equalsIgnoreCase("창립")) {
				if(args.length == 2) {
					if (Master == false && User == false) {
						if (args[1].matches("[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9_]+")) {
							double balance = Main.economy.getBalance(n);
							if (balance > 20000000) {
								String str = args[1];
								if (Methods.getComBoolean(p, args[1])) {
									sender.sendMessage("§c이미 같은 이름을 가진 회사가 있습니다.");
									return true;
								}
								
								try {
									Company.CreateComFile(n, args[1]);
								} catch (IOException e) {}
								Main.economy.withdrawPlayer(n, 20000000).transactionSuccess();
								UserData.SetBooleanData(p, "사장", true);
								Methods.addComList(p, args[1]);
								UserData.SetStringData(p, "회사", args[1]);
								sender.sendMessage("§6성공적으로 §c" + args[1] + " §6회사 를 생성했습니다.");
								return true;	
							} else {
								sender.sendMessage("§c당신은 회사를 창립하기위한 돈이 부족합니다. §b( 2천만원 소요 )");
								return true;
							}
						} else {
							sender.sendMessage("§c사용이 불가능한 문자가 포함되어 있습니다.");
							return true;
						}
					} else {
						sender.sendMessage("§c당신은 이미 취직하거나 창립한 회사가 있습니다.");
						return true;
					}
				} else {
					sender.sendMessage("§6/회사 창립 <이름> §f- 회사를 창립합니다. §b( 돈 2천만원 소요 )");
					return true;
				}
			}
			
			else if(args[0].equalsIgnoreCase("파산")) {
				if (Master == true) {
					String Cname = UserData.getStringYml(p, "회사");
					File cf = new File("plugins/MyCompany/Company/" + Cname + ".yml");
					cf.delete();
					UserData.SetBooleanData(p, "사장", false);
					UserData.SetStringData(p, "회사", "NONE");
					Methods.remComList(p, Cname);
					sender.sendMessage("§c" + Cname + " §6회사를 정상적으로 §c파산§6했습니다.");
					return true;
				} else {
					sender.sendMessage("§c당신은 회사 회장이 아닙니다.");
					return true;
				}
			} else {
				sender.sendMessage("§c당신은 회사 회장이 아닙니다.");
				return true;
			}
		}
		
		else if(args[0].equalsIgnoreCase("초대")) {
			if(args.length == 2) {
				if (Master == true) {
					if (!(args[1] == p.getName())) {
						Player pl = Methods.getOnorOffLine(args[1]);
						if (pl != null)
						{
							if (UserData.getBooleanYml(pl, "사장") == false
							 && UserData.getBooleanYml(pl, "직원") == false) {
								String Cname = UserData.getStringYml(pl, "회사");
								sender.sendMessage("§6성공적으로 §c" + args[1] + "§6 님에게 가입 권유장을 주셨습니다.");
								Methods.InvitePlayer(p, pl, Cname);
							} else {
								sender.sendMessage("§c그 플레이어는 이미 회사에서 일하고 계십니다.");
							}
						} else {
							sender.sendMessage("§c플레이어를 찾을 수 없습니다.");
							return true;
						}
					} else {
						sender.sendMessage("§c자기 자신은 초대할 수 없습니다.");
						return true;
					}
				} else {
					sender.sendMessage("§c당신은 초대할 권한이 없습니다.");
					return true;
				}
			} else {
				sender.sendMessage("§6/회사 초대 <닉네임> §f- <닉네임>에게 회사 가입을 권유합니다.");
				return true;
			}
		}
		
		else if(args[0].equalsIgnoreCase("수락")) {
			String none = "NONE";
			String Cname = UserData.getStringYml(p, "초대");
			if (!(none.equals(Cname))) {
				if (Master == false && User == false) {
					File f = new File("plugins/MyCompany/CompanyList.yml");
					YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
					List<String> list;
			        list = config.getStringList("CompanyList");
			        int i = list.size();
					do {
						if(i == 0) {
							sender.sendMessage("§c당신을 초대한 회사가 파산되어 있습니다.");
							UserData.SetStringData(p, "초대", "NONE");
							return true;
						}
						
		    			if(list.get(i) == Cname) {
		    				sender.sendMessage("§6성공적으로 §c" + Cname + "§6 회사에 입사하셨습니다!");
		    				UserData.SetStringData(p, "초대", "NONE");
		    				UserData.SetStringData(p, "회사", Cname);
		    				UserData.SetBooleanData(p, "직원", true);
		    				try { Company.addUserList(args[1], Cname); } catch (IOException e) {}
		    			} i--;
					} while (i>-1);
				} else {
					sender.sendMessage("§c당신은 이미 입사한 회사가 있습니다.");
					return true;
				}
			} else {
				sender.sendMessage("§c당신을 초대한 회사가 존재하지 않습니다.");
				return true;
			}
		}
		
		else if(args[0].equalsIgnoreCase("거절")) {
			String none = "NONE";
			String Cname = UserData.getStringYml(p, "초대");
			if (!(none.equals(Cname))) {
				if (Master == false && User == false) {
					File f = new File("plugins/MyCompany/CompanyList.yml");
					YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
					List<String> list;
			        list = config.getStringList("CompanyList");
			        int i = list.size();
					do {
						if(i == 0) {
							sender.sendMessage("§c당신을 초대한 회사가 파산되어 있습니다.");
							UserData.SetStringData(p, "초대", "NONE");
							return true;
						}
						
						if(list.get(i) == Cname) {
							sender.sendMessage("§c" + Cname + "§6 회사의 입사 권유를 §c거절 §6하셨습니다..");
							UserData.SetStringData(p, "초대", "NONE");
							return true;
						} i--;
					} while (i>-1);
				} else {
					sender.sendMessage("§c당신은 이미 입사한 회사가 있습니다.");
					return true;
				}
			} else {
				sender.sendMessage("§c당신을 초대한 회사가 존재하지 않습니다.");
				return true;
			}
		}
		
		else if(args[0].equalsIgnoreCase("취직")) {
			if(args.length == 3) {
				if (Master == false && User == false) {
					if (Integer.parseInt(playerstat[3]) == 0) {
						String str = args[1];
						for (int i = Integer.parseInt(companyl[0])-1; i>-1; i--) {
							if (i == -1) {
								sender.sendMessage("§c회사가 존재하지 않습니다.");
								return true;
							}
							
							else if ((str.equals(companyn[i]))) {
								sender.sendMessage("§6성공적으로 §c" + args[1] + "§6 회사에 이력서를 넣었습니다.");
								Company.AddJoinList(p, args[1], args[2]);
								playerstat[3] = (Integer.parseInt(playerstat[3])+1)+"";
								UserData.SetFile(p, playerstat);
								try { 
									Player mnader = Method.getMasterName(args[1]); 
									if (Method.getOnorOffLine(mnader.getName()) == null) { return true; }
									else if (Method.getMaster(mnader, mnader.getName())) {
										mnader.sendMessage("§c" + p.getName() + "§6 님이 당신의 회사에 §c이력서§6를 넣으셨습니다.");
										mnader.sendMessage("§6/회사 신청목록  §f- 우리 회사의 신청한 사람들 목록을 봅니다.");
									}
								} catch (IOException e) {}
								return true;
							}
							
							else if (i==0) {
								sender.sendMessage("§c회사가 존재하지 않습니다.");
								return true;
							}
						}
						return true;
					} else {
						sender.sendMessage("§c당신은 이미 회사에 이력서를 넣으셨습니다.");
						return true;
					}
				} else {
					sender.sendMessage("§c당신은 이미 입사한 회사가 있습니다.");
					return true;
				}
			} else {
				sender.sendMessage("§6/회사 취직 <회사> <각오> §f- 회사에게 자기소개서를 제출합니다.");
				sender.sendMessage("§f- §b띄어쓰기는 \"_\"로 표기해주세요.");
				return true;
			}
		}
		
		
		return false;
	}

}
