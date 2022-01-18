// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class TileEntityEnderChest extends TileEntity
{
    public float a;
    public float b;
    public int c;
    private int d;
    
    public void h() {
        super.h();
        if (++this.d % 20 * 4 == 0) {
            this.world.playNote(this.x, this.y, this.z, Block.ENDER_CHEST.id, 1, this.c);
        }
        this.b = this.a;
        final float n = 0.1f;
        if (this.c > 0 && this.a == 0.0f) {
            this.world.makeSound(this.x + 0.5, this.y + 0.5, this.z + 0.5, "random.chestopen", 0.5f, this.world.random.nextFloat() * 0.1f + 0.9f);
        }
        if ((this.c == 0 && this.a > 0.0f) || (this.c > 0 && this.a < 1.0f)) {
            final float a = this.a;
            if (this.c > 0) {
                this.a += n;
            }
            else {
                this.a -= n;
            }
            if (this.a > 1.0f) {
                this.a = 1.0f;
            }
            final float n2 = 0.5f;
            if (this.a < n2 && a >= n2) {
                this.world.makeSound(this.x + 0.5, this.y + 0.5, this.z + 0.5, "random.chestclosed", 0.5f, this.world.random.nextFloat() * 0.1f + 0.9f);
            }
            if (this.a < 0.0f) {
                this.a = 0.0f;
            }
        }
    }
    
    public boolean b(final int i, final int n) {
        if (i == 1) {
            this.c = n;
            return true;
        }
        return super.b(i, n);
    }
    
    public void w_() {
        this.i();
        super.w_();
    }
    
    public void a() {
        ++this.c;
        this.world.playNote(this.x, this.y, this.z, Block.ENDER_CHEST.id, 1, this.c);
    }
    
    public void b() {
        --this.c;
        this.world.playNote(this.x, this.y, this.z, Block.ENDER_CHEST.id, 1, this.c);
    }
    
    public boolean a(final EntityHuman entityHuman) {
        return this.world.getTileEntity(this.x, this.y, this.z) == this && entityHuman.e(this.x + 0.5, this.y + 0.5, this.z + 0.5) <= 64.0;
    }
}
