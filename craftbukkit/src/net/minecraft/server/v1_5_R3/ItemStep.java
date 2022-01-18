// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemStep extends ItemBlock
{
    private final boolean a;
    private final BlockStepAbstract b;
    private final BlockStepAbstract c;
    
    public ItemStep(final int i, final BlockStepAbstract blockstepabstract, final BlockStepAbstract blockstepabstract1, final boolean flag) {
        super(i);
        this.b = blockstepabstract;
        this.c = blockstepabstract1;
        this.a = flag;
        this.setMaxDurability(0);
        this.a(true);
    }
    
    public int filterData(final int i) {
        return i;
    }
    
    public String d(final ItemStack itemstack) {
        return this.b.c(itemstack.getData());
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2) {
        if (this.a) {
            return super.interactWith(itemstack, entityhuman, world, i, j, k, l, f, f1, f2);
        }
        if (itemstack.count == 0) {
            return false;
        }
        if (!entityhuman.a(i, j, k, l, itemstack)) {
            return false;
        }
        final int i2 = world.getTypeId(i, j, k);
        final int j2 = world.getData(i, j, k);
        final int k2 = j2 & 0x7;
        final boolean flag = (j2 & 0x8) != 0x0;
        if (((l == 1 && !flag) || (l == 0 && flag)) && i2 == this.b.id && k2 == itemstack.getData()) {
            if (world.b(this.c.b(world, i, j, k)) && ItemBlock.processBlockPlace(world, entityhuman, null, i, j, k, this.c.id, k2, i, j, k)) {
                --itemstack.count;
            }
            return true;
        }
        return this.a(itemstack, entityhuman, world, i, j, k, l) || super.interactWith(itemstack, entityhuman, world, i, j, k, l, f, f1, f2);
    }
    
    private boolean a(final ItemStack itemstack, final EntityHuman entityhuman, final World world, int i, int j, int k, final int l) {
        final int clickedX = i;
        final int clickedY = j;
        final int clickedZ = k;
        if (l == 0) {
            --j;
        }
        if (l == 1) {
            ++j;
        }
        if (l == 2) {
            --k;
        }
        if (l == 3) {
            ++k;
        }
        if (l == 4) {
            --i;
        }
        if (l == 5) {
            ++i;
        }
        final int i2 = world.getTypeId(i, j, k);
        final int j2 = world.getData(i, j, k);
        final int k2 = j2 & 0x7;
        if (i2 == this.b.id && k2 == itemstack.getData()) {
            if (world.b(this.c.b(world, i, j, k)) && ItemBlock.processBlockPlace(world, entityhuman, null, i, j, k, this.c.id, k2, clickedX, clickedY, clickedZ)) {
                --itemstack.count;
            }
            return true;
        }
        return false;
    }
}
