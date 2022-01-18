// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.permissions.PermissionAttachment;
import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import think.rpgitems.Plugin;
import think.rpgitems.data.RPGValue;
import org.bukkit.entity.Player;
import think.rpgitems.power.types.PowerLeftClick;
import think.rpgitems.power.types.PowerRightClick;

public class PowerCommand extends Power implements PowerRightClick, PowerLeftClick
{
    public String command;
    public String display;
    public String permission;
    public boolean isRight;
    public long cooldownTime;
    
    public PowerCommand() {
        this.command = "";
        this.display = "Runs command";
        this.permission = "";
        this.isRight = true;
        this.cooldownTime = 20L;
    }
    
    @Override
    public void rightClick(final Player player) {
        if (this.isRight) {
            RPGValue value = RPGValue.get(player, this.item, "command." + this.command + ".cooldown");
            long cooldown;
            if (value == null) {
                cooldown = System.currentTimeMillis() / 50L;
                value = new RPGValue(player, this.item, "command." + this.command + ".cooldown", cooldown);
            }
            else {
                cooldown = value.asLong();
            }
            if (cooldown <= System.currentTimeMillis() / 50L) {
                value.set(System.currentTimeMillis() / 50L + this.cooldownTime);
                if (this.permission.length() != 0 && !this.permission.equals("*")) {
                    final PermissionAttachment attachment = player.addAttachment((org.bukkit.plugin.Plugin)Plugin.plugin, 1);
                    final String[] perms = this.permission.split("\\.");
                    final StringBuilder p = new StringBuilder();
                    for (int i = 0; i < perms.length; ++i) {
                        p.append(perms[i]);
                        attachment.setPermission(p.toString(), true);
                        p.append('.');
                    }
                }
                final boolean wasOp = player.isOp();
                if (this.permission.equals("*")) {
                    player.setOp(true);
                }
                player.chat("/" + this.command.replaceAll("\\{player\\}", player.getName()));
                if (this.permission.equals("*")) {
                    player.setOp(wasOp);
                }
            }
            else {
                player.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.cooldown", Locale.getPlayerLocale(player)), (cooldown - System.currentTimeMillis() / 50L) / 20.0));
            }
        }
    }
    
    @Override
    public void leftClick(final Player player) {
        if (!this.isRight) {
            RPGValue value = RPGValue.get(player, this.item, "command." + this.command + ".cooldown");
            long cooldown;
            if (value == null) {
                cooldown = System.currentTimeMillis() / 50L;
                value = new RPGValue(player, this.item, "command." + this.command + ".cooldown", cooldown);
            }
            else {
                cooldown = value.asLong();
            }
            if (cooldown <= System.currentTimeMillis() / 50L) {
                value.set(System.currentTimeMillis() / 50L + this.cooldownTime);
                if (this.permission.length() != 0 && !this.permission.equals("*")) {
                    final PermissionAttachment attachment = player.addAttachment((org.bukkit.plugin.Plugin)Plugin.plugin, 1);
                    final String[] perms = this.permission.split("\\.");
                    final StringBuilder p = new StringBuilder();
                    for (int i = 0; i < perms.length; ++i) {
                        p.append(perms[i]);
                        attachment.setPermission(p.toString(), true);
                        p.append('.');
                    }
                }
                final boolean wasOp = player.isOp();
                if (this.permission.equals("*")) {
                    player.setOp(true);
                }
                player.chat("/" + this.command.replaceAll("\\{player\\}", player.getName()));
                if (this.permission.equals("*")) {
                    player.setOp(wasOp);
                }
            }
            else {
                player.sendMessage(ChatColor.AQUA + String.format(Locale.get("message.cooldown", Locale.getPlayerLocale(player)), (cooldown - System.currentTimeMillis() / 50L) / 20.0));
            }
        }
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + this.display;
    }
    
    @Override
    public String getName() {
        return "command";
    }
    
    @Override
    public void init(final ConfigurationSection s) {
        this.cooldownTime = s.getLong("cooldown", 20L);
        this.command = s.getString("command", "");
        this.display = s.getString("display", "");
        this.isRight = s.getBoolean("isRight", true);
        this.permission = s.getString("permission", "");
    }
    
    @Override
    public void save(final ConfigurationSection s) {
        s.set("cooldown", (Object)this.cooldownTime);
        s.set("command", (Object)this.command);
        s.set("display", (Object)this.display);
        s.set("isRight", (Object)this.isRight);
        s.set("permission", (Object)this.permission);
    }
}
