// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import org.bukkit.configuration.ConfigurationSection;
import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import java.util.Iterator;
import java.util.List;
import org.bukkit.World;
import think.rpgitems.Plugin;
import org.bukkit.block.Block;
import org.bukkit.Effect;
import java.util.Random;
import org.bukkit.Location;
import think.rpgitems.lib.gnu.trove.map.hash.TObjectLongHashMap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Material;
import org.bukkit.Sound;
import think.rpgitems.data.RPGValue;
import org.bukkit.entity.Player;
import think.rpgitems.power.types.PowerRightClick;

public class PowerIce extends Power implements PowerRightClick
{
    public long cooldownTime;
    
    public PowerIce() {
        this.cooldownTime = 20L;
    }
    
    @Override
    public void rightClick(final Player player) {
        RPGValue value = RPGValue.get(player, this.item, "ice.cooldown");
        long cooldown;
        if (value == null) {
            cooldown = System.currentTimeMillis() / 50L;
            value = new RPGValue(player, this.item, "ice.cooldown", cooldown);
        }
        else {
            cooldown = value.asLong();
        }
        if (cooldown <= System.currentTimeMillis() / 50L) {
            value.set(System.currentTimeMillis() / 50L + this.cooldownTime);
            player.playSound(player.getLocation(), Sound.FIZZ, 1.0f, 0.1f);
            final FallingBlock block = player.getWorld().spawnFallingBlock(player.getLocation().add(0.0, 1.8, 0.0), Material.ICE, (byte)0);
            block.setVelocity(player.getLocation().getDirection().multiply(2.0));
            block.setDropItem(false);
            final BukkitRunnable run = new BukkitRunnable() {
                public void run() {
                    boolean hit = false;
                    final World world = block.getWorld();
                    final Location bLoc = block.getLocation();
                Label_0193:
                    for (int x = -1; x < 2; ++x) {
                        for (int y = -1; y < 2; ++y) {
                            for (int z = -1; z < 2; ++z) {
                                final Location loc = block.getLocation().add((double)x, (double)y, (double)z);
                                if (world.getBlockTypeIdAt(loc) != Material.AIR.getId()) {
                                    final Block b = world.getBlockAt(loc);
                                    if (b.getType().isSolid() && PowerIce.this.checkBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 1.0, 1.0, 1.0, bLoc.getX() - 0.5, bLoc.getY() - 0.5, bLoc.getZ() - 0.5, 1.0, 1.0, 1.0)) {
                                        hit = true;
                                        break Label_0193;
                                    }
                                }
                            }
                        }
                    }
                    if (!hit) {
                        final List<Entity> entities = (List<Entity>)block.getNearbyEntities(1.0, 1.0, 1.0);
                        for (final Entity e : entities) {
                            if (e != player) {
                                hit = true;
                                break;
                            }
                        }
                    }
                    if (block.isDead() || hit) {
                        block.remove();
                        block.getLocation().getBlock().setType(Material.AIR);
                        this.cancel();
                        final TObjectLongHashMap<Location> changedBlocks = new TObjectLongHashMap<Location>();
                        for (int x2 = -1; x2 < 2; ++x2) {
                            for (int y2 = -1; y2 < 3; ++y2) {
                                for (int z2 = -1; z2 < 2; ++z2) {
                                    final Location loc2 = block.getLocation().add((double)x2, (double)y2, (double)z2);
                                    final Block b2 = world.getBlockAt(loc2);
                                    if (!b2.getType().isSolid()) {
                                        changedBlocks.put(b2.getLocation(), b2.getTypeId() | b2.getData() << 16);
                                        b2.setType(Material.ICE);
                                    }
                                }
                            }
                        }
                        new BukkitRunnable() {
                            Random random = new Random();
                            
                            public void run() {
                                for (int i = 0; i < 4; ++i) {
                                    if (changedBlocks.isEmpty()) {
                                        this.cancel();
                                        return;
                                    }
                                    final int index = this.random.nextInt(changedBlocks.size());
                                    final long data = changedBlocks.values()[index];
                                    final Location position = (Location)changedBlocks.keys()[index];
                                    changedBlocks.remove(position);
                                    final Block c = position.getBlock();
                                    position.getWorld().playEffect(position, Effect.STEP_SOUND, c.getTypeId());
                                    c.setTypeId((int)(data & 0xFFFFL));
                                    c.setData((byte)(data >> 16));
                                }
                            }
                        }.runTaskTimer((org.bukkit.plugin.Plugin)Plugin.plugin, (long)(80 + new Random().nextInt(40)), 3L);
                    }
                }
            };
            run.runTaskTimer((org.bukkit.plugin.Plugin)Plugin.plugin, 0L, 1L);
        }
        else {
            player.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.cooldown", Locale.getPlayerLocale(player)), (cooldown - System.currentTimeMillis() / 50L) / 20.0));
        }
    }
    
    private boolean checkBlock(final double x1, final double y1, final double z1, final double w1, final double h1, final double d1, final double x2, final double y2, final double z2, final double w2, final double h2, final double d2) {
        return x1 + w1 >= x2 && x2 + w2 >= x1 && y1 + h1 >= y2 && y2 + h2 >= y1 && z1 + d1 >= z2 && z2 + d2 >= z1;
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + String.format(Locale.get("power.ice", locale), this.cooldownTime / 20.0);
    }
    
    @Override
    public String getName() {
        return "ice";
    }
    
    @Override
    public void init(final ConfigurationSection s) {
        this.cooldownTime = s.getLong("cooldown", 20L);
    }
    
    @Override
    public void save(final ConfigurationSection s) {
        s.set("cooldown", (Object)this.cooldownTime);
    }
}
