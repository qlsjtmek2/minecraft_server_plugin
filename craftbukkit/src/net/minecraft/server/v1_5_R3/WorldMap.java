// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.Bukkit;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.craftbukkit.v1_5_R3.map.CraftMapView;
import java.util.Map;
import java.util.List;

public class WorldMap extends WorldMapBase
{
    public int centerX;
    public int centerZ;
    public byte map;
    public byte scale;
    public byte[] colors;
    public List f;
    private Map i;
    public Map g;
    public final CraftMapView mapView;
    private CraftServer server;
    private UUID uniqueId;
    
    public WorldMap(final String s) {
        super(s);
        this.colors = new byte[16384];
        this.f = new ArrayList();
        this.i = new HashMap();
        this.g = new LinkedHashMap();
        this.uniqueId = null;
        this.mapView = new CraftMapView(this);
        this.server = (CraftServer)Bukkit.getServer();
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        byte dimension = nbttagcompound.getByte("dimension");
        if (dimension >= 10) {
            final long least = nbttagcompound.getLong("UUIDLeast");
            final long most = nbttagcompound.getLong("UUIDMost");
            if (least != 0L && most != 0L) {
                this.uniqueId = new UUID(most, least);
                final CraftWorld world = (CraftWorld)this.server.getWorld(this.uniqueId);
                if (world == null) {
                    dimension = 127;
                }
                else {
                    dimension = (byte)world.getHandle().dimension;
                }
            }
        }
        this.map = dimension;
        this.centerX = nbttagcompound.getInt("xCenter");
        this.centerZ = nbttagcompound.getInt("zCenter");
        this.scale = nbttagcompound.getByte("scale");
        if (this.scale < 0) {
            this.scale = 0;
        }
        if (this.scale > 4) {
            this.scale = 4;
        }
        final short short1 = nbttagcompound.getShort("width");
        final short short2 = nbttagcompound.getShort("height");
        if (short1 == 128 && short2 == 128) {
            this.colors = nbttagcompound.getByteArray("colors");
        }
        else {
            final byte[] abyte = nbttagcompound.getByteArray("colors");
            this.colors = new byte[16384];
            final int i = (128 - short1) / 2;
            final int j = (128 - short2) / 2;
            for (int k = 0; k < short2; ++k) {
                final int l = k + j;
                if (l >= 0 || l < 128) {
                    for (int i2 = 0; i2 < short1; ++i2) {
                        final int j2 = i2 + i;
                        if (j2 >= 0 || j2 < 128) {
                            this.colors[j2 + l * 128] = abyte[i2 + k * short1];
                        }
                    }
                }
            }
        }
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        if (this.map >= 10) {
            if (this.uniqueId == null) {
                for (final World world : this.server.getWorlds()) {
                    final CraftWorld cWorld = (CraftWorld)world;
                    if (cWorld.getHandle().dimension == this.map) {
                        this.uniqueId = cWorld.getUID();
                        break;
                    }
                }
            }
            if (this.uniqueId != null) {
                nbttagcompound.setLong("UUIDLeast", this.uniqueId.getLeastSignificantBits());
                nbttagcompound.setLong("UUIDMost", this.uniqueId.getMostSignificantBits());
            }
        }
        nbttagcompound.setByte("dimension", this.map);
        nbttagcompound.setInt("xCenter", this.centerX);
        nbttagcompound.setInt("zCenter", this.centerZ);
        nbttagcompound.setByte("scale", this.scale);
        nbttagcompound.setShort("width", (short)128);
        nbttagcompound.setShort("height", (short)128);
        nbttagcompound.setByteArray("colors", this.colors);
    }
    
    public void a(final EntityHuman entityhuman, final ItemStack itemstack) {
        if (!this.i.containsKey(entityhuman)) {
            final WorldMapHumanTracker worldmaphumantracker = new WorldMapHumanTracker(this, entityhuman);
            this.i.put(entityhuman, worldmaphumantracker);
            this.f.add(worldmaphumantracker);
        }
        if (!entityhuman.inventory.c(itemstack)) {
            this.g.remove(entityhuman.getName());
        }
        for (int i = 0; i < this.f.size(); ++i) {
            final WorldMapHumanTracker worldmaphumantracker2 = this.f.get(i);
            if (!worldmaphumantracker2.trackee.dead && (worldmaphumantracker2.trackee.inventory.c(itemstack) || itemstack.z())) {
                if (!itemstack.z() && worldmaphumantracker2.trackee.dimension == this.map) {
                    this.a(0, worldmaphumantracker2.trackee.world, worldmaphumantracker2.trackee.getName(), worldmaphumantracker2.trackee.locX, worldmaphumantracker2.trackee.locZ, worldmaphumantracker2.trackee.yaw);
                }
            }
            else {
                this.i.remove(worldmaphumantracker2.trackee);
                this.f.remove(worldmaphumantracker2);
            }
        }
        if (itemstack.z()) {
            this.a(1, entityhuman.world, "frame-" + itemstack.A().id, itemstack.A().x, itemstack.A().z, itemstack.A().direction * 90);
        }
    }
    
    private void a(int i, final net.minecraft.server.v1_5_R3.World world, final String s, final double d0, final double d1, double d2) {
        final int j = 1 << this.scale;
        final float f = (float)(d0 - this.centerX) / j;
        final float f2 = (float)(d1 - this.centerZ) / j;
        byte b0 = (byte)(f * 2.0f + 0.5);
        byte b2 = (byte)(f2 * 2.0f + 0.5);
        final byte b3 = 63;
        byte b4;
        if (f >= -b3 && f2 >= -b3 && f <= b3 && f2 <= b3) {
            d2 += ((d2 < 0.0) ? -8.0 : 8.0);
            b4 = (byte)(d2 * 16.0 / 360.0);
            if (this.map < 0) {
                final int k = (int)(world.getWorldData().getDayTime() / 10L);
                b4 = (byte)(k * k * 34187121 + k * 121 >> 15 & 0xF);
            }
        }
        else {
            if (Math.abs(f) >= 320.0f || Math.abs(f2) >= 320.0f) {
                this.g.remove(s);
                return;
            }
            i = 6;
            b4 = 0;
            if (f <= -b3) {
                b0 = (byte)(b3 * 2 + 2.5);
            }
            if (f2 <= -b3) {
                b2 = (byte)(b3 * 2 + 2.5);
            }
            if (f >= b3) {
                b0 = (byte)(b3 * 2 + 1);
            }
            if (f2 >= b3) {
                b2 = (byte)(b3 * 2 + 1);
            }
        }
        this.g.put(s, new WorldMapDecoration(this, (byte)i, b0, b2, b4));
    }
    
    public byte[] getUpdatePacket(final ItemStack itemstack, final net.minecraft.server.v1_5_R3.World world, final EntityHuman entityhuman) {
        final WorldMapHumanTracker worldmaphumantracker = this.i.get(entityhuman);
        return (byte[])((worldmaphumantracker == null) ? null : worldmaphumantracker.a(itemstack));
    }
    
    public void flagDirty(final int i, final int j, final int k) {
        super.c();
        for (int l = 0; l < this.f.size(); ++l) {
            final WorldMapHumanTracker worldmaphumantracker = this.f.get(l);
            if (worldmaphumantracker.b[i] < 0 || worldmaphumantracker.b[i] > j) {
                worldmaphumantracker.b[i] = j;
            }
            if (worldmaphumantracker.c[i] < 0 || worldmaphumantracker.c[i] < k) {
                worldmaphumantracker.c[i] = k;
            }
        }
    }
    
    public WorldMapHumanTracker a(final EntityHuman entityhuman) {
        WorldMapHumanTracker worldmaphumantracker = this.i.get(entityhuman);
        if (worldmaphumantracker == null) {
            worldmaphumantracker = new WorldMapHumanTracker(this, entityhuman);
            this.i.put(entityhuman, worldmaphumantracker);
            this.f.add(worldmaphumantracker);
        }
        return worldmaphumantracker;
    }
}
