// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.Material;

public class Tripwire extends MaterialData
{
    public Tripwire() {
        super(Material.TRIPWIRE);
    }
    
    public Tripwire(final int type) {
        super(type);
    }
    
    public Tripwire(final int type, final byte data) {
        super(type, data);
    }
    
    public boolean isActivated() {
        return (this.getData() & 0x4) != 0x0;
    }
    
    public void setActivated(final boolean act) {
        int dat = this.getData() & 0xB;
        if (act) {
            dat |= 0x4;
        }
        this.setData((byte)dat);
    }
    
    public boolean isObjectTriggering() {
        return (this.getData() & 0x1) != 0x0;
    }
    
    public void setObjectTriggering(final boolean trig) {
        int dat = this.getData() & 0xE;
        if (trig) {
            dat |= 0x1;
        }
        this.setData((byte)dat);
    }
    
    public Tripwire clone() {
        return (Tripwire)super.clone();
    }
    
    public String toString() {
        return super.toString() + (this.isActivated() ? " Activated" : "") + (this.isObjectTriggering() ? " Triggered" : "");
    }
}
