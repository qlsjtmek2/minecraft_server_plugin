// 
// Decompiled by Procyon v0.5.30
// 

package com.survivorserver.GlobalMarket;

import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.io.FileNotFoundException;
import java.util.jar.JarFile;
import java.nio.channels.ReadableByteChannel;
import java.io.FileOutputStream;
import java.nio.channels.Channels;
import java.net.URL;
import java.io.File;
import java.util.Iterator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.net.ServerSocket;

public class MarketServer extends Thread
{
    Market market;
    MarketStorage storage;
    boolean enabled;
    ServerSocket socket;
    Map<String, UUID> sessions;
    List<WebViewer> viewers;
    InterfaceHandler handler;
    Map<String, String> notifications;
    
    public MarketServer(final Market market, final MarketStorage storage, final InterfaceHandler handler) {
        this.storage = storage;
        this.market = market;
        this.handler = handler;
        this.enabled = market.getConfig().getBoolean("server.enable");
        try {
            this.checkLibraries();
            this.loadLibraries();
        }
        catch (Exception e) {
            market.log.warning("Could not load Jackson library: " + e.getMessage());
            e.printStackTrace();
            this.enabled = false;
            return;
        }
        this.sessions = new HashMap<String, UUID>();
        this.viewers = new ArrayList<WebViewer>();
        this.notifications = new HashMap<String, String>();
    }
    
    @Override
    public void run() {
        while (true) {
            Label_0109: {
                try {
                    this.socket = new ServerSocket(6789);
                    break Label_0109;
                }
                catch (IOException e) {
                    this.market.log.warning("Could not start server: " + e.getMessage());
                    this.closeSocket();
                    break Label_0109;
                }
                try {
                    new ServerHandler(this, this.socket.accept(), this.market).start();
                }
                catch (IOException e) {
                    this.market.log.warning("Could not accept client: " + e.getMessage());
                }
            }
            if (!this.enabled) {
                this.closeSocket();
                return;
            }
            continue;
        }
    }
    
    public WebViewer addViewer(final String name) {
        for (final WebViewer viewer : this.viewers) {
            if (viewer.getViewer().equalsIgnoreCase(name)) {
                return viewer;
            }
        }
        WebViewer viewer = new WebViewer(name, this.handler.getVersionId());
        this.viewers.add(viewer);
        return viewer;
    }
    
    public boolean isConnected(final String name) {
        for (final WebViewer viewer : this.viewers) {
            if (viewer.getViewer().equalsIgnoreCase(name) && System.currentTimeMillis() - viewer.getLastSeen() >= 31000L) {
                return true;
            }
        }
        return false;
    }
    
    public UUID currentVersion() {
        return this.handler.getVersionId();
    }
    
    public void notify(final String who, final String message) {
        this.notifications.put(who, message);
    }
    
    public String sendMailToInventory() {
        return "";
    }
    
    public String getSessionId(final String player) {
        if (this.sessions.containsKey(player)) {
            return this.sessions.get(player).toString();
        }
        final UUID uuid = UUID.randomUUID();
        this.sessions.put(player, UUID.randomUUID());
        return uuid.toString();
    }
    
    public String generateSessionId(final String player) {
        if (this.sessions.containsKey(player)) {
            this.sessions.remove(player);
        }
        final UUID uuid = UUID.randomUUID();
        this.sessions.put(player, UUID.randomUUID());
        return uuid.toString();
    }
    
    public void closeSocket() {
        try {
            this.socket.close();
        }
        catch (IOException e) {
            this.market.log.warning("Could not close server: " + e.getMessage());
        }
    }
    
    public void setDisabled() {
        this.enabled = false;
        this.closeSocket();
    }
    
    public void checkLibraries() throws Exception {
        final List<String> libraries = new ArrayList<String>();
        libraries.add("jackson-core");
        libraries.add("jackson-databind");
        libraries.add("jackson-annotations");
        for (final String library : libraries) {
            final File file = new File("lib/" + library + "-2.1.4.jar");
            if (!file.exists()) {
                this.market.log.info("Downloading " + library + "-2.1.4.jar...");
                final URL site = new URL("http://repo1.maven.org/maven2/com/fasterxml/jackson/core/" + library + "/2.1.4/" + library + "-2.1.4.jar");
                final ReadableByteChannel channel = Channels.newChannel(site.openStream());
                final FileOutputStream fos = new FileOutputStream("lib/" + library + "-2.1.4.jar");
                fos.getChannel().transferFrom(channel, 0L, 16777216L);
                fos.close();
            }
        }
    }
    
    public void loadLibraries() throws Exception {
        final List<JarFile> libraries = new ArrayList<JarFile>();
        final File core = new File("lib/jackson-core-2.1.4.jar");
        final File databind = new File("lib/jackson-databind-2.1.4.jar");
        final File annotations = new File("lib/jackson-annotations-2.1.4.jar");
        if (!core.exists() || !databind.exists() || !annotations.exists()) {
            throw new FileNotFoundException();
        }
        libraries.add(new JarFile(core));
        libraries.add(new JarFile(databind));
        libraries.add(new JarFile(annotations));
        for (final JarFile library : libraries) {
            final URL[] urls = { new URL("jar:" + new File(library.getName()).toURI().toURL().toExternalForm() + "!/") };
            for (int i = 0; i < urls.length; ++i) {
                final URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
                final Class<URLClassLoader> sysclass = URLClassLoader.class;
                final Method method = sysclass.getDeclaredMethod("addURL", URL.class);
                method.setAccessible(true);
                method.invoke(sysloader, urls[i]);
            }
        }
    }
}
