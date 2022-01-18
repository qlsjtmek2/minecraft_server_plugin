// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandGamemode extends CommandAbstract
{
    public String c() {
        return "gamemode";
    }
    
    public int a() {
        return 2;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.gamemode.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length > 0) {
            final EnumGamemode e = this.e(commandListener, array[0]);
            final EntityPlayer entityPlayer = (array.length >= 2) ? CommandAbstract.c(commandListener, array[1]) : CommandAbstract.c(commandListener);
            entityPlayer.a(e);
            entityPlayer.fallDistance = 0.0f;
            final String value = LocaleI18n.get("gameMode." + e.b());
            if (entityPlayer != commandListener) {
                CommandAbstract.a(commandListener, 1, "commands.gamemode.success.other", entityPlayer.getLocalizedName(), value);
            }
            else {
                CommandAbstract.a(commandListener, 1, "commands.gamemode.success.self", value);
            }
            return;
        }
        throw new ExceptionUsage("commands.gamemode.usage", new Object[0]);
    }
    
    protected EnumGamemode e(final ICommandListener commandListener, final String s) {
        if (s.equalsIgnoreCase(EnumGamemode.SURVIVAL.b()) || s.equalsIgnoreCase("s")) {
            return EnumGamemode.SURVIVAL;
        }
        if (s.equalsIgnoreCase(EnumGamemode.CREATIVE.b()) || s.equalsIgnoreCase("c")) {
            return EnumGamemode.CREATIVE;
        }
        if (s.equalsIgnoreCase(EnumGamemode.ADVENTURE.b()) || s.equalsIgnoreCase("a")) {
            return EnumGamemode.ADVENTURE;
        }
        return WorldSettings.a(CommandAbstract.a(commandListener, s, 0, EnumGamemode.values().length - 2));
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1) {
            return CommandAbstract.a(array, "survival", "creative", "adventure");
        }
        if (array.length == 2) {
            return CommandAbstract.a(array, this.d());
        }
        return null;
    }
    
    protected String[] d() {
        return MinecraftServer.getServer().getPlayers();
    }
    
    public boolean a(final String[] array, final int n) {
        return n == 1;
    }
}
