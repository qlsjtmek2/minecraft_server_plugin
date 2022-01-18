// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class Position implements IPosition
{
    protected final double a;
    protected final double b;
    protected final double c;
    
    public Position(final double a, final double b, final double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public double getX() {
        return this.a;
    }
    
    public double getY() {
        return this.b;
    }
    
    public double getZ() {
        return this.c;
    }
}
