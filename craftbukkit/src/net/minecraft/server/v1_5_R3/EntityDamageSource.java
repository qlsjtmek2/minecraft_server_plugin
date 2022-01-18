// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EntityDamageSource extends DamageSource
{
    protected Entity p;
    
    public EntityDamageSource(final String s, final Entity p2) {
        super(s);
        this.p = p2;
    }
    
    public Entity getEntity() {
        return this.p;
    }
    
    public String getLocalizedDeathMessage(final EntityLiving entityLiving) {
        final ItemStack itemStack = (this.p instanceof EntityLiving) ? ((EntityLiving)this.p).bG() : null;
        final String string = "death.attack." + this.translationIndex;
        final String string2 = string + ".item";
        if (itemStack != null && itemStack.hasName() && LocaleI18n.b(string2)) {
            return LocaleI18n.get(string2, entityLiving.getScoreboardDisplayName(), this.p.getScoreboardDisplayName(), itemStack.getName());
        }
        return LocaleI18n.get(string, entityLiving.getScoreboardDisplayName(), this.p.getScoreboardDisplayName());
    }
    
    public boolean p() {
        return this.p != null && this.p instanceof EntityLiving && !(this.p instanceof EntityHuman);
    }
}
