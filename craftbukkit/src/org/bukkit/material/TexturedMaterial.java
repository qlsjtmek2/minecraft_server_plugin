// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import java.util.List;
import org.bukkit.Material;

public abstract class TexturedMaterial extends MaterialData
{
    public TexturedMaterial(final Material m) {
        super(m);
    }
    
    public TexturedMaterial(final int type) {
        super(type);
    }
    
    public TexturedMaterial(final int type, final byte data) {
        super(type, data);
    }
    
    public TexturedMaterial(final Material type, final byte data) {
        super(type, data);
    }
    
    public abstract List<Material> getTextures();
    
    public Material getMaterial() {
        int n = this.getTextureIndex();
        if (n > this.getTextures().size() - 1) {
            n = 0;
        }
        return this.getTextures().get(n);
    }
    
    public void setMaterial(final Material material) {
        if (this.getTextures().contains(material)) {
            this.setTextureIndex(this.getTextures().indexOf(material));
        }
        else {
            this.setTextureIndex(0);
        }
    }
    
    protected int getTextureIndex() {
        return this.getData();
    }
    
    protected void setTextureIndex(final int idx) {
        this.setData((byte)idx);
    }
    
    public String toString() {
        return this.getMaterial() + " " + super.toString();
    }
    
    public TexturedMaterial clone() {
        return (TexturedMaterial)super.clone();
    }
}
