// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import Physical.Fighters.PhysicalFighters;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.Event;
import Physical.Fighters.MinerModule.EventData;
import Physical.Fighters.Script.MainScripter;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MainModule.AbilityBase;

public class Phoenix extends AbilityBase
{
    private int ReviveCounter;
    private boolean AbilityUse;
    
    public Phoenix() {
        this.ReviveCounter = 0;
        this.AbilityUse = false;
        this.InitAbility("\ubd88\uc0ac\uc870", Type.Passive_Manual, Rank.A, "\uc790\uc5f0\uc0ac\ud560\uc2dc \ubb34\uc81c\ud55c\uc73c\ub85c \uc778\ubca4\ud1a0\ub9ac\ub97c \uc783\uc9c0 \uc54a\uace0 \ubd80\ud65c\ud569\ub2c8\ub2e4.", "\ud0c0\uc778\uc5d0\uac8c \uc0ac\ub9dd\ud560 \uacbd\uc6b0 1\ud68c\uc5d0 \ud55c\ud558\uc5ec \uc790\uc5f0\uc0ac\uc640 \uac19\uc774 \ubd80\ud65c\ud569\ub2c8\ub2e4.", "\ubd80\ud65c\uc2dc \uc790\uc2e0\uc758 \ub2a5\ub825\uc774 \ubaa8\ub450\uc5d0\uac8c \uc54c\ub824\uc9c0\uac8c \ub429\ub2c8\ub2e4.", "\ud558\uc9c0\ub9cc \uc774 \ub2a5\ub825\ub3c4 \ub370\uc2a4\ub178\ud2b8\ub9cc\uc740 \ub9c9\uc744\uc218 \uc5c6\uc744\uac83\uc785\ub2c8\ub2e4.");
        this.InitAbility(0, 0, true);
        EventManager.onEntityDeath.add(new EventData(this, 0));
        EventManager.onPlayerRespawn.add(new EventData(this, 1));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        switch (CustomData) {
            case 0: {
                final EntityDeathEvent Event0 = (EntityDeathEvent)event;
                if (this.PlayerCheck((Entity)Event0.getEntity())) {
                    return 0;
                }
                break;
            }
            case 1: {
                final PlayerRespawnEvent Event2 = (PlayerRespawnEvent)event;
                if (this.PlayerCheck(Event2.getPlayer())) {
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
                final PlayerDeathEvent Event0 = (PlayerDeathEvent)event;
                final Player killed = Event0.getEntity();
                EventManager.invsave.put(killed, killed.getInventory().getContents());
                EventManager.arsave.put(killed, killed.getInventory().getArmorContents());
                Event0.getDrops().clear();
                if (this.AbilityUse) {
                    for (Player player : MainScripter.PlayerList)
                    	player.sendMessage(PhysicalFighters.p + "§c불사조§6가 죽었습니다. 더 이상 부활할수 없습니다.");
                } else {
                    for (Player player : MainScripter.PlayerList)
                    	player.sendMessage(PhysicalFighters.p + "§c불사조§6가 죽었습니다. 다시 부활할 수 있습니다.");
                }
                if (killed.getKiller() instanceof Player) {
                    this.AbilityUse = true;
                }
                ++this.ReviveCounter;
                break;
            }
            case 1: {
                final PlayerRespawnEvent Event2 = (PlayerRespawnEvent)event;
                final ItemStack[] ar = EventManager.arsave.get(Event2.getPlayer());
                final ItemStack[] inv = EventManager.invsave.get(Event2.getPlayer());
                if (ar != null) {
                    Event2.getPlayer().getInventory().setArmorContents(ar);
                }
                if (inv != null) {
                    Event2.getPlayer().getInventory().setContents(inv);
                }
                EventManager.arsave.remove(Event2.getPlayer());
                EventManager.invsave.remove(Event2.getPlayer());
                if (!this.AbilityUse) {
                    for (Player player : MainScripter.PlayerList)
                    	player.sendMessage(PhysicalFighters.p + "불사조가 §c부활§6하였습니다. 부활 횟수: §c" + String.valueOf(this.ReviveCounter) + "회");
                }
                Event2.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 600, 0), true);
                Event2.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 0), true);
                Event2.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 600, 0), true);
                Event2.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 0), true);
                Event2.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 600, 0), true);
                Event2.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 0), true);
                Event2.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 0), true);
                break;
            }
        }
    }
    
    @Override
    public void A_SetEvent(final Player p) {
        this.ReviveCounter = 0;
        this.AbilityUse = false;
    }
}
