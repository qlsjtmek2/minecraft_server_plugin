// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryDoubleChest;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_5_R3.Spigot;
import java.util.ArrayList;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import java.util.List;

public class TileEntityHopper extends TileEntity implements IHopper
{
    private ItemStack[] a;
    private String b;
    private int c;
    public List<HumanEntity> transaction;
    private int maxStack;
    
    public ItemStack[] getContents() {
        return this.a;
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
    
    public void setMaxStackSize(final int size) {
        this.maxStack = size;
    }
    
    public TileEntityHopper() {
        this.a = new ItemStack[5];
        this.c = -1;
        this.transaction = new ArrayList<HumanEntity>();
        this.maxStack = 64;
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        final NBTTagList nbttaglist = nbttagcompound.getList("Items");
        this.a = new ItemStack[this.getSize()];
        if (nbttagcompound.hasKey("CustomName")) {
            this.b = nbttagcompound.getString("CustomName");
        }
        this.c = nbttagcompound.getInt("TransferCooldown");
        for (int i = 0; i < nbttaglist.size(); ++i) {
            final NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbttaglist.get(i);
            final byte b0 = nbttagcompound2.getByte("Slot");
            if (b0 >= 0 && b0 < this.a.length) {
                this.a[b0] = ItemStack.createStack(nbttagcompound2);
            }
        }
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        final NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.a.length; ++i) {
            if (this.a[i] != null) {
                final NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                nbttagcompound2.setByte("Slot", (byte)i);
                this.a[i].save(nbttagcompound2);
                nbttaglist.add(nbttagcompound2);
            }
        }
        nbttagcompound.set("Items", nbttaglist);
        nbttagcompound.setInt("TransferCooldown", this.c);
        if (this.c()) {
            nbttagcompound.setString("CustomName", this.b);
        }
    }
    
    public void update() {
        super.update();
    }
    
    public int getSize() {
        return this.a.length;
    }
    
    public ItemStack getItem(final int i) {
        return this.a[i];
    }
    
    public ItemStack splitStack(final int i, final int j) {
        if (this.a[i] == null) {
            return null;
        }
        if (this.a[i].count <= j) {
            final ItemStack itemstack = this.a[i];
            this.a[i] = null;
            return itemstack;
        }
        final ItemStack itemstack = this.a[i].a(j);
        if (this.a[i].count == 0) {
            this.a[i] = null;
        }
        return itemstack;
    }
    
    public ItemStack splitWithoutUpdate(final int i) {
        if (this.a[i] != null) {
            final ItemStack itemstack = this.a[i];
            this.a[i] = null;
            return itemstack;
        }
        return null;
    }
    
    public void setItem(final int i, final ItemStack itemstack) {
        this.a[i] = itemstack;
        if (itemstack != null && itemstack.count > this.getMaxStackSize()) {
            itemstack.count = this.getMaxStackSize();
        }
    }
    
    public String getName() {
        return this.c() ? this.b : "container.hopper";
    }
    
    public boolean c() {
        return this.b != null && this.b.length() > 0;
    }
    
    public void a(final String s) {
        this.b = s;
    }
    
    public int getMaxStackSize() {
        return 64;
    }
    
    public boolean a(final EntityHuman entityhuman) {
        return this.world.getTileEntity(this.x, this.y, this.z) == this && entityhuman.e(this.x + 0.5, this.y + 0.5, this.z + 0.5) <= 64.0;
    }
    
    public void startOpen() {
    }
    
    public void g() {
    }
    
    public boolean b(final int i, final ItemStack itemstack) {
        return true;
    }
    
    public void h() {
        if (this.world != null && !this.world.isStatic) {
            --this.c;
            if (!this.l()) {
                this.c(0);
                this.j();
            }
        }
    }
    
    public boolean j() {
        if (this.world != null && !this.world.isStatic && !this.l() && BlockHopper.d(this.p())) {
            final boolean flag = this.u() | suckInItems(this);
            if (flag) {
                this.c(Spigot.hopperTransferCooldown);
                this.update();
                return true;
            }
        }
        if (this.c == 0) {
            this.c(Spigot.hopperCheckCooldown);
        }
        return false;
    }
    
    private boolean u() {
        final IInventory iinventory = this.v();
        if (iinventory == null) {
            return false;
        }
        for (int i = 0; i < this.getSize(); ++i) {
            if (this.getItem(i) != null) {
                final ItemStack itemstack = this.getItem(i).cloneItemStack();
                final CraftItemStack oitemstack = CraftItemStack.asCraftMirror(this.splitStack(i, 1));
                Inventory destinationInventory;
                if (iinventory instanceof InventoryLargeChest) {
                    destinationInventory = new CraftInventoryDoubleChest((InventoryLargeChest)iinventory);
                }
                else {
                    destinationInventory = iinventory.getOwner().getInventory();
                }
                final InventoryMoveItemEvent event = new InventoryMoveItemEvent(this.getOwner().getInventory(), oitemstack.clone(), destinationInventory, true);
                this.getWorld().getServer().getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    this.setItem(i, itemstack);
                    this.c(Spigot.hopperTransferCooldown);
                    return false;
                }
                final ItemStack itemstack2 = addItem(iinventory, CraftItemStack.asNMSCopy(event.getItem()), Facing.OPPOSITE_FACING[BlockHopper.c(this.p())]);
                if (itemstack2 == null || itemstack2.count == 0) {
                    if (event.getItem().equals(oitemstack)) {
                        iinventory.update();
                    }
                    else {
                        this.setItem(i, itemstack);
                    }
                    return true;
                }
                this.setItem(i, itemstack);
            }
        }
        return false;
    }
    
    public static boolean suckInItems(final IHopper ihopper) {
        final IInventory iinventory = getSourceInventory(ihopper);
        if (iinventory != null) {
            final byte b0 = 0;
            if (iinventory instanceof IWorldInventory && b0 > -1) {
                final IWorldInventory iworldinventory = (IWorldInventory)iinventory;
                final int[] aint = iworldinventory.getSlotsForFace(b0);
                for (int i = 0; i < aint.length; ++i) {
                    if (tryTakeInItemFromSlot(ihopper, iinventory, aint[i], b0)) {
                        return true;
                    }
                }
            }
            else {
                for (int j = iinventory.getSize(), k = 0; k < j; ++k) {
                    if (tryTakeInItemFromSlot(ihopper, iinventory, k, b0)) {
                        return true;
                    }
                }
            }
        }
        else {
            final EntityItem entityitem = getEntityItemAt(ihopper.getWorld(), ihopper.aA(), ihopper.aB() + 1.0, ihopper.aC());
            if (entityitem != null) {
                return addEntityItem(ihopper, entityitem);
            }
        }
        return false;
    }
    
    private static boolean tryTakeInItemFromSlot(final IHopper ihopper, final IInventory iinventory, final int i, final int j) {
        final ItemStack itemstack = iinventory.getItem(i);
        if (itemstack != null && canTakeItemFromInventory(iinventory, itemstack, i, j)) {
            final ItemStack itemstack2 = itemstack.cloneItemStack();
            final CraftItemStack oitemstack = CraftItemStack.asCraftMirror(iinventory.splitStack(i, 1));
            Inventory sourceInventory;
            if (iinventory instanceof InventoryLargeChest) {
                sourceInventory = new CraftInventoryDoubleChest((InventoryLargeChest)iinventory);
            }
            else {
                sourceInventory = iinventory.getOwner().getInventory();
            }
            final InventoryMoveItemEvent event = new InventoryMoveItemEvent(sourceInventory, oitemstack.clone(), ihopper.getOwner().getInventory(), false);
            ihopper.getWorld().getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                iinventory.setItem(i, itemstack2);
                if (ihopper instanceof TileEntityHopper) {
                    ((TileEntityHopper)ihopper).c(Spigot.hopperTransferCooldown);
                }
                else if (ihopper instanceof EntityMinecartHopper) {
                    ((EntityMinecartHopper)ihopper).n(Spigot.hopperTransferCooldown / 2);
                }
                return false;
            }
            final ItemStack itemstack3 = addItem(ihopper, CraftItemStack.asNMSCopy(event.getItem()), -1);
            if (itemstack3 == null || itemstack3.count == 0) {
                if (event.getItem().equals(oitemstack)) {
                    iinventory.update();
                }
                else {
                    iinventory.setItem(i, itemstack2);
                }
                return true;
            }
            iinventory.setItem(i, itemstack2);
        }
        return false;
    }
    
    public static boolean addEntityItem(final IInventory iinventory, final EntityItem entityitem) {
        boolean flag = false;
        if (entityitem == null) {
            return false;
        }
        final InventoryPickupItemEvent event = new InventoryPickupItemEvent(iinventory.getOwner().getInventory(), (Item)entityitem.getBukkitEntity());
        entityitem.world.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }
        final ItemStack itemstack = entityitem.getItemStack().cloneItemStack();
        final ItemStack itemstack2 = addItem(iinventory, itemstack, -1);
        if (itemstack2 != null && itemstack2.count != 0) {
            entityitem.setItemStack(itemstack2);
        }
        else {
            flag = true;
            entityitem.die();
        }
        return flag;
    }
    
    public static ItemStack addItem(final IInventory iinventory, ItemStack itemstack, final int i) {
        if (iinventory instanceof IWorldInventory && i > -1) {
            final IWorldInventory iworldinventory = (IWorldInventory)iinventory;
            final int[] aint = iworldinventory.getSlotsForFace(i);
            for (int j = 0; j < aint.length && itemstack != null && itemstack.count > 0; itemstack = tryMoveInItem(iinventory, itemstack, aint[j], i), ++j) {}
        }
        else {
            for (int k = iinventory.getSize(), l = 0; l < k && itemstack != null && itemstack.count > 0; itemstack = tryMoveInItem(iinventory, itemstack, l, i), ++l) {}
        }
        if (itemstack != null && itemstack.count == 0) {
            itemstack = null;
        }
        return itemstack;
    }
    
    private static boolean canPlaceItemInInventory(final IInventory iinventory, final ItemStack itemstack, final int i, final int j) {
        return iinventory.b(i, itemstack) && (!(iinventory instanceof IWorldInventory) || ((IWorldInventory)iinventory).canPlaceItemThroughFace(i, itemstack, j));
    }
    
    private static boolean canTakeItemFromInventory(final IInventory iinventory, final ItemStack itemstack, final int i, final int j) {
        return !(iinventory instanceof IWorldInventory) || ((IWorldInventory)iinventory).canTakeItemThroughFace(i, itemstack, j);
    }
    
    private static ItemStack tryMoveInItem(final IInventory iinventory, ItemStack itemstack, final int i, final int j) {
        final ItemStack itemstack2 = iinventory.getItem(i);
        if (canPlaceItemInInventory(iinventory, itemstack, i, j)) {
            boolean flag = false;
            if (itemstack2 == null) {
                iinventory.setItem(i, itemstack);
                itemstack = null;
                flag = true;
            }
            else if (canMergeItems(itemstack2, itemstack)) {
                final int k = itemstack.getMaxStackSize() - itemstack2.count;
                final int l = Math.min(itemstack.count, k);
                final ItemStack itemStack = itemstack;
                itemStack.count -= l;
                final ItemStack itemStack2 = itemstack2;
                itemStack2.count += l;
                flag = (l > 0);
            }
            if (flag) {
                if (iinventory instanceof TileEntityHopper) {
                    ((TileEntityHopper)iinventory).c(Spigot.hopperTransferCooldown);
                }
                iinventory.update();
            }
        }
        return itemstack;
    }
    
    private IInventory v() {
        final int i = BlockHopper.c(this.p());
        return getInventoryAt(this.getWorld(), this.x + Facing.b[i], this.y + Facing.c[i], this.z + Facing.d[i]);
    }
    
    public static IInventory getSourceInventory(final IHopper ihopper) {
        return getInventoryAt(ihopper.getWorld(), ihopper.aA(), ihopper.aB() + 1.0, ihopper.aC());
    }
    
    public static EntityItem getEntityItemAt(final World world, final double d0, final double d1, final double d2) {
        final List list = world.a(EntityItem.class, AxisAlignedBB.a().a(d0, d1, d2, d0 + 1.0, d1 + 1.0, d2 + 1.0), IEntitySelector.a);
        return (list.size() > 0) ? list.get(0) : null;
    }
    
    public static IInventory getInventoryAt(final World world, final double d0, final double d1, final double d2) {
        IInventory iinventory = null;
        final int i = MathHelper.floor(d0);
        final int j = MathHelper.floor(d1);
        final int k = MathHelper.floor(d2);
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity != null && tileentity instanceof IInventory) {
            iinventory = (IInventory)tileentity;
            if (iinventory instanceof TileEntityChest) {
                final int l = world.getTypeId(i, j, k);
                final Block block = Block.byId[l];
                if (block instanceof BlockChest) {
                    iinventory = ((BlockChest)block).g_(world, i, j, k);
                }
            }
        }
        if (iinventory == null) {
            final List list = world.getEntities(null, AxisAlignedBB.a().a(d0, d1, d2, d0 + 1.0, d1 + 1.0, d2 + 1.0), IEntitySelector.b);
            if (list != null && list.size() > 0) {
                iinventory = list.get(world.random.nextInt(list.size()));
            }
        }
        return iinventory;
    }
    
    private static boolean canMergeItems(final ItemStack itemstack, final ItemStack itemstack1) {
        return itemstack.id == itemstack1.id && itemstack.getData() == itemstack1.getData() && itemstack.count <= itemstack.getMaxStackSize() && ItemStack.equals(itemstack, itemstack1);
    }
    
    public double aA() {
        return this.x;
    }
    
    public double aB() {
        return this.y;
    }
    
    public double aC() {
        return this.z;
    }
    
    public void c(final int i) {
        this.c = i;
    }
    
    public boolean l() {
        return this.c > 0;
    }
}
