// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.resource;

import com.avaje.ebeaninternal.server.lib.resource.DirectoryFinder;
import com.avaje.ebeaninternal.server.lib.util.NotFoundException;
import com.avaje.ebeaninternal.server.lib.resource.FileResourceSource;
import javax.servlet.ServletContext;
import com.avaje.ebeaninternal.server.lib.resource.UrlResourceSource;
import com.avaje.ebean.config.GlobalProperties;
import java.io.File;
import com.avaje.ebeaninternal.server.lib.resource.ResourceSource;
import com.avaje.ebean.config.ServerConfig;
import java.util.logging.Logger;

public class ResourceManagerFactory
{
    private static final Logger logger;
    
    public static ResourceManager createResourceManager(final ServerConfig serverConfig) {
        final ResourceSource resourceSource = createResourceSource(serverConfig);
        final File autofetchDir = getAutofetchDir(serverConfig, resourceSource);
        return new ResourceManager(resourceSource, autofetchDir);
    }
    
    protected static File getAutofetchDir(final ServerConfig serverConfig, final ResourceSource resourceSource) {
        String dir = null;
        if (serverConfig.getAutofetchConfig() != null) {
            dir = serverConfig.getAutofetchConfig().getLogDirectoryWithEval();
        }
        if (dir != null) {
            return new File(dir);
        }
        final String realPath = resourceSource.getRealPath();
        if (realPath != null) {
            return new File(realPath);
        }
        throw new RuntimeException("No autofetch directory set?");
    }
    
    protected static ResourceSource createResourceSource(final ServerConfig serverConfig) {
        String defaultDir = serverConfig.getResourceDirectory();
        final ServletContext sc = GlobalProperties.getServletContext();
        if (sc != null) {
            if (defaultDir == null) {
                defaultDir = "WEB-INF/ebean";
            }
            return new UrlResourceSource(sc, defaultDir);
        }
        return createFileSource(defaultDir);
    }
    
    private static ResourceSource createFileSource(final String fileDir) {
        if (fileDir != null) {
            final File dir = new File(fileDir);
            if (dir.exists()) {
                ResourceManagerFactory.logger.info("ResourceManager initialised: type[file] [" + fileDir + "]");
                return new FileResourceSource(fileDir);
            }
            final String msg = "ResourceManager could not find directory [" + fileDir + "]";
            throw new NotFoundException(msg);
        }
        else {
            final File guessDir = DirectoryFinder.find(null, "WEB-INF", 3);
            if (guessDir != null) {
                ResourceManagerFactory.logger.info("ResourceManager initialised: type[file] [" + guessDir.getPath() + "]");
                return new FileResourceSource(guessDir.getPath());
            }
            final File workingDir = new File(".");
            return new FileResourceSource(workingDir);
        }
    }
    
    static {
        logger = Logger.getLogger(ResourceManagerFactory.class.getName());
    }
}
