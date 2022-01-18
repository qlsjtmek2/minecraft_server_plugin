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
		if (e.getInventory().getName().startsWith("��c��0")) {
			e.setCancelled(true);
			try {
				String shopname = e.getInventory().getName().replace("��c��0", "").replace("��", "��");
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
				
				if (((String) lores.get(lores.size() - 1)).equals("��c��f��l[ ��b��lĳ�÷� ���� ������ �������Դϴ�. ��f��l]")) {
					lores.remove(lores.size() - 1);
				}

				if (((String) lores.get(lores.size() - 1))
						.equals("��c��f��l[ ��a��l���� : ��6��l"
								+ this.config.getInt(new StringBuilder("shop_").append(shopname).append("_price_")
										.append(toString(Integer.valueOf(e.getRawSlot()))).toString())
						+ "��c��lĳ�� ��f��l]")) {
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
						p.sendMessage("��cĳ�ð� �����մϴ�.");
						return;
					}

					if (!InventoryUtil.isFits(
							this.config
									.getItemStack("shop_" + shopname + "_" + toString(Integer.valueOf(e.getRawSlot()))),
							p.getInventory())) {
						p.sendMessage("��c�κ��丮�� ������ �����ϴ�.");
						return;
					}
					discountCash(p.getName(), amount);
					InventoryUtil.addItem(this.config
							.getItemStack("shop_" + shopname + "_" + toString(Integer.valueOf(e.getRawSlot()))).clone(),
							p.getInventory());
					p.sendMessage("��a�������� ���������� �����ϼ̽��ϴ�.");
				}
			} catch (Exception localException) {
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("��e ---- ��6ĳ�� ��e-- ��6������ ��c1��6/��c1 ��e----");
			sender.sendMessage("��6/ĳ�ü� ��f- ĳ�ü��� �����մϴ�.");
			sender.sendMessage("��6/ĳ�� ���� <�г���>, /ca v <�г���> ��f- �ڽ��� �Ǵ� ������ ĳ�ø� ���ϴ�.");
			if (sender.isOp() && sender.getName().equalsIgnoreCase("shinkhan")) {
				sender.sendMessage("��6/ĳ�� �ֱ�, /ca g <�г���> <ĳ��> ��f- ĳ�ø� �����մϴ�.");
				sender.sendMessage("��6/ĳ�� ����, /ca t <�г���> <ĳ��> ��f- ĳ�ø� �����ϴ�.");
				sender.sendMessage("��6/ĳ�� ����, /ca s <�г���> <ĳ��> ��f- ĳ�ø� �����մϴ�.");
				sender.sendMessage("��6/ĳ�� ����, /ca sh ��f- ĳ�� ���� ��ɾ ���ϴ�.");
			}
			return true;
		}
		
		if (args[0].equals("����") || args[0].equals("v")) {
			if (args.length == 1) {
				if (!(sender instanceof Player)) {
					sender.sendMessage("�÷��̾ ����� ������ ��ɾ��Դϴ�.");
					return true;
				}
				
				sender.sendMessage("��6����� ��c" + toString(Long.valueOf(getCash(sender.getName()))) + " ��6��ŭ�� ĳ�ø� �����ϰ� ��ʴϴ�.");
				return true;
			}
			Player p = Bukkit.getPlayerExact(args[1]);
			if (p == null) {
				sender.sendMessage("��c�ش� �÷��̾�� �¶����� �ƴմϴ�.");
				return true;
			}
			sender.sendMessage("��6" + p.getName() + " ���� ��c" + toString(Long.valueOf(getCash(sender.getName()))) + " ��6��ŭ�� ĳ�ø� �����ϰ� ��ʴϴ�.");
			return true;
		}
		if (args[0].equals("�ֱ�") || args[0].equals("g")) {
			if (!sender.isOp()) {
				sender.sendMessage("��c����� �� ��ɾ ����� ������ �����ϴ�.");
				return true;
			}
			if (args.length != 3) {
				sender.sendMessage("��6/ĳ�� �ֱ� <�г���> <ĳ��> ��f- ĳ�ø� �����մϴ�.");
				return true;
			}
			long i = getLong(args[2]).longValue();
			if (i < 1L) {
				sender.sendMessage("��c<ĳ��> ���� ���ڸ� �����ֽñ� �ٶ��ϴ�.");
				return true;
			}
			addCash(args[1], i);
			Player p = Bukkit.getPlayerExact(args[1]);
			if (p != null) {
				p.sendMessage("��c" + sender.getName() + " ��6���� ��ſ��� ��c" + toString(Long.valueOf(i)) + " ��6��ŭ�� ĳ�ø� �����߽��ϴ�.");
			}
			
			sender.sendMessage("��c" + args[1] + " ��6�Կ��� ��c" + toString(Long.valueOf(i)) + " ��6��ŭ�� ĳ�ø� �����߽��ϴ�.");
			return true;
		}
		if (args[0].equals("����") || args[0].equals("t")) {
			if (!sender.isOp()) {
				sender.sendMessage("��c����� �� ��ɾ ����� ������ �����ϴ�.");
				return true;
			}
			if (args.length != 3) {
				sender.sendMessage("��6/ĳ�� ���� <�г���> <ĳ��> ��f- ĳ�ø� �����ϴ�.");
				return true;
			}
			long i = getLong(args[2]).longValue();
			if (i < 1L) {
				sender.sendMessage("��c<ĳ��> ���� ���ڸ� �����ֽñ� �ٶ��ϴ�.");
				return true;
			}
			discountCash(args[1], i);
			Player p = Bukkit.getPlayerExact(args[1]);
			if (p != null) {
				p.sendMessage("��c" + sender.getName() + " ��6���� �� ĳ�ø� ��c" + toString(Long.valueOf(i)) + " ��6��ŭ �����Ͽ����ϴ�.");
			}
			sender.sendMessage("��c" + args[1] + " ��6�Կ��Լ� ��c" + toString(Long.valueOf(i)) + " ��6��ŭ�� ĳ�ø� �����Ͽ����ϴ�.");
			return true;
		}
		if (args[0].equals("����") || args[0].equals("s")) {
			if (!sender.isOp()) {
				sender.sendMessage("��c����� �� ��ɾ ����� ������ �����ϴ�.");
				return true;
			}
			if (args.length != 3) {
				sender.sendMessage("��6/ĳ�� ���� <�г���> <ĳ��> ��f- ĳ�ø� �����մϴ�.");
				return true;
			}
			long i = getLong(args[2]).longValue();
			if (i < 0L) {
				sender.sendMessage("��c<ĳ��> ���� ���ڸ� �����ֽñ� �ٶ��ϴ�.");
				return true;
			}
			setCash(args[1], i);
			Player p = Bukkit.getPlayerExact(args[1]);
			if (p != null) {
				p.sendMessage("��c" + sender.getName() + " ��6���� �� ĳ�ø� ��c" + toString(Long.valueOf(i)) + " ��6��ŭ �����߽��ϴ�.");
			}
			sender.sendMessage("��c" + args[1] + " ��6���� ĳ�ø� ��c" + toString(Long.valueOf(i)) + " ��6��ŭ���� �����߽��ϴ�.");
			return true;
		}
		if (args[0].equals("����") || args[0].equals("sh")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("��c�÷��̾ ����� ������ ��ɾ��Դϴ�.");
				return true;
			}
			if (!sender.isOp()) {
				sender.sendMessage("��c����� �� ��ɾ ����� ������ �����ϴ�.");
				return true;
			}
			Player p = (Player) sender;
			if (args.length == 1) {
				p.sendMessage("��e ---- ��6ĳ�� ���� ��e-- ��6������ ��c1��6/��c1 ��e----");
				p.sendMessage("��6/ĳ�� ����, /ca sh <�̸�> ��f- ĳ�� ������ ���ϴ�.");
				p.sendMessage("��6/ĳ�� ���� ����, /ca sh c <�̸�> <1~6> ��f- ĳ�� ������ �����մϴ�.");
				p.sendMessage("��6/ĳ�� ���� ����, /ca sh a <�̸�> <����> <��ġ> ��f- ĳ�û����� �����մϴ�.");
				p.sendMessage("��6/ĳ�� ���� ����, /ca sh s <�̸�> <��ġ> ��f- ĳ�û����� �����մϴ�.");
				p.sendMessage("��6/ĳ�� ���� ����, /ca sh r <�̸�> ��f- ĳ�û����� �����մϴ�.");
				p.sendMessage("��6/ĳ�� ���� ���, /ca sh l ��f- ĳ�û��� ����� ���ϴ�.");
			} else if (args.length == 2) {
				if (args[1].equals("���") || args[1].equals("l")) {
					Set<String> l = this.config.getKeys(true);
					ArrayList<String> shopnames = new ArrayList<String>();
					for (int i = 0; i < l.size(); i++) {
						String n = l.toArray()[i].toString();
						if ((n.startsWith("shop_")) && (n.endsWith("_line"))) {
							shopnames.add(n.replace("shop_", "").replace("_line", ""));
						}
					}
					if (shopnames.isEmpty()) {
						p.sendMessage("��cĳ�� ���� ����� ������ϴ�.");
						return true;
					}
					p.sendMessage("��e ---- ��6ĳ�� ���� ��e-- ��c��� ��e----");
					for (int i = 0; i < shopnames.size(); i++) {
						p.sendMessage(((String) shopnames.get(i)).replace("��", "��"));
					}
					return true;
				}
				String name = args[1];
				if (!this.config.isInt("shop_" + name + "_line")) {
					p.sendMessage("��c������ �ùٸ��� �ʽ��ϴ�.");
					return true;
				}
				Inventory inv = Bukkit.createInventory(null, this.config.getInt("shop_" + name + "_line") * 9, "��c��0" + name.replace("��", "��"));
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
					sender.sendMessage("��c����� �� ��ɾ ����� ������ �����ϴ�.");
					return true;
				}
				if (args[1].equals("����") || args[1].equals("c")) {
					if (args.length != 4) {
						p.sendMessage("��6/ĳ�� ���� ���� <�̸�> <1~6> ��f- ĳ�� ������ �����մϴ�.");
						return true;
					}
					String name = args[2];
					if (this.config.isInt("shop_" + name + "_line")) {
						p.sendMessage("��c�ش� �̸��� ������ �̹� �ֽ��ϴ�.");
						return true;
					}
					Long line = getLong(args[3]);
					if ((line.longValue() < 1L) || (line.longValue() > 6L)) {
						sender.sendMessage("��c������ ������ �ùٸ��� �ʽ��ϴ�.");
						return true;
					}
					int lines = Integer.parseInt(Long.toString(line.longValue()));
					this.config.set("shop_" + name + "_line", Integer.valueOf(lines));
					sender.sendMessage("��6������ ���������� �����Ǿ����ϴ�.");
				} else if (args[1].equals("����") || args[1].equals("a")) {
					if (args.length != 5) {
						p.sendMessage("��6/ĳ�� ���� ���� <�̸�> <����> <��ġ> ��f- ĳ�û����� �����մϴ�.");
						return true;
					}
					String name = args[2];
					if (!this.config.isInt("shop_" + name + "_line")) {
						p.sendMessage("��c������ �ùٸ��� �ʽ��ϴ�.");
						return true;
					}
					long price = getLong(args[3]).longValue();
					if (price < 0L) {
						sender.sendMessage("��c������ �ùٸ��� �ʽ��ϴ�.");
						return true;
					}
					long loc = getLong(args[4]).longValue();
					if ((loc < 1L) || (loc > 54L)) {
						sender.sendMessage("��c��ġ�� �ùٸ��� �ʽ��ϴ�.");
						return true;
					}
					ItemStack item = p.getItemInHand();
					if ((item == null) || (item.getType() == Material.AIR)) {
						sender.sendMessage("��c�տ� �������� ������� �ʽ��ϴ�.");
						return true;
					}
					this.config.set("shop_" + name + "_" + toString(Long.valueOf(loc - 1L)), item.clone());
					this.config.set("shop_" + name + "_price_" + toString(Long.valueOf(loc - 1L)), Long.valueOf(price));
					if (this.config.contains("shop_" + name + "_" + toString(Long.valueOf(loc - 1L)))) {
						sender.sendMessage("��6" + toString(Long.valueOf(loc)) + "ĭ�� ���������� ��c��ϡ�6�Ǿ����ϴ�.");
						sender.sendMessage("("
								+ toString(Long.valueOf(this.config.getLong(new StringBuilder("shop_").append(name)
										.append("_price_").append(toString(Long.valueOf(loc - 1L))).toString())))
								+ " ĳ��)");
					} else {
						sender.sendMessage("��c[ERROR] ��ϵ��� �ʾҽ��ϴ�.");
					}
				} else if (args[1].equals("����") || args[1].equals("s")) {
					if (args.length != 4) {
						p.sendMessage("��6/ĳ�� ���� ���� <�̸�> <��ġ> ��f- ĳ�û����� �����մϴ�.");
						return true;
					}
					String name = args[2];
					if (!this.config.isInt("shop_" + name + "_line")) {
						p.sendMessage("��c������ �ùٸ��� �ʽ��ϴ�.");
						return true;
					}
					long loc = getLong(args[3]).longValue();
					if ((loc < 1L) || (loc > 54L)) {
						sender.sendMessage("��c��ġ�� �ùٸ��� �ʽ��ϴ�.");
						return true;
					}
					this.config.set("shop_" + name + "_" + toString(Long.valueOf(loc - 1L)), null);
					this.config.set("shop_" + name + "_price_" + toString(Long.valueOf(loc - 1L)), null);
					sender.sendMessage("��6���������� �����Ǿ����ϴ�.");
				} else if (args[1].equals("����") || args[1].equals("r")) {
					if (args.length != 3) {
						p.sendMessage("��6/ĳ�� ���� ���� <�̸�> ��f- ĳ�û����� �����մϴ�.");
						return true;
					}
					String name = args[2];
					if (!this.config.isInt("shop_" + name + "_line")) {
						p.sendMessage("��c������ �ùٸ��� �ʽ��ϴ�.");
						return true;
					}
					this.config.set("shop_" + name + "_line", null);
					for (int i = 1; i < 55; i++) {
						this.config.set("shop_" + name + "_" + toString(Integer.valueOf(i - 1)), null);
						this.config.set("shop_" + name + "_price_" + toString(Integer.valueOf(i - 1)), null);
					}
					sender.sendMessage("��6���������� ���ŵǾ����ϴ�.");
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