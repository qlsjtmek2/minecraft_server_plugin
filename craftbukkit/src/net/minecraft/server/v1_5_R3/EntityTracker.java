// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import org.bukkit.craftbukkit.v1_5_R3.Spigot;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

public class EntityTracker
{
    private final WorldServer world;
    private Set b;
    public IntHashMap trackedEntities;
    private int d;
    
    public EntityTracker(final WorldServer worldserver) {
        this.b = new HashSet();
        this.trackedEntities = new IntHashMap();
        this.world = worldserver;
        this.d = worldserver.getMinecraftServer().getPlayerList().a();
    }
    
    public void track(final Entity entity) {
        if (entity instanceof EntityPlayer) {
            this.addEntity(entity, 512, 2);
            final EntityPlayer entityplayer = (EntityPlayer)entity;
            for (final EntityTrackerEntry entitytrackerentry : this.b) {
                if (entitytrackerentry.tracker != entityplayer) {
                    entitytrackerentry.updatePlayer(entityplayer);
                }
            }
        }
        else if (entity instanceof EntityFishingHook) {
            this.addEntity(entity, 64, 5, true);
        }
        else if (entity instanceof EntityArrow) {
            this.addEntity(entity, 64, 20, false);
        }
        else if (entity instanceof EntitySmallFireball) {
            this.addEntity(entity, 64, 10, false);
        }
        else if (entity instanceof EntityFireball) {
            this.addEntity(entity, 64, 10, false);
        }
        else if (entity instanceof EntitySnowball) {
            this.addEntity(entity, 64, 10, true);
        }
        else if (entity instanceof EntityEnderPearl) {
            this.addEntity(entity, 64, 10, true);
        }
        else if (entity instanceof EntityEnderSignal) {
            this.addEntity(entity, 64, 4, true);
        }
        else if (entity instanceof EntityEgg) {
            this.addEntity(entity, 64, 10, true);
        }
        else if (entity instanceof EntityPotion) {
            this.addEntity(entity, 64, 10, true);
        }
        else if (entity instanceof EntityThrownExpBottle) {
            this.addEntity(entity, 64, 10, true);
        }
        else if (entity instanceof EntityFireworks) {
            this.addEntity(entity, 64, 10, true);
        }
        else if (entity instanceof EntityItem) {
            this.addEntity(entity, 64, 20, true);
        }
        else if (entity instanceof EntityMinecartAbstract) {
            this.addEntity(entity, 80, 2, true);
        }
        else if (entity instanceof EntityBoat) {
            this.addEntity(entity, 80, 2, true);
        }
        else if (entity instanceof EntitySquid) {
            this.addEntity(entity, 64, 3, true);
        }
        else if (entity instanceof EntityWither) {
            this.addEntity(entity, 80, 3, false);
        }
        else if (entity instanceof EntityBat) {
            this.addEntity(entity, 80, 3, false);
        }
        else if (entity instanceof IAnimal) {
            this.addEntity(entity, 80, 3, true);
        }
        else if (entity instanceof EntityEnderDragon) {
            this.addEntity(entity, 160, 3, true);
        }
        else if (entity instanceof EntityTNTPrimed) {
            this.addEntity(entity, 160, 10, true);
        }
        else if (entity instanceof EntityFallingBlock) {
            this.addEntity(entity, 160, 20, true);
        }
        else if (entity instanceof EntityPainting) {
            this.addEntity(entity, 160, Integer.MAX_VALUE, false);
        }
        else if (entity instanceof EntityExperienceOrb) {
            this.addEntity(entity, 160, 20, true);
        }
        else if (entity instanceof EntityEnderCrystal) {
            this.addEntity(entity, 256, Integer.MAX_VALUE, false);
        }
        else if (entity instanceof EntityItemFrame) {
            this.addEntity(entity, 160, Integer.MAX_VALUE, false);
        }
    }
    
    public void addEntity(final Entity entity, final int i, final int j) {
        this.addEntity(entity, i, j, false);
    }
    
    public void addEntity(final Entity entity, int i, final int j, final boolean flag) {
        if (Thread.currentThread() != MinecraftServer.getServer().primaryThread) {
            throw new IllegalStateException("Asynchronous entity track!");
        }
        i = Spigot.getEntityTrackingRange(entity, i);
        if (i > this.d) {
            i = this.d;
        }
        try {
            if (this.trackedEntities.b(entity.id)) {
                throw new IllegalStateException("Entity is already tracked!");
            }
            final EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(entity, i, j, flag);
            this.b.add(entitytrackerentry);
            this.trackedEntities.a(entity.id, entitytrackerentry);
            entitytrackerentry.scanPlayers(this.world.players);
        }
        catch (Throwable throwable) {
            final CrashReport crashreport = CrashReport.a(throwable, "Adding entity to track");
            final CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity To Track");
            crashreportsystemdetails.a("Tracking range", i + " blocks");
            crashreportsystemdetails.a("Update interval", new CrashReportEntityTrackerUpdateInterval(this, j));
            entity.a(crashreportsystemdetails);
            final CrashReportSystemDetails crashreportsystemdetails2 = crashreport.a("Entity That Is Already Tracked");
            ((EntityTrackerEntry)this.trackedEntities.get(entity.id)).tracker.a(crashreportsystemdetails2);
            try {
                throw new ReportedException(crashreport);
            }
            catch (ReportedException reportedexception) {
                System.err.println("\"Silently\" catching entity tracking error.");
                reportedexception.printStackTrace();
            }
        }
    }
    
    public void untrackEntity(final Entity entity) {
        if (Thread.currentThread() != MinecraftServer.getServer().primaryThread) {
            throw new IllegalStateException("Asynchronous entity untrack!");
        }
        if (entity instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)entity;
            for (final EntityTrackerEntry entitytrackerentry : this.b) {
                entitytrackerentry.a(entityplayer);
            }
        }
        final EntityTrackerEntry entitytrackerentry2 = (EntityTrackerEntry)this.trackedEntities.d(entity.id);
        if (entitytrackerentry2 != null) {
            this.b.remove(entitytrackerentry2);
            entitytrackerentry2.a();
        }
    }
    
    public void updatePlayers() {
        final ArrayList arraylist = new ArrayList();
        for (final EntityTrackerEntry entitytrackerentry : this.b) {
            entitytrackerentry.track(this.world.players);
            if (entitytrackerentry.n && entitytrackerentry.tracker instanceof EntityPlayer) {
                arraylist.add(entitytrackerentry.tracker);
            }
        }
        for (int i = 0; i < arraylist.size(); ++i) {
            final EntityPlayer entityplayer = arraylist.get(i);
            for (final EntityTrackerEntry entitytrackerentry2 : this.b) {
                if (entitytrackerentry2.tracker != entityplayer) {
                    entitytrackerentry2.updatePlayer(entityplayer);
                }
            }
        }
    }
    
    public void a(final Entity entity, final Packet packet) {
        final EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.trackedEntities.get(entity.id);
        if (entitytrackerentry != null) {
            entitytrackerentry.broadcast(packet);
        }
    }
    
    public void sendPacketToEntity(final Entity entity, final Packet packet) {
        final EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.trackedEntities.get(entity.id);
        if (entitytrackerentry != null) {
            entitytrackerentry.broadcastIncludingSelf(packet);
        }
    }
    
    public void untrackPlayer(final EntityPlayer entityplayer) {
        for (final EntityTrackerEntry entitytrackerentry : this.b) {
            entitytrackerentry.clear(entityplayer);
        }
    }
    
    public void a(final EntityPlayer entityplayer, final Chunk chunk) {
        for (final EntityTrackerEntry entitytrackerentry : this.b) {
            if (entitytrackerentry.tracker != entityplayer && entitytrackerentry.tracker.aj == chunk.x && entitytrackerentry.tracker.al == chunk.z) {
                entitytrackerentry.updatePlayer(entityplayer);
            }
        }
    }
}
