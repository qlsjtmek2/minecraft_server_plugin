// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockPotatoes extends BlockCrops
{
    public BlockPotatoes(final int i) {
        super(i);
    }
    
    protected int j() {
        return Item.POTATO.id;
    }
    
    protected int k() {
        return Item.POTATO.id;
    }
    
    public void dropNaturally(final World world, final int n, final int n2, final int n3, final int l, final float f, final int i1) {
        super.dropNaturally(world, n, n2, n3, l, f, i1);
        if (world.isStatic) {
            return;
        }
        if (l >= 7 && world.random.nextInt(50) == 0) {
            this.b(world, n, n2, n3, new ItemStack(Item.POTATO_POISON));
        }
    }
}
