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
        DefaultAbilityUsed = PhysicalFighters.a + "�ɷ��� ��b����f�߽��ϴ�.";
        DefaultCoolDownEnd = PhysicalFighters.a + "�ٽ� �ɷ��� ����� �� �ֽ��ϴ�.";
        DefaultDurationEnd = PhysicalFighters.a + "��d�ɷ� ���ӽð���f�� �������ϴ�.";
    }
}
