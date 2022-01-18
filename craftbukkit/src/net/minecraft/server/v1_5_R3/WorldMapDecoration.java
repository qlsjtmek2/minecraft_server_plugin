// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class WorldMapDecoration
{
    public byte type;
    public byte locX;
    public byte locY;
    public byte rotation;
    final /* synthetic */ WorldMap e;
    
    public WorldMapDecoration(final WorldMap e, final byte type, final byte locX, final byte locY, final byte rotation) {
        this.e = e;
        this.type = type;
        this.locX = locX;
        this.locY = locY;
        this.rotation = rotation;
    }
}
