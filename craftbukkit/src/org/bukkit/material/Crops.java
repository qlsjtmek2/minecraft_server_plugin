// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.CropState;
import org.bukkit.Material;

public class Crops extends MaterialData
{
    public Crops() {
        super(Material.CROPS);
    }
    
    public Crops(final CropState state) {
        this();
        this.setState(state);
    }
    
    public Crops(final int type) {
        super(type);
    }
    
    public Crops(final Material type) {
        super(type);
    }
    
    public Crops(final int type, final byte data) {
        super(type, data);
    }
    
    public Crops(final Material type, final byte data) {
        super(type, data);
    }
    
    public CropState getState() {
        return CropState.getByData(this.getData());
    }
    
    public void setState(final CropState state) {
        this.setData(state.getData());
    }
    
    public String toString() {
        return this.getState() + " " + super.toString();
    }
    
    public Crops clone() {
        return (Crops)super.clone();
    }
}
