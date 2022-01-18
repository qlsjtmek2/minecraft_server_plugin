// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryView;
import net.minecraft.server.v1_5_R3.Packet101CloseWindow;
import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.Location;
import net.minecraft.server.v1_5_R3.ICrafting;
import net.minecraft.server.v1_5_R3.Packet;
import net.minecraft.server.v1_5_R3.Packet100OpenWindow;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftContainer;
import net.minecraft.server.v1_5_R3.Container;
import org.bukkit.event.inventory.InventoryType;
import net.minecraft.server.v1_5_R3.EntityMinecartHopper;
import net.minecraft.server.v1_5_R3.TileEntityHopper;
import net.minecraft.server.v1_5_R3.TileEntityBrewingStand;
import net.minecraft.server.v1_5_R3.TileEntityFurnace;
import net.minecraft.server.v1_5_R3.TileEntityDispenser;
import org.bukkit.inventory.InventoryView;
import org.bukkit.permissions.PermissionAttachmentInfo;
import java.util.Set;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.permissions.Permission;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.PlayerInventory;
import net.minecraft.server.v1_5_R3.IInventory;
import org.bukkit.permissions.ServerOperator;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityHuman;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.GameMode;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryPlayer;
import org.bukkit.entity.HumanEntity;

public class CraftHumanEntity extends CraftLivingEntity implements HumanEntity
{
    private CraftInventoryPlayer inventory;
    private final CraftInventory enderChest;
    protected final PermissibleBase perm;
    private boolean op;
    private GameMode mode;
    
    public CraftHumanEntity(final CraftServer server, final EntityHuman entity) {
        super(server, entity);
        this.perm = new PermissibleBase(this);
        this.mode = server.getDefaultGameMode();
        this.inventory = new CraftInventoryPlayer(entity.inventory);
        this.enderChest = new CraftInventory(entity.getEnderChest());
    }
    
    public String getName() {
        return this.getHandle().name;
    }
    
    public PlayerInventory getInventory() {
        return this.inventory;
    }
    
    public EntityEquipment getEquipment() {
        return this.inventory;
    }
    
    public Inventory getEnderChest() {
        return this.enderChest;
    }
    
    public ItemStack getItemInHand() {
        return this.getInventory().getItemInHand();
    }
    
    public void setItemInHand(final ItemStack item) {
        this.getInventory().setItemInHand(item);
    }
    
    public ItemStack getItemOnCursor() {
        return CraftItemStack.asCraftMirror(this.getHandle().inventory.getCarried());
    }
    
    public void setItemOnCursor(final ItemStack item) {
        final net.minecraft.server.v1_5_R3.ItemStack stack = CraftItemStack.asNMSCopy(item);
        this.getHandle().inventory.setCarried(stack);
        if (this instanceof CraftPlayer) {
            ((EntityPlayer)this.getHandle()).broadcastCarriedItem();
        }
    }
    
    public boolean isSleeping() {
        return this.getHandle().sleeping;
    }
    
    public int getSleepTicks() {
        return this.getHandle().sleepTicks;
    }
    
    public boolean isOp() {
        return this.op;
    }
    
    public boolean isPermissionSet(final String name) {
        return this.perm.isPermissionSet(name);
    }
    
    public boolean isPermissionSet(final Permission perm) {
        return this.perm.isPermissionSet(perm);
    }
    
    public boolean hasPermission(final String name) {
        return this.perm.hasPermission(name);
    }
    
    public boolean hasPermission(final Permission perm) {
        return this.perm.hasPermission(perm);
    }
    
    public PermissionAttachment addAttachment(final Plugin plugin, final String name, final boolean value) {
        return this.perm.addAttachment(plugin, name, value);
    }
    
    public PermissionAttachment addAttachment(final Plugin plugin) {
        return this.perm.addAttachment(plugin);
    }
    
    public PermissionAttachment addAttachment(final Plugin plugin, final String name, final boolean value, final int ticks) {
        return this.perm.addAttachment(plugin, name, value, ticks);
    }
    
    public PermissionAttachment addAttachment(final Plugin plugin, final int ticks) {
        return this.perm.addAttachment(plugin, ticks);
    }
    
    public void removeAttachment(final PermissionAttachment attachment) {
        this.perm.removeAttachment(attachment);
    }
    
    public void recalculatePermissions() {
        this.perm.recalculatePermissions();
    }
    
    public void setOp(final boolean value) {
        this.op = value;
        this.perm.recalculatePermissions();
    }
    
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return this.perm.getEffectivePermissions();
    }
    
    public GameMode getGameMode() {
        return this.mode;
    }
    
    public void setGameMode(final GameMode mode) {
        if (mode == null) {
            throw new IllegalArgumentException("Mode cannot be null");
        }
        this.mode = mode;
    }
    
    public EntityHuman getHandle() {
        return (EntityHuman)this.entity;
    }
    
    public void setHandle(final EntityHuman entity) {
        super.setHandle(entity);
        this.inventory = new CraftInventoryPlayer(entity.inventory);
    }
    
    public String toString() {
        return "CraftHumanEntity{id=" + this.getEntityId() + "name=" + this.getName() + '}';
    }
    
    public InventoryView getOpenInventory() {
        return this.getHandle().activeContainer.getBukkitView();
    }
    
    public InventoryView openInventory(final Inventory inventory) {
        if (!(this.getHandle() instanceof EntityPlayer)) {
            return null;
        }
        final EntityPlayer player = (EntityPlayer)this.getHandle();
        final InventoryType type = inventory.getType();
        final Container formerContainer = this.getHandle().activeContainer;
        final CraftInventory craftinv = (CraftInventory)inventory;
        switch (type) {
            case PLAYER:
            case CHEST:
            case ENDER_CHEST: {
                this.getHandle().openContainer(craftinv.getInventory());
                break;
            }
            case DISPENSER: {
                if (craftinv.getInventory() instanceof TileEntityDispenser) {
                    this.getHandle().openDispenser((TileEntityDispenser)craftinv.getInventory());
                    break;
                }
                this.openCustomInventory(inventory, player, 3);
                break;
            }
            case FURNACE: {
                if (craftinv.getInventory() instanceof TileEntityFurnace) {
                    this.getHandle().openFurnace((TileEntityFurnace)craftinv.getInventory());
                    break;
                }
                this.openCustomInventory(inventory, player, 2);
                break;
            }
            case WORKBENCH: {
                this.openCustomInventory(inventory, player, 1);
                break;
            }
            case BREWING: {
                if (craftinv.getInventory() instanceof TileEntityBrewingStand) {
                    this.getHandle().openBrewingStand((TileEntityBrewingStand)craftinv.getInventory());
                    break;
                }
                this.openCustomInventory(inventory, player, 5);
                break;
            }
            case ENCHANTING: {
                this.openCustomInventory(inventory, player, 4);
                break;
            }
            case HOPPER: {
                if (craftinv.getInventory() instanceof TileEntityHopper) {
                    this.getHandle().openHopper((TileEntityHopper)craftinv.getInventory());
                    break;
                }
                if (craftinv.getInventory() instanceof EntityMinecartHopper) {
                    this.getHandle().openMinecartHopper((EntityMinecartHopper)craftinv.getInventory());
                    break;
                }
                break;
            }
            case CREATIVE:
            case CRAFTING: {
                throw new IllegalArgumentException("Can't open a " + type + " inventory!");
            }
        }
        if (this.getHandle().activeContainer == formerContainer) {
            return null;
        }
        this.getHandle().activeContainer.checkReachable = false;
        return this.getHandle().activeContainer.getBukkitView();
    }
    
    private void openCustomInventory(final Inventory inventory, final EntityPlayer player, final int windowType) {
        if (player.playerConnection == null) {
            return;
        }
        Container container = new CraftContainer(inventory, this, player.nextContainerCounter());
        container = CraftEventFactory.callInventoryOpenEvent(player, container);
        if (container == null) {
            return;
        }
        final String title = container.getBukkitView().getTitle();
        final int size = container.getBukkitView().getTopInventory().getSize();
        player.playerConnection.sendPacket(new Packet100OpenWindow(container.windowId, windowType, title, size, true));
        (this.getHandle().activeContainer = container).addSlotListener(player);
    }
    
    public InventoryView openWorkbench(Location location, final boolean force) {
        if (!force) {
            final Block block = location.getBlock();
            if (block.getType() != Material.WORKBENCH) {
                return null;
            }
        }
        if (location == null) {
            location = this.getLocation();
        }
        this.getHandle().startCrafting(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (force) {
            this.getHandle().activeContainer.checkReachable = false;
        }
        return this.getHandle().activeContainer.getBukkitView();
    }
    
    public InventoryView openEnchanting(Location location, final boolean force) {
        if (!force) {
            final Block block = location.getBlock();
            if (block.getType() != Material.ENCHANTMENT_TABLE) {
                return null;
            }
        }
        if (location == null) {
            location = this.getLocation();
        }
        this.getHandle().startEnchanting(location.getBlockX(), location.getBlockY(), location.getBlockZ(), null);
        if (force) {
            this.getHandle().activeContainer.checkReachable = false;
        }
        return this.getHandle().activeContainer.getBukkitView();
    }
    
    public void openInventory(final InventoryView inventory) {
        if (!(this.getHandle() instanceof EntityPlayer)) {
            return;
        }
        if (((EntityPlayer)this.getHandle()).playerConnection == null) {
            return;
        }
        if (this.getHandle().activeContainer != this.getHandle().defaultContainer) {
            ((EntityPlayer)this.getHandle()).playerConnection.handleContainerClose(new Packet101CloseWindow(this.getHandle().activeContainer.windowId));
        }
        final EntityPlayer player = (EntityPlayer)this.getHandle();
        Container container;
        if (inventory instanceof CraftInventoryView) {
            container = ((CraftInventoryView)inventory).getHandle();
        }
        else {
            container = new CraftContainer(inventory, player.nextContainerCounter());
        }
        container = CraftEventFactory.callInventoryOpenEvent(player, container);
        if (container == null) {
            return;
        }
        final InventoryType type = inventory.getType();
        final int windowType = CraftContainer.getNotchInventoryType(type);
        final String title = inventory.getTitle();
        final int size = inventory.getTopInventory().getSize();
        player.playerConnection.sendPacket(new Packet100OpenWindow(container.windowId, windowType, title, size, false));
        (player.activeContainer = container).addSlotListener(player);
    }
    
    public void closeInventory() {
        this.getHandle().closeInventory();
    }
    
    public boolean isBlocking() {
        return this.getHandle().isBlocking();
    }
    
    public boolean setWindowProperty(final InventoryView.Property prop, final int value) {
        return false;
    }
    
    public int getExpToLevel() {
        return this.getHandle().getExpToLevel();
    }
}
