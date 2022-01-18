// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Skills;

import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.entity.Player;
import me.ThaH3lper.com.Boss.Boss;
import me.ThaH3lper.com.Api.BossSkillEvent;
import me.ThaH3lper.com.Skills.AllSkills.CustomSkill;
import me.ThaH3lper.com.Skills.AllSkills.Teleport;
import me.ThaH3lper.com.Skills.AllSkills.DragIn;
import me.ThaH3lper.com.Skills.AllSkills.Fire;
import me.ThaH3lper.com.Skills.AllSkills.Lightning;
import me.ThaH3lper.com.Skills.AllSkills.PotionBoss;
import me.ThaH3lper.com.Skills.AllSkills.Potions;
import me.ThaH3lper.com.Skills.AllSkills.Bossmsg;
import me.ThaH3lper.com.Skills.AllSkills.Command;
import me.ThaH3lper.com.Skills.AllSkills.Throw;
import me.ThaH3lper.com.Skills.AllSkills.Swarm;
import me.ThaH3lper.com.EpicBoss;

public class SkillsHandler
{
    public EpicBoss eb;
    public Swarm swarm;
    public Throw throwplayer;
    public Command command;
    public Bossmsg bossmsg;
    public Potions potions;
    public PotionBoss potionboss;
    public Lightning lightning;
    public Fire fire;
    public DragIn dragin;
    public Teleport teleport;
    private CustomSkill customskill;
    public BossSkillEvent event;
    
    public SkillsHandler(final EpicBoss boss) {
        this.eb = boss;
        this.swarm = new Swarm(this.eb);
        this.throwplayer = new Throw(this.eb);
        this.command = new Command(this.eb);
        this.bossmsg = new Bossmsg(this.eb);
        this.potions = new Potions(this.eb);
        this.potionboss = new PotionBoss(this.eb);
        this.lightning = new Lightning(this.eb);
        this.fire = new Fire(this.eb);
        this.dragin = new DragIn(this.eb);
        this.teleport = new Teleport(this.eb);
        this.customskill = new CustomSkill(this.eb);
    }
    
    public void skills(final Boss b, final Player p) {
        final List<String> skills = b.getSkill();
        if (skills != null) {
            int index = 0;
            for (final String s : skills) {
                final String[] parts = s.split(" ");
                if (parts[0].equals("swarm")) {
                    this.swarm.executeSwarm(s, b, index);
                }
                else if (parts[0].equals("throw")) {
                    this.throwplayer.executeThrow(s, b, index);
                }
                else if (parts[0].equals("command")) {
                    this.command.executeCommand(s, b, index, p);
                }
                else if (parts[0].equals("bossmsg")) {
                    this.bossmsg.executeMsg(s, b, index, p);
                }
                else if (parts[0].equals("potion")) {
                    this.potions.executePotions(s, b, index);
                }
                else if (parts[0].equals("potionboss")) {
                    this.potionboss.executeBossPotions(s, b, index);
                }
                else if (parts[0].equals("lightning")) {
                    this.lightning.executeLightning(s, b, index);
                }
                else if (parts[0].equals("fire")) {
                    this.fire.executeFire(s, b, index);
                }
                else if (parts[0].equals("dragin")) {
                    this.dragin.executeDragin(s, b, index);
                }
                else if (parts[0].equals("teleport")) {
                    this.teleport.executeTeleport(s, b, index, p);
                }
                else if (this.eb.CustomSkills != null) {
                    for (final String skill : this.eb.CustomSkills) {
                        if (parts[0].equals(skill)) {
                            this.customskill.executeThrow(s, b, index);
                        }
                    }
                }
                ++index;
            }
        }
    }
    
    public List<Player> getPlayersRadious(final int radious, final Boss b) {
        final List<Player> playerlist = new ArrayList<Player>();
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player p = onlinePlayers[i];
            if (radious != 0) {
                if (b.getLocation().getWorld() == p.getLocation().getWorld() && b.getLocation().distance(p.getLocation()) < radious) {
                    playerlist.add(p);
                }
            }
            else {
                playerlist.add(p);
            }
        }
        return playerlist;
    }
}
