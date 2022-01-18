// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemPickaxe extends ItemTool
{
    private static Block[] c;
    
    protected ItemPickaxe(final int n, final EnumToolMaterial enumToolMaterial) {
        super(n, 2, enumToolMaterial, ItemPickaxe.c);
    }
    
    public boolean canDestroySpecialBlock(final Block block) {
        if (block == Block.OBSIDIAN) {
            return this.b.d() == 3;
        }
        if (block == Block.DIAMOND_BLOCK || block == Block.DIAMOND_ORE) {
            return this.b.d() >= 2;
        }
        if (block == Block.EMERALD_ORE || block == Block.EMERALD_BLOCK) {
            return this.b.d() >= 2;
        }
        if (block == Block.GOLD_BLOCK || block == Block.GOLD_ORE) {
            return this.b.d() >= 2;
        }
        if (block == Block.IRON_BLOCK || block == Block.IRON_ORE) {
            return this.b.d() >= 1;
        }
        if (block == Block.LAPIS_BLOCK || block == Block.LAPIS_ORE) {
            return this.b.d() >= 1;
        }
        if (block == Block.REDSTONE_ORE || block == Block.GLOWING_REDSTONE_ORE) {
            return this.b.d() >= 2;
        }
        return block.material == Material.STONE || block.material == Material.ORE || block.material == Material.HEAVY;
    }
    
    public float getDestroySpeed(final ItemStack itemStack, final Block block) {
        if (block != null && (block.material == Material.ORE || block.material == Material.HEAVY || block.material == Material.STONE)) {
            return this.a;
        }
        return super.getDestroySpeed(itemStack, block);
    }
    
    static {
        ItemPickaxe.c = new Block[] { Block.COBBLESTONE, Block.DOUBLE_STEP, Block.STEP, Block.STONE, Block.SANDSTONE, Block.MOSSY_COBBLESTONE, Block.IRON_ORE, Block.IRON_BLOCK, Block.COAL_ORE, Block.GOLD_BLOCK, Block.GOLD_ORE, Block.DIAMOND_ORE, Block.DIAMOND_BLOCK, Block.ICE, Block.NETHERRACK, Block.LAPIS_ORE, Block.LAPIS_BLOCK, Block.REDSTONE_ORE, Block.GLOWING_REDSTONE_ORE, Block.RAILS, Block.DETECTOR_RAIL, Block.GOLDEN_RAIL, Block.ACTIVATOR_RAIL };
    }
}
