// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EntityMinecartChest extends EntityMinecartContainer
{
    public EntityMinecartChest(final World world) {
        super(world);
    }
    
    public EntityMinecartChest(final World world, final double d0, final double d2, final double d3) {
        super(world, d0, d2, d3);
    }
    
    public void a(final DamageSource damagesource) {
        super.a(damagesource);
        this.a(Block.CHEST.id, 1, 0.0f);
    }
    
    public int getSize() {
        return 27;
    }
    
    public int getType() {
        return 1;
    }
    
    public Block n() {
        return Block.CHEST;
    }
    
    public int r() {
        return 8;
    }
}
