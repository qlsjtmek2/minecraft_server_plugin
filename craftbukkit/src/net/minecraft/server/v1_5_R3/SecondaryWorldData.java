// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class SecondaryWorldData extends WorldData
{
    private final WorldData a;
    
    public SecondaryWorldData(final WorldData a) {
        this.a = a;
    }
    
    public NBTTagCompound a() {
        return this.a.a();
    }
    
    public NBTTagCompound a(final NBTTagCompound nbtTagCompound) {
        return this.a.a(nbtTagCompound);
    }
    
    public long getSeed() {
        return this.a.getSeed();
    }
    
    public int c() {
        return this.a.c();
    }
    
    public int d() {
        return this.a.d();
    }
    
    public int e() {
        return this.a.e();
    }
    
    public long getTime() {
        return this.a.getTime();
    }
    
    public long getDayTime() {
        return this.a.getDayTime();
    }
    
    public NBTTagCompound i() {
        return this.a.i();
    }
    
    public int j() {
        return this.a.j();
    }
    
    public String getName() {
        return this.a.getName();
    }
    
    public int l() {
        return this.a.l();
    }
    
    public boolean isThundering() {
        return this.a.isThundering();
    }
    
    public int getThunderDuration() {
        return this.a.getThunderDuration();
    }
    
    public boolean hasStorm() {
        return this.a.hasStorm();
    }
    
    public int getWeatherDuration() {
        return this.a.getWeatherDuration();
    }
    
    public EnumGamemode getGameType() {
        return this.a.getGameType();
    }
    
    public void setTime(final long n) {
    }
    
    public void setDayTime(final long n) {
    }
    
    public void setSpawn(final int n, final int n2, final int n3) {
    }
    
    public void setName(final String s) {
    }
    
    public void e(final int n) {
    }
    
    public void setThundering(final boolean b) {
    }
    
    public void setThunderDuration(final int n) {
    }
    
    public void setStorm(final boolean b) {
    }
    
    public void setWeatherDuration(final int n) {
    }
    
    public boolean shouldGenerateMapFeatures() {
        return this.a.shouldGenerateMapFeatures();
    }
    
    public boolean isHardcore() {
        return this.a.isHardcore();
    }
    
    public WorldType getType() {
        return this.a.getType();
    }
    
    public void setType(final WorldType worldType) {
    }
    
    public boolean allowCommands() {
        return this.a.allowCommands();
    }
    
    public boolean isInitialized() {
        return this.a.isInitialized();
    }
    
    public void d(final boolean b) {
    }
    
    public GameRules getGameRules() {
        return this.a.getGameRules();
    }
}
