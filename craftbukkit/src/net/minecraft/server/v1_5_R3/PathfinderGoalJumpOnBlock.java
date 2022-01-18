// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalJumpOnBlock extends PathfinderGoal
{
    private final EntityOcelot a;
    private final float b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    
    public PathfinderGoalJumpOnBlock(final EntityOcelot a, final float b) {
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.a = a;
        this.b = b;
        this.a(5);
    }
    
    public boolean a() {
        return this.a.isTamed() && !this.a.isSitting() && this.a.aE().nextDouble() <= 0.006500000134110451 && this.f();
    }
    
    public boolean b() {
        return this.c <= this.e && this.d <= 60 && this.a(this.a.world, this.f, this.g, this.h);
    }
    
    public void c() {
        this.a.getNavigation().a(this.f + 0.5, this.g + 1, this.h + 0.5, this.b);
        this.c = 0;
        this.d = 0;
        this.e = this.a.aE().nextInt(this.a.aE().nextInt(1200) + 1200) + 1200;
        this.a.getGoalSit().setSitting(false);
    }
    
    public void d() {
        this.a.setSitting(false);
    }
    
    public void e() {
        ++this.c;
        this.a.getGoalSit().setSitting(false);
        if (this.a.e(this.f, this.g + 1, this.h) > 1.0) {
            this.a.setSitting(false);
            this.a.getNavigation().a(this.f + 0.5, this.g + 1, this.h + 0.5, this.b);
            ++this.d;
        }
        else if (!this.a.isSitting()) {
            this.a.setSitting(true);
        }
        else {
            --this.d;
        }
    }
    
    private boolean f() {
        final int g = (int)this.a.locY;
        double n = 2.147483647E9;
        for (int n2 = (int)this.a.locX - 8; n2 < this.a.locX + 8.0; ++n2) {
            for (int n3 = (int)this.a.locZ - 8; n3 < this.a.locZ + 8.0; ++n3) {
                if (this.a(this.a.world, n2, g, n3) && this.a.world.isEmpty(n2, g + 1, n3)) {
                    final double e = this.a.e(n2, g, n3);
                    if (e < n) {
                        this.f = n2;
                        this.g = g;
                        this.h = n3;
                        n = e;
                    }
                }
            }
        }
        return n < 2.147483647E9;
    }
    
    private boolean a(final World world, final int i, final int j, final int k) {
        final int typeId = world.getTypeId(i, j, k);
        final int data = world.getData(i, j, k);
        if (typeId == Block.CHEST.id) {
            if (((TileEntityChest)world.getTileEntity(i, j, k)).h < 1) {
                return true;
            }
        }
        else {
            if (typeId == Block.BURNING_FURNACE.id) {
                return true;
            }
            if (typeId == Block.BED.id && !BlockBed.e_(data)) {
                return true;
            }
        }
        return false;
    }
}
