// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ConvertProgressUpdater implements IProgressUpdate
{
    private long b;
    final /* synthetic */ MinecraftServer a;
    
    public ConvertProgressUpdater(final MinecraftServer a) {
        this.a = a;
        this.b = System.currentTimeMillis();
    }
    
    public void a(final String s) {
    }
    
    public void a(final int n) {
        if (System.currentTimeMillis() - this.b >= 1000L) {
            this.b = System.currentTimeMillis();
            this.a.getLogger().info("Converting... " + n + "%");
        }
    }
    
    public void c(final String s) {
    }
}
