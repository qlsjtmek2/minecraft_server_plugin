// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.MinerModule;

import org.bukkit.Material;

import Physical.Fighters.PhysicalFighters;

public final class ACC
{
    public static int DefaultItem;
    public static final String DefaultAbilityUsed;
    public static final String DefaultCoolDownEnd;
    public static final String DefaultDurationEnd;
    public static PhysicalFighters M;
    
    static {
        ACC.DefaultItem = Material.SLIME_BALL.getId();
        DefaultAbilityUsed = PhysicalFighters.a + "능력을 §b사용§f했습니다.";
        DefaultCoolDownEnd = PhysicalFighters.a + "다시 능력을 사용할 수 있습니다.";
        DefaultDurationEnd = PhysicalFighters.a + "§d능력 지속시간§f이 끝났습니다.";
    }
}
