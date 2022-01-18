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

public class DragIn
{
    private EpicBoss eb;
    Random r;
    
    public DragIn(final EpicBoss boss) {
        this.r = new Random();
        this.eb = boss;
    }
    
    public void executeDragin(final String s, final Boss b, final int index) {
        final String[] parts = s.split(" ");
        final String[] settings = parts[1].split(":");
        final float chance = Float.parseFloat(parts[3]);
        if (parts[2].contains(">")) {
            final String exe = parts[2].replace(">", "");
            if (b.getHealth() > Integer.parseInt(exe)) {
                this.DragPlayer(Integer.parseInt(settings[0]), chance, b);
            }
        }
        else if (parts[2].contains("=")) {
            final String exe = parts[2].replace("=", "");
            if (b.getHealth() <= Integer.parseInt(exe)) {
                this.DragPlayer(Integer.parseInt(settings[0]), chance, b);
                b.setRemoveSkill(index);
            }
        }
        else if (parts[2].contains("<")) {
            final String exe = parts[2].replace("<", "");
            if (b.getHealth() < Integer.parseInt(exe)) {
                this.DragPlayer(Integer.parseInt(settings[0]), chance, b);
            }
        }
        else if (parts[2].contains("/")) {
            final String exe = parts[2].replace("/", "");
            final String[] value = exe.split("-");
            if (b.getHealth() < Integer.parseInt(value[0]) && b.getHealth() > Integer.parseInt(value[1])) {
                this.DragPlayer(Integer.parseInt(settings[0]), chance, b);
            }
        }
    }
    
    public void DragPlayer(final int radious, final float chance, final Boss b) {
        if (this.r.nextFloat() <= chance) {
            this.eb.skillhandler.event = new BossSkillEvent(this.eb, b, "Dragin", false);
            Bukkit.getServer().getPluginManager().callEvent((Event)this.eb.skillhandler.event);
            if (this.eb.skillhandler.getPlayersRadious(radious, b) != null) {
                for (final Player p : this.eb.skillhandler.getPlayersRadious(radious, b)) {
                    p.teleport(b.getLocation());
                }
            }
        }
    }
}
