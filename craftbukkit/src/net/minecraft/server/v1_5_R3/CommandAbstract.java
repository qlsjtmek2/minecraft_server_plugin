// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class CommandAbstract implements ICommand
{
    private static ICommandDispatcher a;
    
    public int a() {
        return 4;
    }
    
    public String a(final ICommandListener commandListener) {
        return "/" + this.c();
    }
    
    public List b() {
        return null;
    }
    
    public boolean b(final ICommandListener commandListener) {
        return commandListener.a(this.a(), this.c());
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        return null;
    }
    
    public static int a(final ICommandListener commandListener, final String s) {
        try {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException ex) {
            throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[] { s });
        }
    }
    
    public static int a(final ICommandListener commandListener, final String s, final int n) {
        return a(commandListener, s, n, Integer.MAX_VALUE);
    }
    
    public static int a(final ICommandListener commandListener, final String s, final int n, final int n2) {
        final int a = a(commandListener, s);
        if (a < n) {
            throw new ExceptionInvalidNumber("commands.generic.num.tooSmall", new Object[] { a, n });
        }
        if (a > n2) {
            throw new ExceptionInvalidNumber("commands.generic.num.tooBig", new Object[] { a, n2 });
        }
        return a;
    }
    
    public static double b(final ICommandListener commandListener, final String s) {
        try {
            return Double.parseDouble(s);
        }
        catch (NumberFormatException ex) {
            throw new ExceptionInvalidNumber("commands.generic.double.invalid", new Object[] { s });
        }
    }
    
    public static EntityPlayer c(final ICommandListener commandListener) {
        if (commandListener instanceof EntityPlayer) {
            return (EntityPlayer)commandListener;
        }
        throw new ExceptionPlayerNotFound("You must specify which player you wish to perform this action on.", new Object[0]);
    }
    
    public static EntityPlayer c(final ICommandListener commandListener, final String s) {
        final EntityPlayer player = PlayerSelector.getPlayer(commandListener, s);
        if (player != null) {
            return player;
        }
        final EntityPlayer player2 = MinecraftServer.getServer().getPlayerList().getPlayer(s);
        if (player2 == null) {
            throw new ExceptionPlayerNotFound();
        }
        return player2;
    }
    
    public static String d(final ICommandListener commandListener, final String s) {
        final EntityPlayer player = PlayerSelector.getPlayer(commandListener, s);
        if (player != null) {
            return player.getLocalizedName();
        }
        if (PlayerSelector.isPattern(s)) {
            throw new ExceptionPlayerNotFound();
        }
        return s;
    }
    
    public static String a(final ICommandListener commandListener, final String[] array, final int n) {
        return a(commandListener, array, n, false);
    }
    
    public static String a(final ICommandListener commandListener, final String[] array, final int n, final boolean b) {
        final StringBuilder sb = new StringBuilder();
        for (int i = n; i < array.length; ++i) {
            if (i > n) {
                sb.append(" ");
            }
            String s = array[i];
            if (b) {
                final String playerNames = PlayerSelector.getPlayerNames(commandListener, s);
                if (playerNames != null) {
                    s = playerNames;
                }
                else if (PlayerSelector.isPattern(s)) {
                    throw new ExceptionPlayerNotFound();
                }
            }
            sb.append(s);
        }
        return sb.toString();
    }
    
    public static String a(final Object[] array) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            final String string = array[i].toString();
            if (i > 0) {
                if (i == array.length - 1) {
                    sb.append(" and ");
                }
                else {
                    sb.append(", ");
                }
            }
            sb.append(string);
        }
        return sb.toString();
    }
    
    public static String a(final Collection collection) {
        return a((Object[])collection.toArray(new String[0]));
    }
    
    public static boolean a(final String s, final String s2) {
        return s2.regionMatches(true, 0, s, 0, s.length());
    }
    
    public static List a(final String[] array, final String... array2) {
        final String s = array[array.length - 1];
        final ArrayList<String> list = new ArrayList<String>();
        for (final String s2 : array2) {
            if (a(s, s2)) {
                list.add(s2);
            }
        }
        return list;
    }
    
    public static List a(final String[] array, final Iterable iterable) {
        final String s = array[array.length - 1];
        final ArrayList<String> list = new ArrayList<String>();
        for (final String s2 : iterable) {
            if (a(s, s2)) {
                list.add(s2);
            }
        }
        return list;
    }
    
    public boolean a(final String[] array, final int n) {
        return false;
    }
    
    public static void a(final ICommandListener commandListener, final String s, final Object... array) {
        a(commandListener, 0, s, array);
    }
    
    public static void a(final ICommandListener commandListener, final int n, final String s, final Object... array) {
        if (CommandAbstract.a != null) {
            CommandAbstract.a.a(commandListener, n, s, array);
        }
    }
    
    public static void a(final ICommandDispatcher a) {
        CommandAbstract.a = a;
    }
    
    public int a(final ICommand command) {
        return this.c().compareTo(command.c());
    }
    
    static {
        CommandAbstract.a = null;
    }
}
