// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.map;

import java.util.Iterator;
import org.bukkit.map.MapCursorCollection;
import net.minecraft.server.v1_5_R3.WorldMapDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;
import net.minecraft.server.v1_5_R3.WorldMap;
import org.bukkit.map.MapRenderer;

public class CraftMapRenderer extends MapRenderer
{
    private final WorldMap worldMap;
    
    public CraftMapRenderer(final CraftMapView mapView, final WorldMap worldMap) {
        super(false);
        this.worldMap = worldMap;
    }
    
    public void render(final MapView map, final MapCanvas canvas, final Player player) {
        for (int x = 0; x < 128; ++x) {
            for (int y = 0; y < 128; ++y) {
                canvas.setPixel(x, y, this.worldMap.colors[y * 128 + x]);
            }
        }
        final MapCursorCollection cursors = canvas.getCursors();
        while (cursors.size() > 0) {
            cursors.removeCursor(cursors.getCursor(0));
        }
        for (final Object key : this.worldMap.g.keySet()) {
            final Player other = Bukkit.getPlayerExact((String)key);
            if (other != null && !player.canSee(other)) {
                continue;
            }
            final WorldMapDecoration decoration = this.worldMap.g.get(key);
            cursors.addCursor(decoration.locX, decoration.locY, (byte)(decoration.rotation & 0xF), decoration.type);
        }
    }
}
