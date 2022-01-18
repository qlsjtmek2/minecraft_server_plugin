// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Skills.AllSkills;

import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import me.ThaH3lper.com.Boss.Boss;
import me.ThaH3lper.com.Api.BossSkillEvent;
import java.util.Random;
import me.ThaH3lper.com.EpicBoss;

public class CustomSkill
{
    private EpicBoss eb;
    Random r;
    private BossSkillEvent event;
    
    public CustomSkill(final EpicBoss boss) {
        this.r = new Random();
        this.eb = boss;
    }
    
    public void executeThrow(final String s, final Boss b, final int index) {
        final String[] parts = s.split(" ");
        final float chance = Float.parseFloat(parts[2]);
        if (parts[1].contains(">")) {
            final String exe = parts[1].replace(">", "");
            if (b.getHealth() > Integer.parseInt(exe)) {
                this.GoCustom(chance, b, parts[0]);
            }
        }
        else if (parts[1].contains("=")) {
            final String exe = parts[1].replace("=", "");
            if (b.getHealth() <= Integer.parseInt(exe)) {
                this.GoCustom(chance, b, parts[0]);
                b.setRemoveSkill(index);
            }
        }
        else if (parts[1].contains("<")) {
            final String exe = parts[1].replace("<", "");
            if (b.getHealth() < Integer.parseInt(exe)) {
                this.GoCustom(chance, b, parts[0]);
            }
        }
        else if (parts[1].contains("/")) {
            final String exe = parts[1].replace("/", "");
            final String[] value = exe.split("-");
            if (b.getHealth() < Integer.parseInt(value[0]) && b.getHealth() > Integer.parseInt(value[1])) {
                this.GoCustom(chance, b, parts[0]);
            }
        }
    }
    
    public void GoCustom(final float chance, final Boss b, final String name) {
        if (this.r.nextFloat() <= chance) {
            this.event = new BossSkillEvent(this.eb, b, name, true);
            Bukkit.getServer().getPluginManager().callEvent((Event)this.event);
        }
    }
}
