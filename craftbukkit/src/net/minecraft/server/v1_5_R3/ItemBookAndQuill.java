// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemBookAndQuill extends Item
{
    public ItemBookAndQuill(final int n) {
        super(n);
        this.d(1);
    }
    
    public ItemStack a(final ItemStack itemstack, final World world, final EntityHuman entityHuman) {
        entityHuman.d(itemstack);
        return itemstack;
    }
    
    public boolean r() {
        return true;
    }
    
    public static boolean a(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound == null) {
            return false;
        }
        if (!nbtTagCompound.hasKey("pages")) {
            return false;
        }
        final NBTTagList list = (NBTTagList)nbtTagCompound.get("pages");
        for (int i = 0; i < list.size(); ++i) {
            final NBTTagString nbtTagString = (NBTTagString)list.get(i);
            if (nbtTagString.data == null) {
                return false;
            }
            if (nbtTagString.data.length() > 256) {
                return false;
            }
        }
        return true;
    }
}
