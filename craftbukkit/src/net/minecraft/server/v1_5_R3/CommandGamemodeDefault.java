// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class CommandGamemodeDefault extends CommandGamemode
{
    public String c() {
        return "defaultgamemode";
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.defaultgamemode.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length > 0) {
            final EnumGamemode e = this.e(commandListener, array[0]);
            this.a(e);
            CommandAbstract.a(commandListener, "commands.defaultgamemode.success", LocaleI18n.get("gameMode." + e.b()));
            return;
        }
        throw new ExceptionUsage("commands.defaultgamemode.usage", new Object[0]);
    }
    
    protected void a(final EnumGamemode enumgamemode) {
        MinecraftServer.getServer().a(enumgamemode);
    }
}
