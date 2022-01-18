// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EntityMinecartTNT extends EntityMinecartAbstract
{
    private int fuse;
    
    public EntityMinecartTNT(final World world) {
        super(world);
        this.fuse = -1;
    }
    
    public EntityMinecartTNT(final World world, final double d0, final double d2, final double d3) {
        super(world, d0, d2, d3);
        this.fuse = -1;
    }
    
    public int getType() {
        return 3;
    }
    
    public Block n() {
        return Block.TNT;
    }
    
    public void l_() {
        super.l_();
        if (this.fuse > 0) {
            --this.fuse;
            this.world.addParticle("smoke", this.locX, this.locY + 0.5, this.locZ, 0.0, 0.0, 0.0);
        }
        else if (this.fuse == 0) {
            this.c(this.motX * this.motX + this.motZ * this.motZ);
        }
        if (this.positionChanged) {
            final double n = this.motX * this.motX + this.motZ * this.motZ;
            if (n >= 0.009999999776482582) {
                this.c(n);
            }
        }
    }
    
    public void a(final DamageSource damagesource) {
        super.a(damagesource);
        final double n = this.motX * this.motX + this.motZ * this.motZ;
        if (!damagesource.c()) {
            this.a(new ItemStack(Block.TNT, 1), 0.0f);
        }
        if (damagesource.m() || damagesource.c() || n >= 0.009999999776482582) {
            this.c(n);
        }
    }
    
    protected void c(final double n) {
        if (!this.world.isStatic) {
            double sqrt = Math.sqrt(n);
            if (sqrt > 5.0) {
                sqrt = 5.0;
            }
            this.world.explode(this, this.locX, this.locY, this.locZ, (float)(4.0 + this.random.nextDouble() * 1.5 * sqrt), true);
            this.die();
        }
    }
    
    protected void a(final float f) {
        if (f >= 3.0f) {
            final float n = f / 10.0f;
            this.c((double)(n * n));
        }
        super.a(f);
    }
    
    public void a(final int n, final int n2, final int n3, final boolean b) {
        if (b && this.fuse < 0) {
            this.d();
        }
    }
    
    public void d() {
        this.fuse = 80;
        if (!this.world.isStatic) {
            this.world.broadcastEntityEffect(this, (byte)10);
            this.world.makeSound(this, "random.fuse", 1.0f, 1.0f);
        }
    }
    
    public boolean ay() {
        return this.fuse > -1;
    }
    
    public float a(final Explosion explosion, final World world, final int i, final int j, final int k, final Block block) {
        if (this.ay() && (BlockMinecartTrackAbstract.d_(block.id) || BlockMinecartTrackAbstract.d_(world, i, j + 1, k))) {
            return 0.0f;
        }
        return super.a(explosion, world, i, j, k, block);
    }
    
    public boolean a(final Explosion explosion, final World world, final int i, final int j, final int k, final int l, final float f) {
        return (!this.ay() || (!BlockMinecartTrackAbstract.d_(l) && !BlockMinecartTrackAbstract.d_(world, i, j + 1, k))) && super.a(explosion, world, i, j, k, l, f);
    }
    
    protected void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.hasKey("TNTFuse")) {
            this.fuse = nbttagcompound.getInt("TNTFuse");
        }
    }
    
    protected void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("TNTFuse", this.fuse);
    }
}
