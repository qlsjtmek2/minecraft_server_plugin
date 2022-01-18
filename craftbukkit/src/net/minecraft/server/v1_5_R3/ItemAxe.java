// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemAxe extends ItemTool
{
    private static Block[] c;
    
    protected ItemAxe(final int n, final EnumToolMaterial enumToolMaterial) {
        super(n, 3, enumToolMaterial, ItemAxe.c);
    }
    
    public float getDestroySpeed(final ItemStack itemStack, final Block block) {
        if (block != null && (block.material == Material.WOOD || block.material == Material.PLANT || block.material == Material.REPLACEABLE_PLANT)) {
            return this.a;
        }
        return super.getDestroySpeed(itemStack, block);
    }
    
    static {
        ItemAxe.c = new Block[] { Block.WOOD, Block.BOOKSHELF, Block.LOG, Block.CHEST, Block.DOUBLE_STEP, Block.STEP, Block.PUMPKIN, Block.JACK_O_LANTERN };
    }
}
