// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

class CrashReportDetail
{
    private final String a;
    private final String b;
    
    public CrashReportDetail(final String a, final Object o) {
        this.a = a;
        if (o == null) {
            this.b = "~~NULL~~";
        }
        else if (o instanceof Throwable) {
            final Throwable t = (Throwable)o;
            this.b = "~~ERROR~~ " + t.getClass().getSimpleName() + ": " + t.getMessage();
        }
        else {
            this.b = o.toString();
        }
    }
    
    public String a() {
        return this.a;
    }
    
    public String b() {
        return this.b;
    }
}
