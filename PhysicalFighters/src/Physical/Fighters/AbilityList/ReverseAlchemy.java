// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.Material;
import Physical.Fighters.MinerModule.ACC;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Event;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;

public class ReverseAlchemy extends AbilityBase
{
    public ReverseAlchemy() {
        this.InitAbility("\ubc18 \uc5f0\uae08\uc220", Type.Active_Immediately, Rank.A, "\uae08\uad34\ub97c \ub4e4\uace0 \uc624\ub978\ud074\ub9ad\uc2dc \uae08\uad34\ub97c \ud558\ub098 \uc18c\ubaa8\ud558\uc5ec \uc790\uc2e0\uc758 \uccb4\ub825\uc744 \ubc18 \ucc44\uc6cc\uc90d\ub2c8\ub2e4.");
        this.InitAbility(5, 0, true, ShowText.No_Text);
        this.RegisterRightClickEvent();
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        if (this.PlayerCheck(Event.getPlayer())) {
            final PlayerInventory inv = Event.getPlayer().getInventory();
            if (this.ItemCheck(ACC.DefaultItem)) {
                switch (CustomData) {
                    case 1: {
                        if (inv.contains(Material.GOLD_INGOT, 3)) {
                            return CustomData;
                        }
                        break;
                    }
                }
                Event.getPlayer().sendMessage(PhysicalFighters.w + "§c금괴가 부족합니다.");
            }
            else if (this.ItemCheck(Material.GOLD_INGOT.getId()) && inv.contains(Material.GOLD_INGOT, 1) && CustomData == 1) {
                return 2;
            }
        }
        return -1;
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        switch (CustomData) {
            case 1: {
                Event.getPlayer().getInventory().removeItem(new ItemStack[] { new ItemStack(Material.GOLD_INGOT.getId(), 1) });
                this.GiveItem(Event.getPlayer(), Material.GOLD_INGOT, 1);
                break;
            }
            case 2: {
                Event.getPlayer().getInventory().removeItem(new ItemStack[] { new ItemStack(Material.GOLD_INGOT.getId(), 1) });
                if (Event.getPlayer().getHealth() >= 10) {
                    Event.getPlayer().setHealth(20);
                    break;
                }
                Event.getPlayer().setHealth(Event.getPlayer().getHealth() + 10);
                Event.getPlayer().setSaturation(5.0f);
                break;
            }
        }
        Event.getPlayer().updateInventory();
    }
    
    private void GiveItem(final Player p, final Material item, final int amount) {
        final PlayerInventory inv = p.getInventory();
        final Map<Integer, ItemStack> overflow = (Map<Integer, ItemStack>)inv.addItem(new ItemStack[] { new ItemStack(item, amount) });
        for (final ItemStack is : overflow.values()) {
            p.getWorld().dropItemNaturally(p.getLocation(), is);
            p.sendMessage(PhysicalFighters.w + "인벤토리 공간이 부족합니다.");
        }
    }
}
