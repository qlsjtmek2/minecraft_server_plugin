// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ReportedException extends RuntimeException
{
    private final CrashReport a;
    
    public ReportedException(final CrashReport a) {
        this.a = a;
    }
    
    public CrashReport a() {
        return this.a;
    }
    
    public Throwable getCause() {
        return this.a.b();
    }
    
    public String getMessage() {
        return this.a.a();
    }
}
