package me.espoo.PTweaks;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.PluginManager;

import me.espoo.main.main;

public class MonsterLimiter implements CommandExecutor, Listener {
	private main mPlugin;
	private HashMap<String, Integer> mobs = new HashMap();
	private HashMap<String, Integer> animals = new HashMap();

	public MonsterLimiter(main plugin) {
		this.mPlugin = plugin;
		plugin.getCommand("����").setExecutor(this);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public void onEnable() {
		for (World w : this.mPlugin.getServer().getWorlds()) {
			int limitMonster = this.mPlugin.monsterLimiter.getInt("worlds." + w.getName() + ".monsterLimit", 2400);
			int limitAnimal = this.mPlugin.monsterLimiter.getInt("worlds." + w.getName() + ".animalLimit", 2400);

			if (limitMonster != -1) {
				this.mobs.put(w.getName(), Integer.valueOf(limitMonster));
			}

			if (limitAnimal != -1)
				this.animals.put(w.getName(), Integer.valueOf(limitAnimal));
		}
	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		World world = event.getEntity().getLocation().getWorld();
		if (((event.getEntity() instanceof Animals)) || (event.getEntityType() == EntityType.SQUID)
				|| (event.getEntityType() == EntityType.BAT)) {
			if (!this.animals.containsKey(world.getName()))
				return;

			int mobCount = 0;
			for (Entity e : world.getLivingEntities()) {
				if ((e instanceof LivingEntity))
					continue;
				if (((e instanceof Animals)) || (e.getType() == EntityType.SQUID) || (e.getType() == EntityType.BAT)) {
					mobCount++;
				}
			}

			if (mobCount >= ((Integer) this.animals.get(world.getName())).intValue())
				event.setCancelled(true);
		} else {
			if (!this.mobs.containsKey(world.getName()))
				return;

			int mobCount = 0;
			for (Entity e : world.getLivingEntities()) {
				if (((e instanceof LivingEntity)) || (((e instanceof Animals)) && (e.getType() == EntityType.SQUID)
						&& (e.getType() == EntityType.BAT)))
					continue;
				mobCount++;
			}

			if (mobCount >= ((Integer) this.mobs.get(world.getName())).intValue())
				event.setCancelled(true);
		}
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender.hasPermission("ptweaks.ptweaks")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + "============��ɾ� ����============");
				sender.sendMessage(ChatColor.YELLOW + " /���� info ��f- ���� ������ ���� ���� ���� ���ϴ�.");
				sender.sendMessage(ChatColor.YELLOW + " /���� enablelimit [World] ��f- �ش� ���忡 ���� ������ Ȱ��ȭ �մϴ�.");
				sender.sendMessage(ChatColor.YELLOW + " /���� removelimit [World] ��f- �ش� ���忡 ���� ������ ��Ȱ��ȭ �մϴ�.");
				sender.sendMessage(ChatColor.YELLOW + " /���� kill <World> <����> ��f- ��ƼƼ�� �����մϴ�. (����, ������ �������)");
				sender.sendMessage(ChatColor.RED + "============��ɾ� ����============");
				return true;
			}

			if (args[0].equalsIgnoreCase("info")) {
				sender.sendMessage(ChatColor.YELLOW + "���� �ѵ� ����:");
				for (World world : this.mPlugin.getServer().getWorlds()) {
					if (this.mobs.containsKey(world.getName())) {
						int limit = ((Integer) this.mobs.get(world.getName())).intValue();
						sender.sendMessage(ChatColor.DARK_GREEN + " [" + world.getName() + "] ������ ���� ���� �� : "
								+ Integer.toString(limit));
					} else {
						sender.sendMessage(ChatColor.DARK_GREEN + " " + world.getName() + " ������ ���� ���� ������ �����ϴ�.");
					}
					if (this.animals.containsKey(world.getName())) {
						int limit = ((Integer) this.animals.get(world.getName())).intValue();
						sender.sendMessage(ChatColor.DARK_GREEN + " [" + world.getName() + "] ������ ���� ���� �� : "
								+ Integer.toString(limit));
					} else {
						sender.sendMessage(ChatColor.DARK_GREEN + " " + world.getName() + " ������ ���� ���� ������ �����ϴ�.");
					}
				}

			} else if (args[0].equalsIgnoreCase("enablelimit")) {
				if (args.length >= 2) {
					if (this.mPlugin.getServer().getWorld(args[1]) != null) {
						if (args.length == 3) {
							if (args[2].equalsIgnoreCase("animals")) {
								if (!this.animals.containsKey(args[1])) {
									this.animals.put(args[1], Integer.valueOf(this.mPlugin.monsterLimiter
											.getInt("worlds." + args[1] + ".animalLimit", 2400)));
								}
								sender.sendMessage(ChatColor.GREEN + "���������� " + args[1] + "������ ���� ���� ������ Ȱ��ȭ�߽��ϴ�.");
							} else if (args[2].equalsIgnoreCase("monsters")) {
								if (!this.mobs.containsKey(args[1])) {
									this.mobs.put(args[1], Integer.valueOf(this.mPlugin.monsterLimiter
											.getInt("worlds." + args[1] + ".animalLimit", 2400)));
								}
								sender.sendMessage(ChatColor.GREEN + "���������� " + args[1] + "������ ���� ���� ������ Ȱ��ȭ�߽��ϴ�.");
							} else {
								sender.sendMessage("��f[��4����f] ��c����ε� ���ڰ��� �Է����ּ���.");
							}
						} else {
							if (!this.mobs.containsKey(args[1])) {
								this.mobs.put(args[1], Integer.valueOf(this.mPlugin.monsterLimiter
										.getInt("worlds." + args[1] + ".animalLimit", 2400)));
							}
							if (!this.animals.containsKey(args[1])) {
								this.animals.put(args[1], Integer.valueOf(this.mPlugin.monsterLimiter
										.getInt("worlds." + args[1] + ".monsterLimit", 2400)));
							}
							sender.sendMessage(ChatColor.GREEN + "���������� " + args[1] + "������ ���� ���� ������ Ȱ��ȭ�߽��ϴ�.");
						}
					}
				} else {
					for (World world : this.mPlugin.getServer().getWorlds()) {
						if (!this.mobs.containsKey(world.getName())) {
							this.mobs.put(world.getName(), Integer.valueOf(this.mPlugin.monsterLimiter
									.getInt("worlds." + world.getName() + ".monsterLimit", 2400)));
						}
						if (!this.animals.containsKey(world.getName())) {
							this.animals.put(world.getName(), Integer.valueOf(this.mPlugin.monsterLimiter
									.getInt("worlds." + world.getName() + ".monsterLimit", 2400)));
						}
					}
					sender.sendMessage("��e��� ������ ��ƼƼ ���� ������ Ȱ��ȭ �߽��ϴ�.");
				}

			} else if (args[0].equalsIgnoreCase("removelimit")) {
				if (args.length >= 2) {
					if (this.mPlugin.getServer().getWorld(args[1]) != null) {
						if (args.length == 3) {
							if (args[2].equalsIgnoreCase("animals")) {
								if (this.animals.containsKey(args[1])) {
									this.animals.remove(args[1]);
								}
								sender.sendMessage(ChatColor.GREEN + "���������� " + args[1] + "������ ���� ���� ������ ��Ȱ��ȭ�߽��ϴ�.");
							} else if (args[2].equalsIgnoreCase("monsters")) {
								if (this.mobs.containsKey(args[1])) {
									this.mobs.remove(args[1]);
								}
								sender.sendMessage(ChatColor.GREEN + "���������� " + args[1] + "������ ���� ���� ������ ��Ȱ��ȭ�߽��ϴ�.");
							} else {
								sender.sendMessage("��f[��4����f] ��c����ε� ���ڰ��� �Է����ּ���.");
							}
						} else {
							if (this.mobs.containsKey(args[1])) {
								this.mobs.remove(args[1]);
							}
							if (this.animals.containsKey(args[1])) {
								this.animals.remove(args[1]);
							}
							sender.sendMessage(ChatColor.RED + args[1] + "������ ���� ������ �����߽��ϴ�.");
						}
					}
				} else {
					for (World world : this.mPlugin.getServer().getWorlds()) {
						if (this.mobs.containsKey(world.getName())) {
							this.mobs.remove(world.getName());
						}
						if (this.animals.containsKey(world.getName())) {
							this.animals.remove(world.getName());
						}
					}
					sender.sendMessage("��e��� ������ ��ƼƼ ���� ������ ��Ȱ��ȭ �߽��ϴ�.");
				}
			} else if (args[0].equalsIgnoreCase("kill")) {
				Entity e;
				if (args.length == 3) {
					double radius = 0.0D;
					if (!(sender instanceof Player)) {
						sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "���" + ChatColor.WHITE
								+ "] ��Ŷ���� ������ �Ұ��� �մϴ�.");
						return true;
					}
					try {
						radius = Integer.valueOf(args[2]).intValue();
					} catch (NumberFormatException e1) {
						sender.sendMessage(ChatColor.RED + "Please use a numeric value!");
						return true;
					}
					if (this.mPlugin.getServer().getWorld(args[1]) != null) {
						Player player = (Player) sender;
						for (Iterator localIterator2 = player.getNearbyEntities(radius, radius, radius)
								.iterator(); localIterator2.hasNext();) {
							e = (Entity) localIterator2.next();
							if ((e instanceof Player))
								continue;
							e.remove();
						}
						sender.sendMessage(
								ChatColor.GREEN + "���������� �ݰ� ��e" + Double.toString(radius) + "��a�� ���� ��ƼƼ�� �����߽��ϴ�.");
					} else {
						sender.sendMessage("��f[��4����f] ��4�� ����� �������� �ʽ��ϴ�.");
					}
				} else if (args.length == 2) {
					if (this.mPlugin.getServer().getWorld(args[1]) != null) {
						for (Entity e1 : this.mPlugin.getServer().getWorld(args[1]).getEntities()) {
							if ((e1 instanceof Player))
								continue;
							e1.remove();
						}
						sender.sendMessage(ChatColor.GREEN + "���������� " + args[1] + "������ ��ƼƼ�� ��� �����߽��ϴ�.");
					} else {
						sender.sendMessage("��f[��4����f] ��4�� ����� �������� �ʽ��ϴ�.");
					}
				} else {
					for (World w : this.mPlugin.getServer().getWorlds()) {
						for (Entity e1 : w.getEntities()) {
							if ((e1 instanceof Player))
								continue;
							e1.remove();
						}
					}
					sender.sendMessage(ChatColor.GREEN + "���������� ��� ��ƼƼ�� �����߽��ϴ�.");
				}
			}
			return true;
		}

		sender.sendMessage("��f[��4����f] ��c����� ������ �����ϴ�.");
		return true;
	}
}