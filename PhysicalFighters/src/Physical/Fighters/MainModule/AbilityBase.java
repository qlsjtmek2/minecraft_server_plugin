// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.MainModule;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MajorModule.AbilityList;
import Physical.Fighters.MajorModule.CoolDownTimer;
import Physical.Fighters.MajorModule.DurationTimer;
import Physical.Fighters.MajorModule.RestrictionTimer;
import Physical.Fighters.MinerModule.ACC;

public abstract class AbilityBase
{
    protected static CommandManager cm;
    protected static PhysicalFighters va;
    protected static int AbilityCount;
    public static final RestrictionTimer restrictionTimer;
    private Rank rank;
    private Player player;
    private String AbilityName;
    private Type type;
    private String[] Guide;
    private int CoolDown;
    private int Duration;
    private CoolDownTimer CTimer;
    private DurationTimer DTimer;
    private boolean RunAbility;
    private ShowText showtext;
    PhysicalFighters M;
    
    static {
        AbilityBase.AbilityCount = 0;
        restrictionTimer = new RestrictionTimer();
    }
    
    public AbilityBase() {
        this.CoolDown = 0;
        this.Duration = 0;
        this.RunAbility = true;
        this.showtext = ShowText.All_Text;
    }
    
    public abstract int A_Condition(final Event p0, final int p1);
    
    public abstract void A_Effect(final Event p0, final int p1);
    
    public void A_CoolDownStart() {
    }
    
    public void A_CoolDownEnd() {
    }
    
    public void A_DurationStart() {
    }
    
    public void A_DurationEnd() {
    }
    
    public void A_FinalDurationEnd() {
    }
    
    public void A_SetEvent(final Player p) {
    }
    
    public void A_ResetEvent(final Player p) {
    }
    
    public final void RegisterLeftClickEvent() {
        EventManager.LeftHandEvent.add(this);
    }
    
    public final void RegisterRightClickEvent() {
        EventManager.RightHandEvent.add(this);
    }
    
    public final Player GetPlayer() {
        return this.player;
    }
    
    @SuppressWarnings("static-access")
	public final void SetPlayer(final Player p, final boolean textout) {
        this.DTimer.StopTimer();
        this.CTimer.StopTimer();
        if (this.player != null) {
            if (textout) {
                this.player.sendMessage(String.format(va.a + "§a%s" + ChatColor.WHITE + " 능력이 해제되었습니다.", new Object[] { GetAbilityName() }));
            }
            this.A_ResetEvent(this.player);
        }
        if (p != null && this.RunAbility) {
            if (textout) {
                p.sendMessage(String.format(va.a + "§a%s" + ChatColor.WHITE + " 능력이 설정되었습니다.", new Object[] { GetAbilityName() }));
            }
            this.A_SetEvent(p);
        }
        this.player = p;
    }
    
    public final String GetAbilityName() {
        return this.AbilityName;
    }
    
    public final Type GetAbilityType() {
        return (this.type == null) ? null : this.type;
    }
    
    public final String[] GetGuide() {
        return this.Guide;
    }
    
    public final int GetCoolDown() {
        return this.CoolDown;
    }
    
    public final Rank GetRank() {
        return this.rank;
    }
    
    public final int GetDuration() {
        return this.Duration;
    }
    
    public final boolean GetDurationState() {
        return this.DTimer.GetTimerRunning();
    }
    
    public final void SetRunAbility(final boolean RunAbility) {
        this.RunAbility = RunAbility;
    }
    
    public final boolean GetRunAbility() {
        return this.RunAbility;
    }
    
    public final ShowText GetShowText() {
        return this.showtext;
    }
    
    public final void AbilityDTimerCancel() {
        this.DTimer.StopTimer();
    }
    
    public final void AbilityCTimerCancel() {
        this.CTimer.StopTimer();
    }
    
    public final boolean PlayerCheck(final Player p) {
        if (this.player != null && p.getName().equalsIgnoreCase(this.player.getName()) && (this.player.isDead() || Bukkit.getServer().getPlayerExact(this.player.getName()) != null)) {
            this.player = p;
            return true;
        }
        return false;
    }
    
    public final boolean PlayerCheck(final Entity e) {
        return e instanceof Player && e != null && this.PlayerCheck((Player)e);
    }
    
    public final boolean ItemCheck(final int itemID) {
        return this.GetPlayer().getItemInHand().getType().getId() == itemID;
    }
    
    public final boolean AbilityExcute(final Event event) {
        return this.AbilityExcute(event, 0);
    }
    
    @SuppressWarnings("static-access")
	public final boolean AbilityExcute(final Event event, final int CustomData) {
        if (this.player != null && this.RunAbility) {
            final int cd = this.A_Condition(event, CustomData);
            if (cd == -2) {
                return true;
            }
            if (cd != -1) {
                if (this.type == Type.Active_Continue || this.type == Type.Active_Immediately) {
                    if (this.DTimer.GetTimerRunning()) {
                        this.GetPlayer().sendMessage(String.format(M.a + "§c%d초" + ChatColor.GREEN + " §f후 §a지속시간§f이 끝납니다.", this.DTimer.GetCount()));
                        return true;
                    }
                    if (this.CTimer.GetTimerRunning()) {
                        this.GetPlayer().sendMessage(String.format(M.a + "§c%d초" + ChatColor.RED + " §f후 §b능력§f을 다시 사용하실 수 있습니다.", this.CTimer.GetCount()));
                        return true;
                    }
                    if (this.GetShowText() != ShowText.Custom_Text) {
                        this.GetPlayer().sendMessage(ACC.DefaultAbilityUsed);
                    }
                }
                if (this.type == Type.Active_Immediately) {
                    this.A_Effect(event, cd);
                    if (this.GetCoolDown() != 0) {
                        this.CTimer.StartTimer(this.GetCoolDown(), true);
                    }
                }
                else if (this.type == Type.Active_Continue) {
                    this.DTimer.StartTimer(this.GetDuration(), true);
                }
                else {
                    this.A_Effect(event, cd);
                }
                return true;
            }
        }
        return false;
    }
    
    public final int goPlayerVelocity(final Player p1, final Player p2, final int value) {
        p1.setVelocity(p1.getVelocity().add(p2.getLocation().toVector().subtract(p1.getLocation().toVector()).normalize().multiply(value)));
        return 0;
    }
    
    public final int goVelocity(final Player p1, final Location lo, final int value) {
        p1.setVelocity(p1.getVelocity().add(lo.toVector().subtract(p1.getLocation().toVector()).normalize().multiply(value)));
        return 0;
    }
    
    public static final int ArrowVelocity(final Arrow a, final Location lo, final int value) {
        a.setVelocity(a.getVelocity().add(lo.toVector().subtract(a.getLocation().toVector()).normalize().multiply(value)));
        return 0;
    }
    
    public static final int Direction(final Player p) {
        final Location loc = p.getLocation();
        final Location loc2 = p.getTargetBlock((HashSet<Byte>)null, 0).getLocation();
        final int x = (int)Math.abs(Math.abs(loc.getX()) - Math.abs(loc2.getX()));
        final int z = (int)Math.abs(Math.abs(loc.getZ()) - Math.abs(loc2.getZ()));
        if (loc == loc2) {
            return 10;
        }
        if (x > z) {
            if (loc.getX() > loc2.getX()) {
                return 1;
            }
            return 3;
        }
        else {
            if (loc.getZ() > loc2.getZ()) {
                return 2;
            }
            return 4;
        }
    }
    
    public static final int LookAngle(final Location l, final Location l2, final int value) {
        final double degrees = Math.toRadians(-(l.getYaw() % 360.0f));
        final double ydeg = Math.toRadians(-(l.getPitch() % 360.0f));
        l2.setX(l.getX() + (2 * value + 1) * (Math.sin(degrees) * Math.cos(ydeg)));
        l2.setY(l.getY() + (2 * value + 1) * Math.sin(ydeg));
        l2.setZ(l.getZ() + (2 * value + 1) * (Math.cos(degrees) * Math.cos(ydeg)));
        return 0;
    }
    
    public final boolean AbilityDuratinEffect(final Event event) {
        return this.AbilityDuratinEffect(event, 0);
    }
    
    public final boolean AbilityDuratinEffect(final Event event, final int CustomData) {
        if (this.player != null && this.DTimer.GetTimerRunning()) {
            this.A_Effect(event, CustomData);
            return true;
        }
        return false;
    }
    
    protected final void InitAbility(final String AbilityName, final Type type, final Rank rank, final String... Manual) {
        this.AbilityName = AbilityName;
        this.type = type;
        this.Guide = new String[Manual.length];
        for (int loop = 0; loop < Manual.length; ++loop) {
            this.Guide[loop] = Manual[loop];
        }
        this.CTimer = new CoolDownTimer(this);
        this.DTimer = new DurationTimer(this, this.CTimer);
        this.rank = rank;
    }
    
    protected final void InitAbility(final int CoolDown, final int Duration, final boolean RunAbility) {
        this.InitAbility(CoolDown, Duration, RunAbility, ShowText.All_Text);
    }
    
    protected final void InitAbility(final int CoolDown, final int Duration, final boolean RunAbility, final ShowText showtext) {
        this.CoolDown = ((this.type == Type.Active_Continue || this.type == Type.Active_Immediately) ? CoolDown : -1);
        this.Duration = ((this.type == Type.Active_Continue) ? Duration : -1);
        this.RunAbility = RunAbility;
        this.showtext = showtext;
        AbilityList.AbilityList.add(this);
        ++AbilityBase.AbilityCount;
    }
    
    public static final int GetAbilityCount() {
        return AbilityBase.AbilityCount;
    }
    
    public static final AbilityBase FindAbility(final Player p) {
        for (final AbilityBase a : AbilityList.AbilityList) {
            if (a.PlayerCheck(p)) {
                return a;
            }
        }
        return null;
    }
    
    public static final AbilityBase FindAbility(final String name) {
        for (final AbilityBase a : AbilityList.AbilityList) {
            if (a.GetAbilityName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }
    
    public static final void InitAbilityBase(final PhysicalFighters va, final CommandManager cm) {
        AbilityBase.va = va;
        AbilityBase.cm = cm;
    }
    
    public enum Rank
    {
        SSS("SSS", 0, ChatColor.GOLD + "Special Rank"), 
        SS("SS", 1, ChatColor.GRAY + "SS Rank"), 
        S("S", 2, ChatColor.RED + "S Rank"), 
        A("A", 3, ChatColor.GREEN + "A Rank"), 
        B("B", 4, ChatColor.BLUE + "B Rank"), 
        C("C", 5, ChatColor.YELLOW + "C Rank"), 
        F("F", 6, ChatColor.BLACK + "F Rank"), 
        FF("FF", 7, ChatColor.BLACK + "\uac1c\uac00 \uc2f8\ub193\uc740 \ub625"), 
        GOD("GOD", 8, ChatColor.WHITE + "\uc2e0");
        
        private String s;
        
        private Rank(final String s2, final int n, final String s) {
            this.s = s;
        }
        
        public String GetText() {
            return this.s;
        }
    }
    
    public enum ShowText
    {
        All_Text("All_Text", 0), 
        No_CoolDownText("No_CoolDownText", 1), 
        No_DurationText("No_DurationText", 2), 
        No_Text("No_Text", 3), 
        Custom_Text("Custom_Text", 4);
        
        private ShowText(final String s, final int n) {
        }
    }
    
    public enum Type
    {
        Passive_AutoMatic("Passive_AutoMatic", 0), 
        Passive_Manual("Passive_Manual", 1), 
        Active_Immediately("Active_Immediately", 2), 
        Active_Continue("Active_Continue", 3);
        
        private Type(final String s, final int n) {
        }
    }
}
