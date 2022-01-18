// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import java.util.HashSet;
import org.bukkit.entity.Entity;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import think.rpgitems.item.RPGItem;
import think.rpgitems.lib.gnu.trove.map.hash.TObjectIntHashMap;
import java.util.HashMap;

public abstract class Power
{
    public static HashMap<String, Class<? extends Power>> powers;
    public static TObjectIntHashMap<String> powerUsage;
    public RPGItem item;
    
    static {
        Power.powers = new HashMap<String, Class<? extends Power>>();
        Power.powerUsage = new TObjectIntHashMap<String>();
    }
    
    public abstract void init(final ConfigurationSection p0);
    
    public abstract void save(final ConfigurationSection p0);
    
    public abstract String getName();
    
    public abstract String displayText(final String p0);
    
    public static Entity[] getNearbyEntities(final Location l, final double radius) {
        final int iRadius = (int)radius;
        final int chunkRadius = (iRadius < 16) ? 1 : ((iRadius - iRadius % 16) / 16);
        final HashSet<Entity> radiusEntities = new HashSet<Entity>();
        for (int chX = 0 - chunkRadius; chX <= chunkRadius; ++chX) {
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; ++chZ) {
                final int x = (int)l.getX();
                final int y = (int)l.getY();
                final int z = (int)l.getZ();
                Entity[] entities;
                for (int length = (entities = new Location(l.getWorld(), (double)(x + chX * 16), (double)y, (double)(z + chZ * 16)).getChunk().getEntities()).length, i = 0; i < length; ++i) {
                    final Entity e = entities[i];
                    if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()) {
                        radiusEntities.add(e);
                    }
                }
            }
        }
        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }
}
