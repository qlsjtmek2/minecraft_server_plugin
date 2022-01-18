// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemShears extends Item
{
    public ItemShears(final int n) {
        super(n);
        this.d(1);
        this.setMaxDurability(238);
        this.a(CreativeModeTab.i);
    }
    
    public boolean a(final ItemStack itemStack, final World world, final int n, final int n2, final int n3, final int n4, final EntityLiving entityliving) {
        if (n == Block.LEAVES.id || n == Block.WEB.id || n == Block.LONG_GRASS.id || n == Block.VINE.id || n == Block.TRIPWIRE.id) {
            itemStack.damage(1, entityliving);
            return true;
        }
        return super.a(itemStack, world, n, n2, n3, n4, entityliving);
    }
    
    public boolean canDestroySpecialBlock(final Block block) {
        return block.id == Block.WEB.id || block.id == Block.REDSTONE_WIRE.id || block.id == Block.TRIPWIRE.id;
    }
    
    public float getDestroySpeed(final ItemStack itemStack, final Block block) {
        if (block.id == Block.WEB.id || block.id == Block.LEAVES.id) {
            return 15.0f;
        }
        if (block.id == Block.WOOL.id) {
            return 5.0f;
        }
        return super.getDestroySpeed(itemStack, block);
    }
}
