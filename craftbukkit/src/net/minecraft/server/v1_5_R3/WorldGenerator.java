// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.BlockChangeDelegate;
import java.util.Random;

public abstract class WorldGenerator
{
    private final boolean a;
    
    public WorldGenerator() {
        this.a = false;
    }
    
    public WorldGenerator(final boolean flag) {
        this.a = flag;
    }
    
    public abstract boolean a(final World p0, final Random p1, final int p2, final int p3, final int p4);
    
    public void a(final double d0, final double d1, final double d2) {
    }
    
    protected void setType(final BlockChangeDelegate world, final int i, final int j, final int k, final int l) {
        this.setTypeAndData(world, i, j, k, l, 0);
    }
    
    protected void setTypeAndData(final BlockChangeDelegate world, final int i, final int j, final int k, final int l, final int i1) {
        if (this.a) {
            world.setTypeIdAndData(i, j, k, l, i1);
        }
        else if (world instanceof World) {
            ((World)world).setTypeIdAndData(i, j, k, l, i1, 2);
        }
        else {
            world.setRawTypeIdAndData(i, j, k, l, i1);
        }
    }
}
