// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class RecipeMapExtend extends ShapedRecipes
{
    public RecipeMapExtend() {
        super(3, 3, new ItemStack[] { new ItemStack(Item.PAPER), new ItemStack(Item.PAPER), new ItemStack(Item.PAPER), new ItemStack(Item.PAPER), new ItemStack(Item.MAP, 0, 32767), new ItemStack(Item.PAPER), new ItemStack(Item.PAPER), new ItemStack(Item.PAPER), new ItemStack(Item.PAPER) }, new ItemStack(Item.MAP_EMPTY, 0, 0));
    }
    
    public boolean a(final InventoryCrafting inventorycrafting, final World world) {
        if (!super.a(inventorycrafting, world)) {
            return false;
        }
        ItemStack itemstack = null;
        for (int i = 0; i < inventorycrafting.getSize() && itemstack == null; ++i) {
            final ItemStack item = inventorycrafting.getItem(i);
            if (item != null && item.id == Item.MAP.id) {
                itemstack = item;
            }
        }
        if (itemstack == null) {
            return false;
        }
        final WorldMap savedMap = Item.MAP.getSavedMap(itemstack, world);
        return savedMap != null && savedMap.scale < 4;
    }
    
    public ItemStack a(final InventoryCrafting inventoryCrafting) {
        ItemStack itemStack = null;
        for (int i = 0; i < inventoryCrafting.getSize() && itemStack == null; ++i) {
            final ItemStack item = inventoryCrafting.getItem(i);
            if (item != null && item.id == Item.MAP.id) {
                itemStack = item;
            }
        }
        final ItemStack cloneItemStack = itemStack.cloneItemStack();
        cloneItemStack.count = 1;
        if (cloneItemStack.getTag() == null) {
            cloneItemStack.setTag(new NBTTagCompound());
        }
        cloneItemStack.getTag().setBoolean("map_is_scaling", true);
        return cloneItemStack;
    }
}
