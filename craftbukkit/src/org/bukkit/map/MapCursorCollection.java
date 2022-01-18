// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.map;

import java.util.ArrayList;
import java.util.List;

public final class MapCursorCollection
{
    private List<MapCursor> cursors;
    
    public MapCursorCollection() {
        this.cursors = new ArrayList<MapCursor>();
    }
    
    public int size() {
        return this.cursors.size();
    }
    
    public MapCursor getCursor(final int index) {
        return this.cursors.get(index);
    }
    
    public boolean removeCursor(final MapCursor cursor) {
        return this.cursors.remove(cursor);
    }
    
    public MapCursor addCursor(final MapCursor cursor) {
        this.cursors.add(cursor);
        return cursor;
    }
    
    public MapCursor addCursor(final int x, final int y, final byte direction) {
        return this.addCursor(x, y, direction, (byte)0, true);
    }
    
    public MapCursor addCursor(final int x, final int y, final byte direction, final byte type) {
        return this.addCursor(x, y, direction, type, true);
    }
    
    public MapCursor addCursor(final int x, final int y, final byte direction, final byte type, final boolean visible) {
        return this.addCursor(new MapCursor((byte)x, (byte)y, direction, type, visible));
    }
}
