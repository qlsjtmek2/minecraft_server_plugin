// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3;

import net.minecraft.server.v1_5_R3.ChunkCoordinates;
import org.bukkit.Location;
import net.minecraft.server.v1_5_R3.WorldServer;
import org.bukkit.TravelAgent;
import net.minecraft.server.v1_5_R3.PortalTravelAgent;

public class CraftTravelAgent extends PortalTravelAgent implements TravelAgent
{
    public static TravelAgent DEFAULT;
    private int searchRadius;
    private int creationRadius;
    private boolean canCreatePortal;
    
    public CraftTravelAgent(final WorldServer worldserver) {
        super(worldserver);
        this.searchRadius = 128;
        this.creationRadius = 16;
        this.canCreatePortal = true;
        if (CraftTravelAgent.DEFAULT == null && worldserver.dimension == 0) {
            CraftTravelAgent.DEFAULT = this;
        }
    }
    
    public Location findOrCreate(final Location target) {
        final WorldServer worldServer = ((CraftWorld)target.getWorld()).getHandle();
        final boolean before = worldServer.chunkProviderServer.forceChunkLoad;
        worldServer.chunkProviderServer.forceChunkLoad = true;
        Location found = this.findPortal(target);
        if (found == null) {
            if (this.getCanCreatePortal() && this.createPortal(target)) {
                found = this.findPortal(target);
            }
            else {
                found = target;
            }
        }
        worldServer.chunkProviderServer.forceChunkLoad = before;
        return found;
    }
    
    public Location findPortal(final Location location) {
        final PortalTravelAgent pta = ((CraftWorld)location.getWorld()).getHandle().t();
        final ChunkCoordinates found = pta.findPortal(location.getX(), location.getY(), location.getZ(), this.getSearchRadius());
        return (found != null) ? new Location(location.getWorld(), found.x, found.y, found.z, location.getYaw(), location.getPitch()) : null;
    }
    
    public boolean createPortal(final Location location) {
        final PortalTravelAgent pta = ((CraftWorld)location.getWorld()).getHandle().t();
        return pta.createPortal(location.getX(), location.getY(), location.getZ(), this.getCreationRadius());
    }
    
    public TravelAgent setSearchRadius(final int radius) {
        this.searchRadius = radius;
        return this;
    }
    
    public int getSearchRadius() {
        return this.searchRadius;
    }
    
    public TravelAgent setCreationRadius(final int radius) {
        this.creationRadius = ((radius < 2) ? 0 : radius);
        return this;
    }
    
    public int getCreationRadius() {
        return this.creationRadius;
    }
    
    public boolean getCanCreatePortal() {
        return this.canCreatePortal;
    }
    
    public void setCanCreatePortal(final boolean create) {
        this.canCreatePortal = create;
    }
    
    static {
        CraftTravelAgent.DEFAULT = null;
    }
}
