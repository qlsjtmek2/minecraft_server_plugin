// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.Material;
import java.util.Map;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import org.bukkit.inventory.InventoryView;
import java.util.Set;
import java.util.List;

public abstract class Container
{
    public List b;
    public List c;
    public int windowId;
    private short a;
    private int f;
    public int g;
    private final Set h;
    protected List listeners;
    private Set i;
    public boolean checkReachable;
    
    public abstract InventoryView getBukkitView();
    
    public void transferTo(final Container other, final CraftHumanEntity player) {
        final InventoryView source = this.getBukkitView();
        final InventoryView destination = other.getBukkitView();
        ((CraftInventory)source.getTopInventory()).getInventory().onClose(player);
        ((CraftInventory)source.getBottomInventory()).getInventory().onClose(player);
        ((CraftInventory)destination.getTopInventory()).getInventory().onOpen(player);
        ((CraftInventory)destination.getBottomInventory()).getInventory().onOpen(player);
    }
    
    public Container() {
        this.b = new ArrayList();
        this.c = new ArrayList();
        this.windowId = 0;
        this.a = 0;
        this.f = -1;
        this.g = 0;
        this.h = new HashSet();
        this.listeners = new ArrayList();
        this.i = new HashSet();
        this.checkReachable = true;
    }
    
    protected Slot a(final Slot slot) {
        slot.g = this.c.size();
        this.c.add(slot);
        this.b.add(null);
        return slot;
    }
    
    public void addSlotListener(final ICrafting icrafting) {
        if (this.listeners.contains(icrafting)) {
            throw new IllegalArgumentException("Listener already listening");
        }
        this.listeners.add(icrafting);
        icrafting.a(this, this.a());
        this.b();
    }
    
    public List a() {
        final ArrayList arraylist = new ArrayList();
        for (int i = 0; i < this.c.size(); ++i) {
            arraylist.add(this.c.get(i).getItem());
        }
        return arraylist;
    }
    
    public void b() {
        for (int i = 0; i < this.c.size(); ++i) {
            final ItemStack itemstack = this.c.get(i).getItem();
            ItemStack itemstack2 = this.b.get(i);
            if (!ItemStack.matches(itemstack2, itemstack)) {
                itemstack2 = ((itemstack == null) ? null : itemstack.cloneItemStack());
                this.b.set(i, itemstack2);
                for (int j = 0; j < this.listeners.size(); ++j) {
                    this.listeners.get(j).a(this, i, itemstack2);
                }
            }
        }
    }
    
    public boolean a(final EntityHuman entityhuman, final int i) {
        return false;
    }
    
    public Slot a(final IInventory iinventory, final int i) {
        for (int j = 0; j < this.c.size(); ++j) {
            final Slot slot = this.c.get(j);
            if (slot.a(iinventory, i)) {
                return slot;
            }
        }
        return null;
    }
    
    public Slot getSlot(final int i) {
        return this.c.get(i);
    }
    
    public ItemStack b(final EntityHuman entityhuman, final int i) {
        final Slot slot = this.c.get(i);
        return (slot != null) ? slot.getItem() : null;
    }
    
    public ItemStack clickItem(final int i, final int j, final int k, final EntityHuman entityhuman) {
        ItemStack itemstack = null;
        final PlayerInventory playerinventory = entityhuman.inventory;
        if (k == 5) {
            final int i2 = this.g;
            this.g = c(j);
            if ((i2 != 1 || this.g != 2) && i2 != this.g) {
                this.d();
            }
            else if (playerinventory.getCarried() == null) {
                this.d();
            }
            else if (this.g == 0) {
                this.f = b(j);
                if (d(this.f)) {
                    this.g = 1;
                    this.h.clear();
                }
                else {
                    this.d();
                }
            }
            else if (this.g == 1) {
                final Slot slot = this.c.get(i);
                if (slot != null && a(slot, playerinventory.getCarried(), true) && slot.isAllowed(playerinventory.getCarried()) && playerinventory.getCarried().count > this.h.size() && this.b(slot)) {
                    this.h.add(slot);
                }
            }
            else if (this.g == 2) {
                if (!this.h.isEmpty()) {
                    final ItemStack itemstack2 = playerinventory.getCarried().cloneItemStack();
                    int l = playerinventory.getCarried().count;
                    final Iterator iterator = this.h.iterator();
                    final Map<Integer, ItemStack> draggedSlots = new HashMap<Integer, ItemStack>();
                    while (iterator.hasNext()) {
                        final Slot slot2 = iterator.next();
                        if (slot2 != null && a(slot2, playerinventory.getCarried(), true) && slot2.isAllowed(playerinventory.getCarried()) && playerinventory.getCarried().count >= this.h.size() && this.b(slot2)) {
                            final ItemStack itemstack3 = itemstack2.cloneItemStack();
                            final int j2 = slot2.d() ? slot2.getItem().count : 0;
                            a(this.h, this.f, itemstack3, j2);
                            if (itemstack3.count > itemstack3.getMaxStackSize()) {
                                itemstack3.count = itemstack3.getMaxStackSize();
                            }
                            if (itemstack3.count > slot2.a()) {
                                itemstack3.count = slot2.a();
                            }
                            l -= itemstack3.count - j2;
                            draggedSlots.put(slot2.g, itemstack3);
                        }
                    }
                    final InventoryView view = this.getBukkitView();
                    final org.bukkit.inventory.ItemStack newcursor = CraftItemStack.asCraftMirror(itemstack2);
                    newcursor.setAmount(l);
                    final Map<Integer, org.bukkit.inventory.ItemStack> eventmap = new HashMap<Integer, org.bukkit.inventory.ItemStack>();
                    for (final Map.Entry<Integer, ItemStack> ditem : draggedSlots.entrySet()) {
                        eventmap.put(ditem.getKey(), CraftItemStack.asBukkitCopy(ditem.getValue()));
                    }
                    final ItemStack oldCursor = playerinventory.getCarried();
                    playerinventory.setCarried(CraftItemStack.asNMSCopy(newcursor));
                    final InventoryDragEvent event = new InventoryDragEvent(view, (newcursor.getType() != Material.AIR) ? newcursor : null, CraftItemStack.asBukkitCopy(oldCursor), this.f == 1, eventmap);
                    entityhuman.world.getServer().getPluginManager().callEvent(event);
                    boolean needsUpdate = event.getResult() != Event.Result.DEFAULT;
                    if (event.getResult() != Event.Result.DENY) {
                        for (final Map.Entry<Integer, ItemStack> dslot : draggedSlots.entrySet()) {
                            view.setItem(dslot.getKey(), CraftItemStack.asBukkitCopy(dslot.getValue()));
                        }
                        if (playerinventory.getCarried() != null) {
                            playerinventory.setCarried(CraftItemStack.asNMSCopy(event.getCursor()));
                            needsUpdate = true;
                        }
                    }
                    else {
                        playerinventory.setCarried(oldCursor);
                    }
                    if (needsUpdate && entityhuman instanceof EntityPlayer) {
                        ((EntityPlayer)entityhuman).updateInventory(this);
                    }
                }
                this.d();
            }
            else {
                this.d();
            }
        }
        else if (this.g != 0) {
            this.d();
        }
        else if ((k == 0 || k == 1) && (j == 0 || j == 1)) {
            if (i == -999) {
                if (playerinventory.getCarried() != null && i == -999) {
                    if (j == 0) {
                        entityhuman.drop(playerinventory.getCarried());
                        playerinventory.setCarried(null);
                    }
                    if (j == 1) {
                        final ItemStack itemstack4 = playerinventory.getCarried();
                        if (itemstack4.count > 0) {
                            entityhuman.drop(itemstack4.a(1));
                        }
                        if (itemstack4.count == 0) {
                            playerinventory.setCarried(null);
                        }
                    }
                }
            }
            else if (k == 1) {
                if (i < 0) {
                    return null;
                }
                final Slot slot3 = this.c.get(i);
                if (slot3 != null && slot3.a(entityhuman)) {
                    final ItemStack itemstack2 = this.b(entityhuman, i);
                    if (itemstack2 != null) {
                        final int l = itemstack2.id;
                        itemstack = itemstack2.cloneItemStack();
                        if (slot3 != null && slot3.getItem() != null && slot3.getItem().id == l) {
                            this.a(i, j, true, entityhuman);
                        }
                    }
                }
            }
            else {
                if (i < 0) {
                    return null;
                }
                final Slot slot3 = this.c.get(i);
                if (slot3 != null) {
                    ItemStack itemstack2 = slot3.getItem();
                    final ItemStack itemstack4 = playerinventory.getCarried();
                    if (itemstack2 != null) {
                        itemstack = itemstack2.cloneItemStack();
                    }
                    if (itemstack2 == null) {
                        if (itemstack4 != null && slot3.isAllowed(itemstack4)) {
                            int k2 = (j == 0) ? itemstack4.count : 1;
                            if (k2 > slot3.a()) {
                                k2 = slot3.a();
                            }
                            if (itemstack4.count >= k2) {
                                slot3.set(itemstack4.a(k2));
                            }
                            if (itemstack4.count == 0) {
                                playerinventory.setCarried(null);
                            }
                        }
                    }
                    else if (slot3.a(entityhuman)) {
                        if (itemstack4 == null) {
                            final int k2 = (j == 0) ? itemstack2.count : ((itemstack2.count + 1) / 2);
                            final ItemStack itemstack5 = slot3.a(k2);
                            playerinventory.setCarried(itemstack5);
                            if (itemstack2.count == 0) {
                                slot3.set(null);
                            }
                            slot3.a(entityhuman, playerinventory.getCarried());
                        }
                        else if (slot3.isAllowed(itemstack4)) {
                            if (itemstack2.id == itemstack4.id && itemstack2.getData() == itemstack4.getData() && ItemStack.equals(itemstack2, itemstack4)) {
                                int k2 = (j == 0) ? itemstack4.count : 1;
                                if (k2 > slot3.a() - itemstack2.count) {
                                    k2 = slot3.a() - itemstack2.count;
                                }
                                if (k2 > itemstack4.getMaxStackSize() - itemstack2.count) {
                                    k2 = itemstack4.getMaxStackSize() - itemstack2.count;
                                }
                                itemstack4.a(k2);
                                if (itemstack4.count == 0) {
                                    playerinventory.setCarried(null);
                                }
                                final ItemStack itemStack = itemstack2;
                                itemStack.count += k2;
                            }
                            else if (itemstack4.count <= slot3.a()) {
                                slot3.set(itemstack4);
                                playerinventory.setCarried(itemstack2);
                            }
                        }
                        else if (itemstack2.id == itemstack4.id && itemstack4.getMaxStackSize() > 1 && (!itemstack2.usesData() || itemstack2.getData() == itemstack4.getData()) && ItemStack.equals(itemstack2, itemstack4)) {
                            final int k2 = itemstack2.count;
                            if (k2 > 0 && k2 + itemstack4.count <= itemstack4.getMaxStackSize()) {
                                final ItemStack itemStack2 = itemstack4;
                                itemStack2.count += k2;
                                itemstack2 = slot3.a(k2);
                                if (itemstack2.count == 0) {
                                    slot3.set(null);
                                }
                                slot3.a(entityhuman, playerinventory.getCarried());
                            }
                        }
                    }
                    slot3.e();
                }
            }
        }
        else if (k == 2 && j >= 0 && j < 9) {
            final Slot slot3 = this.c.get(i);
            if (slot3.a(entityhuman)) {
                final ItemStack itemstack2 = playerinventory.getItem(j);
                boolean flag = itemstack2 == null || (slot3.inventory == playerinventory && slot3.isAllowed(itemstack2));
                int k2 = -1;
                if (!flag) {
                    k2 = playerinventory.j();
                    flag |= (k2 > -1);
                }
                if (slot3.d() && flag) {
                    final ItemStack itemstack5 = slot3.getItem();
                    playerinventory.setItem(j, itemstack5.cloneItemStack());
                    if ((slot3.inventory != playerinventory || !slot3.isAllowed(itemstack2)) && itemstack2 != null) {
                        if (k2 > -1) {
                            playerinventory.pickup(itemstack2);
                            slot3.a(itemstack5.count);
                            slot3.set(null);
                            slot3.a(entityhuman, itemstack5);
                        }
                    }
                    else {
                        slot3.a(itemstack5.count);
                        slot3.set(itemstack2);
                        slot3.a(entityhuman, itemstack5);
                    }
                }
                else if (!slot3.d() && itemstack2 != null && slot3.isAllowed(itemstack2)) {
                    playerinventory.setItem(j, null);
                    slot3.set(itemstack2);
                }
            }
        }
        else if (k == 3 && entityhuman.abilities.canInstantlyBuild && playerinventory.getCarried() == null && i >= 0) {
            final Slot slot3 = this.c.get(i);
            if (slot3 != null && slot3.d()) {
                final ItemStack itemstack2 = slot3.getItem().cloneItemStack();
                itemstack2.count = itemstack2.getMaxStackSize();
                playerinventory.setCarried(itemstack2);
            }
        }
        else if (k == 4 && playerinventory.getCarried() == null && i >= 0) {
            final Slot slot3 = this.c.get(i);
            if (slot3 != null && slot3.d() && slot3.a(entityhuman)) {
                final ItemStack itemstack2 = slot3.a((j == 0) ? 1 : slot3.getItem().count);
                slot3.a(entityhuman, itemstack2);
                entityhuman.drop(itemstack2);
            }
        }
        else if (k == 6 && i >= 0) {
            final Slot slot3 = this.c.get(i);
            final ItemStack itemstack2 = playerinventory.getCarried();
            if (itemstack2 != null && (slot3 == null || !slot3.d() || !slot3.a(entityhuman))) {
                final int l = (j == 0) ? 0 : (this.c.size() - 1);
                final int k2 = (j == 0) ? 1 : -1;
                for (int l2 = 0; l2 < 2; ++l2) {
                    for (int i3 = l; i3 >= 0 && i3 < this.c.size() && itemstack2.count < itemstack2.getMaxStackSize(); i3 += k2) {
                        final Slot slot4 = this.c.get(i3);
                        if (slot4.d() && a(slot4, itemstack2, true) && slot4.a(entityhuman) && this.a(itemstack2, slot4) && (l2 != 0 || slot4.getItem().count != slot4.getItem().getMaxStackSize())) {
                            final int j3 = Math.min(itemstack2.getMaxStackSize() - itemstack2.count, slot4.getItem().count);
                            final ItemStack itemstack6 = slot4.a(j3);
                            final ItemStack itemStack3 = itemstack2;
                            itemStack3.count += j3;
                            if (itemstack6.count <= 0) {
                                slot4.set(null);
                            }
                            slot4.a(entityhuman, itemstack6);
                        }
                    }
                }
            }
            this.b();
        }
        return itemstack;
    }
    
    public boolean a(final ItemStack itemstack, final Slot slot) {
        return true;
    }
    
    protected void a(final int i, final int j, final boolean flag, final EntityHuman entityhuman) {
        this.clickItem(i, j, 1, entityhuman);
    }
    
    public void b(final EntityHuman entityhuman) {
        final PlayerInventory playerinventory = entityhuman.inventory;
        if (playerinventory.getCarried() != null) {
            entityhuman.drop(playerinventory.getCarried());
            playerinventory.setCarried(null);
        }
    }
    
    public void a(final IInventory iinventory) {
        this.b();
    }
    
    public void setItem(final int i, final ItemStack itemstack) {
        this.getSlot(i).set(itemstack);
    }
    
    public boolean c(final EntityHuman entityhuman) {
        return !this.i.contains(entityhuman);
    }
    
    public void a(final EntityHuman entityhuman, final boolean flag) {
        if (flag) {
            this.i.remove(entityhuman);
        }
        else {
            this.i.add(entityhuman);
        }
    }
    
    public abstract boolean a(final EntityHuman p0);
    
    protected boolean a(final ItemStack itemstack, final int i, final int j, final boolean flag) {
        boolean flag2 = false;
        int k = i;
        if (flag) {
            k = j - 1;
        }
        if (itemstack.isStackable()) {
            while (itemstack.count > 0 && ((!flag && k < j) || (flag && k >= i))) {
                final Slot slot = this.c.get(k);
                final ItemStack itemstack2 = slot.getItem();
                if (itemstack2 != null && itemstack2.id == itemstack.id && (!itemstack.usesData() || itemstack.getData() == itemstack2.getData()) && ItemStack.equals(itemstack, itemstack2)) {
                    final int l = itemstack2.count + itemstack.count;
                    if (l <= itemstack.getMaxStackSize()) {
                        itemstack.count = 0;
                        itemstack2.count = l;
                        slot.e();
                        flag2 = true;
                    }
                    else if (itemstack2.count < itemstack.getMaxStackSize()) {
                        itemstack.count -= itemstack.getMaxStackSize() - itemstack2.count;
                        itemstack2.count = itemstack.getMaxStackSize();
                        slot.e();
                        flag2 = true;
                    }
                }
                if (flag) {
                    --k;
                }
                else {
                    ++k;
                }
            }
        }
        if (itemstack.count > 0) {
            if (flag) {
                k = j - 1;
            }
            else {
                k = i;
            }
            while ((!flag && k < j) || (flag && k >= i)) {
                final Slot slot = this.c.get(k);
                final ItemStack itemstack2 = slot.getItem();
                if (itemstack2 == null) {
                    slot.set(itemstack.cloneItemStack());
                    slot.e();
                    itemstack.count = 0;
                    flag2 = true;
                    break;
                }
                if (flag) {
                    --k;
                }
                else {
                    ++k;
                }
            }
        }
        return flag2;
    }
    
    public static int b(final int i) {
        return i >> 2 & 0x3;
    }
    
    public static int c(final int i) {
        return i & 0x3;
    }
    
    public static boolean d(final int i) {
        return i == 0 || i == 1;
    }
    
    protected void d() {
        this.g = 0;
        this.h.clear();
    }
    
    public static boolean a(final Slot slot, final ItemStack itemstack, final boolean flag) {
        boolean flag2 = slot == null || !slot.d();
        if (slot != null && slot.d() && itemstack != null && itemstack.doMaterialsMatch(slot.getItem()) && ItemStack.equals(slot.getItem(), itemstack)) {
            final int i = flag ? 0 : itemstack.count;
            flag2 |= (slot.getItem().count + i <= itemstack.getMaxStackSize());
        }
        return flag2;
    }
    
    public static void a(final Set set, final int i, final ItemStack itemstack, final int j) {
        switch (i) {
            case 0: {
                itemstack.count = MathHelper.d(itemstack.count / set.size());
                break;
            }
            case 1: {
                itemstack.count = 1;
                break;
            }
        }
        itemstack.count += j;
    }
    
    public boolean b(final Slot slot) {
        return true;
    }
    
    public static int b(final IInventory iinventory) {
        if (iinventory == null) {
            return 0;
        }
        int i = 0;
        float f = 0.0f;
        for (int j = 0; j < iinventory.getSize(); ++j) {
            final ItemStack itemstack = iinventory.getItem(j);
            if (itemstack != null) {
                f += itemstack.count / Math.min(iinventory.getMaxStackSize(), itemstack.getMaxStackSize());
                ++i;
            }
        }
        f /= iinventory.getSize();
        return MathHelper.d(f * 14.0f) + ((i > 0) ? 1 : 0);
    }
}
