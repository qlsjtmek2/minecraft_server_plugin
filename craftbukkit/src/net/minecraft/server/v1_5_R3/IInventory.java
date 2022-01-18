// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.inventory.InventoryHolder;
import org.bukkit.entity.HumanEntity;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;

public interface IInventory
{
    public static final int MAX_STACK = 64;
    
    int getSize();
    
    ItemStack getItem(final int p0);
    
    ItemStack splitStack(final int p0, final int p1);
    
    ItemStack splitWithoutUpdate(final int p0);
    
    void setItem(final int p0, final ItemStack p1);
    
    String getName();
    
    boolean c();
    
    int getMaxStackSize();
    
    void update();
    
    boolean a(final EntityHuman p0);
    
    void startOpen();
    
    void g();
    
    boolean b(final int p0, final ItemStack p1);
    
    ItemStack[] getContents();
    
    void onOpen(final CraftHumanEntity p0);
    
    void onClose(final CraftHumanEntity p0);
    
    List<HumanEntity> getViewers();
    
    InventoryHolder getOwner();
    
    void setMaxStackSize(final int p0);
}
