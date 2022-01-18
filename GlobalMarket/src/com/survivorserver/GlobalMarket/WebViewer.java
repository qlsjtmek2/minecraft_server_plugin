// 
// Decompiled by Procyon v0.5.30
// 

package com.survivorserver.GlobalMarket;

import java.util.UUID;

public class WebViewer
{
    String player;
    UUID versionId;
    Long lastSeen;
    
    public WebViewer(final String player, final UUID versionId) {
        this.player = player;
        this.versionId = versionId;
        this.updateLastSeen();
    }
    
    public String getViewer() {
        return this.player;
    }
    
    public UUID getVersionId() {
        return this.versionId;
    }
    
    public void setVersionId(final UUID versionId) {
        this.versionId = versionId;
    }
    
    public Long getLastSeen() {
        return this.lastSeen;
    }
    
    public void updateLastSeen() {
        this.lastSeen = System.currentTimeMillis();
    }
}
