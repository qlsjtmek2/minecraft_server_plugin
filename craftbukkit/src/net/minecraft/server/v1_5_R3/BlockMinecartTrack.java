// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockMinecartTrack extends BlockMinecartTrackAbstract
{
    protected BlockMinecartTrack(final int n) {
        super(n, false);
    }
    
    protected void a(final World world, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (n6 > 0 && Block.byId[n6].isPowerSource() && new MinecartTrackLogic(this, world, n, n2, n3).a() == 3) {
            this.a(world, n, n2, n3, false);
        }
    }
}
