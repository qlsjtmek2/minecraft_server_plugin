// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class Coin extends AbilityBase
{
    public Coin() {
    	this.InitAbility("\ud3ec\uce08\ucf54\uc778", Type.Active_Immediately, Rank.SS, "\uae08\uad34 \uc88c\ud074\ub9ad\uc2dc \ub2a5\ub825\uc774 \ubc1c\ub3d9 \ub429\ub2c8\ub2e4!", "50% \uccb4\ub825 \ud68c\ubcf5, 30% \uccb4\ub825 \uac10\uc18c, 20% \ub2a5\ub825 \ubb34\ud6a8");
    	this.InitAbility(12, 0, true);
    	this.RegisterLeftClickEvent();
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        if (this.PlayerCheck(Event.getPlayer()) && this.ItemCheck(Material.GOLD_INGOT.getId())) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        final Player p = Event.getPlayer();
        final World w = p.getWorld();
        final Random r = new Random();
        final int a = r.nextInt(10);
        if (a == 1 || a == 3 || a == 5 || a == 7 || a == 9) {
            p.sendMessage(PhysicalFighters.a + "포츈코인이 §e앞면§f이 나왔습니다! §b(체력 회복)");
            if (p.getHealth() >= 16) {
                p.setHealth(20);
            } else {
                p.setHealth(p.getHealth() + 4);
            }
        }
        else if (a == 2 || a == 6 || a == 8) {
            p.sendMessage(PhysicalFighters.a + "포츈코인이 §e뒷면§f이 나왔습니다! §b(체력 감소)");
            w.strikeLightningEffect(p.getLocation());
            if (p.getHealth() <= 4) {
                p.setHealth(0);
            } else {
                p.setHealth(p.getHealth() - 4);
            }
        } else {
            p.sendMessage(PhysicalFighters.a + "포츈코인이 §e땅§f에 박혔습니다! §b(무효)");
        }
    }
}
