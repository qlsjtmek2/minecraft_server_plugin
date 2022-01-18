// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ChunkCoordinatesPortal extends ChunkCoordinates
{
    public long d;
    final /* synthetic */ PortalTravelAgent e;
    
    public ChunkCoordinatesPortal(final PortalTravelAgent e, final int n, final int n2, final int n3, final long d) {
        this.e = e;
        super(n, n2, n3);
        this.d = d;
    }
}
