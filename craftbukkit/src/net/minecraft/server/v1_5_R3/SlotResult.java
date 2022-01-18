// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class SlotResult extends Slot
{
    private final IInventory a;
    private EntityHuman b;
    private int c;
    
    public SlotResult(final EntityHuman b, final IInventory a, final IInventory iinventory, final int i, final int j, final int k) {
        super(iinventory, i, j, k);
        this.b = b;
        this.a = a;
    }
    
    public boolean isAllowed(final ItemStack itemStack) {
        return false;
    }
    
    public ItemStack a(final int i) {
        if (this.d()) {
            this.c += Math.min(i, this.getItem().count);
        }
        return super.a(i);
    }
    
    protected void a(final ItemStack itemStack, final int n) {
        this.c += n;
        this.b(itemStack);
    }
    
    protected void b(final ItemStack itemStack) {
        itemStack.a(this.b.world, this.b, this.c);
        this.c = 0;
        if (itemStack.id == Block.WORKBENCH.id) {
            this.b.a(AchievementList.h, 1);
        }
        else if (itemStack.id == Item.WOOD_PICKAXE.id) {
            this.b.a(AchievementList.i, 1);
        }
        else if (itemStack.id == Block.FURNACE.id) {
            this.b.a(AchievementList.j, 1);
        }
        else if (itemStack.id == Item.WOOD_HOE.id) {
            this.b.a(AchievementList.l, 1);
        }
        else if (itemStack.id == Item.BREAD.id) {
            this.b.a(AchievementList.m, 1);
        }
        else if (itemStack.id == Item.CAKE.id) {
            this.b.a(AchievementList.n, 1);
        }
        else if (itemStack.id == Item.STONE_PICKAXE.id) {
            this.b.a(AchievementList.o, 1);
        }
        else if (itemStack.id == Item.WOOD_SWORD.id) {
            this.b.a(AchievementList.r, 1);
        }
        else if (itemStack.id == Block.ENCHANTMENT_TABLE.id) {
            this.b.a(AchievementList.D, 1);
        }
        else if (itemStack.id == Block.BOOKSHELF.id) {
            this.b.a(AchievementList.F, 1);
        }
    }
    
    public void a(final EntityHuman entityHuman, final ItemStack itemStack) {
        this.b(itemStack);
        for (int i = 0; i < this.a.getSize(); ++i) {
            final ItemStack item = this.a.getItem(i);
            if (item != null) {
                this.a.splitStack(i, 1);
                if (item.getItem().t()) {
                    final ItemStack itemStack2 = new ItemStack(item.getItem().s());
                    if (!item.getItem().j(item) || !this.b.inventory.pickup(itemStack2)) {
                        if (this.a.getItem(i) == null) {
                            this.a.setItem(i, itemStack2);
                        }
                        else {
                            this.b.drop(itemStack2);
                        }
                    }
                }
            }
        }
    }
}
