// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

final class EntitySelectorNotUndead implements IEntitySelector
{
    public boolean a(final Entity entity) {
        return entity instanceof EntityLiving && ((EntityLiving)entity).getMonsterType() != EnumMonsterType.UNDEAD;
    }
}
