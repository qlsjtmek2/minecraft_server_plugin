// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EntityGiantZombie extends EntityMonster
{
    public EntityGiantZombie(final World world) {
        super(world);
        this.texture = "/mob/zombie.png";
        this.bI = 0.5f;
        this.height *= 6.0f;
        this.a(this.width * 6.0f, this.length * 6.0f);
    }
    
    public int getMaxHealth() {
        return 100;
    }
    
    public float a(final int i, final int j, final int k) {
        return this.world.q(i, j, k) - 0.5f;
    }
    
    public int c(final Entity entity) {
        return 50;
    }
}
