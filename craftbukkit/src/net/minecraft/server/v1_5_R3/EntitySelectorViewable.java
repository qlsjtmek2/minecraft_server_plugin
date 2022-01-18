// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

class EntitySelectorViewable implements IEntitySelector
{
    final /* synthetic */ PathfinderGoalAvoidPlayer c;
    
    EntitySelectorViewable(final PathfinderGoalAvoidPlayer c) {
        this.c = c;
    }
    
    public boolean a(final Entity entity) {
        return entity.isAlive() && this.c.b.getEntitySenses().canSee(entity);
    }
}
