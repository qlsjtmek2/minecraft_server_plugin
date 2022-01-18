// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class RecipesWeapons
{
    private String[][] a;
    private Object[][] b;
    
    public RecipesWeapons() {
        this.a = new String[][] { { "X", "X", "#" } };
        this.b = new Object[][] { { Block.WOOD, Block.COBBLESTONE, Item.IRON_INGOT, Item.DIAMOND, Item.GOLD_INGOT }, { Item.WOOD_SWORD, Item.STONE_SWORD, Item.IRON_SWORD, Item.DIAMOND_SWORD, Item.GOLD_SWORD } };
    }
    
    public void a(final CraftingManager craftingManager) {
        for (int i = 0; i < this.b[0].length; ++i) {
            final Object o = this.b[0][i];
            for (int j = 0; j < this.b.length - 1; ++j) {
                craftingManager.registerShapedRecipe(new ItemStack((Item)this.b[j + 1][i]), this.a[j], '#', Item.STICK, 'X', o);
            }
        }
        craftingManager.registerShapedRecipe(new ItemStack(Item.BOW, 1), " #X", "# X", " #X", 'X', Item.STRING, '#', Item.STICK);
        craftingManager.registerShapedRecipe(new ItemStack(Item.ARROW, 4), "X", "#", "Y", 'Y', Item.FEATHER, 'X', Item.FLINT, '#', Item.STICK);
    }
}
