// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MainModule.EventManager;
import Physical.Fighters.MinerModule.ACC;
import Physical.Fighters.MinerModule.EventData;

public class Zoro extends AbilityBase
{
    int dama;
    
    public Zoro() {
        this.dama = 0;
        this.InitAbility("\uc870\ub85c", Type.Active_Immediately, Rank.S, "\ucca0\uad34 \uc67c\ucabd\ud074\ub9ad\uc2dc \uce7c\uc758 \ub370\ubbf8\uc9c0\uac00 \ub79c\ub364\uc73c\ub85c \uc124\uc815\ub429\ub2c8\ub2e4.");
        this.InitAbility(45, 0, true);
        this.RegisterLeftClickEvent();
        EventManager.onEntityDamageByEntity.add(new EventData(this, 1));
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        if (CustomData == 1) {
            final EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
            if (this.PlayerCheck(Event.getDamager()) && (this.ItemCheck(Material.DIAMOND_SWORD.getId()) || this.ItemCheck(Material.WOOD_SWORD.getId()) || this.ItemCheck(Material.IRON_SWORD.getId()) || this.ItemCheck(Material.GOLD_SWORD.getId()))) {
                if (this.dama != 0) {
                    ((Player)Event.getEntity()).damage(this.dama);
                }
                else {
                    this.dama = Event.getDamage();
                }
            }
        }
        else if (CustomData == 0) {
            final PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
            if (this.PlayerCheck(Event2.getPlayer()) && this.ItemCheck(ACC.DefaultItem)) {
                return 0;
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        int rand;
        Random random;
        for (rand = 0, random = new Random(), rand = random.nextInt(10); rand < 5; rand = random.nextInt(10)) {}
        this.dama = rand;
        Event.getPlayer().sendMessage(PhysicalFighters.a + "§c데미지§f가 §e" + this.dama + "§f로 설정되었습니다.");
    }
}
