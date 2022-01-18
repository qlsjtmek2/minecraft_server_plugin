// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.HumanEntity;
import java.util.List;

public class ContainerAnvilInventory extends InventorySubcontainer
{
    final ContainerAnvil a;
    public List<HumanEntity> transaction;
    public Player player;
    private int maxStack;
    
    public ItemStack[] getContents() {
        return this.items;
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
        return this.player;
    }
    
    public void setMaxStackSize(final int size) {
        this.maxStack = size;
    }
    
    ContainerAnvilInventory(final ContainerAnvil containeranvil, final String s, final boolean flag, final int i) {
        super(s, flag, i);
        this.transaction = new ArrayList<HumanEntity>();
        this.maxStack = 64;
        this.a = containeranvil;
        this.setMaxStackSize(1);
    }
    
    public void update() {
        super.update();
        this.a.a(this);
    }
    
    public boolean b(final int i, final ItemStack itemstack) {
        return true;
    }
}
