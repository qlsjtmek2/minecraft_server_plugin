// 
// Decompiled by Procyon v0.5.30
// 

package com.survivorserver.GlobalMarket.tasks;

import java.util.Iterator;
import com.survivorserver.GlobalMarket.Listing;
import com.survivorserver.GlobalMarket.MarketCore;
import com.survivorserver.GlobalMarket.MarketStorage;
import com.survivorserver.GlobalMarket.Market;

public class ExpireTask implements Runnable
{
    Market market;
    MarketStorage storage;
    MarketCore core;
    
    public ExpireTask(final Market market, final MarketStorage storage, final MarketCore core) {
        this.market = market;
        this.storage = storage;
        this.core = core;
    }
    
    @Override
    public void run() {
        for (final Listing listing : this.storage.getAllListings()) {
            final long diff = System.currentTimeMillis() - listing.getTime() * 1000L;
            if (diff / 3600000L >= this.market.getExpireTime()) {
                this.core.removeListing(listing, "Server");
            }
        }
    }
}
