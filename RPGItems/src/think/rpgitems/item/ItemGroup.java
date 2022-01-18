// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.item;

import org.bukkit.configuration.ConfigurationSection;
import java.util.ArrayList;
import java.util.List;

public class ItemGroup
{
    private String name;
    private List<Integer> items;
    
    public ItemGroup(final String name) {
        this.items = new ArrayList<Integer>();
        this.name = name;
    }
    
    public ItemGroup(final ConfigurationSection s) {
        this.items = new ArrayList<Integer>();
        this.name = s.getString("name");
        this.items = (List<Integer>)s.getIntegerList("items");
    }
    
    public void save(final ConfigurationSection s) {
        s.set("name", (Object)this.name);
        s.set("items", (Object)this.items);
    }
    
    public String getName() {
        return this.name;
    }
}
