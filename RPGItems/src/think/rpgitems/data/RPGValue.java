// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.data;

import think.rpgitems.item.RPGItem;
import org.bukkit.entity.Player;
import java.util.HashMap;

public class RPGValue
{
    Object value;
    static HashMap<String, RPGValue> map;
    
    static {
        RPGValue.map = new HashMap<String, RPGValue>();
    }
    
    public static RPGValue get(final Player player, final RPGItem item, final String name) {
        return RPGValue.map.get(String.valueOf(player.getName()) + "." + item.getID() + "." + name);
    }
    
    public RPGValue(final Player player, final RPGItem item, final String name, final Object value) {
        this.value = value;
        RPGValue.map.put(String.valueOf(player.getName()) + "." + item.getID() + "." + name, this);
    }
    
    public void set(final Object value) {
        this.value = value;
    }
    
    public boolean asBoolean() {
        return (boolean)this.value;
    }
    
    public byte asByte() {
        return (byte)this.value;
    }
    
    public double asDouble() {
        return (double)this.value;
    }
    
    public float asFloat() {
        return (float)this.value;
    }
    
    public int asInt() {
        return (int)this.value;
    }
    
    public long asLong() {
        return (long)this.value;
    }
    
    public short asShort() {
        return (short)this.value;
    }
    
    public String asString() {
        return (String)this.value;
    }
    
    public Object value() {
        return this.value;
    }
}
