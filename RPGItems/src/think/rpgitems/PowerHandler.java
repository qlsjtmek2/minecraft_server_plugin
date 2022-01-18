// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems;

import think.rpgitems.power.PowerPotionTick;
import think.rpgitems.power.PowerSkyHook;
import org.bukkit.Material;
import think.rpgitems.power.PowerTNTCannon;
import think.rpgitems.power.PowerTeleport;
import think.rpgitems.power.PowerRumble;
import think.rpgitems.power.PowerRainbow;
import think.rpgitems.power.PowerPotionSelf;
import org.bukkit.potion.PotionEffectType;
import think.rpgitems.power.PowerPotionHit;
import think.rpgitems.power.PowerLightning;
import think.rpgitems.power.PowerKnockup;
import think.rpgitems.power.PowerIce;
import think.rpgitems.power.PowerFlame;
import think.rpgitems.power.PowerFireball;
import think.rpgitems.power.PowerConsume;
import think.rpgitems.power.PowerCommand;
import think.rpgitems.commands.CommandGroup;
import think.rpgitems.commands.CommandDocumentation;
import think.rpgitems.commands.CommandString;
import org.bukkit.ChatColor;
import think.rpgitems.item.ItemManager;
import think.rpgitems.power.Power;
import think.rpgitems.power.PowerArrow;
import think.rpgitems.data.Locale;
import org.bukkit.entity.Player;
import think.rpgitems.item.RPGItem;
import org.bukkit.command.CommandSender;
import think.rpgitems.commands.CommandHandler;

public class PowerHandler implements CommandHandler
{
    @CommandString("rpgitem $n[] power arrow")
    @CommandDocumentation("$command.rpgitem.arrow")
    @CommandGroup("item_power_arrow")
    public void arrow(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerArrow pow = new PowerArrow();
        pow.cooldownTime = 20L;
        (pow.item = item).addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power arrow $cooldown:i[]")
    @CommandDocumentation("$command.rpgitem.arrow.full")
    @CommandGroup("item_power_arrow")
    public void arrow(final CommandSender sender, final RPGItem item, final int cooldown) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerArrow pow = new PowerArrow();
        pow.item = item;
        pow.cooldownTime = cooldown;
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power command $cooldown:i[] $o[left,right] $display:s[] $command:s[]")
    @CommandDocumentation("$command.rpgitem.command")
    @CommandGroup("item_power_command_b")
    public void command(final CommandSender sender, final RPGItem item, final int cooldown, final String mouse, final String displayText, String command) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerCommand com = new PowerCommand();
        com.cooldownTime = cooldown;
        command = command.trim();
        if (command.charAt(0) == '/') {
            command = command.substring(1);
        }
        com.isRight = mouse.equals("right");
        com.display = displayText;
        com.command = command;
        (com.item = item).addPower(com);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power command $cooldown:i[] $o[left,right] $display:s[] $command:s[] $permission:s[]")
    @CommandDocumentation("$command.rpgitem.command.full")
    @CommandGroup("item_power_command_a")
    public void command(final CommandSender sender, final RPGItem item, final int cooldown, final String mouse, final String displayText, String command, final String permission) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerCommand com = new PowerCommand();
        com.cooldownTime = cooldown;
        command = command.trim();
        if (command.charAt(0) == '/') {
            command = command.substring(1);
        }
        com.isRight = mouse.equals("right");
        com.display = displayText;
        com.command = command;
        com.permission = permission;
        (com.item = item).addPower(com);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power command $cooldown:i[] $o[left,right] $details:s[]")
    @CommandDocumentation("$command.rpgitem.command.old")
    @CommandGroup("item_power_command_c")
    public void command(final CommandSender sender, final RPGItem item, final int cooldown, final String mouse, final String details) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final String[] pArgs = details.split("\\|");
        if (pArgs.length < 2) {
            sender.sendMessage(ChatColor.RED + Locale.get("message.error.command.format", locale));
            return;
        }
        final String display = pArgs[0].trim();
        String command = pArgs[1].trim();
        if (command.charAt(0) == '/') {
            command = command.substring(1);
        }
        String permission = "";
        if (pArgs.length > 2) {
            permission = pArgs[2].trim();
        }
        final PowerCommand com = new PowerCommand();
        com.cooldownTime = cooldown;
        com.isRight = mouse.equals("right");
        com.item = item;
        com.display = display;
        com.command = command;
        com.permission = permission;
        item.addPower(com);
        item.rebuild();
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power consume")
    @CommandDocumentation("$command.rpgitem.consume")
    @CommandGroup("item_power_consume")
    public void consume(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerConsume pow = new PowerConsume();
        (pow.item = item).addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power fireball")
    @CommandDocumentation("$command.rpgitem.fireball")
    @CommandGroup("item_power_fireball")
    public void fireball(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerFireball pow = new PowerFireball();
        pow.cooldownTime = 20L;
        (pow.item = item).addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power fireball $cooldown:i[]")
    @CommandDocumentation("$command.rpgitem.fireball.full")
    @CommandGroup("item_power_fireball")
    public void fireball(final CommandSender sender, final RPGItem item, final int cooldown) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerFireball pow = new PowerFireball();
        pow.item = item;
        pow.cooldownTime = cooldown;
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power flame")
    @CommandDocumentation("$command.rpgitem.flame")
    @CommandGroup("item_power_flame")
    public void flame(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerFlame pow = new PowerFlame();
        pow.burnTime = 20;
        (pow.item = item).addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power flame $burntime:i[]")
    @CommandDocumentation("$command.rpgitem.flame.full")
    @CommandGroup("item_power_flame")
    public void flame(final CommandSender sender, final RPGItem item, final int burnTime) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerFlame pow = new PowerFlame();
        pow.item = item;
        pow.burnTime = burnTime;
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power ice")
    @CommandDocumentation("$command.rpgitem.ice")
    @CommandGroup("item_power_ice")
    public void ice(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerIce pow = new PowerIce();
        pow.cooldownTime = 20L;
        (pow.item = item).addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power ice $cooldown:i[]")
    @CommandDocumentation("$command.rpgitem.ice.full")
    @CommandGroup("item_power_ice")
    public void ice(final CommandSender sender, final RPGItem item, final int cooldown) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerIce pow = new PowerIce();
        pow.item = item;
        pow.cooldownTime = cooldown;
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power knockup")
    @CommandDocumentation("$command.rpgitem.knockup")
    @CommandGroup("item_power_knockup")
    public void knockup(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerKnockup pow = new PowerKnockup();
        pow.item = item;
        pow.chance = 20;
        pow.power = 2.0;
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power knockup $chance:i[] $power:f[]")
    @CommandDocumentation("$command.rpgitem.knockup.full")
    @CommandGroup("item_power_knockup")
    public void knockup(final CommandSender sender, final RPGItem item, final int chance, final double power) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerKnockup pow = new PowerKnockup();
        pow.item = item;
        pow.chance = chance;
        pow.power = power;
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power lightning")
    @CommandDocumentation("$command.rpgitem.lightning")
    @CommandGroup("item_power_lightning")
    public void lightning(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerLightning pow = new PowerLightning();
        pow.item = item;
        pow.chance = 20;
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power lightning $chance:i[]")
    @CommandDocumentation("$command.rpgitem.lightning.full")
    @CommandGroup("item_power_lightning")
    public void lightning(final CommandSender sender, final RPGItem item, final int chance) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerLightning pow = new PowerLightning();
        pow.item = item;
        pow.chance = chance;
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power potionhit $chance:i[] $duration:i[] $amplifier:i[] $effect:s[]")
    @CommandDocumentation("$command.rpgitem.potionhit+PotionEffectType")
    @CommandGroup("item_power_potionhit")
    public void potionhit(final CommandSender sender, final RPGItem item, final int chance, final int duration, final int amplifier, final String effect) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerPotionHit pow = new PowerPotionHit();
        pow.item = item;
        pow.chance = chance;
        pow.duration = duration;
        pow.amplifier = amplifier;
        pow.type = PotionEffectType.getByName(effect);
        if (pow.type == null) {
            sender.sendMessage(ChatColor.RED + String.format(Locale.get("message.error.effect", locale), effect));
            return;
        }
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power potionself $cooldown:i[] $duration:i[] $amplifier:i[] $effect:s[]")
    @CommandDocumentation("$command.rpgitem.potionself+PotionEffectType")
    @CommandGroup("item_power_potionself")
    public void potionself(final CommandSender sender, final RPGItem item, final int ccoldown, final int duration, final int amplifier, final String effect) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerPotionSelf pow = new PowerPotionSelf();
        pow.item = item;
        pow.cooldownTime = ccoldown;
        pow.time = duration;
        pow.amplifier = amplifier;
        pow.type = PotionEffectType.getByName(effect);
        if (pow.type == null) {
            sender.sendMessage(ChatColor.RED + String.format(Locale.get("message.error.effect", locale), effect));
            return;
        }
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power rainbow")
    @CommandDocumentation("$command.rpgitem.rainbow")
    @CommandGroup("item_power_rainbow")
    public void rainbow(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerRainbow pow = new PowerRainbow();
        pow.cooldownTime = 20L;
        pow.count = 5;
        (pow.item = item).addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power rainbow $cooldown:i[] $count:i[]")
    @CommandDocumentation("$command.rpgitem.rainbow.full")
    @CommandGroup("item_power_rainbow")
    public void rainbow(final CommandSender sender, final RPGItem item, final int cooldown, final int count) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerRainbow pow = new PowerRainbow();
        pow.cooldownTime = cooldown;
        pow.count = count;
        (pow.item = item).addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power rumble $cooldown:i[] $power:i[] $distance:i[]")
    @CommandDocumentation("$command.rpgitem.rumble")
    @CommandGroup("item_power_rumble")
    public void rumble(final CommandSender sender, final RPGItem item, final int cooldown, final int power, final int distance) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerRumble pow = new PowerRumble();
        pow.item = item;
        pow.cooldownTime = cooldown;
        pow.power = power;
        pow.distance = distance;
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power teleport")
    @CommandDocumentation("$command.rpgitem.teleport")
    @CommandGroup("item_power_teleport")
    public void teleport(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerTeleport pow = new PowerTeleport();
        pow.item = item;
        pow.cooldownTime = 20L;
        pow.distance = 5;
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power teleport $cooldown:i[] $distance:i[]")
    @CommandDocumentation("$command.rpgitem.teleport.full")
    @CommandGroup("item_power_teleport")
    public void teleport(final CommandSender sender, final RPGItem item, final int cooldown, final int distance) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerTeleport pow = new PowerTeleport();
        pow.item = item;
        pow.cooldownTime = cooldown;
        pow.distance = distance;
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power tntcannon")
    @CommandDocumentation("$command.rpgitem.tntcannon")
    @CommandGroup("item_power_tntcannon")
    public void tntcannon(final CommandSender sender, final RPGItem item) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerTNTCannon pow = new PowerTNTCannon();
        pow.item = item;
        pow.cooldownTime = 20L;
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power tntcannon $cooldown:i[]")
    @CommandDocumentation("$command.rpgitem.tntcannon.full")
    @CommandGroup("item_power_tntcannon")
    public void tntcannon(final CommandSender sender, final RPGItem item, final int cooldown) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerTNTCannon pow = new PowerTNTCannon();
        pow.item = item;
        pow.cooldownTime = cooldown;
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power skyhook $m[] $distance:i[]")
    @CommandDocumentation("$command.rpgitem.skyhook")
    @CommandGroup("item_power_skyhook")
    public void skyHook(final CommandSender sender, final RPGItem item, final Material material, final int distance) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerSkyHook pow = new PowerSkyHook();
        pow.item = item;
        pow.railMaterial = material;
        pow.hookDistance = distance;
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
    
    @CommandString("rpgitem $n[] power potiontick $amplifier:i[] $effect:s[]")
    @CommandDocumentation("$command.rpgitem.potiontick")
    @CommandGroup("item_power_potiontick")
    public void potionTick(final CommandSender sender, final RPGItem item, final int amplifier, final String effect) {
        final String locale = (sender instanceof Player) ? Locale.getPlayerLocale((Player)sender) : "en_GB";
        final PowerPotionTick pow = new PowerPotionTick();
        pow.item = item;
        pow.amplifier = amplifier;
        pow.effect = PotionEffectType.getByName(effect);
        if (pow.effect == null) {
            sender.sendMessage(ChatColor.RED + String.format(Locale.get("message.error.effect", locale), effect));
            return;
        }
        item.addPower(pow);
        ItemManager.save(Plugin.plugin);
        sender.sendMessage(ChatColor.AQUA + Locale.get("message.power.ok", locale));
    }
}
