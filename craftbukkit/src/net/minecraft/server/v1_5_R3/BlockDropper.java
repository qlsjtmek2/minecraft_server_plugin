// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.inventory.Inventory;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryDoubleChest;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;

public class BlockDropper extends BlockDispenser
{
    private final IDispenseBehavior cR;
    
    protected BlockDropper(final int i) {
        super(i);
        this.cR = new DispenseBehaviorItem();
    }
    
    protected IDispenseBehavior a(final ItemStack itemstack) {
        return this.cR;
    }
    
    public TileEntity b(final World world) {
        return new TileEntityDropper();
    }
    
    public void dispense(final World world, final int i, final int j, final int k) {
        final SourceBlock sourceblock = new SourceBlock(world, i, j, k);
        final TileEntityDispenser tileentitydispenser = (TileEntityDispenser)sourceblock.getTileEntity();
        if (tileentitydispenser != null) {
            final int l = tileentitydispenser.j();
            if (l < 0) {
                world.triggerEffect(1001, i, j, k, 0);
            }
            else {
                final ItemStack itemstack = tileentitydispenser.getItem(l);
                final int i2 = world.getData(i, j, k) & 0x7;
                final IInventory iinventory = TileEntityHopper.getInventoryAt(world, i + Facing.b[i2], j + Facing.c[i2], k + Facing.d[i2]);
                ItemStack itemstack2;
                if (iinventory != null) {
                    final CraftItemStack oitemstack = CraftItemStack.asCraftMirror(itemstack.cloneItemStack().a(1));
                    Inventory destinationInventory;
                    if (iinventory instanceof InventoryLargeChest) {
                        destinationInventory = new CraftInventoryDoubleChest((InventoryLargeChest)iinventory);
                    }
                    else {
                        destinationInventory = iinventory.getOwner().getInventory();
                    }
                    final InventoryMoveItemEvent event = new InventoryMoveItemEvent(tileentitydispenser.getOwner().getInventory(), oitemstack.clone(), destinationInventory, true);
                    world.getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        return;
                    }
                    itemstack2 = TileEntityHopper.addItem(iinventory, CraftItemStack.asNMSCopy(event.getItem()), Facing.OPPOSITE_FACING[i2]);
                    if (event.getItem().equals(oitemstack) && itemstack2 == null) {
                        final ItemStack cloneItemStack;
                        itemstack2 = (cloneItemStack = itemstack.cloneItemStack());
                        if (--cloneItemStack.count == 0) {
                            itemstack2 = null;
                        }
                    }
                    else {
                        itemstack2 = itemstack.cloneItemStack();
                    }
                }
                else {
                    itemstack2 = this.cR.a(sourceblock, itemstack);
                    if (itemstack2 != null && itemstack2.count == 0) {
                        itemstack2 = null;
                    }
                }
                tileentitydispenser.setItem(l, itemstack2);
            }
        }
    }
}
