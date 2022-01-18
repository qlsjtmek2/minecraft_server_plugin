// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemWaterLily extends ItemWithAuxData
{
    public ItemWaterLily(final int i) {
        super(i, false);
    }
    
    public ItemStack a(final ItemStack itemstack, final World world, final EntityHuman entityhuman) {
        final MovingObjectPosition movingobjectposition = this.a(world, entityhuman, true);
        if (movingobjectposition == null) {
            return itemstack;
        }
        if (movingobjectposition.type == EnumMovingObjectType.TILE) {
            final int i = movingobjectposition.b;
            final int j = movingobjectposition.c;
            final int k = movingobjectposition.d;
            final int clickedX = i;
            final int clickedY = j;
            final int clickedZ = k;
            if (!world.a(entityhuman, i, j, k)) {
                return itemstack;
            }
            if (!entityhuman.a(i, j, k, movingobjectposition.face, itemstack)) {
                return itemstack;
            }
            if (world.getMaterial(i, j, k) == Material.WATER && world.getData(i, j, k) == 0 && world.isEmpty(i, j + 1, k)) {
                if (!ItemBlock.processBlockPlace(world, entityhuman, null, i, j + 1, k, Block.WATER_LILY.id, 0, clickedX, clickedY, clickedZ)) {
                    return itemstack;
                }
                if (!entityhuman.abilities.canInstantlyBuild) {
                    --itemstack.count;
                }
            }
        }
        return itemstack;
    }
}
