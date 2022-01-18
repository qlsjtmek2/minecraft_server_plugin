// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Skills.AllSkills;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import me.ThaH3lper.com.Api.BossSkillEvent;
import org.bukkit.entity.Player;
import me.ThaH3lper.com.Boss.Boss;
import java.util.Random;
import me.ThaH3lper.com.EpicBoss;

public class Teleport
{
    private EpicBoss eb;
    Random r;
    
    public Teleport(final EpicBoss boss) {
        this.r = new Random();
        this.eb = boss;
    }
    
    public void executeTeleport(final String s, final Boss b, final int index, final Player p) {
        final String[] parts = s.split(" ");
        final String[] settings = parts[1].split(":");
        final float chance = Float.parseFloat(parts[3]);
        if (parts[2].contains(">")) {
            final String exe = parts[2].replace(">", "");
            if (b.getHealth() > Integer.parseInt(exe)) {
                this.doTeleport(settings[0], chance, b, p);
            }
        }
        else if (parts[2].contains("=")) {
            final String exe = parts[2].replace("=", "");
            if (b.getHealth() <= Integer.parseInt(exe)) {
                this.doTeleport(settings[0], chance, b, p);
                b.setRemoveSkill(index);
            }
        }
        else if (parts[2].contains("<")) {
            final String exe = parts[2].replace("<", "");
            if (b.getHealth() < Integer.parseInt(exe)) {
                this.doTeleport(settings[0], chance, b, p);
            }
        }
        else if (parts[2].contains("/")) {
            final String exe = parts[2].replace("/", "");
            final String[] value = exe.split("-");
            if (b.getHealth() < Integer.parseInt(value[0]) && b.getHealth() > Integer.parseInt(value[1])) {
                this.doTeleport(settings[0], chance, b, p);
            }
        }
    }
    
    public void doTeleport(final String s, final float chance, final Boss b, final Player pla) {
        if (this.r.nextFloat() <= chance && pla != null) {
            if (s.equalsIgnoreCase("teleport")) {
                b.getLivingEntity().teleport(pla.getLocation());
            }
            else {
                final Location l = b.getLocation();
                b.getLivingEntity().teleport(pla.getLocation());
                pla.teleport(l);
            }
            this.eb.skillhandler.event = new BossSkillEvent(this.eb, b, "teleport", false);
            Bukkit.getServer().getPluginManager().callEvent((Event)this.eb.skillhandler.event);
        }
    }
}
