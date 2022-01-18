// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class RecipesFood
{
    public void a(final CraftingManager craftingManager) {
        craftingManager.registerShapelessRecipe(new ItemStack(Item.MUSHROOM_SOUP), Block.BROWN_MUSHROOM, Block.RED_MUSHROOM, Item.BOWL);
        craftingManager.registerShapedRecipe(new ItemStack(Item.COOKIE, 8), "#X#", 'X', new ItemStack(Item.INK_SACK, 1, 3), '#', Item.WHEAT);
        craftingManager.registerShapedRecipe(new ItemStack(Block.MELON), "MMM", "MMM", "MMM", 'M', Item.MELON);
        craftingManager.registerShapedRecipe(new ItemStack(Item.MELON_SEEDS), "M", 'M', Item.MELON);
        craftingManager.registerShapedRecipe(new ItemStack(Item.PUMPKIN_SEEDS, 4), "M", 'M', Block.PUMPKIN);
        craftingManager.registerShapelessRecipe(new ItemStack(Item.PUMPKIN_PIE), Block.PUMPKIN, Item.SUGAR, Item.EGG);
        craftingManager.registerShapelessRecipe(new ItemStack(Item.FERMENTED_SPIDER_EYE), Item.SPIDER_EYE, Block.BROWN_MUSHROOM, Item.SUGAR);
        craftingManager.registerShapelessRecipe(new ItemStack(Item.SPECKLED_MELON), Item.MELON, Item.GOLD_NUGGET);
        craftingManager.registerShapelessRecipe(new ItemStack(Item.BLAZE_POWDER, 2), Item.BLAZE_ROD);
        craftingManager.registerShapelessRecipe(new ItemStack(Item.MAGMA_CREAM), Item.BLAZE_POWDER, Item.SLIME_BALL);
    }
}
