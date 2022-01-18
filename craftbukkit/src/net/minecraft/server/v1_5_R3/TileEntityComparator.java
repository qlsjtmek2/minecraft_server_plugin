// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class TileEntityComparator extends TileEntity
{
    private int a;
    
    public TileEntityComparator() {
        this.a = 0;
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("OutputSignal", this.a);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a = nbttagcompound.getInt("OutputSignal");
    }
    
    public int a() {
        return this.a;
    }
    
    public void a(final int a) {
        this.a = a;
    }
}
