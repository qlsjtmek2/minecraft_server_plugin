// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemSign extends Item
{
    public ItemSign(final int i) {
        super(i);
        this.maxStackSize = 16;
        this.a(CreativeModeTab.c);
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, int i, int j, int k, int l, final float f, final float f1, final float f2) {
        final int clickedX = i;
        final int clickedY = j;
        final int clickedZ = k;
        if (l == 0) {
            return false;
        }
        if (!world.getMaterial(i, j, k).isBuildable()) {
            return false;
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
        if (!entityhuman.a(i, j, k, l, itemstack)) {
            return false;
        }
        if (!Block.SIGN_POST.canPlace(world, i, j, k)) {
            return false;
        }
        Block block;
        if (l == 1) {
            final int i2 = MathHelper.floor((entityhuman.yaw + 180.0f) * 16.0f / 360.0f + 0.5) & 0xF;
            block = Block.SIGN_POST;
            l = i2;
        }
        else {
            block = Block.WALL_SIGN;
        }
        if (!ItemBlock.processBlockPlace(world, entityhuman, null, i, j, k, block.id, l, clickedX, clickedY, clickedZ)) {
            return false;
        }
        --itemstack.count;
        final TileEntitySign tileentitysign = (TileEntitySign)world.getTileEntity(i, j, k);
        if (tileentitysign != null) {
            entityhuman.a(tileentitysign);
        }
        return true;
    }
}
