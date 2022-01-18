// 
// Decompiled by Procyon v0.5.30
// 

package GoldenMine.Block;

import java.util.Collection;
import java.util.Arrays;
import org.bukkit.block.BlockState;
import GoldenMine.Main;
import org.bukkit.block.ContainerBlock;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.block.Beacon;
import org.bukkit.block.Dropper;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Hopper;
import org.bukkit.block.Furnace;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Chest;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.EventPriority;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.EventHandler;
import GoldenMine.Instance.ConfigSetting;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.Material;
import java.util.ArrayList;
import org.bukkit.event.Listener;

public class BlockEvent implements Listener
{
    public static ArrayList<Material> storagelist;
    
    @EventHandler
    public void Dispense(final BlockDispenseEvent e) {
        if (ConfigSetting.ARROW_CANCEL && e.getItem().getType().equals((Object)Material.ARROW)) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void Dispense(final EntityExplodeEvent e) {
        final List<Block> b = (List<Block>)e.blockList();
        for (int i = 0; i < b.size(); ++i) {
            switch (b.get(i).getType()) {
                case DISPENSER:
                case DROPPER: {
                    e.setCancelled(true);
                    break;
                }
            }
        }
    }
    
    @EventHandler
    public void Break(final BlockBreakEvent e) {
        if (ConfigSetting.FREECAM && BlockEvent.storagelist.contains(e.getBlock().getType())) {
            final BlockState bs = e.getBlock().getState();
            int number = 0;
            if (bs instanceof Chest) {
                number = ((Chest)bs).getInventory().getViewers().size();
            }
            if (bs instanceof DoubleChest) {
                final DoubleChest dc = (DoubleChest)bs;
                number = dc.getLeftSide().getInventory().getViewers().size() + dc.getRightSide().getInventory().getViewers().size();
            }
            if (bs instanceof Furnace) {
                number = ((Furnace)bs).getInventory().getViewers().size();
            }
            if (bs instanceof Hopper) {
                number = ((Hopper)bs).getInventory().getViewers().size();
            }
            if (bs instanceof Dispenser) {
                number = ((Dispenser)bs).getInventory().getViewers().size();
            }
            if (bs instanceof Dropper) {
                number = ((Dropper)bs).getInventory().getViewers().size();
            }
            if (bs instanceof Beacon) {
                number = ((Beacon)bs).getInventory().getViewers().size();
            }
            if (number == 0) {
                if (bs instanceof InventoryHolder) {
                    number = ((InventoryHolder)bs).getInventory().getViewers().size();
                }
                if (bs instanceof ContainerBlock) {
                    number = ((ContainerBlock)bs).getInventory().getViewers().size();
                }
            }
            if (number >= 1) {
                e.setCancelled(true);
                Main.PrintMessage(e.getPlayer(), "\uc0c1\uc790\ub97c \uc5f4\uc5b4\ubcf4\uace0 \uc788\ub294 \ub2e4\ub978 \ud50c\ub808\uc774\uc5b4\uac00 \uc788\ub294\uacbd\uc6b0 \uc0c1\uc790\ub97c \ubd80\uc220 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.");
            }
        }
    }
    
    public void tepoimp(final int k, final long g) {
        final ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        list.stream().filter(num -> num % 2 == 0);
    }
    
    static {
        (BlockEvent.storagelist = new ArrayList<Material>()).add(Material.CHEST);
        BlockEvent.storagelist.add(Material.FURNACE);
        BlockEvent.storagelist.add(Material.HOPPER);
        BlockEvent.storagelist.add(Material.ANVIL);
        BlockEvent.storagelist.add(Material.BURNING_FURNACE);
        BlockEvent.storagelist.add(Material.TRAPPED_CHEST);
        BlockEvent.storagelist.add(Material.DROPPER);
        BlockEvent.storagelist.add(Material.ENCHANTMENT_TABLE);
        BlockEvent.storagelist.add(Material.DISPENSER);
        BlockEvent.storagelist.add(Material.BEACON);
        BlockEvent.storagelist.add(Material.BREWING_STAND);
    }
}
