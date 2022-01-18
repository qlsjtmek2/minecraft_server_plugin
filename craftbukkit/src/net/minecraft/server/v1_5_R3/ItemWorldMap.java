// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.map.MapView;
import org.bukkit.event.server.MapInitializeEvent;

public class ItemWorldMap extends ItemWorldMapBase
{
    protected ItemWorldMap(final int i) {
        super(i);
        this.a(true);
    }
    
    public WorldMap getSavedMap(final ItemStack itemstack, final World world) {
        String s = "map_" + itemstack.getData();
        WorldMap worldmap = (WorldMap)world.a(WorldMap.class, s);
        if (worldmap == null && !world.isStatic) {
            itemstack.setData(world.b("map"));
            s = "map_" + itemstack.getData();
            worldmap = new WorldMap(s);
            worldmap.scale = 3;
            final int i = 128 * (1 << worldmap.scale);
            worldmap.centerX = Math.round(world.getWorldData().c() / i) * i;
            worldmap.centerZ = Math.round(world.getWorldData().e() / i) * i;
            worldmap.map = (byte)((WorldServer)world).dimension;
            worldmap.c();
            world.a(s, worldmap);
            final MapInitializeEvent event = new MapInitializeEvent(worldmap.mapView);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        return worldmap;
    }
    
    public void a(final World world, final Entity entity, final WorldMap worldmap) {
        if (((WorldServer)world).dimension == worldmap.map && entity instanceof EntityHuman) {
            final short short1 = 128;
            final short short2 = 128;
            final int i = 1 << worldmap.scale;
            final int j = worldmap.centerX;
            final int k = worldmap.centerZ;
            final int l = MathHelper.floor(entity.locX - j) / i + short1 / 2;
            final int i2 = MathHelper.floor(entity.locZ - k) / i + short2 / 2;
            int j2 = 128 / i;
            if (world.worldProvider.f) {
                j2 /= 2;
            }
            final WorldMapHumanTracker a;
            final WorldMapHumanTracker worldmaphumantracker = a = worldmap.a((EntityHuman)entity);
            ++a.d;
            for (int k2 = l - j2 + 1; k2 < l + j2; ++k2) {
                if ((k2 & 0xF) == (worldmaphumantracker.d & 0xF)) {
                    int l2 = 255;
                    int i3 = 0;
                    double d0 = 0.0;
                    for (int j3 = i2 - j2 - 1; j3 < i2 + j2; ++j3) {
                        if (k2 >= 0 && j3 >= -1 && k2 < short1 && j3 < short2) {
                            final int k3 = k2 - l;
                            final int l3 = j3 - i2;
                            final boolean flag = k3 * k3 + l3 * l3 > (j2 - 2) * (j2 - 2);
                            final int i4 = (j / i + k2 - short1 / 2) * i;
                            final int j4 = (k / i + j3 - short2 / 2) * i;
                            final int[] aint = new int[256];
                            final Chunk chunk = world.getChunkAtWorldCoords(i4, j4);
                            if (!chunk.isEmpty()) {
                                final int k4 = i4 & 0xF;
                                final int l4 = j4 & 0xF;
                                int i5 = 0;
                                double d2 = 0.0;
                                if (world.worldProvider.f) {
                                    int j5 = i4 + j4 * 231871;
                                    j5 = j5 * j5 * 31287121 + j5 * 11;
                                    if ((j5 >> 20 & 0x1) == 0x0) {
                                        final int[] array = aint;
                                        final int id = Block.DIRT.id;
                                        array[id] += 10;
                                    }
                                    else {
                                        final int[] array2 = aint;
                                        final int id2 = Block.STONE.id;
                                        array2[id2] += 10;
                                    }
                                    d2 = 100.0;
                                }
                                else {
                                    for (int j5 = 0; j5 < i; ++j5) {
                                        for (int k5 = 0; k5 < i; ++k5) {
                                            int l5 = chunk.b(j5 + k4, k5 + l4) + 1;
                                            int j6 = 0;
                                            if (l5 > 1) {
                                                boolean flag2;
                                                do {
                                                    flag2 = true;
                                                    j6 = chunk.getTypeId(j5 + k4, l5 - 1, k5 + l4);
                                                    if (j6 == 0) {
                                                        flag2 = false;
                                                    }
                                                    else if (l5 > 0 && j6 > 0 && Block.byId[j6].material.G == MaterialMapColor.b) {
                                                        flag2 = false;
                                                    }
                                                    if (!flag2) {
                                                        if (--l5 <= 0) {
                                                            break;
                                                        }
                                                        j6 = chunk.getTypeId(j5 + k4, l5 - 1, k5 + l4);
                                                    }
                                                } while (l5 > 0 && !flag2);
                                                if (l5 > 0 && j6 != 0 && Block.byId[j6].material.isLiquid()) {
                                                    int i6 = l5 - 1;
                                                    final boolean flag3 = false;
                                                    int k6;
                                                    do {
                                                        k6 = chunk.getTypeId(j5 + k4, i6--, k5 + l4);
                                                        ++i5;
                                                    } while (i6 > 0 && k6 != 0 && Block.byId[k6].material.isLiquid());
                                                }
                                            }
                                            d2 += l5 / (i * i);
                                            final int[] array3 = aint;
                                            final int n = j6;
                                            ++array3[n];
                                        }
                                    }
                                }
                                i5 /= i * i;
                                int j5 = 0;
                                int k5 = 0;
                                for (int l5 = 0; l5 < 256; ++l5) {
                                    if (aint[l5] > j5) {
                                        k5 = l5;
                                        j5 = aint[l5];
                                    }
                                }
                                double d3 = (d2 - d0) * 4.0 / (i + 4) + ((k2 + j3 & 0x1) - 0.5) * 0.4;
                                byte b0 = 1;
                                if (d3 > 0.6) {
                                    b0 = 2;
                                }
                                if (d3 < -0.6) {
                                    b0 = 0;
                                }
                                int i6 = 0;
                                if (k5 > 0) {
                                    final MaterialMapColor materialmapcolor = Block.byId[k5].material.G;
                                    if (materialmapcolor == MaterialMapColor.n) {
                                        d3 = i5 * 0.1 + (k2 + j3 & 0x1) * 0.2;
                                        b0 = 1;
                                        if (d3 < 0.5) {
                                            b0 = 2;
                                        }
                                        if (d3 > 0.9) {
                                            b0 = 0;
                                        }
                                    }
                                    i6 = materialmapcolor.q;
                                }
                                d0 = d2;
                                if (j3 >= 0 && k3 * k3 + l3 * l3 < j2 * j2 && (!flag || (k2 + j3 & 0x1) != 0x0)) {
                                    final byte b2 = worldmap.colors[k2 + j3 * short1];
                                    final byte b3 = (byte)(i6 * 4 + b0);
                                    if (b2 != b3) {
                                        if (l2 > j3) {
                                            l2 = j3;
                                        }
                                        if (i3 < j3) {
                                            i3 = j3;
                                        }
                                        worldmap.colors[k2 + j3 * short1] = b3;
                                    }
                                }
                            }
                        }
                    }
                    if (l2 <= i3) {
                        worldmap.flagDirty(k2, l2, i3);
                    }
                }
            }
        }
    }
    
    public void a(final ItemStack itemstack, final World world, final Entity entity, final int i, final boolean flag) {
        if (!world.isStatic) {
            final WorldMap worldmap = this.getSavedMap(itemstack, world);
            if (entity instanceof EntityHuman) {
                final EntityHuman entityhuman = (EntityHuman)entity;
                worldmap.a(entityhuman, itemstack);
            }
            if (flag) {
                this.a(world, entity, worldmap);
            }
        }
    }
    
    public Packet c(final ItemStack itemstack, final World world, final EntityHuman entityhuman) {
        final byte[] abyte = this.getSavedMap(itemstack, world).getUpdatePacket(itemstack, world, entityhuman);
        return (abyte == null) ? null : new Packet131ItemData((short)Item.MAP.id, (short)itemstack.getData(), abyte);
    }
    
    public void d(final ItemStack itemstack, final World world, final EntityHuman entityhuman) {
        if (itemstack.hasTag() && itemstack.getTag().getBoolean("map_is_scaling")) {
            final WorldMap worldmap = Item.MAP.getSavedMap(itemstack, world);
            itemstack.setData(world.b("map"));
            final WorldMap worldmap2 = new WorldMap("map_" + itemstack.getData());
            worldmap2.scale = (byte)(worldmap.scale + 1);
            if (worldmap2.scale > 4) {
                worldmap2.scale = 4;
            }
            worldmap2.centerX = worldmap.centerX;
            worldmap2.centerZ = worldmap.centerZ;
            worldmap2.map = worldmap.map;
            worldmap2.c();
            world.a("map_" + itemstack.getData(), worldmap2);
            final MapInitializeEvent event = new MapInitializeEvent(worldmap2.mapView);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }
}
