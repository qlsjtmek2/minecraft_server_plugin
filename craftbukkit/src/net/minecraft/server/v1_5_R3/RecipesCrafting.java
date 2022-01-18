// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class RecipesCrafting
{
    public void a(final CraftingManager craftingManager) {
        craftingManager.registerShapedRecipe(new ItemStack(Block.CHEST), "###", "# #", "###", '#', Block.WOOD);
        craftingManager.registerShapedRecipe(new ItemStack(Block.TRAPPED_CHEST), "#-", '#', Block.CHEST, '-', Block.TRIPWIRE_SOURCE);
        craftingManager.registerShapedRecipe(new ItemStack(Block.ENDER_CHEST), "###", "#E#", "###", '#', Block.OBSIDIAN, 'E', Item.EYE_OF_ENDER);
        craftingManager.registerShapedRecipe(new ItemStack(Block.FURNACE), "###", "# #", "###", '#', Block.COBBLESTONE);
        craftingManager.registerShapedRecipe(new ItemStack(Block.WORKBENCH), "##", "##", '#', Block.WOOD);
        craftingManager.registerShapedRecipe(new ItemStack(Block.SANDSTONE), "##", "##", '#', Block.SAND);
        craftingManager.registerShapedRecipe(new ItemStack(Block.SANDSTONE, 4, 2), "##", "##", '#', Block.SANDSTONE);
        craftingManager.registerShapedRecipe(new ItemStack(Block.SANDSTONE, 1, 1), "#", "#", '#', new ItemStack(Block.STEP, 1, 1));
        craftingManager.registerShapedRecipe(new ItemStack(Block.QUARTZ_BLOCK, 1, 1), "#", "#", '#', new ItemStack(Block.STEP, 1, 7));
        craftingManager.registerShapedRecipe(new ItemStack(Block.QUARTZ_BLOCK, 2, 2), "#", "#", '#', new ItemStack(Block.QUARTZ_BLOCK, 1, 0));
        craftingManager.registerShapedRecipe(new ItemStack(Block.SMOOTH_BRICK, 4), "##", "##", '#', Block.STONE);
        craftingManager.registerShapedRecipe(new ItemStack(Block.IRON_FENCE, 16), "###", "###", '#', Item.IRON_INGOT);
        craftingManager.registerShapedRecipe(new ItemStack(Block.THIN_GLASS, 16), "###", "###", '#', Block.GLASS);
        craftingManager.registerShapedRecipe(new ItemStack(Block.REDSTONE_LAMP_OFF, 1), " R ", "RGR", " R ", 'R', Item.REDSTONE, 'G', Block.GLOWSTONE);
        craftingManager.registerShapedRecipe(new ItemStack(Block.BEACON, 1), "GGG", "GSG", "OOO", 'G', Block.GLASS, 'S', Item.NETHER_STAR, 'O', Block.OBSIDIAN);
        craftingManager.registerShapedRecipe(new ItemStack(Block.NETHER_BRICK, 1), "NN", "NN", 'N', Item.NETHER_BRICK);
    }
}
