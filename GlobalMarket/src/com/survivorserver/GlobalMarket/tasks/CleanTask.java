// 
// Decompiled by Procyon v0.5.30
// 

package com.survivorserver.GlobalMarket.tasks;

import org.bukkit.entity.Player;
import java.util.Iterator;
import java.util.List;
import com.survivorserver.GlobalMarket.InterfaceViewer;
import java.util.ArrayList;
import com.survivorserver.GlobalMarket.InterfaceHandler;
import com.survivorserver.GlobalMarket.Market;

public class CleanTask implements Runnable
{
    Market market;
    InterfaceHandler handler;
    
    public CleanTask(final Market market, final InterfaceHandler handler) {
        this.market = market;
        this.handler = handler;
    }
    
    @Override
    public void run() {
        final List<InterfaceViewer> toRemove = new ArrayList<InterfaceViewer>();
        for (final InterfaceViewer viewer : this.handler.getAllViewers()) {
            final Player player = this.market.getServer().getPlayer(viewer.getViewer());
            if (player != null && player.getOpenInventory() == null) {
                toRemove.add(viewer);
            }
        }
        for (final InterfaceViewer viewer : toRemove) {
            this.handler.removeViewer(viewer);
        }
    }
}
