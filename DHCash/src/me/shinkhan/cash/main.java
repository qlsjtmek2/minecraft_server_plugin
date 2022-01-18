package me.shinkhan.cash;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
	private Config config;

	public void onEnable() {
		Load();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				main.this.Save();
			}
		}, 100L, 100L);
		getServer().getPluginManager().registerEvents(this, this);
	}

	public void onDisable() {
		Save();
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory().getName().startsWith("§c§0")) {
			e.setCancelled(true);
			try {
				String shopname = e.getInventory().getName().replace("§c§0", "").replace("§", "§");
				ItemStack rawItem = e.getCurrentItem().clone();
				if (rawItem == null) {
					return;
				}
				if (rawItem.getTypeId() == 102) {
					return;
				}
				Player p = (Player) e.getWhoClicked();
				ItemMeta im = rawItem.getItemMeta();
				List<String> lores = im.getLore();
				
				if (((String) lores.get(lores.size() - 1)).equals("§c§f§l[ §b§l캐시로 구매 가능한 아이템입니다. §f§l]")) {
					lores.remove(lores.size() - 1);
				}

				if (((String) lores.get(lores.size() - 1))
						.equals("§c§f§l[ §a§l가격 : §6§l"
								+ this.config.getInt(new StringBuilder("shop_").append(shopname).append("_price_")
										.append(toString(Integer.valueOf(e.getRawSlot()))).toString())
						+ "§c§l캐시 §f§l]")) {
					lores.remove(lores.size() - 1);
				}

				im.setLore(lores);
				rawItem.setItemMeta(im);

				if (InventoryUtil.equals(
						this.config.getItemStack("shop_" + shopname + "_" + toString(Integer.valueOf(e.getRawSlot()))),
						rawItem)) {
					int amount = this.config
							.getInt("shop_" + shopname + "_price_" + toString(Integer.valueOf(e.getRawSlot())));
					if (!hasCash(p.getName(), amount)) {
						p.sendMessage("§c캐시가 부족합니다.");
						return;
					}

					if (!InventoryUtil.isFits(
							this.config
									.getItemStack("shop_" + shopname + "_" + toString(Integer.valueOf(e.getRawSlot()))),
							p.getInventory())) {
						p.sendMessage("§c인벤토리에 공간이 없습니다.");
						return;
					}
					discountCash(p.getName(), amount);
					InventoryUtil.addItem(this.config
							.getItemStack("shop_" + shopname + "_" + toString(Integer.valueOf(e.getRawSlot()))).clone(),
							p.getInventory());
					p.sendMessage("§a아이템을 성공적으로 구매하셨습니다.");
				}
			} catch (Exception localException) {
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§e ---- §6캐시 §e-- §6페이지 §c1§6/§c1 §e----");
			sender.sendMessage("§6/캐시샵 §f- 캐시샵을 구경합니다.");
			sender.sendMessage("§6/캐시 보기 <닉네임>, /ca v <닉네임> §f- 자신의 또는 상대방의 캐시를 봅니다.");
			if (sender.isOp() && sender.getName().equalsIgnoreCase("shinkhan")) {
				sender.sendMessage("§6/캐시 주기, /ca g <닉네임> <캐시> §f- 캐시를 지급합니다.");
				sender.sendMessage("§6/캐시 뺏기, /ca t <닉네임> <캐시> §f- 캐시를 뺐습니다.");
				sender.sendMessage("§6/캐시 설정, /ca s <닉네임> <캐시> §f- 캐시를 설정합니다.");
				sender.sendMessage("§6/캐시 상점, /ca sh §f- 캐시 상점 명령어를 봅니다.");
			}
			return true;
		}
		
		if (args[0].equals("보기") || args[0].equals("v")) {
			if (args.length == 1) {
				if (!(sender instanceof Player)) {
					sender.sendMessage("플레이어만 사용이 가능한 명령어입니다.");
					return true;
				}
				
				sender.sendMessage("§6당신은 §c" + toString(Long.valueOf(getCash(sender.getName()))) + " §6만큼의 캐시를 보유하고 계십니다.");
				return true;
			}
			Player p = Bukkit.getPlayerExact(args[1]);
			if (p == null) {
				sender.sendMessage("§c해당 플레이어는 온라인이 아닙니다.");
				return true;
			}
			sender.sendMessage("§6" + p.getName() + " 님은 §c" + toString(Long.valueOf(getCash(sender.getName()))) + " §6만큼의 캐시를 보유하고 계십니다.");
			return true;
		}
		if (args[0].equals("주기") || args[0].equals("g")) {
			if (!sender.isOp()) {
				sender.sendMessage("§c당신은 이 명령어를 사용할 권한이 없습니다.");
				return true;
			}
			if (args.length != 3) {
				sender.sendMessage("§6/캐시 주기 <닉네임> <캐시> §f- 캐시를 지급합니다.");
				return true;
			}
			long i = getLong(args[2]).longValue();
			if (i < 1L) {
				sender.sendMessage("§c<캐시> 에는 숫자만 적어주시기 바랍니다.");
				return true;
			}
			addCash(args[1], i);
			Player p = Bukkit.getPlayerExact(args[1]);
			if (p != null) {
				p.sendMessage("§c" + sender.getName() + " §6님이 당신에게 §c" + toString(Long.valueOf(i)) + " §6만큼의 캐시를 지급했습니다.");
			}
			
			sender.sendMessage("§c" + args[1] + " §6님에게 §c" + toString(Long.valueOf(i)) + " §6만큼의 캐시를 지급했습니다.");
			return true;
		}
		if (args[0].equals("뺏기") || args[0].equals("t")) {
			if (!sender.isOp()) {
				sender.sendMessage("§c당신은 이 명령어를 사용할 권한이 없습니다.");
				return true;
			}
			if (args.length != 3) {
				sender.sendMessage("§6/캐시 뺏기 <닉네임> <캐시> §f- 캐시를 뺐습니다.");
				return true;
			}
			long i = getLong(args[2]).longValue();
			if (i < 1L) {
				sender.sendMessage("§c<캐시> 에는 숫자만 적어주시기 바랍니다.");
				return true;
			}
			discountCash(args[1], i);
			Player p = Bukkit.getPlayerExact(args[1]);
			if (p != null) {
				p.sendMessage("§c" + sender.getName() + " §6님이 내 캐시를 §c" + toString(Long.valueOf(i)) + " §6만큼 제거하였습니다.");
			}
			sender.sendMessage("§c" + args[1] + " §6님에게서 §c" + toString(Long.valueOf(i)) + " §6만큼의 캐시를 제거하였습니다.");
			return true;
		}
		if (args[0].equals("설정") || args[0].equals("s")) {
			if (!sender.isOp()) {
				sender.sendMessage("§c당신은 이 명령어를 사용할 권한이 없습니다.");
				return true;
			}
			if (args.length != 3) {
				sender.sendMessage("§6/캐시 설정 <닉네임> <캐시> §f- 캐시를 설정합니다.");
				return true;
			}
			long i = getLong(args[2]).longValue();
			if (i < 0L) {
				sender.sendMessage("§c<캐시> 에는 숫자만 적어주시기 바랍니다.");
				return true;
			}
			setCash(args[1], i);
			Player p = Bukkit.getPlayerExact(args[1]);
			if (p != null) {
				p.sendMessage("§c" + sender.getName() + " §6님이 내 캐시를 §c" + toString(Long.valueOf(i)) + " §6만큼 설정했습니다.");
			}
			sender.sendMessage("§c" + args[1] + " §6님의 캐시를 §c" + toString(Long.valueOf(i)) + " §6만큼으로 설정했습니다.");
			return true;
		}
		if (args[0].equals("상점") || args[0].equals("sh")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("§c플레이어만 사용이 가능한 명령어입니다.");
				return true;
			}
			if (!sender.isOp()) {
				sender.sendMessage("§c당신은 이 명령어를 사용할 권한이 없습니다.");
				return true;
			}
			Player p = (Player) sender;
			if (args.length == 1) {
				p.sendMessage("§e ---- §6캐시 상점 §e-- §6페이지 §c1§6/§c1 §e----");
				p.sendMessage("§6/캐시 상점, /ca sh <이름> §f- 캐시 상점을 엽니다.");
				p.sendMessage("§6/캐시 상점 생성, /ca sh c <이름> <1~6> §f- 캐시 상점을 생성합니다.");
				p.sendMessage("§6/캐시 상점 진열, /ca sh a <이름> <가격> <위치> §f- 캐시상점을 수정합니다.");
				p.sendMessage("§6/캐시 상점 삭제, /ca sh s <이름> <위치> §f- 캐시상점을 수정합니다.");
				p.sendMessage("§6/캐시 상점 제거, /ca sh r <이름> §f- 캐시상점을 제거합니다.");
				p.sendMessage("§6/캐시 상점 목록, /ca sh l §f- 캐시상점 목록을 봅니다.");
			} else if (args.length == 2) {
				if (args[1].equals("목록") || args[1].equals("l")) {
					Set<String> l = this.config.getKeys(true);
					ArrayList<String> shopnames = new ArrayList<String>();
					for (int i = 0; i < l.size(); i++) {
						String n = l.toArray()[i].toString();
						if ((n.startsWith("shop_")) && (n.endsWith("_line"))) {
							shopnames.add(n.replace("shop_", "").replace("_line", ""));
						}
					}
					if (shopnames.isEmpty()) {
						p.sendMessage("§c캐시 상점 목록이 비었습니다.");
						return true;
					}
					p.sendMessage("§e ---- §6캐시 상점 §e-- §c목록 §e----");
					for (int i = 0; i < shopnames.size(); i++) {
						p.sendMessage(((String) shopnames.get(i)).replace("§", "§"));
					}
					return true;
				}
				String name = args[1];
				if (!this.config.isInt("shop_" + name + "_line")) {
					p.sendMessage("§c상점이 올바르지 않습니다.");
					return true;
				}
				Inventory inv = Bukkit.createInventory(null, this.config.getInt("shop_" + name + "_line") * 9, "§c§0" + name.replace("§", "§"));
				ItemStack nullitem = new ItemStack(Material.AIR);
				for (int i = 0; i < this.config.getInt("shop_" + name + "_line") * 9; i++) {
					if (this.config.contains("shop_" + name + "_" + toString(Integer.valueOf(i)))) {
						if (this.config.isItemStack("shop_" + name + "_" + toString(Integer.valueOf(i)))) {
							try {
								ItemStack item = this.config.getItemStack("shop_" + name + "_" + toString(Integer.valueOf(i))).clone();
								ItemMeta im = item.getItemMeta();
								List<String> lore = new ArrayList<String>();
								if (im.hasLore()) {
									lore = im.getLore();
								}

								im.setLore(lore);
								item.setItemMeta(im);
								inv.setItem(i, item);
							} catch (Exception ex) {
								ex.printStackTrace();
								inv.setItem(i, nullitem);
							}	
						}
					} else inv.setItem(i, nullitem); 
				}
				p.openInventory(inv);
			} else {
				if (!sender.isOp()) {
					sender.sendMessage("§c당신은 이 명령어를 사용할 권한이 없습니다.");
					return true;
				}
				if (args[1].equals("생성") || args[1].equals("c")) {
					if (args.length != 4) {
						p.sendMessage("§6/캐시 상점 생성 <이름> <1~6> §f- 캐시 상점을 생성합니다.");
						return true;
					}
					String name = args[2];
					if (this.config.isInt("shop_" + name + "_line")) {
						p.sendMessage("§c해당 이름의 상점이 이미 있습니다.");
						return true;
					}
					Long line = getLong(args[3]);
					if ((line.longValue() < 1L) || (line.longValue() > 6L)) {
						sender.sendMessage("§c상점의 라인이 올바르지 않습니다.");
						return true;
					}
					int lines = Integer.parseInt(Long.toString(line.longValue()));
					this.config.set("shop_" + name + "_line", Integer.valueOf(lines));
					sender.sendMessage("§6상점이 정상적으로 생성되었습니다.");
				} else if (args[1].equals("진열") || args[1].equals("a")) {
					if (args.length != 5) {
						p.sendMessage("§6/캐시 상점 진열 <이름> <가격> <위치> §f- 캐시상점을 수정합니다.");
						return true;
					}
					String name = args[2];
					if (!this.config.isInt("shop_" + name + "_line")) {
						p.sendMessage("§c상점이 올바르지 않습니다.");
						return true;
					}
					long price = getLong(args[3]).longValue();
					if (price < 0L) {
						sender.sendMessage("§c가격이 올바르지 않습니다.");
						return true;
					}
					long loc = getLong(args[4]).longValue();
					if ((loc < 1L) || (loc > 54L)) {
						sender.sendMessage("§c위치가 올바르지 않습니다.");
						return true;
					}
					ItemStack item = p.getItemInHand();
					if ((item == null) || (item.getType() == Material.AIR)) {
						sender.sendMessage("§c손에 아이템을 들고있지 않습니다.");
						return true;
					}
					this.config.set("shop_" + name + "_" + toString(Long.valueOf(loc - 1L)), item.clone());
					this.config.set("shop_" + name + "_price_" + toString(Long.valueOf(loc - 1L)), Long.valueOf(price));
					if (this.config.contains("shop_" + name + "_" + toString(Long.valueOf(loc - 1L)))) {
						sender.sendMessage("§6" + toString(Long.valueOf(loc)) + "칸에 정상적으로 §c등록§6되었습니다.");
						sender.sendMessage("("
								+ toString(Long.valueOf(this.config.getLong(new StringBuilder("shop_").append(name)
										.append("_price_").append(toString(Long.valueOf(loc - 1L))).toString())))
								+ " 캐시)");
					} else {
						sender.sendMessage("§c[ERROR] 등록되지 않았습니다.");
					}
				} else if (args[1].equals("삭제") || args[1].equals("s")) {
					if (args.length != 4) {
						p.sendMessage("§6/캐시 상점 삭제 <이름> <위치> §f- 캐시상점을 수정합니다.");
						return true;
					}
					String name = args[2];
					if (!this.config.isInt("shop_" + name + "_line")) {
						p.sendMessage("§c상점이 올바르지 않습니다.");
						return true;
					}
					long loc = getLong(args[3]).longValue();
					if ((loc < 1L) || (loc > 54L)) {
						sender.sendMessage("§c위치가 올바르지 않습니다.");
						return true;
					}
					this.config.set("shop_" + name + "_" + toString(Long.valueOf(loc - 1L)), null);
					this.config.set("shop_" + name + "_price_" + toString(Long.valueOf(loc - 1L)), null);
					sender.sendMessage("§6정상적으로 삭제되었습니다.");
				} else if (args[1].equals("제거") || args[1].equals("r")) {
					if (args.length != 3) {
						p.sendMessage("§6/캐시 상점 제거 <이름> §f- 캐시상점을 제거합니다.");
						return true;
					}
					String name = args[2];
					if (!this.config.isInt("shop_" + name + "_line")) {
						p.sendMessage("§c상점이 올바르지 않습니다.");
						return true;
					}
					this.config.set("shop_" + name + "_line", null);
					for (int i = 1; i < 55; i++) {
						this.config.set("shop_" + name + "_" + toString(Integer.valueOf(i - 1)), null);
						this.config.set("shop_" + name + "_price_" + toString(Integer.valueOf(i - 1)), null);
					}
					sender.sendMessage("§6정상적으로 제거되었습니다.");
				}
			}
		}
		return true;
	}

	public String toString(Object o) {
		return o.toString();
	}

	public Long getLong(String n) {
		try {
			return Long.valueOf(Long.parseLong(n));
		} catch (Exception localException) {
		}
		return Long.valueOf(-1L);
	}

	public long getCash(String name) {
		if (!this.config.contains(name + "_cash")) {
			this.config.set(name + "_cash", Long.valueOf(0L));
		}
		return this.config.getLong(name + "_cash");
	}

	private boolean hasCash(String name, long cash) {
		return getCash(name) >= cash;
	}

	public void setCash(String name, long cash) {
		this.config.set(name + "_cash", Long.valueOf(cash < 0L ? 0L : cash));
	}

	public void addCash(String name, long cash) {
		setCash(name, getCash(name) + cash);
	}

	public void discountCash(String name, long cash) {
		long tocash = getCash(name) - cash;
		if (tocash < 0L) {
			tocash = 0L;
		}
		setCash(name, tocash);
	}

	public void Load() {
		try {
			this.config = new Config();
			File f = new File("plugins/DHCash/config.yml");
			if (!f.exists()) {
				new File("plugins/DHCash/").mkdirs();
				f.createNewFile();
			}
			this.config.load(f);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void Save() {
		try {
			File f = new File("plugins/DHCash/config.yml");
			if (!f.exists()) {
				new File("plugins/DHCash/").mkdirs();
				f.createNewFile();
			}
			this.config.save(f);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}