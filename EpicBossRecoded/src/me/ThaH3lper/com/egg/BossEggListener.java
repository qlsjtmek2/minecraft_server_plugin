// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.egg;

import java.util.Iterator;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import java.util.List;
import org.bukkit.plugin.Plugin;
import me.ThaH3lper.com.Boss.Boss;
import org.bukkit.entity.Item;
import me.ThaH3lper.com.LoadBosses.LoadBoss;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import me.ThaH3lper.com.EpicBoss;
import org.bukkit.event.Listener;

public class BossEggListener implements Listener
{
    private EpicBoss eb;
    
    public BossEggListener(final EpicBoss boss) {
        this.eb = boss;
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void UseEgg(final PlayerInteractEvent e) {
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getItem() != null && e.getItem().getTypeId() == 383 && e.getItem().getItemMeta().hasLore()) {
            final List<String> list = (List<String>)e.getItem().getItemMeta().getLore();
            if (list.get(0).equals(new StringBuilder().append(ChatColor.DARK_PURPLE).append(ChatColor.ITALIC).append("A very mysterious egg").toString())) {
                final LoadBoss lb = this.getloadBossforEgg(list.get(2));
                if (lb != null) {
                    ItemStack stack = e.getItem();
                    if (stack.getAmount() == 1) {
                        stack = new ItemStack(Material.AIR, 1);
                        e.getPlayer().setItemInHand(stack);
                    }
                    else {
                        stack.setAmount(stack.getAmount() - 1);
                    }
                    final Player player = e.getPlayer();
                    final Item item = e.getPlayer().getWorld().dropItem(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 1.4, player.getLocation().getZ()), new ItemStack(383, 0));
                    final Vector dir = player.getLocation().getDirection();
                    final Vector vec = new Vector(dir.getX(), dir.getY(), dir.getZ()).multiply(1);
                    item.setVelocity(vec);
                    this.eb.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.eb, (Runnable)new Runnable() {
                        @Override
                        public void run() {
                            BossEggListener.this.eb.BossList.add(new Boss(lb.getName(), lb.getHealth(), item.getLocation(), lb.getType(), lb.getDamage(), lb.getShowhp(), lb.getItems(), lb.getSkills(), lb.getShowtitle(), lb.getSkin()));
                            BossEggListener.this.eb.timer.despawn.DeSpawnEvent(BossEggListener.this.eb);
                            item.remove();
                        }
                    }, 60L);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void ShootEgg(final BlockDispenseEvent e) {
        if (e.getItem().getTypeId() == 383 && e.getItem().getItemMeta().hasLore()) {
            final List<String> list = (List<String>)e.getItem().getItemMeta().getLore();
            if (list.get(0).equals(new StringBuilder().append(ChatColor.DARK_PURPLE).append(ChatColor.ITALIC).append("A very mysterious egg").toString())) {
                final LoadBoss lb = this.getloadBossforEgg(list.get(2));
                if (lb != null) {
                    final Boss b = new Boss(lb.getName(), lb.getHealth(), e.getBlock().getLocation(), lb.getType(), lb.getDamage(), lb.getShowhp(), lb.getItems(), lb.getSkills(), lb.getShowtitle(), lb.getSkin());
                    this.eb.BossList.add(b);
                    this.eb.timer.despawn.DeSpawnEvent(this.eb);
                    if (b.getLivingEntity() != null) {
                        b.getLivingEntity().setVelocity(new Vector(0.0f, 0.5f, 0.0f));
                    }
                }
            }
        }
    }
    
    public LoadBoss getloadBossforEgg(final String s) {
        if (this.eb.BossLoadList != null) {
            for (final LoadBoss lb : this.eb.BossLoadList) {
                final String str = new StringBuilder().append(ChatColor.DARK_PURPLE).append(ChatColor.ITALIC).append(lb.getName()).toString();
                if (str.equals(s)) {
                    return lb;
                }
            }
        }
        return null;
    }
}
