// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public interface ISourceBlock extends ILocationSource
{
    double getX();
    
    double getY();
    
    double getZ();
    
    int getBlockX();
    
    int getBlockY();
    
    int getBlockZ();
    
    int h();
    
    TileEntity getTileEntity();
}
