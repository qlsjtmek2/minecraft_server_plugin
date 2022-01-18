// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.material;

import java.util.ArrayList;
import org.bukkit.Material;
import java.util.List;

public class MonsterEggs extends TexturedMaterial
{
    private static final List<Material> textures;
    
    public MonsterEggs() {
        super(Material.MONSTER_EGGS);
    }
    
    public MonsterEggs(final int type) {
        super(type);
    }
    
    public MonsterEggs(final Material type) {
        super(MonsterEggs.textures.contains(type) ? Material.MONSTER_EGGS : type);
        if (MonsterEggs.textures.contains(type)) {
            this.setMaterial(type);
        }
    }
    
    public MonsterEggs(final int type, final byte data) {
        super(type, data);
    }
    
    public MonsterEggs(final Material type, final byte data) {
        super(type, data);
    }
    
    public List<Material> getTextures() {
        return MonsterEggs.textures;
    }
    
    public MonsterEggs clone() {
        return (MonsterEggs)super.clone();
    }
    
    static {
        (textures = new ArrayList<Material>()).add(Material.STONE);
        MonsterEggs.textures.add(Material.COBBLESTONE);
        MonsterEggs.textures.add(Material.SMOOTH_BRICK);
    }
}
