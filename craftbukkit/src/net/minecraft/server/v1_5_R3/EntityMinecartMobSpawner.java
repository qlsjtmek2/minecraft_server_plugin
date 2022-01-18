// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EntityMinecartMobSpawner extends EntityMinecartAbstract
{
    private final MobSpawnerAbstract a;
    
    public EntityMinecartMobSpawner(final World world) {
        super(world);
        this.a = new MobSpawnerMinecart(this);
    }
    
    public EntityMinecartMobSpawner(final World world, final double d0, final double d2, final double d3) {
        super(world, d0, d2, d3);
        this.a = new MobSpawnerMinecart(this);
    }
    
    public int getType() {
        return 4;
    }
    
    public Block n() {
        return Block.MOB_SPAWNER;
    }
    
    protected void a(final NBTTagCompound nbtTagCompound) {
        super.a(nbtTagCompound);
        this.a.a(nbtTagCompound);
    }
    
    protected void b(final NBTTagCompound nbtTagCompound) {
        super.b(nbtTagCompound);
        this.a.b(nbtTagCompound);
    }
    
    public void l_() {
        super.l_();
        this.a.g();
    }
}
