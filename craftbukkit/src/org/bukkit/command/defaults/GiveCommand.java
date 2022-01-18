// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import java.util.Collection;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import org.bukkit.util.StringUtil;
import java.util.Comparator;
import java.util.Collections;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import java.util.List;

public class GiveCommand extends VanillaCommand
{
    private static List<String> materials;
    
    public GiveCommand() {
        super("give");
        this.description = "Gives the specified player a certain amount of items";
        this.usageMessage = "/give <player> <item> [amount [data]]";
        this.setPermission("bukkit.command.give");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        final Player player = Bukkit.getPlayerExact(args[0]);
        if (player != null) {
            final Material material = Material.matchMaterial(args[1]);
            if (material != null) {
                int amount = 1;
                short data = 0;
                if (args.length >= 3) {
                    amount = this.getInteger(sender, args[2], 1, 64);
                    if (args.length >= 4) {
                        try {
                            data = Short.parseShort(args[3]);
                        }
                        catch (NumberFormatException ex) {}
                    }
                }
                player.getInventory().addItem(new ItemStack(material, amount, data));
                Command.broadcastCommandMessage(sender, "Gave " + player.getName() + " some " + material.getId() + " (" + material + ")");
            }
            else {
                sender.sendMessage("There's no item called " + args[1]);
            }
        }
        else {
            sender.sendMessage("Can't find player " + args[0]);
        }
        return true;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            return super.tabComplete(sender, alias, args);
        }
        if (args.length == 2) {
            final String arg = args[1];
            final List<String> materials = GiveCommand.materials;
            List<String> completion = null;
            final int size = materials.size();
            int i = Collections.binarySearch(materials, arg, String.CASE_INSENSITIVE_ORDER);
            if (i < 0) {
                i = -1 - i;
            }
            while (i < size) {
                final String material = materials.get(i);
                if (!StringUtil.startsWithIgnoreCase(material, arg)) {
                    break;
                }
                if (completion == null) {
                    completion = new ArrayList<String>();
                }
                completion.add(material);
                ++i;
            }
            if (completion != null) {
                return completion;
            }
        }
        return (List<String>)ImmutableList.of();
    }
    
    static {
        final ArrayList<String> materialList = new ArrayList<String>();
        for (final Material material : Material.values()) {
            materialList.add(material.name());
        }
        Collections.sort(materialList);
        GiveCommand.materials = (List<String>)ImmutableList.copyOf((Collection<?>)materialList);
    }
}
