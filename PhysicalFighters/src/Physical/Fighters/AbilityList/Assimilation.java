// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MajorModule.AbilityList;
import Physical.Fighters.MinerModule.CommandInterface;
import Physical.Fighters.MinerModule.EventData;

public class Assimilation extends AbilityBase implements CommandInterface
{
    private boolean ActiveAss;
    
    public Assimilation() {
        this.ActiveAss = false;
        if (PhysicalFighters.SRankUsed) {
            this.InitAbility("\ud761\uc218", Type.Passive_Manual, Rank.S, "\uc790\uc2e0\uc774 \uc8fd\uc778 \uc0ac\ub78c\uc758 \ub2a5\ub825\uc744 \ud761\uc218\ud569\ub2c8\ub2e4. \uc561\ud2f0\ube0c \ub2a5\ub825\uc740", "1\uac1c\ub9cc \uac00\ub2a5\ud569\ub2c8\ub2e4. \ubbf8\ub7ec\ub9c1\ub3c4 \ud761\uc218\uac00 \uac00\ub2a5\ud558\uba70 \ub370\uc2a4 \ub178\ud2b8\uc758 \uacbd\uc6b0", "\uc774\ubbf8 \ub2a5\ub825\uc744 \uc37c\ub354\ub77c\ub3c4 \ub2e4\uc2dc \uc4f8\uc218 \uc788\uc2b5\ub2c8\ub2e4. \uc790\uc2e0\uc5d0\uac8c \ud0c0\uaca9\ubc1b\uc740", "\uc0ac\ub78c\uc740 \ubc30\uace0\ud514\uc774 \ube60\ub974\uac8c \uac10\uc18c\ud569\ub2c8\ub2e4. \"/va a\" \uba85\ub839\uc73c\ub85c", "\uc790\uc2e0\uc774 \ud761\uc218\ud55c \ub2a5\ub825\uc744 \ubcfc\uc218 \uc788\uc2b5\ub2c8\ub2e4.", "\ud761\uc218\uac00 \uac00\ub2a5\ud55c \ub2a5\ub825\uc758 \uac2f\uc218\uc5d0\ub294 \uc81c\ud55c\uc774 \uc5c6\uc2b5\ub2c8\ub2e4.");
            this.InitAbility(0, 0, true);
            EventManager.onEntityDamageByEntity.add(new EventData(this, 0));
            EventManager.onEntityDeath.add(new EventData(this, 1));
            Assimilation.cm.RegisterCommand(this);
        }
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final EntityDamageByEntityEvent Event0 = (EntityDamageByEntityEvent)event;
                if (this.PlayerCheck(Event0.getDamager())) {
                    return 0;
                }
                break;
            }
            case 1: {
                final EntityDeathEvent Event2 = (EntityDeathEvent)event;
                if (Event2.getEntity() instanceof Player && this.PlayerCheck(Event2.getEntity().getKiller())) {
                    return 1;
                }
                break;
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final EntityDamageByEntityEvent Event0 = (EntityDamageByEntityEvent)event;
                if (Event0.getEntity() instanceof Player) {
                    final Player p = (Player)Event0.getEntity();
                    p.setSaturation(0.0f);
                    break;
                }
                break;
            }
            case 1: {
                final EntityDeathEvent Event2 = (EntityDeathEvent)event;
                if (!(Event2.getEntity() instanceof Player)) {
                    break;
                }
                final Player p2 = (Player)Event2.getEntity();
                final AbilityBase a = AbilityBase.FindAbility(p2);
                if (a == null) {
                    break;
                }
                a.AbilityCTimerCancel();
                a.AbilityDTimerCancel();
                if (a.GetAbilityType() == Type.Passive_AutoMatic || a.GetAbilityType() == Type.Passive_Manual) {
                    a.SetPlayer(this.GetPlayer(), false);
                    this.GetPlayer().sendMessage(PhysicalFighters.a + "새로운 §b패시브 §f능력을 흡수하였습니다.");
                    this.GetPlayer().sendMessage(PhysicalFighters.a + "§e새로운 능력: " + ChatColor.WHITE + a.GetAbilityName());
                    break;
                }
                if (!this.ActiveAss) {
                    a.SetPlayer(this.GetPlayer(), false);
                    this.GetPlayer().sendMessage(PhysicalFighters.a + "새로운 §c액티브 §f능력을 흡수하였습니다.");
                    this.GetPlayer().sendMessage(PhysicalFighters.a + "§e새로운 능력: " + ChatColor.WHITE + a.GetAbilityName());
                    this.GetPlayer().sendMessage(PhysicalFighters.a + "이제 §c액티브 §f흡수는 §d불가능§f합니다.");
                    this.ActiveAss = true;
                    break;
                }
                this.GetPlayer().sendMessage(PhysicalFighters.a + "§c흡수할수 없는 능력을 가지고 있었습니다.");
                break;
            }
        }
    }
    
    @Override
    public void A_SetEvent(final Player p) {
        this.ActiveAss = false;
    }
    
    @Override
    public boolean onCommandEvent(final CommandSender sender, final Command command, final String label, final String[] data) {
        if (sender instanceof Player && this.PlayerCheck((Player)sender) && data[0].equalsIgnoreCase("a") && data.length == 1) {
            sender.sendMessage(ChatColor.GREEN + "-- \ub2f9\uc2e0\uc774 \uc18c\uc720\ud55c \ub2a5\ub825 --");
            for (final AbilityBase a : AbilityList.AbilityList) {
                if (a.PlayerCheck(this.GetPlayer())) {
                    this.GetPlayer().sendMessage(a.GetAbilityName());
                }
            }
            return true;
        }
        return false;
    }
}
