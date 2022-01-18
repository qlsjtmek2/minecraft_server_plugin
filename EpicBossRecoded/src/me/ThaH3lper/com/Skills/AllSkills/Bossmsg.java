// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Skills.AllSkills;

import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import me.ThaH3lper.com.Api.BossSkillEvent;
import org.bukkit.entity.Player;
import me.ThaH3lper.com.Boss.Boss;
import java.util.Random;
import me.ThaH3lper.com.EpicBoss;

public class Bossmsg
{
    private EpicBoss eb;
    Random r;
    
    public Bossmsg(final EpicBoss boss) {
        this.r = new Random();
        this.eb = boss;
    }
    
    public void executeMsg(final String s, final Boss b, final int index, final Player p) {
        final String[] parts = s.split(" ");
        final String[] settings = parts[1].split(":");
        final float chance = Float.parseFloat(parts[3]);
        if (parts[2].contains(">")) {
            final String exe = parts[2].replace(">", "");
            if (b.getHealth() > Integer.parseInt(exe)) {
                this.sendMsg(settings[1], chance, Integer.parseInt(settings[0]), b, p);
            }
        }
        else if (parts[2].contains("=")) {
            final String exe = parts[2].replace("=", "");
            if (b.getHealth() <= Integer.parseInt(exe)) {
                this.sendMsg(settings[1], chance, Integer.parseInt(settings[0]), b, p);
                b.setRemoveSkill(index);
            }
        }
        else if (parts[2].contains("<")) {
            final String exe = parts[2].replace("<", "");
            if (b.getHealth() < Integer.parseInt(exe)) {
                this.sendMsg(settings[1], chance, Integer.parseInt(settings[0]), b, p);
            }
        }
        else if (parts[2].contains("/")) {
            final String exe = parts[2].replace("/", "");
            final String[] value = exe.split("-");
            if (b.getHealth() < Integer.parseInt(value[0]) && b.getHealth() > Integer.parseInt(value[1])) {
                this.sendMsg(settings[1], chance, Integer.parseInt(settings[0]), b, p);
            }
        }
    }
    
    public void sendMsg(String s, final float chance, final int radious, final Boss b, final Player pla) {
        if (this.r.nextFloat() <= chance && this.eb.skillhandler.getPlayersRadious(radious, b) != null) {
            for (final Player p : this.eb.skillhandler.getPlayersRadious(radious, b)) {
                this.eb.skillhandler.event = new BossSkillEvent(this.eb, b, "bossmsg", false);
                Bukkit.getServer().getPluginManager().callEvent((Event)this.eb.skillhandler.event);
                s = s.replace("$boss", b.getName());
                s = s.replace("_", " ");
                s = s.replace("@", ":");
                if (p != null && pla != null) {
                    s = s.replace("$player", pla.getName());
                }
                s = ChatColor.translateAlternateColorCodes('&', s);
                p.sendMessage(s);
            }
        }
    }
}
