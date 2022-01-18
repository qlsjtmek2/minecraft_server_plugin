// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.List;

public class EntitySenses
{
    EntityLiving entity;
    List seenEntities;
    List unseenEntities;
    
    public EntitySenses(final EntityLiving entity) {
        this.seenEntities = new ArrayList();
        this.unseenEntities = new ArrayList();
        this.entity = entity;
    }
    
    public void a() {
        this.seenEntities.clear();
        this.unseenEntities.clear();
    }
    
    public boolean canSee(final Entity entity) {
        if (this.seenEntities.contains(entity)) {
            return true;
        }
        if (this.unseenEntities.contains(entity)) {
            return false;
        }
        this.entity.world.methodProfiler.a("canSee");
        final boolean n = this.entity.n(entity);
        this.entity.world.methodProfiler.b();
        if (n) {
            this.seenEntities.add(entity);
        }
        else {
            this.unseenEntities.add(entity);
        }
        return n;
    }
}
