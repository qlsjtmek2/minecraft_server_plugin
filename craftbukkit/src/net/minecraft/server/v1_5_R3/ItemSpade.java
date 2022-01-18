// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemSpade extends ItemTool
{
    private static Block[] c;
    
    public ItemSpade(final int n, final EnumToolMaterial enumToolMaterial) {
        super(n, 1, enumToolMaterial, ItemSpade.c);
    }
    
    public boolean canDestroySpecialBlock(final Block block) {
        return block == Block.SNOW || block == Block.SNOW_BLOCK;
    }
    
    static {
        ItemSpade.c = new Block[] { Block.GRASS, Block.DIRT, Block.SAND, Block.GRAVEL, Block.SNOW, Block.SNOW_BLOCK, Block.CLAY, Block.SOIL, Block.SOUL_SAND, Block.MYCEL };
    }
}
