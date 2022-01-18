// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import think.rpgitems.data.Locale;
import think.rpgitems.data.RPGMetadata;
import think.rpgitems.item.ItemManager;
import think.rpgitems.item.LocaleInventory;
import think.rpgitems.item.RPGItem;
import think.rpgitems.lib.gnu.trove.map.hash.TIntByteHashMap;
import think.rpgitems.lib.gnu.trove.map.hash.TIntIntHashMap;
import think.rpgitems.lib.gnu.trove.map.hash.TObjectIntHashMap;
import think.rpgitems.support.WorldGuard;

public class Events implements Listener {
	public static TIntByteHashMap removeArrows;
	public static TIntIntHashMap rpgProjectiles;
	public static TObjectIntHashMap<String> recipeWindows;
	public static HashMap<String, Set<Integer>> drops;
	public static boolean useLocaleInv;
	private HashSet<LocaleInventory> localeInventories;

	static {
		Events.removeArrows = new TIntByteHashMap();
		Events.rpgProjectiles = new TIntIntHashMap();
		Events.recipeWindows = new TObjectIntHashMap<String>();
		Events.drops = new HashMap<String, Set<Integer>>();
		Events.useLocaleInv = false;
	}

	public Events() {
		this.localeInventories = new HashSet<LocaleInventory>();
	}

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		ItemStack item = player.getItemInHand();
		RPGItem rItem;
		if ((rItem = ItemManager.toRPGItem(item)) != null) {
			RPGMetadata meta = RPGItem.getMetadata(item);
			if (rItem.getMaxDurability() != -1) {
				int durability = meta.containsKey(0) ? ((Number) meta.get(0)).intValue() : rItem.getMaxDurability();
				durability--;
				if (durability <= 0) {
					player.setItemInHand(null);
				}
				meta.put(0, Integer.valueOf(durability));
			}
			RPGItem.updateItem(item, Locale.getPlayerLocale(player), meta);
			player.updateInventory();
		}
	}

	@EventHandler
	public void onEntityDeath(final EntityDeathEvent e) {
		final String type = e.getEntity().getType().toString();
		final Random random = new Random();
		if (Events.drops.containsKey(type)) {
			final Set<Integer> items = Events.drops.get(type);
			final Iterator<Integer> it = items.iterator();
			while (it.hasNext()) {
				final int id = it.next();
				final RPGItem item = ItemManager.getItemById(id);
				if (item == null) {
					it.remove();
				} else {
					final double chance = item.dropChances.get(type);
					if (random.nextDouble() >= chance / 100.0) {
						continue;
					}
					e.getDrops().add(item.toItemStack("en_GB"));
				}
			}
		}
	}

	@EventHandler
	public void onProjectileHit(final ProjectileHitEvent e) {
		final Entity entity = (Entity) e.getEntity();
		if (Events.removeArrows.contains(entity.getEntityId())) {
			entity.remove();
			Events.removeArrows.remove(entity.getEntityId());
		} else if (Events.rpgProjectiles.contains(entity.getEntityId())) {
			final RPGItem item = ItemManager.getItemById(Events.rpgProjectiles.get(entity.getEntityId()));
			new BukkitRunnable() {
				public void run() {
					Events.rpgProjectiles.remove(entity.getEntityId());
				}
			}.runTask((org.bukkit.plugin.Plugin) Plugin.plugin);
			if (item == null) {
				return;
			}
			item.projectileHit((Player) ((Projectile) entity).getShooter(), (Projectile) entity);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onProjectileFire(ProjectileLaunchEvent e) {
		LivingEntity shooter = e.getEntity().getShooter();
		if ((shooter instanceof Player)) {
			Player player = (Player) shooter;
			ItemStack item = player.getItemInHand();
			RPGItem rItem = ItemManager.toRPGItem(item);
			if (rItem == null)
				return;
			if ((!WorldGuard.canPvP(player.getLocation())) && (!rItem.ignoreWorldGuard))
				return;
			RPGMetadata meta = RPGItem.getMetadata(item);
			if (rItem.getMaxDurability() != -1) {
				int durability = meta.containsKey(0) ? ((Number) meta.get(0)).intValue() : rItem.getMaxDurability();
				durability--;
				if (durability <= 0) {
					player.setItemInHand(null);
				}
				meta.put(0, Integer.valueOf(durability));
			}
			RPGItem.updateItem(item, Locale.getPlayerLocale(player), meta);
			player.updateInventory();
			rpgProjectiles.put(e.getEntity().getEntityId(), rItem.getID());
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerAction(final PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR || (e.getAction() == Action.RIGHT_CLICK_BLOCK && !e.isCancelled())) {
			ItemStack item = player.getItemInHand();
			if (item.getType() == Material.BOW || item.getType() == Material.SNOW_BALL || item.getType() == Material.EGG || item.getType() == Material.POTION) {
				return;
			}
			RPGItem rItem = ItemManager.toRPGItem(item);
			if (rItem == null) {
				return;
			}
			//if (!WorldGuard.canPvP(player.getLocation()) && !rItem.ignoreWorldGuard) {
			//	return;
			//}
			rItem.rightClick(player);
			if (player.getItemInHand().getTypeId() == 0) {
				player.setItemInHand((ItemStack) null);
			}
			player.updateInventory();
		} else if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			ItemStack item = player.getItemInHand();
			if (item.getType() == Material.BOW || item.getType() == Material.SNOW_BALL || item.getType() == Material.EGG || item.getType() == Material.POTION) {
				return;
			}
			RPGItem rItem = ItemManager.toRPGItem(item);
			if (rItem == null) {
				return;
			}
			//if (!WorldGuard.canPvP(player.getLocation()) && !rItem.ignoreWorldGuard) {
			//	return;
			//}
			rItem.leftClick(player);
		}
	}

	@EventHandler
	public void onBlockPlace(final BlockPlaceEvent e) {
		final ItemStack item = e.getPlayer().getItemInHand();
		if (item == null) {
			return;
		}
		final RPGItem rItem = ItemManager.toRPGItem(item);
		if (rItem == null) {
			return;
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent e) {
		final Player player = e.getPlayer();
		final PlayerInventory in = player.getInventory();
		final String locale = Locale.getPlayerLocale(player);
		for (int i = 0; i < in.getSize(); ++i) {
			final ItemStack item = in.getItem(i);
			if (ItemManager.toRPGItem(item) != null) {
				RPGItem.updateItem(item, locale);
			}
		}
		ItemStack[] armorContents;
		for (int length = (armorContents = player.getInventory().getArmorContents()).length, j = 0; j < length; ++j) {
			final ItemStack item2 = armorContents[j];
			if (ItemManager.toRPGItem(item2) != null) {
				RPGItem.updateItem(item2, locale);
			}
		}
	}

	@EventHandler
	public void onPlayerPickup(final PlayerPickupItemEvent e) {
		final ItemStack item = e.getItem().getItemStack();
		final String locale = Locale.getPlayerLocale(e.getPlayer());
		if (ItemManager.toRPGItem(item) != null) {
			RPGItem.updateItem(item, locale);
		}
		e.getItem().setItemStack(item);
	}

	@EventHandler
	public void onInventoryClose(final InventoryCloseEvent e) {
		if (Events.recipeWindows.containsKey(e.getPlayer().getName())) {
			final int id = Events.recipeWindows.remove(e.getPlayer().getName());
			final RPGItem item = ItemManager.getItemById(id);
			if (item.recipe == null) {
				item.recipe = new ArrayList<ItemStack>();
			}
			item.recipe.clear();
			for (int y = 0; y < 3; ++y) {
				for (int x = 0; x < 3; ++x) {
					final int i = x + y * 9;
					final ItemStack it = e.getInventory().getItem(i);
					item.recipe.add(it);
				}
			}
			item.resetRecipe(item.hasRecipe = true);
			ItemManager.save(Plugin.plugin);
			((Player) e.getPlayer()).sendMessage(ChatColor.AQUA + "Recipe set for " + item.getName());
		} else if (Events.useLocaleInv && e.getView() instanceof LocaleInventory) {
			this.localeInventories.remove(e.getView());
			((LocaleInventory) e.getView()).getView().close();
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onInventoryClick(final InventoryClickEvent e) {
		if (!e.getInventory().getTitle().equalsIgnoreCase("상점 메일") && !e.getInventory().getTitle().equalsIgnoreCase("상점 목록들") && e.getCurrentItem() != null && e.getCurrentItem().getTypeId() != 0) {
			Player player = (Player) e.getWhoClicked();
			RPGItem.updateItem(e.getCurrentItem(), Locale.getPlayerLocale(player));	
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onInventoryOpen(InventoryOpenEvent e) {
		if (e.getView() instanceof LocaleInventory) {
			return;
		}

		if (e.getInventory().getType() != InventoryType.CHEST || !Events.useLocaleInv) {
			if (!e.getInventory().getTitle().equalsIgnoreCase("상점 메일") && !e.getInventory().getTitle().equalsIgnoreCase("상점 목록들")) {
				Iterator<ItemStack> it = (Iterator<ItemStack>) e.getInventory().iterator();
				String locale = Locale.getPlayerLocale((Player) e.getPlayer());
				try {
					while (it.hasNext()) {
						ItemStack item = it.next();
						if (ItemManager.toRPGItem(item) != null) {
							RPGItem.updateItem(item, locale);
						}
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
				}
			}
		} else if (Events.useLocaleInv) {
			LocaleInventory localeInv = new LocaleInventory((Player) e.getPlayer(), e.getView());
			e.setCancelled(true);
			e.getPlayer().openInventory((InventoryView) localeInv);
			this.localeInventories.add(localeInv);
		}
	}
}
