// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public interface IBlockAccess
{
    int getTypeId(final int p0, final int p1, final int p2);
    
    TileEntity getTileEntity(final int p0, final int p1, final int p2);
    
    int getData(final int p0, final int p1, final int p2);
    
    Material getMaterial(final int p0, final int p1, final int p2);
    
    boolean u(final int p0, final int p1, final int p2);
    
    Vec3DPool getVec3DPool();
    
    int getBlockPower(final int p0, final int p1, final int p2, final int p3);
}
