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
			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE + "] ��Ŷ���� ������ �Ұ��� �մϴ�.");
			return false;
		}
		
		Player player = (Player)sender;
		if (command.getName().equalsIgnoreCase("���")) {
			if (args.length == 0) {
				player.sendMessage("��e ------ ��6��� ��e--- ��6���� ��e------");
				player.sendMessage("��6/��� ��û <�г���> ��f- �ش� �÷��̾�� ��� ��û�� �մϴ�.");
				player.sendMessage("��6/��� ���� ��f- ��� ��û�� �����մϴ�.");
				player.sendMessage("��6/��� ���� ��f- ��� ��û�� �����մϴ�.");
				player.sendMessage("��6/��� ���� ��f- ���� �������� ��� ������ ���ϴ�.");
				player.sendMessage("��6/��� ���� ��f- ���� ��� ���� ������ ���ϴ�.");
				player.sendMessage("��6/��� ��� ��f- ���� ��� ����� ���� ������ ���ϴ�.");
				player.sendMessage("��6/��� �ð� ��f- ���� �������� ��� ���� �ð��� ���ϴ�.");
				player.sendMessage("��6/��� ���� ��f- ���� �������� ��� �÷��̾� ������ ���ϴ�.");
				player.sendMessage("��6/��� ��� <�г���> ��f- ��� ��û ����� �÷��̾ �����մϴ�.");
				player.sendMessage("��6/��� ���� ��f- ��� ��û�� ���� �մϴ�.");

				if (player.isOp()) {
					player.sendMessage("");
					player.sendMessage("��e ------- ��6���� ��ɾ� ��e-------");
					player.sendMessage("��6/��� ù��° ��f- ù��° �÷��̾� ��� ������ �����մϴ�.");
					player.sendMessage("��6/��� �ι�° ��f- �ι�° �÷��̾� ��� ������ �����մϴ�.");
					player.sendMessage("��6/��� ������ ��f- ������ �÷��̾� ������ �����մϴ�.");
					player.sendMessage("��6/��� �������� ��f- ����� �̵��Ǵ� ������ �����մϴ�.");
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
				if (args[0].equals("ù��°")) {
					this.plugin.getConfig().set("PATele1", x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ", " + world);
					player.sendMessage("ù��° �÷��̾� ��� ���� ����");
					player.sendMessage("x - " + x + " y - " + y + " z - " + z + " yaw - " + yaw + " pitch - " + pitch + " world - " + world);
					this.plugin.saveConfig();
					this.plugin.reloadConfig();
				} else if (args[0].equals("�ι�°")) {
					this.plugin.getConfig().set("PATele2", x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ", " + world);
					player.sendMessage("�ι�° �÷��̾� ��� ���� ����");
					player.sendMessage("x - " + x + " y - " + y + " z - " + z + " yaw - " + yaw + " pitch - " + pitch + " world - " + world);
					this.plugin.saveConfig();
					this.plugin.reloadConfig();
				} else if (args[0].equals("������")) {
					this.plugin.getConfig().set("PATele3", x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ", " + world);
					player.sendMessage("������ �÷��̾� ���� ����");
					player.sendMessage("x - " + x + " y - " + y + " z - " + z + " yaw - " + yaw + " pitch - " + pitch + " world - " + world);
					this.plugin.saveConfig();
					this.plugin.reloadConfig();
				} else if (args[0].equals("��������")) {
					this.plugin.getConfig().set("PATele4", x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ", " + world);
					player.sendMessage("����� �̵��Ǵ� ���� ����");
					player.sendMessage("x - " + x + " y - " + y + " z - " + z + " yaw - " + yaw + " pitch - " + pitch + " world - " + world);
					this.plugin.saveConfig();
					this.plugin.reloadConfig();
				}
			}
			int lenth;
			int i;
			if (args[0].equalsIgnoreCase("��û")) {
				if (args.length == 1) {
					player.sendMessage("��6/��� ��û <�г���> ��f- �ش� �÷��̾�� ��� ��û�� �մϴ�.");
					return true;
				}
				String opn = args[1];
				Player op = Bukkit.getPlayer(opn);
				int AcceptTime = this.plugin.getConfig().getInt("PVPAcceptTime");
				if (this.Start) {
					player.sendMessage("��c�̹� �ٸ� �÷��̾���� ����� �Դϴ�.");
					return true;
				}
				if (op == null) {
					player.sendMessage("��c�ش� �÷��̾�� �������� �ʽ��ϴ�.");
					return true;
				}
				if (op.getName() == player.getName()) {
					player.sendMessage("��c�ڽſ��� ��� ��û�� �� �� �����ϴ�.");
					return true;
				}
				try {
					if (PlayerInfoS(op.getName(), "ACCEPT PLAYER LIST").equals("X")) {
						player.sendMessage("��c�ش� �÷��̾�� ��� ��û�� ���� �Ǿ��ֽ��ϴ�.");
					} else if (PlayerInfoS(op.getName(), "ACCEPT PLAYER LIST").equals("O")) {
						player.sendMessage("��c" + op.getName() + " ��6�Կ��� ��c��� ��û��6�� �Ͽ����ϴ�.");
						player.sendMessage("��c" + AcceptTime + "��6�� ���� ��밡 �������� ������ ��c�ڵ����� ��ҡ�6�˴ϴ�.");
						op.sendMessage("��c" + player.getName() + " ��6���� ��ſ��� ��c��� ��û��6�� �Ͽ����ϴ�.");
						op.sendMessage("��6/��� ���� ��f- ��� ��û�� �����մϴ�.");
						op.sendMessage("��6/��� ���� ��f- ��� ��û�� �����մϴ�.");
						this.PVPTime.put(player.getName(), "PVPAcceptWait, " + op.getName() + ", " + (AcceptTime + 1));
						this.PVPTime.put(op.getName(), "PVPAcceptWaitP, " + player.getName() + ", " + (AcceptTime + 1));
					} else {
						String[] a = PlayerInfoS(op.getName(), "ACCEPT PLAYER LIST").split(", ");
						lenth = a.length;
						for (i = 1; i < lenth; i++)
							if (a[i].equals(player.getName())) {
								player.sendMessage("��c" + op.getName() + " ��6�Կ��ԡ�c ��� ��û��6�� �Ͽ����ϴ�.");
								player.sendMessage("��c" + AcceptTime + " ��6�� ���� ��밡 �������� ������ ��c�ڵ����� ��ҡ�6�˴ϴ�.");
								op.sendMessage("��c" + player.getName() + " ��6���� ��ſ��� ��c��� ��û��6�� �Ͽ����ϴ�.");
								op.sendMessage("��6/��� ���� ��f- ��� ��û�� �����մϴ�.");
								op.sendMessage("��6/��� ���� ��f- ��� ��û�� �����մϴ�.");
								this.PVPTime.put(player.getName(), "PVPAcceptWait, " + op.getName() + ", " + (AcceptTime + 1));
								this.PVPTime.put(op.getName(), "PVPAcceptWaitP, " + player.getName() + ", " + (AcceptTime + 1)); } else {
									player.sendMessage("��c�ش� �÷��̾�� ��� ��û�� ���� �Ǿ��ֽ��ϴ�.");
								}
					}
				} catch (IOException ex) {
					Logger.getLogger(PACommand.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			if (args[0].equals("����")) {
				String[] a = ((String)this.PVPTime.get(player.getName())).split(", ");
				if (!a[0].equals("PVPAcceptWaitP")) {
					player.sendMessage("��c��ſ��� ��� ��û�� �÷��̾ �����ϴ�.");
					player.sendMessage("��6/��� ��û <�г���> ��f- �ش� �÷��̾�� ��� ��û�� �մϴ�.");
					return true;
				}
				if (this.Start) {
					player.sendMessage("��c�� �÷��̾�� �̹� �ٸ� �÷��̾��� ����� �Դϴ�.");
					return true;
				}
				Player op = Bukkit.getPlayer(a[1]);
				this.Start = true;
				this.blue = op.getName();
				this.red = player.getName();
				int StartWaitTime = this.plugin.getConfig().getInt("PVPFirstStartTime");
				op.sendMessage("��6�غ�ð� ��c" + StartWaitTime + "��6�ʰ� �־����ϴ�.");
				player.sendMessage("��6�غ�ð� ��c" + StartWaitTime + "��6�ʰ� �־����ϴ�.");
				this.PVPTime.put(this.blue, "PVPFirstStartTime, " + this.red + ", " + (StartWaitTime + 1));
				this.PVPTime.put(this.red, "PVPFirstStartTimeP, " + this.blue + ", " + (StartWaitTime + 1));
			}
			else if (args[0].equals("����")) {
				String[] a = ((String)this.PVPTime.get(player.getName())).split(", ");
				if (!a[0].equals("PVPAcceptWaitP")) {
					player.sendMessage("��c��ſ��� ��� ��û�� �÷��̾ �����ϴ�.");
					player.sendMessage("��6/��� ��û <�г���> ��f- �ش� �÷��̾�� ��� ��û�� �մϴ�.");
					return true;
				}
				if (this.Start) {
					player.sendMessage("��c�� �÷��̾�� �̹� �ٸ� �÷��̾��� ����� �Դϴ�.");
					return true;
				}
				Player op = Bukkit.getPlayer(a[1]);
				op.sendMessage("��c" + player.getName() + "��6���� ����� ��� ��û�� ��c���� ��6�Ͽ����ϴ�.");
				player.sendMessage("��6����� ��c" + op.getName() + "��6���� ��� ��û�� ��c���� ��6�Ͽ����ϴ�.");
				this.PVPTime.put(op.getName(), "");
				this.PVPTime.put(player.getName(), "");
			}
			else if (args[0].equals("����")) {
				try {
					player.sendMessage("��e * ��6���� ��cų ��6or ��c���� ��6����");
					player.sendMessage("��e ������ ��cKILL ��7:: ��f" + PlayerInfo(player.getName(), "KILL"));
					player.sendMessage("��e ������ ��cDEATH ��7:: ��f" + PlayerInfo(player.getName(), "DEATH"));
				} catch (IOException ex) {
					Logger.getLogger(PACommand.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else if (args[0].equals("����")) {
				if (!this.Start) {
					player.sendMessage("��c���� ������� �÷��̾���� �����ϴ�.");
					return true;
				}
				Player bluen = Bukkit.getPlayer(this.blue);
				Player redn = Bukkit.getPlayer(this.red);
				try {
					player.sendMessage("��e * " + bluen.getName() + " ��6���� ��cų ��6or ��c���� ��6����");
					player.sendMessage("��e ������ ��cKILL ��7:: ��f" + PlayerInfo(bluen.getName(), "KILL"));
					player.sendMessage("��e ������ ��cDEATH ��7:: ��f" + PlayerInfo(bluen.getName(), "DEATH"));
					player.sendMessage("");
					player.sendMessage("��e * " + redn.getName() + " ��6���� ��cų ��6or ��c���� ��6����");
					player.sendMessage("��e ������ ��cKILL ��7:: ��f" + PlayerInfo(redn.getName(), "KILL"));
					player.sendMessage("��e ������ ��cDEATH ��7:: ��f" + PlayerInfo(redn.getName(), "DEATH"));
				} catch (IOException ex) {
					Logger.getLogger(PACommand.class.getName()).log(Level.SEVERE, null, ex);
				}
			} else if (args[0].equals("���")) {
				if (!this.Start) {
					player.sendMessage("��c��ſ��� ����� ��û�� ����� �����ϴ�.");
					return true;
				}
				String[] a = ((String)this.PVPTime.get(player.getName())).split(", ");
				Player op = Bukkit.getPlayer(a[1]);
				try {
					player.sendMessage("��e * " + op.getName() +  " ��6���� ��cų ��6or ��c���� ��6����");
					player.sendMessage("��e ������ ��cKILL ��7:: ��f" + PlayerInfo(op.getName(), "KILL"));
					player.sendMessage("��e ������ ��cDEATH ��7:: ��f" + PlayerInfo(op.getName(), "DEATH"));
				} catch (IOException ex) {
					Logger.getLogger(PACommand.class.getName()).log(Level.SEVERE, null, ex);
				}
			} else if (args[0].equals("����")) {
				if (!this.Start) {
					player.sendMessage("��c���� ������� �÷��̾���� �����ϴ�.");
					return true;
				}
				if ((player.getName() == this.blue) || (player.getName() == this.red)) {
					player.sendMessage("��c����� ���� ����� �Դϴ�.");
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
			else if (args[0].equals("����")) {
				try {
					if (PlayerInfoS(player.getName(), "ACCEPT PLAYER LIST").equals("O")) {
						PlayerInfoSave(player.getName(), "ACCEPT PLAYER LIST", "X");
						player.sendMessage("��6��� �÷��̾��� ��c��� ��û��6�� ��c���ܡ�6�߽��ϴ�.");
					} else if (PlayerInfoS(player.getName(), "ACCEPT PLAYER LIST").equals("X")) {
						PlayerInfoSave(player.getName(), "ACCEPT PLAYER LIST", "O");
						player.sendMessage("��6��� �÷��̾��� ��c��� ��û��6�� ��c����6�߽��ϴ�.");
					} else {
						PlayerInfoSave(player.getName(), "ACCEPT PLAYER LIST", "X");
						player.sendMessage("��6��� �÷��̾��� ��c��� ��û��6�� ��c���ܡ�6�߽��ϴ�.");
					}
				} catch (IOException ex) {
					Logger.getLogger(PACommand.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else if (args[0].equals("���")) {
				if (args.length == 1) {
					player.sendMessage("��6/��� ��� <�г���> ��f- ��� ��û ����� �÷��̾ �����մϴ�.");
					return true;
				}
				String opn = args[1];
				Player op = Bukkit.getPlayer(opn);
				try {
					if (op == null) {
						player.sendMessage("��c�ش� �÷��̾�� �������� �ʽ��ϴ�.");
						return true;
					}
					if (op.getName() == player.getName()) {
						player.sendMessage("��c�ڱ� �ڽ��� ����Ͽ� ���� �� �����ϴ�.");
						return true;
					}
					if (PlayerInfoS(player.getName(), "ACCEPT PLAYER LIST").equals("O")) {
						player.sendMessage("��c����� ���ܵ� ���¿��߸� ��ɾ� ����� �����մϴ�.");
						player.sendMessage("��6/��� ���� ��f- ��� ��û�� ���� �մϴ�.");
						return true;
					}
					String a = PlayerInfoS(player.getName(), "ACCEPT PLAYER LIST");
					PlayerInfoSave(player.getName(), "ACCEPT PLAYER LIST", a + ", " + op.getName());
					player.sendMessage(op.getName() + " ���.");
				} catch (IOException ex) {
					Logger.getLogger(PACommand.class.getName()).log(Level.SEVERE, null, ex);
				}
			} else if (args[0].equals("�ð�")) {
				if (!this.Start) {
					player.sendMessage("��c���� ������� �÷��̾���� �����ϴ�.");
					return true;
				}
				if (this.GodTime) {
					player.sendMessage("��c��� ���� ������Դϴ�. ��ø� ��ٷ��ּ���.");
					return true;
				}
				Player bluen = Bukkit.getPlayer(this.blue);
				String[] a = ((String)this.PVPTime.get(bluen.getName())).split(", ");
				int time = Integer.parseInt(a[2]);
				player.sendMessage("��a��� �����ð�: ��b" + time + " ��");
				return true;
			}
		}
		return true;
	}
}