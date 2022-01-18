// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemArmor extends Item
{
    private static final int[] cu;
    private static final String[] cv;
    public static final String[] a;
    private static final IDispenseBehavior cw;
    public final int b;
    public final int c;
    public final int d;
    private final EnumArmorMaterial cx;
    
    public ItemArmor(final int n, final EnumArmorMaterial cx, final int d, final int b) {
        super(n);
        this.cx = cx;
        this.b = b;
        this.d = d;
        this.c = cx.b(b);
        this.setMaxDurability(cx.a(b));
        this.maxStackSize = 1;
        this.a(CreativeModeTab.j);
        BlockDispenser.a.a(this, ItemArmor.cw);
    }
    
    public int c() {
        return this.cx.a();
    }
    
    public EnumArmorMaterial d() {
        return this.cx;
    }
    
    public boolean a(final ItemStack itemStack) {
        return this.cx == EnumArmorMaterial.CLOTH && itemStack.hasTag() && itemStack.getTag().hasKey("display") && itemStack.getTag().getCompound("display").hasKey("color");
    }
    
    public int b(final ItemStack itemStack) {
        if (this.cx != EnumArmorMaterial.CLOTH) {
            return -1;
        }
        final NBTTagCompound tag = itemStack.getTag();
        if (tag == null) {
            return 10511680;
        }
        final NBTTagCompound compound = tag.getCompound("display");
        if (compound == null) {
            return 10511680;
        }
        if (compound.hasKey("color")) {
            return compound.getInt("color");
        }
        return 10511680;
    }
    
    public void c(final ItemStack itemStack) {
        if (this.cx != EnumArmorMaterial.CLOTH) {
            return;
        }
        final NBTTagCompound tag = itemStack.getTag();
        if (tag == null) {
            return;
        }
        final NBTTagCompound compound = tag.getCompound("display");
        if (compound.hasKey("color")) {
            compound.remove("color");
        }
    }
    
    public void b(final ItemStack itemStack, final int n) {
        if (this.cx != EnumArmorMaterial.CLOTH) {
            throw new UnsupportedOperationException("Can't dye non-leather!");
        }
        NBTTagCompound tag = itemStack.getTag();
        if (tag == null) {
            tag = new NBTTagCompound();
            itemStack.setTag(tag);
        }
        final NBTTagCompound compound = tag.getCompound("display");
        if (!tag.hasKey("display")) {
            tag.setCompound("display", compound);
        }
        compound.setInt("color", n);
    }
    
    public boolean a(final ItemStack itemStack, final ItemStack itemStack2) {
        return this.cx.b() == itemStack2.id || super.a(itemStack, itemStack2);
    }
    
    public ItemStack a(final ItemStack itemstack, final World world, final EntityHuman entityHuman) {
        final int n = EntityLiving.b(itemstack) - 1;
        if (entityHuman.q(n) == null) {
            entityHuman.setEquipment(n, itemstack.cloneItemStack());
            itemstack.count = 0;
        }
        return itemstack;
    }
    
    static {
        cu = new int[] { 11, 16, 15, 13 };
        cv = new String[] { "helmetCloth_overlay", "chestplateCloth_overlay", "leggingsCloth_overlay", "bootsCloth_overlay" };
        a = new String[] { "slot_empty_helmet", "slot_empty_chestplate", "slot_empty_leggings", "slot_empty_boots" };
        cw = new DispenseBehaviorArmor();
    }
}
