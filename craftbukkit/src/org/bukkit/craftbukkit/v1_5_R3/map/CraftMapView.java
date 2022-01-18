// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.map;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.server.v1_5_R3.WorldMap;
import org.bukkit.map.MapRenderer;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import java.util.Map;
import org.bukkit.map.MapView;

public final class CraftMapView implements MapView
{
    private final Map<CraftPlayer, RenderData> renderCache;
    public final List<MapRenderer> renderers;
    private final Map<MapRenderer, Map<CraftPlayer, CraftMapCanvas>> canvases;
    protected final WorldMap worldMap;
    
    public CraftMapView(final WorldMap worldMap) {
        this.renderCache = new HashMap<CraftPlayer, RenderData>();
        this.renderers = new ArrayList<MapRenderer>();
        this.canvases = new HashMap<MapRenderer, Map<CraftPlayer, CraftMapCanvas>>();
        this.worldMap = worldMap;
        this.addRenderer(new CraftMapRenderer(this, worldMap));
    }
    
    public short getId() {
        final String text = this.worldMap.id;
        if (text.startsWith("map_")) {
            try {
                return Short.parseShort(text.substring("map_".length()));
            }
            catch (NumberFormatException ex) {
                throw new IllegalStateException("Map has non-numeric ID");
            }
        }
        throw new IllegalStateException("Map has invalid ID");
    }
    
    public boolean isVirtual() {
        return this.renderers.size() > 0 && !(this.renderers.get(0) instanceof CraftMapRenderer);
    }
    
    public Scale getScale() {
        return Scale.valueOf(this.worldMap.scale);
    }
    
    public void setScale(final Scale scale) {
        this.worldMap.scale = scale.getValue();
    }
    
    public World getWorld() {
        final byte dimension = this.worldMap.map;
        for (final World world : Bukkit.getServer().getWorlds()) {
            if (((CraftWorld)world).getHandle().dimension == dimension) {
                return world;
            }
        }
        return null;
    }
    
    public void setWorld(final World world) {
        this.worldMap.map = (byte)((CraftWorld)world).getHandle().dimension;
    }
    
    public int getCenterX() {
        return this.worldMap.centerX;
    }
    
    public int getCenterZ() {
        return this.worldMap.centerZ;
    }
    
    public void setCenterX(final int x) {
        this.worldMap.centerX = x;
    }
    
    public void setCenterZ(final int z) {
        this.worldMap.centerZ = z;
    }
    
    public List<MapRenderer> getRenderers() {
        return new ArrayList<MapRenderer>(this.renderers);
    }
    
    public void addRenderer(final MapRenderer renderer) {
        if (!this.renderers.contains(renderer)) {
            this.renderers.add(renderer);
            this.canvases.put(renderer, new HashMap<CraftPlayer, CraftMapCanvas>());
            renderer.initialize(this);
        }
    }
    
    public boolean removeRenderer(final MapRenderer renderer) {
        if (this.renderers.contains(renderer)) {
            this.renderers.remove(renderer);
            for (final Map.Entry<CraftPlayer, CraftMapCanvas> entry : this.canvases.get(renderer).entrySet()) {
                for (int x = 0; x < 128; ++x) {
                    for (int y = 0; y < 128; ++y) {
                        entry.getValue().setPixel(x, y, (byte)(-1));
                    }
                }
            }
            this.canvases.remove(renderer);
            return true;
        }
        return false;
    }
    
    private boolean isContextual() {
        for (final MapRenderer renderer : this.renderers) {
            if (renderer.isContextual()) {
                return true;
            }
        }
        return false;
    }
    
    public RenderData render(final CraftPlayer player) {
        final boolean context = this.isContextual();
        RenderData render = this.renderCache.get(context ? player : null);
        if (render == null) {
            render = new RenderData();
            this.renderCache.put(context ? player : null, render);
        }
        if (context && this.renderCache.containsKey(null)) {
            this.renderCache.remove(null);
        }
        Arrays.fill(render.buffer, (byte)0);
        render.cursors.clear();
        for (final MapRenderer renderer : this.renderers) {
            CraftMapCanvas canvas = this.canvases.get(renderer).get(renderer.isContextual() ? player : null);
            if (canvas == null) {
                canvas = new CraftMapCanvas(this);
                this.canvases.get(renderer).put(renderer.isContextual() ? player : null, canvas);
            }
            canvas.setBase(render.buffer);
            renderer.render(this, canvas, player);
            final byte[] buf = canvas.getBuffer();
            for (int i = 0; i < buf.length; ++i) {
                if (buf[i] >= 0) {
                    render.buffer[i] = buf[i];
                }
            }
            for (int i = 0; i < canvas.getCursors().size(); ++i) {
                render.cursors.add(canvas.getCursors().getCursor(i));
            }
        }
        return render;
    }
}
