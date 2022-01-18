// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import org.bukkit.configuration.ConfigurationSection;
import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import think.rpgitems.Plugin;
import org.bukkit.block.Block;
import java.util.Iterator;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import java.util.ArrayList;
import org.bukkit.Sound;
import think.rpgitems.data.RPGValue;
import org.bukkit.entity.Player;
import java.util.Random;
import think.rpgitems.power.types.PowerRightClick;

public class PowerRainbow extends Power implements PowerRightClick
{
    public long cooldownTime;
    public int count;
    private Random random;
    
    public PowerRainbow() {
        this.cooldownTime = 20L;
        this.count = 5;
        this.random = new Random();
    }
    
    @Override
    public void rightClick(final Player player) {
        RPGValue value = RPGValue.get(player, this.item, "arrow.rainbow");
        long cooldown;
        if (value == null) {
            cooldown = System.currentTimeMillis() / 50L;
            value = new RPGValue(player, this.item, "arrow.rainbow", cooldown);
        }
        else {
            cooldown = value.asLong();
        }
        if (cooldown <= System.currentTimeMillis() / 50L) {
            value.set(System.currentTimeMillis() / 50L + this.cooldownTime);
            player.playSound(player.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
            final ArrayList<FallingBlock> blocks = new ArrayList<FallingBlock>();
            for (int i = 0; i < this.count; ++i) {
                final FallingBlock block = player.getWorld().spawnFallingBlock(player.getLocation().add(0.0, 1.8, 0.0), Material.WOOL, (byte)this.random.nextInt(16));
                block.setVelocity(player.getLocation().getDirection().multiply(new Vector(this.random.nextDouble() * 2.0 + 0.5, this.random.nextDouble() * 2.0 + 0.5, this.random.nextDouble() * 2.0 + 0.5)));
                block.setDropItem(false);
                blocks.add(block);
            }
            new BukkitRunnable() {
                ArrayList<Location> fallLocs = new ArrayList<Location>();
                Random random = new Random();
                
                public void run() {
                    final Iterator<Location> l = this.fallLocs.iterator();
                    while (l.hasNext()) {
                        final Location loc = l.next();
                        if (this.random.nextBoolean()) {
                            final Block b = loc.getBlock();
                            if (b.getType() == Material.WOOL) {
                                loc.getWorld().playEffect(loc, Effect.STEP_SOUND, Material.WOOL.getId(), (int)b.getData());
                                b.setType(Material.AIR);
                            }
                            l.remove();
                        }
                        if (this.random.nextInt(5) == 0) {
                            break;
                        }
                    }
                    final Iterator<FallingBlock> it = blocks.iterator();
                    while (it.hasNext()) {
                        final FallingBlock block = it.next();
                        if (block.isDead()) {
                            this.fallLocs.add(block.getLocation());
                            it.remove();
                        }
                    }
                    if (this.fallLocs.isEmpty() && blocks.isEmpty()) {
                        this.cancel();
                    }
                }
            }.runTaskTimer((org.bukkit.plugin.Plugin)Plugin.plugin, 0L, 5L);
        }
        else {
            player.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.cooldown", Locale.getPlayerLocale(player)), (cooldown - System.currentTimeMillis() / 50L) / 20.0));
        }
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + String.format(Locale.get("power.rainbow", locale), this.count, this.cooldownTime / 20.0);
    }
    
    @Override
    public String getName() {
        return "rainbow";
    }
    
    @Override
    public void init(final ConfigurationSection s) {
        this.cooldownTime = s.getLong("cooldown", 20L);
        this.count = s.getInt("count", 5);
    }
    
    @Override
    public void save(final ConfigurationSection s) {
        s.set("cooldown", (Object)this.cooldownTime);
        s.set("count", (Object)this.count);
    }
}
