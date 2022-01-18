// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import org.bukkit.craftbukkit.v1_5_R3.map.RenderData;
import org.bukkit.map.MapCursor;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_5_R3.map.CraftMapRenderer;

public class WorldMapHumanTracker
{
    public final EntityHuman trackee;
    public int[] b;
    public int[] c;
    private int f;
    private int g;
    private byte[] h;
    public int d;
    private boolean i;
    final WorldMap worldMap;
    
    public WorldMapHumanTracker(final WorldMap worldmap, final EntityHuman entityhuman) {
        this.worldMap = worldmap;
        this.b = new int[128];
        this.c = new int[128];
        this.f = 0;
        this.g = 0;
        this.i = false;
        this.trackee = entityhuman;
        for (int i = 0; i < this.b.length; ++i) {
            this.b[i] = 0;
            this.c[i] = 127;
        }
    }
    
    public byte[] a(final ItemStack itemstack) {
        if (!this.i) {
            final byte[] abyte = { 2, this.worldMap.scale };
            this.i = true;
            return abyte;
        }
        final boolean custom = this.worldMap.mapView.renderers.size() > 1 || !(this.worldMap.mapView.renderers.get(0) instanceof CraftMapRenderer);
        final RenderData render = custom ? this.worldMap.mapView.render((CraftPlayer)this.trackee.getBukkitEntity()) : null;
        if (--this.g < 0) {
            this.g = 4;
            final byte[] abyte = new byte[(custom ? render.cursors.size() : this.worldMap.g.size()) * 3 + 1];
            abyte[0] = 1;
            int i = 0;
            final Iterator iterator = custom ? render.cursors.iterator() : this.worldMap.g.values().iterator();
            while (iterator.hasNext()) {
                final MapCursor cursor = custom ? iterator.next() : null;
                if (cursor == null || cursor.isVisible()) {
                    final WorldMapDecoration deco = custom ? null : iterator.next();
                    abyte[i * 3 + 1] = (byte)((custom ? cursor.getRawType() : deco.type) << 4 | ((custom ? cursor.getDirection() : deco.rotation) & 0xF));
                    abyte[i * 3 + 2] = (custom ? cursor.getX() : deco.locX);
                    abyte[i * 3 + 3] = (custom ? cursor.getY() : deco.locY);
                }
                ++i;
            }
            boolean flag = !itemstack.z();
            if (this.h != null && this.h.length == abyte.length) {
                for (int j = 0; j < abyte.length; ++j) {
                    if (abyte[j] != this.h[j]) {
                        flag = false;
                        break;
                    }
                }
            }
            else {
                flag = false;
            }
            if (!flag) {
                return this.h = abyte;
            }
        }
        for (int k = 0; k < 1; ++k) {
            final int i = this.f++ * 11 % 128;
            if (this.b[i] >= 0) {
                final int l = this.c[i] - this.b[i] + 1;
                final int j = this.b[i];
                final byte[] abyte2 = new byte[l + 3];
                abyte2[0] = 0;
                abyte2[1] = (byte)i;
                abyte2[2] = (byte)j;
                for (int i2 = 0; i2 < abyte2.length - 3; ++i2) {
                    abyte2[i2 + 3] = (custom ? render.buffer : this.worldMap.colors)[(i2 + j) * 128 + i];
                }
                this.c[i] = -1;
                this.b[i] = -1;
                return abyte2;
            }
        }
        return null;
    }
}
