// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class RecipesArmor
{
    private String[][] a;
    private Object[][] b;
    
    public RecipesArmor() {
        this.a = new String[][] { { "XXX", "X X" }, { "X X", "XXX", "XXX" }, { "XXX", "X X", "X X" }, { "X X", "X X" } };
        this.b = new Object[][] { { Item.LEATHER, Block.FIRE, Item.IRON_INGOT, Item.DIAMOND, Item.GOLD_INGOT }, { Item.LEATHER_HELMET, Item.CHAINMAIL_HELMET, Item.IRON_HELMET, Item.DIAMOND_HELMET, Item.GOLD_HELMET }, { Item.LEATHER_CHESTPLATE, Item.CHAINMAIL_CHESTPLATE, Item.IRON_CHESTPLATE, Item.DIAMOND_CHESTPLATE, Item.GOLD_CHESTPLATE }, { Item.LEATHER_LEGGINGS, Item.CHAINMAIL_LEGGINGS, Item.IRON_LEGGINGS, Item.DIAMOND_LEGGINGS, Item.GOLD_LEGGINGS }, { Item.LEATHER_BOOTS, Item.CHAINMAIL_BOOTS, Item.IRON_BOOTS, Item.DIAMOND_BOOTS, Item.GOLD_BOOTS } };
    }
    
    public void a(final CraftingManager craftingManager) {
        for (int i = 0; i < this.b[0].length; ++i) {
            final Object o = this.b[0][i];
            for (int j = 0; j < this.b.length - 1; ++j) {
                craftingManager.registerShapedRecipe(new ItemStack((Item)this.b[j + 1][i]), this.a[j], 'X', o);
            }
        }
    }
}
