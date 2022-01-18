// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class RemoteControlCommandListener implements ICommandListener
{
    public static final RemoteControlCommandListener instance;
    private StringBuffer b;
    
    public RemoteControlCommandListener() {
        this.b = new StringBuffer();
    }
    
    public void c() {
        this.b.setLength(0);
    }
    
    public String d() {
        return this.b.toString();
    }
    
    public String getName() {
        return "Rcon";
    }
    
    public void sendMessage(final String s) {
        this.b.append(s);
    }
    
    public boolean a(final int n, final String s) {
        return true;
    }
    
    public String a(final String s, final Object... aobject) {
        return LocaleLanguage.a().a(s, aobject);
    }
    
    public ChunkCoordinates b() {
        return new ChunkCoordinates(0, 0, 0);
    }
    
    static {
        instance = new RemoteControlCommandListener();
    }
}
