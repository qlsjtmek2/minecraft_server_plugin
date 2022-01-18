// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class TileEntityMobSpawner extends TileEntity
{
    private final MobSpawnerAbstract a;
    
    public TileEntityMobSpawner() {
        this.a = new MobSpawner(this);
    }
    
    public void a(final NBTTagCompound nbtTagCompound) {
        super.a(nbtTagCompound);
        this.a.a(nbtTagCompound);
    }
    
    public void b(final NBTTagCompound nbtTagCompound) {
        super.b(nbtTagCompound);
        this.a.b(nbtTagCompound);
    }
    
    public void h() {
        this.a.g();
        super.h();
    }
    
    public Packet getUpdatePacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.b(nbtTagCompound);
        nbtTagCompound.remove("SpawnPotentials");
        return new Packet132TileEntityData(this.x, this.y, this.z, 1, nbtTagCompound);
    }
    
    public boolean b(final int n, final int j) {
        return this.a.b(n) || super.b(n, j);
    }
    
    public MobSpawnerAbstract a() {
        return this.a;
    }
}
