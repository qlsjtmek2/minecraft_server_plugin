package me.espoo.FarmAssist;

import java.util.List;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitScheduler;

import me.espoo.main.main;

public class FarmAssistBlockListener implements Listener {
	public main plugin;

	public FarmAssistBlockListener(main instance) {
		this.plugin = instance;
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerInt(PlayerInteractEvent event) {
		if (this.plugin.config.getBoolean("WorldFarm.Enable Per World")) {
			if (!this.plugin.config.getList("WorldFarm.Enabled Worlds").contains(event.getPlayer().getWorld().getName())) {
				return;
			}
		}
		if ((!this.plugin.playerList.contains(event.getPlayer().getName())) && (event.hasBlock())
				&& (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
				&& ((event.getClickedBlock().getTypeId() == 2) || (event.getClickedBlock().getTypeId() == 3))
				&& (event.getClickedBlock().getRelative(BlockFace.UP).getTypeId() == 0)) {
			Player p = event.getPlayer();
			if ((this.plugin.Enabled) && (this.plugin.config.getBoolean("Wheat.Enabled"))
					&& (this.plugin.config.getBoolean("Wheat.Plant on till"))
					&& ((!this.plugin.config.getBoolean("Main.Use Permissions"))
							|| (p.hasPermission("farmassist.till")))) {
				int id = p.getItemInHand().getTypeId();
				if ((id == 291) || (id == 292) || (id == 293) || (id == 294) || (id == 290)) {
					if (p.getInventory().contains(295)) {
						int spot = p.getInventory().first(295);
						if (spot >= 0) {
							ItemStack next = p.getInventory().getItem(spot);
							if (next.getAmount() > 1) {
								next.setAmount(next.getAmount() - 1);
								p.getInventory().setItem(spot, next);
							} else {
								p.getInventory().setItem(spot, new ItemStack(0));
							}
							p.updateInventory();

							Runnable b = new FarmAssistReplant(this.plugin, event.getClickedBlock(), 9);
							this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, b, 20L);
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();
		World world = player.getWorld();
		int typeid = block.getTypeId();
		Byte data = Byte.valueOf(block.getData());

		if (main.blockE) {
			if (!event.getPlayer().hasPermission("AllPlayer.admin")) {
				event.getPlayer().sendMessage("§6지금은 §c블럭§6을 부술 수 없습니다.");
				event.setCancelled(true);
			}
		}

		if (this.plugin.config.getBoolean("WorldFarm.Enable Per World")) {
			if (!this.plugin.config.getList("WorldFarm.Enabled Worlds").contains(world.getName())) {
				return;
			}
		}
		if (this.plugin.playerList.contains(event.getPlayer().getName())) {
			return;
		}

		if ((typeid == 59) && (this.plugin.Enabled) && (this.plugin.config.getBoolean("Wheat.Enabled"))
				&& ((!this.plugin.config.getBoolean("Main.Use Permissions"))
						|| (player.hasPermission("farmassist.wheat")))) {
			if ((player.getInventory().contains(295))
					&& ((!this.plugin.config.getBoolean("Wheat.Only Replant When Fully Grown"))
							|| (data.byteValue() == 7))) {
				int spot = player.getInventory().first(295);
				if (spot >= 0) {
					ItemStack next = player.getInventory().getItem(spot);
					if (next.getAmount() > 1) {
						next.setAmount(next.getAmount() - 1);
						player.getInventory().setItem(spot, next);
					} else {
						player.getInventory().setItem(spot, new ItemStack(0));
					}

					Runnable b = new FarmAssistReplant(this.plugin, block, 1);
					this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, b, 20L);
				}
			}
		}

		if ((typeid == 83) && (this.plugin.Enabled) && (this.plugin.config.getBoolean("Reeds.Enabled"))
				&& ((!this.plugin.config.getBoolean("Main.Use Permissions"))
						|| (player.hasPermission("farmassist.reeds")))) {
			if (player.getInventory().contains(338)) {
				int spot = player.getInventory().first(338);
				if (spot >= 0) {
					ItemStack next = player.getInventory().getItem(spot);
					if (next.getAmount() > 1) {
						next.setAmount(next.getAmount() - 1);
						player.getInventory().setItem(spot, next);
					} else {
						player.getInventory().setItem(spot, new ItemStack(0));
					}

					Runnable b = new FarmAssistReplant(this.plugin, block, 2);
					this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, b, 20L);
				}
			}
		}

		if ((typeid == 115) && (this.plugin.Enabled) && (this.plugin.config.getBoolean("Netherwart.Enabled"))
				&& ((!this.plugin.config.getBoolean("Main.Use Permissions"))
						|| (player.hasPermission("farmassist.wart")))) {
			if ((player.getInventory().contains(372))
					&& ((!this.plugin.config.getBoolean("Netherwart.Only Replant When Fully Grown"))
							|| (data.byteValue() == 2))) {
				int spot = player.getInventory().first(372);
				if (spot >= 0) {
					ItemStack next = player.getInventory().getItem(spot);
					if (next.getAmount() > 1) {
						next.setAmount(next.getAmount() - 1);
						player.getInventory().setItem(spot, next);
					} else {
						player.getInventory().setItem(spot, new ItemStack(0));
					}

					Runnable b = new FarmAssistReplant(this.plugin, block, 3);
					this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, b, 20L);
				}
			}
		}

		if ((typeid == 127) && (this.plugin.Enabled) && (this.plugin.config.getBoolean("Cocoa Beans.Enabled"))
				&& ((!this.plugin.config.getBoolean("Main.Use Permissions"))
						|| (player.hasPermission("farmassist.cocoa")))) {
			if (player.getInventory().contains(351))
				if (this.plugin.config.getBoolean("Cocoa Beans.Only Replant When Fully Grown")) {
					if (((data.byteValue() == 8 ? 1 : 0) | (data.byteValue() == 9 ? 1 : 0)
							| (data.byteValue() == 10 ? 1 : 0) | (data.byteValue() == 11 ? 1 : 0)) == 0)
						;
				} else {
					int spot = player.getInventory().first(351);
					if (spot >= 0) {
						ItemStack next = player.getInventory().getItem(spot);
						if (next.getAmount() > 1) {
							next.setAmount(next.getAmount() - 1);
							player.getInventory().setItem(spot, next);
						} else {
							player.getInventory().setItem(spot, new ItemStack(0));
						}

						Runnable b = new FarmAssistReplant(this.plugin, block, 4);
						this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, b, 20L);
					}
				}
		}

		if ((typeid == 141) && (this.plugin.Enabled) && (this.plugin.config.getBoolean("Carrots.Enabled"))
				&& ((!this.plugin.config.getBoolean("Main.Use Permissions"))
						|| (player.hasPermission("farmassist.carrots")))) {
			if ((player.getInventory().contains(391))
					&& ((!this.plugin.config.getBoolean("Carrots.Only Replant When Fully Grown"))
							|| (data.byteValue() == 7))) {
				int spot = player.getInventory().first(391);
				if (spot >= 0) {
					ItemStack next = player.getInventory().getItem(spot);
					if (next.getAmount() > 1) {
						next.setAmount(next.getAmount() - 1);
						player.getInventory().setItem(spot, next);
					} else {
						player.getInventory().setItem(spot, new ItemStack(0));
					}
					Runnable b = new FarmAssistReplant(this.plugin, block, 5);
					this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, b, 20L);
				}
			}
		}

		if ((typeid == 142) && (this.plugin.Enabled) && (this.plugin.config.getBoolean("Potatoes.Enabled"))
				&& ((!this.plugin.config.getBoolean("Main.Use Permissions"))
						|| (player.hasPermission("farmassist.potatoes")))) {
			if ((player.getInventory().contains(392))
					&& ((!this.plugin.config.getBoolean("Potatoes.Only Replant When Fully Grown"))
							|| (data.byteValue() == 7))) {
				int spot = player.getInventory().first(392);
				if (spot >= 0) {
					ItemStack next = player.getInventory().getItem(spot);
					if (next.getAmount() > 1) {
						next.setAmount(next.getAmount() - 1);
						player.getInventory().setItem(spot, next);
					} else {
						player.getInventory().setItem(spot, new ItemStack(0));
					}
					Runnable b = new FarmAssistReplant(this.plugin, block, 6);
					this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, b, 20L);
				}
			}
		}

		if ((typeid == 104) && (this.plugin.Enabled) && (this.plugin.config.getBoolean("Pumpkin.Enabled"))
				&& ((!this.plugin.config.getBoolean("Main.Use Permissions"))
						|| (player.hasPermission("farmassist.pumpkin")))) {
			if ((player.getInventory().contains(361))
					&& ((!this.plugin.config.getBoolean("Pumpkin.Only Replant When Fully Grown"))
							|| (data.byteValue() == 7))) {
				int spot = player.getInventory().first(361);
				if (spot >= 0) {
					ItemStack next = player.getInventory().getItem(spot);
					if (next.getAmount() > 1) {
						next.setAmount(next.getAmount() - 1);
						player.getInventory().setItem(spot, next);
					} else {
						player.getInventory().setItem(spot, new ItemStack(0));
					}
					Runnable b = new FarmAssistReplant(this.plugin, block, 7);
					this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, b, 20L);
				}
			}
		}

		if ((typeid == 105) && (this.plugin.Enabled) && (this.plugin.config.getBoolean("Melon.Enabled"))
				&& ((!this.plugin.config.getBoolean("Main.Use Permissions"))
						|| (player.hasPermission("farmassist.melon")))) {
			if ((player.getInventory().contains(362))
					&& ((!this.plugin.config.getBoolean("Melon.Only Replant When Fully Grown"))
							|| (data.byteValue() == 7))) {
				int spot = player.getInventory().first(362);
				if (spot >= 0) {
					ItemStack next = player.getInventory().getItem(spot);
					if (next.getAmount() > 1) {
						next.setAmount(next.getAmount() - 1);
						player.getInventory().setItem(spot, next);
					} else {
						player.getInventory().setItem(spot, new ItemStack(0));
					}
					Runnable b = new FarmAssistReplant(this.plugin, block, 8);
					this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, b, 20L);
				}
			}
		}
	}
}