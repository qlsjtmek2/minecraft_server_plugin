// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EntitySelectorEquipable implements IEntitySelector
{
    private final ItemStack c;
    
    public EntitySelectorEquipable(final ItemStack c) {
        this.c = c;
    }
    
    public boolean a(final Entity entity) {
        if (!entity.isAlive()) {
            return false;
        }
        if (!(entity instanceof EntityLiving)) {
            return false;
        }
        final EntityLiving entityLiving = (EntityLiving)entity;
        return entityLiving.getEquipment(EntityLiving.b(this.c)) == null && (entityLiving.bT() || entityLiving instanceof EntityHuman);
    }
}
