// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class SourceBlock implements ISourceBlock
{
    private final World a;
    private final int b;
    private final int c;
    private final int d;
    
    public SourceBlock(final World a, final int b, final int c, final int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    
    public World k() {
        return this.a;
    }
    
    public double getX() {
        return this.b + 0.5;
    }
    
    public double getY() {
        return this.c + 0.5;
    }
    
    public double getZ() {
        return this.d + 0.5;
    }
    
    public int getBlockX() {
        return this.b;
    }
    
    public int getBlockY() {
        return this.c;
    }
    
    public int getBlockZ() {
        return this.d;
    }
    
    public int h() {
        return this.a.getData(this.b, this.c, this.d);
    }
    
    public TileEntity getTileEntity() {
        return this.a.getTileEntity(this.b, this.c, this.d);
    }
}
