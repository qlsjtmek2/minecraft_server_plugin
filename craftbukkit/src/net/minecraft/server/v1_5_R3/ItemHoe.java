// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemHoe extends Item
{
    protected EnumToolMaterial a;
    
    public ItemHoe(final int i, final EnumToolMaterial enumtoolmaterial) {
        super(i);
        this.a = enumtoolmaterial;
        this.maxStackSize = 1;
        this.setMaxDurability(enumtoolmaterial.a());
        this.a(CreativeModeTab.i);
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2) {
        if (!entityhuman.a(i, j, k, l, itemstack)) {
            return false;
        }
        final int i2 = world.getTypeId(i, j, k);
        final int j2 = world.getTypeId(i, j + 1, k);
        if ((l == 0 || j2 != 0 || i2 != Block.GRASS.id) && i2 != Block.DIRT.id) {
            return false;
        }
        final Block block = Block.SOIL;
        world.makeSound(i + 0.5f, j + 0.5f, k + 0.5f, block.stepSound.getStepSound(), (block.stepSound.getVolume1() + 1.0f) / 2.0f, block.stepSound.getVolume2() * 0.8f);
        if (world.isStatic) {
            return true;
        }
        if (!ItemBlock.processBlockPlace(world, entityhuman, null, i, j, k, block.id, 0, i, j, k)) {
            return false;
        }
        itemstack.damage(1, entityhuman);
        return true;
    }
    
    public String g() {
        return this.a.toString();
    }
}
