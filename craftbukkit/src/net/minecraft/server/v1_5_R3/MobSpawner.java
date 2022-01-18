// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

class MobSpawner extends MobSpawnerAbstract
{
    final /* synthetic */ TileEntityMobSpawner a;
    
    MobSpawner(final TileEntityMobSpawner a) {
        this.a = a;
    }
    
    public void a(final int i1) {
        this.a.world.playNote(this.a.x, this.a.y, this.a.z, Block.MOB_SPAWNER.id, i1, 0);
    }
    
    public World a() {
        return this.a.world;
    }
    
    public int b() {
        return this.a.x;
    }
    
    public int c() {
        return this.a.y;
    }
    
    public int d() {
        return this.a.z;
    }
    
    public void a(final TileEntityMobSpawnerData tileentitymobspawnerdata) {
        super.a(tileentitymobspawnerdata);
        if (this.a() != null) {
            this.a().notify(this.a.x, this.a.y, this.a.z);
        }
    }
}
