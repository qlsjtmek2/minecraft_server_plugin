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
		plugin.getCommand("몬스터").setExecutor(this);
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
				sender.sendMessage(ChatColor.RED + "============명령어 모음============");
				sender.sendMessage(ChatColor.YELLOW + " /몬스터 info §f- 현재 월드의 몬스터 제한 수를 봅니다.");
				sender.sendMessage(ChatColor.YELLOW + " /몬스터 enablelimit [World] §f- 해당 월드에 몬스터 제한을 활성화 합니다.");
				sender.sendMessage(ChatColor.YELLOW + " /몬스터 removelimit [World] §f- 해당 월드에 몬스터 제한을 비활성화 합니다.");
				sender.sendMessage(ChatColor.YELLOW + " /몬스터 kill <World> <범위> §f- 엔티티을 제거합니다. (월드, 범위는 안적어도됨)");
				sender.sendMessage(ChatColor.RED + "============명령어 모음============");
				return true;
			}

			if (args[0].equalsIgnoreCase("info")) {
				sender.sendMessage(ChatColor.YELLOW + "몬스터 한도 제한:");
				for (World world : this.mPlugin.getServer().getWorlds()) {
					if (this.mobs.containsKey(world.getName())) {
						int limit = ((Integer) this.mobs.get(world.getName())).intValue();
						sender.sendMessage(ChatColor.DARK_GREEN + " [" + world.getName() + "] 월드의 몬스터 제한 수 : "
								+ Integer.toString(limit));
					} else {
						sender.sendMessage(ChatColor.DARK_GREEN + " " + world.getName() + " 월드의 몬스터 스폰 제한이 없습니다.");
					}
					if (this.animals.containsKey(world.getName())) {
						int limit = ((Integer) this.animals.get(world.getName())).intValue();
						sender.sendMessage(ChatColor.DARK_GREEN + " [" + world.getName() + "] 월드의 동물 제한 수 : "
								+ Integer.toString(limit));
					} else {
						sender.sendMessage(ChatColor.DARK_GREEN + " " + world.getName() + " 월드의 동물 스폰 제한이 없습니다.");
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
								sender.sendMessage(ChatColor.GREEN + "성공적으로 " + args[1] + "월드의 동물 스폰 제한을 활성화했습니다.");
							} else if (args[2].equalsIgnoreCase("monsters")) {
								if (!this.mobs.containsKey(args[1])) {
									this.mobs.put(args[1], Integer.valueOf(this.mPlugin.monsterLimiter
											.getInt("worlds." + args[1] + ".animalLimit", 2400)));
								}
								sender.sendMessage(ChatColor.GREEN + "성공적으로 " + args[1] + "월드의 몬스터 스폰 제한을 활성화했습니다.");
							} else {
								sender.sendMessage("§f[§4경고§f] §c제대로된 인자값을 입력해주세요.");
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
							sender.sendMessage(ChatColor.GREEN + "성공적으로 " + args[1] + "월드의 동물 스폰 제한을 활성화했습니다.");
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
					sender.sendMessage("§e모든 월드의 엔티티 스폰 제한을 활성화 했습니다.");
				}

			} else if (args[0].equalsIgnoreCase("removelimit")) {
				if (args.length >= 2) {
					if (this.mPlugin.getServer().getWorld(args[1]) != null) {
						if (args.length == 3) {
							if (args[2].equalsIgnoreCase("animals")) {
								if (this.animals.containsKey(args[1])) {
									this.animals.remove(args[1]);
								}
								sender.sendMessage(ChatColor.GREEN + "성공적으로 " + args[1] + "월드의 동물 스폰 제한을 비활성화했습니다.");
							} else if (args[2].equalsIgnoreCase("monsters")) {
								if (this.mobs.containsKey(args[1])) {
									this.mobs.remove(args[1]);
								}
								sender.sendMessage(ChatColor.GREEN + "성공적으로 " + args[1] + "월드의 몬스터 스폰 제한을 비활성화했습니다.");
							} else {
								sender.sendMessage("§f[§4경고§f] §c제대로된 인자값을 입력해주세요.");
							}
						} else {
							if (this.mobs.containsKey(args[1])) {
								this.mobs.remove(args[1]);
							}
							if (this.animals.containsKey(args[1])) {
								this.animals.remove(args[1]);
							}
							sender.sendMessage(ChatColor.RED + args[1] + "월드의 스폰 제한을 제거했습니다.");
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
					sender.sendMessage("§e모든 월드의 엔티티 스폰 제한을 비활성화 했습니다.");
				}
			} else if (args[0].equalsIgnoreCase("kill")) {
				Entity e;
				if (args.length == 3) {
					double radius = 0.0D;
					if (!(sender instanceof Player)) {
						sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "경고" + ChatColor.WHITE
								+ "] 버킷에선 실행이 불가능 합니다.");
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
								ChatColor.GREEN + "성공적으로 반경 §e" + Double.toString(radius) + "§a블럭 내의 엔티티를 제거했습니다.");
					} else {
						sender.sendMessage("§f[§4경고§f] §4이 월드는 존재하지 않습니다.");
					}
				} else if (args.length == 2) {
					if (this.mPlugin.getServer().getWorld(args[1]) != null) {
						for (Entity e1 : this.mPlugin.getServer().getWorld(args[1]).getEntities()) {
							if ((e1 instanceof Player))
								continue;
							e1.remove();
						}
						sender.sendMessage(ChatColor.GREEN + "성공적으로 " + args[1] + "월드의 엔티티를 모두 제거했습니다.");
					} else {
						sender.sendMessage("§f[§4경고§f] §4이 월드는 존재하지 않습니다.");
					}
				} else {
					for (World w : this.mPlugin.getServer().getWorlds()) {
						for (Entity e1 : w.getEntities()) {
							if ((e1 instanceof Player))
								continue;
							e1.remove();
						}
					}
					sender.sendMessage(ChatColor.GREEN + "성공적으로 모든 엔티티를 제거했습니다.");
				}
			}
			return true;
		}

		sender.sendMessage("§f[§4경고§f] §c당신은 권한이 없습니다.");
		return true;
	}
}