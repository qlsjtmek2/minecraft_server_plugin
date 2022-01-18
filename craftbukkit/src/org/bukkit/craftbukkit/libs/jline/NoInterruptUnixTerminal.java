// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline;

public class NoInterruptUnixTerminal extends UnixTerminal
{
    public void init() throws Exception {
        super.init();
        this.getSettings().set("intr undef");
    }
    
    public void restore() throws Exception {
        this.getSettings().set("intr ^C");
        super.restore();
    }
}
