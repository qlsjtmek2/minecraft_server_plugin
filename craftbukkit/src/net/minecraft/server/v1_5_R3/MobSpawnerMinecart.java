// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

class MobSpawnerMinecart extends MobSpawnerAbstract
{
    final /* synthetic */ EntityMinecartMobSpawner a;
    
    MobSpawnerMinecart(final EntityMinecartMobSpawner a) {
        this.a = a;
    }
    
    public void a(final int n) {
        this.a.world.broadcastEntityEffect(this.a, (byte)n);
    }
    
    public World a() {
        return this.a.world;
    }
    
    public int b() {
        return MathHelper.floor(this.a.locX);
    }
    
    public int c() {
        return MathHelper.floor(this.a.locY);
    }
    
    public int d() {
        return MathHelper.floor(this.a.locZ);
    }
}
