// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.updater;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.List;

public class AutoUpdater
{
    public static final String WARN_CONSOLE = "warn-console";
    public static final String WARN_OPERATORS = "warn-ops";
    private final BukkitDLUpdaterService service;
    private final List<String> onUpdate;
    private final List<String> onBroken;
    private final Logger log;
    private final String channel;
    private boolean enabled;
    private ArtifactDetails current;
    private ArtifactDetails latest;
    private boolean suggestChannels;
    
    public AutoUpdater(final BukkitDLUpdaterService service, final Logger log, final String channel) {
        this.onUpdate = new ArrayList<String>();
        this.onBroken = new ArrayList<String>();
        this.current = null;
        this.latest = null;
        this.suggestChannels = true;
        this.service = service;
        this.log = log;
        this.channel = channel;
    }
    
    public String getChannel() {
        return this.channel;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean isEnabled) {
        this.enabled = isEnabled;
    }
    
    public boolean shouldSuggestChannels() {
        return this.suggestChannels;
    }
    
    public void setSuggestChannels(final boolean suggestChannels) {
        this.suggestChannels = suggestChannels;
    }
    
    public List<String> getOnBroken() {
        return this.onBroken;
    }
    
    public List<String> getOnUpdate() {
        return this.onUpdate;
    }
    
    public boolean isUpdateAvailable() {
        return this.latest != null && this.current != null && this.isEnabled() && this.latest.getCreated().after(this.current.getCreated());
    }
    
    public ArtifactDetails getCurrent() {
        return this.current;
    }
    
    public ArtifactDetails getLatest() {
        return this.latest;
    }
    
    public void check(final String currentSlug) {
        if (!this.isEnabled()) {
            return;
        }
        new Thread() {
            public void run() {
                AutoUpdater.this.current = AutoUpdater.this.service.getArtifact(currentSlug, "information about this CraftBukkit version; perhaps you are running a custom one?");
                AutoUpdater.this.latest = AutoUpdater.this.service.getArtifact("latest-" + AutoUpdater.this.channel, "latest artifact information");
                if (AutoUpdater.this.isUpdateAvailable()) {
                    if (AutoUpdater.this.current.isBroken() && AutoUpdater.this.onBroken.contains("warn-console")) {
                        AutoUpdater.this.log.severe("----- Bukkit Auto Updater -----");
                        AutoUpdater.this.log.severe("Your version of CraftBukkit is known to be broken. It is strongly advised that you update to a more recent version ASAP.");
                        AutoUpdater.this.log.severe("Known issues with your version:");
                        for (final String line : AutoUpdater.this.current.getBrokenReason().split("\n")) {
                            AutoUpdater.this.log.severe("> " + line);
                        }
                        AutoUpdater.this.log.severe("Newer version " + AutoUpdater.this.latest.getVersion() + " (build #" + AutoUpdater.this.latest.getBuildNumber() + ") was released on " + AutoUpdater.this.latest.getCreated() + ".");
                        AutoUpdater.this.log.severe("Details: " + AutoUpdater.this.latest.getHtmlUrl());
                        AutoUpdater.this.log.severe("Download: " + AutoUpdater.this.latest.getFile().getUrl());
                        AutoUpdater.this.log.severe("----- ------------------- -----");
                    }
                    else if (AutoUpdater.this.onUpdate.contains("warn-console")) {
                        AutoUpdater.this.log.warning("----- Bukkit Auto Updater -----");
                        AutoUpdater.this.log.warning("Your version of CraftBukkit is out of date. Version " + AutoUpdater.this.latest.getVersion() + " (build #" + AutoUpdater.this.latest.getBuildNumber() + ") was released on " + AutoUpdater.this.latest.getCreated() + ".");
                        AutoUpdater.this.log.warning("Details: " + AutoUpdater.this.latest.getHtmlUrl());
                        AutoUpdater.this.log.warning("Download: " + AutoUpdater.this.latest.getFile().getUrl());
                        AutoUpdater.this.log.warning("----- ------------------- -----");
                    }
                }
                else if (AutoUpdater.this.current != null && AutoUpdater.this.current.isBroken() && AutoUpdater.this.onBroken.contains("warn-console")) {
                    AutoUpdater.this.log.severe("----- Bukkit Auto Updater -----");
                    AutoUpdater.this.log.severe("Your version of CraftBukkit is known to be broken. It is strongly advised that you update to a more recent version ASAP.");
                    AutoUpdater.this.log.severe("Known issues with your version:");
                    for (final String line : AutoUpdater.this.current.getBrokenReason().split("\n")) {
                        AutoUpdater.this.log.severe("> " + line);
                    }
                    AutoUpdater.this.log.severe("Unfortunately, there is not yet a newer version suitable for your server. We would advise you wait an hour or two, or try out a dev build.");
                    AutoUpdater.this.log.severe("----- ------------------- -----");
                }
                else if (AutoUpdater.this.current != null && AutoUpdater.this.shouldSuggestChannels()) {
                    final ArtifactDetails.ChannelDetails prefChan = AutoUpdater.this.service.getChannel(AutoUpdater.this.channel, "preferred channel details");
                    if (prefChan != null && AutoUpdater.this.current.getChannel().getPriority() < prefChan.getPriority()) {
                        AutoUpdater.this.log.info("----- Bukkit Auto Updater -----");
                        AutoUpdater.this.log.info("It appears that you're running a " + AutoUpdater.this.current.getChannel().getName() + ", when you've specified in bukkit.yml that you prefer to run " + prefChan.getName() + "s.");
                        AutoUpdater.this.log.info("If you would like to be kept informed about new " + AutoUpdater.this.current.getChannel().getName() + " releases, it is recommended that you change 'preferred-channel' in your bukkit.yml to '" + AutoUpdater.this.current.getChannel().getSlug() + "'.");
                        AutoUpdater.this.log.info("With that set, you will be told whenever a new version is available for download, so that you can always keep up to date and secure with the latest fixes.");
                        AutoUpdater.this.log.info("If you would like to disable this warning, simply set 'suggest-channels' to false in bukkit.yml.");
                        AutoUpdater.this.log.info("----- ------------------- -----");
                    }
                }
            }
        }.start();
    }
}
