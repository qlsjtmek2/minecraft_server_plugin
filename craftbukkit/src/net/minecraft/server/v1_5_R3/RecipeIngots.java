// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class RecipeIngots
{
    private Object[][] a;
    
    public RecipeIngots() {
        this.a = new Object[][] { { Block.GOLD_BLOCK, new ItemStack(Item.GOLD_INGOT, 9) }, { Block.IRON_BLOCK, new ItemStack(Item.IRON_INGOT, 9) }, { Block.DIAMOND_BLOCK, new ItemStack(Item.DIAMOND, 9) }, { Block.EMERALD_BLOCK, new ItemStack(Item.EMERALD, 9) }, { Block.LAPIS_BLOCK, new ItemStack(Item.INK_SACK, 9, 4) }, { Block.REDSTONE_BLOCK, new ItemStack(Item.REDSTONE, 9) } };
    }
    
    public void a(final CraftingManager craftingManager) {
        for (int i = 0; i < this.a.length; ++i) {
            final Block block = (Block)this.a[i][0];
            final ItemStack itemstack = (ItemStack)this.a[i][1];
            craftingManager.registerShapedRecipe(new ItemStack(block), "###", "###", "###", '#', itemstack);
            craftingManager.registerShapedRecipe(itemstack, "#", '#', block);
        }
        craftingManager.registerShapedRecipe(new ItemStack(Item.GOLD_INGOT), "###", "###", "###", '#', Item.GOLD_NUGGET);
        craftingManager.registerShapedRecipe(new ItemStack(Item.GOLD_NUGGET, 9), "#", '#', Item.GOLD_INGOT);
    }
}
