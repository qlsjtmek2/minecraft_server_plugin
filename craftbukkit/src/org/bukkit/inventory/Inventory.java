// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory;

import java.util.ListIterator;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.entity.HumanEntity;
import java.util.List;
import org.bukkit.Material;
import java.util.HashMap;

public interface Inventory extends Iterable<ItemStack>
{
    int getSize();
    
    int getMaxStackSize();
    
    void setMaxStackSize(final int p0);
    
    String getName();
    
    ItemStack getItem(final int p0);
    
    void setItem(final int p0, final ItemStack p1);
    
    HashMap<Integer, ItemStack> addItem(final ItemStack... p0) throws IllegalArgumentException;
    
    HashMap<Integer, ItemStack> removeItem(final ItemStack... p0) throws IllegalArgumentException;
    
    ItemStack[] getContents();
    
    void setContents(final ItemStack[] p0) throws IllegalArgumentException;
    
    boolean contains(final int p0);
    
    boolean contains(final Material p0) throws IllegalArgumentException;
    
    boolean contains(final ItemStack p0);
    
    boolean contains(final int p0, final int p1);
    
    boolean contains(final Material p0, final int p1) throws IllegalArgumentException;
    
    boolean contains(final ItemStack p0, final int p1);
    
    boolean containsAtLeast(final ItemStack p0, final int p1);
    
    HashMap<Integer, ? extends ItemStack> all(final int p0);
    
    HashMap<Integer, ? extends ItemStack> all(final Material p0) throws IllegalArgumentException;
    
    HashMap<Integer, ? extends ItemStack> all(final ItemStack p0);
    
    int first(final int p0);
    
    int first(final Material p0) throws IllegalArgumentException;
    
    int first(final ItemStack p0);
    
    int firstEmpty();
    
    void remove(final int p0);
    
    void remove(final Material p0) throws IllegalArgumentException;
    
    void remove(final ItemStack p0);
    
    void clear(final int p0);
    
    void clear();
    
    List<HumanEntity> getViewers();
    
    String getTitle();
    
    InventoryType getType();
    
    InventoryHolder getHolder();
    
    ListIterator<ItemStack> iterator();
    
    ListIterator<ItemStack> iterator(final int p0);
}
