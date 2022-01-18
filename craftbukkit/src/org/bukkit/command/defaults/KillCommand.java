// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

public class KillCommand extends VanillaCommand
{
    public KillCommand() {
        super("kill");
        this.description = "Commits suicide, only usable as a player";
        this.usageMessage = "/kill";
        this.setPermission("bukkit.command.kill");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            final EntityDamageEvent ede = new EntityDamageEvent(player, EntityDamageEvent.DamageCause.SUICIDE, 1000);
            Bukkit.getPluginManager().callEvent(ede);
            if (ede.isCancelled()) {
                return true;
            }
            ede.getEntity().setLastDamageCause(ede);
            player.setHealth(0);
            sender.sendMessage("Ouch. That look like it hurt.");
        }
        else {
            sender.sendMessage("You can only perform this command as a player");
        }
        return true;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        return (List<String>)ImmutableList.of();
    }
}
