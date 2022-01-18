// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.Map;

public class WorldGenMineshaft extends StructureGenerator
{
    private double e;
    
    public WorldGenMineshaft() {
        this.e = 0.01;
    }
    
    public WorldGenMineshaft(final Map map) {
        this.e = 0.01;
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            if (entry.getKey().equals("chance")) {
                this.e = MathHelper.a((String)entry.getValue(), this.e);
            }
        }
    }
    
    protected boolean a(final int n, final int n2) {
        return this.b.nextDouble() < this.e && this.b.nextInt(80) < Math.max(Math.abs(n), Math.abs(n2));
    }
    
    protected StructureStart b(final int n, final int n2) {
        return new WorldGenMineshaftStart(this.c, this.b, n, n2);
    }
}
