// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.item;

import org.bukkit.command.CommandSender;
import think.rpgitems.data.Font;
import org.apache.commons.lang.StringUtils;
import think.rpgitems.data.RPGMetadata;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Player;
import java.util.Map;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.Bukkit;
import org.bukkit.configuration.MemoryConfiguration;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import think.rpgitems.Events;
import think.rpgitems.data.Locale;
import org.bukkit.Color;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.Material;
import think.rpgitems.Plugin;
import think.rpgitems.lib.gnu.trove.map.hash.TObjectDoubleHashMap;
import think.rpgitems.power.types.PowerTick;
import think.rpgitems.power.types.PowerHit;
import think.rpgitems.power.types.PowerProjectileHit;
import think.rpgitems.power.types.PowerRightClick;
import think.rpgitems.power.types.PowerLeftClick;
import think.rpgitems.power.Power;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;

public class RPGItem {
	private ItemStack item;
	private HashMap<String, ItemMeta> localeMeta;
	private int id;
	private String name;
	private String encodedID;
	private String displayName;
	private Quality quality;
	private int damageMin;
	private int damageMax;
	private int armour;
	private String loreText;
	private String type;
	private String hand;
	public boolean ignoreWorldGuard;
	public List<String> description;
	public ArrayList<Power> powers;
	private ArrayList<PowerLeftClick> powerLeftClick;
	private ArrayList<PowerRightClick> powerRightClick;
	private ArrayList<PowerProjectileHit> powerProjectileHit;
	private ArrayList<PowerHit> powerHit;
	private ArrayList<PowerTick> powerTick;
	public boolean hasRecipe;
	public List<ItemStack> recipe;
	public TObjectDoubleHashMap<String> dropChances;
	private int tooltipWidth;
	private int maxDurability;
	private boolean hasBar;
	private boolean forceBar;

	public RPGItem(final String name, final int id) {
		this.localeMeta = new HashMap<String, ItemMeta>();
		this.quality = Quality.TRASH;
		this.damageMin = 0;
		this.damageMax = 3;
		this.armour = 0;
		this.loreText = "";
		this.type = Plugin.plugin.getConfig().getString("defaults.sword", "Sword");
		this.hand = Plugin.plugin.getConfig().getString("defaults.hand", "One handed");
		this.ignoreWorldGuard = false;
		this.description = new ArrayList<String>();
		this.powers = new ArrayList<Power>();
		this.powerLeftClick = new ArrayList<PowerLeftClick>();
		this.powerRightClick = new ArrayList<PowerRightClick>();
		this.powerProjectileHit = new ArrayList<PowerProjectileHit>();
		this.powerHit = new ArrayList<PowerHit>();
		this.powerTick = new ArrayList<PowerTick>();
		this.hasRecipe = false;
		this.recipe = null;
		this.dropChances = new TObjectDoubleHashMap<String>();
		this.tooltipWidth = 150;
		this.hasBar = false;
		this.forceBar = false;
		this.name = name;
		this.id = id;
		this.encodedID = getMCEncodedID(id);
		this.item = new ItemStack(Material.WOOD_SWORD);
		this.displayName = this.item.getType().toString();
		this.localeMeta.put("en_GB", this.item.getItemMeta());
		this.rebuild();
	}

	public RPGItem(final ConfigurationSection s) {
		this.localeMeta = new HashMap<String, ItemMeta>();
		this.quality = Quality.TRASH;
		this.damageMin = 0;
		this.damageMax = 3;
		this.armour = 0;
		this.loreText = "";
		this.type = Plugin.plugin.getConfig().getString("defaults.sword", "Sword");
		this.hand = Plugin.plugin.getConfig().getString("defaults.hand", "One handed");
		this.ignoreWorldGuard = false;
		this.description = new ArrayList<String>();
		this.powers = new ArrayList<Power>();
		this.powerLeftClick = new ArrayList<PowerLeftClick>();
		this.powerRightClick = new ArrayList<PowerRightClick>();
		this.powerProjectileHit = new ArrayList<PowerProjectileHit>();
		this.powerHit = new ArrayList<PowerHit>();
		this.powerTick = new ArrayList<PowerTick>();
		this.hasRecipe = false;
		this.recipe = null;
		this.dropChances = new TObjectDoubleHashMap<String>();
		this.tooltipWidth = 150;
		this.hasBar = false;
		this.forceBar = false;
		this.name = s.getString("name");
		this.id = s.getInt("id");
		this.setDisplay(s.getString("display"), false);
		this.setType(s.getString("type", Plugin.plugin.getConfig().getString("defaults.sword", "Sword")), false);
		this.setHand(s.getString("hand", Plugin.plugin.getConfig().getString("defaults.hand", "One handed")), false);
		this.setLore(s.getString("lore"), false);
		this.description = (List<String>) s.getList("description", (List) new ArrayList());
		for (int i = 0; i < this.description.size(); ++i) {
			this.description.set(i, ChatColor.translateAlternateColorCodes('&', (String) this.description.get(i)));
		}
		this.quality = Quality.valueOf(s.getString("quality"));
		this.damageMin = s.getInt("damageMin");
		this.damageMax = s.getInt("damageMax");
		this.armour = s.getInt("armour", 0);
		this.item = new ItemStack(Material.valueOf(s.getString("item")));
		final ItemMeta meta = this.item.getItemMeta();
		if (meta instanceof LeatherArmorMeta) {
			((LeatherArmorMeta) meta).setColor(Color.fromRGB(s.getInt("item_colour", 0)));
		} else {
			this.item.setDurability((short) s.getInt("item_data", 0));
		}
		for (final String locale : Locale.getLocales()) {
			this.localeMeta.put(locale, meta.clone());
		}
		this.ignoreWorldGuard = s.getBoolean("ignoreWorldGuard", false);
		final ConfigurationSection powerList = s.getConfigurationSection("powers");
		if (powerList != null) {
			for (final String sectionKey : powerList.getKeys(false)) {
				final ConfigurationSection section = powerList.getConfigurationSection(sectionKey);
				try {
					if (!Power.powers.containsKey(section.getString("powerName"))) {
						continue;
					}
					final Power pow = (Power) Power.powers.get(section.getString("powerName")).newInstance();
					pow.init(section);
					(pow.item = this).addPower(pow, false);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e2) {
					e2.printStackTrace();
				}
			}
		}
		this.encodedID = getMCEncodedID(this.id);
		this.hasRecipe = s.getBoolean("hasRecipe", false);
		if (this.hasRecipe) {
			this.recipe = (List<ItemStack>) s.getList("recipe");
		}
		final ConfigurationSection drops = s.getConfigurationSection("dropChances");
		if (drops != null) {
			for (final String key : drops.getKeys(false)) {
				double chance = drops.getDouble(key, 0.0);
				chance = Math.min(chance, 100.0);
				if (chance > 0.0) {
					this.dropChances.put(key, chance);
					if (!Events.drops.containsKey(key)) {
						Events.drops.put(key, new HashSet<Integer>());
					}
					final Set<Integer> set = Events.drops.get(key);
					set.add(this.getID());
				} else {
					this.dropChances.remove(key);
					if (Events.drops.containsKey(key)) {
						final Set<Integer> set = Events.drops.get(key);
						set.remove(this.getID());
					}
				}
				this.dropChances.put(key, chance);
			}
		}
		if (this.item.getType().getMaxDurability() != 0) {
			this.hasBar = true;
		}
		this.maxDurability = s.getInt("maxDurability", (int) this.item.getType().getMaxDurability());
		this.forceBar = s.getBoolean("forceBar", false);
		if (this.maxDurability == 0) {
			this.maxDurability = -1;
		}
		this.rebuild();
	}

	public void save(final ConfigurationSection s) {
		s.set("name", (Object) this.name);
		s.set("id", (Object) this.id);
		s.set("display", (Object) this.displayName.replaceAll("」", "&"));
		s.set("quality", (Object) this.quality.toString());
		s.set("damageMin", (Object) this.damageMin);
		s.set("damageMax", (Object) this.damageMax);
		s.set("armour", (Object) this.armour);
		s.set("type", (Object) this.type.replaceAll("」", "&"));
		s.set("hand", (Object) this.hand.replaceAll("」", "&"));
		s.set("lore", (Object) this.loreText.replaceAll("」", "&"));
		final ArrayList<String> descriptionConv = new ArrayList<String>(this.description);
		for (int i = 0; i < descriptionConv.size(); ++i) {
			descriptionConv.set(i, descriptionConv.get(i).replaceAll("」", "&"));
		}
		s.set("description", (Object) descriptionConv);
		s.set("item", (Object) this.item.getType().toString());
		s.set("ignoreWorldGuard", (Object) this.ignoreWorldGuard);
		final ItemMeta meta = this.localeMeta.get("en_GB");
		if (meta instanceof LeatherArmorMeta) {
			s.set("item_colour", (Object) ((LeatherArmorMeta) meta).getColor().asRGB());
		} else {
			s.set("item_data", (Object) this.item.getDurability());
		}
		final ConfigurationSection powerConfigs = s.createSection("powers");
		int j = 0;
		for (final Power p : this.powers) {
			final MemoryConfiguration pConfig = new MemoryConfiguration();
			pConfig.set("powerName", (Object) p.getName());
			p.save((ConfigurationSection) pConfig);
			powerConfigs.set(Integer.toString(j), (Object) pConfig);
			++j;
		}
		s.set("hasRecipe", (Object) this.hasRecipe);
		if (this.hasRecipe) {
			s.set("recipe", (Object) this.recipe);
		}
		final ConfigurationSection drops = s.createSection("dropChances");
		for (final String key : this.dropChances.keySet()) {
			drops.set(key, (Object) this.dropChances.get(key));
		}
		s.set("maxDurability", (Object) this.maxDurability);
		s.set("forceBar", (Object) this.forceBar);
	}

	public void resetRecipe(final boolean removeOld) {
		if (removeOld) {
			final Iterator<Recipe> it = (Iterator<Recipe>) Bukkit.recipeIterator();
			while (it.hasNext()) {
				final Recipe recipe = it.next();
				final RPGItem rpgitem = ItemManager.toRPGItem(recipe.getResult());
				if (rpgitem == null) {
					continue;
				}
				if (rpgitem.getID() != this.getID()) {
					continue;
				}
				it.remove();
			}
		}
		if (this.hasRecipe) {
			final Set<ItemStack> iSet = new HashSet<ItemStack>();
			for (final ItemStack m : this.recipe) {
				iSet.add(m);
			}
			final ItemStack[] iList = iSet.toArray(new ItemStack[iSet.size()]);
			this.item.setItemMeta(this.getLocaleMeta("en_GB"));
			final ShapedRecipe shapedRecipe = new ShapedRecipe(this.item);
			int i = 0;
			final Map<ItemStack, Character> iMap = new HashMap<ItemStack, Character>();
			ItemStack[] array;
			for (int length = (array = iList).length, l = 0; l < length; ++l) {
				final ItemStack j = array[l];
				iMap.put(j, (char) (65 + i));
				++i;
			}
			iMap.put(null, ' ');
			final StringBuilder out = new StringBuilder();
			for (final ItemStack k : this.recipe) {
				out.append(iMap.get(k));
			}
			final String shape = out.toString();
			shapedRecipe.shape(new String[] { shape.substring(0, 3), shape.substring(3, 6), shape.substring(6, 9) });
			for (final Map.Entry<ItemStack, Character> e : iMap.entrySet()) {
				if (e.getKey() != null) {
					shapedRecipe.setIngredient((char) e.getValue(), e.getKey().getType(),
							(int) e.getKey().getDurability());
				}
			}
			Bukkit.addRecipe((Recipe) shapedRecipe);
		}
	}

	public void leftClick(final Player player) {
		for (final PowerLeftClick power : this.powerLeftClick) {
			power.leftClick(player);
		}
	}

	public void rightClick(final Player player) {
		for (final PowerRightClick power : this.powerRightClick) {
			power.rightClick(player);
		}
	}

	public void projectileHit(final Player player, final Projectile arrow) {
		for (final PowerProjectileHit power : this.powerProjectileHit) {
			power.projectileHit(player, arrow);
		}
	}

	public void hit(final Player player, final LivingEntity e) {
		for (final PowerHit power : this.powerHit) {
			power.hit(player, e);
		}
	}

	public void tick(final Player player) {
		for (final PowerTick power : this.powerTick) {
			power.tick(player);
		}
	}

	public void rebuild() {
		for (final String locale : Locale.getLocales()) {
			if (!this.localeMeta.containsKey(locale)) {
				this.localeMeta.put(locale, this.getLocaleMeta("en_GB"));
			}
		}
		for (final String locale : Locale.getLocales()) {
			final List<String> lines = this.getTooltipLines(locale);
			final ItemMeta meta = this.getLocaleMeta(locale);
			meta.setDisplayName((String) lines.get(0));
			lines.remove(0);
			meta.setLore((List<String>) lines);
			this.setLocaleMeta(locale, meta);
		}
		Player[] onlinePlayers;
		for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, i = 0; i < length; ++i) {
			final Player player = onlinePlayers[i];
			final Iterator<ItemStack> it = (Iterator<ItemStack>) player.getInventory().iterator();
			final String locale2 = Locale.getPlayerLocale(player);
			while (it.hasNext()) {
				final ItemStack item = it.next();
				if (ItemManager.toRPGItem(item) != null) {
					updateItem(item, locale2, false);
				}
			}
			ItemStack[] armorContents;
			for (int length2 = (armorContents = player.getInventory()
					.getArmorContents()).length, j = 0; j < length2; ++j) {
				final ItemStack item = armorContents[j];
				if (ItemManager.toRPGItem(item) != null) {
					updateItem(item, locale2, false);
				}
			}
		}
		this.resetRecipe(true);
	}

	public static RPGMetadata getMetadata(final ItemStack item) {
		if (!item.hasItemMeta() || !item.getItemMeta().hasLore() || item.getItemMeta().getLore().size() == 0) {
			return new RPGMetadata();
		}
		return RPGMetadata.parseLoreline(item.getItemMeta().getLore().get(0));
	}

	public static void updateItem(final ItemStack item, final String locale) {
		updateItem(item, locale, getMetadata(item));
	}

	public static void updateItem(final ItemStack item, final String locale, final RPGMetadata rpgMeta) {
		updateItem(item, locale, rpgMeta, false);
	}

	public static void updateItem(final ItemStack item, final String locale, final boolean updateDurability) {
		updateItem(item, locale, getMetadata(item), false);
	}

	public static void updateItem(final ItemStack item, final String locale, final RPGMetadata rpgMeta,
			final boolean updateDurability) {
		final RPGItem rItem = ItemManager.toRPGItem(item);
		if (rItem == null) {
			return;
		}
		item.setType(rItem.item.getType());
		final ItemMeta meta = rItem.getLocaleMeta(locale);
		if (!(meta instanceof LeatherArmorMeta) && updateDurability) {
			item.setDurability(rItem.item.getDurability());
		}
		final List<String> lore = (List<String>) meta.getLore();
		rItem.addExtra(rpgMeta, item, lore);
		lore.set(0, String.valueOf(meta.getLore().get(0)) + rpgMeta.toMCString());
		meta.setLore((List<String>) lore);
		item.setItemMeta(meta);
	}

	public void addExtra(RPGMetadata rpgMeta, ItemStack item, List<String> lore) {
		if (this.maxDurability > 0) {
			if (!rpgMeta.containsKey(0)) {
				rpgMeta.put(0, Integer.valueOf(this.maxDurability));
			}
			int durability = ((Number) rpgMeta.get(0)).intValue();

			if ((!this.hasBar) || (this.forceBar)) {
				StringBuilder out = new StringBuilder();
				char boxChar = '＝';
				int boxCount = this.tooltipWidth / 4;
				int mid = (int) (boxCount * (durability / this.maxDurability));
				for (int i = 0; i < boxCount; i++) {
					out.append(i == mid ? ChatColor.YELLOW : i < mid ? ChatColor.GREEN : ChatColor.RED);
					out.append(boxChar);
				}
				lore.add(out.toString());
			}
			if (this.hasBar)
				item.setDurability((short) (item.getType().getMaxDurability()
						- (short) (int) (item.getType().getMaxDurability() * (durability / this.maxDurability))));
		} else if (this.maxDurability <= 0) {
			item.setDurability(this.hasBar ? 0 : this.item.getDurability());
		}
	}

	public List<String> getTooltipLines(final String locale) {
		final ArrayList<String> output = new ArrayList<String>();
		int width = 150;
		output.add(String.valueOf(this.encodedID) + this.quality.colour + ChatColor.BOLD + this.displayName);
		int dWidth = getStringWidthBold(ChatColor.stripColor(this.displayName));
		if (dWidth > width) {
			width = dWidth;
		}
		dWidth = getStringWidth(ChatColor.stripColor(String.valueOf(this.hand) + "     " + this.type));
		if (dWidth > width) {
			width = dWidth;
		}
		String damageStr = null;
		if (this.damageMin == 0 && this.damageMax == 0 && this.armour != 0) {
			damageStr = String.valueOf(this.armour) + "% "
					+ Plugin.plugin.getConfig().getString("defaults.armour", "Armour");
		} else if (this.armour == 0 && this.damageMin == 0 && this.damageMax == 0) {
			damageStr = null;
		} else if (this.damageMin == this.damageMax) {
			damageStr = String.valueOf(this.damageMin) + " "
					+ Plugin.plugin.getConfig().getString("defaults.damage", "Damage");
		} else {
			damageStr = String.valueOf(this.damageMin) + "-" + this.damageMax + " "
					+ Plugin.plugin.getConfig().getString("defaults.damage", "Damage");
		}
		if (this.damageMin != 0 || this.damageMax != 0 || this.armour != 0) {
			dWidth = getStringWidth(damageStr);
			if (dWidth > width) {
				width = dWidth;
			}
		}
		for (final Power p : this.powers) {
			dWidth = getStringWidth(ChatColor.stripColor(p.displayText(locale)));
			if (dWidth > width) {
				width = dWidth;
			}
		}
		for (final String s : this.description) {
			dWidth = getStringWidth(ChatColor.stripColor(s));
			if (dWidth > width) {
				width = dWidth;
			}
		}
		this.tooltipWidth = width;
		output.add(
				ChatColor.WHITE + this.hand
						+ StringUtils
								.repeat(" ",
										(width - getStringWidth(
												ChatColor.stripColor(String.valueOf(this.hand) + this.type))) / 4)
						+ this.type);
		if (damageStr != null) {
			output.add(ChatColor.WHITE + damageStr);
		}
		for (final Power p : this.powers) {
			output.add(p.displayText(locale));
		}
		if (this.loreText.length() != 0) {
			int cWidth = 0;
			int tWidth = 0;
			StringBuilder out = new StringBuilder();
			StringBuilder temp = new StringBuilder();
			out.append(ChatColor.YELLOW);
			out.append(ChatColor.ITALIC);
			String currentColour = ChatColor.YELLOW.toString();
			final String dMsg = "\"" + this.loreText + "\"";
			for (int i = 0; i < dMsg.length(); ++i) {
				final char c = dMsg.charAt(i);
				temp.append(c);
				if (c == '」' || c == '&') {
					++i;
					temp.append(dMsg.charAt(i));
					currentColour = "」" + dMsg.charAt(i);
				} else {
					if (c == ' ') {
						tWidth += 4;
					} else {
						tWidth += Font.widths[c] + 1;
					}
					if (c == ' ' || i == dMsg.length() - 1) {
						if (cWidth + tWidth > width) {
							cWidth = 0;
							cWidth += tWidth;
							tWidth = 0;
							output.add(out.toString());
							out = new StringBuilder();
							out.append(currentColour);
							out.append(ChatColor.ITALIC);
							out.append((CharSequence) temp);
							temp = new StringBuilder();
						} else {
							out.append((CharSequence) temp);
							temp = new StringBuilder();
							cWidth += tWidth;
							tWidth = 0;
						}
					}
				}
			}
			out.append((CharSequence) temp);
			output.add(out.toString());
		}
		for (final String s : this.description) {
			output.add(s);
		}
		return output;
	}

	public ItemStack toItemStack(final String locale) {
		final ItemStack rStack = this.item.clone();
		final RPGMetadata rpgMeta = new RPGMetadata();
		final ItemMeta meta = this.getLocaleMeta(locale);
		final List<String> lore = (List<String>) meta.getLore();
		lore.set(0, String.valueOf(meta.getLore().get(0)) + rpgMeta.toMCString());
		this.addExtra(rpgMeta, rStack, lore);
		meta.setLore((List<String>) lore);
		rStack.setItemMeta(meta);
		return rStack;
	}

	public ItemMeta getLocaleMeta(final String locale) {
		ItemMeta meta = this.localeMeta.get(locale);
		if (meta == null) {
			meta = this.localeMeta.get("en_GB");
		}
		return meta.clone();
	}

	public void setLocaleMeta(final String locale, final ItemMeta meta) {
		this.localeMeta.put(locale, meta);
	}

	public String getName() {
		return this.name;
	}

	public int getID() {
		return this.id;
	}

	public String getMCEncodedID() {
		return this.encodedID;
	}

	public static String getMCEncodedID(final int id) {
		final String hex = String.format("%08x", id);
		final StringBuilder out = new StringBuilder();
		char[] charArray;
		for (int length = (charArray = hex.toCharArray()).length, i = 0; i < length; ++i) {
			final char h = charArray[i];
			out.append('」');
			out.append(h);
		}
		return out.toString();
	}

	private static int getStringWidth(final String str) {
		int width = 0;
		for (int i = 0; i < str.length(); ++i) {
			final char c = str.charAt(i);
			width += Font.widths[c] + 1;
		}
		return width;
	}

	private static int getStringWidthBold(final String str) {
		int width = 0;
		for (int i = 0; i < str.length(); ++i) {
			final char c = str.charAt(i);
			width += Font.widths[c] + 2;
		}
		return width;
	}

	public void print(final CommandSender sender) {
		final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player) sender) : "en_GB";
		final List<String> lines = this.getTooltipLines(locale);
		for (final String s : lines) {
			sender.sendMessage(s);
		}
		sender.sendMessage(String.format(Locale.get("message.print.durability", locale), this.maxDurability));
	}

	public void setDisplay(final String str) {
		this.setDisplay(str, true);
	}

	public void setDisplay(final String str, final boolean update) {
		this.displayName = ChatColor.translateAlternateColorCodes('&', str);
		if (update) {
			this.rebuild();
		}
	}

	public String getDisplay() {
		return String.valueOf(this.quality.colour) + ChatColor.BOLD + this.displayName;
	}

	public void setType(final String str) {
		this.setType(str, true);
	}

	public void setType(final String str, final boolean update) {
		this.type = ChatColor.translateAlternateColorCodes('&', str);
		if (update) {
			this.rebuild();
		}
	}

	public String getType() {
		return this.type;
	}

	public void setHand(final String h) {
		this.setHand(h, true);
	}

	public void setHand(final String h, final boolean update) {
		this.hand = ChatColor.translateAlternateColorCodes('&', h);
		if (update) {
			this.rebuild();
		}
	}

	public String getHand() {
		return this.hand;
	}

	public void setDamage(final int min, final int max) {
		this.setDamage(min, max, true);
	}

	public void setDamage(final int min, final int max, final boolean update) {
		this.damageMin = min;
		this.damageMax = max;
		this.armour = 0;
		if (update) {
			this.rebuild();
		}
	}

	public int getDamageMin() {
		return this.damageMin;
	}

	public int getDamageMax() {
		return this.damageMax;
	}

	public void setArmour(final int a) {
		this.setArmour(a, true);
	}

	public void setArmour(final int a, final boolean update) {
		this.armour = a;
		final boolean b = false;
		this.damageMax = (b ? 1 : 0);
		this.damageMin = (b ? 1 : 0);
		if (update) {
			this.rebuild();
		}
	}

	public int getArmour() {
		return this.armour;
	}

	public void setLore(final String str) {
		this.setLore(str, true);
	}

	public void setLore(final String str, final boolean update) {
		this.loreText = ChatColor.translateAlternateColorCodes('&', str);
		if (update) {
			this.rebuild();
		}
	}

	public String getLore() {
		return this.loreText;
	}

	public void setQuality(final Quality q) {
		this.setQuality(q, true);
	}

	public void setQuality(final Quality q, final boolean update) {
		this.quality = q;
		if (update) {
			this.rebuild();
		}
	}

	public Quality getQuality() {
		return this.quality;
	}

	public void setItem(final Material mat) {
		this.setItem(mat, true);
	}

	public void setItem(final Material mat, final boolean update) {
		if (this.maxDurability == this.item.getType().getMaxDurability()) {
			this.maxDurability = mat.getMaxDurability();
		}
		this.item.setType(mat);
		if (update) {
			this.rebuild();
		}
	}

	public void setDataValue(final short value, final boolean update) {
		this.item.setDurability(value);
		if (update) {
			this.rebuild();
		}
	}

	public void setDataValue(final short value) {
		this.item.setDurability(value);
	}

	public short getDataValue() {
		return this.item.getDurability();
	}

	public Material getItem() {
		return this.item.getType();
	}

	public void setMaxDurability(final int newVal) {
		this.setMaxDurability(newVal, true);
	}

	public void setMaxDurability(final int newVal, final boolean update) {
		this.maxDurability = newVal;
		if (update) {
			this.rebuild();
		}
	}

	public int getMaxDurability() {
		return (this.maxDurability <= 0) ? -1 : this.maxDurability;
	}

	public void give(final Player player) {
		player.getInventory().addItem(new ItemStack[] { this.toItemStack(Locale.getPlayerLocale(player)) });
	}

	public void addPower(final Power power) {
		this.addPower(power, true);
	}

	public void addPower(final Power power, final boolean update) {
		this.powers.add(power);
		Power.powerUsage.put(power.getName(), Power.powerUsage.get(power.getName()) + 1);
		if (power instanceof PowerHit) {
			this.powerHit.add((PowerHit) power);
		}
		if (power instanceof PowerLeftClick) {
			this.powerLeftClick.add((PowerLeftClick) power);
		}
		if (power instanceof PowerRightClick) {
			this.powerRightClick.add((PowerRightClick) power);
		}
		if (power instanceof PowerProjectileHit) {
			this.powerProjectileHit.add((PowerProjectileHit) power);
		}
		if (power instanceof PowerTick) {
			this.powerTick.add((PowerTick) power);
		}
		if (update) {
			this.rebuild();
		}
	}

	public boolean removePower(final String pow) {
		final Iterator<Power> it = this.powers.iterator();
		Power power = null;
		while (it.hasNext()) {
			final Power p = it.next();
			if (p.getName().equalsIgnoreCase(pow)) {
				it.remove();
				power = p;
				this.rebuild();
				break;
			}
		}
		if (power != null) {
			if (power instanceof PowerHit) {
				this.powerHit.remove(power);
			}
			if (power instanceof PowerLeftClick) {
				this.powerLeftClick.remove(power);
			}
			if (power instanceof PowerRightClick) {
				this.powerRightClick.remove(power);
			}
			if (power instanceof PowerProjectileHit) {
				this.powerProjectileHit.remove(power);
			}
			if (power instanceof PowerTick) {
				this.powerTick.remove(power);
			}
		}
		return power != null;
	}

	public void addDescription(final String str) {
		this.addDescription(str, true);
	}

	public void addDescription(final String str, final boolean update) {
		this.description.add(ChatColor.translateAlternateColorCodes('&', str));
		if (update) {
			this.rebuild();
		}
	}

	public void toggleBar() {
		this.forceBar = !this.forceBar;
		this.rebuild();
	}
}
