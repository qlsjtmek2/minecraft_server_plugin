// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.concurrent.Callable;
import java.util.ArrayList;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import java.util.List;

public class PlayerInventory implements IInventory
{
    public ItemStack[] items;
    public ItemStack[] armor;
    public int itemInHandIndex;
    public EntityHuman player;
    private ItemStack g;
    public boolean e;
    public List<HumanEntity> transaction;
    private int maxStack;
    
    public ItemStack[] getContents() {
        return this.items;
    }
    
    public ItemStack[] getArmorContents() {
        return this.armor;
    }
    
    public void onOpen(final CraftHumanEntity who) {
        this.transaction.add(who);
    }
    
    public void onClose(final CraftHumanEntity who) {
        this.transaction.remove(who);
    }
    
    public List<HumanEntity> getViewers() {
        return this.transaction;
    }
    
    public InventoryHolder getOwner() {
        return this.player.getBukkitEntity();
    }
    
    public void setMaxStackSize(final int size) {
        this.maxStack = size;
    }
    
    public PlayerInventory(final EntityHuman entityhuman) {
        this.items = new ItemStack[36];
        this.armor = new ItemStack[4];
        this.itemInHandIndex = 0;
        this.e = false;
        this.transaction = new ArrayList<HumanEntity>();
        this.maxStack = 64;
        this.player = entityhuman;
    }
    
    public ItemStack getItemInHand() {
        return (this.itemInHandIndex < 9 && this.itemInHandIndex >= 0) ? this.items[this.itemInHandIndex] : null;
    }
    
    public static int getHotbarSize() {
        return 9;
    }
    
    private int h(final int i) {
        for (int j = 0; j < this.items.length; ++j) {
            if (this.items[j] != null && this.items[j].id == i) {
                return j;
            }
        }
        return -1;
    }
    
    private int firstPartial(final ItemStack itemstack) {
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] != null && this.items[i].id == itemstack.id && this.items[i].isStackable() && this.items[i].count < this.items[i].getMaxStackSize() && this.items[i].count < this.getMaxStackSize() && (!this.items[i].usesData() || this.items[i].getData() == itemstack.getData()) && ItemStack.equals(this.items[i], itemstack)) {
                return i;
            }
        }
        return -1;
    }
    
    public int canHold(final ItemStack itemstack) {
        int remains = itemstack.count;
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] == null) {
                return itemstack.count;
            }
            if (this.items[i] != null && this.items[i].id == itemstack.id && this.items[i].isStackable() && this.items[i].count < this.items[i].getMaxStackSize() && this.items[i].count < this.getMaxStackSize() && (!this.items[i].usesData() || this.items[i].getData() == itemstack.getData())) {
                remains -= ((this.items[i].getMaxStackSize() < this.getMaxStackSize()) ? this.items[i].getMaxStackSize() : this.getMaxStackSize()) - this.items[i].count;
            }
            if (remains <= 0) {
                return itemstack.count;
            }
        }
        return itemstack.count - remains;
    }
    
    public int j() {
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] == null) {
                return i;
            }
        }
        return -1;
    }
    
    public int b(final int i, final int j) {
        int k = 0;
        for (int l = 0; l < this.items.length; ++l) {
            final ItemStack itemstack = this.items[l];
            if (itemstack != null && (i <= -1 || itemstack.id == i) && (j <= -1 || itemstack.getData() == j)) {
                k += itemstack.count;
                this.items[l] = null;
            }
        }
        for (int l = 0; l < this.armor.length; ++l) {
            final ItemStack itemstack = this.armor[l];
            if (itemstack != null && (i <= -1 || itemstack.id == i) && (j <= -1 || itemstack.getData() == j)) {
                k += itemstack.count;
                this.armor[l] = null;
            }
        }
        return k;
    }
    
    private int e(final ItemStack itemstack) {
        final int i = itemstack.id;
        int j = itemstack.count;
        if (itemstack.getMaxStackSize() == 1) {
            final int k = this.j();
            if (k < 0) {
                return j;
            }
            if (this.items[k] == null) {
                this.items[k] = ItemStack.b(itemstack);
            }
            return 0;
        }
        else {
            int k = this.firstPartial(itemstack);
            if (k < 0) {
                k = this.j();
            }
            if (k < 0) {
                return j;
            }
            if (this.items[k] == null) {
                this.items[k] = new ItemStack(i, 0, itemstack.getData());
                if (itemstack.hasTag()) {
                    this.items[k].setTag((NBTTagCompound)itemstack.getTag().clone());
                }
            }
            int l;
            if ((l = j) > this.items[k].getMaxStackSize() - this.items[k].count) {
                l = this.items[k].getMaxStackSize() - this.items[k].count;
            }
            if (l > this.getMaxStackSize() - this.items[k].count) {
                l = this.getMaxStackSize() - this.items[k].count;
            }
            if (l == 0) {
                return j;
            }
            j -= l;
            final ItemStack itemStack = this.items[k];
            itemStack.count += l;
            this.items[k].b = 5;
            return j;
        }
    }
    
    public void k() {
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] != null) {
                this.items[i].a(this.player.world, this.player, i, this.itemInHandIndex == i);
            }
        }
    }
    
    public boolean d(final int i) {
        final int j = this.h(i);
        if (j < 0) {
            return false;
        }
        final ItemStack itemStack = this.items[j];
        if (--itemStack.count <= 0) {
            this.items[j] = null;
        }
        return true;
    }
    
    public boolean e(final int i) {
        final int j = this.h(i);
        return j >= 0;
    }
    
    public boolean pickup(final ItemStack itemstack) {
        if (itemstack == null) {
            return false;
        }
        try {
            if (itemstack.i()) {
                final int i = this.j();
                if (i >= 0) {
                    this.items[i] = ItemStack.b(itemstack);
                    this.items[i].b = 5;
                    itemstack.count = 0;
                    return true;
                }
                if (this.player.abilities.canInstantlyBuild) {
                    itemstack.count = 0;
                    return true;
                }
                return false;
            }
            else {
                int i;
                do {
                    i = itemstack.count;
                    itemstack.count = this.e(itemstack);
                } while (itemstack.count > 0 && itemstack.count < i);
                if (itemstack.count == i && this.player.abilities.canInstantlyBuild) {
                    itemstack.count = 0;
                    return true;
                }
                return itemstack.count < i;
            }
        }
        catch (Throwable throwable) {
            final CrashReport crashreport = CrashReport.a(throwable, "Adding item to inventory");
            final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Item being added");
            crashreportsystemdetails.a("Item ID", itemstack.id);
            crashreportsystemdetails.a("Item data", itemstack.getData());
            crashreportsystemdetails.a("Item name", new CrashReportItemName(this, itemstack));
            throw new ReportedException(crashreport);
        }
    }
    
    public ItemStack splitStack(int i, final int j) {
        ItemStack[] aitemstack = this.items;
        if (i >= this.items.length) {
            aitemstack = this.armor;
            i -= this.items.length;
        }
        if (aitemstack[i] == null) {
            return null;
        }
        if (aitemstack[i].count <= j) {
            final ItemStack itemstack = aitemstack[i];
            aitemstack[i] = null;
            return itemstack;
        }
        final ItemStack itemstack = aitemstack[i].a(j);
        if (aitemstack[i].count == 0) {
            aitemstack[i] = null;
        }
        return itemstack;
    }
    
    public ItemStack splitWithoutUpdate(int i) {
        ItemStack[] aitemstack = this.items;
        if (i >= this.items.length) {
            aitemstack = this.armor;
            i -= this.items.length;
        }
        if (aitemstack[i] != null) {
            final ItemStack itemstack = aitemstack[i];
            aitemstack[i] = null;
            return itemstack;
        }
        return null;
    }
    
    public void setItem(int i, final ItemStack itemstack) {
        ItemStack[] aitemstack = this.items;
        if (i >= aitemstack.length) {
            i -= aitemstack.length;
            aitemstack = this.armor;
        }
        aitemstack[i] = itemstack;
    }
    
    public float a(final Block block) {
        float f = 1.0f;
        if (this.items[this.itemInHandIndex] != null) {
            f *= this.items[this.itemInHandIndex].a(block);
        }
        return f;
    }
    
    public NBTTagList a(final NBTTagList nbttaglist) {
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] != null) {
                final NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.items[i].save(nbttagcompound);
                nbttaglist.add(nbttagcompound);
            }
        }
        for (int i = 0; i < this.armor.length; ++i) {
            if (this.armor[i] != null) {
                final NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)(i + 100));
                this.armor[i].save(nbttagcompound);
                nbttaglist.add(nbttagcompound);
            }
        }
        return nbttaglist;
    }
    
    public void b(final NBTTagList nbttaglist) {
        this.items = new ItemStack[36];
        this.armor = new ItemStack[4];
        for (int i = 0; i < nbttaglist.size(); ++i) {
            final NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.get(i);
            final int j = nbttagcompound.getByte("Slot") & 0xFF;
            final ItemStack itemstack = ItemStack.createStack(nbttagcompound);
            if (itemstack != null) {
                if (j >= 0 && j < this.items.length) {
                    this.items[j] = itemstack;
                }
                if (j >= 100 && j < this.armor.length + 100) {
                    this.armor[j - 100] = itemstack;
                }
            }
        }
    }
    
    public int getSize() {
        return this.items.length + 4;
    }
    
    public ItemStack getItem(int i) {
        ItemStack[] aitemstack = this.items;
        if (i >= aitemstack.length) {
            i -= aitemstack.length;
            aitemstack = this.armor;
        }
        return aitemstack[i];
    }
    
    public String getName() {
        return "container.inventory";
    }
    
    public boolean c() {
        return false;
    }
    
    public int getMaxStackSize() {
        return this.maxStack;
    }
    
    public int a(final Entity entity) {
        final ItemStack itemstack = this.getItem(this.itemInHandIndex);
        return (itemstack != null) ? itemstack.a(entity) : 1;
    }
    
    public boolean b(final Block block) {
        if (block.material.isAlwaysDestroyable()) {
            return true;
        }
        final ItemStack itemstack = this.getItem(this.itemInHandIndex);
        return itemstack != null && itemstack.b(block);
    }
    
    public ItemStack f(final int i) {
        return this.armor[i];
    }
    
    public int l() {
        int i = 0;
        for (int j = 0; j < this.armor.length; ++j) {
            if (this.armor[j] != null && this.armor[j].getItem() instanceof ItemArmor) {
                final int k = ((ItemArmor)this.armor[j].getItem()).c;
                i += k;
            }
        }
        return i;
    }
    
    public void g(int i) {
        i /= 4;
        if (i < 1) {
            i = 1;
        }
        for (int j = 0; j < this.armor.length; ++j) {
            if (this.armor[j] != null && this.armor[j].getItem() instanceof ItemArmor) {
                this.armor[j].damage(i, this.player);
                if (this.armor[j].count == 0) {
                    this.armor[j] = null;
                }
            }
        }
    }
    
    public void m() {
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] != null) {
                this.player.a(this.items[i], true);
                this.items[i] = null;
            }
        }
        for (int i = 0; i < this.armor.length; ++i) {
            if (this.armor[i] != null) {
                this.player.a(this.armor[i], true);
                this.armor[i] = null;
            }
        }
    }
    
    public void update() {
        this.e = true;
    }
    
    public void setCarried(final ItemStack itemstack) {
        this.g = itemstack;
    }
    
    public ItemStack getCarried() {
        if (this.g != null && this.g.count == 0) {
            this.setCarried(null);
        }
        return this.g;
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return !this.player.dead && entityhuman.e(this.player) <= 64.0;
    }
    
    public boolean c(final ItemStack itemstack) {
        for (int i = 0; i < this.armor.length; ++i) {
            if (this.armor[i] != null && this.armor[i].doMaterialsMatch(itemstack)) {
                return true;
            }
        }
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] != null && this.items[i].doMaterialsMatch(itemstack)) {
                return true;
            }
        }
        return false;
    }
    
    public void startOpen() {
    }
    
    public void g() {
    }
    
    public boolean b(final int i, final ItemStack itemstack) {
        return true;
    }
    
    public void b(final PlayerInventory playerinventory) {
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i] = ItemStack.b(playerinventory.items[i]);
        }
        for (int i = 0; i < this.armor.length; ++i) {
            this.armor[i] = ItemStack.b(playerinventory.armor[i]);
        }
        this.itemInHandIndex = playerinventory.itemInHandIndex;
    }
}
