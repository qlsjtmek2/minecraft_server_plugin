// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import org.bukkit.Material;

public class Cauldron extends MaterialData
{
    private static final int CAULDRON_FULL = 3;
    private static final int CAULDRON_EMPTY = 0;
    
    public Cauldron() {
        super(Material.CAULDRON);
    }
    
    public Cauldron(final int type, final byte data) {
        super(type, data);
    }
    
    public Cauldron(final byte data) {
        super(Material.CAULDRON, data);
    }
    
    public boolean isFull() {
        return this.getData() >= 3;
    }
    
    public boolean isEmpty() {
        return this.getData() <= 0;
    }
    
    public String toString() {
        return (this.isEmpty() ? "EMPTY" : (this.isFull() ? "FULL" : (this.getData() + "/3 FULL"))) + " CAULDRON";
    }
    
    public Cauldron clone() {
        return (Cauldron)super.clone();
    }
}
