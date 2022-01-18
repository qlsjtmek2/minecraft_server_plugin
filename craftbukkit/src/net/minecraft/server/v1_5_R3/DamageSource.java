// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class DamageSource
{
    public static DamageSource FIRE;
    public static DamageSource BURN;
    public static DamageSource LAVA;
    public static DamageSource STUCK;
    public static DamageSource DROWN;
    public static DamageSource STARVE;
    public static DamageSource CACTUS;
    public static DamageSource FALL;
    public static DamageSource OUT_OF_WORLD;
    public static DamageSource GENERIC;
    public static DamageSource MAGIC;
    public static DamageSource WITHER;
    public static DamageSource ANVIL;
    public static DamageSource FALLING_BLOCK;
    private boolean p;
    private boolean q;
    private float r;
    private boolean s;
    private boolean t;
    private boolean u;
    private boolean v;
    private boolean w;
    public String translationIndex;
    
    public static DamageSource mobAttack(final EntityLiving entityLiving) {
        return new EntityDamageSource("mob", entityLiving);
    }
    
    public static DamageSource playerAttack(final EntityHuman entityHuman) {
        return new EntityDamageSource("player", entityHuman);
    }
    
    public static DamageSource arrow(final EntityArrow entity, final Entity entity2) {
        return new EntityDamageSourceIndirect("arrow", entity, entity2).b();
    }
    
    public static DamageSource fireball(final EntityFireball entity, final Entity entity2) {
        if (entity2 == null) {
            return new EntityDamageSourceIndirect("onFire", entity, entity).l().b();
        }
        return new EntityDamageSourceIndirect("fireball", entity, entity2).l().b();
    }
    
    public static DamageSource projectile(final Entity entity, final Entity entity2) {
        return new EntityDamageSourceIndirect("thrown", entity, entity2).b();
    }
    
    public static DamageSource b(final Entity entity, final Entity entity2) {
        return new EntityDamageSourceIndirect("indirectMagic", entity, entity2).j().r();
    }
    
    public static DamageSource a(final Entity entity) {
        return new EntityDamageSource("thorns", entity).r();
    }
    
    public static DamageSource explosion(final Explosion explosion) {
        if (explosion != null && explosion.c() != null) {
            return new EntityDamageSource("explosion.player", explosion.c()).o().d();
        }
        return new DamageSource("explosion").o().d();
    }
    
    public boolean a() {
        return this.t;
    }
    
    public DamageSource b() {
        this.t = true;
        return this;
    }
    
    public boolean c() {
        return this.w;
    }
    
    public DamageSource d() {
        this.w = true;
        return this;
    }
    
    public boolean ignoresArmor() {
        return this.p;
    }
    
    public float f() {
        return this.r;
    }
    
    public boolean ignoresInvulnerability() {
        return this.q;
    }
    
    protected DamageSource(final String translationIndex) {
        this.p = false;
        this.q = false;
        this.r = 0.3f;
        this.v = false;
        this.w = false;
        this.translationIndex = translationIndex;
    }
    
    public Entity h() {
        return this.getEntity();
    }
    
    public Entity getEntity() {
        return null;
    }
    
    protected DamageSource j() {
        this.p = true;
        this.r = 0.0f;
        return this;
    }
    
    protected DamageSource k() {
        this.q = true;
        return this;
    }
    
    protected DamageSource l() {
        this.s = true;
        return this;
    }
    
    public String getLocalizedDeathMessage(final EntityLiving entityLiving) {
        final EntityLiving bn = entityLiving.bN();
        final String string = "death.attack." + this.translationIndex;
        final String string2 = string + ".player";
        if (bn != null && LocaleI18n.b(string2)) {
            return LocaleI18n.get(string2, entityLiving.getScoreboardDisplayName(), bn.getScoreboardDisplayName());
        }
        return LocaleI18n.get(string, entityLiving.getScoreboardDisplayName());
    }
    
    public boolean m() {
        return this.s;
    }
    
    public String n() {
        return this.translationIndex;
    }
    
    public DamageSource o() {
        this.u = true;
        return this;
    }
    
    public boolean p() {
        return this.u;
    }
    
    public boolean q() {
        return this.v;
    }
    
    public DamageSource r() {
        this.v = true;
        return this;
    }
    
    static {
        DamageSource.FIRE = new DamageSource("inFire").l();
        DamageSource.BURN = new DamageSource("onFire").j().l();
        DamageSource.LAVA = new DamageSource("lava").l();
        DamageSource.STUCK = new DamageSource("inWall").j();
        DamageSource.DROWN = new DamageSource("drown").j();
        DamageSource.STARVE = new DamageSource("starve").j();
        DamageSource.CACTUS = new DamageSource("cactus");
        DamageSource.FALL = new DamageSource("fall").j();
        DamageSource.OUT_OF_WORLD = new DamageSource("outOfWorld").j().k();
        DamageSource.GENERIC = new DamageSource("generic").j();
        DamageSource.MAGIC = new DamageSource("magic").j().r();
        DamageSource.WITHER = new DamageSource("wither").j();
        DamageSource.ANVIL = new DamageSource("anvil");
        DamageSource.FALLING_BLOCK = new DamageSource("fallingBlock");
    }
}
