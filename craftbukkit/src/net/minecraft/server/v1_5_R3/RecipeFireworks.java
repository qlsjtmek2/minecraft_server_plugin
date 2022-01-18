// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class RecipeFireworks extends ShapelessRecipes implements IRecipe
{
    private ItemStack a;
    
    public RecipeFireworks() {
        super(new ItemStack(Item.FIREWORKS, 0, 0), Arrays.asList(new ItemStack(Item.SULPHUR, 0, 5)));
    }
    
    public boolean a(final InventoryCrafting inventorycrafting, final World world) {
        this.a = null;
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        int i2 = 0;
        int j2 = 0;
        for (int k2 = 0; k2 < inventorycrafting.getSize(); ++k2) {
            final ItemStack itemstack = inventorycrafting.getItem(k2);
            if (itemstack != null) {
                if (itemstack.id == Item.SULPHUR.id) {
                    ++j;
                }
                else if (itemstack.id == Item.FIREWORKS_CHARGE.id) {
                    ++l;
                }
                else if (itemstack.id == Item.INK_SACK.id) {
                    ++k;
                }
                else if (itemstack.id == Item.PAPER.id) {
                    ++i;
                }
                else if (itemstack.id == Item.GLOWSTONE_DUST.id) {
                    ++i2;
                }
                else if (itemstack.id == Item.DIAMOND.id) {
                    ++i2;
                }
                else if (itemstack.id == Item.FIREBALL.id) {
                    ++j2;
                }
                else if (itemstack.id == Item.FEATHER.id) {
                    ++j2;
                }
                else if (itemstack.id == Item.GOLD_NUGGET.id) {
                    ++j2;
                }
                else {
                    if (itemstack.id != Item.SKULL.id) {
                        return false;
                    }
                    ++j2;
                }
            }
        }
        i2 += k + j2;
        if (j > 3 || i > 1) {
            return false;
        }
        if (j >= 1 && i == 1 && i2 == 0) {
            this.a = new ItemStack(Item.FIREWORKS);
            if (l > 0) {
                final NBTTagCompound nbttagcompound = new NBTTagCompound();
                final NBTTagCompound nbttagcompound2 = new NBTTagCompound("Fireworks");
                final NBTTagList nbttaglist = new NBTTagList("Explosions");
                for (int l2 = 0; l2 < inventorycrafting.getSize(); ++l2) {
                    final ItemStack itemstack2 = inventorycrafting.getItem(l2);
                    if (itemstack2 != null && itemstack2.id == Item.FIREWORKS_CHARGE.id && itemstack2.hasTag() && itemstack2.getTag().hasKey("Explosion")) {
                        nbttaglist.add(itemstack2.getTag().getCompound("Explosion"));
                    }
                }
                nbttagcompound2.set("Explosions", nbttaglist);
                nbttagcompound2.setByte("Flight", (byte)j);
                nbttagcompound.set("Fireworks", nbttagcompound2);
                this.a.setTag(nbttagcompound);
            }
            return true;
        }
        if (j == 1 && i == 0 && l == 0 && k > 0 && j2 <= 1) {
            this.a = new ItemStack(Item.FIREWORKS_CHARGE);
            final NBTTagCompound nbttagcompound = new NBTTagCompound();
            final NBTTagCompound nbttagcompound2 = new NBTTagCompound("Explosion");
            byte b0 = 0;
            final ArrayList arraylist = new ArrayList();
            for (int i3 = 0; i3 < inventorycrafting.getSize(); ++i3) {
                final ItemStack itemstack3 = inventorycrafting.getItem(i3);
                if (itemstack3 != null) {
                    if (itemstack3.id == Item.INK_SACK.id) {
                        arraylist.add(ItemDye.c[itemstack3.getData()]);
                    }
                    else if (itemstack3.id == Item.GLOWSTONE_DUST.id) {
                        nbttagcompound2.setBoolean("Flicker", true);
                    }
                    else if (itemstack3.id == Item.DIAMOND.id) {
                        nbttagcompound2.setBoolean("Trail", true);
                    }
                    else if (itemstack3.id == Item.FIREBALL.id) {
                        b0 = 1;
                    }
                    else if (itemstack3.id == Item.FEATHER.id) {
                        b0 = 4;
                    }
                    else if (itemstack3.id == Item.GOLD_NUGGET.id) {
                        b0 = 2;
                    }
                    else if (itemstack3.id == Item.SKULL.id) {
                        b0 = 3;
                    }
                }
            }
            final int[] aint = new int[arraylist.size()];
            for (int j3 = 0; j3 < aint.length; ++j3) {
                aint[j3] = arraylist.get(j3);
            }
            nbttagcompound2.setIntArray("Colors", aint);
            nbttagcompound2.setByte("Type", b0);
            nbttagcompound.set("Explosion", nbttagcompound2);
            this.a.setTag(nbttagcompound);
            return true;
        }
        if (j != 0 || i != 0 || l != 1 || k <= 0 || k != i2) {
            return false;
        }
        final ArrayList arraylist2 = new ArrayList();
        for (int k3 = 0; k3 < inventorycrafting.getSize(); ++k3) {
            final ItemStack itemstack4 = inventorycrafting.getItem(k3);
            if (itemstack4 != null) {
                if (itemstack4.id == Item.INK_SACK.id) {
                    arraylist2.add(ItemDye.c[itemstack4.getData()]);
                }
                else if (itemstack4.id == Item.FIREWORKS_CHARGE.id) {
                    this.a = itemstack4.cloneItemStack();
                    this.a.count = 1;
                }
            }
        }
        final int[] aint2 = new int[arraylist2.size()];
        for (int l3 = 0; l3 < aint2.length; ++l3) {
            aint2[l3] = arraylist2.get(l3);
        }
        if (this.a == null || !this.a.hasTag()) {
            return false;
        }
        final NBTTagCompound nbttagcompound3 = this.a.getTag().getCompound("Explosion");
        if (nbttagcompound3 == null) {
            return false;
        }
        nbttagcompound3.setIntArray("FadeColors", aint2);
        return true;
    }
    
    public ItemStack a(final InventoryCrafting inventorycrafting) {
        return this.a.cloneItemStack();
    }
    
    public int a() {
        return 10;
    }
    
    public ItemStack b() {
        return this.a;
    }
}
