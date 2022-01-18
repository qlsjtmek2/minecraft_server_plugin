// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.HashMap;
import java.util.Map;

public class RecipesFurnace
{
    private static final RecipesFurnace a;
    public Map recipes;
    private Map c;
    
    public static final RecipesFurnace getInstance() {
        return RecipesFurnace.a;
    }
    
    public RecipesFurnace() {
        this.recipes = new HashMap();
        this.c = new HashMap();
        this.registerRecipe(Block.IRON_ORE.id, new ItemStack(Item.IRON_INGOT), 0.7f);
        this.registerRecipe(Block.GOLD_ORE.id, new ItemStack(Item.GOLD_INGOT), 1.0f);
        this.registerRecipe(Block.DIAMOND_ORE.id, new ItemStack(Item.DIAMOND), 1.0f);
        this.registerRecipe(Block.SAND.id, new ItemStack(Block.GLASS), 0.1f);
        this.registerRecipe(Item.PORK.id, new ItemStack(Item.GRILLED_PORK), 0.35f);
        this.registerRecipe(Item.RAW_BEEF.id, new ItemStack(Item.COOKED_BEEF), 0.35f);
        this.registerRecipe(Item.RAW_CHICKEN.id, new ItemStack(Item.COOKED_CHICKEN), 0.35f);
        this.registerRecipe(Item.RAW_FISH.id, new ItemStack(Item.COOKED_FISH), 0.35f);
        this.registerRecipe(Block.COBBLESTONE.id, new ItemStack(Block.STONE), 0.1f);
        this.registerRecipe(Item.CLAY_BALL.id, new ItemStack(Item.CLAY_BRICK), 0.3f);
        this.registerRecipe(Block.CACTUS.id, new ItemStack(Item.INK_SACK, 1, 2), 0.2f);
        this.registerRecipe(Block.LOG.id, new ItemStack(Item.COAL, 1, 1), 0.15f);
        this.registerRecipe(Block.EMERALD_ORE.id, new ItemStack(Item.EMERALD), 1.0f);
        this.registerRecipe(Item.POTATO.id, new ItemStack(Item.POTATO_BAKED), 0.35f);
        this.registerRecipe(Block.NETHERRACK.id, new ItemStack(Item.NETHER_BRICK), 0.1f);
        this.registerRecipe(Block.COAL_ORE.id, new ItemStack(Item.COAL), 0.1f);
        this.registerRecipe(Block.REDSTONE_ORE.id, new ItemStack(Item.REDSTONE), 0.7f);
        this.registerRecipe(Block.LAPIS_ORE.id, new ItemStack(Item.INK_SACK, 1, 4), 0.2f);
        this.registerRecipe(Block.QUARTZ_ORE.id, new ItemStack(Item.QUARTZ), 0.2f);
    }
    
    public void registerRecipe(final int i, final ItemStack itemstack, final float f) {
        this.recipes.put(i, itemstack);
        this.c.put(itemstack.id, f);
    }
    
    public ItemStack getResult(final int i) {
        return this.recipes.get(i);
    }
    
    public Map getRecipes() {
        return this.recipes;
    }
    
    public float c(final int i) {
        return this.c.containsKey(i) ? this.c.get(i) : 0.0f;
    }
    
    static {
        a = new RecipesFurnace();
    }
}
