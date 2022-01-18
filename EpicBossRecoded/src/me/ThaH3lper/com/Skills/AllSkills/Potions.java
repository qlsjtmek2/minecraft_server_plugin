// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Skills.AllSkills;

import java.util.Iterator;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import me.ThaH3lper.com.Api.BossSkillEvent;
import me.ThaH3lper.com.Boss.Boss;
import java.util.Random;
import me.ThaH3lper.com.EpicBoss;

public class Potions
{
    private EpicBoss eb;
    Random r;
    
    public Potions(final EpicBoss boss) {
        this.r = new Random();
        this.eb = boss;
    }
    
    public void executePotions(final String s, final Boss b, final int index) {
        final String[] parts = s.split(" ");
        final String[] settings = parts[1].split(":");
        final float chance = Float.parseFloat(parts[3]);
        if (parts[2].contains(">")) {
            final String exe = parts[2].replace(">", "");
            if (b.getHealth() > Integer.parseInt(exe)) {
                this.SetPlayer(Integer.parseInt(settings[0]), settings[1], Integer.parseInt(settings[3]), Integer.parseInt(settings[2]), chance, b);
            }
        }
        else if (parts[2].contains("=")) {
            final String exe = parts[2].replace("=", "");
            if (b.getHealth() <= Integer.parseInt(exe)) {
                this.SetPlayer(Integer.parseInt(settings[0]), settings[1], Integer.parseInt(settings[3]), Integer.parseInt(settings[2]), chance, b);
                b.setRemoveSkill(index);
            }
        }
        else if (parts[2].contains("<")) {
            final String exe = parts[2].replace("<", "");
            if (b.getHealth() < Integer.parseInt(exe)) {
                this.SetPlayer(Integer.parseInt(settings[0]), settings[1], Integer.parseInt(settings[3]), Integer.parseInt(settings[2]), chance, b);
            }
        }
        else if (parts[2].contains("/")) {
            final String exe = parts[2].replace("/", "");
            final String[] value = exe.split("-");
            if (b.getHealth() < Integer.parseInt(value[0]) && b.getHealth() > Integer.parseInt(value[1])) {
                this.SetPlayer(Integer.parseInt(settings[0]), settings[1], Integer.parseInt(settings[3]), Integer.parseInt(settings[2]), chance, b);
            }
        }
    }
    
    public void SetPlayer(final int radious, final String potion, final int lvl, final int duration, final float chance, final Boss b) {
        if (this.r.nextFloat() <= chance) {
            this.eb.skillhandler.event = new BossSkillEvent(this.eb, b, "potion", false);
            Bukkit.getServer().getPluginManager().callEvent((Event)this.eb.skillhandler.event);
            if (this.eb.skillhandler.getPlayersRadious(radious, b) != null) {
                for (final Player p : this.eb.skillhandler.getPlayersRadious(radious, b)) {
                    final PotionEffect pe = new PotionEffect(PotionEffectType.getByName(potion), duration * 20, lvl - 1);
                    if (pe != null) {
                        p.addPotionEffect(pe);
                    }
                }
            }
        }
    }
}
