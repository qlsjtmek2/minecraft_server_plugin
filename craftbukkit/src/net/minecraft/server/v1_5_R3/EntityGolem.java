// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public abstract class EntityGolem extends EntityCreature implements IAnimal
{
    public EntityGolem(final World world) {
        super(world);
    }
    
    protected void a(final float n) {
    }
    
    protected String bb() {
        return "none";
    }
    
    protected String bc() {
        return "none";
    }
    
    protected String bd() {
        return "none";
    }
    
    public int aQ() {
        return 120;
    }
    
    protected boolean isTypeNotPersistent() {
        return false;
    }
}
