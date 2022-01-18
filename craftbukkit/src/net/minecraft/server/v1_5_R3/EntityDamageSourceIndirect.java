// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EntityDamageSourceIndirect extends EntityDamageSource
{
    private Entity owner;
    
    public EntityDamageSourceIndirect(final String s, final Entity entity, final Entity entity1) {
        super(s, entity);
        this.owner = entity1;
    }
    
    public Entity h() {
        return this.p;
    }
    
    public Entity getEntity() {
        return this.owner;
    }
    
    public String getLocalizedDeathMessage(final EntityLiving entityliving) {
        final String s = (this.owner == null) ? this.p.getScoreboardDisplayName() : this.owner.getScoreboardDisplayName();
        final ItemStack itemstack = (this.owner instanceof EntityLiving) ? ((EntityLiving)this.owner).bG() : null;
        final String s2 = "death.attack." + this.translationIndex;
        final String s3 = s2 + ".item";
        return (itemstack != null && itemstack.hasName() && LocaleI18n.b(s3)) ? LocaleI18n.get(s3, entityliving.getScoreboardDisplayName(), s, itemstack.getName()) : LocaleI18n.get(s2, entityliving.getScoreboardDisplayName(), s);
    }
    
    public Entity getProximateDamageSource() {
        return super.getEntity();
    }
}
