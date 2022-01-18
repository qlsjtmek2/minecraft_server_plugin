// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandBanIp extends CommandAbstract
{
    public static final Pattern a;
    
    public String c() {
        return "ban-ip";
    }
    
    public int a() {
        return 3;
    }
    
    public boolean b(final ICommandListener commandListener) {
        return MinecraftServer.getServer().getPlayerList().getIPBans().isEnabled() && super.b(commandListener);
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.banip.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length >= 1 && array[0].length() > 1) {
            final Matcher matcher = CommandBanIp.a.matcher(array[0]);
            String a = null;
            if (array.length >= 2) {
                a = CommandAbstract.a(commandListener, array, 1);
            }
            if (matcher.matches()) {
                this.a(commandListener, array[0], a);
            }
            else {
                final EntityPlayer player = MinecraftServer.getServer().getPlayerList().getPlayer(array[0]);
                if (player == null) {
                    throw new ExceptionPlayerNotFound("commands.banip.invalid", new Object[0]);
                }
                this.a(commandListener, player.p(), a);
            }
            return;
        }
        throw new ExceptionUsage("commands.banip.usage", new Object[0]);
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1) {
            return CommandAbstract.a(array, MinecraftServer.getServer().getPlayers());
        }
        return null;
    }
    
    protected void a(final ICommandListener commandListener, final String s, final String reason) {
        final BanEntry banEntry = new BanEntry(s);
        banEntry.setSource(commandListener.getName());
        if (reason != null) {
            banEntry.setReason(reason);
        }
        MinecraftServer.getServer().getPlayerList().getIPBans().add(banEntry);
        final List j = MinecraftServer.getServer().getPlayerList().j(s);
        final String[] array = new String[j.size()];
        int n = 0;
        for (final EntityPlayer entityPlayer : j) {
            entityPlayer.playerConnection.disconnect("You have been IP banned.");
            array[n++] = entityPlayer.getLocalizedName();
        }
        if (j.isEmpty()) {
            CommandAbstract.a(commandListener, "commands.banip.success", s);
        }
        else {
            CommandAbstract.a(commandListener, "commands.banip.success.players", s, CommandAbstract.a((Object[])array));
        }
    }
    
    static {
        a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    }
}
