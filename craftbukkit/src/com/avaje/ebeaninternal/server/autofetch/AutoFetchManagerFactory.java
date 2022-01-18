// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.autofetch;

import javax.persistence.PersistenceException;
import java.util.logging.Level;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.File;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.server.resource.ResourceManager;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import java.util.logging.Logger;

public class AutoFetchManagerFactory
{
    private static final Logger logger;
    
    public static AutoFetchManager create(final SpiEbeanServer server, final ServerConfig serverConfig, final ResourceManager resourceManager) {
        final AutoFetchManagerFactory me = new AutoFetchManagerFactory();
        return me.createAutoFetchManager(server, serverConfig, resourceManager);
    }
    
    private AutoFetchManager createAutoFetchManager(final SpiEbeanServer server, final ServerConfig serverConfig, final ResourceManager resourceManager) {
        final AutoFetchManager manager = this.createAutoFetchManager(server.getName(), resourceManager);
        manager.setOwner(server, serverConfig);
        return manager;
    }
    
    private AutoFetchManager createAutoFetchManager(final String serverName, final ResourceManager resourceManager) {
        final File autoFetchFile = this.getAutoFetchFile(serverName, resourceManager);
        AutoFetchManager autoFetchManager = null;
        final boolean readFile = GlobalProperties.getBoolean("autofetch.readfromfile", true);
        if (readFile) {
            autoFetchManager = this.deserializeAutoFetch(autoFetchFile);
        }
        if (autoFetchManager == null) {
            autoFetchManager = new DefaultAutoFetchManager(autoFetchFile.getAbsolutePath());
        }
        return autoFetchManager;
    }
    
    private AutoFetchManager deserializeAutoFetch(final File autoFetchFile) {
        try {
            if (!autoFetchFile.exists()) {
                return null;
            }
            final FileInputStream fi = new FileInputStream(autoFetchFile);
            final ObjectInputStream ois = new ObjectInputStream(fi);
            final AutoFetchManager profListener = (AutoFetchManager)ois.readObject();
            AutoFetchManagerFactory.logger.info("AutoFetch deserialized from file [" + autoFetchFile.getAbsolutePath() + "]");
            return profListener;
        }
        catch (Exception ex) {
            AutoFetchManagerFactory.logger.log(Level.SEVERE, "Error loading autofetch file " + autoFetchFile.getAbsolutePath(), ex);
            return null;
        }
    }
    
    private File getAutoFetchFile(final String serverName, final ResourceManager resourceManager) {
        final String fileName = ".ebean." + serverName + ".autofetch";
        final File dir = resourceManager.getAutofetchDirectory();
        if (!dir.exists() && !dir.mkdirs()) {
            final String m = "Unable to create directory [" + dir + "] for autofetch file [" + fileName + "]";
            throw new PersistenceException(m);
        }
        return new File(dir, fileName);
    }
    
    static {
        logger = Logger.getLogger(AutoFetchManagerFactory.class.getName());
    }
}
