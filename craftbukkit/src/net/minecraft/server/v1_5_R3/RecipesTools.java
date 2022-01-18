// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class RecipesTools
{
    private String[][] a;
    private Object[][] b;
    
    public RecipesTools() {
        this.a = new String[][] { { "XXX", " # ", " # " }, { "X", "#", "#" }, { "XX", "X#", " #" }, { "XX", " #", " #" } };
        this.b = new Object[][] { { Block.WOOD, Block.COBBLESTONE, Item.IRON_INGOT, Item.DIAMOND, Item.GOLD_INGOT }, { Item.WOOD_PICKAXE, Item.STONE_PICKAXE, Item.IRON_PICKAXE, Item.DIAMOND_PICKAXE, Item.GOLD_PICKAXE }, { Item.WOOD_SPADE, Item.STONE_SPADE, Item.IRON_SPADE, Item.DIAMOND_SPADE, Item.GOLD_SPADE }, { Item.WOOD_AXE, Item.STONE_AXE, Item.IRON_AXE, Item.DIAMOND_AXE, Item.GOLD_AXE }, { Item.WOOD_HOE, Item.STONE_HOE, Item.IRON_HOE, Item.DIAMOND_HOE, Item.GOLD_HOE } };
    }
    
    public void a(final CraftingManager craftingManager) {
        for (int i = 0; i < this.b[0].length; ++i) {
            final Object o = this.b[0][i];
            for (int j = 0; j < this.b.length - 1; ++j) {
                craftingManager.registerShapedRecipe(new ItemStack((Item)this.b[j + 1][i]), this.a[j], '#', Item.STICK, 'X', o);
            }
        }
        craftingManager.registerShapedRecipe(new ItemStack(Item.SHEARS), " #", "# ", '#', Item.IRON_INGOT);
    }
}
