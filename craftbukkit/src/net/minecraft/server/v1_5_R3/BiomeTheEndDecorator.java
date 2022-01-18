// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BiomeTheEndDecorator extends BiomeDecorator
{
    protected WorldGenerator L;
    
    public BiomeTheEndDecorator(final BiomeBase biomeBase) {
        super(biomeBase);
        this.L = new WorldGenEnder(Block.WHITESTONE.id);
    }
    
    protected void a() {
        this.b();
        if (this.b.nextInt(5) == 0) {
            final int i = this.c + this.b.nextInt(16) + 8;
            final int j = this.d + this.b.nextInt(16) + 8;
            final int k = this.a.i(i, j);
            if (k > 0) {}
            this.L.a(this.a, this.b, i, k, j);
        }
        if (this.c == 0 && this.d == 0) {
            final EntityEnderDragon entity = new EntityEnderDragon(this.a);
            entity.setPositionRotation(0.0, 128.0, 0.0, this.b.nextFloat() * 360.0f, 0.0f);
            this.a.addEntity(entity);
        }
    }
}
