// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Collections;
import java.util.ArrayList;
import org.bukkit.inventory.InventoryView;
import java.util.List;

public class CraftingManager
{
    private static final CraftingManager a;
    public List recipes;
    public IRecipe lastRecipe;
    public InventoryView lastCraftView;
    
    public static final CraftingManager getInstance() {
        return CraftingManager.a;
    }
    
    public CraftingManager() {
        this.recipes = new ArrayList();
        new RecipesTools().a(this);
        new RecipesWeapons().a(this);
        new RecipeIngots().a(this);
        new RecipesFood().a(this);
        new RecipesCrafting().a(this);
        new RecipesArmor().a(this);
        new RecipesDyes().a(this);
        this.recipes.add(new RecipeArmorDye());
        this.recipes.add(new RecipeMapClone());
        this.recipes.add(new RecipeMapExtend());
        this.recipes.add(new RecipeFireworks());
        this.registerShapedRecipe(new ItemStack(Item.PAPER, 3), "###", '#', Item.SUGAR_CANE);
        this.registerShapelessRecipe(new ItemStack(Item.BOOK, 1), Item.PAPER, Item.PAPER, Item.PAPER, Item.LEATHER);
        this.registerShapelessRecipe(new ItemStack(Item.BOOK_AND_QUILL, 1), Item.BOOK, new ItemStack(Item.INK_SACK, 1, 0), Item.FEATHER);
        this.registerShapedRecipe(new ItemStack(Block.FENCE, 2), "###", "###", '#', Item.STICK);
        this.registerShapedRecipe(new ItemStack(Block.COBBLE_WALL, 6, 0), "###", "###", '#', Block.COBBLESTONE);
        this.registerShapedRecipe(new ItemStack(Block.COBBLE_WALL, 6, 1), "###", "###", '#', Block.MOSSY_COBBLESTONE);
        this.registerShapedRecipe(new ItemStack(Block.NETHER_FENCE, 6), "###", "###", '#', Block.NETHER_BRICK);
        this.registerShapedRecipe(new ItemStack(Block.FENCE_GATE, 1), "#W#", "#W#", '#', Item.STICK, 'W', Block.WOOD);
        this.registerShapedRecipe(new ItemStack(Block.JUKEBOX, 1), "###", "#X#", "###", '#', Block.WOOD, 'X', Item.DIAMOND);
        this.registerShapedRecipe(new ItemStack(Block.NOTE_BLOCK, 1), "###", "#X#", "###", '#', Block.WOOD, 'X', Item.REDSTONE);
        this.registerShapedRecipe(new ItemStack(Block.BOOKSHELF, 1), "###", "XXX", "###", '#', Block.WOOD, 'X', Item.BOOK);
        this.registerShapedRecipe(new ItemStack(Block.SNOW_BLOCK, 1), "##", "##", '#', Item.SNOW_BALL);
        this.registerShapedRecipe(new ItemStack(Block.SNOW, 6), "###", '#', Block.SNOW_BLOCK);
        this.registerShapedRecipe(new ItemStack(Block.CLAY, 1), "##", "##", '#', Item.CLAY_BALL);
        this.registerShapedRecipe(new ItemStack(Block.BRICK, 1), "##", "##", '#', Item.CLAY_BRICK);
        this.registerShapedRecipe(new ItemStack(Block.GLOWSTONE, 1), "##", "##", '#', Item.GLOWSTONE_DUST);
        this.registerShapedRecipe(new ItemStack(Block.QUARTZ_BLOCK, 1), "##", "##", '#', Item.QUARTZ);
        this.registerShapedRecipe(new ItemStack(Block.WOOL, 1), "##", "##", '#', Item.STRING);
        this.registerShapedRecipe(new ItemStack(Block.TNT, 1), "X#X", "#X#", "X#X", 'X', Item.SULPHUR, '#', Block.SAND);
        this.registerShapedRecipe(new ItemStack(Block.STEP, 6, 3), "###", '#', Block.COBBLESTONE);
        this.registerShapedRecipe(new ItemStack(Block.STEP, 6, 0), "###", '#', Block.STONE);
        this.registerShapedRecipe(new ItemStack(Block.STEP, 6, 1), "###", '#', Block.SANDSTONE);
        this.registerShapedRecipe(new ItemStack(Block.STEP, 6, 4), "###", '#', Block.BRICK);
        this.registerShapedRecipe(new ItemStack(Block.STEP, 6, 5), "###", '#', Block.SMOOTH_BRICK);
        this.registerShapedRecipe(new ItemStack(Block.STEP, 6, 6), "###", '#', Block.NETHER_BRICK);
        this.registerShapedRecipe(new ItemStack(Block.STEP, 6, 7), "###", '#', Block.QUARTZ_BLOCK);
        this.registerShapedRecipe(new ItemStack(Block.WOOD_STEP, 6, 0), "###", '#', new ItemStack(Block.WOOD, 1, 0));
        this.registerShapedRecipe(new ItemStack(Block.WOOD_STEP, 6, 2), "###", '#', new ItemStack(Block.WOOD, 1, 2));
        this.registerShapedRecipe(new ItemStack(Block.WOOD_STEP, 6, 1), "###", '#', new ItemStack(Block.WOOD, 1, 1));
        this.registerShapedRecipe(new ItemStack(Block.WOOD_STEP, 6, 3), "###", '#', new ItemStack(Block.WOOD, 1, 3));
        this.registerShapedRecipe(new ItemStack(Block.LADDER, 3), "# #", "###", "# #", '#', Item.STICK);
        this.registerShapedRecipe(new ItemStack(Item.WOOD_DOOR, 1), "##", "##", "##", '#', Block.WOOD);
        this.registerShapedRecipe(new ItemStack(Block.TRAP_DOOR, 2), "###", "###", '#', Block.WOOD);
        this.registerShapedRecipe(new ItemStack(Item.IRON_DOOR, 1), "##", "##", "##", '#', Item.IRON_INGOT);
        this.registerShapedRecipe(new ItemStack(Item.SIGN, 3), "###", "###", " X ", '#', Block.WOOD, 'X', Item.STICK);
        this.registerShapedRecipe(new ItemStack(Item.CAKE, 1), "AAA", "BEB", "CCC", 'A', Item.MILK_BUCKET, 'B', Item.SUGAR, 'C', Item.WHEAT, 'E', Item.EGG);
        this.registerShapedRecipe(new ItemStack(Item.SUGAR, 1), "#", '#', Item.SUGAR_CANE);
        this.registerShapedRecipe(new ItemStack(Block.WOOD, 4, 0), "#", '#', new ItemStack(Block.LOG, 1, 0));
        this.registerShapedRecipe(new ItemStack(Block.WOOD, 4, 1), "#", '#', new ItemStack(Block.LOG, 1, 1));
        this.registerShapedRecipe(new ItemStack(Block.WOOD, 4, 2), "#", '#', new ItemStack(Block.LOG, 1, 2));
        this.registerShapedRecipe(new ItemStack(Block.WOOD, 4, 3), "#", '#', new ItemStack(Block.LOG, 1, 3));
        this.registerShapedRecipe(new ItemStack(Item.STICK, 4), "#", "#", '#', Block.WOOD);
        this.registerShapedRecipe(new ItemStack(Block.TORCH, 4), "X", "#", 'X', Item.COAL, '#', Item.STICK);
        this.registerShapedRecipe(new ItemStack(Block.TORCH, 4), "X", "#", 'X', new ItemStack(Item.COAL, 1, 1), '#', Item.STICK);
        this.registerShapedRecipe(new ItemStack(Item.BOWL, 4), "# #", " # ", '#', Block.WOOD);
        this.registerShapedRecipe(new ItemStack(Item.GLASS_BOTTLE, 3), "# #", " # ", '#', Block.GLASS);
        this.registerShapedRecipe(new ItemStack(Block.RAILS, 16), "X X", "X#X", "X X", 'X', Item.IRON_INGOT, '#', Item.STICK);
        this.registerShapedRecipe(new ItemStack(Block.GOLDEN_RAIL, 6), "X X", "X#X", "XRX", 'X', Item.GOLD_INGOT, 'R', Item.REDSTONE, '#', Item.STICK);
        this.registerShapedRecipe(new ItemStack(Block.ACTIVATOR_RAIL, 6), "XSX", "X#X", "XSX", 'X', Item.IRON_INGOT, '#', Block.REDSTONE_TORCH_ON, 'S', Item.STICK);
        this.registerShapedRecipe(new ItemStack(Block.DETECTOR_RAIL, 6), "X X", "X#X", "XRX", 'X', Item.IRON_INGOT, 'R', Item.REDSTONE, '#', Block.STONE_PLATE);
        this.registerShapedRecipe(new ItemStack(Item.MINECART, 1), "# #", "###", '#', Item.IRON_INGOT);
        this.registerShapedRecipe(new ItemStack(Item.CAULDRON, 1), "# #", "# #", "###", '#', Item.IRON_INGOT);
        this.registerShapedRecipe(new ItemStack(Item.BREWING_STAND, 1), " B ", "###", '#', Block.COBBLESTONE, 'B', Item.BLAZE_ROD);
        this.registerShapedRecipe(new ItemStack(Block.JACK_O_LANTERN, 1), "A", "B", 'A', Block.PUMPKIN, 'B', Block.TORCH);
        this.registerShapedRecipe(new ItemStack(Item.STORAGE_MINECART, 1), "A", "B", 'A', Block.CHEST, 'B', Item.MINECART);
        this.registerShapedRecipe(new ItemStack(Item.POWERED_MINECART, 1), "A", "B", 'A', Block.FURNACE, 'B', Item.MINECART);
        this.registerShapedRecipe(new ItemStack(Item.MINECART_TNT, 1), "A", "B", 'A', Block.TNT, 'B', Item.MINECART);
        this.registerShapedRecipe(new ItemStack(Item.MINECART_HOPPER, 1), "A", "B", 'A', Block.HOPPER, 'B', Item.MINECART);
        this.registerShapedRecipe(new ItemStack(Item.BOAT, 1), "# #", "###", '#', Block.WOOD);
        this.registerShapedRecipe(new ItemStack(Item.BUCKET, 1), "# #", " # ", '#', Item.IRON_INGOT);
        this.registerShapedRecipe(new ItemStack(Item.FLOWER_POT, 1), "# #", " # ", '#', Item.CLAY_BRICK);
        this.registerShapedRecipe(new ItemStack(Item.FLINT_AND_STEEL, 1), "A ", " B", 'A', Item.IRON_INGOT, 'B', Item.FLINT);
        this.registerShapedRecipe(new ItemStack(Item.BREAD, 1), "###", '#', Item.WHEAT);
        this.registerShapedRecipe(new ItemStack(Block.WOOD_STAIRS, 4), "#  ", "## ", "###", '#', new ItemStack(Block.WOOD, 1, 0));
        this.registerShapedRecipe(new ItemStack(Block.BIRCH_WOOD_STAIRS, 4), "#  ", "## ", "###", '#', new ItemStack(Block.WOOD, 1, 2));
        this.registerShapedRecipe(new ItemStack(Block.SPRUCE_WOOD_STAIRS, 4), "#  ", "## ", "###", '#', new ItemStack(Block.WOOD, 1, 1));
        this.registerShapedRecipe(new ItemStack(Block.JUNGLE_WOOD_STAIRS, 4), "#  ", "## ", "###", '#', new ItemStack(Block.WOOD, 1, 3));
        this.registerShapedRecipe(new ItemStack(Item.FISHING_ROD, 1), "  #", " #X", "# X", '#', Item.STICK, 'X', Item.STRING);
        this.registerShapedRecipe(new ItemStack(Item.CARROT_STICK, 1), "# ", " X", '#', Item.FISHING_ROD, 'X', Item.CARROT).c();
        this.registerShapedRecipe(new ItemStack(Block.COBBLESTONE_STAIRS, 4), "#  ", "## ", "###", '#', Block.COBBLESTONE);
        this.registerShapedRecipe(new ItemStack(Block.BRICK_STAIRS, 4), "#  ", "## ", "###", '#', Block.BRICK);
        this.registerShapedRecipe(new ItemStack(Block.STONE_STAIRS, 4), "#  ", "## ", "###", '#', Block.SMOOTH_BRICK);
        this.registerShapedRecipe(new ItemStack(Block.NETHER_BRICK_STAIRS, 4), "#  ", "## ", "###", '#', Block.NETHER_BRICK);
        this.registerShapedRecipe(new ItemStack(Block.SANDSTONE_STAIRS, 4), "#  ", "## ", "###", '#', Block.SANDSTONE);
        this.registerShapedRecipe(new ItemStack(Block.QUARTZ_STAIRS, 4), "#  ", "## ", "###", '#', Block.QUARTZ_BLOCK);
        this.registerShapedRecipe(new ItemStack(Item.PAINTING, 1), "###", "#X#", "###", '#', Item.STICK, 'X', Block.WOOL);
        this.registerShapedRecipe(new ItemStack(Item.ITEM_FRAME, 1), "###", "#X#", "###", '#', Item.STICK, 'X', Item.LEATHER);
        this.registerShapedRecipe(new ItemStack(Item.GOLDEN_APPLE, 1, 0), "###", "#X#", "###", '#', Item.GOLD_NUGGET, 'X', Item.APPLE);
        this.registerShapedRecipe(new ItemStack(Item.GOLDEN_APPLE, 1, 1), "###", "#X#", "###", '#', Block.GOLD_BLOCK, 'X', Item.APPLE);
        this.registerShapedRecipe(new ItemStack(Item.CARROT_GOLDEN, 1, 0), "###", "#X#", "###", '#', Item.GOLD_NUGGET, 'X', Item.CARROT);
        this.registerShapedRecipe(new ItemStack(Block.LEVER, 1), "X", "#", '#', Block.COBBLESTONE, 'X', Item.STICK);
        this.registerShapedRecipe(new ItemStack(Block.TRIPWIRE_SOURCE, 2), "I", "S", "#", '#', Block.WOOD, 'S', Item.STICK, 'I', Item.IRON_INGOT);
        this.registerShapedRecipe(new ItemStack(Block.REDSTONE_TORCH_ON, 1), "X", "#", '#', Item.STICK, 'X', Item.REDSTONE);
        this.registerShapedRecipe(new ItemStack(Item.DIODE, 1), "#X#", "III", '#', Block.REDSTONE_TORCH_ON, 'X', Item.REDSTONE, 'I', Block.STONE);
        this.registerShapedRecipe(new ItemStack(Item.REDSTONE_COMPARATOR, 1), " # ", "#X#", "III", '#', Block.REDSTONE_TORCH_ON, 'X', Item.QUARTZ, 'I', Block.STONE);
        this.registerShapedRecipe(new ItemStack(Item.WATCH, 1), " # ", "#X#", " # ", '#', Item.GOLD_INGOT, 'X', Item.REDSTONE);
        this.registerShapedRecipe(new ItemStack(Item.COMPASS, 1), " # ", "#X#", " # ", '#', Item.IRON_INGOT, 'X', Item.REDSTONE);
        this.registerShapedRecipe(new ItemStack(Item.MAP_EMPTY, 1), "###", "#X#", "###", '#', Item.PAPER, 'X', Item.COMPASS);
        this.registerShapedRecipe(new ItemStack(Block.STONE_BUTTON, 1), "#", '#', Block.STONE);
        this.registerShapedRecipe(new ItemStack(Block.WOOD_BUTTON, 1), "#", '#', Block.WOOD);
        this.registerShapedRecipe(new ItemStack(Block.STONE_PLATE, 1), "##", '#', Block.STONE);
        this.registerShapedRecipe(new ItemStack(Block.WOOD_PLATE, 1), "##", '#', Block.WOOD);
        this.registerShapedRecipe(new ItemStack(Block.IRON_PLATE, 1), "##", '#', Item.IRON_INGOT);
        this.registerShapedRecipe(new ItemStack(Block.GOLD_PLATE, 1), "##", '#', Item.GOLD_INGOT);
        this.registerShapedRecipe(new ItemStack(Block.DISPENSER, 1), "###", "#X#", "#R#", '#', Block.COBBLESTONE, 'X', Item.BOW, 'R', Item.REDSTONE);
        this.registerShapedRecipe(new ItemStack(Block.DROPPER, 1), "###", "# #", "#R#", '#', Block.COBBLESTONE, 'R', Item.REDSTONE);
        this.registerShapedRecipe(new ItemStack(Block.PISTON, 1), "TTT", "#X#", "#R#", '#', Block.COBBLESTONE, 'X', Item.IRON_INGOT, 'R', Item.REDSTONE, 'T', Block.WOOD);
        this.registerShapedRecipe(new ItemStack(Block.PISTON_STICKY, 1), "S", "P", 'S', Item.SLIME_BALL, 'P', Block.PISTON);
        this.registerShapedRecipe(new ItemStack(Item.BED, 1), "###", "XXX", '#', Block.WOOL, 'X', Block.WOOD);
        this.registerShapedRecipe(new ItemStack(Block.ENCHANTMENT_TABLE, 1), " B ", "D#D", "###", '#', Block.OBSIDIAN, 'B', Item.BOOK, 'D', Item.DIAMOND);
        this.registerShapedRecipe(new ItemStack(Block.ANVIL, 1), "III", " i ", "iii", 'I', Block.IRON_BLOCK, 'i', Item.IRON_INGOT);
        this.registerShapelessRecipe(new ItemStack(Item.EYE_OF_ENDER, 1), Item.ENDER_PEARL, Item.BLAZE_POWDER);
        this.registerShapelessRecipe(new ItemStack(Item.FIREBALL, 3), Item.SULPHUR, Item.BLAZE_POWDER, Item.COAL);
        this.registerShapelessRecipe(new ItemStack(Item.FIREBALL, 3), Item.SULPHUR, Item.BLAZE_POWDER, new ItemStack(Item.COAL, 1, 1));
        this.registerShapedRecipe(new ItemStack(Block.DAYLIGHT_DETECTOR), "GGG", "QQQ", "WWW", 'G', Block.GLASS, 'Q', Item.QUARTZ, 'W', Block.WOOD_STEP);
        this.registerShapedRecipe(new ItemStack(Block.HOPPER), "I I", "ICI", " I ", 'I', Item.IRON_INGOT, 'C', Block.CHEST);
        this.sort();
        System.out.println(this.recipes.size() + " recipes");
    }
    
    public void sort() {
        Collections.sort((List<Object>)this.recipes, new RecipeSorter(this));
    }
    
    public ShapedRecipes registerShapedRecipe(final ItemStack itemstack, final Object... aobject) {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;
        if (aobject[i] instanceof String[]) {
            final String[] astring = (String[])aobject[i++];
            for (int l = 0; l < astring.length; ++l) {
                final String s2 = astring[l];
                ++k;
                j = s2.length();
                s += s2;
            }
        }
        else {
            while (aobject[i] instanceof String) {
                final String s3 = (String)aobject[i++];
                ++k;
                j = s3.length();
                s += s3;
            }
        }
        final HashMap hashmap = new HashMap();
        while (i < aobject.length) {
            final Character character = (Character)aobject[i];
            ItemStack itemstack2 = null;
            if (aobject[i + 1] instanceof Item) {
                itemstack2 = new ItemStack((Item)aobject[i + 1]);
            }
            else if (aobject[i + 1] instanceof Block) {
                itemstack2 = new ItemStack((Block)aobject[i + 1], 1, 32767);
            }
            else if (aobject[i + 1] instanceof ItemStack) {
                itemstack2 = (ItemStack)aobject[i + 1];
            }
            hashmap.put(character, itemstack2);
            i += 2;
        }
        final ItemStack[] aitemstack = new ItemStack[j * k];
        for (int i2 = 0; i2 < j * k; ++i2) {
            final char c0 = s.charAt(i2);
            if (hashmap.containsKey(c0)) {
                aitemstack[i2] = hashmap.get(c0).cloneItemStack();
            }
            else {
                aitemstack[i2] = null;
            }
        }
        final ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, aitemstack, itemstack);
        this.recipes.add(shapedrecipes);
        return shapedrecipes;
    }
    
    public void registerShapelessRecipe(final ItemStack itemstack, final Object... aobject) {
        final ArrayList arraylist = new ArrayList();
        for (final Object object : aobject) {
            if (object instanceof ItemStack) {
                arraylist.add(((ItemStack)object).cloneItemStack());
            }
            else if (object instanceof Item) {
                arraylist.add(new ItemStack((Item)object));
            }
            else {
                if (!(object instanceof Block)) {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }
                arraylist.add(new ItemStack((Block)object));
            }
        }
        this.recipes.add(new ShapelessRecipes(itemstack, arraylist));
    }
    
    public ItemStack craft(final InventoryCrafting inventorycrafting, final World world) {
        int i = 0;
        ItemStack itemstack = null;
        ItemStack itemstack2 = null;
        for (int j = 0; j < inventorycrafting.getSize(); ++j) {
            final ItemStack itemstack3 = inventorycrafting.getItem(j);
            if (itemstack3 != null) {
                if (i == 0) {
                    itemstack = itemstack3;
                }
                if (i == 1) {
                    itemstack2 = itemstack3;
                }
                ++i;
            }
        }
        if (i == 2 && itemstack.id == itemstack2.id && itemstack.count == 1 && itemstack2.count == 1 && Item.byId[itemstack.id].usesDurability()) {
            final Item item = Item.byId[itemstack.id];
            final int k = item.getMaxDurability() - itemstack.j();
            final int l = item.getMaxDurability() - itemstack2.j();
            final int i2 = k + l + item.getMaxDurability() * 5 / 100;
            int j2 = item.getMaxDurability() - i2;
            if (j2 < 0) {
                j2 = 0;
            }
            ItemStack result = new ItemStack(itemstack.id, 1, j2);
            final List<ItemStack> ingredients = new ArrayList<ItemStack>();
            ingredients.add(itemstack.cloneItemStack());
            ingredients.add(itemstack2.cloneItemStack());
            final ShapelessRecipes recipe = new ShapelessRecipes(result.cloneItemStack(), ingredients);
            inventorycrafting.currentRecipe = recipe;
            result = CraftEventFactory.callPreCraftEvent(inventorycrafting, result, this.lastCraftView, true);
            return result;
        }
        for (int j = 0; j < this.recipes.size(); ++j) {
            final IRecipe irecipe = this.recipes.get(j);
            if (irecipe.a(inventorycrafting, world)) {
                inventorycrafting.currentRecipe = irecipe;
                final ItemStack result2 = irecipe.a(inventorycrafting);
                return CraftEventFactory.callPreCraftEvent(inventorycrafting, result2, this.lastCraftView, false);
            }
        }
        return null;
    }
    
    public List getRecipes() {
        return this.recipes;
    }
    
    static {
        a = new CraftingManager();
    }
}
