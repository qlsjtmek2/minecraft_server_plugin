// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.AbilityList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MinerModule.ACC;

public class Door extends AbilityBase
{
    public static Location Aloc;
    
    public Door() {
    	this.InitAbility("\uc5b4\ub514\ub85c\ub4e0\uc9c0 \ubb38", Type.Active_Immediately, Rank.B, "\ucca0\uad34 \uc67c\ud074\ub9ad\uc2dc \uc800\uc7a5\ub41c \uc704\uce58\ub85c \uc774\ub3d9\ub429\ub2c8\ub2e4.", "\ucca0\uad34 \uc624\ub978\ud074\ub9ad\uc2dc \ud604\uc7ac  \uc704\uce58\ub97c \uc800\uc7a5\ud569\ub2c8\ub2e4.");
    	this.InitAbility(50, 0, true);
    	this.RegisterRightClickEvent();
    	this.RegisterLeftClickEvent();
    }
    
    @Override
    public int A_Condition(final Event event, final int CustomData) {
        final PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
        switch (CustomData) {
            case 0: {
                if (!this.PlayerCheck(Event2.getPlayer())) {
                    break;
                }
                if (!this.ItemCheck(ACC.DefaultItem)) {
                    break;
                }
                if (Door.Aloc == null) {
                    this.GetPlayer().sendMessage(PhysicalFighters.w + "저장된 위치가 없어 사용할수 없습니다.");
                    return -1;
                }
                return 1;
            }
            case 1: {
                if (!this.PlayerCheck(Event2.getPlayer())) {
                    break;
                }
                if (!this.ItemCheck(ACC.DefaultItem)) {
                    break;
                }
                final Player p = Event2.getPlayer();
                Door.Aloc = p.getLocation();
                this.GetPlayer().sendMessage(PhysicalFighters.a + "현재 위치를 §e저장 §f하였습니다.");
                return -1;
            }
        }
        return -1;
    }
    
    @Override
    public void A_Effect(final Event event, final int CustomData) {
        final PlayerInteractEvent Event = (PlayerInteractEvent)event;
        final Player p = Event.getPlayer();
        final World w = p.getWorld();
        final Location loc = p.getLocation();
        w.createExplosion(loc, 0.0f);
        this.GetPlayer().teleport(Door.Aloc);
        this.GetPlayer().sendMessage(PhysicalFighters.a + "저장된 위치로 §b이동§f하였습니다.");
    }
    
    @Override
    public void A_SetEvent(final Player p) {
        Door.Aloc = null;
    }
}
