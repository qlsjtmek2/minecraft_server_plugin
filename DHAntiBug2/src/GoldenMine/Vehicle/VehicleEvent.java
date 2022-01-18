// 
// Decompiled by Procyon v0.5.30
// 

package GoldenMine.Vehicle;

import java.util.Collection;
import java.util.ArrayList;
import GoldenMine.Block.BlockEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.Inventory;
import GoldenMine.Main;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.Player;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Vehicle;
import java.util.List;
import java.util.Arrays;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import GoldenMine.Instance.ConfigSetting;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import java.util.HashMap;
import org.bukkit.event.Listener;

public class VehicleEvent implements Listener
{
    public static HashMap<EntityType, Material> hm;
    
    public VehicleEvent() {
        VehicleEvent.hm.put(EntityType.MINECART_CHEST, Material.STORAGE_MINECART);
        VehicleEvent.hm.put(EntityType.MINECART_HOPPER, Material.HOPPER_MINECART);
        VehicleEvent.hm.put(EntityType.MINECART_FURNACE, Material.POWERED_MINECART);
        VehicleEvent.hm.put(EntityType.BOAT, Material.BOAT);
    }
    
    @EventHandler
    public void onCreate(final VehicleCreateEvent e) {
        try {
            if (ConfigSetting.HOPPER_CART_REMOVE_CHEST && e.getVehicle().getType().equals((Object)EntityType.MINECART_HOPPER) && this.isVehicleRemoving(e.getVehicle().getLocation(), Material.CHEST, Material.TRAPPED_CHEST)) {
                this.VehicleRemoveIteming(e.getVehicle());
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    public boolean isVehicleRemoving(final Location loc, final Material... materials) {
        return this.isVehicleRemoving(loc, Arrays.asList(materials));
    }
    
    public boolean isVehicleRemoving(final Location loc, final List<Material> list) {
        for (int i = 0; i <= 1; ++i) {
            final Material P1 = loc.add(0.0, (double)i, 0.0).getBlock().getType();
            for (int j = 0; j < list.size(); ++j) {
                if (P1.equals((Object)list.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void VehicleRemoveIteming(final Vehicle v) {
        v.remove();
        Bukkit.getWorld(v.getWorld().getName()).dropItem(v.getLocation(), new ItemStack((Material)VehicleEvent.hm.get(v.getType())));
    }
    
    @EventHandler
    public void onDestroy(final VehicleDestroyEvent e) {
        try {
            if (ConfigSetting.FREECAM && e.getAttacker() instanceof Player) {
                Inventory inv = null;
                if (e.getVehicle() instanceof HopperMinecart) {
                    inv = ((HopperMinecart)e.getVehicle()).getInventory();
                }
                if (e.getVehicle() instanceof StorageMinecart) {
                    inv = ((StorageMinecart)e.getVehicle()).getInventory();
                }
                if (inv != null && inv.getViewers().size() >= 1) {
                    e.setCancelled(true);
                    Main.PrintMessage(e.getAttacker(), "\uae54\ub300\uae30 \uce74\ud2b8 \ub610\ub294 \ud638\ud37c \uce74\ud2b8 \ub610\ub294 \ud654\ub85c \uce74\ud2b8\ub97c \ubd80\uc220 \ub54c \uce74\ud2b8\ub97c \uc5f4\uace0 \uc788\ub294 \uc720\uc800\uac00 \uc788\ub294\uacbd\uc6b0 \ubd80\uc220 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.");
                }
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    @EventHandler
    public void onMove(final VehicleMoveEvent e) {
        try {
            final Vehicle v = e.getVehicle();
            if (ConfigSetting.HOPPER_CART_REMOVE_CHEST && e.getVehicle().getType().equals((Object)EntityType.MINECART_HOPPER) && this.isVehicleRemoving(v.getLocation(), BlockEvent.storagelist)) {
                this.VehicleRemoveIteming(v);
            }
            if (ConfigSetting.BOAT_REMOVE && e.getVehicle().getType().equals((Object)EntityType.BOAT) && this.isVehicleRemoving(v.getLocation(), Material.WATER, Material.STATIONARY_WATER)) {
                this.VehicleRemoveIteming(v);
            }
            Label_0221: {
                if (ConfigSetting.HOPPER_CART_REMOVE_PORTAL && VehicleEvent.hm.containsKey(v.getType())) {
                    for (int x = -1; x <= 1; ++x) {
                        for (int y = -1; y <= 1; ++y) {
                            for (int z = -1; z <= 1; ++z) {
                                final Material P1 = e.getVehicle().getLocation().add((double)x, (double)y, (double)z).getBlock().getType();
                                if (P1.equals((Object)Material.PORTAL)) {
                                    this.VehicleRemoveIteming(v);
                                    break Label_0221;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    public void tepoimp(final int k, final long g) {
        final ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        list.stream().filter(num -> num % 2 == 0);
    }
    
    static {
        VehicleEvent.hm = new HashMap<EntityType, Material>();
    }
}
