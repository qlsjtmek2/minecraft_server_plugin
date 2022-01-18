// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.configuration.serialization;

import org.bukkit.FireworkEffect;
import org.bukkit.potion.PotionEffect;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;
import java.util.HashMap;
import org.apache.commons.lang.Validate;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import java.util.Map;

public class ConfigurationSerialization
{
    public static final String SERIALIZED_TYPE_KEY = "==";
    private final Class<? extends ConfigurationSerializable> clazz;
    private static Map<String, Class<? extends ConfigurationSerializable>> aliases;
    
    protected ConfigurationSerialization(final Class<? extends ConfigurationSerializable> clazz) {
        this.clazz = clazz;
    }
    
    protected Method getMethod(final String name, final boolean isStatic) {
        try {
            final Method method = this.clazz.getDeclaredMethod(name, Map.class);
            if (!ConfigurationSerializable.class.isAssignableFrom(method.getReturnType())) {
                return null;
            }
            if (Modifier.isStatic(method.getModifiers()) != isStatic) {
                return null;
            }
            return method;
        }
        catch (NoSuchMethodException ex) {
            return null;
        }
        catch (SecurityException ex2) {
            return null;
        }
    }
    
    protected Constructor<? extends ConfigurationSerializable> getConstructor() {
        try {
            return this.clazz.getConstructor(Map.class);
        }
        catch (NoSuchMethodException ex) {
            return null;
        }
        catch (SecurityException ex2) {
            return null;
        }
    }
    
    protected ConfigurationSerializable deserializeViaMethod(final Method method, final Map<String, Object> args) {
        try {
            final ConfigurationSerializable result = (ConfigurationSerializable)method.invoke(null, args);
            if (result != null) {
                return result;
            }
            Logger.getLogger(ConfigurationSerialization.class.getName()).log(Level.SEVERE, "Could not call method '" + method.toString() + "' of " + this.clazz + " for deserialization: method returned null");
        }
        catch (Throwable ex) {
            Logger.getLogger(ConfigurationSerialization.class.getName()).log(Level.SEVERE, "Could not call method '" + method.toString() + "' of " + this.clazz + " for deserialization", (ex instanceof InvocationTargetException) ? ex.getCause() : ex);
        }
        return null;
    }
    
    protected ConfigurationSerializable deserializeViaCtor(final Constructor<? extends ConfigurationSerializable> ctor, final Map<String, Object> args) {
        try {
            return (ConfigurationSerializable)ctor.newInstance(args);
        }
        catch (Throwable ex) {
            Logger.getLogger(ConfigurationSerialization.class.getName()).log(Level.SEVERE, "Could not call constructor '" + ctor.toString() + "' of " + this.clazz + " for deserialization", (ex instanceof InvocationTargetException) ? ex.getCause() : ex);
            return null;
        }
    }
    
    public ConfigurationSerializable deserialize(final Map<String, Object> args) {
        Validate.notNull(args, "Args must not be null");
        ConfigurationSerializable result = null;
        Method method = null;
        if (result == null) {
            method = this.getMethod("deserialize", true);
            if (method != null) {
                result = this.deserializeViaMethod(method, args);
            }
        }
        if (result == null) {
            method = this.getMethod("valueOf", true);
            if (method != null) {
                result = this.deserializeViaMethod(method, args);
            }
        }
        if (result == null) {
            final Constructor<? extends ConfigurationSerializable> constructor = this.getConstructor();
            if (constructor != null) {
                result = this.deserializeViaCtor(constructor, args);
            }
        }
        return result;
    }
    
    public static ConfigurationSerializable deserializeObject(final Map<String, Object> args, final Class<? extends ConfigurationSerializable> clazz) {
        return new ConfigurationSerialization(clazz).deserialize(args);
    }
    
    public static ConfigurationSerializable deserializeObject(final Map<String, Object> args) {
        Class<? extends ConfigurationSerializable> clazz = null;
        if (args.containsKey("==")) {
            try {
                final String alias = args.get("==");
                if (alias == null) {
                    throw new IllegalArgumentException("Cannot have null alias");
                }
                clazz = getClassByAlias(alias);
                if (clazz == null) {
                    throw new IllegalArgumentException("Specified class does not exist ('" + alias + "')");
                }
                return new ConfigurationSerialization(clazz).deserialize(args);
            }
            catch (ClassCastException ex) {
                ex.fillInStackTrace();
                throw ex;
            }
            throw new IllegalArgumentException("Args doesn't contain type key ('==')");
        }
        throw new IllegalArgumentException("Args doesn't contain type key ('==')");
    }
    
    public static void registerClass(final Class<? extends ConfigurationSerializable> clazz) {
        final DelegateDeserialization delegate = clazz.getAnnotation(DelegateDeserialization.class);
        if (delegate == null) {
            registerClass(clazz, getAlias(clazz));
            registerClass(clazz, clazz.getName());
        }
    }
    
    public static void registerClass(final Class<? extends ConfigurationSerializable> clazz, final String alias) {
        ConfigurationSerialization.aliases.put(alias, clazz);
    }
    
    public static void unregisterClass(final String alias) {
        ConfigurationSerialization.aliases.remove(alias);
    }
    
    public static void unregisterClass(final Class<? extends ConfigurationSerializable> clazz) {
        while (ConfigurationSerialization.aliases.values().remove(clazz)) {}
    }
    
    public static Class<? extends ConfigurationSerializable> getClassByAlias(final String alias) {
        return ConfigurationSerialization.aliases.get(alias);
    }
    
    public static String getAlias(final Class<? extends ConfigurationSerializable> clazz) {
        DelegateDeserialization delegate = clazz.getAnnotation(DelegateDeserialization.class);
        if (delegate != null) {
            if (delegate.value() != null && delegate.value() != clazz) {
                return getAlias(delegate.value());
            }
            delegate = null;
        }
        if (delegate == null) {
            final SerializableAs alias = clazz.getAnnotation(SerializableAs.class);
            if (alias != null && alias.value() != null) {
                return alias.value();
            }
        }
        return clazz.getName();
    }
    
    static {
        ConfigurationSerialization.aliases = new HashMap<String, Class<? extends ConfigurationSerializable>>();
        registerClass(Vector.class);
        registerClass(BlockVector.class);
        registerClass(ItemStack.class);
        registerClass(Color.class);
        registerClass(PotionEffect.class);
        registerClass(FireworkEffect.class);
    }
}
