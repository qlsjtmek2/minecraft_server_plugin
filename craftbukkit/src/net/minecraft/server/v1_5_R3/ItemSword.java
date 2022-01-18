// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemSword extends Item
{
    private int damage;
    private final EnumToolMaterial b;
    
    public ItemSword(final int n, final EnumToolMaterial b) {
        super(n);
        this.b = b;
        this.maxStackSize = 1;
        this.setMaxDurability(b.a());
        this.a(CreativeModeTab.j);
        this.damage = 4 + b.c();
    }
    
    public int g() {
        return this.b.c();
    }
    
    public float getDestroySpeed(final ItemStack itemStack, final Block block) {
        if (block.id == Block.WEB.id) {
            return 15.0f;
        }
        final Material material = block.material;
        if (material == Material.PLANT || material == Material.REPLACEABLE_PLANT || material == Material.CORAL || material == Material.LEAVES || material == Material.PUMPKIN) {
            return 1.5f;
        }
        return 1.0f;
    }
    
    public boolean a(final ItemStack itemStack, final EntityLiving entityLiving, final EntityLiving entityliving) {
        itemStack.damage(1, entityliving);
        return true;
    }
    
    public boolean a(final ItemStack itemStack, final World world, final int n, final int i, final int j, final int k, final EntityLiving entityliving) {
        if (Block.byId[n].l(world, i, j, k) != 0.0) {
            itemStack.damage(2, entityliving);
        }
        return true;
    }
    
    public int a(final Entity entity) {
        return this.damage;
    }
    
    public EnumAnimation b_(final ItemStack itemStack) {
        return EnumAnimation.BLOCK;
    }
    
    public int c_(final ItemStack itemStack) {
        return 72000;
    }
    
    public ItemStack a(final ItemStack itemstack, final World world, final EntityHuman entityHuman) {
        entityHuman.a(itemstack, this.c_(itemstack));
        return itemstack;
    }
    
    public boolean canDestroySpecialBlock(final Block block) {
        return block.id == Block.WEB.id;
    }
    
    public int c() {
        return this.b.e();
    }
    
    public String h() {
        return this.b.toString();
    }
    
    public boolean a(final ItemStack itemStack, final ItemStack itemStack2) {
        return this.b.f() == itemStack2.id || super.a(itemStack, itemStack2);
    }
}
