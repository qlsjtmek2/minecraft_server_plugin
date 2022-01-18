// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.inventory;

import java.util.Collections;
import java.util.Iterator;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.Validate;
import org.bukkit.inventory.InventoryView;
import java.util.Set;
import org.bukkit.inventory.ItemStack;
import java.util.Map;
import org.bukkit.event.HandlerList;

public class InventoryDragEvent extends InventoryInteractEvent
{
    private static final HandlerList handlers;
    private final DragType type;
    private final Map<Integer, ItemStack> addedItems;
    private final Set<Integer> containerSlots;
    private final ItemStack oldCursor;
    private ItemStack newCursor;
    
    public InventoryDragEvent(final InventoryView what, final ItemStack newCursor, final ItemStack oldCursor, final boolean right, final Map<Integer, ItemStack> slots) {
        super(what);
        Validate.notNull(oldCursor);
        Validate.notNull(slots);
        this.type = (right ? DragType.SINGLE : DragType.EVEN);
        this.newCursor = newCursor;
        this.oldCursor = oldCursor;
        this.addedItems = slots;
        final ImmutableSet.Builder<Integer> b = ImmutableSet.builder();
        for (final Integer slot : slots.keySet()) {
            b.add(Integer.valueOf(what.convertSlot(slot)));
        }
        this.containerSlots = b.build();
    }
    
    public Map<Integer, ItemStack> getNewItems() {
        return Collections.unmodifiableMap((Map<? extends Integer, ? extends ItemStack>)this.addedItems);
    }
    
    public Set<Integer> getRawSlots() {
        return this.addedItems.keySet();
    }
    
    public Set<Integer> getInventorySlots() {
        return this.containerSlots;
    }
    
    public ItemStack getCursor() {
        return this.newCursor;
    }
    
    public void setCursor(final ItemStack newCursor) {
        this.newCursor = newCursor;
    }
    
    public ItemStack getOldCursor() {
        return this.oldCursor.clone();
    }
    
    public DragType getType() {
        return this.type;
    }
    
    public HandlerList getHandlers() {
        return InventoryDragEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return InventoryDragEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
