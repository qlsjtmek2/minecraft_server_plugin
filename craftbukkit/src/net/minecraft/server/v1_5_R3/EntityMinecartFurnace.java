// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EntityMinecartFurnace extends EntityMinecartAbstract
{
    private int c;
    public double a;
    public double b;
    
    public EntityMinecartFurnace(final World world) {
        super(world);
        this.c = 0;
    }
    
    public EntityMinecartFurnace(final World world, final double d0, final double d2, final double d3) {
        super(world, d0, d2, d3);
        this.c = 0;
    }
    
    public int getType() {
        return 2;
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(16, new Byte((byte)0));
    }
    
    public void l_() {
        super.l_();
        if (this.c > 0) {
            --this.c;
        }
        if (this.c <= 0) {
            final double n = 0.0;
            this.b = n;
            this.a = n;
        }
        this.f(this.c > 0);
        if (this.d() && this.random.nextInt(4) == 0) {
            this.world.addParticle("largesmoke", this.locX, this.locY + 0.8, this.locZ, 0.0, 0.0, 0.0);
        }
    }
    
    public void a(final DamageSource damagesource) {
        super.a(damagesource);
        if (!damagesource.c()) {
            this.a(new ItemStack(Block.FURNACE, 1), 0.0f);
        }
    }
    
    protected void a(final int i, final int j, final int k, final double d0, final double d2, final int l, final int i2) {
        super.a(i, j, k, d0, d2, l, i2);
        final double n = this.a * this.a + this.b * this.b;
        if (n > 1.0E-4 && this.motX * this.motX + this.motZ * this.motZ > 0.001) {
            final double n2 = MathHelper.sqrt(n);
            this.a /= n2;
            this.b /= n2;
            if (this.a * this.motX + this.b * this.motZ < 0.0) {
                this.a = 0.0;
                this.b = 0.0;
            }
            else {
                this.a = this.motX;
                this.b = this.motZ;
            }
        }
    }
    
    protected void h() {
        final double n = this.a * this.a + this.b * this.b;
        if (n > 1.0E-4) {
            final double n2 = MathHelper.sqrt(n);
            this.a /= n2;
            this.b /= n2;
            final double n3 = 0.05;
            this.motX *= 0.800000011920929;
            this.motY *= 0.0;
            this.motZ *= 0.800000011920929;
            this.motX += this.a * n3;
            this.motZ += this.b * n3;
        }
        else {
            this.motX *= 0.9800000190734863;
            this.motY *= 0.0;
            this.motZ *= 0.9800000190734863;
        }
        super.h();
    }
    
    public boolean a_(final EntityHuman entityHuman) {
        final ItemStack itemInHand = entityHuman.inventory.getItemInHand();
        if (itemInHand != null && itemInHand.id == Item.COAL.id) {
            final ItemStack itemStack = itemInHand;
            if (--itemStack.count == 0) {
                entityHuman.inventory.setItem(entityHuman.inventory.itemInHandIndex, null);
            }
            this.c += 3600;
        }
        this.a = this.locX - entityHuman.locX;
        this.b = this.locZ - entityHuman.locZ;
        return true;
    }
    
    protected void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setDouble("PushX", this.a);
        nbttagcompound.setDouble("PushZ", this.b);
        nbttagcompound.setShort("Fuel", (short)this.c);
    }
    
    protected void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a = nbttagcompound.getDouble("PushX");
        this.b = nbttagcompound.getDouble("PushZ");
        this.c = nbttagcompound.getShort("Fuel");
    }
    
    protected boolean d() {
        return (this.datawatcher.getByte(16) & 0x1) != 0x0;
    }
    
    protected void f(final boolean b) {
        if (b) {
            this.datawatcher.watch(16, (byte)(this.datawatcher.getByte(16) | 0x1));
        }
        else {
            this.datawatcher.watch(16, (byte)(this.datawatcher.getByte(16) & 0xFFFFFFFE));
        }
    }
    
    public Block n() {
        return Block.BURNING_FURNACE;
    }
    
    public int p() {
        return 2;
    }
}
