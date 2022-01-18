package me.espoo.ptk;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PACommand implements CommandExecutor {
	private final main plugin;
	public boolean Start;
	public boolean GodTime;
	public HashMap<String, String> PVPTime = new HashMap<String, String>();
	public String blue;
	public String red;

	public PACommand(main instance)
	{
		this.plugin = instance;
	}

	public void CreateNewInfo(String P) throws IOException {
		File cf = new File("plugins/PlayertwoKill/PlayerInfo/" + P + ".yml");
		YamlConfiguration cof = YamlConfiguration.loadConfiguration(cf);
		cof.set("KILL", Integer.valueOf(0));
		cof.set("DEATH", Integer.valueOf(0));
		cof.set("ACCEPT PLAYER LIST", "O");
		cof.set("QUIT", "X");
		cof.save(cf);
	}

	public void CreateAllowCommand() throws IOException {
		File cf = new File("plugins/PlayertwoKill/AC.yml");
		YamlConfiguration cof = YamlConfiguration.loadConfiguration(cf);
		if (!cf.exists()) {
			cof.set("AllowCommand", "");
			cof.save(cf);
		}
	}

	public boolean AllowCommandCheck(String c) throws IOException {
		File cf = new File("plugins/PlayertwoKill/AC.yml");
		YamlConfiguration cof = YamlConfiguration.loadConfiguration(cf);
		String[] a = cof.getString("AllowCommand").split(", ");
		if (cf.exists()) {
			for (int i = 0; i < a.length; i++) {
				if (a[i].equalsIgnoreCase(c)) return true;
			}
		}
		return false;
	}

	public void PlayerInfoSave(String P, String a, int b) throws IOException {
		File cf = new File("plugins/PlayertwoKill/PlayerInfo/" + P + ".yml");
		YamlConfiguration cof = YamlConfiguration.loadConfiguration(cf);
		cof.set(a, Integer.valueOf(b));
		cof.save(cf);
	}

	public void ConfigSave(String a, String b) throws IOException {
		File cf = new File("plugins/PlayertwoKill/config.yml");
		YamlConfiguration cof = YamlConfiguration.loadConfiguration(cf);
		cof.set(a, b);
		cof.save(cf);
	}

	public void PlayerInfoSave(String P, String a, String b) throws IOException {
		File cf = new File("plugins/PlayertwoKill/PlayerInfo/" + P + ".yml");
		YamlConfiguration cof = YamlConfiguration.loadConfiguration(cf);
		cof.set(a, b);
		cof.save(cf);
	}

	public boolean PlayerInfoB(String P) throws IOException {
		File cf = new File("plugins/PlayertwoKill/PlayerInfo/" + P + ".yml");
		return cf.exists();
	}

	public int PlayerInfo(String P, String a) throws IOException
	{
		File cf = new File("plugins/PlayertwoKill/PlayerInfo/" + P + ".yml");
		YamlConfiguration cof = YamlConfiguration.loadConfiguration(cf);
		return cof.getInt(a);
	}

	public String PlayerInfoS(String P, String a) throws IOException {
		File cf = new File("plugins/PlayertwoKill/PlayerInfo/" + P + ".yml");
		YamlConfiguration cof = YamlConfiguration.loadConfiguration(cf);
		return cof.getString(a);
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE + "] 버킷에선 실행이 불가능 합니다.");
			return false;
		}
		
		Player player = (Player)sender;
		if (command.getName().equalsIgnoreCase("대련")) {
			if (args.length == 0) {
				player.sendMessage("§e ------ §6대련 §e--- §6도움말 §e------");
				player.sendMessage("§6/대련 신청 <닉네임> §f- 해당 플레이어에게 대련 신청을 합니다.");
				player.sendMessage("§6/대련 수락 §f- 대련 신청을 수락합니다.");
				player.sendMessage("§6/대련 거절 §f- 대련 신청을 거절합니다.");
				player.sendMessage("§6/대련 관전 §f- 현재 진행중인 대련 관전을 갑니다.");
				player.sendMessage("§6/대련 전적 §f- 나의 대련 전적 정보를 봅니다.");
				player.sendMessage("§6/대련 상대 §f- 나의 대련 상대의 전적 정보를 봅니다.");
				player.sendMessage("§6/대련 시간 §f- 현재 진행준인 대련 남은 시간을 봅니다.");
				player.sendMessage("§6/대련 정보 §f- 현재 진행중인 대련 플레이어 정보를 봅니다.");
				player.sendMessage("§6/대련 허용 <닉네임> §f- 대련 신청 허용할 플레이어를 설정합니다.");
				player.sendMessage("§6/대련 차단 §f- 대련 신청을 차단 합니다.");

				if (player.isOp()) {
					player.sendMessage("");
					player.sendMessage("§e ------- §6어드민 명령어 §e-------");
					player.sendMessage("§6/대련 첫번째 §f- 첫번째 플레이어 대련 스폰을 설정합니다.");
					player.sendMessage("§6/대련 두번째 §f- 두번째 플레이어 대련 스폰을 설정합니다.");
					player.sendMessage("§6/대련 관전자 §f- 관전자 플레이어 스폰을 설정합니다.");
					player.sendMessage("§6/대련 스폰설정 §f- 대련후 이동되는 스폰을 설정합니다.");
				}
				return true;
			}

			if (player.isOp()) {
				double x = player.getLocation().getX();
				double y = player.getLocation().getY();
				double z = player.getLocation().getZ();
				float yaw = player.getLocation().getYaw();
				float pitch = player.getLocation().getPitch();
				String world = player.getWorld().getName();
				if (args[0].equals("첫번째")) {
					this.plugin.getConfig().set("PATele1", x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ", " + world);
					player.sendMessage("첫번째 플레이어 대련 스폰 저장");
					player.sendMessage("x - " + x + " y - " + y + " z - " + z + " yaw - " + yaw + " pitch - " + pitch + " world - " + world);
					this.plugin.saveConfig();
					this.plugin.reloadConfig();
				} else if (args[0].equals("두번째")) {
					this.plugin.getConfig().set("PATele2", x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ", " + world);
					player.sendMessage("두번째 플레이어 대련 스폰 저장");
					player.sendMessage("x - " + x + " y - " + y + " z - " + z + " yaw - " + yaw + " pitch - " + pitch + " world - " + world);
					this.plugin.saveConfig();
					this.plugin.reloadConfig();
				} else if (args[0].equals("관전자")) {
					this.plugin.getConfig().set("PATele3", x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ", " + world);
					player.sendMessage("관전자 플레이어 스폰 저장");
					player.sendMessage("x - " + x + " y - " + y + " z - " + z + " yaw - " + yaw + " pitch - " + pitch + " world - " + world);
					this.plugin.saveConfig();
					this.plugin.reloadConfig();
				} else if (args[0].equals("스폰설정")) {
					this.plugin.getConfig().set("PATele4", x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ", " + world);
					player.sendMessage("대련후 이동되는 스폰 저장");
					player.sendMessage("x - " + x + " y - " + y + " z - " + z + " yaw - " + yaw + " pitch - " + pitch + " world - " + world);
					this.plugin.saveConfig();
					this.plugin.reloadConfig();
				}
			}
			int lenth;
			int i;
			if (args[0].equalsIgnoreCase("신청")) {
				if (args.length == 1) {
					player.sendMessage("§6/대련 신청 <닉네임> §f- 해당 플레이어에게 대련 신청을 합니다.");
					return true;
				}
				String opn = args[1];
				Player op = Bukkit.getPlayer(opn);
				int AcceptTime = this.plugin.getConfig().getInt("PVPAcceptTime");
				if (this.Start) {
					player.sendMessage("§c이미 다른 플레이어들이 대련중 입니다.");
					return true;
				}
				if (op == null) {
					player.sendMessage("§c해당 플레이어는 존재하지 않습니다.");
					return true;
				}
				if (op.getName() == player.getName()) {
					player.sendMessage("§c자신에게 대련 신청을 걸 수 없습니다.");
					return true;
				}
				try {
					if (PlayerInfoS(op.getName(), "ACCEPT PLAYER LIST").equals("X")) {
						player.sendMessage("§c해당 플레이어는 대련 신청이 차단 되어있습니다.");
					} else if (PlayerInfoS(op.getName(), "ACCEPT PLAYER LIST").equals("O")) {
						player.sendMessage("§c" + op.getName() + " §6님에게 §c대련 신청§6을 하였습니다.");
						player.sendMessage("§c" + AcceptTime + "§6초 동안 상대가 수락하지 않으면 §c자동으로 취소§6됩니다.");
						op.sendMessage("§c" + player.getName() + " §6님이 당신에게 §c대련 신청§6을 하였습니다.");
						op.sendMessage("§6/대련 수락 §f- 대련 신청을 수락합니다.");
						op.sendMessage("§6/대련 거절 §f- 대련 신청을 거절합니다.");
						this.PVPTime.put(player.getName(), "PVPAcceptWait, " + op.getName() + ", " + (AcceptTime + 1));
						this.PVPTime.put(op.getName(), "PVPAcceptWaitP, " + player.getName() + ", " + (AcceptTime + 1));
					} else {
						String[] a = PlayerInfoS(op.getName(), "ACCEPT PLAYER LIST").split(", ");
						lenth = a.length;
						for (i = 1; i < lenth; i++)
							if (a[i].equals(player.getName())) {
								player.sendMessage("§c" + op.getName() + " §6님에게§c 대련 신청§6을 하였습니다.");
								player.sendMessage("§c" + AcceptTime + " §6초 동안 상대가 수락하지 않으면 §c자동으로 취소§6됩니다.");
								op.sendMessage("§c" + player.getName() + " §6님이 당신에게 §c대련 신청§6을 하였습니다.");
								op.sendMessage("§6/대련 수락 §f- 대련 신청을 수락합니다.");
								op.sendMessage("§6/대련 거절 §f- 대련 신청을 거절합니다.");
								this.PVPTime.put(player.getName(), "PVPAcceptWait, " + op.getName() + ", " + (AcceptTime + 1));
								this.PVPTime.put(op.getName(), "PVPAcceptWaitP, " + player.getName() + ", " + (AcceptTime + 1)); } else {
									player.sendMessage("§c해당 플레이어는 대련 신청이 차단 되어있습니다.");
								}
					}
				} catch (IOException ex) {
					Logger.getLogger(PACommand.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			if (args[0].equals("수락")) {
				String[] a = ((String)this.PVPTime.get(player.getName())).split(", ");
				if (!a[0].equals("PVPAcceptWaitP")) {
					player.sendMessage("§c당신에게 대련 신청한 플레이어가 없습니다.");
					player.sendMessage("§6/대련 신청 <닉네임> §f- 해당 플레이어에게 대련 신청을 합니다.");
					return true;
				}
				if (this.Start) {
					player.sendMessage("§c이 플레이어는 이미 다른 플레이어들과 대련중 입니다.");
					return true;
				}
				Player op = Bukkit.getPlayer(a[1]);
				this.Start = true;
				this.blue = op.getName();
				this.red = player.getName();
				int StartWaitTime = this.plugin.getConfig().getInt("PVPFirstStartTime");
				op.sendMessage("§6준비시간 §c" + StartWaitTime + "§6초가 주어집니다.");
				player.sendMessage("§6준비시간 §c" + StartWaitTime + "§6초가 주어집니다.");
				this.PVPTime.put(this.blue, "PVPFirstStartTime, " + this.red + ", " + (StartWaitTime + 1));
				this.PVPTime.put(this.red, "PVPFirstStartTimeP, " + this.blue + ", " + (StartWaitTime + 1));
			}
			else if (args[0].equals("거절")) {
				String[] a = ((String)this.PVPTime.get(player.getName())).split(", ");
				if (!a[0].equals("PVPAcceptWaitP")) {
					player.sendMessage("§c당신에게 대련 신청한 플레이어가 없습니다.");
					player.sendMessage("§6/대련 신청 <닉네임> §f- 해당 플레이어에게 대련 신청을 합니다.");
					return true;
				}
				if (this.Start) {
					player.sendMessage("§c이 플레이어는 이미 다른 플레이어들과 대련중 입니다.");
					return true;
				}
				Player op = Bukkit.getPlayer(a[1]);
				op.sendMessage("§c" + player.getName() + "§6님이 당신의 대련 신청을 §c거절 §6하였습니다.");
				player.sendMessage("§6당신은 §c" + op.getName() + "§6님의 대련 신청을 §c거절 §6하였습니다.");
				this.PVPTime.put(op.getName(), "");
				this.PVPTime.put(player.getName(), "");
			}
			else if (args[0].equals("전적")) {
				try {
					player.sendMessage("§e * §6나의 §c킬 §6or §c데스 §6전적");
					player.sendMessage("§e ┗━━ §cKILL §7:: §f" + PlayerInfo(player.getName(), "KILL"));
					player.sendMessage("§e ┗━━ §cDEATH §7:: §f" + PlayerInfo(player.getName(), "DEATH"));
				} catch (IOException ex) {
					Logger.getLogger(PACommand.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else if (args[0].equals("정보")) {
				if (!this.Start) {
					player.sendMessage("§c현재 대련중인 플레이어들이 없습니다.");
					return true;
				}
				Player bluen = Bukkit.getPlayer(this.blue);
				Player redn = Bukkit.getPlayer(this.red);
				try {
					player.sendMessage("§e * " + bluen.getName() + " §6님의 §c킬 §6or §c데스 §6전적");
					player.sendMessage("§e ┗━━ §cKILL §7:: §f" + PlayerInfo(bluen.getName(), "KILL"));
					player.sendMessage("§e ┗━━ §cDEATH §7:: §f" + PlayerInfo(bluen.getName(), "DEATH"));
					player.sendMessage("");
					player.sendMessage("§e * " + redn.getName() + " §6님의 §c킬 §6or §c데스 §6전적");
					player.sendMessage("§e ┗━━ §cKILL §7:: §f" + PlayerInfo(redn.getName(), "KILL"));
					player.sendMessage("§e ┗━━ §cDEATH §7:: §f" + PlayerInfo(redn.getName(), "DEATH"));
				} catch (IOException ex) {
					Logger.getLogger(PACommand.class.getName()).log(Level.SEVERE, null, ex);
				}
			} else if (args[0].equals("상대")) {
				if (!this.Start) {
					player.sendMessage("§c당신에게 대련을 신청한 사람이 없습니다.");
					return true;
				}
				String[] a = ((String)this.PVPTime.get(player.getName())).split(", ");
				Player op = Bukkit.getPlayer(a[1]);
				try {
					player.sendMessage("§e * " + op.getName() +  " §6님의 §c킬 §6or §c데스 §6전적");
					player.sendMessage("§e ┗━━ §cKILL §7:: §f" + PlayerInfo(op.getName(), "KILL"));
					player.sendMessage("§e ┗━━ §cDEATH §7:: §f" + PlayerInfo(op.getName(), "DEATH"));
				} catch (IOException ex) {
					Logger.getLogger(PACommand.class.getName()).log(Level.SEVERE, null, ex);
				}
			} else if (args[0].equals("관전")) {
				if (!this.Start) {
					player.sendMessage("§c현재 대련중인 플레이어들이 없습니다.");
					return true;
				}
				if ((player.getName() == this.blue) || (player.getName() == this.red)) {
					player.sendMessage("§c당신은 지금 대련중 입니다.");
					return true;
				}
				String[] Spawn1 = this.plugin.getConfig().getString("PATele3").split(", ");
				double Spawn1x = Double.parseDouble(Spawn1[0]);
				double Spawn1y = Double.parseDouble(Spawn1[1]);
				double Spawn1z = Double.parseDouble(Spawn1[2]);
				float Spawn1yaw = Float.parseFloat(Spawn1[3]);
				float Spawn1pitch = Float.parseFloat(Spawn1[4]);
				World Spawn3world = Bukkit.getWorld(Spawn1[5]);
				Location loc = new Location(Spawn3world, Spawn1x, Spawn1y, Spawn1z, Spawn1yaw, Spawn1pitch);
				player.teleport(loc);
			}
			else if (args[0].equals("차단")) {
				try {
					if (PlayerInfoS(player.getName(), "ACCEPT PLAYER LIST").equals("O")) {
						PlayerInfoSave(player.getName(), "ACCEPT PLAYER LIST", "X");
						player.sendMessage("§6모든 플레이어의 §c대련 신청§6을 §c차단§6했습니다.");
					} else if (PlayerInfoS(player.getName(), "ACCEPT PLAYER LIST").equals("X")) {
						PlayerInfoSave(player.getName(), "ACCEPT PLAYER LIST", "O");
						player.sendMessage("§6모든 플레이어의 §c대련 신청§6을 §c허용§6했습니다.");
					} else {
						PlayerInfoSave(player.getName(), "ACCEPT PLAYER LIST", "X");
						player.sendMessage("§6모든 플레이어의 §c대련 신청§6을 §c차단§6했습니다.");
					}
				} catch (IOException ex) {
					Logger.getLogger(PACommand.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else if (args[0].equals("허용")) {
				if (args.length == 1) {
					player.sendMessage("§6/대련 허용 <닉네임> §f- 대련 신청 허용할 플레이어를 설정합니다.");
					return true;
				}
				String opn = args[1];
				Player op = Bukkit.getPlayer(opn);
				try {
					if (op == null) {
						player.sendMessage("§c해당 플레이어는 존재하지 않습니다.");
						return true;
					}
					if (op.getName() == player.getName()) {
						player.sendMessage("§c자기 자신을 허용목록에 넣을 수 없습니다.");
						return true;
					}
					if (PlayerInfoS(player.getName(), "ACCEPT PLAYER LIST").equals("O")) {
						player.sendMessage("§c대련이 차단된 상태여야만 명령어 사용이 가능합니다.");
						player.sendMessage("§6/대련 차단 §f- 대련 신청을 차단 합니다.");
						return true;
					}
					String a = PlayerInfoS(player.getName(), "ACCEPT PLAYER LIST");
					PlayerInfoSave(player.getName(), "ACCEPT PLAYER LIST", a + ", " + op.getName());
					player.sendMessage(op.getName() + " 허용.");
				} catch (IOException ex) {
					Logger.getLogger(PACommand.class.getName()).log(Level.SEVERE, null, ex);
				}
			} else if (args[0].equals("시간")) {
				if (!this.Start) {
					player.sendMessage("§c현재 대련중인 플레이어들이 없습니다.");
					return true;
				}
				if (this.GodTime) {
					player.sendMessage("§c대련 진행 대기중입니다. 잠시만 기다려주세요.");
					return true;
				}
				Player bluen = Bukkit.getPlayer(this.blue);
				String[] a = ((String)this.PVPTime.get(bluen.getName())).split(", ");
				int time = Integer.parseInt(a[2]);
				player.sendMessage("§a대련 남은시간: §b" + time + " 초");
				return true;
			}
		}
		return true;
	}
}