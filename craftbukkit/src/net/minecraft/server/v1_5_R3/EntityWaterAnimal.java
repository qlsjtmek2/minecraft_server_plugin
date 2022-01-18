// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public abstract class EntityWaterAnimal extends EntityCreature implements IAnimal
{
    public EntityWaterAnimal(final World world) {
        super(world);
    }
    
    public boolean bf() {
        return true;
    }
    
    public boolean canSpawn() {
        return this.world.b(this.boundingBox);
    }
    
    public int aQ() {
        return 120;
    }
    
    protected boolean isTypeNotPersistent() {
        return true;
    }
    
    protected int getExpValue(final EntityHuman entityHuman) {
        return 1 + this.world.random.nextInt(3);
    }
    
    public void x() {
        int airTicks = this.getAirTicks();
        super.x();
        if (this.isAlive() && !this.a(Material.WATER)) {
            this.setAirTicks(--airTicks);
            if (this.getAirTicks() == -20) {
                this.setAirTicks(0);
                this.damageEntity(DamageSource.DROWN, 2);
            }
        }
        else {
            this.setAirTicks(300);
        }
    }
}
