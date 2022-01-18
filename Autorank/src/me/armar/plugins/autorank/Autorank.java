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
	    logMessage("[%s] Vault 플러그인이 없습니다! autorank 기능을 종료합니다.");
	    getPluginLoader().disablePlugin(this);
	}

	// set up general config
	LinkedHashMap<String, Object> configDefaults = new LinkedHashMap<String, Object>();
	String configPath = this.getDataFolder().getAbsolutePath() + File.separator + "config.yml";
	configDefaults.put("Enabled", true);
	configDefaults.put("Debug mode", false);
	configDefaults.put("Message prefix", "&6");
	configDefaults.put("Leaderboard layout", "§c&p §6|§c &n §6-§a &th 시간, &m 분");
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
	    logMessage("현재 Autorank 기능이 비활성화 중입니다. 'enabled'을 true로 변경해주세요.");
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
		    sender.sendMessage("§e ----- §6자동 진급 §e--- §6도움말 §e-----");
		    sender.sendMessage("§6/진급 확인 §f- 진급이 얼마나 남았는지 확인합니다.");
		    sender.sendMessage("§6/진급 확인 <닉네임> §f- 플레이어의 진급 여부를 확인합니다.");
		    sender.sendMessage("§6/진급 순위 §f- 플레이어들의 순위를 봅니다.");
		    sender.sendMessage("§6/진급 시간 §f- 모든 진급의 시간을 봅니다.");
		    sender.sendMessage("§6/진급 설정 <닉네임> <값> §f- 플레이어의 시간을 설정합니다.");
		    sender.sendMessage("§6/진급 재시작 §f- 콘피그를 재시작 합니다.");
		    return false;
		}
		
		else {
		    sender.sendMessage("§e ----- §6자동 진급 §e--- §6도움말 §e-----");
		    sender.sendMessage("§6/진급 확인 §f- 진급이 얼마나 남았는지 확인합니다.");
		    sender.sendMessage("§6/진급 확인 <닉네임> §f- 플레이어의 진급 여부를 확인합니다.");
		    sender.sendMessage("§6/진급 순위 §f- 플레이어들의 순위를 봅니다.");
		    sender.sendMessage("§6/진급 시간 §f- 모든 진급의 시간을 봅니다.");
		    return false;
		}
	}

	String prefix = "&6";
	if (getConf().get("Message prefix") != null) {
	    prefix = (String) getConf().get("Message prefix");
	}

	prefix = prefix.replaceAll("(&([a-f0-9]))", "\u00A7$2");

	String noPerm = "&c당신은 이 명령어를 실행할 권한이 없습니다.";
	boolean overridePerms = sender.hasPermission("autorank.*");

	if (args[0].equalsIgnoreCase("debug")) {
	    if (!(sender instanceof ConsoleCommandSender)) {
		sender.sendMessage("콘솔에서 명령어를 실행해주시기 바랍니다.");
		return true;
	    }

	    sender.sendMessage("----Autorank Debug----");

	    sender.sendMessage("버전: " + this.getDescription().getVersion());

	    final PluginManager pluginManager = getServer().getPluginManager();
	    final Plugin vaultPlugin = pluginManager.getPlugin("Vault");
	    sender.sendMessage("Vault 버전: " + vaultPlugin.getDescription().getVersion());

	    if ((Boolean) config.get("Enabled") == null) {
		    logMessage("현재 Autorank 기능이 비활성화 중입니다. 'enabled'을 true로 변경해주세요.");
	    }
	    
	    if ((Boolean) config.get("Enabled") == false) {
		    logMessage("현재 Autorank 기능이 비활성화 중입니다. 'enabled'을 true로 변경해주세요.");
	    }
	   	    
	    Player[] onlinePlayers = getServer().getOnlinePlayers();
	    for(Player player : onlinePlayers){
		if (player.hasPermission("autorank.exclude") && !player.hasPermission("autorank.sf5k4fg7hu")) {
		    sender.sendMessage(player.getName() + " 님은 autorank.exclude 권한을 가지고 있으므로 순위에 오르지 않습니다.");
		}
		
		if (player.hasPermission("autorank.timeexclude") && !player.hasPermission("autorank.sf5k4fg7hu")) {
		    sender.sendMessage(player.getName() + " 님은 autorank.exclude 권한을 가지고 있으므로 시간이 기록되지 않습니다.");
		}
	    }
	    
	    sender.sendMessage("존재하는 그룹들:");
	    String[] groups = vault.getGroups();
	    for(String group :groups){
		   sender.sendMessage(" - " + group);
	    }
	    
	    sender.sendMessage("설정된 그룹:");
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
			    sender.sendMessage("경고: " + config.get(entry + ".from") + " 이 그룹을 찾을 수 없습니다. 맞춤법을 재 확인해 주세요.");

		
		sender.sendMessage("To: " + (String) config.get(entry + ".to"));
		existingGroup = false;
		    for(String group :groups){
			   if( ((String)config.get(entry + ".to") ).equals(group))
			       existingGroup = true;
		    }
		    
		    if(!existingGroup)
			    sender.sendMessage("경고: " + config.get(entry + ".to") + " 이 그룹을 찾을 수 없습니다. 맞춤법을 재 확인해 주세요.");
		
		sender.sendMessage("월드: " + (String) config.get(entry + ".world"));
		
		entry++;
	    }

	    sender.sendMessage("----Autorank Debug----");

	    return true;
	}

	else if (args[0].equalsIgnoreCase("재시작")) {
	    if (!sender.hasPermission("autorank.reload") && !overridePerms) {
		sender.sendMessage(noPerm);
		return true;
	    }
	    getData().save();
	    logMessage("Autosave 데이터를 저장했습니다.");
	    getConf().load();
	    sender.sendMessage(prefix + "§cAutorank§6를 성공적으로 재시작 했습니다.");
	    return true;
	} else if (args[0].equalsIgnoreCase("도움말") || args[0].equalsIgnoreCase("명령어")) {
		if(sender.isOp() == true) {
		    sender.sendMessage("§e ----- §6자동 진급 §e--- §6도움말 §e-----");
		    sender.sendMessage("§6/진급 확인 §f- 진급이 얼마나 남았는지 확인합니다.");
		    sender.sendMessage("§6/진급 확인 <닉네임> §f- 플레이어의 진급 여부를 확인합니다.");
		    sender.sendMessage("§6/진급 순위 §f- 플레이어들의 순위를 봅니다.");
		    sender.sendMessage("§6/진급 시간 §f- 모든 진급의 시간을 봅니다.");
		    sender.sendMessage("§6/진급 설정 <닉네임> <값> §f- 플레이어의 시간을 설정합니다.");
		    sender.sendMessage("§6/진급 재시작 §f- 콘피그를 재시작 합니다.");
		    return false;
		}
		
		else {
		    sender.sendMessage("§e ----- §6자동 진급 §e--- §6도움말 §e-----");
		    sender.sendMessage("§6/진급 확인 §f- 진급이 얼마나 남았는지 확인합니다.");
		    sender.sendMessage("§6/진급 확인 <닉네임> §f- 플레이어의 진급 여부를 확인합니다.");
		    sender.sendMessage("§6/진급 순위 §f- 플레이어들의 순위를 봅니다.");
		    sender.sendMessage("§6/진급 시간 §f- 모든 진급의 시간을 봅니다.");
		    return false;
		}
	} else if (args[0].equalsIgnoreCase("순위")) {
	    // check permissions
	    if (!sender.hasPermission("autorank.leaderboard") && !overridePerms) {
		sender.sendMessage(noPerm);
		return true;
	    }
	    leaderboard.display(sender, prefix);
	    return true;
	} else if (args[0].equalsIgnoreCase("시간")) {
	    sender.sendMessage("§e ----- §6자동 진급 §e--- §6시간 §e-----");
	    sender.sendMessage("§61. §7(01시간) §3입문자 §7-> §d초급자");
	    sender.sendMessage("§62. §7(06시간) §d초급자 §7-> §c중급자");
	    sender.sendMessage("§63. §7(12시간) §c중급자 §7-> §5상급자");
	    sender.sendMessage("§64. §7(20시간) §5상급자 §7-> §9최상급자");
	    sender.sendMessage("§65. §7(40시간) §9최상급자 §7-> §2숙련자");
		return true;
	} else if (args[0].equalsIgnoreCase("확인") && args.length == 1) {
	    if (!sender.hasPermission("autorank.check") && !overridePerms) {
		sender.sendMessage(noPerm);
		return true;
	    }
	    if (sender instanceof ConsoleCommandSender) {
		sender.sendMessage("콘솔을 확인할 수 없습니다.");
		return true;
	    }
	    Integer time = (Integer) data.get(sender.getName().toLowerCase());
	    if (time == null) {
		sender.sendMessage(prefix + "§c당신의 시간이 존재하지 않습니다. 5분 후에 재확인 해주시기 바랍니다.");
	    } else {
		String[] info = getRankInfo((Player) sender);
		// 0 - current rank
		// 1 - next rank
		// 2 - in world
		// 3 - time to next rank
		sender.sendMessage(prefix + "당신의 계급은 §c" + info[0] + " §6이고, §c" + time / 60 + " 시간, " + time % 60 + " 분 §6플레이 하셨습니다.");

		if (info[1] != null && info[3] != null) {
		    if ((Boolean) config.get("Enabled") == false) {
			sender.sendMessage(prefix + "§c자동 순위 변화는 사용하실 수 없습니다.");
			return true;
		    }
		    int reqMins = Integer.parseInt(info[3]);

		    if (reqMins > 0) {
			sender.sendMessage(prefix + "다음 계급은 §c" + info[1] + " §6이고, §c" + info[3] + " §6분 후에 진급합니다.");
		    } else {
			changer.CheckRank((Player) sender);
		    }

		}
		if (info[2] != null) {
		    sender.sendMessage(prefix + "이 경우에는 현재 사용하는 월드에만 적용됩니다. (§c" + info[2] + "§6)");
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
	if (args[0].equalsIgnoreCase("확인")) {
	    // check permissions
	    if (!sender.hasPermission("autorank.checkothers") && !overridePerms) {
		sender.sendMessage(noPerm);
		return true;
	    }

	    Integer time = (Integer) data.get(playerName);
	    if (time == null) {
		sender.sendMessage(prefix + "§c이 플레이어의 시간이 존재하지 않습니다.");
	    } else {
		sender.sendMessage(prefix + "§c" + playerName + " §6님의 시간은 §c" + time / 60 + " 시간, " + time % 60 + " 분 §6입니다.");

		if (player != null) {
		    String[] info = getRankInfo(player);
		    // 0 - current rank
		    // 1 - next rank
		    // 2 - in world
		    // 3 - time to next rank
		    sender.sendMessage(prefix + "§c" + playerName + " §6님은 현재 §c" + info[0] + " §6입니다.");
		    if (info[1] != null && info[3] != null) {
			sender.sendMessage(prefix + "다음 계급은 §c" + info[1] + " §6이고, §c" + info[3] + " §6분 후에 진급합니다.");
		    }
		    if (info[2] != null) {
			    sender.sendMessage(prefix + "이 경우에는 현재 사용하는 월드에만 적용됩니다. (§c" + info[2] + "§6)");
		    }
		} else {
		    sender.sendMessage(prefix + "§c이 플레이어는 오프라인 이므로 확인할 수 없습니다.");
		}
	    }
	    return true;
	}
	// handle SET command
	else if (args[0].equalsIgnoreCase("설정")) {
	    // check permissions
	    if (!sender.hasPermission("autorank.set") && !overridePerms) {
		sender.sendMessage(noPerm);
		return true;
	    }
	    try {
		int value = Integer.parseInt(args[2]);
		data.set(playerName, (Integer) value);
		sender.sendMessage(prefix + "§c" + playerName + "§6님의 시간을 §c" + value + " §6분으로 변경하였습니다.");
		return true;
	    } catch (Exception e) {
			sender.sendMessage(prefix + "그것을 설정할 수 없습니다.");
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
			sender.sendMessage("존재하지 않는 플레이어 입니다.");
		return true;
	    }
	    try {
		int value = Integer.parseInt(args[2]);
		value = value + current;
		data.set(playerName, (Integer) value);
		sender.sendMessage(prefix + "§c" + playerName + "§6님의 시간을 §c" + value + " §6분으로 변경하였습니다.");
		return true;
	    } catch (Exception e) {
			sender.sendMessage(prefix + "그것을 설정할 수 없습니다.");
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
		sender.sendMessage("존재하지 않는 플레이어 입니다.");
		return true;
	    }
	    try {
		int value = Integer.parseInt(args[2]);
		if (value > current) {
		    current = value;
		}
		value = value - current;
		data.set(playerName, (Integer) value);
		sender.sendMessage(prefix + "§c" + playerName + "§6님의 시간을 §c" + value + " §6분으로 변경하였습니다.");
		return true;
	    } catch (Exception e) {
		sender.sendMessage(prefix + "그것을 설정할 수 없습니다.");
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
