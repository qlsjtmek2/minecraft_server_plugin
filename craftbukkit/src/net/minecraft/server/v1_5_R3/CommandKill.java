// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class CommandKill extends CommandAbstract
{
    public String c() {
        return "kill";
    }
    
    public int a() {
        return 0;
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        CommandAbstract.c(commandListener).damageEntity(DamageSource.OUT_OF_WORLD, 1000);
        commandListener.sendMessage("Ouch. That looks like it hurt.");
    }
}
