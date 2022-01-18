// 
// Decompiled by Procyon v0.5.30
// 

package com.survivorserver.GlobalMarket;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class MarketQueue implements Runnable
{
    Market market;
    MarketStorage storage;
    
    public MarketQueue(final Market market, final MarketStorage storage) {
        this.market = market;
        this.storage = storage;
        market.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)market, (Runnable)this, 0L, 1200L);
    }
    
    public void queueListing(final ItemStack item, final String seller, final double price) {
        this.storage.storeQueueItem(QueueType.LISTING_CREATE, item, seller, price);
    }
    
    public void queueMail(final ItemStack item, final String to) {
        this.storage.storeQueueItem(QueueType.MAIL_TO, item, to);
    }
    
    @Override
    public void run() {
        try {
            final Map<Integer, List<Object>> items = this.storage.getAllQueueItems();
            if (!items.isEmpty()) {
                for (final Map.Entry<Integer, List<Object>> set : items.entrySet()) {
                    final List<Object> item = set.getValue();
                    final QueueType type = QueueType.valueOf(item.get(0));
                    if (type == QueueType.LISTING_CREATE) {
                        final Long time = item.get(item.size() - 1);
                        if ((System.currentTimeMillis() - time) / 1000L < this.market.getTradeTime()) {
                            continue;
                        }
                        this.storage.storeListing(item.get(1), item.get(2), item.get(3));
                        this.storage.removeQueueItem(set.getKey());
                    }
                    else {
                        if (type != QueueType.MAIL_TO) {
                            continue;
                        }
                        final Long time = item.get(item.size() - 1);
                        if ((System.currentTimeMillis() - time) / 1000L < this.market.getMailTime()) {
                            continue;
                        }
                        this.storage.storeMail(item.get(1), item.get(2), true);
                        this.storage.removeQueueItem(set.getKey());
                    }
                }
            }
        }
        catch (Exception e) {
            this.market.log.severe("You shouldn't see this message! Could not process queue:");
            e.printStackTrace();
        }
    }
    
    public enum QueueType
    {
        LISTING_CREATE("LISTING_CREATE", 0, "listing_create"), 
        MAIL_TO("MAIL_TO", 1, "mail_to");
        
        private String value;
        
        private QueueType(final String s, final int n, final String value) {
            this.value = value;
        }
        
        public String getValue() {
            return this.value;
        }
    }
}
