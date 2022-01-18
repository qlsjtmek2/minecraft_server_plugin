// 
// Decompiled by Procyon v0.5.30
// 

package com.survivorserver.GlobalMarket;

public class LocaleHandler
{
    ConfigHandler config;
    
    public LocaleHandler(final ConfigHandler config) {
        this.config = config;
    }
    
    public String get(final String string, final Object... args) {
        if (!this.config.getLocaleYML().isSet(string)) {
            return string;
        }
        return String.format(this.config.getLocaleYML().getString(string), args);
    }
    
    public String get(final String string) {
        if (!this.config.getLocaleYML().isSet(string)) {
            return string;
        }
        return this.config.getLocaleYML().getString(string);
    }
}
