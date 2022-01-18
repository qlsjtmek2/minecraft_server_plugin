// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Skills.AllSkills;

import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import me.ThaH3lper.com.Api.BossSkillEvent;
import me.ThaH3lper.com.Boss.Boss;
import java.util.Random;
import me.ThaH3lper.com.EpicBoss;

public class Lightning
{
    private EpicBoss eb;
    Random r;
    
    public Lightning(final EpicBoss boss) {
        this.r = new Random();
        this.eb = boss;
    }
    
    public void executeLightning(final String s, final Boss b, final int index) {
        final String[] parts = s.split(" ");
        final String[] settings = parts[1].split(":");
        final float chance = Float.parseFloat(parts[3]);
        if (parts[2].contains(">")) {
            final String exe = parts[2].replace(">", "");
            if (b.getHealth() > Integer.parseInt(exe)) {
                this.lightPlayer(Integer.parseInt(settings[0]), Integer.parseInt(settings[1]), b, chance);
            }
        }
        else if (parts[2].contains("=")) {
            final String exe = parts[2].replace("=", "");
            if (b.getHealth() <= Integer.parseInt(exe)) {
                this.lightPlayer(Integer.parseInt(settings[0]), Integer.parseInt(settings[1]), b, chance);
                b.setRemoveSkill(index);
            }
        }
        else if (parts[2].contains("<")) {
            final String exe = parts[2].replace("<", "");
            if (b.getHealth() < Integer.parseInt(exe)) {
                this.lightPlayer(Integer.parseInt(settings[0]), Integer.parseInt(settings[1]), b, chance);
            }
        }
        else if (parts[2].contains("/")) {
            final String exe = parts[2].replace("/", "");
            final String[] value = exe.split("-");
            if (b.getHealth() < Integer.parseInt(value[0]) && b.getHealth() > Integer.parseInt(value[1])) {
                this.lightPlayer(Integer.parseInt(settings[0]), Integer.parseInt(settings[1]), b, chance);
            }
        }
    }
    
    public void lightPlayer(final int radious, final int damage, final Boss b, final float chance) {
        if (this.r.nextFloat() <= chance) {
            this.eb.skillhandler.event = new BossSkillEvent(this.eb, b, "lightning", false);
            Bukkit.getServer().getPluginManager().callEvent((Event)this.eb.skillhandler.event);
            if (this.eb.skillhandler.getPlayersRadious(radious, b) != null) {
                for (final Player p : this.eb.skillhandler.getPlayersRadious(radious, b)) {
                    p.getLocation().getWorld().strikeLightningEffect(p.getLocation());
                    p.damage(damage);
                }
            }
        }
    }
}
