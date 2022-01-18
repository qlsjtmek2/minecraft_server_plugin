// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.config.ServerConfig;

public class ConfigBuilder
{
    public ServerConfig build(final String serverName) {
        final ServerConfig config = new ServerConfig();
        config.setName(serverName);
        config.loadFromProperties();
        return config;
    }
}
