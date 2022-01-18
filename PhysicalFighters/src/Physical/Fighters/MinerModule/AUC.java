// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.MinerModule;

import org.bukkit.ChatColor;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MainModule.AbilityBase;
import Physical.Fighters.MajorModule.AbilityList;
import org.bukkit.entity.Player;

public final class AUC
{
	public static PhysicalFighters M;
	
    public static final void InfoTextOut(final Player p) {
        AbilityBase a;
        if (AbilityList.assimilation.GetPlayer() == p) {
            a = AbilityList.assimilation;
        }
        else {
            a = AbilityBase.FindAbility(p);
        }
        if (a != null) {
            p.sendMessage(ChatColor.GOLD + "당신의 능력 정보");
            p.sendMessage(ChatColor.AQUA + a.GetAbilityName() + ChatColor.WHITE + " [" + TypeTextOut(a) + "] " + a.GetRank().GetText());
            for (int l = 0; l < a.GetGuide().length; ++l) {
                p.sendMessage(a.GetGuide()[l]);
            }
            p.sendMessage(TimerTextOut(a));
            return;
        }
        p.sendMessage(PhysicalFighters.w + "능력이 없거나 옵저버입니다.");
    }
    
    public static final String TypeTextOut(final AbilityBase ab) {
        final AbilityBase.Type type = ab.GetAbilityType();
        if (!ab.GetRunAbility()) {
            return ChatColor.GRAY + "\ub2a5\ub825 \ube44\ud65c\uc131\ud654\ub428" + ChatColor.WHITE;
        }
        if (type == AbilityBase.Type.Active_Continue) {
            return ChatColor.RED + "Active " + ChatColor.WHITE + "/ " + ChatColor.GOLD + "\uc9c0\uc18d" + ChatColor.WHITE;
        }
        if (type == AbilityBase.Type.Active_Immediately) {
            return ChatColor.RED + "Active " + ChatColor.WHITE + "/ " + ChatColor.GOLD + "\uc989\ubc1c" + ChatColor.WHITE;
        }
        if (type == AbilityBase.Type.Passive_AutoMatic) {
            return ChatColor.GREEN + "Passive " + ChatColor.WHITE + "/ " + ChatColor.GOLD + "\uc790\ub3d9" + ChatColor.WHITE;
        }
        if (type == AbilityBase.Type.Passive_Manual) {
            return ChatColor.GREEN + "Passive " + ChatColor.WHITE + "/ " + ChatColor.GOLD + "\uc218\ub3d9" + ChatColor.WHITE;
        }
        return "Unknown";
    }
    
    public static final String TimerTextOut(final AbilityBase data) {
        if (data.GetAbilityType() == AbilityBase.Type.Active_Continue) {
            return String.format(ChatColor.RED + "\ucfe8\ud0c0\uc784 : " + ChatColor.WHITE + "%d\ucd08 / " + ChatColor.RED + "\uc9c0\uc18d\uc2dc\uac04 : " + ChatColor.WHITE + "%d\ucd08", data.GetCoolDown(), data.GetDuration());
        }
        if (data.GetAbilityType() == AbilityBase.Type.Active_Immediately) {
            return String.format(ChatColor.RED + "\ucfe8\ud0c0\uc784 : " + ChatColor.WHITE + "%d\ucd08 / " + ChatColor.RED + "\uc9c0\uc18d\uc2dc\uac04 : " + ChatColor.WHITE + "\uc5c6\uc74c", data.GetCoolDown());
        }
        if (data.GetAbilityType() == AbilityBase.Type.Passive_AutoMatic) {
            return ChatColor.RED + "\ucfe8\ud0c0\uc784 : " + ChatColor.WHITE + "\uc5c6\uc74c / " + ChatColor.RED + "\uc9c0\uc18d\uc2dc\uac04 : " + ChatColor.WHITE + "\uc5c6\uc74c";
        }
        if (data.GetAbilityType() == AbilityBase.Type.Passive_Manual) {
            return ChatColor.RED + "\ucfe8\ud0c0\uc784 : " + ChatColor.WHITE + "\uc5c6\uc74c / " + ChatColor.RED + "\uc9c0\uc18d\uc2dc\uac04 : " + ChatColor.WHITE + "\uc5c6\uc74c";
        }
        return "None";
    }
}
