// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public abstract class WorldMapBase
{
    public final String id;
    private boolean a;
    
    public WorldMapBase(final String id) {
        this.id = id;
    }
    
    public abstract void a(final NBTTagCompound p0);
    
    public abstract void b(final NBTTagCompound p0);
    
    public void c() {
        this.a(true);
    }
    
    public void a(final boolean a) {
        this.a = a;
    }
    
    public boolean d() {
        return this.a;
    }
}
