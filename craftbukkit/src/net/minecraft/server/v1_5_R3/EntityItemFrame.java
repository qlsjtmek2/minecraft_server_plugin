// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EntityItemFrame extends EntityHanging
{
    private float e;
    
    public EntityItemFrame(final World world) {
        super(world);
        this.e = 1.0f;
    }
    
    public EntityItemFrame(final World world, final int i, final int j, final int k, final int n) {
        super(world, i, j, k, n);
        this.e = 1.0f;
        this.setDirection(n);
    }
    
    protected void a() {
        this.getDataWatcher().a(2, 5);
        this.getDataWatcher().a(3, (Object)(byte)0);
    }
    
    public int d() {
        return 9;
    }
    
    public int g() {
        return 9;
    }
    
    public void h() {
        this.a(new ItemStack(Item.ITEM_FRAME), 0.0f);
        final ItemStack i = this.i();
        if (i != null && this.random.nextFloat() < this.e) {
            final ItemStack cloneItemStack = i.cloneItemStack();
            cloneItemStack.a((EntityItemFrame)null);
            this.a(cloneItemStack, 0.0f);
        }
    }
    
    public ItemStack i() {
        return this.getDataWatcher().getItemStack(2);
    }
    
    public void a(ItemStack cloneItemStack) {
        cloneItemStack = cloneItemStack.cloneItemStack();
        cloneItemStack.count = 1;
        cloneItemStack.a(this);
        this.getDataWatcher().watch(2, cloneItemStack);
        this.getDataWatcher().h(2);
    }
    
    public int j() {
        return this.getDataWatcher().getByte(3);
    }
    
    public void setRotation(final int n) {
        this.getDataWatcher().watch(3, (byte)(n % 4));
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        if (this.i() != null) {
            nbttagcompound.setCompound("Item", this.i().save(new NBTTagCompound()));
            nbttagcompound.setByte("ItemRotation", (byte)this.j());
            nbttagcompound.setFloat("ItemDropChance", this.e);
        }
        super.b(nbttagcompound);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        final NBTTagCompound compound = nbttagcompound.getCompound("Item");
        if (compound != null && !compound.isEmpty()) {
            this.a(ItemStack.createStack(compound));
            this.setRotation(nbttagcompound.getByte("ItemRotation"));
            if (nbttagcompound.hasKey("ItemDropChance")) {
                this.e = nbttagcompound.getFloat("ItemDropChance");
            }
        }
        super.a(nbttagcompound);
    }
    
    public boolean a_(final EntityHuman entityHuman) {
        if (this.i() == null) {
            final ItemStack bg = entityHuman.bG();
            if (bg != null && !this.world.isStatic) {
                this.a(bg);
                if (!entityHuman.abilities.canInstantlyBuild) {
                    final ItemStack itemStack = bg;
                    if (--itemStack.count <= 0) {
                        entityHuman.inventory.setItem(entityHuman.inventory.itemInHandIndex, null);
                    }
                }
            }
        }
        else if (!this.world.isStatic) {
            this.setRotation(this.j() + 1);
        }
        return true;
    }
}
