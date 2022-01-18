// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import java.util.ArrayList;
import org.bukkit.Material;
import java.util.List;

public class SmoothBrick extends TexturedMaterial
{
    private static final List<Material> textures;
    
    public SmoothBrick() {
        super(Material.SMOOTH_BRICK);
    }
    
    public SmoothBrick(final int type) {
        super(type);
    }
    
    public SmoothBrick(final Material type) {
        super(SmoothBrick.textures.contains(type) ? Material.SMOOTH_BRICK : type);
        if (SmoothBrick.textures.contains(type)) {
            this.setMaterial(type);
        }
    }
    
    public SmoothBrick(final int type, final byte data) {
        super(type, data);
    }
    
    public SmoothBrick(final Material type, final byte data) {
        super(type, data);
    }
    
    public List<Material> getTextures() {
        return SmoothBrick.textures;
    }
    
    public SmoothBrick clone() {
        return (SmoothBrick)super.clone();
    }
    
    static {
        (textures = new ArrayList<Material>()).add(Material.STONE);
        SmoothBrick.textures.add(Material.MOSSY_COBBLESTONE);
        SmoothBrick.textures.add(Material.COBBLESTONE);
        SmoothBrick.textures.add(Material.SMOOTH_BRICK);
    }
}
