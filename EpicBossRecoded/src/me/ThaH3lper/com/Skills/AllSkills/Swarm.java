// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Skills.AllSkills;

import me.ThaH3lper.com.LoadBosses.LoadBoss;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import me.ThaH3lper.com.Api.BossSkillEvent;
import me.ThaH3lper.com.Boss.Boss;
import java.util.Random;
import me.ThaH3lper.com.EpicBoss;

public class Swarm
{
    private EpicBoss eb;
    Random r;
    
    public Swarm(final EpicBoss boss) {
        this.r = new Random();
        this.eb = boss;
    }
    
    public void executeSwarm(final String s, final Boss b, final int index) {
        final String[] parts = s.split(" ");
        final String[] settings = parts[1].split(":");
        final int amount = Integer.parseInt(settings[1]);
        final float chance = Float.parseFloat(parts[3]);
        if (parts[2].contains(">")) {
            final String exe = parts[2].replace(">", "");
            if (b.getHealth() > Integer.parseInt(exe)) {
                this.spawn(settings[0], amount, b, chance);
            }
        }
        else if (parts[2].contains("=")) {
            final String exe = parts[2].replace("=", "");
            if (b.getHealth() <= Integer.parseInt(exe)) {
                this.spawn(settings[0], amount, b, chance);
                b.setRemoveSkill(index);
            }
        }
        else if (parts[2].contains("<")) {
            final String exe = parts[2].replace("<", "");
            if (b.getHealth() < Integer.parseInt(exe)) {
                this.spawn(settings[0], amount, b, chance);
            }
        }
        else if (parts[2].contains("/")) {
            final String exe = parts[2].replace("/", "");
            final String[] value = exe.split("-");
            if (b.getHealth() < Integer.parseInt(value[0]) && b.getHealth() > Integer.parseInt(value[1])) {
                this.spawn(settings[0], amount, b, chance);
            }
        }
    }
    
    public void spawn(final String mob, final int amount, final Boss b, final float Chance) {
        if (this.r.nextFloat() <= Chance) {
            this.eb.skillhandler.event = new BossSkillEvent(this.eb, b, "swarm", false);
            Bukkit.getServer().getPluginManager().callEvent((Event)this.eb.skillhandler.event);
            if (mob.contains("$")) {
                final String bossname = mob.replace("$", "");
                final LoadBoss lb = this.eb.loadconfig.getLoadBoss(bossname);
                if (lb != null) {
                    int i = 1;
                    while (i <= amount) {
                        ++i;
                        this.eb.BossList.add(new Boss(lb.getName(), lb.getHealth(), b.getLocation(), lb.getType(), lb.getDamage(), lb.getShowhp(), lb.getItems(), lb.getSkills(), lb.getShowtitle(), lb.getSkin()));
                    }
                }
            }
            else {
                int j = 1;
                while (j <= amount) {
                    ++j;
                    this.eb.mobs.SpawnMob(mob, b.getLocation());
                }
            }
            this.eb.timer.despawn.DeSpawnEvent(this.eb);
        }
    }
}
