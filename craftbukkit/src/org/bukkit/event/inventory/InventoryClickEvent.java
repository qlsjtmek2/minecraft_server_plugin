// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.inventory;

import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.HandlerList;

public class InventoryClickEvent extends InventoryInteractEvent
{
    private static final HandlerList handlers;
    private final ClickType click;
    private final InventoryAction action;
    private InventoryType.SlotType slot_type;
    private int whichSlot;
    private int rawSlot;
    private ItemStack current;
    private int hotbarKey;
    
    public InventoryClickEvent(final InventoryView view, final InventoryType.SlotType type, final int slot, final boolean right, final boolean shift) {
        this(view, type, slot, right ? (shift ? ClickType.SHIFT_RIGHT : ClickType.RIGHT) : (shift ? ClickType.SHIFT_LEFT : ClickType.LEFT), InventoryAction.SWAP_WITH_CURSOR);
    }
    
    public InventoryClickEvent(final InventoryView view, final InventoryType.SlotType type, final int slot, final ClickType click, final InventoryAction action) {
        super(view);
        this.current = null;
        this.hotbarKey = -1;
        this.slot_type = type;
        this.rawSlot = slot;
        this.whichSlot = view.convertSlot(slot);
        this.click = click;
        this.action = action;
    }
    
    public InventoryClickEvent(final InventoryView view, final InventoryType.SlotType type, final int slot, final ClickType click, final InventoryAction action, final int key) {
        this(view, type, slot, click, action);
        this.hotbarKey = key;
    }
    
    public InventoryType.SlotType getSlotType() {
        return this.slot_type;
    }
    
    public ItemStack getCursor() {
        return this.getView().getCursor();
    }
    
    public ItemStack getCurrentItem() {
        if (this.slot_type == InventoryType.SlotType.OUTSIDE) {
            return this.current;
        }
        return this.getView().getItem(this.rawSlot);
    }
    
    public boolean isRightClick() {
        return this.click.isRightClick();
    }
    
    public boolean isLeftClick() {
        return this.click.isLeftClick();
    }
    
    public boolean isShiftClick() {
        return this.click.isShiftClick();
    }
    
    @Deprecated
    public void setCursor(final ItemStack stack) {
        this.getView().setCursor(stack);
    }
    
    public void setCurrentItem(final ItemStack stack) {
        if (this.slot_type == InventoryType.SlotType.OUTSIDE) {
            this.current = stack;
        }
        else {
            this.getView().setItem(this.rawSlot, stack);
        }
    }
    
    public int getSlot() {
        return this.whichSlot;
    }
    
    public int getRawSlot() {
        return this.rawSlot;
    }
    
    public int getHotbarButton() {
        return this.hotbarKey;
    }
    
    public InventoryAction getAction() {
        return this.action;
    }
    
    public ClickType getClick() {
        return this.click;
    }
    
    public HandlerList getHandlers() {
        return InventoryClickEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return InventoryClickEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
