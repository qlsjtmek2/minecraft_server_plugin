// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.configuration;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.OfflinePlayer;
import org.bukkit.util.Vector;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.util.NumberConversions;
import java.util.Iterator;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang.Validate;
import java.util.LinkedHashMap;
import java.util.Map;

public class MemorySection implements ConfigurationSection
{
    protected final Map<String, Object> map;
    private final Configuration root;
    private final ConfigurationSection parent;
    private final String path;
    private final String fullPath;
    
    protected MemorySection() {
        this.map = new LinkedHashMap<String, Object>();
        if (!(this instanceof Configuration)) {
            throw new IllegalStateException("Cannot construct a root MemorySection when not a Configuration");
        }
        this.path = "";
        this.fullPath = "";
        this.parent = null;
        this.root = (Configuration)this;
    }
    
    protected MemorySection(final ConfigurationSection parent, final String path) {
        this.map = new LinkedHashMap<String, Object>();
        Validate.notNull(parent, "Parent cannot be null");
        Validate.notNull(path, "Path cannot be null");
        this.path = path;
        this.parent = parent;
        Validate.notNull(this.root = parent.getRoot(), "Path cannot be orphaned");
        this.fullPath = createPath(parent, path);
    }
    
    public Set<String> getKeys(final boolean deep) {
        final Set<String> result = new LinkedHashSet<String>();
        final Configuration root = this.getRoot();
        if (root != null && root.options().copyDefaults()) {
            final ConfigurationSection defaults = this.getDefaultSection();
            if (defaults != null) {
                result.addAll(defaults.getKeys(deep));
            }
        }
        this.mapChildrenKeys(result, this, deep);
        return result;
    }
    
    public Map<String, Object> getValues(final boolean deep) {
        final Map<String, Object> result = new LinkedHashMap<String, Object>();
        final Configuration root = this.getRoot();
        if (root != null && root.options().copyDefaults()) {
            final ConfigurationSection defaults = this.getDefaultSection();
            if (defaults != null) {
                result.putAll(defaults.getValues(deep));
            }
        }
        this.mapChildrenValues(result, this, deep);
        return result;
    }
    
    public boolean contains(final String path) {
        return this.get(path) != null;
    }
    
    public boolean isSet(final String path) {
        final Configuration root = this.getRoot();
        if (root == null) {
            return false;
        }
        if (root.options().copyDefaults()) {
            return this.contains(path);
        }
        return this.get(path, null) != null;
    }
    
    public String getCurrentPath() {
        return this.fullPath;
    }
    
    public String getName() {
        return this.path;
    }
    
    public Configuration getRoot() {
        return this.root;
    }
    
    public ConfigurationSection getParent() {
        return this.parent;
    }
    
    public void addDefault(final String path, final Object value) {
        Validate.notNull(path, "Path cannot be null");
        final Configuration root = this.getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot add default without root");
        }
        if (root == this) {
            throw new UnsupportedOperationException("Unsupported addDefault(String, Object) implementation");
        }
        root.addDefault(createPath(this, path), value);
    }
    
    public ConfigurationSection getDefaultSection() {
        final Configuration root = this.getRoot();
        final Configuration defaults = (root == null) ? null : root.getDefaults();
        if (defaults != null && defaults.isConfigurationSection(this.getCurrentPath())) {
            return defaults.getConfigurationSection(this.getCurrentPath());
        }
        return null;
    }
    
    public void set(final String path, final Object value) {
        Validate.notEmpty(path, "Cannot set to an empty path");
        final Configuration root = this.getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot use section without a root");
        }
        final char separator = root.options().pathSeparator();
        int i1 = -1;
        ConfigurationSection section = this;
        int i2;
        while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1) {
            final String node = path.substring(i2, i1);
            final ConfigurationSection subSection = section.getConfigurationSection(node);
            if (subSection == null) {
                section = section.createSection(node);
            }
            else {
                section = subSection;
            }
        }
        final String key = path.substring(i2);
        if (section == this) {
            if (value == null) {
                this.map.remove(key);
            }
            else {
                this.map.put(key, value);
            }
        }
        else {
            section.set(key, value);
        }
    }
    
    public Object get(final String path) {
        return this.get(path, this.getDefault(path));
    }
    
    public Object get(final String path, final Object def) {
        Validate.notNull(path, "Path cannot be null");
        if (path.length() == 0) {
            return this;
        }
        final Configuration root = this.getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot access section without a root");
        }
        final char separator = root.options().pathSeparator();
        int i1 = -1;
        ConfigurationSection section = this;
        int i2;
        while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1) {
            section = section.getConfigurationSection(path.substring(i2, i1));
            if (section == null) {
                return def;
            }
        }
        final String key = path.substring(i2);
        if (section == this) {
            final Object result = this.map.get(key);
            return (result == null) ? def : result;
        }
        return section.get(key, def);
    }
    
    public ConfigurationSection createSection(final String path) {
        Validate.notEmpty(path, "Cannot create section at empty path");
        final Configuration root = this.getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot create section without a root");
        }
        final char separator = root.options().pathSeparator();
        int i1 = -1;
        ConfigurationSection section = this;
        int i2;
        while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1) {
            final String node = path.substring(i2, i1);
            final ConfigurationSection subSection = section.getConfigurationSection(node);
            if (subSection == null) {
                section = section.createSection(node);
            }
            else {
                section = subSection;
            }
        }
        final String key = path.substring(i2);
        if (section == this) {
            final ConfigurationSection result = new MemorySection(this, key);
            this.map.put(key, result);
            return result;
        }
        return section.createSection(key);
    }
    
    public ConfigurationSection createSection(final String path, final Map<?, ?> map) {
        final ConfigurationSection section = this.createSection(path);
        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                section.createSection(entry.getKey().toString(), (Map<?, ?>)entry.getValue());
            }
            else {
                section.set(entry.getKey().toString(), entry.getValue());
            }
        }
        return section;
    }
    
    public String getString(final String path) {
        final Object def = this.getDefault(path);
        return this.getString(path, (def != null) ? def.toString() : null);
    }
    
    public String getString(final String path, final String def) {
        final Object val = this.get(path, def);
        return (val != null) ? val.toString() : def;
    }
    
    public boolean isString(final String path) {
        final Object val = this.get(path);
        return val instanceof String;
    }
    
    public int getInt(final String path) {
        final Object def = this.getDefault(path);
        return this.getInt(path, (def instanceof Number) ? NumberConversions.toInt(def) : 0);
    }
    
    public int getInt(final String path, final int def) {
        final Object val = this.get(path, def);
        return (val instanceof Number) ? NumberConversions.toInt(val) : def;
    }
    
    public boolean isInt(final String path) {
        final Object val = this.get(path);
        return val instanceof Integer;
    }
    
    public boolean getBoolean(final String path) {
        final Object def = this.getDefault(path);
        return this.getBoolean(path, def instanceof Boolean && (boolean)def);
    }
    
    public boolean getBoolean(final String path, final boolean def) {
        final Object val = this.get(path, def);
        return (boolean)((val instanceof Boolean) ? val : def);
    }
    
    public boolean isBoolean(final String path) {
        final Object val = this.get(path);
        return val instanceof Boolean;
    }
    
    public double getDouble(final String path) {
        final Object def = this.getDefault(path);
        return this.getDouble(path, (def instanceof Number) ? NumberConversions.toDouble(def) : 0.0);
    }
    
    public double getDouble(final String path, final double def) {
        final Object val = this.get(path, def);
        return (val instanceof Number) ? NumberConversions.toDouble(val) : def;
    }
    
    public boolean isDouble(final String path) {
        final Object val = this.get(path);
        return val instanceof Double;
    }
    
    public long getLong(final String path) {
        final Object def = this.getDefault(path);
        return this.getLong(path, (def instanceof Number) ? NumberConversions.toLong(def) : 0L);
    }
    
    public long getLong(final String path, final long def) {
        final Object val = this.get(path, def);
        return (val instanceof Number) ? NumberConversions.toLong(val) : def;
    }
    
    public boolean isLong(final String path) {
        final Object val = this.get(path);
        return val instanceof Long;
    }
    
    public List<?> getList(final String path) {
        final Object def = this.getDefault(path);
        return this.getList(path, (def instanceof List) ? ((List)def) : null);
    }
    
    public List<?> getList(final String path, final List<?> def) {
        final Object val = this.get(path, def);
        return (List<?>)((val instanceof List) ? val : def);
    }
    
    public boolean isList(final String path) {
        final Object val = this.get(path);
        return val instanceof List;
    }
    
    public List<String> getStringList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<String>(0);
        }
        final List<String> result = new ArrayList<String>();
        for (final Object object : list) {
            if (object instanceof String || this.isPrimitiveWrapper(object)) {
                result.add(String.valueOf(object));
            }
        }
        return result;
    }
    
    public List<Integer> getIntegerList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<Integer>(0);
        }
        final List<Integer> result = new ArrayList<Integer>();
        for (final Object object : list) {
            if (object instanceof Integer) {
                result.add((Integer)object);
            }
            else if (object instanceof String) {
                try {
                    result.add(Integer.valueOf((String)object));
                }
                catch (Exception ex) {}
            }
            else if (object instanceof Character) {
                result.add((int)(char)object);
            }
            else {
                if (!(object instanceof Number)) {
                    continue;
                }
                result.add(((Number)object).intValue());
            }
        }
        return result;
    }
    
    public List<Boolean> getBooleanList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<Boolean>(0);
        }
        final List<Boolean> result = new ArrayList<Boolean>();
        for (final Object object : list) {
            if (object instanceof Boolean) {
                result.add((Boolean)object);
            }
            else {
                if (!(object instanceof String)) {
                    continue;
                }
                if (Boolean.TRUE.toString().equals(object)) {
                    result.add(true);
                }
                else {
                    if (!Boolean.FALSE.toString().equals(object)) {
                        continue;
                    }
                    result.add(false);
                }
            }
        }
        return result;
    }
    
    public List<Double> getDoubleList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<Double>(0);
        }
        final List<Double> result = new ArrayList<Double>();
        for (final Object object : list) {
            if (object instanceof Double) {
                result.add((Double)object);
            }
            else if (object instanceof String) {
                try {
                    result.add(Double.valueOf((String)object));
                }
                catch (Exception ex) {}
            }
            else if (object instanceof Character) {
                result.add((double)(char)object);
            }
            else {
                if (!(object instanceof Number)) {
                    continue;
                }
                result.add(((Number)object).doubleValue());
            }
        }
        return result;
    }
    
    public List<Float> getFloatList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<Float>(0);
        }
        final List<Float> result = new ArrayList<Float>();
        for (final Object object : list) {
            if (object instanceof Float) {
                result.add((Float)object);
            }
            else if (object instanceof String) {
                try {
                    result.add(Float.valueOf((String)object));
                }
                catch (Exception ex) {}
            }
            else if (object instanceof Character) {
                result.add((float)(char)object);
            }
            else {
                if (!(object instanceof Number)) {
                    continue;
                }
                result.add(((Number)object).floatValue());
            }
        }
        return result;
    }
    
    public List<Long> getLongList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<Long>(0);
        }
        final List<Long> result = new ArrayList<Long>();
        for (final Object object : list) {
            if (object instanceof Long) {
                result.add((Long)object);
            }
            else if (object instanceof String) {
                try {
                    result.add(Long.valueOf((String)object));
                }
                catch (Exception ex) {}
            }
            else if (object instanceof Character) {
                result.add((long)(char)object);
            }
            else {
                if (!(object instanceof Number)) {
                    continue;
                }
                result.add(((Number)object).longValue());
            }
        }
        return result;
    }
    
    public List<Byte> getByteList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<Byte>(0);
        }
        final List<Byte> result = new ArrayList<Byte>();
        for (final Object object : list) {
            if (object instanceof Byte) {
                result.add((Byte)object);
            }
            else if (object instanceof String) {
                try {
                    result.add(Byte.valueOf((String)object));
                }
                catch (Exception ex) {}
            }
            else if (object instanceof Character) {
                result.add((byte)(char)object);
            }
            else {
                if (!(object instanceof Number)) {
                    continue;
                }
                result.add(((Number)object).byteValue());
            }
        }
        return result;
    }
    
    public List<Character> getCharacterList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<Character>(0);
        }
        final List<Character> result = new ArrayList<Character>();
        for (final Object object : list) {
            if (object instanceof Character) {
                result.add((Character)object);
            }
            else if (object instanceof String) {
                final String str = (String)object;
                if (str.length() != 1) {
                    continue;
                }
                result.add(str.charAt(0));
            }
            else {
                if (!(object instanceof Number)) {
                    continue;
                }
                result.add((char)((Number)object).intValue());
            }
        }
        return result;
    }
    
    public List<Short> getShortList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<Short>(0);
        }
        final List<Short> result = new ArrayList<Short>();
        for (final Object object : list) {
            if (object instanceof Short) {
                result.add((Short)object);
            }
            else if (object instanceof String) {
                try {
                    result.add(Short.valueOf((String)object));
                }
                catch (Exception ex) {}
            }
            else if (object instanceof Character) {
                result.add((short)(char)object);
            }
            else {
                if (!(object instanceof Number)) {
                    continue;
                }
                result.add(((Number)object).shortValue());
            }
        }
        return result;
    }
    
    public List<Map<?, ?>> getMapList(final String path) {
        final List<?> list = this.getList(path);
        final List<Map<?, ?>> result = new ArrayList<Map<?, ?>>();
        if (list == null) {
            return result;
        }
        for (final Object object : list) {
            if (object instanceof Map) {
                result.add((Map<?, ?>)object);
            }
        }
        return result;
    }
    
    public Vector getVector(final String path) {
        final Object def = this.getDefault(path);
        return this.getVector(path, (def instanceof Vector) ? ((Vector)def) : null);
    }
    
    public Vector getVector(final String path, final Vector def) {
        final Object val = this.get(path, def);
        return (Vector)((val instanceof Vector) ? val : def);
    }
    
    public boolean isVector(final String path) {
        final Object val = this.get(path);
        return val instanceof Vector;
    }
    
    public OfflinePlayer getOfflinePlayer(final String path) {
        final Object def = this.getDefault(path);
        return this.getOfflinePlayer(path, (def instanceof OfflinePlayer) ? ((OfflinePlayer)def) : null);
    }
    
    public OfflinePlayer getOfflinePlayer(final String path, final OfflinePlayer def) {
        final Object val = this.get(path, def);
        return (OfflinePlayer)((val instanceof OfflinePlayer) ? val : def);
    }
    
    public boolean isOfflinePlayer(final String path) {
        final Object val = this.get(path);
        return val instanceof OfflinePlayer;
    }
    
    public ItemStack getItemStack(final String path) {
        final Object def = this.getDefault(path);
        return this.getItemStack(path, (def instanceof ItemStack) ? ((ItemStack)def) : null);
    }
    
    public ItemStack getItemStack(final String path, final ItemStack def) {
        final Object val = this.get(path, def);
        return (ItemStack)((val instanceof ItemStack) ? val : def);
    }
    
    public boolean isItemStack(final String path) {
        final Object val = this.get(path);
        return val instanceof ItemStack;
    }
    
    public Color getColor(final String path) {
        final Object def = this.getDefault(path);
        return this.getColor(path, (def instanceof Color) ? ((Color)def) : null);
    }
    
    public Color getColor(final String path, final Color def) {
        final Object val = this.get(path, def);
        return (Color)((val instanceof Color) ? val : def);
    }
    
    public boolean isColor(final String path) {
        final Object val = this.get(path);
        return val instanceof Color;
    }
    
    public ConfigurationSection getConfigurationSection(final String path) {
        Object val = this.get(path, null);
        if (val != null) {
            return (val instanceof ConfigurationSection) ? ((ConfigurationSection)val) : null;
        }
        val = this.get(path, this.getDefault(path));
        return (val instanceof ConfigurationSection) ? this.createSection(path) : null;
    }
    
    public boolean isConfigurationSection(final String path) {
        final Object val = this.get(path);
        return val instanceof ConfigurationSection;
    }
    
    protected boolean isPrimitiveWrapper(final Object input) {
        return input instanceof Integer || input instanceof Boolean || input instanceof Character || input instanceof Byte || input instanceof Short || input instanceof Double || input instanceof Long || input instanceof Float;
    }
    
    protected Object getDefault(final String path) {
        Validate.notNull(path, "Path cannot be null");
        final Configuration root = this.getRoot();
        final Configuration defaults = (root == null) ? null : root.getDefaults();
        return (defaults == null) ? null : defaults.get(createPath(this, path));
    }
    
    protected void mapChildrenKeys(final Set<String> output, final ConfigurationSection section, final boolean deep) {
        if (section instanceof MemorySection) {
            final MemorySection sec = (MemorySection)section;
            for (final Map.Entry<String, Object> entry : sec.map.entrySet()) {
                output.add(createPath(section, entry.getKey(), this));
                if (deep && entry.getValue() instanceof ConfigurationSection) {
                    final ConfigurationSection subsection = entry.getValue();
                    this.mapChildrenKeys(output, subsection, deep);
                }
            }
        }
        else {
            final Set<String> keys = section.getKeys(deep);
            for (final String key : keys) {
                output.add(createPath(section, key, this));
            }
        }
    }
    
    protected void mapChildrenValues(final Map<String, Object> output, final ConfigurationSection section, final boolean deep) {
        if (section instanceof MemorySection) {
            final MemorySection sec = (MemorySection)section;
            for (final Map.Entry<String, Object> entry : sec.map.entrySet()) {
                output.put(createPath(section, entry.getKey(), this), entry.getValue());
                if (entry.getValue() instanceof ConfigurationSection && deep) {
                    this.mapChildrenValues(output, entry.getValue(), deep);
                }
            }
        }
        else {
            final Map<String, Object> values = section.getValues(deep);
            for (final Map.Entry<String, Object> entry : values.entrySet()) {
                output.put(createPath(section, entry.getKey(), this), entry.getValue());
            }
        }
    }
    
    public static String createPath(final ConfigurationSection section, final String key) {
        return createPath(section, key, (section == null) ? null : section.getRoot());
    }
    
    public static String createPath(final ConfigurationSection section, final String key, final ConfigurationSection relativeTo) {
        Validate.notNull(section, "Cannot create path without a section");
        final Configuration root = section.getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot create path without a root");
        }
        final char separator = root.options().pathSeparator();
        final StringBuilder builder = new StringBuilder();
        if (section != null) {
            for (ConfigurationSection parent = section; parent != null && parent != relativeTo; parent = parent.getParent()) {
                if (builder.length() > 0) {
                    builder.insert(0, separator);
                }
                builder.insert(0, parent.getName());
            }
        }
        if (key != null && key.length() > 0) {
            if (builder.length() > 0) {
                builder.append(separator);
            }
            builder.append(key);
        }
        return builder.toString();
    }
    
    public String toString() {
        final Configuration root = this.getRoot();
        return this.getClass().getSimpleName() + "[path='" + this.getCurrentPath() + "', root='" + ((root == null) ? null : root.getClass().getSimpleName()) + "']";
    }
}
