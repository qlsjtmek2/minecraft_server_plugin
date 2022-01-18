// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;

public class BlockPressurePlateWeighted extends BlockPressurePlateAbstract
{
    private final int a;
    
    protected BlockPressurePlateWeighted(final int i, final String s, final Material material, final int a) {
        super(i, s, material);
        this.a = a;
    }
    
    protected int e(final World world, final int i, final int j, final int k) {
        int n = 0;
        final Iterator<EntityItem> iterator = world.a(EntityItem.class, this.a(i, j, k)).iterator();
        while (iterator.hasNext()) {
            n += iterator.next().getItemStack().count;
            if (n >= this.a) {
                break;
            }
        }
        if (n <= 0) {
            return 0;
        }
        return MathHelper.f(Math.min(this.a, n) / this.a * 15.0f);
    }
    
    protected int c(final int n) {
        return n;
    }
    
    protected int d(final int n) {
        return n;
    }
    
    public int a(final World world) {
        return 10;
    }
}
