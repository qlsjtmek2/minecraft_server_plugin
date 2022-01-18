// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import Physical.Fighters.MajorModule.AbilityList;
import org.bukkit.inventory.ItemStack;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Event;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class nemi extends AbilityBase
{
    public nemi() {
        this.InitAbility("\ub098\ubbf8", Type.Active_Immediately, Rank.C, "\ub2e4\ub978\uc0ac\ub78c\ub4e4\uc758 \uc704\uce58\ub97c \uc54c\uc218\uc788\ub2e4.");
        this.InitAbility(3, 0, true, ShowText.No_Text);
        this.RegisterLeftClickEvent();
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        if (!this.PlayerCheck(Event.getPlayer())) {
            return -1;
        }
        if (this.ItemCheck(ACC.DefaultItem)) {
            return 0;
        }
        final ItemStack i = Event.getPlayer().getItemInHand();
        i.setDurability((short)0);
        return -2;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        final Player p = Event.getPlayer();
        switch (CustomData) {
            case 0: {
                p.sendMessage(PhysicalFighters.a + "§6=-=- §c플레이어 위치 §6-=-=");
                p.sendMessage(PhysicalFighters.a + "§6§l━━━━━━━━━━━━━━━━━━━");
                final List<AbilityBase> pl = AbilityList.AbilityList;
                int count = 0;
                for (int l = 0; l < pl.size(); ++l) {
                    if (pl.get(l).GetPlayer() != null) {
                        final Player temp = Bukkit.getServer().getPlayerExact(pl.get(l).GetPlayer().getName());
                        final AbilityBase tempab = pl.get(l);
                        if (temp != null) {
                            final int x = (int)tempab.GetPlayer().getLocation().getX();
                            final int y = (int)tempab.GetPlayer().getLocation().getY();
                            final int z = (int)tempab.GetPlayer().getLocation().getZ();
                            p.sendMessage(String.format(PhysicalFighters.a + "§a%d. §f%s : §cX §f%d, §cY §f%d, §cZ §f%d", count, tempab.GetPlayer().getName(), x, y, z));
                            ++count;
                        }
                    }
                }
                p.sendMessage(PhysicalFighters.a + "§6§l━━━━━━━━━━━━━━━━━━━");
                break;
            }
        }
    }
}
