// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EntityMinecartRideable extends EntityMinecartAbstract
{
    public EntityMinecartRideable(final World world) {
        super(world);
    }
    
    public EntityMinecartRideable(final World world, final double d0, final double d2, final double d3) {
        super(world, d0, d2, d3);
    }
    
    public boolean a_(final EntityHuman entityHuman) {
        if (this.passenger != null && this.passenger instanceof EntityHuman && this.passenger != entityHuman) {
            return true;
        }
        if (this.passenger != null && this.passenger != entityHuman) {
            return false;
        }
        if (!this.world.isStatic) {
            entityHuman.mount(this);
        }
        return true;
    }
    
    public int getType() {
        return 0;
    }
}
