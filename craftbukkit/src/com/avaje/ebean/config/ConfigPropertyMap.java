// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

class ConfigPropertyMap implements GlobalProperties.PropertySource
{
    private final String serverName;
    
    public ConfigPropertyMap(final String serverName) {
        this.serverName = serverName;
    }
    
    public String getServerName() {
        return this.serverName;
    }
    
    public String get(final String key, final String defaultValue) {
        final String namedKey = "ebean." + this.serverName + "." + key;
        final String inheritKey = "ebean." + key;
        String value = GlobalProperties.get(namedKey, null);
        if (value == null) {
            value = GlobalProperties.get(inheritKey, null);
        }
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
    
    public int getInt(final String key, final int defaultValue) {
        final String value = this.get(key, String.valueOf(defaultValue));
        return Integer.parseInt(value);
    }
    
    public boolean getBoolean(final String key, final boolean defaultValue) {
        final String value = this.get(key, String.valueOf(defaultValue));
        return Boolean.parseBoolean(value);
    }
    
    public <T extends Enum<T>> T getEnum(final Class<T> enumType, final String key, final T defaultValue) {
        final String level = this.get(key, defaultValue.name());
        return Enum.valueOf(enumType, level.toUpperCase());
    }
}
