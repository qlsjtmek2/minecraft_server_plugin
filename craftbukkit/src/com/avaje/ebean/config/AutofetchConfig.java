// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

public class AutofetchConfig
{
    private AutofetchMode mode;
    private boolean queryTuning;
    private boolean queryTuningAddVersion;
    private boolean profiling;
    private int profilingMin;
    private int profilingBase;
    private double profilingRate;
    private boolean useFileLogging;
    private String logDirectory;
    private int profileUpdateFrequency;
    private int garbageCollectionWait;
    
    public AutofetchConfig() {
        this.mode = AutofetchMode.DEFAULT_ONIFEMPTY;
        this.queryTuning = false;
        this.queryTuningAddVersion = false;
        this.profiling = false;
        this.profilingMin = 1;
        this.profilingBase = 10;
        this.profilingRate = 0.05;
        this.useFileLogging = false;
        this.profileUpdateFrequency = 60;
        this.garbageCollectionWait = 100;
    }
    
    public AutofetchMode getMode() {
        return this.mode;
    }
    
    public void setMode(final AutofetchMode mode) {
        this.mode = mode;
    }
    
    public boolean isQueryTuning() {
        return this.queryTuning;
    }
    
    public void setQueryTuning(final boolean queryTuning) {
        this.queryTuning = queryTuning;
    }
    
    public boolean isQueryTuningAddVersion() {
        return this.queryTuningAddVersion;
    }
    
    public void setQueryTuningAddVersion(final boolean queryTuningAddVersion) {
        this.queryTuningAddVersion = queryTuningAddVersion;
    }
    
    public boolean isProfiling() {
        return this.profiling;
    }
    
    public void setProfiling(final boolean profiling) {
        this.profiling = profiling;
    }
    
    public int getProfilingMin() {
        return this.profilingMin;
    }
    
    public void setProfilingMin(final int profilingMin) {
        this.profilingMin = profilingMin;
    }
    
    public int getProfilingBase() {
        return this.profilingBase;
    }
    
    public void setProfilingBase(final int profilingBase) {
        this.profilingBase = profilingBase;
    }
    
    public double getProfilingRate() {
        return this.profilingRate;
    }
    
    public void setProfilingRate(final double profilingRate) {
        this.profilingRate = profilingRate;
    }
    
    public boolean isUseFileLogging() {
        return this.useFileLogging;
    }
    
    public void setUseFileLogging(final boolean useFileLogging) {
        this.useFileLogging = useFileLogging;
    }
    
    public String getLogDirectory() {
        return this.logDirectory;
    }
    
    public String getLogDirectoryWithEval() {
        return GlobalProperties.evaluateExpressions(this.logDirectory);
    }
    
    public void setLogDirectory(final String logDirectory) {
        this.logDirectory = logDirectory;
    }
    
    public int getProfileUpdateFrequency() {
        return this.profileUpdateFrequency;
    }
    
    public void setProfileUpdateFrequency(final int profileUpdateFrequency) {
        this.profileUpdateFrequency = profileUpdateFrequency;
    }
    
    public int getGarbageCollectionWait() {
        return this.garbageCollectionWait;
    }
    
    public void setGarbageCollectionWait(final int garbageCollectionWait) {
        this.garbageCollectionWait = garbageCollectionWait;
    }
    
    public void loadSettings(final ConfigPropertyMap p) {
        this.logDirectory = p.get("autofetch.logDirectory", null);
        this.queryTuning = p.getBoolean("autofetch.querytuning", false);
        this.queryTuningAddVersion = p.getBoolean("autofetch.queryTuningAddVersion", false);
        this.profiling = p.getBoolean("autofetch.profiling", false);
        this.mode = p.getEnum(AutofetchMode.class, "autofetch.implicitmode", AutofetchMode.DEFAULT_ONIFEMPTY);
        this.profilingMin = p.getInt("autofetch.profiling.min", 1);
        this.profilingBase = p.getInt("autofetch.profiling.base", 10);
        final String rate = p.get("autofetch.profiling.rate", "0.05");
        this.profilingRate = Double.parseDouble(rate);
        this.useFileLogging = p.getBoolean("autofetch.useFileLogging", this.profiling);
        this.profileUpdateFrequency = p.getInt("autofetch.profiling.updatefrequency", 60);
    }
}
