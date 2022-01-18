// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class RecipesDyes
{
    public void a(final CraftingManager craftingManager) {
        for (int i = 0; i < 16; ++i) {
            craftingManager.registerShapelessRecipe(new ItemStack(Block.WOOL, 1, BlockCloth.c(i)), new ItemStack(Item.INK_SACK, 1, i), new ItemStack(Item.byId[Block.WOOL.id], 1, 0));
        }
        craftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 11), Block.YELLOW_FLOWER);
        craftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 1), Block.RED_ROSE);
        craftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 3, 15), Item.BONE);
        craftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 9), new ItemStack(Item.INK_SACK, 1, 1), new ItemStack(Item.INK_SACK, 1, 15));
        craftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 14), new ItemStack(Item.INK_SACK, 1, 1), new ItemStack(Item.INK_SACK, 1, 11));
        craftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 10), new ItemStack(Item.INK_SACK, 1, 2), new ItemStack(Item.INK_SACK, 1, 15));
        craftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 8), new ItemStack(Item.INK_SACK, 1, 0), new ItemStack(Item.INK_SACK, 1, 15));
        craftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 7), new ItemStack(Item.INK_SACK, 1, 8), new ItemStack(Item.INK_SACK, 1, 15));
        craftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 3, 7), new ItemStack(Item.INK_SACK, 1, 0), new ItemStack(Item.INK_SACK, 1, 15), new ItemStack(Item.INK_SACK, 1, 15));
        craftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 12), new ItemStack(Item.INK_SACK, 1, 4), new ItemStack(Item.INK_SACK, 1, 15));
        craftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 6), new ItemStack(Item.INK_SACK, 1, 4), new ItemStack(Item.INK_SACK, 1, 2));
        craftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 5), new ItemStack(Item.INK_SACK, 1, 4), new ItemStack(Item.INK_SACK, 1, 1));
        craftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 2, 13), new ItemStack(Item.INK_SACK, 1, 5), new ItemStack(Item.INK_SACK, 1, 9));
        craftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 3, 13), new ItemStack(Item.INK_SACK, 1, 4), new ItemStack(Item.INK_SACK, 1, 1), new ItemStack(Item.INK_SACK, 1, 9));
        craftingManager.registerShapelessRecipe(new ItemStack(Item.INK_SACK, 4, 13), new ItemStack(Item.INK_SACK, 1, 4), new ItemStack(Item.INK_SACK, 1, 1), new ItemStack(Item.INK_SACK, 1, 1), new ItemStack(Item.INK_SACK, 1, 15));
    }
}
