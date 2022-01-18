// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.updater;

import java.util.Date;

public class ArtifactDetails
{
    private String brokenReason;
    private boolean isBroken;
    private int buildNumber;
    private String htmlUrl;
    private String version;
    private Date created;
    private FileDetails file;
    private ChannelDetails channel;
    
    public ChannelDetails getChannel() {
        return this.channel;
    }
    
    public void setChannel(final ChannelDetails channel) {
        this.channel = channel;
    }
    
    public boolean isIsBroken() {
        return this.isBroken;
    }
    
    public void setIsBroken(final boolean isBroken) {
        this.isBroken = isBroken;
    }
    
    public FileDetails getFile() {
        return this.file;
    }
    
    public void setFile(final FileDetails file) {
        this.file = file;
    }
    
    public String getBrokenReason() {
        return this.brokenReason;
    }
    
    public void setBrokenReason(final String brokenReason) {
        this.brokenReason = brokenReason;
    }
    
    public int getBuildNumber() {
        return this.buildNumber;
    }
    
    public void setBuildNumber(final int buildNumber) {
        this.buildNumber = buildNumber;
    }
    
    public Date getCreated() {
        return this.created;
    }
    
    public void setCreated(final Date created) {
        this.created = created;
    }
    
    public String getHtmlUrl() {
        return this.htmlUrl;
    }
    
    public void setHtmlUrl(final String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }
    
    public boolean isBroken() {
        return this.isBroken;
    }
    
    public void setBroken(final boolean isBroken) {
        this.isBroken = isBroken;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public void setVersion(final String version) {
        this.version = version;
    }
    
    public static class FileDetails
    {
        private String url;
        
        public String getUrl() {
            return this.url;
        }
        
        public void setUrl(final String url) {
            this.url = url;
        }
    }
    
    public static class ChannelDetails
    {
        private String name;
        private String slug;
        private int priority;
        
        public String getName() {
            return this.name;
        }
        
        public void setName(final String name) {
            this.name = name;
        }
        
        public int getPriority() {
            return this.priority;
        }
        
        public void setPriority(final int priority) {
            this.priority = priority;
        }
        
        public String getSlug() {
            return this.slug;
        }
        
        public void setSlug(final String slug) {
            this.slug = slug;
        }
    }
}
