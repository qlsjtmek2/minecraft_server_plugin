// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemSkull extends Item
{
    private static final String[] b;
    public static final String[] a;
    
    public ItemSkull(final int i) {
        super(i);
        this.a(CreativeModeTab.c);
        this.setMaxDurability(0);
        this.a(true);
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, int i, int j, int k, int l, final float f, final float f1, final float f2) {
        final int clickedX = i;
        final int clickedY = j;
        final int clickedZ = k;
        if (l == 0) {
            return false;
        }
        if (!world.getMaterial(i, j, k).isBuildable()) {
            return false;
        }
        if (l == 1) {
            ++j;
        }
        if (l == 2) {
            --k;
        }
        if (l == 3) {
            ++k;
        }
        if (l == 4) {
            --i;
        }
        if (l == 5) {
            ++i;
        }
        if (!entityhuman.a(i, j, k, l, itemstack)) {
            return false;
        }
        if (!Block.SKULL.canPlace(world, i, j, k)) {
            return false;
        }
        if (!ItemBlock.processBlockPlace(world, entityhuman, null, i, j, k, Block.SKULL.id, l, clickedX, clickedY, clickedZ)) {
            return false;
        }
        l = world.getData(i, j, k);
        int i2 = 0;
        if (l == 1) {
            i2 = (MathHelper.floor(entityhuman.yaw * 16.0f / 360.0f + 0.5) & 0xF);
        }
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity != null && tileentity instanceof TileEntitySkull) {
            String s = "";
            if (itemstack.hasTag() && itemstack.getTag().hasKey("SkullOwner")) {
                s = itemstack.getTag().getString("SkullOwner");
            }
            ((TileEntitySkull)tileentity).setSkullType(itemstack.getData(), s);
            ((TileEntitySkull)tileentity).setRotation(i2);
            ((BlockSkull)Block.SKULL).a(world, i, j, k, (TileEntitySkull)tileentity);
        }
        --itemstack.count;
        return true;
    }
    
    public int filterData(final int i) {
        return i;
    }
    
    public String d(final ItemStack itemstack) {
        int i = itemstack.getData();
        if (i < 0 || i >= ItemSkull.b.length) {
            i = 0;
        }
        return super.getName() + "." + ItemSkull.b[i];
    }
    
    public String l(final ItemStack itemstack) {
        return (itemstack.getData() == 3 && itemstack.hasTag() && itemstack.getTag().hasKey("SkullOwner")) ? LocaleI18n.get("item.skull.player.name", itemstack.getTag().getString("SkullOwner")) : super.l(itemstack);
    }
    
    static {
        b = new String[] { "skeleton", "wither", "zombie", "char", "creeper" };
        a = new String[] { "skull_skeleton", "skull_wither", "skull_zombie", "skull_char", "skull_creeper" };
    }
}
