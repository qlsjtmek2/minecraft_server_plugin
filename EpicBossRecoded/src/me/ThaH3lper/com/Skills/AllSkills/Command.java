// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Skills.AllSkills;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import me.ThaH3lper.com.Api.BossSkillEvent;
import org.bukkit.entity.Player;
import me.ThaH3lper.com.Boss.Boss;
import java.util.Random;
import me.ThaH3lper.com.EpicBoss;

public class Command
{
    private EpicBoss eb;
    Random r;
    
    public Command(final EpicBoss boss) {
        this.r = new Random();
        this.eb = boss;
    }
    
    public void executeCommand(final String s, final Boss b, final int index, final Player p) {
        final String[] parts = s.split(" ");
        final float chance = Float.parseFloat(parts[3]);
        if (parts[2].contains(">")) {
            final String exe = parts[2].replace(">", "");
            if (b.getHealth() > Integer.parseInt(exe)) {
                this.sendCommand(parts[1], chance, p, b);
            }
        }
        else if (parts[2].contains("=")) {
            final String exe = parts[2].replace("=", "");
            if (b.getHealth() <= Integer.parseInt(exe)) {
                this.sendCommand(parts[1], chance, p, b);
                b.setRemoveSkill(index);
            }
        }
        else if (parts[2].contains("<")) {
            final String exe = parts[2].replace("<", "");
            if (b.getHealth() < Integer.parseInt(exe)) {
                this.sendCommand(parts[1], chance, p, b);
            }
        }
        else if (parts[2].contains("/")) {
            final String exe = parts[2].replace("/", "");
            final String[] value = exe.split("-");
            if (b.getHealth() < Integer.parseInt(value[0]) && b.getHealth() > Integer.parseInt(value[1])) {
                this.sendCommand(parts[1], chance, p, b);
            }
        }
    }
    
    public void sendCommand(String s, final float chance, final Player p, final Boss b) {
        if (this.r.nextFloat() <= chance) {
            this.eb.skillhandler.event = new BossSkillEvent(this.eb, b, "command", false);
            Bukkit.getServer().getPluginManager().callEvent((Event)this.eb.skillhandler.event);
            s = s.replace("_", " ");
            s = s.replace("@", "_");
            if (p != null) {
                s = s.replace("$player", p.getName());
            }
            s = s.replace("$boss", b.getName());
            Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), s);
        }
    }
}
