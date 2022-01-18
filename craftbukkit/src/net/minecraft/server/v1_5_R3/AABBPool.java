// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.List;

public class AABBPool
{
    private final int a;
    private final int b;
    private final List pool;
    private int d;
    private int largestSize;
    private int resizeTime;
    
    public AABBPool(final int i, final int j) {
        this.pool = new ArrayList();
        this.d = 0;
        this.largestSize = 0;
        this.resizeTime = 0;
        this.a = i;
        this.b = j;
    }
    
    public AxisAlignedBB a(final double d0, final double d1, final double d2, final double d3, final double d4, final double d5) {
        if (this.resizeTime == 0) {
            return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
        }
        AxisAlignedBB axisalignedbb;
        if (this.d >= this.pool.size()) {
            axisalignedbb = new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
            this.pool.add(axisalignedbb);
        }
        else {
            axisalignedbb = this.pool.get(this.d);
            axisalignedbb.b(d0, d1, d2, d3, d4, d5);
        }
        ++this.d;
        return axisalignedbb;
    }
    
    public void a() {
        if (this.d > this.largestSize) {
            this.largestSize = this.d;
        }
        if ((this.resizeTime++ & 0xFF) == 0x0) {
            final int newSize = this.pool.size() - (this.pool.size() >> 3);
            if (newSize > this.largestSize) {
                for (int i = this.pool.size() - 1; i > newSize; --i) {
                    this.pool.remove(i);
                }
            }
            this.largestSize = 0;
        }
        this.d = 0;
    }
    
    public int c() {
        return this.pool.size();
    }
    
    public int d() {
        return this.d;
    }
}
