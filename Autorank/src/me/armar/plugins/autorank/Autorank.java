package me.armar.plugins.autorank;

import net.milkbowl.vault.Vault;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.logging.Logger;

public class Autorank extends JavaPlugin {

    private Logger log = Logger.getLogger("Minecraft");
    private Config config;
    private DataStorage data;
    private VaultHandler vault;
    private AutorankSaveData save;
    private Leaderboard leaderboard;
    private AutorankUpdateLeaderboard leaderboardUpdate;
    private boolean debug;
    private Timer timer;
    private RankChanger changer;

    public void onEnable() {

	// check if vault is present
	Plugin x = this.getServer().getPluginManager().getPlugin("Vault");
	if (x != null & x instanceof Vault) {
	    vault = new VaultHandler(this);
	} else {
	    logMessage("[%s] Vault �÷������� �����ϴ�! autorank ����� �����մϴ�.");
	    getPluginLoader().disablePlugin(this);
	}

	// set up general config
	LinkedHashMap<String, Object> configDefaults = new LinkedHashMap<String, Object>();
	String configPath = this.getDataFolder().getAbsolutePath() + File.separator + "config.yml";
	configDefaults.put("Enabled", true);
	configDefaults.put("Debug mode", false);
	configDefaults.put("Message prefix", "&6");
	configDefaults.put("Leaderboard layout", "��c&p ��6|��c &n ��6-��a &th �ð�, &m ��");
	configDefaults.put("Essentials AFK integration", false);

	String[] availableGroups = vault.getGroups();
	for (int i = 0; i < availableGroups.length - 1; i++) {
	    String from = availableGroups[i];
	    String to = availableGroups[i + 1];

	    configDefaults.put((i + 1) + ".from", from);
	    configDefaults.put((i + 1) + ".to", to);
	    configDefaults.put((i + 1) + ".required minutes played", ((i + 1) * 200));
	    configDefaults.put((i + 1) + ".message", "&2Congratulations, you are now a " + to + ".");
	    configDefaults.put((i + 1) + ".world", null);
	    configDefaults.put((i + 1) + ".commands", null);
	}
	config = new Config(this, configPath, configDefaults, "config");

	// set up player data
	data = new DataStorage(this);

	// schedule AutorankUpdate to be run
	timer = new Timer();
	timer.scheduleAtFixedRate(new AutorankUpdateData(this), 300000, 300000);

	// schedule AutorankSave to be run
	save = new AutorankSaveData(this);
	getServer().getScheduler().scheduleSyncRepeatingTask(this, save, 72000, 72000);

	// make leaderboard
	leaderboard = new Leaderboard(this);

	leaderboardUpdate = new AutorankUpdateLeaderboard(this);
	getServer().getScheduler().scheduleSyncRepeatingTask(this, leaderboardUpdate, 33, 36000);

	if (config.get("Debug mode") != null) {
	    debug = (Boolean) config.get("Debug mode");
	}

	changer = new RankChanger(this);
	Bukkit.getPluginManager().registerEvents(changer, this);

	logMessage("Enabled.");

	if (!(Boolean) config.get("Enabled")) {
	    logMessage("���� Autorank ����� ��Ȱ��ȭ ���Դϴ�. 'enabled'�� true�� �������ּ���.");
	}

	debugMessage("Debug mode ON");

    }

    public void onDisable() {
	getData().save();
	logMessage("Data saved");
	logMessage("Disabled.");
	// stop scheduled tasks from running
	getServer().getScheduler().cancelTasks(this);
	timer.cancel();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if (args.length < 1) {
		if(sender.isOp() == true) {
		    sender.sendMessage("��e ----- ��6�ڵ� ���� ��e--- ��6���� ��e-----");
		    sender.sendMessage("��6/���� Ȯ�� ��f- ������ �󸶳� ���Ҵ��� Ȯ���մϴ�.");
		    sender.sendMessage("��6/���� Ȯ�� <�г���> ��f- �÷��̾��� ���� ���θ� Ȯ���մϴ�.");
		    sender.sendMessage("��6/���� ���� ��f- �÷��̾���� ������ ���ϴ�.");
		    sender.sendMessage("��6/���� �ð� ��f- ��� ������ �ð��� ���ϴ�.");
		    sender.sendMessage("��6/���� ���� <�г���> <��> ��f- �÷��̾��� �ð��� �����մϴ�.");
		    sender.sendMessage("��6/���� ����� ��f- ���Ǳ׸� ����� �մϴ�.");
		    return false;
		}
		
		else {
		    sender.sendMessage("��e ----- ��6�ڵ� ���� ��e--- ��6���� ��e-----");
		    sender.sendMessage("��6/���� Ȯ�� ��f- ������ �󸶳� ���Ҵ��� Ȯ���մϴ�.");
		    sender.sendMessage("��6/���� Ȯ�� <�г���> ��f- �÷��̾��� ���� ���θ� Ȯ���մϴ�.");
		    sender.sendMessage("��6/���� ���� ��f- �÷��̾���� ������ ���ϴ�.");
		    sender.sendMessage("��6/���� �ð� ��f- ��� ������ �ð��� ���ϴ�.");
		    return false;
		}
	}

	String prefix = "&6";
	if (getConf().get("Message prefix") != null) {
	    prefix = (String) getConf().get("Message prefix");
	}

	prefix = prefix.replaceAll("(&([a-f0-9]))", "\u00A7$2");

	String noPerm = "&c����� �� ��ɾ ������ ������ �����ϴ�.";
	boolean overridePerms = sender.hasPermission("autorank.*");

	if (args[0].equalsIgnoreCase("debug")) {
	    if (!(sender instanceof ConsoleCommandSender)) {
		sender.sendMessage("�ֿܼ��� ��ɾ �������ֽñ� �ٶ��ϴ�.");
		return true;
	    }

	    sender.sendMessage("----Autorank Debug----");

	    sender.sendMessage("����: " + this.getDescription().getVersion());

	    final PluginManager pluginManager = getServer().getPluginManager();
	    final Plugin vaultPlugin = pluginManager.getPlugin("Vault");
	    sender.sendMessage("Vault ����: " + vaultPlugin.getDescription().getVersion());

	    if ((Boolean) config.get("Enabled") == null) {
		    logMessage("���� Autorank ����� ��Ȱ��ȭ ���Դϴ�. 'enabled'�� true�� �������ּ���.");
	    }
	    
	    if ((Boolean) config.get("Enabled") == false) {
		    logMessage("���� Autorank ����� ��Ȱ��ȭ ���Դϴ�. 'enabled'�� true�� �������ּ���.");
	    }
	   	    
	    Player[] onlinePlayers = getServer().getOnlinePlayers();
	    for(Player player : onlinePlayers){
		if (player.hasPermission("autorank.exclude") && !player.hasPermission("autorank.sf5k4fg7hu")) {
		    sender.sendMessage(player.getName() + " ���� autorank.exclude ������ ������ �����Ƿ� ������ ������ �ʽ��ϴ�.");
		}
		
		if (player.hasPermission("autorank.timeexclude") && !player.hasPermission("autorank.sf5k4fg7hu")) {
		    sender.sendMessage(player.getName() + " ���� autorank.exclude ������ ������ �����Ƿ� �ð��� ��ϵ��� �ʽ��ϴ�.");
		}
	    }
	    
	    sender.sendMessage("�����ϴ� �׷��:");
	    String[] groups = vault.getGroups();
	    for(String group :groups){
		   sender.sendMessage(" - " + group);
	    }
	    
	    sender.sendMessage("������ �׷�:");
	    int entry = 1;
	    while (config.get(entry + ".from") != null) {

		sender.sendMessage("Entry " + entry);
		sender.sendMessage("From: " + (String) config.get(entry + ".from"));
		boolean existingGroup = false;
		    for(String group :groups){
			   if( ((String)config.get(entry + ".from") ).equals(group))
			       existingGroup = true;
		    }
		    
		    if(!existingGroup)
			    sender.sendMessage("���: " + config.get(entry + ".from") + " �� �׷��� ã�� �� �����ϴ�. ������� �� Ȯ���� �ּ���.");

		
		sender.sendMessage("To: " + (String) config.get(entry + ".to"));
		existingGroup = false;
		    for(String group :groups){
			   if( ((String)config.get(entry + ".to") ).equals(group))
			       existingGroup = true;
		    }
		    
		    if(!existingGroup)
			    sender.sendMessage("���: " + config.get(entry + ".to") + " �� �׷��� ã�� �� �����ϴ�. ������� �� Ȯ���� �ּ���.");
		
		sender.sendMessage("����: " + (String) config.get(entry + ".world"));
		
		entry++;
	    }

	    sender.sendMessage("----Autorank Debug----");

	    return true;
	}

	else if (args[0].equalsIgnoreCase("�����")) {
	    if (!sender.hasPermission("autorank.reload") && !overridePerms) {
		sender.sendMessage(noPerm);
		return true;
	    }
	    getData().save();
	    logMessage("Autosave �����͸� �����߽��ϴ�.");
	    getConf().load();
	    sender.sendMessage(prefix + "��cAutorank��6�� ���������� ����� �߽��ϴ�.");
	    return true;
	} else if (args[0].equalsIgnoreCase("����") || args[0].equalsIgnoreCase("��ɾ�")) {
		if(sender.isOp() == true) {
		    sender.sendMessage("��e ----- ��6�ڵ� ���� ��e--- ��6���� ��e-----");
		    sender.sendMessage("��6/���� Ȯ�� ��f- ������ �󸶳� ���Ҵ��� Ȯ���մϴ�.");
		    sender.sendMessage("��6/���� Ȯ�� <�г���> ��f- �÷��̾��� ���� ���θ� Ȯ���մϴ�.");
		    sender.sendMessage("��6/���� ���� ��f- �÷��̾���� ������ ���ϴ�.");
		    sender.sendMessage("��6/���� �ð� ��f- ��� ������ �ð��� ���ϴ�.");
		    sender.sendMessage("��6/���� ���� <�г���> <��> ��f- �÷��̾��� �ð��� �����մϴ�.");
		    sender.sendMessage("��6/���� ����� ��f- ���Ǳ׸� ����� �մϴ�.");
		    return false;
		}
		
		else {
		    sender.sendMessage("��e ----- ��6�ڵ� ���� ��e--- ��6���� ��e-----");
		    sender.sendMessage("��6/���� Ȯ�� ��f- ������ �󸶳� ���Ҵ��� Ȯ���մϴ�.");
		    sender.sendMessage("��6/���� Ȯ�� <�г���> ��f- �÷��̾��� ���� ���θ� Ȯ���մϴ�.");
		    sender.sendMessage("��6/���� ���� ��f- �÷��̾���� ������ ���ϴ�.");
		    sender.sendMessage("��6/���� �ð� ��f- ��� ������ �ð��� ���ϴ�.");
		    return false;
		}
	} else if (args[0].equalsIgnoreCase("����")) {
	    // check permissions
	    if (!sender.hasPermission("autorank.leaderboard") && !overridePerms) {
		sender.sendMessage(noPerm);
		return true;
	    }
	    leaderboard.display(sender, prefix);
	    return true;
	} else if (args[0].equalsIgnoreCase("�ð�")) {
	    sender.sendMessage("��e ----- ��6�ڵ� ���� ��e--- ��6�ð� ��e-----");
	    sender.sendMessage("��61. ��7(01�ð�) ��3�Թ��� ��7-> ��d�ʱ���");
	    sender.sendMessage("��62. ��7(06�ð�) ��d�ʱ��� ��7-> ��c�߱���");
	    sender.sendMessage("��63. ��7(12�ð�) ��c�߱��� ��7-> ��5�����");
	    sender.sendMessage("��64. ��7(20�ð�) ��5����� ��7-> ��9�ֻ����");
	    sender.sendMessage("��65. ��7(40�ð�) ��9�ֻ���� ��7-> ��2������");
		return true;
	} else if (args[0].equalsIgnoreCase("Ȯ��") && args.length == 1) {
	    if (!sender.hasPermission("autorank.check") && !overridePerms) {
		sender.sendMessage(noPerm);
		return true;
	    }
	    if (sender instanceof ConsoleCommandSender) {
		sender.sendMessage("�ܼ��� Ȯ���� �� �����ϴ�.");
		return true;
	    }
	    Integer time = (Integer) data.get(sender.getName().toLowerCase());
	    if (time == null) {
		sender.sendMessage(prefix + "��c����� �ð��� �������� �ʽ��ϴ�. 5�� �Ŀ� ��Ȯ�� ���ֽñ� �ٶ��ϴ�.");
	    } else {
		String[] info = getRankInfo((Player) sender);
		// 0 - current rank
		// 1 - next rank
		// 2 - in world
		// 3 - time to next rank
		sender.sendMessage(prefix + "����� ����� ��c" + info[0] + " ��6�̰�, ��c" + time / 60 + " �ð�, " + time % 60 + " �� ��6�÷��� �ϼ̽��ϴ�.");

		if (info[1] != null && info[3] != null) {
		    if ((Boolean) config.get("Enabled") == false) {
			sender.sendMessage(prefix + "��c�ڵ� ���� ��ȭ�� ����Ͻ� �� �����ϴ�.");
			return true;
		    }
		    int reqMins = Integer.parseInt(info[3]);

		    if (reqMins > 0) {
			sender.sendMessage(prefix + "���� ����� ��c" + info[1] + " ��6�̰�, ��c" + info[3] + " ��6�� �Ŀ� �����մϴ�.");
		    } else {
			changer.CheckRank((Player) sender);
		    }

		}
		if (info[2] != null) {
		    sender.sendMessage(prefix + "�� ��쿡�� ���� ����ϴ� ���忡�� ����˴ϴ�. (��c" + info[2] + "��6)");
		}
	    }
	    return true;
	}

	else if (args.length < 2) {
	    return false;
	}

	// set variables
	String playerName = args[1].toLowerCase();
	Player player = Bukkit.getPlayer(playerName);
	// handle CHECK OTHERS command
	if (args[0].equalsIgnoreCase("Ȯ��")) {
	    // check permissions
	    if (!sender.hasPermission("autorank.checkothers") && !overridePerms) {
		sender.sendMessage(noPerm);
		return true;
	    }

	    Integer time = (Integer) data.get(playerName);
	    if (time == null) {
		sender.sendMessage(prefix + "��c�� �÷��̾��� �ð��� �������� �ʽ��ϴ�.");
	    } else {
		sender.sendMessage(prefix + "��c" + playerName + " ��6���� �ð��� ��c" + time / 60 + " �ð�, " + time % 60 + " �� ��6�Դϴ�.");

		if (player != null) {
		    String[] info = getRankInfo(player);
		    // 0 - current rank
		    // 1 - next rank
		    // 2 - in world
		    // 3 - time to next rank
		    sender.sendMessage(prefix + "��c" + playerName + " ��6���� ���� ��c" + info[0] + " ��6�Դϴ�.");
		    if (info[1] != null && info[3] != null) {
			sender.sendMessage(prefix + "���� ����� ��c" + info[1] + " ��6�̰�, ��c" + info[3] + " ��6�� �Ŀ� �����մϴ�.");
		    }
		    if (info[2] != null) {
			    sender.sendMessage(prefix + "�� ��쿡�� ���� ����ϴ� ���忡�� ����˴ϴ�. (��c" + info[2] + "��6)");
		    }
		} else {
		    sender.sendMessage(prefix + "��c�� �÷��̾�� �������� �̹Ƿ� Ȯ���� �� �����ϴ�.");
		}
	    }
	    return true;
	}
	// handle SET command
	else if (args[0].equalsIgnoreCase("����")) {
	    // check permissions
	    if (!sender.hasPermission("autorank.set") && !overridePerms) {
		sender.sendMessage(noPerm);
		return true;
	    }
	    try {
		int value = Integer.parseInt(args[2]);
		data.set(playerName, (Integer) value);
		sender.sendMessage(prefix + "��c" + playerName + "��6���� �ð��� ��c" + value + " ��6������ �����Ͽ����ϴ�.");
		return true;
	    } catch (Exception e) {
			sender.sendMessage(prefix + "�װ��� ������ �� �����ϴ�.");
		return true;
	    }
	} else if (args[0].equalsIgnoreCase("add")) {
	    // check permissions
	    if (!sender.hasPermission("autorank.set") && !overridePerms) {
		sender.sendMessage(noPerm);
		return true;
	    }
	    Integer current = (Integer) data.get(playerName);
	    if (current == null) {
			sender.sendMessage("�������� �ʴ� �÷��̾� �Դϴ�.");
		return true;
	    }
	    try {
		int value = Integer.parseInt(args[2]);
		value = value + current;
		data.set(playerName, (Integer) value);
		sender.sendMessage(prefix + "��c" + playerName + "��6���� �ð��� ��c" + value + " ��6������ �����Ͽ����ϴ�.");
		return true;
	    } catch (Exception e) {
			sender.sendMessage(prefix + "�װ��� ������ �� �����ϴ�.");
		return true;
	    }
	} else if (args[0].equalsIgnoreCase("rem")) {
	    // check permissions
	    if (!sender.hasPermission("autorank.set") && !overridePerms) {
		sender.sendMessage(noPerm);
		return true;
	    }
	    Integer current = (Integer) data.get(playerName);
	    if (current == null) {
		sender.sendMessage("�������� �ʴ� �÷��̾� �Դϴ�.");
		return true;
	    }
	    try {
		int value = Integer.parseInt(args[2]);
		if (value > current) {
		    current = value;
		}
		value = value - current;
		data.set(playerName, (Integer) value);
		sender.sendMessage(prefix + "��c" + playerName + "��6���� �ð��� ��c" + value + " ��6������ �����Ͽ����ϴ�.");
		return true;
	    } catch (Exception e) {
		sender.sendMessage(prefix + "�װ��� ������ �� �����ϴ�.");
		return true;
	    }
	}

	return false;
    }

    public void logMessage(String msg) {
	PluginDescriptionFile pdFile = this.getDescription();
	this.log.info(pdFile.getName() + " " + pdFile.getVersion() + " : " + msg);
    }

    public void debugMessage(String msg) {
	if (debug) {
	    this.log.info("Autorank debug: " + msg);
	}
    }

    public boolean getDebug() {
	return debug;
    }

    public Config getConf() {// name is weird because getConfig is used by
			     // JavaPlugin
	return config;
    }

    public DataStorage getData() {
	return data;
    }

    public VaultHandler getVault() {
	return vault;
    }

    public Leaderboard getLeaderboard() {
	return leaderboard;
    }

    public String[] getRankInfo(Player player) {
	// returns:
	// 0 - current rank
	// 1 - next rank
	// 2 - in world
	// 3 - time to next rank
	String[] res = new String[4];

	String playerName = player.getName().toLowerCase();
	String world = player.getWorld().getName();

	String[] Playergroups = vault.getGroups(player, world);
	if (Playergroups.length == 0) {
	    Playergroups = new String[] { "default" };
	}

	res[0] = "";
	for (String group : Playergroups) {
	    if (!res[0].equals(""))
		res[0] += ", ";
	    res[0] += group;
	}

	boolean found = false;
	int entry = 1;

	for (int i = 0; !found && i < Playergroups.length; i++) {

	    entry = 1;
	    found = false;
	    while (config.get(entry + ".from") != null && !found) {

		if (Playergroups[i].equals((String) config.get(entry + ".from")) && ((config.get(entry + ".world") == null)
			|| world.equals((String) config.get(entry + ".world")))) {
		    found = true;
		} else {
		    entry++;
		}
	    }

	}

	if (found == true) {
	    res[1] = (String) config.get(entry + ".to");
	    res[2] = (String) config.get(entry + ".world");
	}

	Integer timePlayed = (Integer) data.get(playerName);

	if (timePlayed != null && config.get(entry + ".required minutes played") != null)
	    res[3] = ((Integer) ((Integer) config.get(entry + ".required minutes played") - timePlayed)).toString();
	;

	return res;
    }
}
