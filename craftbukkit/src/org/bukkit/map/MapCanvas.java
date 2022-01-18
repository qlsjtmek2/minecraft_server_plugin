// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.map;

import java.awt.Image;

public interface MapCanvas
{
    MapView getMapView();
    
    MapCursorCollection getCursors();
    
    void setCursors(final MapCursorCollection p0);
    
    void setPixel(final int p0, final int p1, final byte p2);
    
    byte getPixel(final int p0, final int p1);
    
    byte getBasePixel(final int p0, final int p1);
    
    void drawImage(final int p0, final int p1, final Image p2);
    
    void drawText(final int p0, final int p1, final MapFont p2, final String p3);
}
