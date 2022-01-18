// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.command;

import net.minecraft.server.v1_5_R3.ChunkCoordinates;
import net.minecraft.server.v1_5_R3.LocaleLanguage;
import java.lang.reflect.Method;
import org.bukkit.command.CommandSender;
import net.minecraft.server.v1_5_R3.ICommandListener;

public class ServerCommandListener implements ICommandListener
{
    private final CommandSender commandSender;
    private final String prefix;
    
    public ServerCommandListener(final CommandSender commandSender) {
        this.commandSender = commandSender;
        final String[] parts = commandSender.getClass().getName().split("\\.");
        this.prefix = parts[parts.length - 1];
    }
    
    public void sendMessage(final String msg) {
        this.commandSender.sendMessage(msg);
    }
    
    public CommandSender getSender() {
        return this.commandSender;
    }
    
    public String getName() {
        try {
            final Method getName = this.commandSender.getClass().getMethod("getName", (Class<?>[])new Class[0]);
            return (String)getName.invoke(this.commandSender, new Object[0]);
        }
        catch (Exception e) {
            return this.prefix;
        }
    }
    
    public String a(final String s, final Object... aobject) {
        return LocaleLanguage.a().a(s, aobject);
    }
    
    public boolean a(final int i, final String s) {
        return true;
    }
    
    public ChunkCoordinates b() {
        return new ChunkCoordinates(0, 0, 0);
    }
}
