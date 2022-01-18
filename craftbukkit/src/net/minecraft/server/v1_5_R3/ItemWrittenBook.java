// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemWrittenBook extends Item
{
    public ItemWrittenBook(final int n) {
        super(n);
        this.d(1);
    }
    
    public static boolean a(final NBTTagCompound nbtTagCompound) {
        if (!ItemBookAndQuill.a(nbtTagCompound)) {
            return false;
        }
        if (!nbtTagCompound.hasKey("title")) {
            return false;
        }
        final String string = nbtTagCompound.getString("title");
        return string != null && string.length() <= 16 && nbtTagCompound.hasKey("author");
    }
    
    public String l(final ItemStack itemStack) {
        if (itemStack.hasTag()) {
            final NBTTagString nbtTagString = (NBTTagString)itemStack.getTag().get("title");
            if (nbtTagString != null) {
                return nbtTagString.toString();
            }
        }
        return super.l(itemStack);
    }
    
    public ItemStack a(final ItemStack itemstack, final World world, final EntityHuman entityHuman) {
        entityHuman.d(itemstack);
        return itemstack;
    }
    
    public boolean r() {
        return true;
    }
}
