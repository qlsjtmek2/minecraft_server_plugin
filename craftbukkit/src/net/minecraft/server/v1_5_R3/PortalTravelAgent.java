// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.util.Vector;
import org.bukkit.World;
import org.bukkit.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PortalTravelAgent
{
    private final WorldServer a;
    private final Random b;
    private final LongHashMap c;
    private final List d;
    
    public PortalTravelAgent(final WorldServer worldserver) {
        this.c = new LongHashMap();
        this.d = new ArrayList();
        this.a = worldserver;
        this.b = new Random(worldserver.getSeed());
    }
    
    public void a(final Entity entity, final double d0, final double d1, final double d2, final float f) {
        if (this.a.worldProvider.dimension != 1) {
            if (!this.b(entity, d0, d1, d2, f)) {
                this.a(entity);
                this.b(entity, d0, d1, d2, f);
            }
        }
        else {
            final ChunkCoordinates created = this.createEndPortal(d0, d1, d2);
            entity.setPositionRotation(created.x, created.y, created.z, entity.yaw, 0.0f);
            final double motX = 0.0;
            entity.motZ = motX;
            entity.motY = motX;
            entity.motX = motX;
        }
    }
    
    private ChunkCoordinates createEndPortal(final double x, final double y, final double z) {
        final int i = MathHelper.floor(x);
        final int j = MathHelper.floor(y) - 1;
        final int k = MathHelper.floor(z);
        final byte b0 = 1;
        final byte b2 = 0;
        for (int l = -2; l <= 2; ++l) {
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int j2 = -1; j2 < 3; ++j2) {
                    final int k2 = i + i2 * b0 + l * b2;
                    final int l2 = j + j2;
                    final int i3 = k + i2 * b2 - l * b0;
                    final boolean flag = j2 < 0;
                    this.a.setTypeIdUpdate(k2, l2, i3, flag ? Block.OBSIDIAN.id : 0);
                }
            }
        }
        return new ChunkCoordinates(i, j, k);
    }
    
    private ChunkCoordinates findEndPortal(final ChunkCoordinates portal) {
        final int i = portal.x;
        final int j = portal.y - 1;
        final int k = portal.z;
        final byte b0 = 1;
        final byte b2 = 0;
        for (int l = -2; l <= 2; ++l) {
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int j2 = -1; j2 < 3; ++j2) {
                    final int k2 = i + i2 * b0 + l * b2;
                    final int l2 = j + j2;
                    final int i3 = k + i2 * b2 - l * b0;
                    final boolean flag = j2 < 0;
                    if (this.a.getTypeId(k2, l2, i3) != (flag ? Block.OBSIDIAN.id : 0)) {
                        return null;
                    }
                }
            }
        }
        return new ChunkCoordinates(i, j, k);
    }
    
    public boolean b(final Entity entity, final double d0, final double d1, final double d2, final float f) {
        final ChunkCoordinates found = this.findPortal(entity.locX, entity.locY, entity.locZ, 128);
        if (found == null) {
            return false;
        }
        final Location exit = new Location(this.a.getWorld(), found.x, found.y, found.z, f, entity.pitch);
        final Vector velocity = entity.getBukkitEntity().getVelocity();
        this.adjustExit(entity, exit, velocity);
        entity.setPositionRotation(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());
        if (entity.motX != velocity.getX() || entity.motY != velocity.getY() || entity.motZ != velocity.getZ()) {
            entity.getBukkitEntity().setVelocity(velocity);
        }
        return true;
    }
    
    public ChunkCoordinates findPortal(final double x, final double y, final double z, final int short1) {
        if (this.a.getWorld().getEnvironment() == World.Environment.THE_END) {
            return this.findEndPortal(this.a.worldProvider.h());
        }
        double d3 = -1.0;
        int i = 0;
        int j = 0;
        int k = 0;
        final int l = MathHelper.floor(x);
        final int i2 = MathHelper.floor(z);
        final long j2 = ChunkCoordIntPair.a(l, i2);
        boolean flag = true;
        if (this.c.contains(j2)) {
            final ChunkCoordinatesPortal chunkcoordinatesportal = (ChunkCoordinatesPortal)this.c.getEntry(j2);
            d3 = 0.0;
            i = chunkcoordinatesportal.x;
            j = chunkcoordinatesportal.y;
            k = chunkcoordinatesportal.z;
            chunkcoordinatesportal.d = this.a.getTime();
            flag = false;
        }
        else {
            for (int k2 = l - short1; k2 <= l + short1; ++k2) {
                final double d4 = k2 + 0.5 - x;
                for (int l2 = i2 - short1; l2 <= i2 + short1; ++l2) {
                    final double d5 = l2 + 0.5 - z;
                    for (int i3 = this.a.R() - 1; i3 >= 0; --i3) {
                        if (this.a.getTypeId(k2, i3, l2) == Block.PORTAL.id) {
                            while (this.a.getTypeId(k2, i3 - 1, l2) == Block.PORTAL.id) {
                                --i3;
                            }
                            final double d6 = i3 + 0.5 - y;
                            final double d7 = d4 * d4 + d6 * d6 + d5 * d5;
                            if (d3 < 0.0 || d7 < d3) {
                                d3 = d7;
                                i = k2;
                                j = i3;
                                k = l2;
                            }
                        }
                    }
                }
            }
        }
        if (d3 >= 0.0) {
            if (flag) {
                this.c.put(j2, new ChunkCoordinatesPortal(this, i, j, k, this.a.getTime()));
                this.d.add(j2);
            }
            return new ChunkCoordinates(i, j, k);
        }
        return null;
    }
    
    public void adjustExit(final Entity entity, final Location position, final Vector velocity) {
        final Location from = position.clone();
        final Vector before = velocity.clone();
        final int i = position.getBlockX();
        final int j = position.getBlockY();
        final int k = position.getBlockZ();
        float f = position.getYaw();
        if (this.a.getWorld().getEnvironment() == World.Environment.THE_END) {
            position.setPitch(0.0f);
            velocity.setX(0);
            velocity.setY(0);
            velocity.setZ(0);
        }
        else {
            double d8 = i + 0.5;
            final double d9 = j + 0.5;
            double d10 = k + 0.5;
            int j2 = -1;
            if (this.a.getTypeId(i - 1, j, k) == Block.PORTAL.id) {
                j2 = 2;
            }
            if (this.a.getTypeId(i + 1, j, k) == Block.PORTAL.id) {
                j2 = 0;
            }
            if (this.a.getTypeId(i, j, k - 1) == Block.PORTAL.id) {
                j2 = 3;
            }
            if (this.a.getTypeId(i, j, k + 1) == Block.PORTAL.id) {
                j2 = 1;
            }
            final int k2 = entity.as();
            if (j2 > -1) {
                int l2 = Direction.h[j2];
                int i2 = Direction.a[j2];
                int j3 = Direction.b[j2];
                int k3 = Direction.a[l2];
                int l3 = Direction.b[l2];
                boolean flag1 = !this.a.isEmpty(i + i2 + k3, j, k + j3 + l3) || !this.a.isEmpty(i + i2 + k3, j + 1, k + j3 + l3);
                boolean flag2 = !this.a.isEmpty(i + i2, j, k + j3) || !this.a.isEmpty(i + i2, j + 1, k + j3);
                if (flag1 && flag2) {
                    j2 = Direction.f[j2];
                    l2 = Direction.f[l2];
                    i2 = Direction.a[j2];
                    j3 = Direction.b[j2];
                    k3 = Direction.a[l2];
                    l3 = Direction.b[l2];
                    final int k4 = i - k3;
                    d8 -= k3;
                    final int i3 = k - l3;
                    d10 -= l3;
                    flag1 = (!this.a.isEmpty(k4 + i2 + k3, j, i3 + j3 + l3) || !this.a.isEmpty(k4 + i2 + k3, j + 1, i3 + j3 + l3));
                    flag2 = (!this.a.isEmpty(k4 + i2, j, i3 + j3) || !this.a.isEmpty(k4 + i2, j + 1, i3 + j3));
                }
                float f2 = 0.5f;
                float f3 = 0.5f;
                if (!flag1 && flag2) {
                    f2 = 1.0f;
                }
                else if (flag1 && !flag2) {
                    f2 = 0.0f;
                }
                else if (flag1 && flag2) {
                    f3 = 0.0f;
                }
                d8 += k3 * f2 + f3 * i2;
                d10 += l3 * f2 + f3 * j3;
                float f4 = 0.0f;
                float f5 = 0.0f;
                float f6 = 0.0f;
                float f7 = 0.0f;
                if (j2 == k2) {
                    f4 = 1.0f;
                    f5 = 1.0f;
                }
                else if (j2 == Direction.f[k2]) {
                    f4 = -1.0f;
                    f5 = -1.0f;
                }
                else if (j2 == Direction.g[k2]) {
                    f6 = 1.0f;
                    f7 = -1.0f;
                }
                else {
                    f6 = -1.0f;
                    f7 = 1.0f;
                }
                final double d11 = velocity.getX();
                final double d12 = velocity.getZ();
                velocity.setX(d11 * f4 + d12 * f7);
                velocity.setZ(d11 * f6 + d12 * f5);
                f = f - k2 * 90 + j2 * 90;
            }
            else {
                velocity.setX(0);
                velocity.setY(0);
                velocity.setZ(0);
            }
            position.setX(d8);
            position.setY(d9);
            position.setZ(d10);
            position.setYaw(f);
        }
        final EntityPortalExitEvent event = new EntityPortalExitEvent(entity.getBukkitEntity(), from, position, before, velocity);
        this.a.getServer().getPluginManager().callEvent(event);
        final Location to = event.getTo();
        if (event.isCancelled() || to == null || !entity.isAlive()) {
            position.setX(from.getX());
            position.setY(from.getY());
            position.setZ(from.getZ());
            position.setYaw(from.getYaw());
            position.setPitch(from.getPitch());
            velocity.copy(before);
        }
        else {
            position.setX(to.getX());
            position.setY(to.getY());
            position.setZ(to.getZ());
            position.setYaw(to.getYaw());
            position.setPitch(to.getPitch());
            velocity.copy(event.getAfter());
        }
    }
    
    public boolean a(final Entity entity) {
        return this.createPortal(entity.locX, entity.locY, entity.locZ, 16);
    }
    
    public boolean createPortal(final double x, final double y, final double z, final int b0) {
        if (this.a.getWorld().getEnvironment() == World.Environment.THE_END) {
            this.createEndPortal(x, y, z);
            return true;
        }
        double d0 = -1.0;
        final int i = MathHelper.floor(x);
        final int j = MathHelper.floor(y);
        final int k = MathHelper.floor(z);
        int l = i;
        int i2 = j;
        int j2 = k;
        int k2 = 0;
        final int l2 = this.b.nextInt(4);
        for (int i3 = i - b0; i3 <= i + b0; ++i3) {
            final double d2 = i3 + 0.5 - x;
            for (int j3 = k - b0; j3 <= k + b0; ++j3) {
                final double d3 = j3 + 0.5 - z;
            Label_0446:
                for (int k3 = this.a.R() - 1; k3 >= 0; --k3) {
                    if (this.a.isEmpty(i3, k3, j3)) {
                        while (k3 > 0 && this.a.isEmpty(i3, k3 - 1, j3)) {
                            --k3;
                        }
                        for (int i4 = l2; i4 < l2 + 4; ++i4) {
                            int l3 = i4 % 2;
                            int k4 = 1 - l3;
                            if (i4 % 4 >= 2) {
                                l3 = -l3;
                                k4 = -k4;
                            }
                            for (int j4 = 0; j4 < 3; ++j4) {
                                for (int i5 = 0; i5 < 4; ++i5) {
                                    for (int l4 = -1; l4 < 4; ++l4) {
                                        final int k5 = i3 + (i5 - 1) * l3 + j4 * k4;
                                        final int j5 = k3 + l4;
                                        final int l5 = j3 + (i5 - 1) * k4 - j4 * l3;
                                        if (l4 < 0 && !this.a.getMaterial(k5, j5, l5).isBuildable()) {
                                            continue Label_0446;
                                        }
                                        if (l4 >= 0 && !this.a.isEmpty(k5, j5, l5)) {
                                            continue Label_0446;
                                        }
                                    }
                                }
                            }
                            final double d4 = k3 + 0.5 - y;
                            final double d5 = d2 * d2 + d4 * d4 + d3 * d3;
                            if (d0 < 0.0 || d5 < d0) {
                                d0 = d5;
                                l = i3;
                                i2 = k3;
                                j2 = j3;
                                k2 = i4 % 4;
                            }
                        }
                    }
                }
            }
        }
        if (d0 < 0.0) {
            for (int i3 = i - b0; i3 <= i + b0; ++i3) {
                final double d2 = i3 + 0.5 - x;
                for (int j3 = k - b0; j3 <= k + b0; ++j3) {
                    final double d3 = j3 + 0.5 - z;
                Label_0796:
                    for (int k3 = this.a.R() - 1; k3 >= 0; --k3) {
                        if (this.a.isEmpty(i3, k3, j3)) {
                            while (k3 > 0 && this.a.isEmpty(i3, k3 - 1, j3)) {
                                --k3;
                            }
                            for (int i4 = l2; i4 < l2 + 2; ++i4) {
                                final int l3 = i4 % 2;
                                final int k4 = 1 - l3;
                                for (int j4 = 0; j4 < 4; ++j4) {
                                    for (int i5 = -1; i5 < 4; ++i5) {
                                        final int l4 = i3 + (j4 - 1) * l3;
                                        final int k5 = k3 + i5;
                                        final int j5 = j3 + (j4 - 1) * k4;
                                        if (i5 < 0 && !this.a.getMaterial(l4, k5, j5).isBuildable()) {
                                            continue Label_0796;
                                        }
                                        if (i5 >= 0 && !this.a.isEmpty(l4, k5, j5)) {
                                            continue Label_0796;
                                        }
                                    }
                                }
                                final double d4 = k3 + 0.5 - y;
                                final double d5 = d2 * d2 + d4 * d4 + d3 * d3;
                                if (d0 < 0.0 || d5 < d0) {
                                    d0 = d5;
                                    l = i3;
                                    i2 = k3;
                                    j2 = j3;
                                    k2 = i4 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }
        final int i6 = l;
        int j6 = i2;
        int j3 = j2;
        int k6 = k2 % 2;
        int l6 = 1 - k6;
        if (k2 % 4 >= 2) {
            k6 = -k6;
            l6 = -l6;
        }
        if (d0 < 0.0) {
            if (i2 < 70) {
                i2 = 70;
            }
            if (i2 > this.a.R() - 10) {
                i2 = this.a.R() - 10;
            }
            j6 = i2;
            for (int k3 = -1; k3 <= 1; ++k3) {
                for (int i4 = 1; i4 < 3; ++i4) {
                    for (int l3 = -1; l3 < 3; ++l3) {
                        final int k4 = i6 + (i4 - 1) * k6 + k3 * l6;
                        final int j4 = j6 + l3;
                        final int i5 = j3 + (i4 - 1) * l6 - k3 * k6;
                        final boolean flag = l3 < 0;
                        this.a.setTypeIdUpdate(k4, j4, i5, flag ? Block.OBSIDIAN.id : 0);
                    }
                }
            }
        }
        for (int k3 = 0; k3 < 4; ++k3) {
            for (int i4 = 0; i4 < 4; ++i4) {
                for (int l3 = -1; l3 < 4; ++l3) {
                    final int k4 = i6 + (i4 - 1) * k6;
                    final int j4 = j6 + l3;
                    final int i5 = j3 + (i4 - 1) * l6;
                    final boolean flag = i4 == 0 || i4 == 3 || l3 == -1 || l3 == 3;
                    this.a.setTypeIdAndData(k4, j4, i5, flag ? Block.OBSIDIAN.id : Block.PORTAL.id, 0, 2);
                }
            }
            for (int i4 = 0; i4 < 4; ++i4) {
                for (int l3 = -1; l3 < 4; ++l3) {
                    final int k4 = i6 + (i4 - 1) * k6;
                    final int j4 = j6 + l3;
                    final int i5 = j3 + (i4 - 1) * l6;
                    this.a.applyPhysics(k4, j4, i5, this.a.getTypeId(k4, j4, i5));
                }
            }
        }
        return true;
    }
    
    public void a(final long i) {
        if (i % 100L == 0L) {
            final Iterator iterator = this.d.iterator();
            final long j = i - 600L;
            while (iterator.hasNext()) {
                final Long olong = iterator.next();
                final ChunkCoordinatesPortal chunkcoordinatesportal = (ChunkCoordinatesPortal)this.c.getEntry(olong);
                if (chunkcoordinatesportal == null || chunkcoordinatesportal.d < j) {
                    iterator.remove();
                    this.c.remove(olong);
                }
            }
        }
    }
}
