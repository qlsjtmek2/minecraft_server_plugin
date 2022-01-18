// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ItemTool extends Item
{
    private Block[] c;
    protected float a;
    private int d;
    protected EnumToolMaterial b;
    
    protected ItemTool(final int n, final int n2, final EnumToolMaterial b, final Block[] c) {
        super(n);
        this.a = 4.0f;
        this.b = b;
        this.c = c;
        this.maxStackSize = 1;
        this.setMaxDurability(b.a());
        this.a = b.b();
        this.d = n2 + b.c();
        this.a(CreativeModeTab.i);
    }
    
    public float getDestroySpeed(final ItemStack itemStack, final Block block) {
        for (int i = 0; i < this.c.length; ++i) {
            if (this.c[i] == block) {
                return this.a;
            }
        }
        return 1.0f;
    }
    
    public boolean a(final ItemStack itemStack, final EntityLiving entityLiving, final EntityLiving entityliving) {
        itemStack.damage(2, entityliving);
        return true;
    }
    
    public boolean a(final ItemStack itemStack, final World world, final int n, final int i, final int j, final int k, final EntityLiving entityliving) {
        if (Block.byId[n].l(world, i, j, k) != 0.0) {
            itemStack.damage(1, entityliving);
        }
        return true;
    }
    
    public int a(final Entity entity) {
        return this.d;
    }
    
    public int c() {
        return this.b.e();
    }
    
    public String g() {
        return this.b.toString();
    }
    
    public boolean a(final ItemStack itemStack, final ItemStack itemStack2) {
        return this.b.f() == itemStack2.id || super.a(itemStack, itemStack2);
    }
}
